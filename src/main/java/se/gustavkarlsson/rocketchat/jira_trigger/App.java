package se.gustavkarlsson.rocketchat.jira_trigger;

import com.atlassian.jira.rest.client.api.AuthenticationHandler;
import com.atlassian.jira.rest.client.api.IssueRestClient;
import com.atlassian.jira.rest.client.api.JiraRestClient;
import com.atlassian.jira.rest.client.api.domain.Issue;
import com.atlassian.jira.rest.client.auth.AnonymousAuthenticationHandler;
import com.atlassian.jira.rest.client.auth.BasicHttpAuthenticationHandler;
import com.atlassian.jira.rest.client.internal.async.AsynchronousJiraRestClientFactory;
import org.slf4j.Logger;
import se.gustavkarlsson.rocketchat.jira_trigger.configuration.Configuration;
import se.gustavkarlsson.rocketchat.jira_trigger.configuration.JiraConfiguration;
import se.gustavkarlsson.rocketchat.jira_trigger.converters.IssueToRocketChatMessageConverter;
import se.gustavkarlsson.rocketchat.jira_trigger.models.IncomingMessage;
import se.gustavkarlsson.rocketchat.jira_trigger.routes.DetectIssueRoute;
import spark.Request;
import spark.Spark;

import java.util.Collection;
import java.util.function.Function;

import static org.slf4j.LoggerFactory.getLogger;

public class App {
	public static final String APPLICATION_JSON = "application/json";
	private static final Logger log = getLogger(App.class);

	public static void main(String[] args) {
		if (args.length < 1) {
			log.error("No configuration file specified");
			System.exit(1);
		}
		try {
			Configuration config = new Configuration(args[0]);
			setupServer(config);
		} catch (Exception e) {
			log.error("Fatal error", e);
			System.exit(1);
		}
	}

	private static void setupServer(Configuration config) {
		IssueRestClient issueClient = createIssueRestClient(config.getJiraConfiguration());
		Spark.port(config.getAppConfiguration().getPort());
		Spark.before((request, response) -> log(request));
		Function<Collection<Issue>, IncomingMessage> converter = new IssueToRocketChatMessageConverter(config.getMessageConfiguration());
		Spark.post("/", APPLICATION_JSON, new DetectIssueRoute(config.getRocketChatConfiguration().getBlacklistedUsernames(), issueClient, converter));
		Spark.exception(Exception.class, new UuidGeneratingExceptionHandler());
	}

	private static IssueRestClient createIssueRestClient(JiraConfiguration jiraConfig) {
		AuthenticationHandler authHandler = getAuthHandler(jiraConfig);
		AsynchronousJiraRestClientFactory factory = new AsynchronousJiraRestClientFactory();
		JiraRestClient jiraClient = factory.create(jiraConfig.getUri(), authHandler);
		return jiraClient.getIssueClient();
	}

	private static AuthenticationHandler getAuthHandler(JiraConfiguration jiraConfig) {
		String username = jiraConfig.getUsername();
		String password = jiraConfig.getPassword();
		if (username != null && password != null) {
			log.info("Using basic authentication");
			return new BasicHttpAuthenticationHandler(username, password);
		} else {
			log.info("No credentials configured. Using anonymous authentication");
			return new AnonymousAuthenticationHandler();
		}
	}

	private static void log(Request request) {
		log.info("Incoming request | IP: {} | Method: {} | Path: {} | Content-Length: {}",
				request.raw().getRemoteAddr(), request.requestMethod(), request.pathInfo(), request.contentLength());
	}

}
