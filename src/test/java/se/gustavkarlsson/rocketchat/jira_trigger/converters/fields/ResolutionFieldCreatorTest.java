package se.gustavkarlsson.rocketchat.jira_trigger.converters.fields;

import com.atlassian.jira.rest.client.api.domain.Issue;
import com.atlassian.jira.rest.client.api.domain.Resolution;
import org.junit.Before;
import org.junit.Test;
import se.gustavkarlsson.rocketchat.jira_trigger.models.Field;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ResolutionFieldCreatorTest {

	private Issue mockIssue;
	private Resolution resolution;
	private ResolutionFieldCreator creator;

	@Before
	public void setUp() throws Exception {
		mockIssue = mock(Issue.class);
		resolution = mock(Resolution.class);
		creator = new ResolutionFieldCreator();
	}

	@Test(expected = NullPointerException.class)
	public void applyNullThrowsNPE() throws Exception {
		creator.apply(null);
	}

	@Test
	public void applyReturnsCorrectField() throws Exception {
		String type = "Bug";
		when(mockIssue.getResolution()).thenReturn(this.resolution);
		when(this.resolution.getName()).thenReturn(type);

		Field field = creator.apply(mockIssue);

		assertThat(field.getValue()).isEqualTo(type);
	}

	@Test
	public void applyWithNullResolutionSetsNonEmptyValue() throws Exception {
		when(mockIssue.getResolution()).thenReturn(null);

		Field field = creator.apply(mockIssue);

		assertThat(field.getValue()).isNotNull();
	}
}
