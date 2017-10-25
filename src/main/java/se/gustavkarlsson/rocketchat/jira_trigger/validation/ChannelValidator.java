package se.gustavkarlsson.rocketchat.jira_trigger.validation;

import org.slf4j.Logger;
import se.gustavkarlsson.rocketchat.models.from_rocket_chat.FromRocketChatMessage;

import java.util.Set;

import static org.apache.commons.lang3.Validate.noNullElements;
import static org.slf4j.LoggerFactory.getLogger;

class ChannelValidator implements Validator {
	private static final Logger log = getLogger(ChannelValidator.class);

	private final Set<String> blacklistedChannels;
	private final Set<String> whitelistedChannels;

	ChannelValidator(Set<String> blacklistedChannels, Set<String> whitelistedChannels) {
		this.blacklistedChannels = noNullElements(blacklistedChannels);
		this.whitelistedChannels = noNullElements(whitelistedChannels);
	}

	@Override
	public boolean isValid(FromRocketChatMessage message) {
		String channelName = message.getChannelName();
		if (!isAllowed(channelName)) {
			log.info("Forbidden channel encountered: {}", channelName);
			return false;
		}
		return true;
	}

	private boolean isAllowed(String channelName) {
		return !blacklistedChannels.contains(channelName) && (whitelistedChannels.isEmpty() || whitelistedChannels.contains(channelName));
	}
}
