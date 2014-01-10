package at.ac.tuwien.softwareArchitecture.SWAzam.Database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;



import at.ac.tuwien.softwareArchitecture.SWAzam.model.Account;

public class AccountDAOImpl implements AccountDAO {
	
	private DBAccess db = DBAccess.getDbCon();

	@Override
	public boolean save(Account account) {
		
		String sqlQuery = "INSERT INTO Account (`username`,`password`,`firstname`,`lastname`) VALUES (?,?,?,?)";
		try {
			PreparedStatement insertQuery = db.conn.prepareStatement(sqlQuery);
			insertQuery.setString(1, account.getUsername());
			insertQuery.setString(2, account.getPassword());
			insertQuery.setString(3, account.getFirstname());
			insertQuery.setString(4, account.getLastname());
			
			insertQuery.executeUpdate();
			System.out.println("Insert Account Successfull!");
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return false;
	}

	@Override
	public boolean update(Account account) {
		int paramCount = 1;
		boolean comma = false;
		String sqlQuery = "UPDATE Account SET ";
	try {
		
		if (account.getFirstname() != null) {
			if(!comma) {
				comma = true;
				sqlQuery += " firstname = ?";
			} else {
				sqlQuery += " , firstname = ?";
			}
		}
		
		if(account.getLastname() != null) {
			if(!comma) {
				comma = true;
				sqlQuery += " lastname = ?";
			} else {
				sqlQuery += ", lastname = ?";
			}
		}
		
		if(account.getPassword() != null) {
			if(!comma) {
				comma = true;
				sqlQuery += " password = ?";
			} else {
				sqlQuery += " , password = ?";
			}
				
		}
		
		if(account.getSessionkey() != null) {
			if(!comma) {
				comma = true;
				sqlQuery += " sessionkey = ?, sessiondate = ?";
			} else {
				sqlQuery += ", sessionkey = ?, sessiondate = ?";
			}
		}
		
		sqlQuery += " WHERE id = ?";
		
		PreparedStatement insertQuery = db.conn.prepareStatement(sqlQuery);
		// Insering Parameters
		//insertQuery.setInt(paramCount++, account.getCoin());
		
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
		System.out.println("Update Account Successfull!");
		return true;
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	return false;
	}

	@Override
	public Account findByAccountNumber(int accountNumber) {
		String sqlQuery = "SELECT * FROM Account WHERE (id = ?);";
		System.out.println(sqlQuery + "," + accountNumber);
		Account account = null;
		
		try {
			PreparedStatement selectQuery = db.conn.prepareStatement(sqlQuery);
			selectQuery.setInt(1, accountNumber);
			
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
				account.setSessiondate(rs.getDate("sessiondate"));
				account.setSessionkey(rs.getString("sessionkey"));
				account.setCoin(rs.getInt("coin"));
			}
			if(count == 1) {
				System.out.println("Account Found!");
				return account;
			}

		} catch(Exception exc) {
			exc.printStackTrace();	
		}
		
		return null;
	}

	@Override
	public boolean delete(Account account) {
		String sqlQuery = "DELETE FROM Account WHERE id = ?";
		try {
			PreparedStatement deleteQuery = db.conn.prepareStatement(sqlQuery);
			deleteQuery.setInt(1, account.getId());
			deleteQuery.execute();
			System.out.println("Delete Account Successfull!");
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
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
				account.setSessiondate(rs.getDate("sessiondate"));
				account.setSessionkey(rs.getString("sessionkey"));
				account.setCoin(rs.getInt("coin"));
			}
			if(count == 1) {
				System.out.println("Account Found!");
				return account;
			}

		} catch(Exception exc) {
			exc.printStackTrace();	
		}
		
		return null;
	}

	@Override
	public void addCoin(int accountid, int amount) {
		Account account = findByAccountNumber(accountid);
		account.setCoin(account.getCoin() + amount);
		update(account);
	}

	@Override
	public Account findBySession(String Session) {
		String sqlQuery = "SELECT * FROM Account WHERE (sessionkey = ?);";
		System.out.println(sqlQuery + "," + Session);
		Account account = null;
		
		try {
			PreparedStatement selectQuery = db.conn.prepareStatement(sqlQuery);
			selectQuery.setString(1, Session);
			
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
				account.setSessiondate(rs.getDate("sessiondate"));
				account.setSessionkey(rs.getString("sessionkey"));
				account.setCoin(rs.getInt("coin"));
			}
			if(count == 1) {
				System.out.println("Account with session Found!");
				return account;
			}

		} catch(Exception exc) {
			exc.printStackTrace();	
		}
		
		return null;
	}

}
