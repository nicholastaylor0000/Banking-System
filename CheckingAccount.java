package accounts;

import java.math.BigDecimal;

/**
 * CheckingAccount Class for BankAccount (Homework1)
 * @author Nicholas Taylor
 */
public class CheckingAccount extends Account{

	// Variables
	private final static BigDecimal OVERDRAFT_AMOUNT = new BigDecimal("-1000.00");
	private final static BigDecimal OVERDRAFT_FEE = new BigDecimal("35.00");
	
	/**
	 *  Enum for CheckingAccount Status
	 */
	public enum STATUS {OVERDRAFTED, NORMAL}
	STATUS stat = STATUS.NORMAL; // Starts status of an account as normal
	
	/**
	 * Constructor
	 * @param accountBalance
	 * @param accountOwner
	 */
	public CheckingAccount(BigDecimal accountBalance, String accountOwner) {
		super(accountBalance, accountOwner);
	}

	/**
	 * Deposit to Checking Account
	 */
	@Override
	public BigDecimal deposit(String amountToDeposit) {
		BigDecimal amount = super.getValidAmount(amountToDeposit);
		super.setAccountBalance(super.getAccountBalance().add(amount));
		if((stat == STATUS.OVERDRAFTED) && super.getAccountBalance().compareTo(BigDecimal.ZERO)>= 0) {
			 stat = STATUS.NORMAL;
		}
		return super.getAccountBalance();
	}
	
	/**
	 * Withdraw from Checking Account
	 */
	@Override
	public BigDecimal withdraw(String amountToWithdraw) {
		BigDecimal amount = super.getValidAmount(amountToWithdraw);
		BigDecimal result = super.getAccountBalance().subtract(amount);
		if(result.compareTo(OVERDRAFT_AMOUNT) >= 0) {
			super.setAccountBalance(result);
			if(result.compareTo(BigDecimal.ZERO) == -1) {
				super.setAccountBalance(super.getAccountBalance().subtract(OVERDRAFT_FEE));
				if(stat == STATUS.NORMAL){
					stat = STATUS.OVERDRAFTED;
					System.out.println("Account is currently overdrafted. Added a 35$ Overdraft Fee.");
				}
			}
		}
		else {
			System.out.println("Cannot overdraft your account by this amount: " + result);
		}
		return super.getAccountBalance();
	}

} // End CheckingAccount Class
