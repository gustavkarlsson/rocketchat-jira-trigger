package se.gustavkarlsson.rocketchat.jira_trigger.configuration;

import com.moandjiezana.toml.Toml;

import java.util.List;

import static org.apache.commons.lang3.Validate.notNull;

class TomlConfigMap implements ConfigMap {
	private final Toml toml;

	TomlConfigMap(Toml toml) {
		this.toml = notNull(toml);
	}

	@Override
	public String getString(String key) {
		return toml.getString(key);
	}

	@Override
	public Long getLong(String key) {
		return toml.getLong(key);
	}

	@Override
	public Boolean getBoolean(String key) {
		return toml.getBoolean(key);
	}

	@Override
	public List<String> getStringList(String key) {
		return toml.getList(key);
	}
}
