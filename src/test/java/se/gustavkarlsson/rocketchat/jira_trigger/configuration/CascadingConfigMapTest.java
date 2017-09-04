package se.gustavkarlsson.rocketchat.jira_trigger.configuration;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CascadingConfigMapTest {
	private static final String KEY = "key";

	@Mock
	private ConfigMap mockFirstConfigMap;

	@Mock
	private ConfigMap mockSecondConfigMap;

	private CascadingConfigMap cascadingConfigMap;

	@Before
	public void setUp() throws Exception {
		this.cascadingConfigMap = new CascadingConfigMap(asList(mockFirstConfigMap, mockSecondConfigMap));
	}

	@Test(expected = NullPointerException.class)
	public void createWithNullConfigMapsThrowsException() throws Exception {
		new CascadingConfigMap(null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void createWithNullConfigMapsValueThrowsException() throws Exception {
		new CascadingConfigMap(singletonList(null));
	}

	@Test
	public void getWithTwoValuesReturnsSecond() throws Exception {
		String second = "second";
		when(mockFirstConfigMap.getString(KEY)).thenReturn("first");
		when(mockSecondConfigMap.getString(KEY)).thenReturn(second);

		String value = cascadingConfigMap.getString(KEY);

		assertThat(value).isEqualTo(second);
	}

	@Test
	public void getWithNullAsFirstValueReturnsSecond() throws Exception {
		String second = "second";
		when(mockFirstConfigMap.getString(KEY)).thenReturn(null);
		when(mockSecondConfigMap.getString(KEY)).thenReturn(second);

		String value = cascadingConfigMap.getString(KEY);

		assertThat(value).isEqualTo(second);
	}

	@Test
	public void getWithNullAsSecondValueReturnsFirst() throws Exception {
		String first = "first";
		when(mockFirstConfigMap.getString(KEY)).thenReturn(first);
		when(mockSecondConfigMap.getString(KEY)).thenReturn(null);

		String value = cascadingConfigMap.getString(KEY);

		assertThat(value).isEqualTo(first);
	}

	@Test
	public void getWithOnlyNullValuesReturnsNull() throws Exception {
		when(mockFirstConfigMap.getString(KEY)).thenReturn(null);
		when(mockSecondConfigMap.getString(KEY)).thenReturn(null);

		String value = cascadingConfigMap.getString(KEY);

		assertThat(value).isEqualTo(null);
	}

	@Test
	public void getWithNoConfigMapsReturnsNull() throws Exception {
		CascadingConfigMap cascadingConfigMap = new CascadingConfigMap(emptyList());

		String value = cascadingConfigMap.getString(KEY);

		assertThat(value).isEqualTo(null);
	}
}
