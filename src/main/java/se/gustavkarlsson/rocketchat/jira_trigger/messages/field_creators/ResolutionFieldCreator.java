package se.gustavkarlsson.rocketchat.jira_trigger.messages.field_creators;

import com.atlassian.jira.rest.client.api.domain.Issue;
import com.atlassian.jira.rest.client.api.domain.Resolution;

public class ResolutionFieldCreator extends AbstractFieldCreator {

	@Override
	protected String getTitle() {
		return "Resolution";
	}

	@Override
	protected String getValue(Issue issue) {
		Resolution resolution = issue.getResolution();
		return resolution == null ? "-" : resolution.getName();
	}

	@Override
	protected boolean isShortValue() {
		return true;
	}
}
