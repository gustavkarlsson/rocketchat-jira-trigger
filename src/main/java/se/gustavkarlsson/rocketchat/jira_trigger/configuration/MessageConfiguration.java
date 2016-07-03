package se.gustavkarlsson.rocketchat.jira_trigger.configuration;

import com.moandjiezana.toml.Toml;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class MessageConfiguration extends DefaultingConfiguration {
	static final String KEY_PREFIX = "message.";

	static final String USERNAME_KEY = "username";
	static final String ICON_URL_KEY = "icon_url";
	static final String DATE_PATTERN_KEY = "date_pattern";
	static final String DATE_LOCALE_KEY = "date_locale";
	static final String PRIORITY_COLORS_KEY = "priority_colors";
	static final String DEFAULT_COLOR_KEY = "default_color";
	static final String DEFAULT_FIELDS_KEY = "default_fields";
	static final String EXTENDED_FIELDS_KEY = "extended_fields";

	private final String username;
	private final String iconUrl;
	private final DateFormat dateFormat;
	private final boolean priorityColors;
	private final String defaultColor;
	private final List<String> defaultFields;
	private final List<String> extendedFields;

	MessageConfiguration(Toml toml, Toml defaults) throws ValidationException {
		super(toml, defaults);
		try {
			username = getString(KEY_PREFIX + USERNAME_KEY);
			iconUrl = getString(KEY_PREFIX + ICON_URL_KEY);
			dateFormat = new SimpleDateFormat(getString(KEY_PREFIX + DATE_PATTERN_KEY),
					Locale.forLanguageTag(getString(KEY_PREFIX + DATE_LOCALE_KEY)));
			priorityColors = getBoolean(KEY_PREFIX + PRIORITY_COLORS_KEY);
			defaultColor = getString(KEY_PREFIX + DEFAULT_COLOR_KEY);
			defaultFields = getList(KEY_PREFIX + DEFAULT_FIELDS_KEY);
			extendedFields = getList(KEY_PREFIX + EXTENDED_FIELDS_KEY);
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

	public List<String> getDefaultFields() {
		return defaultFields;
	}

	public List<String> getExtendedFields() {
		return extendedFields;
	}
}
