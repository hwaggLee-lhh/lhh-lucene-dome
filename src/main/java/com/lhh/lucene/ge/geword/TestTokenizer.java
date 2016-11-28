package com.lhh.lucene.ge.geword;

import java.io.IOException;
import java.io.Reader;

import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.tokenattributes.OffsetAttribute;
import org.apache.lucene.util.AttributeFactory;

public class TestTokenizer extends Tokenizer{

	private final StringBuilder buffer = new StringBuilder();

	private int tokenStart = 0, tokenEnd = 0;
	private final static String PUNCTION = " -()/";
	private final CharTermAttribute termAtt = addAttribute(CharTermAttribute.class);
	private final OffsetAttribute offsetAtt = addAttribute(OffsetAttribute.class);

	// private final TypeAttribute typeAtt = addAttribute(TypeAttribute.class);

	public TestTokenizer(Reader reader) {  
        super();  
        try {
			super.setReader(reader);
		} catch (IOException e) {
			e.printStackTrace();
		}
    }  
  
    public TestTokenizer(AttributeFactory factory, Reader input) {  
        super(factory);  
        try {
			super.setReader(input);
		} catch (IOException e) {
			e.printStackTrace();
		}
    } 

	@Override
	public boolean incrementToken() throws IOException {
		clearAttributes();
		buffer.setLength(0);
		int ci;
		char ch;
		tokenStart = tokenEnd;

		ci = input.read();
		if (ci > 64 && ci < 91) {
			ci = ci + 32;
		}
		ch = (char) ci;
		while (true) {
			if (ci == -1) {
				if (buffer.length() == 0)
					return false;
				else {
					termAtt.setEmpty().append(buffer);
					offsetAtt.setOffset(correctOffset(tokenStart),
							correctOffset(tokenEnd));
					return true;
				}
			} else if (PUNCTION.indexOf(ch) != -1) {
				// buffer.append(ch);
				tokenEnd++;
				if (buffer.length() > 0) {
					termAtt.setEmpty().append(buffer);
					offsetAtt.setOffset(correctOffset(tokenStart),
							correctOffset(tokenEnd));
					return true;
				} else {
					ci = input.read();
					if (ci > 64 && ci < 91) {
						ci = ci + 32;
					}
					ch = (char) ci;
				}
			} else {
				buffer.append(ch);
				tokenEnd++;
				ci = input.read();
				if (ci > 64 && ci < 91) {
					ci = ci + 32;
				}
				ch = (char) ci;
			}
		}

	}

	@Override
	public void reset() throws IOException {
		super.reset();
		tokenStart = tokenEnd = 0;
	}

	@Override
	public void end() throws IOException {
		super.end();
		final int finalOffset = correctOffset(tokenEnd);
		offsetAtt.setOffset(finalOffset, finalOffset);
	}
}
