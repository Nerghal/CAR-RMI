package rmi;


import java.rmi.Remote;
import java.rmi.RemoteException;

public interface SiteItf extends Remote
{
	public String getData() throws RemoteException;
	public void propagateData(String data) throws RemoteException;
}
