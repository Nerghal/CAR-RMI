
import java.rmi.*;

public interface SiteItf extends Remote
{
	public String getData() throws RemoteException;

	public void sendData(String data) throws RemoteException;

	public void addSon(SiteItf son) throws RemoteException;

	public void setFather(SiteItf father) throws RemoteException;
}
