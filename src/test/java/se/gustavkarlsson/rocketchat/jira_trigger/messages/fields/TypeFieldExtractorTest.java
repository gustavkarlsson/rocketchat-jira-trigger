package se.gustavkarlsson.rocketchat.jira_trigger.messages.fields;

import com.atlassian.jira.rest.client.api.domain.Issue;
import com.atlassian.jira.rest.client.api.domain.IssueType;
import org.junit.Before;
import org.junit.Test;
import se.gustavkarlsson.rocketchat.models.to_rocket_chat.Field;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TypeFieldExtractorTest {

	private Issue mockIssue;
	private IssueType type;
	private TypeFieldExtractor extractor;

	@Before
	public void setUp() throws Exception {
		mockIssue = mock(Issue.class);
		type = mock(IssueType.class);
		extractor = new TypeFieldExtractor();
	}

	@Test(expected = NullPointerException.class)
	public void createNullThrowsNPE() throws Exception {
		extractor.create(null);
	}

	@Test
	public void createReturnsCorrectField() throws Exception {
		String type = "Bug";
		when(mockIssue.getIssueType()).thenReturn(this.type);
		when(this.type.getName()).thenReturn(type);

		Field field = extractor.create(mockIssue);

		assertThat(field.getValue()).isEqualTo(type);
	}
}
