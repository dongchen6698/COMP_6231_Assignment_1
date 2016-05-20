package CommonServer;

import java.rmi.Remote;
import java.rmi.RemoteException;
/**
 * 
 * @author peilin
 *
 */
public interface CommonNum_Interface extends Remote{
	
	public int getSartNumber() throws RemoteException;
	
}
