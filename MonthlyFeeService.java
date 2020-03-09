package accounts;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.List;

import dao.AccountDAO;
import dao.TransactionDAO;
import transaction.Transaction;
import transaction.Transaction.TransactionType;

/**
 * MonthlyFeeService Class
 */
public final class MonthlyFeeService {

	private TransactionDAO transactionDAO;
	private AccountDAO accountDAO;

	public MonthlyFeeService(TransactionDAO transactionDAO, AccountDAO accountDAO) {
		this.transactionDAO = transactionDAO;
		this.accountDAO = accountDAO;
	}

	/**
	 * Applies Monthly Fees to Accounts
	 * @throws Exception
	 */
	public void applyMonthlyFee() throws Exception {
		List<Account> accounts = accountDAO.getAllAccounts();
		for (Account account : accounts) {
			TransactionType transType = TransactionType.fee;
			BigDecimal fee = this.getAccountMonthlyFee(account);
			account.setAccountBalance(account.getAccountBalance().subtract(fee));
			accountDAO.updateAccount(account);
			transactionDAO.recordTransaction(new Transaction(getAccountMonthlyFee(account), TransactionDAO.getOpenTransactionNumber(), account.getAccountNumber(), LocalDate.now(), transType));
		}
	}

	/**
	 * Calculates Monthly Fee
	 * @param account
	 * @return BigDecimal fee
	 * @throws Exception
	 */
	private BigDecimal getAccountMonthlyFee(Account account) throws Exception, SQLException {
		BigDecimal fee = BigDecimal.ZERO;
		switch (account.getAccountType()) {
		case PERSONAL_CHECKING:
			fee = new BigDecimal("10.00");
			LocalDate firstDayOfMonth = LocalDate.now().with(TemporalAdjusters.firstDayOfMonth());
			LocalDate lastDayOfMonth = LocalDate.now().with(TemporalAdjusters.lastDayOfMonth());
			List<Transaction> transactions = transactionDAO.getTransactions(account.getAccountNumber(), firstDayOfMonth,
					lastDayOfMonth);
			boolean feeWaived = false;
			boolean dailyBalanceAbove500 = true;
			for (Transaction transaction : transactions) {
				if (transaction.getTransType() == TransactionType.deposit
						&& transaction.getAmount().compareTo(new BigDecimal("500.00")) >= 0) {
					feeWaived = true;
					break;
				} else if (account.getAccountBalance().compareTo(new BigDecimal("1500.00")) < 0) {
					dailyBalanceAbove500 = false;
					break;
				}
			}
			List<Account> accounts = accountDAO.getUserAccounts(account.getLicenseNumber());
			BigDecimal average = BigDecimal.ZERO;
			BigDecimal sum = BigDecimal.ZERO;
			for (Account a : accounts) {
				sum = sum.add(a.getAccountBalance());
			}
			if (average.compareTo(BigDecimal.ZERO) > 0) {
				average = sum.divide(BigDecimal.valueOf(accounts.size()), 15, RoundingMode.HALF_UP);
			}
			if (average.compareTo(new BigDecimal("5000.00")) >= 0) {
				feeWaived = true;
			}

			if (feeWaived == true || dailyBalanceAbove500 == true) {
				fee = BigDecimal.ZERO;
			}
			break;
		case BUSINESS_CHECKING:
			fee = new BigDecimal("25.00");
			if (account.getAccountBalance().compareTo(new BigDecimal("2500.00")) >= 0) {
				fee = BigDecimal.ZERO;
			}
			break;
		case STUDENT_SAVINGS:
			fee = new BigDecimal("5.00");
			if (account.getAccountBalance().compareTo(new BigDecimal("150.00")) >= 0) {
				fee = BigDecimal.ZERO;
			}
			break;
		case PERSONAL_SAVINGS:
			fee = new BigDecimal("5.00");
			if (account.getAccountBalance().compareTo(new BigDecimal("300.00")) >= 0) {
				fee = BigDecimal.ZERO;
			}
			break;
		case BUSINESS_SAVINGS:
			fee = new BigDecimal("5.00");
			if (account.getAccountBalance().compareTo(new BigDecimal("3500.00")) >= 0) {
				fee = BigDecimal.ZERO;
			}
			break;
		default:
			break;
		}
		return fee;
	}

}
