package se.gustavkarlsson.rocketchat.jira_trigger.configuration;

import com.moandjiezana.toml.Toml;

import static org.apache.commons.lang3.Validate.notNull;

public class Configuration {
	private final AppConfiguration app;
	private final JiraConfiguration jira;
	private final MessageConfiguration message;
	private final RocketChatConfiguration rocketchat;

	public Configuration(Toml toml, Toml defaults) throws ValidationException {
		notNull(toml);
		notNull(defaults);
		app = new AppConfiguration(toml, defaults);
		jira = new JiraConfiguration(toml, defaults);
		message = new MessageConfiguration(toml, defaults);
		rocketchat = new RocketChatConfiguration(toml, defaults);
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
