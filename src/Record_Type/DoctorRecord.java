package Record_Type;

/**
 * a class of the doctor record
 * @author peilin
 *
 */
public class DoctorRecord {

	private String firstName;
	private String lastName;
	private String address;
	private String phone;
	private String specialization;
	private String location;
	
	/**
	 * 
	 * @param n_firstName
	 * @param n_lastName
	 * @param n_address
	 * @param n_phone
	 * @param n_specialization
	 * @param n_location
	 * Default Constructor of DoctorRecord.
	 */
	public DoctorRecord(String n_firstName, String n_lastName, String n_address, String n_phone, String n_specialization, String n_location){
		this.firstName = n_firstName;
		this.lastName = n_lastName;
		this.address = n_address;
		this.phone = n_phone;
		this.specialization = n_specialization;
		this.location = n_location;
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
	 * this is a method of get address
	 * @return
	 */
	public String getAddress() {
		return address;
	}
	
	/**
	 * this is a method of set address
	 * @param address
	 */
	public void setAddress(String address) {
		this.address = address;
	}
	
	/**
	 * this is a method of get phone number
	 * @return
	 */
	public String getPhone() {
		return phone;
	}
	
	/**
	 * this is a method of set phone number
	 * @param phone
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	/**
	 * this is a method of get specialization
	 * @return
	 */
	public String getSpecialization() {
		return specialization;
	}
	
	/**
	 * this is a method of set specialization
	 * @param specialization
	 */
	public void setSpecialization(String specialization) {
		this.specialization = specialization;
	}
	
	/**
	 * this is a method of get the location
	 * @return
	 */
	public String getLocation() {
		return location;
	}
	
	/**
	 * this is a method of set the location
	 * @param location
	 */
	public void setLocation(String location) {
		this.location = location;
	}
	
	/**
	 * this is a method of print the information
	 */
	@Override
	public String toString() {
		String str = "First Name: "+ getFirstName() + "\n" 
					+ "Last Name: " + getLastName() + "\n"
					+ "Address: " + getAddress() + "\n"
					+ "Phone: " + getPhone() + "\n"
					+ "specialization: " + getSpecialization() + "\n"
					+ "location: " + getLocation() + "\n";
		return str;
	}
	
	
}
