package se.gustavkarlsson.rocketchat.jira_trigger.converters;

import com.atlassian.jira.rest.client.api.domain.BasicPriority;
import com.atlassian.jira.rest.client.api.domain.Issue;
import org.junit.Before;
import org.junit.Test;
import se.gustavkarlsson.rocketchat.jira_trigger.configuration.MessageConfiguration;
import se.gustavkarlsson.rocketchat.models.to_rocket_chat.Field;
import se.gustavkarlsson.rocketchat.models.to_rocket_chat.ToRocketChatAttachment;

import java.net.URI;

import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ToRocketChatAttachmentConverterTest {
	private static final URI DUMMY_URI = URI.create("http://somejira");

	private MessageConfiguration mockConfig;
	private Issue mockIssue;

	@Before
	public void setUp() {
		mockConfig = mock(MessageConfiguration.class);
		mockIssue = mock(Issue.class);
		when(mockIssue.getKey()).thenReturn("ISS-1234");
	}

	@Test(expected = NullPointerException.class)
	public void createWithNullConfigThrowsNPE() {
		new AttachmentConverter(null, emptyList(), emptyList(), DUMMY_URI);
	}

	@Test(expected = NullPointerException.class)
	public void createWithNullDefaultFieldCreatorsThrowsNPE() {
		new AttachmentConverter(mockConfig, null, emptyList(), DUMMY_URI);
	}

	@Test(expected = NullPointerException.class)
	public void createWithNullExtendedFieldCreatorsConfigThrowsNPE() {
		new AttachmentConverter(mockConfig, emptyList(), null, DUMMY_URI);
	}

	@Test(expected = NullPointerException.class)
	public void createWithNullBaseUriThrowsNPE() {
		new AttachmentConverter(mockConfig, emptyList(), emptyList(), null);
	}

	@Test
	public void convertWithBlockerPrioritySetsBlockerColor() {
		when(mockIssue.getPriority()).thenReturn(new BasicPriority(null, 0L, "Blocker"));
		when(mockConfig.isPriorityColors()).thenReturn(true);
		AttachmentConverter converter = new AttachmentConverter(mockConfig, emptyList(), emptyList(), DUMMY_URI);

		ToRocketChatAttachment attachment = converter.convert(mockIssue, false);

		assertThat(attachment.getColor()).isEqualTo(AttachmentConverter.BLOCKER_COLOR);
	}

	@Test
	public void convertWithoutPriorityColorsSetsDefaultColor() {
		String defaultColor = "#F24678";
		when(mockConfig.isPriorityColors()).thenReturn(false);
		when(mockConfig.getDefaultColor()).thenReturn(defaultColor);
		when(mockIssue.getPriority()).thenReturn(new BasicPriority(null, 0L, "Blocker"));
		AttachmentConverter converter = new AttachmentConverter(mockConfig, emptyList(), emptyList(), DUMMY_URI);

		ToRocketChatAttachment attachment = converter.convert(mockIssue, false);

		assertThat(attachment.getColor()).isEqualTo(defaultColor);
	}

	@Test
	public void convertWithUnknownPrioritySetsDefaultColor() {
		String defaultColor = "#F24678";
		when(mockConfig.isPriorityColors()).thenReturn(true);
		when(mockConfig.getDefaultColor()).thenReturn(defaultColor);
		when(mockIssue.getPriority()).thenReturn(new BasicPriority(null, 0L, "Unknown"));
		AttachmentConverter converter = new AttachmentConverter(mockConfig, emptyList(), emptyList(), DUMMY_URI);

		ToRocketChatAttachment attachment = converter.convert(mockIssue, false);

		assertThat(attachment.getColor()).isEqualTo(defaultColor);
	}

	@Test
	public void convertWithSingleDefaultFieldCreator() {
		Field field = new Field("title", "value", true);
		AttachmentConverter converter = new AttachmentConverter(mockConfig, singletonList(issue -> field), emptyList(), DUMMY_URI);

		ToRocketChatAttachment attachment = converter.convert(mockIssue, false);

		assertThat(attachment.getFields()).containsExactly(field);
	}

	@Test
	public void convertWithSingleExtendedFieldCreator() {
		Field field = new Field("title", "value", true);
		AttachmentConverter converter = new AttachmentConverter(mockConfig, emptyList(), singletonList(issue -> field), DUMMY_URI);

		ToRocketChatAttachment attachment = converter.convert(mockIssue, true);

		assertThat(attachment.getFields()).containsExactly(field);
	}

	@Test
	public void htmlEndodedSummaryIsCleaned() {
		when(mockIssue.getSummary()).thenReturn("&lt;Fran&ccedil;ais&gt;");
		AttachmentConverter converter = new AttachmentConverter(mockConfig, emptyList(), emptyList(), DUMMY_URI);

		ToRocketChatAttachment attachment = converter.convert(mockIssue, false);

		assertThat(attachment.getText()).isEqualTo("<http://somejira/browse/ISS-1234|Français>");
	}

	@Test
	public void nonRootJiraUriCreatesCorrectLink() {
		when(mockIssue.getSummary()).thenReturn("&lt;Fran&ccedil;ais&gt;");
		AttachmentConverter converter = new AttachmentConverter(mockConfig, emptyList(), emptyList(), URI.create("http://some/dummy/jira"));

		ToRocketChatAttachment attachment = converter.convert(mockIssue, false);

		assertThat(attachment.getText()).isEqualTo("<http://some/dummy/jira/browse/ISS-1234|Français>");
	}

	@Test
	public void create() {
		new AttachmentConverter(mockConfig, emptyList(), emptyList(), DUMMY_URI);
	}
}
