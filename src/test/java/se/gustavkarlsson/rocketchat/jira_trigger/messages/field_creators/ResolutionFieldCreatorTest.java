package se.gustavkarlsson.rocketchat.jira_trigger.messages.field_creators;

import com.atlassian.jira.rest.client.api.domain.Issue;
import com.atlassian.jira.rest.client.api.domain.Resolution;
import org.junit.Before;
import org.junit.Test;
import se.gustavkarlsson.rocketchat.models.to_rocket_chat.Field;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ResolutionFieldCreatorTest {

	private Issue mockIssue;
	private Resolution resolution;
	private ResolutionFieldCreator extractor;

	@Before
	public void setUp() throws Exception {
		mockIssue = mock(Issue.class);
		resolution = mock(Resolution.class);
		extractor = new ResolutionFieldCreator();
	}

	@Test(expected = NullPointerException.class)
	public void createNullThrowsNPE() throws Exception {
		extractor.create(null);
	}

	@Test
	public void createReturnsCorrectField() throws Exception {
		String type = "Bug";
		when(mockIssue.getResolution()).thenReturn(this.resolution);
		when(this.resolution.getName()).thenReturn(type);

		Field field = extractor.create(mockIssue);

		assertThat(field.getValue()).isEqualTo(type);
	}

	@Test
	public void createWithNullResolutionSetsNonEmptyValue() throws Exception {
		when(mockIssue.getResolution()).thenReturn(null);

		Field field = extractor.create(mockIssue);

		assertThat(field.getValue()).isNotNull();
	}
}
