package com.lhh.lucene.ge.genumber;

import java.util.Map;

import org.apache.lucene.analysis.util.TokenizerFactory;
import org.apache.lucene.util.AttributeFactory;

public class MyNumberTokenizerFactory extends TokenizerFactory {
	public MyNumberTokenizerFactory(Map<String, String> args) {
		super(args);
		if (!args.isEmpty()) {
			throw new IllegalArgumentException("Unknown parameters: " + args);
		}
	}

	@Override
	public MyNumberTokenizer create(AttributeFactory factory) {
		return new MyNumberTokenizer(factory);
	}
	
	

}
