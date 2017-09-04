package se.gustavkarlsson.rocketchat.jira_trigger.configuration;

import com.moandjiezana.toml.Toml;
import se.gustavkarlsson.rocketchat.jira_trigger.di.annotations.Default;

import javax.inject.Inject;

import static org.apache.commons.lang3.Validate.notNull;

public class Configuration {
	private final AppConfiguration app;
	private final JiraConfiguration jira;
	private final MessageConfiguration message;
	private final RocketChatConfiguration rocketchat;

	@Inject
	public Configuration(Toml toml, @Default Toml defaults) throws ValidationException {
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
