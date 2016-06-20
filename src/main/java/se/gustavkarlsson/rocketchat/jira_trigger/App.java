package se.gustavkarlsson.rocketchat.jira_trigger;

import com.atlassian.jira.rest.client.api.IssueRestClient;
import com.atlassian.jira.rest.client.api.JiraRestClient;
import com.atlassian.jira.rest.client.api.domain.Issue;
import com.atlassian.jira.rest.client.internal.async.AsynchronousJiraRestClientFactory;
import org.slf4j.Logger;
import se.gustavkarlsson.rocketchat.jira_trigger.configuration.Configuration;
import se.gustavkarlsson.rocketchat.jira_trigger.configuration.ValidationException;
import se.gustavkarlsson.rocketchat.jira_trigger.converters.IssueToRocketChatMessageConverter;
import se.gustavkarlsson.rocketchat.jira_trigger.models.IncomingMessage;
import se.gustavkarlsson.rocketchat.jira_trigger.routes.DetectIssueRoute;
import spark.Spark;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.util.function.Function;

import static org.slf4j.LoggerFactory.getLogger;

public class App {
	private static final Logger log = getLogger(App.class);

	public static final String APPLICATION_JSON = "application/json";

	public static void main(String[] args) throws ParseException {
		if (args.length < 1) {
			log.error("No properties file specified");
			System.exit(1);
		}
		Configuration config = null;
		try {
			config = new Configuration(args[0]);
		} catch (FileNotFoundException e) {
			log.error("File not found: {}", args[0]);
			System.exit(2);
		} catch (IOException e) {
			log.error("Unable to load configuration", e);
			System.exit(3);
		} catch (ValidationException e) {
			log.error("Configuration validation failed", e);
			System.exit(4);
		}

		AsynchronousJiraRestClientFactory factory = new AsynchronousJiraRestClientFactory();
		JiraRestClient jiraClient = factory.createWithBasicHttpAuthentication(config.getJiraUri(), config.getJiraUsername(), config.getJiraPassword());
		IssueRestClient issueClient = jiraClient.getIssueClient();
		Function<Issue, IncomingMessage> converter = new IssueToRocketChatMessageConverter(config);

		Spark.port(config.getPort());
		Spark.post("/", APPLICATION_JSON, new DetectIssueRoute(issueClient, converter));
		Spark.exception(Exception.class, new UuidGeneratingExceptionHandler());
	}

}
