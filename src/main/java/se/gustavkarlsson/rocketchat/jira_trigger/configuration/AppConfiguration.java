package se.gustavkarlsson.rocketchat.jira_trigger.configuration;

import com.moandjiezana.toml.Toml;

import static org.apache.commons.lang3.Validate.inclusiveBetween;

public class AppConfiguration {
	private static final String KEY_PREFIX = "app.";
	private static final String PORT_KEY = "port";

	private final int port;

	AppConfiguration(Toml toml) throws ValidationException {
		try {
			port = toml.getLong(KEY_PREFIX + PORT_KEY).intValue();
			inclusiveBetween(1, Integer.MAX_VALUE, port);
		} catch (Exception e) {
			throw new ValidationException(e);
		}
	}

	public int getPort() {
		return port;
	}
}
