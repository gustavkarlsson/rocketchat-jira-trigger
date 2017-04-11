package se.gustavkarlsson.rocketchat.jira_trigger.converters.fields;

import com.atlassian.jira.rest.client.api.domain.Issue;
import se.gustavkarlsson.rocketchat.jira_trigger.models.to_rocket_chat.Field;

public interface FieldExtractor {
	Field create(Issue issue);
}
