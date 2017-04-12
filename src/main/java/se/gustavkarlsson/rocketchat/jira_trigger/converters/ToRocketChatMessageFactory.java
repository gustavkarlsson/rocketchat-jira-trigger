package se.gustavkarlsson.rocketchat.jira_trigger.converters;

import se.gustavkarlsson.rocketchat.jira_trigger.configuration.MessageConfiguration;
import se.gustavkarlsson.rocketchat.models.to_rocket_chat.ToRocketChatMessage;

import static org.apache.commons.lang3.Validate.notNull;

public class ToRocketChatMessageFactory {

	private final MessageConfiguration config;

	public ToRocketChatMessageFactory(MessageConfiguration config) {
		this.config = notNull(config);
	}

	public ToRocketChatMessage create() {
		ToRocketChatMessage message = new ToRocketChatMessage();
		message.setUsername(config.getUsername());
		message.setIconUrl(config.getIconUrl());
		return message;
	}

}
