package se.gustavkarlsson.rocketchat.jira_trigger.configuration;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;

import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class CascadingConfigMapTest {
	private static final String KEY = "key";

	@Mock
	private ConfigMap mockFirstConfigMap;

	@Mock
	private ConfigMap mockSecondConfigMap;

	private CascadingConfigMap cascadingConfigMap;

	@Before
	public void setUp() {
		this.cascadingConfigMap = new CascadingConfigMap(Arrays.asList(mockFirstConfigMap, mockSecondConfigMap));
	}

	@Test(expected = NullPointerException.class)
	public void createWithNullConfigMapsThrowsException() {
		new CascadingConfigMap(null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void createWithNullConfigMapsValueThrowsException() {
		new CascadingConfigMap(singletonList(null));
	}

	@Test
	public void getWithTwoValuesReturnsFirst() {
		String first = "first";
		when(mockFirstConfigMap.getString(KEY)).thenReturn(first);

		String value = cascadingConfigMap.getString(KEY);

		assertThat(value).isEqualTo(first);
		verify(mockSecondConfigMap, never()).getString(KEY);
	}

	@Test
	public void getWithNullAsFirstValueReturnsSecond() {
		String second = "second";
		when(mockFirstConfigMap.getString(KEY)).thenReturn(null);
		when(mockSecondConfigMap.getString(KEY)).thenReturn(second);

		String value = cascadingConfigMap.getString(KEY);

		assertThat(value).isEqualTo(second);
	}

	@Test
	public void getWithNullAsSecondValueReturnsFirst() {
		String first = "first";
		when(mockFirstConfigMap.getString(KEY)).thenReturn(first);

		String value = cascadingConfigMap.getString(KEY);

		assertThat(value).isEqualTo(first);
		verify(mockSecondConfigMap, never()).getString(KEY);
	}

	@Test
	public void getWithOnlyNullValuesReturnsNull() {
		when(mockFirstConfigMap.getString(KEY)).thenReturn(null);
		when(mockSecondConfigMap.getString(KEY)).thenReturn(null);

		String value = cascadingConfigMap.getString(KEY);

		assertThat(value).isEqualTo(null);
	}

	@Test
	public void getWithNoConfigMapsReturnsNull() {
		CascadingConfigMap cascadingConfigMap = new CascadingConfigMap(emptyList());

		String value = cascadingConfigMap.getString(KEY);

		assertThat(value).isEqualTo(null);
	}
}
