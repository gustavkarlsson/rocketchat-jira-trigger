package se.gustavkarlsson.rocketchat.jira_trigger.configuration;

import com.moandjiezana.toml.Toml;

import static org.apache.commons.lang3.Validate.inclusiveBetween;

public class AppConfiguration extends DefaultingTomlConfiguration {
	private static final String KEY_PREFIX = "app.";
	private static final String PORT_KEY = "port";

	private final int port;

	AppConfiguration(Toml toml, Toml defaults) throws ValidationException {
		super(toml, defaults);
		try {
			port = getLongOrDefault(KEY_PREFIX + PORT_KEY).intValue();
			inclusiveBetween(1, Integer.MAX_VALUE, port);
		} catch (Exception e) {
			throw new ValidationException(e);
		}
	}

	public int getPort() {
		return port;
	}
}
