package se.gustavkarlsson.rocketchat.jira_trigger.messages.field_creators;

import com.atlassian.jira.rest.client.api.domain.Issue;
import com.atlassian.jira.rest.client.api.domain.IssueField;

import java.util.stream.StreamSupport;

public class FallbackFieldCreator extends AbstractFieldCreator {

	private String fieldName;

	public FallbackFieldCreator(String fieldName) {
		this.fieldName = fieldName;
	}

	@Override
	protected String getTitle() {
		return fieldName;
	}

	@Override
	protected String getValue(Issue issue) {
		return StreamSupport.stream(issue.getFields().spliterator(), false)
				.filter(f -> f.getId().equalsIgnoreCase(fieldName) || f.getName().equalsIgnoreCase(fieldName))
				.findFirst()
				.map(IssueField::getValue)
				.map(Object::toString)
				.orElse("-");
	}

	@Override
	protected boolean isShortValue() {
		return true;
	}
}
