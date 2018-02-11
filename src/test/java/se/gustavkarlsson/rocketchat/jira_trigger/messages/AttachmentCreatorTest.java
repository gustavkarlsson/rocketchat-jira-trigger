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
import static se.gustavkarlsson.rocketchat.jira_trigger.routes.IssueDetail.EXTENDED;
import static se.gustavkarlsson.rocketchat.jira_trigger.routes.IssueDetail.NORMAL;

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
		new AttachmentCreator(false, null, emptyList(), emptyList(), baseUri);
	}

	@Test(expected = NullPointerException.class)
	public void createWithNullDefaultFieldCreatorsThrowsNPE() {
		new AttachmentCreator(false, DEFAULT_COLOR, null, emptyList(), baseUri);
	}

	@Test(expected = NullPointerException.class)
	public void createWithNullExtendedFieldCreatorsConfigThrowsNPE() {
		new AttachmentCreator(false, DEFAULT_COLOR, emptyList(), null, baseUri);
	}

	@Test(expected = NullPointerException.class)
	public void createWithNullBaseUriThrowsNPE() {
		new AttachmentCreator(false, DEFAULT_COLOR, emptyList(), emptyList(), null);
	}

	@Test
	public void convertWithBlockerPrioritySetsBlockerColor() {
		when(mockIssue.getPriority()).thenReturn(new BasicPriority(null, 0L, "Blocker"));
		AttachmentCreator converter = new AttachmentCreator(true, DEFAULT_COLOR, emptyList(), emptyList(), baseUri);

		ToRocketChatAttachment attachment = converter.create(mockIssue, NORMAL);

		assertThat(attachment.getColor()).isEqualTo(AttachmentCreator.BLOCKER_COLOR);
	}

	@Test
	public void convertWithoutPriorityColorsSetsDefaultColor() {
		AttachmentCreator converter = new AttachmentCreator(false, DEFAULT_COLOR, emptyList(), emptyList(), baseUri);

		ToRocketChatAttachment attachment = converter.create(mockIssue, NORMAL);

		assertThat(attachment.getColor()).isEqualTo(DEFAULT_COLOR);
	}

	@Test
	public void convertWithUnknownPrioritySetsDefaultColor() {
		when(mockIssue.getPriority()).thenReturn(new BasicPriority(null, 0L, "Unknown"));
		AttachmentCreator converter = new AttachmentCreator(true, DEFAULT_COLOR, emptyList(), emptyList(), baseUri);

		ToRocketChatAttachment attachment = converter.create(mockIssue, NORMAL);

		assertThat(attachment.getColor()).isEqualTo(DEFAULT_COLOR);
	}

	@Test
	public void convertWithSingleDefaultFieldCreator() {
		Field field = new Field("title", "value", true);
		AttachmentCreator converter = new AttachmentCreator(false, DEFAULT_COLOR, singletonList(issue -> field), emptyList(), baseUri);

		ToRocketChatAttachment attachment = converter.create(mockIssue, NORMAL);

		assertThat(attachment.getFields()).containsExactly(field);
	}

	@Test
	public void convertWithSingleExtendedFieldCreator() {
		Field field = new Field("title", "value", true);
		AttachmentCreator converter = new AttachmentCreator(false, DEFAULT_COLOR, emptyList(), singletonList(issue -> field), baseUri);

		ToRocketChatAttachment attachment = converter.create(mockIssue, EXTENDED);

		assertThat(attachment.getFields()).containsExactly(field);
	}

	@Test
	public void htmlEncodedSummaryIsCleaned() {
		when(mockIssue.getSummary()).thenReturn("&lt;Fran&ccedil;ais&gt;");
		AttachmentCreator converter = new AttachmentCreator(false, DEFAULT_COLOR, emptyList(), emptyList(), baseUri);

		ToRocketChatAttachment attachment = converter.create(mockIssue, NORMAL);

		assertThat(attachment.getText()).isEqualTo("<http://my.jira/browse/ISS-1234|Français>");
	}

	@Test(expected = IllegalArgumentException.class)
	public void nullElementInDefaultFieldCreatorThrowsException() {
		new AttachmentCreator(false, DEFAULT_COLOR, singletonList(null), emptyList(), baseUri);
	}

	@Test(expected = IllegalArgumentException.class)
	public void nullElementInExtendedFieldCreatorThrowsException() {
		new AttachmentCreator(false, DEFAULT_COLOR, emptyList(), singletonList(null), baseUri);
	}
}
