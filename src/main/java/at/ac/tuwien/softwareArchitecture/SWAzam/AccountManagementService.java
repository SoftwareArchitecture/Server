package at.ac.tuwien.softwareArchitecture.SWAzam;

import java.util.List;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import at.ac.tuwien.softwareArchitecture.SWAzam.model.Account;

@Path("accountmanagement")
public class AccountManagementService {
 
	/**
     * Method handling HTTP GET list all users requests. The returned object will be sent
     * to the client as "XML" media type.
     *
     * @return String that will be returned as a JSON/XML response.
     */
    @GET @Path("/list")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Account> getAccounts() {

        return AccountManagement.getAllAccounts();
    }
    
	/**
     * Method handling HTTP  login requests. The returned object will be sent
     * to the client as "XML" media type.
     *
     * @return String that will be returned as a JSON/XML response.
     */
    @GET @Path("login")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Account Login(@QueryParam("username") String Username, @QueryParam("password") String Password) {
    	return AccountManagement.login(Username, Password);
    }
    
	/**
     * Method handling HTTP PUT requests for updating an account. The returned object will be sent
     * to the client as "XML" media type.
     *
     * @return String that will be returned as a JSON/XML response.
     */
    @PUT @Path("updateaccount")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void updateAccount(@QueryParam("id") int id, @QueryParam("username") String Username, @QueryParam("password") String Password, @QueryParam("firstname") String Firstname, @QueryParam("lastname") String Lastname, @QueryParam("sessionkey") String sessionkey) {
    	AccountManagement.updateAccount(id, Username, Password, Firstname, Lastname, sessionkey);
    }
    
	/**
     * Method handling HTTP Delete requests for Deleting account. The returned object will be sent
     * to the client as "XML" media type.
     *
     * @return String that will be returned as a JSON/XML response.
     */
    @DELETE @Path("deleteaccount")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void updateAccount(@QueryParam("id") int id, @QueryParam("sessionkey") String SessionKey) {
    	AccountManagement.deleteAccount(id, SessionKey);
    }
    
	/**
     * Method handling HTTP Post requests for inserting accounts. The returned object will be sent
     * to the client as "XML" media type.
     *
     * @return String that will be returned as a JSON/XML response.
     */
    @POST @Path("insertaccount")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void insertAccount(@QueryParam("username") String Username, @QueryParam("password") String Password, @QueryParam("firstname") String Firstname, @QueryParam("lastname") String Lastname, @QueryParam("sessionkey") String SessionKey) {
    	AccountManagement.insertAccount(Username, Password, Firstname, Lastname, SessionKey);
    }
}
