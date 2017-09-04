package se.gustavkarlsson.rocketchat.jira_trigger.configuration;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Mockito.when;
import static se.gustavkarlsson.rocketchat.jira_trigger.configuration.RocketChatConfiguration.KEY_PREFIX;
import static se.gustavkarlsson.rocketchat.jira_trigger.configuration.RocketChatConfiguration.TOKENS_KEY;

@RunWith(MockitoJUnitRunner.class)
public class RocketChatConfigurationTest {

	@Mock
	private ConfigMap mockConfigMap;

	@Test(expected = NullPointerException.class)
	public void createWithNullConfigMapThrowsNPE() throws Exception {
		new RocketChatConfiguration(null);
	}

	@Test(expected = ValidationException.class)
	public void createWithNullTokensConfigMapThrowsValidationException() throws Exception {
		when(mockConfigMap.getStringList(KEY_PREFIX + TOKENS_KEY)).thenReturn(null);
		new RocketChatConfiguration(mockConfigMap);
	}

}
