package se.gustavkarlsson.rocketchat.jira_trigger.configuration;

class ValidationException extends Exception {
	ValidationException(Exception cause) {
		super(cause);
	}
}
