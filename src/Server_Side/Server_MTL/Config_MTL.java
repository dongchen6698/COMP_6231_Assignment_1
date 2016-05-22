package Server_Side.Server_MTL;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import Record_Type.DoctorRecord;
import Record_Type.NurseRecord;
import Record_Type.RecordInfo;

/**
 * 
 * @author AlexChen
 * This is a config file of Server_Montreal.
 */
public class Config_MTL {
	//static ArrayList<String> MANAGER_ACCOUNT = new ArrayList<String>(Arrays.asList("MTL10000", "MTL10001", "MTL10002"));
	static ArrayList<String> MANAGER_ACCOUNT = new ArrayList<String>(){
		{
			add("MTL10000");
			add("MTL10001");
			add("MTL10002");
		}
	};
	static Map<Character, ArrayList<RecordInfo>> HASH_TABLE = new HashMap<Character, ArrayList<RecordInfo>>(){
		{
			put('L', new ArrayList<RecordInfo>(Arrays.asList(new RecordInfo("DR00001", new DoctorRecord("one", "Li", "Montreal", "5141234567", "Surgery", "mtl")))));
			put('Z', new ArrayList<RecordInfo>(Arrays.asList(new RecordInfo("DR00002", new DoctorRecord("two", "Zhang", "Montreal", "5142345678", "Surgery", "mtl")))));
//			put('W', new ArrayList<RecordInfo>(Arrays.asList(new RecordInfo("NR00003", new NurseRecord("three", "Wang", "junior", "active", "2005/09/12")))));
//			put('H', new ArrayList<RecordInfo>(Arrays.asList(new RecordInfo("NR00004", new NurseRecord("four", "Hu", "junior", "active", "2016/09/12")))));
		}
	};
	static ArrayList<RecordInfo> RECORD_LIST = null;
	static int REGISTRY_PORT = 1099;
	static String SERVER_NAME = "server_mtl";
	static int LOCAL_LISTENING_PORT = 6001;
	
	static int SERVER_PORT_MTL = 6001;
	static int SERVER_PORT_LVL = 6002;
	static int SERVER_PORT_DDO = 6003;
	
	
}
