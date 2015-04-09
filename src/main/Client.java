package main;


import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Random;

import rmi.SiteItf;

/**
 * Classe client pour communiquer avec les objets distants et le registre RMI local.
 *
 */
public class Client {
    public static void main(String[] args) throws RemoteException, MalformedURLException {
    	if (args.length != 1) {
    		System.err.println("Usage: Client nb_messages");
    		
    		return;
    	}
    	
    	ArrayList<SiteItf> stubs = new ArrayList<SiteItf>();
    	
    	/* Récupération des noms dans le registre */
    	String [] names = Naming.list("rmi://localhost:1099");
    	SiteItf racine = null;
    	Random r = new Random();
		
		/* Récupération des objets distants. */
    	for (String name : names) {
    		System.out.println(name);
    		
    		try {
				SiteItf remote = (SiteItf) Naming.lookup(name);
				stubs.add(remote);
				
				/* Si on est dans le cas de l'arbre. Permet d'assurer qu'au moins un 
				 * message sera envoyé depuis la racine. A condition que ce nom ait 
				 * été donné à la racine de l'arbre... */
				if (name.equals("//localhost:1099/racine"))
					racine = remote;
			} catch (NotBoundException e) {
				System.err.println("Object " + name + " not bound!");
			}
    	}
    	
    	/* Message depuis la racine. */
    	if (racine != null) {
    		String rootMessage = "Message depuis la racine";
    		System.out.println("Envoi de : " + rootMessage);
    		racine.propagateData(rootMessage);
    	}
    	
    	for (int i=0 ; i<Integer.parseInt(args[0]) ; i++) {
	    	String randomMessage = "Message n°"+i+" depuis un noeud aléatoire";
	    	SiteItf randomRemote = stubs.get(r.nextInt(stubs.size()));
	    	System.out.println("Envoi de : " + randomMessage);
	    	randomRemote.propagateData(randomMessage);
    	}
    }
}
