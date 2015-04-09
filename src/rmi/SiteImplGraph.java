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
	private String id;
	private String data;

	public SiteImplGraph(String id) throws RemoteException {
		this.listOfNeighbours = new ArrayList<SiteItfGraph>();
		this.id = id;
	}
	
	public String getData() throws RemoteException {
		return this.data;
	}

	public void addNeighbour(SiteItfGraph newNeighbour) throws RemoteException {			
		if (!listOfNeighbours.contains(newNeighbour)) {
			this.listOfNeighbours.add(newNeighbour);
			newNeighbour.addNeighbour(this);
		}
	}

	
	public ArrayList<SiteItfGraph> getNeighbours() throws RemoteException {
		ArrayList<SiteItfGraph> l = new ArrayList<SiteItfGraph>(this.listOfNeighbours);
		return l;
	}
	
	public void propagateData(final String data) throws RemoteException {	
		System.out.println(this.id + " initiates propagation with the following message : \"" + data + "\"");
		this.data = data;
		this.propagateToNeighbour(data, new ArrayList<SiteItfGraph>());
	}

	public String getId() throws RemoteException {
		return this.id;
	}
	
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