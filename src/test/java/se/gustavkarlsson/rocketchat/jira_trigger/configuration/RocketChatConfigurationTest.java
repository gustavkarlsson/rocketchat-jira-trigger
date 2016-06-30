package se.gustavkarlsson.rocketchat.jira_trigger.configuration;

import com.moandjiezana.toml.Toml;
import org.junit.Before;
import org.junit.Test;
import se.gustavkarlsson.rocketchat.jira_trigger.test.TomlUtils;

public class RocketChatConfigurationTest {

	private Toml minimal;
	private Toml defaults;

	@Before
	public void setUp() throws Exception {
		minimal = TomlUtils.getMinimalToml();
		defaults = TomlUtils.getDefaultsToml();
	}

	@Test(expected = NullPointerException.class)
	public void createWithNullTomlThrowsNPE() throws Exception {
		new RocketChatConfiguration(null, defaults);
	}

	@Test
	public void createWithMinimalToml() throws Exception {
		new RocketChatConfiguration(minimal, defaults);
	}

	@Test
	public void getTokens() throws Exception {
		new RocketChatConfiguration(minimal, defaults).getTokens();
	}

	@Test
	public void getWhitelistedChannels() throws Exception {
		new RocketChatConfiguration(minimal, defaults).getWhitelistedChannels();
	}

	@Test
	public void getBlacklistedChannels() throws Exception {
		new RocketChatConfiguration(minimal, defaults).getBlacklistedChannels();
	}

	@Test
	public void getWhitelistedUsers() throws Exception {
		new RocketChatConfiguration(minimal, defaults).getWhitelistedUsers();
	}

	@Test
	public void getBlacklistedUsers() throws Exception {
		new RocketChatConfiguration(minimal, defaults).getBlacklistedUsers();
	}

}
