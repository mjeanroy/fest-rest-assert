package org.fest.assertions.api.http;

import java.util.List;

import org.fest.assertions.api.AbstractAssert;
import org.fest.assertions.api.Assertions;
import org.fest.assertions.util.Cookie;
import org.fest.assertions.util.Response;

public class ResponseAssert extends AbstractAssert<ResponseAssert, Response> {

	public ResponseAssert(Response actual) {
		super(actual, ResponseAssert.class);
	}

	/**
	 * Check if http code status is between 200 and 299 (which means success status).
	 *
	 * @return {@code this} the assertion object.
	 */
	public ResponseAssert isSuccess() {
		return isStatusBetween(200, 299);
	}

	/**
	 * Check if http code status is between 300 and 399 (which means redirection status).
	 *
	 * @return {@code this} the assertion object.
	 */
	public ResponseAssert isRedirection() {
		return isStatusBetween(300, 399);
	}

	/**
	 * Check if http code status is between 400 and 499 (which means client-side error).
	 *
	 * @return {@code this} the assertion object.
	 */
	public ResponseAssert isClientError() {
		return isStatusBetween(400, 499);
	}

	/**
	 * Check if http code status is between 500 and 599 (which means server error).
	 *
	 * @return {@code this} the assertion object.
	 */
	public ResponseAssert isServerError() {
		return isStatusBetween(500, 599);
	}

	/**
	 * Check if http code status is 200 (a.k.a 'OK').
	 *
	 * @return {@code this} the assertion object.
	 */
	public ResponseAssert isOk() {
		return isStatusEqualTo(200);
	}

	/**
	 * Check if http code status is 202 (a.k.a 'ACCEPTED').
	 *
	 * @return {@code this} the assertion object.
	 */
	public ResponseAssert isAccepted() {
		return isStatusEqualTo(202);
	}

	/**
	 * Check if http code status is 201 (a.k.a 'CREATED').
	 *
	 * @return {@code this} the assertion object.
	 */
	public ResponseAssert isCreated() {
		return isStatusEqualTo(201);
	}

	/**
	 * Check if http code status is 203 (a.k.a 'NON-AUTHORITATIVE INFORMATION').
	 *
	 * @return {@code this} the assertion object.
	 */
	public ResponseAssert isNonAuthoritativeInformation() {
		return isStatusEqualTo(203);
	}

	/**
	 * Check if http code status is 204 (a.k.a 'NO CONTENT').
	 *
	 * @return {@code this} the assertion object.
	 */
	public ResponseAssert isNoContent() {
		return isStatusEqualTo(204);
	}

	/**
	 * Check if http code status is 205 (a.k.a 'RESET CONTENT').
	 *
	 * @return {@code this} the assertion object.
	 */
	public ResponseAssert isResetContent() {
		return isStatusEqualTo(205);
	}

	/**
	 * Check if http code status is 206 (a.k.a 'PARTIAL CONTENT').
	 *
	 * @return {@code this} the assertion object.
	 */
	public ResponseAssert isPartialContent() {
		return isStatusEqualTo(206);
	}

	/**
	 * Check if http code status is 300 (a.k.a 'MULTIPLE CHOICES').
	 *
	 * @return {@code this} the assertion object.
	 */
	public ResponseAssert isMultipleChoice() {
		return isStatusEqualTo(300);
	}

	/**
	 * Check if http code status is 301 (a.k.a 'MOVED PERMANENTLY').
	 *
	 * @return {@code this} the assertion object.
	 */
	public ResponseAssert isMovedPermanently() {
		return isStatusEqualTo(301);
	}

	/**
	 * Check if http code status is 302 (a.k.a 'MOVED TEMPORARILY').
	 *
	 * @return {@code this} the assertion object.
	 */
	public ResponseAssert isMovedTemporarily() {
		return isStatusEqualTo(302);
	}

	/**
	 * Check if http code status is 303 (a.k.a 'SEE OTHER').
	 *
	 * @return {@code this} the assertion object.
	 */
	public ResponseAssert isSeeOther() {
		return isStatusEqualTo(303);
	}

	/**
	 * Check if http code status is 304 (a.k.a 'NOT MODIFIED').
	 *
	 * @return {@code this} the assertion object.
	 */
	public ResponseAssert isNotModified() {
		return isStatusEqualTo(304);
	}

	/**
	 * Check if http code status is 400 (a.k.a 'BAD REQUEST').
	 *
	 * @return {@code this} the assertion object.
	 */
	public ResponseAssert isBadRequest() {
		return isStatusEqualTo(400);
	}

	/**
	 * Check if http code status is 401 (a.k.a 'UNAUTHORIZED').
	 *
	 * @return {@code this} the assertion object.
	 */
	public ResponseAssert isUnauthorized() {
		return isStatusEqualTo(401);
	}

	/**
	 * Check if http code status is 403 (a.k.a 'FORBIDDEN').
	 *
	 * @return {@code this} the assertion object.
	 */
	public ResponseAssert isForbidden() {
		return isStatusEqualTo(403);
	}

	/**
	 * Check if http code status is 404 (a.k.a 'NOT FOUND').
	 *
	 * @return {@code this} the assertion object.
	 */
	public ResponseAssert isNotFound() {
		return isStatusEqualTo(404);
	}

	/**
	 * Check if http code status is 405 (a.k.a 'METHOD NOT ALLOWED').
	 *
	 * @return {@code this} the assertion object.
	 */
	public ResponseAssert isMethodNotAllowed() {
		return isStatusEqualTo(405);
	}

	/**
	 * Check if http code status is 500 (a.k.a 'INTERNAL SERVER ERROR').
	 *
	 * @return {@code this} the assertion object.
	 */
	public ResponseAssert isInternalServerError() {
		return isStatusEqualTo(500);
	}

	/**
	 * Check if http code status is 501 (a.k.a 'NOT IMPLEMENTED').
	 *
	 * @return {@code this} the assertion object.
	 */
	public ResponseAssert isNotImplemented() {
		return isStatusEqualTo(501);
	}

	/**
	 * Check if http code status is equal to expected value.
	 *
	 * @param expected Expected value.
	 * @return {@code this} the assertion object.
	 */
	public ResponseAssert isStatusEqualTo(int expected) {
		isNotNull();
		Assertions.assertThat(actual.getStatusCode())
				.overridingErrorMessage("Expected status code to be <%s> but was <%s>", expected, actual.getStatusCode())
				.isEqualTo(expected);
		return this;
	}

	/**
	 * Check if http code status is equal to expected value.
	 *
	 * @param expected Expected value.
	 * @return {@code this} the assertion object.
	 */
	public ResponseAssert isStatusNotEqualTo(int expected) {
		isNotNull();
		Assertions.assertThat(actual.getStatusCode())
				.overridingErrorMessage("Expected status code not to be <%s> but was <%s>", expected, actual.getStatusCode())
				.isNotEqualTo(expected);
		return this;
	}

	/**
	 * Check if http code status is between expected values.
	 *
	 * @param start Start value (inclusive).
	 * @param end End value (inclusive).
	 * @return {@code this} the assertion object.
	 */
	public ResponseAssert isStatusBetween(int start, int end) {
		isNotNull();
		Assertions.assertThat(actual.getStatusCode())
				.overridingErrorMessage("Expected status code to be between <%s> and <%s> but was <%s>", start, end, actual.getStatusCode())
				.isGreaterThanOrEqualTo(start)
				.isLessThanOrEqualTo(end);
		return this;
	}

	/**
	 * Check if http code status is between expected values.
	 *
	 * @param start Start value (inclusive).
	 * @param end End value (inclusive).
	 * @return {@code this} the assertion object.
	 */
	public ResponseAssert isStatusNotBetween(int start, int end) {
		isNotNull();
		Assertions.assertThat(actual.getStatusCode() < start || actual.getStatusCode() > end)
				.overridingErrorMessage("Expected status code not to be between <%s> and <%s> but was <%s>", start, end, actual.getStatusCode())
				.isTrue();
		return this;
	}

	public ResponseAssert hasHeader(String headerName) {
		isNotNull();

		Assertions.assertThat(actual.getHeaders().containsKey(headerName))
				.overridingErrorMessage("Expected header <%s> to be defined", headerName)
				.isTrue();

		return this;
	}

	/**
	 * Check if mime type is octet-stream (a.k.a 'application/octet-stream').
	 * See RFC 2046.
	 *
	 * @return {@code this} the assertion object.
	 */
	public ResponseAssert isOctetStream() {
		return isMimeTypeTypeEqualTo("application/octet-stream");
	}

	/**
	 * Check if mime type is json (a.k.a 'application/json').
	 * See RFC 4627.
	 *
	 * @return {@code this} the assertion object.
	 */
	public ResponseAssert isJson() {
		return isMimeTypeTypeEqualTo("application/json");
	}

	/**
	 * Check if mime type is xml (a.k.a 'application/xml').
	 * See RFC 3023.
	 *
	 * @return {@code this} the assertion object.
	 */
	public ResponseAssert isXml() {
		return isMimeTypeIn("application/xml", "text/xml");
	}

	/**
	 * Check if mime type is css (a.k.a 'text/css').
	 * See RFC 2318.
	 *
	 * @return {@code this} the assertion object.
	 */
	public ResponseAssert isCss() {
		return isMimeTypeTypeEqualTo("text/css");
	}

	/**
	 * Check if mime type is javascript (a.k.a 'text/javascript' or 'application/javascript').
	 * See RFC 4329.
	 * Note : 'text/javascript' is deprecated but a possible value.
	 *
	 * @return {@code this} the assertion object.
	 */
	public ResponseAssert isJavascript() {
		return isMimeTypeIn("application/javascript", "text/javascript");
	}

	/**
	 * Check if mime type is text plain (a.k.a 'text/plain').
	 * See RFC 2049 and RFC 3676.
	 *
	 * @return {@code this} the assertion object.
	 */
	public ResponseAssert isTextPlain() {
		return isMimeTypeTypeEqualTo("text/plain");
	}

	/**
	 * Check if mime type is html (a.k.a 'text/html').
	 * See RFC 2854.
	 *
	 * @return {@code this} the assertion object.
	 */
	public ResponseAssert isHtml() {
		return isMimeTypeTypeEqualTo("text/html");
	}

	/**
	 * Check if mime type is xhtml (a.k.a 'application/xhtml+xml').
	 * See RFC 3023.
	 *
	 * @return {@code this} the assertion object.
	 */
	public ResponseAssert isXhtml() {
		return isMimeTypeTypeEqualTo("application/xhtml+xml");
	}

	/**
	 * Check if mime type is html or xhtml (a.k.a 'text/html' or 'application/xhtml+xml').
	 * See RFC 2854 and RFC 3023.
	 *
	 * @return {@code this} the assertion object.
	 */
	public ResponseAssert isHtmlOrXhtml() {
		return isMimeTypeIn("text/html", "application/xhtml+xml");
	}

	/**
	 * Check if mime type is pdf (a.k.a 'application/pdf').
	 * See RFC 3778.
	 *
	 * @return {@code this} the assertion object.
	 */
	public ResponseAssert isPdf() {
		return isMimeTypeTypeEqualTo("application/pdf");
	}

	/**
	 * Check if mime type is csv (a.k.a 'text/csv').
	 * See RFC 4180.
	 *
	 * @return {@code this} the assertion object.
	 */
	public ResponseAssert isCsv() {
		return isMimeTypeTypeEqualTo("text/csv");
	}

	/**
	 * Check if mime type is zip (a.k.a 'application/zip').
	 *
	 * @return {@code this} the assertion object.
	 */
	public ResponseAssert isZip() {
		return isMimeTypeTypeEqualTo("application/zip");
	}

	/**
	 * Check if mime type is flash content (a.k.a 'application/x-shockwave-flash' or 'video/x-flv').
	 *
	 * @return {@code this} the assertion object.
	 */
	public ResponseAssert isFlashContent() {
		return isMimeTypeIn("application/x-shockwave-flash", "video/x-flv");
	}

	/**
	 * Check if Content-Type is defined in response.
	 *
	 * @return {@code this} the assertion object.
	 */
	public ResponseAssert hasContentType() {
		isNotNull();
		String contentType = actual.getContentType();
		Assertions.assertThat(contentType)
				.overridingErrorMessage("Expect Content-Type to be defined and not empty")
				.isNotNull().isNotEmpty();
		return this;
	}

	/**
	 * Check if mime type is equal to a given value.
	 *
	 * @param expected Expected value.
	 * @return {@code this} the assertion object.
	 */
	public ResponseAssert isMimeTypeTypeEqualTo(String expected) {
		hasContentType();
		String type = actual.getContentType().split(";")[0].toLowerCase();
		Assertions.assertThat(type)
				.overridingErrorMessage("Expect Content-Type to be <%s> but was <%s>", expected, type)
				.isEqualTo(expected);
		return this;
	}

	/**
	 * Check if mime type is in a list of expected values.
	 *
	 * @param expecteds Expected values.
	 * @return {@code this} the assertion object.
	 */
	public ResponseAssert isMimeTypeIn(String... expecteds) {
		hasContentType();
		String type = actual.getContentType().split(";")[0].toLowerCase();
		Assertions.assertThat(type)
				.overridingErrorMessage("Expect Content-Type to be one of <%s> but was <%s>", join(", ", expecteds), type)
				.isIn(expecteds);
		return this;
	}

	/**
	 * Check if a charset value is defined in Content-Type of response.
	 *
	 * @return {@code this} the assertion object.
	 */
	public ResponseAssert hasCharset() {
		hasContentType();
		String[] type = actual.getContentType().split(";");
		Assertions.assertThat(type)
				.overridingErrorMessage("Expect charset to be defined in Content-Type value")
				.hasSize(2);

		String charset = type[1].replace(" ", "").toLowerCase();
		Assertions.assertThat(charset)
				.overridingErrorMessage("Expect charset not to be empty in Content-Type value")
				.isNotEmpty()
				.startsWith("charset=");

		return this;
	}

	/**
	 * Check if charset value in Content-Type is equal to an expected value.
	 *
	 * @param expected Expected value.
	 * @return {@code this} the assertion object.
	 */
	public ResponseAssert isCharsetEqualTo(String expected) {
		hasCharset();
		String charset = actual.getContentType().split(";")[1].trim().replace(" ", "").replace("charset=", "");
		Assertions.assertThat(charset)
				.overridingErrorMessage("Expect charset to be <%s> but was <%s>", expected, charset)
				.isEqualToIgnoringCase(expected);
		return this;
	}

	/**
	 * Check if charset value in Content-Type is equal to 'utf-8' (case insensitive).
	 *
	 * @return {@code this} the assertion object.
	 */
	public ResponseAssert isUtf8() {
		return isCharsetEqualTo("utf-8");
	}

	/**
	 * Check if content-type of response is Json (mime type) and Utf-8 (charset).
	 *
	 * @return {@code this} the assertion object.
	 */
	public ResponseAssert isJsonUtf8() {
		return isJson().isUtf8();
	}

	/**
	 * Check if content-type of response is Xml (mime type) and Utf-8 (charset).
	 *
	 * @return {@code this} the assertion object.
	 */
	public ResponseAssert isXmlUtf8() {
		return isXml().isUtf8();
	}

	/**
	 * Check if content-type of response is Html (mime type) and Utf-8 (charset).
	 *
	 * @return {@code this} the assertion object.
	 */
	public ResponseAssert isHtmlUtf8() {
		return isHtmlOrXhtml().isUtf8();
	}

	public ResponseAssert hasETag() {
		isNotNull();
		String header = actual.getHeader("ETag");
		Assertions.assertThat(header)
				.overridingErrorMessage("Expected ETag value to be defined", header)
				.isNotNull()
				.isNotEmpty();
		return this;
	}

	public ResponseAssert hasETagEquals(String etag) {
		hasETag();
		String header = actual.getHeader("ETag");
		Assertions.assertThat(header)
				.overridingErrorMessage("Expected ETag to be <%s> but was <%s>", etag, header)
				.isEqualTo(etag);
		return this;
	}

	private Cookie cookie(String name) {
		List<Cookie> cookies = actual.getCookies();
		Cookie cookie = null;
		for (Cookie c : cookies) {
			if (c.getName().equals(name)) {
				cookie = c;
				break;
			}
		}
		return cookie;
	}

	public ResponseAssert hasCookie(String cookieName) {
		isNotNull();

		Cookie cookie = cookie(cookieName);
		Assertions.assertThat(cookie)
				.overridingErrorMessage("Expected cookie <%s> to be defined", cookieName)
				.isNotNull();

		return this;
	}

	public ResponseAssert hasCookieEqualsTo(String cookieName, String value) {
		hasCookie(cookieName);

		Cookie cookie = cookie(cookieName);
		Assertions.assertThat(cookie.getName())
				.overridingErrorMessage("Expected cookie <%s> to be <%s> but was <%s>", cookieName, value, cookie.getValue())
				.isEqualTo(value);

		return this;
	}

	public ResponseAssert hasCookieEqualsTo(String cookieName, String value, String domain, String path, int maxAge, boolean secure) {
		hasCookieEqualsTo(cookieName, value);

		Cookie cookie = cookie(cookieName);

		Assertions.assertThat(cookie.getDomain())
				.overridingErrorMessage("Expected cookie <%s> to have domain equals to <%s> but was <%s>", cookieName, domain, cookie.getDomain())
				.isEqualTo(domain);

		Assertions.assertThat(cookie.getPath())
				.overridingErrorMessage("Expected cookie <%s> to have path equals to <%s> but was <%s>", cookieName, path, cookie.getPath())
				.isEqualTo(path);

		Assertions.assertThat(cookie.getMaxAge())
				.overridingErrorMessage("Expected cookie <%s> to have max-age equals to <%s> but was <%s>", cookieName, maxAge, cookie.getMaxAge())
				.isEqualTo(maxAge);

		Assertions.assertThat(cookie.isSecure())
				.overridingErrorMessage("Expected cookie <%s> to have secure equals to <%s> but was <%s>", cookieName, secure, cookie.isSecure())
				.isEqualTo(secure);

		return this;
	}

	private String join(String on, String... values) {
		String result = "";
		for (String value : values) {
			result += value + on;
		}
		return result.substring(0, result.length() - on.length());
	}
}
