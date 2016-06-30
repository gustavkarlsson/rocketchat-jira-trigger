package se.gustavkarlsson.rocketchat.jira_trigger.configuration;

import com.moandjiezana.toml.Toml;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import se.gustavkarlsson.rocketchat.jira_trigger.test.TomlUtils;

public class ConfigurationTest {

	private Toml minimal;
	private Toml defaults;

	@Before
	public void setUp() throws Exception {
		minimal = TomlUtils.getMinimalToml();
		defaults = TomlUtils.getDefaultsToml();
	}

	@Test(expected = NullPointerException.class)
	public void createWithNullFileThrowsNPE() throws Exception {
		new Configuration(null, defaults);
	}

	@Test
	public void createWithMinimalFile() throws Exception {
		new Configuration(minimal, defaults);
	}

	@Test
	public void getAppConfig() throws Exception {
		AppConfiguration config = new Configuration(minimal, defaults).getAppConfiguration();
		Assertions.assertThat(config).isNotNull();
	}

	@Test
	public void getRocketChatConfig() throws Exception {
		RocketChatConfiguration config = new Configuration(minimal, defaults).getRocketChatConfiguration();
		Assertions.assertThat(config).isNotNull();
	}

	@Test
	public void getMessageConfig() throws Exception {
		MessageConfiguration config = new Configuration(minimal, defaults).getMessageConfiguration();
		Assertions.assertThat(config).isNotNull();
	}

	@Test
	public void getJiraConfig() throws Exception {
		JiraConfiguration config = new Configuration(minimal, defaults).getJiraConfiguration();
		Assertions.assertThat(config).isNotNull();
	}
}
