package at.ac.tuwien.softwareArchitecture.SWAzam;

import java.util.List;

import at.ac.tuwien.softwareArchitecture.SWAzam.Database.AccountDAO;
import at.ac.tuwien.softwareArchitecture.SWAzam.Database.AccountDAOFactory;
import at.ac.tuwien.softwareArchitecture.SWAzam.Database.Helper;
import at.ac.tuwien.softwareArchitecture.SWAzam.model.Account;

public class AccountManagement {

	private static AccountDAO accountdao = AccountDAOFactory.create();
	
	public static List<Account> getAllAccounts() {
		List<Account> retList = accountdao.getAccounts();
		System.out.println("Accounts found " + retList.size());
		return retList;
	}
	
	public static boolean insertAccount(String Username, String Password, String Firstname, String Lastname) {
		Account insAccount = new Account();
		insAccount.setFirstname(Firstname);
		insAccount.setLastname(Lastname);
		insAccount.setUsername(Username);
		insAccount.setPassword(Password);
		
		return accountdao.save(insAccount);
	}
	
	public static boolean deleteAccount(int id) {
		Account delAccount = new Account();
		delAccount.setId(id);
		return accountdao.delete(delAccount);
	}
	
	public static boolean updateAccount(int id, String Username, String Password, String Firstname, String Lastname) {
		Account updAccount = new Account();
		updAccount.setId(id);
		if (Username != null) {
			updAccount.setUsername(Username);
		}
		
		if (Password != null) {
			updAccount.setPassword(Password);
		}
		
		if (Firstname != null) {
			updAccount.setFirstname(Firstname);
		}
		
		if(Lastname != null) {
			updAccount.setLastname(Lastname);
		}
		return accountdao.update(updAccount);
	}
	
	public static Account login(String Username, String Password) {
		Account loginAccount = accountdao.findByUsernamePassword(Username, Password);
		
		if(loginAccount != null) {
			String session = Helper.generateSession();
			loginAccount.setSessionkey(session);
			accountdao.update(loginAccount);
			System.out.println("Accounts found " + loginAccount.getId());
		} else {
			System.out.println("Username/Pass not found!");
		}
		return loginAccount;
	}
}
