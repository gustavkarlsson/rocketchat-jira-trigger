package se.gustavkarlsson.rocketchat.jira_trigger.jira;

import com.atlassian.jira.rest.client.api.JiraRestClient;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import se.gustavkarlsson.rocketchat.jira_trigger.configuration.JiraConfiguration;

public class JiraModule extends AbstractModule {
	@Override
	protected void configure() {
	}

	@Provides
	JiraRestClient provideJiraRestClient(RestClientProvider restClientProvider, JiraConfiguration jiraConfig) {
		return restClientProvider.get(jiraConfig);
	}
}
