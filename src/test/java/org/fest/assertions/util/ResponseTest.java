package org.fest.assertions.util;

import com.ning.http.client.Cookie;
import com.ning.http.client.FluentCaseInsensitiveStringsMap;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.fest.assertions.api.Assertions.entry;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ResponseTest {

	@Test
	public void test_constructShouldInitializeStatusCodeAndContentType() {
		int statusCode = 200;
		String contentType = "application/json";
		Response rsp = new Response(statusCode, contentType);
		assertThat(rsp.getStatusCode()).isEqualTo(statusCode);
		assertThat(rsp.getContentType()).isEqualTo(contentType);
		assertThat(rsp.getCookies()).isNotNull().isEmpty();
		assertThat(rsp.getHeaders()).isNotNull().isEmpty();
	}

	@Test
	public void test_constructShouldInitializeStatusCodeAndContentTypeWithHeadersAndCookies() {
		List<org.fest.assertions.util.Cookie> cookies = Arrays.asList(
				new org.fest.assertions.util.Cookie("cookie1", "value1"),
				new org.fest.assertions.util.Cookie("cookie2", "value2")
		);

		Map<String, String> headers = new HashMap<String, String>();
		headers.put("header", "name");

		int statusCode = 200;
		String contentType = "application/json";
		Response rsp = new Response(statusCode, contentType, headers, cookies);
		assertThat(rsp.getStatusCode()).isEqualTo(statusCode);
		assertThat(rsp.getContentType()).isEqualTo(contentType);
		assertThat(rsp.getCookies()).isNotNull().hasSize(2);
		assertThat(rsp.getHeaders()).isNotNull().hasSize(1);
	}

	@Test
	public void test_constructFromAsyncHttpResponseWithoutHeadersAndCookies() throws Exception {
		com.ning.http.client.Response asyncResponse = mock(com.ning.http.client.Response.class);

		int statusCode = 200;
		when(asyncResponse.getStatusCode()).thenReturn(statusCode);

		String contentType = "application/json";
		when(asyncResponse.getContentType()).thenReturn(contentType);

		FluentCaseInsensitiveStringsMap headers = new FluentCaseInsensitiveStringsMap();
		when(asyncResponse.getHeaders()).thenReturn(headers);

		Response rsp = new Response(asyncResponse);
		assertThat(rsp.getStatusCode()).isEqualTo(statusCode);
		assertThat(rsp.getContentType()).isEqualTo(contentType);
		assertThat(rsp.getHeaders()).isNotNull().isEmpty();
		assertThat(rsp.getCookies()).isNotNull().isEmpty();
	}

	@Test
	public void test_constructFromAsyncHttpResponse() throws Exception {
		com.ning.http.client.Response asyncResponse = mock(com.ning.http.client.Response.class);

		int statusCode = 200;
		when(asyncResponse.getStatusCode()).thenReturn(statusCode);

		String contentType = "application/json";
		when(asyncResponse.getContentType()).thenReturn(contentType);

		// Set some headers
		FluentCaseInsensitiveStringsMap headers = new FluentCaseInsensitiveStringsMap();

		String headerContentLength = "Content-Length";
		String valueContentLength = "200";
		headers.add(headerContentLength, valueContentLength);

		String headerFakeHeader = "Fake-Header";
		String valueFakeHeader = "fake";
		headers.add(headerFakeHeader, valueFakeHeader);

		when(asyncResponse.getHeaders()).thenReturn(headers);
		when(asyncResponse.getHeader(headerContentLength)).thenReturn(valueContentLength);
		when(asyncResponse.getHeader(headerFakeHeader)).thenReturn(valueFakeHeader);

		// Set some cookies
		List<Cookie> cookies = Arrays.asList(
				new Cookie("domain1", "name1", "value1", "path1", 0, true),
				new Cookie("domain2", "name2", "value2", "path2", -1, false)
		);
		when(asyncResponse.getCookies()).thenReturn(cookies);

		Response rsp = new Response(asyncResponse);
		assertThat(rsp.getStatusCode()).isEqualTo(statusCode);
		assertThat(rsp.getContentType()).isEqualTo(contentType);
		assertThat(rsp.getHeaders()).isNotNull().isNotEmpty().hasSize(2)
				.contains(entry(headerContentLength.toLowerCase(), valueContentLength))
				.contains(entry(headerFakeHeader.toLowerCase(), valueFakeHeader));
		assertThat(rsp.getCookies()).isNotNull().hasSize(2);
	}

	@Test
	public void test_shouldFindHeaderCaseInsensitive() {
		Map<String, String> headers = new HashMap<String, String>();
		headers.put("Header", "name");

		Response rsp = new Response(200, "application/json", headers, null);
		assertThat(rsp.getHeader("Header")).isNotNull().isEqualTo("name");
		assertThat(rsp.getHeader("header")).isNotNull().isEqualTo("name");
		assertThat(rsp.getHeader("HEADER")).isNotNull().isEqualTo("name");
		assertThat(rsp.getHeader("FOO")).isNull();
	}

	@Test
	public void test_shouldFindCookieByNameCaseInsensitive() {
		List<org.fest.assertions.util.Cookie> cookies = Arrays.asList(
				new org.fest.assertions.util.Cookie("cookie1", "value1"),
				new org.fest.assertions.util.Cookie("cookie2", "value2")
		);

		Response rsp = new Response(200, "application/json", null, cookies);
		assertThat(rsp.getCookie("cookie1")).isNotNull();
		assertThat(rsp.getCookie("cookie2")).isNotNull();
		assertThat(rsp.getCookie("COOKIE1")).isNotNull();
		assertThat(rsp.getCookie("Cookie1")).isNotNull();
		assertThat(rsp.getCookie("foo")).isNull();
	}
}
