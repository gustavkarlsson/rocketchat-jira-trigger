package se.gustavkarlsson.rocketchat.jira_trigger.messages.field_creators;

import com.atlassian.jira.rest.client.api.domain.Issue;
import com.atlassian.jira.rest.client.api.domain.User;

public class ReporterFieldCreator extends AbstractFieldCreator {

	private final boolean useRealName;

	public ReporterFieldCreator(boolean useRealName) {
		this.useRealName = useRealName;
	}
	@Override
	protected String getTitle() {
		return "Reporter";
	}

	@Override
	protected String getValue(Issue issue) {
		User reporter = issue.getReporter();
		if (reporter == null) {
			return "-";
		}
		return useRealName ? reporter.getDisplayName() : reporter.getName();
	}

	@Override
	protected boolean isShortValue() {
		return true;
	}
}
