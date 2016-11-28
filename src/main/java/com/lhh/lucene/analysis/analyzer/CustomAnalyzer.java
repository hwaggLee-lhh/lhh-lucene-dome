package com.lhh.lucene.analysis.analyzer;

import java.io.IOException;
import java.io.StringReader;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.core.LowerCaseTokenizer;
import org.apache.lucene.analysis.core.StopFilter;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.util.CharArraySet;

import com.lhh.lucene.analysis.analyzer.filter.CustomFilter;

/**
 * 自定义分词器
 * @author lhh
 * @createDate 2016年10月12日
 */
public class CustomAnalyzer extends Analyzer{
	

    private CharArraySet stops;
    
    public CustomAnalyzer(){
    	stops = new CharArraySet(0, true);
    }
    
    /**
     * 在原来停用词基础上增加自己的停用词
     * @param stopwords    自定义停用词采用数组传递
     */
    public CustomAnalyzer(String[] stopwords){
        stops = StopFilter.makeStopSet(stopwords,true);
    }
    
	@Override
	protected TokenStreamComponents createComponents(String fieldName) {
        Tokenizer source = new LowerCaseTokenizer();
        return new TokenStreamComponents(source, CustomFilter.getInsence(source,stops));
	}
	
    
    /**
     *
     * Description:         查看分词信息
     * @param str        待分词的字符串
     * @param analyzer    分词器
     *
     */
    public static void displayToken(String str,Analyzer analyzer){
        try {
            //将一个字符串创建成Token流
            TokenStream stream  = analyzer.tokenStream("value", new StringReader(str));
            stream.reset();
            //保存相应词汇
            CharTermAttribute cta = stream.addAttribute(CharTermAttribute.class);
            while(stream.incrementToken()){
                System.out.print("[" + cta + "]");
            }
            System.out.println();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static void main(String[] args) {
        //获取原来的停用词
        Analyzer myAnalyzer1 = new CustomAnalyzer();
        //追加自己的停用词
        Analyzer myAnalyzer2 = new CustomAnalyzer(new String[]{"hate","fuck"});
        //分词处理的句子
        String text = "fuck! I hate you very much,我是中国人";
        
        displayToken(text, myAnalyzer1);
        displayToken(text, myAnalyzer2);
    }

	
}
