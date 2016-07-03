package se.gustavkarlsson.rocketchat.jira_trigger.converters.fields;

import com.atlassian.jira.rest.client.api.domain.Issue;
import com.atlassian.jira.rest.client.api.domain.User;
import org.junit.Before;
import org.junit.Test;
import se.gustavkarlsson.rocketchat.jira_trigger.models.Field;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ReporterFieldCreatorTest {

	private Issue mockIssue;
	private User mockUser;
	private ReporterFieldCreator creator;

	@Before
	public void setUp() throws Exception {
		mockIssue = mock(Issue.class);
		mockUser = mock(User.class);
		creator = new ReporterFieldCreator();
	}

	@Test(expected = NullPointerException.class)
	public void applyNullThrowsNPE() throws Exception {
		creator.apply(null);
	}

	@Test
	public void applyReturnsCorrectField() throws Exception {
		String name = "Someone";
		when(mockIssue.getReporter()).thenReturn(mockUser);
		when(mockUser.getName()).thenReturn(name);

		Field field = creator.apply(mockIssue);

		assertThat(field.getValue()).isEqualTo(name);
	}

	@Test
	public void applyWithNullReporterSetsNonEmptyValue() throws Exception {
		when(mockIssue.getReporter()).thenReturn(null);

		Field field = creator.apply(mockIssue);

		assertThat(field.getValue()).isNotEmpty();
	}
}
