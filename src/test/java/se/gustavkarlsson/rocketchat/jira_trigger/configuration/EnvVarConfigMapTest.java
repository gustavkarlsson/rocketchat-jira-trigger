package se.gustavkarlsson.rocketchat.jira_trigger.configuration;

import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

@RunWith(Enclosed.class)
public class EnvVarConfigMapTest {

	public static class Construction {
		@Test(expected = NullPointerException.class)
		public void createWithNullMapThrowsNPE() {
			new EnvVarConfigMap(null);
		}

		@Test(expected = IllegalArgumentException.class)
		public void createWithNullKeyThrowsException() {
			Map<String, String> map = new HashMap<>();
			map.put(null, "value");
			new EnvVarConfigMap(map);
		}

		@Test(expected = IllegalArgumentException.class)
		public void createWithNullValueThrowsException() {
			Map<String, String> map = new HashMap<>();
			map.put("key", null);
			new EnvVarConfigMap(map);
		}
	}

	@RunWith(Parameterized.class)
	public static class GetString {

		private final String configKey;
		private final String expectedValue;

		private final EnvVarConfigMap configMap;

		public GetString(String envKey, String envValue, String configKey, String expectedValue) {
			this.configKey = configKey;
			this.expectedValue = expectedValue;
			Map<String, String> environment = new HashMap<>();
			environment.put(envKey, envValue);
			configMap = new EnvVarConfigMap(environment);
		}

		@Parameters(name = "{0}={1} | getString({2}) should return: {3}")
		public static Collection<Object[]> data() {
			return asList(new Object[][]{
					{"KEY", "value", "KEY", "value"},
					{"key", "value", "key", "value"},
					{"key", "value", "KEY", "value"},
					{"KEY", "value", "key", "value"},
					{"KEY_A", "value", "key.a", "value"},
					{"KEY_A", "value", "key_a", "value"},
					{"KEY_A_B", "value", "key.a_b", "value"},
					{"KEY_A_B_C", "value", "key.a_b.c", "value"},
					{"KEY_A", "value", "key", null},
					{"KEY", "value", "key.a", null},

			});
		}

		@Test
		public void getStringReturnsCorrectValue() {
			String result = configMap.getString(configKey);

			assertThat(result).isEqualTo(expectedValue);
		}
	}

	@RunWith(Parameterized.class)
	public static class GetLong {

		private final String configKey;
		private final Object expectedValue;

		private final EnvVarConfigMap configMap;

		public GetLong(String envKey, String envValue, String configKey, Object expectedValue) {
			this.configKey = configKey;
			this.expectedValue = expectedValue;
			Map<String, String> environment = new HashMap<>();
			environment.put(envKey, envValue);
			configMap = new EnvVarConfigMap(environment);
		}

		@Parameters(name = "{0}={1} | getLong({2}) should return: {3}")
		public static Collection<Object[]> data() {
			return asList(new Object[][]{
					{"KEY", "1", "KEY", 1L},
					{"key", "1", "key", 1L},
					{"key", "1", "KEY", 1L},
					{"KEY", "1", "key", 1L},
					{"KEY_A", "1", "key.a", 1L},
					{"KEY_A", "1", "key_a", 1L},
					{"KEY_A_B", "1", "key.a_b", 1L},
					{"KEY_A_B_C", "1", "key.a_b.c", 1L},
					{"KEY_A", "1", "key", null},
					{"KEY", "-58014", "KEY", -58014L},
					{"KEY", "0", "KEY", 0L},
					{"KEY", " 5", "KEY", 5L},
					{"KEY", "5\t", "KEY", 5L},
					{"KEY", " ", "KEY", NumberFormatException.class},
					{"KEY", "a", "KEY", NumberFormatException.class},
					{"KEY", "", "KEY", NumberFormatException.class},
					{"KEY", "four", "KEY", NumberFormatException.class},
			});
		}

		@Test
		public void getLongReturnsCorrectValue() {
			if (expectedValue instanceof Class) {
				Class clazz = (Class) expectedValue;
				assertThatExceptionOfType(clazz).isThrownBy(() -> configMap.getLong(configKey));
			} else {
				Long result = configMap.getLong(configKey);

				assertThat(result).isEqualTo(expectedValue);
			}
		}
	}

	@RunWith(Parameterized.class)
	public static class GetBoolean {

		private final String configKey;
		private final Object expectedValue;

		private final EnvVarConfigMap configMap;

		public GetBoolean(String envKey, String envValue, String configKey, Object expectedValue) {
			this.configKey = configKey;
			this.expectedValue = expectedValue;
			Map<String, String> environment = new HashMap<>();
			environment.put(envKey, envValue);
			configMap = new EnvVarConfigMap(environment);
		}

		@Parameters(name = "{0}={1} | getBoolean({2}) should return: {3}")
		public static Collection<Object[]> data() {
			return asList(new Object[][]{
					{"KEY", "true", "KEY", true},
					{"key", "true", "key", true},
					{"key", "true", "KEY", true},
					{"KEY", "true", "key", true},
					{"KEY_A", "true", "key.a", true},
					{"KEY_A", "true", "key_a", true},
					{"KEY_A_B", "true", "key.a_b", true},
					{"KEY_A_B_C", "true", "key.a_b.c", true},
					{"KEY_A", "true", "key", null},
					{"KEY", "TRUE", "key", true},
					{"KEY", "TrUe", "key", true},
					{"KEY", " true\n", "key", true},
					{"KEY", "false", "key", false},
					{"KEY", "FALSE", "key", false},
					{"KEY", "FaLSe", "key", false},
					{"KEY", "a", "key", Throwable.class},
			});
		}

		@Test
		public void getBooleanReturnsCorrectValue() {
			if (expectedValue instanceof Class) {
				Class clazz = (Class) expectedValue;
				assertThatExceptionOfType(clazz).isThrownBy(() -> configMap.getBoolean(configKey));
			} else {
				Boolean result = configMap.getBoolean(configKey);

				assertThat(result).isEqualTo(expectedValue);
			}
		}
	}

	@RunWith(Parameterized.class)
	public static class GetStringList {

		private final String configKey;
		private final Object expectedValue;

		private final EnvVarConfigMap configMap;

		public GetStringList(String envKey, String envValue, String configKey, Object expectedValue) {
			this.configKey = configKey;
			this.expectedValue = expectedValue;
			Map<String, String> environment = new HashMap<>();
			environment.put(envKey, envValue);
			configMap = new EnvVarConfigMap(environment);
		}

		@Parameters(name = "{0}={1} | getStringList({2}) should return: {3}")
		public static Collection<Object[]> data() {
			return asList(new Object[][]{
					{"KEY", "foo", "KEY", asList("foo")},
					{"KEY", "", "KEY", asList("")},
					{"KEY", ",", "KEY", asList("", "")},
					{"KEY", "foo,bar", "KEY", asList("foo", "bar")},
					{"KEY", "foo,", "KEY", asList("foo", "")},
					{"KEY", ",foo", "KEY", asList("", "foo")},
					{"KEY", "\\\\", "KEY", asList("\\")},
					{"KEY", "foo\\,bar", "KEY", asList("foo,bar")},
					{"KEY", "foo\\\\bar", "KEY", asList("foo\\bar")},
					{"KEY", "foo\\", "KEY", Exception.class},
					{"KEY", "foo\\\\\\", "KEY", Exception.class},
					{"KEY", "foo\\\\", "KEY", asList("foo\\")},
					{"KEY", "foo\\bar", "KEY", asList("foobar")},
			});
		}

		@Test
		public void getBooleanReturnsCorrectValue() {
			if (expectedValue instanceof Class) {
				Class clazz = (Class) expectedValue;
				assertThatExceptionOfType(clazz).isThrownBy(() -> configMap.getStringList(configKey));
			} else {
				List<String> result = configMap.getStringList(configKey);

				assertThat(result).isEqualTo(expectedValue);
			}
		}
	}
}
