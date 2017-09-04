package se.gustavkarlsson.rocketchat.jira_trigger.configuration;

import org.junit.Test;

public class TomlConfigMapTest {

	@Test(expected = NullPointerException.class)
	public void createWithNullTomlThrowsNPE() throws Exception {
		new TomlConfigMap(null);
	}
}
