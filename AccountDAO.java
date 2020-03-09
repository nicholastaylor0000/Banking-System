package dao;

import java.util.List;

import accounts.Account;

public class AccountDAO {

	/**
	 * Grabs an already made account from the Database
	 * @param licenseNumber
	 * @return Account
	 * @throws Exception
	 */
	public Account getAccount(String licenseNumber) throws Exception {
		AccessDatabase db = new AccessDatabase();
		Account account = new Account();
		db.connectToDB();  // opens connection to DB
		account = (db.getAccount("select * from Account where licenseNumber = '" + licenseNumber + "'"));
		db.close(); // closes connection to DB
		return account;
	} // End getAccount()

	/**
	 * Writes a new account to the database
	 * @param account
	 * @throws Exception
	 */
	public void recordAccount(Account account) throws Exception{
		AccessDatabase db = new AccessDatabase();
		db.connectToDB(); // opens connection to DB
		db.recordAccount(account);
		db.close(); // closes connection to DB
	}

	/**
	 * Removes an account from the database
	 * @param account
	 * @throws Exception
	 */
	public void removeAccount(Account account) throws Exception{
		AccessDatabase db = new AccessDatabase();
		db.connectToDB(); // opens connection to DB
		db.removeAccount(account);
		db.close(); // closes connection to DB
	}
	
	/**
	 * Updates an account within the database with new information by repassing entire account
	 * @param account
	 * @throws Exception
	 */
	public void updateAccount(Account account) throws Exception{
		AccessDatabase db = new AccessDatabase();
		db.connectToDB(); // opens connection to DB
		db.updateAccount(account);
		db.close(); // closes connection to DB
	}

	/**
	 * Pulls the next available account number as an integer from the database
	 * @return
	 * @throws Exception
	 */
	public static int getOpenAccountNumber() throws Exception {
		AccessDatabase db = new AccessDatabase();
		db.connectToDB(); // opens connection to DB
		int openAccountNumber = db.getOpenAccountNumber();
		db.close(); // closes connection to DB
		return openAccountNumber;
	}

	/**
	 * Grabs all accounts from the database
	 * @return List of accounts
	 * @throws Exception
	 */
	public List<Account> getAllAccounts() throws Exception{
		AccessDatabase db = new AccessDatabase();
		db.connectToDB(); // opens connection to DB
		List<Account> allAccounts = db.getAllAccounts("select * from user");
		db.close();
		return allAccounts;
	}

	/**
	 * Grabs an already made account from the Database
	 * @param licenseNumber
	 * @return Account
	 * @throws Exception
	 */
	public List<Account> getUserAccounts(String licenseNumber) throws Exception {
		AccessDatabase db = new AccessDatabase();
		db.connectToDB();  // opens connection to DB
		List<Account> allAccounts = db.getUserAccounts("select * from Account where licenseNumber = '" + licenseNumber + "'");
		db.close(); // closes connection to DB
		return allAccounts;
	} // End getAccount()
	
} // End AccountDAO Class
