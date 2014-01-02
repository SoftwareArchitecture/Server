package at.ac.tuwien.softwareArchitecture.SWAzam.controller;

import java.io.Serializable;
import java.util.Map;


import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import at.ac.tuwien.softwareArchitecture.SWAzam.model.Account;

//@ManagedBean(name="accounts")
//@SessionScoped
public class AccountController implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//@ManagedProperty(value = "#{param.userID}")
    private String userName;
	
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@ManagedProperty(value = "#{param.password}")
    private String password;
	
	public Account getAccountByUsername() {
		Account acc = new Account();
		acc.setUsername("Lara");
		acc.setPassword("craft");
		return acc;
	}
}
