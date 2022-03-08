/*
 * MIT License
 *
 * Copyright (c) 2018-2022 Alexis Jehan
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
package com.github.alexisjehan.javanilla.io.chars;

import com.github.alexisjehan.javanilla.misc.quality.Ensure;

import java.io.BufferedReader;
import java.io.CharArrayReader;
import java.io.CharArrayWriter;
import java.io.FilterReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * <p>An utility class that provides {@link Reader} tools.</p>
 * @since 1.0.0
 */
public final class Readers {

	/**
	 * <p>A {@link Reader} that wraps multiple ones as a sequence.</p>
	 * @since 1.0.0
	 */
	private static final class SequenceReader extends Reader {

		/**
		 * <p>{@link Iterator} of {@link Reader}s.</p>
		 * @since 1.0.0
		 */
		private final Iterator<? extends Reader> iterator;

		/**
		 * <p>Current {@link Reader}.</p>
		 * @since 1.0.0
		 */
		private Reader current;

		/**
		 * <p>Private constructor.</p>
		 * @param iterator the {@link Iterator} of {@link Reader}s
		 * @since 1.0.0
		 */
		private SequenceReader(final Iterator<? extends Reader> iterator) {
			this.iterator = iterator;
			peekNextReader();
		}

		/**
		 * <p>Close the current {@link Reader} if set then peek the next one.</p>
		 * @throws IOException might occur with I/O operations
		 * @since 1.0.0
		 */
		private void nextReader() throws IOException {
			if (null != current) {
				current.close();
			}
			peekNextReader();
		}

		/**
		 * <p>Set the current {@link Reader} as the next one from the {@link Iterator}.</p>
		 * @since 1.0.0
		 */
		private void peekNextReader() {
			if (iterator.hasNext()) {
				current = iterator.next();
			} else {
				current = null;
			}
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public int read(final char[] buffer, final int offset, final int length) throws IOException {
			Ensure.notNull("buffer", buffer);
			Ensure.between("offset", offset, 0, buffer.length);
			Ensure.between("length", length, 0, buffer.length - offset);
			if (null == current) {
				return -1;
			}
			if (0 == length) {
				return 0;
			}
			do {
				final var total = current.read(buffer, offset, length);
				if (0 < total) {
					return total;
				}
				nextReader();
			} while (null != current);
			return -1;
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void close() throws IOException {
			do {
				nextReader();
			} while (null != current);
		}
	}

	/**
	 * <p>An empty {@link Reader} that returns no char.</p>
	 * @deprecated since 1.4.0, on Java 11, use {@code Reader#nullReader()} instead
	 * @since 1.0.0
	 */
	@Deprecated(since = "1.4.0")
	public static final Reader EMPTY = new Reader() {

		/**
		 * {@inheritDoc}
		 */
		@Override
		public int read(final CharBuffer buffer) {
			Ensure.notNull("buffer", buffer);
			return -1;
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public int read() {
			return -1;
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public int read(final char[] buffer) {
			Ensure.notNull("buffer", buffer);
			if (0 == buffer.length) {
				return 0;
			}
			return -1;
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public int read(final char[] buffer, final int offset, final int length) {
			Ensure.notNull("buffer", buffer);
			Ensure.between("offset", offset, 0, buffer.length);
			Ensure.between("length", length, 0, buffer.length - offset);
			if (0 == length) {
				return 0;
			}
			return -1;
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public long skip(final long number) {
			return 0L;
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void close() {
			// Do nothing
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public long transferTo(final Writer writer) {
			Ensure.notNull("writer", writer);
			return 0L;
		}
	};

	/**
	 * <p>Constructor not available.</p>
	 * @since 1.0.0
	 */
	private Readers() {
		// Not available
	}

	/**
	 * <p>Wrap a {@link Reader} replacing {@code null} by an empty one.</p>
	 * @param reader the {@link Reader} or {@code null}
	 * @return a non-{@code null} {@link Reader}
	 * @since 1.0.0
	 */
	public static Reader nullToEmpty(final Reader reader) {
		return nullToDefault(reader, EMPTY);
	}

	/**
	 * <p>Wrap a {@link Reader} replacing {@code null} by a default one.</p>
	 * @param reader the {@link Reader} or {@code null}
	 * @param defaultReader the default {@link Reader}
	 * @param <R> the {@link Reader} type
	 * @return a non-{@code null} {@link Reader}
	 * @throws NullPointerException if the default {@link Reader} is {@code null}
	 * @since 1.1.0
	 */
	public static <R extends Reader> R nullToDefault(final R reader, final R defaultReader) {
		Ensure.notNull("defaultReader", defaultReader);
		return null != reader ? reader : defaultReader;
	}

	/**
	 * <p>Decorate a {@link Reader} as a {@link BufferedReader} if it was not already.</p>
	 * @param reader the {@link Reader} to decorate
	 * @return a {@link BufferedReader}
	 * @throws NullPointerException if the {@link Reader} is {@code null}
	 * @since 1.0.0
	 */
	public static BufferedReader buffered(final Reader reader) {
		Ensure.notNull("reader", reader);
		if (reader instanceof BufferedReader) {
			return (BufferedReader) reader;
		}
		return new BufferedReader(reader);
	}

	/**
	 * <p>Decorate a {@link Reader} so that it supports {@link Reader#mark(int)} if it did not already.</p>
	 * @param reader the {@link Reader} to decorate
	 * @return a {@link Reader} with mark supported
	 * @throws NullPointerException if the {@link Reader} is {@code null}
	 * @since 1.0.0
	 */
	public static Reader markSupported(final Reader reader) {
		Ensure.notNull("reader", reader);
		if (reader.markSupported()) {
			return reader;
		}
		return new BufferedReader(reader);
	}

	/**
	 * <p>Decorate a {@link Reader} so that its {@link Reader#close()} method has no effect.</p>
	 * @param reader the {@link Reader} to decorate
	 * @return a {@link Reader} that cannot be closed
	 * @throws NullPointerException if the {@link Reader} is {@code null}
	 * @since 1.0.0
	 */
	public static Reader uncloseable(final Reader reader) {
		Ensure.notNull("reader", reader);
		return new FilterReader(reader) {

			/**
			 * {@inheritDoc}
			 */
			@Override
			public void close() {
				// Do nothing
			}
		};
	}

	/**
	 * <p>Read the {@link Reader} from the current position to the end and return the length.</p>
	 * <p><b>Note</b>: The {@link Reader} will not be closed.</p>
	 * <p><b>Warning</b>: Can produce an infinite loop if the {@link Reader} does not end.</p>
	 * @param reader the {@link Reader} to read
	 * @return the length from the current position
	 * @throws IOException might occur with I/O operations
	 * @throws NullPointerException if the {@link Reader} is {@code null}
	 * @since 1.0.0
	 */
	@SuppressWarnings("deprecation")
	public static long length(final Reader reader) throws IOException {
		Ensure.notNull("reader", reader);
		return reader.transferTo(Writers.EMPTY);
	}

	/**
	 * <p>Concatenate multiple {@link Reader}s.</p>
	 * @param readers the {@link Reader} array to concatenate
	 * @return the concatenated {@link Reader}
	 * @throws NullPointerException if the {@link Reader} array or any of them is {@code null}
	 * @since 1.0.0
	 */
	public static Reader concat(final Reader... readers) {
		Ensure.notNullAndNotNullElements("readers", readers);
		return concat(List.of(readers));
	}

	/**
	 * <p>Concatenate multiple {@link Reader}s.</p>
	 * @param readers the {@link Reader} {@link List} to concatenate
	 * @return the concatenated {@link Reader}
	 * @throws NullPointerException if the {@link Reader} {@link List} or any of them is {@code null}
	 * @since 1.0.0
	 */
	public static Reader concat(final List<? extends Reader> readers) {
		Ensure.notNullAndNotNullElements("readers", readers);
		final var size = readers.size();
		if (0 == size) {
			return EMPTY;
		}
		if (1 == size) {
			return readers.get(0);
		}
		return new SequenceReader(readers.iterator());
	}

	/**
	 * <p>Join multiple {@link Reader}s using a {@code char} array separator.</p>
	 * @param separator the {@code char} array separator
	 * @param readers the {@link Reader} array to join
	 * @return the joined {@link Reader}
	 * @throws NullPointerException if the {@code char} array separator, the {@link Reader} array or any of them is
	 *         {@code null}
	 * @since 1.0.0
	 */
	public static Reader join(final char[] separator, final Reader... readers) {
		Ensure.notNullAndNotNullElements("readers", readers);
		return join(separator, List.of(readers));
	}

	/**
	 * <p>Join multiple {@link Reader}s using a {@code char} array separator.</p>
	 * @param separator the {@code char} array separator
	 * @param readers the {@link Reader} {@link List} to join
	 * @return the joined {@link Reader}
	 * @throws NullPointerException if the {@code char} array separator, the {@link Reader} {@link List} or any of them
	 *         is {@code null}
	 * @since 1.0.0
	 */
	public static Reader join(final char[] separator, final List<? extends Reader> readers) {
		Ensure.notNull("separator", separator);
		Ensure.notNullAndNotNullElements("readers", readers);
		if (0 == separator.length) {
			return concat(readers);
		}
		final var size = readers.size();
		if (0 == size) {
			return EMPTY;
		}
		if (1 == size) {
			return readers.get(0);
		}
		final var list = new ArrayList<Reader>(2 * size - 1);
		final var iterator = readers.iterator();
		list.add(iterator.next());
		while (iterator.hasNext()) {
			list.add(of(separator));
			list.add(iterator.next());
		}
		return new SequenceReader(list.iterator());
	}

	/**
	 * <p>Create a {@link Reader} from a single {@code char}.</p>
	 * @param c the {@code char} to convert
	 * @return the created {@link Reader}
	 * @since 1.1.0
	 */
	public static Reader singleton(final char c) {
		return of(c);
	}

	/**
	 * <p>Create a {@link Reader} from multiple {@code char}s.</p>
	 * @param chars the {@code char} array to convert
	 * @return the created {@link Reader}
	 * @throws NullPointerException if the {@code char} array is {@code null}
	 * @since 1.0.0
	 */
	public static Reader of(final char... chars) {
		Ensure.notNull("chars", chars);
		if (0 == chars.length) {
			return EMPTY;
		}
		return new CharArrayReader(chars);
	}

	/**
	 * <p>Create a {@link Reader} from a {@link String}.</p>
	 * @param string the {@link String} to convert
	 * @return the created {@link Reader}
	 * @throws NullPointerException if the {@link String} is {@code null}
	 * @since 1.0.0
	 */
	public static Reader of(final String string) {
		Ensure.notNull("string", string);
		if (string.isEmpty()) {
			return EMPTY;
		}
		return new StringReader(string);
	}

	/**
	 * <p>Create a {@link BufferedReader} from a {@link Path} using {@link Charset#defaultCharset()}.</p>
	 * @param path the {@link Path} to convert
	 * @return the created {@link BufferedReader}
	 * @throws IOException might occur with I/O operations
	 * @throws NullPointerException if the {@link Path} is {@code null}
	 * @since 1.2.0
	 */
	public static BufferedReader of(final Path path) throws IOException {
		return of(path, Charset.defaultCharset());
	}

	/**
	 * <p>Create a {@link BufferedReader} from a {@link Path} using a custom {@link Charset}.</p>
	 * @param path the {@link Path} to convert
	 * @param charset the {@link Charset} to use
	 * @return the created {@link BufferedReader}
	 * @throws IOException might occur with I/O operations
	 * @throws NullPointerException if the {@link Path} or the {@link Charset} is {@code null}
	 * @since 1.2.0
	 */
	public static BufferedReader of(final Path path, final Charset charset) throws IOException {
		Ensure.notNull("path", path);
		Ensure.notNull("charset", charset);
		return Files.newBufferedReader(path, charset);
	}

	/**
	 * <p>Convert a {@link Reader} to a {@code char} array.</p>
	 * <p><b>Note</b>: The {@link Reader} will not be closed.</p>
	 * <p><b>Warning</b>: Can produce a memory overflow if the {@link Reader} is too large.</p>
	 * @param reader the {@link Reader} to convert
	 * @return the created {@code char} array
	 * @throws IOException might occur with I/O operations
	 * @throws NullPointerException if the {@link Reader} is {@code null}
	 * @since 1.0.0
	 */
	public static char[] toChars(final Reader reader) throws IOException {
		Ensure.notNull("reader", reader);
		try (final var writer = new CharArrayWriter()) {
			reader.transferTo(writer);
			return writer.toCharArray();
		}
	}

	/**
	 * <p>Convert a {@link Reader} to a {@link String}.</p>
	 * <p><b>Note</b>: The {@link Reader} will not be closed.</p>
	 * <p><b>Warning</b>: Can produce a memory overflow if the {@link Reader} is too large.</p>
	 * @param reader the {@link Reader} to convert
	 * @return the created {@link String}
	 * @throws IOException might occur with I/O operations
	 * @throws NullPointerException if the {@link Reader} is {@code null}
	 * @since 1.0.0
	 */
	public static String toString(final Reader reader) throws IOException {
		Ensure.notNull("reader", reader);
		try (final var writer = new StringWriter()) {
			reader.transferTo(writer);
			return writer.toString();
		}
	}
}