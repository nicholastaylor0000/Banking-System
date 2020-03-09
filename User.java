package userInfo;

import java.time.LocalDate;
import java.time.Period;

public class User {

	private String firstName;
	private String middleName;
	private String lastName;
	private LocalDate birthDate;
	private String licenseNumber;
	private String occupation;
	private Address address;
	
	/**
	 * Constructor
	 * @param firstName
	 * @param middleName
	 * @param lastName
	 * @param birthDate
	 * @param licenseNumber
	 * @param occupation
	 * @param address
	 */
	public User(String firstName, String middleName, String lastName, LocalDate birthDate, String licenseNumber,
			String occupation, Address address) {
		super();
		this.firstName = firstName;
		this.middleName = middleName;
		this.lastName = lastName;
		this.birthDate = birthDate;
		this.licenseNumber = licenseNumber;
		this.occupation = occupation;
		this.address = address;
	}

	/**
	 * Default Constructor
	 */
	public User() {
		this.firstName = "xxx";
		this.middleName = "xxx";
		this.lastName = "xxx";
		this.birthDate = LocalDate.of(2010, 12, 01);
		this.licenseNumber = "xxxxxxxxxxxxx";
		this.occupation = "no job";
		this.address = null;
	}

	/**
	 * Gets first name of User
	 * @return
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * Sets first name of User
	 * @param firstName
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * Gets middle name of User
	 * @return
	 */
	public String getMiddleName() {
		return middleName;
	}

	/**
	 * Sets middle name of User
	 * @param middleName
	 */
	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	/**
	 * Gets Last name of User
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * Sets last name of User
	 * @param lastName
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * Gets birth date of User
	 * @return
	 */
	public LocalDate getBirthDate() {
		return birthDate;
	}

	/**
	 * Sets birth date of User
	 * @param birthDate
	 */
	public void setBirthDate(LocalDate birthDate) {
		this.birthDate = birthDate;
	}

	/**
	 * Gets License Number of User
	 * @return
	 */
	public String getLicenseNumber() {
		return licenseNumber;
	}

	/**
	 * Sets License Number for User
	 * @param licenseNumber
	 */
	public void setLicenseNumber(String licenseNumber) {
		this.licenseNumber = licenseNumber;
	}

	/**
	 * Gets Occupation of User
	 * @return
	 */
	public String getOccupation() {
		return occupation;
	}

	/**
	 * Sets Occupation for User
	 * @param occupation
	 */
	public void setOccupation(String occupation) {
		this.occupation = occupation;
	}

	/**
	 * Gets Address for User
	 * @return
	 */
	public Address getAddress() {
		return address;
	}

	/**
	 * Sets Address for User
	 * @param address
	 */
	public void setAddress(Address address) {
		this.address = address;
	}

	/**
	 * Gets Age of User
	 * @return
	 */
	public int getAge() {
		return Period.between(birthDate, LocalDate.now()).getYears();
	}
	
} // End User Class
