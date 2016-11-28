package com.lhh.lucene.analysis.analyzer.filter;

import java.io.IOException;

import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.core.StopAnalyzer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.util.CharArraySet;
import org.apache.lucene.analysis.util.FilteringTokenFilter;

/**
 * 处理分词过滤器
 * @author lhh
 * @createDate 2016年10月12日
 */
public class CustomFilter extends FilteringTokenFilter{
	

	//需要过滤掉的词
    private CharArraySet stopwords;
    //每个分词信息
    private final CharTermAttribute termAtt = (CharTermAttribute)addAttribute(CharTermAttribute.class);

	private CustomFilter(TokenStream in,CharArraySet tokenKeyOnt) {
		super(in);
		this.stopwords = tokenKeyOnt;
	}
	
	public static CustomFilter getInsence(TokenStream in,CharArraySet tokenKeyOnt){
		return new CustomFilter(in,tokenKeyOnt);
	}
	

	public static CustomFilter getInsenceStopAnalyzer(TokenStream in){
		return new CustomFilter(in,StopAnalyzer.ENGLISH_STOP_WORDS_SET);
	}
	
	
	/**
	 * 分词
	 * @return
	 * @throws IOException
	 */
	@Override
	protected boolean accept() throws IOException {
        return !stopwords.contains(termAtt.buffer(), 0, termAtt.length());
	}

}
