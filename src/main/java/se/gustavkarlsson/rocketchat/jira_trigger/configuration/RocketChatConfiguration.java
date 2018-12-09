package se.gustavkarlsson.rocketchat.jira_trigger.configuration;

import com.google.inject.Singleton;

import javax.inject.Inject;
import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.apache.commons.lang3.Validate.notNull;

@Singleton
public class RocketChatConfiguration {
	private static final String KEY_PREFIX = "rocketchat.";
	static final String TOKENS_KEY = KEY_PREFIX + "tokens";
	static final String WHITELISTED_USERS = KEY_PREFIX + "whitelisted_users";
	static final String BLACKLISTED_USERS = KEY_PREFIX + "blacklisted_users";
	static final String WHITELISTED_CHANNELS = KEY_PREFIX + "whitelisted_channels";
	static final String BLACKLISTED_CHANNELS = KEY_PREFIX + "blacklisted_channels";
	static final String IGNORE_BOTS = KEY_PREFIX + "ignore_bots";

	private final Set<String> tokens;
	private final Set<String> whitelistedUsers;
	private final Set<String> blacklistedUsers;
	private final Set<String> whitelistedChannels;
	private final Set<String> blacklistedChannels;
	private final boolean ignoreBots;

	@Inject
	RocketChatConfiguration(ConfigMap configMap) throws ValidationException {
		notNull(configMap);
		try {
			tokens = new HashSet<>(Optional.of(configMap.getStringList(TOKENS_KEY)).orElse(Collections.emptyList()));
			whitelistedUsers = new HashSet<>(Optional.of(configMap.getStringList(WHITELISTED_USERS)).orElse(Collections.emptyList()));
			blacklistedUsers = new HashSet<>(Optional.of(configMap.getStringList(BLACKLISTED_USERS)).orElse(Collections.emptyList()));
			whitelistedChannels = new HashSet<>(Optional.of(configMap.getStringList(WHITELISTED_CHANNELS)).orElse(Collections.emptyList()));
			blacklistedChannels = new HashSet<>(Optional.of(configMap.getStringList(BLACKLISTED_CHANNELS)).orElse(Collections.emptyList()));
			ignoreBots = notNull(configMap.getBoolean(IGNORE_BOTS), String.format("%s must be provided", IGNORE_BOTS));
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

	public boolean isIgnoreBots() {
		return ignoreBots;
	}
}
