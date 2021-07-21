package se.gustavkarlsson.rocketchat.jira_trigger.validation;

import se.gustavkarlsson.rocketchat.models.from_rocket_chat.FromRocketChatMessage;

import java.util.Optional;

class EditValidator implements Validator {
	private boolean ignoreEdits;

	EditValidator(boolean ignoreEdits) {
		this.ignoreEdits = ignoreEdits;
	}

	@Override
	public boolean isValid(FromRocketChatMessage message) {
		Boolean isEdit = Optional.ofNullable(message.getEdited()).orElse(false);
		return !isEdit || !ignoreEdits;
	}
}
