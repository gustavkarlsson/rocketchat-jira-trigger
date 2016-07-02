package se.gustavkarlsson.rocketchat.jira_trigger.configuration;

import com.moandjiezana.toml.Toml;

import static org.apache.commons.lang3.Validate.inclusiveBetween;

public class AppConfiguration extends DefaultingConfiguration {
	private static final String KEY_PREFIX = "app.";

	private static final long MAX_THREADS = 100;

	private static final String PORT_KEY = "port";
	private static final String THREADS_KEY = "max_threads";

	private final int port;
	private final int maxThreads;

	AppConfiguration(Toml toml, Toml defaults) throws ValidationException {
		super(toml, defaults);
		try {
			port = getLong(KEY_PREFIX + PORT_KEY).intValue();
			inclusiveBetween(1, Integer.MAX_VALUE, port);
			maxThreads = getLong(KEY_PREFIX + THREADS_KEY).intValue();
			inclusiveBetween(1, MAX_THREADS, maxThreads);
		} catch (Exception e) {
			throw new ValidationException(e);
		}
	}

	public int getPort() {
		return port;
	}

	public int getMaxThreads() {
		return maxThreads;
	}
}
