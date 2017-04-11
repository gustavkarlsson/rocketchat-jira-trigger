package se.gustavkarlsson.rocketchat.jira_trigger.models;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import org.junit.Test;

public class FromRocketChatMessageTest {

	@Test
	public void equals() throws Exception {
		EqualsVerifier.forClass(FromRocketChatMessage.class).suppress(Warning.NONFINAL_FIELDS).verify();
	}

}
