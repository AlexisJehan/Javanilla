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
package com.github.alexisjehan.javanilla.io.line;

import com.github.alexisjehan.javanilla.misc.quality.Ensure;

import java.io.IOException;

/**
 * <p>An abstract {@link LineReader} filter to create decorators.</p>
 * @since 1.8.0
 */
public abstract class FilterLineReader extends LineReader {

	/**
	 * <p>Delegated {@link LineReader}.</p>
	 * @since 1.8.0
	 */
	protected final LineReader lineReader;

	/**
	 * <p>Constructor with a {@link LineReader} to decorate.</p>
	 * @param lineReader the {@link LineReader} to decorate
	 * @throws NullPointerException if the {@link LineReader} is {@code null}
	 * @since 1.8.0
	 */
	protected FilterLineReader(final LineReader lineReader) {
		Ensure.notNull("lineReader", lineReader);
		this.lineReader = lineReader;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String read() throws IOException {
		return lineReader.read();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public long skip(final long number) throws IOException {
		return lineReader.skip(number);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void close() throws IOException {
		lineReader.close();
	}
}