package se.gustavkarlsson.rocketchat.jira_trigger.test;

import com.google.common.io.Resources;
import com.moandjiezana.toml.Toml;

import java.nio.charset.StandardCharsets;

public class TomlUtils {

	private static Toml minimal;
	private static Toml defaults;

	public synchronized static Toml getMinimalToml() throws Exception {
		if (minimal == null) {
			String toml = Resources.toString(Resources.getResource("minimal.toml"), StandardCharsets.UTF_8);
			minimal = new Toml().read(toml);
		}
		return minimal;
	}

	public synchronized static Toml getDefaultsToml() throws Exception {
		if (defaults == null) {
			String toml = Resources.toString(Resources.getResource("defaults.toml"), StandardCharsets.UTF_8);
			defaults = new Toml().read(toml);
		}
		return defaults;
	}
}
