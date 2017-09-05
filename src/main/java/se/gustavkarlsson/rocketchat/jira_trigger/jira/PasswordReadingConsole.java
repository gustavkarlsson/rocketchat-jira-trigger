package se.gustavkarlsson.rocketchat.jira_trigger.jira;

interface PasswordReadingConsole {
	char[] readPassword(String prompt);
}
