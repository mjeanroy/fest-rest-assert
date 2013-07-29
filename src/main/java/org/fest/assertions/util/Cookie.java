package org.fest.assertions.util;

import java.util.Date;

public class Cookie {

	private String name;
	private String value;
	private String domain;
	private String path;
	private int maxAge;
	private Date expiryDate;
	private boolean secure;
	private boolean httpOnly;

	public Cookie() {
	}

	public Cookie(String name, String value) {
		this.name = name;
		this.value = value;
	}

	public Cookie(javax.servlet.http.Cookie cookie) {
		this(cookie.getName(), cookie.getValue(), cookie.getDomain(), cookie.getPath(), cookie.getMaxAge(), cookie.getSecure(), cookie.isHttpOnly());
	}

	public Cookie(com.ning.http.client.Cookie cookie) {
		this(cookie.getName(), cookie.getValue(), cookie.getDomain(), cookie.getPath(), cookie.getMaxAge(), cookie.isSecure(), cookie.isHttpOnly());
	}

	public Cookie(org.apache.http.cookie.Cookie cookie) {
		this(cookie.getName(), cookie.getValue(), cookie.getDomain(), cookie.getPath(), cookie.getExpiryDate(), cookie.isSecure(), false);
	}

	public Cookie(String name, String value, String domain, String path, int maxAge, boolean secure, boolean httpOnly) {
		this.name = name;
		this.value = value;
		this.domain = domain;
		this.path = path;
		this.maxAge = maxAge;
		this.secure = secure;
		this.httpOnly = httpOnly;
		if (maxAge > 0) {
			expiryDate = new Date(System.currentTimeMillis() + maxAge * 1000L);
		}
	}

	public Cookie(String name, String value, String domain, String path, Date expiryDate, boolean secure, boolean httpOnly) {
		this.name = name;
		this.value = value;
		this.domain = domain;
		this.path = path;
		this.expiryDate = expiryDate;
		this.secure = secure;
		this.httpOnly = httpOnly;
		if (expiryDate == null) {
			maxAge = -1;
		}
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public int getMaxAge() {
		return maxAge;
	}

	public void setMaxAge(int maxAge) {
		this.maxAge = maxAge;
	}

	public boolean isSecure() {
		return secure;
	}

	public void setSecure(boolean secure) {
		this.secure = secure;
	}

	public boolean isHttpOnly() {
		return httpOnly;
	}

	public void setHttpOnly(boolean httpOnly) {
		this.httpOnly = httpOnly;
	}
}
