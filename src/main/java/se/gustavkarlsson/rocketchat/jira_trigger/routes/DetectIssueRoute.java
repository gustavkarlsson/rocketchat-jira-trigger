package se.gustavkarlsson.rocketchat.jira_trigger.routes;

import com.atlassian.jira.rest.client.api.IssueRestClient;
import com.atlassian.jira.rest.client.api.RestClientException;
import com.atlassian.jira.rest.client.api.domain.Issue;
import com.atlassian.util.concurrent.Promise;
import org.eclipse.jetty.http.HttpStatus;
import org.slf4j.Logger;
import se.gustavkarlsson.rocketchat.jira_trigger.configuration.RocketChatConfiguration;
import se.gustavkarlsson.rocketchat.jira_trigger.converters.AttachmentConverter;
import se.gustavkarlsson.rocketchat.jira_trigger.converters.MessageCreator;
import se.gustavkarlsson.rocketchat.jira_trigger.models.Attachment;
import se.gustavkarlsson.rocketchat.jira_trigger.models.IncomingMessage;
import se.gustavkarlsson.rocketchat.jira_trigger.models.OutgoingMessage;
import spark.Request;
import spark.Response;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static java.util.Arrays.stream;
import static org.apache.commons.lang3.Validate.notNull;
import static org.slf4j.LoggerFactory.getLogger;

public class DetectIssueRoute extends RocketChatMessageRoute {
	private static final Logger log = getLogger(DetectIssueRoute.class);

	private static final Pattern JIRA_ISSUE = Pattern.compile("((?<!([A-Za-z]{1,10})-?)[A-Z]+-\\d+\\+?)");

	private final RocketChatConfiguration config;
	private final IssueRestClient issueClient;
	private final MessageCreator messageCreator;
	private final AttachmentConverter attachmentConverter;

	public DetectIssueRoute(RocketChatConfiguration config, IssueRestClient issueClient, MessageCreator messageCreator,
							AttachmentConverter attachmentConverter) {
		this.config = notNull(config);
		this.issueClient = notNull(issueClient);
		this.messageCreator = notNull(messageCreator);
		this.attachmentConverter = notNull(attachmentConverter);
	}

	private static boolean isAllowed(Collection<String> blacklist, Collection<String> whitelist, String... values) {
		return stream(values).allMatch((value) -> isAllowed(blacklist, whitelist, value));
	}

	private static boolean isAllowed(Collection<String> blacklist, Collection<String> whitelist, String value) {
		return !blacklist.contains(value) && (whitelist.isEmpty() || whitelist.contains(value));
	}

	private static boolean isNotFound(com.google.common.base.Optional<Integer> statusCode) {
		return statusCode.isPresent() && statusCode.get() == HttpStatus.NOT_FOUND_404;
	}

	@Override
	protected Optional<IncomingMessage> handle(Request request, Response response, OutgoingMessage outgoing) throws Exception {
		String token = outgoing.getToken();
		if (!config.getTokens().isEmpty() && !config.getTokens().contains(token)) {
			log.info("Forbidden token encountered: {}. Ignoring", token);
			return Optional.empty();
		}
		String userId = outgoing.getUserId();
		String userName = outgoing.getUserName();
		if (!isAllowed(config.getBlacklistedUsers(), config.getWhitelistedUsers(), userId, userName)) {
			log.info("Forbidden user encountered. ID: {}, Name: {}. Ignoring", userId, userName);
			return Optional.empty();
		}
		String channelId = outgoing.getChannelId();
		String channelName = outgoing.getChannelName();
		if (!isAllowed(config.getBlacklistedChannels(), config.getWhitelistedChannels(), channelId, channelName)) {
			log.info("Forbidden channel encountered. ID: {}, Name: {}. Ignoring", channelId, channelName);
			return Optional.empty();
		}
		log.info("Message is being processed...");
		log.debug("Parsing keys from text: \"{}\"", outgoing.getText());
		Map<String, Boolean> jiraKeys = parseJiraKeys(outgoing.getText());
		log.info("Found {} keys", jiraKeys.size());
		log.debug("Keys: {}", jiraKeys.keySet());
		log.debug("Fetching issues...");
		Map<Issue, Boolean> issues = jiraKeys.entrySet().parallelStream()
				.map(e -> new AbstractMap.SimpleEntry<>(getJiraIssue(e.getKey()), e.getValue()))
				.filter(e -> e.getKey() != null)
				.collect(Collectors.toMap(AbstractMap.SimpleEntry::getKey, AbstractMap.SimpleEntry::getValue));
		if (issues.isEmpty()) {
			log.debug("No matching issue found. Ignoring");
			return Optional.empty();
		}
		log.info("Found {} issues", issues.size());
		log.debug("Issues: {}", issues.keySet().stream()
				.map(Issue::getId)
				.collect(Collectors.toList()));
		log.debug("Creating message");
		IncomingMessage message = messageCreator.create();
		message.setText(issues.size() == 1 ? "Found 1 issue" : "Found " + issues.size() + " issues");
		log.debug("Creating attachments");
		List<Attachment> attachments = issues.entrySet().stream()
				.map(e -> attachmentConverter.convert(e.getKey(), e.getValue()))
				.collect(Collectors.toList());
		message.setAttachments(attachments);
		return Optional.of(message);
	}

	private Map<String, Boolean> parseJiraKeys(String messageText) {
		Matcher matcher = JIRA_ISSUE.matcher(messageText);
		Map<String, Boolean> jiraKeys = new HashMap<>();
		while (matcher.find()) {
			String key = matcher.group();
			boolean extended = key.endsWith("+");
			if (extended) {
				key = key.substring(0, key.length() - 1);
			}
			jiraKeys.put(key, extended);
		}
		return jiraKeys;
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

}
