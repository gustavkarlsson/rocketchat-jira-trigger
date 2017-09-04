package se.gustavkarlsson.rocketchat.jira_trigger.messages.fields;

import com.atlassian.jira.rest.client.api.domain.Issue;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormatter;
import org.junit.Before;
import org.junit.Test;
import se.gustavkarlsson.rocketchat.models.to_rocket_chat.Field;

import static org.assertj.core.api.Assertions.assertThat;
import static org.joda.time.format.DateTimeFormat.mediumDate;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UpdatedFieldExtractorTest {

	private Issue mockIssue;
	private DateTime dateTime;
	private DateTimeFormatter defaultDateFormatter;
	private UpdatedFieldExtractor extractor;

	@Before
	public void setUp() throws Exception {
		mockIssue = mock(Issue.class);
		dateTime = new DateTime();
		defaultDateFormatter = mediumDate();
		extractor = new UpdatedFieldExtractor(defaultDateFormatter);
	}

	@Test(expected = NullPointerException.class)
	public void createWithNullDateFormatThrowsNPE() throws Exception {
		new CreatedFieldExtractor(null);
	}

	@Test(expected = NullPointerException.class)
	public void createNullThrowsNPE() throws Exception {
		extractor.create(null);
	}

	@Test
	public void createReturnsCorrectField() throws Exception {
		when(mockIssue.getUpdateDate()).thenReturn(dateTime);

		Field field = extractor.create(mockIssue);

		assertThat(field.getValue()).isEqualTo(defaultDateFormatter.print(dateTime));
	}
}
