package se.gustavkarlsson.rocketchat.jira_trigger.messages;

import org.junit.Test;
import se.gustavkarlsson.rocketchat.models.to_rocket_chat.ToRocketChatMessage;

import static org.assertj.core.api.Assertions.assertThat;

public class ToRocketChatMessageFactoryTest {

	@Test
	public void constructWithNullUsernameIsOk() throws Exception {
		new ToRocketChatMessageFactory(null, "url");
	}

	@Test
	public void constructWithNullIconUrlIsOk() throws Exception {
		new ToRocketChatMessageFactory("johnDoe", null);
	}

	@Test
	public void messageContainsUsername() throws Exception {
		String username = "user";
		ToRocketChatMessageFactory factory = new ToRocketChatMessageFactory(username, null);

		ToRocketChatMessage message = factory.create();

		assertThat(message.getAlias()).isEqualTo(username);
	}

	@Test
	public void messageContainsUconUrl() throws Exception {
		String url = "http://someurl.com/image.png";
		ToRocketChatMessageFactory factory = new ToRocketChatMessageFactory(null, url);

		ToRocketChatMessage message = factory.create();

		assertThat(message.getAvatar()).isEqualTo(url);
	}

}
