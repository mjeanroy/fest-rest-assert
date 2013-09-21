package org.fest.assertions.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Response {

	/** Status code of response */
	private int statusCode;

	/** Content Type of response */
	private String contentType;

	/** Headers contained in response */
	private Map<String, String> headers;

	/** Cookies stored in response */
	private List<Cookie> cookies;

	public Response() {
		this.headers = new HashMap<String, String>();
		this.cookies = new ArrayList<Cookie>();
	}

	public Response(int statusCode, String contentType) {
		this();
		this.statusCode = statusCode;
		this.contentType = contentType;
	}

	public Response(int statusCode, String contentType, Map<String, String> headers, List<Cookie> cookies) {
		this.statusCode = statusCode;
		this.contentType = contentType;

		this.headers = new HashMap<String, String>();
		if (headers != null) {
			for (Map.Entry<String, String> entries : headers.entrySet()) {
				this.headers.put(entries.getKey().toLowerCase(), entries.getValue());
			}
		}

		this.cookies = cookies == null ? new ArrayList<Cookie>() : cookies;
	}

	public Response(com.ning.http.client.Response response) {
		this();
		statusCode = response.getStatusCode();
		contentType = response.getContentType();

		for (String name : response.getHeaders().keySet()) {
			this.headers.put(name.toLowerCase(), response.getHeader(name));
		}

		for (com.ning.http.client.Cookie cookie : response.getCookies()) {
			this.cookies.add(new Cookie(cookie));
		}
	}

	public Response(org.apache.http.HttpResponse response) {
		this();
		statusCode = response.getStatusLine().getStatusCode();
		contentType = response.getEntity().getContentType().getValue();

		// TODO parse headers
		// TODO parse cookies
	}

	public int getStatusCode() {
		return statusCode;
	}

	public String getContentType() {
		return contentType;
	}

	public Map<String, String> getHeaders() {
		return headers;
	}

	public List<Cookie> getCookies() {
		return cookies;
	}

	/**
	 * Get header value by its name.
	 *
	 * @param name Name of header.
	 * @return Value of header.
	 */
	public String getHeader(String name) {
		return this.headers.get(name.toLowerCase());
	}

	/**
	 * Find cookie by its name.
	 *
	 * @param cookieName Cookie name.
	 * @return Cookie, null if cookie does not exist.
	 */
	public Cookie getCookie(String cookieName) {
		if (cookies != null && !cookies.isEmpty()) {
			for (Cookie cookie : cookies) {
				if (cookie.getName().equalsIgnoreCase(cookieName)) {
					return cookie;
				}
			}
		}
		return null;
	}
}
