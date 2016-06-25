package se.gustavkarlsson.rocketchat.jira_trigger.configuration;

import com.moandjiezana.toml.Toml;

import java.io.File;

public class Configuration extends DefaultingTomlConfiguration {
	private static final String DEFAULTS_FILE_NAME = "defaults.toml";

	private final AppConfiguration app;
	private final JiraConfiguration jira;
	private final MessageConfiguration message;
	private final RocketChatConfiguration rocketchat;

	public Configuration(String configFilePath) throws ValidationException {
		super(parseToml(configFilePath), parseDefaults());
		app = new AppConfiguration(toml, defaults);
		jira = new JiraConfiguration(toml, defaults);
		message = new MessageConfiguration(toml, defaults);
		rocketchat = new RocketChatConfiguration(toml, defaults);
	}

	private static Toml parseToml(String configFilePath) {
		return new Toml().read(new File(configFilePath));
	}

	private static Toml parseDefaults() {
		return new Toml().read(Configuration.class.getClassLoader().getResourceAsStream(DEFAULTS_FILE_NAME));
	}

	public AppConfiguration getAppConfiguration() {
		return app;
	}

	public JiraConfiguration getJiraConfiguration() {
		return jira;
	}

	public MessageConfiguration getMessageConfiguration() {
		return message;
	}

	public RocketChatConfiguration getRocketChatConfiguration() {
		return rocketchat;
	}
}
