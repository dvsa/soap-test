package uk.gov.dvsa.webservice;

import static java.lang.String.format;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.yaml.snakeyaml.Yaml;

/**
 * Creates an instance of Configuration, that is a representation of the file
 * config.yml
 * 
 * @author Simon Cutts
 *
 */
public final class Configuration {
	private String port;
	private String host;
	private Credentials credentials;

	/**
	 * Create a new instance of Configuration
	 * 
	 * @return
	 */
	public static Configuration newInstance() {
		Configuration config = null;
		Yaml yaml = new Yaml();
		try (InputStream in = Files.newInputStream(Paths.get("config.yml"))) {
			config = yaml.loadAs(in, Configuration.class);
		} catch (IOException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
		return config;
	}

	private Configuration() {
	}

	public String getPort() {
		return port;
	}

	public String getHost() {
		return host;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public void setHost(String version) {
		this.host = version;
	}

	public Credentials getCredentials() {
		return credentials;
	}

	public void setCredentials(Credentials credentials) {
		this.credentials = credentials;
	}

	@Override
	public String toString() {
		return new StringBuilder().append(format("Host: %s\n", host)).append(format("Port: %s\n", port))
				.append(format("Connecting as: %s\n", credentials)).toString();
	}
}