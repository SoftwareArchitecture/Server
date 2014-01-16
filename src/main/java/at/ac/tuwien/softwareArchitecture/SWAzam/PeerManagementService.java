package at.ac.tuwien.softwareArchitecture.SWAzam;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import at.ac.tuwien.softwarearchitecture.swazam.common.infos.FingerprintSearchResponse;
import at.ac.tuwien.softwarearchitecture.swazam.common.infos.PeerInfo;

@Path("peermanagement")
public class PeerManagementService {

	/*
	 * This is the function which will be called by a peer in order to finilize a search
	 */
    @POST
    @Path("/searchresult")
    @Consumes(MediaType.APPLICATION_XML)
    public void searchResult(FingerprintSearchResponse searchResponse) {
    	PeerManagement pm = new PeerManagement();
    	pm.searchResult(searchResponse);
    }
    
    /*
     * Peers will using this for registering / reactivating into server
     */
    @POST
    @Path("/registerpeer")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public PeerInfo registerPeer(PeerInfo peerInfo) {
    	PeerManagement pm = new PeerManagement();
    	return pm.registerPeer(peerInfo);
    }
    
    /*
     * Peers will using this for registering / reactivating into server
     */
    @POST
    @Path("/updatepeer")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void updatePeer(PeerInfo peerInfo) {
    	PeerManagement pm = new PeerManagement();
    	pm.updatePeer(peerInfo);
    }
}
