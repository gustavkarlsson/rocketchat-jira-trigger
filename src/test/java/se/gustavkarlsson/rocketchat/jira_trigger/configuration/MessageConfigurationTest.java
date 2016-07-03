package se.gustavkarlsson.rocketchat.jira_trigger.configuration;

import com.moandjiezana.toml.Toml;
import org.junit.Before;
import org.junit.Test;
import se.gustavkarlsson.rocketchat.jira_trigger.test.TomlUtils;

import java.sql.Date;
import java.text.DateFormat;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static se.gustavkarlsson.rocketchat.jira_trigger.configuration.MessageConfiguration.*;

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

	@Test
	public void getPrintDefault() throws Exception {
		new MessageConfiguration(minimal, defaults).getDefaultFields();
	}

	@Test
	public void getPrintExtended() throws Exception {
		new MessageConfiguration(minimal, defaults).getExtendedFields();
	}

	@Test(expected = ValidationException.class)
	public void createWithInvalidDateFormatTomlThrowsValidationException() throws Exception {
		Toml toml = mock(Toml.class);
		when(toml.getString(KEY_PREFIX + DATE_PATTERN_KEY)).thenReturn("MP)H(/FF/T/&%G%UJ");
		new MessageConfiguration(toml, defaults);
	}

	@Test
	public void createWithSwedishDateLocaleTomlProducesSwedishDate() throws Exception {
		Toml toml = mock(Toml.class);
		when(toml.getString(KEY_PREFIX + DATE_LOCALE_KEY)).thenReturn("sv-SE");
		when(toml.getString(KEY_PREFIX + DATE_PATTERN_KEY)).thenReturn("E");
		DateFormat dateFormat = new MessageConfiguration(toml, defaults).getDateFormat();
		assertThat(dateFormat.format(new Date(0))).isEqualTo("to");
	}

	@Test
	public void createWithUsEnglishDateLocaleTomlProducesSwedishDate() throws Exception {
		Toml toml = mock(Toml.class);
		when(toml.getString(KEY_PREFIX + DATE_LOCALE_KEY)).thenReturn("en-US");
		when(toml.getString(KEY_PREFIX + DATE_PATTERN_KEY)).thenReturn("E");
		DateFormat dateFormat = new MessageConfiguration(toml, defaults).getDateFormat();
		assertThat(dateFormat.format(new Date(0))).isEqualTo("Thu");
	}
}
