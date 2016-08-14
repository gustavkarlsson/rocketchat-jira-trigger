package se.gustavkarlsson.rocketchat.jira_trigger;

import com.google.common.io.Resources;
import com.moandjiezana.toml.Toml;
import com.sun.jersey.api.client.Client;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import se.gustavkarlsson.rocketchat.jira_trigger.configuration.Configuration;
import se.gustavkarlsson.rocketchat.jira_trigger.test.TomlUtils;
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

	private Configuration config;
	private Service jiraServer;
	private Thread appServer;
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
		Toml minimal = TomlUtils.getMinimalToml();
		Toml defaults = TomlUtils.getDefaultsToml();
		config = new Configuration(minimal, defaults);
		jiraServer = startJiraServer(JIRA_SERVER_PORT, jiraResponse);
		appServer = startAppServer(config);
		client = Client.create();
	}

	@After
	public void tearDown() throws Exception {
		jiraServer.stop();
		App.stop();
		appServer.join();
	}

	private Service startJiraServer(int port, String response) {
		Service server = ignite().port(port);
		server.get("/rest/api/latest/issue/:issue", (req, res) -> response);
		return server;
	}

	private Thread startAppServer(Configuration config) {
		Thread thread = new Thread(() -> App.start(config));
		thread.start();
		return thread;
	}

	private String postMessage(String entity) {
		return client.resource("http://localhost:" + config.getAppConfiguration().getPort())
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
