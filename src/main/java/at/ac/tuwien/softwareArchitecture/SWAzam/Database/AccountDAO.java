package at.ac.tuwien.softwareArchitecture.SWAzam.Database;

import java.util.List;

import at.ac.tuwien.softwareArchitecture.SWAzam.model.Account;

public interface AccountDAO {
		   public boolean save(Account account);
		   public boolean update(Account account);
		   public Account findByAccountNumber(int accountNumber);
		   public Account findByUsernamePassword(String Username, String Password);
		   public boolean delete(Account account);
		   public List<Account> getAccounts();
}
