package Server_Side.Server_MTL;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Map;

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
	
	public ClinicServer_MTL() {
		super();
	}
	
	@Override
	public String createDRecord(String firstName, String lastName, String address, String phone,
			String specialization, String location) throws RemoteException {
		
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
		Config_MTL.RECORD_LIST.add(doc_recorde_with_recordID);
		synchronized (this) {
			Config_MTL.HASH_TABLE.put(capital_lastname, Config_MTL.RECORD_LIST);
		}
		return "Doctor Record Buid Succeed !" + "\n" +doc_recorde_with_recordID.toString();
	}

	@Override
	public String createNRecord(String firstName, String lastName, String designation, String status,
			String statusDate) throws RemoteException {
		
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
		Config_MTL.RECORD_LIST.add(nur_recorde_with_recordID);
		synchronized (this) {
			Config_MTL.HASH_TABLE.put(capital_lastname, Config_MTL.RECORD_LIST);
		}
		return "Nurse Record Buid Succeed !" + "\n" +nur_recorde_with_recordID.toString();
	}

	@Override
	public String getRecordCounts(String recordType) throws RemoteException {
		String lvl_hash_size = sendMessageToOtherServer(Config_MTL.SERVER_PORT_LVL, recordType);
		String ddo_hash_size = sendMessageToOtherServer(Config_MTL.SERVER_PORT_DDO, recordType);
		String mtl_hash_size = getLocalHashSize(recordType);
		return mtl_hash_size + "\n" + lvl_hash_size + "\n" + ddo_hash_size + "\n";
	}

	@Override
	public String editRecord(String recordID, String fieldName, String newValue) throws RemoteException {
		for(Map.Entry<Character, ArrayList<RecordInfo>> entry:Config_MTL.HASH_TABLE.entrySet()){
			for(RecordInfo record:entry.getValue()){
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

	public static void main(String[] args) {
//		if(System.getSecurityManager() == null){
//			System.setSecurityManager(new SecurityManager());
//		}
		exportServerObject();
		openUDPListener();
	}
		
	public static String checkManagerID(String managerID){
		for(String account: Config_MTL.MANAGER_ACCOUNT){
			if(account.equalsIgnoreCase(managerID)){
				return "valid";
			}
		}
		return "invalid";
	}
	
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
		
	public static void exportServerObject(){
		try {
			String server_name = Config_MTL.SERVER_NAME;
			ClinicServers_Interface obj = new ClinicServer_MTL();
			ClinicServers_Interface stub = (ClinicServers_Interface) UnicastRemoteObject.exportObject(obj, 0);
			Registry registry = LocateRegistry.getRegistry(Config_MTL.REGISTRY_PORT);
	        registry.rebind(server_name, stub);
	        System.out.println("ClinicServer_MTL bound");
		} catch (Exception e) {
			e.printStackTrace();
		}	
	}
	
	public static void openUDPListener(){
		DatagramSocket socket = null;
		String result = null;
		try{
			socket = new DatagramSocket(Config_MTL.LOCAL_LISTENING_PORT); 
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
			return "MTL "+"NR: "+dr_num;
		}else{
			return "MTL "+"ALL: "+(dr_num+nr_num);
		}
	}

	/**
	 * 
	 * @param server_port
	 * @param recordType
	 * @return
	 * This function is for request other 2 server for their count of specific record type.
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
