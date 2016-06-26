package se.gustavkarlsson.rocketchat.jira_trigger.models;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import org.junit.Test;

public class FieldTest {

	@Test
	public void equals() throws Exception {
		EqualsVerifier.forClass(Field.class).suppress(Warning.NONFINAL_FIELDS).verify();
	}

}
