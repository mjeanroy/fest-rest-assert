package org.fest.assertions.api.rest;

import java.io.IOException;
import java.util.List;

import org.fest.assertions.util.Cookie;
import org.fest.assertions.util.Response;

public class REST {

	/**
	 * Build new assertion object from {@link com.ning.http.client.Response} object.
	 *
	 * @param response Original response object.
	 * @return Assertion object.
	 */
	public static ResponseAssert assertThat(com.ning.http.client.Response response) {
		return new ResponseAssert(new Response(response));
	}

	/**
	 * Build new assertion object from {@link org.apache.http.HttpResponse} object.
	 *
	 * @param response Original response object.
	 * @return Assertion object.
	 */
	public static ResponseAssert assertThat(org.apache.http.HttpResponse response) {
		return new ResponseAssert(new Response(response));
	}

	/**
	 * Build new assertion object from {@link Response} object.
	 *
	 * @param response Original response object.
	 * @return Assertion object.
	 */
	public static ResponseAssert assertThat(Response response) {
		return new ResponseAssert(response);
	}

	/**
	 * Build new cookie assertion object.
	 *
	 * @param cookie Cookie object.
	 * @return Assertion object.
	 */
	public static CookieAssert assertThat(Cookie cookie) {
		return new CookieAssert(cookie);
	}

	/**
	 * Build new cookie assertion object from {@link org.apache.http.HttpResponse} object.
	 *
	 * @param response Original response object.
	 * @return Assertion object.
	 */
	public static CookieAssert assertCookieThat(String cookieName, com.ning.http.client.Response response) {
		return new CookieAssert(extractCookie(cookieName, response));
	}

	/**
	 * Build new cookie assertion object from {@link org.apache.http.HttpResponse} object.
	 *
	 * @param response Original response object.
	 * @return Assertion object.
	 */
	public static JsonAssert assertJsonThat(com.ning.http.client.Response response) {
		return new JsonAssert(extractJson(response));
	}

	/**
	 * Extract cookie from {@link org.apache.http.HttpResponse} object.
	 *
	 * @param name Name of cookie to extract.
	 * @param response Original response object.
	 * @return Cookie object.
	 */
	public static Cookie extractCookie(String name, com.ning.http.client.Response response) {
		List<com.ning.http.client.Cookie> cookies = response.getCookies();
		for (com.ning.http.client.Cookie cookie : cookies) {
			if (cookie.getName().equals(name)) {
				return new Cookie(cookie);
			}
		}
		return null;
	}

	/**
	 * Extract json from {@link org.apache.http.HttpResponse} object.
	 *
	 * @param response Original response object.
	 * @return Json response.
	 */
	public static String extractJson(com.ning.http.client.Response response) {
		try {
			return response.getResponseBody();
		}
		catch (IOException ex) {
			throw new AssertionError("Cannot extract JSON body from response", ex);
		}
	}
}
