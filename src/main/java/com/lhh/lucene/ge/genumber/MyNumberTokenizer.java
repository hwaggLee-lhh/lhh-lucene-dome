package com.lhh.lucene.ge.genumber;

import org.apache.lucene.util.AttributeFactory;

public final class MyNumberTokenizer extends MyCharTokenizer {

	private long count = 0;

	public MyNumberTokenizer() {
		super();
	}

	public MyNumberTokenizer(AttributeFactory factory) {
		super(factory);
	}

	@Override
	protected boolean isTokenChar(int c) {
		return get();
	}

	public synchronized boolean get() {
		count++;
		if (count % 2 == 0) {
			count = 0L;
			return false;
		}
		return true;
	}
}
