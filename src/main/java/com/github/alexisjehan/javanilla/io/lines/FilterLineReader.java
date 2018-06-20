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
package com.github.alexisjehan.javanilla.io.lines;

import java.io.IOException;

/**
 * <p>An abstract {@link LineReader} filter to create decorators.</p>
 * @since 1.0.0
 */
public abstract class FilterLineReader extends LineReader {

	/**
	 * <p>Delegated {@code LineReader}.</p>
	 * @since 1.0.0
	 */
	protected final LineReader lineReader;

	/**
	 * <p>Constructor with a delegated {@code LineReader}.</p>
	 * @param lineReader the delegated {@code LineReader}
	 * @throws NullPointerException if the {@code LineReader} is {@code null}
	 * @since 1.0.0
	 */
	protected FilterLineReader(final LineReader lineReader) {
		if (null == lineReader) {
			throw new NullPointerException("Invalid line reader (not null expected)");
		}
		this.lineReader = lineReader;
	}

	@Override
	public String read() throws IOException {
		return lineReader.read();
	}

	@Override
	public long skip(final long n) throws IOException {
		return lineReader.skip(n);
	}

	@Override
	public void close() throws IOException {
		lineReader.close();
	}
}