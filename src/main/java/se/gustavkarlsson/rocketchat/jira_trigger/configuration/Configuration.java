package se.gustavkarlsson.rocketchat.jira_trigger.configuration;

import org.slf4j.Logger;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URI;
import java.util.Properties;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.Arrays.stream;
import static org.apache.commons.lang3.Validate.*;
import static org.slf4j.LoggerFactory.getLogger;

public class Configuration {
	private static final Logger log = getLogger(Configuration.class);

	private static final String JIRA_URI_KEY = "jira_uri";
	private static final String JIRA_USER_KEY = "jira_user";
	private static final String JIRA_PASSWORD_KEY = "jira_password";

	private static final String PORT_KEY = "port";

	private static final String USERNAME_KEY = "username";
	private static final String ICON_URL_KEY = "icon_url";
	private static final String PRIORITY_COLORS_KEY = "priority_colors";
	private static final String DEFAULT_COLOR_KEY = "default_color";
	private static final String BLACKLISTED_USERNAMES_KEY = "blacklisted_usernames";

	private static final String PRINT_DESCRIPTION_KEY = "print_description";
	private static final String PRINT_ASSIGNEE_KEY = "print_assignee";
	private static final String PRINT_STATUS_KEY = "print_status";
	private static final String PRINT_REPORTER_KEY = "print_reporter";
	private static final String PRINT_PRIORITY_KEY = "print_priority";
	private static final String PRINT_RESOLUTION_KEY = "print_resolution";
	private static final String PRINT_TYPE_KEY = "print_type";
	private static final String PRINT_CREATED_KEY = "print_created";
	private static final String PRINT_UPDATED_KEY = "print_updated";

	private static final int PORT_DEFAULT = 4567;
	private static final boolean PRIORITY_COLORS_DEFAULT = true;
	private static final String DEFAULT_COLOR_DEFAULT = "#205081";
	private static final String BLACKLISTED_USERNAMES_DEFAULT = "rocket.cat,hubot";

	private static final boolean PRINT_DESCRIPTION_DEFAULT = false;
	private static final boolean PRINT_ASSIGNEE_DEFAULT = true;
	private static final boolean PRINT_STATUS_DEFAULT = true;
	private static final boolean PRINT_REPORTER_DEFAULT = false;
	private static final boolean PRINT_PRIORITY_DEFAULT = false;
	private static final boolean PRINT_RESOLUTION_DEFAULT = false;
	private static final boolean PRINT_TYPE_DEFAULT = false;
	private static final boolean PRINT_CREATED_DEFAULT = false;
	private static final boolean PRINT_UPDATED_DEFAULT = false;

	private final Properties properties;

	public Configuration(String propertiesFile) throws IOException, ValidationException {
		properties = new Properties();
		try (FileInputStream fis = new FileInputStream(propertiesFile)) {
			properties.load(fis);
		}
		validate();
	}

	private void validate() throws ValidationException {
		try {
			notNull(getJiraUri(), JIRA_URI_KEY);
			notBlank(getJiraUsername(), JIRA_USER_KEY);
			notBlank(getJiraPassword(), JIRA_PASSWORD_KEY);
			inclusiveBetween(1, Integer.MAX_VALUE, getPort());

			isPriorityColors();
			isPrintDescription();
		} catch (Exception e) {
			throw new ValidationException(e);
		}
	}

	public URI getJiraUri() {
		return URI.create(properties.getProperty(JIRA_URI_KEY));
	}

	public String getJiraUsername() {
		return properties.getProperty(JIRA_USER_KEY);
	}

	public String getJiraPassword() {
		return properties.getProperty(JIRA_PASSWORD_KEY);
	}

	public int getPort() {
		return getInteger(PORT_KEY, PORT_DEFAULT);
	}

	public String getUsername() {
		return properties.getProperty(USERNAME_KEY);
	}

	public String getIconUrl() {
		return properties.getProperty(ICON_URL_KEY);
	}

	public boolean isPriorityColors() {
		return getBoolean(PRIORITY_COLORS_KEY, PRIORITY_COLORS_DEFAULT);
	}

	public String getDefaultColor() {
		return properties.getProperty(DEFAULT_COLOR_KEY, DEFAULT_COLOR_DEFAULT);
	}

	public Set<String> getBlacklistedUsernames() {
		String property = properties.getProperty(BLACKLISTED_USERNAMES_KEY, BLACKLISTED_USERNAMES_DEFAULT);
		return stream(property.split(","))
				.map(String::trim)
				.filter(s -> !s.isEmpty())
				.collect(Collectors.toSet());
	}

	public boolean isPrintDescription() {
		return getBoolean(PRINT_DESCRIPTION_KEY, PRINT_DESCRIPTION_DEFAULT);
	}

	public boolean isPrintAssignee() {
		return getBoolean(PRINT_ASSIGNEE_KEY, PRINT_ASSIGNEE_DEFAULT);
	}

	public boolean isPrintStatus() {
		return getBoolean(PRINT_STATUS_KEY, PRINT_STATUS_DEFAULT);
	}

	public boolean isPrintReporter() {
		return getBoolean(PRINT_REPORTER_KEY, PRINT_REPORTER_DEFAULT);
	}

	public boolean isPrintPriority() {
		return getBoolean(PRINT_PRIORITY_KEY, PRINT_PRIORITY_DEFAULT);
	}

	public boolean isPrintResolution() {
		return getBoolean(PRINT_RESOLUTION_KEY, PRINT_RESOLUTION_DEFAULT);
	}

	public boolean isPrintCreated() {
		return getBoolean(PRINT_CREATED_KEY, PRINT_CREATED_DEFAULT);
	}

	public boolean isPrintUpdated() {
		return getBoolean(PRINT_UPDATED_KEY, PRINT_UPDATED_DEFAULT);
	}

	public boolean isPrintType() {
		return getBoolean(PRINT_TYPE_KEY, PRINT_TYPE_DEFAULT);
	}

	private int getInteger(String key, int theDefault) {
		String stringValue = properties.getProperty(key);
		if (stringValue == null) {
			return theDefault;
		}
		try {
			return Integer.parseInt(stringValue);
		} catch (NumberFormatException e) {
			log.warn("Could not parse {} to an integer. Using default value", stringValue);
			return theDefault;
		}
	}

	private boolean getBoolean(String key, boolean theDefault) {
		String stringValue = properties.getProperty(key);
		if (stringValue == null) {
			return theDefault;
		}
		if (stringValue.equalsIgnoreCase("true")) {
			return true;
		}
		if (stringValue.equalsIgnoreCase("false")) {
			return false;
		}
		log.warn("Could not parse {} to a boolean. Using default value", stringValue);
		return theDefault;
	}


}
