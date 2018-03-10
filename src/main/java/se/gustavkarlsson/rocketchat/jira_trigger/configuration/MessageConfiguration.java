package se.gustavkarlsson.rocketchat.jira_trigger.configuration;

import com.google.inject.Singleton;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import javax.inject.Inject;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.Set;

import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.toSet;
import static org.apache.commons.lang3.Validate.notNull;

@Singleton
public class MessageConfiguration {
	private static final String KEY_PREFIX = "message.";
	static final String USERNAME_KEY = KEY_PREFIX + "username";
	static final String USE_REAL_NAMES_KEY = KEY_PREFIX + "use_real_names";
	static final String ICON_URL_KEY = KEY_PREFIX + "icon_url";
	static final String DATE_PATTERN_KEY = KEY_PREFIX + "date_pattern";
	static final String DATE_LOCALE_KEY = KEY_PREFIX + "date_locale";
	static final String PRIORITY_COLORS_KEY = KEY_PREFIX + "priority_colors";
	static final String DEFAULT_COLOR_KEY = KEY_PREFIX + "default_color";
	static final String DEFAULT_FIELDS_KEY = KEY_PREFIX + "default_fields";
	static final String EXTENDED_FIELDS_KEY = KEY_PREFIX + "extended_fields";
	static final String WHITELISTED_KEY_PREFIXES_KEY = KEY_PREFIX + "whitelisted_jira_key_prefixes";
	static final String WHITELISTED_KEY_SUFFIXES_KEY = KEY_PREFIX + "whitelisted_jira_key_suffixes";

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
			username = configMap.getString(USERNAME_KEY);
			useRealNames = notNull(configMap.getBoolean(USE_REAL_NAMES_KEY), String.format("%s must be provided", USE_REAL_NAMES_KEY));
			iconUrl = configMap.getString(ICON_URL_KEY);
			dateFormatter = DateTimeFormat
					.forPattern(notNull(configMap.getString(DATE_PATTERN_KEY), String.format("%s must be provided", DATE_PATTERN_KEY)))
					.withLocale(Locale.forLanguageTag(notNull(configMap.getString(DATE_LOCALE_KEY), String.format("%s must be provided", DATE_LOCALE_KEY))));
			priorityColors = notNull(configMap.getBoolean(PRIORITY_COLORS_KEY), String.format("%s must be provided", PRIORITY_COLORS_KEY));
			defaultColor = notNull(configMap.getString(DEFAULT_COLOR_KEY), String.format("%s must be provided", DEFAULT_COLOR_KEY));
			defaultFields = Optional.ofNullable(configMap.getStringList(DEFAULT_FIELDS_KEY)).orElse(emptyList());
			extendedFields = Optional.ofNullable(configMap.getStringList(EXTENDED_FIELDS_KEY)).orElse(emptyList());
			whitelistedJiraKeyPrefixes = toCharacterSet(Optional.ofNullable(configMap.getString(WHITELISTED_KEY_PREFIXES_KEY)).orElse(""));
			whitelistedJiraKeySuffixes = toCharacterSet(Optional.ofNullable(configMap.getString(WHITELISTED_KEY_SUFFIXES_KEY)).orElse(""));
		} catch (Exception e) {
			throw new ValidationException(e);
		}
	}

	private static Set<Character> toCharacterSet(String string) {
		return string.chars()
				.mapToObj(c -> (char) c)
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
