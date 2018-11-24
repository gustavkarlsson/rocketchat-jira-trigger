package se.gustavkarlsson.rocketchat.jira_trigger.messages;

import com.atlassian.jira.rest.client.api.domain.BasicPriority;
import com.atlassian.jira.rest.client.api.domain.Issue;
import se.gustavkarlsson.rocketchat.jira_trigger.messages.field_creators.FieldCreator;
import se.gustavkarlsson.rocketchat.jira_trigger.routes.IssueDetail;
import se.gustavkarlsson.rocketchat.models.to_rocket_chat.Field;
import se.gustavkarlsson.rocketchat.models.to_rocket_chat.ToRocketChatAttachment;

import javax.ws.rs.core.UriBuilder;
import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.apache.commons.lang.StringEscapeUtils.unescapeHtml;
import static org.apache.commons.lang3.Validate.*;
import static se.gustavkarlsson.rocketchat.jira_trigger.routes.IssueDetail.EXTENDED;

public class AttachmentCreator {
	static final String BLOCKER_COLOR = "#FF4437";
	static final String CRITICAL_COLOR = "#D04437";
	static final String MAJOR_COLOR = "#E3833C";
	static final String MINOR_COLOR = "#F6C342";
	static final String TRIVIAL_COLOR = "#707070";

	private final boolean priorityColors;
	private final String defaultColor;
	private final List<FieldCreator> defaultFieldCreators;
	private final List<FieldCreator> extendedFieldCreators;
	private final URI baseUri;
	private final int maxTextLength;

	// TODO Move some of this into factory
	AttachmentCreator(boolean priorityColors, String defaultColor, List<FieldCreator> defaultFieldCreators, List<FieldCreator> extendedFieldCreators, URI baseUri, int maxTextLength) {
		this.priorityColors = priorityColors;
		this.defaultColor = notNull(defaultColor);
		this.defaultFieldCreators = noNullElements(defaultFieldCreators);
		this.extendedFieldCreators = noNullElements(extendedFieldCreators);
		this.baseUri = notNull(baseUri);
		inclusiveBetween(1L, Integer.MAX_VALUE, maxTextLength, "maxTextLength");
		this.maxTextLength = maxTextLength;
	}

	public ToRocketChatAttachment create(Issue issue, IssueDetail detail) {
		List<FieldCreator> fieldCreators = detail == EXTENDED ? extendedFieldCreators : defaultFieldCreators;
		ToRocketChatAttachment attachment = new ToRocketChatAttachment();
		attachment.setTitle(createTitle(issue));
		attachment.setTitleLink(parseTitleLink(issue));
		if (priorityColors && issue.getPriority() != null) {
			attachment.setColor(getPriorityColor(issue.getPriority(), defaultColor));
		} else {
			attachment.setColor(defaultColor);
		}
		attachment.setText(createText(issue)); // FIXME Set ellipsize limit
		// FIXME make configurable attachment.setCollapsed(true);
		// FIXME Consider attachment.setAuthorName(); (and removing from default fields)
		// FIXME Consider attachment.setAuthorLink();
		// FIXME Consider attachment.setAuthorIcon();
		List<Field> fields = fieldCreators.stream().map(fc -> fc.create(issue)).collect(Collectors.toList());
		attachment.setFields(fields);
		return attachment;
	}

	private String createTitle(Issue issue) {
		String summary = Optional.ofNullable(issue.getSummary()).orElse("");
		String unescaped = unescapeHtml(summary);
		String stripped = stripReservedLinkCharacters(unescaped);
		return String.format("%s %s", issue.getKey(), stripped);
	}

	private static String stripReservedLinkCharacters(String text) {
		return text.replaceAll("[<>]", "");
	}

	private String parseTitleLink(Issue issue) {
		return UriBuilder.fromUri(baseUri)
				.path("browse")
				.path(issue.getKey())
				.build()
				.toASCIIString();
	}

	private String createText(Issue issue) {
		String summary = Optional.ofNullable(issue.getDescription()).orElse("");
		String unescaped = unescapeHtml(summary);
		String text = stripReservedLinkCharacters(unescaped);
		if (text.length() > maxTextLength) {
			text = text.substring(0, maxTextLength - 1) + '…';
		}
		return text;
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
