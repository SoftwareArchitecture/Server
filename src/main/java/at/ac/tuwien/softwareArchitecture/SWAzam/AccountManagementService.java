package at.ac.tuwien.softwareArchitecture.SWAzam;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import at.ac.tuwien.softwareArchitecture.SWAzam.model.Account;

@Path("accountmanagement")
public class AccountManagementService {

	   /**
     * Method handling HTTP GET requests. The returned object will be sent
     * to the client as "XML" media type.
     *
     * @return String that will be returned as a text/plain response.
     */
    @GET @Path("/list")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Account> getAccounts() {

        return AccountManagement.getAllAccounts();
    }
    
    @GET @Path("login")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Account Login(@QueryParam("username") String Username, @QueryParam("password") String Password) {
    	return AccountManagement.login(Username, Password);
    }
}
