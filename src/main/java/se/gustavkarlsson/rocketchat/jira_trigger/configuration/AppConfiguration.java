package se.gustavkarlsson.rocketchat.jira_trigger.configuration;

import com.moandjiezana.toml.Toml;

import static org.apache.commons.lang3.Validate.inclusiveBetween;
import static org.apache.commons.lang3.Validate.notNull;

public class AppConfiguration {
	private static final String PORT_KEY = "port";

	private final int port;

	AppConfiguration(Toml toml) throws ValidationException {
		notNull(toml);
		try {
			port = toml.getLong(PORT_KEY).intValue();
			inclusiveBetween(1, Integer.MAX_VALUE, port);
		} catch (Exception e) {
			throw new ValidationException(e);
		}
	}

	public int getPort() {
		return port;
	}
}
