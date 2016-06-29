package se.gustavkarlsson.rocketchat.jira_trigger.configuration;

import com.moandjiezana.toml.Toml;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

import static org.apache.commons.lang3.Validate.notNull;

public class MessageConfiguration {
	static final String PRINT_DEFAULT_TABLE_KEY = "print_default";
	static final String PRINT_EXTENDED_TABLE_KEY = "print_extended";

	private static final String USERNAME_KEY = "username";
	private static final String ICON_URL_KEY = "icon_url";
	private static final String DATE_PATTERN_KEY = "date_pattern";
	private static final String DATE_LOCALE_KEY = "date_locale";
	private static final String PRIORITY_COLORS_KEY = "priority_colors";
	private static final String DEFAULT_COLOR_KEY = "default_color";

	private final String username;
	private final String iconUrl;
	private final DateFormat dateFormat;
	private final boolean priorityColors;
	private final String defaultColor;
	private final MessagePrintConfiguration printDefaultConfig;
	private final MessagePrintConfiguration printExtendedConfig;

	MessageConfiguration(Toml toml) throws ValidationException {
		notNull(toml);
		try {
			username = toml.getString(USERNAME_KEY);
			iconUrl = toml.getString(ICON_URL_KEY);
			dateFormat = new SimpleDateFormat(toml.getString(DATE_PATTERN_KEY),
					Locale.forLanguageTag(toml.getString(DATE_LOCALE_KEY)));
			priorityColors = toml.getBoolean(PRIORITY_COLORS_KEY);
			defaultColor = toml.getString(DEFAULT_COLOR_KEY);
			printDefaultConfig = new MessagePrintConfiguration(toml.getTable(PRINT_DEFAULT_TABLE_KEY));
			printExtendedConfig = new MessagePrintConfiguration(toml.getTable(PRINT_EXTENDED_TABLE_KEY));
		} catch (Exception e) {
			throw new ValidationException(e);
		}
	}

	public String getUsername() {
		return username;
	}

	public String getIconUrl() {
		return iconUrl;
	}

	public DateFormat getDateFormat() {
		return dateFormat;
	}

	public boolean isPriorityColors() {
		return priorityColors;
	}

	public String getDefaultColor() {
		return defaultColor;
	}

	public MessagePrintConfiguration getPrintDefaultConfig() {
		return printDefaultConfig;
	}

	public MessagePrintConfiguration getPrintExtendedConfig() {
		return printExtendedConfig;
	}
}
