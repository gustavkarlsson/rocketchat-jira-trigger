package se.gustavkarlsson.rocketchat.jira_trigger.jira;

import com.atlassian.jira.rest.client.api.IssueRestClient;
import com.atlassian.jira.rest.client.api.JiraRestClient;
import com.atlassian.jira.rest.client.api.MetadataRestClient;
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

	@Provides
	MetadataRestClient provideMetadataRestClient(JiraRestClient jiraClient) {
		return jiraClient.getMetadataClient();
	}

	@Provides
	IssueRestClient provideIssueRestClient(JiraRestClient jiraClient) {
		return jiraClient.getIssueClient();
	}
}
