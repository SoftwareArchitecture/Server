package at.ac.tuwien.softwareArchitecture.SWAzam.Database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import at.ac.tuwien.softwareArchitecture.SWAzam.model.Peer;

public class PeerDAOImpl implements PeerDAO {

	private DBAccess db = DBAccess.getDbCon();
	
	@Override
	public Peer save(Peer peer) {
		String sqlQuery = "INSERT INTO Peer (`Issuperpeer`,`Superpeerid`,`name`,`port`, `ip`, `accountid`, `active`) VALUES (?,?,?,?,?,?,?)";
		try {
			PreparedStatement insertQuery = db.conn.prepareStatement(sqlQuery, Statement.RETURN_GENERATED_KEYS);
			if(peer.isIsSuperPeer()) {
				insertQuery.setInt(1, 1);
			} else {
				insertQuery.setInt(1, 0);
			}
			insertQuery.setInt(2, peer.getSuperpeerid());
			insertQuery.setString(3, peer.getName());
			insertQuery.setInt(4, peer.getPort());
			insertQuery.setString(5, peer.getPeerIP());
			insertQuery.setInt(6, peer.getAccountid());
			insertQuery.setBoolean(7, peer.isActive());
			
			insertQuery.executeUpdate();
			
			ResultSet rs = insertQuery.getGeneratedKeys();
			int last_inserted_id = 0;
            if(rs.next())
            {
                last_inserted_id = rs.getInt(1);
            }
            rs.close();
            
            Peer insertedPeer = findByPeerNumber(last_inserted_id);
            System.out.println("Last insertet peer: " + insertedPeer.getId());
			System.out.println("Insert Peer Successfull!");
			return insertedPeer;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}

	@Override
	public boolean update(Peer peer) {
		int paramCount = 1;
		
		Peer foundpeer = findByPeerNumber(peer.getId());
		
		String sqlQuery = "UPDATE Peer SET active = ? ";
	try {
		
		if (peer.getName() != foundpeer.getName()) {
			sqlQuery += " , name = ?";
		}
		
		if(peer.getPeerIP() != foundpeer.getPeerIP()) {
			sqlQuery += ", ip = ?";
		}
		
		if(peer.getSuperpeerid() != foundpeer.getSuperpeerid()) {
			sqlQuery += " , Superpeerid = ?";
		}
		
		if(peer.getPort() != foundpeer.getPort()) {
			sqlQuery += ", port = ?";
		}
		
		if(peer.getAccountid() != foundpeer.getAccountid()) {
			sqlQuery += ", accountid = ?";
		}

		
		sqlQuery += " WHERE id = ?";
		
		System.out.println("Update SQL :" + sqlQuery);
		PreparedStatement insertQuery = db.conn.prepareStatement(sqlQuery);
		// Insering Parameters
		insertQuery.setBoolean(paramCount++, peer.isActive());
		
		if (peer.getName() != foundpeer.getName()) {
			insertQuery.setString(paramCount++, peer.getName());
		}
		
		if(peer.getPeerIP() != foundpeer.getPeerIP()) {
			insertQuery.setString(paramCount++, peer.getPeerIP());
		}
		
		if(peer.getSuperpeerid() != foundpeer.getSuperpeerid()) {
			insertQuery.setInt(paramCount++, peer.getSuperpeerid());
		}
		
		if(peer.getPort() != foundpeer.getPort()) {
			insertQuery.setInt(paramCount++, peer.getPort());
		}
		
		if(peer.getAccountid() != foundpeer.getAccountid()) {
			insertQuery.setInt(paramCount++, peer.getAccountid());
		}
		
		insertQuery.setInt(paramCount++, peer.getId());
		
		insertQuery.executeUpdate();
		System.out.println("Update Peer Successfull!");
		return true;
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	return false;
	}

	@Override
	public Peer findByPeerNumber(int peerId) {
		String sqlQuery = "SELECT * FROM Peer WHERE (id = ?);";
		System.out.println(sqlQuery + "," + peerId);
		Peer peer = null;
		
		try {
			PreparedStatement selectQuery = db.conn.prepareStatement(sqlQuery);
			selectQuery.setInt(1, peerId);
			
			ResultSet rs = selectQuery.executeQuery();
			int count = 0;
			while(rs.next()) {
				count++;
				peer = new Peer();
				peer.setId(rs.getInt("id"));
				peer.setIsSuperPeer(rs.getBoolean("issuperpeer"));
				peer.setSuperpeerid(rs.getInt("Superpeerid"));
				peer.setName(rs.getString("name"));
				peer.setPort(rs.getInt("port"));
				peer.setPeerIP(rs.getString("ip"));
				peer.setAccountid(rs.getInt("accountid"));
				peer.setActive(rs.getBoolean("active"));
			}
			if(count == 1) {
				System.out.println("Peer Found!");
				return peer;
			}

		} catch(Exception exc) {
			exc.printStackTrace();	
		}
		
		return null;
	}

	@Override
	public boolean delete(Peer peer) {
		String sqlQuery = "DELETE FROM Peer WHERE id = ?";
		try {
			PreparedStatement deleteQuery = db.conn.prepareStatement(sqlQuery);
			deleteQuery.setInt(1, peer.getId());
			deleteQuery.execute();
			System.out.println("Delete Peer Successfull!");
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public List<Peer> getPeer() {
		String sqlQuery = "SELECT * FROM Peer";
		List<Peer> listAccounts = new ArrayList<Peer>();
		try {
			PreparedStatement selectQuery = db.conn.prepareStatement(sqlQuery);
			ResultSet rs = selectQuery.executeQuery();
			Peer peer = null;
			while(rs.next()) {
				peer = new Peer();
				peer.setId(rs.getInt("id"));
				peer.setIsSuperPeer(rs.getBoolean("issuperpeer"));
				peer.setSuperpeerid(rs.getInt("Superpeerid"));
				peer.setName(rs.getString("name"));
				peer.setPort(rs.getInt("port"));
				peer.setPeerIP(rs.getString("ip"));
				peer.setAccountid(rs.getInt("accountid"));
				peer.setActive(rs.getBoolean("active"));
				listAccounts.add(peer);
			}
		} catch(Exception exc) {
			exc.printStackTrace();	
		}
		return listAccounts;
	}

	@Override
	public List<Peer> getSuperPeers() {
		String sqlQuery = "SELECT * FROM Peer WHERE (Issuperpeer = 1 AND active = 1)";
		List<Peer> listAccounts = new ArrayList<Peer>();
		try {
			PreparedStatement selectQuery = db.conn.prepareStatement(sqlQuery);
			ResultSet rs = selectQuery.executeQuery();
			Peer peer = null;
			while(rs.next()) {
				peer = new Peer();
				peer.setId(rs.getInt("id"));
				peer.setIsSuperPeer(rs.getBoolean("issuperpeer"));
				peer.setSuperpeerid(rs.getInt("Superpeerid"));
				peer.setName(rs.getString("name"));
				peer.setPort(rs.getInt("port"));
				peer.setPeerIP(rs.getString("ip"));
				peer.setAccountid(rs.getInt("accountid"));
				peer.setActive(rs.getBoolean("active"));
				listAccounts.add(peer);
			}
		} catch(Exception exc) {
			exc.printStackTrace();	
		}
		return listAccounts;
	}

	@Override
	public List<Peer> getPeersOfSuperPeer(int superpeerid) {
		String sqlQuery = "SELECT * FROM Peer WHERE Superpeerid = ?";
		List<Peer> listAccounts = new ArrayList<Peer>();
		try {
			PreparedStatement selectQuery = db.conn.prepareStatement(sqlQuery);
			selectQuery.setInt(1, superpeerid);
			ResultSet rs = selectQuery.executeQuery();
			Peer peer = null;
			while(rs.next()) {
				peer = new Peer();
				peer.setId(rs.getInt("id"));
				peer.setIsSuperPeer(rs.getBoolean("issuperpeer"));
				peer.setSuperpeerid(rs.getInt("Superpeerid"));
				peer.setName(rs.getString("name"));
				peer.setPort(rs.getInt("port"));
				peer.setPeerIP(rs.getString("ip"));
				peer.setAccountid(rs.getInt("accountid"));
				peer.setActive(rs.getBoolean("active"));
				listAccounts.add(peer);
			}
		} catch(Exception exc) {
			exc.printStackTrace();	
		}
		return listAccounts;
	}

	@Override
	public Peer getPeerByAccountId(int Accountid) {
		String sqlQuery = "SELECT * FROM Peer WHERE (accountid = ?);";
		System.out.println(sqlQuery + "," + Accountid);
		Peer peer = null;
		
		try {
			PreparedStatement selectQuery = db.conn.prepareStatement(sqlQuery);
			selectQuery.setInt(1, Accountid);
			
			ResultSet rs = selectQuery.executeQuery();
			int count = 0;
			while(rs.next()) {
				count++;
				peer = new Peer();
				peer.setId(rs.getInt("id"));
				peer.setIsSuperPeer(rs.getBoolean("issuperpeer"));
				peer.setSuperpeerid(rs.getInt("Superpeerid"));
				peer.setName(rs.getString("name"));
				peer.setPort(rs.getInt("port"));
				peer.setPeerIP(rs.getString("ip"));
				peer.setAccountid(rs.getInt("accountid"));
				peer.setActive(rs.getBoolean("active"));
			}
			if(count == 1) {
				System.out.println("Peer Found!");
				return peer;
			}

		} catch(Exception exc) {
			exc.printStackTrace();	
		}
		
		return null;
	}

}
