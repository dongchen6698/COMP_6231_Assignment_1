package Client_Side;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import Server_Side.ClinicServers_Interface;

public class MultiThreadTestCreate implements Runnable{
	static Registry registry;
	static ClinicServers_Interface stub;
	String threadID;
	
	public MultiThreadTestCreate(String n_threadID) {
		this.threadID = n_threadID;
	}
	
	public static void getStubForTest(){
		try {
			registry = LocateRegistry.getRegistry();
			stub = (ClinicServers_Interface)registry.lookup("server_mtl");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void run() {
		try {
			System.err.println(threadID + " start create DR.");
			for(int i=0;i<5;i++){
				String str_dr = stub.createDRecord("test_DR_FirstName", "test_DR_LastName", "test_DR_Address", "test_DR_Phone", "abc", "mtl");
				System.out.println(str_dr);
				String str_nr = stub.createNRecord("test_NR_FirstName", "test_NR_LastName", "junior", "active", "2016/04/03");
				System.out.println(str_nr);
			}	
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		getStubForTest();
		Thread testForCreateDR[] = new Thread[10];
		for(int i=0;i<10;i++){
			testForCreateDR[i] = new Thread(new MultiThreadTestCreate("CreatDR_T " + i));
		}
		for(int i=0;i<10;i++){
			testForCreateDR[i].start();
		}				
	}
}

