package se.gustavkarlsson.rocketchat.jira_trigger.messages;

import com.atlassian.jira.rest.client.api.domain.BasicPriority;
import com.atlassian.jira.rest.client.api.domain.Issue;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import se.gustavkarlsson.rocketchat.models.to_rocket_chat.Field;
import se.gustavkarlsson.rocketchat.models.to_rocket_chat.ToRocketChatAttachment;

import java.net.URI;

import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AttachmentConverterTest {
	private static final String DEFAULT_COLOR = "#121212";

	@Mock
	private Issue mockIssue;

	@Before
	public void setUp() throws Exception {
		when(mockIssue.getKey()).thenReturn("ISS-1234");
		when(mockIssue.getSelf()).thenReturn(URI.create("http://somejira/browse/ISS-1234"));
	}

	@Test(expected = NullPointerException.class)
	public void createWithNullDefaultColorThrowsNPE() throws Exception {
		new AttachmentConverter(false, null, emptyList(), emptyList());
	}

	@Test(expected = NullPointerException.class)
	public void createWithNullDefaultFieldCreatorsThrowsNPE() throws Exception {
		new AttachmentConverter(false, DEFAULT_COLOR, null, emptyList());
	}

	@Test(expected = NullPointerException.class)
	public void createWithNullExtendedFieldCreatorsConfigThrowsNPE() throws Exception {
		new AttachmentConverter(false, DEFAULT_COLOR, emptyList(), null);
	}

	@Test
	public void convertWithBlockerPrioritySetsBlockerColor() throws Exception {
		when(mockIssue.getPriority()).thenReturn(new BasicPriority(null, 0L, "Blocker"));
		AttachmentConverter converter = new AttachmentConverter(true, DEFAULT_COLOR, emptyList(), emptyList());

		ToRocketChatAttachment attachment = converter.convert(mockIssue, false);

		assertThat(attachment.getColor()).isEqualTo(AttachmentConverter.BLOCKER_COLOR);
	}

	@Test
	public void convertWithoutPriorityColorsSetsDefaultColor() throws Exception {
		AttachmentConverter converter = new AttachmentConverter(false, DEFAULT_COLOR, emptyList(), emptyList());

		ToRocketChatAttachment attachment = converter.convert(mockIssue, false);

		assertThat(attachment.getColor()).isEqualTo(DEFAULT_COLOR);
	}

	@Test
	public void convertWithUnknownPrioritySetsDefaultColor() throws Exception {
		when(mockIssue.getPriority()).thenReturn(new BasicPriority(null, 0L, "Unknown"));
		AttachmentConverter converter = new AttachmentConverter(true, DEFAULT_COLOR, emptyList(), emptyList());

		ToRocketChatAttachment attachment = converter.convert(mockIssue, false);

		assertThat(attachment.getColor()).isEqualTo(DEFAULT_COLOR);
	}

	@Test
	public void convertWithSingleDefaultFieldCreator() throws Exception {
		Field field = new Field("title", "value", true);
		AttachmentConverter converter = new AttachmentConverter(false, DEFAULT_COLOR, singletonList(issue -> field), emptyList());

		ToRocketChatAttachment attachment = converter.convert(mockIssue, false);

		assertThat(attachment.getFields()).containsExactly(field);
	}

	@Test
	public void convertWithSingleExtendedFieldCreator() throws Exception {
		Field field = new Field("title", "value", true);
		AttachmentConverter converter = new AttachmentConverter(false, DEFAULT_COLOR, emptyList(), singletonList(issue -> field));

		ToRocketChatAttachment attachment = converter.convert(mockIssue, true);

		assertThat(attachment.getFields()).containsExactly(field);
	}

	@Test
	public void htmlEndodedSummaryIsCleaned() throws Exception {
		when(mockIssue.getSummary()).thenReturn("&lt;Fran&ccedil;ais&gt;");
		AttachmentConverter converter = new AttachmentConverter(false, DEFAULT_COLOR, emptyList(), emptyList());

		ToRocketChatAttachment attachment = converter.convert(mockIssue, false);

		assertThat(attachment.getText()).isEqualTo("<http://somejira/browse/ISS-1234|Français>");
	}

	@Test(expected = IllegalArgumentException.class)
	public void nullElementInDefaultFieldCreatorThrowsException() throws Exception {
		new AttachmentConverter(false, DEFAULT_COLOR, singletonList(null), emptyList());
	}

	@Test(expected = IllegalArgumentException.class)
	public void nullElementInExtendedFieldCreatorThrowsException() throws Exception {
		new AttachmentConverter(false, DEFAULT_COLOR, emptyList(), singletonList(null));
	}
}
