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

import java.util.Set;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.apache.commons.lang3.Validate.notNull;
import static org.slf4j.LoggerFactory.getLogger;

public class DetectIssueRoute extends RocketChatMessageRoute {
	private static final Logger log = getLogger(DetectIssueRoute.class);

	private static final Pattern JIRA_ISSUE = Pattern.compile("((?<!([A-Za-z]{1,10})-?)[A-Z]+-\\d+)");


	private final Set<String> blacklistednames;
	private final IssueRestClient issueClient;
	private final Function<Issue, IncomingMessage> converter;

	public DetectIssueRoute(Set<String> blacklistednames, IssueRestClient issueClient, Function<Issue, IncomingMessage> converter) {
		this.blacklistednames = notNull(blacklistednames);
		this.issueClient = notNull(issueClient);
		this.converter = notNull(converter);
	}

	@Override
	protected IncomingMessage handle(Request request, Response response, OutgoingMessage outgoing) throws Exception {
		if (blacklistednames.contains(outgoing.getUserName())) {
			return null;
		}
		Matcher matcher = JIRA_ISSUE.matcher(outgoing.getText());
		if (!matcher.find()) {
			return null;
		}

		String jiraKey = matcher.group();
		Issue issue = getJiraIssue(jiraKey);
		return converter.apply(issue);
	}

	private Issue getJiraIssue(String jiraKey) {
		Promise<Issue> issuePromise = issueClient.getIssue(jiraKey);
		try {
			return issuePromise.claim();
		} catch (RestClientException e) {
			Optional<Integer> statusCode = e.getStatusCode();
			if (statusCode.isPresent() && statusCode.get() == HttpStatus.NOT_FOUND_404) {
				return null;
			}
			throw e;
		}
	}

}
