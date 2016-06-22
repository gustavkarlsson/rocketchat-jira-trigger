package se.gustavkarlsson.rocketchat.jira_trigger.routes;

import com.atlassian.jira.rest.client.api.IssueRestClient;
import com.atlassian.jira.rest.client.api.RestClientException;
import com.atlassian.jira.rest.client.api.domain.Issue;
import com.atlassian.util.concurrent.Promise;
import com.google.common.base.Optional;
import org.eclipse.jetty.http.HttpStatus;
import org.slf4j.Logger;
import se.gustavkarlsson.rocketchat.jira_trigger.models.IncomingMessage;
import se.gustavkarlsson.rocketchat.jira_trigger.models.OutgoingMessage;
import spark.Request;
import spark.Response;

import java.util.*;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static org.apache.commons.lang3.Validate.notNull;
import static org.slf4j.LoggerFactory.getLogger;

public class DetectIssueRoute extends RocketChatMessageRoute {
	private static final Logger log = getLogger(DetectIssueRoute.class);

	private static final Pattern JIRA_ISSUE = Pattern.compile("((?<!([A-Za-z]{1,10})-?)[A-Z]+-\\d+)");

	private final Set<String> blacklistedNames;
	private final IssueRestClient issueClient;
	private final Function<Collection<Issue>, IncomingMessage> converter;

	public DetectIssueRoute(Set<String> blacklistedNames, IssueRestClient issueClient, Function<Collection<Issue>, IncomingMessage> converter) {
		this.blacklistedNames = notNull(blacklistedNames);
		this.issueClient = notNull(issueClient);
		this.converter = notNull(converter);
	}

	@Override
	protected IncomingMessage handle(Request request, Response response, OutgoingMessage outgoing) throws Exception {
		if (blacklistedNames.contains(outgoing.getUserName())) {
			log.info("Blacklisted name encountered: {}. Ignoring", outgoing.getUserName());
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

	private boolean isNotFound(Optional<Integer> statusCode) {
		return statusCode.isPresent() && statusCode.get() == HttpStatus.NOT_FOUND_404;
	}

}
