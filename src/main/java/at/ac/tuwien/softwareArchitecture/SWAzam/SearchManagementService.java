package at.ac.tuwien.softwareArchitecture.SWAzam;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("searchmanagement")
public class SearchManagementService {

	@GET @Path("search")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public void search() {
		
	}
}
