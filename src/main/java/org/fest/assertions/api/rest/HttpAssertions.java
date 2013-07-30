package org.fest.assertions.api.rest;

import java.util.List;

import org.fest.assertions.util.Cookie;
import org.fest.assertions.util.Response;

public class HttpAssertions {

	public static ResponseAssert from(com.ning.http.client.Response response) {
		return new ResponseAssert(new Response(response));
	}

	public static ResponseAssert from(org.apache.http.HttpResponse response) {
		return new ResponseAssert(new Response(response));
	}

	public static Cookie extractCookie(String name, com.ning.http.client.Response response) {
		List<com.ning.http.client.Cookie> cookies = response.getCookies();
		for (com.ning.http.client.Cookie cookie : cookies) {
			if (cookie.getName().equals(name)) {
				return new Cookie(cookie);
			}
		}
		return null;
	}
}
