package se.gustavkarlsson.rocketchat.jira_trigger;

import com.moandjiezana.toml.Toml;
import se.gustavkarlsson.rocketchat.jira_trigger.configuration.Configuration;
import se.gustavkarlsson.rocketchat.jira_trigger.configuration.ValidationException;
import se.gustavkarlsson.rocketchat.jira_trigger.di.annotations.ConfigFile;

import javax.inject.Inject;
import java.io.File;

import static org.apache.commons.lang3.Validate.notNull;

public class ConfigFactory {
	private static final String DEFAULTS_FILE_NAME = "defaults.toml";

	private final File configFile;

	@Inject
	public ConfigFactory(@ConfigFile File configFile) {
		this.configFile = notNull(configFile);
	}

	private static Toml parseToml(File configFile) {
		return new Toml().read(configFile);
	}

	private static Toml parseDefaults() {
		return new Toml().read(Configuration.class.getClassLoader().getResourceAsStream(DEFAULTS_FILE_NAME));
	}

	public Configuration get() throws ValidationException {
		Toml toml = parseToml(configFile);
		Toml defaults = parseDefaults();
		return new Configuration(toml, defaults);
	}
}
