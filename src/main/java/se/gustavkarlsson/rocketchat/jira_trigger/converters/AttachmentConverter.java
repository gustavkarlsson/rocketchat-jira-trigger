package se.gustavkarlsson.rocketchat.jira_trigger.converters;

import com.atlassian.jira.rest.client.api.domain.BasicPriority;
import com.atlassian.jira.rest.client.api.domain.Issue;
import se.gustavkarlsson.rocketchat.jira_trigger.configuration.MessageConfiguration;
import se.gustavkarlsson.rocketchat.jira_trigger.converters.fields.AbstractFieldCreator;
import se.gustavkarlsson.rocketchat.jira_trigger.models.Attachment;
import se.gustavkarlsson.rocketchat.jira_trigger.models.Field;

import javax.ws.rs.core.UriBuilder;
import java.util.List;
import java.util.stream.Collectors;

import static org.apache.commons.lang3.Validate.notNull;

public class AttachmentConverter {

	private static final String BLOCKER_COLOR = "#FF4437";
	private static final String CRITICAL_COLOR = "#D04437";
	private static final String MAJOR_COLOR = "#E3833C";
	private static final String MINOR_COLOR = "#F6C342";
	private static final String TRIVIAL_COLOR = "#707070";

	private final MessageConfiguration config;
	private final List<AbstractFieldCreator> defaultFieldCreators;
	private final List<AbstractFieldCreator> extendedFieldCreators;

	public AttachmentConverter(MessageConfiguration config, List<AbstractFieldCreator> defaultFieldCreators, List<AbstractFieldCreator> extendedFieldCreators) {
		this.config = notNull(config);
		this.defaultFieldCreators = notNull(defaultFieldCreators);
		this.extendedFieldCreators = notNull(extendedFieldCreators);
	}

	public Attachment convert(Issue issue, Boolean extended) {
		List<AbstractFieldCreator> fieldCreators = extended ? defaultFieldCreators : extendedFieldCreators;
		return createAttachment(issue, fieldCreators);
	}

	private Attachment createAttachment(Issue issue, List<AbstractFieldCreator> fieldCreators) {
		Attachment attachment = new Attachment();
		attachment.setTitle(issue.getKey());
		if (config.isPriorityColors() && issue.getPriority() != null) {
			attachment.setColor(getPriorityColor(issue.getPriority(), config.getDefaultColor()));
		} else {
			attachment.setColor(config.getDefaultColor());
		}
		attachment.setText(createSummaryLink(issue));
		List<Field> fields = fieldCreators.stream().map(fc -> fc.apply(issue)).collect(Collectors.toList());
		attachment.setFields(fields);
		return attachment;
	}

	private String createSummaryLink(Issue issue) {
		return String.format("<%s|%s>", parseTitleLink(issue), issue.getSummary());
	}

	private String parseTitleLink(Issue issue) {
		return UriBuilder.fromUri(issue.getSelf())
				.replacePath(null)
				.path("browse/")
				.path(issue.getKey())
				.build()
				.toASCIIString();
	}

	private String getPriorityColor(BasicPriority priority, String fallbackColor) {
		switch (priority.getName()) {
			case "Blocker":
				return BLOCKER_COLOR;
			case "Critical":
				return CRITICAL_COLOR;
			case "Major":
				return MAJOR_COLOR;
			case "Minor":
				return MINOR_COLOR;
			case "Trivial":
				return TRIVIAL_COLOR;
			default:
				return fallbackColor;
		}
	}

}
