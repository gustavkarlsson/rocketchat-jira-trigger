package se.gustavkarlsson.rocketchat.jira_trigger.di.modules;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import se.gustavkarlsson.rocketchat.jira_trigger.ConfigFactory;
import se.gustavkarlsson.rocketchat.jira_trigger.configuration.Configuration;
import se.gustavkarlsson.rocketchat.jira_trigger.di.annotations.ConfigFile;

import java.io.File;

public class ConfigurationModule extends AbstractModule {
	private final File configFile;

	public ConfigurationModule(String... args) {
		if (args.length != 1) {
			throw new IllegalArgumentException("Exactly one configuration file must be specified");
		}
		configFile = new File(args[0]);
	}

	@Override
	protected void configure() {
		bind(File.class).annotatedWith(ConfigFile.class).toInstance(configFile);
	}

	@Provides
	Configuration provideConfiguration(ConfigFactory configFactory) throws Exception {
		return configFactory.get();
	}
}
