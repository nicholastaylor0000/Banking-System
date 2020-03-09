package dao;

import userInfo.Address;

public class AddressDAO {

    /**
	 * Grabs Address from the Database
	 * @param licenseNumber
	 * @return Address
	 * @throws Exception
	 */
	public Address getAddress(String licenseNumber) throws Exception {
		AccessDatabase db = new AccessDatabase();
		Address address = new Address();
		db.connectToDB();
		address = (db.getAddress("select * from Address where licenseNumber = '" + licenseNumber + "''"));
		db.close();
		return address;
	} // End getAddress()

	/**
	 * Writes a new address to the database
	 * @param address
	 * @throws Exception
	 */
	public void recordAddress(Address address) throws Exception{
		AccessDatabase db = new AccessDatabase();
		db.connectToDB(); // opens connection to DB
		db.recordAddress(address);
		db.close(); // closes connection to DB
	}

	/**
	 * Removes an user from the database
	 * @param address
	 * @throws Exception
	 */
	public void removeAddress(Address address) throws Exception{
		AccessDatabase db = new AccessDatabase();
		db.connectToDB(); // opens connection to DB
		db.removeAddress(address);
		db.close(); // closes connection to DB
	}
	
	/**
	 * Updates an address within the database with new information by repassing entire address
	 * @param address
	 * @throws Exception
	 */
	public void updateAddress(Address address) throws Exception{
		AccessDatabase db = new AccessDatabase();
		db.connectToDB(); // opens connection to DB
		db.updateAddress(address);
		db.close(); // closes connection to DB
	}

} // End AddressDAO Class
