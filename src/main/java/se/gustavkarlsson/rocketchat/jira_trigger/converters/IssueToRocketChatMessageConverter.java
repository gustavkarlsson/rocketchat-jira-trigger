package se.gustavkarlsson.rocketchat.jira_trigger.converters;

import com.atlassian.jira.rest.client.api.domain.BasicPriority;
import com.atlassian.jira.rest.client.api.domain.Issue;
import com.atlassian.jira.rest.client.api.domain.Resolution;
import com.atlassian.jira.rest.client.api.domain.User;
import org.joda.time.DateTime;
import se.gustavkarlsson.rocketchat.jira_trigger.configuration.Configuration;
import se.gustavkarlsson.rocketchat.jira_trigger.models.Attachment;
import se.gustavkarlsson.rocketchat.jira_trigger.models.Field;
import se.gustavkarlsson.rocketchat.jira_trigger.models.IncomingMessage;

import javax.ws.rs.core.UriBuilder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import static org.apache.commons.lang3.Validate.notNull;

public class IssueToRocketChatMessageConverter implements Function<Collection<Issue>, IncomingMessage> {

	private static final String BLOCKER_COLOR = "#FF4437";
	private static final String CRITICAL_COLOR = "#D04437";
	private static final String MAJOR_COLOR = "#E3833C";
	private static final String MINOR_COLOR = "#F6C342";
	private static final String TRIVIAL_COLOR = "#707070";

	private final SimpleDateFormat dateFormat = new SimpleDateFormat("EEE, d MMM, yyyy");

	private final Configuration config;

	public IssueToRocketChatMessageConverter(Configuration config) {
		this.config = notNull(config);
	}

	@Override
	public IncomingMessage apply(Collection<Issue> issue) {
		IncomingMessage message = new IncomingMessage();

		message.setUsername(config.getUsername());
		message.setIconUrl(config.getIconUrl());

		List<Attachment> attachments = issue.stream()
				.map(this::createAttachment)
				.collect(Collectors.toList());

		message.setAttachments(attachments);

		return message;
	}

	private Attachment createAttachment(Issue issue) {
		Attachment attachment = new Attachment();
		attachment.setTitle(issue.getKey());

		if (config.isPriorityColors() && issue.getPriority() != null) {
			attachment.setColor(getPriorityColor(issue.getPriority(), config.getDefaultColor()));
		} else {
			attachment.setColor(config.getDefaultColor());
		}

		StringBuilder text = new StringBuilder(createSummaryLink(issue));
		if (config.isPrintDescription()) {
			text.append('\n');
			text.append(issue.getDescription());
		}
		attachment.setText(text.toString());

		attachment.setFields(createFields(issue));

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

	private List<Field> createFields(Issue issue) {
		List<Field> fields = new ArrayList<>();

		if (config.isPrintType()) {
			fields.add(new Field("Type", issue.getIssueType().getName(), true));
		}

		if (config.isPrintAssignee()) {
			User assignee = issue.getAssignee();
			String assigneeText = assignee == null ? "Unassigned" : assignee.getDisplayName();
			fields.add(new Field("Assigned To", assigneeText, true));
		}

		if (config.isPrintStatus()) {
			fields.add(new Field("Status", issue.getStatus().getName(), true));
		}

		if (config.isPrintResolution()) {
			Resolution resolution = issue.getResolution();
			String resolutionText = resolution == null ? "None" : resolution.getName();
			fields.add(new Field("Resolution", resolutionText, true));
		}

		if (config.isPrintReporter()) {
			User reporter = issue.getReporter();
			String reporterText = reporter == null ? "No reporter" : reporter.getDisplayName();
			fields.add(new Field("Reporter", reporterText, true));
		}

		if (config.isPrintPriority()) {
			BasicPriority priority = issue.getPriority();
			String priorityText = priority == null ? "Unprioritized" : priority.getName();
			fields.add(new Field("Priority", priorityText, true));
		}

		if (config.isPrintCreated()) {
			fields.add(new Field("Created", formatDateTime(issue.getCreationDate()), true));
		}

		if (config.isPrintUpdated()) {
			fields.add(new Field("Updated", formatDateTime(issue.getUpdateDate()), true));
		}
		return fields;
	}

	private synchronized String formatDateTime(DateTime dateTime) {
		return dateFormat.format(dateTime.toDate());
	}

}
