package se.gustavkarlsson.rocketchat.jira_trigger.configuration;

import com.moandjiezana.toml.Toml;
import org.junit.Before;
import org.junit.Test;
import se.gustavkarlsson.rocketchat.jira_trigger.test.MinimalToml;

public class MessagePrintConfigurationTest {

	private Toml minimal;

	@Before
	public void setUp() throws Exception {
		minimal = MinimalToml.get().getTable(Configuration.MESSAGE_KEY + "." + MessageConfiguration.PRINT_DEFAULT_TABLE_KEY);
	}

	@Test(expected = NullPointerException.class)
	public void createWithNullTomlThrowsNPE() throws Exception {
		new MessagePrintConfiguration(null);
	}

	@Test
	public void createWithMinimalToml() throws Exception {
		new MessagePrintConfiguration(minimal);
	}

	@Test
	public void isPrintType() throws Exception {
		new MessagePrintConfiguration(minimal).isPrintType();
	}

	@Test
	public void isPrintDescription() throws Exception {
		new MessagePrintConfiguration(minimal).isPrintDescription();
	}

	@Test
	public void isPrintUpdated() throws Exception {
		new MessagePrintConfiguration(minimal).isPrintUpdated();
	}

	@Test
	public void isPrintCreated() throws Exception {
		new MessagePrintConfiguration(minimal).isPrintCreated();
	}

	@Test
	public void isPrintResolution() throws Exception {
		new MessagePrintConfiguration(minimal).isPrintResolution();
	}

	@Test
	public void isPrintAssignee() throws Exception {
		new MessagePrintConfiguration(minimal).isPrintAssignee();
	}

	@Test
	public void isPrintPriority() throws Exception {
		new MessagePrintConfiguration(minimal).isPrintPriority();
	}

	@Test
	public void isPrintReporter() throws Exception {
		new MessagePrintConfiguration(minimal).isPrintReporter();
	}

	@Test
	public void isPrintStatus() throws Exception {
		new MessagePrintConfiguration(minimal).isPrintStatus();
	}
}
