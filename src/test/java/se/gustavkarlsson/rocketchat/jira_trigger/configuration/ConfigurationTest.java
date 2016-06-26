package se.gustavkarlsson.rocketchat.jira_trigger.configuration;

import org.assertj.core.api.Assertions;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.File;

public class ConfigurationTest {

	private static File minimal;

	@BeforeClass
	public static void setUpClass() throws Exception {
		minimal = new File(ConfigurationTest.class.getClassLoader().getResource("minimal.toml").toURI());
	}

	@Test(expected = NullPointerException.class)
	public void createWithNullFileThrowsNPE() throws Exception {
		new Configuration(null);
	}

	@Test
	public void createWithMinimalFile() throws Exception {
		new Configuration(minimal);
	}

	@Test
	public void getAppConfig() throws Exception {
		AppConfiguration config = new Configuration(minimal).getAppConfiguration();
		Assertions.assertThat(config).isNotNull();
	}

	@Test
	public void getRocketChatConfig() throws Exception {
		RocketChatConfiguration config = new Configuration(minimal).getRocketChatConfiguration();
		Assertions.assertThat(config).isNotNull();
	}

	@Test
	public void getMessageConfig() throws Exception {
		MessageConfiguration config = new Configuration(minimal).getMessageConfiguration();
		Assertions.assertThat(config).isNotNull();
	}

	@Test
	public void getJiraConfig() throws Exception {
		JiraConfiguration config = new Configuration(minimal).getJiraConfiguration();
		Assertions.assertThat(config).isNotNull();
	}
}
