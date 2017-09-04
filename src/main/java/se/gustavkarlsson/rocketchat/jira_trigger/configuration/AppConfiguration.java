package se.gustavkarlsson.rocketchat.jira_trigger.configuration;

import com.moandjiezana.toml.Toml;
import se.gustavkarlsson.rocketchat.jira_trigger.di.annotations.Default;

import javax.inject.Inject;

import static org.apache.commons.lang3.Validate.inclusiveBetween;

public class AppConfiguration extends DefaultingConfiguration {
	static final String KEY_PREFIX = "app.";

	static final long MAX_THREADS = 100;

	static final String PORT_KEY = "port";
	static final String MAX_THREADS_KEY = "max_threads";

	private final int port;
	private final int maxThreads;

	@Inject
	AppConfiguration(Toml toml, @Default Toml defaults) throws ValidationException {
		super(toml, defaults);
		try {
			port = getLong(KEY_PREFIX + PORT_KEY).intValue();
			inclusiveBetween(1, Integer.MAX_VALUE, port);
			maxThreads = getLong(KEY_PREFIX + MAX_THREADS_KEY).intValue();
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
