package org.fest.assertions.util;

import org.fest.assertions.core.Condition;
import org.junit.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.util.Date;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CookieTest {

	@Test
	public void test_constructShouldInitializeWithDefaultValues() {
		Cookie cookie = new Cookie("name", "value");
		assertThat(cookie.getDomain()).isEqualTo("localhost");
		assertThat(cookie.getPath()).isEqualTo("/");
		assertThat(cookie.getName()).isEqualTo("name");
		assertThat(cookie.getValue()).isEqualTo("value");
		assertThat(cookie.getMaxAge()).isEqualTo(-1);
		assertThat(cookie.isSecure()).isFalse();
		assertThat(cookie.isHttpOnly()).isFalse();
		assertThat(cookie.getExpiryDate()).isNull();
	}

	@Test
	public void test_constructFromJavaxHttp() {
		javax.servlet.http.Cookie javaxHttpCookie = mock(javax.servlet.http.Cookie.class);

		String domain = "domain";
		String path = "/";
		String cookieName = "cookieName";
		String cookieValue = "cookieValue";
		long maxAge = 3600;

		when(javaxHttpCookie.getDomain()).thenReturn(domain);
		when(javaxHttpCookie.getPath()).thenReturn(path);
		when(javaxHttpCookie.getName()).thenReturn(cookieName);
		when(javaxHttpCookie.getValue()).thenReturn(cookieValue);
		when(javaxHttpCookie.getMaxAge()).thenReturn(Long.valueOf(maxAge).intValue());
		when(javaxHttpCookie.getSecure()).thenReturn(true);
		when(javaxHttpCookie.isHttpOnly()).thenReturn(true);

		Cookie cookie = new Cookie(javaxHttpCookie);
		assertThat(cookie.getDomain()).isEqualTo(domain);
		assertThat(cookie.getPath()).isEqualTo(path);
		assertThat(cookie.getName()).isEqualTo(cookieName);
		assertThat(cookie.getValue()).isEqualTo(cookieValue);
		assertThat(cookie.getMaxAge()).isEqualTo(maxAge);
		assertThat(cookie.isSecure()).isTrue();
		assertThat(cookie.isHttpOnly()).isTrue();
		assertThat(cookie.getExpiryDate()).isCloseTo(new Date(), maxAge * 1000);

		// Must set expiry date to null
		when(javaxHttpCookie.getMaxAge()).thenReturn(-1);
		cookie = new Cookie(javaxHttpCookie);
		assertThat(cookie.getExpiryDate()).isNull();
	}

	@Test
	public void test_constructFromApacheHttp() {
		org.apache.http.cookie.Cookie apacheHttpCookie = mock(org.apache.http.cookie.Cookie.class);

		String domain = "domain";
		String path = "/";
		String cookieName = "cookieName";
		String cookieValue = "cookieValue";
		final long maxAge = 3600;

		when(apacheHttpCookie.getDomain()).thenReturn(domain);
		when(apacheHttpCookie.getPath()).thenReturn(path);
		when(apacheHttpCookie.getName()).thenReturn(cookieName);
		when(apacheHttpCookie.getValue()).thenReturn(cookieValue);
		when(apacheHttpCookie.getExpiryDate()).thenAnswer(new Answer<Object>() {
			@Override
			public Object answer(InvocationOnMock invocation) throws Throwable {
				return new Date(System.currentTimeMillis() + maxAge);
			}
		});
		when(apacheHttpCookie.isSecure()).thenReturn(true);

		Cookie cookie = new Cookie(apacheHttpCookie);
		assertThat(cookie.getDomain()).isEqualTo(domain);
		assertThat(cookie.getPath()).isEqualTo(path);
		assertThat(cookie.getName()).isEqualTo(cookieName);
		assertThat(cookie.getValue()).isEqualTo(cookieValue);
		assertThat(cookie.getMaxAge()).is(new Condition<Long>() {
			@Override
			public boolean matches(Long value) {
				return Math.abs(3600 - value) < 3;
			}
		});
		assertThat(cookie.isSecure()).isTrue();
		assertThat(cookie.isHttpOnly()).isFalse();
		assertThat(cookie.getExpiryDate()).isCloseTo(new Date(), maxAge * 1000);

		// Must set maxAge to -1
		when(apacheHttpCookie.getExpiryDate()).thenReturn(null);
		cookie = new Cookie(apacheHttpCookie);
		assertThat(cookie.getMaxAge()).isEqualTo(-1);
	}

	@Test
	public void test_constructFromAsyncHttpClient() {
		com.ning.http.client.Cookie asyncCookie = mock(com.ning.http.client.Cookie.class);

		String domain = "domain";
		String path = "/";
		String cookieName = "cookieName";
		String cookieValue = "cookieValue";
		long maxAge = 3600;

		when(asyncCookie.getDomain()).thenReturn(domain);
		when(asyncCookie.getPath()).thenReturn(path);
		when(asyncCookie.getName()).thenReturn(cookieName);
		when(asyncCookie.getValue()).thenReturn(cookieValue);
		when(asyncCookie.getMaxAge()).thenReturn(Long.valueOf(maxAge).intValue());
		when(asyncCookie.isSecure()).thenReturn(true);
		when(asyncCookie.isHttpOnly()).thenReturn(true);

		Cookie cookie = new Cookie(asyncCookie);
		assertThat(cookie.getDomain()).isEqualTo(domain);
		assertThat(cookie.getPath()).isEqualTo(path);
		assertThat(cookie.getName()).isEqualTo(cookieName);
		assertThat(cookie.getValue()).isEqualTo(cookieValue);
		assertThat(cookie.getMaxAge()).isEqualTo(maxAge);
		assertThat(cookie.isSecure()).isTrue();
		assertThat(cookie.isHttpOnly()).isTrue();
		assertThat(cookie.getExpiryDate()).isCloseTo(new Date(), maxAge * 1000);

		// Must set expiry date to null
		when(asyncCookie.getMaxAge()).thenReturn(-1);
		cookie = new Cookie(asyncCookie);
		assertThat(cookie.getExpiryDate()).isNull();
	}
}
