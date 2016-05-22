package Record_Type;

public class RecordInfo {
	private String recordID;
	private DoctorRecord doctorRecord;
	private NurseRecord nurseRecord;

	/**
	 * 
	 * @param n_recordID
	 * @param n_doctorRecord
	 * This Constructor is for create doctor record with recordID.
	 */
	public RecordInfo(String n_recordID, DoctorRecord n_doctorRecord) {
		this.recordID = n_recordID;
		this.doctorRecord = n_doctorRecord;
	}
	/**
	 * 	
	 * @param n_recordID
	 * @param n_nurseRecord
	 * This Constructor is for create nurse record with recordID.
	 */
	public RecordInfo(String n_recordID, NurseRecord n_nurseRecord) {
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
		switch (recordID.substring(0, 2)) {
		case "DR":
			str = "RecordID: " + getRecordID() + "\n" + doctorRecord.toString();
			return str;
		case "NR":
			str = "RecordID: " + getRecordID() + "\n" + nurseRecord.toString();
			return str;
		default:
			return null;
		}
	}
}
