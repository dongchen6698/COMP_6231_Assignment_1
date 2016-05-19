package ManagerClients;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Date;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import ClinicServers.ClinicServers_Interface;

public class ManagerClients {
	
	public ManagerClients() {
		// TODO Auto-generated constructor stub
	}
	
	public static void showMenu(String managerID)
	{
		System.out.println("****Welcome to DSMS****");
		System.out.println("****Manager: "+managerID +"****\n");
		System.out.println("Please select an option (1-4)");
		System.out.println("1. Create Doctor Record.");
		System.out.println("2. Create Nurse Record");
		System.out.println("3. Get Record Counts");
		System.out.println("4. Edit Record");
		System.out.println("5. Exit DSMS");
	}
	
	public static Boolean checkManagerID(String ID){
		String pattern = "^(MTL|LVL|DDO)(\\d{5})$";
		Pattern re = Pattern.compile(pattern,Pattern.CASE_INSENSITIVE);
		Matcher matcher = re.matcher(ID);
		if(matcher.find()){
			return true;
		}else{
			return false;
		}
	}
	
	public static void main(String[] args){
		try {
			String managerID = null;
			Registry registry = null;
			ClinicServers_Interface stub = null;
			Scanner keyboard = new Scanner(System.in);
			do{
				System.out.println("\n****Please input the manager ID****\n");
				managerID = keyboard.next();
			}while(!checkManagerID(managerID));
			
			if(managerID.substring(0, 3).equalsIgnoreCase("mtl")){
				registry = LocateRegistry.getRegistry("127.0.0.1", 1099);
				stub = (ClinicServers_Interface) registry.lookup("Montreal");
			}else if(managerID.substring(0, 3).equalsIgnoreCase("lvl")){
				
			}else if(managerID.substring(0, 3).equalsIgnoreCase("ddo")){
				
			}

			
			int userChoice=0;
			String requestInput= "Please enter a random string.";
			
			
			showMenu(managerID);
			
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
				
				// Manage user selection.
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
					
					String d_result = stub.createDRecord(d_firstname, d_lastname, d_address, d_phone, d_specialization, d_location);
					System.out.println(d_result);
					
					showMenu(managerID);
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
					
					String result = stub.createNRecord(n_firstname, n_lastname, n_designation, n_status, n_status_date);
					System.out.println(result);
					
					showMenu(managerID);
					break;
				case 3:
					
				case 4:
					System.out.println("Please input the RecordID");
					String recordID = keyboard.next();
					
					System.out.println("Please input the FieldName");
					String fieldname = keyboard.next();
					
					System.out.println("Please input the New Value");
					String newvalue = keyboard.next();
					
					String e_result = stub.editRecord(recordID, fieldname, newvalue);
					System.out.println(e_result);
					
					showMenu(managerID);
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
