package rmi;

import java.rmi.RemoteException;

/**
 * Interface pour la communication entre objets RMI connectés selon une 
 * topologie de type arbre. 
 *
 */
public interface SiteItfTree extends SiteItf {
	/**
	 * Ajoute un fils à la liste des fils pour la propagation des données.
	 * @param newSon le nouveau fils
	 * @throws RemoteException
	 */
	public void addSon(SiteItfTree newSon) throws RemoteException;
	
	public String getId() throws RemoteException;
	
	/**
	 * Effectue la propagation parmi les fils de l'objet courant.
	 * @param data les données à propager
	 * @throws RemoteException
	 */
	public void propagateToSons(String data) throws RemoteException;
}
