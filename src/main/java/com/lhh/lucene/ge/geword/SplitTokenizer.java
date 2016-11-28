package com.lhh.lucene.ge.geword;

import java.io.IOException;
import java.io.Reader;

import org.apache.lucene.analysis.util.CharTokenizer;
import org.apache.lucene.util.AttributeFactory;

/**
 * 按字符拆分 Tokenizer
 * 
 * @author lhh
 * @createDate 2016年11月9日
 */
public class SplitTokenizer extends CharTokenizer {

	char c;

	public SplitTokenizer( Reader input, char c) {
		super();
		try {
			if(input!= null ) super.setReader(input);
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.c = c;
	}

	public SplitTokenizer(AttributeFactory factory, Reader input, char c) {
		super(factory);
		try {
			super.setReader(input);
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.c = c;
	}

	@Override
	protected boolean isTokenChar(int arg0) {
		return arg0 == c ? false : true;
	}

}
