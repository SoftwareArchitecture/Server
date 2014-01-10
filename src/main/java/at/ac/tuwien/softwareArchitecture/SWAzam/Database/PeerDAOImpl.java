package at.ac.tuwien.softwareArchitecture.SWAzam.Database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import at.ac.tuwien.softwareArchitecture.SWAzam.model.Account;
import at.ac.tuwien.softwareArchitecture.SWAzam.model.Peer;

public class PeerDAOImpl implements PeerDAO {

	private DBAccess db = DBAccess.getDbCon();
	
	@Override
	public boolean save(Peer peer) {
		
		return false;
	}

	@Override
	public boolean update(Peer peer) {

		return false;
	}

	@Override
	public Peer findByAccountNumber(int peerId) {
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
			}
			if(count == 1) {
				System.out.println("Account Found!");
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
			System.out.println("Delete Account Successfull!");
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
				listAccounts.add(peer);
			}
		} catch(Exception exc) {
			exc.printStackTrace();	
		}
		return listAccounts;
	}

}
