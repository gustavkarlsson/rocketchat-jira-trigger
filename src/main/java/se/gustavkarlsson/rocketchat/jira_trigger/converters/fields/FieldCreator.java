package se.gustavkarlsson.rocketchat.jira_trigger.converters.fields;

import com.atlassian.jira.rest.client.api.domain.Issue;
import se.gustavkarlsson.rocketchat.jira_trigger.models.Field;

import java.util.function.Function;

public interface FieldCreator extends Function<Issue, Field> {
}
