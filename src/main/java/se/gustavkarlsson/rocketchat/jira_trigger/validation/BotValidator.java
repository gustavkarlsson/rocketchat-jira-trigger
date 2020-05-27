package se.gustavkarlsson.rocketchat.jira_trigger.validation;

import se.gustavkarlsson.rocketchat.models.from_rocket_chat.FromRocketChatMessage;

import java.util.Optional;

class BotValidator implements Validator {
	private boolean ignoreBots;

	BotValidator(boolean ignoreBots) {
		this.ignoreBots = ignoreBots;
	}

	@Override
	public boolean isValid(FromRocketChatMessage message) {
		Object isBot = Optional.ofNullable(message.getBot() != null).orElse(false);
		return !isaBoolean(isBot) || !ignoreBots;
	}

	private boolean isaBoolean(Object isBot) {
		return isBot instanceof Boolean && !Boolean.valueOf(isBot.toString());
	}
}
