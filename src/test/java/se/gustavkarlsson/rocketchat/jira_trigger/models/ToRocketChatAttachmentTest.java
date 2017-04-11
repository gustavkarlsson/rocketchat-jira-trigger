package se.gustavkarlsson.rocketchat.jira_trigger.models;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import org.junit.Before;
import org.junit.Test;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;

public class ToRocketChatAttachmentTest {

	private ToRocketChatAttachment attachment;

	@Before
	public void setUp() throws Exception {
		attachment = new ToRocketChatAttachment();
	}

	@Test
	public void equals() throws Exception {
		EqualsVerifier.forClass(ToRocketChatAttachment.class).suppress(Warning.NONFINAL_FIELDS).verify();
	}

	@Test
	public void addFieldToNullList() throws Exception {
		Field field = new Field();

		attachment.addField(field);

		assertThat(attachment.getFields()).containsOnly(field);
	}

	@Test
	public void addFieldToNonEmptyList() throws Exception {
		Field field = new Field("baz", "buz", false);
		attachment.setFields(Collections.singletonList(new Field("foo", "bar", true)));

		attachment.addField(field);

		assertThat(attachment.getFields()).contains(field);
	}
}
