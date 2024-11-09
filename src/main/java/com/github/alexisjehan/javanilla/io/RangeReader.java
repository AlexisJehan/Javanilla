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
package com.github.alexisjehan.javanilla.io;

import com.github.alexisjehan.javanilla.misc.quality.Ensure;

import java.io.FilterReader;
import java.io.IOException;
import java.io.Reader;

/**
 * A {@link Reader} decorator that reads only chars within a range from the current position.
 * @since 1.8.0
 */
public final class RangeReader extends FilterReader {

	/**
	 * Inclusive index of the first char to read.
	 * @since 1.8.0
	 */
	private final long fromIndex;

	/**
	 * Inclusive index of the last char to read.
	 * @since 1.8.0
	 */
	private final long toIndex;

	/**
	 * Current index.
	 * @since 1.8.0
	 */
	private long index;

	/**
	 * Index at the last call of {@link #mark(int)}, or {@code 0} if not called yet.
	 * @since 1.8.0
	 */
	private long markedIndex;

	/**
	 * Constructor with a {@link Reader} to decorate and a range from an inclusive index to another one.
	 * @param reader the {@link Reader} to decorate
	 * @param fromIndex the inclusive index of the first char to read
	 * @param toIndex the inclusive index of the last char to read
	 * @throws NullPointerException if the {@link Reader} is {@code null}
	 * @throws IllegalArgumentException if the starting index is lower than {@code 0} or greater than the ending one
	 * @since 1.8.0
	 */
	public RangeReader(final Reader reader, final long fromIndex, final long toIndex) {
		super(Ensure.notNull("reader", reader));
		Ensure.greaterThanOrEqualTo("fromIndex", fromIndex, 0L);
		Ensure.greaterThanOrEqualTo("toIndex", toIndex, fromIndex);
		this.fromIndex = fromIndex;
		this.toIndex = toIndex;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int read() throws IOException {
		if (fromIndex > index) {
			index += in.skip(fromIndex - index);
		}
		if (toIndex < index) {
			return -1;
		}
		final var next = in.read();
		if (-1 != next) {
			++index;
		}
		return next;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int read(final char[] buffer, final int offset, final int length) throws IOException {
		Ensure.notNull("buffer", buffer);
		Ensure.between("offset", offset, 0, buffer.length);
		Ensure.between("length", length, 0, buffer.length - offset);
		if (0 == length) {
			return 0;
		}
		if (fromIndex > index) {
			index += in.skip(fromIndex - index);
		}
		if (toIndex < index) {
			return -1;
		}
		final var total = in.read(buffer, offset, StrictMath.toIntExact(StrictMath.min(length, toIndex - index + 1L)));
		if (-1 != total) {
			index += total;
		}
		return total;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public long skip(final long number) throws IOException {
		if (0L >= number || toIndex < index) {
			return 0L;
		}
		if (fromIndex > index) {
			index += in.skip(fromIndex - index);
		}
		final var actual = in.skip(number);
		index += actual;
		return actual;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void mark(final int limit) throws IOException {
		in.mark(limit);
		markedIndex = index;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void reset() throws IOException {
		in.reset();
		index = markedIndex;
	}

	/**
	 * Get the inclusive index of the first char to read.
	 * @return the inclusive starting index
	 * @since 1.8.0
	 */
	public long getFromIndex() {
		return fromIndex;
	}

	/**
	 * Get the inclusive index of the last char to read.
	 * @return the inclusive ending index
	 * @since 1.8.0
	 */
	public long getToIndex() {
		return toIndex;
	}
}