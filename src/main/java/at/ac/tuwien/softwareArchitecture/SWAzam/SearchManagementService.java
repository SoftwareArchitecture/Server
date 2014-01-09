package at.ac.tuwien.softwareArchitecture.SWAzam;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

@Path("searchmanagement")
public class SearchManagementService {

	@GET @Path("search")
    @Produces({MediaType.TEXT_PLAIN})
	public String search(@QueryParam("username") String Username, @QueryParam("password") String Password, @QueryParam("fingerprint") ac.at.tuwien.infosys.swa.audio.Fingerprint Fingerprint) {
		
		return "";
	}
}
