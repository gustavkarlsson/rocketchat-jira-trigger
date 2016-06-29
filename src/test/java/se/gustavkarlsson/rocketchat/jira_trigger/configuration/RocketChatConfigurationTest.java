package se.gustavkarlsson.rocketchat.jira_trigger.configuration;

import com.moandjiezana.toml.Toml;
import org.junit.Before;
import org.junit.Test;
import se.gustavkarlsson.rocketchat.jira_trigger.test.MinimalToml;

public class RocketChatConfigurationTest {

	private Toml minimal;

	@Before
	public void setUp() throws Exception {
		minimal = MinimalToml.get().getTable(Configuration.ROCKETCHAT_KEY);
	}

	@Test(expected = NullPointerException.class)
	public void createWithNullTomlThrowsNPE() throws Exception {
		new RocketChatConfiguration(null);
	}

	@Test
	public void createWithMinimalToml() throws Exception {
		new RocketChatConfiguration(minimal);
	}

	@Test
	public void getTokens() throws Exception {
		new RocketChatConfiguration(minimal).getTokens();
	}

	@Test
	public void getWhitelistedChannels() throws Exception {
		new RocketChatConfiguration(minimal).getWhitelistedChannels();
	}

	@Test
	public void getBlacklistedChannels() throws Exception {
		new RocketChatConfiguration(minimal).getBlacklistedChannels();
	}

	@Test
	public void getWhitelistedUsers() throws Exception {
		new RocketChatConfiguration(minimal).getWhitelistedUsers();
	}

	@Test
	public void getBlacklistedUsers() throws Exception {
		new RocketChatConfiguration(minimal).getBlacklistedUsers();
	}

}
