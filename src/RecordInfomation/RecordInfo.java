package RecordInfomation;

public class RecordInfo {
	private String recordID;
	private DoctorRecord doctorRecord;
	private NurseRecord nurseRecord;
	
	public RecordInfo(String n_recordID, DoctorRecord n_doctorRecord) {
		// TODO Auto-generated constructor stub
		this.recordID = n_recordID;
		this.doctorRecord = n_doctorRecord;
	}
	
	public RecordInfo(String n_recordID, NurseRecord n_nurseRecord) {
		// TODO Auto-generated constructor stub
		this.recordID = n_recordID;
		this.nurseRecord = n_nurseRecord;
	}

	public String getRecordID() {
		return recordID;
	}

	public void setRecordID(String recordID) {
		this.recordID = recordID;
	}

	public DoctorRecord getDoctorRecord() {
		return doctorRecord;
	}

	public void setDoctorRecord(DoctorRecord doctorRecord) {
		this.doctorRecord = doctorRecord;
	}

	public NurseRecord getNurseRecord() {
		return nurseRecord;
	}

	public void setNurseRecord(NurseRecord nurseRecord) {
		this.nurseRecord = nurseRecord;
	}
	
	@Override
	public String toString() {
		String str = null;
		if(doctorRecord != null){
			str = "RecordID: " + getRecordID() + "\n" + doctorRecord.toString();
		}else if(nurseRecord != null){
			str = "RecordID: " + getRecordID() + "\n" + nurseRecord.toString();
		}
		return str;
	}
	
}
