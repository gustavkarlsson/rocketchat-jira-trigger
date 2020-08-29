package se.gustavkarlsson.rocketchat.jira_trigger.configuration;

import org.joda.time.Instant;
import org.joda.time.ReadableInstant;
import org.joda.time.format.DateTimeFormatter;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static se.gustavkarlsson.rocketchat.jira_trigger.configuration.MessageConfiguration.*;

@RunWith(MockitoJUnitRunner.class)
public class MessageConfigurationTest {
	private static final ReadableInstant EPOCH = new Instant(0);

	@Mock
	private ConfigMap mockConfigMap;

	@Before
	public void setUp() throws Exception {
		when(mockConfigMap.getString(DATE_PATTERN_KEY)).thenReturn("EEE, d MMM, yyyy");
		when(mockConfigMap.getString(DATE_LOCALE_KEY)).thenReturn("en-US");
		when(mockConfigMap.getString(WHITELISTED_KEY_PREFIXES_KEY)).thenReturn("A");
		when(mockConfigMap.getString(WHITELISTED_KEY_SUFFIXES_KEY)).thenReturn("B");
	}

	@Test(expected = NullPointerException.class)
	public void createWithNullConfigMapThrowsNPE() throws Exception {
		new MessageConfiguration(null);
	}

	@Test(expected = ValidationException.class)
	public void createWithInvalidDateFormatConfigMapThrowsValidationException() throws Exception {
		when(mockConfigMap.getString(DATE_PATTERN_KEY)).thenReturn("MP)H(/FF/T/&%G%UJ");

		new MessageConfiguration(mockConfigMap);
	}

	@Test
	public void createWithUsEnglishDateLocaleConfigMapProducesEnglishDate() throws Exception {
		when(mockConfigMap.getString(DATE_LOCALE_KEY)).thenReturn("en-US");
		when(mockConfigMap.getString(DATE_PATTERN_KEY)).thenReturn("E");
		when(mockConfigMap.getString(DEFAULT_COLOR_KEY)).thenReturn("#123123");
		MessageConfiguration messageConfig = new MessageConfiguration(mockConfigMap);

		DateTimeFormatter dateFormatter = messageConfig.getDateFormatter();

		assertThat(dateFormatter.print(EPOCH)).isEqualTo("Thu");
	}
}
