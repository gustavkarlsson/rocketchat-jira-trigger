package se.gustavkarlsson.rocketchat.jira_trigger.converters;

import com.moandjiezana.toml.Toml;
import org.junit.Before;
import org.junit.Test;
import se.gustavkarlsson.rocketchat.jira_trigger.configuration.Configuration;
import se.gustavkarlsson.rocketchat.jira_trigger.configuration.MessageConfiguration;
import se.gustavkarlsson.rocketchat.jira_trigger.test.TomlUtils;

import static java.util.Collections.emptyList;

public class AttachmentConverterTest {

	private MessageConfiguration config;

	@Before
	public void setUp() throws Exception {
		Toml minimal = TomlUtils.getMinimalToml();
		Toml defaults = TomlUtils.getDefaultsToml();
		config = new Configuration(minimal, defaults).getMessageConfiguration();
	}

	@Test(expected = NullPointerException.class)
	public void createWithNullConfigThrowsNPE() throws Exception {
		new AttachmentConverter(null, emptyList(), emptyList());
	}

	@Test(expected = NullPointerException.class)
	public void createWithNullDefaultFieldCreatorsThrowsNPE() throws Exception {
		new AttachmentConverter(config, null, emptyList());
	}

	@Test(expected = NullPointerException.class)
	public void createWithNullExtendedFieldCreatorsConfigThrowsNPE() throws Exception {
		new AttachmentConverter(config, emptyList(), null);
	}

	@Test
	public void create() throws Exception {
		new AttachmentConverter(config, emptyList(), emptyList());
	}
}
