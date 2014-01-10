package at.ac.tuwien.softwareArchitecture.SWAzam;

import javax.sound.sampled.AudioInputStream;

import ac.at.tuwien.infosys.swa.audio.SubFingerprint;
import at.ac.tuwien.softwareArchitecture.SWAzam.Database.AccountDAO;
import at.ac.tuwien.softwareArchitecture.SWAzam.Database.AccountDAOFactory;
import at.ac.tuwien.softwareArchitecture.SWAzam.Database.HistoryDAO;
import at.ac.tuwien.softwareArchitecture.SWAzam.Database.HistoryDAOFactory;
import at.ac.tuwien.softwareArchitecture.SWAzam.model.Account;
import at.ac.tuwien.softwarearchitecture.swazam.common.infos.FingerprintSearchRequest;

public class SearchManagement {

	private static AccountDAO accountdao = AccountDAOFactory.create();
	private static HistoryDAO historydao = HistoryDAOFactory.create();
	public static String search(String Username, String Password, FingerprintSearchRequest Fingerprint) {
		// Create and save a request into DB
		Account foundAcc = accountdao.findByUsernamePassword(Username, Password);
		
		return "";
	}
}
