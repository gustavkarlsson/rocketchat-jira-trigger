package se.gustavkarlsson.rocketchat.jira_trigger.jira;

import com.atlassian.jira.rest.client.api.AuthenticationHandler;
import com.atlassian.jira.rest.client.auth.AnonymousAuthenticationHandler;
import com.atlassian.jira.rest.client.auth.BasicHttpAuthenticationHandler;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import se.gustavkarlsson.rocketchat.jira_trigger.configuration.JiraConfiguration;

import java.io.Console;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AuthHandlerProviderTest {

	@Mock
	JiraConfiguration mockJiraConfig;

	@Mock
	Console mockConsole;

	@Test
	public void getWithNoUsernameOrPasswordReturnsAnonymousAuthenticationHandler() throws Exception {
		AuthHandlerProvider authHandlerProvider = new AuthHandlerProvider(mockJiraConfig, mockConsole);

		AuthenticationHandler result = authHandlerProvider.get();

		assertThat(result).isInstanceOf(AnonymousAuthenticationHandler.class);
	}

	@Test
	public void getWithOnlyPasswordReturnsAnonymousAuthenticationHandler() throws Exception {
		when(mockJiraConfig.getPassword()).thenReturn("monkey123");
		AuthHandlerProvider authHandlerProvider = new AuthHandlerProvider(mockJiraConfig, mockConsole);

		AuthenticationHandler result = authHandlerProvider.get();

		assertThat(result).isInstanceOf(AnonymousAuthenticationHandler.class);
	}

	@Test
	public void getWithUsernameAndPasswordReturnsBasicHttpAuthenticationHandler() throws Exception {
		when(mockJiraConfig.getUsername()).thenReturn("johndoe");
		when(mockJiraConfig.getPassword()).thenReturn("monkey123");
		AuthHandlerProvider authHandlerProvider = new AuthHandlerProvider(mockJiraConfig, mockConsole);

		AuthenticationHandler result = authHandlerProvider.get();

		assertThat(result).isInstanceOf(BasicHttpAuthenticationHandler.class);
	}

	@Test
	public void getWithOnlyUsernameReadsPasswordFromConsoleAndReturnsBasicHttpAuthenticationHandler() throws Exception {
		when(mockJiraConfig.getUsername()).thenReturn("johndoe");
		when(mockConsole.readPassword(any())).thenReturn("monkey123".toCharArray());
		AuthHandlerProvider authHandlerProvider = new AuthHandlerProvider(mockJiraConfig, mockConsole);

		AuthenticationHandler result = authHandlerProvider.get();
		assertThat(result).isInstanceOf(BasicHttpAuthenticationHandler.class);
		verify(mockConsole).readPassword(any());
	}
}
