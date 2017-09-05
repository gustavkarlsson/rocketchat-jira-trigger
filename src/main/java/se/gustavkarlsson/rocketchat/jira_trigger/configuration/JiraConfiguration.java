package se.gustavkarlsson.rocketchat.jira_trigger.configuration;

import javax.inject.Inject;
import java.net.URI;

import static org.apache.commons.lang3.Validate.notNull;

public class JiraConfiguration {
	private static final String KEY_PREFIX = "jira.";
	static final String URI_KEY = KEY_PREFIX + "uri";
	static final String USER_KEY = KEY_PREFIX + "username";
	static final String PASSWORD_KEY = KEY_PREFIX + "password";

	private final URI uri;
	private final String username;
	private final String password;

	@Inject
	JiraConfiguration(ConfigMap configMap) throws ValidationException {
		notNull(configMap);
		try {
			uri = URI.create(notNull(configMap.getString(URI_KEY), String.format("%s must be provided", URI_KEY)));
			username = configMap.getString(USER_KEY);
			password = configMap.getString(PASSWORD_KEY);
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
