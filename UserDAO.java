package dao;

import userInfo.User;

public class UserDAO {
	
	/**
	 * Grabs User from the Database
	 * @param licenseNumber
	 * @return User
	 * @throws Exception
	 */
	public User getUser(String licenseNumber) throws Exception {
		AccessDatabase db = new AccessDatabase();
		User user = new User();
		db.connectToDB();
		user = (db.getUser("select * from User where licenseNumber = '" + licenseNumber + "''"));
		db.close();
		return user;
	} // End getUser()

	/**
	 * Writes a new user to the database
	 * @param user
	 * @throws Exception
	 */
	public void recordUser(User user) throws Exception{
		AccessDatabase db = new AccessDatabase();
		db.connectToDB(); // opens connection to DB
		db.recordUser(user);
		db.close(); // closes connection to DB
	}

	/**
	 * Removes an user from the database
	 * @param user
	 * @throws Exception
	 */
	public void removeUser(User user) throws Exception{
		AccessDatabase db = new AccessDatabase();
		db.connectToDB(); // opens connection to DB
		db.removeUser(user);
		db.close(); // closes connection to DB
	}
	
	/**
	 * Updates an user within the database with new information by repassing entire user
	 * @param user
	 * @throws Exception
	 */
	public void updateUser(User user) throws Exception{
		AccessDatabase db = new AccessDatabase();
		db.connectToDB(); // opens connection to DB
		db.updateUser(user);
		db.close(); // closes connection to DB
	}

} // End UserDAO Class
