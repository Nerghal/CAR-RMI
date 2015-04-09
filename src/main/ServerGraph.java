package main;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import rmi.SiteImplGraph;
import rmi.SiteItfGraph;

public class ServerGraph {

	/**
	 * 
	 * @param args le premier argument est le nom du nouvel objet à créer et binder. 
	 * Les arguments suivants (optionnels) sont les objets qui seront les voisins du nouvel objet 
	 * créé dans le graphe. Ces voisins doivent pré-exister.
	 * @throws RemoteException
	 * @throws MalformedURLException
	 */
	public static void main(String[] args) throws RemoteException, MalformedURLException {
		if (args.length < 1) {
			System.err.println("Erreur arguments");
			System.err.println("Usage : ServerGraph name_object_to_create [name_neighbour1 [name_neighbour2 [...]]]");
			
			System.exit(1);
		}
		
		String rmiPrefix = "rmi://localhost:1099/";
		
		SiteImplGraph newNode = new SiteImplGraph(args[0]);
		
		Naming.rebind(args[0], newNode);
		
		if (args.length > 1) {
			for (int i=1 ; i < args.length ; i++) {
				try {
					SiteItfGraph newNeighBour = (SiteItfGraph) Naming.lookup(rmiPrefix+args[i]);
					newNode.addNeighbour(newNeighBour);
				} catch (NotBoundException e) {
					System.err.println(args[1] + " doesn't exist. Ignoring...");
				}
			}
		}
	}
}