package at.ac.tuwien.softwareArchitecture.SWAzam;

import java.util.List;
import java.util.UUID;

import at.ac.tuwien.softwareArchitecture.SWAzam.Database.AccountDAO;
import at.ac.tuwien.softwareArchitecture.SWAzam.Database.AccountDAOFactory;
import at.ac.tuwien.softwareArchitecture.SWAzam.model.Account;

public class AccountManagement {

	private static AccountDAO accountdao = AccountDAOFactory.create();
	
	public static List<Account> getAllAccounts() {
		List<Account> retList = accountdao.getAccounts();
		System.out.println("Accounts found " + retList.size());
		return retList;
	}
	
	public static Account login(String Username, String Password) {
		Account loginAccount = accountdao.findByUsernamePassword(Username, Password);
		
		if(loginAccount != null) {
			// Generate SessionKey
			UUID uniqueKey = UUID.randomUUID();
			String sessionKey = String.valueOf(uniqueKey);
			
			// Updating Account with SessionKey
			loginAccount.setSessionkey(sessionKey);
			accountdao.update(loginAccount);
			System.out.println("Accounts found " + loginAccount.getId());
		} else {
			System.out.println("Username/Pass not found!");
		}
		return loginAccount;
	}
}
