package se.gustavkarlsson.rocketchat.jira_trigger.configuration;

import com.moandjiezana.toml.Toml;

import java.io.File;

import static org.apache.commons.lang3.Validate.notNull;

public class Configuration {
	static final String APP_KEY = "app";
	static final String JIRA_KEY = "jira";
	static final String MESSAGE_KEY = "message";
	static final String ROCKETCHAT_KEY = "rocketchat";
	private static final String DEFAULTS_FILE_NAME = "defaults.toml";
	private final AppConfiguration app;
	private final JiraConfiguration jira;
	private final MessageConfiguration message;
	private final RocketChatConfiguration rocketchat;

	public Configuration(File configFile) throws ValidationException {
		notNull(configFile);
		Toml toml = parseToml(configFile);
		app = new AppConfiguration(toml.getTable(APP_KEY));
		jira = new JiraConfiguration(toml.getTable(JIRA_KEY));
		message = new MessageConfiguration(toml.getTable(MESSAGE_KEY));
		rocketchat = new RocketChatConfiguration(toml.getTable(ROCKETCHAT_KEY));
	}

	private static Toml parseToml(File configFile) {
		return new Toml(parseDefaults()).read(configFile);
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
