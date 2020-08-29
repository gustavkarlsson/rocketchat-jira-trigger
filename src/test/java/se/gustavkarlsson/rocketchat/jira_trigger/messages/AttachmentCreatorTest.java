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
public class AttachmentCreatorTest {
	private static final String DEFAULT_COLOR = "#121212";
	private static final URI baseUri = URI.create("http://my.jira");

	@Mock
	private Issue mockIssue;

	@Before
	public void setUp() {
		when(mockIssue.getKey()).thenReturn("ISS-1234");
	}

	@Test(expected = NullPointerException.class)
	public void createWithNullDefaultColorThrowsNPE() {
		new AttachmentCreator(false, null, emptyList(), baseUri, 300);
	}

	@Test(expected = NullPointerException.class)
	public void createWithNullFieldCreatorsThrowsNPE() {
		new AttachmentCreator(false, DEFAULT_COLOR, null, baseUri, 300);
	}

	@Test(expected = NullPointerException.class)
	public void createWithNullBaseUriThrowsNPE() {
		new AttachmentCreator(false, DEFAULT_COLOR, emptyList(), null, 300);
	}

	@Test
	public void convertWithBlockerPrioritySetsBlockerColor() {
		when(mockIssue.getPriority()).thenReturn(new BasicPriority(null, 0L, "Blocker"));
		AttachmentCreator converter = new AttachmentCreator(true, DEFAULT_COLOR, emptyList(), baseUri, 300);

		ToRocketChatAttachment attachment = converter.create(mockIssue);

		assertThat(attachment.getColor()).isEqualTo(AttachmentCreator.BLOCKER_COLOR);
	}

	@Test
	public void convertWithoutPriorityColorsSetsDefaultColor() {
		AttachmentCreator converter = new AttachmentCreator(false, DEFAULT_COLOR, emptyList(), baseUri, 300);

		ToRocketChatAttachment attachment = converter.create(mockIssue);

		assertThat(attachment.getColor()).isEqualTo(DEFAULT_COLOR);
	}

	@Test
	public void convertWithUnknownPrioritySetsDefaultColor() {
		when(mockIssue.getPriority()).thenReturn(new BasicPriority(null, 0L, "Unknown"));
		AttachmentCreator converter = new AttachmentCreator(true, DEFAULT_COLOR, emptyList(), baseUri, 300);

		ToRocketChatAttachment attachment = converter.create(mockIssue);

		assertThat(attachment.getColor()).isEqualTo(DEFAULT_COLOR);
	}

	@Test
	public void convertWithSingleFieldCreator() {
		Field field = new Field("title", "value", true);
		AttachmentCreator converter = new AttachmentCreator(false, DEFAULT_COLOR, singletonList(issue -> field), baseUri, 300);

		ToRocketChatAttachment attachment = converter.create(mockIssue);

		assertThat(attachment.getFields()).containsExactly(field);
	}

	@Test
	public void htmlEncodedSummaryIsCleaned() {
		when(mockIssue.getSummary()).thenReturn("&lt;Fran&ccedil;ais&gt;");
		AttachmentCreator converter = new AttachmentCreator(false, DEFAULT_COLOR, emptyList(), baseUri, 300);

		ToRocketChatAttachment attachment = converter.create(mockIssue);

		assertThat(attachment.getTitle()).isEqualTo("ISS-1234 Fran\u00E7ais");
	}

	@Test
	public void htmlEncodedDescriptionIsCleaned() {
		when(mockIssue.getDescription()).thenReturn("&lt;Fran&ccedil;ais&gt;");
		AttachmentCreator converter = new AttachmentCreator(false, DEFAULT_COLOR, emptyList(), baseUri, 300);

		ToRocketChatAttachment attachment = converter.create(mockIssue);

		assertThat(attachment.getText()).isEqualTo("Fran\u00E7ais");
	}

	@Test
	public void descriptionIsEllipsized() {
		when(mockIssue.getDescription()).thenReturn("I am 18 chars long");
		AttachmentCreator converter = new AttachmentCreator(false, DEFAULT_COLOR, emptyList(), baseUri, 8);

		ToRocketChatAttachment attachment = converter.create(mockIssue);

		assertThat(attachment.getText()).isEqualTo("I am 18\u2026");
	}

	@Test
	public void linkIsCorrect() {
		AttachmentCreator converter = new AttachmentCreator(false, DEFAULT_COLOR, emptyList(), baseUri, 300);

		ToRocketChatAttachment attachment = converter.create(mockIssue);

		assertThat(attachment.getTitleLink()).isEqualTo("http://my.jira/browse/ISS-1234");
	}

	@Test(expected = IllegalArgumentException.class)
	public void nullElementInFieldCreatorThrowsException() {
		new AttachmentCreator(false, DEFAULT_COLOR, singletonList(null), baseUri, 300);
	}
}
