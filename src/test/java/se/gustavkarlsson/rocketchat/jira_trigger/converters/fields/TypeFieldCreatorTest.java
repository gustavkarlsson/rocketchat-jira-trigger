package se.gustavkarlsson.rocketchat.jira_trigger.converters.fields;

import com.atlassian.jira.rest.client.api.domain.Issue;
import com.atlassian.jira.rest.client.api.domain.IssueType;
import org.junit.Before;
import org.junit.Test;
import se.gustavkarlsson.rocketchat.jira_trigger.models.Field;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TypeFieldCreatorTest {

	private Issue mockIssue;
	private IssueType type;
	private TypeFieldCreator creator;

	@Before
	public void setUp() throws Exception {
		mockIssue = mock(Issue.class);
		type = mock(IssueType.class);
		creator = new TypeFieldCreator();
	}

	@Test(expected = NullPointerException.class)
	public void applyNullThrowsNPE() throws Exception {
		creator.apply(null);
	}

	@Test
	public void applyReturnsCorrectField() throws Exception {
		String type = "Bug";
		when(mockIssue.getIssueType()).thenReturn(this.type);
		when(this.type.getName()).thenReturn(type);

		Field field = creator.apply(mockIssue);

		assertThat(field.getValue()).isEqualTo(type);
	}
}
