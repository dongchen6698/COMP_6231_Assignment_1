package Server_Side.Server_COUNT;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import Server_Side.NumAssign_Interface;

public class NumAssign implements NumAssign_Interface {
	public static int startNumber = 10000;
	
	@Override
	public synchronized int getSartNumber() throws RemoteException {
		return startNumber++;
	}
	
	public static void exportServerObj(){
		try {
			String server_name = "NumberAssign";
			NumAssign_Interface comn_obj = new NumAssign();
			NumAssign_Interface stub = (NumAssign_Interface) UnicastRemoteObject.exportObject(comn_obj, 0);
			Registry registry = LocateRegistry.createRegistry(1099);
	        registry.bind(server_name, stub);
	        System.out.println("Number Assign Server bound");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public static void main(String[] args){
		exportServerObj();
	}
}