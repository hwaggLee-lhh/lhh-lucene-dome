package com.lhh.utils;

import java.io.IOException;
import java.io.Reader;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;

public class LuceneTokenUtils {

    /**
     *
     * Description:         查看分词信息
     * @param reader        待分词的字符串（new StringReader(str)）
     * @param analyzer    分词器
     *
     */
    public static void displayToken(Reader reader,Analyzer analyzer){
        try {
            //将一个字符串创建成Token流
            TokenStream stream  = analyzer.tokenStream("value", reader);
            stream.reset();
            //保存相应词汇

            CharTermAttribute cta = stream.addAttribute(CharTermAttribute.class);
            System.out.print("[" + analyzer.getClass().getSimpleName() + "]");
            while(stream.incrementToken()){
                System.out.print("[" + cta + "]");
            }
            System.out.println();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
}
