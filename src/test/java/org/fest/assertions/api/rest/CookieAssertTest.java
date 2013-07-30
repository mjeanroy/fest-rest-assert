package org.fest.assertions.api.rest;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.fest.assertions.api.Assertions.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.fest.assertions.util.Cookie;
import org.junit.Before;
import org.junit.Test;

public class CookieAssertTest {

	private Cookie cookie;
	private CookieAssert assertion;

	@Before
	public void setUp() {
		cookie = mock(Cookie.class);
		assertion = new CookieAssert(cookie);
	}

	@Test
	public void test_isNameEqualTo() {
		when(cookie.getName()).thenReturn("foo");
		assertion.isNameEqualTo("foo");

		try {
			when(cookie.getName()).thenReturn("bar");
			assertion.isNameEqualTo("foo");
			fail("Expected AssertionError to be thrown");
		}
		catch (AssertionError error) {
			assertThat(error.getMessage()).isEqualTo("Expected name of cookie to be <foo> but was <bar>");
		}
	}

	@Test
	public void test_isValueEqualTo() {
		when(cookie.getValue()).thenReturn("foo");
		assertion.isValueEqualTo("foo");

		try {
			when(cookie.getValue()).thenReturn("bar");
			assertion.isValueEqualTo("foo");
			fail("Expected AssertionError to be thrown");
		}
		catch (AssertionError error) {
			assertThat(error.getMessage()).isEqualTo("Expected value of cookie to be <foo> but was <bar>");
		}
	}

	@Test
	public void test_isDomainEqualTo() {
		when(cookie.getDomain()).thenReturn("foo");
		assertion.isDomainEqualTo("foo");

		try {
			when(cookie.getDomain()).thenReturn("bar");
			assertion.isDomainEqualTo("foo");
			fail("Expected AssertionError to be thrown");
		}
		catch (AssertionError error) {
			assertThat(error.getMessage()).isEqualTo("Expected domain of cookie to be <foo> but was <bar>");
		}
	}

	@Test
	public void test_isPathEqualTo() {
		when(cookie.getPath()).thenReturn("foo");
		assertion.isPathEqualTo("foo");

		try {
			when(cookie.getPath()).thenReturn("bar");
			assertion.isPathEqualTo("foo");
			fail("Expected AssertionError to be thrown");
		}
		catch (AssertionError error) {
			assertThat(error.getMessage()).isEqualTo("Expected path of cookie to be <foo> but was <bar>");
		}
	}

	@Test
	public void test_isSecure() {
		when(cookie.isSecure()).thenReturn(true);
		assertion.isSecure();

		try {
			when(cookie.isSecure()).thenReturn(false);
			assertion.isSecure();
			fail("Expected AssertionError to be thrown");
		}
		catch (AssertionError error) {
			assertThat(error.getMessage()).isEqualTo("Expected cookie to be secure");
		}
	}

	@Test
	public void test_isNotSecure() {
		when(cookie.isSecure()).thenReturn(false);
		assertion.isNotSecure();

		try {
			when(cookie.isSecure()).thenReturn(true);
			assertion.isNotSecure();
			fail("Expected AssertionError to be thrown");
		}
		catch (AssertionError error) {
			assertThat(error.getMessage()).isEqualTo("Expected cookie not to be secure");
		}
	}

	@Test
	public void test_isHttpOnly() {
		when(cookie.isHttpOnly()).thenReturn(true);
		assertion.isHttpOnly();

		try {
			when(cookie.isHttpOnly()).thenReturn(false);
			assertion.isHttpOnly();
			fail("Expected AssertionError to be thrown");
		}
		catch (AssertionError error) {
			assertThat(error.getMessage()).isEqualTo("Expected cookie to be rest only");
		}
	}

	@Test
	public void test_isNotHttpOnly() {
		when(cookie.isHttpOnly()).thenReturn(false);
		assertion.isNotHttpOnly();

		try {
			when(cookie.isHttpOnly()).thenReturn(true);
			assertion.isNotHttpOnly();
			fail("Expected AssertionError to be thrown");
		}
		catch (AssertionError error) {
			assertThat(error.getMessage()).isEqualTo("Expected cookie not to be rest only");
		}
	}
}
