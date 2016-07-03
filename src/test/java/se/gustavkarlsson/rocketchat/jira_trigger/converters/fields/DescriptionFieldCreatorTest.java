package se.gustavkarlsson.rocketchat.jira_trigger.converters.fields;

import com.atlassian.jira.rest.client.api.domain.Issue;
import org.junit.Before;
import org.junit.Test;
import se.gustavkarlsson.rocketchat.jira_trigger.models.Field;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class DescriptionFieldCreatorTest {

	private Issue mockIssue;
	private DescriptionFieldCreator creator;

	@Before
	public void setUp() throws Exception {
		mockIssue = mock(Issue.class);
		creator = new DescriptionFieldCreator();
	}

	@Test(expected = NullPointerException.class)
	public void applyNullThrowsNPE() throws Exception {
		creator.apply(null);
	}

	@Test
	public void applyReturnsCorrectField() throws Exception {
		String description = "Description of the issue";
		when(mockIssue.getDescription()).thenReturn(description);

		Field field = creator.apply(mockIssue);

		assertThat(field.getValue()).isEqualTo(description);
	}

	@Test
	public void applyWithNullPrioritySetsNonEmptyValue() throws Exception {
		when(mockIssue.getDescription()).thenReturn(null);

		Field field = creator.apply(mockIssue);

		assertThat(field.getValue()).isNotNull();
	}
}
