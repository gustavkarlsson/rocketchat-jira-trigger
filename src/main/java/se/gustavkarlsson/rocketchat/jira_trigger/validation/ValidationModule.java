package se.gustavkarlsson.rocketchat.jira_trigger.validation;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import se.gustavkarlsson.rocketchat.jira_trigger.configuration.RocketChatConfiguration;

import java.util.Arrays;
import java.util.List;

public class ValidationModule extends AbstractModule {
	@Override
	protected void configure() {

	}

	@Provides
	@Singleton
	TokenValidator provideTokenValidator(RocketChatConfiguration rocketChatConfig) {
		return new TokenValidator(rocketChatConfig.getTokens());
	}

	@Provides
	@Singleton
	UserValidator provideUserValidator(RocketChatConfiguration rocketChatConfig) {
		return new UserValidator(rocketChatConfig.getBlacklistedUsers(), rocketChatConfig.getWhitelistedUsers());
	}

	@Provides
	@Singleton
	ChannelValidator provideChannelValidator(RocketChatConfiguration rocketChatConfig) {
		return new ChannelValidator(rocketChatConfig.getBlacklistedChannels(), rocketChatConfig.getWhitelistedChannels());
	}

	@Provides
	@Singleton
	List<Validator> provideValidators(TokenValidator tokenValidator, UserValidator userValidator, ChannelValidator channelValidator) {
		return Arrays.asList(tokenValidator, userValidator, channelValidator);
	}
}
