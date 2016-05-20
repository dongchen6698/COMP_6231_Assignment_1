package ClinicServers.Montreal_MTL;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import ClinicServers.ClinicServers_Interface;
import CommonServer.CommonNum_Interface;
import RecordInfomation.DoctorRecord;
import RecordInfomation.NurseRecord;
import RecordInfomation.RecordInfo;

public class ClinicServer_MTL implements ClinicServers_Interface {
	static ArrayList<String> managerlist = new ArrayList(Arrays.asList("mtl10000", "mtl10001", "mtl10002"));
	static Map<Character, ArrayList<RecordInfo>> mtl_hash = new HashMap<Character, ArrayList<RecordInfo>>();
	static ArrayList<RecordInfo> list = null;
	static Registry registry;
	static CommonNum_Interface stub;
	static int startID;
	
	public ClinicServer_MTL() {
		super();
	}
	
	public static String checkManagerIDValid(String managerID){
		for(String account: managerlist){
			if(managerID.equals(account)){
				return "yes";
			}
		}
		return "no";
	}
	
	public static CommonNum_Interface getStubFromCommon() throws Exception{
		registry = LocateRegistry.getRegistry();
		stub = (CommonNum_Interface)registry.lookup("CommonServer");
		return stub;
	}
	
	@Override
	public String createDRecord(String firstName, String lastName, String address, String phone,
			String specialization, String location) throws RemoteException {
		
		String recordID = null;
		RecordInfo doc_recorde_with_recordID = null;
		Character capital_lastname = lastName.charAt(0);
		if(mtl_hash.containsKey(capital_lastname)){
			list = mtl_hash.get(capital_lastname);
		}else{
			list = new ArrayList<RecordInfo>();
		}
		DoctorRecord doc_recorde = new DoctorRecord(firstName, lastName, address, phone, specialization, location);
		synchronized (this) {
			try{
			recordID = "DR" + getStubFromCommon().getSartNumber();
			}catch(Exception e){
				e.printStackTrace();
			}
			doc_recorde_with_recordID = new RecordInfo(recordID, doc_recorde);
			list.add(doc_recorde_with_recordID);
			mtl_hash.put(capital_lastname, list);
		}
		return "DoctorID: " + doc_recorde_with_recordID.getRecordID() + " buid succeed !" + "\n" +doc_recorde_with_recordID.toString();
	}

	@Override
	public String createNRecord(String firstName, String lastName, String designation, String status,
			String statusDate) throws RemoteException {
		
		String recordID = null;
		RecordInfo nur_recorde_with_recordID = null;
		 
		Character capital_lastname = lastName.charAt(0);
		if(mtl_hash.containsKey(capital_lastname)){
			list = mtl_hash.get(capital_lastname);
		}else{
			list = new ArrayList<RecordInfo>();
		}
		NurseRecord nur_recorde = new NurseRecord(firstName, lastName, designation, status, statusDate);
		synchronized (this) {
			try{
				recordID = "NR" + getStubFromCommon().getSartNumber();
				}catch(Exception e){
					e.printStackTrace();
				}
			nur_recorde_with_recordID = new RecordInfo(recordID, nur_recorde);
			list.add(nur_recorde_with_recordID);
			mtl_hash.put(capital_lastname, list);
		}
		return "NurseID: " + nur_recorde_with_recordID.getRecordID() + " buid succeed !" + "\n" +nur_recorde_with_recordID.toString();
	}

	@Override
	public String getRecordCounts(String recordType) throws RemoteException {
		
		return Integer.toString(mtl_hash.size());
	}

	@Override
	public String editRecord(String recordID, String fieldName, String newValue) throws RemoteException {
		
		for(Map.Entry<Character, ArrayList<RecordInfo>> entry:mtl_hash.entrySet()){
			System.out.println(entry.getKey());
			for(RecordInfo record:entry.getValue()){
				System.out.println(record.getRecordID());
				if(recordID.equalsIgnoreCase(record.getRecordID())){
					if(recordID.contains("DR")||recordID.contains("dr")){
						if(fieldName.equalsIgnoreCase("Address")){
							record.getDoctorRecord().setAddress(newValue);
							return "edit succeed !\n"+record.toString();
						}else if(fieldName.equalsIgnoreCase("Phone")){
							record.getDoctorRecord().setPhone(newValue);
							return "edit succeed !\n"+record.toString();
						}else if (fieldName.equalsIgnoreCase("Location")){
							record.getDoctorRecord().setLocation(newValue);
							return "edit succeed !\n"+record.toString();
						}
					}else if(recordID.contains("NR")||recordID.contains("nr")){
						if(fieldName.equalsIgnoreCase("Designation")){
							record.getNurseRecord().setDesignation(newValue);
							return "edit succeed !\n"+record.toString();
						}else if(fieldName.equalsIgnoreCase("Status")){
							record.getNurseRecord().setStatus(newValue);
							return "edit succeed !\n"+record.toString();
						}else if (fieldName.equalsIgnoreCase("statusDate")){
							record.getNurseRecord().setStatusDate(newValue);
							return "edit succeed !\n"+record.toString();
						}
					}
				}
			}
		}
		
		return "edit failed";
	}
	
	@Override
	public String sayHello() throws RemoteException {
		// TODO Auto-generated method stub
		return "Hello";
	}
	
	public static void exportServerObj() throws Exception{
		String server_name = "Montreal";
		ClinicServers_Interface mtl_obj = new ClinicServer_MTL();
		ClinicServers_Interface stub = (ClinicServers_Interface) UnicastRemoteObject.exportObject(mtl_obj, 0);
		Registry registry = LocateRegistry.getRegistry();
        registry.rebind(server_name, stub);
        mtl_hash.put('l', new ArrayList<RecordInfo>(Arrays.asList(new RecordInfo("DR00001", new DoctorRecord("si", "li", "Montreal", "12345678", "abc", "mtl")))));
        mtl_hash.put('z', new ArrayList<RecordInfo>(Arrays.asList(new RecordInfo("DR00002", new DoctorRecord("san", "zhang", "laval", "12345678", "abc", "lvl")))));
        System.out.println("ClinicServer_MTL bound");
	}
	public static void main(String[] args) {
//		if(System.getSecurityManager() == null){
//			System.setSecurityManager(new SecurityManager());
//		}
		DatagramSocket aSocket = null;
		try{
			exportServerObj();
			aSocket = new DatagramSocket(6001); 
			byte[] buffer = new byte[100]; 
			while(true){
				DatagramPacket request = new DatagramPacket(buffer, buffer.length); 
				aSocket.receive(request);
				String result = checkManagerIDValid(new String(request.getData()).trim());
				DatagramPacket reply = new DatagramPacket(result.getBytes(),result.getBytes().length, request.getAddress(), request.getPort()); 
				aSocket.send(reply);
			}
		}
		catch(Exception e){
			e.printStackTrace();
		} 
		finally{
			if(aSocket != null) aSocket.close();
		}
	}
}
