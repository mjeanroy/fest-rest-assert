package org.fest.assertions.util;

import static org.fest.assertions.api.Assertions.assertThat;

import java.util.List;

import org.junit.Test;

public class JsonComparatorTest {

	@Test
	public void test_isValid_shouldReturnTrueWithSimpleValue() {
		String json = "1";
		assertThat(JsonComparator.isValid(json)).isTrue();
	}

	@Test
	public void test_isValid_shouldReturnTrueWithObject() {
		String json = "";
		json += "{";
		json += "  \"foo\": 1,";
		json += "  \"bar\": \"2\"";
		json += "}";
		assertThat(JsonComparator.isValid(json)).isTrue();
	}

	@Test
	public void test_isValid_shouldReturnTrueWithArrayOfValues() {
		String json = "[1, 2, 3]";
		assertThat(JsonComparator.isValid(json)).isTrue();
	}

	@Test
	public void test_isValid_shouldReturnTrueWithArrayOfObject() {
		String json = "";
		json += "[";
		json += "  {";
		json += "    \"foo\": 1,";
		json += "    \"bar\": \"2\"";
		json += "  }";
		json += "]";
		assertThat(JsonComparator.isValid(json)).isTrue();
	}

	@Test
	public void test_isValid_shouldReturnFalseWithInvalidObject_missingEnd() {
		String json = "";
		json += "{";
		json += "  \"foo\": 1,";
		json += "  \"bar\": \"2\"";
		assertThat(JsonComparator.isValid(json)).isFalse();
	}

	@Test
	public void test_isValid_shouldReturnFalseWithInvalidObject_missingComma() {
		String json = "";
		json += "{";
		json += "  \"foo\": 1";
		json += "  \"bar\": \"2\"";
		json += "}";
		assertThat(JsonComparator.isValid(json)).isFalse();
	}

	@Test
	public void test_isValid_shouldReturnFalseWithInvalidArray_missingEnd() {
		String json = "";
		json += "[";
		json += "  {";
		json += "    \"foo\": 1,";
		json += "    \"bar\": \"2\"";
		json += "  },";
		json += "  {";
		json += "    \"foo\": 1,";
		json += "    \"bar\": \"2\"";
		json += "  }";
		assertThat(JsonComparator.isValid(json)).isFalse();
	}

	@Test
	public void test_isValid_shouldReturnFalseWithInvalidArray_missingCommaSeparator() {
		String json = "";
		json += "[";
		json += "  {";
		json += "    \"foo\": 1,";
		json += "    \"bar\": \"2\"";
		json += "  }";
		json += "  {";
		json += "    \"foo\": 1,";
		json += "    \"bar\": \"2\"";
		json += "  }";
		json += "]";
		assertThat(JsonComparator.isValid(json)).isFalse();
	}

	@Test
	public void test_compareTwoSimpleJsonStrictlyEqual() {
		String json1 = "";
		json1 += "{";
		json1 += "  \"foo\": 1,";
		json1 += "  \"bar\": \"2\"";
		json1 += "}";

		List<String> errors = JsonComparator.compareJson(json1, json1);
		assertThat(errors).isNotNull().isEmpty();
	}

	@Test
	public void test_compareTwoSimpleArrayOfValuesStrictlyEqual() {
		String json1 = "[1, 2, 3]";
		List<String> errors = JsonComparator.compareJson(json1, json1);
		assertThat(errors).isNotNull().isEmpty();
	}

	@Test
	public void test_compareTwoSimpleArray_expectSizeToBeGreater() {
		String json1 = "[1, 2, 3]";
		String json2 = "[1, 2, 3, 4]";
		List<String> errors = JsonComparator.compareJson(json1, json2);
		assertThat(errors).isNotNull().hasSize(1)
				.contains("Expect size of array to be <4> but was <3>");
	}

	@Test
	public void test_compareTwoSimpleArray_expectSizeToBeLess() {
		String json1 = "[1, 2, 3, 4]";
		String json2 = "[1, 2, 3]";
		List<String> errors = JsonComparator.compareJson(json1, json2);
		assertThat(errors).isNotNull().hasSize(1)
				.contains("Expect size of array to be <3> but was <4>");
	}

	@Test
	public void test_compareTwoSimpleArrayOfObjectsStrictlyEqual() {
		String json1 = "";
		json1 += "[";
		json1 += "  {";
		json1 += "    \"foo\": 1,";
		json1 += "    \"bar\": 2";
		json1 += "  },";
		json1 += "  {";
		json1 += "    \"foo\": 3,";
		json1 += "    \"bar\": 4";
		json1 += "  }";
		json1 += "]";

		List<String> errors = JsonComparator.compareJson(json1, json1);
		assertThat(errors).isNotNull().isEmpty();
	}

	@Test
	public void test_compareTwoSimpleArrayOfObjectsNotEqualByValue() {
		String json1 = "";
		json1 += "[";
		json1 += "  {";
		json1 += "    \"foo\": 1,";
		json1 += "    \"bar\": 2";
		json1 += "  },";
		json1 += "  {";
		json1 += "    \"foo\": 3,";
		json1 += "    \"bar\": 4";
		json1 += "  }";
		json1 += "]";

		String json2 = "";
		json2 += "[";
		json2 += "  {";
		json2 += "    \"foo\": 1,";
		json2 += "    \"bar\": 3";
		json2 += "  },";
		json2 += "  {";
		json2 += "    \"foo\": 5,";
		json2 += "    \"bar\": 4";
		json2 += "  }";
		json2 += "]";

		List<String> errors = JsonComparator.compareJson(json1, json2);
		assertThat(errors).isNotNull().hasSize(2)
				.contains("Expect <[0].bar> to be <2> but was <3>")
				.contains("Expect <[1].foo> to be <3> but was <5>");
	}

	@Test
	public void test_compareObjectWithArray() {
		String json1 = "";
		json1 += "{";
		json1 += "  \"foo\": 1,";
		json1 += "  \"bar\": \"2\"";
		json1 += "}";

		String json2 = "";
		json2 += "[";
		json2 += "  {";
		json2 += "    \"foo\": 1,";
		json2 += "    \"bar\": \"2\"";
		json2 += "  }";
		json2 += "]";

		List<String> errors = JsonComparator.compareJson(json1, json2);
		assertThat(errors).isNotNull().isNotEmpty().hasSize(1)
				.contains("Expect json to be <Array> but was <Object>");
	}

	@Test
	public void test_compareArrayWithObject() {
		String json1 = "";
		json1 += "[";
		json1 += "  {";
		json1 += "    \"foo\": 1,";
		json1 += "    \"bar\": \"2\"";
		json1 += "  }";
		json1 += "]";

		String json2 = "";
		json2 += "{";
		json2 += "  \"foo\": 1,";
		json2 += "  \"bar\": \"2\"";
		json2 += "}";

		List<String> errors = JsonComparator.compareJson(json1, json2);
		assertThat(errors).isNotNull().isNotEmpty().hasSize(1)
				.contains("Expect json to be <Array> but was <Object>");
	}

	@Test
	public void test_compareTwoSimpleJson_valuesAreNotEqual() {
		String json1 = "";
		json1 += "{";
		json1 += "  \"foo\": 1,";
		json1 += "  \"bar\": \"2\"";
		json1 += "}";

		String json2 = "";
		json2 += "{";
		json2 += "  \"foo\": 2,";
		json2 += "  \"bar\": \"3\"";
		json2 += "}";

		List<String> errors = JsonComparator.compareJson(json1, json2);
		assertThat(errors).isNotNull().isNotEmpty().hasSize(2)
				.contains("Expect <foo> to be <1> but was <2>")
				.contains("Expect <bar> to be <2> but was <3>");
	}

	@Test
	public void test_compareTwoSimpleJson_valuesAreNotSameType() {
		String json1 = "";
		json1 += "{";
		json1 += "  \"foo\": 1,";
		json1 += "  \"bar\": 2";
		json1 += "}";

		String json2 = "";
		json2 += "{";
		json2 += "  \"foo\": \"1\",";
		json2 += "  \"bar\": 2";
		json2 += "}";

		List<String> errors = JsonComparator.compareJson(json1, json2);
		assertThat(errors).isNotNull().isNotEmpty().hasSize(1)
				.contains("Expect type <String> but was <Integer> for key <foo>");
	}

	@Test
	public void test_compareTwoSimpleJson_missingKeyInJson1() {
		String json1 = "";
		json1 += "{";
		json1 += "  \"foo\": 1";
		json1 += "}";

		String json2 = "";
		json2 += "{";
		json2 += "  \"foo\": 1,";
		json2 += "  \"bar\": 2";
		json2 += "}";

		List<String> errors = JsonComparator.compareJson(json1, json2);
		assertThat(errors).isNotNull().isNotEmpty().hasSize(1)
				.contains("Expect json to contain <bar>");
	}

	@Test
	public void test_compareTwoSimpleJson_missingKeyInJson2() {
		String json1 = "";
		json1 += "{";
		json1 += "  \"foo\": 1,";
		json1 += "  \"bar\": 2";
		json1 += "}";

		String json2 = "";
		json2 += "{";
		json2 += "  \"foo\": 1";
		json2 += "}";

		List<String> errors = JsonComparator.compareJson(json1, json2);
		assertThat(errors).isNotNull().isNotEmpty().hasSize(1)
				.contains("Key <bar> was found but not expected");
	}

	@Test
	public void test_compareTwoNestedJson_useNullValueInJson1() {
		String json1 = "";
		json1 += "{";
		json1 += "  \"foo\": null";
		json1 += "}";

		String json2 = "";
		json2 += "{";
		json2 += "  \"foo\": 1";
		json2 += "}";

		List<String> errors = JsonComparator.compareJson(json1, json2);
		assertThat(errors).isNotNull().isNotEmpty().hasSize(1)
				.contains("Key <foo> was null but expected value was <1>");
	}

	@Test
	public void test_compareTwoNestedJson_useNullValueInJson2() {
		String json1 = "";
		json1 += "{";
		json1 += "  \"foo\": 1";
		json1 += "}";

		String json2 = "";
		json2 += "{";
		json2 += "  \"foo\": null";
		json2 += "}";

		List<String> errors = JsonComparator.compareJson(json1, json2);
		assertThat(errors).isNotNull().isNotEmpty().hasSize(1)
				.contains("Key <foo> was expected to be null but found value was <1>");
	}

	@Test
	public void test_compareTwoNestedJson_missingKeyInJson2() {
		String json1 = "";
		json1 += "{";
		json1 += "  \"foo\": 1,";
		json1 += "  \"bar\": {";
		json1 += "    \"bar1\": 1,";
		json1 += "    \"bar2\": 2";
		json1 += "  }";
		json1 += "}";

		String json2 = "";
		json2 += "{";
		json2 += "  \"foo\": 1,";
		json2 += "  \"bar\": {";
		json2 += "    \"bar1\": 0,";
		json2 += "    \"bar2\": 2";
		json2 += "  }";
		json2 += "}";

		List<String> errors = JsonComparator.compareJson(json1, json2);
		assertThat(errors).isNotNull().isNotEmpty().hasSize(1)
				.contains("Expect <bar.bar1> to be <1> but was <0>");
	}

	@Test
	public void test_compareTwoJson_useSimpleArrayWithDifferentSize() {
		String json1 = "";
		json1 += "{";
		json1 += "  \"foo\": [1, 2, 3],";
		json1 += "  \"bar\": {";
		json1 += "    \"bar1\": 1,";
		json1 += "    \"bar2\": 2";
		json1 += "  }";
		json1 += "}";

		String json2 = "";
		json2 += "{";
		json2 += "  \"foo\": [1,2],";
		json2 += "  \"bar\": {";
		json2 += "    \"bar1\": 1,";
		json2 += "    \"bar2\": 2";
		json2 += "  }";
		json2 += "}";

		List<String> errors = JsonComparator.compareJson(json1, json2);
		assertThat(errors).isNotNull().isNotEmpty().hasSize(1)
				.contains("Expect size of array <foo> to be <2> but was <3>");
	}

	@Test
	public void test_compareTwoJson_useSimpleArrayWithDifferentValues() {
		String json1 = "";
		json1 += "{";
		json1 += "  \"foo\": [1, 2, 3],";
		json1 += "  \"bar\": {";
		json1 += "    \"bar1\": 1,";
		json1 += "    \"bar2\": 2";
		json1 += "  }";
		json1 += "}";

		String json2 = "";
		json2 += "{";
		json2 += "  \"foo\": [1, 2, 4],";
		json2 += "  \"bar\": {";
		json2 += "    \"bar1\": 1,";
		json2 += "    \"bar2\": 2";
		json2 += "  }";
		json2 += "}";

		List<String> errors = JsonComparator.compareJson(json1, json2);
		assertThat(errors).isNotNull().isNotEmpty().hasSize(1)
				.contains("Expect <foo[2]> to be <3> but was <4>");
	}

	@Test
	public void test_compareTwoJson_useArrayWithNestedObjectsWithDifferentValues() {
		String json1 = "";
		json1 += "{";
		json1 += "  \"foo\": [";
		json1 += "    {";
		json1 += "      \"foo1\": 1,";
		json1 += "      \"bar1\": 2";
		json1 += "    },";
		json1 += "    {";
		json1 += "      \"foo1\": 3,";
		json1 += "      \"bar1\": 4";
		json1 += "    }";
		json1 += "  ],";
		json1 += "  \"bar\": {";
		json1 += "    \"bar1\": 1,";
		json1 += "    \"bar2\": 2";
		json1 += "  }";
		json1 += "}";

		String json2 = "";
		json2 += "{";
		json2 += "  \"foo\": [";
		json2 += "    {";
		json2 += "      \"foo1\": 1,";
		json2 += "      \"bar1\": 2";
		json2 += "    },";
		json2 += "    {";
		json2 += "      \"foo1\": 3,";
		json2 += "      \"bar1\": 5";
		json2 += "    }";
		json2 += "  ],";
		json2 += "  \"bar\": {";
		json2 += "    \"bar1\": 1,";
		json2 += "    \"bar2\": 2";
		json2 += "  }";
		json2 += "}";

		List<String> errors = JsonComparator.compareJson(json1, json2);
		assertThat(errors).isNotNull().isNotEmpty().hasSize(1)
				.contains("Expect <foo[1].bar1> to be <4> but was <5>");
	}
}
