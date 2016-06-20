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
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import static java.util.Collections.singletonList;
import static org.apache.commons.lang3.Validate.notNull;

public class IssueToRocketChatMessageConverter implements Function<Issue, IncomingMessage> {

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
	public IncomingMessage apply(Issue issue) {
		if (issue == null) {
			return null;
		}
		IncomingMessage message = new IncomingMessage();
		Attachment attachment = new Attachment();
		List<Field> fields = new ArrayList<>();
		attachment.setFields(fields);
		List<Attachment> attachments = singletonList(attachment);
		message.setAttachments(attachments);

		message.setText(String.format("<%s|%s - %s>", parseTitleLink(issue), issue.getKey(), issue.getSummary()));


		Optional<String> username = config.getUsername();
		if (username.isPresent()) {
			message.setUsername(username.get());
		}

		Optional<String> iconUrl = config.getIconUrl();
		if (iconUrl.isPresent()) {
			message.setIconUrl(iconUrl.get());
		}

		if (config.isPriorityColors() && issue.getPriority() != null) {
			attachment.setColor(getPriorityColor(issue.getPriority(), config.getDefaultColor()));
		} else {
			attachment.setColor(config.getDefaultColor());
		}

		if (config.isPrintDescription()) {
			attachment.setText(issue.getDescription());
		}

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

		return message;
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

	private String parseTitleLink(Issue issue) {
		return UriBuilder.fromUri(issue.getSelf())
				.replacePath(null)
				.path("browse/")
				.path(issue.getKey())
				.build()
				.toASCIIString();
	}

	private synchronized String formatDateTime(DateTime dateTime) {
		return dateFormat.format(dateTime.toDate());
	}

}
