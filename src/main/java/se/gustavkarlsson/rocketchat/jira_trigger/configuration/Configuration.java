package se.gustavkarlsson.rocketchat.jira_trigger.configuration;

import com.moandjiezana.toml.Toml;

import static org.apache.commons.lang3.Validate.notNull;

public class Configuration {
	static final String APP_KEY = "app";
	static final String JIRA_KEY = "jira";
	static final String MESSAGE_KEY = "message";
	static final String ROCKETCHAT_KEY = "rocketchat";

	private final AppConfiguration app;
	private final JiraConfiguration jira;
	private final MessageConfiguration message;
	private final RocketChatConfiguration rocketchat;

	public Configuration(Toml toml) throws ValidationException {
		notNull(toml);
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
