package se.gustavkarlsson.rocketchat.jira_trigger.messages.field_creators;

import com.atlassian.jira.rest.client.api.domain.Issue;
import se.gustavkarlsson.rocketchat.models.to_rocket_chat.Field;

public interface FieldCreator {
	Field create(Issue issue);
}
