package se.gustavkarlsson.rocketchat.jira_trigger.converters;

import se.gustavkarlsson.rocketchat.jira_trigger.configuration.MessageConfiguration;
import se.gustavkarlsson.rocketchat.jira_trigger.models.IncomingMessage;

import static org.apache.commons.lang3.Validate.notNull;

public class MessageCreator {

	private final MessageConfiguration config;

	public MessageCreator(MessageConfiguration config) {
		this.config = notNull(config);
	}

	public IncomingMessage create() {
		IncomingMessage message = new IncomingMessage();
		message.setUsername(config.getUsername());
		message.setIconUrl(config.getIconUrl());
		return message;
	}

}
