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
	public History save(History history) {
		String sqlQuery = "INSERT INTO History (`accountid`,`reqtype`,`reqmessage`) VALUES (?,?,?)";
		try {
			PreparedStatement insertQuery = db.conn.prepareStatement(sqlQuery);
			insertQuery.setInt(1, history.getAccountid());
			insertQuery.setInt(2, history.getRequesttype());
			insertQuery.setString(3, history.getRequestMessage());
			
			insertQuery.executeUpdate();
			ResultSet rs = insertQuery.getGeneratedKeys();
			int last_inserted_id = 0;
            if(rs.next())
            {
                last_inserted_id = rs.getInt(1);
            }
            rs.close();
            
            History insertedHistory = findByHistoryNumber(last_inserted_id);
            
			System.out.println("Insert History Successfull!");
			return insertedHistory;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}

	@Override
	public boolean update(History history) {
		int paramCount = 1;
		boolean comma = false;
		History oldHistory = findByHistoryNumber(history.getId());
		String sqlQuery = "UPDATE History SET ";
	try {
		
		if(history.getRequestMessage() != oldHistory.getRequestMessage()) {
			if(!comma) {
				comma = true;
				sqlQuery += " reqmessage = ?";
			} else {
				sqlQuery += ", reqmessage = ?";
			}
		}
		
		if(history.getSessionkey() != oldHistory.getSessionkey()) {
			if(!comma) {
				comma = true;
				sqlQuery += " sessionkey = ?, sessiondate = ?";
			} else {
				sqlQuery += ", sessionkey = ?, sessiondate = ?";
			}
		}
		
		if(history.getMusicdesc() != oldHistory.getMusicdesc()) {
			if(!comma) {
				comma = true;
				sqlQuery += " musicdesc  = ?";
			} else {
				sqlQuery += ", musicdesc = ?";
			}
		}
		
		sqlQuery += " WHERE id = ?";
		
		PreparedStatement insertQuery = db.conn.prepareStatement(sqlQuery);
		// Insering Parameters
		
		if(history.getRequestMessage() != oldHistory.getRequestMessage()) {
			insertQuery.setString(paramCount++, history.getRequestMessage());
		}
		
		if(history.getSessionkey() != oldHistory.getSessionkey()) {
			insertQuery.setString(paramCount++, history.getSessionkey());
			java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			
			Calendar cal = Calendar.getInstance();
			String currentTime = sdf.format(cal.getTime());
			insertQuery.setString(paramCount++, currentTime);
		}
		
		if(history.getMusicdesc() != oldHistory.getMusicdesc()) {
			insertQuery.setString(paramCount++, history.getMusicdesc());
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
	public History findByHistoryNumber(int historyid) {
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
				history.setMusicdesc(rs.getString("musicdesc"));
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
				history.setMusicdesc(rs.getString("musicdesc"));
				listHistories.add(history);
			}
			rs.close();
		} catch(Exception exc) {
			exc.printStackTrace();	
		}
		return listHistories;
	}

	@Override
	public History searchWithSession(String Sessionkey) {
		String sqlQuery = "SELECT * FROM History WHERE (sessionkey = ?);";
		System.out.println(sqlQuery + "," + Sessionkey);
		History history = null;
		
		try {
			PreparedStatement selectQuery = db.conn.prepareStatement(sqlQuery);
			selectQuery.setString(1, Sessionkey);
			
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
				history.setMusicdesc(rs.getString("musicdesc"));
			}
			rs.close();
			if(count == 1) {
				System.out.println("History With Session Found!");
				return history;
			}

		} catch(Exception exc) {
			exc.printStackTrace();	
		}
		
		return null;
	}

	@Override
	public List<History> getHistoriesByAccountID(int accountid) {
		String sqlQuery = "SELECT * FROM History WHERE (accountid = ? OR peerid = ?);";
		System.out.println(sqlQuery + "," + accountid);
		List<History> histories = new ArrayList<History>();
		History history = null;
		
		try {
			PreparedStatement selectQuery = db.conn.prepareStatement(sqlQuery);
			selectQuery.setInt(1, accountid);
			selectQuery.setInt(2, accountid);
			
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
				history.setMusicdesc(rs.getString("musicdesc"));
				history.setPeerid(rs.getInt("peerid"));
				histories.add(history);
			}
			rs.close();
			System.out.println("Histories With accountid Found!");
			return histories;

		} catch(Exception exc) {
			exc.printStackTrace();	
		}
		
		return null;
	}

}
