package accounts;

import java.math.BigDecimal;

/**
 * SavingsAccount Class for BankAccount (Homework1)
 * @author Nicholas Taylor
 */
public class SavingsAccount extends Account{
	
	/**
	 * Constructor for Savings Account
	 * @param accountBalance
	 * @param accountOwner
	 */
	public SavingsAccount(BigDecimal accountBalance, String accountOwner) {
		super(accountBalance, accountOwner);
	}
	
	/**
	 * Deposit to Savings Account
	 */
	@Override
	public BigDecimal deposit(String amountToDeposit) {
		BigDecimal amount = super.getValidAmount(amountToDeposit);
		super.setAccountBalance(super.getAccountBalance().add(amount));
		return super.getAccountBalance();
	}
	
	/**
	 * Withdraw from Savings Account
	 */
	@Override
	public BigDecimal withdraw(String amountToWithdraw) throws IllegalArgumentException{
		BigDecimal amount = super.getValidAmount(amountToWithdraw);
		if(super.getAccountBalance().compareTo(amount) < 0) {
			throw new IllegalArgumentException(amount.toPlainString() + " is more than the current account balance of " + super.getAccountBalance());
		}
		super.setAccountBalance(super.getAccountBalance().subtract(amount));
		return super.getAccountBalance();
	}
	
} // End SavingsAccount Class
