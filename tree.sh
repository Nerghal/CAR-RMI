#!/bin/sh

TERMINAL=xterm

#Launch registry
cd bin
rmic rmi.SiteImplTree
rmiregistry&
rmiregPID="$!"
cd ..

VMs_pid=""

#Servers
$TERMINAL -e "java -classpath bin/ main.ServerTree racine"&
VMs_pid="$VMs_pid $!"
sleep 0.5

$TERMINAL -e "java -classpath bin/ main.ServerTree 2 racine"&
VMs_pid="$VMs_pid $!"
sleep 0.5

$TERMINAL -e "java -classpath bin/ main.ServerTree 5 racine"&
VMs_pid="$VMs_pid $!"
sleep 0.5

$TERMINAL -e "java -classpath bin/ main.ServerTree 3 2"&
VMs_pid="$VMs_pid $!"
sleep 0.5

$TERMINAL -e "java -classpath bin/ main.ServerTree 4 2"&
VMs_pid="$VMs_pid $!"
sleep 0.5

$TERMINAL -e "java -classpath bin/ main.ServerTree 6 5"&
VMs_pid="$VMs_pid $!"
sleep 0.5

#Client
$TERMINAL -e "java -classpath bin/ main.Client"&

echo "Press any key to clean... "
read var_end

# Clean
kill $rmiregPID
for VM_pid in $VMs_pid
do
	kill $VM_pid
done
