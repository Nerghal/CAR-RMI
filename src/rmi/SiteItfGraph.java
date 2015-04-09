package rmi;

import java.rmi.RemoteException;
import java.util.ArrayList;

/**
 * Interface pour la communication entre objets RMI connectés selon une 
 * topologie de type graphe. 
 *
 */
public interface SiteItfGraph extends SiteItf {
	
	/**
	 * Ajoute un voisin à la liste des voisins pour la propagation des données.
	 * @param newNeighbour le nouveau voisin
	 * @throws RemoteException
	 */
	public void addNeighbour(SiteItfGraph newNeighbour) throws RemoteException;
	
	public ArrayList<SiteItfGraph> getNeighbours() throws RemoteException;
	
	public String getId() throws RemoteException;
	
	/**
	 * Effectue la propagation parmi les voisins de l'objet courant.
	 * @param data les données à propager
	 * @param list la liste des objets ayant déjà reçus le message
	 * @throws RemoteException
	 */
	public void propagateToNeighbour(final String data, final ArrayList<SiteItfGraph> list) throws RemoteException;
}