package se.gustavkarlsson.rocketchat.jira_trigger.configuration;

import com.moandjiezana.toml.Toml;
import org.junit.Before;
import org.junit.Test;
import se.gustavkarlsson.rocketchat.jira_trigger.test.MinimalToml;

public class MessageConfigurationTest {

	private Toml minimal;

	@Before
	public void setUp() throws Exception {
		minimal = MinimalToml.get().getTable(Configuration.MESSAGE_KEY);
	}

	@Test(expected = NullPointerException.class)
	public void createWithNullTomlThrowsNPE() throws Exception {
		new MessageConfiguration(null);
	}

	@Test
	public void createWithMinimalToml() throws Exception {
		new MessageConfiguration(minimal);
	}

	@Test
	public void getDateFormat() throws Exception {
		new MessageConfiguration(minimal).getDateFormat();
	}

	@Test
	public void getUsername() throws Exception {
		new MessageConfiguration(minimal).getUsername();
	}

	@Test
	public void getDefault() throws Exception {
		new MessageConfiguration(minimal).getDefaultColor();
	}

	@Test
	public void getIconUrl() throws Exception {
		new MessageConfiguration(minimal).getIconUrl();
	}

	@Test
	public void isPriorityColors() throws Exception {
		new MessageConfiguration(minimal).isPriorityColors();
	}
}
