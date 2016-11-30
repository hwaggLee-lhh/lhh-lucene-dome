package com.lhh.lucene.ge.geword;

import java.io.StringReader;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;

/**
 * 自定义单个char字符分词器
 * 
 * @author hwaggLee
 * @createDate 2016年11月9日
 */
public class SplitAnalyzer extends Analyzer {
	char c;// 按特定符号进行拆分
	public SplitAnalyzer(char c) {
		this.c = c;
	}

	@Override
	protected TokenStreamComponents createComponents(String arg0) {
		Tokenizer token = new SplitTokenizer(new StringReader(arg0),c);
		return new TokenStreamComponents(token);
	}

	public static void main(String[] args) throws Exception {
		SplitAnalyzer analyzer = new SplitAnalyzer('#');
		// SplitAnalyzer analyzer=new SplitAnalyzer('+');
		// PatternAnalyzer analyzer=new PatternAnalyzer("abc");
		TokenStream ts = analyzer.tokenStream("field","我#你#他");
		// TokenStream ts= analyzer.tokenStream("field", new
		// StringReader("我+你+他"));
		CharTermAttribute term = ts.addAttribute(CharTermAttribute.class);
		ts.reset();
		while (ts.incrementToken()) {
			System.out.println(term.toString());
		}
		ts.end();
		ts.close();
	}
}
