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
package com.github.javanilla.io.lines;

import java.io.IOException;

/**
 * <p>A {@link LineWriter} decorator that counts the number of lines written from the current position.</p>
 * @since 1.0
 */
public final class CountLineWriter extends FilterLineWriter {

	/**
	 * <p>Number of lines written.</p>
	 * @since 1.0
	 */
	private long count = 0L;

	/**
	 * <p>Constructor with a delegated {@code LineWriter}.</p>
	 * @param lineWriter the delegated {@code LineWriter}
	 * @throws NullPointerException if the delegated {@code LineWriter} is {@code null}
	 * @since 1.0
	 */
	public CountLineWriter(final LineWriter lineWriter) {
		super(lineWriter);
	}

	@Override
	public void write(final String line) throws IOException {
		super.write(line);
		++count;
	}

	@Override
	public void newLine() throws IOException {
		super.newLine();
		++count;
	}

	/**
	 * <p>Get the number of lines written.</p>
	 * @return the number of lines written
	 * @since 1.0
	 */
	public long getCount() {
		return count;
	}
}