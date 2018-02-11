package se.gustavkarlsson.rocketchat.jira_trigger.configuration;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.moandjiezana.toml.Toml;
import se.gustavkarlsson.rocketchat.jira_trigger.di.qualifiers.Default;

import java.io.File;

import static org.apache.commons.lang3.Validate.notEmpty;

public class ConfigurationModule extends AbstractModule {
	private static final String DEFAULTS_FILE_NAME = "defaults.toml";

	private final File configFile;

	public ConfigurationModule(String... args) {
		notEmpty(args, "A configuration file must be specified as the first argument");
		configFile = new File(args[0]);
	}

	@Override
	protected void configure() {
	}

	@Provides
	@Default
	Toml provideDefaultToml() {
		return new Toml().read(ConfigurationModule.class.getClassLoader().getResourceAsStream(DEFAULTS_FILE_NAME));
	}

	@Provides
	Toml provideTomlFromFile() {
		return new Toml().read(configFile);
	}

	@Provides
	ConfigMap provideConfigMap(@Default Toml defaultToml, Toml configFileToml) {
		ConfigMap configFileConfig = new TomlConfigMap(configFileToml);
		ConfigMap defaultConfig = new TomlConfigMap(defaultToml);
		return new CascadingConfigMap(configFileConfig, defaultConfig);
	}
}
