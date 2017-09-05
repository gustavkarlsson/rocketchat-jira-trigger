package se.gustavkarlsson.rocketchat.jira_trigger.configuration;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static se.gustavkarlsson.rocketchat.jira_trigger.configuration.JiraConfiguration.*;

@RunWith(MockitoJUnitRunner.class)
public class JiraConfigurationTest {
	private static final String URI = "https://restapp.com/api";
	private static final String USERNAME = "johndoe";
	private static final String PASSWORD = "monkey";

	@Mock
	private ConfigMap mockConfigMap;

	private JiraConfiguration jiraConfig;

	@Before
	public void setUp() throws Exception {
		when(mockConfigMap.getString(URI_KEY)).thenReturn(URI);
		when(mockConfigMap.getString(USER_KEY)).thenReturn(USERNAME);
		when(mockConfigMap.getString(PASSWORD_KEY)).thenReturn(PASSWORD);
		this.jiraConfig = new JiraConfiguration(mockConfigMap);
	}

	@Test(expected = NullPointerException.class)
	public void createWithNullConfigMapThrowsNPE() throws Exception {
		new JiraConfiguration(null);
	}

	@Test(expected = ValidationException.class)
	public void createWithInvalidUriConfigThrowsValidationException() throws Exception {
		when(mockConfigMap.getString(URI_KEY)).thenReturn("not a URI\\few %E//%");

		new JiraConfiguration(mockConfigMap);
	}

	@Test(expected = ValidationException.class)
	public void createWithNullUriConfigThrowsValidationException() throws Exception {
		when(mockConfigMap.getString(URI_KEY)).thenReturn(null);

		new JiraConfiguration(mockConfigMap);
	}

	@Test
	public void getUri() throws Exception {
		java.net.URI uri = jiraConfig.getUri();

		assertThat(uri.toString()).isEqualTo(URI);
	}

	@Test
	public void getUsername() throws Exception {
		String username = jiraConfig.getUsername();

		assertThat(username).isEqualTo(USERNAME);
	}

	@Test
	public void getPassword() throws Exception {
		String password = jiraConfig.getPassword();

		assertThat(password).isEqualTo(PASSWORD);
	}

}
