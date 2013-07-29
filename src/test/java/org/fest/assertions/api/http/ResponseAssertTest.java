package org.fest.assertions.api.http;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.fest.assertions.util.Cookie;
import org.fest.assertions.util.Response;
import org.fest.assertions.utils.OneParameterClojure;
import org.fest.assertions.utils.VoidClojure;
import org.junit.Before;
import org.junit.Test;

import com.google.common.base.Joiner;

public class ResponseAssertTest {

	private Response response;
	private ResponseAssert assertion;

	@Before
	public void setUp() {
		response = mock(Response.class);
		assertion = new ResponseAssert(response);
	}

	@Test
	public void test_isStatusNotEqualTo() {
		when(response.getStatusCode()).thenReturn(300);
		assertion.isStatusNotEqualTo(200);

		try {
			when(response.getStatusCode()).thenReturn(200);
			assertion.isStatusNotEqualTo(200);
		}
		catch (AssertionError error) {
			assertThat(error.getMessage()).isEqualTo("Expected status code not to be <200>");
		}
	}

	@Test
	public void test_isStatusNotBetween() {
		when(response.getStatusCode()).thenReturn(300);
		assertion.isStatusNotBetween(200, 299);

		for (int i = 200; i <= 299; i++) {
			try {
				when(response.getStatusCode()).thenReturn(i);
				assertion.isStatusNotBetween(200, 299);
			}
			catch (AssertionError error) {
				assertThat(error.getMessage()).isEqualTo("Expected status code not to be between <200> and <299> but was <" + i + ">");
			}
		}
	}

	@Test
	public void test_isSuccess() {
		checkRange(200, 299, new VoidClojure() {
			@Override
			public void apply() {
				assertion.isSuccess();
			}
		});
	}

	@Test
	public void test_isRedirection() {
		checkRange(300, 399, new VoidClojure() {
			@Override
			public void apply() {
				assertion.isRedirection();
			}
		});
	}

	@Test
	public void test_isClientError() {
		checkRange(400, 499, new VoidClojure() {
			@Override
			public void apply() {
				assertion.isClientError();
			}
		});
	}

	@Test
	public void test_isServerError() {
		checkRange(500, 599, new VoidClojure() {
			@Override
			public void apply() {
				assertion.isServerError();
			}
		});
	}

	@Test
	public void test_isOk() {
		checkStatus(200, new VoidClojure() {
			@Override
			public void apply() {
				assertion.isOk();
			}
		});
	}

	@Test
	public void test_isCreated() {
		checkStatus(201, new VoidClojure() {
			@Override
			public void apply() {
				assertion.isCreated();
			}
		});
	}

	@Test
	public void test_isAccepted() {
		checkStatus(202, new VoidClojure() {
			@Override
			public void apply() {
				assertion.isAccepted();
			}
		});
	}

	@Test
	public void test_isNonAuthoritativeInformation() {
		checkStatus(203, new VoidClojure() {
			@Override
			public void apply() {
				assertion.isNonAuthoritativeInformation();
			}
		});
	}

	@Test
	public void test_isNoContent() {
		checkStatus(204, new VoidClojure() {
			@Override
			public void apply() {
				assertion.isNoContent();
			}
		});
	}

	@Test
	public void test_isResetContent() {
		checkStatus(205, new VoidClojure() {
			@Override
			public void apply() {
				assertion.isResetContent();
			}
		});
	}

	@Test
	public void test_isPartialContent() {
		checkStatus(206, new VoidClojure() {
			@Override
			public void apply() {
				assertion.isPartialContent();
			}
		});
	}

	@Test
	public void test_isMultipleChoice() {
		checkStatus(300, new VoidClojure() {
			@Override
			public void apply() {
				assertion.isMultipleChoice();
			}
		});
	}

	@Test
	public void test_isMovedPermanently() {
		checkStatus(301, new VoidClojure() {
			@Override
			public void apply() {
				assertion.isMovedPermanently();
			}
		});
	}

	@Test
	public void test_isMovedTemporarily() {
		checkStatus(302, new VoidClojure() {
			@Override
			public void apply() {
				assertion.isMovedTemporarily();
			}
		});
	}

	@Test
	public void test_isSeeOther() {
		checkStatus(303, new VoidClojure() {
			@Override
			public void apply() {
				assertion.isSeeOther();
			}
		});
	}

	@Test
	public void test_isNotModified() {
		checkStatus(304, new VoidClojure() {
			@Override
			public void apply() {
				assertion.isNotModified();
			}
		});
	}

	@Test
	public void test_isBadRequest() {
		checkStatus(400, new VoidClojure() {
			@Override
			public void apply() {
				assertion.isBadRequest();
			}
		});
	}

	@Test
	public void test_isUnauthorized() {
		checkStatus(401, new VoidClojure() {
			@Override
			public void apply() {
				assertion.isUnauthorized();
			}
		});
	}

	@Test
	public void test_isForbidden() {
		checkStatus(403, new VoidClojure() {
			@Override
			public void apply() {
				assertion.isForbidden();
			}
		});
	}

	@Test
	public void test_isNotFound() {
		checkStatus(404, new VoidClojure() {
			@Override
			public void apply() {
				assertion.isNotFound();
			}
		});
	}

	@Test
	public void test_isMethodNotAllowed() {
		checkStatus(405, new VoidClojure() {
			@Override
			public void apply() {
				assertion.isMethodNotAllowed();
			}
		});
	}

	@Test
	public void test_isInternalServerError() {
		checkStatus(500, new VoidClojure() {
			@Override
			public void apply() {
				assertion.isInternalServerError();
			}
		});
	}

	@Test
	public void test_isNotImplemented() {
		checkStatus(501, new VoidClojure() {
			@Override
			public void apply() {
				assertion.isNotImplemented();
			}
		});
	}

	@Test
	public void test_isOctetStream() {
		checkType(Arrays.asList("application/octet-stream"), new VoidClojure() {
			@Override
			public void apply() {
				assertion.isOctetStream();
			}
		});
	}

	@Test
	public void test_isJson() {
		checkType(Arrays.asList("application/json"), new VoidClojure() {
			@Override
			public void apply() {
				assertion.isJson();
			}
		});
	}

	@Test
	public void test_isXml() {
		checkType(Arrays.asList("application/xml", "text/xml"), new VoidClojure() {
			@Override
			public void apply() {
				assertion.isXml();
			}
		});
	}

	@Test
	public void test_isCss() {
		checkType(Arrays.asList("text/css"), new VoidClojure() {
			@Override
			public void apply() {
				assertion.isCss();
			}
		});
	}

	@Test
	public void test_isJavascript() {
		checkType(Arrays.asList("application/javascript", "text/javascript"), new VoidClojure() {
			@Override
			public void apply() {
				assertion.isJavascript();
			}
		});
	}

	@Test
	public void test_isTextPlain() {
		checkType(Arrays.asList("text/plain"), new VoidClojure() {
			@Override
			public void apply() {
				assertion.isTextPlain();
			}
		});
	}

	@Test
	public void test_isHtml() {
		checkType(Arrays.asList("text/html"), new VoidClojure() {
			@Override
			public void apply() {
				assertion.isHtml();
			}
		});
	}

	@Test
	public void test_isXhtml() {
		checkType(Arrays.asList("application/xhtml+xml"), new VoidClojure() {
			@Override
			public void apply() {
				assertion.isXhtml();
			}
		});
	}

	@Test
	public void test_isHtmlOrXhtml() {
		checkType(Arrays.asList("text/html", "application/xhtml+xml"), new VoidClojure() {
			@Override
			public void apply() {
				assertion.isHtmlOrXhtml();
			}
		});
	}

	@Test
	public void test_isZip() {
		checkType(Arrays.asList("application/zip"), new VoidClojure() {
			@Override
			public void apply() {
				assertion.isZip();
			}
		});
	}

	@Test
	public void test_isPdf() {
		checkType(Arrays.asList("application/pdf"), new VoidClojure() {
			@Override
			public void apply() {
				assertion.isPdf();
			}
		});
	}

	@Test
	public void test_isCsv() {
		checkType(Arrays.asList("text/csv"), new VoidClojure() {
			@Override
			public void apply() {
				assertion.isCsv();
			}
		});
	}

	@Test
	public void test_isFlashContent() {
		checkType(Arrays.asList("application/x-shockwave-flash", "video/x-flv"), new VoidClojure() {
			@Override
			public void apply() {
				assertion.isFlashContent();
			}
		});
	}

	@Test
	public void test_hasContentType() {
		when(response.getContentType()).thenReturn("application/json");
		assertion.hasContentType();

		try {
			when(response.getContentType()).thenReturn(null);
			assertion.hasContentType();
		}
		catch (AssertionError error) {
			assertThat(error.getMessage()).isEqualTo("Expect Content-Type to be defined and not empty");
		}

		try {
			when(response.getContentType()).thenReturn("  ");
			assertion.hasContentType();
		}
		catch (AssertionError error) {
			assertThat(error.getMessage()).isEqualTo("Expect Content-Type to be defined and not empty");
		}
	}

	@Test
	public void test_hasCharset() {
		when(response.getContentType()).thenReturn("application/json; charset=utf-8");
		assertion.hasCharset();

		try {
			when(response.getContentType()).thenReturn("application/json");
			assertion.hasCharset();
		}
		catch (AssertionError error) {
			assertThat(error.getMessage()).isEqualTo("Expect charset to be defined in Content-Type value");
		}

		try {
			when(response.getContentType()).thenReturn("application/json;foo");
			assertion.hasContentType();
		}
		catch (AssertionError error) {
			assertThat(error.getMessage()).isEqualTo("Expect charset not to be empty in Content-Type value");
		}
	}

	@Test
	public void test_isCharsetEqualTo() {
		when(response.getContentType()).thenReturn("application/json; charset=utf-8");
		assertion.isCharsetEqualTo("utf-8");
		assertion.isCharsetEqualTo("UTF-8");

		try {
			when(response.getContentType()).thenReturn("application/json; charset=iso8859-1");
			assertion.isCharsetEqualTo("utf-8");
		}
		catch (AssertionError error) {
			assertThat(error.getMessage()).isEqualTo("Expect charset to be <utf-8> but was <iso8859-1>");
		}
	}

	@Test
	public void test_isUtf8() {
		when(response.getContentType()).thenReturn("application/json; charset=utf-8");
		assertion.isUtf8();

		try {
			when(response.getContentType()).thenReturn("application/json; charset=iso8859-1");
			assertion.isUtf8();
		}
		catch (AssertionError error) {
			assertThat(error.getMessage()).isEqualTo("Expect charset to be <utf-8> but was <iso8859-1>");
		}
	}

	@Test
	public void test_isJsonUtf8() {
		when(response.getContentType()).thenReturn("application/json; charset=utf-8");
		assertion.isJsonUtf8();

		try {
			when(response.getContentType()).thenReturn("application/xml; charset=utf-8");
			assertion.isJsonUtf8();
		}
		catch (AssertionError error) {
			assertThat(error.getMessage()).isEqualTo("Expect Content-Type to be <application/json> but was <application/xml>");
		}

		try {
			when(response.getContentType()).thenReturn("application/json; charset=iso8859-1");
			assertion.isJsonUtf8();
		}
		catch (AssertionError error) {
			assertThat(error.getMessage()).isEqualTo("Expect charset to be <utf-8> but was <iso8859-1>");
		}
	}

	@Test
	public void test_isXmlUtf8() {
		when(response.getContentType()).thenReturn("application/xml; charset=utf-8");
		assertion.isXmlUtf8();

		try {
			when(response.getContentType()).thenReturn("application/json; charset=utf-8");
			assertion.isXmlUtf8();
		}
		catch (AssertionError error) {
			assertThat(error.getMessage()).isEqualTo("Expect Content-Type to be one of <application/xml, text/xml> but was <application/json>");
		}

		try {
			when(response.getContentType()).thenReturn("application/xml; charset=iso8859-1");
			assertion.isXmlUtf8();
		}
		catch (AssertionError error) {
			assertThat(error.getMessage()).isEqualTo("Expect charset to be <utf-8> but was <iso8859-1>");
		}
	}

	@Test
	public void test_isHtmlUtf8() {
		when(response.getContentType()).thenReturn("text/html; charset=utf-8");
		assertion.isHtmlUtf8();

		try {
			when(response.getContentType()).thenReturn("application/json; charset=utf-8");
			assertion.isHtmlUtf8();
		}
		catch (AssertionError error) {
			assertThat(error.getMessage()).isEqualTo("Expect Content-Type to be one of <text/html, application/xhtml+xml> but was <application/json>");
		}

		try {
			when(response.getContentType()).thenReturn("text/html; charset=iso8859-1");
			assertion.isHtmlUtf8();
		}
		catch (AssertionError error) {
			assertThat(error.getMessage()).isEqualTo("Expect charset to be <utf-8> but was <iso8859-1>");
		}
	}

	@Test
	public void test_hasHeader() {
		when(response.getHeader("foo")).thenReturn("bar");
		assertion.hasHeader("foo");
		assertion.hasHeader("FOO");

		try {
			when(response.getHeader("foo")).thenReturn(null);
			assertion.hasHeader("foo");
		}
		catch (AssertionError error) {
			assertThat(error.getMessage()).isEqualTo("Expected header <foo> to be defined");
		}

		try {
			when(response.getHeader("foo")).thenReturn("  ");
			assertion.hasHeader("foo");
		}
		catch (AssertionError error) {
			assertThat(error.getMessage()).isEqualTo("Expected header <foo> to be defined");
		}
	}

	@Test
	public void test_hasHeaderEqualTol() {
		when(response.getHeader("foo")).thenReturn("bar");
		assertion.hasHeaderEqualTo("foo", "bar");

		try {
			when(response.getHeader("foo")).thenReturn("quix");
			assertion.hasHeaderEqualTo("foo", "bar");
		}
		catch (AssertionError error) {
			assertThat(error.getMessage()).isEqualTo("Expected header <foo> to be <bar> but was <quix>");
		}
	}

	@Test
	public void test_hasETagEqualTo() {
		checkHeader("ETag", "123456789",
				new VoidClojure() {
					@Override
					public void apply() {
						assertion.hasETagHeader();
					}
				},
				new OneParameterClojure<String>() {
					@Override
					public void apply(String val) {
						assertion.hasETagEqualTo(val);
					}
				}
		);
	}

	@Test
	public void test_hasLocationEqualTo() {
		checkHeader("Location", "foo",
				new VoidClojure() {
					@Override
					public void apply() {
						assertion.hasLocationHeader();
					}
				},
				new OneParameterClojure<String>() {
					@Override
					public void apply(String val) {
						assertion.hasLocationEqualTo(val);
					}
				}
		);
	}

	@Test
	public void test_hasCookie() {
		when(response.getCookie("foo")).thenReturn(new Cookie());
		assertion.hasCookie("foo");

		try {
			when(response.getCookie("bar")).thenReturn(null);
			assertion.hasCookie("bar");
		}
		catch (AssertionError error) {
			assertThat(error.getMessage()).isEqualTo("Expected cookie <bar> to be defined");
		}
	}

	private void checkHeader(String name, String expected, VoidClojure hasCheck, OneParameterClojure<String> fn) {
		when(response.getHeader(name.toLowerCase())).thenReturn(expected);
		hasCheck.apply();
		fn.apply(expected);

		try {
			when(response.getHeader(name)).thenReturn(null);
			hasCheck.apply();
		}
		catch (AssertionError error) {
			assertThat(error.getMessage()).isEqualTo("Expected header <" + name + "> to be defined");
		}

		try {
			when(response.getHeader(name)).thenReturn("  ");
			hasCheck.apply();
		}
		catch (AssertionError error) {
			assertThat(error.getMessage()).isEqualTo("Expected header <" + name + "> to be defined");
		}

		try {
			when(response.getHeader(name.toLowerCase())).thenReturn(expected + "foo");
			fn.apply(expected);
		}
		catch (AssertionError error) {
			assertThat(error.getMessage()).isEqualTo("Expected header <" + name + "> to be <" + expected + "> but was <" + (expected + "foo") + ">");
		}
	}

	private void checkType(List<String> expecteds, VoidClojure fn) {
		for (String expected : expecteds) {
			when(response.getContentType()).thenReturn(expected);
			fn.apply();
		}
		for (String expected : expecteds) {
			when(response.getContentType()).thenReturn(expected + "; charset=utf-8");
			fn.apply();
		}

		String oneOf = expecteds.size() > 1 ? "one of " : "";
		when(response.getContentType()).thenReturn("foo; charset=utf-8");
		try {
			fn.apply();
		}
		catch (AssertionError error) {
			assertThat(error.getMessage()).isEqualTo("Expect Content-Type to be " + oneOf + "<" + Joiner.on(", ").join(expecteds) + "> but was <foo>");
		}
	}

	private void checkRange(int start, int end, VoidClojure fn) {
		for (int i = start; i <= end; i++) {
			when(response.getStatusCode()).thenReturn(i);
			fn.apply();
		}

		int test = start - 1;
		when(response.getStatusCode()).thenReturn(test);
		try {
			fn.apply();
		}
		catch (AssertionError error) {
			assertThat(error.getMessage()).isEqualTo("Expected status code to be between <" + start + "> and <" + end + "> but was <" + test + ">");
		}
	}

	private void checkStatus(int status, VoidClojure fn) {
		when(response.getStatusCode()).thenReturn(status);
		fn.apply();

		int errorStatus = status + 1;
		when(response.getStatusCode()).thenReturn(errorStatus);
		try {
			fn.apply();
		}
		catch (AssertionError error) {
			assertThat(error.getMessage()).isEqualTo("Expected status code to be <" + status + "> but was <" + errorStatus + ">");
		}
	}
}
