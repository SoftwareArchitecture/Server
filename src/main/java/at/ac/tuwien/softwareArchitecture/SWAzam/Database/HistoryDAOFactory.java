package at.ac.tuwien.softwareArchitecture.SWAzam.Database;

public class HistoryDAOFactory {
	public static HistoryDAO create() {
		return new HistoryDAOImpl();
	}
}
