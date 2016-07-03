package se.gustavkarlsson.rocketchat.jira_trigger.converters.fields;

import com.atlassian.jira.rest.client.api.domain.Issue;
import com.atlassian.jira.rest.client.api.domain.User;

public class ReporterFieldCreator extends AbstractFieldCreator {
	@Override
	protected String getTitle() {
		return "Reporter";
	}

	@Override
	protected String getValue(Issue issue) {
		User reporter = issue.getReporter();
		return reporter == null ? "No reporter" : reporter.getName();
	}

	@Override
	protected boolean isShortValue() {
		return true;
	}
}
