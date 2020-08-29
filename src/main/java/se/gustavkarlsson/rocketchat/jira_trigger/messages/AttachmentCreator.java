package se.gustavkarlsson.rocketchat.jira_trigger.messages;

import com.atlassian.jira.rest.client.api.domain.BasicPriority;
import com.atlassian.jira.rest.client.api.domain.Issue;
import se.gustavkarlsson.rocketchat.jira_trigger.messages.field_creators.FieldCreator;
import se.gustavkarlsson.rocketchat.models.to_rocket_chat.Field;
import se.gustavkarlsson.rocketchat.models.to_rocket_chat.ToRocketChatAttachment;

import javax.ws.rs.core.UriBuilder;
import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.apache.commons.lang.StringEscapeUtils.unescapeHtml;
import static org.apache.commons.lang3.Validate.*;

public class AttachmentCreator {
	static final String BLOCKER_COLOR = "#FF4437";
	static final String CRITICAL_COLOR = "#D04437";
	static final String MAJOR_COLOR = "#E3833C";
	static final String MINOR_COLOR = "#F6C342";
	static final String TRIVIAL_COLOR = "#707070";

	private final boolean priorityColors;
	private final String defaultColor;
	private final List<FieldCreator> fieldCreators;
	private final URI baseUri;
	private final int maxTextLength;

	AttachmentCreator(boolean priorityColors, String defaultColor, List<FieldCreator> fieldCreators, URI baseUri, int maxTextLength) {
		this.priorityColors = priorityColors;
		this.defaultColor = notNull(defaultColor);
		this.fieldCreators = noNullElements(fieldCreators);
		this.baseUri = notNull(baseUri);
		inclusiveBetween(1L, Integer.MAX_VALUE, maxTextLength, "maxTextLength");
		this.maxTextLength = maxTextLength;
	}

	public ToRocketChatAttachment create(Issue issue) {
		ToRocketChatAttachment attachment = new ToRocketChatAttachment();
		attachment.setCollapsed(true);
		attachment.setTitle(getTitle(issue));
		attachment.setTitleLink(getTitleLink(issue));
		attachment.setColor(getColor(issue));
		attachment.setText(createText(issue));
		attachment.setFields(getFields(issue));
		return attachment;
	}

	private String getTitle(Issue issue) {
		String summary = Optional.ofNullable(issue.getSummary()).orElse("");
		String cleaned = cleanText(summary);
		return String.format("%s %s", issue.getKey(), cleaned);
	}

	private String getTitleLink(Issue issue) {
		return UriBuilder.fromUri(baseUri)
				.path("browse")
				.path(issue.getKey())
				.build()
				.toASCIIString();
	}

	private String getColor(Issue issue) {
		if (priorityColors && issue.getPriority() != null) {
			return getPriorityColor(issue.getPriority(), defaultColor);
		} else {
			return defaultColor;
		}
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

	private String createText(Issue issue) {
		String description = Optional.ofNullable(issue.getDescription()).orElse("");
		String cleaned = cleanText(description);
		return trim(cleaned);
	}

	private String trim(String cleaned) {
		if (cleaned.length() > maxTextLength) {
			cleaned = cleaned.substring(0, maxTextLength - 1) + "...";
		}
		return cleaned;
	}

	private static String cleanText(String summary) {
		String unescaped = unescapeHtml(summary);
		return stripReservedLinkCharacters(unescaped);
	}

	private static String stripReservedLinkCharacters(String text) {
		return text.replaceAll("[<>]", "");
	}

	private List<Field> getFields(Issue issue) {
		return fieldCreators.stream().map(fc -> fc.create(issue)).collect(Collectors.toList());
	}

}
