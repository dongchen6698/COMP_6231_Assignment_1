package Server_Side.Server_LVL;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
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
 * This is Laval server of DSMS.
 * @author AlexChen
 *
 */
public class ClinicServer_LVL implements ClinicServers_Interface {
	
	/**
	 * this is a constructor of the class
	 */
	public ClinicServer_LVL() {
		super();
	}
	
	/**
	 * this method is create a doctor record
	 */
	@Override
	public String createDRecord(String firstName, String lastName, String address, String phone,
			String specialization, String location) throws RemoteException {
		
		String recordID = null;
		RecordInfo doc_recorde_with_recordID = null;
		
		Character capital_lastname = lastName.charAt(0);
		if(Config_LVL.HASH_TABLE.containsKey(capital_lastname)){
			Config_LVL.RECORD_LIST = Config_LVL.HASH_TABLE.get(capital_lastname);
		}else{
			Config_LVL.RECORD_LIST = new ArrayList<RecordInfo>();
		}
		DoctorRecord doc_recorde = new DoctorRecord(firstName, lastName, address, phone, specialization, location);
		recordID = "DR" + getNumAssignStub().getSartNumber();
		doc_recorde_with_recordID = new RecordInfo(recordID, doc_recorde);
		Config_LVL.RECORD_LIST.add(doc_recorde_with_recordID);
		synchronized (this) {
			Config_LVL.HASH_TABLE.put(capital_lastname, Config_LVL.RECORD_LIST);
		}
		Config_LVL.LOGGER.info("Manager: "+ Config_LVL.MANAGER_ID + " Creat Doctor Record: "+ "\n" +doc_recorde_with_recordID.toString());
		return "Doctor Record Buid Succeed !" + "\n" +doc_recorde_with_recordID.toString();
	}
	
	/**
	 * this method is create a nurse record
	 */
	@Override
	public String createNRecord(String firstName, String lastName, String designation, String status,
			String statusDate) throws RemoteException {
		
		String recordID = null;
		RecordInfo nur_recorde_with_recordID = null;
		 
		Character capital_lastname = lastName.charAt(0);
		if(Config_LVL.HASH_TABLE.containsKey(capital_lastname)){
			Config_LVL.RECORD_LIST = Config_LVL.HASH_TABLE.get(capital_lastname);
		}else{
			Config_LVL.RECORD_LIST = new ArrayList<RecordInfo>();
		}
		NurseRecord nur_recorde = new NurseRecord(firstName, lastName, designation, status, statusDate);
		recordID = "NR" + getNumAssignStub().getSartNumber();
		nur_recorde_with_recordID = new RecordInfo(recordID, nur_recorde);
		Config_LVL.RECORD_LIST.add(nur_recorde_with_recordID);
		synchronized (this) {
			Config_LVL.HASH_TABLE.put(capital_lastname, Config_LVL.RECORD_LIST);
		}
		Config_LVL.LOGGER.info("Manager: "+ Config_LVL.MANAGER_ID + " Creat Nurse Record: "+ "\n" +nur_recorde_with_recordID.toString());
		return "Nurse Record Buid Succeed !" + "\n" +nur_recorde_with_recordID.toString();
	}
	
	/**
	 * this method is getting a number of the record of the doctor or the nurse
	 * 	 
	 */
	@Override
	public String getRecordCounts(String recordType) throws RemoteException {
		String lvl_hash_size = getLocalHashSize(recordType);
		String ddo_hash_size = sendMessageToOtherServer(Config_LVL.SERVER_PORT_DDO, recordType);
		String mtl_hash_size = sendMessageToOtherServer(Config_LVL.SERVER_PORT_MTL, recordType);
		String result = mtl_hash_size + "\n" + lvl_hash_size + "\n" + ddo_hash_size + "\n";
		Config_LVL.LOGGER.info("Manager: "+ Config_LVL.MANAGER_ID + " search RecordCounts: "+ "\n" + result);
		return result;
	}
	
	/**
	 * this method is edit the record of a doctor or a nurse
	 */
	@Override
	public String editRecord(String recordID, String fieldName, String newValue) throws RemoteException {
		for(Map.Entry<Character, ArrayList<RecordInfo>> entry:Config_LVL.HASH_TABLE.entrySet()){
			for(RecordInfo record:entry.getValue()){
				if(recordID.equalsIgnoreCase(record.getRecordID())){
					if(fieldName.equalsIgnoreCase("Address")){
						record.getDoctorRecord().setAddress(newValue);
						Config_LVL.LOGGER.info("Manager: "+ Config_LVL.MANAGER_ID + " edit the Address of Doctor Record: "+ "\n" + record.toString());
						return "edit succeed !\n"+record.toString();
					}else if(fieldName.equalsIgnoreCase("Phone")){
						record.getDoctorRecord().setPhone(newValue);
						Config_LVL.LOGGER.info("Manager: "+ Config_LVL.MANAGER_ID + " edit the phone of Doctor Record: "+ "\n" + record.toString());
						return "edit succeed !\n"+record.toString();
					}else if (fieldName.equalsIgnoreCase("Location")){
						record.getDoctorRecord().setLocation(newValue);
						Config_LVL.LOGGER.info("Manager: "+ Config_LVL.MANAGER_ID + " edit the Location of Doctor Record: "+ "\n" + record.toString());
						return "edit succeed !\n"+record.toString();
					}
				}else if(recordID.contains("NR")||recordID.contains("nr")){
					if(fieldName.equalsIgnoreCase("Designation")){
						record.getNurseRecord().setDesignation(newValue);
						Config_LVL.LOGGER.info("Manager: "+ Config_LVL.MANAGER_ID + " edit the Designation of Nurse Record: "+ "\n" + record.toString());
						return "edit succeed !\n"+record.toString();
					}else if(fieldName.equalsIgnoreCase("Status")){
						record.getNurseRecord().setStatus(newValue);
						Config_LVL.LOGGER.info("Manager: "+ Config_LVL.MANAGER_ID + " edit the Status of Nurse Record: "+ "\n" + record.toString());
						return "edit succeed !\n"+record.toString();
					}else if (fieldName.equalsIgnoreCase("statusDate")){
						record.getNurseRecord().setStatusDate(newValue);
						Config_LVL.LOGGER.info("Manager: "+ Config_LVL.MANAGER_ID + " edit the Status date of Nurse Record: "+ "\n" + record.toString());
						return "edit succeed !\n"+record.toString();
					}
				}
			}
		}
		return "edit failed";
	}

	public static void main(String[] args) {
		initLogger(Config_LVL.SERVER_NAME);
		exportServerObject();
		openUDPListener();
	}
	
	/**
	 * Initial the Logger function.
	 * @param server_name
	 */
	public static void initLogger(String server_name){
		try {
			String dir = "Server_Side_Log/";
			Config_LVL.LOGGER = Logger.getLogger(ManagerClients.class.getName());
			Config_LVL.LOGGER.setUseParentHandlers(false);
			Config_LVL.FH = new FileHandler(dir+server_name+".log",true);
			Config_LVL.LOGGER.addHandler(Config_LVL.FH);
			SimpleFormatter formatter = new SimpleFormatter();
			Config_LVL.FH.setFormatter(formatter);
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * this method is check the manager ID
	 * @param managerID
	 * @return
	 */
	public static String checkManagerID(String managerID){
		for(String account: Config_LVL.MANAGER_ACCOUNT){
			if(account.equalsIgnoreCase(managerID)){
				Config_LVL.MANAGER_ID = managerID;
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
			String server_name = Config_LVL.SERVER_NAME;
			ClinicServers_Interface obj = new ClinicServer_LVL();
			ClinicServers_Interface stub = (ClinicServers_Interface) UnicastRemoteObject.exportObject(obj, 0);
			Registry registry = LocateRegistry.getRegistry(Config_LVL.REGISTRY_PORT);
	        registry.rebind(server_name, stub);
	        System.out.println("ClinicServer_LVL bound");
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
		String result = null;
		try{
			socket = new DatagramSocket(Config_LVL.LOCAL_LISTENING_PORT); 
			byte[] buffer = new byte[100]; 
			while(true){
				DatagramPacket request = new DatagramPacket(buffer, buffer.length); 
				socket.receive(request);
				String requestcode = new String(request.getData()).trim().substring(0, 3);
				switch (requestcode) {
				case "001":
					result = checkManagerID(new String(request.getData()).trim().substring(3));
					break;
				case "002":
					result = getLocalHashSize(new String(request.getData()).trim().substring(3));
					break;
				}
				DatagramPacket reply = new DatagramPacket(result.getBytes(),result.getBytes().length, request.getAddress(), request.getPort()); 
				socket.send(reply);
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
	 * Check local hash table size and return the value.
	 * @param recordType
	 * @return
	 */
	public static String getLocalHashSize(String recordType){
		int dr_num = 0;
		int nr_num = 0;
		
		for(Map.Entry<Character, ArrayList<RecordInfo>> entry:Config_LVL.HASH_TABLE.entrySet()){
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
			return "LVL "+"DR: "+dr_num;
		}else if(recordType.equalsIgnoreCase("nr")){
			return "LVL "+"NR: "+dr_num;
		}else{
			return "LVL "+"ALL: "+(dr_num+nr_num);
		}
	}

	/**
	 * This function is for request other 2 server for their count of specific record type.
	 * @param server_port
	 * @param recordType
	 * @return
	 * 
	 */
	public static String sendMessageToOtherServer(int server_port, String recordType){
		DatagramSocket socket = null;
		String hostname = "127.0.0.1";
		String requestcode = "002";
		int serverPort = server_port;
		
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
