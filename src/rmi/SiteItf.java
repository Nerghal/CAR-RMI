package rmi;


import java.rmi.Remote;
import java.rmi.RemoteException;

public interface SiteItf extends Remote
{
	public String getData() throws RemoteException;

	public void sendData(String data) throws RemoteException;

	public void addSon(SiteItf son) throws RemoteException;

	public void setFather(SiteItf father) throws RemoteException;
}
