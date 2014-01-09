package at.ac.tuwien.softwareArchitecture.SWAzam.Database;

import java.util.UUID;

/*
 * Helper Class for usefull methods
 */
public class Helper {

	public static String generateSession() {
		// Generate SessionKey
		UUID uniqueKey = UUID.randomUUID();
		String sessionKey = String.valueOf(uniqueKey);
		return sessionKey;
					
	}
}
