package se.gustavkarlsson.rocketchat.jira_trigger.messages.fields;

import com.atlassian.jira.rest.client.api.domain.Issue;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormatter;

import static org.apache.commons.lang3.Validate.notNull;

public class CreatedFieldExtractor extends AbstractFieldExtractor {

	private final DateTimeFormatter dateFormatter;

	public CreatedFieldExtractor(DateTimeFormatter dateTimeFormatter) {
		this.dateFormatter = notNull(dateTimeFormatter);
	}

	@Override
	protected String getTitle() {
		return "Created";
	}

	@Override
	protected String getValue(Issue issue) {
		DateTime creationDate = issue.getCreationDate();
		return creationDate == null ? "-" : dateFormatter.print(creationDate);
	}

	@Override
	protected boolean isShortValue() {
		return true;
	}
}
