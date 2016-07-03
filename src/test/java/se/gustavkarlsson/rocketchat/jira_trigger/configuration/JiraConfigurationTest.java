package se.gustavkarlsson.rocketchat.jira_trigger.configuration;

import com.moandjiezana.toml.Toml;
import org.junit.Before;
import org.junit.Test;
import se.gustavkarlsson.rocketchat.jira_trigger.test.TomlUtils;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static se.gustavkarlsson.rocketchat.jira_trigger.configuration.JiraConfiguration.KEY_PREFIX;
import static se.gustavkarlsson.rocketchat.jira_trigger.configuration.JiraConfiguration.URI_KEY;

public class JiraConfigurationTest {

	private Toml minimal;
	private Toml defaults;

	@Before
	public void setUp() throws Exception {
		minimal = TomlUtils.getMinimalToml();
		defaults = TomlUtils.getDefaultsToml();
	}

	@Test(expected = NullPointerException.class)
	public void createWithNullTomlThrowsNPE() throws Exception {
		new JiraConfiguration(null, defaults);
	}

	@Test
	public void createWithMinimalToml() throws Exception {
		new JiraConfiguration(minimal, defaults);
	}

	@Test(expected = ValidationException.class)
	public void createWithInvalidUriTomlThrowsValidationException() throws Exception {
		Toml toml = mock(Toml.class);
		when(toml.getString(KEY_PREFIX + URI_KEY)).thenReturn("not a URI\\few %E//%");
		new JiraConfiguration(toml, defaults);
	}

	@Test
	public void getUri() throws Exception {
		new JiraConfiguration(minimal, defaults).getUri();
	}

	@Test
	public void getUsername() throws Exception {
		new JiraConfiguration(minimal, defaults).getUsername();
	}

	@Test
	public void getPassword() throws Exception {
		new JiraConfiguration(minimal, defaults).getPassword();
	}

}
