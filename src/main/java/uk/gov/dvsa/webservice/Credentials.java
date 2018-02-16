package uk.gov.dvsa.webservice;

/**
 * Username and password credentials
 * 
 * @author Simon Cutts
 *
 */
public class Credentials {
	private String username;
	private String password;

	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	@Override
	public String toString() {
		return String.format("credentials: %s/%s", getUsername(), getPassword());
	}

}
