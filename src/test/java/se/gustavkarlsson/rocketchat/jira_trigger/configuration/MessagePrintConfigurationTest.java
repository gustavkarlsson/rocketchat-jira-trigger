package se.gustavkarlsson.rocketchat.jira_trigger.configuration;

import com.moandjiezana.toml.Toml;
import org.junit.Before;
import org.junit.Test;
import se.gustavkarlsson.rocketchat.jira_trigger.test.TomlUtils;

public class MessagePrintConfigurationTest {

	private static final String defaultKey = MessageConfiguration.KEY_PREFIX + MessageConfiguration.PRINT_DEFAULT_TABLE_KEY + ".";
	private Toml minimal;
	private Toml defaults;

	@Before
	public void setUp() throws Exception {
		minimal = TomlUtils.getMinimalToml();
		defaults = TomlUtils.getDefaultsToml();
	}

	@Test(expected = NullPointerException.class)
	public void createWithNullTomlThrowsNPE() throws Exception {
		new MessagePrintConfiguration(null, defaults, defaultKey);
	}

	@Test
	public void createWithMinimalToml() throws Exception {
		new MessagePrintConfiguration(minimal, defaults, defaultKey);
	}

	@Test
	public void isPrintType() throws Exception {
		new MessagePrintConfiguration(minimal, defaults, defaultKey).isPrintType();
	}

	@Test
	public void isPrintDescription() throws Exception {
		new MessagePrintConfiguration(minimal, defaults, defaultKey).isPrintDescription();
	}

	@Test
	public void isPrintUpdated() throws Exception {
		new MessagePrintConfiguration(minimal, defaults, defaultKey).isPrintUpdated();
	}

	@Test
	public void isPrintCreated() throws Exception {
		new MessagePrintConfiguration(minimal, defaults, defaultKey).isPrintCreated();
	}

	@Test
	public void isPrintResolution() throws Exception {
		new MessagePrintConfiguration(minimal, defaults, defaultKey).isPrintResolution();
	}

	@Test
	public void isPrintAssignee() throws Exception {
		new MessagePrintConfiguration(minimal, defaults, defaultKey).isPrintAssignee();
	}

	@Test
	public void isPrintPriority() throws Exception {
		new MessagePrintConfiguration(minimal, defaults, defaultKey).isPrintPriority();
	}

	@Test
	public void isPrintReporter() throws Exception {
		new MessagePrintConfiguration(minimal, defaults, defaultKey).isPrintReporter();
	}

	@Test
	public void isPrintStatus() throws Exception {
		new MessagePrintConfiguration(minimal, defaults, defaultKey).isPrintStatus();
	}
}
