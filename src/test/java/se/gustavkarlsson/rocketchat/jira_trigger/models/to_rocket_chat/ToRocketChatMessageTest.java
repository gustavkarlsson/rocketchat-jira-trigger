package se.gustavkarlsson.rocketchat.jira_trigger.models.to_rocket_chat;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import org.junit.Test;

public class ToRocketChatMessageTest {

	@Test
	public void equals() throws Exception {
		EqualsVerifier.forClass(ToRocketChatMessage.class).suppress(Warning.NONFINAL_FIELDS).verify();
	}

}
