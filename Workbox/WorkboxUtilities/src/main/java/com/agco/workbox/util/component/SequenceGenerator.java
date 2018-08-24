package com.agco.workbox.util.component;

public class SequenceGenerator {

	volatile static int n = 1;

	public static synchronized int nextNum() {
		return n++;
	}
}
