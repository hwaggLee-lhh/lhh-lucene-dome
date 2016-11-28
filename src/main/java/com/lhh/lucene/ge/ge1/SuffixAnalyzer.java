package com.lhh.lucene.ge.ge1;


import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.Tokenizer;

public final class SuffixAnalyzer extends Analyzer {  

  @Override  
  protected TokenStreamComponents createComponents(String fieldName) {  
      final Tokenizer source = new SuffixTokenizer();  
      return new TokenStreamComponents(source, source);  
  }  
}  
