package ManagerClients;

import java.net.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.io.*;
public class testServer{
	public static ArrayList<String> list = new ArrayList(Arrays.asList("mtl10000", "mtl10001", "mtl10002"));
	
	public static String checkManagerIDValid(String managerID){
		for(String account: list){
			if(managerID.equals(account)){
				return "yes";
			}
		}
		return "no";
	}
	public static void main(String args[]){ 
		DatagramSocket aSocket = null;
		try{
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
		catch (SocketException e){
			System.out.println("Socket: " + e.getMessage());
		}
		catch (IOException e) {
			System.out.println("IO: " + e.getMessage());
			} 
		finally{
			if(aSocket != null) aSocket.close();
			}
		}
	}
