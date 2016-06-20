package se.gustavkarlsson.rocketchat.jira_trigger;

import org.eclipse.jetty.http.HttpStatus;
import org.slf4j.Logger;
import spark.ExceptionHandler;
import spark.Request;
import spark.Response;

import java.util.UUID;

import static org.slf4j.LoggerFactory.getLogger;

class UuidGeneratingExceptionHandler implements ExceptionHandler {
	private static final Logger log = getLogger(UuidGeneratingExceptionHandler.class);

	@Override
	public void handle(Exception exception, Request request, Response response) {
		String errorId = UUID.randomUUID().toString();
		String errorText = "Unexpected server error: " + errorId;
		log.error(errorText, exception);
		response.status(HttpStatus.INTERNAL_SERVER_ERROR_500);
		response.body(errorText);
	}
}
