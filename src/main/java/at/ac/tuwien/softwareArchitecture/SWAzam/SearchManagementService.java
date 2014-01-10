package at.ac.tuwien.softwareArchitecture.SWAzam;

import java.io.IOException;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import at.ac.tuwien.softwarearchitecture.swazam.common.infos.FingerprintSearchRequest;
import at.ac.tuwien.softwarearchitecture.swazam.common.infos.Helper;

@Path("searchmanagement")
public class SearchManagementService {

	@GET @Path("search")
	@Consumes({MediaType.APPLICATION_XML})
    @Produces({MediaType.TEXT_PLAIN})
	public String search(FingerprintSearchRequest Fingerprint) {
		
		return "Hello";
	}
}
