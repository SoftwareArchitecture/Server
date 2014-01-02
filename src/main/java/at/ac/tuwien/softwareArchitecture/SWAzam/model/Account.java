package at.ac.tuwien.softwareArchitecture.SWAzam.model;

public class Account {
	private int id = 0;
	private String username;
	private String password;
	
	private String name;

    public String getName() {
        return "HI";
    }

    public void setName(String user_name) {
        this.name = user_name;
    }
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
}
