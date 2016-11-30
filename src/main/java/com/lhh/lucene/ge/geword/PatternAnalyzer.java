package com.lhh.lucene.ge.geword;

import java.io.Reader;
import java.io.StringReader;
import java.util.regex.Pattern;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.pattern.PatternTokenizer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;

/**
 * 正则表达式去分词
 * @author hwaggLee
 * @createDate 2016年11月9日
 */
public class PatternAnalyzer extends Analyzer {

	String regex;// 使用的正则拆分式
	Reader reader;

	public PatternAnalyzer(String regex, Reader arg1) {
		this.regex = regex;
		this.reader = arg1;
	}

	@Override
	protected TokenStreamComponents createComponents(String arg0) {
		Tokenizer tokenizer = new PatternTokenizer(Pattern.compile(regex), -1);
		return new TokenStreamComponents(tokenizer);
	}

	public static void main(String[] args)throws Exception {  
		Reader reader = new StringReader("我#你#他");
		//SplitAnalyzer analyzer=new SplitAnalyzer('#');  
        PatternAnalyzer analyzer=new PatternAnalyzer("我",reader);  
         //空字符串代表单字切分    
        TokenStream ts= analyzer.tokenStream("field", reader);  
        CharTermAttribute term=ts.addAttribute(CharTermAttribute.class);  
        ts.reset();  
        while(ts.incrementToken()){  
            System.out.println(term.toString());  
        }  
        ts.end();  
        ts.close();  
           
    }}
