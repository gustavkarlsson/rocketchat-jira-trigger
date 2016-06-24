package se.gustavkarlsson.rocketchat.jira_trigger.configuration;

import com.moandjiezana.toml.Toml;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class RocketChatConfiguration {
	private static final String BLACKLISTED_USERNAMES_KEY = "blacklisted_usernames";
	private static final List<String> BLACKLISTED_USERNAMES_DEFAULT = Arrays.asList("rocket.cat", "hubot");

	private HashSet<String> blacklistedUsernames;

	RocketChatConfiguration(Toml toml) throws ValidationException {
		try {
			blacklistedUsernames = new HashSet<>(toml.getList(BLACKLISTED_USERNAMES_KEY, BLACKLISTED_USERNAMES_DEFAULT));
		} catch (Exception e) {
			throw new ValidationException(e);
		}
	}

	public Set<String> getBlacklistedUsernames() {
		return blacklistedUsernames;
	}
}
