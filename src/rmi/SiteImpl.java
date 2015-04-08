package rmi;
import java.rmi.RemoteException;
import java.util.ArrayList;

public class SiteImpl implements SiteItf
{
	private SiteItf father;
	private ArrayList<SiteItf> listofsons;
	private String id,data;

	public SiteImpl(String id)
	{
		listofsons = new ArrayList<SiteItf>();
		this.id = id;
	}
	
	public String getData() throws RemoteException 
	{
		System.out.println(data);
		return data;
	}

	public void addSon(SiteItf son) 
	{
		listofsons.add(son);
	}

	public void setFather(SiteItf father) 
	{
		this.father = father;
	}
	
	public SiteItf getFather() 
	{
		return father;
	}

	public ArrayList<SiteItf> getSons() 
	{
		return listofsons;
	}

	public String geId() 
	{
		return id;
	}

	public void sendData(final String data) throws RemoteException 
	{
		System.out.println("My identification is :" + id);
		
		this.data = data;
		
		for(final SiteItf temp : listofsons)
		{
			new Thread(){
				public void run()
				{
					try 
					{
						temp.sendData(data);
					} 
					catch (RemoteException e) 
					{
						
						e.printStackTrace();
					}
				}
			}.start();
		}
		
	}

}
