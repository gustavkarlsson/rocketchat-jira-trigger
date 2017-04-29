package se.gustavkarlsson.rocketchat.jira_trigger;

import com.moandjiezana.toml.Toml;
import se.gustavkarlsson.rocketchat.jira_trigger.configuration.Configuration;
import se.gustavkarlsson.rocketchat.jira_trigger.configuration.ValidationException;

import java.io.File;

class ConfigFactory {
	private static final String DEFAULTS_FILE_NAME = "defaults.toml";

	private static Toml parseToml(File configFile) {
		return new Toml().read(configFile);
	}

	private static Toml parseDefaults() {
		return new Toml().read(Configuration.class.getClassLoader().getResourceAsStream(DEFAULTS_FILE_NAME));
	}

	Configuration get(File configFile) throws ValidationException {
		Toml toml = parseToml(configFile);
		Toml defaults = parseDefaults();
		return new Configuration(toml, defaults);
	}
}
