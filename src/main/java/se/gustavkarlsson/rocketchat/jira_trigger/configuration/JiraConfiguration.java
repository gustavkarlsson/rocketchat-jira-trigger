package se.gustavkarlsson.rocketchat.jira_trigger.configuration;

import javax.inject.Inject;
import java.net.URI;

import static org.apache.commons.lang3.Validate.notNull;

public class JiraConfiguration {
	static final String KEY_PREFIX = "jira.";

	static final String URI_KEY = "uri";
	static final String USER_KEY = "username";
	static final String PASSWORD_KEY = "password";

	private final URI uri;
	private final String username;
	private final String password;

	@Inject
	JiraConfiguration(ConfigMap configMap) throws ValidationException {
		notNull(configMap);
		try {
			uri = URI.create(configMap.getString(KEY_PREFIX + URI_KEY));
			username = configMap.getString(KEY_PREFIX + USER_KEY);
			password = configMap.getString(KEY_PREFIX + PASSWORD_KEY);
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
