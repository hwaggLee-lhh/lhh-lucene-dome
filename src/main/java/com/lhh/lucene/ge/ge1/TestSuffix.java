package com.lhh.lucene.ge.ge1;

import java.io.IOException;

import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;

/**
 * 自定义分词器测试
 * (注：集团的内部通讯工具搜同事时，需要根据姓名后缀进行搜索。譬如“徐欢春”，我们要能根据“欢春”搜出这个人；“黄继刚”，要根据“继刚”为关键字搜出“黄继刚”。这是个很人性化的用户体验，当我们有同事的名字是三个字的时候，我们通常会叫他们名字的最后两个字。Lucene本身并没有提供这种分词器，只能自己照着Lucene已有的分词器进行模仿开发。)
 * @author hwaggLee
 * @createDate 2016年11月1日
 */
public class TestSuffix {
	
	@SuppressWarnings("resource")
	public static void main(String[] args) {
		try {
			SuffixAnalyzer analyzer = new SuffixAnalyzer();
			TokenStream ts = analyzer.tokenStream("text", "黄继刚");
			CharTermAttribute term = ts.addAttribute(CharTermAttribute.class);
			ts.reset();
			while (ts.incrementToken()) {
				System.out.println(term.toString());
			}
			ts.end();
			ts.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
