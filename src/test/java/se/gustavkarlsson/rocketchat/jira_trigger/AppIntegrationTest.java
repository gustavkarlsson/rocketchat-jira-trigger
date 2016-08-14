package se.gustavkarlsson.rocketchat.jira_trigger;

import com.google.common.io.Resources;
import com.sun.jersey.api.client.Client;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import spark.Service;

import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import static spark.Service.ignite;

public class AppIntegrationTest {
	private static final int JIRA_SERVER_PORT = 9999;
	private static final String rocketChatMessage = "{" +
			"\"token\": \"12345\"," +
			"\"channel_id\": \"12356\"," +
			"\"channel_name\": \"testing-channel\"," +
			"\"timestamp\": \"2016-06-15T15:11:05.270Z\"," +
			"\"user_id\": \"123567\"," +
			"\"user_name\": \"tester\"," +
			"\"text\": \"Let's have a look at ISS-123\"" +
			"}";
	private static String jiraResponse;
	private static String expectedAppResponse;

	private Service jiraServer;
	private App app;
	private Client client;

	@BeforeClass
	public static void setUpClass() throws Exception {
		jiraResponse = readJiraResponse();
		expectedAppResponse = readExpectedAppResponse();
	}

	private static String readJiraResponse() {
		try {
			URL resource = AppIntegrationTest.class.getClassLoader().getResource("jira_response.json");
			return Resources.toString(resource, StandardCharsets.UTF_8);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	private static String readExpectedAppResponse() {
		try {
			URL resource = AppIntegrationTest.class.getClassLoader().getResource("expected_app_response.json");
			return Resources.toString(resource, StandardCharsets.UTF_8);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
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

	private String postMessage(String entity) {
		return client.resource("http://localhost:8888")
				.path("")
				.accept(MediaType.APPLICATION_JSON_TYPE)
				.type(MediaType.APPLICATION_JSON_TYPE)
				.post(String.class, entity);
	}

	@Test
	public void fullStack() throws Exception {
		String response = postMessage(rocketChatMessage);

		JSONAssert.assertEquals(expectedAppResponse, response, false);
	}
}
