package Server_Side.Server_DDO;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.FileHandler;
import java.util.logging.Logger;

import Record_Type.DoctorRecord;
import Record_Type.NurseRecord;
import Record_Type.RecordInfo;

/**
 * 
 * @author AlexChen
 *This is a config file of Server_DDO.
 */
public class Config_DDO {
	static enum D_LOCATION{
		mtl,lvl,ddo
	}
	
	static enum N_DESIGNATION{
		junior,senior
	}
	
	static enum N_STATUS{
		active,terminated
	}
//	static ArrayList<String> MANAGER_ACCOUNT = new ArrayList<String>(Arrays.asList("DDO10000", "DDO10001", "DDO10002"));
	static ArrayList<String> MANAGER_ACCOUNT = new ArrayList<String>(){
		{
			add("DDO10000");
			add("DDO10001");
			add("DDO10002");
		}
	};
	static Map<Character, ArrayList<RecordInfo>> HASH_TABLE = new HashMap<Character, ArrayList<RecordInfo>>(){
		{
//			put('L', new ArrayList<RecordInfo>(Arrays.asList(new RecordInfo("DR00009", new DoctorRecord("nine", "Li", "Montreal", "5141234567", "Surgery", "mtl")))));
			put('Z', new ArrayList<RecordInfo>(Arrays.asList(new RecordInfo("DR00010", new DoctorRecord("ten", "Zhang", "Montreal", "5142345678", "Surgery", "mtl")))));
//			put('W', new ArrayList<RecordInfo>(Arrays.asList(new RecordInfo("NR00011", new NurseRecord("eleven", "Wang", "junior", "active", "2005/09/12")))));
			put('H', new ArrayList<RecordInfo>(Arrays.asList(new RecordInfo("NR00012", new NurseRecord("twelve", "Hu", "junior", "active", "2016/09/12")))));
		}
	};
	static String MANAGER_ID = null;
	static ArrayList<RecordInfo> RECORD_LIST = null;
	static int REGISTRY_PORT = 1099;
	static String SERVER_NAME = "server_ddo";
	static int LOCAL_LISTENING_PORT = 6003;
	static Logger LOGGER = null;
	static FileHandler FH = null;
	
	static int SERVER_PORT_MTL = 6001;
	static int SERVER_PORT_LVL = 6002;
	static int SERVER_PORT_DDO = 6003;
	
	
}
