package se.gustavkarlsson.rocketchat.jira_trigger;

import com.atlassian.jira.rest.client.api.JiraRestClient;
import org.junit.Before;
import org.junit.Test;
import se.gustavkarlsson.rocketchat.jira_trigger.configuration.JiraConfiguration;

import java.net.URI;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class RestClientProviderTest {

	private JiraConfiguration mockConfig;
	private PasswordReadingConsole mockConsole;
	private RestClientProvider provider;

	@Before
	public void setUp() throws Exception {
		mockConfig = mock(JiraConfiguration.class);
		mockConsole = mock(PasswordReadingConsole.class);
		provider = new RestClientProvider(mockConsole);
	}

	@Test(expected = NullPointerException.class)
	public void getWithNullConfigThrowsNPE() throws Exception {
		provider.get(null);
	}

	@Test(expected = NullPointerException.class)
	public void getWithEmptyConfigThrowsNPE() throws Exception {
		JiraRestClient client = provider.get(mockConfig);
		assertThat(client).isNotNull();
	}

	@Test
	public void getWithUriAndUsernameConfigFailsBecauseOfMissingConsole() throws Exception {
		when(mockConfig.getUri()).thenReturn(URI.create("https://jira.company.com:1234"));
		when(mockConfig.getUsername()).thenReturn("johndoe");
		when(mockConsole.readPassword(anyString())).thenReturn("monkey123".toCharArray());
		JiraRestClient client = provider.get(mockConfig);
		assertThat(client).isNotNull();
	}

	@Test
	public void getWithUriAndPasswordConfigIsOk() throws Exception {
		when(mockConfig.getUri()).thenReturn(URI.create("https://jira.company.com:1234"));
		when(mockConfig.getPassword()).thenReturn("monkey123");
		JiraRestClient client = provider.get(mockConfig);
		assertThat(client).isNotNull();
	}

	@Test
	public void getWithFullConfigIsOk() throws Exception {
		when(mockConfig.getUri()).thenReturn(URI.create("https://jira.company.com:1234"));
		when(mockConfig.getUsername()).thenReturn("johndoe");
		when(mockConfig.getPassword()).thenReturn("monkey123");
		JiraRestClient client = provider.get(mockConfig);
		assertThat(client).isNotNull();
	}
}
