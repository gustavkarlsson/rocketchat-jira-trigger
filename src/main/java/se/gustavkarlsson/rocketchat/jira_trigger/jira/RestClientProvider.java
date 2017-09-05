package se.gustavkarlsson.rocketchat.jira_trigger.jira;

import com.atlassian.jira.rest.client.api.AuthenticationHandler;
import com.atlassian.jira.rest.client.api.JiraRestClient;
import com.atlassian.jira.rest.client.auth.AnonymousAuthenticationHandler;
import com.atlassian.jira.rest.client.auth.BasicHttpAuthenticationHandler;
import com.atlassian.jira.rest.client.internal.async.AsynchronousJiraRestClientFactory;
import org.slf4j.Logger;
import se.gustavkarlsson.rocketchat.jira_trigger.PasswordReadingConsole;
import se.gustavkarlsson.rocketchat.jira_trigger.configuration.JiraConfiguration;

import javax.inject.Inject;
import java.net.URI;

import static com.google.common.base.Preconditions.checkNotNull;
import static org.slf4j.LoggerFactory.getLogger;

class RestClientProvider {
	private static final Logger log = getLogger(RestClientProvider.class);
	private final PasswordReadingConsole passwordReadingConsole;

	@Inject
	RestClientProvider() {
		this(prompt -> System.console().readPassword(prompt));
	}

	RestClientProvider(PasswordReadingConsole passwordReadingConsole) {
		this.passwordReadingConsole = checkNotNull(passwordReadingConsole);
	}

	JiraRestClient get(JiraConfiguration jiraConfig) {
		URI uri = checkNotNull(jiraConfig.getUri(), "No JIRA URI provided");
		AuthenticationHandler authHandler = getAuthHandler(jiraConfig);
		AsynchronousJiraRestClientFactory factory = new AsynchronousJiraRestClientFactory();
		return factory.create(uri, authHandler);
	}

	private AuthenticationHandler getAuthHandler(JiraConfiguration jiraConfig) {
		String username = jiraConfig.getUsername();
		if (username == null) {
			log.info("No username configured. Using anonymous authentication");
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

	private String readPassword(String username) {
		if (passwordReadingConsole == null) {
			throw new IllegalStateException("No console available for password input");
		}
		char[] password = passwordReadingConsole.readPassword("Enter JIRA password for user " + username + ": ");
		return new String(password);
	}
}
