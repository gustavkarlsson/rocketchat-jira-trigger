package se.gustavkarlsson.rocketchat.jira_trigger.configuration;

import javax.inject.Inject;

import static org.apache.commons.lang3.Validate.notNull;

public class Configuration {
	private final AppConfiguration appConfig;
	private final JiraConfiguration jiraConfig;
	private final MessageConfiguration messageConfig;
	private final RocketChatConfiguration rocketChatConfig;

	@Inject
	public Configuration(AppConfiguration appConfig, JiraConfiguration jiraConfig, MessageConfiguration messageConfig, RocketChatConfiguration rocketChatConfig) {
		this.appConfig = notNull(appConfig);
		this.jiraConfig = notNull(jiraConfig);
		this.messageConfig = notNull(messageConfig);
		this.rocketChatConfig = notNull(rocketChatConfig);
	}

	public AppConfiguration getAppConfiguration() {
		return appConfig;
	}

	public JiraConfiguration getJiraConfiguration() {
		return jiraConfig;
	}

	public MessageConfiguration getMessageConfiguration() {
		return messageConfig;
	}

	public RocketChatConfiguration getRocketChatConfiguration() {
		return rocketChatConfig;
	}
}
