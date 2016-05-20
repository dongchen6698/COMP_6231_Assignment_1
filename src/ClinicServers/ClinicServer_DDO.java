package ClinicServers;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import NumAssignServer.NumAssign_Interface;
import RecordInfomation.DoctorRecord;
import RecordInfomation.NurseRecord;
import RecordInfomation.RecordInfo;

public class ClinicServer_DDO implements ClinicServers_Interface {
	static ArrayList<String> managerlist = new ArrayList<String>(Arrays.asList("ddo10000", "ddo10001", "ddo10002"));
	static Map<Character, ArrayList<RecordInfo>> ddo_hash = new HashMap<Character, ArrayList<RecordInfo>>();
	static ArrayList<RecordInfo> list = null;
	static Registry registry;
	static NumAssign_Interface stub;
	static int startID;
	
	public ClinicServer_DDO() {
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
	
	public static NumAssign_Interface getStubFromCommon() throws Exception{
		registry = LocateRegistry.getRegistry();
		stub = (NumAssign_Interface)registry.lookup("CommonServer");
		return stub;
	}
	
	@Override
	public String createDRecord(String firstName, String lastName, String address, String phone,
			String specialization, String location) throws RemoteException {
		
		String recordID = null;
		RecordInfo doc_recorde_with_recordID = null;
		Character capital_lastname = lastName.charAt(0);
		if(ddo_hash.containsKey(capital_lastname)){
			list = ddo_hash.get(capital_lastname);
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
			ddo_hash.put(capital_lastname, list);
		}
		return "DoctorID: " + doc_recorde_with_recordID.getRecordID() + " buid succeed !" + "\n" +doc_recorde_with_recordID.toString();
	}

	@Override
	public String createNRecord(String firstName, String lastName, String designation, String status,
			String statusDate) throws RemoteException {
		
		String recordID = null;
		RecordInfo nur_recorde_with_recordID = null;
		 
		Character capital_lastname = lastName.charAt(0);
		if(ddo_hash.containsKey(capital_lastname)){
			list = ddo_hash.get(capital_lastname);
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
			ddo_hash.put(capital_lastname, list);
		}
		return "NurseID: " + nur_recorde_with_recordID.getRecordID() + " buid succeed !" + "\n" +nur_recorde_with_recordID.toString();
	}

	@Override
	public String getRecordCounts(String recordType) throws RemoteException {
		
		return Integer.toString(ddo_hash.size());
	}

	@Override
	public String editRecord(String recordID, String fieldName, String newValue) throws RemoteException {
		
		for(Map.Entry<Character, ArrayList<RecordInfo>> entry:ddo_hash.entrySet()){
			//System.out.println(entry.getKey());
			for(RecordInfo record:entry.getValue()){
				//System.out.println(record.getRecordID());
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
		return "Hello";
	}
	
	public static void exportServerObj() throws Exception{
		String server_name = "Dollard-des-Ormeaux";
		ClinicServers_Interface mtl_obj = new ClinicServer_DDO();
		ClinicServers_Interface stub = (ClinicServers_Interface) UnicastRemoteObject.exportObject(mtl_obj, 0);
		Registry registry = LocateRegistry.getRegistry();
        registry.rebind(server_name, stub);
        ddo_hash.put('l', new ArrayList<RecordInfo>(Arrays.asList(new RecordInfo("DR00005", new DoctorRecord("yong", "li", "Montreal", "12345678", "abc", "mtl")))));
        ddo_hash.put('z', new ArrayList<RecordInfo>(Arrays.asList(new RecordInfo("DR00006", new DoctorRecord("liu", "zhang", "laval", "12345678", "abc", "lvl")))));
        System.out.println("ClinicServer_DDO bound");
	}
	public static void main(String[] args) {
//		if(System.getSecurityManager() == null){
//			System.setSecurityManager(new SecurityManager());
//		}
		DatagramSocket aSocket = null;
		try{
			exportServerObj();
			aSocket = new DatagramSocket(6003); 
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
