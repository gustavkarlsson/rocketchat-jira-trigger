package se.gustavkarlsson.rocketchat.jira_trigger.configuration;

import com.moandjiezana.toml.Toml;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

import static org.apache.commons.lang3.Validate.notNull;

public class MessageConfiguration {
	private static final String KEY_PREFIX = "message.";
	private static final String USERNAME_KEY = "username";
	private static final String ICON_URL_KEY = "icon_url";
	private static final String DATE_PATTERN_KEY = "date_pattern";
	private static final String DATE_LOCALE_KEY = "date_locale";
	private static final String PRIORITY_COLORS_KEY = "priority_colors";
	private static final String DEFAULT_COLOR_KEY = "default_color";
	private static final String PRINT_DESCRIPTION_KEY = "print_description";
	private static final String PRINT_ASSIGNEE_KEY = "print_assignee";
	private static final String PRINT_STATUS_KEY = "print_status";
	private static final String PRINT_REPORTER_KEY = "print_reporter";
	private static final String PRINT_PRIORITY_KEY = "print_priority";
	private static final String PRINT_RESOLUTION_KEY = "print_resolution";
	private static final String PRINT_TYPE_KEY = "print_type";
	private static final String PRINT_CREATED_KEY = "print_created";
	private static final String PRINT_UPDATED_KEY = "print_updated";

	private final String username;
	private final String iconUrl;
	private final DateFormat dateFormat;
	private final boolean priorityColors;
	private final String defaultColor;
	private final boolean printDescription;
	private final boolean printAssignee;
	private final boolean printStatus;
	private final boolean printReporter;
	private final boolean printPriority;
	private final boolean printResolution;
	private final boolean printCreated;
	private final boolean printUpdated;
	private final boolean printType;

	MessageConfiguration(Toml toml) throws ValidationException {
		notNull(toml);
		try {
			username = toml.getString(KEY_PREFIX + USERNAME_KEY);
			iconUrl = toml.getString(KEY_PREFIX + ICON_URL_KEY);
			dateFormat = new SimpleDateFormat(toml.getString(KEY_PREFIX + DATE_PATTERN_KEY),
					Locale.forLanguageTag(toml.getString(KEY_PREFIX + DATE_LOCALE_KEY)));
			priorityColors = toml.getBoolean(KEY_PREFIX + PRIORITY_COLORS_KEY);
			defaultColor = toml.getString(KEY_PREFIX + DEFAULT_COLOR_KEY);
			printDescription = toml.getBoolean(KEY_PREFIX + PRINT_DESCRIPTION_KEY);
			printAssignee = toml.getBoolean(KEY_PREFIX + PRINT_ASSIGNEE_KEY);
			printStatus = toml.getBoolean(KEY_PREFIX + PRINT_STATUS_KEY);
			printReporter = toml.getBoolean(KEY_PREFIX + PRINT_REPORTER_KEY);
			printPriority = toml.getBoolean(KEY_PREFIX + PRINT_PRIORITY_KEY);
			printResolution = toml.getBoolean(KEY_PREFIX + PRINT_RESOLUTION_KEY);
			printCreated = toml.getBoolean(KEY_PREFIX + PRINT_CREATED_KEY);
			printUpdated = toml.getBoolean(KEY_PREFIX + PRINT_UPDATED_KEY);
			printType = toml.getBoolean(KEY_PREFIX + PRINT_TYPE_KEY);
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

	public boolean isPrintDescription() {
		return printDescription;
	}

	public boolean isPrintAssignee() {
		return printAssignee;
	}

	public boolean isPrintStatus() {
		return printStatus;
	}

	public boolean isPrintReporter() {
		return printReporter;
	}

	public boolean isPrintPriority() {
		return printPriority;
	}

	public boolean isPrintResolution() {
		return printResolution;
	}

	public boolean isPrintCreated() {
		return printCreated;
	}

	public boolean isPrintUpdated() {
		return printUpdated;
	}

	public boolean isPrintType() {
		return printType;
	}
}
