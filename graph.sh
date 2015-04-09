#!/bin/sh

TERMINAL=xterm

#Launch registry
cd bin
rmic rmi.SiteImplGraph
rmiregistry&
rmiregPID="$!"
cd ..

VMs_pid=""

#Servers
$TERMINAL -e "java -classpath bin/ main.ServerGraph 1"&
VMs_pid="$VMs_pid $!"
sleep 0.5

$TERMINAL -e "java -classpath bin/ main.ServerGraph 2 1"&
VMs_pid="$VMs_pid $!"
sleep 0.5

$TERMINAL -e "java -classpath bin/ main.ServerGraph 5 1"&
VMs_pid="$VMs_pid $!"
sleep 0.5

$TERMINAL -e "java -classpath bin/ main.ServerGraph 3 2"&
VMs_pid="$VMs_pid $!"
sleep 0.5

$TERMINAL -e "java -classpath bin/ main.ServerGraph 4 2"&
VMs_pid="$VMs_pid $!"
sleep 0.5

$TERMINAL -e "java -classpath bin/ main.ServerGraph 6 5 4"&
VMs_pid="$VMs_pid $!"
sleep 0.5

#Client
$TERMINAL -e "java -classpath bin/ main.Client 3"&

echo "Press any key to clean... "
read var_end

# Clean
kill $rmiregPID
for VM_pid in $VMs_pid
do
	kill $VM_pid
done
