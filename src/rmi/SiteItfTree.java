package rmi;

import java.rmi.RemoteException;

public interface SiteItfTree extends SiteItf {
	public void addSon(SiteItfTree newSon) throws RemoteException;
	public String getId() throws RemoteException;
	public void propagateToSons(String data) throws RemoteException;
}
