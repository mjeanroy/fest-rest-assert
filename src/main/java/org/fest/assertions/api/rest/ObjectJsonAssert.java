package org.fest.assertions.api.rest;

import org.fest.assertions.api.AbstractAssert;
import org.fest.assertions.api.Assertions;

import com.jayway.jsonpath.InvalidPathException;
import com.jayway.jsonpath.JsonPath;

public class ObjectJsonAssert extends AbstractAssert<ObjectJsonAssert, String> {

	public ObjectJsonAssert(String actual) {
		super(actual, ObjectJsonAssert.class);
	}

	/**
	 * Check if a path exist in json representation (support JSONPath specification).
	 *
	 * @param path Path to look for.
	 * @return {@code this} the assertion object.
	 */
	public ObjectJsonAssert hasPath(String path) {
		try {
			JsonPath.read(actual, formatPath(path));
		}
		catch (InvalidPathException ex) {
			throw new AssertionError("Expected path <" + path + "> to be find in json <" + actual + ">");
		}
		return this;
	}

	/**
	 * Check if a path exist in json representation (support JSONPath specification) and if value stored at expected path is equal to an expected value.
	 *
	 * @param path Path to look for.
	 * @param obj Expected value.
	 * @return {@code this} the assertion object.
	 */
	public <T> ObjectJsonAssert isPathEqualTo(String path, T obj) {
		hasPath(path);
		T result = JsonPath.read(actual, path);
		Assertions.assertThat(result)
				.overridingErrorMessage("Expect path <%s> to be <%s> but was <%s>", path, obj, result)
				.isEqualTo(obj);
		return this;
	}

	/**
	 * Check if a path exist in json representation (support JSONPath specification) and if value stored at expected path is equal to {@code true}.
	 *
	 * @param path Path to look for.
	 * @return {@code this} the assertion object.
	 */
	public ObjectJsonAssert isTrue(String path) {
		return isPathEqualTo(path, true);
	}

	/**
	 * Check if a path exist in json representation (support JSONPath specification) and if value stored at expected path is equal to {@code false}.
	 *
	 * @param path Path to look for.
	 * @return {@code this} the assertion object.
	 */
	public ObjectJsonAssert isFalse(String path) {
		return isPathEqualTo(path, false);
	}

	/**
	 * Check if a path exist in json representation (support JSONPath specification) and if value stored at expected path is equal to {@code zero}.
	 *
	 * @param path Path to look for.
	 * @return {@code this} the assertion object.
	 */
	public ObjectJsonAssert isZero(String path) {
		return isPathEqualTo(path, 0);
	}

	/**
	 * Check if a path exist in json representation (support JSONPath specification) and if value stored at expected path is greater than an expected value.
	 *
	 * @param path Path to look for.
	 * @param value Bound value.
	 * @return {@code this} the assertion object.
	 */
	public ObjectJsonAssert isGreaterThan(String path, int value) {
		hasPath(path);
		int result = JsonPath.read(actual, path);
		Assertions.assertThat(result)
				.overridingErrorMessage("Expect path <%s> to be greater than <%s> but was <%s>", path, value, result)
				.isGreaterThan(value);
		return this;
	}

	/**
	 * Check if a path exist in json representation (support JSONPath specification) and if value stored at expected path is greater than or equal to an expected value.
	 *
	 * @param path Path to look for.
	 * @param value Bound value.
	 * @return {@code this} the assertion object.
	 */
	public ObjectJsonAssert isGreaterThanOrEqualTo(String path, int value) {
		hasPath(path);
		int result = JsonPath.read(actual, path);
		Assertions.assertThat(result)
				.overridingErrorMessage("Expect path <%s> to be greater than or equal to <%s> but was <%s>", path, value, result)
				.isGreaterThanOrEqualTo(value);
		return this;
	}

	/**
	 * Check if a path exist in json representation (support JSONPath specification) and if value stored at expected path is less than an expected value.
	 *
	 * @param path Path to look for.
	 * @param value Bound value.
	 * @return {@code this} the assertion object.
	 */
	public ObjectJsonAssert isLessThan(String path, int value) {
		hasPath(path);
		int result = JsonPath.read(actual, path);
		Assertions.assertThat(result)
				.overridingErrorMessage("Expect path <%s> to be less than <%s> but was <%s>", path, value, result)
				.isLessThan(value);
		return this;
	}

	/**
	 * Check if a path exist in json representation (support JSONPath specification) and if value stored at expected path is less than or equal to an expected value.
	 *
	 * @param path Path to look for.
	 * @param value Bound value.
	 * @return {@code this} the assertion object.
	 */
	public ObjectJsonAssert isLessThanOrEqualTo(String path, int value) {
		hasPath(path);
		int result = JsonPath.read(actual, path);
		Assertions.assertThat(result)
				.overridingErrorMessage("Expect path <%s> to be less than or equal to <%s> but was <%s>", path, value, result)
				.isLessThanOrEqualTo(value);
		return this;
	}

	/**
	 * Check if a path exist in json representation (support JSONPath specification) and if value stored at expected path is positive (a.k.a greater than zero).
	 *
	 * @param path Path to look for.
	 * @return {@code this} the assertion object.
	 */
	public ObjectJsonAssert isPositive(String path) {
		return isGreaterThan(path, 0);
	}

	/**
	 * Check if a path exist in json representation (support JSONPath specification) and if value stored at expected path is negative (a.k.a lower than zero).
	 *
	 * @param path Path to look for.
	 * @return {@code this} the assertion object.
	 */
	public ObjectJsonAssert isNegative(String path) {
		return isLessThan(path, 0);
	}

	private String formatPath(String path) {
		if (!path.startsWith("$.")) {
			return "$." + path;
		}
		return path;
	}
}
