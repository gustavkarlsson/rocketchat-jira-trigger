package se.gustavkarlsson.rocketchat.jira_trigger.configuration;

import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.stream.Collectors;

class EnvVarConfigMap implements ConfigMap {

	private final Map<String, String> environment;

	EnvVarConfigMap(Map<String, String> environment) {

		this.environment = environment.entrySet().stream()
				.peek(EnvVarConfigMap::validateEntry)
				.map(EnvVarConfigMap::normalizeEntry)
				.collect(Collectors.toMap(Entry::getKey, Entry::getValue));
	}

	private static void validateEntry(Entry<String, String> entry) {
		if (entry.getKey() == null || entry.getValue() == null) {
			throw new IllegalArgumentException("environment may not contain null keys or values");
		}
	}

	private static Entry<String, String> normalizeEntry(Entry<String, String> entry) {
		return new SimpleEntry<>(normalizeKey(entry.getKey()), entry.getValue());
	}

	private static String normalizeKey(String key) {
		return key.replaceAll("\\.", "_").toUpperCase();
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

	private static List<String> parseStringList(String rawString) {
		return parseRemainingStrings(new ArrayList<>(), rawString);
	}

	private static List<String> parseRemainingStrings(List<String> list, String rawString) {
		StringBuilder sb = new StringBuilder();
		if (rawString.isEmpty()) {
			list.add(sb.toString());
			return list;
		}

		char[] chars = rawString.toCharArray();
		for (int i = 0; i < chars.length; i++) {
			if (chars[i] == ',') {
				list.add(sb.toString());
				return (parseRemainingStrings(list, rawString.substring(i + 1)));
			}
			if (chars[i] == '\\') {
				i++;
			}
			sb.append(chars[i]);
		}

		list.add(sb.toString());
		return list;
	}
}
