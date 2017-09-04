package se.gustavkarlsson.rocketchat.jira_trigger.configuration;

import javax.inject.Inject;

import static org.apache.commons.lang3.Validate.inclusiveBetween;
import static org.apache.commons.lang3.Validate.notNull;

public class AppConfiguration {
	static final String KEY_PREFIX = "app.";

	static final long MAX_PORT_NUMBER = 65535;

	static final String PORT_KEY = "port";
	static final String MAX_THREADS_KEY = "max_threads";

	private final int port;
	private final int maxThreads;

	@Inject
	AppConfiguration(ConfigMap configMap) throws ValidationException {
		notNull(configMap);
		try {
			port = configMap.getLong(KEY_PREFIX + PORT_KEY).intValue();
			inclusiveBetween(1, MAX_PORT_NUMBER, port);
			maxThreads = configMap.getLong(KEY_PREFIX + MAX_THREADS_KEY).intValue();
			inclusiveBetween(1, Integer.MAX_VALUE, maxThreads);
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
