package se.gustavkarlsson.rocketchat.jira_trigger.validation;

import org.slf4j.Logger;
import se.gustavkarlsson.rocketchat.models.from_rocket_chat.FromRocketChatMessage;

import java.util.Set;

import static java.util.Arrays.stream;
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
		String channelId = message.getChannelId();
		String channelName = message.getChannelName();
		if (!areAllowed(channelId, channelName)) {
			log.info("Forbidden channel encountered. ID: {}, Name: {}", channelId, channelName);
			return false;
		}
		return true;
	}

	private boolean areAllowed(String... channelNamesAndIds) {
		return stream(channelNamesAndIds).allMatch(this::isAllowed);
	}

	private boolean isAllowed(String value) {
		return !blacklistedChannels.contains(value) && (whitelistedChannels.isEmpty() || whitelistedChannels.contains(value));
	}
}
