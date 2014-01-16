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
	
	public void updatePeer(PeerInfo peerinfo) {
		// Update the superpeer statuses
		
	}
	
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
				if(foundPeer.isIsSuperPeer()) {
					Peer superPeer = peerdao.findByPeerNumber(foundPeer.getSuperpeerid());
					peerinfo.setSuperPeerID(superPeer.getId());
					peerinfo.setSuperPeerPort(superPeer.getPort());
					peerinfo.setSuperPeerIp(superPeer.getPeerIP());
				}
				peerinfo.setPeerID(foundPeer.getId());
				peerinfo.setIp(foundPeer.getPeerIP());
				peerinfo.setPort(foundPeer.getPort());
				return peerinfo;
			}

			
			Peer peer = new Peer();
			peer.setAccountid(peerAccount.getId());
			peer.setActive(true);
			peer.setPeerIP(peerinfo.getIp());
			peer.setPort(peerinfo.getPort());
			
			Peer superPeer = getAppropriateSuperPeer();
			if (superPeer == null) {
				System.out.println("Super peer not found, it should be himself");
				peer.setIsSuperPeer(true);
				//peer.setSuperpeerid(peer.getId());
			} else {
				peer.setSuperpeerid(superPeer.getId());
				//peer.setPeerIP(superPeer.getPeerIP());
				//peer.setPort(superPeer.getPort());
			}
				
			peer = peerdao.save(peer);
			System.out.println("peer inserted!");
			
			System.out.println("Update super peer!");
			if(peer.isIsSuperPeer()) {
				peer.setSuperpeerid(peer.getId());
			}
			
			peerdao.update(peer);
			System.out.println("Updated super peer!");
			if(!peer.isIsSuperPeer()) {
				peerinfo.setSuperPeerID(superPeer.getId());
				peerinfo.setSuperPeerPort(superPeer.getPort());
				peerinfo.setSuperPeerIp(superPeer.getPeerIP());
			} else {
				peerinfo.setSuperPeerID(peer.getId());
				peerinfo.setSuperPeerPort(peer.getPort());
				peerinfo.setSuperPeerIp(peer.getPeerIP());
			}
			peerinfo.setPeerID(peer.getId());
			peerinfo.setIp(peer.getPeerIP());
			peerinfo.setPort(peer.getPort());
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
