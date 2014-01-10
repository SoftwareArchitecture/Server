package at.ac.tuwien.softwareArchitecture.SWAzam;

import java.util.Date;

import at.ac.tuwien.softwareArchitecture.SWAzam.Database.AccountDAO;
import at.ac.tuwien.softwareArchitecture.SWAzam.Database.DAOFactory;
import at.ac.tuwien.softwareArchitecture.SWAzam.Database.HistoryDAO;
import at.ac.tuwien.softwareArchitecture.SWAzam.model.Account;
import at.ac.tuwien.softwareArchitecture.SWAzam.model.History;
import at.ac.tuwien.softwarearchitecture.swazam.common.infos.FingerprintSearchRequest;
import at.ac.tuwien.softwarearchitecture.swazam.common.infos.Helper;

public class SearchManagement {

	private static AccountDAO accountdao = DAOFactory.createAccount();
	private static HistoryDAO historydao = DAOFactory.createHistory();
	public static String search(FingerprintSearchRequest Fingerprint) {		
		// 1. Check Username Pass
		Account foundAcc = accountdao.findByUsernamePassword(Fingerprint.getClientInfo().getUsername(), Fingerprint.getClientInfo().getPassword());
		
		// 2. Save History and generate a session
		if(foundAcc != null) {
			// Accound Found
			History newHistory = new History();
			newHistory.setAccountid(foundAcc.getId());
			newHistory.setRequesttype(0);
			newHistory.setSessionkey(Helper.generateSession());
			newHistory.setSessiondate(new Date());
			newHistory.setRequestMessage("Search Fingerprint with accountid: " + foundAcc.getId());
			historydao.save(newHistory);
			
			// 3. Send it to Peer
			
		}
		
		
		
		return "";
	}
}
