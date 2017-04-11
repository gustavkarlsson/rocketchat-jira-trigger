package se.gustavkarlsson.rocketchat.jira_trigger.converters.fields;

import com.atlassian.jira.rest.client.api.domain.Issue;

import java.text.DateFormat;

import static org.apache.commons.lang3.Validate.notNull;

public class UpdatedFieldExtractor extends AbstractFieldExtractor {

	private final DateFormat dateFormat;

	public UpdatedFieldExtractor(DateFormat dateFormat) {
		this.dateFormat = notNull(dateFormat);
	}

	@Override
	protected String getTitle() {
		return "Updated";
	}

	@Override
	protected String getValue(Issue issue) {
		return dateFormat.format(issue.getUpdateDate().toDate());
	}

	@Override
	protected boolean isShortValue() {
		return true;
	}
}
