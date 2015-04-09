package rmi;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

/**
 * Représente un objet RMI relié à ses pairs dans une topologie de 
 * type graphe non-orienté.
 */
public class SiteImplGraph extends UnicastRemoteObject implements SiteItfGraph
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -1971677018818545300L;
	
	/**
	 * La liste des voisins
	 */
	private ArrayList<SiteItfGraph> listOfNeighbours;
	/**
	 * L'id utilisé pour le bind de l'objet RMI.
	 */
	private String id;
	/**
	 * Les données dernièrement transférées.
	 */
	private String data;

	/**
	 * Construit une nouvelle instance de SiteImplTree sans voisins.
	 * @param id 
	 * @throws RemoteException
	 */
	public SiteImplGraph(String id) throws RemoteException {
		this.listOfNeighbours = new ArrayList<SiteItfGraph>();
		this.id = id;
	}
	
	public String getData() throws RemoteException {
		return this.data;
	}

	/**
	 * Ajoute le voisin passé en paramètre à l'objet courant et vice-versa.
	 */
	public void addNeighbour(SiteItfGraph newNeighbour) throws RemoteException {			
		if (!listOfNeighbours.contains(newNeighbour)) {
			this.listOfNeighbours.add(newNeighbour);
			newNeighbour.addNeighbour(this);
		}
	}

	/**
	 * Retourne une copie de la liste des voisins.
	 */
	public ArrayList<SiteItfGraph> getNeighbours() throws RemoteException {
		ArrayList<SiteItfGraph> l = new ArrayList<SiteItfGraph>(this.listOfNeighbours);
		return l;
	}
	
	/**
	 * Déclenche la propagation des données d'un nouveau message passé en 
	 * paramètre. Méthode appelée par le client.
	 */
	public void propagateData(final String data) throws RemoteException {	
		System.out.println(this.id + " initiates propagation with the following message : \"" + data + "\"");
		this.data = data;
		this.propagateToNeighbour(data, new ArrayList<SiteItfGraph>());
	}

	public String getId() throws RemoteException {
		return this.id;
	}
	
	/**
	 * Déclenche la propagation concurrente du message passé en paramètre 
	 * parmi les voisins de l'objet courant en veillant à ne pas répéter le message.
	 */
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

}