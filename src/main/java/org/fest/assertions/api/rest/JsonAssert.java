package org.fest.assertions.api.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.InvalidPathException;
import com.jayway.jsonpath.JsonPath;
import org.fest.assertions.api.AbstractAssert;
import org.fest.assertions.api.Assertions;
import org.fest.assertions.data.JsonEntry;
import org.fest.assertions.util.JsonComparator;
import org.fest.util.FilesException;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@SuppressWarnings("unchecked")
public class JsonAssert extends AbstractAssert<JsonAssert, String> {

	public JsonAssert(String actual) {
		super(actual.trim(), JsonAssert.class);
		if (!JsonComparator.isValid(actual)) {
			throw new AssertionError("Expect json to be valid");
		}
	}

	/**
	 * Check if json is an array.
	 *
	 * @return {@code this} the assertion object.
	 */
	public JsonAssert isArray() {
		if (!JsonComparator.isArray(actual)) {
			throw new AssertionError("Expect json to be an array");
		}
		return this;
	}

	/**
	 * Check if json is an array with expected size.
	 *
	 * @param size Expected size.
	 * @return {@code this} the assertion object.
	 */
	public JsonAssert isArrayWithSize(int size) {
		isArray();
		int actualSize = 0;
		try {
			Collection c = new ObjectMapper().readValue(actual, Collection.class);
			actualSize = c.size();
		}
		catch (Throwable ex) {
			// Should not happen because json is valid and is an array
		}

		if (actualSize != size) {
			String msg = String.format("Expect json to be an array with size <%s> but was <%s>", size, actualSize);
			throw new AssertionError(msg);
		}

		return this;
	}

	/** Check if json is an empty array. */
	public void isEmptyArray() {
		isArrayWithSize(0);
	}

	/**
	 * Check if json is an object.
	 *
	 * @return {@code this} the assertion object.
	 */

	public JsonAssert isObject() {
		if (!JsonComparator.isObject(actual)) {
			throw new AssertionError("Expect json to be an object");
		}
		return this;
	}

	/**
	 * Check if a path exist in json representation (support JSONPath specification).
	 *
	 * @param path Path to look for.
	 * @return {@code this} the assertion object.
	 */
	public JsonAssert contain(String path) {
		if (!hasKey(path)) {
			String msg = String.format("Expected path <%s> to be find", path);
			throw new AssertionError(msg);
		}
		return this;
	}

	/**
	 * Check if a list of paths exist in json representation (support JSONPath specification).
	 *
	 * @param paths List of path to look for.
	 * @return {@code this} the assertion object.
	 */
	public JsonAssert contains(String... paths) {
		List<String> errors = new ArrayList<String>(paths.length);
		for (String path : paths) {
			if (!hasKey(path)) {
				errors.add(path);
			}
		}

		if (!errors.isEmpty()) {
			String errs = join(errors, ", ");
			String msg = String.format("Expected keys <%s> to be find", errs);
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
	public JsonAssert containsEntry(JsonEntry entry) {
		String path = entry.key();
		Object value = entry.value();
		return contain(path, value);
	}

	/**
	 * Check if a list of entries exist in json representation (support JSONPath specification).
	 *
	 * @param entries Entries to look for.
	 * @return {@code this} the assertion object.
	 */
	public JsonAssert containsEntries(JsonEntry... entries) {
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
			List<String> paths = new ArrayList<String>(errors.size());
			List<String> expectedValues = new ArrayList<String>(errors.size());
			for (JsonEntry error : errors) {
				paths.add(error.key());

				String val = error.value() == null ? null : error.value().toString();
				expectedValues.add(val);
			}
			String msg = String.format("Expect following paths <%s> to be <%s>", join(paths, ", "), join(expectedValues, ", "));
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
	 * @param obj  Expected value.
	 * @return {@code this} the assertion object.
	 */
	public <T> JsonAssert contain(String path, T obj) {
		contain(path);
		T result = JsonPath.read(actual, path);
		Assertions.assertThat(result)
				.overridingErrorMessage("Expect path <%s> to be <%s> but was <%s>", path, obj, result)
				.isEqualTo(obj);
		return this;
	}

	/**
	 * Check if a path exist in json representation (support JSONPath specification) and is an array.
	 *
	 * @param path Path to look for.
	 * @return {@code this} the assertion object.
	 */
	public JsonAssert isArray(String path) {
		contain(path);
		try {
			Collection result = JsonPath.read(actual, path);
		}
		catch (Throwable e) {
			String msg = String.format("Expect <%s> to be an array", path);
			throw new AssertionError(msg);
		}
		return this;
	}

	/**
	 * Check if a path exist in json representation (support JSONPath specification) and is an object (i.e. not an array).
	 *
	 * @param path Path to look for.
	 * @return {@code this} the assertion object.
	 */
	public JsonAssert isObject(String path) {
		contain(path);

		boolean isObject = false;
		try {
			Collection result = JsonPath.read(actual, path);
		}
		catch (Throwable e) {
			// Expected case
			isObject = true;
		}

		if (!isObject) {
			String msg = String.format("Expect <%s> to be an object", path);
			throw new AssertionError(msg);
		}

		return this;
	}

	/**
	 * Check if a path exist in json representation (support JSONPath specification) and is an array with expected size.
	 *
	 * @param path Path to look for.
	 * @param size Expected size.
	 * @return {@code this} the assertion object.
	 */
	public JsonAssert isArrayWithSize(String path, int size) {
		isArray(path);

		int actualSize = 0;

		try {
			Collection result = JsonPath.read(actual, path);
			actualSize = result.size();
		}
		catch (Throwable e) {
			// Should not happen
		}

		if (actualSize != size) {
			String msg = String.format("Expect <%s> to be an array with size <%s> but was <%s>", path, size, actualSize);
			throw new AssertionError(msg);
		}

		return this;
	}

	/**
	 * Check if a path exist in json representation (support JSONPath specification) and is an empty array.
	 *
	 * @param path Path to look for.
	 * @return {@code this} the assertion object.
	 */
	public JsonAssert isEmptyArray(String path) {
		return isArrayWithSize(path, 0);
	}

	/**
	 * Check if a path exist in json representation (support JSONPath specification) and is null.
	 *
	 * @param path Path to look for.
	 * @return {@code this} the assertion object.
	 */
	public JsonAssert isNull(String path) {
		contain(path);
		Object result = JsonPath.read(actual, path);
		Assertions.assertThat(result)
				.overridingErrorMessage("Expect <%s> to be null", path)
				.isNull();
		return this;
	}

	/**
	 * Check if a path exist in json representation (support JSONPath specification) and is not null.
	 *
	 * @param path Path to look for.
	 * @return {@code this} the assertion object.
	 */
	public JsonAssert isNotNull(String path) {
		contain(path);
		Object result = JsonPath.read(actual, path);
		Assertions.assertThat(result)
				.overridingErrorMessage("Expect <%s> not to be null", path)
				.isNotNull();
		return this;
	}

	/**
	 * Check if a path exist in json representation (support JSONPath specification) and is a number.
	 *
	 * @param path Path to look for.
	 * @return {@code this} the assertion object.
	 */
	public JsonAssert isNumber(String path) {
		return isOfType(path, Number.class, "number");
	}

	/**
	 * Check if a path exist in json representation (support JSONPath specification) and is a string.
	 *
	 * @param path Path to look for.
	 * @return {@code this} the assertion object.
	 */
	public JsonAssert isString(String path) {
		return isOfType(path, String.class, "string");
	}

	/**
	 * Check if a path exist in json representation (support JSONPath specification) and is a boolean.
	 *
	 * @param path Path to look for.
	 * @return {@code this} the assertion object.
	 */
	public JsonAssert isBoolean(String path) {
		return isOfType(path, Boolean.class, "boolean");
	}

	/**
	 * Check if a path exist in json representation (support JSONPath specification) and is of expected type.
	 *
	 * @param path    Path to look for.
	 * @param klass   Expected type.
	 * @param typeStr Type displayed in error message.
	 * @return {@code this} the assertion object.
	 */
	private <T> JsonAssert isOfType(String path, Class<T> klass, String typeStr) {
		isNotNull(path);

		try {
			T obj = JsonPath.read(actual, path);
			if (!klass.isAssignableFrom(obj.getClass())) {
				throw new RuntimeException();
			}
		}
		catch (Throwable e) {
			String msg = String.format("Expect <%s> to be a %s", path, typeStr);
			throw new AssertionError(msg);
		}

		return this;
	}

	/**
	 * Check if a path exist in json representation (support JSONPath specification) and if value stored at expected path is equal to {@code true}.
	 *
	 * @param path Path to look for.
	 * @return {@code this} the assertion object.
	 */
	public JsonAssert isTrue(String path) {
		isBoolean(path);
		return contain(path, true);
	}

	/**
	 * Check if a path exist in json representation (support JSONPath specification) and if value stored at expected path is equal to {@code false}.
	 *
	 * @param path Path to look for.
	 * @return {@code this} the assertion object.
	 */
	public JsonAssert isFalse(String path) {
		isBoolean(path);
		return contain(path, false);
	}

	/**
	 * Check if a path exist in json representation (support JSONPath specification) and if value stored at expected path is equal to {@code zero}.
	 *
	 * @param path Path to look for.
	 * @return {@code this} the assertion object.
	 */
	public JsonAssert isZero(String path) {
		isNumber(path);
		return contain(path, 0);
	}

	/**
	 * Check if a path exist in json representation (support JSONPath specification) and if value stored at expected path is greater than an expected value.
	 *
	 * @param path  Path to look for.
	 * @param value Bound value.
	 * @return {@code this} the assertion object.
	 */
	public JsonAssert isGreaterThan(String path, int value) {
		isNumber(path);
		int result = JsonPath.read(actual, path);
		Assertions.assertThat(result)
				.overridingErrorMessage("Expect path <%s> to be greater than <%s> but was <%s>", path, value, result)
				.isGreaterThan(value);
		return this;
	}

	/**
	 * Check if a path exist in json representation (support JSONPath specification) and if value stored at expected path is greater than or equal to an expected value.
	 *
	 * @param path  Path to look for.
	 * @param value Bound value.
	 * @return {@code this} the assertion object.
	 */
	public JsonAssert isGreaterThanOrEqualTo(String path, int value) {
		isNumber(path);
		int result = JsonPath.read(actual, path);
		Assertions.assertThat(result)
				.overridingErrorMessage("Expect path <%s> to be greater than or equal to <%s> but was <%s>", path, value, result)
				.isGreaterThanOrEqualTo(value);
		return this;
	}

	/**
	 * Check if a path exist in json representation (support JSONPath specification) and if value stored at expected path is less than an expected value.
	 *
	 * @param path  Path to look for.
	 * @param value Bound value.
	 * @return {@code this} the assertion object.
	 */
	public JsonAssert isLessThan(String path, int value) {
		isNumber(path);
		int result = JsonPath.read(actual, path);
		Assertions.assertThat(result)
				.overridingErrorMessage("Expect path <%s> to be less than <%s> but was <%s>", path, value, result)
				.isLessThan(value);
		return this;
	}

	/**
	 * Check if a path exist in json representation (support JSONPath specification) and if value stored at expected path is less than or equal to an expected value.
	 *
	 * @param path  Path to look for.
	 * @param value Bound value.
	 * @return {@code this} the assertion object.
	 */
	public JsonAssert isLessThanOrEqualTo(String path, int value) {
		isNumber(path);
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
	public JsonAssert isPositive(String path) {
		return isGreaterThan(path, 0);
	}

	/**
	 * Check if a path exist in json representation (support JSONPath specification) and if value stored at expected path is negative (a.k.a lower than zero).
	 *
	 * @param path Path to look for.
	 * @return {@code this} the assertion object.
	 */
	public JsonAssert isNegative(String path) {
		return isLessThan(path, 0);
	}

	/**
	 * Check if actual json is strictly equals to an expected json representation accessible at given URL.
	 *
	 * @param url            Expected json representation.
	 * @param ignoringFields Fields to ignore during comparison.
	 * @return {@code this} the assertion object.
	 */
	public JsonAssert isStrictlyEqualsToIgnoringFields(URL url, List<String> ignoringFields) throws IOException {
		try {
			URI uri = url.toURI();
			return isStrictlyEqualsToIgnoringFields(uri, ignoringFields);
		}
		catch (URISyntaxException ex) {
			String msg = String.format("Unable to read content of url:<%s>", url.getPath());
			throw new FilesException(msg, ex);
		}
	}

	/**
	 * Check if actual json is strictly equals to an expected json representation accessible at given URI.
	 *
	 * @param uri            Expected json representation.
	 * @param ignoringFields Fields to ignore during comparison.
	 * @return {@code this} the assertion object.
	 */
	public JsonAssert isStrictlyEqualsToIgnoringFields(URI uri, List<String> ignoringFields) throws IOException {
		File file = new File(uri);
		return isStrictlyEqualsToIgnoringFields(file, ignoringFields);
	}

	/**
	 * Check if actual json is strictly equals to an expected json representation stored in the given file.
	 *
	 * @param file           Expected json representation.
	 * @param ignoringFields Fields to ignore during comparison.
	 * @return {@code this} the assertion object.
	 */
	public JsonAssert isStrictlyEqualsToIgnoringFields(File file, List<String> ignoringFields) {
		BufferedReader br;

		try {
			br = new BufferedReader(new FileReader(file));
			try {
				StringBuilder sb = new StringBuilder();
				String line = br.readLine();

				while (line != null) {
					sb.append(line);
					line = br.readLine();
				}

				String content = sb.toString();
				return isStrictlyEqualsToIgnoringFields(content, ignoringFields);
			}
			catch (FileNotFoundException ex) {
				String msg = String.format("Unable to get content of file:<%s>", file.getPath());
				throw new FilesException(msg, ex);
			}
			catch (IOException ex) {
				String msg = String.format("Unable to get content of file:<%s>", file.getPath());
				throw new FilesException(msg, ex);
			}
			finally {
				try {
					br.close();
				}
				catch (IOException ex) {
				}
			}
		}
		catch (IOException ex) {
			String msg = String.format("Unable to get content of file:<%s>", file.getPath());
			throw new FilesException(msg, ex);
		}
	}

	/**
	 * Check if actual json is strictly equals to an expected object (automatically serialized to a json representation
	 * using a default {@link ObjectMapper}).
	 *
	 * @param object         Expected json representation.
	 * @param ignoringFields Fields to ignore during comparison.
	 * @return {@code this} the assertion object.
	 */
	public JsonAssert isStrictlyEqualsToIgnoringFields(Object object, List<String> ignoringFields) {
		return isStrictlyEqualsToIgnoringFields(object, new ObjectMapper(), ignoringFields);
	}

	/**
	 * Check if actual json is strictly equals to an expected object (automatically serialized to a json representation
	 * using given {@link ObjectMapper}).
	 *
	 * @param object         Expected json representation.
	 * @param ignoringFields Fields to ignore during comparison.
	 * @return {@code this} the assertion object.
	 */
	public JsonAssert isStrictlyEqualsToIgnoringFields(Object object, ObjectMapper mapper, List<String> ignoringFields) {
		try {
			String json = mapper.writeValueAsString(object);
			return isStrictlyEqualsToIgnoringFields(json, ignoringFields);
		}
		catch (JsonProcessingException ex) {
			throw new AssertionError(ex.getMessage());
		}
	}

	/**
	 * Check if actual json is strictly equals to an expected json representation.
	 *
	 * @param json           Expected json representation.
	 * @param ignoringFields Fields to ignore during comparison.
	 * @return {@code this} the assertion object.
	 */
	public JsonAssert isStrictlyEqualsToIgnoringFields(String json, List<String> ignoringFields) {
		List<String> errors = JsonComparator.compareJson(actual, json, ignoringFields);
		if (!errors.isEmpty()) {
			String msg = join(errors, ",\n");
			throw new AssertionError(msg);
		}
		return this;
	}

	/**
	 * Check if actual json is strictly equals to an expected json representation accessible at given URL.
	 *
	 * @param url Expected json representation.
	 * @return {@code this} the assertion object.
	 */
	public JsonAssert isStrictlyEqualsTo(URL url) throws IOException {
		return isStrictlyEqualsToIgnoringFields(url, Collections.EMPTY_LIST);
	}

	/**
	 * Check if actual json is strictly equals to an expected json representation accessible at given URI.
	 *
	 * @param uri Expected json representation.
	 * @return {@code this} the assertion object.
	 */
	public JsonAssert isStrictlyEqualsTo(URI uri) throws IOException {
		return isStrictlyEqualsToIgnoringFields(uri, Collections.EMPTY_LIST);
	}

	/**
	 * Check if actual json is strictly equals to an expected json representation stored in the given file.
	 *
	 * @param file Expected json representation.
	 * @return {@code this} the assertion object.
	 */
	public JsonAssert isStrictlyEqualsTo(File file) {
		return isStrictlyEqualsToIgnoringFields(file, Collections.EMPTY_LIST);
	}

	/**
	 * Check if actual json is strictly equals to an expected object (automatically serialized to a json representation
	 * using a default {@link ObjectMapper}).
	 *
	 * @param object Expected json representation.
	 * @return {@code this} the assertion object.
	 */
	public JsonAssert isStrictlyEqualsTo(Object object) {
		return isStrictlyEqualsToIgnoringFields(object, Collections.EMPTY_LIST);
	}

	/**
	 * Check if actual json is strictly equals to an expected object (automatically serialized to a json representation
	 * using given {@link ObjectMapper}).
	 *
	 * @param object Expected json representation.
	 * @return {@code this} the assertion object.
	 */
	public JsonAssert isStrictlyEqualsTo(Object object, ObjectMapper mapper) {
		return isStrictlyEqualsToIgnoringFields(object, mapper, Collections.EMPTY_LIST);
	}

	/**
	 * Check if actual json is strictly equals to an expected json representation.
	 *
	 * @param json Expected json representation.
	 * @return {@code this} the assertion object.
	 */
	public JsonAssert isStrictlyEqualsTo(String json) {
		return isStrictlyEqualsToIgnoringFields(json, Collections.EMPTY_LIST);
	}

	/**
	 * Join a list of string with given delimiter.
	 *
	 * @param strings   Strings to join.
	 * @param delimiter Delimiter.
	 * @return Formatted string.
	 */
	private String join(List<String> strings, String delimiter) {
		String msg = "";
		for (String str : strings) {
			msg += str;
			msg += delimiter;
		}
		return msg.substring(0, msg.length() - delimiter.length());
	}

	private String formatPath(String path) {
		if (!path.startsWith("$.")) {
			return "$." + path;
		}
		return path;
	}
}
