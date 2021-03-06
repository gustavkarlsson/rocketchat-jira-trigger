package se.gustavkarlsson.rocketchat.jira_trigger.messages.field_creators;

import com.atlassian.jira.rest.client.api.domain.Issue;
import com.atlassian.jira.rest.client.api.domain.Status;

public class StatusFieldCreator extends AbstractFieldCreator {
	@Override
	protected String getTitle() {
		return "Status";
	}

	@Override
	protected String getValue(Issue issue) {
		Status status = issue.getStatus();
		return status == null ? "-" : status.getName();
	}

	@Override
	protected boolean isShortValue() {
		return true;
	}
}
