package se.gustavkarlsson.rocketchat.jira_trigger.messages.field_creators;

import com.atlassian.jira.rest.client.api.domain.BasicPriority;
import com.atlassian.jira.rest.client.api.domain.Issue;

public class PriorityFieldCreator extends AbstractFieldCreator {
	@Override
	protected String getTitle() {
		return "Priority";
	}

	@Override
	protected String getValue(Issue issue) {
		BasicPriority priority = issue.getPriority();
		return priority == null ? "-" : priority.getName();
	}

	@Override
	protected boolean isShortValue() {
		return true;
	}
}
