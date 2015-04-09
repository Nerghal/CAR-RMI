package rmi;

import java.rmi.RemoteException;
import java.util.ArrayList;

public interface SiteItfGraph extends SiteItf {
	public void addNeighbour(SiteItfGraph newNeighbour) throws RemoteException;
	public ArrayList<SiteItfGraph> getNeighbours() throws RemoteException;
}