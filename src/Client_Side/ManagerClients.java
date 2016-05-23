package Client_Side;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import Server_Side.ClinicServers_Interface;

/**
 * This is a Client side of DSMS.
 * @author AlexChen
 *
 */
public class ManagerClients {
	
	public ManagerClients() {
		super();
	}
	
	/**
	 * This is a local function for check manager format use Regular expression.
	 * @param n_managerID
	 * @return
	 */
	public static Boolean checkManagerIDFormat(String n_managerID){
		String pattern = "^(MTL|LVL|DDO)(\\d{5})$";
		Pattern re = Pattern.compile(pattern,Pattern.CASE_INSENSITIVE);
		Matcher matcher = re.matcher(n_managerID);
		if(matcher.find()){
			return true;
		}else{
			System.err.println("Usage:[MTL,LVL,DDO]+[10000]\n");
			return false;
		}
	}
	
	/**
	 * This is a loop for require user to input the managerID, like Login.
	 */
	public static void checkManagerLogIn(){
		Boolean valid = false;
		while(!valid){
			Scanner keyboard = new Scanner(System.in);
			do{
				System.out.println("****Please input the manager ID****");
				Config_Client.MANAGER_ID = keyboard.next();
			}while(!checkManagerIDFormat(Config_Client.MANAGER_ID));
			valid = true;
		}
	}
	
	/**
	 * This function is for check the prefix of managerID and send this managerID to specific server to check valid or not.
	 * @param n_managerID
	 * @return
	 */
	public static Boolean checkServerInfo(String n_managerID){
		DatagramSocket socket = null;
		String hostname = Config_Client.HOST;
		String requestcode = "001";
		int serverPort = 0;
		
		if(Config_Client.MANAGER_ID.substring(0, 3).equalsIgnoreCase("mtl")){
			serverPort = Config_Client.SERVER_PORT_MTL;
		}else if(Config_Client.MANAGER_ID.substring(0, 3).equalsIgnoreCase("lvl")){
			serverPort = Config_Client.SERVER_PORT_LVL;
		}else if(Config_Client.MANAGER_ID.substring(0, 3).equalsIgnoreCase("ddo")){
			serverPort = Config_Client.SERVER_PORT_DDO;
		}
		
	    try {
	    	socket = new DatagramSocket();
	    	
	    	byte[] message = (new String(requestcode+n_managerID)).getBytes();
	    	InetAddress Host = InetAddress.getByName(hostname);
	    	DatagramPacket request = new DatagramPacket(message, message.length, Host, serverPort);
	    	socket.send(request);
	    	byte[] buffer = new byte[100];
	    	DatagramPacket reply = new DatagramPacket(buffer, buffer.length); 
	    	socket.receive(reply);
	    	String result = new String(reply.getData()).trim();
	    	if(result.equals("valid")){
	    		System.out.println("Valid Account");
	    		return true;
	    	}else{
	    		System.out.println("Invalid Account");
	    		return false;
	    	}
	    }
	    catch(Exception e){
	    	System.out.println("Socket: " + e.getMessage()); 
	    	}
		finally{
			if(socket != null){
				socket.close();
				}
			}
		return null; 
	}
	
	/**
	 * If managerID is valid, this function is for get the stub of that server.
	 * @param managerID
	 * @return
	 * @throws Exception
	 */
	public static ClinicServers_Interface getServerReferrence(String managerID){
		try {
			if(managerID.substring(0, 3).equalsIgnoreCase("mtl")){
				Registry registry = LocateRegistry.getRegistry(Config_Client.HOST, Config_Client.REGISTRY_PORT);
				return (ClinicServers_Interface) registry.lookup("server_mtl");
			}else if(managerID.substring(0, 3).equalsIgnoreCase("lvl")){
				Registry registry = LocateRegistry.getRegistry(Config_Client.HOST, Config_Client.REGISTRY_PORT);
				return (ClinicServers_Interface) registry.lookup("server_lvl");
			}else if(managerID.substring(0, 3).equalsIgnoreCase("ddo")){
				Registry registry = LocateRegistry.getRegistry(Config_Client.HOST, Config_Client.REGISTRY_PORT);
				return (ClinicServers_Interface) registry.lookup("server_ddo");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}	
		return null;
	}
	
	/**
	 * Define the Menu list.
	 * @param managerID
	 */
	public static void showMenu(String managerID) {
		System.out.println("****Welcome to DSMS****");
		System.out.println("****Manager: "+managerID +"****\n");
		System.out.println("Please select an option (1-4)");
		System.out.println("1. Create Doctor Record.");
		System.out.println("2. Create Nurse Record");
		System.out.println("3. Get Record Counts");
		System.out.println("4. Edit Record");
		System.out.println("5. Exit DSMS");
	}
	
	public static void main(String[] args) {
		checkManagerLogIn();
		while(!checkServerInfo(Config_Client.MANAGER_ID)){
			System.err.println("ManagerID is not right !\n");
			checkManagerLogIn();
		};
		
		try {
			Config_Client.STUB = getServerReferrence(Config_Client.MANAGER_ID);
			int userChoice=0;
			Scanner keyboard = new Scanner(System.in);
			showMenu(Config_Client.MANAGER_ID);
			
			while(true)
			{
				Boolean valid = false;
				while(!valid)
				{
					try{
						userChoice=keyboard.nextInt();
						valid=true;
					}
					catch(Exception e)
					{
						System.out.println("Invalid Input, please enter an Integer");
						valid=false;
						keyboard.nextLine();
					}
				}
				
				switch(userChoice)
				{
				case 1: 
					System.out.println("Please input the FirstName");
					String d_firstname = keyboard.next();
					System.out.println("Please input the LastName");
					String d_lastname = keyboard.next();
					System.out.println("Please input the Address");
					String d_address = keyboard.next();
					System.out.println("Please input the Phone");
					String d_phone = keyboard.next();
					System.out.println("Please input the Specialization");
					String d_specialization = keyboard.next();
					System.out.println("Please input the Location(mtl,lvl,ddo)");
					String d_location =keyboard.next();
					String d_result = Config_Client.STUB.createDRecord(d_firstname, d_lastname, d_address, d_phone, d_specialization, d_location);
					System.out.println(d_result);
					showMenu(Config_Client.MANAGER_ID);
					break;
				case 2:
					System.out.println("Please input the FirstName");
					String n_firstname = keyboard.next();
					System.out.println("Please input the LastName");
					String n_lastname = keyboard.next();
					System.out.println("Please input the Designation(junior/senior)");
					String n_designation = keyboard.next();
					System.out.println("Please input the Status(active/terminated)");
					String n_status = keyboard.next();
					System.out.println("Please input the Status Date(yyyy/mm/dd/)");
					String n_status_date = keyboard.next();
					String result = Config_Client.STUB.createNRecord(n_firstname, n_lastname, n_designation, n_status, n_status_date);
					System.out.println(result);
					showMenu(Config_Client.MANAGER_ID);
					break;
				case 3:
					System.out.println("Please input search type");
					String searchtype = keyboard.next();
					String s_result = Config_Client.STUB.getRecordCounts(searchtype);
					System.out.println(s_result);
					showMenu(Config_Client.MANAGER_ID);
					break;
				case 4:
					System.out.println("Please input the RecordID");
					String recordID = keyboard.next();
					System.out.println("Please input the FieldName");
					String fieldname = keyboard.next();
					System.out.println("Please input the New Value");
					String newvalue = keyboard.next();
					String e_result = Config_Client.STUB.editRecord(recordID, fieldname, newvalue);
					System.out.println(e_result);
					showMenu(Config_Client.MANAGER_ID);
					break;
				case 5:
					System.out.println("Have a nice day!");
					keyboard.close();
					System.exit(0);
				default:
					System.out.println("Invalid Input, please try again.");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}				
	}
}
