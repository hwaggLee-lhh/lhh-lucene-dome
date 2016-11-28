package com.lhh.lucene.ge.genumber;

import java.io.StringReader;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;

public final class MyNumberAnalyzer extends Analyzer {

	public MyNumberAnalyzer() {
	}

	public static void main(String[] args) throws Exception {
		MyNumberAnalyzer analyzer = new MyNumberAnalyzer();
		// WhitespaceAnalyzer analyzer = new WhitespaceAnalyzer();
		TokenStream tokenStream = analyzer.tokenStream("content",new StringReader("123467899988"));
		tokenStream.reset();
		tokenStream.addAttribute(CharTermAttribute.class);
		
		while (tokenStream.incrementToken()) {
			CharTermAttribute termAttribute = tokenStream.getAttribute(CharTermAttribute.class);
			System.out.println(termAttribute.toString());
		}
	}

	@Override
	protected TokenStreamComponents createComponents(String fieldName) {
		return new TokenStreamComponents(new MyNumberTokenizer());
	}
}
