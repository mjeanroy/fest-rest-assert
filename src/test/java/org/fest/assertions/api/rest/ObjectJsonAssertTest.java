package org.fest.assertions.api.rest;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.fest.assertions.api.Assertions.fail;

import org.junit.Before;
import org.junit.Test;

public class ObjectJsonAssertTest {

	private String simpleJson;
	private String nestedJson;

	private ObjectJsonAssert simpleJsonAssertion;
	private ObjectJsonAssert nestedJsonAssertion;

	@Before
	public void setUp() {
		simpleJson = "{\"id\": 1, \"name\": \"foo\", \"nickname\": \"bar\", \"flag\": true, \"zero\": 0, \"negative\": -1 }";
		nestedJson = "{\"id\": 1, \"name\": { \"firstName\": \"foo\", \"lastName\": \"bar\" }, \"nickname\": \"bar\", \"flag\": false }";

		simpleJsonAssertion = new ObjectJsonAssert(simpleJson);
		nestedJsonAssertion = new ObjectJsonAssert(nestedJson);
	}

	@Test
	public void test_hasPath() {
		simpleJsonAssertion.hasPath("id");
		nestedJsonAssertion.hasPath("name.firstName");

		try {
			simpleJsonAssertion.hasPath("foo");
			fail("Expected AssertionError to be thrown");
		}
		catch (AssertionError error) {
			assertThat(error.getMessage()).isEqualTo("Expected path <foo> to be find in json <{\"id\": 1, \"name\": \"foo\", \"nickname\": \"bar\", \"flag\": true, \"zero\": 0, \"negative\": -1 }>");
		}

		try {
			nestedJsonAssertion.hasPath("name.foo");
			fail("Expected AssertionError to be thrown");
		}
		catch (AssertionError error) {
			assertThat(error.getMessage()).isEqualTo("Expected path <name.foo> to be find in json <{\"id\": 1, \"name\": { \"firstName\": \"foo\", \"lastName\": \"bar\" }, \"nickname\": \"bar\", \"flag\": false }>");
		}
	}

	@Test
	public void test_isPathEqualTo() {
		simpleJsonAssertion.isPathEqualTo("id", 1);
		simpleJsonAssertion.isPathEqualTo("name", "foo");
		simpleJsonAssertion.isPathEqualTo("flag", true);

		try {
			simpleJsonAssertion.isPathEqualTo("name", "bar");
			fail("Expected AssertionError to be thrown");
		}
		catch (AssertionError error) {
			assertThat(error.getMessage()).isEqualTo("Expect path <name> to be <bar> but was <foo>");
		}

		try {
			simpleJsonAssertion.isPathEqualTo("flag", false);
			fail("Expected AssertionError to be thrown");
		}
		catch (AssertionError error) {
			assertThat(error.getMessage()).isEqualTo("Expect path <flag> to be <false> but was <true>");
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
}
