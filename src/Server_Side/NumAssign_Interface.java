package Server_Side;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * this is a interface 
 * @author peilin
 *
 */
public interface NumAssign_Interface extends Remote{
	public int getSartNumber() throws RemoteException;
}
