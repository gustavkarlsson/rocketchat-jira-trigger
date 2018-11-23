package se.gustavkarlsson.rocketchat.jira_trigger.messages;

import se.gustavkarlsson.rocketchat.models.to_rocket_chat.ToRocketChatMessage;

public class ToRocketChatMessageFactory {

	private final String alias;
	private final String avatar;

	ToRocketChatMessageFactory(String alias, String avatar) {
		this.alias = alias;
		this.avatar = avatar;
	}

	public ToRocketChatMessage create() {
		ToRocketChatMessage message = new ToRocketChatMessage();
		message.setAlias(alias);
		message.setAvatar(avatar);
		return message;
	}

}
