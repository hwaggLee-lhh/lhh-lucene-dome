package com.lhh.lucene.ge.ge1;

import java.util.Map;

import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.util.TokenizerFactory;
import org.apache.lucene.util.AttributeFactory;
  
/**
 * 工厂类的作用就是产生分词器类的实例。
 * @author hwaggLee
 * @createDate 2016年11月1日
 */
public class SuffixTokenizerFactory extends TokenizerFactory {  
  
    public SuffixTokenizerFactory(Map<String, String> args) {  
        super(args);  
    }  
  
    @Override  
    public Tokenizer create(AttributeFactory factory) {  
        return new SuffixTokenizer(factory);  
    }  
} 
