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
package com.github.alexisjehan.javanilla.io.chars;

import com.github.alexisjehan.javanilla.misc.quality.Ensure;

import java.io.FilterReader;
import java.io.IOException;
import java.io.Reader;

/**
 * <p>A {@link Reader} decorator that counts the number of chars read from the current position.</p>
 * @since 1.0.0
 */
public final class CountReader extends FilterReader {

	/**
	 * <p>Number of chars read.</p>
	 * @since 1.0.0
	 */
	private long count = 0L;

	/**
	 * <p>Number of chars read at the last call of {@link #mark(int)}, or {@code 0} if not called yet.</p>
	 * @since 1.0.0
	 */
	private long markedCount = 0L;

	/**
	 * <p>Constructor with a {@code Reader} to decorate.</p>
	 * @param reader the {@code Reader} to decorate
	 * @throws NullPointerException if the {@code Reader} is {@code null}
	 * @since 1.0.0
	 */
	public CountReader(final Reader reader) {
		super(Ensure.notNull("reader", reader));
	}

	@Override
	public int read() throws IOException {
		final var next = in.read();
		if (-1 != next) {
			++count;
		}
		return next;
	}

	@Override
	public int read(final char[] buffer, final int offset, final int length) throws IOException {
		Ensure.notNull("buffer", buffer);
		Ensure.between("offset", offset, 0, buffer.length);
		Ensure.between("length", length, 0, buffer.length - offset);
		if (0 == length) {
			return 0;
		}
		final var total = in.read(buffer, offset, length);
		if (-1 != total) {
			count += total;
		}
		return total;
	}

	@Override
	public long skip(final long number) throws IOException {
		if (0L >= number) {
			return 0L;
		}
		final var actual = in.skip(number);
		count += actual;
		return actual;
	}

	@Override
	public void mark(final int limit) throws IOException {
		in.mark(limit);
		markedCount = count;
	}

	@Override
	public void reset() throws IOException {
		in.reset();
		count = markedCount;
	}

	/**
	 * <p>Get the number of chars read.</p>
	 * @return the number of chars read
	 * @since 1.0.0
	 */
	public long getCount() {
		return count;
	}
}