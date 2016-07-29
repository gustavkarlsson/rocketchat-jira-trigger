package se.gustavkarlsson.rocketchat.jira_trigger;

import org.junit.Before;
import org.junit.Test;
import se.gustavkarlsson.rocketchat.jira_trigger.configuration.MessageConfiguration;
import se.gustavkarlsson.rocketchat.jira_trigger.converters.fields.FieldCreator;
import se.gustavkarlsson.rocketchat.jira_trigger.converters.fields.StatusFieldCreator;

import java.text.SimpleDateFormat;
import java.util.List;

import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static se.gustavkarlsson.rocketchat.jira_trigger.FieldCreatorMapper.STATUS_KEY;

public class FieldCreatorMapperTest {
	private MessageConfiguration mockConfig;

	@Before
	public void setUp() throws Exception {
		mockConfig = mock(MessageConfiguration.class);
		when(mockConfig.getDateFormat()).thenReturn(SimpleDateFormat.getDateInstance());
	}

	@Test
	public void create() throws Exception {
		new FieldCreatorMapper(mockConfig);
	}

	@Test(expected = NullPointerException.class)
	public void createWithNullConfigThrowsNpe() throws Exception {
		new FieldCreatorMapper(null);
	}

	@Test
	public void getSingleCreator() throws Exception {
		when(mockConfig.getDateFormat()).thenReturn(SimpleDateFormat.getDateInstance());
		FieldCreatorMapper mapper = new FieldCreatorMapper(mockConfig);

		List<FieldCreator> creators = mapper.getCreators(singletonList(STATUS_KEY));

		assertThat(creators).hasSize(1);
		assertThat(creators.get(0)).isExactlyInstanceOf(StatusFieldCreator.class);
	}

	@Test
	public void getNoCreator() throws Exception {
		when(mockConfig.getDateFormat()).thenReturn(SimpleDateFormat.getDateInstance());
		FieldCreatorMapper mapper = new FieldCreatorMapper(mockConfig);

		List<FieldCreator> creators = mapper.getCreators(emptyList());

		assertThat(creators).isEmpty();
	}

}
