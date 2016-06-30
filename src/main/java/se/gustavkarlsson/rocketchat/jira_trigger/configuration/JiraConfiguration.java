package se.gustavkarlsson.rocketchat.jira_trigger.configuration;

import com.moandjiezana.toml.Toml;

import java.net.URI;

public class JiraConfiguration extends DefaultingConfiguration {
	private static final String KEY_PREFIX = "jira.";

	private static final String JIRA_URI_KEY = "uri";
	private static final String JIRA_USER_KEY = "username";
	private static final String JIRA_PASSWORD_KEY = "password";

	private final URI uri;
	private final String username;
	private final String password;

	JiraConfiguration(Toml toml, Toml defaults) throws ValidationException {
		super(toml, defaults);
		try {
			uri = URI.create(getString(KEY_PREFIX + JIRA_URI_KEY));
			username = getString(KEY_PREFIX + JIRA_USER_KEY);
			password = getString(KEY_PREFIX + JIRA_PASSWORD_KEY);
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
