package se.gustavkarlsson.rocketchat.jira_trigger.messages;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.google.inject.TypeLiteral;
import se.gustavkarlsson.rocketchat.jira_trigger.configuration.JiraConfiguration;
import se.gustavkarlsson.rocketchat.jira_trigger.configuration.MessageConfiguration;
import se.gustavkarlsson.rocketchat.jira_trigger.messages.field_creators.FieldCreator;

import java.util.List;

public class MessageModule extends AbstractModule {
	@Override
	protected void configure() {
		bind(new TypeLiteral<List<FieldCreator>>() {
		}).toProvider(FieldCreatorsProvider.class);
	}

	@Provides
	@Singleton
	ToRocketChatMessageFactory provideToRocketChatMessageFactory(MessageConfiguration messageConfig) {
		return new ToRocketChatMessageFactory(messageConfig.getUsername(), messageConfig.getIconUrl());
	}

	@Provides
	@Singleton
	FieldCreatorMapper provideFieldCreatorMapper(MessageConfiguration messageConfig) {
		return new FieldCreatorMapper(messageConfig.isUseRealNames(), messageConfig.getDateFormatter());
	}

	@Provides
	@Singleton
	AttachmentCreator provideAttachmentConverter(MessageConfiguration messageConfig, JiraConfiguration jiraConfig, List<FieldCreator> fieldCreators) {
		return new AttachmentCreator(messageConfig.isPriorityColors(), messageConfig.getDefaultColor(), fieldCreators, jiraConfig.getUri(), messageConfig.getMaxTextLength());
	}

}
