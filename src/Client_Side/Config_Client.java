package Client_Side;

import java.util.logging.FileHandler;
import java.util.logging.Logger;

import Server_Side.ClinicServers_Interface;

public class Config_Client {
	static String MANAGER_ID = null;
	static ClinicServers_Interface STUB;
	static String HOST = "127.0.0.1";
	static int REGISTRY_PORT = 1099;
	static int SERVER_PORT_MTL = 6001;
	static int SERVER_PORT_LVL = 6002;
	static int SERVER_PORT_DDO = 6003;
	
	static Logger LOGGER = null;
	static FileHandler FH = null;
}