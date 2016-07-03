package se.gustavkarlsson.rocketchat.jira_trigger.converters.fields;

import com.atlassian.jira.rest.client.api.domain.Issue;
import com.atlassian.jira.rest.client.api.domain.Priority;
import org.junit.Before;
import org.junit.Test;
import se.gustavkarlsson.rocketchat.jira_trigger.models.Field;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PriorityFieldCreatorTest {

	private Issue mockIssue;
	private Priority mockPrio;
	private PriorityFieldCreator creator;

	@Before
	public void setUp() throws Exception {
		mockIssue = mock(Issue.class);
		mockPrio = mock(Priority.class);
		creator = new PriorityFieldCreator();
	}

	@Test(expected = NullPointerException.class)
	public void applyNullThrowsNPE() throws Exception {
		creator.apply(null);
	}

	@Test
	public void applyReturnsCorrectField() throws Exception {
		String prio = "Blocker";
		when(mockIssue.getPriority()).thenReturn(mockPrio);
		when(mockPrio.getName()).thenReturn(prio);

		Field field = creator.apply(mockIssue);

		assertThat(field.getValue()).isEqualTo(prio);
	}

	@Test
	public void applyWithNullPrioritySetsNonEmptyValue() throws Exception {
		when(mockIssue.getPriority()).thenReturn(null);

		Field field = creator.apply(mockIssue);

		assertThat(field.getValue()).isNotNull();
	}
}
