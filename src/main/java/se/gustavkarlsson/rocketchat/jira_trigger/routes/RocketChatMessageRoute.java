package se.gustavkarlsson.rocketchat.jira_trigger.routes;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import org.slf4j.Logger;
import se.gustavkarlsson.rocketchat.jira_trigger.models.IncomingMessage;
import se.gustavkarlsson.rocketchat.jira_trigger.models.OutgoingMessage;
import spark.Request;
import spark.Response;
import spark.Route;

import java.util.Optional;

import static org.slf4j.LoggerFactory.getLogger;

abstract class RocketChatMessageRoute implements Route {
	private static final Logger log = getLogger(RocketChatMessageRoute.class);
	private static final String EMPTY_RESPONSE = "";

	private final Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSX").create();

	@Override
	public final Object handle(Request request, Response response) throws Exception {
		String requestBody = request.body();
		OutgoingMessage outgoing;
		try {
			outgoing = gson.fromJson(requestBody, OutgoingMessage.class);
		} catch (JsonSyntaxException e) {
			log.warn("Invalid JSON in request: " + e.getCause().getMessage() + "\n"
					+ "JSON: " + requestBody);
			return EMPTY_RESPONSE;
		}
		Optional<IncomingMessage> responseMessage = handle(request, response, outgoing);
		return responseMessage.map(gson::toJson).orElse(EMPTY_RESPONSE);
	}

	protected abstract Optional<IncomingMessage> handle(Request request, Response response, OutgoingMessage outgoing) throws Exception;
}
