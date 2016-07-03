package se.gustavkarlsson.rocketchat.jira_trigger.converters.fields;

import com.atlassian.jira.rest.client.api.domain.Issue;
import se.gustavkarlsson.rocketchat.jira_trigger.models.Field;

abstract class AbstractFieldCreator implements FieldCreator {

	@Override
	public Field apply(Issue issue) {
		return new Field(getTitle(), getValue(issue), isShortValue());
	}

	protected abstract String getTitle();

	protected abstract String getValue(Issue issue);

	protected abstract boolean isShortValue();
}
