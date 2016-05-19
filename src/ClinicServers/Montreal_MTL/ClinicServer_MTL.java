package ClinicServers.Montreal_MTL;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import ClinicServers.ClinicServers_Interface;
import RecordInfomation.DoctorRecord;
import RecordInfomation.NurseRecord;
import RecordInfomation.RecordInfo;

public class ClinicServer_MTL implements ClinicServers_Interface {
	Map<Character, ArrayList<RecordInfo>> mtl_hash = new HashMap<Character, ArrayList<RecordInfo>>();
	ArrayList<RecordInfo> list = null;
	int startID = 10000;
	
	public ClinicServer_MTL() {
		// TODO Auto-generated constructor stub
		super();
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
			recordID = "DR" + Integer.toString(startID++);
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
			recordID = "NR" + Integer.toString(startID++);
			nur_recorde_with_recordID = new RecordInfo(recordID, nur_recorde);
			list.add(nur_recorde_with_recordID);
			mtl_hash.put(capital_lastname, list);
		}
		return "NurseID: " + nur_recorde_with_recordID.getRecordID() + " buid succeed !" + "\n" +nur_recorde_with_recordID.toString();
	}

	@Override
	public String getRecordCounts(String recordType) throws RemoteException {
		
		return null;
	}

	@Override
	public String editRecord(String recordID, String fieldName, String newValue) throws RemoteException {
		
		for(Map.Entry<Character, ArrayList<RecordInfo>> entry:mtl_hash.entrySet()){
			System.out.println(entry.getKey());
			for(RecordInfo record:entry.getValue()){
				System.out.println(record.getRecordID());
				if(recordID.equalsIgnoreCase(record.getRecordID())){
					if(recordID.contains("DR")){
						if(fieldName.equalsIgnoreCase("Address")){
							record.getDoctorRecord().setAddress(newValue);
							return "edit succeed !";
						}else if(fieldName.equalsIgnoreCase("Phone")){
							record.getDoctorRecord().setPhone(newValue);
							return "edit succeed !";
						}else if (fieldName.equalsIgnoreCase("Location")){
							record.getDoctorRecord().setLocation(newValue);
							return "edit succeed !";
						}
					}else if(recordID.contains("NR")){
						if(fieldName.equalsIgnoreCase("Designation")){
							record.getNurseRecord().setDesignation(newValue);
							return "edit succeed !";
						}else if(fieldName.equalsIgnoreCase("Status")){
							record.getNurseRecord().setStatus(newValue);
							return "edit succeed !";
						}else if (fieldName.equalsIgnoreCase("statusDate")){
							record.getNurseRecord().setStatusDate(newValue);
							return "edit succeed !";
						}
					}
				}
			}
		}
		
		return Integer.toString(mtl_hash.size());
	}
	
	@Override
	public String sayHello() throws RemoteException {
		// TODO Auto-generated method stub
		return "Hello";
	}
	
	public static void main(String[] args) {
//		if(System.getSecurityManager() == null){
//			System.setSecurityManager(new SecurityManager());
//		}
		try{
			String server_name = "Montreal";
			ClinicServers_Interface mtl_obj = new ClinicServer_MTL();
			ClinicServers_Interface stub = (ClinicServers_Interface) UnicastRemoteObject.exportObject(mtl_obj, 0);
			Registry registry = LocateRegistry.createRegistry(1099);
            registry.bind(server_name, stub);
            System.out.println("ClinicServer_MTL bound");
		}
		catch(Exception e){
			System.out.print("ClinicServer_MTL Exception!!");
			e.printStackTrace();
		}
	}

	public Map<Character, ArrayList<RecordInfo>> getMtl_hash() {
		return mtl_hash;
	}

	public void setMtl_hash(Map<Character, ArrayList<RecordInfo>> mtl_hash) {
		this.mtl_hash = mtl_hash;
	}

	public int getStartID() {
		return startID;
	}

	public void setStartID(int startID) {
		this.startID = startID;
	}

	

}
