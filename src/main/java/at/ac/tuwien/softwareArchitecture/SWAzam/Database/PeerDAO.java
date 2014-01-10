package at.ac.tuwien.softwareArchitecture.SWAzam.Database;

import java.util.List;

import at.ac.tuwien.softwareArchitecture.SWAzam.model.Peer;

public interface PeerDAO {
	   public boolean save(Peer peer);
	   public boolean update(Peer peer);
	   public Peer findByAccountNumber(int peerId);
	   public boolean delete(Peer peer);
	   public List<Peer> getPeer();
}