package se.gustavkarlsson.rocketchat.jira_trigger.configuration;

import com.moandjiezana.toml.Toml;
import org.junit.Before;
import org.junit.Test;
import se.gustavkarlsson.rocketchat.jira_trigger.test.MinimalToml;

public class JiraConfigurationTest {

	private Toml minimal;

	@Before
	public void setUp() throws Exception {
		minimal = MinimalToml.get().getTable(Configuration.JIRA_KEY);
	}

	@Test(expected = NullPointerException.class)
	public void createWithNullTomlThrowsNPE() throws Exception {
		new JiraConfiguration(null);
	}

	@Test
	public void createWithMinimalToml() throws Exception {
		new JiraConfiguration(minimal);
	}

	@Test
	public void getUri() throws Exception {
		new JiraConfiguration(minimal).getUri();
	}

	@Test
	public void getUsername() throws Exception {
		new JiraConfiguration(minimal).getUsername();
	}

	@Test
	public void getPassword() throws Exception {
		new JiraConfiguration(minimal).getPassword();
	}

}
