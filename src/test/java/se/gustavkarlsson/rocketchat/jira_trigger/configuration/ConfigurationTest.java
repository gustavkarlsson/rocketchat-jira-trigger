package se.gustavkarlsson.rocketchat.jira_trigger.configuration;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class ConfigurationTest {

	@Mock
	private AppConfiguration mockAppConfig;

	@Mock
	private JiraConfiguration mockJiraConfig;

	@Mock
	private MessageConfiguration mockMessageConfig;

	@Mock
	private RocketChatConfiguration mockRocketChatConfig;

	private Configuration config;

	@Before
	public void setUp() throws Exception {
		this.config = new Configuration(mockAppConfig, mockJiraConfig, mockMessageConfig, mockRocketChatConfig);
	}

	@Test(expected = NullPointerException.class)
	public void createWithNullAppConfigFileThrowsNPE() throws Exception {
		new Configuration(null, mockJiraConfig, mockMessageConfig, mockRocketChatConfig);
	}

	@Test(expected = NullPointerException.class)
	public void createWithNullJiraConfigFileThrowsNPE() throws Exception {
		new Configuration(mockAppConfig, null, mockMessageConfig, mockRocketChatConfig);
	}

	@Test(expected = NullPointerException.class)
	public void createWithNullMessageConfigFileThrowsNPE() throws Exception {
		new Configuration(mockAppConfig, mockJiraConfig, null, mockRocketChatConfig);
	}

	@Test(expected = NullPointerException.class)
	public void createWithNullRocketChatConfigFileThrowsNPE() throws Exception {
		new Configuration(mockAppConfig, mockJiraConfig, mockMessageConfig, null);
	}

	@Test
	public void getAppConfig() throws Exception {
		AppConfiguration appConfig = config.getAppConfiguration();
		assertThat(appConfig).isNotNull();
	}

	@Test
	public void getRocketChatConfig() throws Exception {
		RocketChatConfiguration rocketChatConfig = config.getRocketChatConfiguration();
		assertThat(rocketChatConfig).isNotNull();
	}

	@Test
	public void getMessageConfig() throws Exception {
		MessageConfiguration messageConfig = config.getMessageConfiguration();
		assertThat(messageConfig).isNotNull();
	}

	@Test
	public void getJiraConfig() throws Exception {
		JiraConfiguration jiraConfig = config.getJiraConfiguration();
		assertThat(jiraConfig).isNotNull();
	}
}
