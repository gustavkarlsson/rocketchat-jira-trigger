package se.gustavkarlsson.rocketchat.jira_trigger.validation;

import org.slf4j.Logger;
import se.gustavkarlsson.rocketchat.models.from_rocket_chat.FromRocketChatMessage;

import java.util.Set;

import static org.apache.commons.lang3.Validate.noNullElements;
import static org.slf4j.LoggerFactory.getLogger;

class TokenValidator implements Validator {
	private static final Logger log = getLogger(TokenValidator.class);

	private final Set<String> validTokens;

	TokenValidator(Set<String> validTokens) {
		this.validTokens = noNullElements(validTokens);
	}

	@Override
	public boolean isValid(FromRocketChatMessage message) {
		String token = message.getToken();
		if (!validTokens.isEmpty() && !validTokens.contains(token)) {
			log.info("Forbidden token encountered: {}", token);
			return false;
		}
		return true;
	}
}
