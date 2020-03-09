package accounts;

import userInfo.User;

/**
 * Creates account for user depending on situation
 */
public final class AccountFactory {

	/**
	 * Makes an account suitable for user
	 * @param accountType
	 * @param user
	 * @param ableToOverDraft
	 * @param authorizedUsers
	 * @return account
	 * @throws Exception
	 */
	public static Account getAccount(AccountType accountType, User user, boolean ableToOverDraft,
			User... authorizedUsers) throws Exception {
		Account account = null;
		switch (accountType) {
		case STUDENT_CHECKING:
			if (user.getAge() >= 17 && user.getAge() <= 23) {
				account = new Account(user.getLicenseNumber(), accountType, ableToOverDraft);
			} else if (user.getAge() >= 12 && user.getAge() < 17) {
				boolean flag = false;
				for (User authorizedUser : authorizedUsers) {
					if (authorizedUser.getAge() >= 18) {
						account = new Account(user.getLicenseNumber(), accountType, authorizedUser, ableToOverDraft);
						flag = true;
						break;
					}
				}
				if (flag == false) {
					throw new IllegalStateException("Cannot create " + accountType + " for " + user.getLicenseNumber()
							+ ". No eligible authorized user.");
				}
			} else {
				throw new IllegalStateException("Cannot create " + accountType + " for " + user.getLicenseNumber()
						+ ". Age is not between 17 and 23: " + user.getAge());
			}
			break;
		case STUDENT_SAVINGS:
			if (user.getAge() >= 17 && user.getAge() <= 23) {
				account = new Account(user.getLicenseNumber(), accountType, ableToOverDraft);
			} else if (user.getAge() >= 12) {
				boolean flag = false;
				for (User authorizedUser : authorizedUsers) {
					if (authorizedUser.getAge() >= 18) {
						account = new Account(user.getLicenseNumber(), accountType, authorizedUser, ableToOverDraft);
						flag = true;
						break;
					}
				}
				if (flag == false) {
					throw new IllegalStateException("Cannot create " + accountType + " for " + user.getLicenseNumber()
							+ ". Age is not between 17 and 23: " + user.getAge() + ". No eligible authorized user.");
				}
			} else {
				throw new IllegalStateException("Cannot create " + accountType + " for " + user.getLicenseNumber()
						+ ". Age is not between 17 and 23: " + user.getAge());
			}
			break;
		case PERSONAL_CHECKING:
		case BUSINESS_CHECKING:
		case PERSONAL_SAVINGS:
		case BUSINESS_SAVINGS:
			account = new Account(user.getLicenseNumber(), accountType, ableToOverDraft);
			break;
		default:
			throw new IllegalStateException("Unable to create account with account type: " + accountType.toString());
		}
		return account;
	}

}
