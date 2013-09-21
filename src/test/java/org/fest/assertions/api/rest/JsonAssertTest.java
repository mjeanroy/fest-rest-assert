package org.fest.assertions.api.rest;

import org.fest.assertions.data.JsonEntry;
import org.fest.assertions.utils.FooBar;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.net.URI;
import java.net.URL;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.fest.assertions.api.Assertions.fail;

public class JsonAssertTest {

	private String simpleJson;
	private String nestedJson;

	private JsonAssert simpleJsonAssertion;
	private JsonAssert nestedJsonAssertion;

	@Before
	public void setUp() {
		simpleJson = "";
		nestedJson = "";

		simpleJson += "{";
		simpleJson += "  \"id\": 1,";
		simpleJson += "  \"name\": \"foo\",";
		simpleJson += "  \"nickname\": \"bar\",";
		simpleJson += "  \"flag\": true,";
		simpleJson += "  \"zero\": 0,";
		simpleJson += "  \"negative\": -1";
		simpleJson += "}";

		nestedJson += "{";
		nestedJson += "  \"id\": 1,";
		nestedJson += "  \"name\": {";
		nestedJson += "    \"firstName\": \"foo\",";
		nestedJson += "    \"lastName\": \"bar\"";
		nestedJson += "  },";
		nestedJson += "  \"nickname\": \"bar\",";
		nestedJson += "  \"flag\": false";
		nestedJson += "}";

		simpleJsonAssertion = new JsonAssert(simpleJson);
		nestedJsonAssertion = new JsonAssert(nestedJson);
	}

	@Test
	public void test_isValid() {
		String array = "";
		array += "{";
		array += "  \"foo\": 1,";
		array += "}";

		try {
			new JsonAssert(array);
			fail("Expected AssertionError to be thrown");
		}
		catch (AssertionError error) {
			assertThat(error.getMessage()).isEqualTo("Expect json to be valid");
		}
	}

	@Test
	public void test_isObject() {
		String array = "";
		array += "{";
		array += "  \"foo\": 1";
		array += "}";
		new JsonAssert(array).isObject();

		array = "";
		array += "[";
		array += "  {";
		array += "    \"foo\": 1";
		array += "  }";
		array += "]";
		new JsonAssert(array).isArray();

		try {
			array = "";
			array += "[";
			array += "  {";
			array += "    \"foo\": 1";
			array += "  }";
			array += "]";
			new JsonAssert(array).isObject();
			fail("Expected AssertionError to be thrown");
		}
		catch (AssertionError error) {
			assertThat(error.getMessage()).isEqualTo("Expect json to be an object");
		}
	}

	@Test
	public void test_isArray() {
		String array = "[1, 2, 3]";
		new JsonAssert(array).isArray();

		array = "";
		array += "[";
		array += "  {";
		array += "    \"foo\": 1";
		array += "  }";
		array += "]";
		new JsonAssert(array).isArray();

		try {
			array = "";
			array += "{";
			array += "  \"foo\": 1";
			array += "}";
			new JsonAssert(array).isArray();
			fail("Expected AssertionError to be thrown");
		}
		catch (AssertionError error) {
			assertThat(error.getMessage()).isEqualTo("Expect json to be an array");
		}
	}

	@Test
	public void test_isArrayWithSize() {
		String array = "[1, 2, 3]";
		new JsonAssert(array).isArrayWithSize(3);

		try {
			new JsonAssert(array).isArrayWithSize(2);
		}
		catch (AssertionError error) {
			assertThat(error.getMessage()).isEqualTo("Expect json to be an array with size <2> but was <3>");
		}
	}

	@Test
	public void test_isEmptyArray() {
		String array = "[]";
		new JsonAssert(array).isEmptyArray();

		try {
			array = "[1, 2, 3]";
			new JsonAssert(array).isEmptyArray();
		}
		catch (AssertionError error) {
			assertThat(error.getMessage()).isEqualTo("Expect json to be an array with size <0> but was <3>");
		}
	}

	@Test
	public void test_Contain() {
		simpleJsonAssertion.contain("id");
		nestedJsonAssertion.contain("name.firstName");

		try {
			simpleJsonAssertion.contain("foo");
			fail("Expected AssertionError to be thrown");
		}
		catch (AssertionError error) {
			assertThat(error.getMessage()).isEqualTo("Expected path <foo> to be find");
		}

		try {
			nestedJsonAssertion.contain("name.foo");
			fail("Expected AssertionError to be thrown");
		}
		catch (AssertionError error) {
			assertThat(error.getMessage()).isEqualTo("Expected path <name.foo> to be find");
		}
	}

	@Test
	public void test_contains() {
		simpleJsonAssertion.contains("id", "name", "nickname", "flag", "zero");
		nestedJsonAssertion.contains("id", "name.firstName");

		try {
			simpleJsonAssertion.contains("id", "idfoo", "idbar");
			fail("Expected AssertionError to be thrown");
		}
		catch (AssertionError error) {
			assertThat(error.getMessage()).isEqualTo("Expected keys <idfoo, idbar> to be find");
		}
	}

	@Test
	public void test_containsEntry() {
		simpleJsonAssertion.containsEntry(JsonEntry.entry("id", 1));
		simpleJsonAssertion.containsEntry(JsonEntry.entry("name", "foo"));
		simpleJsonAssertion.containsEntry(JsonEntry.entry("flag", true));

		try {
			simpleJsonAssertion.containsEntry(JsonEntry.entry("flag", false));
			fail("Expected AssertionError to be thrown");
		}
		catch (AssertionError error) {
			assertThat(error.getMessage()).isEqualTo("Expect path <flag> to be <false> but was <true>");
		}
	}

	@Test
	public void test_containsEntries() {
		simpleJsonAssertion.containsEntries(
				JsonEntry.entry("id", 1),
				JsonEntry.entry("name", "foo"),
				JsonEntry.entry("flag", true)
		);

		try {
			simpleJsonAssertion.containsEntries(
					JsonEntry.entry("flag", false),
					JsonEntry.entry("name", "bar"),
					JsonEntry.entry("id", 1)
			);
			fail("Expected AssertionError to be thrown");
		}
		catch (AssertionError error) {
			assertThat(error.getMessage()).isEqualTo("Expect following paths <flag, name> to be <false, bar>");
		}
	}

	@Test
	public void test_contain() {
		simpleJsonAssertion.contain("id", 1);
		simpleJsonAssertion.contain("name", "foo");
		simpleJsonAssertion.contain("flag", true);

		try {
			simpleJsonAssertion.contain("name", "bar");
			fail("Expected AssertionError to be thrown");
		}
		catch (AssertionError error) {
			assertThat(error.getMessage()).isEqualTo("Expect path <name> to be <bar> but was <foo>");
		}

		try {
			simpleJsonAssertion.contain("flag", false);
			fail("Expected AssertionError to be thrown");
		}
		catch (AssertionError error) {
			assertThat(error.getMessage()).isEqualTo("Expect path <flag> to be <false> but was <true>");
		}
	}

	@Test
	public void test_isValueAnArray() {
		String array = "";
		array += "{";
		array += "  \"foo\": [1, 2, 3],";
		array += "  \"bar\": 1";
		array += "}";
		new JsonAssert(array).isArray("foo");

		try {
			new JsonAssert(array).isArray("bar");
			fail("Expected AssertionError to be thrown");
		}
		catch (AssertionError error) {
			assertThat(error.getMessage()).isEqualTo("Expect <bar> to be an array");
		}
	}

	@Test
	public void test_isValueAnObject() {
		String array = "";
		array += "{";
		array += "  \"foo\": [1, 2, 3],";
		array += "  \"bar\": 1,";
		array += "  \"quix\": {";
		array += "    \"bar\": 1";
		array += "  }";
		array += "}";
		new JsonAssert(array).isObject("bar");
		new JsonAssert(array).isObject("quix");

		try {
			new JsonAssert(array).isObject("foo");
			fail("Expected AssertionError to be thrown");
		}
		catch (AssertionError error) {
			assertThat(error.getMessage()).isEqualTo("Expect <foo> to be an object");
		}
	}

	@Test
	public void test_isValueAnArrayWithExpectedSize() {
		String array = "";
		array += "{";
		array += "  \"foo\": [1, 2, 3],";
		array += "  \"bar\": 1";
		array += "}";
		new JsonAssert(array).isArrayWithSize("foo", 3);

		try {
			new JsonAssert(array).isArrayWithSize("foo", 2);
			fail("Expected AssertionError to be thrown");
		}
		catch (AssertionError error) {
			assertThat(error.getMessage()).isEqualTo("Expect <foo> to be an array with size <2> but was <3>");
		}
	}

	@Test
	public void test_isValueAnEmptyArray() {
		String array = "";
		array += "{";
		array += "  \"foo\": [1, 2, 3],";
		array += "  \"bar\": []";
		array += "}";
		new JsonAssert(array).isEmptyArray("bar");

		try {
			new JsonAssert(array).isEmptyArray("foo");
			fail("Expected AssertionError to be thrown");
		}
		catch (AssertionError error) {
			assertThat(error.getMessage()).isEqualTo("Expect <foo> to be an array with size <0> but was <3>");
		}
	}

	@Test
	public void test_isNumber() {
		String array = "";
		array += "{";
		array += "  \"foo\": [1, 2, 3],";
		array += "  \"bar\": \"1\",";
		array += "  \"nb\": 1,";
		array += "  \"quix\": {";
		array += "    \"bar\": 1";
		array += "  }";
		array += "}";

		new JsonAssert(array).isNumber("nb");
		new JsonAssert(array).isNumber("quix.bar");
		new JsonAssert(array).isNumber("foo[0]");
		new JsonAssert(array).isNumber("foo[1]");
		new JsonAssert(array).isNumber("foo[2]");

		try {
			new JsonAssert(array).isNumber("bar");
			fail("Expected AssertionError to be thrown");
		}
		catch (AssertionError error) {
			assertThat(error.getMessage()).isEqualTo("Expect <bar> to be a number");
		}

		try {
			new JsonAssert(array).isNumber("quix");
			fail("Expected AssertionError to be thrown");
		}
		catch (AssertionError error) {
			assertThat(error.getMessage()).isEqualTo("Expect <quix> to be a number");
		}
	}

	@Test
	public void test_isString() {
		String array = "";
		array += "{";
		array += "  \"foo\": [1, \"2\", 3],";
		array += "  \"bar\": \"1\",";
		array += "  \"nb\": 1,";
		array += "  \"quix\": {";
		array += "    \"bar\": 1";
		array += "  }";
		array += "}";

		new JsonAssert(array).isString("bar");
		new JsonAssert(array).isString("foo[1]");

		try {
			new JsonAssert(array).isString("nb");
			fail("Expected AssertionError to be thrown");
		}
		catch (AssertionError error) {
			assertThat(error.getMessage()).isEqualTo("Expect <nb> to be a string");
		}

		try {
			new JsonAssert(array).isString("foo[0]");
			fail("Expected AssertionError to be thrown");
		}
		catch (AssertionError error) {
			assertThat(error.getMessage()).isEqualTo("Expect <foo[0]> to be a string");
		}
	}

	@Test
	public void test_isEmptyString() {
		String array = "";
		array += "{";
		array += "  \"bar\": \"\",";
		array += "  \"quix\": {";
		array += "    \"bar\": \"une chaine de caractère\"";
		array += "  }";
		array += "}";

		new JsonAssert(array).isEmptyString("bar");

		try {
			new JsonAssert(array).isEmptyString("quix.bar");
			fail("Expected AssertionError to be thrown");
		}
		catch (AssertionError error) {
			assertThat(error.getMessage()).isEqualTo("Expect <quix.bar> to be an empty string");
		}
	}

	@Test
	public void test_isBoolean() {
		String array = "";
		array += "{";
		array += "  \"foo\": [1, true, 3],";
		array += "  \"bar\": \"false\",";
		array += "  \"nb\": 1,";
		array += "  \"quix\": {";
		array += "    \"bar\": false";
		array += "  }";
		array += "}";

		new JsonAssert(array).isBoolean("foo[1]");
		new JsonAssert(array).isBoolean("quix.bar");

		try {
			new JsonAssert(array).isBoolean("nb");
			fail("Expected AssertionError to be thrown");
		}
		catch (AssertionError error) {
			assertThat(error.getMessage()).isEqualTo("Expect <nb> to be a boolean");
		}

		try {
			new JsonAssert(array).isBoolean("bar");
			fail("Expected AssertionError to be thrown");
		}
		catch (AssertionError error) {
			assertThat(error.getMessage()).isEqualTo("Expect <bar> to be a boolean");
		}
	}

	@Test
	public void test_isValueNull() {
		String array = "";
		array += "{";
		array += "  \"foo\": [1, 2, 3],";
		array += "  \"bar\": null,";
		array += "  \"quix\": {";
		array += "    \"bar\": 1";
		array += "  }";
		array += "}";
		new JsonAssert(array).isNull("bar");

		try {
			new JsonAssert(array).isNull("foo");
			fail("Expected AssertionError to be thrown");
		}
		catch (AssertionError error) {
			assertThat(error.getMessage()).isEqualTo("Expect <foo> to be null");
		}
	}

	@Test
	public void test_isValueNotNull() {
		String array = "";
		array += "{";
		array += "  \"foo\": [1, 2, 3],";
		array += "  \"bar\": null,";
		array += "  \"quix\": {";
		array += "    \"bar\": 1";
		array += "  }";
		array += "}";
		new JsonAssert(array).isNotNull("foo");
		new JsonAssert(array).isNotNull("quix");
		new JsonAssert(array).isNotNull("quix.bar");

		try {
			new JsonAssert(array).isNotNull("bar");
			fail("Expected AssertionError to be thrown");
		}
		catch (AssertionError error) {
			assertThat(error.getMessage()).isEqualTo("Expect <bar> not to be null");
		}
	}

	@Test
	public void test_isTrue() {
		simpleJsonAssertion.isTrue("flag");
		nestedJsonAssertion.isFalse("flag");

		try {
			simpleJsonAssertion.isFalse("flag");
			fail("Expected AssertionError to be thrown");
		}
		catch (AssertionError error) {
			assertThat(error.getMessage()).isEqualTo("Expect path <flag> to be <false> but was <true>");
		}

		try {
			nestedJsonAssertion.isTrue("flag");
			fail("Expected AssertionError to be thrown");
		}
		catch (AssertionError error) {
			assertThat(error.getMessage()).isEqualTo("Expect path <flag> to be <true> but was <false>");
		}
	}

	@Test
	public void test_isZero() {
		simpleJsonAssertion.isZero("zero");

		try {
			simpleJsonAssertion.isZero("id");
			fail("Expected AssertionError to be thrown");
		}
		catch (AssertionError error) {
			assertThat(error.getMessage()).isEqualTo("Expect path <id> to be <0> but was <1>");
		}
	}

	@Test
	public void test_isGreaterThan() {
		simpleJsonAssertion.isGreaterThan("id", 0);

		try {
			simpleJsonAssertion.isGreaterThan("id", 1);
			fail("Expected AssertionError to be thrown");
		}
		catch (AssertionError error) {
			assertThat(error.getMessage()).isEqualTo("Expect path <id> to be greater than <1> but was <1>");
		}
	}

	@Test
	public void test_isGreaterThanOrEqualTo() {
		simpleJsonAssertion.isGreaterThanOrEqualTo("id", 0);
		simpleJsonAssertion.isGreaterThanOrEqualTo("id", 1);

		try {
			simpleJsonAssertion.isGreaterThanOrEqualTo("id", 2);
			fail("Expected AssertionError to be thrown");
		}
		catch (AssertionError error) {
			assertThat(error.getMessage()).isEqualTo("Expect path <id> to be greater than or equal to <2> but was <1>");
		}
	}

	@Test
	public void test_isLessThan() {
		simpleJsonAssertion.isLessThan("id", 10);

		try {
			simpleJsonAssertion.isLessThan("id", 1);
			fail("Expected AssertionError to be thrown");
		}
		catch (AssertionError error) {
			assertThat(error.getMessage()).isEqualTo("Expect path <id> to be less than <1> but was <1>");
		}
	}

	@Test
	public void test_isLessThanOrEqualTo() {
		simpleJsonAssertion.isLessThanOrEqualTo("id", 2);
		simpleJsonAssertion.isLessThanOrEqualTo("id", 1);

		try {
			simpleJsonAssertion.isLessThanOrEqualTo("id", 0);
			fail("Expected AssertionError to be thrown");
		}
		catch (AssertionError error) {
			assertThat(error.getMessage()).isEqualTo("Expect path <id> to be less than or equal to <0> but was <1>");
		}
	}

	@Test
	public void test_isPositive() {
		simpleJsonAssertion.isPositive("id");

		try {
			simpleJsonAssertion.isPositive("negative");
			fail("Expected AssertionError to be thrown");
		}
		catch (AssertionError error) {
			assertThat(error.getMessage()).isEqualTo("Expect path <negative> to be greater than <0> but was <-1>");
		}
	}

	@Test
	public void test_isNegative() {
		simpleJsonAssertion.isNegative("negative");

		try {
			simpleJsonAssertion.isNegative("id");
			fail("Expected AssertionError to be thrown");
		}
		catch (AssertionError error) {
			assertThat(error.getMessage()).isEqualTo("Expect path <id> to be less than <0> but was <1>");
		}
	}

	@Test
	public void test_isStrictlyEqualToWithFile_useSimpleObject() throws Exception {
		String json = "";
		json += "{";
		json += "  \"foo\": 1,";
		json += "  \"bar\": 1";
		json += "}";

		URL url = getClass().getResource("/expectedStrictlyEqual.json");
		URI uri = url.toURI();
		File file = new File(uri);

		JsonAssert assertion = new JsonAssert(json);
		assertion.isStrictlyEqualsTo(file);
	}

	@Test
	public void test_isStrictlyEqualToWithURL_useSimpleObject() throws Exception {
		String json = "";
		json += "{";
		json += "  \"foo\": 1,";
		json += "  \"bar\": 1";
		json += "}";

		JsonAssert assertion = new JsonAssert(json);
		assertion.isStrictlyEqualsTo(getClass().getResource("/expectedStrictlyEqual.json"));
	}

	@Test
	public void test_isStrictlyEqualToWithObject_useSimpleObject() throws Exception {
		String json = "";
		json += "{";
		json += "  \"foo\": 1,";
		json += "  \"bar\": 1";
		json += "}";

		JsonAssert assertion = new JsonAssert(json);
		assertion.isStrictlyEqualsTo(new FooBar(1L, 1L));
	}

	@Test
	public void test_isStrictlyEqualTo_useSimpleObject() {
		String json = "";
		json += "{";
		json += "  \"foo\": 1,";
		json += "  \"bar\": 1";
		json += "}";

		String expectedJson = "";
		expectedJson += "{";
		expectedJson += "  \"foo\": 1,";
		expectedJson += "  \"bar\": 1";
		expectedJson += "}";

		JsonAssert assertion = new JsonAssert(json);
		assertion.isStrictlyEqualsTo(expectedJson);
	}

	@Test
	public void test_isStrictlyEqualTo_useSimpleObject_expectToHaveMissingKey() {
		String json = "";
		json += "{";
		json += "  \"foo\": 1,";
		json += "  \"bar\": 1";
		json += "}";

		String expectedJson = "";
		expectedJson += "{";
		expectedJson += "  \"foo\": 1,";
		expectedJson += "  \"bar\": 1,";
		expectedJson += "  \"quix\": 1";
		expectedJson += "}";

		try {
			JsonAssert assertion = new JsonAssert(json);
			assertion.isStrictlyEqualsTo(expectedJson);
			fail("Expected AssertionError to be thrown");
		}
		catch (AssertionError error) {
			assertThat(error.getMessage()).isEqualTo("Expect json to contain <quix>");
		}
	}

	@Test
	public void test_isStrictlyEqualTo_useSimpleObject_expectToHaveNullValue() {
		String json = "";
		json += "{";
		json += "  \"foo\": 1,";
		json += "  \"bar\": 1";
		json += "}";

		String expectedJson = "";
		expectedJson += "{";
		expectedJson += "  \"foo\": null,";
		expectedJson += "  \"bar\": 1";
		expectedJson += "}";

		try {
			JsonAssert assertion = new JsonAssert(json);
			assertion.isStrictlyEqualsTo(expectedJson);
			fail("Expected AssertionError to be thrown");
		}
		catch (AssertionError error) {
			assertThat(error.getMessage()).isEqualTo("Key <foo> was expected to be null but found value was <1>");
		}
	}

	@Test
	public void test_isStrictlyEqualTo_useSimpleObject_expectToHaveMissingKey_withNestedObject() {
		String json = "";
		json += "{";
		json += "  \"foo\": 1,";
		json += "  \"bar\": {";
		json += "    \"quix1\": 0";
		json += "   }";
		json += "}";

		String expectedJson = "";
		expectedJson += "{";
		expectedJson += "  \"foo\": 1,";
		expectedJson += "  \"bar\": {";
		expectedJson += "    \"quix1\": 0,";
		expectedJson += "    \"quix2\": 0";
		expectedJson += "   }";
		expectedJson += "}";

		try {
			JsonAssert assertion = new JsonAssert(json);
			assertion.isStrictlyEqualsTo(expectedJson);
			fail("Expected AssertionError to be thrown");
		}
		catch (AssertionError error) {
			assertThat(error.getMessage()).isEqualTo("Expect json to contain <bar.quix2>");
		}
	}

	@Test
	public void test_isStrictlyEqualTo_useSimpleObject_expectToHaveMoreKeyThanExpected() {
		String json = "";
		json += "{";
		json += "  \"foo\": 1,";
		json += "  \"bar\": 1,";
		json += "  \"quix\": 1";
		json += "}";

		String expectedJson = "";
		expectedJson += "{";
		expectedJson += "  \"foo\": 1,";
		expectedJson += "  \"bar\": 1";
		expectedJson += "}";

		try {
			JsonAssert assertion = new JsonAssert(json);
			assertion.isStrictlyEqualsTo(expectedJson);
			fail("Expected AssertionError to be thrown");
		}
		catch (AssertionError error) {
			assertThat(error.getMessage()).isEqualTo("Key <quix> was found but not expected");
		}
	}

	@Test
	public void test_isStrictlyEqualTo_useSimpleObject_hasNullValue() {
		String json = "";
		json += "{";
		json += "  \"foo\": null,";
		json += "  \"bar\": 1";
		json += "}";

		String expectedJson = "";
		expectedJson += "{";
		expectedJson += "  \"foo\": 1,";
		expectedJson += "  \"bar\": 1";
		expectedJson += "}";

		try {
			JsonAssert assertion = new JsonAssert(json);
			assertion.isStrictlyEqualsTo(expectedJson);
			fail("Expected AssertionError to be thrown");
		}
		catch (AssertionError error) {
			assertThat(error.getMessage()).isEqualTo("Key <foo> was null but expected value was <1>");
		}
	}

	@Test
	public void test_isStrictlyEqualTo_useSimpleObject_expectToHaveMoreKey_withNestedObject() {
		String json = "";
		json += "{";
		json += "  \"foo\": 1,";
		json += "  \"bar\": {";
		json += "    \"quix1\": 0,";
		json += "    \"quix2\": 0";
		json += "   }";
		json += "}";

		String expectedJson = "";
		expectedJson += "{";
		expectedJson += "  \"foo\": 1,";
		expectedJson += "  \"bar\": {";
		expectedJson += "    \"quix1\": 0";
		expectedJson += "   }";
		expectedJson += "}";

		try {
			JsonAssert assertion = new JsonAssert(json);
			assertion.isStrictlyEqualsTo(expectedJson);
			fail("Expected AssertionError to be thrown");
		}
		catch (AssertionError error) {
			assertThat(error.getMessage()).isEqualTo("Key <bar.quix2> was found but not expected");
		}
	}

	@Test
	public void test_isStrictlyEqualTo_useSimpleObject_expectStringInsteadOfInt() {
		String json = "";
		json += "{";
		json += "  \"foo\": 1,";
		json += "  \"bar\": 1";
		json += "}";

		String expectedJson = "";
		expectedJson += "{";
		expectedJson += "  \"foo\": \"1\",";
		expectedJson += "  \"bar\": 1";
		expectedJson += "}";

		try {
			JsonAssert assertion = new JsonAssert(json);
			assertion.isStrictlyEqualsTo(expectedJson);
			fail("Expected AssertionError to be thrown");
		}
		catch (AssertionError error) {
			assertThat(error.getMessage()).isEqualTo("Expect type <String> but was <Integer> for key <foo>");
		}
	}

	@Test
	public void test_isStrictlyEqualTo_useSimpleObject_expectArrayToHaveSizeInferior() {
		String json = "";
		json += "{";
		json += "  \"foo\": [1, 2, 3],";
		json += "  \"bar\": 1";
		json += "}";

		String expectedJson = "";
		expectedJson += "{";
		expectedJson += "  \"foo\": [1, 2],";
		expectedJson += "  \"bar\": 1";
		expectedJson += "}";

		try {
			JsonAssert assertion = new JsonAssert(json);
			assertion.isStrictlyEqualsTo(expectedJson);
			fail("Expected AssertionError to be thrown");
		}
		catch (AssertionError error) {
			assertThat(error.getMessage()).isEqualTo("Expect size of array <foo> to be <2> but was <3>");
		}
	}

	@Test
	public void test_isStrictlyEqualTo_useSimpleObject_expectArrayToHaveSizeSuperior() {
		String json = "";
		json += "{";
		json += "  \"foo\": [1, 2, 3],";
		json += "  \"bar\": 1";
		json += "}";

		String expectedJson = "";
		expectedJson += "{";
		expectedJson += "  \"foo\": [1, 2, 3, 4],";
		expectedJson += "  \"bar\": 1";
		expectedJson += "}";

		try {
			JsonAssert assertion = new JsonAssert(json);
			assertion.isStrictlyEqualsTo(expectedJson);
			fail("Expected AssertionError to be thrown");
		}
		catch (AssertionError error) {
			assertThat(error.getMessage()).isEqualTo("Expect size of array <foo> to be <4> but was <3>");
		}
	}

	@Test
	public void test_isStrictlyEqualTo_useSimpleObject_expectArrayToHaveItem() {
		String json = "";
		json += "{";
		json += "  \"foo\": [1, 2, 3],";
		json += "  \"bar\": 1";
		json += "}";

		String expectedJson = "";
		expectedJson += "{";
		expectedJson += "  \"foo\": [1, 2, 4],";
		expectedJson += "  \"bar\": 1";
		expectedJson += "}";

		try {
			JsonAssert assertion = new JsonAssert(json);
			assertion.isStrictlyEqualsTo(expectedJson);
			fail("Expected AssertionError to be thrown");
		}
		catch (AssertionError error) {
			assertThat(error.getMessage()).isEqualTo("Expect <foo[2]> to be <3> but was <4>");
		}
	}

	@Test
	public void test_isStrictlyEqualTo_useArrayWithNestedObjectNotSameType() {
		String json = "";
		json += "{";
		json += "  \"foo\": [";
		json += "    {";
		json += "       \"id\": 1,";
		json += "       \"name\": \"foo\"";
		json += "    }";
		json += "  ],";
		json += "  \"bar\": 1";
		json += "}";

		String expectedJson = "";
		expectedJson += "{";
		expectedJson += "  \"foo\": [";
		expectedJson += "    {";
		expectedJson += "       \"id\": \"1\",";
		expectedJson += "       \"name\": \"foo\"";
		expectedJson += "    }";
		expectedJson += "  ],";
		expectedJson += "  \"bar\": 1";
		expectedJson += "}";

		try {
			JsonAssert assertion = new JsonAssert(json);
			assertion.isStrictlyEqualsTo(expectedJson);
			fail("Expected AssertionError to be thrown");
		}
		catch (AssertionError error) {
			assertThat(error.getMessage()).isEqualTo("Expect type <String> but was <Integer> for key <foo[0].id>");
		}
	}

	@Test
	public void test_isStrictlyEqualTo_useArrayWithNestedObjectNotSameKeys() {
		String json = "";
		json += "{";
		json += "  \"foo\": [";
		json += "    {";
		json += "       \"id\": 1,";
		json += "       \"name\": \"foo\"";
		json += "    }";
		json += "  ],";
		json += "  \"bar\": 1";
		json += "}";

		String expectedJson = "";
		expectedJson += "{";
		expectedJson += "  \"foo\": [";
		expectedJson += "    {";
		expectedJson += "       \"id\": 1";
		expectedJson += "    }";
		expectedJson += "  ],";
		expectedJson += "  \"bar\": 1";
		expectedJson += "}";

		try {
			JsonAssert assertion = new JsonAssert(json);
			assertion.isStrictlyEqualsTo(expectedJson);
			fail("Expected AssertionError to be thrown");
		}
		catch (AssertionError error) {
			assertThat(error.getMessage()).isEqualTo("Key <foo[0].name> was found but not expected");
		}
	}

	@Test
	public void test_isStrictlyEqualTo_useArrayWithNestedObjectNotEquals() {
		String json = "";
		json += "{";
		json += "  \"foo\": [";
		json += "    {";
		json += "       \"id\": 1,";
		json += "       \"name\": \"foo\"";
		json += "    }";
		json += "  ],";
		json += "  \"bar\": 1";
		json += "}";

		String expectedJson = "";
		expectedJson += "{";
		expectedJson += "  \"foo\": [";
		expectedJson += "    {";
		expectedJson += "       \"id\": 2,";
		expectedJson += "       \"name\": \"foo\"";
		expectedJson += "    }";
		expectedJson += "  ],";
		expectedJson += "  \"bar\": 1";
		expectedJson += "}";

		try {
			JsonAssert assertion = new JsonAssert(json);
			assertion.isStrictlyEqualsTo(expectedJson);
			fail("Expected AssertionError to be thrown");
		}
		catch (AssertionError error) {
			assertThat(error.getMessage()).isEqualTo("Expect <foo[0].id> to be <1> but was <2>");
		}
	}
}
