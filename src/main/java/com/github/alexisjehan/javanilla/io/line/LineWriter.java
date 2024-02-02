/*
 * MIT License
 *
 * Copyright (c) 2018-2023 Alexis Jehan
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

import com.github.alexisjehan.javanilla.io.Writers;
import com.github.alexisjehan.javanilla.misc.quality.Ensure;

import java.io.Closeable;
import java.io.IOException;
import java.io.Writer;
import java.nio.charset.Charset;
import java.nio.file.Path;

/**
 * <p>A {@link Writer} wrapper that allows to write lines based on a strict {@link LineSeparator} type <i>(unlike
 * {@link java.io.BufferedWriter})</i>.</p>
 * @since 1.8.0
 */
public class LineWriter implements Closeable {

	/**
	 * <p>Default value for whether or not a terminating new line should be appended on close.</p>
	 * @since 1.8.0
	 */
	private static final boolean DEFAULT_APPEND_TERMINATING_NEW_LINE = false;

	/**
	 * <p>Delegated {@link Writer}.</p>
	 * @since 1.8.0
	 */
	private final Writer writer;

	/**
	 * <p>{@link LineSeparator} type to use.</p>
	 * @since 1.8.0
	 */
	private final LineSeparator lineSeparator;

	/**
	 * <p>Whether or not a terminating new line should be appended on close.</p>
	 * @since 1.8.0
	 */
	private final boolean appendTerminatingNewLine;

	/**
	 * <p>Internal value to tell if the next line to write is the first one.</p>
	 * @since 1.8.0
	 */
	private boolean firstLine = true;

	/**
	 * <p>Constructor used by {@link FilterLineWriter#FilterLineWriter(LineWriter)}.</p>
	 * @since 1.8.0
	 */
	LineWriter() {
		writer = null;
		lineSeparator = null;
		appendTerminatingNewLine = DEFAULT_APPEND_TERMINATING_NEW_LINE;
	}

	/**
	 * <p>Constructor with the given {@link Path} and the default {@link LineSeparator}.</p>
	 * @param path the {@link Path} of the file to write to
	 * @throws IOException might occur with I/O operations
	 * @throws NullPointerException if the {@link Path} is {@code null}
	 * @since 1.8.0
	 */
	public LineWriter(final Path path) throws IOException {
		this(path, LineSeparator.DEFAULT);
	}

	/**
	 * <p>Constructor with given {@link Path}, {@link LineSeparator} and the default value for whether or not a
	 * terminating new line should be appended on close.</p>
	 * @param path the {@link Path} of the file to write to
	 * @param lineSeparator the {@link LineSeparator} type
	 * @throws IOException might occur with I/O operations
	 * @throws NullPointerException if the {@link Path} or the {@link LineSeparator} is {@code null}
	 * @since 1.8.0
	 */
	public LineWriter(final Path path, final LineSeparator lineSeparator) throws IOException {
		this(path, lineSeparator, DEFAULT_APPEND_TERMINATING_NEW_LINE);
	}

	/**
	 * <p>Constructor with given {@link Path}, {@link LineSeparator} and whether or not a terminating new line should be
	 * appended on close.</p>
	 * @param path the {@link Path} of the file to write to
	 * @param lineSeparator the {@link LineSeparator} type
	 * @param appendTerminatingNewLine whether a terminating new line should be appended on close
	 * @throws IOException might occur with I/O operations
	 * @throws NullPointerException if the {@link Path} or the {@link LineSeparator} is {@code null}
	 * @since 1.8.0
	 */
	public LineWriter(final Path path, final LineSeparator lineSeparator, final boolean appendTerminatingNewLine) throws IOException {
		this(Writers.of(path), lineSeparator, appendTerminatingNewLine);
	}

	/**
	 * <p>Constructor with given {@link Path}, {@link Charset} and the default {@link LineSeparator}.</p>
	 * @param path the {@link Path} of the file to write to
	 * @param charset the {@link Charset} to use
	 * @throws IOException might occur with I/O operations
	 * @throws NullPointerException if the {@link Path} or the {@link Charset} is {@code null}
	 * @since 1.8.0
	 */
	public LineWriter(final Path path, final Charset charset) throws IOException {
		this(path, charset, LineSeparator.DEFAULT);
	}

	/**
	 * <p>Constructor with given {@link Path}, {@link Charset}, {@link LineSeparator} and the default value for whether
	 * or not a terminating new line should be appended on close.</p>
	 * @param path the {@link Path} of the file to write to
	 * @param charset the {@link Charset} to use
	 * @param lineSeparator the {@link LineSeparator} type
	 * @throws IOException might occur with I/O operations
	 * @throws NullPointerException if the {@link Path}, the {@link Charset} or the {@link LineSeparator} is
	 *         {@code null}
	 * @since 1.8.0
	 */
	public LineWriter(final Path path, final Charset charset, final LineSeparator lineSeparator) throws IOException {
		this(path, charset, lineSeparator, DEFAULT_APPEND_TERMINATING_NEW_LINE);
	}

	/**
	 * <p>Constructor with given {@link Path}, {@link Charset}, {@link LineSeparator} and whether or not a terminating
	 * new line should be appended on close.</p>
	 * @param path the {@link Path} of the file to write to
	 * @param charset the {@link Charset} to use
	 * @param lineSeparator the {@link LineSeparator} type
	 * @param appendTerminatingNewLine whether a terminating new line should be appended on close
	 * @throws IOException might occur with I/O operations
	 * @throws NullPointerException if the {@link Path}, the {@link Charset} or the {@link LineSeparator} is
	 *         {@code null}
	 * @since 1.8.0
	 */
	public LineWriter(final Path path, final Charset charset, final LineSeparator lineSeparator, final boolean appendTerminatingNewLine) throws IOException {
		this(Writers.of(path, charset), lineSeparator, appendTerminatingNewLine);
	}

	/**
	 * <p>Constructor with the given {@link Writer} and the default {@link LineSeparator}.</p>
	 * @param writer the {@link Writer} to write to
	 * @throws NullPointerException if the {@link Writer} is {@code null}
	 * @since 1.8.0
	 */
	public LineWriter(final Writer writer) {
		this(writer, LineSeparator.DEFAULT);
	}

	/**
	 * <p>Constructor with given {@link Writer}, {@link LineSeparator} and the default value for whether or not a
	 * terminating new line should be appended on close.</p>
	 * @param writer the {@link Writer} to write to
	 * @param lineSeparator the {@link LineSeparator} type
	 * @throws NullPointerException if the {@link Writer} or the {@link LineSeparator} is {@code null}
	 * @since 1.8.0
	 */
	public LineWriter(final Writer writer, final LineSeparator lineSeparator) {
		this(writer, lineSeparator, DEFAULT_APPEND_TERMINATING_NEW_LINE);
	}

	/**
	 * <p>Constructor with given {@link Writer}, {@link LineSeparator} and whether or not a terminating new line should
	 * be appended on close.</p>
	 * @param writer the {@link Writer} to write to
	 * @param lineSeparator the {@link LineSeparator} type
	 * @param appendTerminatingNewLine whether a terminating new line should be appended on close
	 * @throws NullPointerException if the {@link Writer} or the {@link LineSeparator} is {@code null}
	 * @since 1.8.0
	 */
	public LineWriter(final Writer writer, final LineSeparator lineSeparator, final boolean appendTerminatingNewLine) {
		Ensure.notNull("writer", writer);
		Ensure.notNull("lineSeparator", lineSeparator);
		this.writer = writer;
		this.lineSeparator = lineSeparator;
		this.appendTerminatingNewLine = appendTerminatingNewLine;
	}

	/**
	 * <p>Writes a line.</p>
	 * @param line the {@link String} line to write
	 * @throws IOException might occur with I/O operations
	 * @throws NullPointerException if the line is {@code null}
	 * @since 1.8.0
	 */
	public void write(final String line) throws IOException {
		Ensure.notNull("line", line);
		if (!firstLine) {
			newLine();
		} else {
			firstLine = false;
		}
		writer.write(line);
	}

	/**
	 * <p>Writes a new line whose representation is based on the {@link LineSeparator} type.</p>
	 * @throws IOException might occur with I/O operations
	 * @since 1.8.0
	 */
	public void newLine() throws IOException {
		writer.write(lineSeparator.toString());
	}

	/**
	 * <p>Flush the delegated {@link Writer}.</p>
	 * @throws IOException might occur with I/O operations
	 * @since 1.8.0
	 */
	public void flush() throws IOException {
		writer.flush();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void close() throws IOException {
		if (appendTerminatingNewLine) {
			newLine();
		}
		writer.close();
	}
}