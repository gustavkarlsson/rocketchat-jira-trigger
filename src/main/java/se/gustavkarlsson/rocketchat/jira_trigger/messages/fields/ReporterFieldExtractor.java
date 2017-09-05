package se.gustavkarlsson.rocketchat.jira_trigger.messages.fields;

import com.atlassian.jira.rest.client.api.domain.Issue;
import com.atlassian.jira.rest.client.api.domain.User;

public class ReporterFieldExtractor extends AbstractFieldExtractor {

	private final boolean useRealName;

	public ReporterFieldExtractor(boolean useRealName) {
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
