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

import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

/**
 * <p>An utility class that provides {@link Reader} tools.</p>
 * @since 1.0.0
 */
public final class Readers {

	/**
	 * <p>A {@link Reader} that wraps multiple ones as a sequence.</p>
	 * @since 1.0.0
	 */
	private static class SequenceReader extends Reader {

		/**
		 * <p>{@code Iterator} of wrapped {@code Reader}s.</p>
		 * @since 1.0.0
		 */
		private final Iterator<? extends Reader> iterator;

		/**
		 * <p>Current {@code Reader}.</p>
		 * @since 1.0.0
		 */
		private Reader current;

		/**
		 * <p>Private constructor.</p>
		 * @param iterable the {@code Reader} iterable
		 * @since 1.0.0
		 */
		private SequenceReader(final Iterable<? extends Reader> iterable) {
			iterator = iterable.iterator();
			peekNextReader();
		}

		/**
		 * <p>Close the current {@code Reader} if set then peek a new one.</p>
		 * @throws IOException might occurs with I/O operations
		 * @since 1.0.0
		 */
		private void nextReader() throws IOException {
			if (null != current) {
				current.close();
			}
			peekNextReader();
		}

		/**
		 * <p>Set the current {@code Reader} as the next one from the iterator.</p>
		 * @since 1.0.0
		 */
		private void peekNextReader() {
			if (iterator.hasNext()) {
				current = iterator.next();
			} else {
				current = null;
			}
		}

		@Override
		public int read(@NotNull final char[] cbuf, final int off, final int len) throws IOException {
			if (null == current) {
				return -1;
			}
			if (0 > off || off > cbuf.length || 0 > len || off + len > cbuf.length) {
				throw new IndexOutOfBoundsException();
			}
			if (0 == len) {
				return 0;
			}
			do {
				final var n = current.read(cbuf, off, len);
				if (0 < n) {
					return n;
				}
				nextReader();
			} while (null != current);
			return -1;
		}

		@Override
		public void close() throws IOException {
			do {
				nextReader();
			} while (null != current);
		}
	}

	/**
	 * <p>An empty {@code Reader} that returns no char.</p>
	 * @since 1.0.0
	 */
	public static final Reader EMPTY = new Reader() {
		@Override
		public int read() {
			return -1;
		}

		@Override
		public int read(@NotNull final char[] cbuf, final int off, final int len) {
			if (0 == len) {
				return 0;
			}
			return -1;
		}

		@Override
		public void close() {
			// Do nothing
		}
	};

	/**
	 * <p>An endless {@code Reader} that returns random chars.</p>
	 * @since 1.0.0
	 */
	public static final Reader ENDLESS = new Reader() {
		@Override
		public int read() {
			return ThreadLocalRandom.current().nextInt(0, 65536); // Range between 0 and 65535 included
		}

		@Override
		public int read(@NotNull final char[] cbuf, final int off, final int len) {
			if (0 > off || off > cbuf.length || 0 > len || off + len > cbuf.length) {
				throw new IndexOutOfBoundsException();
			}
			if (0 == len) {
				return 0;
			}
			for (var i = 0; i < len; ++i) {
				cbuf[off + i] = (char) read();
			}
			return len;
		}

		@Override
		public void close() {
			// Do nothing
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
	 * <p>Wrap a {@code Reader} replacing {@code null} by an empty {@code Reader}.</p>
	 * @param reader a {@code Reader} or {@code null}
	 * @return the non-{@code null} {@code Reader}
	 * @since 1.0.0
	 */
	public static Reader nullToEmpty(final Reader reader) {
		return nullToDefault(reader, EMPTY);
	}

	/**
	 * <p>Wrap a {@code Reader} replacing {@code null} by a default {@code Reader}.</p>
	 * @param reader a {@code Reader} or {@code null}
	 * @param defaultReader the default {@code Reader}
	 * @return the non-{@code null} {@code Reader}
	 * @throws NullPointerException if the default {@code Reader} is {@code null}
	 * @since 1.1.0
	 */
	public static Reader nullToDefault(final Reader reader, final Reader defaultReader) {
		if (null == defaultReader) {
			throw new NullPointerException("Invalid default reader (not null expected)");
		}
		return null != reader ? reader : defaultReader;
	}

	/**
	 * <p>Wrap a {@code Reader} as a {@code BufferedReader} if it was not already.</p>
	 * @param reader the {@code Reader} to wrap
	 * @return the buffered {@code Reader}
	 * @throws NullPointerException if the {@code Reader} is {@code null}
	 * @since 1.0.0
	 */
	public static BufferedReader buffered(final Reader reader) {
		if (null == reader) {
			throw new NullPointerException("Invalid reader (not null expected)");
		}
		if (reader instanceof BufferedReader) {
			return (BufferedReader) reader;
		}
		return new BufferedReader(reader);
	}

	/**
	 * <p>Wrap a {@code Reader} so that it supports {@link Reader#mark(int)} if it did not already.</p>
	 * @param reader the {@code Reader} to wrap
	 * @return the {@code Reader} with mark supported
	 * @throws NullPointerException if the {@code Reader} is {@code null}
	 * @since 1.0.0
	 */
	public static Reader markSupported(final Reader reader) {
		if (null == reader) {
			throw new NullPointerException("Invalid reader (not null expected)");
		}
		if (reader.markSupported()) {
			return reader;
		}
		return new BufferedReader(reader);
	}

	/**
	 * <p>Wrap a {@code Reader} so that its {@link Reader#close()} method has no effect.</p>
	 * @param reader the {@code Reader} to wrap
	 * @return the uncloseable {@code Reader}
	 * @throws NullPointerException if the {@code Reader} is {@code null}
	 * @since 1.0.0
	 */
	public static Reader uncloseable(final Reader reader) {
		if (null == reader) {
			throw new NullPointerException("Invalid reader (not null expected)");
		}
		return new FilterReader(reader) {
			@Override
			public void close() {
				// Do nothing
			}
		};
	}

	/**
	 * <p>Read the {@code Reader} from the current position to the end and return the length.</p>
	 * <p><b>Note</b>: The {@code Reader} will not be closed.</p>
	 * <p><b>Warning</b>: Can produce an infinite loop if the {@code Reader} does not end.</p>
	 * @param reader the {@code Reader} to read
	 * @return the length from the current position
	 * @throws IOException might occurs with I/O operations
	 * @throws NullPointerException if the {@code Reader} is {@code null}
	 * @since 1.0.0
	 */
	public static long length(final Reader reader) throws IOException {
		if (null == reader) {
			throw new NullPointerException("Invalid reader (not null expected)");
		}
		return reader.transferTo(Writers.BLANK);
	}

	/**
	 * <p>Concatenate multiple {@code Reader}s.</p>
	 * @param readers {@code Reader}s to concatenate
	 * @return the concatenated {@code Reader}
	 * @throws NullPointerException whether the {@code Reader}s array or any of the {@code Reader}s is {@code null}
	 * @since 1.0.0
	 */
	public static Reader concat(final Reader... readers) {
		if (null == readers) {
			throw new NullPointerException("Invalid readers (not null expected)");
		}
		return concat(Arrays.asList(readers));
	}

	/**
	 * <p>Concatenate a list of {@code Reader}s.</p>
	 * @param readers {@code Reader}s to concatenate
	 * @return the concatenated {@code Reader}
	 * @throws NullPointerException whether the {@code Reader}s list or any of the {@code Reader}s is {@code null}
	 * @since 1.0.0
	 */
	public static Reader concat(final List<Reader> readers) {
		if (null == readers) {
			throw new NullPointerException("Invalid readers (not null expected)");
		}
		var i = 0;
		for (final var reader : readers) {
			if (null == reader) {
				throw new NullPointerException("Invalid reader at index " + i + " (not null expected)");
			}
			++i;
		}
		if (readers.isEmpty()) {
			return EMPTY;
		}
		if (1 == readers.size()) {
			return readers.get(0);
		}
		return new SequenceReader(readers);
	}

	/**
	 * <p>Join multiple {@code Reader}s using a {@code char array} separator.</p>
	 * @param separator the {@code char array} sequence to add between each joined {@code Reader}
	 * @param readers {@code Reader}s to join
	 * @return the joined {@code Reader}
	 * @throws NullPointerException whether the separator, the {@code Reader}s array or any of the {@code Reader}s is
	 * {@code null}
	 * @since 1.0.0
	 */
	public static Reader join(final char[] separator, final Reader... readers) {
		if (null == readers) {
			throw new NullPointerException("Invalid readers (not null expected)");
		}
		return join(separator, Arrays.asList(readers));
	}

	/**
	 * <p>Join a list of {@code Reader}s using a {@code char array} separator.</p>
	 * @param separator the {@code char array} sequence to add between each joined {@code Reader}
	 * @param readers {@code Reader}s to join
	 * @return the joined {@code Reader}
	 * @throws NullPointerException whether the separator, the {@code Reader}s list or any of the {@code Reader}s is
	 * {@code null}
	 * @since 1.0.0
	 */
	public static Reader join(final char[] separator, final List<Reader> readers) {
		if (null == separator) {
			throw new NullPointerException("Invalid separator (not null expected)");
		}
		if (null == readers) {
			throw new NullPointerException("Invalid readers (not null expected)");
		}
		var i = 0;
		for (final var reader : readers) {
			if (null == reader) {
				throw new NullPointerException("Invalid reader at index " + i + " (not null expected)");
			}
			++i;
		}
		if (0 == separator.length) {
			return concat(readers);
		}
		if (readers.isEmpty()) {
			return EMPTY;
		}
		if (1 == readers.size()) {
			return readers.get(0);
		}
		final var list = new ArrayList<Reader>();
		final var iterator = readers.iterator();
		list.add(iterator.next());
		while (iterator.hasNext()) {
			list.add(of(separator));
			list.add(iterator.next());
		}
		return new SequenceReader(list);
	}

	/**
	 * <p>Create a single-{@code char} {@code Reader} using the given {@code char}.</p>
	 * @param c the {@code char}
	 * @return the created single-{@code char} {@code Reader}
	 * @since 1.1.0
	 */
	public static Reader singleton(final char c) {
		return of(c);
	}

	/**
	 * <p>Create a {@code Reader} with a {@code char array}.</p>
	 * @param chars the {@code char array} to convert
	 * @return the created {@code Reader}
	 * @throws NullPointerException if the {@code char array} is {@code null}
	 * @since 1.0.0
	 */
	public static Reader of(final char... chars) {
		if (null == chars) {
			throw new NullPointerException("Invalid chars (not null expected)");
		}
		if (0 == chars.length) {
			return EMPTY;
		}
		return new CharArrayReader(chars);
	}

	/**
	 * <p>Convert a {@code Reader} to a {@code char array}.</p>
	 * <p><b>Note</b>: The {@code Reader} will not be closed.</p>
	 * <p><b>Warning</b>: Can produce a memory overflow if the {@code Reader} is too large.</p>
	 * @param reader the {@code Reader} to convert
	 * @return the created {@code char array}
	 * @throws IOException might occurs with I/O operations
	 * @throws NullPointerException if the {@code Reader} is {@code null}
	 * @since 1.0.0
	 */
	public static char[] toChars(final Reader reader) throws IOException {
		if (null == reader) {
			throw new NullPointerException("Invalid reader (not null expected)");
		}
		try (final var writer = new CharArrayWriter()) {
			reader.transferTo(writer);
			return writer.toCharArray();
		}
	}

	/**
	 * <p>Create a {@code Reader} with a {@code String}.</p>
	 * @param string the {@code String} to convert
	 * @return the created {@code Reader}
	 * @throws NullPointerException if the {@code String} is {@code null}
	 * @since 1.0.0
	 */
	public static Reader of(final String string) {
		if (null == string) {
			throw new NullPointerException("Invalid string (not null expected)");
		}
		return new StringReader(string);
	}

	/**
	 * <p>Convert a {@code Reader} to a {@code String}.</p>
	 * <p><b>Note</b>: The {@code Reader} will not be closed.</p>
	 * <p><b>Warning</b>: Can produce a memory overflow if the {@code Reader} is too large.</p>
	 * @param reader the {@code Reader} to convert
	 * @return the created {@code String}
	 * @throws IOException might occurs with I/O operations
	 * @throws NullPointerException if the {@code Reader} is {@code null}
	 * @since 1.0.0
	 */
	public static String toString(final Reader reader) throws IOException {
		if (null == reader) {
			throw new NullPointerException("Invalid reader (not null expected)");
		}
		try (final var writer = new StringWriter()) {
			reader.transferTo(writer);
			return writer.toString();
		}
	}
}