package CommonServer.CommonNum;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import CommonServer.CommonNum_Interface;
/**
 * 
 * @author peilin
 *
 */
public class CommonNum implements CommonNum_Interface {
	public static int startNumber = 10000;
	@Override
	public synchronized int getSartNumber() throws RemoteException {
		return startNumber++;
	}
	
	public static void exportServerObj() throws Exception{
		String server_name = "CommonServer";
		CommonNum_Interface comn_obj = new CommonNum();
		CommonNum_Interface stub = (CommonNum_Interface) UnicastRemoteObject.exportObject(comn_obj, 0);
		Registry registry = LocateRegistry.createRegistry(1099);
        registry.bind(server_name, stub);
        System.out.println("CommonServer bound");
	}
	
	public static void main(String[] args){
		
		try {
			exportServerObj();
		} catch (Exception e) {
		
			e.printStackTrace();
		}
		
		
	}
}