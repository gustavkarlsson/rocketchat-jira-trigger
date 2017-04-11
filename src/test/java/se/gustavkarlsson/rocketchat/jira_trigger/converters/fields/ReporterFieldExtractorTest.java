package se.gustavkarlsson.rocketchat.jira_trigger.converters.fields;

import com.atlassian.jira.rest.client.api.domain.Issue;
import com.atlassian.jira.rest.client.api.domain.User;
import org.junit.Before;
import org.junit.Test;
import se.gustavkarlsson.rocketchat.jira_trigger.models.to_rocket_chat.Field;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ReporterFieldExtractorTest {

	private Issue mockIssue;
	private User mockUser;
	private ReporterFieldExtractor creator;

	@Before
	public void setUp() throws Exception {
		mockIssue = mock(Issue.class);
		mockUser = mock(User.class);
		creator = new ReporterFieldExtractor();
	}

	@Test(expected = NullPointerException.class)
	public void applyNullThrowsNPE() throws Exception {
		creator.create(null);
	}

	@Test
	public void applyReturnsCorrectField() throws Exception {
		String name = "Someone";
		when(mockIssue.getReporter()).thenReturn(mockUser);
		when(mockUser.getName()).thenReturn(name);

		Field field = creator.create(mockIssue);

		assertThat(field.getValue()).isEqualTo(name);
	}

	@Test
	public void applyWithNullReporterSetsNonEmptyValue() throws Exception {
		when(mockIssue.getReporter()).thenReturn(null);

		Field field = creator.create(mockIssue);

		assertThat(field.getValue()).isNotEmpty();
	}
}
