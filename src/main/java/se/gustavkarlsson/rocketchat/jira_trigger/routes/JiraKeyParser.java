package se.gustavkarlsson.rocketchat.jira_trigger.routes;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.google.common.collect.Sets.union;
import static org.apache.commons.lang3.Validate.notNull;

class JiraKeyParser {
	private static final Pattern JIRA_KEY = Pattern.compile("[a-zA-Z][a-zA-Z0-9]+-\\d+");
	private static final Set<Character> ALWAYS_VALID = new HashSet<>(Arrays.asList(' ', '\t', '\n'));

	private final Set<Character> whitelistedPrefixes;
	private final Set<Character> whitelistedSuffixes;

	JiraKeyParser(Set<Character> whitelistedPrefixes, Set<Character> whitelistedSuffixes) {
		this.whitelistedPrefixes = union(notNull(whitelistedPrefixes), ALWAYS_VALID);
		this.whitelistedSuffixes = union(notNull(whitelistedSuffixes), ALWAYS_VALID);
	}

	private static Set<Pair<Character, Character>> parseContexts(String key, String text) {
		Set<Pair<Character, Character>> contexts = new HashSet<>();
		int fromIndex = 0;
		while (true) {
			int beginIndex = text.indexOf(key, fromIndex);
			if (beginIndex == -1) {
				break;
			}
			int endIndex = beginIndex + key.length();
			Character prefix = beginIndex > 0 ? text.charAt(beginIndex - 1) : ' ';
			Character suffix = endIndex < text.length() ? text.charAt(endIndex) : ' ';
			contexts.add(new ImmutablePair<>(prefix, suffix));
			fromIndex = endIndex;
		}
		return contexts;
	}

	Set<String> parse(String text) {
		Matcher matcher = JIRA_KEY.matcher(text);
		Set<String> jiraKeys = new HashSet<>();
		while (matcher.find()) {
			String key = matcher.group();
			if (!hasValidContext(key, text)) {
				continue;
			}
			jiraKeys.add(key);
		}
		return jiraKeys;
	}

	private boolean hasValidContext(String key, String text) {
		Set<Pair<Character, Character>> contextsForKey = parseContexts(key, text);
		return contextsForKey.stream().anyMatch(this::isValidContext);
	}

	private boolean isValidContext(Pair<Character, Character> context) {
		return whitelistedPrefixes.contains(context.getLeft()) && whitelistedSuffixes.contains(context.getRight());
	}
}
