# TP3 RMI
===========


## Auteurs

 - Kevin Mestdach
 - Samuel Grandsir
 
 
## Introduction

Implémentation Java de transfert de données en RMI sous la forme d'un 
arbre et d'un graphe.


## Architecture

Le projet comporte deux implémentations de serveur : l'une avec une 
topologie RMI en arbre et l'autre en graphe. Un seul code de client 
permet de communiquer avec l'un ou l'autre des deux serveurs. Deux 
scripts shell de lancement permettent de tester l'application.


## Design

Une interface basique de transfert de données est utilisée (SiteItf) 
et implémentée par deux interfaces spécifiques, l'une pour l'arbre (SiteItfTree) 
et l'autre pour les graphes (SiteItfGraphe). Chacune de ces deux interfaces 
possèdent leur implémentation.


## Gestion d'erreur

Dans les méthodes main, si les objets distants sont introuvables on les 
ignore et essaye de continuer malgré tout.

			try {
				SiteItfTree father = (SiteItfTree) Naming.lookup(rmiPrefix+args[1]);
				father.addSon(newNode);
			} catch (NotBoundException e) {
				System.err.println(args[1] + " doesn't exist. Ignoring...");
			}


## Code samples

### Propagation vers les fils pour l'arbre

System.out.println(this.id + " receives the message : \"" + data + "\"");
		
		for (final SiteItfTree son : listOfSons) {
			System.out.println(this.id + " propagates to " + son.getId() + " the following message : \"" + data + "\"");
			
			new Thread() {
				public void run() {
					try {
						son.propagateToSons(data);
					} catch (RemoteException e) {
						e.printStackTrace();
					}
				}
			}.start();
		}
		
### Ajout d'un voisin pour le graphe

	public void addNeighbour(SiteItfGraph newNeighbour) throws RemoteException {			
		if (!listOfNeighbours.contains(newNeighbour)) {
			this.listOfNeighbours.add(newNeighbour);
			newNeighbour.addNeighbour(this);
		}
	}
	
### Initialisation de la propagation pour le graphe

	public void propagateData(final String data) throws RemoteException {	
		System.out.println(this.id + " initiates propagation with the following message : \"" + data + "\"");
		this.data = data;
		this.propagateToNeighbour(data, new ArrayList<SiteItfGraph>());
	}
		
### Propagation vers les voisins pour le graphe

	public void propagateToNeighbour(final String data, final ArrayList<SiteItfGraph> list) throws RemoteException {
		if (list.contains(this))
			return;
		
		list.add(this);
		System.out.println(this.id + " receives the message : \"" + data + "\"");

		for (final SiteItfGraph neighbour : listOfNeighbours) {
			System.out.println(this.id + " propagates to " + neighbour.getId() + " the following message : \"" + data + "\"");
			new Thread() {
				public void run() {
					try {
						neighbour.propagateToNeighbour(data, list);
					} catch (RemoteException e) {
						e.printStackTrace();
					}
				}
			}.start();
		}
	}
	
### Serveur pour le graphe (avec construction dynamique du graphe)

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