package transaction;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Transaction {

	// Variables
	private BigDecimal amount;
	private int transNumber;
	private int accountNumber;
	private LocalDate date;
	public TransactionType transType;

	// Withdraw / Deposit type for each Transaction
	public enum TransactionType{
		withdraw, 
		deposit,
		fee
	}
		
	/**
	 * Default Constructor
	 */
	public Transaction(){
		this.amount = new BigDecimal(0.00);
		this.accountNumber = 0;
		this.transNumber = 0;
		this.date = LocalDate.of(2010, 12, 01);
		this.transType = TransactionType.fee;
	}

	/**
	 * Constructor
	 * @param amount
	 * @param transNumber
	 * @param accountNumber
	 * @param date
	 * @param transType
	 */
	public Transaction(BigDecimal amount, int transNumber, int accountNumber, LocalDate date, TransactionType transType) {
		this.amount = amount;
		this.transNumber = transNumber;
		this.accountNumber = accountNumber;
		this.date = date;
		this.transType = transType;
	}

	/**
	 * Gets amount of transaction
	 * @return
	 */
	public BigDecimal getAmount() {
		return amount;
	}

	/**
	 * Set amount for transaction
	 */
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	/**
	 * Get Transaction Number
	 * @return
	 */
	public int getTransNumber() {
		return transNumber;
	}

	/**
	 * Set Transaction Number
	 * @param transNumber
	 */
	public void setTransNumber(int transNumber) {
		this.transNumber = transNumber;
	}

	/**
	 * Get Account Number associated with a transaction
	 * @return
	 */
	public int getAccountNumber() {
		return accountNumber;
	}

	/**
	 * Set Account Number to be associated with a transaction
	 * @param accountNumber
	 */
	public void setAccountNumber(int accountNumber) {
		this.accountNumber = accountNumber;
	}

	/**
	 * Get date for transaction
	 * @return
	 */
	public LocalDate getDate() {
		return date;
	}

	/**
	 * Sets date for transaction
	 * @param date
	 */
	public void setDate(LocalDate date) {
		this.date = date;
	}
	
	/**
	 * Gets transaction type of transaction
	 * @return
	 */
	public TransactionType getTransType(){
		return transType;
	}

	/**
	 * Sets transaction type for the transaction
	 * @param transType
	 */
	public void setTransType(TransactionType transType){
		this.transType = transType;
	}

} // End Transaction Class
