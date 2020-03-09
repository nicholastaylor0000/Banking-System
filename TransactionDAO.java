package dao;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import transaction.Transaction;

public class TransactionDAO {

    /**
	 * Grabs Transaction from the Database
	 * @param transactionNumber
	 * @return Transaction
	 * @throws Exception
	 */
	public Transaction getTransaction(int transactionNumber) throws SQLException {
		AccessDatabase db = new AccessDatabase();
		Transaction transaction = new Transaction();
		db.connectToDB();
		transaction = (db.getTransaction("select * from Transaction where transactionNumber = " + transactionNumber));
		db.close();
		return transaction;
	} // End getTransaction()

	/**
	 * Writes a new transaction to the database
	 * @param transaction
	 * @throws Exception
	 */
	public void recordTransaction(Transaction transaction) throws SQLException{
		AccessDatabase db = new AccessDatabase();
		db.connectToDB(); // opens connection to DB
		db.recordTransaction(transaction);
		db.close(); // closes connection to DB
	}

	/**
	 * Removes an transaction from the database
	 * @param transaction
	 * @throws Exception
	 */
	public void removeTransaction(Transaction transaction) throws Exception{
		AccessDatabase db = new AccessDatabase();
		db.connectToDB(); // opens connection to DB
		db.removeTransaction(transaction);
		db.close(); // closes connection to DB
	}
	
	/**
	 * Updates an transaction within the database with new information by repassing entire transaction
	 * @param transaction
	 * @throws Exception
	 */
	public void updateTransaction(Transaction transaction) throws SQLException{
		AccessDatabase db = new AccessDatabase();
		db.connectToDB(); // opens connection to DB
		db.updateTransaction(transaction);
		db.close(); // closes connection to DB
	}

    /**
	 * Pulls the next available transaction number as an integer from the database
	 * @return int
	 * @throws Exception
	 */
	public static int getOpenTransactionNumber() throws SQLException {
		AccessDatabase db = new AccessDatabase();
		db.connectToDB(); // opens connection to DB
		int openTransactionNumber = db.getOpenTransactionNumber();
		db.close(); // closes connection to DB
		return openTransactionNumber;
	}

    /**
     * Pulls a list of transactions from the db
     * @param accountNumber
     * @param firstDayOfMonth
     * @param lastDayOfMonth
     * @return List<Transaction>
     * @throws SQLException
     */
	public List<Transaction> getTransactions(int accountNumber, LocalDate firstDayOfMonth, LocalDate lastDayOfMonth) throws SQLException{
        AccessDatabase db = new AccessDatabase();
        db.connectToDB(); // opens connection to DB
        List<Transaction> transactionList = db.getAccountTransactions("");
        db.close();
		return transactionList;
	}

}
