package se.gustavkarlsson.rocketchat.jira_trigger.configuration;

import com.moandjiezana.toml.Toml;

import java.util.HashSet;
import java.util.Set;

public class RocketChatConfiguration extends DefaultingTomlConfiguration {
	private static final String KEY_PREFIX = "rocketchat.";
	private static final String BLACKLISTED_USERNAMES_KEY = "blacklisted_usernames";

	private final Set<String> blacklistedUsernames;

	RocketChatConfiguration(Toml toml, Toml defaults) throws ValidationException {
		super(toml, defaults);
		try {
			blacklistedUsernames = new HashSet<>(getListOrDefault(KEY_PREFIX + BLACKLISTED_USERNAMES_KEY));
		} catch (Exception e) {
			throw new ValidationException(e);
		}
	}

	public Set<String> getBlacklistedUsernames() {
		return blacklistedUsernames;
	}
}
