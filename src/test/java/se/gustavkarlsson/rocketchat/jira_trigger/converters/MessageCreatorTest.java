package se.gustavkarlsson.rocketchat.jira_trigger.converters;

import org.junit.Before;
import org.junit.Test;
import se.gustavkarlsson.rocketchat.jira_trigger.configuration.MessageConfiguration;
import se.gustavkarlsson.rocketchat.jira_trigger.models.IncomingMessage;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class MessageCreatorTest {

	private MessageConfiguration mockMessageConfig;

	@Before
	public void setUp() throws Exception {
		mockMessageConfig = mock(MessageConfiguration.class);
	}

	@Test(expected = NullPointerException.class)
	public void constructWithNullThrowsNPE() throws Exception {
		new MessageCreator(null);
	}

	@Test
	public void constructWithConfigSucceeds() throws Exception {
		new MessageCreator(mockMessageConfig);
	}

	@Test
	public void create() throws Exception {
		String username = "user";
		String url = "http://someurl.com/image.png";
		MessageCreator creator = new MessageCreator(mockMessageConfig);
		when(mockMessageConfig.getUsername()).thenReturn(username);
		when(mockMessageConfig.getIconUrl()).thenReturn(url);

		IncomingMessage message = creator.create();

		assertThat(message.getUsername()).isEqualTo(username);
		assertThat(message.getIconUrl()).isEqualTo(url);
	}

}
