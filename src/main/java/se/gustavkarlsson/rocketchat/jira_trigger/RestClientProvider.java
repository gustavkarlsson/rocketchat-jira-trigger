package se.gustavkarlsson.rocketchat.jira_trigger;

import com.atlassian.jira.rest.client.api.AuthenticationHandler;
import com.atlassian.jira.rest.client.api.IssueRestClient;
import com.atlassian.jira.rest.client.api.JiraRestClient;
import com.atlassian.jira.rest.client.auth.AnonymousAuthenticationHandler;
import com.atlassian.jira.rest.client.auth.BasicHttpAuthenticationHandler;
import com.atlassian.jira.rest.client.internal.async.AsynchronousJiraRestClientFactory;
import org.slf4j.Logger;
import se.gustavkarlsson.rocketchat.jira_trigger.configuration.JiraConfiguration;

import java.io.Console;

import static org.slf4j.LoggerFactory.getLogger;

class RestClientProvider {
	private static final Logger log = getLogger(RestClientProvider.class);

	private static AuthenticationHandler getAuthHandler(JiraConfiguration jiraConfig) {
		log.info("Getting authentication handler");
		String username = jiraConfig.getUsername();
		if (username == null) {
			log.info("No credentials configured. Using anonymous authentication");
			return new AnonymousAuthenticationHandler();
		} else {
			log.info("Using basic authentication");
			String password = jiraConfig.getPassword();
			if (password != null) {
				log.info("Password provided through configuration");
			} else {
				log.info("No password configured");
				password = readPassword(jiraConfig.getUsername());
				log.info("Password provided through console");
			}
			return new BasicHttpAuthenticationHandler(username, password);
		}
	}

	private static String readPassword(String username) {
		log.info("Reading password from console");
		Console console = System.console();
		if (console == null) {
			throw new IllegalStateException("No console available for password input");
		}
		char[] password = console.readPassword("Enter JIRA password for user %s: ", username);
		return new String(password);
	}

	IssueRestClient get(JiraConfiguration jiraConfig) {
		log.info("Creating JIRA client");
		AuthenticationHandler authHandler = getAuthHandler(jiraConfig);
		AsynchronousJiraRestClientFactory factory = new AsynchronousJiraRestClientFactory();
		JiraRestClient jiraClient = factory.create(jiraConfig.getUri(), authHandler);
		return jiraClient.getIssueClient();
	}
}
