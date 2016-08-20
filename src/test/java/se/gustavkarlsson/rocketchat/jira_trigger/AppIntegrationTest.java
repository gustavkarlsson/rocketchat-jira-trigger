package se.gustavkarlsson.rocketchat.jira_trigger;

import com.google.common.io.Resources;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import spark.Service;

import javax.ws.rs.core.MediaType;
import java.nio.charset.StandardCharsets;

import static org.assertj.core.api.Assertions.assertThat;
import static spark.Service.ignite;

public class AppIntegrationTest {
	private static final int JIRA_SERVER_PORT = 9999;

	private static String rocketChatMessage;
	private static String jiraResponse;
	private static String expectedAppResponse;

	private Service jiraServer;
	private App app;
	private Client client;

	@BeforeClass
	public static void setUpClass() throws Exception {
		rocketChatMessage = Resources.toString(Resources.getResource("rocketchat_message.json"), StandardCharsets.UTF_8);
		jiraResponse = Resources.toString(Resources.getResource("jira_response.json"), StandardCharsets.UTF_8);
		expectedAppResponse = Resources.toString(Resources.getResource("expected_app_response.json"), StandardCharsets.UTF_8);
	}

	@Before
	public void setUp() throws Exception {
		jiraServer = startJiraServer(JIRA_SERVER_PORT, jiraResponse);
		app = new App(new String[]{"src/test/resources/minimal.toml"});
		client = Client.create();
	}

	@After
	public void tearDown() throws Exception {
		client.destroy();
		app.stop();
		jiraServer.stop();
	}

	private Service startJiraServer(int port, String response) {
		Service server = ignite().port(port);
		server.get("/rest/api/latest/issue/:issue", (req, res) -> response);
		return server;
	}

	private ClientResponse postMessage(String entity) {
		return client.resource("http://localhost:8888")
				.path("")
				.accept(MediaType.APPLICATION_JSON_TYPE)
				.type(MediaType.APPLICATION_JSON_TYPE)
				.post(ClientResponse.class, entity);
	}

	@Test
	public void fullStack() throws Exception {
		ClientResponse response = postMessage(rocketChatMessage);
		String body = response.getEntity(String.class);

		assertThat(response.getStatus() == 200);
		assertThat(response.getType()).isEqualTo(MediaType.APPLICATION_JSON_TYPE);
		JSONAssert.assertEquals(expectedAppResponse, body, false);
	}
}
