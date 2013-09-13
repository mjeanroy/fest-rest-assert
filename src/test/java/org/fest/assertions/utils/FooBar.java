package org.fest.assertions.utils;

public class FooBar {

	private Long foo;
	private Long bar;

	public FooBar() {
	}

	public FooBar(Long foo, Long bar) {
		this.foo = foo;
		this.bar = bar;
	}

	public Long getFoo() {
		return foo;
	}

	public Long getBar() {
		return bar;
	}
}
