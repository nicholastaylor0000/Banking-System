import java.math.BigDecimal;
import java.util.ArrayList;

import accounts.Account;
import accounts.CheckingAccount;
import accounts.SavingsAccount;

/**
 * Main Class for BankAccount (Homework1)
 * @author Nicholas Taylor
 */
public class Main {

	public static void main(String[] args) {

		// Make both a Savings Account and Checking Account
		SavingsAccount savingsAccount = new SavingsAccount(new BigDecimal("100.00"), "Taylor");
		CheckingAccount checkingAccount = new CheckingAccount(new BigDecimal("100.00"), "Taylor");
		
		// Make an ArrayList of these accounts
		ArrayList<Account> accounts = new ArrayList<Account>();
		accounts.add(savingsAccount);
		accounts.add(checkingAccount);
		
		// Test Deposit and Withdraw from Savings Account
		savingsAccount.deposit("5000.00");
		savingsAccount.withdraw("5000.00");
		System.out.print("SAVINGS: " + savingsAccount.getAccountBalance() + "\n");
		//savingsAccount.withdraw("100000.00"); Throws Illegal Exception so just commenting out
		System.out.print("CHECKING: " + checkingAccount.getAccountBalance() + "\n");
		checkingAccount.deposit("5000.00");
		System.out.print("CHECKING: " + checkingAccount.getAccountBalance() + "\n");
		checkingAccount.withdraw("5000.00");
		System.out.print("CHECKING: " + checkingAccount.getAccountBalance() + "\n");
		checkingAccount.withdraw("800.00");
		System.out.print("CHECKING: " + checkingAccount.getAccountBalance() + "\n");
		checkingAccount.withdraw("100000.00"); // Will print an error and not give any money
		System.out.print("CHECKING: " + checkingAccount.getAccountBalance() + "\n");
		checkingAccount.withdraw("100.00");
		System.out.print("CHECKING: " + checkingAccount.getAccountBalance() + "\n");
		
		System.out.println("Depositing 1200.00 to both accounts");
		for(Account account : accounts){ 
			account.deposit("1200.00"); 
			System.out.println(account.getAccountBalance()); 
		}
		
	} // End Main Method

} // End Main Class
