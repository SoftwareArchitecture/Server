package at.ac.tuwien.softwareArchitecture.SWAzam.model;

import java.io.Serializable;
import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement
public class History implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@XmlAttribute(name = "historyid", required = true)
	private int id;
	
	@XmlAttribute(name = "historyid", required = true)
	private int accountid;
	
	@XmlAttribute(name = "requesttype", required = true)
	private int requesttype;
	
	@XmlAttribute(name = "requestmessage", required = true)
	private String requestMessage;
	
	@XmlAttribute(name = "sessionkey", required = true)
	private String sessionkey;
	
	@XmlAttribute(name = "sessiondate", required = true)
	private Date sessiondate;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getAccountid() {
		return accountid;
	}

	public void setAccountid(int accountid) {
		this.accountid = accountid;
	}

	public int getRequesttype() {
		return requesttype;
	}

	public void setRequesttype(int requesttype) {
		this.requesttype = requesttype;
	}

	public String getRequestMessage() {
		return requestMessage;
	}

	public void setRequestMessage(String requestMessage) {
		this.requestMessage = requestMessage;
	}

	public String getSessionkey() {
		return sessionkey;
	}

	public void setSessionkey(String sessionkey) {
		this.sessionkey = sessionkey;
	}

	public Date getSessiondate() {
		return sessiondate;
	}

	public void setSessiondate(Date sessiondate) {
		this.sessiondate = sessiondate;
	}
	
}
