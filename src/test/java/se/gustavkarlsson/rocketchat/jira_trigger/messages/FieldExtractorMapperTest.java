package se.gustavkarlsson.rocketchat.jira_trigger.messages;

import org.joda.time.format.DateTimeFormat;
import org.junit.Test;
import se.gustavkarlsson.rocketchat.jira_trigger.messages.fields.FieldExtractor;
import se.gustavkarlsson.rocketchat.jira_trigger.messages.fields.StatusFieldExtractor;

import java.util.List;

import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;
import static se.gustavkarlsson.rocketchat.jira_trigger.messages.FieldExtractorMapper.STATUS_KEY;

public class FieldExtractorMapperTest {

	@Test(expected = NullPointerException.class)
	public void createWithNullDateTimeFormatterThrowsNpe() throws Exception {
		new FieldExtractorMapper(false, null);
	}

	@Test
	public void getSingleCreator() throws Exception {
		FieldExtractorMapper mapper = new FieldExtractorMapper(false, DateTimeFormat.forPattern("yyyy"));

		List<FieldExtractor> creators = mapper.getCreators(singletonList(STATUS_KEY));

		assertThat(creators).hasSize(1);
		assertThat(creators.get(0)).isExactlyInstanceOf(StatusFieldExtractor.class);
	}

	@Test
	public void getNoCreator() throws Exception {
		FieldExtractorMapper mapper = new FieldExtractorMapper(false, DateTimeFormat.forPattern("yyyy"));

		List<FieldExtractor> creators = mapper.getCreators(emptyList());

		assertThat(creators).isEmpty();
	}

}
