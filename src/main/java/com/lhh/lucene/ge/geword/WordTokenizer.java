package com.lhh.lucene.ge.geword;

import java.io.IOException;

import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.tokenattributes.OffsetAttribute;

/**
 * 单个分词分裂
 * @author lhh
 * @createDate 2016年11月8日
 */
public class WordTokenizer extends Tokenizer {

	public WordTokenizer() {
		super();
	}

	private int offset = 0, bufferIndex = 0, dataLen = 0;
	private final static int MAX_WORD_LEN = 255;
	private final static int IO_BUFFER_SIZE = 1024;
	private final char[] buffer = new char[MAX_WORD_LEN];
	private final char[] ioBuffer = new char[IO_BUFFER_SIZE];

	private int length;
	private int start;

	private final CharTermAttribute termAtt = addAttribute(CharTermAttribute.class);
	private final OffsetAttribute offsetAtt = addAttribute(OffsetAttribute.class);

	private final void push(char c) {

		//System.out.println("->"+c);
		if (length == 0)
			start = offset - 1; // start of token
		buffer[length++] = Character.toLowerCase(c); // buffer it

	}

	private final boolean flush() {

		//System.out.println(new String(buffer, 0,length));
		if (length > 0) {
			termAtt.copyBuffer(buffer, 0, length);
			offsetAtt.setOffset(correctOffset(start), correctOffset(start+ length));
			return true;
		} else
			return false;
	}

	@Override
	public boolean incrementToken() throws IOException {
		clearAttributes();

		length = 0;
		start = offset;

		while (true) {

			final char c;
			offset++;

			if (bufferIndex >= dataLen) {
				dataLen = input.read(ioBuffer);
				bufferIndex = 0;
			}

			if (dataLen == -1) {
				offset--;
				return flush();
			} else
				c = ioBuffer[bufferIndex++];
			//System.out.print(Character.getType(c));
			switch (Character.getType(c)) {

			case Character.DECIMAL_DIGIT_NUMBER:// 注意此部分不过滤一些熟悉或者字母（中文）
			case Character.LOWERCASE_LETTER:// 注意此部分（标点）
			case Character.UPPERCASE_LETTER:// 注意此部分（字母）
			/* push(c);
			 if (length == MAX_WORD_LEN) return flush();
			 break*/;
			case Character.OTHER_LETTER:
				if (length > 0) {
					bufferIndex--;
					offset--;
					return flush();
				}
				push(c);
				return flush();

			default:
				if (length >= 0)
					return flush();
				break;

			}
		}
	}

	@Override
	public final void end() {
		// set final offset
		final int finalOffset = correctOffset(offset);
		this.offsetAtt.setOffset(finalOffset, finalOffset);
	}

	@Override
	public void reset() throws IOException {
		super.reset();
		offset = bufferIndex = dataLen = 0;
	}

}
