package Server_Side;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Date;

public interface ClinicServers_Interface extends Remote{
	public String createDRecord(String firstName, String lastName, String address, String phone, String specialization, String location) throws RemoteException;
	public String createNRecord(String firstName, String lastName, String designation, String status, String statusDate) throws RemoteException;
	public String getRecordCounts(String recordType) throws RemoteException;
	public String editRecord(String recordID, String fielddName, String newValue) throws RemoteException;
}
