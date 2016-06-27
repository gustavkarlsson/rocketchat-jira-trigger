package se.gustavkarlsson.rocketchat.jira_trigger.configuration;

import com.moandjiezana.toml.Toml;
import org.junit.Before;
import org.junit.Test;
import se.gustavkarlsson.rocketchat.jira_trigger.test.MinimalToml;

public class MessageConfigurationTest {

	private Toml minimal;

	@Before
	public void setUp() throws Exception {
		minimal = MinimalToml.get();
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
	public void isPrintType() throws Exception {
		new MessageConfiguration(minimal).isPrintType();
	}

	@Test
	public void isPrintDescription() throws Exception {
		new MessageConfiguration(minimal).isPrintDescription();
	}

	@Test
	public void isPrintUpdated() throws Exception {
		new MessageConfiguration(minimal).isPrintUpdated();
	}

	@Test
	public void isPrintCreated() throws Exception {
		new MessageConfiguration(minimal).isPrintCreated();
	}

	@Test
	public void isPrintResolution() throws Exception {
		new MessageConfiguration(minimal).isPrintResolution();
	}

	@Test
	public void isPrintAssignee() throws Exception {
		new MessageConfiguration(minimal).isPrintAssignee();
	}

	@Test
	public void isPrintPriority() throws Exception {
		new MessageConfiguration(minimal).isPrintPriority();
	}

	@Test
	public void isPrintReporter() throws Exception {
		new MessageConfiguration(minimal).isPrintReporter();
	}

	@Test
	public void isPriorityColors() throws Exception {
		new MessageConfiguration(minimal).isPriorityColors();
	}

	@Test
	public void isPrintStatus() throws Exception {
		new MessageConfiguration(minimal).isPrintStatus();
	}

}
