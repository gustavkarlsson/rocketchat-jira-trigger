package se.gustavkarlsson.rocketchat.jira_trigger.messages.fields;

import com.atlassian.jira.rest.client.api.domain.Issue;
import se.gustavkarlsson.rocketchat.models.to_rocket_chat.Field;

abstract class AbstractFieldExtractor implements FieldExtractor {

	@Override
	public Field create(Issue issue) {
		return new Field(getTitle(), getValue(issue), isShortValue());
	}

	protected abstract String getTitle();

	protected abstract String getValue(Issue issue);

	protected abstract boolean isShortValue();
}
