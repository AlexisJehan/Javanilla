/*
 * MIT License
 *
 * Copyright (c) 2018-2026 Alexis Jehan
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
package com.github.alexisjehan.javanilla.io.line;

import com.github.alexisjehan.javanilla.io.Readers;
import com.github.alexisjehan.javanilla.misc.quality.Ensure;

import java.io.Closeable;
import java.io.IOException;
import java.io.Reader;
import java.nio.charset.Charset;
import java.nio.file.Path;

/**
 * A {@link Reader} wrapper that allows to read lines based on a strict {@link LineSeparator} type <i>(unlike
 * {@link java.io.BufferedReader})</i>.
 * @since 1.8.0
 */
public class LineReader implements Closeable {

	/**
	 * Default value for whether a terminating new line should be ignored.
	 * @since 1.8.0
	 */
	private static final boolean DEFAULT_IGNORE_TERMINATING_NEW_LINE = true;

	/**
	 * Delegated {@link Reader}.
	 * @since 1.8.0
	 */
	private final Reader reader;

	/**
	 * {@link LineSeparator} type to use.
	 * @since 1.8.0
	 */
	private final LineSeparator lineSeparator;

	/**
	 * Whether a terminating new line should be ignored.
	 * @since 1.8.0
	 */
	private final boolean ignoreTerminatingNewLine;

	/**
	 * Internal value to tell if the next line to read is the last one.
	 * @since 1.8.0
	 */
	private boolean lastLine;

	/**
	 * A {@link StringBuilder} to build the read {@link String} line, filled by
	 * {@link LineSeparator#read(Reader, StringBuilder)}.
	 * @since 1.8.0
	 */
	private final StringBuilder builder = new StringBuilder(80);

	/**
	 * Constructor used by {@link FilterLineReader#FilterLineReader(LineReader)}.
	 * @since 1.8.0
	 */
	LineReader() {
		reader = null;
		lineSeparator = null;
		ignoreTerminatingNewLine = DEFAULT_IGNORE_TERMINATING_NEW_LINE;
	}

	/**
	 * Constructor with the given {@link Path}, detecting the {@link LineSeparator}.
	 * @param path the {@link Path} of the file to read from
	 * @throws IOException might occur with I/O operations
	 * @throws NullPointerException if the {@link Path} is {@code null}
	 * @since 1.8.0
	 */
	public LineReader(final Path path) throws IOException {
		this(path, LineSeparator.detect(path));
	}

	/**
	 * Constructor with given {@link Path}, {@link LineSeparator} and the default value for whether a terminating new
	 * line should be ignored.
	 * @param path the {@link Path} of the file to read from
	 * @param lineSeparator the {@link LineSeparator} type
	 * @throws IOException might occur with I/O operations
	 * @throws NullPointerException if the {@link Path} or the {@link LineSeparator} is {@code null}
	 * @since 1.8.0
	 */
	public LineReader(final Path path, final LineSeparator lineSeparator) throws IOException {
		this(path, lineSeparator, DEFAULT_IGNORE_TERMINATING_NEW_LINE);
	}

	/**
	 * Constructor with given {@link Path}, {@link LineSeparator} and whether a terminating new line should be ignored.
	 * @param path the {@link Path} of the file to read from
	 * @param lineSeparator the {@link LineSeparator} type
	 * @param ignoreTerminatingNewLine whether a terminating new line should be ignored
	 * @throws IOException might occur with I/O operations
	 * @throws NullPointerException if the {@link Path} or the {@link LineSeparator} is {@code null}
	 * @since 1.8.0
	 */
	public LineReader(final Path path, final LineSeparator lineSeparator, final boolean ignoreTerminatingNewLine) throws IOException {
		this(Readers.of(path), lineSeparator, ignoreTerminatingNewLine);
	}

	/**
	 * Constructor with given {@link Path} and {@link Charset}, detecting the {@link LineSeparator}.
	 * @param path the {@link Path} of the file to read from
	 * @param charset the {@link Charset} to use
	 * @throws IOException might occur with I/O operations
	 * @throws NullPointerException if the {@link Path} or the {@link Charset} is {@code null}
	 * @since 1.8.0
	 */
	public LineReader(final Path path, final Charset charset) throws IOException {
		this(path, charset, LineSeparator.detect(path, charset));
	}

	/**
	 * Constructor with given {@link Path}, {@link Charset}, {@link LineSeparator} and the default value for whether a
	 * terminating new line should be ignored.
	 * @param path the {@link Path} of the file to read from
	 * @param charset the {@link Charset} to use
	 * @param lineSeparator the {@link LineSeparator} type
	 * @throws IOException might occur with I/O operations
	 * @throws NullPointerException if the {@link Path}, the {@link Charset} or the {@link LineSeparator} is
	 *         {@code null}
	 * @since 1.8.0
	 */
	public LineReader(final Path path, final Charset charset, final LineSeparator lineSeparator) throws IOException {
		this(path, charset, lineSeparator, DEFAULT_IGNORE_TERMINATING_NEW_LINE);
	}

	/**
	 * Constructor with given {@link Path}, {@link Charset}, {@link LineSeparator} and whether a terminating new line
	 * should be ignored.
	 * @param path the {@link Path} of the file to read from
	 * @param charset the {@link Charset} to use
	 * @param lineSeparator the {@link LineSeparator} type
	 * @param ignoreTerminatingNewLine whether a terminating new line should be ignored
	 * @throws IOException might occur with I/O operations
	 * @throws NullPointerException if the {@link Path}, the {@link Charset} or the {@link LineSeparator} is
	 *         {@code null}
	 * @since 1.8.0
	 */
	public LineReader(final Path path, final Charset charset, final LineSeparator lineSeparator, final boolean ignoreTerminatingNewLine) throws IOException {
		this(Readers.of(path, charset), lineSeparator, ignoreTerminatingNewLine);
	}

	/**
	 * Constructor with the given {@link Reader}, detecting the {@link LineSeparator}.
	 *
	 * <p><b>Note</b>: The {@link Reader} need to support {@link Reader#mark(int)}.</p>
	 * @param reader the {@link Reader} to read from
	 * @throws IOException might occur with I/O operations
	 * @throws NullPointerException if the {@link Reader} is {@code null}
	 * @throws IllegalArgumentException if the {@link Reader} does not support {@link Reader#mark(int)}
	 * @since 1.8.0
	 */
	public LineReader(final Reader reader) throws IOException {
		this(reader, LineSeparator.detect(reader));
	}

	/**
	 * Constructor with given {@link Reader}, {@link LineSeparator} and the default value for whether a terminating new
	 * line should be ignored.
	 * @param reader the {@link Reader} to read from
	 * @param lineSeparator the {@link LineSeparator} type
	 * @throws NullPointerException if the {@link Reader} or the {@link LineSeparator} is {@code null}
	 * @since 1.8.0
	 */
	public LineReader(final Reader reader, final LineSeparator lineSeparator) {
		this(reader, lineSeparator, DEFAULT_IGNORE_TERMINATING_NEW_LINE);
	}

	/**
	 * Constructor with given {@link Reader}, {@link LineSeparator} and whether a terminating new line should be
	 * ignored.
	 * @param reader the {@link Reader} to read from
	 * @param lineSeparator the {@link LineSeparator} type
	 * @param ignoreTerminatingNewLine whether a terminating new line should be ignored
	 * @throws NullPointerException if the {@link Reader} or the {@link LineSeparator} is {@code null}
	 * @since 1.8.0
	 */
	public LineReader(final Reader reader, final LineSeparator lineSeparator, final boolean ignoreTerminatingNewLine) {
		Ensure.notNull("reader", reader);
		Ensure.notNull("lineSeparator", lineSeparator);
		this.reader = reader;
		this.lineSeparator = lineSeparator;
		this.ignoreTerminatingNewLine = ignoreTerminatingNewLine;
	}

	/**
	 * Read a line.
	 *
	 * <p><b>Warning</b>: Can produce a memory overflow if the line is too large.</p>
	 * @return a read {@link String} line or {@code null} if there is no more line to read
	 * @throws IOException might occur with I/O operations
	 * @since 1.8.0
	 */
	public String read() throws IOException {
		builder.setLength(0);
		final var last = lineSeparator.read(reader, builder);
		if (ignoreTerminatingNewLine) {
			return -1 != last || 0 < builder.length() ? builder.toString() : null;
		}
		if (lastLine) {
			return null;
		}
		if (-1 == last) {
			lastLine = true;
		}
		return builder.toString();
	}

	/**
	 * Skip the given number of lines.
	 *
	 * <p><b>Note</b>: {@link #read()} is used under the hood.</p>
	 *
	 * <p><b>Warning</b>: Can produce a memory overflow if any line is too large.</p>
	 * @param number the number of lines to attempt to skip
	 * @return the actual number of lines skipped
	 * @throws IOException might occur with I/O operations
	 * @throws IllegalArgumentException if the number is lower than {@code 0}
	 * @since 1.8.0
	 */
	public long skip(final long number) throws IOException {
		Ensure.greaterThanOrEqualTo("number", number, 0L);
		if (0L == number) {
			return 0L;
		}
		var actual = 0L;
		while (number > actual && null != read()) {
			++actual;
		}
		return actual;
	}

	/**
	 * Transfer all lines from the current position to the given {@link LineWriter}.
	 *
	 * <p><b>Warning</b>: Can produce a memory overflow if any line is too large.</p>
	 *
	 * <p><b>Warning</b>: Can produce an infinite loop if the {@code LineReader} does not end.</p>
	 * @param lineWriter the {@link LineWriter} to write lines to
	 * @return the number of lines transferred
	 * @throws IOException might occur with I/O operations
	 * @throws NullPointerException if the {@link LineWriter} is {@code null}
	 * @since 1.8.0
	 */
	public long transferTo(final LineWriter lineWriter) throws IOException {
		Ensure.notNull("lineWriter", lineWriter);
		var number = 0L;
		String line;
		while (null != (line = read())) {
			lineWriter.write(line);
			++number;
		}
		return number;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void close() throws IOException {
		reader.close();
	}
}