package rmi;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class SiteImplGraph extends UnicastRemoteObject implements SiteItfGraph
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -1971677018818545300L;
	private ArrayList<SiteItfGraph> listOfNeighbours;
	private ArrayList<SiteItfGraph> listOfDone;
	private String id,data;

	public SiteImplGraph(String id) throws RemoteException
	{
		this.listOfNeighbours = new ArrayList<SiteItfGraph>();
		this.id = id;
	}
	
	public String getData() throws RemoteException 
	{
		return this.data;
	}

	public void addNeighbour(SiteItfGraph newNeighbour) throws RemoteException
	{
		if (!listOfNeighbours.contains(newNeighbour)) {
		newNeighbour.addNeighbour(this);
		this.listOfNeighbours.add(newNeighbour);
		}
	}

	
	public ArrayList<SiteItfGraph> getNeighbours() throws RemoteException
	{
		return this.listOfNeighbours;
	}
	
	public void propagateData(final String data) throws RemoteException 
	{	
		System.out.println(this.id + " initiates propagation with the following message : \"" + data + "\"");
		this.data = data;
		this.propagateToNeighbour(data,listOfDone);
	}

	public String getId() 
	{
		return this.id;
	}
	
	private void propagateToNeighbour(final String data, ArrayList<SiteItfGraph> list) {
		for (final SiteItfGraph neighbour : listOfNeighbours) {
			if (!listOfDone.contains(neighbour)) {
			System.out.println(this.id + " propagates to " + ((SiteImplGraph) neighbour).id + " the following message : \"" + data + "\"");
			this.listOfDone.add(neighbour);
			new Thread() {
				public void run() {
					((SiteImplGraph) neighbour).propagateToNeighbour(data,listOfDone);
				}
			}.start();
			}
		}
	}

}