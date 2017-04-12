package se.gustavkarlsson.rocketchat.jira_trigger.routes;

import org.junit.Before;
import org.junit.Test;
import se.gustavkarlsson.rocketchat.models.from_rocket_chat.FromRocketChatMessage;
import se.gustavkarlsson.rocketchat.models.to_rocket_chat.ToRocketChatMessage;
import spark.Request;
import spark.Response;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class RocketChatMessageRouteTest {

	private Request request;
	private Response response;

	@Before
	public void setUp() throws Exception {
		request = mock(Request.class);
		response = mock(Response.class);
	}

	@Test
	public void invalidJsonReturnsEmptyString() throws Exception {
		RocketChatMessageRoute route = new RocketChatMessageRoute() {
			@Override
			protected Optional<ToRocketChatMessage> handle(Request request, Response response, FromRocketChatMessage fromRocketChatMessage) throws Exception {
				fail("Should not reach this method");
				return Optional.empty();
			}
		};
		when(request.body()).thenReturn("invalid json");

		assertThat(route.handle(request, response)).isEqualTo("");
	}
}
