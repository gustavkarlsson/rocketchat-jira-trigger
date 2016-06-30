package se.gustavkarlsson.rocketchat.jira_trigger.configuration;

import com.moandjiezana.toml.Toml;

import java.util.HashSet;
import java.util.Set;

public class RocketChatConfiguration extends DefaultingConfiguration {
	private static final String KEY_PREFIX = "rocketchat.";

	private static final String TOKENS_KEY = "tokens";
	private static final String WHITELISTED_USERS = "whitelisted_users";
	private static final String BLACKLISTED_USERS = "blacklisted_users";
	private static final String WHITELISTED_CHANNELS = "whitelisted_channels";
	private static final String BLACKLISTED_CHANNELS = "blacklisted_channels";

	private final Set<String> tokens;
	private final Set<String> whitelistedUsers;
	private final Set<String> blacklistedUsers;
	private final Set<String> whitelistedChannels;
	private final Set<String> blacklistedChannels;

	RocketChatConfiguration(Toml toml, Toml defaults) throws ValidationException {
		super(toml, defaults);
		try {
			tokens = new HashSet<>(getList(KEY_PREFIX + TOKENS_KEY));
			whitelistedUsers = new HashSet<>(getList(KEY_PREFIX + WHITELISTED_USERS));
			blacklistedUsers = new HashSet<>(getList(KEY_PREFIX + BLACKLISTED_USERS));
			whitelistedChannels = new HashSet<>(getList(KEY_PREFIX + WHITELISTED_CHANNELS));
			blacklistedChannels = new HashSet<>(getList(KEY_PREFIX + BLACKLISTED_CHANNELS));
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
