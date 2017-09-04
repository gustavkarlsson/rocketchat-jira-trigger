package se.gustavkarlsson.rocketchat.jira_trigger.messages;

import org.joda.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import se.gustavkarlsson.rocketchat.jira_trigger.messages.fields.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import static org.slf4j.LoggerFactory.getLogger;

class FieldExtractorMapper {
	private static final Logger log = getLogger(FieldExtractorMapper.class);

	static final String DESCRIPTION_KEY = "description";
	static final String ASSIGNEE_KEY = "assignee";
	static final String STATUS_KEY = "status";
	static final String REPORTER_KEY = "reporter";
	static final String PRIORITY_KEY = "priority";
	static final String RESOLUTION_KEY = "resolution";
	static final String TYPE_KEY = "type";
	static final String CREATED_KEY = "created";
	static final String UPDATED_KEY = "updated";

	private final Map<String, FieldExtractor> fieldExtractorsByName = new HashMap<>();

	FieldExtractorMapper(boolean useRealNames, DateTimeFormatter dateTimeFormatter) {
		fieldExtractorsByName.put(DESCRIPTION_KEY, new DescriptionFieldExtractor());
		fieldExtractorsByName.put(STATUS_KEY, new StatusFieldExtractor());
		fieldExtractorsByName.put(PRIORITY_KEY, new PriorityFieldExtractor());
		fieldExtractorsByName.put(TYPE_KEY, new TypeFieldExtractor());
		fieldExtractorsByName.put(RESOLUTION_KEY, new ResolutionFieldExtractor());
		fieldExtractorsByName.put(REPORTER_KEY, new ReporterFieldExtractor(useRealNames));
		fieldExtractorsByName.put(ASSIGNEE_KEY, new AssigneeFieldExtractor(useRealNames));
		fieldExtractorsByName.put(CREATED_KEY, new CreatedFieldExtractor(dateTimeFormatter));
		fieldExtractorsByName.put(UPDATED_KEY, new UpdatedFieldExtractor(dateTimeFormatter));
	}


	List<FieldExtractor> getCreators(List<String> fields) {
		log.debug("Fields to find creators for: {}", fields);
		List<FieldExtractor> fieldExtractors = fields.stream()
				.map(fieldExtractorsByName::get)
				.filter(Objects::nonNull)
				.collect(Collectors.toList());
		log.debug("Found creators: {}", fieldExtractors.stream().map(c -> c.getClass().getSimpleName()));
		return fieldExtractors;
	}
}
