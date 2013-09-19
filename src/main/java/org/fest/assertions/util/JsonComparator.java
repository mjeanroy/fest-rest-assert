package org.fest.assertions.util;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

@SuppressWarnings("unchecked")
public class JsonComparator {

	private JsonComparator() {
	}

	/**
	 * Compare two json representation.
	 *
	 * @param json     Json to check.
	 * @param expected Expected json.
	 * @return List of errors.
	 */
	public static List<String> compareJson(String json, String expected) {
		String json1 = json.trim();
		String json2 = expected.trim();

		boolean json1IsArray = isArray(json1);
		boolean json2IsArray = isArray(json2);

		if (json1IsArray != json2IsArray) {
			String typeFound = "Object";
			String typeExpected = "Array";
			if (!json1IsArray) {
				typeFound = "Object";
				typeExpected = "Array";
			}

			String msg = String.format("Expect json to be <%s> but was <%s>", typeExpected, typeFound);
			List<String> errors = new ArrayList<String>();
			errors.add(msg);
			return errors;
		}

		try {
			return json1IsArray ?
					compareJsonArrays(json1, json2) :
					compareJsonMap(json1, json2);
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
	 * Check if a json is a valid representation.
	 *
	 * @param json Json to check.
	 * @return True if json is valid, false otherwise.
	 */
	public static boolean isValid(String json) {
		try {
			new ObjectMapper().readValue(json, Object.class);
			return true;
		}
		catch (Throwable ex) {
			return false;
		}
	}

	/**
	 * Check if json is an array.
	 *
	 * @param json Json to check.
	 * @return True if json is an array, false otherwise.
	 */
	public static boolean isArray(String json) {
		char char0Json1 = json.charAt(0);
		return char0Json1 == '[';
	}

	/**
	 * Check if json is an object.
	 *
	 * @param json Json to check.
	 * @return True if json is an object, false otherwise.
	 */
	public static boolean isObject(String json) {
		char char0Json1 = json.charAt(0);
		return char0Json1 == '{';
	}

	/**
	 * Compare two json objects.
	 *
	 * @param json1 First json.
	 * @param json2 Second json.
	 * @return List of found errors.
	 * @throws IOException
	 */
	private static List<String> compareJsonMap(String json1, String json2) throws IOException {
		TypeReference<HashMap<String, Object>> typeRef = new TypeReference<HashMap<String, Object>>() {
		};

		ObjectMapper mapper = new ObjectMapper();
		HashMap<String, Object> actualJson = mapper.readValue(json1, typeRef);
		HashMap<String, Object> expectedJson = mapper.readValue(json2, typeRef);
		return compareMaps(actualJson, expectedJson);
	}

	/**
	 * Compare two json (arrays).
	 *
	 * @param json1 First json.
	 * @param json2 Second json.
	 * @return List of found errors.
	 * @throws IOException
	 */
	private static List<String> compareJsonArrays(String json1, String json2) throws IOException {
		boolean json1IsArrayOfObjects = isArrayOfObjects(json1);
		boolean json2IsArrayOfObjects = isArrayOfObjects(json2);

		if (json1IsArrayOfObjects != json2IsArrayOfObjects) {
			String msg = json1IsArrayOfObjects ?
					"Expect json not to be an array of objects" :
					"Expect json to be an array of objects";

			List<String> errors = new ArrayList<String>();
			errors.add(msg);
			return errors;
		}

		return json1IsArrayOfObjects ?
				compareJsonArraysOfObject(json1, json2) :
				compareJsonArraysOfSimpleValues(json1, json2);
	}

	/**
	 * Check if given json is an array of objects or an array of simple objects (number, string, boolean etc.).
	 *
	 * @param json Json to check.
	 * @return True if json is an array of objects, false otherwise.
	 */
	private static boolean isArrayOfObjects(String json) {
		for (int i = 1; i < json.length(); i++) {
			char character = json.charAt(i);
			if (!Character.isWhitespace(character)) {
				return character == '{';
			}
		}
		return false;
	}

	private static List<String> compareJsonArraysOfSimpleValues(String json1, String json2) throws IOException {
		TypeReference<ArrayList<Object>> typeRef = new TypeReference<ArrayList<Object>>() {
		};

		ObjectMapper mapper = new ObjectMapper();
		ArrayList<HashMap<String, Object>> actualJson = mapper.readValue(json1, typeRef);
		ArrayList<HashMap<String, Object>> expectedJson = mapper.readValue(json2, typeRef);
		return compareCollections(actualJson, expectedJson);
	}

	private static List<String> compareJsonArraysOfObject(String json1, String json2) throws IOException {
		TypeReference<ArrayList<HashMap<String, Object>>> typeRef = new TypeReference<ArrayList<HashMap<String, Object>>>() {
		};

		ObjectMapper mapper = new ObjectMapper();
		ArrayList<HashMap<String, Object>> actualJson = mapper.readValue(json1, typeRef);
		ArrayList<HashMap<String, Object>> expectedJson = mapper.readValue(json2, typeRef);
		return compareCollections(actualJson, expectedJson);
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
	 * @param map1          First map.
	 * @param map2          Expected map.
	 * @param currentKey    Current key (a.k.a context) in json representation (for nested object).
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
	 * @param map1       First map.
	 * @param map2       Second map.
	 * @param key        Key to compare.
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
	 * @param v1          First object to compare.
	 * @param v2          Second object to compare.
	 * @param previousKey Current context in json representation.
	 * @param key         Current key.
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
			errors = compareCollections((Collection) v1, (Collection) v2, previousKey, key);
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
	 * @param c1 First collection.
	 * @param c2 Second collection.
	 * @return List of found errors.
	 */
	private static List<String> compareCollections(Collection c1, Collection c2) {
		List<String> errors = new ArrayList<String>();

		int size1 = c1.size();
		int size2 = c2.size();

		// First check size of both collections
		if (size1 != size2) {
			String msg = String.format("Expect size of array to be <%s> but was <%s>", size2, size1);
			errors.add(msg);
			return errors;
		}

		return compareCollections(c1, c2, "", "");
	}

	/**
	 * Compare two collections of Object.
	 *
	 * @param previousKey Current context in json representation.
	 * @param key         Current key.
	 * @param c1          First collection.
	 * @param c2          Second collection.
	 * @return List of found errors.
	 */
	private static List<String> compareCollections(Collection c1, Collection c2, String previousKey, String key) {
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
