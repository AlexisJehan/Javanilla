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
package com.github.alexisjehan.javanilla.io.bytes;

import com.github.alexisjehan.javanilla.lang.array.ByteArrays;
import com.github.alexisjehan.javanilla.misc.quality.Ensure;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.io.SequenceInputStream;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * <p>An utility class that provides {@link InputStream} tools.</p>
 * @deprecated since 1.8.0, use {@link com.github.alexisjehan.javanilla.io.InputStreams} instead
 * @since 1.0.0
 */
@Deprecated(since = "1.8.0")
public final class InputStreams {

	/**
	 * <p>An empty {@link InputStream} that returns no byte.</p>
	 * @deprecated since 1.4.0, on Java 11, use {@code InputStream#nullInputStream()} instead
	 * @since 1.0.0
	 */
	@Deprecated(since = "1.4.0")
	public static final InputStream EMPTY = new InputStream() {

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
		public int read(final byte[] buffer) {
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
		public int read(final byte[] buffer, final int offset, final int length) {
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
		public byte[] readAllBytes() {
			return ByteArrays.EMPTY;
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public int readNBytes(final byte[] buffer, final int offset, final int length) {
			Ensure.notNull("buffer", buffer);
			Ensure.between("offset", offset, 0, buffer.length);
			Ensure.between("length", length, 0, buffer.length - offset);
			return 0;
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
		public long transferTo(final OutputStream outputStream) {
			Ensure.notNull("outputStream", outputStream);
			return 0L;
		}
	};

	/**
	 * <p>Constructor not available.</p>
	 * @since 1.0.0
	 */
	private InputStreams() {
		// Not available
	}

	/**
	 * <p>Wrap an {@link InputStream} replacing {@code null} by an empty one.</p>
	 * @param inputStream the {@link InputStream} or {@code null}
	 * @return a non-{@code null} {@link InputStream}
	 * @since 1.0.0
	 */
	public static InputStream nullToEmpty(final InputStream inputStream) {
		return nullToDefault(inputStream, EMPTY);
	}

	/**
	 * <p>Wrap an {@link InputStream} replacing {@code null} by a default one.</p>
	 * @param inputStream the {@link InputStream} or {@code null}
	 * @param defaultInputStream the default {@link InputStream}
	 * @param <I> the {@link InputStream} type
	 * @return a non-{@code null} {@link InputStream}
	 * @throws NullPointerException if the default {@link InputStream} is {@code null}
	 * @since 1.1.0
	 */
	public static <I extends InputStream> I nullToDefault(final I inputStream, final I defaultInputStream) {
		Ensure.notNull("defaultInputStream", defaultInputStream);
		return null != inputStream ? inputStream : defaultInputStream;
	}

	/**
	 * <p>Decorate an {@link InputStream} as a {@link BufferedInputStream} if it was not already.</p>
	 * @param inputStream the {@link InputStream} to decorate
	 * @return a {@link BufferedInputStream}
	 * @throws NullPointerException if the {@link InputStream} is {@code null}
	 * @since 1.0.0
	 */
	public static BufferedInputStream buffered(final InputStream inputStream) {
		Ensure.notNull("inputStream", inputStream);
		if (inputStream instanceof BufferedInputStream) {
			return (BufferedInputStream) inputStream;
		}
		return new BufferedInputStream(inputStream);
	}

	/**
	 * <p>Decorate an {@link InputStream} so that it supports {@link InputStream#mark(int)} if it did not already.</p>
	 * @param inputStream the {@link InputStream} to decorate
	 * @return an {@link InputStream} with mark supported
	 * @throws NullPointerException if the {@link InputStream} is {@code null}
	 * @since 1.0.0
	 */
	public static InputStream markSupported(final InputStream inputStream) {
		Ensure.notNull("inputStream", inputStream);
		if (inputStream.markSupported()) {
			return inputStream;
		}
		return new BufferedInputStream(inputStream);
	}

	/**
	 * <p>Decorate an {@link InputStream} so that its {@link InputStream#close()} method has no effect.</p>
	 * @param inputStream the {@link InputStream} to decorate
	 * @return an {@link InputStream} that cannot be closed
	 * @throws NullPointerException if the {@link InputStream} is {@code null}
	 * @since 1.0.0
	 */
	public static InputStream uncloseable(final InputStream inputStream) {
		Ensure.notNull("inputStream", inputStream);
		return new FilterInputStream(inputStream) {

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
	 * <p>Concatenate multiple {@link InputStream}s.</p>
	 * @param inputStreams the {@link InputStream} array to concatenate
	 * @return the concatenated {@link InputStream}
	 * @throws NullPointerException if the {@link InputStream} array or any of them is {@code null}
	 * @since 1.0.0
	 */
	public static InputStream concat(final InputStream... inputStreams) {
		Ensure.notNullAndNotNullElements("inputStreams", inputStreams);
		return concat(List.of(inputStreams));
	}

	/**
	 * <p>Concatenate multiple {@link InputStream}s.</p>
	 * @param inputStreams the {@link InputStream} {@link List} to concatenate
	 * @return the concatenated {@link InputStream}
	 * @throws NullPointerException if the {@link InputStream} {@link List} or any of them is {@code null}
	 * @since 1.0.0
	 */
	public static InputStream concat(final List<? extends InputStream> inputStreams) {
		Ensure.notNullAndNotNullElements("inputStreams", inputStreams);
		final var size = inputStreams.size();
		if (0 == size) {
			return EMPTY;
		}
		if (1 == size) {
			return inputStreams.get(0);
		}
		return new SequenceInputStream(Collections.enumeration(inputStreams));
	}

	/**
	 * <p>Join multiple {@link InputStream}s using a {@code byte} array separator.</p>
	 * @param separator the {@code byte} array separator
	 * @param inputStreams the {@link InputStream} array to join
	 * @return the joined {@link InputStream}
	 * @throws NullPointerException if the {@code byte} array separator, the {@link InputStream} array or any of them is
	 *         {@code null}
	 * @since 1.0.0
	 */
	public static InputStream join(final byte[] separator, final InputStream... inputStreams) {
		Ensure.notNullAndNotNullElements("inputStreams", inputStreams);
		return join(separator, List.of(inputStreams));
	}

	/**
	 * <p>Join multiple {@link InputStream}s using a {@code byte} array separator.</p>
	 * @param separator the {@code byte} array separator
	 * @param inputStreams the {@link InputStream} {@link List} to join
	 * @return the joined {@link InputStream}
	 * @throws NullPointerException if the {@code byte} array separator, the {@link InputStream} {@link List} or any of
	 *         them is {@code null}
	 * @since 1.0.0
	 */
	public static InputStream join(final byte[] separator, final List<? extends InputStream> inputStreams) {
		Ensure.notNull("separator", separator);
		Ensure.notNullAndNotNullElements("inputStreams", inputStreams);
		if (0 == separator.length) {
			return concat(inputStreams);
		}
		final var size = inputStreams.size();
		if (0 == size) {
			return EMPTY;
		}
		if (1 == size) {
			return inputStreams.get(0);
		}
		final var list = new ArrayList<InputStream>(2 * size - 1);
		final var iterator = inputStreams.iterator();
		list.add(iterator.next());
		while (iterator.hasNext()) {
			list.add(of(separator));
			list.add(iterator.next());
		}
		return new SequenceInputStream(Collections.enumeration(list));
	}

	/**
	 * <p>Read an {@link InputStream} from the current position to the end and return the length.</p>
	 * <p><b>Note</b>: The {@link InputStream} will not be closed.</p>
	 * <p><b>Warning</b>: Can produce an infinite loop if the {@link InputStream} does not end.</p>
	 * @param inputStream the {@link InputStream} to read
	 * @return the length from the current position
	 * @throws IOException might occur with I/O operations
	 * @throws NullPointerException if the {@link InputStream} is {@code null}
	 * @since 1.0.0
	 */
	public static long length(final InputStream inputStream) throws IOException {
		Ensure.notNull("inputStream", inputStream);
		return inputStream.transferTo(OutputStreams.EMPTY);
	}

	/**
	 * <p>Create an {@link InputStream} from a single {@code byte}.</p>
	 * @param b the {@code byte} to convert
	 * @return the created {@link InputStream}
	 * @since 1.1.0
	 */
	public static InputStream singleton(final byte b) {
		return of(b);
	}

	/**
	 * <p>Create an {@link InputStream} from multiple {@code byte}s.</p>
	 * @param bytes the {@code byte} array to convert
	 * @return the created {@link InputStream}
	 * @throws NullPointerException if the {@code byte} array is {@code null}
	 * @since 1.0.0
	 */
	public static InputStream of(final byte... bytes) {
		Ensure.notNull("bytes", bytes);
		if (0 == bytes.length) {
			return EMPTY;
		}
		return new ByteArrayInputStream(bytes);
	}

	/**
	 * <p>Create an {@link InputStream} from a {@link String} using {@link Charset#defaultCharset()}.</p>
	 * @param string the {@link String} to convert
	 * @return the created {@link InputStream}
	 * @throws NullPointerException if the {@link String} is {@code null}
	 * @since 1.0.0
	 */
	public static InputStream of(final String string) {
		return of(string, Charset.defaultCharset());
	}

	/**
	 * <p>Create an {@link InputStream} from a {@link String} using a custom {@link Charset}.</p>
	 * @param string the {@link String} to convert
	 * @param charset the {@link Charset} to use
	 * @return the created {@link InputStream}
	 * @throws NullPointerException if the {@link String} or the {@link Charset} is {@code null}
	 * @since 1.0.0
	 */
	public static InputStream of(final String string, final Charset charset) {
		Ensure.notNull("string", string);
		Ensure.notNull("charset", charset);
		if (string.isEmpty()) {
			return EMPTY;
		}
		return of(string.getBytes(charset));
	}

	/**
	 * <p>Create a {@link BufferedInputStream} from a {@link Path}.</p>
	 * @param path the {@link Path} to convert
	 * @return the created {@link BufferedInputStream}
	 * @throws IOException might occur with I/O operations
	 * @throws NullPointerException if the {@link Path} is {@code null}
	 * @since 1.2.0
	 */
	public static BufferedInputStream of(final Path path) throws IOException {
		Ensure.notNull("path", path);
		return new BufferedInputStream(Files.newInputStream(path));
	}

	/**
	 * <p>Convert an {@link InputStream} to a {@code byte} array.</p>
	 * <p><b>Note</b>: The {@link InputStream} will not be closed.</p>
	 * <p><b>Warning</b>: Can produce a memory overflow if the {@link InputStream} is too large.</p>
	 * @param inputStream the {@link InputStream} to convert
	 * @return the created {@code byte} array
	 * @throws IOException might occur with I/O operations
	 * @throws NullPointerException if the {@link InputStream} is {@code null}
	 * @since 1.0.0
	 */
	public static byte[] toBytes(final InputStream inputStream) throws IOException {
		Ensure.notNull("inputStream", inputStream);
		return inputStream.readAllBytes();
	}

	/**
	 * <p>Convert an {@link InputStream} to a {@link String} using {@link Charset#defaultCharset()}.</p>
	 * <p><b>Note</b>: The {@link InputStream} will not be closed.</p>
	 * <p><b>Warning</b>: Can produce a memory overflow if the {@link InputStream} is too large.</p>
	 * @param inputStream the {@link InputStream} to convert
	 * @return the created {@link String}
	 * @throws IOException might occur with I/O operations
	 * @throws NullPointerException if the {@link InputStream} is {@code null}
	 * @since 1.0.0
	 */
	public static String toString(final InputStream inputStream) throws IOException {
		return toString(inputStream, Charset.defaultCharset());
	}

	/**
	 * <p>Convert an {@link InputStream} to a {@link String} using a custom {@link Charset}.</p>
	 * <p><b>Note</b>: The {@link InputStream} will not be closed.</p>
	 * <p><b>Warning</b>: Can produce a memory overflow if the {@link InputStream} is too large.</p>
	 * @param inputStream the {@link InputStream} to convert
	 * @param charset the {@link Charset} to use
	 * @return the created {@link String}
	 * @throws IOException might occur with I/O operations
	 * @throws NullPointerException if the {@link InputStream} or the {@link Charset} is {@code null}
	 * @since 1.0.0
	 */
	public static String toString(final InputStream inputStream, final Charset charset) throws IOException {
		Ensure.notNull("inputStream", inputStream);
		Ensure.notNull("charset", charset);
		return new String(toBytes(inputStream), charset);
	}

	/**
	 * <p>Convert an {@link InputStream} to a {@link Reader} using {@link Charset#defaultCharset()}.</p>
	 * @param inputStream the {@link InputStream} to convert
	 * @return the created {@link Reader}
	 * @throws NullPointerException if the {@link InputStream} is {@code null}
	 * @since 1.0.0
	 */
	public static Reader toReader(final InputStream inputStream) {
		return toReader(inputStream, Charset.defaultCharset());
	}

	/**
	 * <p>Convert an {@link InputStream} to a {@link Reader} using a custom {@link Charset}.</p>
	 * @param inputStream the {@link InputStream} to convert
	 * @param charset the {@link Charset} to use
	 * @return the created {@link Reader}
	 * @throws NullPointerException if the {@link InputStream} or the {@link Charset} is {@code null}
	 * @since 1.0.0
	 */
	public static Reader toReader(final InputStream inputStream, final Charset charset) {
		Ensure.notNull("inputStream", inputStream);
		Ensure.notNull("charset", charset);
		return new InputStreamReader(buffered(inputStream), charset);
	}
}