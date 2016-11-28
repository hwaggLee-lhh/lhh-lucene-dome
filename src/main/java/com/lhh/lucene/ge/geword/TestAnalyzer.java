package com.lhh.lucene.ge.geword;
import java.io.Reader;
import java.io.StringReader;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
  
public class TestAnalyzer extends Analyzer{  
	Reader reader;
	public TestAnalyzer(Reader reader){
		this.reader = reader;
	}
  
    @Override  
    protected TokenStreamComponents createComponents(String fieldName) {  
        return new TokenStreamComponents(new TestTokenizer(reader));  
    }  
    
    public static void main(String[] args) throws Exception {  
    	String str = "Norther 雪中悍刀行 河北邯郸   AC DF-II-SDFzd(asd)/小时";  
    	Reader reader = new StringReader(str);
    	TestAnalyzer test = new TestAnalyzer(reader);  
        TokenStream ts = test.tokenStream("field",reader);  
        CharTermAttribute c = ts.addAttribute(CharTermAttribute.class);  
        ts.reset();  
        while (ts.incrementToken()) {  
            System.out.println(c.toString());  
        }  
  
        ts.end();  
        ts.close();  
    }  
  
}
