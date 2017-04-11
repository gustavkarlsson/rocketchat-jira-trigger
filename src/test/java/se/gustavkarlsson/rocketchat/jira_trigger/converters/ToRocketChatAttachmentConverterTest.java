package se.gustavkarlsson.rocketchat.jira_trigger.converters;

import com.atlassian.jira.rest.client.api.domain.BasicPriority;
import com.atlassian.jira.rest.client.api.domain.Issue;
import org.junit.Before;
import org.junit.Test;
import se.gustavkarlsson.rocketchat.jira_trigger.configuration.MessageConfiguration;
import se.gustavkarlsson.rocketchat.jira_trigger.models.to_rocket_chat.Field;
import se.gustavkarlsson.rocketchat.jira_trigger.models.to_rocket_chat.ToRocketChatAttachment;

import java.net.URI;

import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ToRocketChatAttachmentConverterTest {

	private MessageConfiguration mockConfig;
	private Issue mockIssue;

	@Before
	public void setUp() throws Exception {
		mockConfig = mock(MessageConfiguration.class);
		mockIssue = mock(Issue.class);
		when(mockIssue.getKey()).thenReturn("ISS-1234");
		when(mockIssue.getSelf()).thenReturn(URI.create("http://somejira/browse/ISS-1234"));
	}

	@Test(expected = NullPointerException.class)
	public void createWithNullConfigThrowsNPE() throws Exception {
		new AttachmentConverter(null, emptyList(), emptyList());
	}

	@Test(expected = NullPointerException.class)
	public void createWithNullDefaultFieldCreatorsThrowsNPE() throws Exception {
		new AttachmentConverter(mockConfig, null, emptyList());
	}

	@Test(expected = NullPointerException.class)
	public void createWithNullExtendedFieldCreatorsConfigThrowsNPE() throws Exception {
		new AttachmentConverter(mockConfig, emptyList(), null);
	}

	@Test
	public void convertWithBlockerPrioritySetsBlockerColor() throws Exception {
		when(mockIssue.getPriority()).thenReturn(new BasicPriority(null, 0L, "Blocker"));
		when(mockConfig.isPriorityColors()).thenReturn(true);
		AttachmentConverter converter = new AttachmentConverter(mockConfig, emptyList(), emptyList());

		ToRocketChatAttachment attachment = converter.convert(mockIssue, false);

		assertThat(attachment.getColor()).isEqualTo(AttachmentConverter.BLOCKER_COLOR);
	}

	@Test
	public void convertWithoutPriorityColorsSetsDefaultColor() throws Exception {
		String defaultColor = "#F24678";
		when(mockConfig.isPriorityColors()).thenReturn(false);
		when(mockConfig.getDefaultColor()).thenReturn(defaultColor);
		when(mockIssue.getPriority()).thenReturn(new BasicPriority(null, 0L, "Blocker"));
		AttachmentConverter converter = new AttachmentConverter(mockConfig, emptyList(), emptyList());

		ToRocketChatAttachment attachment = converter.convert(mockIssue, false);

		assertThat(attachment.getColor()).isEqualTo(defaultColor);
	}

	@Test
	public void convertWithUnknownPrioritySetsDefaultColor() throws Exception {
		String defaultColor = "#F24678";
		when(mockConfig.isPriorityColors()).thenReturn(true);
		when(mockConfig.getDefaultColor()).thenReturn(defaultColor);
		when(mockIssue.getPriority()).thenReturn(new BasicPriority(null, 0L, "Unknown"));
		AttachmentConverter converter = new AttachmentConverter(mockConfig, emptyList(), emptyList());

		ToRocketChatAttachment attachment = converter.convert(mockIssue, false);

		assertThat(attachment.getColor()).isEqualTo(defaultColor);
	}

	@Test
	public void convertWithSingleDefaultFieldCreator() throws Exception {
		Field field = new Field("title", "value", true);
		AttachmentConverter converter = new AttachmentConverter(mockConfig, singletonList(issue -> field), emptyList());

		ToRocketChatAttachment attachment = converter.convert(mockIssue, false);

		assertThat(attachment.getFields()).containsExactly(field);
	}

	@Test
	public void convertWithSingleExtendedFieldCreator() throws Exception {
		Field field = new Field("title", "value", true);
		AttachmentConverter converter = new AttachmentConverter(mockConfig, emptyList(), singletonList(issue -> field));

		ToRocketChatAttachment attachment = converter.convert(mockIssue, true);

		assertThat(attachment.getFields()).containsExactly(field);
	}

	@Test
	public void create() throws Exception {
		new AttachmentConverter(mockConfig, emptyList(), emptyList());
	}
}
