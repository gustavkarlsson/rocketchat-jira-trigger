package se.gustavkarlsson.rocketchat.jira_trigger.converters.fields;

import com.atlassian.jira.rest.client.api.domain.Issue;
import com.atlassian.jira.rest.client.api.domain.User;
import org.junit.Before;
import org.junit.Test;
import se.gustavkarlsson.rocketchat.models.to_rocket_chat.Field;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class AssigneeFieldExtractorTest {

	private Issue mockIssue;
	private User mockUser;
	private AssigneeFieldExtractor creator;

	@Before
	public void setUp() throws Exception {
		mockIssue = mock(Issue.class);
		mockUser = mock(User.class);
		creator = new AssigneeFieldExtractor();
	}

	@Test(expected = NullPointerException.class)
	public void applyNullThrowsNPE() throws Exception {
		creator.create(null);
	}

	@Test
	public void applyReturnsCorrectField() throws Exception {
		String name = "Someone";
		when(mockIssue.getAssignee()).thenReturn(mockUser);
		when(mockUser.getName()).thenReturn(name);

		Field field = creator.create(mockIssue);

		assertThat(field.getValue()).isEqualTo(name);
	}

	@Test
	public void applyWithNullAssigneeSetsNonEmptyValue() throws Exception {
		when(mockIssue.getAssignee()).thenReturn(null);

		Field field = creator.create(mockIssue);

		assertThat(field.getValue()).isNotEmpty();
	}
}
