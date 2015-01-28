package org.rascalmpl.unicode;

import java.io.FilterReader;
import java.io.IOException;
import java.io.Reader;

public class UnicodeOffsetLengthReader extends FilterReader {
	private int charsRead;
	private int offset;
	private int length;
	
	public UnicodeOffsetLengthReader(Reader in, int offset, int len) {
		super(in);
		this.offset = offset;
		this.length = len;
	}
	
	private void offset() throws IOException {
		while (offset > 0) {
			if (!Character.isHighSurrogate((char) super.read())) {
				offset--;
			}
		}
	}
	
	@Override
	public int read() throws IOException {
		offset();
		if (charsRead >= length) {
			return -1;
		}
		
		int res = super.read();
		
		if (res != -1 && !Character.isHighSurrogate((char) res)) {
			charsRead++;
		}
		
		return res;
	}
	
	@Override
	public int read(char[] cbuf, int off, int len) throws IOException {
		offset();
		
		if (this.charsRead >= this.length) {
			// we are at the end already
			return -1;
		}
		
		
		// just get what we can, we will cut the result below
		int res = super.read(cbuf, off, len);
		
		if (res == 0) {
			return res; // unlikely corner case
		}
		
		if (res != -1) {
			// now cut off the result
			int count = 0;
			for (int i = 0; i < res; i++) {
				count++;
				if (!Character.isHighSurrogate(cbuf[i])) {
					charsRead++;
					
					if (charsRead >= length) {
						// done
						return count;
					}
				}
			}
			return count;
		}
		
		return res;
	}
}