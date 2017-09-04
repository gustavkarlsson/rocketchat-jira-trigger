package se.gustavkarlsson.rocketchat.jira_trigger.messages.fields;

import com.atlassian.jira.rest.client.api.domain.Issue;
import se.gustavkarlsson.rocketchat.models.to_rocket_chat.Field;

public interface FieldExtractor {
	Field create(Issue issue);
}
