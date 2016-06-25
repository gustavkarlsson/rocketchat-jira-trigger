package se.gustavkarlsson.rocketchat.jira_trigger.configuration;

import com.moandjiezana.toml.Toml;

import java.io.File;

public class Configuration {
	private static final String APP_KEY = "app";
	private static final String JIRA_KEY = "jira";
	private static final String MESSAGE_KEY = "message";
	private static final String ROCKETCHAT_KEY = "rocketchat";

	private final AppConfiguration app;
	private final JiraConfiguration jira;
	private final MessageConfiguration message;
	private final RocketChatConfiguration rocketchat;

	public Configuration(String configFilePath) throws ValidationException {
		Toml toml = new Toml().read(new File(configFilePath));
		app = new AppConfiguration(toml.getTable(APP_KEY));
		jira = new JiraConfiguration(toml.getTable(JIRA_KEY));
		message = new MessageConfiguration(toml.getTable(MESSAGE_KEY));
		rocketchat = new RocketChatConfiguration(toml.getTable(ROCKETCHAT_KEY));
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
