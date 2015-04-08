package rmi;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class SiteImplTree extends UnicastRemoteObject implements SiteItfTree
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -1971677018818545300L;
	private SiteItfTree father;
	private ArrayList<SiteItfTree> listOfSons;
	private String id,data;

	public SiteImplTree(String id) throws RemoteException
	{
		this.listOfSons = new ArrayList<SiteItfTree>();
		this.id = id;
	}
	
	public String getData() throws RemoteException 
	{
		return this.data;
	}

	public void addSon(SiteItfTree newSon) throws RemoteException
	{
		newSon.setFather(this);
		this.listOfSons.add(newSon);
	}

	public void setFather(SiteItfTree father) throws RemoteException
	{
		this.father = father;
	}
	
	public SiteItfTree getFather() throws RemoteException
	{
		return this.father;
	}
	
	public void propagateData(final String data) throws RemoteException 
	{	
		System.out.println(this.id + " initiates propagation with the following message : \"" + data + "\"");
		this.data = data;
		this.propagateToSons(data);
	}

	public String getId() 
	{
		return this.id;
	}
	
	private void propagateToSons(final String data) {
		for (final SiteItfTree son : listOfSons) {
			System.out.println(this.id + " propagates to " + ((SiteImplTree) son).id + " the following message : \"" + data + "\"");
			
			new Thread() {
				public void run() {
					((SiteImplTree) son).propagateToSons(data);
				}
			}.start();
		}
	}

}
