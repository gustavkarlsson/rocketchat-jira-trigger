package se.gustavkarlsson.rocketchat.jira_trigger.converters.fields;

import com.atlassian.jira.rest.client.api.domain.Issue;

public class StatusFieldCreator extends AbstractFieldCreator {
	@Override
	protected String getTitle() {
		return "Status";
	}

	@Override
	protected String getValue(Issue issue) {
		return issue.getStatus().getName();
	}

	@Override
	protected boolean isShortValue() {
		return true;
	}
}
