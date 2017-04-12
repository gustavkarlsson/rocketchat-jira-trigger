package se.gustavkarlsson.rocketchat.jira_trigger.converters.fields;

import com.atlassian.jira.rest.client.api.domain.Issue;
import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;
import se.gustavkarlsson.rocketchat.models.to_rocket_chat.Field;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UpdatedFieldExtractorTest {

	private Issue mockIssue;
	private DateTime dateTime;
	private DateFormat defaultDateFormat;
	private UpdatedFieldExtractor creator;

	@Before
	public void setUp() throws Exception {
		mockIssue = mock(Issue.class);
		dateTime = new DateTime();
		defaultDateFormat = SimpleDateFormat.getDateInstance();
		creator = new UpdatedFieldExtractor(defaultDateFormat);
	}

	@Test(expected = NullPointerException.class)
	public void createWithNullDateFormatThrowsNPE() throws Exception {
		new CreatedFieldExtractor(null);
	}

	@Test(expected = NullPointerException.class)
	public void applyNullThrowsNPE() throws Exception {
		creator.create(null);
	}

	@Test
	public void applyReturnsCorrectField() throws Exception {
		when(mockIssue.getUpdateDate()).thenReturn(dateTime);

		Field field = creator.create(mockIssue);

		assertThat(field.getValue()).isEqualTo(defaultDateFormat.format(dateTime.toDate()));
	}
}
