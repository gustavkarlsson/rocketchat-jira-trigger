package se.gustavkarlsson.rocketchat.jira_trigger.configuration;

import com.google.inject.Singleton;
import se.gustavkarlsson.rocketchat.jira_trigger.server.Server;

import javax.inject.Inject;

import static org.apache.commons.lang3.Validate.*;

@Singleton
public class AppConfiguration {
	private static final String KEY_PREFIX = "app.";
	static final String IP_ADDRESS_KEY = KEY_PREFIX + "ip_address";
	static final String PORT_KEY = KEY_PREFIX + "port";
	static final String MAX_THREADS_KEY = KEY_PREFIX + "max_threads";

	private final String ipAddress;
	private final int port;
	private final int maxThreads;

	@Inject
	AppConfiguration(ConfigMap configMap) throws ValidationException {
		notNull(configMap);
		try {
			ipAddress = notBlank(configMap.getString(IP_ADDRESS_KEY), String.format("Non-blank %s must be provided", IP_ADDRESS_KEY));
			port = notNull(configMap.getLong(PORT_KEY), String.format("%s must be provided", PORT_KEY)).intValue();
			inclusiveBetween(1, Server.MAX_PORT_NUMBER, port, String.format("%s must be within %d and %d. Was: %d", PORT_KEY, 1, Server.MAX_PORT_NUMBER, port));
			maxThreads = notNull(configMap.getLong(MAX_THREADS_KEY), String.format("%s must be provided", MAX_THREADS_KEY)).intValue();
			inclusiveBetween(1, Integer.MAX_VALUE, maxThreads, String.format("%s must be within %d and %d. Was: %d", MAX_THREADS_KEY, 1, Integer.MAX_VALUE, maxThreads));
		} catch (Exception e) {
			throw new ValidationException(e);
		}
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public int getPort() {
		return port;
	}

	public int getMaxThreads() {
		return maxThreads;
	}
}
