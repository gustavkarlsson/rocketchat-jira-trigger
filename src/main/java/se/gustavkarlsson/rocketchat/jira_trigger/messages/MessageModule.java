package se.gustavkarlsson.rocketchat.jira_trigger.messages;

import com.google.inject.*;
import se.gustavkarlsson.rocketchat.jira_trigger.configuration.MessageConfiguration;
import se.gustavkarlsson.rocketchat.jira_trigger.di.annotations.Default;
import se.gustavkarlsson.rocketchat.jira_trigger.messages.fields.FieldExtractor;

import java.util.List;

public class MessageModule extends AbstractModule {
	@Override
	protected void configure() {
		bind(new TypeLiteral<List<FieldExtractor>>() {
		}).annotatedWith(Default.class).toProvider(DefaultFieldExtractorsProvider.class);
		bind(new TypeLiteral<List<FieldExtractor>>() {
		}).toProvider(ExtendedFieldExtractorsProvider.class);
	}

	@Provides
	ToRocketChatMessageFactory provideToRocketChatMessageFactory(MessageConfiguration messageConfig) {
		return new ToRocketChatMessageFactory(messageConfig.getUsername(), messageConfig.getIconUrl());
	}

	@Provides
	FieldExtractorMapper provideFieldExtractorMapper(MessageConfiguration messageConfig) {
		return new FieldExtractorMapper(messageConfig.isUseRealNames(), messageConfig.getDateFormatter());
	}

	@Provides
	AttachmentConverter provideAttachmentConverter(MessageConfiguration messageConfig, @Default List<FieldExtractor> defaultFieldExtractors, List<FieldExtractor> extendedFieldExtractors) {
		return new AttachmentConverter(messageConfig.isPriorityColors(), messageConfig.getDefaultColor(), defaultFieldExtractors, extendedFieldExtractors);
	}

	public static class DefaultFieldExtractorsProvider implements Provider<List<FieldExtractor>> {
		private final FieldExtractorMapper fieldExtractorMapper;
		private final MessageConfiguration messageConfig;

		@Inject
		public DefaultFieldExtractorsProvider(FieldExtractorMapper fieldExtractorMapper, MessageConfiguration messageConfig) {
			this.fieldExtractorMapper = fieldExtractorMapper;
			this.messageConfig = messageConfig;
		}

		@Override
		public List<FieldExtractor> get() {
			return fieldExtractorMapper.getCreators(messageConfig.getDefaultFields());
		}
	}

	public static class ExtendedFieldExtractorsProvider implements Provider<List<FieldExtractor>> {
		private final FieldExtractorMapper fieldExtractorMapper;
		private final MessageConfiguration messageConfig;

		@Inject
		public ExtendedFieldExtractorsProvider(FieldExtractorMapper fieldExtractorMapper, MessageConfiguration messageConfig) {
			this.fieldExtractorMapper = fieldExtractorMapper;
			this.messageConfig = messageConfig;
		}

		@Override
		public List<FieldExtractor> get() {
			return fieldExtractorMapper.getCreators(messageConfig.getExtendedFields());
		}
	}
}
