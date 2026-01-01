/*
 * MIT License
 *
 * Copyright (c) 2018-2026 Alexis Jehan
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
package com.github.alexisjehan.javanilla.io;

import com.github.alexisjehan.javanilla.misc.quality.Ensure;

import java.io.FilterWriter;
import java.io.IOException;
import java.io.Writer;

/**
 * A {@link Writer} decorator that counts the number of chars written from the current position.
 * @since 1.8.0
 */
public final class CountWriter extends FilterWriter {

	/**
	 * Number of chars written.
	 * @since 1.8.0
	 */
	private long count;

	/**
	 * Constructor with a {@link Writer} to decorate.
	 * @param writer the {@link Writer} to decorate
	 * @throws NullPointerException if the {@link Writer} is {@code null}
	 * @since 1.8.0
	 */
	public CountWriter(final Writer writer) {
		super(Ensure.notNull("writer", writer));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void write(final int i) throws IOException {
		out.write(i);
		++count;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void write(final char[] chars, final int offset, final int length) throws IOException {
		Ensure.notNull("chars", chars);
		Ensure.between("offset", offset, 0, chars.length);
		Ensure.between("length", length, 0, chars.length - offset);
		if (0 < length) {
			out.write(chars, offset, length);
			count += length;
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void write(final String string, final int offset, final int length) throws IOException {
		Ensure.notNull("string", string);
		final var stringLength = string.length();
		Ensure.between("offset", offset, 0, stringLength);
		Ensure.between("length", length, 0, stringLength - offset);
		if (0 < length) {
			out.write(string, offset, length);
			count += length;
		}
	}

	/**
	 * Get the number of chars written.
	 * @return the number of chars written
	 * @since 1.8.0
	 */
	public long getCount() {
		return count;
	}
}