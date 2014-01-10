package at.ac.tuwien.softwareArchitecture.SWAzam.model;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement
public class Peer implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@XmlAttribute(name = "peerid", required = true)
	private int id;
	
	@XmlAttribute(name = "issuperpeer")
	private boolean IsSuperPeer;
	
	@XmlAttribute(name = "superpeerid")
	private int Superpeerid;
	
	@XmlAttribute(name = "peername")
	private String Name;
	
	@XmlAttribute(name = "port")
	private int Port;
	
	@XmlAttribute(name = "peerip")
	private String PeerIP;
	
	@XmlAttribute(name = "accountid")
	private int accountid;
	
	@XmlAttribute(name = "active")
	private boolean active;

	public int getAccountid() {
		return accountid;
	}

	public void setAccountid(int accountid) {
		this.accountid = accountid;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public boolean isIsSuperPeer() {
		return IsSuperPeer;
	}

	public void setIsSuperPeer(boolean isSuperPeer) {
		IsSuperPeer = isSuperPeer;
	}

	public int getSuperpeerid() {
		return Superpeerid;
	}

	public void setSuperpeerid(int superpeerid) {
		Superpeerid = superpeerid;
	}

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	public int getPort() {
		return Port;
	}

	public void setPort(int port) {
		Port = port;
	}

	public String getPeerIP() {
		return PeerIP;
	}

	public void setPeerIP(String peerIP) {
		PeerIP = peerIP;
	}
	
	
}
