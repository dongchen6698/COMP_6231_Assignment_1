package Server_Side.Server_LVL;

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
 *This is a config file of Server_Laval.
 */
public class Config_LVL {
//	static ArrayList<String> MANAGER_ACCOUNT = new ArrayList<String>(Arrays.asList("LVL10000", "LVL10001", "LVL10002"));
	static ArrayList<String> MANAGER_ACCOUNT = new ArrayList<String>(){
		{
			add("LVL10000");
			add("LVL10001");
			add("LVL10002");
		}
	};
	static Map<Character, ArrayList<RecordInfo>> HASH_TABLE = new HashMap<Character, ArrayList<RecordInfo>>(){
		{
//			put('L', new ArrayList<RecordInfo>(Arrays.asList(new RecordInfo("DR00005", new DoctorRecord("five", "Li", "Montreal", "5141234567", "Surgery", "mtl")))));
//			put('Z', new ArrayList<RecordInfo>(Arrays.asList(new RecordInfo("DR00006", new DoctorRecord("six", "Zhang", "Montreal", "5142345678", "Surgery", "mtl")))));
			put('W', new ArrayList<RecordInfo>(Arrays.asList(new RecordInfo("NR00007", new NurseRecord("seven", "Wang", "junior", "active", "2005/09/12")))));
			put('H', new ArrayList<RecordInfo>(Arrays.asList(new RecordInfo("NR00008", new NurseRecord("eight", "Hu", "junior", "active", "2016/09/12")))));
		}
	};
	static ArrayList<RecordInfo> RECORD_LIST = null;
	static int REGISTRY_PORT = 1099;
	static String SERVER_NAME = "server_lvl";
	static int LOCAL_LISTENING_PORT = 6002;
	
	static int SERVER_PORT_MTL = 6001;
	static int SERVER_PORT_LVL = 6002;
	static int SERVER_PORT_DDO = 6003;
	
	
}
