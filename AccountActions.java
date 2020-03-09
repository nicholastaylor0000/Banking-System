package accounts;

import java.math.BigDecimal;
import java.time.LocalDate;

import dao.AccountDAO;
import dao.TransactionDAO;
import transaction.Transaction;
import transaction.Transaction.TransactionType;

/**
 * AccountActions Class for HW3
 */
public class AccountActions {

	private AccountDAO accountDAO;
	private TransactionDAO transactionDAO;

	public AccountActions(AccountDAO accountDAO, TransactionDAO transactionDAO) {
		this.accountDAO = accountDAO;
		this.transactionDAO = transactionDAO;
	}

	/**
	 * Adds account to database
	 * @param account
	 * @throws Exception
	 */
	public void addAccount(Account account) throws Exception {
		this.accountDAO.recordAccount(account);
	}

	/**
	 * Updates an account when it needs to be changed in the database
	 * @param account
	 * @throws Exception
	 */
	public void updateAccount(Account account) throws Exception {
		this.accountDAO.updateAccount(account);
	}

	/**
	 * Writes deposit transaction to db
	 * @param account
	 * @param amount
	 * @return
	 * @throws Exception
	 */
	public boolean deposit(Account account, String amount) throws Exception {
		Transaction transaction = this.depositTransaction(account, amount);
		accountDAO.updateAccount(account);
		transactionDAO.recordTransaction(transaction);
		return true;
	}

	/**
	 * Deposit amount into an account
	 * @param account
	 * @param amount
	 * @return
	 * @throws Exception
	 */
	private Transaction depositTransaction(Account account, String amount) throws Exception {
		BigDecimal validAmount = Account.getValidAmount(amount);
		BigDecimal newBalance = account.getAccountBalance().add(Account.getValidAmount(amount));
		TransactionType transType = TransactionType.deposit;
		account.setAccountBalance(newBalance);
		if(account.getAccountStatus() == AccountStatus.OVERDRAWN && newBalance.compareTo(BigDecimal.ZERO) >= 0) {
			account.setAccountStatus(AccountStatus.ACTIVE);
		}
		return new Transaction(validAmount, TransactionDAO.getOpenTransactionNumber(), account.getAccountNumber(), LocalDate.now(), transType);
	}

	/**
	 * Writes withdraw transaction to db
	 * @param account
	 * @param amount
	 * @return
	 * @throws Exception
	 */
	public boolean withdraw(Account account, String amount) throws Exception {
		Transaction transaction = this.withdrawTransactions(account, amount);
		accountDAO.updateAccount(account);
		transactionDAO.recordTransaction(transaction);
		if (account.checkAbleToOverdraft(account.getAccountType(), account.getAccountStatus()) && account.getAccountBalance().compareTo(BigDecimal.ZERO) < 0) {
			transaction = OverDraftFeeService.applyOverDraftFee(account);
			accountDAO.updateAccount(account);
			transactionDAO.recordTransaction(transaction);
		}
		return true;
	}

	/**
	 * Withdraw amount from an account
	 * @param account
	 * @param amount
	 * @return
	 * @throws Exception
	 */
	private Transaction withdrawTransactions(Account account, String amount) throws Exception {
		BigDecimal validAmount = Account.getValidAmount(amount);
		BigDecimal newBalance = account.getAccountBalance().subtract(Account.getValidAmount(amount));
		TransactionType transType = TransactionType.withdraw;
		if ((newBalance.compareTo(BigDecimal.ZERO) < 0 && !account.checkAbleToOverdraft(account.getAccountType(), account.getAccountStatus()))) {
			throw new IllegalStateException("Account is not allowed to overdraft.");
		} else if (account.checkAbleToOverdraft(account.getAccountType(), account.getAccountStatus())
				&& newBalance.compareTo(OverDraftFeeService.getOverDraftFeeLimit(account.getAccountType())) <= 0) {
			throw new IllegalStateException("Withdraw exceeds over draft maximum of "
					+ OverDraftFeeService.getOverDraftFeeLimit(account.getAccountType()).toPlainString());
		} else {
			account.setAccountBalance(newBalance);
			return new Transaction(validAmount, TransactionDAO.getOpenTransactionNumber(), account.getAccountNumber(), LocalDate.now(), transType);
		}
	}

}
