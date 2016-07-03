package se.gustavkarlsson.rocketchat.jira_trigger.configuration;

import org.junit.Test;

public class ValidationExceptionTest {

	@Test
	public void constructWithNull() throws Exception {
		new ValidationException(null);
	}

	@Test
	public void constructWithReason() throws Exception {
		new ValidationException(new IllegalArgumentException("reason"));
	}
}
