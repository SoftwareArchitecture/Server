package src.main.swazam.dao;

public class AccountDAO {
	private Connection connection;

    public AccountDAO() {
        connection = DBAccess.getConnection();
    }
}
