package se.gustavkarlsson.rocketchat.jira_trigger.configuration;

import com.moandjiezana.toml.Toml;
import org.junit.Before;
import org.junit.Test;
import se.gustavkarlsson.rocketchat.jira_trigger.test.TomlUtils;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static se.gustavkarlsson.rocketchat.jira_trigger.configuration.AppConfiguration.*;

public class AppConfigurationTest {

	private Toml minimal;
	private Toml defaults;

	@Before
	public void setUp() throws Exception {
		minimal = TomlUtils.getMinimalToml();
		defaults = TomlUtils.getDefaultsToml();
	}

	@Test(expected = NullPointerException.class)
	public void createWithNullTomlThrowsNPE() throws Exception {
		new AppConfiguration(null, defaults);
	}

	@Test
	public void createWithMinimalToml() throws Exception {
		new AppConfiguration(minimal, defaults);
	}

	@Test(expected = ValidationException.class)
	public void createWithTooHighPortThrowsValidationException() throws Exception {
		Toml toml = mock(Toml.class);
		when(toml.getLong(KEY_PREFIX + PORT_KEY)).thenReturn((long) Integer.MAX_VALUE + 1);
		when(toml.getLong(KEY_PREFIX + MAX_THREADS_KEY)).thenReturn(10L);
		new AppConfiguration(toml, defaults);
	}

	@Test(expected = ValidationException.class)
	public void createWithTooLowPortThrowsValidationException() throws Exception {
		Toml toml = mock(Toml.class);
		when(toml.getLong(KEY_PREFIX + PORT_KEY)).thenReturn(0L);
		when(toml.getLong(KEY_PREFIX + MAX_THREADS_KEY)).thenReturn(10L);
		new AppConfiguration(toml, defaults);
	}

	@Test
	public void createWithLowestPortSucceeds() throws Exception {
		Toml toml = mock(Toml.class);
		when(toml.getLong(KEY_PREFIX + PORT_KEY)).thenReturn(1L);
		when(toml.getLong(KEY_PREFIX + MAX_THREADS_KEY)).thenReturn(10L);
		new AppConfiguration(toml, defaults);
	}

	@Test
	public void createWithHighestPortSucceeds() throws Exception {
		Toml toml = mock(Toml.class);
		when(toml.getLong(KEY_PREFIX + PORT_KEY)).thenReturn((long) Integer.MAX_VALUE);
		when(toml.getLong(KEY_PREFIX + MAX_THREADS_KEY)).thenReturn(10L);
		new AppConfiguration(toml, defaults);
	}

	@Test(expected = ValidationException.class)
	public void createWithTooHighMaxThreadsThrowsValidationException() throws Exception {
		Toml toml = mock(Toml.class);
		when(toml.getLong(KEY_PREFIX + MAX_THREADS_KEY)).thenReturn(MAX_THREADS + 1);
		when(toml.getLong(KEY_PREFIX + PORT_KEY)).thenReturn(10L);
		new AppConfiguration(toml, defaults);
	}

	@Test(expected = ValidationException.class)
	public void createWithTooLowMaxThreadsThrowsValidationException() throws Exception {
		Toml toml = mock(Toml.class);
		when(toml.getLong(KEY_PREFIX + MAX_THREADS_KEY)).thenReturn(0L);
		when(toml.getLong(KEY_PREFIX + PORT_KEY)).thenReturn(10L);
		new AppConfiguration(toml, defaults);
	}

	@Test
	public void createWithLowestMaxThreadsSucceeds() throws Exception {
		Toml toml = mock(Toml.class);
		when(toml.getLong(KEY_PREFIX + MAX_THREADS_KEY)).thenReturn(1L);
		when(toml.getLong(KEY_PREFIX + PORT_KEY)).thenReturn(10L);
		new AppConfiguration(toml, defaults);
	}

	@Test
	public void createWithHighestMaxThreadsSucceeds() throws Exception {
		Toml toml = mock(Toml.class);
		when(toml.getLong(KEY_PREFIX + MAX_THREADS_KEY)).thenReturn(MAX_THREADS);
		when(toml.getLong(KEY_PREFIX + PORT_KEY)).thenReturn(10L);
		new AppConfiguration(toml, defaults);
	}

	@Test
	public void getPort() throws Exception {
		new AppConfiguration(minimal, defaults).getPort();
	}

	@Test
	public void getMaxThreads() throws Exception {
		new AppConfiguration(minimal, defaults).getMaxThreads();
	}
}
