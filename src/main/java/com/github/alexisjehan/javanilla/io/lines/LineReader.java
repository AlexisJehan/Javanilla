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
package com.github.alexisjehan.javanilla.io.lines;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.IOException;
import java.io.Reader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * <p>A {@link Reader} wrapper that allows to read lines based on a strict {@link LineSeparator} type <i>(unlike
 * {@link BufferedReader})</i>.</p>
 * @since 1.0.0
 */
public class LineReader implements Closeable {

	/**
	 * <p>Default value for whether or not a terminating new line should be ignored.</p>
	 * @since 1.0.0
	 */
	private static final boolean DEFAULT_IGNORE_TERMINATING_NEW_LINE = true;

	/**
	 * <p>Delegated {@code Reader}.</p>
	 * @since 1.0.0
	 */
	private final Reader reader;

	/**
	 * <p>{@code LineSeparator} type to use.</p>
	 * @since 1.0.0
	 */
	private final LineSeparator lineSeparator;

	/**
	 * <p>Whether or not a terminating new line should be ignored.</p>
	 * @since 1.0.0
	 */
	private final boolean ignoreTerminatingNewLine;

	/**
	 * <p>Internal value to tell if the next line to read is the last one.</p>
	 * @since 1.0.0
	 */
	private boolean lastLine = false;

	/**
	 * <p>A {@code StringBuilder} to build the read {@code String} line, filled by
	 * {@link LineSeparator#read(Reader, StringBuilder)}.</p>
	 * @since 1.0.0
	 */
	private final StringBuilder builder = new StringBuilder(80);

	/**
	 * <p>Package-private constructor used by {@link FilterLineReader#FilterLineReader(LineReader)}.</p>
	 * @since 1.0.0
	 */
	LineReader() {
		reader = null;
		lineSeparator = null;
		ignoreTerminatingNewLine = DEFAULT_IGNORE_TERMINATING_NEW_LINE;
	}

	/**
	 * <p>Constructor with the given {@code Path}, detecting the {@code LineSeparator}.</p>
	 * @param file the {@code Path} of the file to read from
	 * @throws IOException might occurs with I/O operations
	 * @since 1.0.0
	 */
	public LineReader(final Path file) throws IOException {
		this(file, LineSeparator.detect(file));
	}

	/**
	 * <p>Constructor with given {@code Path}, {@code LineSeparator} and the default value for whether or not a
	 * terminating new line should be ignored.</p>
	 * @param file the {@code Path} of the file to read from
	 * @param lineSeparator the {@code LineSeparator} type
	 * @throws IOException might occurs with I/O operations
	 * @throws NullPointerException if the {@code LineSeparator} is {@code null}
	 * @since 1.0.0
	 */
	public LineReader(final Path file, final LineSeparator lineSeparator) throws IOException {
		this(file, lineSeparator, DEFAULT_IGNORE_TERMINATING_NEW_LINE);
	}

	/**
	 * <p>Constructor with given {@code Path}, {@code LineSeparator} and whether or not a terminating new line should be
	 * ignored.</p>
	 * @param file the {@code Path} of the file to read from
	 * @param lineSeparator the {@code LineSeparator} type
	 * @param ignoreTerminatingNewLine whether or not a terminating new line should be ignored
	 * @throws IOException might occurs with I/O operations
	 * @throws NullPointerException if the {@code LineSeparator} is {@code null}
	 * @since 1.0.0
	 */
	public LineReader(final Path file, final LineSeparator lineSeparator, final boolean ignoreTerminatingNewLine) throws IOException {
		this(Files.newBufferedReader(file), lineSeparator, ignoreTerminatingNewLine);
	}

	/**
	 * <p>Constructor with given {@code Path} and {@code Charset}, detecting the {@code LineSeparator}.</p>
	 * @param file the {@code Path} of the file to read from
	 * @param charset the {@code Charset} to use
	 * @throws IOException might occurs with I/O operations
	 * @since 1.0.0
	 */
	public LineReader(final Path file, final Charset charset) throws IOException {
		this(file, charset, LineSeparator.detect(file, charset));
	}

	/**
	 * <p>Constructor with given {@code Path}, {@code Charset}, {@code LineSeparator} and the default value for whether
	 * or not a terminating new line should be ignored.</p>
	 * @param file the {@code Path} of the file to read from
	 * @param charset the {@code Charset} to use
	 * @param lineSeparator the {@code LineSeparator} type
	 * @throws IOException might occurs with I/O operations
	 * @throws NullPointerException if the {@code LineSeparator} is {@code null}
	 * @since 1.0.0
	 */
	public LineReader(final Path file, final Charset charset, final LineSeparator lineSeparator) throws IOException {
		this(file, charset, lineSeparator, DEFAULT_IGNORE_TERMINATING_NEW_LINE);
	}

	/**
	 * <p>Constructor with given {@code Path}, {@code Charset}, {@code LineSeparator} and whether or not a terminating
	 * new line should be ignored.</p>
	 * @param file the {@code Path} of the file to read from
	 * @param charset the {@code Charset} to use
	 * @param lineSeparator the {@code LineSeparator} type
	 * @param ignoreTerminatingNewLine whether or not a terminating new line should be ignored
	 * @throws IOException might occurs with I/O operations
	 * @throws NullPointerException if the {@code LineSeparator} is {@code null}
	 * @since 1.0.0
	 */
	public LineReader(final Path file, final Charset charset, final LineSeparator lineSeparator, final boolean ignoreTerminatingNewLine) throws IOException {
		this(Files.newBufferedReader(file, charset), lineSeparator, ignoreTerminatingNewLine);
	}

	/**
	 * <p>Constructor with the given {@code Reader}, detecting the {@code LineSeparator}.</p>
	 * <p><b>Note</b>: The {@code Reader} need to support {@link Reader#mark(int)}.</p>
	 * @param reader the {@code Reader} to read from
	 * @throws IOException might occurs with I/O operations
	 * @throws NullPointerException if the {@code Reader} is {@code null}
	 * @throws IllegalArgumentException if the {@code Reader} does not support {@link Reader#mark(int)}
	 * @since 1.0.0
	 */
	public LineReader(final Reader reader) throws IOException {
		this(reader, LineSeparator.detect(reader));
	}

	/**
	 * <p>Constructor with given {@code Reader}, {@code LineSeparator} and the default value for whether or not a
	 * terminating new line should be ignored.</p>
	 * @param reader the {@code Reader} to read from
	 * @param lineSeparator the {@code LineSeparator} type
	 * @throws NullPointerException if the {@code Reader} or the {@code LineSeparator} is {@code null}
	 * @since 1.0.0
	 */
	public LineReader(final Reader reader, final LineSeparator lineSeparator) {
		this(reader, lineSeparator, DEFAULT_IGNORE_TERMINATING_NEW_LINE);
	}

	/**
	 * <p>Constructor with given {@code Reader}, {@code LineSeparator} and whether or not a terminating new line should
	 * be ignored.</p>
	 * @param reader the {@code Reader} to read from
	 * @param lineSeparator the {@code LineSeparator} type
	 * @param ignoreTerminatingNewLine whether or not a terminating new line should be ignored
	 * @throws NullPointerException if the {@code Reader} or the {@code LineSeparator} is {@code null}
	 * @since 1.0.0
	 */
	public LineReader(final Reader reader, final LineSeparator lineSeparator, final boolean ignoreTerminatingNewLine) {
		if (null == reader) {
			throw new NullPointerException("Invalid Reader (not null expected)");
		}
		if (null == lineSeparator) {
			throw new NullPointerException("Invalid LineSeparator (not null expected)");
		}
		this.reader = reader;
		this.lineSeparator = lineSeparator;
		this.ignoreTerminatingNewLine = ignoreTerminatingNewLine;
	}

	/**
	 * <p>Read a line.</p>
	 * <p><b>Warning</b>: Can produce a memory overflow if the line is too large.</p>
	 * @return a read {@code String} line or {@code null} if there is no more line to read
	 * @throws IOException might occurs with I/O operations
	 * @since 1.0.0
	 */
	public String read() throws IOException {
		builder.setLength(0);
		final var c = lineSeparator.read(reader, builder);
		if (ignoreTerminatingNewLine) {
			return -1 != c || 0 < builder.length() ? builder.toString() : null;
		}
		if (lastLine) {
			return null;
		}
		if (-1 == c) {
			lastLine = true;
		}
		return builder.toString();
	}

	/**
	 * <p>Skip the given number of lines.</p>
	 * <p><b>Note</b>: {@link #read()} is used under the hood.</p>
	 * <p><b>Warning</b>: Can produce a memory overflow if any line is too large.</p>
	 * @param n the number of lines to attempt to skip
	 * @return the effective number of lines skipped
	 * @throws IOException might occurs with I/O operations
	 * @throws IllegalArgumentException if the number of lines to skip is lower than {@code 0}
	 * @since 1.0.0
	 */
	public long skip(final long n) throws IOException {
		if (0L > n) {
			throw new IllegalArgumentException("Invalid number of lines: " + n + " (greater than or equal to 0 expected)");
		}
		if (0L == n) {
			return 0L;
		}
		var s = 0L;
		while (n > s && null != read()) {
			++s;
		}
		return s;
	}

	/**
	 * <p>Transfer all lines from the current position to the given {@code LineWriter}.</p>
	 * <p><b>Warning</b>: Can produce a memory overflow if any line is too large.</p>
	 * <p><b>Warning</b>: Can produce an infinite loop if the {@code LineReader} does not end.</p>
	 * @param lineWriter the {@code LineWriter} to write lines to
	 * @return the number of lines transferred
	 * @throws IOException might occurs with I/O operations
	 * @throws NullPointerException if the {@code LineWriter} is {@code null}
	 * @since 1.0.0
	 */
	public long transferTo(final LineWriter lineWriter) throws IOException {
		if (null == lineWriter) {
			throw new NullPointerException("Invalid LineWriter (not null expected)");
		}
		var l = 0L;
		String line;
		while (null != (line = read())) {
			lineWriter.write(line);
			++l;
		}
		return l;
	}

	@Override
	public void close() throws IOException {
		reader.close();
	}
}