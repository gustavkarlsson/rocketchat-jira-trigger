package se.gustavkarlsson.rocketchat.jira_trigger.validation;

import se.gustavkarlsson.rocketchat.models.from_rocket_chat.FromRocketChatMessage;

public interface Validator {
	boolean isValid(FromRocketChatMessage message);
}
