package ClinicServers.Laval_LVL;

import java.rmi.RemoteException;

import ClinicServers.ClinicServers_Interface;

public class ClinicServer_LVL implements ClinicServers_Interface {
	
	public ClinicServer_LVL() {
		// TODO Auto-generated constructor stub
		super();
	}

	@Override
	public String createDRecord(String recordsID, String firstName, String lastName, String address, String phone,
			String specialization, String location) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String createNRecord(String recordsID, String firstName, String lastName, String designation, String status,
			String statusDate) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getRecordCounts(String recordType) throws RemoteException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String editRecord(String recordID, String fielddName, String newValue) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}
	
	public static void main(String[] args) {
		
	}

}
