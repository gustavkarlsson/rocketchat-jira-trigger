package se.gustavkarlsson.rocketchat.jira_trigger.configuration;

import com.moandjiezana.toml.Toml;
import org.junit.Before;
import org.junit.Test;
import se.gustavkarlsson.rocketchat.jira_trigger.test.TomlUtils;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static se.gustavkarlsson.rocketchat.jira_trigger.configuration.RocketChatConfiguration.KEY_PREFIX;
import static se.gustavkarlsson.rocketchat.jira_trigger.configuration.RocketChatConfiguration.TOKENS_KEY;

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

	@Test(expected = ValidationException.class)
	public void createWithNullTokensTomlThrowsValidationException() throws Exception {
		Toml defaultsToml = mock(Toml.class);
		when(defaultsToml.getList(KEY_PREFIX + TOKENS_KEY)).thenReturn(null);
		new RocketChatConfiguration(minimal, defaultsToml).getTokens();
	}

}
