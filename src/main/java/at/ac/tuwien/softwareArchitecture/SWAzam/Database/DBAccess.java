package at.ac.tuwien.softwareArchitecture.SWAzam.Database;

import com.mysql.jdbc.Connection;
import java.sql.*;
import java.sql.DriverManager;

public class DBAccess {
	
	public Connection conn;
    private Statement statement;
    public static DBAccess db;
    
    private DBAccess() {
        String url= "jdbc:mysql://localhost:3306/";
        String dbName = "swazam";
        String driver = "com.mysql.jdbc.Driver";
        String userName = "root";
        String password = "";
        try {
            Class.forName(driver).newInstance();
            this.conn = (Connection)DriverManager.getConnection(url+dbName,userName,password);
        }
        catch (Exception sqle) {
            sqle.printStackTrace();
        }
    }
    /**
     *
     * @return MysqlConnect Database connection object
     */
    public static synchronized DBAccess getDbCon() {
        if ( db == null ) {
            db = new DBAccess();
        }
        return db;
 
    }
    /**
     *
     * @param query String The query to be executed
     * @return a ResultSet object containing the results or null if not available
     * @throws SQLException
     */
    public ResultSet query(String query) throws SQLException{
        statement = db.conn.createStatement();
        ResultSet res = statement.executeQuery(query);
        return res;
    }
    /**
     * @desc Method to insert data to a table
     * @param insertQuery String The Insert query
     * @return boolean
     * @throws SQLException
     */
    public int insert(String insertQuery) throws SQLException {
        statement = db.conn.createStatement();
        int result = statement.executeUpdate(insertQuery);
        return result;
 
    }
}
