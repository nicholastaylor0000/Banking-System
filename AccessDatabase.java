package dao;

import java.math.BigDecimal;

// Accesses the database with student and phone information

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

import accounts.Account;
import accounts.AccountType;
import accounts.AccountStatus;
import transaction.Transaction;
import transaction.Transaction.TransactionType;
import userInfo.Address;
import userInfo.User;

public class AccessDatabase {

	private static Connection connect = null;
	private static Statement statement = null;
	private ResultSet resultSet = null;

	/**
	 * Creates a connection to the Database
	 * @throws SQLException
	 */
	public void connectToDB() throws SQLException {

		try {

			// Allocate a database 'Connection' object
			connect = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/BankingSystem?allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=UTC",
					"myuser", "2apiehwd"); // For MySQL only
			// The format is: "jdbc:mysql://hostname:port/databaseName", "username",
			// "password"

			// Allocate a 'Statement' object in the Connection
			statement = connect.createStatement();

		} catch (Exception e) {
			throw e;
		}

	} // End connectToDB()

	/**
	 * Gets User from the database
	 * @param command
	 * @return
	 * @throws Exception
	 */
	public User getUser(String command) throws Exception {

		try {

			User myUser = new User();
			resultSet = statement.executeQuery(command);

			while (resultSet.next()) {
				String firstName = resultSet.getString("firstName");
				String middleName = resultSet.getString("middleName");
				String lastName = resultSet.getString("lastName");
				Date tempDate = resultSet.getDate("birthDate");
				LocalDate birthDate = convertToLocalDate(tempDate);
				String licenseNumber = resultSet.getString("licenseNumber");
				String occupation = resultSet.getString("occupation");
				Address address = getAddress("select * from address where licenseNumber = '" + licenseNumber + "'");
				myUser.setFirstName(firstName);
				myUser.setMiddleName(middleName);
				myUser.setLastName(lastName);
				myUser.setBirthDate(birthDate);
				myUser.setLicenseNumber(licenseNumber);
				myUser.setOccupation(occupation);
				myUser.setAddress(address);
			}

			return myUser;

		} catch (SQLException e) {
			throw e;
		}

	} // End getUser()

	/**
	 * Gets Account from the database
	 * @param command
	 * @return
	 * @throws Exception
	 */
	public Address getAddress(String command) throws Exception {

		try {

			Address address = new Address();
			resultSet = statement.executeQuery(command);

			while (resultSet.next()) {
				String licenseNumber = resultSet.getString("licenseNumber");
				String street = resultSet.getString("street");
				String state = resultSet.getString("state");
				String zip = resultSet.getString("zip");
				address.setLicenseNumber(licenseNumber);
				address.setStreet(street);
				address.setState(state);
				address.setZip(zip);
			}

			return address;

		} catch (SQLException e) {
			throw e;
		}

	} // End getAddress()

	/**
	 * Gets Account from the database
	 * @param command
	 * @return
	 * @throws Exception
	 */
	public Account getAccount(String command) throws Exception {

		try {

			Account account = new Account();
			resultSet = statement.executeQuery(command);

			while (resultSet.next()) {
				int accountNumber = resultSet.getInt("accountNumber");
				String licenseNumber = resultSet.getString("accountOwnerLN");
				BigDecimal accountBalance = resultSet.getBigDecimal("accountBalance");
				String accountType = resultSet.getString("accountType");
				String accountStatus = resultSet.getString("accountStatus");
				account.setAccountNumber(accountNumber);
				account.setLicenseNumber(licenseNumber);
				account.setAccountBalance(accountBalance);
				account.setAccountType(AccountType.valueOf(accountType));
				account.setAccountStatus(AccountStatus.valueOf(accountStatus));

			}

			return account;

		} catch (SQLException e) {
			throw e;
		}

	} // End getAccount()

	/**
	 * Gets transaction from the database
	 * @param command
	 * @return
	 * @throws SQLException
	 */
	public Transaction getTransaction(String command) throws SQLException {

		try {

			Transaction trans = new Transaction();
			resultSet = statement.executeQuery(command);

			while (resultSet.next()) {
				int transactionNumber = resultSet.getInt("transactionNumber");
				int accountNumber = resultSet.getInt("accountNumber");
				Date tempDate = resultSet.getDate("transDate");
				LocalDate date = convertToLocalDate(tempDate);
				BigDecimal amount = resultSet.getBigDecimal("amount");
				String transType = resultSet.getString("transType");
				trans.setTransNumber(transactionNumber);
				trans.setAccountNumber(accountNumber);
				trans.setDate(date);
				trans.setAmount(amount);
				trans.setTransType(TransactionType.valueOf(transType));
			}

			return trans;

		}
		catch(SQLException e){
			throw e;
		}

	} // End getTransaction()

	/**
	 * Records User into the database
	 * @param user
	 */
	public void recordUser(User user){

		try{

			String command = "insert into User values "
			 + "('" + user.getFirstName() + "', '" + user.getMiddleName() + "', '" + user.getLastName() 
			 + "', '" + user.getBirthDate().toString() + "', '" + user.getOccupation() + "', '" + user.getAddress().getStreet() + "')";
			statement.executeQuery(command);

		}
		catch(SQLException e){
			e.printStackTrace();
		}

	} // End recordUser()

	/**
	 * Updates User within the database
	 * @param user
	 */
	public void updateUser(User user){

		try{

			String command = "UPDATE User SET firstName = '" + user.getFirstName() + "', "
			+ "middleName = '" + user.getMiddleName() + "', "
			+ "lastName = '" + user.getLastName() + "', "
			+ "birthDate = '" + user.getBirthDate().toString() + "', "
			+ "occupation = '" + user.getOccupation() + "', "
			+ "address = '" + user.getAddress().getStreet() + "' " 
			+ "WHERE licenseNumber = '" + user.getLicenseNumber() + "'"
			;
			statement.executeQuery(command);

		}
		catch(SQLException e){
			e.printStackTrace();
		}

	} // End updateUser()

	/**
	 * Removes User from the database
	 * @param user
	 */
	public void removeUser(User user){

		try{

			String command = "DELETE from User WHERE licenseNumber = '" + user.getLicenseNumber() + "'";
			statement.executeQuery(command);

		}
		catch(SQLException e){
			e.printStackTrace();
		}

	} // End removeUser()

	/**
	 * Records Address into the database
	 * @param address
	 */
	public void recordAddress(Address address){

		try{

			String command = "insert into Address values "
			 + "('" + address.getLicenseNumber() + "', '" + address.getStreet() + "', '" + address.getState() + "', '" + address.getZip() + "')";
			statement.executeQuery(command);

		}
		catch(SQLException e){
			e.printStackTrace();
		}

	} // End recordAddress()

	/**
	 * Updates Address within the database
	 * @param address
	 */
	public void updateAddress(Address address){

		try{

			String command = "UPDATE Address SET licenseNumber = '" + address.getLicenseNumber() + "', "
			+ "street = '" + address.getStreet() + "', "
			+ "state = '" + address.getState() + "', "
			+ "zip = '" + address.getZip() + "' " 
			+ "WHERE licenseNumber = '" + address.getLicenseNumber() + "'"
			;
			statement.executeQuery(command);

		}
		catch(SQLException e){
			e.printStackTrace();
		}

	} // End updateAddress()
	
	/**
	 * Removes Address from the database
	 * @param address
	 */
	public void removeAddress(Address address){

		try{

			String command = "DELETE from Address WHERE street = '" + address.getStreet() + "'";
			statement.executeQuery(command);

		}
		catch(SQLException e){
			e.printStackTrace();
		}

	} // End removeAddress()

	/**
	 * Records account to the database
	 * @param account
	 */
	public void recordAccount(Account account){

		try{

			String command = "insert into Account values "
			 + "(" + account.getAccountNumber() + ", '" + account.getLicenseNumber() + "', " + account.getAccountBalance() 
			 + ", '" + account.getAccountType().toString() + "', '" + account.getAccountStatus().toString() + "')";
			statement.executeQuery(command);

		}
		catch(SQLException e){
			e.printStackTrace();
		}

	} // End recordAccount()

	/**
	 * Updates account within the database
	 * @param account
	 */
	public void updateAccount(Account account){

		try{

			String command = "UPDATE Account SET accountNumber = " + account.getAccountNumber() + ", "
			+ "accountOwnerLN = '" + account.getLicenseNumber() + "', "
			+ "accountBalance = " + account.getAccountBalance() + ", "
			+ "accountType = '" + account.getAccountType().toString() + "', "
			+ "accountStatus = '" + account.getAccountStatus().toString() + "' " 
			+ "WHERE accountNumber = " + account.getAccountNumber()
			;
			statement.executeQuery(command);

		}
		catch(SQLException e){
			e.printStackTrace();
		}

	} // End updateAccount()

	/**
	 * Removes Account from database
	 * @param account
	 */
	public void removeAccount(Account account){

		try{

			String command = "DELETE from Account WHERE accountNumber = " + account.getAccountNumber();
			statement.executeQuery(command);

		}
		catch(SQLException e){
			e.printStackTrace();
		}

	} // End removeAccount()

	/**
	 * Records transaction in database
	 * @param trans
	 */
	public void recordTransaction(Transaction trans){

		try{

			String command = "insert into Transaction values "
			 + "(" + trans.getTransNumber() + ", " + trans.getAccountNumber() + ", '" + trans.getDate().toString() 
			 + "', " + trans.getAmount() + ", '" + trans.getTransType().toString() + "')";
			statement.executeQuery(command);

		}
		catch(SQLException e){
			e.printStackTrace();
		}

	} // End recordTransaction()

	/**
	 * Updates transaction within database
	 * @param trans
	 */
	public void updateTransaction(Transaction trans){

		try{

			String command = "UPDATE Transaction SET transNumber = " + trans.getTransNumber() + ", "
			+ "accountNumber = " + trans.getAccountNumber() + ", "
			+ "date = '" + trans.getDate().toString() + "', "
			+ "amount = " + trans.getAmount() + ", "
			+ "transactionType = '" + trans.getTransType().toString() + "' " 
			+ "WHERE transactionNumber = " + trans.getTransNumber()
			;
			statement.executeQuery(command);

		}
		catch(SQLException e){
			e.printStackTrace();
		}

	} // End updateTransaction()

	/**
	 * Removes transaction from database
	 * @param trans
	 */
	public void removeTransaction(Transaction trans){

		try{

			String command = "DELETE from Transaction WHERE transactionNumber = " + trans.getTransNumber();
			statement.executeQuery(command);

		}
		catch(SQLException e){
			e.printStackTrace();
		}

	} // End removeTransaction()

	/**
	 * Gets next available Account Number
	 * @return int
	 * @throws SQLException
	 */
	public int getOpenAccountNumber() throws SQLException {

		try {

			int maxAccountNumber = 0;
			resultSet = statement.executeQuery("SELECT MAX(accountNumber) from Account");

			maxAccountNumber = resultSet.getInt("accountNumber");

			int maxAccountNumberAddOne = maxAccountNumber + 1;
			return maxAccountNumberAddOne;

		} catch (SQLException e) {
			throw e;
		}

	} // End getOpenAccountNumber()

	/**
	 * Gets next available Transaction Number
	 * @return int
	 * @throws SQLException
	 */
	public int getOpenTransactionNumber() throws SQLException {

		try {

			int maxTransactionNumber = 0;
			resultSet = statement.executeQuery("SELECT MAX(transactionNumber) from Transaction");

			maxTransactionNumber = resultSet.getInt("transactionNumber");

			int maxTransactionNumberAddOne = maxTransactionNumber + 1;
			return maxTransactionNumberAddOne;

		} catch (SQLException e) {
			throw e;
		}

	} // End getOpenTransactionNumber()

	/**
	 * Converts a date from Java Date to Java LocalDate
	 * @param dateToConvert
	 * @return
	 */
	public LocalDate convertToLocalDate(Date dateToConvert) {
		return dateToConvert.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
	}

	/**
	 * Closes the resultSet which is needed to be done between operations
	 */
	public void close() {
		
		try {
			
			if (resultSet != null) {
				resultSet.close();
			}

			if (statement != null) {
				statement.close();
			}

			if (connect != null) {
				connect.close();
			}
			
		} catch (SQLException e) {
			
		}

	} // End close()

	/**
	 * Gets all accounts within the database
	 * @param command
	 * @return List<Account>
	 * @throws SQLException
	 */
	public List<Account> getAllAccounts(String command) throws SQLException {
		
		try {

			Account account = new Account();
			List<Account> allAccounts = new ArrayList<Account>();
			resultSet = statement.executeQuery(command);

			while(resultSet.next()) {
				int accountNumber = resultSet.getInt("accountNumber");
				String licenseNumber = resultSet.getString("accountOwnerLN");
				BigDecimal accountBalance = resultSet.getBigDecimal("accountBalance");
				String accountType = resultSet.getString("accountType");
				String accountStatus = resultSet.getString("accountStatus");
				account.setAccountNumber(accountNumber);
				account.setLicenseNumber(licenseNumber);
				account.setAccountBalance(accountBalance);
				account.setAccountType(AccountType.valueOf(accountType));
				account.setAccountStatus(AccountStatus.valueOf(accountStatus));
				allAccounts.add(account);
			}

			return allAccounts;

		} catch (SQLException e) {
			throw e;
		}

	} // End getAllAccounts()

	/**
	 * Gets all accounts for a user
	 * @param command
	 * @return List<Account>
	 * @throws SQLException
	 */
	public List<Account> getUserAccounts(String command) throws SQLException {
		
		try {

			Account account = new Account();
			List<Account> allAccounts = new ArrayList<Account>();
			resultSet = statement.executeQuery(command);

			while(resultSet.next()) {
				int accountNumber = resultSet.getInt("accountNumber");
				String licenseNumber = resultSet.getString("accountOwnerLN");
				BigDecimal accountBalance = resultSet.getBigDecimal("accountBalance");
				String accountType = resultSet.getString("accountType");
				String accountStatus = resultSet.getString("accountStatus");
				account.setAccountNumber(accountNumber);
				account.setLicenseNumber(licenseNumber);
				account.setAccountBalance(accountBalance);
				account.setAccountType(AccountType.valueOf(accountType));
				account.setAccountStatus(AccountStatus.valueOf(accountStatus));
				allAccounts.add(account);
			}

			return allAccounts;

		} catch (SQLException e) {
			throw e;
		}

	} // End getUserAccounts()

	/**
	 * Gets Transactions for an account
	 * @param command
	 * @return List<Transaction>
	 * @throws SQLException
	 */
	public List<Transaction> getAccountTransactions(String command) throws SQLException{
		
		try {

			Transaction trans = new Transaction();
			List<Transaction> accountTransactions = new ArrayList<Transaction>();
			resultSet = statement.executeQuery(command);

			while (resultSet.next()) {
				int transactionNumber = resultSet.getInt("transactionNumber");
				int accountNumber = resultSet.getInt("accountNumber");
				Date tempDate = resultSet.getDate("transDate");
				LocalDate date = convertToLocalDate(tempDate);
				BigDecimal amount = resultSet.getBigDecimal("amount");
				String transType = resultSet.getString("transType");
				trans.setTransNumber(transactionNumber);
				trans.setAccountNumber(accountNumber);
				trans.setDate(date);
				trans.setAmount(amount);
				trans.setTransType(TransactionType.valueOf(transType));
				accountTransactions.add(trans);
			}

			return accountTransactions;

		}
		catch(SQLException e){
			throw e;
		}

	} // End getAccountTransactions()

} // End AccessDatabase Class
