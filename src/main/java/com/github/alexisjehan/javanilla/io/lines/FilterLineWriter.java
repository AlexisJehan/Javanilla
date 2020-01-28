/*
 * MIT License
 *
 * Copyright (c) 2018-2020 Alexis Jehan
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

import com.github.alexisjehan.javanilla.misc.quality.Ensure;

import java.io.IOException;

/**
 * <p>An abstract {@link LineWriter} filter to create decorators.</p>
 * @since 1.0.0
 */
public abstract class FilterLineWriter extends LineWriter {

	/**
	 * <p>Delegated {@link LineWriter}.</p>
	 * @since 1.0.0
	 */
	protected final LineWriter lineWriter;

	/**
	 * <p>Constructor with a {@link LineWriter} to decorate.</p>
	 * @param lineWriter the {@link LineWriter} to decorate
	 * @throws NullPointerException if the {@link LineWriter} is {@code null}
	 * @since 1.0.0
	 */
	protected FilterLineWriter(final LineWriter lineWriter) {
		Ensure.notNull("lineWriter", lineWriter);
		this.lineWriter = lineWriter;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void write(final String line) throws IOException {
		lineWriter.write(line);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void newLine() throws IOException {
		lineWriter.newLine();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void flush() throws IOException {
		lineWriter.flush();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void close() throws IOException {
		lineWriter.close();
	}
}