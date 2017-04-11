package se.gustavkarlsson.rocketchat.jira_trigger.converters.fields;

import com.atlassian.jira.rest.client.api.domain.Issue;

public class TypeFieldExtractor extends AbstractFieldExtractor {

	@Override
	protected String getTitle() {
		return "Type";
	}

	@Override
	protected String getValue(Issue issue) {
		return issue.getIssueType().getName();
	}

	@Override
	protected boolean isShortValue() {
		return true;
	}
}
