package com.lhh.lucene.test;

import junit.framework.TestCase;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.core.SimpleAnalyzer;
import org.apache.lucene.analysis.core.StopAnalyzer;
import org.apache.lucene.analysis.core.WhitespaceAnalyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;

import com.lhh.lucene.analysis.analyzer.CustomAnalyzer;
import com.lhh.lucene.analysis.analyzer.token.AnalyzerTokenUtils;

/**
 * 测试 分词
 * @author lhh
 * @createDate 2016年11月1日
 */
public class TestAnalyzer extends TestCase{
	
	public void testAnalyzer(){
		System.out.println("-->");
	}


	public static void main(String[] args) {
		Analyzer aly3 = new SimpleAnalyzer();//将数字停用 中文不起作用,只按照空格分割
		Analyzer aly2 = new StopAnalyzer();//将数字停用 中文不起作用,只做空格分割
		Analyzer aly1 = new StandardAnalyzer();//将数字作为一个整体,每个单词都进行分隔
		Analyzer aly4 = new WhitespaceAnalyzer();//按照空格分隔,中文不起作用
		String[] stopwords = {"我"};
		Analyzer aly5 = new CustomAnalyzer(stopwords);
		String str = "hello kim,I am dennisit,我是 中国人,my email is xxxx@xxx.com, and my QQ is xxxx";
		System.out.println(str);
		AnalyzerTokenUtils.displayToken(str, aly1);
		AnalyzerTokenUtils.displayToken(str, aly2);
		AnalyzerTokenUtils.displayToken(str, aly3);
		AnalyzerTokenUtils.displayToken(str, aly4);
		AnalyzerTokenUtils.displayToken(str, aly5);
	}
}
