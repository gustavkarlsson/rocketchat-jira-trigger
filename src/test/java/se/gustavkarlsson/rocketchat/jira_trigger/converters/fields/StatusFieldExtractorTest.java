package se.gustavkarlsson.rocketchat.jira_trigger.converters.fields;

import com.atlassian.jira.rest.client.api.domain.Issue;
import com.atlassian.jira.rest.client.api.domain.Status;
import org.junit.Before;
import org.junit.Test;
import se.gustavkarlsson.rocketchat.jira_trigger.models.to_rocket_chat.Field;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class StatusFieldExtractorTest {

	private Issue mockIssue;
	private Status mockStatus;
	private StatusFieldExtractor creator;

	@Before
	public void setUp() throws Exception {
		mockIssue = mock(Issue.class);
		mockStatus = mock(Status.class);
		creator = new StatusFieldExtractor();
	}

	@Test(expected = NullPointerException.class)
	public void applyNullThrowsNPE() throws Exception {
		creator.create(null);
	}

	@Test
	public void applyReturnsCorrectField() throws Exception {
		String status = "Open";
		when(mockIssue.getStatus()).thenReturn(mockStatus);
		when(mockStatus.getName()).thenReturn(status);

		Field field = creator.create(mockIssue);

		assertThat(field.getValue()).isEqualTo(status);
	}
}
