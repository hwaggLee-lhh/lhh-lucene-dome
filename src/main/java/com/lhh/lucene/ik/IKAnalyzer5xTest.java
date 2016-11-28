package com.lhh.lucene.ik;

import java.io.IOException;
import java.io.StringReader;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.OffsetAttribute;

public class IKAnalyzer5xTest {
	private static Analyzer analyzer = new IKAnalyzer5x(true);
	public static void main(String[] args) throws IOException {
		TokenStream ts = analyzer.tokenStream("field", new StringReader("我是中国人对方的萨芬物权法的顽强奋斗蔷薇饭店上期"));
		OffsetAttribute offsetAtt = ts.addAttribute(OffsetAttribute.class);
		try {
			ts.reset();
			while (ts.incrementToken()) {
				//System.out.println(offsetAtt.toString());
			}
			ts.end();
		} finally {
			ts.close();
		}
	}
}
