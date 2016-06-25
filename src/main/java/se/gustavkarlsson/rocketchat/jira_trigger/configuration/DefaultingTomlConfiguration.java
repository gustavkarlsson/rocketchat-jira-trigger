package se.gustavkarlsson.rocketchat.jira_trigger.configuration;

import com.moandjiezana.toml.Toml;

import java.util.List;

import static org.apache.commons.lang3.Validate.notNull;

abstract class DefaultingTomlConfiguration {

	final Toml toml;
	final Toml defaults;

	DefaultingTomlConfiguration(Toml toml, Toml defaults) {
		this.toml = toml == null ? new Toml() : toml;
		this.defaults = notNull(defaults);
	}

	Long getLongOrDefault(String key) {
		return toml.getLong(key, defaults.getLong(key));
	}

	String getStringOrDefault(String key) {
		return toml.getString(key, defaults.getString(key));
	}

	Boolean getBooleanOrDefault(String key) {
		return toml.getBoolean(key, defaults.getBoolean(key));
	}

	<T> List<T> getListOrDefault(String key) {
		return toml.getList(key, defaults.<T>getList(key));
	}

}
