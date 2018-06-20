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

import java.io.BufferedWriter;
import java.io.Closeable;
import java.io.IOException;
import java.io.Writer;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * <p>A {@link Writer} wrapper that allows to write lines based on a strict {@link LineSeparator} type <i>(unlike
 * {@link BufferedWriter})</i>.</p>
 * @since 1.0.0
 */
public class LineWriter implements Closeable {

	/**
	 * <p>Default value for whether or not a terminating new line should be appended on close.</p>
	 * @since 1.0.0
	 */
	private static final boolean DEFAULT_APPEND_TERMINATING_NEW_LINE = false;

	/**
	 * <p>Delegated {@code Writer}.</p>
	 * @since 1.0.0
	 */
	private final Writer writer;

	/**
	 * <p>{@code LineSeparator} type to use.</p>
	 * @since 1.0.0
	 */
	private final LineSeparator lineSeparator;

	/**
	 * <p>Whether or not a terminating new line should be appended on close.</p>
	 * @since 1.0.0
	 */
	private final boolean appendTerminatingNewLine;

	/**
	 * <p>Internal value to tell if the next line to write is the first one.</p>
	 * @since 1.0.0
	 */
	private boolean firstLine = true;

	/**
	 * <p>Packed-private constructor used by {@link FilterLineWriter#FilterLineWriter(LineWriter)}.</p>
	 * @since 1.0.0
	 */
	LineWriter() {
		writer = null;
		lineSeparator = null;
		appendTerminatingNewLine = DEFAULT_APPEND_TERMINATING_NEW_LINE;
	}

	/**
	 * <p>Constructor with the given {@code Path} and the default {@code LineSeparator}.</p>
	 * @param file the {@code Path} of the file to write to
	 * @throws IOException might occurs with I/O operations
	 * @since 1.0.0
	 */
	public LineWriter(final Path file) throws IOException {
		this(file, LineSeparator.DEFAULT);
	}

	/**
	 * <p>Constructor with given {@code Path}, {@code LineSeparator} and the default value for whether or not a
	 * terminating new line should be appended on close.</p>
	 * @param file the {@code Path} of the file to write to
	 * @param lineSeparator the {@code LineSeparator} type
	 * @throws IOException might occurs with I/O operations
	 * @throws NullPointerException if the {@code LineSeparator} is {@code null}
	 * @since 1.0.0
	 */
	public LineWriter(final Path file, final LineSeparator lineSeparator) throws IOException {
		this(file, lineSeparator, DEFAULT_APPEND_TERMINATING_NEW_LINE);
	}

	/**
	 * <p>Constructor with given {@code Path}, {@code LineSeparator} and whether or not a terminating new line should be
	 * appended on close.</p>
	 * @param file the {@code Path} of the file to write to
	 * @param lineSeparator the {@code LineSeparator} type
	 * @param appendTerminatingNewLine whether or not a terminating new line should be appended on close
	 * @throws IOException might occurs with I/O operations
	 * @throws NullPointerException if the {@code LineSeparator} is {@code null}
	 * @since 1.0.0
	 */
	public LineWriter(final Path file, final LineSeparator lineSeparator, final boolean appendTerminatingNewLine) throws IOException {
		this(Files.newBufferedWriter(file), lineSeparator, appendTerminatingNewLine);
	}

	/**
	 * <p>Constructor with given {@code Path}, {@code Charset} and the default {@code LineSeparator}.</p>
	 * @param file the {@code Path} of the file to write to
	 * @param charset the {@code Charset} to use
	 * @throws IOException might occurs with I/O operations
	 * @since 1.0.0
	 */
	public LineWriter(final Path file, final Charset charset) throws IOException {
		this(file, charset, LineSeparator.DEFAULT);
	}

	/**
	 * <p>Constructor with given {@code Path}, {@code Charset}, {@code LineSeparator} and the default value for whether
	 * or not a terminating new line should be appended on close.</p>
	 * @param file the {@code Path} of the file to write to
	 * @param charset the {@code Charset} to use
	 * @param lineSeparator the {@code LineSeparator} type
	 * @throws IOException might occurs with I/O operations
	 * @throws NullPointerException if the {@code LineSeparator} is {@code null}
	 * @since 1.0.0
	 */
	public LineWriter(final Path file, final Charset charset, final LineSeparator lineSeparator) throws IOException {
		this(file, charset, lineSeparator, DEFAULT_APPEND_TERMINATING_NEW_LINE);
	}

	/**
	 * <p>Constructor with given {@code Path}, {@code Charset}, {@code LineSeparator} and whether or not a terminating
	 * new line should be appended on close.</p>
	 * @param file the {@code Path} of the file to write to
	 * @param charset the {@code Charset} to use
	 * @param lineSeparator the {@code LineSeparator} type
	 * @param appendTerminatingNewLine whether or not a terminating new line should be appended on close
	 * @throws IOException might occurs with I/O operations
	 * @throws NullPointerException if the {@code LineSeparator} is {@code null}
	 * @since 1.0.0
	 */
	public LineWriter(final Path file, final Charset charset, final LineSeparator lineSeparator, final boolean appendTerminatingNewLine) throws IOException {
		this(Files.newBufferedWriter(file, charset), lineSeparator, appendTerminatingNewLine);
	}

	/**
	 * <p>Constructor with the given {@code Writer} and the default {@code LineSeparator}.</p>
	 * @param writer the delegated {@code Writer}
	 * @throws NullPointerException if the {@code Writer} is {@code null}
	 * @since 1.0.0
	 */
	public LineWriter(final Writer writer) {
		this(writer, LineSeparator.DEFAULT);
	}

	/**
	 * <p>Constructor with given {@code Writer}, {@code LineSeparator} and the default value for whether or not a
	 * terminating new line should be appended on close.</p>
	 * @param writer the delegated {@code Writer}
	 * @param lineSeparator the {@code LineSeparator} type
	 * @throws NullPointerException whether the {@code Writer} or the {@code LineSeparator} is {@code null}
	 * @since 1.0.0
	 */
	public LineWriter(final Writer writer, final LineSeparator lineSeparator) {
		this(writer, lineSeparator, DEFAULT_APPEND_TERMINATING_NEW_LINE);
	}

	/**
	 * <p>Constructor with given {@code Writer}, {@code LineSeparator} and whether or not a terminating new line should
	 * be appended on close.</p>
	 * @param writer the delegated {@code Writer}
	 * @param lineSeparator the {@code LineSeparator} type
	 * @param appendTerminatingNewLine whether or not a terminating new line should be appended on close
	 * @throws NullPointerException whether the {@code Writer} or the {@code LineSeparator} is {@code null}
	 * @since 1.0.0
	 */
	public LineWriter(final Writer writer, final LineSeparator lineSeparator, final boolean appendTerminatingNewLine) {
		if (null == writer) {
			throw new NullPointerException("Invalid writer (not null expected)");
		}
		if (null == lineSeparator) {
			throw new NullPointerException("Invalid line separator (not null expected)");
		}
		this.writer = writer;
		this.lineSeparator = lineSeparator;
		this.appendTerminatingNewLine = appendTerminatingNewLine;
	}

	/**
	 * <p>Writes a {@code String} line.</p>
	 * @param line the {@code String} line to write
	 * @throws IOException might occurs with I/O operations
	 * @throws NullPointerException if the {@code String} line is {@code null}
	 * @since 1.0.0
	 */
	public void write(final String line) throws IOException {
		if (null == line) {
			throw new NullPointerException("Invalid line (not null expected)");
		}
		if (!firstLine) {
			newLine();
		} else {
			firstLine = false;
		}
		writer.write(line);
	}

	/**
	 * <p>Writes a new line whose representation is based on the {@code LineSeparator} type.</p>
	 * @throws IOException might occurs with I/O operations
	 * @since 1.0.0
	 */
	public void newLine() throws IOException {
		writer.write(lineSeparator.toString());
	}

	/**
	 * <p>Flush the delegated {@code Writer}.</p>
	 * @throws IOException might occurs with I/O operations
	 * @since 1.0.0
	 */
	public void flush() throws IOException {
		writer.flush();
	}

	@Override
	public void close() throws IOException {
		if (appendTerminatingNewLine) {
			newLine();
		}
		writer.close();
	}
}