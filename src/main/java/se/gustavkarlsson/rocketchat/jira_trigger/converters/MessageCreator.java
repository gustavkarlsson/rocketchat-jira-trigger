package se.gustavkarlsson.rocketchat.jira_trigger.converters;

import se.gustavkarlsson.rocketchat.jira_trigger.configuration.MessageConfiguration;
import se.gustavkarlsson.rocketchat.jira_trigger.models.IncomingMessage;

import java.util.function.Supplier;

import static org.apache.commons.lang3.Validate.notNull;

public class MessageCreator implements Supplier<IncomingMessage> {

	private final MessageConfiguration config;

	public MessageCreator(MessageConfiguration config) {
		this.config = notNull(config);
	}

	@Override
	public IncomingMessage get() {
		IncomingMessage message = new IncomingMessage();
		message.setUsername(config.getUsername());
		message.setIconUrl(config.getIconUrl());
		return message;
	}

}
