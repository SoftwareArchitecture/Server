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
	@Consumes({MediaType.APPLICATION_OCTET_STREAM})
    @Produces({MediaType.TEXT_PLAIN})
	public String search(byte[] Fingerprint) {
		try {
			FingerprintSearchRequest fpsearch = (FingerprintSearchRequest)  Helper.deserialize(Fingerprint);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "Hello";
	}
}
