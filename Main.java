/**
 * Banking System - Homework 3
 * @author Nicholas Taylor
 */

// Import Classes from other Packages
import dao.*;
import accounts.*;
import userInfo.*;

import java.time.LocalDate;

public class Main {

	public static void main(String[] args) throws Exception {

		Address address = new Address();
		User user = new User("Nicholas", "Adam", "Taylor", LocalDate.of(1999, 9, 8), "T460630031699", "System Analyst", address);
		Account account = AccountFactory.getAccount(AccountType.PERSONAL_CHECKING, user, true);

		// Records User to db
		UserDAO userDAO = new UserDAO();
		userDAO.recordUser(user);

		// Records Account to db
		AccountDAO accountDAO = new AccountDAO();
		accountDAO.recordAccount(account);

		// Prepares to interact with db and accounts
		TransactionDAO transactionDAO = new TransactionDAO();
		AccountActions accountActions = new AccountActions(accountDAO, transactionDAO);

		// Tests deposit
		accountActions.deposit(account, "50.00");
		System.out.println(account.getAccountBalance());

		// Tests withdraw
		accountActions.withdraw(account, "75.00");
		System.out.println(account.getAccountBalance());

		// Tests charging accounts for monthly fees
		MonthlyFeeService monthlyFeeService = new MonthlyFeeService(transactionDAO, accountDAO);
		monthlyFeeService.applyMonthlyFee();

		// Tests checking account after charges
		account = accountDAO.getAccount("0");
		System.out.println(account.getAccountBalance());

		// Makes savings account for user
		account = AccountFactory.getAccount(AccountType.PERSONAL_SAVINGS, user, true);
		accountDAO.recordAccount(account);
		accountActions.deposit(account, "50.00");

		// Displays Results
		account = accountDAO.getAccount("1");
		System.out.println(account.getAccountBalance());
		
	}

}
