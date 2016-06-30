package se.gustavkarlsson.rocketchat.jira_trigger.configuration;

import com.moandjiezana.toml.Toml;

import java.util.List;
import java.util.Optional;

import static org.apache.commons.lang3.Validate.notNull;

abstract class DefaultingConfiguration {

	private final Toml toml;
	private final Toml defaults;

	DefaultingConfiguration(Toml toml, Toml defaults) {
		this.toml = notNull(toml);
		this.defaults = notNull(defaults);
	}

	Long getLong(String key) {
		return Optional.ofNullable(toml.getLong(key)).orElse(defaults.getLong(key));
	}

	String getString(String key) {
		return Optional.ofNullable(toml.getString(key)).orElse(defaults.getString(key));
	}

	Boolean getBoolean(String key) {
		return Optional.ofNullable(toml.getBoolean(key)).orElse(defaults.getBoolean(key));
	}

	<T> List<T> getList(String key) {
		Optional<List<T>> list = Optional.ofNullable(toml.getList(key));
		return list.orElse(defaults.getList(key));
	}


}
