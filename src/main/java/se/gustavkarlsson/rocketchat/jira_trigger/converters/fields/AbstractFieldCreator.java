package se.gustavkarlsson.rocketchat.jira_trigger.converters.fields;

import com.atlassian.jira.rest.client.api.domain.Issue;
import se.gustavkarlsson.rocketchat.jira_trigger.models.Field;

import java.util.function.Function;

public abstract class AbstractFieldCreator implements Function<Issue, Field> {

	@Override
	public Field apply(Issue issue) {
		return new Field(getTitle(), getValue(issue), isShortValue());
	}

	protected abstract String getTitle();

	protected abstract String getValue(Issue issue);

	protected abstract boolean isShortValue();
}
