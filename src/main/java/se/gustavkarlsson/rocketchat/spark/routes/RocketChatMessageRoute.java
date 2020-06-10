package se.gustavkarlsson.rocketchat.spark.routes;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import org.eclipse.jetty.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import se.gustavkarlsson.rocketchat.models.from_rocket_chat.FromRocketChatMessage;
import se.gustavkarlsson.rocketchat.models.to_rocket_chat.ToRocketChatMessage;
import spark.Request;
import spark.Response;
import spark.Route;

/**
 * A specialized route for handling Rocket.Chat messages. Does all the json marshalling/unmarshalling for you, as well
 * as setting the content-type "application/json" for outgoing messages.
 * <p>
 * Simply implement {@link #handle(Request, Response, FromRocketChatMessage)} and you're good to go.
 *
 * @see RocketChatMessageRoute#handle(spark.Request, spark.Response, se.gustavkarlsson.rocketchat.models.from_rocket_chat.FromRocketChatMessage)
 */
public abstract class RocketChatMessageRoute implements Route {
	private static final String ROCKETCHAT_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSSX";

	private static final Logger logger = LoggerFactory.getLogger(RocketChatMessageRoute.class);
	private static final Gson gson = new GsonBuilder().setDateFormat(ROCKETCHAT_DATE_FORMAT).create();

	@Override
	public final Object handle(Request request, Response response) throws Exception {
		String requestBody = request.body();
		FromRocketChatMessage fromRocketChatMessage;
		try {
			fromRocketChatMessage = gson.fromJson(requestBody, FromRocketChatMessage.class);
		} catch (JsonSyntaxException e) {
			logger.warn("Invalid JSON in request body: {}\n"
					+ "Body: {}", e.getCause().getMessage(), requestBody);
			return null;
		}
		ToRocketChatMessage responseMessage = handle(request, response, fromRocketChatMessage);
		if (responseMessage == null) {
			response.status(HttpStatus.OK_200);
			return "";
		}
		response.type("application/json");
		return gson.toJson(responseMessage);
	}

	/**
	 * Handle Rocket.Chat messages sent to your application. Messages are automatically marshalled/unmarshalled to/from json.
	 *
	 * @param request               The request object providing information about the HTTP request
	 * @param response              The response object providing functionality for modifying the response
	 * @param fromRocketChatMessage The message that Rocket.Chat sent out (Created by unmarshalling the json body of the request)
	 * @return The message to respond with, or <code>null</code> for an empty message (ignored by Rocket.Chat).
	 * This message will be marshalled to json and set as the body of the response.
	 * @throws Exception implementation can choose to throw exception
	 * @see Route#handle(spark.Request, spark.Response)
	 */
	@SuppressWarnings("WeakerAccess")
	protected abstract ToRocketChatMessage handle(Request request, Response response, FromRocketChatMessage fromRocketChatMessage) throws Exception;
}
