package RecordInfomation;

public class NurseRecord {
	private String recordsID;
	private String firstName;
	private String lastName;
	private String designation;
	private String status;
	private String statusDate;
	
	public NurseRecord(String n_recordsID, String n_firstName, String n_lastName, String n_designation, String n_status, String n_statusDate) {
		
		this.recordsID = n_recordsID;
		this.firstName = n_firstName;
		this.lastName = n_lastName;
		this.designation = n_designation;
		this.status = n_status;
		this.statusDate = n_statusDate;
	}

	public String getRecordsID() {
		return recordsID;
	}

	public void setRecordsID(String recordsID) {
		this.recordsID = recordsID;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getStatusDate() {
		return statusDate;
	}

	public void setStatusDate(String statusDate) {
		this.statusDate = statusDate;
	}
	
	
}
