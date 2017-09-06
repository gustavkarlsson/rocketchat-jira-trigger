package se.gustavkarlsson.rocketchat.jira_trigger.messages.field_creators;

import com.atlassian.jira.rest.client.api.domain.Issue;

public class DescriptionFieldCreator extends AbstractFieldCreator {

	@Override
	protected String getTitle() {
		return "Description";
	}

	@Override
	protected String getValue(Issue issue) {
		String description = issue.getDescription();
		return description == null ? "-" : description;
	}

	@Override
	protected boolean isShortValue() {
		return false;
	}
}
