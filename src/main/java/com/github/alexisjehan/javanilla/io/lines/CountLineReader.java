/*
 * MIT License
 *
 * Copyright (c) 2018-2024 Alexis Jehan
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package com.github.alexisjehan.javanilla.io.lines;

import java.io.IOException;

/**
 * <p>A {@link LineReader} decorator that counts the number of lines read from the current position.</p>
 * @deprecated since 1.8.0, use {@link com.github.alexisjehan.javanilla.io.line.CountLineReader} instead
 * @since 1.0.0
 */
@Deprecated(since = "1.8.0")
public final class CountLineReader extends FilterLineReader {

	/**
	 * <p>Number of lines read.</p>
	 * @since 1.0.0
	 */
	private long count;

	/**
	 * <p>Constructor with a {@link LineReader} to decorate.</p>
	 * @param lineReader the {@link LineReader} to decorate
	 * @throws NullPointerException if the {@link LineReader} is {@code null}
	 * @since 1.0.0
	 */
	public CountLineReader(final LineReader lineReader) {
		super(lineReader);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String read() throws IOException {
		final var line = super.read();
		if (null != line) {
			++count;
		}
		return line;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public long skip(final long number) throws IOException {
		if (0L >= number) {
			return 0L;
		}
		final var actual = super.skip(number);
		count += actual;
		return actual;
	}

	/**
	 * <p>Get the number of lines read.</p>
	 * @return the number of lines read
	 * @since 1.0.0
	 */
	public long getCount() {
		return count;
	}
}