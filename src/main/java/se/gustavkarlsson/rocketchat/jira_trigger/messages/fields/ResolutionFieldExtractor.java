package se.gustavkarlsson.rocketchat.jira_trigger.messages.fields;

import com.atlassian.jira.rest.client.api.domain.Issue;
import com.atlassian.jira.rest.client.api.domain.Resolution;

public class ResolutionFieldExtractor extends AbstractFieldExtractor {

	@Override
	protected String getTitle() {
		return "Resolution";
	}

	@Override
	protected String getValue(Issue issue) {
		Resolution resolution = issue.getResolution();
		return resolution == null ? "None" : resolution.getName();
	}

	@Override
	protected boolean isShortValue() {
		return true;
	}
}
