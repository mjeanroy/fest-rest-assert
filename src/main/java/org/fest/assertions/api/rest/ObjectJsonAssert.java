package org.fest.assertions.api.rest;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.fest.assertions.api.AbstractAssert;
import org.fest.assertions.api.Assertions;
import org.fest.assertions.data.JsonEntry;
import org.fest.assertions.util.JsonComparator;
import org.fest.util.FilesException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.InvalidPathException;
import com.jayway.jsonpath.JsonPath;

@SuppressWarnings("unchecked")
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
	public ObjectJsonAssert containsPaths(String... paths) {
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

	/**
	 * Check if actual json is strictly equals to an expected json representation stored in the given file.
	 *
	 * @param file Expected json representation.
	 * @return {@code this} the assertion object.
	 */
	public ObjectJsonAssert isStrictlyEqualsTo(File file) {
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
				return isStrictlyEqualsTo(content);
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
	 * Check if actual json is strictly equals to an expected json representation accessible at given URL.
	 *
	 * @param url Expected json representation.
	 * @return {@code this} the assertion object.
	 */
	public ObjectJsonAssert isStrictlyEqualsTo(URL url) throws IOException {
		try {
			URI uri = url.toURI();
			return isStrictlyEqualsTo(uri);
		}
		catch (URISyntaxException ex) {
			String msg = String.format("Unable to read content of url:<%s>", url.getPath());
			throw new FilesException(msg, ex);
		}
	}

	/**
	 * Check if actual json is strictly equals to an expected json representation accessible at given URI.
	 *
	 * @param uri Expected json representation.
	 * @return {@code this} the assertion object.
	 */
	public ObjectJsonAssert isStrictlyEqualsTo(URI uri) throws IOException {
		File file = new File(uri);
		return isStrictlyEqualsTo(file);
	}

	/**
	 * Check if actual json is strictly equals to an expected object (automatically serialized to a json representation
	 * using a default {@link ObjectMapper}).
	 *
	 * @param object Expected json representation.
	 * @return {@code this} the assertion object.
	 */
	public ObjectJsonAssert isStrictlyEqualsTo(Object object) {
		return isStrictlyEqualsTo(object, new ObjectMapper());
	}

	/**
	 * Check if actual json is strictly equals to an expected object (automatically serialized to a json representation
	 * using given {@link ObjectMapper}).
	 *
	 * @param object Expected json representation.
	 * @return {@code this} the assertion object.
	 */
	public ObjectJsonAssert isStrictlyEqualsTo(Object object, ObjectMapper mapper) {
		try {
			String json = mapper.writeValueAsString(object);
			return isStrictlyEqualsTo(json);
		}
		catch (JsonProcessingException ex) {
			throw new AssertionError(ex.getMessage());
		}
	}

	/**
	 * Check if actual json is strictly equals to an expected json representation.
	 *
	 * @param json Expected json representation.
	 * @return {@code this} the assertion object.
	 */
	public ObjectJsonAssert isStrictlyEqualsTo(String json) {
		List<String> errors = JsonComparator.compareJson(actual, json);
		if (!errors.isEmpty()) {
			String msg = join(errors, ",\n");
			throw new AssertionError(msg);
		}
		return this;
	}

	/**
	 * Join a list of string with given delimiter.
	 *
	 * @param strings Strings to join.
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
