package se.gustavkarlsson.rocketchat.jira_trigger.configuration;

import org.apache.commons.lang3.NotImplementedException;

import java.util.AbstractMap.SimpleEntry;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.stream.Collectors;

class EnvVarConfigMap implements ConfigMap {

	private final Map<String, String> environment;

	EnvVarConfigMap(Map<String, String> environment) {
		this.environment = environment.entrySet().stream()
				.map(EnvVarConfigMap::normalizeEntry)
				.collect(Collectors.toMap(Entry::getKey, Entry::getValue));
	}

	private static Entry<String, String> normalizeEntry(Entry<String, String> entry) {
		return new SimpleEntry<>(normalizeKey(entry.getKey()), entry.getValue());
	}

	private static String normalizeKey(String key) {
		return key.replaceAll("\\.", "_").toUpperCase();
	}

	private static List<String> parseStringList(String rawString) {
		throw new NotImplementedException("parseStringList");
	}

	@Override
	public String getString(String key) {
		return environment.get(normalizeKey(key));
	}

	@Override
	public Long getLong(String key) {
		return Optional.ofNullable(getString(key))
				.map(String::trim)
				.map(Long::valueOf)
				.orElse(null);
	}

	@Override
	public Boolean getBoolean(String key) {
		return Optional.ofNullable(getString(key))
				.map(String::trim)
				.map(string -> {
					if (string.equalsIgnoreCase("true")) {
						return true;
					} else if (string.equalsIgnoreCase("false")) {
						return false;
					} else {
						throw new IllegalStateException("'" + string + "' could not be parsed to a boolean value");
					}
				})
				.orElse(null);
	}

	@Override
	public List<String> getStringList(String key) {
		return Optional.ofNullable(getString(key))
				.map(EnvVarConfigMap::parseStringList)
				.orElse(null);
	}
}
