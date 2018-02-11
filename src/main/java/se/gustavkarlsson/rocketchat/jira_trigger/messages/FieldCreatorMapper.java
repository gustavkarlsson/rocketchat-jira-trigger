package se.gustavkarlsson.rocketchat.jira_trigger.messages;

import org.joda.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import se.gustavkarlsson.rocketchat.jira_trigger.messages.field_creators.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import static org.apache.commons.lang3.Validate.notNull;
import static org.slf4j.LoggerFactory.getLogger;

class FieldCreatorMapper {
	private static final Logger log = getLogger(FieldCreatorMapper.class);

	static final String DESCRIPTION_KEY = "description";
	static final String ASSIGNEE_KEY = "assignee";
	static final String STATUS_KEY = "status";
	static final String REPORTER_KEY = "reporter";
	static final String PRIORITY_KEY = "priority";
	static final String RESOLUTION_KEY = "resolution";
	static final String TYPE_KEY = "type";
	static final String CREATED_KEY = "created";
	static final String UPDATED_KEY = "updated";

	private final Map<String, FieldCreator> fieldCreatorsByName = new HashMap<>();

	FieldCreatorMapper(boolean useRealNames, DateTimeFormatter dateTimeFormatter) {
		fieldCreatorsByName.put(DESCRIPTION_KEY, new DescriptionFieldCreator());
		fieldCreatorsByName.put(STATUS_KEY, new StatusFieldCreator());
		fieldCreatorsByName.put(PRIORITY_KEY, new PriorityFieldCreator());
		fieldCreatorsByName.put(TYPE_KEY, new TypeFieldCreator());
		fieldCreatorsByName.put(RESOLUTION_KEY, new ResolutionFieldCreator());
		fieldCreatorsByName.put(REPORTER_KEY, new ReporterFieldCreator(useRealNames));
		fieldCreatorsByName.put(ASSIGNEE_KEY, new AssigneeFieldCreator(useRealNames));
		fieldCreatorsByName.put(CREATED_KEY, new CreatedFieldCreator(notNull(dateTimeFormatter)));
		fieldCreatorsByName.put(UPDATED_KEY, new UpdatedFieldCreator(notNull(dateTimeFormatter)));
	}


	List<FieldCreator> getCreators(List<String> fields) {
		log.debug("Fields to find creators for: {}", fields);
		List<FieldCreator> fieldCreators = fields.stream()
				.map(fieldCreatorsByName::get)
				.filter(Objects::nonNull)
				.collect(Collectors.toList());
		log.debug("Found creators: {}", fieldCreators.stream().map(c -> c.getClass().getSimpleName()));
		return fieldCreators;
	}
}
