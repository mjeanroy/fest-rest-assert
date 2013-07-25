package org.fest.assertions.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Response {

	private int statusCode;
	private String contentType;
	private Map<String, String> headers;
	private List<Cookie> cookies;

	public Response() {
		this.headers = new HashMap<String, String>();
		this.cookies = new ArrayList<Cookie>();
	}

	public Response(com.ning.http.client.Response response) {
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
		statusCode = response.getStatusLine().getStatusCode();
		contentType = response.getEntity().getContentType().getValue();

		// TODO parse headers
		// TODO parse cookies
	}

	public int getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public Map<String, String> getHeaders() {
		return headers;
	}

	public void setHeaders(Map<String, String> headers) {
		this.headers = headers;
	}

	public String getHeader(String name) {
		return this.headers.get(name.toLowerCase());
	}

	public List<Cookie> getCookies() {
		return cookies;
	}

	public void setCookies(List<Cookie> cookies) {
		this.cookies = cookies;
	}
}
