package se.gustavkarlsson.rocketchat.jira_trigger.configuration;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;

import static org.apache.commons.lang3.Validate.noNullElements;

class CascadingConfigMap implements ConfigMap {
	private final List<ConfigMap> configMaps;

	CascadingConfigMap(ConfigMap... configMaps) {
		this.configMaps = noNullElements(Arrays.asList(configMaps));
	}

	@Override
	public String getString(String key) {
		return getFirstNonNull(configMap -> configMap.getString(key));
	}

	@Override
	public Long getLong(String key) {
		return getFirstNonNull(configMap -> configMap.getLong(key));
	}

	@Override
	public Boolean getBoolean(String key) {
		return getFirstNonNull(configMap -> configMap.getBoolean(key));
	}

	@Override
	public List<String> getStringList(String key) {
		return getFirstNonNull(configMap -> configMap.getStringList(key));
	}

	private <T> T getFirstNonNull(Function<ConfigMap, T> getValue) {
		return configMaps.stream()
				.map(getValue)
				.filter(Objects::nonNull)
				.findFirst()
				.orElse(null);
	}
}
