package org.fest.assertions.api.http;

import org.fest.assertions.api.AbstractAssert;
import org.fest.assertions.api.Assertions;
import org.fest.assertions.util.Cookie;

public class CookieAssert extends AbstractAssert<CookieAssert, Cookie> {

	public CookieAssert(Cookie actual) {
		super(actual, CookieAssert.class);
	}

	/**
	 * Check if cookie has a name equal to an expected value.
	 *
	 * @param name Expected name of cookie.
	 * @return {@code this} the assertion object.
	 */
	public CookieAssert isNameEqualTo(String name) {
		isNotNull();
		Assertions.assertThat(actual.getName())
				.overridingErrorMessage("Expected name of cookie to be <%s> but was <%s>", name, actual.getName())
				.isEqualTo(name);
		return this;
	}

	/**
	 * Check if cookie has a value equal to an expected value.
	 *
	 * @param value Expected value of cookie.
	 * @return {@code this} the assertion object.
	 */
	public CookieAssert isValueEqualTo(String value) {
		isNotNull();
		Assertions.assertThat(actual.getValue())
				.overridingErrorMessage("Expected value of cookie to be <%s> but was <%s>", value, actual.getValue())
				.isEqualTo(value);
		return this;
	}

	/**
	 * Check if cookie has a domain equal to an expected value.
	 *
	 * @param domain Expected domain of cookie.
	 * @return {@code this} the assertion object.
	 */
	public CookieAssert isDomainEqualTo(String domain) {
		isNotNull();
		Assertions.assertThat(actual.getDomain())
				.overridingErrorMessage("Expected domain of cookie to be <%s> but was <%s>", domain, actual.getDomain())
				.isEqualTo(domain);
		return this;
	}

	public CookieAssert hasPath(String path) {
		isNotNull();
		Assertions.assertThat(actual.getPath())
				.overridingErrorMessage("Expected path of cookie to be <%s> but was <%s>", path, actual.getPath())
				.isEqualTo(path);
		return this;
	}

	public CookieAssert hasMaxAge(int maxAge) {
		isNotNull();
		Assertions.assertThat(actual.getMaxAge())
				.overridingErrorMessage("Expected max age of cookie to be <%s> but was <%s>", maxAge, actual.getMaxAge())
				.isEqualTo(maxAge);
		return this;
	}

	public CookieAssert isPersistent() {
		isNotNull();
		Assertions.assertThat(actual.getMaxAge())
				.overridingErrorMessage("Expected cookie to be persistent but max-age is equal to <%s>", actual.getMaxAge())
				.isGreaterThan(0);
		return this;
	}

	public CookieAssert isNotPersistent() {
		isNotNull();
		Assertions.assertThat(actual.getMaxAge())
				.overridingErrorMessage("Expected cookie not to be persistent but max-age is equal to <%s>", actual.getMaxAge())
				.isLessThan(0);
		return this;
	}

	public CookieAssert willBeDeleted() {
		isNotNull();
		Assertions.assertThat(actual.getMaxAge())
				.overridingErrorMessage("Expected cookie to be deleted but max-age is equal to <%s>", actual.getMaxAge())
				.isEqualTo(0);
		return this;
	}

	public CookieAssert isSecure() {
		isNotNull();
		Assertions.assertThat(actual.isSecure())
				.overridingErrorMessage("Expected cookie to be secure")
				.isTrue();
		return this;
	}

	public CookieAssert isNotSecure() {
		isNotNull();
		Assertions.assertThat(actual.isSecure())
				.overridingErrorMessage("Expected cookie not to be secure")
				.isFalse();
		return this;
	}

	public CookieAssert isHttpOnly() {
		isNotNull();
		Assertions.assertThat(actual.isHttpOnly())
				.overridingErrorMessage("Expected cookie to be http only")
				.isTrue();
		return this;
	}

	public CookieAssert isNotHttpOnly() {
		isNotNull();
		Assertions.assertThat(actual.isHttpOnly())
				.overridingErrorMessage("Expected cookie not to be http only")
				.isFalse();
		return this;
	}
}
