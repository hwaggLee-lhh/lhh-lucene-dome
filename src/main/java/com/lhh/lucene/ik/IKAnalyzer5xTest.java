package com.lhh.lucene.ik;

import java.io.IOException;
import java.io.StringReader;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.OffsetAttribute;

public class IKAnalyzer5xTest {
	public static void main(String[] args) throws IOException {
		Analyzer analyzer = new IKAnalyzer5x(true);
		TokenStream ts = analyzer.tokenStream("field", new StringReader("我是中国人"));
		OffsetAttribute offsetAtt = ts.addAttribute(OffsetAttribute.class);
		try {
			ts.reset();
			while (ts.incrementToken()) {
				System.out.println(offsetAtt.toString());
			}
			ts.end();
		} finally {
			ts.close();
		}
	}
}
