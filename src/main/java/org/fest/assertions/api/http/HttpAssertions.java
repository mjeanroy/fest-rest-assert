package org.fest.assertions.api.http;

import java.util.List;

import org.fest.assertions.util.Cookie;
import org.fest.assertions.util.HttpResponse;

public class HttpAssertions {

	public static HttpResponseAssert from(com.ning.http.client.Response response) {
		return new HttpResponseAssert(new HttpResponse(response));
	}

	public static HttpResponseAssert from(org.apache.http.HttpResponse response) {
		return new HttpResponseAssert(new HttpResponse(response));
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
