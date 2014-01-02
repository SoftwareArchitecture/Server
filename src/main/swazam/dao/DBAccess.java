package src.main.swazam.dao;

import java.sql.Connection;

public class DBAccess {
	private Connection connect = null;

	public void Connection Connect() {
		Class.forName("com.mysql.jdbc.Driver");
		// Setup the connection with the DB
		connect = DriverManager.getConnection("jdbc:mysql://localhost/feedback?user=sqluser&password=sqluserpw");
		return connect;
	}
}
