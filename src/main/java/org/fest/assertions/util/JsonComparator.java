package org.fest.assertions.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@SuppressWarnings("unchecked")
public class JsonComparator {

	private JsonComparator() {
	}

	/**
	 * Compare two json representation.
	 *
	 * @param json Json to check.
	 * @param expected Expected json.
	 * @return List of errors.
	 */
	public static List<String> compareJson(String json, String expected) {
		try {
			ObjectMapper mapper = new ObjectMapper();
			Map<String, Object> actualJson = mapper.readValue(json, Map.class);
			Map<String, Object> expectedJson = mapper.readValue(expected, Map.class);
			return compareMaps(actualJson, expectedJson);
		}
		catch (JsonMappingException ex) {
			throw new AssertionError(ex.getMessage());
		}
		catch (JsonParseException ex) {
			throw new AssertionError(ex.getMessage());
		}
		catch (IOException ex) {
			throw new AssertionError(ex.getMessage());
		}
	}

	/**
	 * Compare two maps obtained from two json representation.
	 *
	 * @param map1 First map.
	 * @param map2 Expected map.
	 * @return List of errors during comparison.
	 */
	private static List<String> compareMaps(Map<String, Object> map1, Map<String, Object> map2) {
		return compareMaps(map1, map2, "", new ArrayList<String>(50));
	}

	/**
	 * Compare two maps obtained from two json representation and append new errors to the list.
	 *
	 * @param map1 First map.
	 * @param map2 Expected map.
	 * @param currentKey Current key (a.k.a context) in json representation (for nested object).
	 * @param currentErrors Previously founded errors.
	 * @return List of errors during comparison.
	 */
	private static List<String> compareMaps(Map<String, Object> map1, Map<String, Object> map2, String currentKey, List<String> currentErrors) {
		List<String> errors = new ArrayList<String>();

		// Check entries of first map
		Set<Map.Entry<String, Object>> entries = map1.entrySet();
		for (Map.Entry<String, Object> entry : entries) {
			errors.addAll(compareMapEntry(map1, map2, entry.getKey(), currentKey));
			if (map2.containsKey(entry.getKey())) {
				map2.remove(entry.getKey());
			}
		}

		// Check entries of second map
		Set<Map.Entry<String, Object>> entries2 = map2.entrySet();
		for (Map.Entry<String, Object> entry : entries2) {
			errors.addAll(compareMapEntry(map1, map2, entry.getKey(), currentKey));
		}

		currentErrors.addAll(errors);
		return currentErrors;
	}

	/**
	 * Compare an entry in both map.
	 *
	 * @param map1 First map.
	 * @param map2 Second map.
	 * @param key Key to compare.
	 * @param currentKey Current context in json representation.
	 * @return Errors found during comparison.
	 */
	private static List<String> compareMapEntry(Map<String, Object> map1, Map<String, Object> map2, String key, String currentKey) {
		List<String> errors = new ArrayList<String>();

		// First map does not contain expected key
		if (!map1.containsKey(key)) {
			String msg = String.format("Expect json to contain <%s>", formatKeyName(currentKey, key));
			errors.add(msg);
			return errors;
		}

		// Second map does not contain a key (i.e. first map contains more keys than expected)
		if (!map2.containsKey(key)) {
			String msg = String.format("Key <%s> was found but not expected", formatKeyName(currentKey, key));
			errors.add(msg);
			return errors;
		}

		// We can compare by value
		Object v1 = map1.get(key);
		Object v2 = map2.get(key);

		List<String> foundErrors = compareValue(v1, v2, currentKey, key);
		errors.addAll(foundErrors);
		return errors;
	}

	/**
	 * Compare two objects by value.
	 *
	 * @param v1 First object to compare.
	 * @param v2 Second object to compare.
	 * @param previousKey Current context in json representation.
	 * @param key Current key.
	 * @return List of found errors.
	 */
	private static List<String> compareValue(Object v1, Object v2, String previousKey, String key) {
		List<String> errors = new ArrayList<String>();

		if (v1 == v2) {
			// Map entry are the sames (both null, or same instance)
			return errors;
		}

		// Error cases

		// Check for null differences
		if (v1 == null && v2 != null) {
			String msg = String.format("Key <%s> was null but expected value was <%s>", formatKeyName(previousKey, key), v2.toString());
			errors.add(msg);
			return errors;
		}
		if (v1 != null && v2 == null) {
			String msg = String.format("Key <%s> was expected to be null but found value was <%s>", formatKeyName(previousKey, key), v1.toString());
			errors.add(msg);
			return errors;
		}

		// Check for type difference
		if (v1.getClass() != v2.getClass()) {
			String msg = String.format("Expect type <%s> but was <%s> for key <%s>", v2.getClass().getSimpleName(), v1.getClass().getSimpleName(), formatKeyName(previousKey, key));
			errors.add(msg);
			return errors;
		}

		// Check values
		if (v1 instanceof Map) {
			// Recursive call to check both maps
			errors = compareMaps((Map<String, Object>) v1, (Map<String, Object>) v2, key, errors);
		}
		else if (v1 instanceof Collection) {
			// Need to check each item of collection
			errors = checkCollections((Collection) v1, (Collection) v2, previousKey, key);
		}
		else if (!v1.equals(v2)) {
			// Values are different
			String msg = String.format("Expect <%s> to be <%s> but was <%s>", formatKeyName(previousKey, key), v1, v2);
			errors.add(msg);
		}

		return errors;
	}

	/**
	 * Compare two collections of Object.
	 *
	 * @param previousKey Current context in json representation.
	 * @param key Current key.
	 * @param c1 First collection.
	 * @param c2 Second collection.
	 * @return List of found errors.
	 */
	private static List<String> checkCollections(Collection c1, Collection c2, String previousKey, String key) {
		List<String> errors = new ArrayList<String>();

		int size1 = c1.size();
		int size2 = c2.size();

		// First check size of both collections
		if (size1 != size2) {
			String msg = String.format("Expect size of array <%s> to be <%s> but was <%s>", formatKeyName(previousKey, key), size2, size1);
			errors.add(msg);
			return errors;
		}

		// Size are equals, check each items of collections
		Iterator<Object> i1 = c1.iterator();
		Iterator<Object> i2 = c2.iterator();
		for (int i = 0; i < size1; ++i) {
			Object o1 = i1.next();
			Object o2 = i2.next();
			List<String> objectErrors = compareValue(o1, o2, previousKey, key + "[" + i + "]");
			errors.addAll(objectErrors);
		}

		return errors;
	}

	private static String formatKeyName(String previous, String key) {
		return previous == null || previous.isEmpty() ? key : previous + "." + key;
	}
}
