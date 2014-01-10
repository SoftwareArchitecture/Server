package at.ac.tuwien.softwareArchitecture.SWAzam.Database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import at.ac.tuwien.softwareArchitecture.SWAzam.model.History;

public class HistoryDAOImpl implements HistoryDAO {

	private DBAccess db = DBAccess.getDbCon();
	
	@Override
	public boolean save(History history) {
		String sqlQuery = "INSERT INTO History (`accountid`,`reqtype`,`reqmessage`) VALUES (?,?,?)";
		try {
			PreparedStatement insertQuery = db.conn.prepareStatement(sqlQuery);
			insertQuery.setInt(1, history.getAccountid());
			insertQuery.setInt(2, history.getRequesttype());
			insertQuery.setString(3, history.getRequestMessage());
			
			insertQuery.executeUpdate();
			System.out.println("Insert History Successfull!");
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return false;
	}

	@Override
	public boolean update(History history) {
		int paramCount = 1;
		boolean comma = false;
		String sqlQuery = "UPDATE History SET ";
	try {
		
		if(history.getRequestMessage() != null) {
			if(!comma) {
				comma = true;
				sqlQuery += " reqmessage = ?";
			} else {
				sqlQuery += ", reqmessage = ?";
			}
		}
		
		if(history.getSessionkey() != null) {
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
		
		if(history.getRequestMessage() != null) {
			insertQuery.setString(paramCount++, history.getRequestMessage());
		}
		
		if(history.getSessionkey() != null) {
			insertQuery.setString(paramCount++, history.getSessionkey());
			java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			
			Calendar cal = Calendar.getInstance();
			String currentTime = sdf.format(cal.getTime());
			insertQuery.setString(paramCount++, currentTime);
		}
		insertQuery.setInt(paramCount++, history.getId());
		
		insertQuery.executeUpdate();
		System.out.println("Update History Successfull!");
		return true;
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	return false;
	}

	@Override
	public History findByAccountNumber(int historyid) {
		String sqlQuery = "SELECT * FROM History WHERE (id = ?);";
		System.out.println(sqlQuery + "," + historyid);
		History history = null;
		
		try {
			PreparedStatement selectQuery = db.conn.prepareStatement(sqlQuery);
			selectQuery.setInt(1, historyid);
			
			ResultSet rs = selectQuery.executeQuery();
			int count = 0;
			while(rs.next()) {
				count++;
				history = new History();
				history.setId(rs.getInt("id"));
				history.setAccountid(rs.getInt("accountid"));
				history.setRequesttype(rs.getInt("reqtype"));
				history.setRequestMessage(rs.getString("reqmessage"));
				history.setSessionkey(rs.getString("sessionkey"));
				history.setSessiondate(rs.getDate("sessiondate"));
			}
			if(count == 1) {
				System.out.println("History Found!");
				return history;
			}

		} catch(Exception exc) {
			exc.printStackTrace();	
		}
		
		return null;
	}

	@Override
	public boolean delete(History history) {
		String sqlQuery = "DELETE FROM History WHERE id = ?";
		try {
			PreparedStatement deleteQuery = db.conn.prepareStatement(sqlQuery);
			deleteQuery.setInt(1, history.getId());
			deleteQuery.execute();
			System.out.println("Delete History Successfull!");
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public List<History> getHistories() {
		String sqlQuery = "SELECT * FROM Account";
		List<History> listHistories = new ArrayList<History>();
		try {
			PreparedStatement selectQuery = db.conn.prepareStatement(sqlQuery);
			ResultSet rs = selectQuery.executeQuery();
			History history = null;
			while(rs.next()) {
				history = new History();
				history.setId(rs.getInt("id"));
				history.setAccountid(rs.getInt("accountid"));
				history.setRequesttype(rs.getInt("reqtype"));
				history.setRequestMessage(rs.getString("reqmessage"));
				history.setSessionkey(rs.getString("sessionkey"));
				history.setSessiondate(rs.getDate("sessiondate"));
				listHistories.add(history);
			}
			rs.close();
		} catch(Exception exc) {
			exc.printStackTrace();	
		}
		return listHistories;
	}

}
