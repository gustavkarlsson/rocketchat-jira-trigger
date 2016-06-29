package se.gustavkarlsson.rocketchat.jira_trigger.configuration;

import com.moandjiezana.toml.Toml;

import java.net.URI;

import static org.apache.commons.lang3.Validate.notNull;

public class JiraConfiguration {
	private static final String URI_KEY = "uri";
	private static final String USER_KEY = "username";
	private static final String PASSWORD_KEY = "password";

	private final URI uri;
	private final String username;
	private final String password;

	JiraConfiguration(Toml toml) throws ValidationException {
		notNull(toml);
		try {
			uri = URI.create(toml.getString(URI_KEY));
			username = toml.getString(USER_KEY);
			password = toml.getString(PASSWORD_KEY);
		} catch (Exception e) {
			throw new ValidationException(e);
		}
	}

	public URI getUri() {
		return uri;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}
}
