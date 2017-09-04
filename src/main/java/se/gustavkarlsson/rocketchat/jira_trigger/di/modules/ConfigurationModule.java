package se.gustavkarlsson.rocketchat.jira_trigger.di.modules;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import se.gustavkarlsson.rocketchat.jira_trigger.ConfigFactory;
import se.gustavkarlsson.rocketchat.jira_trigger.configuration.Configuration;

import java.io.File;

public class ConfigurationModule extends AbstractModule {
	private final String configFilePath;

	public ConfigurationModule(String... args) {
		if (args.length != 1) {
			throw new IllegalArgumentException("Exactly one configuration file must be specified");
		}
		configFilePath = args[0];
	}

	@Override
	protected void configure() {

	}

	@Provides
	Configuration provideConfiguration() throws Exception {
		ConfigFactory configFactory = new ConfigFactory();
		return configFactory.get(new File(configFilePath));
	}
}
