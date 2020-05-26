package se.gustavkarlsson.rocketchat.jira_trigger.server;

import com.atlassian.jira.rest.client.api.MetadataRestClient;
import com.atlassian.jira.rest.client.api.domain.ServerInfo;

import com.google.inject.Singleton;
import io.atlassian.util.concurrent.Promise;
import org.slf4j.Logger;

import javax.inject.Inject;

import static org.apache.commons.lang3.Validate.notNull;
import static org.slf4j.LoggerFactory.getLogger;

@Singleton
class JiraServerInfoLogger {
	private static final Logger log = getLogger(JiraServerInfoLogger.class);

	private final MetadataRestClient metadataClient;

	@Inject
	JiraServerInfoLogger(MetadataRestClient metadataClient) {
		this.metadataClient = notNull(metadataClient);
	}

	void logInfo() {
		Promise<ServerInfo> serverInfoPromise = metadataClient.getServerInfo();
		try {
			ServerInfo serverInfo = serverInfoPromise.get();
			log.info("Found JIRA instance '{}' running version {} at {}",
					serverInfo.getServerTitle(),
					serverInfo.getVersion(),
					serverInfo.getBaseUri());
		} catch (Exception e) {
			log.error("Failed to connect to JIRA", e);
		}
	}
}
