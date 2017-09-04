package se.gustavkarlsson.rocketchat.jira_trigger.messages.fields;

import com.atlassian.jira.rest.client.api.domain.Issue;

public class DescriptionFieldExtractor extends AbstractFieldExtractor {

	@Override
	protected String getTitle() {
		return "Description";
	}

	@Override
	protected String getValue(Issue issue) {
		String description = issue.getDescription();
		return description == null ? "None" : description;
	}

	@Override
	protected boolean isShortValue() {
		return false;
	}
}
