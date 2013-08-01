package org.fest.assertions.api.rest;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.fest.assertions.api.Assertions.fail;

import org.junit.Before;
import org.junit.Test;

public class JsonAssertTest {

	private String simpleJson;
	private String nestedJson;

	private JsonAssert simpleJsonAssertion;
	private JsonAssert nestedJsonAssertion;

	@Before
	public void setUp() {
		simpleJson = "{\"id\": 1, \"name\": \"foo\", \"nickname\": \"bar\", \"flag\": true }";
		nestedJson = "{\"id\": 1, \"name\": { \"firstName\": \"foo\", \"lastName\": \"bar\" }, \"nickname\": \"bar\", \"flag\": false }";

		simpleJsonAssertion = new JsonAssert(simpleJson);
		nestedJsonAssertion = new JsonAssert(nestedJson);
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
			assertThat(error.getMessage()).isEqualTo("Expected path <foo> to be find in json <{\"id\": 1, \"name\": \"foo\", \"nickname\": \"bar\", \"flag\": true }>");
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
}
