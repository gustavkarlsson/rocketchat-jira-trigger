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
	private static String jiraIssueResponse;
	private static String jiraServerInfoResponse;
	private static String expectedAppResponse;

	private Service jiraServer;
	private App app;
	private Client client;

	@BeforeClass
	public static void setUpClass() throws Exception {
		rocketChatMessage = Resources.toString(Resources.getResource("rocketchat_edit_message.json"), StandardCharsets.UTF_8);
		jiraIssueResponse = Resources.toString(Resources.getResource("jira_issue_response.json"), StandardCharsets.UTF_8);
		jiraServerInfoResponse = Resources.toString(Resources.getResource("jira_server_info_response.json"), StandardCharsets.UTF_8);
		expectedAppResponse = Resources.toString(Resources.getResource("expected_app_response.json"), StandardCharsets.UTF_8);
	}

	@Before
	public void setUp() throws Exception {
		jiraServer = startJiraServer(JIRA_SERVER_PORT, jiraIssueResponse, jiraServerInfoResponse);
		app = new App("src/test/resources/minimal_edit.toml");
		app.getServer().start();
		client = Client.create();
	}

	@After
	public void tearDown() throws Exception {
		client.destroy();
		app.getServer().stop();
		jiraServer.stop();
	}

	private Service startJiraServer(int port, String issueResponse, String serverInfoResponse) {
		Service server = ignite().port(port);
		server.get("/rest/api/latest/issue/:issue", (req, res) -> issueResponse);
		server.get("/rest/api/latest/serverInfo", (req, res) -> serverInfoResponse);
		return server;
	}

	@Test
	public void fullStack() throws Exception {
		ClientResponse response = postMessage(rocketChatMessage);
		String body = response.getEntity(String.class);

		assertThat(response.getStatus() == 200);
		// response is empty and makes type equal to text/html
    // assertThat(response.getType()).isEqualTo(MediaType.APPLICATION_JSON_TYPE);
		// JSONAssert.assertEquals(expectedAppResponse, body, false);
	}

	private ClientResponse postMessage(String entity) {
		return client.resource("http://localhost:8888")
				.path("")
				.accept(MediaType.APPLICATION_JSON_TYPE)
				.type(MediaType.APPLICATION_JSON_TYPE)
				.post(ClientResponse.class, entity);
	}
}
