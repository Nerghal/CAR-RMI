package rmi;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

public class SiteImplTree extends UnicastRemoteObject implements SiteItfTree
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -1971677018818545300L;
	private List<SiteItfTree> listOfSons;
	private String id;
	private String data;

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
	
	public void propagateData(final String data) throws RemoteException  {	
		System.out.println(this.id + " initiates propagation with the following message : \"" + data + "\"");
		this.data = data;
		this.propagateToSons(data);
	}

	public String getId() throws RemoteException {
		return this.id;
	}
	
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
