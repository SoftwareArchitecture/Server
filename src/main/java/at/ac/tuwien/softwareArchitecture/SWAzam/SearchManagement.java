package at.ac.tuwien.softwareArchitecture.SWAzam;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.StringWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import at.ac.tuwien.softwareArchitecture.SWAzam.Database.AccountDAO;
import at.ac.tuwien.softwareArchitecture.SWAzam.Database.DAOFactory;
import at.ac.tuwien.softwareArchitecture.SWAzam.Database.HistoryDAO;
import at.ac.tuwien.softwareArchitecture.SWAzam.Database.PeerDAO;
import at.ac.tuwien.softwareArchitecture.SWAzam.model.Account;
import at.ac.tuwien.softwareArchitecture.SWAzam.model.History;
import at.ac.tuwien.softwareArchitecture.SWAzam.model.Peer;
import at.ac.tuwien.softwarearchitecture.swazam.common.infos.FingerprintSearchRequest;
import at.ac.tuwien.softwarearchitecture.swazam.common.infos.Helper;

public class SearchManagement {

	private static AccountDAO accountdao = DAOFactory.createAccount();
	private static HistoryDAO historydao = DAOFactory.createHistory();
	private static PeerDAO peerdao = DAOFactory.createPeer();
	
	/*
	 * Searching history for an account id
	 */
	public static synchronized List<History> getHistory(int accountid) {
		return historydao.getHistoriesByAccountID(accountid);
	}
	
	/*
	 * Searching if the search is finished with the given session key
	 */
	public static synchronized String searchResult(String Sessionkey) {
		System.out.println("Sessionkey requested: " + Sessionkey);
		History history = historydao.searchWithSession(Sessionkey);
		if (history.getProcessstatus() == 2) {
			// Status = 2 search is finished and answered by peer and subtract 5 coins
			accountdao.addCoin(history.getAccountid(), -1);
			return history.getMusicdesc();
		} 
		else if (history.getProcessstatus() == 1) {
			return "In Progress!";
		}
		
		return "session not found!";
	}
	
	/*
	 * Perform a Search Request
	 */
	public static synchronized String search(FingerprintSearchRequest Fingerprint) {		
		// 1. Check Username Pass
		//System.out.println("Finger print: " + Fingerprint.getClientInfo().getClientID());
		Account foundAcc = accountdao.findByUsernamePassword(Fingerprint.getClientInfo().getUsername(), Fingerprint.getClientInfo().getPassword());
		History newHistory = null;
		// 2. Save History and generate a session
		if(foundAcc != null) {
			// Accound Found
			newHistory = new History();
			newHistory.setAccountid(foundAcc.getId());
			newHistory.setRequesttype(0);
			newHistory.setSessionkey(Helper.generateSession());
			newHistory.setRequestMessage("Search Fingerprint with accountid: " + foundAcc.getId());
			History addedHistory = historydao.save(newHistory);
			
			// 3. Send it to Peer
			// Search For Super Peer
			System.out.println("Trying to get random superpeer to send the request");
			List<Peer> foundSuperPeers = getRndSuperPeer();
			System.out.println("Super Peers " + foundSuperPeers.size() + " found!");
			boolean searchResult = false;
			if(foundSuperPeers != null) {
				// Send the SuperPeer Request
				for (Peer foundSuperPeer : foundSuperPeers) {
					Fingerprint.getClientInfo().setSessionKey(newHistory.getSessionkey());
					Fingerprint.getClientInfo().setClientID(foundAcc.getId());
					try {
					searchResult = sendPeerSearchRequest(foundSuperPeer, Fingerprint);
					} catch (Exception e) {
						// Deavtivate Superpeer
						Peer disablePeer = peerdao.findByPeerNumber(foundSuperPeer.getId());
						disablePeer.setActive(false);
						peerdao.update(disablePeer);
					}
				}
				
				//IN FUTURE -> if super peer down update is inactive
				
				// Update History to in progress
				if (searchResult) {
					addedHistory.setProcessstatus(1);
					historydao.update(addedHistory);
				}
			}
		}
		
		
		if(newHistory != null) {
			return newHistory.getSessionkey();
		}
		return "";
	}
	
	private static synchronized List<Peer> getRndSuperPeer() {
		return peerdao.getSuperPeers();
	}
	
	private static synchronized boolean sendPeerSearchRequest(Peer peer, FingerprintSearchRequest searchRequest) {
		
		try {
	
			JAXBContext context = JAXBContext.newInstance(FingerprintSearchRequest.class);
	
			StringWriter stringWriter = new StringWriter();
			Marshaller m = context.createMarshaller();
			m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			m.marshal(searchRequest, stringWriter);
		 
		 
		

			URL url = new URL(String.format("http://%s:%s/Peer/REST_API/search", peer.getPeerIP(), peer.getPort()));
			System.out.println("Trying to contact Peer with URL: " + url);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/xml");

			String input = stringWriter.getBuffer().toString();
			
			//System.out.println(input);

			OutputStream os = conn.getOutputStream();
			os.write(input.getBytes());
			os.flush();
			//System.out.println(os.toString());
			if (conn.getResponseCode() != HttpURLConnection.HTTP_NO_CONTENT) {
				System.out.println(conn.getResponseMessage());
				throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
			}

			BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

			String output;
			System.out.println("Output from Server .... \n");
			while ((output = br.readLine()) != null) {
				System.out.println(output);
			}

			conn.disconnect();

			return true;
		} catch (MalformedURLException e) {

			e.printStackTrace();

		} catch (IOException e) {

			e.printStackTrace();

		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
}
