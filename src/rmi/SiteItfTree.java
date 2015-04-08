package rmi;

import java.rmi.RemoteException;

public interface SiteItfTree extends SiteItf {
	public void addSon(SiteItfTree newSon) throws RemoteException;
	public void setFather(SiteItfTree father) throws RemoteException;
	public SiteItfTree getFather() throws RemoteException;
}
