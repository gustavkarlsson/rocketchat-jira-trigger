package se.gustavkarlsson.rocketchat.jira_trigger.jira;

import com.atlassian.jira.rest.client.api.AuthenticationHandler;
import com.atlassian.jira.rest.client.auth.AnonymousAuthenticationHandler;
import com.atlassian.jira.rest.client.auth.BasicHttpAuthenticationHandler;
import com.google.inject.Inject;
import com.google.inject.Provider;
import org.slf4j.Logger;
import se.gustavkarlsson.rocketchat.jira_trigger.configuration.JiraConfiguration;

import java.io.Console;

import static org.slf4j.LoggerFactory.getLogger;

class AuthHandlerProvider implements Provider<AuthenticationHandler> {
	private static final Logger log = getLogger(AuthHandlerProvider.class);

	private final String username;
	private final String password;

	@Inject
	AuthHandlerProvider(JiraConfiguration jiraConfig) {
		this.username = jiraConfig.getUsername();
		this.password = jiraConfig.getPassword();
	}

	@Override
	public AuthenticationHandler get() {
		String actualPassword = password;
		if (username == null) {
			log.info("No username configured. Using anonymous authentication");
			return new AnonymousAuthenticationHandler();
		} else {
			log.info("Using basic authentication");
			if (actualPassword != null) {
				log.info("Password already provided");
			} else {
				log.info("No password configured");
				actualPassword = readPassword(username);
				log.info("Password provided through console");
			}
			return new BasicHttpAuthenticationHandler(username, actualPassword);
		}
	}

	private String readPassword(String username) {
		Console console = System.console();
		if (console == null) {
			throw new IllegalStateException("No console available for password input");
		}
		char[] password = console.readPassword("Enter JIRA password for user " + username + ": ");
		return new String(password);
	}
}
