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
import java.io.UncheckedIOException;
import java.util.Objects;

/**
 * <p>An {@link OutputStream} decorator that throws {@link UncheckedIOException}s instead of {@link IOException}s.</p>
 * @since 1.2.0
 */
public final class UncheckedOutputStream extends FilterOutputStream {

	/**
	 * <p>Constructor with an {@code OutputStream} to decorate.</p>
	 * @param outputStream the {@code OutputStream} to decorate
	 * @throws NullPointerException if the {@code OutputStream} is {@code null}
	 * @since 1.2.0
	 */
	public UncheckedOutputStream(final OutputStream outputStream) {
		super(Objects.requireNonNull(outputStream, "Invalid OutputStream (not null expected)"));
	}

	@Override
	public void write(final int b) {
		try {
			out.write(b);
		} catch (final IOException e) {
			throw new UncheckedIOException(e);
		}
	}

	@Override
	public void write(final byte[] bytes) {
		if (null == bytes) {
			throw new NullPointerException("Invalid bytes (not null expected)");
		}
		try {
			out.write(bytes);
		} catch (final IOException e) {
			throw new UncheckedIOException(e);
		}
	}

	@Override
	public void write(final byte[] bytes, final int offset, final int length) {
		if (null == bytes) {
			throw new NullPointerException("Invalid bytes (not null expected)");
		}
		if (0 > offset || bytes.length < offset) {
			throw new IndexOutOfBoundsException("Invalid offset: " + offset + " (between 0 and " + bytes.length + " expected)");
		}
		if (0 > length || bytes.length - offset < length) {
			throw new IndexOutOfBoundsException("Invalid length: " + length + " (between 0 and " + (bytes.length - offset) + " expected)");
		}
		try {
			out.write(bytes, offset, length);
		} catch (final IOException e) {
			throw new UncheckedIOException(e);
		}
	}

	@Override
	public void flush() {
		try {
			out.flush();
		} catch (final IOException e) {
			throw new UncheckedIOException(e);
		}
	}

	@Override
	public void close() {
		try {
			out.close();
		} catch (final IOException e) {
			throw new UncheckedIOException(e);
		}
	}
}