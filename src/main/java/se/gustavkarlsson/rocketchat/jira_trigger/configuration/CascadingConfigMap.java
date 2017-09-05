package se.gustavkarlsson.rocketchat.jira_trigger.configuration;

import java.util.List;
import java.util.Objects;
import java.util.function.Function;

import static org.apache.commons.lang3.Validate.noNullElements;

class CascadingConfigMap implements ConfigMap {
	private final List<ConfigMap> configMaps;

	CascadingConfigMap(List<ConfigMap> configMaps) {
		this.configMaps = noNullElements(configMaps);
	}

	@Override
	public String getString(String key) {
		return getLastNonNull(configMap -> configMap.getString(key));
	}

	@Override
	public Long getLong(String key) {
		return getLastNonNull(configMap -> configMap.getLong(key));
	}

	@Override
	public Boolean getBoolean(String key) {
		return getLastNonNull(configMap -> configMap.getBoolean(key));
	}

	@Override
	public List<String> getStringList(String key) {
		return getLastNonNull(configMap -> configMap.getStringList(key));
	}

	private <T> T getLastNonNull(Function<ConfigMap, T> getValue) {
		return configMaps.stream()
				.map(getValue)
				.filter(Objects::nonNull)
				.reduce((first, second) -> second) // Workaround to do findLast
				.orElse(null);
	}
}
