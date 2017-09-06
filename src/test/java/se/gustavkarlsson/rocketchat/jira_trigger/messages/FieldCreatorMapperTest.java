package se.gustavkarlsson.rocketchat.jira_trigger.messages;

import org.joda.time.format.DateTimeFormat;
import org.junit.Test;
import se.gustavkarlsson.rocketchat.jira_trigger.messages.field_creators.FieldCreator;
import se.gustavkarlsson.rocketchat.jira_trigger.messages.field_creators.StatusFieldCreator;

import java.util.List;

import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;
import static se.gustavkarlsson.rocketchat.jira_trigger.messages.FieldCreatorMapper.STATUS_KEY;

public class FieldCreatorMapperTest {

	@Test(expected = NullPointerException.class)
	public void createWithNullDateTimeFormatterThrowsNpe() throws Exception {
		new FieldCreatorMapper(false, null);
	}

	@Test
	public void getSingleCreator() throws Exception {
		FieldCreatorMapper mapper = new FieldCreatorMapper(false, DateTimeFormat.forPattern("yyyy"));

		List<FieldCreator> creators = mapper.getCreators(singletonList(STATUS_KEY));

		assertThat(creators).hasSize(1);
		assertThat(creators.get(0)).isExactlyInstanceOf(StatusFieldCreator.class);
	}

	@Test
	public void getNoCreator() throws Exception {
		FieldCreatorMapper mapper = new FieldCreatorMapper(false, DateTimeFormat.forPattern("yyyy"));

		List<FieldCreator> creators = mapper.getCreators(emptyList());

		assertThat(creators).isEmpty();
	}

}
