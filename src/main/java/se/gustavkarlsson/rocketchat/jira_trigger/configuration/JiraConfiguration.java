package se.gustavkarlsson.rocketchat.jira_trigger.configuration;

import com.moandjiezana.toml.Toml;
import se.gustavkarlsson.rocketchat.jira_trigger.di.annotations.Default;

import javax.inject.Inject;
import java.net.URI;

public class JiraConfiguration extends DefaultingConfiguration {
	static final String KEY_PREFIX = "jira.";

	static final String URI_KEY = "uri";
	static final String USER_KEY = "username";
	static final String PASSWORD_KEY = "password";

	private final URI uri;
	private final String username;
	private final String password;

	@Inject
	JiraConfiguration(Toml toml, @Default Toml defaults) throws ValidationException {
		super(toml, defaults);
		try {
			uri = URI.create(getString(KEY_PREFIX + URI_KEY));
			username = getString(KEY_PREFIX + USER_KEY);
			password = getString(KEY_PREFIX + PASSWORD_KEY);
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
