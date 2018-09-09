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

import java.io.*;
import java.nio.CharBuffer;
import java.util.Objects;

/**
 * <p>A {@link Reader} decorator that throws {@link UncheckedIOException}s instead of {@link IOException}s.</p>
 * @since 1.2.0
 */
public final class UncheckedReader extends FilterReader {

	/**
	 * <p>Constructor with a {@code Reader} to decorate.</p>
	 * @param reader the {@code Reader} to decorate
	 * @throws NullPointerException if the {@code Reader} is {@code null}
	 * @since 1.2.0
	 */
	public UncheckedReader(final Reader reader) {
		super(Objects.requireNonNull(reader, "Invalid Reader (not null expected)"));
	}

	@Override
	public int read(final CharBuffer charBuffer) {
		if (null == charBuffer) {
			throw new NullPointerException("Invalid CharBuffer (not null expected)");
		}
		try {
			return in.read(charBuffer);
		} catch (final IOException e) {
			throw new UncheckedIOException(e);
		}
	}

	@Override
	public int read() {
		try {
			return in.read();
		} catch (final IOException e) {
			throw new UncheckedIOException(e);
		}
	}

	@Override
	public int read(final char[] buffer) {
		if (null == buffer) {
			throw new NullPointerException("Invalid buffer (not null expected)");
		}
		try {
			return in.read(buffer);
		} catch (final IOException e) {
			throw new UncheckedIOException(e);
		}
	}

	@Override
	public int read(final char[] buffer, int offset, int length) {
		if (null == buffer) {
			throw new NullPointerException("Invalid buffer (not null expected)");
		}
		if (0 > offset || buffer.length < offset) {
			throw new IndexOutOfBoundsException("Invalid offset: " + offset + " (between 0 and " + buffer.length + " expected)");
		}
		if (0 > length || buffer.length - offset < length) {
			throw new IndexOutOfBoundsException("Invalid length: " + length + " (between 0 and " + (buffer.length - offset) + " expected)");
		}
		try {
			return in.read(buffer, offset, length);
		} catch (final IOException e) {
			throw new UncheckedIOException(e);
		}
	}

	@Override
	public long skip(final long n) {
		try {
			return in.skip(n);
		} catch (final IOException e) {
			throw new UncheckedIOException(e);
		}
	}

	@Override
	public boolean ready() {
		try {
			return in.ready();
		} catch (final IOException e) {
			throw new UncheckedIOException(e);
		}
	}

	@Override
	public boolean markSupported() {
		return in.markSupported();
	}

	@Override
	public void mark(final int limit) {
		try {
			in.mark(limit);
		} catch (final IOException e) {
			throw new UncheckedIOException(e);
		}
	}

	@Override
	public void reset() {
		try {
			in.reset();
		} catch (final IOException e) {
			throw new UncheckedIOException(e);
		}
	}

	@Override
	public void close() {
		try {
			in.close();
		} catch (final IOException e) {
			throw new UncheckedIOException(e);
		}
	}

	@Override
	public long transferTo(final Writer writer) {
		if (null == writer) {
			throw new NullPointerException("Invalid Writer (not null expected)");
		}
		try {
			return in.transferTo(writer);
		} catch (final IOException e) {
			throw new UncheckedIOException(e);
		}
	}
}