package main;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;

import rmi.SiteImplGraph;
import rmi.SiteItfGraph;

public class ServerGraph {

	public static void main(String[] args) throws RemoteException, MalformedURLException {
		if (args.length < 1 || args.length > 2) {
			System.err.println("Erreur arguments "+args.length);
			System.err.println("Usage : ServerTree name_object_to_create [name_father]");
		}
		
		String rmiPrefix = "rmi://localhost:1099/";
		
		SiteImplGraph newNode = new SiteImplGraph(args[0]);
		
		Naming.rebind(args[0], newNode);
		
		if (args.length == 2) {
			try {
				SiteItfGraph father = (SiteItfGraph) Naming.lookup(rmiPrefix+args[1]);
				father.addNeighbour(newNode);
			} catch (NotBoundException e) {
				System.err.println(args[1] + " doesn't exist. Ignoring...");
			}
		}
	}

}