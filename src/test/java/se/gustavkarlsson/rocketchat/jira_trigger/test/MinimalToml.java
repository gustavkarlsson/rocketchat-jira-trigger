package se.gustavkarlsson.rocketchat.jira_trigger.test;

import com.moandjiezana.toml.Toml;
import se.gustavkarlsson.rocketchat.jira_trigger.configuration.ConfigurationTest;

import java.io.File;

public class MinimalToml {

	private static Toml minimal;

	public synchronized static Toml get() throws Exception {
		if (minimal == null) {
			Toml defaults = new Toml().read(ConfigurationTest.class.getClassLoader().getResourceAsStream("defaults.toml"));
			File minimalFile = new File(ConfigurationTest.class.getClassLoader().getResource("minimal.toml").toURI());
			minimal = new Toml(defaults).read(minimalFile);
		}
		return minimal;
	}
}
