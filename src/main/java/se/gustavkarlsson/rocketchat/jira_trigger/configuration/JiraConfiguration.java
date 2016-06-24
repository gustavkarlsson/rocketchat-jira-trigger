package se.gustavkarlsson.rocketchat.jira_trigger.configuration;

import com.moandjiezana.toml.Toml;

import java.net.URI;

public class JiraConfiguration {
	private static final String JIRA_URI_KEY = "uri";
	private static final String JIRA_USER_KEY = "username";
	private static final String JIRA_PASSWORD_KEY = "password";

	private URI uri;
	private String username;
	private String password;

	JiraConfiguration(Toml toml) throws ValidationException {
		try {
			uri = URI.create(toml.getString(JIRA_URI_KEY));
			username = toml.getString(JIRA_USER_KEY);
			password = toml.getString(JIRA_PASSWORD_KEY);
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
