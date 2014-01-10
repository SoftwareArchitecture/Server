package at.ac.tuwien.softwareArchitecture.SWAzam;

import java.util.List;

import at.ac.tuwien.softwareArchitecture.SWAzam.Database.AccountDAO;
import at.ac.tuwien.softwareArchitecture.SWAzam.Database.DAOFactory;
import at.ac.tuwien.softwareArchitecture.SWAzam.Database.HistoryDAO;
import at.ac.tuwien.softwareArchitecture.SWAzam.Database.PeerDAO;
import at.ac.tuwien.softwareArchitecture.SWAzam.model.Account;
import at.ac.tuwien.softwareArchitecture.SWAzam.model.History;
import at.ac.tuwien.softwareArchitecture.SWAzam.model.Peer;
import at.ac.tuwien.softwarearchitecture.swazam.common.infos.FingerprintSearchResponse;
import at.ac.tuwien.softwarearchitecture.swazam.common.infos.PeerInfo;

public class PeerManagement {
	private PeerDAO peerdao = DAOFactory.createPeer();
	private HistoryDAO historydao = DAOFactory.createHistory();
	private AccountDAO accountdao = DAOFactory.createAccount();
	
	public void searchResult(FingerprintSearchResponse Response) {
		// update History to finish and update music description if exist
		
		History resHistory = historydao.searchWithSession(Response.getClientInfo().getSessionKey());
		if (resHistory != null) {
			resHistory.setMusicdesc(Response.getFingerprint().getDescription());
			resHistory.setProcessstatus(2);
			historydao.update(resHistory);
			
			// Add coin to peer
			accountdao.addCoin(Response.getPeerInfo().getPeerID(), 10);
		}
	}
	
	public PeerInfo registerPeer(PeerInfo peerinfo) {
		// Check Username Pass
		Account peerAccount = accountdao.findByUsernamePassword(peerinfo.getUsername() , peerinfo.getPassword());
						
		// Insert Into DB
		if (peerAccount != null) {
			
			// Search IF Peer aleready exists
			Peer foundPeer = peerdao.getPeerByAccountId(peerAccount.getId());
			if(foundPeer != null) {
				// Peer already registered before
				peerinfo.setPeerID(foundPeer.getId());
				return peerinfo;
			}

			
			Peer peer = new Peer();
			peer.setAccountid(peerAccount.getId());
			peer.setActive(true);
			peer.setPeerIP(peerinfo.getIp());
			peer.setPort(peerinfo.getPort());
			
			Peer superPeer = getAppropriateSuperPeer();
			if (superPeer == null) {
				peer.setIsSuperPeer(true);
			}
			peer = peerdao.save(peer);
			peer.setSuperpeerid(peer.getId());
			peerdao.update(peer);
			peerinfo.setPeerID(peer.getId());
		}
		return peerinfo;
	}
	
	public Peer getAppropriateSuperPeer() {
		List<Peer> peerList = peerdao.getSuperPeers();
		if (peerList != null) {
			for (Peer peer : peerList) {
				if (peer.isActive()) {
					// Select Peers of super peer
					List<Peer> peersOfSuperPeer = peerdao.getPeersOfSuperPeer(peer.getId());
					if(peersOfSuperPeer.size() < 5) {
						return peer;
					}
				}
			}
		}
		return null;
	}
}
