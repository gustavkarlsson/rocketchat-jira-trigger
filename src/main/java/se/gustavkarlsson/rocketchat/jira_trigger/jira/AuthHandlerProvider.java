package se.gustavkarlsson.rocketchat.jira_trigger.jira;

import com.atlassian.jira.rest.client.api.AuthenticationHandler;
import com.atlassian.jira.rest.client.auth.AnonymousAuthenticationHandler;
import com.atlassian.jira.rest.client.auth.BasicHttpAuthenticationHandler;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import org.slf4j.Logger;
import se.gustavkarlsson.rocketchat.jira_trigger.configuration.JiraConfiguration;

import javax.annotation.Nullable;
import java.io.Console;

import static org.slf4j.LoggerFactory.getLogger;

@Singleton
class AuthHandlerProvider implements Provider<AuthenticationHandler> {
	private static final Logger log = getLogger(AuthHandlerProvider.class);

	private final String username;
	private final String password;
	private final Console console;

	@Inject
	AuthHandlerProvider(JiraConfiguration jiraConfig, @Nullable Console console) {
		this.username = jiraConfig.getUsername();
		this.password = jiraConfig.getPassword();
		this.console = console;
	}

	@Override
	public AuthenticationHandler get() {
		String finalPassword = password;
		if (username == null) {
			log.info("No username configured. Using anonymous authentication");
			if (password != null) {
				log.warn("Ignoring password from configuration");
			}
			return new AnonymousAuthenticationHandler();
		} else {
			log.info("Using basic authentication");
			if (finalPassword != null) {
				log.info("Password already provided");
			} else {
				log.info("No password configured");
				finalPassword = readPassword(username);
				log.info("Password provided through console");
			}
			return new BasicHttpAuthenticationHandler(username, finalPassword);
		}
	}

	private String readPassword(String username) {
		if (console == null) {
			throw new IllegalStateException("No console available for password input");
		}
		char[] password = console.readPassword(String.format("Enter JIRA password for user %s: ", username));
		return new String(password);
	}
}
