package Record_Type;

/**
 * a class of the nurse record
 * @author peilin
 *
 */
public class NurseRecord {

	private String firstName;
	private String lastName;
	private String designation;
	private String status;
	private String statusDate;

	/**
	 * 	
	 * @param n_firstName
	 * @param n_lastName
	 * @param n_designation
	 * @param n_status
	 * @param n_statusDate
	 * Default Constructor of NurseRecord.
	 */
	public NurseRecord(String n_firstName, String n_lastName, String n_designation, String n_status, String n_statusDate) {
		this.firstName = n_firstName;
		this.lastName = n_lastName;
		this.designation = n_designation;
		this.status = n_status;
		this.statusDate = n_statusDate;	
	}
	
	/**
	 * this is a method of get first name
	 * @return
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * this is a method of set first name
	 * @param firstName
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * this is a method of get last name
	 * @return
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * this is a method of set last name
	 * @param lastName
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * this is a method of get designation
	 * @return
	 */
	public String getDesignation() {
		return designation;
	}

	/**
	 * this is a method of set designation
	 * @param designation
	 */
	public void setDesignation(String designation) {
		this.designation = designation;
	}
	/**
	 * this is method of get status
	 * @return
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * this is a method of set status
	 * @param status
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	
	/**
	 * this is a method of get status date
	 * @return
	 */
	public String getStatusDate() {
		return statusDate;
	}
	
	/**
	 * this is a method of set status date 
	 * @param statusDate
	 */
	public void setStatusDate(String statusDate) {
		this.statusDate = statusDate;
	}
	
	/**
	 * this is method of print information
	 */
	@Override
	public String toString() {
		String str = "First Name: "+ getFirstName() + "\n" 
				+ "Last Name: " + getLastName() + "\n"
				+ "Designation: " + getDesignation() + "\n"
				+ "Status:" + getStatus() + "\n"
				+ "StatusDate" + getStatusDate() + "\n";
		return str;
	}
	
}
