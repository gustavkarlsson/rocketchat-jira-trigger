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
import se.gustavkarlsson.rocketchat.jira_trigger.converters.fields.FieldCreator;
import se.gustavkarlsson.rocketchat.jira_trigger.routes.DetectIssueRoute;
import spark.Request;
import spark.Response;
import spark.Service;

import java.io.Console;
import java.io.File;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;
import static spark.Service.ignite;

public class App {
	private static final Logger log = getLogger(App.class);

	private static final String APPLICATION_JSON = "application/json";
	private static final String DEFAULTS_FILE_NAME = "defaults.toml";

	private Service server;

	App(String[] args) throws Exception {
		String configFilePath = verifySyntax(args);
		Configuration config = createConfiguration(configFilePath);
		server = start(config);
	}

	public static void main(String[] args) {
		try {
			new App(args);
		} catch (Exception e) {
			log.error("Fatal error", e);
			System.exit(1);
		}
	}

	private static String verifySyntax(String[] args) {
		if (args.length != 1) {
			throw new IllegalArgumentException("Exactly one configuration file must be specified");
		}
		return args[0];
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

	private static IssueRestClient createIssueRestClient(JiraConfiguration jiraConfig) {
		log.info("Creating JIRA client");
		AuthenticationHandler authHandler = getAuthHandler(jiraConfig);
		AsynchronousJiraRestClientFactory factory = new AsynchronousJiraRestClientFactory();
		JiraRestClient jiraClient = factory.create(jiraConfig.getUri(), authHandler);
		return jiraClient.getIssueClient();
	}

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

	private static void log(Request request) {
		log.info("Incoming request: {} {} {} {}",
				request.raw().getRemoteAddr(), request.requestMethod(), request.contentType(), request.pathInfo());
	}

	private static void log(Response response) {
		String responseContent = response.body() == null ? "empty" : "Rocket.Chat message";
		log.info("Outgoing response: {}", responseContent);
	}

	private static void setApplicationJson(Response response) {
		if (response.body() == null) {
			response.type(APPLICATION_JSON);
		}
	}

	private Service start(Configuration config) {
		log.info("Initializing");
		IssueRestClient issueClient = createIssueRestClient(config.getJiraConfiguration());
		MessageCreator messageCreator = new MessageCreator(config.getMessageConfiguration());
		FieldCreatorMapper fieldCreatorMapper = new FieldCreatorMapper(config.getMessageConfiguration());
		log.debug("Finding default field creators");
		List<FieldCreator> defaultFieldCreators = fieldCreatorMapper.getCreators(config.getMessageConfiguration().getDefaultFields());
		log.debug("Finding extended field creators");
		List<FieldCreator> extendedFieldCreators = fieldCreatorMapper.getCreators(config.getMessageConfiguration().getExtendedFields());
		AttachmentConverter attachmentConverter = new AttachmentConverter(config.getMessageConfiguration(), defaultFieldCreators, extendedFieldCreators);

		log.info("Setting up server");
		Service server = ignite();
		server.threadPool(config.getAppConfiguration().getMaxThreads());
		server.port(config.getAppConfiguration().getPort());
		server.before((request, response) -> log(request));
		server.post("/", APPLICATION_JSON, new DetectIssueRoute(config.getRocketChatConfiguration(), issueClient, messageCreator, attachmentConverter));
		server.after((request, response) -> setApplicationJson(response));
		server.after((request, response) -> log(response));
		server.exception(Exception.class, new UuidGeneratingExceptionHandler());
		log.info("Server setup completed");
		return server;
	}

	void stop() {
		if (server == null) {
			throw new IllegalStateException("Already stopped");
		}
		server.stop();
		server = null;
	}
}
