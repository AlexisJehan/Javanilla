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
import java.io.UncheckedIOException;
import java.io.Writer;
import java.util.Objects;

/**
 * <p>A {@link Writer} decorator that throws {@link UncheckedIOException}s instead of {@link IOException}s.</p>
 * @since 1.2.0
 */
public final class UncheckedWriter extends FilterWriter {

	/**
	 * <p>Constructor with a {@code Writer} to decorate.</p>
	 * @param writer the {@code Writer} to decorate
	 * @throws NullPointerException if the {@code Writer} is {@code null}
	 * @since 1.2.0
	 */
	public UncheckedWriter(final Writer writer) {
		super(Objects.requireNonNull(writer, "Invalid Writer (not null expected)"));
	}

	@Override
	public void write(final int c) {
		try {
			out.write(c);
		} catch (final IOException e) {
			throw new UncheckedIOException(e);
		}
	}

	@Override
	public void write(final char[] chars) {
		if (null == chars) {
			throw new NullPointerException("Invalid chars (not null expected)");
		}
		try {
			out.write(chars);
		} catch (final IOException e) {
			throw new UncheckedIOException(e);
		}
	}

	@Override
	public void write(final char[] chars, final int offset, final int length) {
		if (null == chars) {
			throw new NullPointerException("Invalid chars (not null expected)");
		}
		if (0 > offset || chars.length < offset) {
			throw new IndexOutOfBoundsException("Invalid offset: " + offset + " (between 0 and " + chars.length + " expected)");
		}
		if (0 > length || chars.length - offset < length) {
			throw new IndexOutOfBoundsException("Invalid length: " + length + " (between 0 and " + (chars.length - offset) + " expected)");
		}
		try {
			out.write(chars, offset, length);
		} catch (final IOException e) {
			throw new UncheckedIOException(e);
		}
	}

	@Override
	public void write(final String string) {
		if (null == string) {
			throw new NullPointerException("Invalid String (not null expected)");
		}
		try {
			out.write(string);
		} catch (final IOException e) {
			throw new UncheckedIOException(e);
		}
	}

	@Override
	public void write(final String string, final int offset, final int length) {
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
		try {
			out.write(string, offset, length);
		} catch (final IOException e) {
			throw new UncheckedIOException(e);
		}
	}

	@Override
	public Writer append(final CharSequence charSequence) {
		if (null == charSequence) {
			throw new NullPointerException("Invalid CharSequence (not null expected)");
		}
		try {
			out.append(charSequence);
		} catch (final IOException e) {
			throw new UncheckedIOException(e);
		}
		return this;
	}

	@Override
	public Writer append(final CharSequence charSequence, final int start, final int end) {
		if (null == charSequence) {
			throw new NullPointerException("Invalid CharSequence (not null expected)");
		}
		final var length = charSequence.length();
		if (0 > start) {
			throw new IndexOutOfBoundsException("Invalid start: " + start + " (greater than 0 expected)");
		}
		if (start > end || length < end) {
			throw new IndexOutOfBoundsException("Invalid end: " + end + " (between " + start + " and " + length + " expected)");
		}
		try {
			out.append(charSequence, start, end);
		} catch (final IOException e) {
			throw new UncheckedIOException(e);
		}
		return this;
	}

	@Override
	public Writer append(final char c) {
		try {
			out.append(c);
		} catch (final IOException e) {
			throw new UncheckedIOException(e);
		}
		return this;
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