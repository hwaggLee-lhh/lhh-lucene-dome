package com.lhh.lucene.analysis.analyzer.token;

import java.io.IOException;
import java.io.StringReader;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.core.SimpleAnalyzer;
import org.apache.lucene.analysis.core.StopAnalyzer;
import org.apache.lucene.analysis.core.WhitespaceAnalyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.tokenattributes.OffsetAttribute;
import org.apache.lucene.analysis.tokenattributes.PositionIncrementAttribute;
import org.apache.lucene.analysis.tokenattributes.TypeAttribute;

/**
 * 分词分析器解析
 * @author hwaggLee
 * @createDate 2016年10月12日
 */
public class AnalyzerTokenUtils {
	
	
	/**
	 *
	 * Description: 查看分词信息
	 * @param str 待分词的字符串
	 * @param analyzer 分词器
	 *
	 */
	public static void displayToken(String str, Analyzer analyzer) {
		try {
			// 将一个字符串创建成Token流
			TokenStream stream = analyzer.tokenStream("value",new StringReader(str));
			stream.reset();
			// 保存相应词汇
			CharTermAttribute cta = stream.addAttribute(CharTermAttribute.class);
			while (stream.incrementToken()) {
				System.out.print("[" + cta + "]");
			}
			System.out.println();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
     * 
     * Description:  显示分词的全部信息
     * @param str
     * @param analyzer
     *
     */
    public static void displayAllTokenInfo(String str, Analyzer analyzer){
        try {
            //第一个参数只是标识性没有实际作用
            TokenStream stream = analyzer.tokenStream("", new StringReader(str));
            //获取词与词之间的位置增量
            PositionIncrementAttribute postiona = stream.addAttribute(PositionIncrementAttribute.class);
            //获取各个单词之间的偏移量
            OffsetAttribute offseta = stream.addAttribute(OffsetAttribute.class);
            //获取每个单词信息
            CharTermAttribute chara = stream.addAttribute(CharTermAttribute.class);
            //获取当前分词的类型
            TypeAttribute typea = stream.addAttribute(TypeAttribute.class);
            while(stream.incrementToken()){
                System.out.print("位置增量" +postiona.getPositionIncrement()+":\t");
                System.out.println(chara+"\t[" + offseta.startOffset()+" - " + offseta.endOffset() + "]\t<" + typea +">");
            }
            System.out.println();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

	public static void main(String[] args) {
		Analyzer aly1 = new StandardAnalyzer();//将数字作为一个整体,每个单词都进行分隔
		Analyzer aly2 = new StopAnalyzer();//将数字停用 中文不起作用,只做空格分割
		Analyzer aly3 = new SimpleAnalyzer();//将数字停用 中文不起作用,只按照空格分割
		Analyzer aly4 = new WhitespaceAnalyzer();//按照空格分隔,中文不起作用

		String str = "hello kim,I am dennisit,我是 中国人,my email is xxxx@xxx.com, and my QQ is xxxx";

		AnalyzerTokenUtils.displayToken(str, aly1);
		AnalyzerTokenUtils.displayToken(str, aly2);
		AnalyzerTokenUtils.displayToken(str, aly3);
		AnalyzerTokenUtils.displayToken(str, aly4);
	}
}
