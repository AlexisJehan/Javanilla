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

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

/**
 * <p>An {@link InputStream} decorator that reads only bytes within a range from the current position.</p>
 * @since 1.0.0
 */
public final class RangeInputStream extends FilterInputStream {

	/**
	 * <p>Inclusive index of the first byte to read.</p>
	 * @since 1.0.0
	 */
	private final long fromIndex;

	/**
	 * <p>Inclusive index of the last byte to read.<p>
	 * @since 1.0.0
	 */
	private final long toIndex;

	/**
	 * <p>Current index.</p>
	 * @since 1.0.0
	 */
	private long index = 0L;

	/**
	 * <p>Index at the last call of {@link #mark(int)}, or {@code 0} if not called yet.</p>
	 * @since 1.0.0
	 */
	private long markedIndex = 0L;

	/**
	 * <p>Constructor with an {@code InputStream} to decorate and a range from {@code 0} to an inclusive index.</p>
	 * @param inputStream the {@code InputStream} to decorate
	 * @param toIndex the inclusive index of the last byte to read
	 * @throws NullPointerException if the {@code InputStream} is {@code null}
	 * @throws IndexOutOfBoundsException if the index is lower than {@code 0}
	 * @since 1.0.0
	 */
	public RangeInputStream(final InputStream inputStream, final long toIndex) {
		this(inputStream, 0L, toIndex);
	}

	/**
	 * <p>Constructor with an {@code InputStream} to decorate and a range from an inclusive index to another one.</p>
	 * @param inputStream the {@code InputStream} to decorate
	 * @param fromIndex the inclusive index of the first byte to read
	 * @param toIndex the inclusive index of the last byte to read
	 * @throws NullPointerException if the {@code InputStream} is {@code null}
	 * @throws IndexOutOfBoundsException if the starting index is lower than {@code 0} or greater than the ending one
	 * @since 1.0.0
	 */
	public RangeInputStream(final InputStream inputStream, final long fromIndex, final long toIndex) {
		super(Objects.requireNonNull(inputStream, "Invalid InputStream (not null expected)"));
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
	public int read() throws IOException {
		if (fromIndex > index) {
			index += in.skip(fromIndex - index);
		}
		if (toIndex < index) {
			return -1;
		}
		final var b = in.read();
		if (-1 != b) {
			++index;
		}
		return b;
	}

	@Override
	public int read(final byte[] buffer, final int offset, final int length) throws IOException {
		if (null == buffer) {
			throw new NullPointerException("Invalid buffer (not null expected)");
		}
		if (0 > offset || buffer.length < offset) {
			throw new IndexOutOfBoundsException("Invalid offset: " + offset + " (between 0 and " + buffer.length + " expected)");
		}
		if (0 > length || buffer.length - offset < length) {
			throw new IndexOutOfBoundsException("Invalid length: " + length + " (between 0 and " + (buffer.length - offset) + " expected)");
		}
		if (0 == length) {
			return 0;
		}
		if (fromIndex > index) {
			index += in.skip(fromIndex - index);
		}
		if (toIndex < index) {
			return -1;
		}
		final var n = in.read(buffer, offset, Math.min(length, (int) (toIndex - index + 1)));
		if (-1 != n) {
			index += n;
		}
		return n;
	}

	@Override
	public long skip(final long n) throws IOException {
		if (fromIndex > index) {
			index += in.skip(fromIndex - index);
		}
		if (toIndex < index) {
			return 0L;
		}
		final var s = in.skip(n);
		index += s;
		return s;
	}

	@Override
	public synchronized void mark(final int limit) {
		in.mark(limit);
		markedIndex = index;
	}

	@Override
	public synchronized void reset() throws IOException {
		in.reset();
		index = markedIndex;
	}

	/**
	 * <p>Get the inclusive index of the first byte to read.</p>
	 * @return the inclusive starting index
	 * @since 1.0.0
	 */
	public long getFromIndex() {
		return fromIndex;
	}

	/**
	 * <p>Get the inclusive index of the last byte to read.<p>
	 * @return the inclusive ending index
	 * @since 1.0.0
	 */
	public long getToIndex() {
		return toIndex;
	}
}