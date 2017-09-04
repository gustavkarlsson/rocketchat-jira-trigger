package se.gustavkarlsson.rocketchat.jira_trigger.configuration;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import javax.inject.Inject;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import static java.util.stream.Collectors.toSet;
import static org.apache.commons.lang3.Validate.notNull;

public class MessageConfiguration {
	static final String KEY_PREFIX = "message.";

	static final String USERNAME_KEY = "username";
	static final String USE_REAL_NAMES_KEY = "use_real_names";
	static final String ICON_URL_KEY = "icon_url";
	static final String DATE_PATTERN_KEY = "date_pattern";
	static final String DATE_LOCALE_KEY = "date_locale";
	static final String PRIORITY_COLORS_KEY = "priority_colors";
	static final String DEFAULT_COLOR_KEY = "default_color";
	static final String DEFAULT_FIELDS_KEY = "default_fields";
	static final String EXTENDED_FIELDS_KEY = "extended_fields";
	static final String WHITELISTED_KEY_PREFIXES_KEY = "whitelisted_jira_key_prefixes";
	static final String WHITELISTED_KEY_SUFFIXES_KEY = "whitelisted_jira_key_suffixes";

	private final String username;
	private final boolean useRealNames;
	private final String iconUrl;
	private final DateTimeFormatter dateFormatter;
	private final boolean priorityColors;
	private final String defaultColor;
	private final List<String> defaultFields;
	private final List<String> extendedFields;
	private final Set<Character> whitelistedJiraKeyPrefixes;
	private final Set<Character> whitelistedJiraKeySuffixes;

	@Inject
	MessageConfiguration(ConfigMap configMap) throws ValidationException {
		notNull(configMap);
		try {
			username = configMap.getString(KEY_PREFIX + USERNAME_KEY);
			useRealNames = configMap.getBoolean(KEY_PREFIX + USE_REAL_NAMES_KEY);
			iconUrl = configMap.getString(KEY_PREFIX + ICON_URL_KEY);
			dateFormatter = DateTimeFormat
					.forPattern(configMap.getString(KEY_PREFIX + DATE_PATTERN_KEY))
					.withLocale(Locale.forLanguageTag(configMap.getString(KEY_PREFIX + DATE_LOCALE_KEY)));
			priorityColors = configMap.getBoolean(KEY_PREFIX + PRIORITY_COLORS_KEY);
			defaultColor = configMap.getString(KEY_PREFIX + DEFAULT_COLOR_KEY);
			defaultFields = configMap.getStringList(KEY_PREFIX + DEFAULT_FIELDS_KEY);
			extendedFields = configMap.getStringList(KEY_PREFIX + EXTENDED_FIELDS_KEY);
			whitelistedJiraKeyPrefixes = charSet(configMap.getString(KEY_PREFIX + WHITELISTED_KEY_PREFIXES_KEY));
			whitelistedJiraKeySuffixes = charSet(configMap.getString(KEY_PREFIX + WHITELISTED_KEY_SUFFIXES_KEY));
		} catch (Exception e) {
			throw new ValidationException(e);
		}
	}

	private static Set<Character> charSet(String string) {
		return string.chars()
				.mapToObj(c -> ((char) c))
				.collect(toSet());
	}

	public String getUsername() {
		return username;
	}

	public boolean isUseRealNames() {
		return useRealNames;
	}

	public String getIconUrl() {
		return iconUrl;
	}

	public DateTimeFormatter getDateFormatter() {
		return dateFormatter;
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

	public Set<Character> getWhitelistedJiraKeyPrefixes() {
		return whitelistedJiraKeyPrefixes;
	}

	public Set<Character> getWhitelistedJiraKeySuffixes() {
		return whitelistedJiraKeySuffixes;
	}
}
