package se.gustavkarlsson.rocketchat.jira_trigger.routes;

import com.atlassian.jira.rest.client.api.IssueRestClient;
import com.atlassian.jira.rest.client.api.RestClientException;
import com.atlassian.jira.rest.client.api.domain.Issue;
import com.atlassian.util.concurrent.Promise;
import org.eclipse.jetty.http.HttpStatus;
import org.slf4j.Logger;
import se.gustavkarlsson.rocketchat.jira_trigger.configuration.RocketChatConfiguration;
import se.gustavkarlsson.rocketchat.jira_trigger.converters.AttachmentConverter;
import se.gustavkarlsson.rocketchat.jira_trigger.converters.ToRocketChatMessageFactory;
import se.gustavkarlsson.rocketchat.models.from_rocket_chat.FromRocketChatMessage;
import se.gustavkarlsson.rocketchat.models.to_rocket_chat.ToRocketChatAttachment;
import se.gustavkarlsson.rocketchat.models.to_rocket_chat.ToRocketChatMessage;
import se.gustavkarlsson.rocketchat.spark.routes.RocketChatMessageRoute;
import spark.Request;
import spark.Response;

import java.util.AbstractMap;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.apache.commons.lang3.Validate.notNull;
import static org.slf4j.LoggerFactory.getLogger;

public class DetectIssueRoute extends RocketChatMessageRoute {
	private static final Logger log = getLogger(DetectIssueRoute.class);

	private final RocketChatConfiguration config;
	private final IssueRestClient issueClient;
	private final ToRocketChatMessageFactory messageFactory;
	private final AttachmentConverter attachmentConverter;
	private final JiraKeyParser jiraKeyParser;

	public DetectIssueRoute(RocketChatConfiguration config, IssueRestClient issueClient, ToRocketChatMessageFactory messageFactory,
							AttachmentConverter attachmentConverter, JiraKeyParser jiraKeyParser) {
		this.config = notNull(config);
		this.issueClient = notNull(issueClient);
		this.messageFactory = notNull(messageFactory);
		this.attachmentConverter = notNull(attachmentConverter);
		this.jiraKeyParser = notNull(jiraKeyParser);
	}

	private static boolean isAllowed(Collection<String> blacklist, Collection<String> whitelist, String value) {
		return !blacklist.contains(value) && (whitelist.isEmpty() || whitelist.contains(value));
	}

	private static boolean isNotFound(com.google.common.base.Optional<Integer> statusCode) {
		return statusCode.isPresent() && statusCode.get() == HttpStatus.NOT_FOUND_404;
	}

	private Issue getJiraIssue(String jiraKey) {
		Promise<Issue> issuePromise = issueClient.getIssue(jiraKey);
		try {
			return issuePromise.claim();
		} catch (RestClientException e) {
			if (isNotFound(e.getStatusCode())) {
				return null;
			}
			throw e;
		}
	}

	@Override
	protected ToRocketChatMessage handle(Request request, Response response, FromRocketChatMessage fromRocketChatMessage) {
		String token = fromRocketChatMessage.getToken();
		if (!config.getTokens().isEmpty() && !config.getTokens().contains(token)) {
			log.info("Forbidden token encountered: {}. Ignoring", token);
			return null;
		}
		String userName = fromRocketChatMessage.getUserName();
		if (!isAllowed(config.getBlacklistedUsers(), config.getWhitelistedUsers(), userName)) {
			log.info("Forbidden user encountered: {}. Ignoring", userName);
			return null;
		}
		String channelName = fromRocketChatMessage.getChannelName();
		if (!isAllowed(config.getBlacklistedChannels(), config.getWhitelistedChannels(), channelName)) {
			log.info("Forbidden channel encountered: {}. Ignoring", channelName);
			return null;
		}
		log.info("Message is being processed...");
		log.debug("Parsing keys from text: \"{}\"", fromRocketChatMessage.getText());
		Map<String, Boolean> jiraKeys = jiraKeyParser.parse(fromRocketChatMessage.getText());
		log.info("Found {} keys", jiraKeys.size());
		log.debug("Keys: {}", jiraKeys.keySet());
		log.debug("Fetching issues...");
		Map<Issue, Boolean> issues = jiraKeys.entrySet().parallelStream()
				.map(e -> new AbstractMap.SimpleEntry<>(getJiraIssue(e.getKey()), e.getValue()))
				.filter(e -> e.getKey() != null)
				.collect(Collectors.toMap(AbstractMap.SimpleEntry::getKey, AbstractMap.SimpleEntry::getValue));
		log.info("Found {} issues", issues.size());
		if (issues.isEmpty()) {
			return null;
		}
		log.debug("Issues: {}", issues.keySet().stream()
				.map(Issue::getId)
				.collect(Collectors.toList()));
		log.debug("Creating message");
		ToRocketChatMessage message = messageFactory.create();
		message.setText(issues.size() == 1 ? "Found 1 issue" : "Found " + issues.size() + " issues");
		log.debug("Creating attachments");
		List<ToRocketChatAttachment> attachments = issues.entrySet().stream()
				.map(e -> attachmentConverter.convert(e.getKey(), e.getValue()))
				.collect(Collectors.toList());
		message.setAttachments(attachments);
		return message;
	}
}
