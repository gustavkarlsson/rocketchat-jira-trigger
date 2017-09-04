package se.gustavkarlsson.rocketchat.jira_trigger.configuration;

import java.util.List;

interface ConfigMap {
	String getString(String key);

	Long getLong(String key);

	Boolean getBoolean(String key);

	List<String> getStringList(String key);
}
