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
 * <p>An {@link InputStream} decorator that counts the number of bytes read from the current position.</p>
 * @since 1.0.0
 */
public final class CountInputStream extends FilterInputStream {

	/**
	 * <p>Number of bytes read.</p>
	 * @since 1.0.0
	 */
	private long count = 0L;

	/**
	 * <p>Number of bytes read at the last call of {@link #mark(int)}, or {@code 0} if not called yet.</p>
	 * @since 1.0.0
	 */
	private long markedCount = 0L;

	/**
	 * <p>Constructor with an {@code InputStream} to decorate.</p>
	 * @param inputStream the {@code InputStream} to decorate
	 * @throws NullPointerException if the {@code InputStream} is {@code null}
	 * @since 1.0.0
	 */
	public CountInputStream(final InputStream inputStream) {
		super(Objects.requireNonNull(inputStream, "Invalid InputStream (not null expected)"));
	}

	@Override
	public int read() throws IOException {
		final var b = in.read();
		if (-1 != b) {
			++count;
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
		final var n = in.read(buffer, offset, length);
		if (-1 != n) {
			count += n;
		}
		return n;
	}

	@Override
	public long skip(final long n) throws IOException {
		final var s = in.skip(n);
		count += s;
		return s;
	}

	@Override
	public synchronized void mark(final int limit) {
		in.mark(limit);
		markedCount = count;
	}

	@Override
	public synchronized void reset() throws IOException {
		in.reset();
		count = markedCount;
	}

	/**
	 * <p>Get the number of bytes read.</p>
	 * @return the number of bytes read
	 * @since 1.0.0
	 */
	public long getCount() {
		return count;
	}
}