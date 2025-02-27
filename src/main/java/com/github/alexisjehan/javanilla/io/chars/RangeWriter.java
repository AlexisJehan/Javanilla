/*
 * MIT License
 *
 * Copyright (c) 2018-2025 Alexis Jehan
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

import java.io.FilterWriter;
import java.io.IOException;
import java.io.Writer;

/**
 * A {@link Writer} decorator that writes only chars within a range from the current position.
 * @deprecated since 1.8.0, use {@link com.github.alexisjehan.javanilla.io.RangeWriter} instead
 * @since 1.0.0
 */
@Deprecated(since = "1.8.0")
public final class RangeWriter extends FilterWriter {

	/**
	 * Inclusive index of the first char to write.
	 * @since 1.0.0
	 */
	private final long fromIndex;

	/**
	 * Inclusive index of the last char to write.
	 * @since 1.0.0
	 */
	private final long toIndex;

	/**
	 * Current index.
	 * @since 1.0.0
	 */
	private long index;

	/**
	 * Constructor with a {@link Writer} to decorate and a range from an inclusive index to another one.
	 * @param writer the {@link Writer} to decorate
	 * @param fromIndex the inclusive index of the first char to write
	 * @param toIndex the inclusive index of the last char to write
	 * @throws NullPointerException if the {@link Writer} is {@code null}
	 * @throws IllegalArgumentException if the starting index is lower than {@code 0} or greater than the ending one
	 * @since 1.0.0
	 */
	public RangeWriter(final Writer writer, final long fromIndex, final long toIndex) {
		super(Ensure.notNull("writer", writer));
		Ensure.greaterThanOrEqualTo("fromIndex", fromIndex, 0L);
		Ensure.greaterThanOrEqualTo("toIndex", toIndex, fromIndex);
		this.fromIndex = fromIndex;
		this.toIndex = toIndex;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void write(final int i) throws IOException {
		if (fromIndex <= index && toIndex >= index) {
			out.write(i);
		}
		++index;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void write(final char[] chars, final int offset, final int length) throws IOException {
		Ensure.notNull("chars", chars);
		Ensure.between("offset", offset, 0, chars.length);
		Ensure.between("length", length, 0, chars.length - offset);
		if (0 == length) {
			return;
		}
		if (fromIndex <= index + length && toIndex >= index) {
			out.write(chars, offset + StrictMath.toIntExact(fromIndex > index ? fromIndex - index : 0L), StrictMath.toIntExact(StrictMath.min(length, toIndex != index ? toIndex - index : 1L)));
		}
		index += length;
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
		if (0 == length) {
			return;
		}
		if (fromIndex <= index + length && toIndex >= index) {
			out.write(string, offset + StrictMath.toIntExact(fromIndex > index ? fromIndex - index : 0L), StrictMath.toIntExact(StrictMath.min(length, toIndex != index ? toIndex - index : 1L)));
		}
		index += length;
	}

	/**
	 * Get the inclusive index of the first char to write.
	 * @return the inclusive starting index
	 * @since 1.0.0
	 */
	public long getFromIndex() {
		return fromIndex;
	}

	/**
	 * Get the inclusive index of the last char to write.
	 * @return the inclusive ending index
	 * @since 1.0.0
	 */
	public long getToIndex() {
		return toIndex;
	}
}