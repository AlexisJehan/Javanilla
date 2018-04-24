/*
MIT License

Copyright (c) 2018 Alexis Jehan

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
*/
package com.github.alexisjehan.javanilla.io.chars;

import java.io.FilterWriter;
import java.io.IOException;
import java.io.Writer;

/**
 * <p>A {@link Writer} decorator that counts the number of chars written from the current position.</p>
 * @since 1.0
 */
public final class CountWriter extends FilterWriter {

	/**
	 * <p>Number of chars written.</p>
	 * @since 1.0
	 */
	private long count = 0L;

	/**
	 * <p>Constructor with a delegated {@code Writer}.</p>
	 * @param writer the delegated {@code Writer}
	 * @throws NullPointerException if the delegated {@code Writer} is {@code null}
	 * @since 1.0
	 */
	public CountWriter(final Writer writer) {
		super(writer);
	}

	@Override
	public void write(final int c) throws IOException {
		out.write(c);
		++count;
	}

	@Override
	public void write(final char[] cbuf, final int off, final int len) throws IOException {
		out.write(cbuf, off, len);
		count += len;
	}

	@Override
	public void write(final String str, final int off, final int len) throws IOException {
		out.write(str, off, len);
		count += len;
	}

	/**
	 * <p>Get the number of chars written.</p>
	 * @return the number of chars written
	 * @since 1.0
	 */
	public long getCount() {
		return count;
	}
}