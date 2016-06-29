package se.gustavkarlsson.rocketchat.jira_trigger.configuration;

import com.moandjiezana.toml.Toml;
import org.junit.Before;
import org.junit.Test;
import se.gustavkarlsson.rocketchat.jira_trigger.test.MinimalToml;

public class AppConfigurationTest {

	private Toml minimal;

	@Before
	public void setUp() throws Exception {
		minimal = MinimalToml.get().getTable(Configuration.APP_KEY);
	}

	@Test(expected = NullPointerException.class)
	public void createWithNullTomlThrowsNPE() throws Exception {
		new AppConfiguration(null);
	}

	@Test
	public void createWithMinimalToml() throws Exception {
		new AppConfiguration(minimal);
	}

	@Test
	public void getPort() throws Exception {
		new AppConfiguration(minimal).getPort();
	}
}
