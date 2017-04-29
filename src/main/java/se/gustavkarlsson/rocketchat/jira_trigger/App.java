package se.gustavkarlsson.rocketchat.jira_trigger;

import org.slf4j.Logger;
import se.gustavkarlsson.rocketchat.jira_trigger.configuration.Configuration;

import java.io.File;

import static org.slf4j.LoggerFactory.getLogger;

public class App {
	private static final Logger log = getLogger(App.class);

	private final Server server;

	App(String... args) throws Exception {
		String configFilePath = verifySyntax(args);
		ConfigFactory configFactory = new ConfigFactory();
		Configuration config = configFactory.get(new File(configFilePath));
		Server server = new Server(config);
		server.logJiraServerInfo();
		this.server = server;
	}

	public static void main(String... args) {
		try {
			new App(args).server.start();
		} catch (Exception e) {
			log.error("Fatal error", e);
			System.exit(1);
		}
	}

	private static String verifySyntax(String[] args) {
		if (args.length != 1) {
			throw new IllegalArgumentException("Exactly one configuration file must be specified");
		}
		return args[0];
	}

	Server getServer() {
		return server;
	}
}
