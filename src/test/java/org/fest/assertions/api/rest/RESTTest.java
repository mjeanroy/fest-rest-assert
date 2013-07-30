package org.fest.assertions.api.rest;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.RETURNS_DEEP_STUBS;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.fest.assertions.util.Cookie;
import org.junit.Test;

public class RESTTest {

	@Test
	public void test_assertThatHttpResponse() {
		org.apache.http.HttpResponse response = mock(org.apache.http.HttpResponse.class, RETURNS_DEEP_STUBS);
		ResponseAssert assertion = REST.assertThat(response);
		assertThat(assertion).isNotNull();
	}

	@Test
	public void test_assertThatAsyncHttpResponse() {
		com.ning.http.client.Response response = mock(com.ning.http.client.Response.class, RETURNS_DEEP_STUBS);
		ResponseAssert assertion = REST.assertThat(response);
		assertThat(assertion).isNotNull();
	}

	@Test
	public void test_assertThatAsyncCookie() {
		Cookie cookie = mock(Cookie.class);
		CookieAssert assertion = REST.assertThat(cookie);
		assertThat(assertion).isNotNull();
	}

	@Test
	public void test_assertCookieThat() {
		com.ning.http.client.Cookie cookie1 = mock(com.ning.http.client.Cookie.class);
		com.ning.http.client.Cookie cookie2 = mock(com.ning.http.client.Cookie.class);

		when(cookie1.getName()).thenReturn("cookie1");
		when(cookie2.getName()).thenReturn("cookie2");

		List<com.ning.http.client.Cookie> cookies = new ArrayList<com.ning.http.client.Cookie>();
		cookies.add(cookie1);
		cookies.add(cookie2);

		com.ning.http.client.Response response = mock(com.ning.http.client.Response.class);
		when(response.getCookies()).thenReturn(cookies);

		CookieAssert assertion = REST.assertCookieThat("cookie1", response);
		assertThat(assertion).isNotNull();

		assertion = REST.assertCookieThat("cookie2", response);
		assertThat(assertion).isNotNull();

		assertion = REST.assertCookieThat("cookie3", response);
		assertThat(assertion).isNotNull();
	}

	@Test
	public void test_extractCookieFromAsyncHttpResponse() {
		com.ning.http.client.Cookie cookie1 = mock(com.ning.http.client.Cookie.class);
		com.ning.http.client.Cookie cookie2 = mock(com.ning.http.client.Cookie.class);

		when(cookie1.getName()).thenReturn("cookie1");
		when(cookie2.getName()).thenReturn("cookie2");

		List<com.ning.http.client.Cookie> cookies = new ArrayList<com.ning.http.client.Cookie>();
		cookies.add(cookie1);
		cookies.add(cookie2);

		com.ning.http.client.Response response = mock(com.ning.http.client.Response.class);
		when(response.getCookies()).thenReturn(cookies);

		Cookie cookie = REST.extractCookie("cookie1", response);
		assertThat(cookie).isNotNull();

		cookie = REST.extractCookie("cookie2", response);
		assertThat(cookie).isNotNull();

		cookie = REST.extractCookie("cookie3", response);
		assertThat(cookie).isNull();
	}
}
