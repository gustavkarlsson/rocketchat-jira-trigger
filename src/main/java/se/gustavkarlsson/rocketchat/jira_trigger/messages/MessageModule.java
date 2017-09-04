package se.gustavkarlsson.rocketchat.jira_trigger.messages;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import se.gustavkarlsson.rocketchat.jira_trigger.configuration.MessageConfiguration;

public class MessageModule extends AbstractModule {
	@Override
	protected void configure() {
	}

	@Provides
	ToRocketChatMessageFactory provideToRocketChatMessageFactory(MessageConfiguration messageConfig) throws Exception {
		return new ToRocketChatMessageFactory(messageConfig.getUsername(), messageConfig.getIconUrl());
	}

	@Provides
	FieldExtractorMapper provideFieldExtractorMapper(MessageConfiguration messageConfig) throws Exception {
		return new FieldExtractorMapper(messageConfig.isUseRealNames(), messageConfig.getDateFormatter());
	}
}
