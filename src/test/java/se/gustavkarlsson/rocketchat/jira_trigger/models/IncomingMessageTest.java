package se.gustavkarlsson.rocketchat.jira_trigger.models;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import org.junit.Before;
import org.junit.Test;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;

public class IncomingMessageTest {

	private IncomingMessage message;

	@Before
	public void setUp() throws Exception {
		message = new IncomingMessage();
	}

	@Test
	public void equals() throws Exception {
		EqualsVerifier.forClass(IncomingMessage.class).suppress(Warning.NONFINAL_FIELDS).verify();
	}

	@Test
	public void addAttachmentToNullList() throws Exception {
		Attachment attachment = new Attachment();

		message.addAttachment(attachment);

		assertThat(message.getAttachments()).containsOnly(attachment);
	}

	@Test
	public void addAttachmentToNonEmptyList() throws Exception {
		Attachment attachment = new Attachment("someone", null, null, null, null, null, null, null, null, null, null, null);
		message.setAttachments(Collections.singletonList(new Attachment("someone-else", null, null, null, null, null, null, null, null, null, null, null)));

		message.addAttachment(attachment);

		assertThat(message.getAttachments()).contains(attachment);
	}

}
