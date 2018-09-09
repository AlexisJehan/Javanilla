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
import java.util.Objects;

/**
 * <p>A {@link Writer} decorator that writes only chars within a range from the current position.</p>
 * @since 1.0.0
 */
public final class RangeWriter extends FilterWriter {

	/**
	 * <p>Inclusive index of the first char to write.</p>
	 * @since 1.0.0
	 */
	private final long fromIndex;

	/**
	 * <p>Inclusive index of the last char to write.<p>
	 * @since 1.0.0
	 */
	private final long toIndex;

	/**
	 * <p>Current index.</p>
	 * @since 1.0.0
	 */
	private long index = 0L;

	/**
	 * <p>Constructor with a {@code Writer} to decorate and a range from {@code 0} to an inclusive index.</p>
	 * @param writer the {@code Writer} to decorate
	 * @param toIndex the inclusive index of the last char to write
	 * @throws NullPointerException if the {@code Writer} is {@code null}
	 * @throws IndexOutOfBoundsException if the index is lower than {@code 0}
	 * @since 1.0.0
	 */
	public RangeWriter(final Writer writer, final long toIndex) {
		this(writer, 0L, toIndex);
	}

	/**
	 * <p>Constructor with a {@code Writer} to decorate and a range from an inclusive index to another one.</p>
	 * @param writer the {@code Writer} to decorate
	 * @param fromIndex the inclusive index of the first char to write
	 * @param toIndex the inclusive index of the last char to write
	 * @throws NullPointerException if the {@code Writer} is {@code null}
	 * @throws IndexOutOfBoundsException if the starting index is lower than {@code 0} or greater than the ending one
	 * @since 1.0.0
	 */
	public RangeWriter(final Writer writer, final long fromIndex, final long toIndex) {
		super(Objects.requireNonNull(writer, "Invalid Writer (not null expected)"));
		if (0L > fromIndex) {
			throw new IndexOutOfBoundsException("Invalid from index: " + fromIndex + " (greater than or equal to 0 expected)");
		}
		if (fromIndex > toIndex) {
			throw new IndexOutOfBoundsException("Invalid to index: " + toIndex + " (greater than or equal to the from index expected)");
		}
		this.fromIndex = fromIndex;
		this.toIndex = toIndex;
	}

	@Override
	public void write(final int c) throws IOException {
		if (fromIndex <= index && toIndex >= index) {
			out.write(c);
		}
		++index;
	}

	@Override
	public void write(final char[] chars, final int offset, final int length) throws IOException {
		if (null == chars) {
			throw new NullPointerException("Invalid chars (not null expected)");
		}
		if (0 > offset || chars.length < offset) {
			throw new IndexOutOfBoundsException("Invalid offset: " + offset + " (between 0 and " + chars.length + " expected)");
		}
		if (0 > length || chars.length - offset < length) {
			throw new IndexOutOfBoundsException("Invalid length: " + length + " (between 0 and " + (chars.length - offset) + " expected)");
		}
		if (0 == length) {
			return;
		}
		if (fromIndex <= index + length && toIndex >= index) {
			out.write(chars, offset + (fromIndex > index ? (int) (fromIndex - index) : 0), Math.min(length, toIndex != index ? (int) (toIndex - index) : 1));
		}
		index += length;
	}

	@Override
	public void write(final String string, final int offset, final int length) throws IOException {
		if (null == string) {
			throw new NullPointerException("Invalid String (not null expected)");
		}
		final var stringLength = string.length();
		if (0 > offset || stringLength < offset) {
			throw new IndexOutOfBoundsException("Invalid offset: " + offset + " (between 0 and " + stringLength + " expected)");
		}
		if (0 > length || stringLength - offset < length) {
			throw new IndexOutOfBoundsException("Invalid length: " + length + " (between 0 and " + (stringLength - offset) + " expected)");
		}
		if (fromIndex <= index + length && toIndex >= index) {
			out.write(string, offset + (fromIndex > index ? (int) (fromIndex - index) : 0), Math.min(length, toIndex != index ? (int) (toIndex - index) : 1));
		}
		index += length;
	}

	/**
	 * <p>Get the inclusive index of the first char to write.</p>
	 * @return the inclusive starting index
	 * @since 1.0.0
	 */
	public long getFromIndex() {
		return fromIndex;
	}

	/**
	 * <p>Get the inclusive index of the last char to write.<p>
	 * @return the inclusive ending index
	 * @since 1.0.0
	 */
	public long getToIndex() {
		return toIndex;
	}
}