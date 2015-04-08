package main;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.util.ArrayList;

import rmi.SiteImplTree;
import rmi.SiteItfTree;

public class ServerTree {

	public static void main(String[] args) throws RemoteException {
		ArrayList<SiteItfTree> list = new ArrayList<SiteItfTree>();

		SiteItfTree racineSkeleton = new SiteImplTree("racine");
		SiteItfTree skeleton2 = new SiteImplTree("2");
		SiteItfTree skeleton3 = new SiteImplTree("3");
		SiteItfTree skeleton4 = new SiteImplTree("4");
		SiteItfTree skeleton5 = new SiteImplTree("5");
		SiteItfTree skeleton6 = new SiteImplTree("6");
		
		racineSkeleton.addSon(skeleton2);
		racineSkeleton.addSon(skeleton5);
		
		skeleton2.addSon(skeleton3);
		skeleton2.addSon(skeleton4);
		
		skeleton5.addSon(skeleton6);
		
		try {
			Naming.rebind("racine", racineSkeleton);
			Naming.rebind("2", skeleton2);
			Naming.rebind("3", skeleton3);
			Naming.rebind("4", skeleton4);
			Naming.rebind("5", skeleton5);
			Naming.rebind("6", skeleton6);
			
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
