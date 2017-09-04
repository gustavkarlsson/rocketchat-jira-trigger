package se.gustavkarlsson.rocketchat.jira_trigger.configuration;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.moandjiezana.toml.Toml;
import se.gustavkarlsson.rocketchat.jira_trigger.di.annotations.Default;

import java.io.File;
import java.util.Arrays;

public class ConfigurationModule extends AbstractModule {
	private static final String DEFAULTS_FILE_NAME = "defaults.toml";

	private final File configFile;

	public ConfigurationModule(String... args) {
		if (args.length != 1) {
			throw new IllegalArgumentException("Exactly one configuration file must be specified");
		}
		configFile = new File(args[0]);
	}

	@Override
	protected void configure() {
	}

	@Provides
	@Default
	Toml provideDefaultToml() throws Exception {
		return new Toml().read(Configuration.class.getClassLoader().getResourceAsStream(DEFAULTS_FILE_NAME));
	}

	@Provides
	Toml provideTomlFromFile() throws Exception {
		return new Toml().read(configFile);
	}

	@Provides
	ConfigMap provideConfigMap(@Default Toml defaultToml, Toml configFileToml) throws Exception {
		ConfigMap defaultConfig = new TomlConfigMap(defaultToml);
		ConfigMap configFileConfig = new TomlConfigMap(configFileToml);
		return new CascadingConfigMap(Arrays.asList(defaultConfig, configFileConfig));
	}
}
