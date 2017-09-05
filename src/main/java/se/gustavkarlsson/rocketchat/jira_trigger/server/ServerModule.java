package se.gustavkarlsson.rocketchat.jira_trigger.server;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import se.gustavkarlsson.rocketchat.jira_trigger.configuration.AppConfiguration;
import se.gustavkarlsson.rocketchat.jira_trigger.routes.DetectIssueRoute;

public class ServerModule extends AbstractModule {
	@Override
	protected void configure() {
	}

	@Provides
	Server provideServer(AppConfiguration appConfig, DetectIssueRoute detectIssueRoute, JiraServerInfoLogger jiraServerInfoLogger) {
		return new Server(appConfig.getMaxThreads(), appConfig.getPort(), detectIssueRoute, jiraServerInfoLogger);
	}
}
