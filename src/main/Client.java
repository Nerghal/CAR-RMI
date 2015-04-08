package main;


import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Random;

import rmi.SiteItf;

public class Client {
    public static void main(String[] args) throws RemoteException, MalformedURLException {
    	ArrayList<SiteItf> stubs = new ArrayList<SiteItf>();
    	
    	String [] names = Naming.list("rmi://localhost:1099");
    	SiteItf racine = null;
    	Random r = new Random();
		
		/* Récupération des objets distants. */
    	for (String name : names) {
    		System.out.println(name);
    		
    		try {
				SiteItf remote = (SiteItf) Naming.lookup(name);
				stubs.add(remote);
				if (name.equals("//localhost:1099/racine"))
					racine = remote;
			} catch (NotBoundException e) {
				System.err.println("Object " + name + " not bound!");
			}
    	}
    	
    	if (racine != null) {
    		String rootMessage = "Message depuis la racine";
    		System.out.println("Envoi de : " + rootMessage);
    		racine.propagateData(rootMessage);
    	}
    	
    	String randomMessage = "Message depuis un noeud aléatoire";
    	SiteItf randomRemote = stubs.get(r.nextInt(stubs.size()));
    	System.out.println("Envoi de : " + randomMessage);
    	randomRemote.propagateData(randomMessage);
    }
}
