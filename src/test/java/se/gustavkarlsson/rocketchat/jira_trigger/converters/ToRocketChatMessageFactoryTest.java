package se.gustavkarlsson.rocketchat.jira_trigger.converters;

import org.junit.Before;
import org.junit.Test;
import se.gustavkarlsson.rocketchat.jira_trigger.configuration.MessageConfiguration;
import se.gustavkarlsson.rocketchat.models.to_rocket_chat.ToRocketChatMessage;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ToRocketChatMessageFactoryTest {

	private MessageConfiguration mockMessageConfig;

	@Before
	public void setUp() throws Exception {
		mockMessageConfig = mock(MessageConfiguration.class);
	}

	@Test(expected = NullPointerException.class)
	public void constructWithNullThrowsNPE() throws Exception {
		new ToRocketChatMessageFactory(null);
	}

	@Test
	public void constructWithConfigSucceeds() throws Exception {
		new ToRocketChatMessageFactory(mockMessageConfig);
	}

	@Test
	public void create() throws Exception {
		String username = "user";
		String url = "http://someurl.com/image.png";
		ToRocketChatMessageFactory factory = new ToRocketChatMessageFactory(mockMessageConfig);
		when(mockMessageConfig.getUsername()).thenReturn(username);
		when(mockMessageConfig.getIconUrl()).thenReturn(url);

		ToRocketChatMessage message = factory.create();

		assertThat(message.getUsername()).isEqualTo(username);
		assertThat(message.getIconUrl()).isEqualTo(url);
	}

}
