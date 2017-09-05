package se.gustavkarlsson.rocketchat.jira_trigger.server;

import org.slf4j.Logger;
import se.gustavkarlsson.rocketchat.jira_trigger.routes.DetectIssueRoute;
import spark.Request;
import spark.Response;
import spark.Service;

import static org.slf4j.LoggerFactory.getLogger;
import static spark.Service.ignite;

public class Server {
	private static final Logger log = getLogger(Server.class);

	private static final String APPLICATION_JSON = "application/json";

	private final int maxThreads;
	private final int port;
	private final DetectIssueRoute detectIssueRoute;
	private final JiraServerInfoLogger jiraServerInfoLogger;

	private Service service;

	Server(int maxThreads, int port, DetectIssueRoute detectIssueRoute, JiraServerInfoLogger jiraServerInfoLogger) {
		this.maxThreads = maxThreads;
		this.port = port;
		this.detectIssueRoute = detectIssueRoute;
		this.jiraServerInfoLogger = jiraServerInfoLogger;
	}

	private static void logRequest(Request request) {
		log.info("Incoming request: {} {} {} {}",
				request.raw().getRemoteAddr(), request.requestMethod(), request.contentType(), request.pathInfo());
	}

	private static void logResponse(Response response) {
		String responseContent = response.body() == null ? "<empty>" : "Rocket.Chat message";
		log.info("Outgoing response: {}", responseContent);
	}

	public synchronized void start() {
		jiraServerInfoLogger.logInfo();
		log.info("Starting server...");
		if (service != null) {
			throw new IllegalStateException("Already started");
		}
		Service service = ignite();
		service.threadPool(maxThreads);
		service.port(port);
		service.before((request, response) -> logRequest(request));
		service.post("/", APPLICATION_JSON, detectIssueRoute);
		service.after((request, response) -> logResponse(response));
		service.exception(Exception.class, new UuidGeneratingExceptionHandler());
		this.service = service;
	}

	public synchronized void stop() {
		if (service == null) {
			throw new IllegalStateException("Already stopped");
		}
		service.stop();
		service = null;
	}
}
