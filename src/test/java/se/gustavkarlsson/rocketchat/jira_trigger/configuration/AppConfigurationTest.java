package se.gustavkarlsson.rocketchat.jira_trigger.configuration;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import se.gustavkarlsson.rocketchat.jira_trigger.server.Server;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static se.gustavkarlsson.rocketchat.jira_trigger.configuration.AppConfiguration.MAX_THREADS_KEY;
import static se.gustavkarlsson.rocketchat.jira_trigger.configuration.AppConfiguration.PORT_KEY;

@RunWith(MockitoJUnitRunner.class)
public class AppConfigurationTest {
	private static final int PORT = 2000;
	private static final int MAX_THREADS = 10;

	@Mock
	private ConfigMap mockConfigMap;

	private AppConfiguration appConfig;

	@Before
	public void setUp() throws Exception {
		when(mockConfigMap.getLong(PORT_KEY)).thenReturn((long) PORT);
		when(mockConfigMap.getLong(MAX_THREADS_KEY)).thenReturn((long) MAX_THREADS);
		this.appConfig = new AppConfiguration(mockConfigMap);
	}

	@Test(expected = NullPointerException.class)
	public void createWithNullConfigMapThrowsNPE() throws Exception {
		new AppConfiguration(null);
	}

	@Test(expected = ValidationException.class)
	public void createWithTooHighPortThrowsValidationException() throws Exception {
		when(mockConfigMap.getLong(PORT_KEY)).thenReturn(Server.MAX_PORT_NUMBER + 1);

		new AppConfiguration(mockConfigMap);
	}

	@Test(expected = ValidationException.class)
	public void createWithTooLowPortThrowsValidationException() throws Exception {
		when(mockConfigMap.getLong(PORT_KEY)).thenReturn(0L);

		new AppConfiguration(mockConfigMap);
	}

	@Test
	public void createWithLowestPortSucceeds() throws Exception {
		when(mockConfigMap.getLong(PORT_KEY)).thenReturn(1L);

		new AppConfiguration(mockConfigMap);
	}

	@Test
	public void createWithHighestPortSucceeds() throws Exception {
		when(mockConfigMap.getLong(PORT_KEY)).thenReturn(Server.MAX_PORT_NUMBER);

		new AppConfiguration(mockConfigMap);
	}

	@Test(expected = ValidationException.class)
	public void createWithTooLowMaxThreadsThrowsValidationException() throws Exception {
		when(mockConfigMap.getLong(MAX_THREADS_KEY)).thenReturn(0L);

		new AppConfiguration(mockConfigMap);
	}

	@Test
	public void createWithLowestMaxThreadsSucceeds() throws Exception {
		when(mockConfigMap.getLong(MAX_THREADS_KEY)).thenReturn(1L);

		new AppConfiguration(mockConfigMap);
	}

	@Test
	public void getPort() {
		int port = appConfig.getPort();

		assertThat(port).isEqualTo(PORT);
	}

	@Test
	public void getMaxThreads() {
		int maxThreads = appConfig.getMaxThreads();

		assertThat(maxThreads).isEqualTo(MAX_THREADS);
	}
}
