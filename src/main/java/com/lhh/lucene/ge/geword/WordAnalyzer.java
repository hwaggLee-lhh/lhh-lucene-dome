package com.lhh.lucene.ge.geword;

import java.io.Reader;
import java.io.StringReader;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.Tokenizer;

import com.lhh.utils.LuceneTokenUtils;

/**
 * 单个单词分词器
 * 
 * @author lhh
 * @createDate 2016年11月8日
 */
public class WordAnalyzer extends Analyzer {
	

	Reader reader;
	public WordAnalyzer() {
	}
	protected TokenStreamComponents createComponents(String fieldName) {
		Tokenizer token = new WordTokenizer();
		return new TokenStreamComponents(token);
	}

	public static void main(String[] args) {
		String str="天气不错132abc@#$+-)(*&^.,/"; 
		System.out.println(str);
		Reader reader = new StringReader(str);
		WordAnalyzer analyzer = new WordAnalyzer();
		LuceneTokenUtils.displayToken(reader, analyzer);
		/*Analyzer aly1 = new StandardAnalyzer();//将数字作为一个整体,每个单词都进行分隔
		Analyzer aly2 = new StopAnalyzer();//将数字停用 中文不起作用,只做空格分割
		Analyzer aly3 = new SimpleAnalyzer();//将数字停用 中文不起作用,只按照空格分割
		Analyzer aly4 = new WhitespaceAnalyzer();//按照空格分隔,中文不起作用
		
		LuceneTokenUtils.displayToken(str, aly1);
		LuceneTokenUtils.displayToken(str, aly2);
		LuceneTokenUtils.displayToken(str, aly3);
		LuceneTokenUtils.displayToken(str, aly4);*/
	}
}
