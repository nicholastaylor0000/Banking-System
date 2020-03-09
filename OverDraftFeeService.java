package accounts;

import java.math.BigDecimal;
import java.time.LocalDate;

import dao.TransactionDAO;
import transaction.Transaction;
import transaction.Transaction.TransactionType;

/**
 * OverdraftFee Class
 */
public final class OverDraftFeeService{

	/*
	 * The overdraft fee amount
	 */
	private static final BigDecimal OVER_DRAFT_FEE = new BigDecimal("35.00");

	/**
	 * Overdrafting Fee
	 */
	public static Transaction applyOverDraftFee(Account account) throws Exception {
		account.setAccountBalance(account.getAccountBalance().subtract(OVER_DRAFT_FEE));
		TransactionType transType = TransactionType.fee;
		return new Transaction(OVER_DRAFT_FEE, TransactionDAO.getOpenTransactionNumber(), account.getAccountNumber(), LocalDate.now(), transType);
	}

	/**
	 * Use to determine the overdraft fee limit.
	 * 
	 * @param type: The type of account.
	 * @return: The overdraft fee limit.
	 */
	public static BigDecimal getOverDraftFeeLimit(AccountType type) {
		BigDecimal overDraftFeeLimit = BigDecimal.ZERO;
		switch (type) {
		case STUDENT_CHECKING:
			overDraftFeeLimit = new BigDecimal("-500.00");
			break;
		case PERSONAL_CHECKING:
			overDraftFeeLimit = new BigDecimal("-1500.00");
			break;
		case BUSINESS_CHECKING:
			overDraftFeeLimit = new BigDecimal("-7500.00");
			break;
		default:
			throw new IllegalArgumentException("Undefined Account Type.");
		}
		return overDraftFeeLimit;
	}

}
