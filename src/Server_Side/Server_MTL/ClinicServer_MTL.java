package Server_Side.Server_MTL;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Map;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import Client_Side.ManagerClients;
import Record_Type.DoctorRecord;
import Record_Type.NurseRecord;
import Record_Type.RecordInfo;
import Server_Side.NumAssign_Interface;
import Server_Side.ClinicServers_Interface;

/**
 * This is Montreal server of DSMS.
 * @author AlexChen
 *
 */
public class ClinicServer_MTL implements ClinicServers_Interface {
	
	/**
	 * This is a constructor of the class
	 */
	public ClinicServer_MTL() {
		super();
	}
	
	/**
	 * this method is create a doctor record
	 */
	@Override
	public String createDRecord(String firstName, String lastName, String address, String phone,
			String specialization, String location) throws RemoteException {
		if(!checkLocation(location)){
			return "Location is not right. Please input (mtl,lvl or ddo).\n";
		}
		String recordID = null;
		RecordInfo doc_recorde_with_recordID = null;
		
		Character capital_lastname = lastName.charAt(0);
		if(Config_MTL.HASH_TABLE.containsKey(capital_lastname)){
			Config_MTL.RECORD_LIST = Config_MTL.HASH_TABLE.get(capital_lastname);
		}else{
			Config_MTL.RECORD_LIST = new ArrayList<RecordInfo>();
		}
		DoctorRecord doc_recorde = new DoctorRecord(firstName, lastName, address, phone, specialization, location);
		recordID = "DR" + getNumAssignStub().getSartNumber();
		doc_recorde_with_recordID = new RecordInfo(recordID, doc_recorde);
		
		synchronized (this) {
			Config_MTL.RECORD_LIST.add(doc_recorde_with_recordID);
			Config_MTL.HASH_TABLE.put(capital_lastname, Config_MTL.RECORD_LIST);
		}
		Config_MTL.LOGGER.info("Manager: "+ Config_MTL.MANAGER_ID + " Creat Doctor Record: "+ "\n" +doc_recorde_with_recordID.toString());
		return "Doctor Record Buid Succeed !" + "\n" +doc_recorde_with_recordID.toString();
	}
	
	/**
	 * this method is create a nurse record
	 */
	@Override
	public String createNRecord(String firstName, String lastName, String designation, String status,
			String statusDate) throws RemoteException {
		if(!checkDesignation(designation)){
			return "Designation is not right. Please input (junior or senior).\n";
		}
		if(!checkStatus(status)){
			return "Status is not right. Please input (active or terminated).\n";
		}
		if(!checkStatusDate(statusDate)){
			return "Status Date is not right. Please input the right format of date (yyyy/mm/dd).";
		}
		String recordID = null;
		RecordInfo nur_recorde_with_recordID = null;
		 
		Character capital_lastname = lastName.charAt(0);
		if(Config_MTL.HASH_TABLE.containsKey(capital_lastname)){
			Config_MTL.RECORD_LIST = Config_MTL.HASH_TABLE.get(capital_lastname);
		}else{
			Config_MTL.RECORD_LIST = new ArrayList<RecordInfo>();
		}
		NurseRecord nur_recorde = new NurseRecord(firstName, lastName, designation, status, statusDate);
		recordID = "NR" + getNumAssignStub().getSartNumber();
		nur_recorde_with_recordID = new RecordInfo(recordID, nur_recorde);
		
		synchronized (this) {
			Config_MTL.RECORD_LIST.add(nur_recorde_with_recordID);
			Config_MTL.HASH_TABLE.put(capital_lastname, Config_MTL.RECORD_LIST);
		}
		Config_MTL.LOGGER.info("Manager: "+ Config_MTL.MANAGER_ID + " Creat Nurse Record: "+ "\n" +nur_recorde_with_recordID.toString());
		return "Nurse Record Buid Succeed !" + "\n" +nur_recorde_with_recordID.toString();
	}
	
	/**
	 * this method is get the counts of the doctor or nurse record
	 */
	@Override
	public String getRecordCounts(String recordType) throws RemoteException {
		String lvl_hash_size = sendMessageToOtherServer(Config_MTL.SERVER_PORT_LVL, recordType);
		String ddo_hash_size = sendMessageToOtherServer(Config_MTL.SERVER_PORT_DDO, recordType);
		String mtl_hash_size = getLocalHashSize(recordType);
		String result = mtl_hash_size + "\n" + lvl_hash_size + "\n" + ddo_hash_size + "\n";
		Config_MTL.LOGGER.info("Manager: "+ Config_MTL.MANAGER_ID + " search RecordCounts: "+ "\n" + result);
		return result;
	}
	
	/**
	 * this method is edit the record of a doctor or a nurse 
	 */
	@Override
	public String editRecord(String recordID, String fieldName, String newValue) throws RemoteException {
		for(Map.Entry<Character, ArrayList<RecordInfo>> entry:Config_MTL.HASH_TABLE.entrySet()){
			for(RecordInfo record:entry.getValue()){
				if(recordID.equalsIgnoreCase(record.getRecordID())){
					if(recordID.contains("DR")||recordID.contains("dr")){
						if(fieldName.equalsIgnoreCase("Address")){
							record.getDoctorRecord().setAddress(newValue);
							Config_MTL.LOGGER.info("Manager: "+ Config_MTL.MANAGER_ID + " edit the Address of Doctor Record: "+ "\n" + record.toString());
							return "edit succeed !\n"+record.toString();
						}else if(fieldName.equalsIgnoreCase("Phone")){
							record.getDoctorRecord().setPhone(newValue);
							Config_MTL.LOGGER.info("Manager: "+ Config_MTL.MANAGER_ID + " edit the phone of Doctor Record: "+ "\n" + record.toString());
							return "edit succeed !\n"+record.toString();
						}else if (fieldName.equalsIgnoreCase("Location")){
							if(!checkLocation(newValue)){
								return "Location is not right. Please input (mtl,lvl or ddo).\n";
							}
							record.getDoctorRecord().setLocation(newValue);
							Config_MTL.LOGGER.info("Manager: "+ Config_MTL.MANAGER_ID + " edit the Location of Doctor Record: "+ "\n" + record.toString());
							return "edit succeed !\n"+record.toString();
						}
					}else if(recordID.contains("NR")||recordID.contains("nr")){
						if(fieldName.equalsIgnoreCase("Designation")){
							if(!checkDesignation(newValue)){
								return "Designation is not right. Please input (junior or senior).\n";
							}
							record.getNurseRecord().setDesignation(newValue);
							Config_MTL.LOGGER.info("Manager: "+ Config_MTL.MANAGER_ID + " edit the Designation of Nurse Record: "+ "\n" + record.toString());
							return "edit succeed !\n"+record.toString();
						}else if(fieldName.equalsIgnoreCase("Status")){
							if(!checkStatus(newValue)){
								return "Status is not right. Please input (active or terminated).\n";
							}
							record.getNurseRecord().setStatus(newValue);
							Config_MTL.LOGGER.info("Manager: "+ Config_MTL.MANAGER_ID + " edit the Status of Nurse Record: "+ "\n" + record.toString());
							return "edit succeed !\n"+record.toString();
						}else if (fieldName.equalsIgnoreCase("statusDate")){
							record.getNurseRecord().setStatusDate(newValue);
							Config_MTL.LOGGER.info("Manager: "+ Config_MTL.MANAGER_ID + " edit the Status date of Nurse Record: "+ "\n" + record.toString());
							return "edit succeed !\n"+record.toString();
						}
						
					}
				}
			}
		}
		return "edit failed";
	}

	public static void main(String[] args) {
		initLogger(Config_MTL.SERVER_NAME);
		exportServerObject();
		openUDPListener();
	}
	
	/**
	 * Local check Location is right or not.
	 * @param location
	 * @return
	 */
	public static Boolean checkLocation(String location){
		for(Config_MTL.D_LOCATION d_location: Config_MTL.D_LOCATION.values()){
			if(location.equals(d_location.toString())){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Local check Designation is right or not.
	 * @param designation
	 * @return
	 */
	public static Boolean checkDesignation(String designation){
		for(Config_MTL.N_DESIGNATION n_designation: Config_MTL.N_DESIGNATION.values()){
			if(designation.equals(n_designation.toString())){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Local check Status is right or not.
	 * @param status
	 * @return
	 */
	public static Boolean checkStatus(String status){
		for(Config_MTL.N_STATUS n_status: Config_MTL.N_STATUS.values()){
			if(status.equals(n_status.toString())){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Local check Status Date is right or not.
	 * @param date
	 * @return
	 */
	public static Boolean checkStatusDate(String date){
		SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
		try {
			format.setLenient(false);
			format.parse(date);
		} catch (ParseException e) {
			return false;
		}
		return true;	
	}
	
	/**
	 * Initial the Logger function.
	 * @param server_name
	 */
	public static void initLogger(String server_name){
		try {
			String dir = "Server_Side_Log/";
			Config_MTL.LOGGER = Logger.getLogger(ManagerClients.class.getName());
			Config_MTL.LOGGER.setUseParentHandlers(false);
			Config_MTL.FH = new FileHandler(dir+server_name+".log",true);
			Config_MTL.LOGGER.addHandler(Config_MTL.FH);
			SimpleFormatter formatter = new SimpleFormatter();
			Config_MTL.FH.setFormatter(formatter);
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
		
	/**
	 * Check ManagerID and return valid or invalid.
	 * @param managerID
	 * @return
	 */
	public static String checkManagerID(String managerID){
		for(String account: Config_MTL.MANAGER_ACCOUNT){
			if(account.equalsIgnoreCase(managerID)){
				Config_MTL.MANAGER_ID = managerID;
				return "valid";
			}
		}
		return "invalid";
	}
	
	/**
	 * Get the stub of Number Assign server
	 * @return
	 */
	public static NumAssign_Interface getNumAssignStub(){
		try {
			Registry registry = LocateRegistry.getRegistry();
			NumAssign_Interface stub = (NumAssign_Interface)registry.lookup("NumberAssign");
			return stub;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;	
	}
	
	/**
	 * Export server object.
	 */
	public static void exportServerObject(){
		try {
			String server_name = Config_MTL.SERVER_NAME;
			ClinicServers_Interface obj = new ClinicServer_MTL();
			ClinicServers_Interface stub = (ClinicServers_Interface) UnicastRemoteObject.exportObject(obj, 0);
			Registry registry = LocateRegistry.getRegistry();
	        registry.rebind(server_name, stub);
	        System.out.println("ClinicServer_MTL bound");
		} catch (Exception e) {
			e.printStackTrace();
		}	
	}
	
	/**
	 * Open UDP listening port to check ManagerID and receive the other server request to get local hash table size.
	 * Request 001 for check ManagerID
	 * Request 002 for get local hash table size
	 */
	public static void openUDPListener(){
		DatagramSocket socket = null;
		try{
			socket = new DatagramSocket(Config_MTL.LOCAL_LISTENING_PORT);
			while(true){
				byte[] buffer = new byte[100]; 
				DatagramPacket request = new DatagramPacket(buffer, buffer.length); 
				socket.receive(request);
				Config_MTL.LOGGER.info("Get request: " + (new String(request.getData()).trim())+ "\n" + "Start a new thread to handle this.");
				new Connection(socket, request);
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally{
			if(socket != null) socket.close();
		}
	}
	
	/**
	 * New thread to handle the newly request
	 * @author AlexChen
	 *
	 */
	static class Connection extends Thread{
		DatagramSocket socket = null;
		DatagramPacket request = null;
		String result = null;
		public Connection(DatagramSocket n_socket, DatagramPacket n_request) {
			this.socket = n_socket;
			this.request = n_request;
			String requestcode = new String(request.getData()).trim().substring(0, 3);
			switch (requestcode) {
			case "001":
				Config_MTL.LOGGER.info("Request code: " + requestcode + ", " + "Check ManagerID: " + (new String(request.getData()).trim().substring(3)+ " valid or not."));
				result = checkManagerID(new String(request.getData()).trim().substring(3));
				break;
			case "002":
				Config_MTL.LOGGER.info("Request code: " + requestcode + ", " + "Search HashMap, SearchType: " + (new String(request.getData()).trim().substring(3)));
				result = getLocalHashSize(new String(request.getData()).trim().substring(3));
				break;
			}
			this.start();
		}
		
		@Override
		public void run() {
			try {
				DatagramPacket reply = new DatagramPacket(result.getBytes(),result.getBytes().length, request.getAddress(), request.getPort()); 
				socket.send(reply);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Check local hash table size and return the value.
	 * @param recordType
	 * @return
	 */
	public static String getLocalHashSize(String recordType){
		int dr_num = 0;
		int nr_num = 0;
		
		for(Map.Entry<Character, ArrayList<RecordInfo>> entry:Config_MTL.HASH_TABLE.entrySet()){
			for(RecordInfo record:entry.getValue()){
				switch(record.getRecordID().substring(0, 2)){
				case "DR":
					dr_num++;
					break;
				case "NR":
					nr_num++;
					break;
				}
			}
		}
		if(recordType.equalsIgnoreCase("dr")){
			return "MTL "+"DR: "+dr_num;
		}else if(recordType.equalsIgnoreCase("nr")){
			return "MTL "+"NR: "+nr_num;
		}else{
			return "MTL "+"ALL: "+(dr_num+nr_num);
		}
	}

	/**
	 * This function is for request other 2 server for their count of specific record type.
	 * @param server_port
	 * @param recordType
	 * @return
	 * 
	 */
	public static String sendMessageToOtherServer(int serverPort, String recordType){
		DatagramSocket socket = null;
		String hostname = "127.0.0.1";
		String requestcode = "002";
		
	    try {
	    	socket = new DatagramSocket();
	    	byte[] message = (new String(requestcode+recordType)).getBytes();
	    	InetAddress Host = InetAddress.getByName(hostname);
	    	DatagramPacket request = new DatagramPacket(message, message.length, Host, serverPort);
	    	socket.send(request);
	    	byte[] buffer = new byte[100];
	    	DatagramPacket reply = new DatagramPacket(buffer, buffer.length); 
	    	socket.receive(reply);
	    	String result = new String(reply.getData()).trim();
	    	return result;
	    }
	    catch(Exception e){
	    	e.printStackTrace();
	    	}
		finally{
			if(socket != null){
				socket.close();
				}
			}
		return null; 
	}
}
