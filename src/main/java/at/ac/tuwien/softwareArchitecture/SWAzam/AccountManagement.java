package at.ac.tuwien.softwareArchitecture.SWAzam;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import at.ac.tuwien.softwareArchitecture.SWAzam.Database.AccountDAO;
import at.ac.tuwien.softwareArchitecture.SWAzam.Database.DAOFactory;
import at.ac.tuwien.softwarearchitecture.swazam.common.infos.Helper;
import at.ac.tuwien.softwareArchitecture.SWAzam.model.Account;

public class AccountManagement {

	private static AccountDAO accountdao = DAOFactory.createAccount();
	
	public static List<Account> getAllAccounts() {
		List<Account> retList = accountdao.getAccounts();
		System.out.println("Accounts found " + retList.size());
		return retList;
	}
	
	public static boolean insertAccount(String Username, String Password, String Firstname, String Lastname, String SessionKey) {
		Account insAccount = new Account();
		insAccount.setFirstname(Firstname);
		insAccount.setLastname(Lastname);
		insAccount.setUsername(Username);
		insAccount.setPassword(Password);
		
		if (isLoggedin(insAccount.getId(), SessionKey)) {
			return accountdao.save(insAccount);
		}
		else {
			return false;
		}
	}
	
	public static boolean deleteAccount(int id, String SessionKey) {
		Account delAccount = new Account();
		delAccount.setId(id);
		
		if (isLoggedin(delAccount.getId(), SessionKey)) {
			return accountdao.delete(delAccount);
		}
		else {
			return false;
		}
	}
	
	private static boolean isLoggedin(int id, String sessionkey) {
		Account logAccount = accountdao.findByAccountNumber(id);
		 
		Date d1 = null;
		Date d2 = null;
		Calendar cal = Calendar.getInstance();
		
		try {
			d1 = logAccount.getSessiondate();
			d2 = cal.getTime();
			
			System.out.println("Account Session Date: " + d1.getTime());
			System.out.println("Time Now: " + d2.getTime());
			
			long diff = d2.getTime() - d1.getTime();
			long diffMinutes = diff / (60 * 1000) % 60;
			
			if(logAccount.getSessionkey() == sessionkey && diffMinutes > 20) {
				System.out.println("Session Timeout!");
				return false;
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return false;
	}
	
	public static boolean updateAccount(int id, String Username, String Password, String Firstname, String Lastname, String SessionKey) {
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
		
		if (isLoggedin(updAccount.getId(), SessionKey)) { 
			return accountdao.update(updAccount);
		} else {
			return false;
		}
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
