package se.gustavkarlsson.rocketchat.jira_trigger.configuration;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.moandjiezana.toml.Toml;
import se.gustavkarlsson.rocketchat.jira_trigger.di.qualifiers.Default;

import javax.annotation.Nullable;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ConfigurationModule extends AbstractModule {
	private static final String DEFAULTS_FILE_NAME = "defaults.toml";

	private final File configFile;

	public ConfigurationModule(String... args) {
		if (args.length > 0) {
			configFile = new File(args[0]);
		} else {
			configFile = null;
		}
	}

	@Override
	protected void configure() {
	}

	@Provides
	@Default
	@Singleton
	Toml provideDefaultToml() {
		return new Toml().read(ConfigurationModule.class.getClassLoader().getResourceAsStream(DEFAULTS_FILE_NAME));
	}

	@Provides
	@Singleton
	Toml provideTomlFromFile() {
		return Optional.ofNullable(configFile).map(configFile -> new Toml().read(configFile)).orElse(null);
	}

	@Provides
	@Singleton
	ConfigMap provideConfigMap(@Default Toml defaultToml, @Nullable Toml configFileToml) {
		List<ConfigMap> configMaps = new ArrayList<>();
		configMaps.add(new EnvVarConfigMap(System.getenv()));
		if (configFileToml != null) {
			configMaps.add(new TomlConfigMap(configFileToml));
		}
		configMaps.add(new TomlConfigMap(defaultToml));
		return new CascadingConfigMap(configMaps);
	}
}
