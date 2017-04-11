package se.gustavkarlsson.rocketchat.jira_trigger.models;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import org.junit.Before;
import org.junit.Test;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;

public class ToRocketChatMessageTest {

	private ToRocketChatMessage message;

	@Before
	public void setUp() throws Exception {
		message = new ToRocketChatMessage();
	}

	@Test
	public void equals() throws Exception {
		EqualsVerifier.forClass(ToRocketChatMessage.class).suppress(Warning.NONFINAL_FIELDS).verify();
	}

	@Test
	public void addAttachmentToNullList() throws Exception {
		ToRocketChatAttachment attachment = new ToRocketChatAttachment();

		message.addAttachment(attachment);

		assertThat(message.getAttachments()).containsOnly(attachment);
	}

	@Test
	public void addAttachmentToNonEmptyList() throws Exception {
		ToRocketChatAttachment attachment = new ToRocketChatAttachment("someone", null, null, null, null, null, null, null, null, null, null, null);
		message.setAttachments(Collections.singletonList(new ToRocketChatAttachment("someone-else", null, null, null, null, null, null, null, null, null, null, null)));

		message.addAttachment(attachment);

		assertThat(message.getAttachments()).contains(attachment);
	}

}
