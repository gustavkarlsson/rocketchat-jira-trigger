package se.gustavkarlsson.rocketchat.jira_trigger.routes;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import se.gustavkarlsson.rocketchat.jira_trigger.models.IncomingMessage;
import se.gustavkarlsson.rocketchat.jira_trigger.models.OutgoingMessage;
import spark.Request;
import spark.Response;
import spark.Route;

abstract class RocketChatMessageRoute implements Route {

	private final Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSX").create();

	@Override
	public final Object handle(Request request, Response response) throws Exception {
		OutgoingMessage outgoing = gson.fromJson(request.body(), OutgoingMessage.class);
		IncomingMessage responseMessage = handle(request, response, outgoing);
		if (responseMessage == null) {
			return "";
		}
		return gson.toJson(responseMessage);
	}

	protected abstract IncomingMessage handle(Request request, Response response, OutgoingMessage outgoing) throws Exception;
}
