package at.ac.tuwien.softwareArchitecture.SWAzam.Database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;



import at.ac.tuwien.softwareArchitecture.SWAzam.model.Account;

public class AccountDAOImpl implements AccountDAO {
	
	private DBAccess db = DBAccess.getDbCon();

	@Override
	public boolean save(Account account) {
		
		String sqlQuery = "INSERT INTO Account ('username','password','firstname','lastname')"+
					"VALUES (?,?,?,?,?,?)";
		try {
			PreparedStatement insertQuery = db.conn.prepareStatement(sqlQuery);
			insertQuery.setString(1, account.getUsername());
			insertQuery.setString(2, account.getPassword());
			insertQuery.setString(3, account.getFirstname());
			insertQuery.setString(4, account.getLastname());
			
			insertQuery.executeUpdate();
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return false;
	}

	@Override
	public boolean update(Account account) {
		Boolean comma = false;
		int paramCount = 1;
		String sqlQuery = "UPDATE Account SET coin = ? ";
	try {
		
		if(account.getFirstname() != null) {
				sqlQuery += " , firstname = ?";
		}
		
		if(account.getLastname() != null) {
				sqlQuery += ", lastname = ?";
		}
		
		if(account.getPassword() != null) {
				sqlQuery += " , password = ?";
		}
		
		if(account.getSessionkey() != null) {
				sqlQuery += " , sessionkey = ?, sessiondate = ?";
		}
		sqlQuery += " WHERE id = ?";
		
		PreparedStatement insertQuery = db.conn.prepareStatement(sqlQuery);
		// Insering Parameters
		insertQuery.setInt(paramCount++, account.getCoin());
		
		if(account.getFirstname() != null) {
			insertQuery.setString(paramCount++, account.getFirstname());
		}
		
		if(account.getLastname() != null) {
			insertQuery.setString(paramCount++, account.getLastname());
		}
		
		if(account.getPassword() != null) {
			insertQuery.setString(paramCount++, account.getPassword());
		}
		
		if(account.getSessionkey() != null) {
			insertQuery.setString(paramCount++, account.getSessionkey());
			java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			
			Calendar cal = Calendar.getInstance();
			String currentTime = sdf.format(cal.getTime());
			insertQuery.setString(paramCount++, currentTime);
		}
		insertQuery.setInt(paramCount++, account.getId());
		
		insertQuery.executeUpdate();
		return true;
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	return false;
	}

	@Override
	public boolean findByAccountNumber(int accountNumber) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(Account account) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<Account> getAccounts() {
		String sqlQuery = "SELECT * FROM Account";
		List<Account> listAccounts = new ArrayList<Account>();
		try {
			PreparedStatement selectQuery = db.conn.prepareStatement(sqlQuery);
			ResultSet rs = selectQuery.executeQuery();
			Account account = null;
			while(rs.next()) {
				account = new Account();
				account.setId(rs.getInt("id"));
				account.setFirstname(rs.getString("firstname"));
				account.setLastname(rs.getString("lastname"));
				account.setUsername(rs.getString("username"));
				account.setPassword(rs.getString("password"));
				account.setCoin(rs.getInt("coin"));
				listAccounts.add(account);
			}
		} catch(Exception exc) {
			exc.printStackTrace();	
		}
		return listAccounts;
	}

	@Override
	public Account findByUsernamePassword(String Username, String Password) {
		String sqlQuery = "SELECT * FROM Account WHERE (username = ? AND password = ?);";
		System.out.println(sqlQuery + "," + Username + "," + Password);
		Account account = null;
		
		try {
			PreparedStatement selectQuery = db.conn.prepareStatement(sqlQuery);
			selectQuery.setString(1, Username);
			selectQuery.setString(2, Password);
			
			ResultSet rs = selectQuery.executeQuery();
			int count = 0;
			while(rs.next()) {
				count++;
				account = new Account();
				account.setId(rs.getInt("id"));
				account.setFirstname(rs.getString("firstname"));
				account.setLastname(rs.getString("lastname"));
				account.setUsername(rs.getString("username"));
				account.setPassword(rs.getString("password"));
				account.setCoin(rs.getInt("coin"));
			}
			if(count == 1) {
				return account;
			}

		} catch(Exception exc) {
			exc.printStackTrace();	
		}
		
		return null;
	}

}
