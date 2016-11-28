package com.lhh.lucene.ge.genumber;

import java.io.IOException;

import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.tokenattributes.OffsetAttribute;
import org.apache.lucene.analysis.util.CharacterUtils;
import org.apache.lucene.analysis.util.CharacterUtils.CharacterBuffer;
import org.apache.lucene.util.AttributeFactory;

public abstract class MyCharTokenizer extends Tokenizer {

	public MyCharTokenizer() {
		super();
		charUtils = CharacterUtils.getInstance();
	}

	public MyCharTokenizer(AttributeFactory factory) {
		super(factory);
		charUtils = CharacterUtils.getInstance();
	}

	private int offset = 0, bufferIndex = 0, dataLen = 0, finalOffset = 0;
	private static final int MAX_WORD_LEN = 255;
	private static final int IO_BUFFER_SIZE = 4096;

	private final CharTermAttribute termAtt = addAttribute(CharTermAttribute.class);
	private final OffsetAttribute offsetAtt = addAttribute(OffsetAttribute.class);

	private final CharacterUtils charUtils;
	private final CharacterBuffer ioBuffer = CharacterUtils.newCharacterBuffer(IO_BUFFER_SIZE);

	protected abstract boolean isTokenChar(int c);

	protected int normalize(int c) {
		return c;
	}

	@Override
	public final boolean incrementToken() throws IOException {
		clearAttributes();
		int length = 0;
		int start = -1; // this variable is always initialized
		int end = -1;
		char[] buffer = termAtt.buffer();
		while (true) {
			// 第一次进入，bufferIndex=dataLen=0
			if (bufferIndex >= dataLen) {
				offset += dataLen;// offset=0

				// 将数据拷贝进入ioBuffer
				charUtils.fill(ioBuffer, input); // read supplementary char
													// aware with CharacterUtils

				//
				if (ioBuffer.getLength() == 0) {
					dataLen = 0; // so next offset += dataLen won't decrement
									// offset
					if (length > 0) {
						break;
					} else {
						finalOffset = correctOffset(offset);
						// 返回false，表示分词结束
						return false;
					}
				}

				// 重置数据的长度
				dataLen = ioBuffer.getLength();

				// 重置起始位置
				bufferIndex = 0;
			}

			// use CharacterUtils here to support < 3.1 UTF-16 code unit
			// behavior if the char based methods are gone
			// 取得ioBuffer中第bufferIndex位的字符
			final int c = charUtils.codePointAt(ioBuffer.getBuffer(),
					bufferIndex, ioBuffer.getLength());
			// 获得字符长度
			final int charCount = Character.charCount(c);

			// 起始位置加charCount
			bufferIndex += charCount;
			//
			if (isTokenChar(c)) { // if it's a token char
				if (length == 0) { // start of token
					assert start == -1;
					start = offset + bufferIndex - charCount;
					end = start;
				} else if (length >= buffer.length - 1) { // check if a
															// supplementary
															// could run out of
															// bounds
					buffer = termAtt.resizeBuffer(2 + length); // make sure a
																// supplementary
																// fits in the
																// buffer
				}
				end += charCount;
				length += Character.toChars(normalize(c), buffer, length); // buffer
																			// it,
																			// normalized
				if (length >= MAX_WORD_LEN) { // buffer overflow! make sure to
												// check for >= surrogate pair
												// could break == test
					break;
				}
			} else if (length > 0) { // at non-Letter w/ chars
				// length++;
				// end++;

				if (length == 0) { // start of token
					assert start == -1;
					start = offset + bufferIndex - charCount;
					end = start;
				} else if (length >= buffer.length - 1) { // check if a
															// supplementary
															// could run out of
															// bounds
					buffer = termAtt.resizeBuffer(2 + length); // make sure a
																// supplementary
																// fits in the
																// buffer
				}
				end += charCount;
				length += Character.toChars(normalize(c), buffer, length); // buffer
																			// it,
																			// normalized
				if (length >= MAX_WORD_LEN) { // buffer overflow! make sure to
												// check for >= surrogate pair
												// could break == test
					break;
				}

				break; // return 'em
			}
		}
		termAtt.setLength(length);
		assert start != -1;
		offsetAtt.setOffset(correctOffset(start),
				finalOffset = correctOffset(end));
		return true;
	}

	@Override
	public final void end() throws IOException {
		super.end();
		// set final offset
		offsetAtt.setOffset(finalOffset, finalOffset);
	}

	@Override
	public void reset() throws IOException {
		super.reset();
		bufferIndex = 0;
		offset = 0;
		dataLen = 0;
		finalOffset = 0;
		ioBuffer.reset(); // make sure to reset the IO buffer!!
	}
}
