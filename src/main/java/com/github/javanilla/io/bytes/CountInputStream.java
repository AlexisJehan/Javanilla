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
 * <p>An {@link InputStream} decorator that counts the number of bytes read from the current position.</p>
 * @since 1.0
 */
public final class CountInputStream extends FilterInputStream {

	/**
	 * <p>Number of bytes read.</p>
	 * @since 1.0
	 */
	private long count = 0L;

	/**
	 * <p>Number of bytes read at the last call of {@link #mark(int)}, or {@code 0} if not called yet.</p>
	 * @since 1.0
	 */
	private long markedCount = 0L;

	/**
	 * <p>Constructor with a delegated {@code InputStream}.</p>
	 * @param inputStream the delegated {@code InputStream}
	 * @throws NullPointerException if the delegated {@code InputStream} is {@code null}
	 * @since 1.0
	 */
	public CountInputStream(final InputStream inputStream) {
		super(inputStream);
		if (null == in) {
			throw new NullPointerException("Invalid input stream (not null expected)");
		}
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
	public int read(@NotNull final byte[] b, final int off, final int len) throws IOException {
		final var n = in.read(b, off, len);
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
	public void mark(final int readLimit) {
		in.mark(readLimit);
		markedCount = count;
	}

	@Override
	public void reset() throws IOException {
		in.reset();
		count = markedCount;
	}

	/**
	 * <p>Get the number of bytes read.</p>
	 * @return the number of bytes read
	 * @since 1.0
	 */
	public long getCount() {
		return count;
	}
}