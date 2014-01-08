package at.ac.tuwien.softwareArchitecture.SWAzam.Database;

public class AccountDAOFactory {
	public static AccountDAO create() {
		return new AccountDAOImpl();
	}
}
