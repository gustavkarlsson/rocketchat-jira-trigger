package se.gustavkarlsson.rocketchat.jira_trigger;

import com.atlassian.jira.rest.client.api.IssueRestClient;
import org.slf4j.Logger;
import se.gustavkarlsson.rocketchat.jira_trigger.configuration.Configuration;
import se.gustavkarlsson.rocketchat.jira_trigger.converters.AttachmentConverter;
import se.gustavkarlsson.rocketchat.jira_trigger.converters.MessageCreator;
import se.gustavkarlsson.rocketchat.jira_trigger.converters.fields.FieldCreator;
import se.gustavkarlsson.rocketchat.jira_trigger.routes.DetectIssueRoute;
import spark.Request;
import spark.Response;
import spark.Service;

import java.io.File;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;
import static spark.Service.ignite;

public class App {
	private static final Logger log = getLogger(App.class);

	private static final String APPLICATION_JSON = "application/json";

	private final ConfigurationProvider configReader = new ConfigurationProvider();
	private final RestClientProvider restClientProvider = new RestClientProvider();
	private Service server;

	App(String[] args) throws Exception {
		String configFilePath = verifySyntax(args);
		Configuration config = configReader.get(new File(configFilePath));
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

	private static void log(Request request) {
		log.info("Incoming request: {} {} {} {}",
				request.raw().getRemoteAddr(), request.requestMethod(), request.contentType(), request.pathInfo());
	}

	private static void log(Response response) {
		String responseContent = response.body() == null ? "empty" : "Rocket.Chat message";
		log.info("Outgoing response: {}", responseContent);
	}

	private static void setApplicationJson(Response response) {
		if (response.body() != null) {
			response.type(APPLICATION_JSON);
		}
	}

	private Service start(Configuration config) {
		log.info("Initializing");
		IssueRestClient issueClient = restClientProvider.get(config.getJiraConfiguration());
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
