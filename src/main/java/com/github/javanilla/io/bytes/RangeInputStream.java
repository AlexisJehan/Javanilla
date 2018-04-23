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
package com.github.javanilla.io.bytes;

import org.jetbrains.annotations.NotNull;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * <p>An {@link InputStream} decorator that reads only bytes within a range from the current position.</p>
 * @since 1.0
 */
public final class RangeInputStream extends FilterInputStream {

	/**
	 * <p>The inclusive index of the first byte to read.</p>
	 * @since 1.0
	 */
	private final long fromIndex;

	/**
	 * <p>The inclusive index of the last byte to read.<p>
	 * @since 1.0
	 */
	private final long toIndex;

	/**
	 * <p>Current index.</p>
	 * @since 1.0
	 */
	private long index = 0L;

	/**
	 * <p>Index at the last call of {@link #mark(int)}, or {@code 0} if not called yet.</p>
	 * @since 1.0
	 */
	private long markedIndex = 0L;

	/**
	 * <p>Constructor with a delegated {@code InputStream} and a range from {@code 0} to the given inclusive index.</p>
	 * @param inputStream the delegated {@code InputStream}
	 * @param toIndex the inclusive index of the last byte to read
	 * @throws NullPointerException if the delegated {@code InputStream} is {@code null}
	 * @throws IndexOutOfBoundsException if the index is negative
	 * @since 1.0
	 */
	public RangeInputStream(final InputStream inputStream, final long toIndex) {
		this(inputStream, 0L, toIndex);
	}

	/**
	 * <p>Constructor with a delegated {@code InputStream} and a range from an inclusive index to another one.</p>
	 * @param inputStream the delegated {@code InputStream}
	 * @param fromIndex the inclusive index of the first byte to read
	 * @param toIndex the inclusive index of the last byte to read
	 * @throws NullPointerException if the delegated {@code InputStream} is {@code null}
	 * @throws IndexOutOfBoundsException whether the starting index is negative or greater than the ending one
	 * @since 1.0
	 */
	public RangeInputStream(final InputStream inputStream, final long fromIndex, final long toIndex) {
		super(inputStream);
		if (null == in) {
			throw new NullPointerException("Invalid input stream (not null expected)");
		}
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
	public int read(@NotNull final byte[] b, final int off, final int len) throws IOException {
		if (fromIndex > index) {
			index += in.skip(fromIndex - index);
		}
		if (toIndex < index) {
			return -1;
		}
		final var n = in.read(b, off, Math.min(len, (int) (toIndex - index + 1)));
		if (-1 != n) {
			index += n;
		}
		return n;
	}

	@Override
	public long skip(final long n) throws IOException {
		final var s = in.skip(n);
		index += s;
		return s;
	}

	@Override
	public void mark(final int readLimit) {
		in.mark(readLimit);
		markedIndex = index;
	}

	@Override
	public void reset() throws IOException {
		in.reset();
		index = markedIndex;
	}

	/**
	 * <p>Get the inclusive index of the first byte to read.</p>
	 * @return the inclusive index of the first byte to read
	 * @since 1.0
	 */
	public long getFromIndex() {
		return fromIndex;
	}

	/**
	 * <p>Get the inclusive index of the last byte to read.<p>
	 * @return the inclusive index of the last byte to read
	 * @since 1.0
	 */
	public long getToIndex() {
		return toIndex;
	}
}