package se.gustavkarlsson.rocketchat.jira_trigger.messages.fields;

import com.atlassian.jira.rest.client.api.domain.Issue;
import com.atlassian.jira.rest.client.api.domain.IssueType;

public class TypeFieldExtractor extends AbstractFieldExtractor {

	@Override
	protected String getTitle() {
		return "Type";
	}

	@Override
	protected String getValue(Issue issue) {
		IssueType issueType = issue.getIssueType();
		return issueType == null ? "-" : issueType.getName();
	}

	@Override
	protected boolean isShortValue() {
		return true;
	}
}
