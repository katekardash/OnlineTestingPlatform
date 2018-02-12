package ua.nure.kardash.Testing.DB.Entity;

/**
 * A brief account summary used to check access and display info
 */
public class AccountInfo {
	private int id;
	private String login;
	private String name;
	private String role;
	private boolean isBlocked;
	private String settings;

	public AccountInfo(){};

	public AccountInfo(int id, String login, String name, String role, boolean isBlocked, String settings) {
		this.id = id;
		this.login = login;
		this.name = name;
		this.role = role;
		this.isBlocked = isBlocked;
		this.setSettings(settings);
	}
	public int getId() {
		return id;
	}
	public String getLogin() {
		return login;
	}
	public String getName() {
		return name;
	}
	public String getRole() {
		return role;
	}
	public boolean isBlocked() {
		return isBlocked;
	}
	public void setId(int id) {
		this.id = id;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public void setBlocked(boolean isBlocked) {
		this.isBlocked = isBlocked;
	}
	public String getSettings() {
		return settings;
	}
	public void setSettings(String settings) {
		this.settings = settings;
	}


}
