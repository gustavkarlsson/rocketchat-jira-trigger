package se.gustavkarlsson.rocketchat.jira_trigger;

import org.junit.Before;
import org.junit.Test;
import spark.Request;
import spark.Response;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class UuidGeneratingExceptionHandlerTest {

	private UuidGeneratingExceptionHandler handler;

	private Request mockRequest;
	private Response mockResponse;

	@Before
	public void setUp() throws Exception {
		handler = new UuidGeneratingExceptionHandler();
		mockRequest = mock(Request.class);
		mockResponse = mock(Response.class);
	}

	@Test
	public void handleSetsStatus500() throws Exception {
		handler.handle(new Exception(), mockRequest, mockResponse);
		verify(mockResponse).status(500);
	}

	@Test
	public void handleSetsBody() throws Exception {
		TestResponse response = new TestResponse();
		handler.handle(new Exception(), mockRequest, response);
		assertThat(response.body()).startsWith(UuidGeneratingExceptionHandler.ERROR_MESSAGE_PREFIX);
	}

	@Test
	public void handleGeneratesUniqueErrorCode() throws Exception {
		TestResponse response1 = new TestResponse();
		handler.handle(new Exception(), mockRequest, response1);
		TestResponse response2 = new TestResponse();
		handler.handle(new Exception(), mockRequest, response2);

		int errorCodeIndex = UuidGeneratingExceptionHandler.ERROR_MESSAGE_PREFIX.length();
		String errorCode1 = response1.body().substring(errorCodeIndex);
		String errorCode2 = response2.body().substring(errorCodeIndex);

		assertThat(errorCode1).isNotEmpty();
		assertThat(errorCode2).isNotEmpty();
		assertThat(errorCode1).isNotEqualTo(errorCode2);
	}

	private static class TestResponse extends Response {
		private int status;

		@Override
		public int status() {
			return status;
		}

		@Override
		public void status(int status) {
			this.status = status;
		}
	}
}
