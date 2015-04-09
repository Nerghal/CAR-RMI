package rmi;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

/**
 * Représente un objet RMI relié à ses pairs dans une topologie de 
 * type arbre. La communication est uniquement descendante. 
 *
 */
public class SiteImplTree extends UnicastRemoteObject implements SiteItfTree
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -1971677018818545300L;
	/**
	 * Contient la liste des fils de l'objet dans l'arbre.
	 */
	private List<SiteItfTree> listOfSons;
	/**
	 * L'id utilisé pour le bind de l'objet RMI.
	 */
	private String id;
	/**
	 * Les données dernièrement transférées.
	 */
	private String data;

	/**
	 * Construit une nouvelle instance de SiteImplTree sans fils.
	 * @param id 
	 * @throws RemoteException
	 */
	public SiteImplTree(String id) throws RemoteException {
		this.listOfSons = new ArrayList<SiteItfTree>();
		this.id = id;
	}
	
	public String getData() throws RemoteException  {
		return this.data;
	}

	public void addSon(SiteItfTree newSon) throws RemoteException {
		this.listOfSons.add(newSon);
	}
	
	/**
	 * Déclenche la propagation des données d'un nouveau message passé en 
	 * paramètre. Méthode appelée par le client.
	 */
	public void propagateData(final String data) throws RemoteException  {	
		System.out.println(this.id + " initiates propagation with the following message : \"" + data + "\"");
		this.data = data;
		this.propagateToSons(data);
	}

	public String getId() throws RemoteException {
		return this.id;
	}
	
	/**
	 * Déclenche la propagation concurrente du message passé en paramètre 
	 * parmi les fils de l'objet courant.
	 */
	public void propagateToSons(final String data) throws RemoteException {
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
	}

}
