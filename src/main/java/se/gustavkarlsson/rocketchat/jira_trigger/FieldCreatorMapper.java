package se.gustavkarlsson.rocketchat.jira_trigger;

import org.slf4j.Logger;
import se.gustavkarlsson.rocketchat.jira_trigger.configuration.MessageConfiguration;
import se.gustavkarlsson.rocketchat.jira_trigger.converters.fields.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import static org.slf4j.LoggerFactory.getLogger;

class FieldCreatorMapper {
	static final String DESCRIPTION_KEY = "description";
	static final String ASSIGNEE_KEY = "assignee";
	static final String STATUS_KEY = "status";
	static final String REPORTER_KEY = "reporter";
	static final String PRIORITY_KEY = "priority";
	static final String RESOLUTION_KEY = "resolution";
	static final String TYPE_KEY = "type";
	static final String CREATED_KEY = "created";
	static final String UPDATED_KEY = "updated";
	private static final Logger log = getLogger(FieldCreatorMapper.class);
	private final Map<String, Supplier<FieldCreator>> mapping = new HashMap<>();

	FieldCreatorMapper(MessageConfiguration config) {
		mapping.put(DESCRIPTION_KEY, DescriptionFieldCreator::new);
		mapping.put(STATUS_KEY, StatusFieldCreator::new);
		mapping.put(PRIORITY_KEY, PriorityFieldCreator::new);
		mapping.put(TYPE_KEY, TypeFieldCreator::new);
		mapping.put(RESOLUTION_KEY, ResolutionFieldCreator::new);
		mapping.put(REPORTER_KEY, ReporterFieldCreator::new);
		mapping.put(ASSIGNEE_KEY, AssigneeFieldCreator::new);
		mapping.put(CREATED_KEY, () -> new CreatedFieldCreator(config.getDateFormat()));
		mapping.put(UPDATED_KEY, () -> new UpdatedFieldCreator(config.getDateFormat()));
	}


	List<FieldCreator> getCreators(List<String> fields) {
		log.debug("Fields to find creators for: {}", fields);
		List<FieldCreator> fieldCreators = fields.stream()
				.map(mapping::get)
				.filter(Objects::nonNull)
				.map(Supplier::get)
				.collect(Collectors.toList());
		log.debug("Found creators: {}", fieldCreators.stream().map(c -> c.getClass().getSimpleName()));
		return fieldCreators;
	}
}
