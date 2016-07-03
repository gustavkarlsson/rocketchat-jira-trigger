package se.gustavkarlsson.rocketchat.jira_trigger.converters.fields;

import com.atlassian.jira.rest.client.api.domain.Issue;
import com.atlassian.jira.rest.client.api.domain.User;

public class AssigneeFieldCreator extends AbstractFieldCreator {
	@Override
	protected String getTitle() {
		return "Assigned To";
	}

	@Override
	protected String getValue(Issue issue) {
		User assignee = issue.getAssignee();
		return assignee == null ? "Unassigned" : assignee.getName();
	}

	@Override
	protected boolean isShortValue() {
		return true;
	}
}
