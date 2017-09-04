package se.gustavkarlsson.rocketchat.jira_trigger;

import com.google.inject.Guice;
import com.google.inject.Injector;
import org.slf4j.Logger;
import se.gustavkarlsson.rocketchat.jira_trigger.di.modules.ConfigurationModule;

import static org.slf4j.LoggerFactory.getLogger;

public class App {
	private static final Logger log = getLogger(App.class);

	private final Server server;

	public static void main(String... args) {
		try {
			new App(args).server.start();
		} catch (Exception e) {
			log.error("Fatal error", e);
			System.exit(1);
		}
	}

	App(String... args) throws Exception {
		Injector injector = Guice.createInjector(new ConfigurationModule(args));
		this.server = injector.getInstance(Server.class);
	}

	Server getServer() {
		return server;
	}
}
