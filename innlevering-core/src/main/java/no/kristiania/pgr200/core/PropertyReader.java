package no.kristiania.pgr200.core;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertyReader {

	private String url;
	private String username;
	private String password;

	private Properties properties;

	public PropertyReader() throws IOException {
		this.properties = new Properties();
		ClassLoader loader = Thread.currentThread().getContextClassLoader();
		InputStream stream = loader.getResourceAsStream("innlevering.properties");
		properties.load(stream);

		url = properties.getProperty("jdbc-url");
		username = properties.getProperty("jdbc-username");
		password = properties.getProperty("jdbc-password");

	}

	public String getUrl() {
		return url;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

}