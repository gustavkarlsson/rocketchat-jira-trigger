package se.gustavkarlsson.rocketchat.jira_trigger.validation;

import org.slf4j.Logger;
import se.gustavkarlsson.rocketchat.models.from_rocket_chat.FromRocketChatMessage;

import java.util.Set;

import static java.util.Arrays.stream;
import static org.apache.commons.lang3.Validate.noNullElements;
import static org.slf4j.LoggerFactory.getLogger;

class UserValidator implements Validator {
	private static final Logger log = getLogger(UserValidator.class);

	private final Set<String> blacklistedUsers;
	private final Set<String> whitelistedUsers;

	UserValidator(Set<String> blacklistedUsers, Set<String> whitelistedUsers) {
		this.blacklistedUsers = noNullElements(blacklistedUsers);
		this.whitelistedUsers = noNullElements(whitelistedUsers);
	}

	@Override
	public boolean isValid(FromRocketChatMessage message) {
		String userId = message.getUserId();
		String userName = message.getUserName();
		if (!areAllowed(userId, userName)) {
			log.info("Forbidden user encountered. ID: {}, Name: {}", userId, userName);
			return false;
		}
		return true;
	}

	private boolean areAllowed(String... userNamesAndIds) {
		return stream(userNamesAndIds).allMatch(this::isAllowed);
	}

	private boolean isAllowed(String value) {
		return !blacklistedUsers.contains(value) && (whitelistedUsers.isEmpty() || whitelistedUsers.contains(value));
	}
}
