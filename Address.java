package userInfo;

public class Address {

	private String licenseNumber;
	private String street;
	private String state;
	private String zip;
	
	/**
	 * Constructor
	 * @param licenseNumber
	 * @param street
	 * @param state
	 * @param zip
	 */
	public Address(String licenseNumber, String street, String state, String zip) {
		super();
		this.licenseNumber = licenseNumber;
		this.street = street;
		this.state = state;
		this.zip = zip;
	}

	/**
	 * Default Constructor for Address
	 */
	public Address() {
		this.licenseNumber = "xxxxxxxxxxxxxxx";
		this.street = "needs replaced";
		this.state = "XX";
		this.zip = "11111";
	}

	/**
	 * Get license number associated with an address
	 * @return
	 */
	public String getLicenseNumber() {
		return licenseNumber;
	}

	/**
	 * Sets license number to be associated with address
	 * @param licenseNumber
	 */
	public void setLicenseNumber(String licenseNumber) {
		this.licenseNumber = licenseNumber;
	}

	/**
	 * Gets street
	 * @return
	 */
	public String getStreet() {
		return street;
	}

	/**
	 * Sets street
	 * @param street
	 */
	public void setStreet(String street) {
		this.street = street;
	}

	/**
	 * Get State
	 * @return
	 */
	public String getState() {
		return state;
	}

	/**
	 * Set State
	 * @param state
	 */
	public void setState(String state) {
		this.state = state;
	}

	/**
	 * Get Zipcode
	 */
	public String getZip() {
		return zip;
	}

	/**
	 * Set Zipcode
	 * @param zip
	 */
	public void setZip(String zip) {
		this.zip = zip;
	}
	
} // End Address Class
