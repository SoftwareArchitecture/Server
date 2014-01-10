package at.ac.tuwien.softwareArchitecture.SWAzam.Database;

import java.util.List;

import at.ac.tuwien.softwareArchitecture.SWAzam.model.Peer;

public interface PeerDAO {
	   public Peer save(Peer peer);
	   public boolean update(Peer peer);
	   public Peer findByPeerNumber(int peerId);
	   public boolean delete(Peer peer);
	   public List<Peer> getPeer();
	   public List<Peer> getSuperPeers();
	   public List<Peer> getPeersOfSuperPeer(int superpeerid);
	   public Peer getPeerByAccountId(int Accountid);
}
