package RecordInfomation;

public class DoctorRecord {

	private String firstName;
	private String lastName;
	private String address;
	private String phone;
	private String specialization;
	private String location;
	
	public DoctorRecord(String n_firstName, String n_lastName, String n_address, String n_phone, String n_specialization, String n_location){
		this.firstName = n_firstName;
		this.lastName = n_lastName;
		this.address = n_address;
		this.phone = n_phone;
		this.specialization = n_specialization;
		this.location = n_location;
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

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getSpecialization() {
		return specialization;
	}

	public void setSpecialization(String specialization) {
		this.specialization = specialization;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}
	
	@Override
	public String toString() {
		String str = "First Name: "+ getFirstName() + "\n" 
					+ "Last Name: " + getLastName() + "\n"
					+ "Address: " + getAddress() + "\n"
					+ "Phone:" + getPhone() + "\n"
					+ "specialization: " + getSpecialization() + "\n"
					+ "location: " + getLocation() + "\n";
		return str;
	}
	
	
}
