package se.gustavkarlsson.rocketchat.jira_trigger.configuration;

import com.moandjiezana.toml.Toml;
import org.junit.Before;
import org.junit.Test;
import se.gustavkarlsson.rocketchat.jira_trigger.test.TomlUtils;

public class MessageConfigurationTest {

	private Toml minimal;
	private Toml defaults;

	@Before
	public void setUp() throws Exception {
		minimal = TomlUtils.getMinimalToml();
		defaults = TomlUtils.getDefaultsToml();
	}

	@Test(expected = NullPointerException.class)
	public void createWithNullTomlThrowsNPE() throws Exception {
		new MessageConfiguration(null, defaults);
	}

	@Test
	public void createWithMinimalToml() throws Exception {
		new MessageConfiguration(minimal, defaults);
	}

	@Test
	public void getDateFormat() throws Exception {
		new MessageConfiguration(minimal, defaults).getDateFormat();
	}

	@Test
	public void getUsername() throws Exception {
		new MessageConfiguration(minimal, defaults).getUsername();
	}

	@Test
	public void getDefault() throws Exception {
		new MessageConfiguration(minimal, defaults).getDefaultColor();
	}

	@Test
	public void getIconUrl() throws Exception {
		new MessageConfiguration(minimal, defaults).getIconUrl();
	}

	@Test
	public void isPriorityColors() throws Exception {
		new MessageConfiguration(minimal, defaults).isPriorityColors();
	}
}
