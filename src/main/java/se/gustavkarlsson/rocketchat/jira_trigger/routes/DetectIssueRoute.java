package se.gustavkarlsson.rocketchat.jira_trigger.routes;

import com.atlassian.jira.rest.client.api.IssueRestClient;
import com.atlassian.jira.rest.client.api.RestClientException;
import com.atlassian.jira.rest.client.api.domain.Issue;
import com.atlassian.util.concurrent.Promise;
import com.google.common.base.Optional;
import org.eclipse.jetty.http.HttpStatus;
import org.slf4j.Logger;
import se.gustavkarlsson.rocketchat.jira_trigger.configuration.RocketChatConfiguration;
import se.gustavkarlsson.rocketchat.jira_trigger.models.IncomingMessage;
import se.gustavkarlsson.rocketchat.jira_trigger.models.OutgoingMessage;
import spark.Request;
import spark.Response;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static java.util.Arrays.stream;
import static org.apache.commons.lang3.Validate.notNull;
import static org.slf4j.LoggerFactory.getLogger;

public class DetectIssueRoute extends RocketChatMessageRoute {
	private static final Logger log = getLogger(DetectIssueRoute.class);

	private static final Pattern JIRA_ISSUE = Pattern.compile("((?<!([A-Za-z]{1,10})-?)[A-Z]+-\\d+)");

	private final RocketChatConfiguration config;
	private final IssueRestClient issueClient;
	private final Function<Collection<Issue>, IncomingMessage> converter;

	public DetectIssueRoute(RocketChatConfiguration config, IssueRestClient issueClient, Function<Collection<Issue>, IncomingMessage> converter) {
		this.config = notNull(config);
		this.issueClient = notNull(issueClient);
		this.converter = notNull(converter);
	}

	private static boolean isAllowed(Collection<String> blacklist, Collection<String> whitelist, String... values) {
		return stream(values).allMatch((value) -> isAllowed(blacklist, whitelist, value));
	}

	private static boolean isAllowed(Collection<String> blacklist, Collection<String> whitelist, String value) {
		return !blacklist.contains(value) && (whitelist.isEmpty() || whitelist.contains(value));
	}

	private static boolean isNotFound(Optional<Integer> statusCode) {
		return statusCode.isPresent() && statusCode.get() == HttpStatus.NOT_FOUND_404;
	}

	@Override
	protected IncomingMessage handle(Request request, Response response, OutgoingMessage outgoing) throws Exception {
		String token = outgoing.getToken();
		if (!config.getTokens().isEmpty() && !config.getTokens().contains(token)) {
			log.info("Forbidden token encountered: {}. Ignoring", token);
			return null;
		}
		String userId = outgoing.getUserId();
		String userName = outgoing.getUserName();
		if (!isAllowed(config.getBlacklistedUsers(), config.getWhitelistedUsers(), userId, userName)) {
			log.info("Forbidden user encountered. ID: {}, Name: {}. Ignoring", userId, userName);
			return null;
		}
		String channelId = outgoing.getChannelId();
		String channelName = outgoing.getChannelName();
		if (!isAllowed(config.getBlacklistedChannels(), config.getWhitelistedChannels(), channelId, channelName)) {
			log.info("Forbidden channel encountered. ID: {}, Name: {}. Ignoring", channelId, channelName);
			return null;
		}
		log.debug("Parsing keys from text: \"{}\"", outgoing.getText());
		List<String> jiraKeys = parseJiraKeys(outgoing.getText());
		log.debug("Found keys: {}", jiraKeys);
		log.debug("Fetching issues...");
		List<Issue> issues = jiraKeys.parallelStream()
				.map(this::getJiraIssue)
				.filter(Objects::nonNull)
				.collect(Collectors.toList());
		if (issues.isEmpty()) {
			log.debug("No matching issue found. Ignoring");
			return null;
		}
		log.debug("Found issues: {}", issues.stream()
				.map(Issue::getId)
				.collect(Collectors.toList()));
		log.debug("Converting issues");
		return converter.apply(issues);
	}

	private List<String> parseJiraKeys(String messageText) {
		Matcher matcher = JIRA_ISSUE.matcher(messageText);
		List<String> jiraKeys = new ArrayList<>();
		while (matcher.find()) {
			jiraKeys.add(matcher.group());
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
