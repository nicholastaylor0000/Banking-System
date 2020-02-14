package accounts;

import java.math.BigDecimal;

/**
 * Account Class for BankAccount (Homework1)
 * @author Nicholas Taylor
 */
public abstract class Account {
	
	private BigDecimal accountBalance;
	private String accountOwner;
	
	/**
	 * Constructor
	 * @param accountBalance Amount of Money in Account
	 * @param accountOwner Name of Owner
	 */
	public Account(BigDecimal accountBalance, String accountOwner) {
		this.accountBalance = accountBalance;
		this.accountOwner = accountOwner;
	}
	
	/**
	 * Deposit valid amount into account
	 * 
	 * @param amount
	 * @return
	 */
	public abstract BigDecimal deposit(String amount) throws IllegalArgumentException;
	
	/**
	 * Withdraw valid amount from an account
	 * 
	 * @param amount The amount withdrawing
	 * @return The updated balance
	 */
	public abstract BigDecimal withdraw(String amount) throws IllegalArgumentException;
	
	/**
	 * Validates the amount passed is verified
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
	}
	
	/**
	 * Getter for AccountBalance
	 * @return accountBalance
	 */
	public BigDecimal getAccountBalance() {
		return accountBalance;
	}
	
	/**
	 * Setter for Account Balance of Money Amount
	 * @param accountBalance
	 */
	public void setAccountBalance(BigDecimal accountBalance) {
		this.accountBalance = accountBalance;
	}
	
	/**
	 * Getter for Account Owner
	 * @return accountOwner
	 */
	public String getAccountOwner() {
		return accountOwner;
	}
	
	/**
	 * Setter for accountOwner using Name
	 * @param accountOwner
	 */
	public void setAccountOwner(String accountOwner) {
		this.accountOwner = accountOwner;
	}
	
} // End of Account Class
