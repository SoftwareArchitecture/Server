package at.ac.tuwien.softwareArchitecture.SWAzam;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import at.ac.tuwien.softwareArchitecture.SWAzam.model.History;
import at.ac.tuwien.softwarearchitecture.swazam.common.infos.FingerprintSearchRequest;

@Path("searchmanagement")
public class SearchManagementService {

	/*
	 * Requesting a search 
	 * Return sessionKey in order to search later for it
	 */
	@GET @Path("search")
	@Consumes({MediaType.APPLICATION_XML})
    @Produces({MediaType.TEXT_PLAIN})
	public String search(FingerprintSearchRequest Fingerprint) {
		String sessionkey = SearchManagement.search(Fingerprint);
		return sessionkey;
	}
	
	/*
	 * Requesting the search result by sessionkey
	 */
	@GET @Path("searchresult")
	@Produces({MediaType.TEXT_PLAIN})
	public String searchResult(@QueryParam("sessionkey") String Sessionkey) {
		return SearchManagement.searchResult(Sessionkey);
	}
	
	/*
	 * Requesting the search result by sessionkey
	 */
	@GET @Path("history")
	@Produces({MediaType.APPLICATION_XML})
	public List<History> getHistory(@QueryParam("accountid") int AccountID) {
		return SearchManagement.getHistory(AccountID);
	}
}
