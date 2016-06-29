package se.gustavkarlsson.rocketchat.jira_trigger.configuration;

import com.moandjiezana.toml.Toml;

import static org.apache.commons.lang3.Validate.notNull;

public class MessagePrintConfiguration {
	private static final String PRINT_DESCRIPTION_KEY = "description";
	private static final String PRINT_ASSIGNEE_KEY = "assignee";
	private static final String PRINT_STATUS_KEY = "status";
	private static final String PRINT_REPORTER_KEY = "reporter";
	private static final String PRINT_PRIORITY_KEY = "priority";
	private static final String PRINT_RESOLUTION_KEY = "resolution";
	private static final String PRINT_TYPE_KEY = "type";
	private static final String PRINT_CREATED_KEY = "created";
	private static final String PRINT_UPDATED_KEY = "updated";

	private final boolean printDescription;
	private final boolean printAssignee;
	private final boolean printStatus;
	private final boolean printReporter;
	private final boolean printPriority;
	private final boolean printResolution;
	private final boolean printCreated;
	private final boolean printUpdated;
	private final boolean printType;

	MessagePrintConfiguration(Toml toml) throws ValidationException {
		notNull(toml);
		try {
			printDescription = toml.getBoolean(PRINT_DESCRIPTION_KEY);
			printAssignee = toml.getBoolean(PRINT_ASSIGNEE_KEY);
			printStatus = toml.getBoolean(PRINT_STATUS_KEY);
			printReporter = toml.getBoolean(PRINT_REPORTER_KEY);
			printPriority = toml.getBoolean(PRINT_PRIORITY_KEY);
			printResolution = toml.getBoolean(PRINT_RESOLUTION_KEY);
			printCreated = toml.getBoolean(PRINT_CREATED_KEY);
			printUpdated = toml.getBoolean(PRINT_UPDATED_KEY);
			printType = toml.getBoolean(PRINT_TYPE_KEY);
		} catch (Exception e) {
			throw new ValidationException(e);
		}
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
