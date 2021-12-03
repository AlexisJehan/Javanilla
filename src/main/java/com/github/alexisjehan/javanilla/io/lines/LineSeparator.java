/*
 * MIT License
 *
 * Copyright (c) 2018-2021 Alexis Jehan
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
package com.github.alexisjehan.javanilla.io.lines;

import com.github.alexisjehan.javanilla.io.chars.Readers;
import com.github.alexisjehan.javanilla.misc.quality.Ensure;

import java.io.IOException;
import java.io.Reader;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.util.stream.IntStream;

/**
 * <p>Enumeration of line separator types used by {@link LineReader} and {@link LineWriter}.</p>
 * <p><b>Note</b>: This class implements its own {@link #toString()} method.</p>
 * @since 1.0.0
 */
public enum LineSeparator {

	/**
	 * <p>"Line Feed" char line separator type, used mainly on <i>Linux</i> and <i>MacOS</i> operating systems.</p>
	 * @since 1.0.0
	 */
	LF("\n") {

		/**
		 * {@inheritDoc}
		 */
		@Override
		int read(final Reader reader, final StringBuilder builder) throws IOException {
			int i;
			while (-1 != (i = reader.read())) {
				if ('\n' == i) {
					break;
				}
				builder.appendCodePoint(i);
			}
			return i;
		}
	},

	/**
	 * <p>"Carriage Return" followed by "Line Feed" chars line separator type, used mainly on the <i>Windows</i>
	 * operating system.</p>
	 * @since 1.0.0
	 */
	CR_LF("\r\n") {

		/**
		 * {@inheritDoc}
		 */
		@Override
		int read(final Reader reader, final StringBuilder builder) throws IOException {
			int i1;
			int i2;
			while (-1 != (i1 = reader.read())) {
				if ('\r' == i1) {
					i2 = reader.read();
					if ('\n' == i2) {
						return i2;
					}
					builder.appendCodePoint(i1);
					if (-1 == i2) {
						return i2;
					}
					builder.appendCodePoint(i2);
				} else {
					builder.appendCodePoint(i1);
				}
			}
			return i1;
		}
	},

	/**
	 * <p>"Carriage Return" char line separator type, used on old <i>MacOS</i> operating systems.</p>
	 * @since 1.0.0
	 */
	CR("\r") {

		/**
		 * {@inheritDoc}
		 */
		@Override
		int read(final Reader reader, final StringBuilder builder) throws IOException {
			int i;
			while (-1 != (i = reader.read())) {
				if ('\r' == i) {
					break;
				}
				builder.appendCodePoint(i);
			}
			return i;
		}
	},

	/**
	 * <p>"Line Feed" or "Carriage Return" char line separator type on read, system's default line separator
	 * representation on write.</p>
	 * @since 1.0.0
	 */
	DEFAULT(System.lineSeparator()) {

		/**
		 * {@inheritDoc}
		 */
		@Override
		int read(final Reader reader, final StringBuilder builder) throws IOException {
			int i;
			while (-1 != (i = reader.read())) {
				if ('\n' == i || '\r' == i) {
					break;
				}
				builder.appendCodePoint(i);
			}
			return i;
		}
	};

	/**
	 * <p>Limit on the characters sample to read while detecting the {@link LineSeparator} type over a
	 * {@link Reader}.</p>
	 * @since 1.0.0
	 */
	private static final int DETECTION_LIMIT = 8_000;

	/**
	 * <p>{@link String} representation of the current {@link LineSeparator} type.</p>
	 * @since 1.0.0
	 */
	private final String string;

	/**
	 * <p>Enumeration constructor.</p>
	 * @param string the {@link String} representation
	 * @since 1.0.0
	 */
	LineSeparator(final String string) {
		this.string = string;
	}

	/**
	 * <p>Read the next line from the given {@link Reader} using the current {@link LineSeparator} strategy.</p>
	 * @param reader the {@link Reader} to read from
	 * @param builder the {@link StringBuilder} to write the line to
	 * @return the {@code int} value of the last read {@code char}
	 * @throws IOException might occur with I/O operations
	 * @since 1.0.0
	 */
	abstract int read(final Reader reader, final StringBuilder builder) throws IOException;

	/**
	 * <p>Return the {@link String} representation.</p>
	 * @return the {@link String} representation
	 * @since 1.0.0
	 */
	@Override
	public String toString() {
		return string;
	}

	/**
	 * <p>Attempt to detect the {@link LineSeparator} type of the given {@link Path} using a default {@link Charset}
	 * reading a sample.</p>
	 * @param path the {@link Path} of the file to analyze
	 * @return the detected {@link LineSeparator} if one has been found, {@link DEFAULT} otherwise
	 * @throws IOException might occur with I/O operations
	 * @throws NullPointerException if the {@link Path} is {@code null}
	 * @since 1.0.0
	 */
	public static LineSeparator detect(final Path path) throws IOException {
		return detect(path, Charset.defaultCharset());
	}

	/**
	 * <p>Attempt to detect the {@link LineSeparator} type of the given {@link Path} using the given {@link Charset}
	 * reading a sample.</p>
	 * @param path the {@link Path} of the file to analyze
	 * @param charset the {@link Charset} to use
	 * @return the detected {@link LineSeparator} if one has been found, {@link DEFAULT} otherwise
	 * @throws IOException might occur with I/O operations
	 * @throws NullPointerException if the {@link Path} or the {@link Charset} is {@code null}
	 * @since 1.0.0
	 */
	public static LineSeparator detect(final Path path, final Charset charset) throws IOException {
		Ensure.notNull("path", path);
		Ensure.notNull("charset", charset);
		try (final var reader = Readers.of(path, charset)) {
			return detect(reader);
		}
	}

	/**
	 * <p>Attempt to detect the {@link LineSeparator} type of the given {@link Reader} using a sample.</p>
	 * <p><b>Note</b>: The {@link Reader} need to support {@link Reader#mark(int)}.</p>
	 * @param reader the {@link Reader} to analyze
	 * @return the detected {@link LineSeparator} if one has been found, {@link DEFAULT} otherwise
	 * @throws IOException might occur with I/O operations
	 * @throws NullPointerException if the {@link Reader} is {@code null}
	 * @throws IllegalArgumentException if the {@link Reader} does not support {@link Reader#mark(int)}
	 * @since 1.0.0
	 */
	public static LineSeparator detect(final Reader reader) throws IOException {
		Ensure.notNullAndMarkSupported("reader", reader);
		reader.mark(DETECTION_LIMIT);
		final var counts = new int[3];
		int i1;
		int i2;
		var i = 0;
		while (-1 != (i1 = reader.read())) {
			if ('\n' == i1) {
				++counts[LF.ordinal()];
			} else if ('\r' == i1) {
				i2 = reader.read();
				if ('\n' == i2) {
					++counts[CR_LF.ordinal()];
				} else {
					++counts[CR.ordinal()];
					if ('\r' == i2) {
						++counts[CR.ordinal()];
					}
				}
			}
			if (DETECTION_LIMIT <= ++i) {
				break;
			}
		}
		reader.reset();
		final var max = IntStream.of(counts).max().getAsInt();
		final var min = IntStream.of(counts).min().getAsInt();
		if (max != min) {
			if (max == counts[LF.ordinal()]) {
				return LF;
			} else if (max == counts[CR_LF.ordinal()]) {
				return CR_LF;
			} else {
				return CR;
			}
		}
		return DEFAULT;
	}
}