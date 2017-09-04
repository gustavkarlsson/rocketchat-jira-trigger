package se.gustavkarlsson.rocketchat.jira_trigger.messages.fields;

import com.atlassian.jira.rest.client.api.domain.Issue;
import org.junit.Before;
import org.junit.Test;
import se.gustavkarlsson.rocketchat.models.to_rocket_chat.Field;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class DescriptionFieldExtractorTest {

	private Issue mockIssue;
	private DescriptionFieldExtractor extractor;

	@Before
	public void setUp() throws Exception {
		mockIssue = mock(Issue.class);
		extractor = new DescriptionFieldExtractor();
	}

	@Test(expected = NullPointerException.class)
	public void createNullThrowsNPE() throws Exception {
		extractor.create(null);
	}

	@Test
	public void createReturnsCorrectField() throws Exception {
		String description = "Description of the issue";
		when(mockIssue.getDescription()).thenReturn(description);

		Field field = extractor.create(mockIssue);

		assertThat(field.getValue()).isEqualTo(description);
	}

	@Test
	public void createWithNullPrioritySetsNonEmptyValue() throws Exception {
		when(mockIssue.getDescription()).thenReturn(null);

		Field field = extractor.create(mockIssue);

		assertThat(field.getValue()).isNotNull();
	}
}
