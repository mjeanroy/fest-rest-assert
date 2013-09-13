package org.fest.assertions.api.rest;

import java.util.ArrayList;
import java.util.List;

import org.fest.assertions.api.AbstractAssert;
import org.fest.assertions.api.Assertions;
import org.fest.assertions.data.JsonEntry;

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
		if (!hasKey(path)) {
			throw new AssertionError("Expected path <" + path + "> to be find in json <" + actual + ">");
		}
		return this;
	}

	/**
	 * Check if a list of paths exist in json representation (support JSONPath specification).
	 *
	 * @param paths List of path to look for.
	 * @return {@code this} the assertion object.
	 */
	public ObjectJsonAssert containsPaths(String... paths) {
		List<String> errors = new ArrayList<String>(paths.length);
		for (String path : paths) {
			if (!hasKey(path)) {
				errors.add(path);
			}
		}

		if (!errors.isEmpty()) {
			String msg = "Expected keys <";
			for (String error : errors) {
				msg += error + ", ";
			}
			msg = msg.substring(0, msg.length() - 2);
			msg += "> to be find in json <" + actual + ">";
			throw new AssertionError(msg);
		}

		return this;
	}

	/**
	 * Check if an entry exist in json representation (support JSONPath specification).
	 *
	 * @param entry Entry to look for.
	 * @return {@code this} the assertion object.
	 */
	public ObjectJsonAssert containsEntry(JsonEntry entry) {
		String path = entry.key();
		Object value = entry.value();
		return isPathEqualTo(path, value);
	}

	/**
	 * Check if a list of entries exist in json representation (support JSONPath specification).
	 *
	 * @param entries Entries to look for.
	 * @return {@code this} the assertion object.
	 */
	public ObjectJsonAssert containsEntries(JsonEntry... entries) {
		List<JsonEntry> errors = new ArrayList<JsonEntry>(entries.length);
		for (JsonEntry entry : entries) {
			try {
				containsEntry(entry);
			}
			catch (AssertionError assertionError) {
				errors.add(entry);
			}
		}

		if (!errors.isEmpty()) {
			String paths = "";
			String expectedValues = "";
			for (JsonEntry error : errors) {
				paths += error.key() + ", ";
				expectedValues += error.value() + ", ";
			}
			paths = paths.substring(0, paths.length() - 2);
			expectedValues = expectedValues.substring(0, expectedValues.length() - 2);

			String msg = "Expect following paths <" + paths + "> to be <" + expectedValues + "> in json <" + actual + ">";
			throw new AssertionError(msg);
		}

		return this;
	}

	/**
	 * Check if a key/path exist in json representation (support JSONPath specification).
	 *
	 * @param path Path to look for.
	 * @return {@code this} the assertion object.
	 */
	private boolean hasKey(String path) {
		try {
			JsonPath.read(actual, formatPath(path));
			return true;
		}
		catch (InvalidPathException ex) {
			return false;
		}
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
