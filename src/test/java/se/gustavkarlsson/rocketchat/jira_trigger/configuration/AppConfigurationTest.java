package se.gustavkarlsson.rocketchat.jira_trigger.configuration;

import com.moandjiezana.toml.Toml;
import org.junit.Before;
import org.junit.Test;
import se.gustavkarlsson.rocketchat.jira_trigger.test.TomlUtils;

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

	@Test
	public void getPort() throws Exception {
		new AppConfiguration(minimal, defaults).getPort();
	}

	@Test
	public void getMaxThreads() throws Exception {
		new AppConfiguration(minimal, defaults).getMaxThreads();
	}
}
