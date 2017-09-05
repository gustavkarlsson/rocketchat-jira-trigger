package se.gustavkarlsson.rocketchat.jira_trigger.messages.fields;

import com.atlassian.jira.rest.client.api.domain.Issue;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormatter;

import static org.apache.commons.lang3.Validate.notNull;

public class UpdatedFieldExtractor extends AbstractFieldExtractor {

	private final DateTimeFormatter dateFormatter;

	public UpdatedFieldExtractor(DateTimeFormatter dateFormatter) {
		this.dateFormatter = notNull(dateFormatter);
	}

	@Override
	protected String getTitle() {
		return "Updated";
	}

	@Override
	protected String getValue(Issue issue) {
		DateTime updateDate = issue.getUpdateDate();
		return updateDate == null ? "-" : dateFormatter.print(updateDate);
	}

	@Override
	protected boolean isShortValue() {
		return true;
	}
}
