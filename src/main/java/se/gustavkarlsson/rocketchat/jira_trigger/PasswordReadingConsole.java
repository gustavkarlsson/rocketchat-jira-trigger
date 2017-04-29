package se.gustavkarlsson.rocketchat.jira_trigger;

public interface PasswordReadingConsole {
	char[] readPassword(String prompt);
}
