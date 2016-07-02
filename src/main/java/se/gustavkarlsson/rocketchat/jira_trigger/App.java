package se.gustavkarlsson.rocketchat.jira_trigger;

import com.atlassian.jira.rest.client.api.AuthenticationHandler;
import com.atlassian.jira.rest.client.api.IssueRestClient;
import com.atlassian.jira.rest.client.api.JiraRestClient;
import com.atlassian.jira.rest.client.auth.AnonymousAuthenticationHandler;
import com.atlassian.jira.rest.client.auth.BasicHttpAuthenticationHandler;
import com.atlassian.jira.rest.client.internal.async.AsynchronousJiraRestClientFactory;
import com.moandjiezana.toml.Toml;
import org.slf4j.Logger;
import se.gustavkarlsson.rocketchat.jira_trigger.configuration.Configuration;
import se.gustavkarlsson.rocketchat.jira_trigger.configuration.JiraConfiguration;
import se.gustavkarlsson.rocketchat.jira_trigger.configuration.ValidationException;
import se.gustavkarlsson.rocketchat.jira_trigger.converters.AttachmentConverter;
import se.gustavkarlsson.rocketchat.jira_trigger.converters.MessageCreator;
import se.gustavkarlsson.rocketchat.jira_trigger.routes.DetectIssueRoute;
import spark.Request;
import spark.Spark;

import java.io.Console;
import java.io.File;

import static org.slf4j.LoggerFactory.getLogger;

public class App {
	private static final Logger log = getLogger(App.class);

	private static final String APPLICATION_JSON = "application/json";
	private static final String DEFAULTS_FILE_NAME = "defaults.toml";

	public static void main(String[] args) {
		try {
			verifySyntax(args);
			Configuration config = createConfiguration(args[0]);
			setupServer(config);
		} catch (Exception e) {
			log.error("Fatal error", e);
			System.exit(1);
		}
	}

	private static void verifySyntax(String[] args) {
		if (args.length < 1) {
			throw new IllegalArgumentException("No configuration file specified");
		}
	}

	private static Configuration createConfiguration(String arg) throws ValidationException {
		Toml toml = parseToml(new File(arg));
		Toml defaults = parseDefaults();
		return new Configuration(toml, defaults);
	}

	private static Toml parseToml(File configFile) {
		return new Toml().read(configFile);
	}

	private static Toml parseDefaults() {
		return new Toml().read(Configuration.class.getClassLoader().getResourceAsStream(DEFAULTS_FILE_NAME));
	}

	private static void setupServer(Configuration config) {
		IssueRestClient issueClient = createIssueRestClient(config.getJiraConfiguration());
		MessageCreator messageCreator = new MessageCreator(config.getMessageConfiguration());
		AttachmentConverter attachmentConverter = new AttachmentConverter(config.getMessageConfiguration());

		Spark.threadPool(config.getAppConfiguration().getMaxThreads());
		Spark.port(config.getAppConfiguration().getPort());
		Spark.before((request, response) -> log(request));
		Spark.post("/", APPLICATION_JSON, new DetectIssueRoute(config.getRocketChatConfiguration(), issueClient, messageCreator, attachmentConverter));
		Spark.after((request, response) -> response.type(APPLICATION_JSON));
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
		if (username == null) {
			log.info("No credentials configured. Using anonymous authentication");
			return new AnonymousAuthenticationHandler();
		} else {
			log.info("Using basic authentication");
			String password = jiraConfig.getPassword();
			if (password != null) {
				log.info("Password provided through configuration");
			} else {
				log.info("No password configured. Reading from console");
				password = readPassword(jiraConfig.getUsername());
				log.info("Password provided through stdin");
			}
			return new BasicHttpAuthenticationHandler(username, password);
		}
	}

	private static String readPassword(String username) {
		Console console = System.console();
		if (console == null) {
			throw new IllegalStateException("No console available for password input");
		}
		char[] password = console.readPassword("Enter JIRA password for user %s: ", username);
		return new String(password);
	}

	private static void log(Request request) {
		log.info("Incoming request: {} {} {}",
				request.raw().getRemoteAddr(), request.requestMethod(), request.pathInfo());
	}

}
