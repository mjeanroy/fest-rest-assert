package org.fest.assertions.util;

import java.util.Date;

public class Cookie {

	private String name;
	private String value;
	private String domain;
	private String path;
	private long maxAge;
	private Date expiryDate;
	private boolean secure;
	private boolean httpOnly;

	private Cookie() {
	}

	public Cookie(String name, String value) {
		this.domain = "localhost";
		this.path = "/";
		this.name = name;
		this.value = value;
		this.maxAge = -1;
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

		maxAge = expiryDate == null ? - 1 : expiryDate.getTime() - System.currentTimeMillis();
	}

	public String getName() {
		return name;
	}

	public String getValue() {
		return value;
	}

	public String getDomain() {
		return domain;
	}

	public String getPath() {
		return path;
	}

	public long getMaxAge() {
		return maxAge;
	}

	public boolean isSecure() {
		return secure;
	}

	public boolean isHttpOnly() {
		return httpOnly;
	}

	public Date getExpiryDate() {
		return expiryDate == null ? null : new Date(expiryDate.getTime());
	}
}
