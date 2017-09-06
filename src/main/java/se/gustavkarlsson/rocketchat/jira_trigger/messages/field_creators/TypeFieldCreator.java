package se.gustavkarlsson.rocketchat.jira_trigger.messages.field_creators;

import com.atlassian.jira.rest.client.api.domain.Issue;
import com.atlassian.jira.rest.client.api.domain.IssueType;

public class TypeFieldCreator extends AbstractFieldCreator {

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
