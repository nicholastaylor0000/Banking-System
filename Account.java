package accounts;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import dao.AccountDAO;
import dao.UserDAO;
import userInfo.User;

public class Account {

	private int accountNumber;
	private String licenseNumber;
	private BigDecimal accountBalance;
	private AccountType accountType;
	private AccountStatus accountStatus;
	private List<User> accountOwners = new ArrayList<User>();
	private boolean ableToOverdraft;
	
	/**
	 * Default Constructor
	 */
	public Account() {
		this.accountNumber = 0;
		this.licenseNumber = "";
		this.accountBalance =  new BigDecimal(0.00);
		this.accountType = AccountType.STUDENT_CHECKING;
		this.accountStatus = AccountStatus.ACTIVE;
		this.ableToOverdraft = checkAbleToOverdraft(accountType, accountStatus);
	}

	/**
	 * Constructor for a new account
	 * 
	 * @param licenseNumber
	 * @param accountType
	 * @param ableToOverdraft
	 * @param accountOwners
	 * @throws Exception
	 */
	public Account(String licenseNumber, AccountType accountType, boolean ableToOverdraft, User... accountOwners)
			throws Exception {
		this.accountNumber = AccountDAO.getOpenAccountNumber();
		this.licenseNumber = licenseNumber;
		this.accountBalance = BigDecimal.ZERO;
		this.accountType = accountType;
		this.accountStatus = AccountStatus.ACTIVE;
		this.ableToOverdraft = ableToOverdraft;
		this.accountOwners = Arrays.asList(accountOwners);
	}
	
	/**
	 * Constructor for making a new account 
	 * @param accountNumber
	 * @param licenseNumber
	 * @param accountBalance
	 * @param accountType
	 * @param ableToOverdraft
	 * @param accountOwners
	 */
	public Account(int accountNumber, String licenseNumber, BigDecimal accountBalance, AccountType accountType, boolean ableToOverdraft, User... accountOwners){
		this.accountNumber = accountNumber;
		this.licenseNumber = licenseNumber;
		this.accountBalance = accountBalance;
		this.accountType = accountType;
		this.accountStatus = AccountStatus.ACTIVE;
		this.ableToOverdraft = ableToOverdraft;
		this.accountOwners = Arrays.asList(accountOwners);
	}

	/**
	 * Constructor for recreating an account object from the database stored information
	 * @param accountNumber
	 * @param licenseNumber
	 * @param accountBalance
	 * @param accountType
	 * @param userDAO
	 * @throws Exception
	 */
	public Account(int accountNumber, String licenseNumber, BigDecimal accountBalance, AccountType accountType, AccountStatus accountStatus, UserDAO userDAO) throws Exception{
		this.accountNumber = accountNumber;
		this.licenseNumber = licenseNumber;
		this.accountBalance = accountBalance;
		this.accountType = accountType;
		this.accountStatus = accountStatus;
		this.ableToOverdraft = checkAbleToOverdraft(accountType, accountStatus);
		this.accountOwners.add(userDAO.getUser(licenseNumber));
	}

	/**
	 * Constructor for a new account with only one account owner
	 * @param licenseNumber
	 * @param accountType
	 * @param accountOwner
	 * @param ableToOverdraft
	 * @throws Exception
	 */
	public Account(String licenseNumber, AccountType accountType, User accountOwner, boolean ableToOverdraft) throws Exception{
		this.accountNumber = AccountDAO.getOpenAccountNumber();
		this.accountBalance = BigDecimal.ZERO;
		this.licenseNumber = licenseNumber;
		this.accountType = accountType;
		this.accountStatus = AccountStatus.ACTIVE;
		this.accountOwners.add(accountOwner);
	}

	/**
	 * Gets Account Number
	 * @return
	 */
	public int getAccountNumber(){
		return accountNumber;
	}
	
	/**
	 * Sets Account Number
	 * @param accountNumber
	 */
	public void setAccountNumber(int accountNumber){
		this.accountNumber = accountNumber;
	}

	/**
	 * Gets License Number
	 * @return
	 */
	public String getLicenseNumber(){
		return licenseNumber;
	}

	/**
	 * Sets License Number
	 * @param licenseNumber
	 */
	public void setLicenseNumber(String licenseNumber){
		this.licenseNumber = licenseNumber;
	}

	/**
	 * Gets Account Balance
	 * @return
	 */
	public BigDecimal getAccountBalance(){
		return accountBalance;
	}

	/**
	 * Sets Account Balance
	 * @param accountBalance
	 */
	public void setAccountBalance(BigDecimal accountBalance){
		this.accountBalance = accountBalance;
	}

	/**
	 * Gets Account Type
	 */
	public AccountType getAccountType(){
		return accountType;
	}

	/**
	 * Sets Account Type
	 * @param accountType
	 */
	public void setAccountType(AccountType accountType){
		this.accountType = accountType;
	}

	/**
	 * Gets Account Status
	 * @return
	 */
	public AccountStatus getAccountStatus(){
		return accountStatus;
	}

	/**
	 * Sets Account Status
	 * @param accountStatus
	 */
	public void setAccountStatus(AccountStatus accountStatus){
		this.accountStatus = accountStatus;
	}

	/**
	 * Boolean if accounts able to Overdraft
	 * @return
	 */
	public boolean ableToOverdraft(){
		return ableToOverdraft;
	}

	/**
	 * Set account ability to overdraft
	 * @param ableToOverdraft
	 */
	public void setAbleToOverdraft(boolean ableToOverdraft){
		this.ableToOverdraft = ableToOverdraft;
	}

	/**
	 * Add Account Owner
	 * @param user
	 * @throws Exception
	 */
	public void addAccountOwner(User user) throws Exception{
		accountOwners.add(user);
	}

	/**
	 * Gets a list of account owners
	 * @return
	 */
	public List<User> getAccountOwners(){
		return accountOwners;
	}

	@Override
	public String toString(){
		String accountOwners = "";
		Iterator<User> it = this.accountOwners.iterator();
		if(it.hasNext()){
			accountOwners = it.next().getLicenseNumber();
		}
		while(it.hasNext()){
			accountOwners = accountOwners + ", " + it.next().getLicenseNumber();
		}
		return String.format("%s,%s,%s,%s,%s,%s", this.accountNumber, this.accountOwners, this.accountBalance, this.accountType.toString(), this.accountStatus.toString(), this.ableToOverdraft);
	}

	/**
	 * Validates the amount passed is the correct form.
	 * 
	 * @param amount: The amount in the form #+.##
	 * @return A BigDecimal representation of the amount
	 * @throws IllegalArgumentException if the amount is not in a valid form
	 */
	public static BigDecimal getValidAmount(String amount) throws IllegalArgumentException {

		if (amount.matches("^\\d+\\.\\d{2}$")) {
			return new BigDecimal(amount);
		}
		throw new IllegalArgumentException(amount + " should be in the form $(#+).##");

	} // End getValidAmount()

	/**
	 * Checks the Account Type and Account Status to see if it should be able to overdraft
	 * @param accountType
	 * @param accountStatus
	 * @return boolean
	 */
	public boolean checkAbleToOverdraft(AccountType accountType, AccountStatus accountStatus){
		
		boolean able = false;

		if(accountType == AccountType.STUDENT_CHECKING && accountStatus == AccountStatus.ACTIVE) {
			able = true;
		}
		else if(accountType == AccountType.PERSONAL_CHECKING && accountStatus == AccountStatus.ACTIVE){
			able = true;
		}
		else if(accountType == AccountType.BUSINESS_CHECKING && accountStatus == AccountStatus.ACTIVE){
			able = true;
		}
		else if(accountType == AccountType.STUDENT_SAVINGS && accountStatus == AccountStatus.ACTIVE){
			able = false;
		}
		else if(accountType == AccountType.PERSONAL_SAVINGS && accountStatus == AccountStatus.ACTIVE){
			able = false;
		}
		else if(accountType == AccountType.BUSINESS_SAVINGS && accountStatus == AccountStatus.ACTIVE){
			able = false;
		}

		return able;

	} // End checkAbleToOverdraft()

} // End Account Class
