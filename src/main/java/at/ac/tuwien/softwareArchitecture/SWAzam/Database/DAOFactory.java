package at.ac.tuwien.softwareArchitecture.SWAzam.Database;

public class DAOFactory {
	public static AccountDAO createAccount() {
		return new AccountDAOImpl();
	}
	
	public static HistoryDAO createHistory() {
		return new HistoryDAOImpl();
	}
	
	public static PeerDAO createPeer() {
		return new PeerDAOImpl();
	}
	
}
