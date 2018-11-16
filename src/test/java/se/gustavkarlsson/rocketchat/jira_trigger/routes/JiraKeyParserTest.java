package se.gustavkarlsson.rocketchat.jira_trigger.routes;

import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import java.util.*;

import static java.util.Collections.emptySet;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(Enclosed.class)
public class JiraKeyParserTest {

	public static class Construction {

		@Test(expected = NullPointerException.class)
		public void nullPrefixWhitelistThrowsException() throws Exception {
			new JiraKeyParser(null, emptySet());
		}

		@Test(expected = NullPointerException.class)
		public void nullSuffixWhitelistThrowsException() throws Exception {
			new JiraKeyParser(emptySet(), null);
		}
	}

	@RunWith(Parameterized.class)
	public static class ValidJiraKeys {

		private final String text;

		public ValidJiraKeys(String text) {
			this.text = text;
		}

		@Parameters(name = "{0}")
		public static Collection<Object[]> data() {
			return Arrays.asList(new Object[][]{
					{"A-1"},
					{"AB-1"},
					{"A-999999999"},
					{"AAAAAAAAAA-1"},
					{"ZZZZZZZZZZ-9999999999"},
					{"Lets try ABC-123"},
					{"ABC-123 is a good one"},
					{"\nABC-123\n"},
					{"\tABC-123\t"},
					{" ABC-123 "},
					{"A1BC3-123"}
			});
		}

		@Test
		public void singleJiraKeyDetected() throws Exception {
			JiraKeyParser parser = new JiraKeyParser(emptySet(), emptySet());

			Map<String, IssueDetail> keys = parser.parse(text);

			assertThat(keys).hasSize(1);
		}

	}

	@RunWith(Parameterized.class)
	public static class InvalidJiraKeys {

		private final String text;

		public InvalidJiraKeys(String text) {
			this.text = text;
		}

		@Parameters(name = "{0}")
		public static Collection<Object[]> data() {
			return Arrays.asList(new Object[][]{
					{""},
					{"ABC"},
					{"123"},
					{"-123"},
					{"ABC-"},
					{"Å-123"},
					{"ABC--123"},
					{"ABC123"},
					{"abc-123"},
					{"ABC-123Z"},
					{"(ABC-123)"},
					{"browse/ABC-123"}
			});
		}

		@Test
		public void noJiraKeyDetected() throws Exception {
			JiraKeyParser parser = new JiraKeyParser(emptySet(), emptySet());

			Map<String, IssueDetail> keys = parser.parse(text);

			assertThat(keys).isEmpty();
		}

	}

	@RunWith(Parameterized.class)
	public static class Context {

		private final Set<Character> whitelistedPrefixes;
		private final Set<Character> whitelistedSuffixes;
		private final String text;

		public Context(Set<Character> whitelistedPrefixes, Set<Character> whitelistedSuffixes, String text) {
			this.whitelistedPrefixes = whitelistedPrefixes;
			this.whitelistedSuffixes = whitelistedSuffixes;
			this.text = text;
		}

		@Parameters(name = "prefixes: {0}, suffixes: {1}, text: {2}")
		public static Collection<Object[]> data() {
			return Arrays.asList(new Object[][]{
					{setOf('('), setOf(')'), "(ABC-123)"},
					{setOf('('), emptySet(), "(ABC-123"},
					{emptySet(), setOf(')'), "ABC-123)"},
					{emptySet(), setOf(')'), "ABC-123)"}
			});
		}

		private static Set<Character> setOf(Character... chars) {
			return new HashSet<>(Arrays.asList(chars));
		}

		@Test
		public void singleJiraKeyDetected() throws Exception {
			JiraKeyParser parser = new JiraKeyParser(whitelistedPrefixes, whitelistedSuffixes);

			Map<String, IssueDetail> keys = parser.parse(text);

			assertThat(keys).hasSize(1);
		}

	}
}
