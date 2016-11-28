package com.lhh.lucene.ge.ge1;
import java.io.IOException;

import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.tokenattributes.OffsetAttribute;
import org.apache.lucene.util.AttributeFactory;
  
/** 
 * 用于姓名取后缀的分词器 
 *  
 * @author cdlvsheng 
 *         date : 2014年9月23日 
 *         time : 下午1:49:36 
 *         project : im-solr-web 
 *         user : cdlvsheng 
 */  
public class SuffixTokenizer extends Tokenizer {  
  
  
    private char[]                  buffer      = new char[1024];  
    private int                     suffixOffset;  
    private static int              MAX_SIZE;  
    private int                     termSize;  
  
    private final CharTermAttribute termAtt     = addAttribute(CharTermAttribute.class);  
    private final OffsetAttribute   offsetAtt   = addAttribute(OffsetAttribute.class);  
  
    protected SuffixTokenizer() {  
        super();  
    }  
  
    protected SuffixTokenizer(AttributeFactory factory) {  
        super(factory);  
    }  
  
    /** incrementToken方法是实际分词时用到的方法。一会单元测试时可以看出它的作用。
     * @return
     * @throws IOException
     */
    @Override  
    public boolean incrementToken() throws IOException {  
        try {  
            clearAttributes();  
  
            if (termSize != -1) {  
                termSize = input.read(buffer);  
                if (termSize >= 0) {  
                    MAX_SIZE = termSize;  
                }  
            }  
  
            if (suffixOffset + 1 >= MAX_SIZE) {  
                return false;  
            }  
  
            termAtt.copyBuffer(buffer, suffixOffset, MAX_SIZE);  
  
            termAtt.setLength(MAX_SIZE - suffixOffset);  
            offsetAtt.setOffset(correctOffset(suffixOffset), correctOffset(MAX_SIZE));  
            suffixOffset++;  
        } catch (Exception e) { 
        	e.printStackTrace();
        }  
        return true;  
    }  
  
    @Override  
    public final void end() {  
        final int finalOffset = correctOffset(suffixOffset);  
        this.offsetAtt.setOffset(finalOffset, finalOffset);  
    }  
  
    @Override  
    public void reset() throws IOException {  
        super.reset();  
        termSize = 1;  
        suffixOffset = 1;  
        MAX_SIZE = 0;  
    }  
} 
