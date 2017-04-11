package se.gustavkarlsson.rocketchat.jira_trigger;

import org.junit.Before;
import org.junit.Test;
import se.gustavkarlsson.rocketchat.jira_trigger.configuration.MessageConfiguration;
import se.gustavkarlsson.rocketchat.jira_trigger.converters.fields.FieldExtractor;
import se.gustavkarlsson.rocketchat.jira_trigger.converters.fields.StatusFieldExtractor;

import java.text.SimpleDateFormat;
import java.util.List;

import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static se.gustavkarlsson.rocketchat.jira_trigger.FieldExtractorMapper.STATUS_KEY;

public class FieldExtractorMapperTest {
	private MessageConfiguration mockConfig;

	@Before
	public void setUp() throws Exception {
		mockConfig = mock(MessageConfiguration.class);
		when(mockConfig.getDateFormat()).thenReturn(SimpleDateFormat.getDateInstance());
	}

	@Test
	public void create() throws Exception {
		new FieldExtractorMapper(mockConfig);
	}

	@Test(expected = NullPointerException.class)
	public void createWithNullConfigThrowsNpe() throws Exception {
		new FieldExtractorMapper(null);
	}

	@Test
	public void getSingleCreator() throws Exception {
		when(mockConfig.getDateFormat()).thenReturn(SimpleDateFormat.getDateInstance());
		FieldExtractorMapper mapper = new FieldExtractorMapper(mockConfig);

		List<FieldExtractor> creators = mapper.getCreators(singletonList(STATUS_KEY));

		assertThat(creators).hasSize(1);
		assertThat(creators.get(0)).isExactlyInstanceOf(StatusFieldExtractor.class);
	}

	@Test
	public void getNoCreator() throws Exception {
		when(mockConfig.getDateFormat()).thenReturn(SimpleDateFormat.getDateInstance());
		FieldExtractorMapper mapper = new FieldExtractorMapper(mockConfig);

		List<FieldExtractor> creators = mapper.getCreators(emptyList());

		assertThat(creators).isEmpty();
	}

}
