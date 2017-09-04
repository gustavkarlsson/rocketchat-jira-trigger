package se.gustavkarlsson.rocketchat.jira_trigger.messages;

import se.gustavkarlsson.rocketchat.models.to_rocket_chat.ToRocketChatMessage;

public class ToRocketChatMessageFactory {

	private final String username;
	private final String iconUrl;

	ToRocketChatMessageFactory(String username, String iconUrl) {
		this.username = username;
		this.iconUrl = iconUrl;
	}

	public ToRocketChatMessage create() {
		ToRocketChatMessage message = new ToRocketChatMessage();
		message.setUsername(username);
		message.setIconUrl(iconUrl);
		return message;
	}

}
