package org.fest.assertions.api.http;

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
}
