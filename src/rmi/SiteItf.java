package rmi;


import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Interface pour modéliser un objet RMI pour la propagation de données. 
 *
 */
public interface SiteItf extends Remote
{
	public String getData() throws RemoteException;
	public void propagateData(String data) throws RemoteException;
}
