package se.gustavkarlsson.rocketchat.jira_trigger.configuration;

import javax.inject.Inject;
import java.util.HashSet;
import java.util.Set;

import static org.apache.commons.lang3.Validate.notNull;

public class RocketChatConfiguration {
	static final String KEY_PREFIX = "rocketchat.";

	static final String TOKENS_KEY = "tokens";
	static final String WHITELISTED_USERS = "whitelisted_users";
	static final String BLACKLISTED_USERS = "blacklisted_users";
	static final String WHITELISTED_CHANNELS = "whitelisted_channels";
	static final String BLACKLISTED_CHANNELS = "blacklisted_channels";

	private final Set<String> tokens;
	private final Set<String> whitelistedUsers;
	private final Set<String> blacklistedUsers;
	private final Set<String> whitelistedChannels;
	private final Set<String> blacklistedChannels;

	@Inject
	RocketChatConfiguration(ConfigMap configMap) throws ValidationException {
		notNull(configMap);
		try {
			tokens = new HashSet<>(configMap.getStringList(KEY_PREFIX + TOKENS_KEY));
			whitelistedUsers = new HashSet<>(configMap.getStringList(KEY_PREFIX + WHITELISTED_USERS));
			blacklistedUsers = new HashSet<>(configMap.getStringList(KEY_PREFIX + BLACKLISTED_USERS));
			whitelistedChannels = new HashSet<>(configMap.getStringList(KEY_PREFIX + WHITELISTED_CHANNELS));
			blacklistedChannels = new HashSet<>(configMap.getStringList(KEY_PREFIX + BLACKLISTED_CHANNELS));
		} catch (Exception e) {
			throw new ValidationException(e);
		}
	}

	public Set<String> getTokens() {
		return tokens;
	}

	public Set<String> getWhitelistedUsers() {
		return whitelistedUsers;
	}

	public Set<String> getBlacklistedUsers() {
		return blacklistedUsers;
	}

	public Set<String> getWhitelistedChannels() {
		return whitelistedChannels;
	}

	public Set<String> getBlacklistedChannels() {
		return blacklistedChannels;
	}
}
