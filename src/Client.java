

import java.rmi.*;
import java.rmi.registry.*;
import java.util.*;

public class Client {
    public static void main(String[] argv) throws RemoteException {
    	ArrayList<SiteItf> elements = new ArrayList<SiteItf>();
    	
    	int port = 1234;
    	
    	Registry memory;
    	
    	
    	
        try {	
        	memory = LocateRegistry.getRegistry(port);
        	for(int i = 1 ; i <= 6 ; i++){
	            elements.add((SiteItf) memory.lookup("ID" + i));
        	}
        } catch (Exception e) {
            e.printStackTrace();
        }
        

        elements.get(0).addSon(elements.get(1));
        elements.get(1).setFather(elements.get(0));
        elements.get(0).addSon(elements.get(4)); 
        elements.get(4).setFather(elements.get(0));
        elements.get(1).addSon(elements.get(2)); 
        elements.get(2).setFather(elements.get(1));
        elements.get(1).addSon(elements.get(3)); 
        elements.get(3).setFather(elements.get(1));
        elements.get(4).addSon(elements.get(5)); 
        elements.get(5).setFather(elements.get(4));

        elements.get(0).sendData("Test");
        try {
			Thread.sleep(1);
		} catch (InterruptedException e) 
		{
			System.err.println("Error from the Thread");
			e.printStackTrace();
		}
              
        System.out.println("TESTING ...");
        
        for(int i = 0 ; i < elements.size() ; i++)
        {
        	System.out.println(elements.get(i).getData());
        }
    }	
}
