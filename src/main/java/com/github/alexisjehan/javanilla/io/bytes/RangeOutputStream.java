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
package com.github.alexisjehan.javanilla.io.bytes;

import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Objects;

/**
 * <p>An {@link OutputStream} decorator that writes only bytes within a range from the current position.</p>
 * @since 1.0.0
 */
public final class RangeOutputStream extends FilterOutputStream {

	/**
	 * <p>Inclusive index of the first byte to write.</p>
	 * @since 1.0.0
	 */
	private final long fromIndex;

	/**
	 * <p>Inclusive index of the last byte to write.<p>
	 * @since 1.0.0
	 */
	private final long toIndex;

	/**
	 * <p>Current index.</p>
	 * @since 1.0.0
	 */
	private long index = 0L;

	/**
	 * <p>Constructor with an {@code OutputStream} to decorate and a range from {@code 0} to an inclusive index.</p>
	 * @param outputStream the {@code OutputStream} to decorate
	 * @param toIndex the inclusive index of the last byte to write
	 * @throws NullPointerException if the {@code OutputStream} is {@code null}
	 * @throws IndexOutOfBoundsException if the index is lower than {@code 0}
	 * @since 1.0.0
	 */
	public RangeOutputStream(final OutputStream outputStream, final long toIndex) {
		this(outputStream, 0L, toIndex);
	}

	/**
	 * <p>Constructor with an {@code OutputStream} to decorate and a range from an inclusive index to another one.</p>
	 * @param outputStream the {@code OutputStream} to decorate
	 * @param fromIndex the inclusive index of the first byte to write
	 * @param toIndex the inclusive index of the last byte to write
	 * @throws NullPointerException if the {@code OutputStream} is {@code null}
	 * @throws IndexOutOfBoundsException if the starting index is lower than {@code 0} or greater than the ending one
	 * @since 1.0.0
	 */
	public RangeOutputStream(final OutputStream outputStream, final long fromIndex, final long toIndex) {
		super(Objects.requireNonNull(outputStream, "Invalid OutputStream (not null expected)"));
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
	public void write(final int b) throws IOException {
		if (fromIndex <= index && toIndex >= index) {
			out.write(b);
		}
		++index;
	}

	@Override
	public void write(final byte[] bytes, final int offset, final int length) throws IOException {
		if (null == bytes) {
			throw new NullPointerException("Invalid bytes (not null expected)");
		}
		if (0 > offset || bytes.length < offset) {
			throw new IndexOutOfBoundsException("Invalid offset: " + offset + " (between 0 and " + bytes.length + " expected)");
		}
		if (0 > length || bytes.length - offset < length) {
			throw new IndexOutOfBoundsException("Invalid length: " + length + " (between 0 and " + (bytes.length - offset) + " expected)");
		}
		if (0 == length) {
			return;
		}
		if (fromIndex <= index + length && toIndex >= index) {
			out.write(bytes, offset + (fromIndex > index ? (int) (fromIndex - index) : 0), Math.min(length, toIndex != index ? (int) (toIndex - index) : 1));
		}
		index += length;
	}

	/**
	 * <p>Get the inclusive index of the first byte to write.</p>
	 * @return the inclusive starting index
	 * @since 1.0.0
	 */
	public long getFromIndex() {
		return fromIndex;
	}

	/**
	 * <p>Get the inclusive index of the last byte to write.<p>
	 * @return the inclusive ending index
	 * @since 1.0.0
	 */
	public long getToIndex() {
		return toIndex;
	}
}