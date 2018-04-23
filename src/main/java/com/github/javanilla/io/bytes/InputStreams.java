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
package com.github.javanilla.io.bytes;

import com.github.javanilla.io.chars.Readers;

import java.io.*;
import java.nio.charset.Charset;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

/**
 * <p>An utility class that provides {@link InputStream} tools.</p>
 * @since 1.0
 */
public final class InputStreams {

	/**
	 * <p>An empty {@code InputStream} that returns no byte.</p>
	 * @since 1.0
	 */
	public static final InputStream EMPTY = new InputStream() {
		@Override
		public int read() {
			return -1;
		}
	};

	/**
	 * <p>An endless {@code InputStream} that returns random bytes.</p>
	 * @since 1.0
	 */
	public static final InputStream ENDLESS = new InputStream() {
		@Override
		public int read() {
			return ThreadLocalRandom.current().nextInt(0, 256); // Range between 0 and 255 included
		}
	};

	/**
	 * <p>Constructor not available.</p>
	 * @since 1.0
	 */
	private InputStreams() {
		// Not available
	}

	/**
	 * <p>Wrap an {@code InputStream} replacing {@code null} by an empty {@code InputStream}.</p>
	 * @param inputStream an {@code InputStream} or {@code null}
	 * @return a non-{@code null} {@code InputStream}
	 * @since 1.0
	 */
	public static InputStream nullToEmpty(final InputStream inputStream) {
		return null != inputStream ? inputStream : EMPTY;
	}

	/**
	 * <p>Wrap an {@code InputStream} as a {@code BufferedInputStream} if it was not already.</p>
	 * @param inputStream the {@code InputStream} to wrap
	 * @return the buffered {@code InputStream}
	 * @throws NullPointerException if the {@code InputStream} is {@code null}
	 * @since 1.0
	 */
	public static BufferedInputStream buffered(final InputStream inputStream) {
		if (null == inputStream) {
			throw new NullPointerException("Invalid input stream (not null expected)");
		}
		if (inputStream instanceof BufferedInputStream) {
			return (BufferedInputStream) inputStream;
		}
		return new BufferedInputStream(inputStream);
	}

	/**
	 * <p>Wrap an {@code InputStream} with {@link InputStream#mark(int)} supported if it was not already.</p>
	 * @param inputStream the {@code InputStream} to wrap
	 * @return {@code InputStream} with mark supported
	 * @throws NullPointerException if the {@code InputStream} is {@code null}
	 * @since 1.0
	 */
	public static InputStream markSupported(final InputStream inputStream) {
		if (null == inputStream) {
			throw new NullPointerException("Invalid input stream (not null expected)");
		}
		if (inputStream.markSupported()) {
			return inputStream;
		}
		return new BufferedInputStream(inputStream);
	}

	/**
	 * <p>Wrap an {@code InputStream} whose {@link InputStream#close()} method has no effect.</p>
	 * @param inputStream the {@code InputStream} to wrap
	 * @return the uncloseable {@code InputStream}
	 * @throws NullPointerException if the {@code InputStream} is {@code null}
	 * @since 1.0
	 */
	public static InputStream uncloseable(final InputStream inputStream) {
		if (null == inputStream) {
			throw new NullPointerException("Invalid input stream (not null expected)");
		}
		return new FilterInputStream(inputStream) {
			@Override
			public void close() {
				// Do nothing
			}
		};
	}

	/**
	 * <p>Read an {@code InputStream} from the current position to the end and return the length.</p>
	 * <p><b>Note</b>: The {@code InputStream} will not be closed.</p>
	 * <p><b>Warning</b>: Could result in an infinite loop if the {@code InputStream} does not end.</p>
	 * @param inputStream the {@code InputStream} to read
	 * @return the length
	 * @throws IOException might occurs with I/O operations
	 * @throws NullPointerException if the {@code InputStream} is {@code null}
	 * @since 1.0
	 */
	public static long length(final InputStream inputStream) throws IOException {
		if (null == inputStream) {
			throw new NullPointerException("Invalid input stream (not null expected)");
		}
		return inputStream.transferTo(OutputStreams.BLANK);
	}

	/**
	 * <p>Concatenate multiple {@code InputStream}s.</p>
	 * @param inputStreams {@code InputStream}s to concatenate
	 * @return the concatenated {@code InputStream}
	 * @throws NullPointerException if the {@code InputStream}s array or any of the {@code InputStream}s is {@code null}
	 * @since 1.0
	 */
	public static InputStream concat(final InputStream... inputStreams) {
		if (null == inputStreams) {
			throw new NullPointerException("Invalid input streams (not null expected)");
		}
		return concat(Arrays.asList(inputStreams));
	}

	/**
	 * <p>Concatenate a list of {@code InputStream}s.</p>
	 * @param inputStreams {@code InputStream}s to concatenate
	 * @return the concatenated {@code InputStream}
	 * @throws NullPointerException if the {@code InputStream}s list or any of the {@code InputStream}s is {@code null}
	 * @since 1.0
	 */
	public static InputStream concat(final List<InputStream> inputStreams) {
		if (null == inputStreams) {
			throw new NullPointerException("Invalid input streams (not null expected)");
		}
		for (final var inputStream : inputStreams) {
			if (null == inputStream) {
				throw new NullPointerException("Invalid input stream (not null expected)");
			}
		}
		if (inputStreams.isEmpty()) {
			return EMPTY;
		}
		if (1 == inputStreams.size()) {
			return inputStreams.get(0);
		}
		return new SequenceInputStream(Collections.enumeration(inputStreams));
	}

	/**
	 * <p>Join multiple {@code InputStream}s using a {@code byte array} separator.</p>
	 * @param separator the {@code byte array} sequence to add between each joined {@code InputStream}
	 * @param inputStreams {@code InputStream}s to join
	 * @return the joined {@code InputStream}
	 * @throws NullPointerException if the separator, the {@code InputStream}s array or any of the {@code InputStream}s
	 * is {@code null}
	 * @since 1.0
	 */
	public static InputStream join(final byte[] separator, final InputStream... inputStreams) {
		if (null == inputStreams) {
			throw new NullPointerException("Invalid input streams (not null expected)");
		}
		return join(separator, Arrays.asList(inputStreams));
	}

	/**
	 * <p>Join a list of {@code InputStream}s using a {@code byte array} separator.</p>
	 * @param separator the {@code byte array} sequence to add between each joined {@code InputStream}
	 * @param inputStreams {@code InputStream}s to join
	 * @return the joined {@code InputStream}
	 * @throws NullPointerException if the separator, the {@code InputStream}s list or any of the {@code InputStream}s
	 * is {@code null}
	 * @since 1.0
	 */
	public static InputStream join(final byte[] separator, final List<InputStream> inputStreams) {
		if (null == separator) {
			throw new NullPointerException("Invalid separator (not null expected)");
		}
		if (null == inputStreams) {
			throw new NullPointerException("Invalid input streams (not null expected)");
		}
		for (final var inputStream : inputStreams) {
			if (null == inputStream) {
				throw new NullPointerException("Invalid input stream (not null expected)");
			}
		}
		if (0 == separator.length) {
			return concat(inputStreams);
		}
		if (inputStreams.isEmpty()) {
			return EMPTY;
		}
		if (1 == inputStreams.size()) {
			return inputStreams.get(0);
		}
		final var list = new ArrayList<InputStream>();
		final var iterator = inputStreams.iterator();
		list.add(iterator.next());
		while (iterator.hasNext()) {
			list.add(of(separator));
			list.add(iterator.next());
		}
		return new SequenceInputStream(Collections.enumeration(list));
	}

	/**
	 * <p>Create an {@code InputStream} from a {@code byte array}.</p>
	 * @param bytes the {@code byte array} to convert
	 * @return the created {@code InputStream}
	 * @throws NullPointerException if the {@code byte array} is {@code null}
	 * @since 1.0
	 */
	public static InputStream of(final byte... bytes) {
		if (null == bytes) {
			throw new NullPointerException("Invalid bytes (not null expected)");
		}
		if (0 == bytes.length) {
			return EMPTY;
		}
		return new ByteArrayInputStream(bytes);
	}

	/**
	 * <p>Convert an {@code InputStream} to a {@code byte array}.</p>
	 * <p><b>Note</b>: The {@code InputStream} will not be closed.</p>
	 * @param inputStream the {@code InputStream} to convert
	 * @return the created {@code byte array}
	 * @throws IOException might occurs with I/O operations
	 * @throws NullPointerException if the {@code InputStream} is {@code null}
	 * @since 1.0
	 */
	public static byte[] toBytes(final InputStream inputStream) throws IOException {
		if (null == inputStream) {
			throw new NullPointerException("Invalid input stream (not null expected)");
		}
		return inputStream.readAllBytes();
	}

	/**
	 * <p>Create an {@code InputStream} with a {@code String} using {@link Charset#defaultCharset()}.</p>
	 * @param string the {@code String} to convert
	 * @return the created {@code InputStream}
	 * @throws NullPointerException if the {@code String} is {@code null}
	 * @since 1.0
	 */
	public static InputStream of(final String string) {
		return of(string, Charset.defaultCharset());
	}

	/**
	 * <p>Create an {@code InputStream} with a {@code String} using a custom {@code Charset}.</p>
	 * @param string the {@code String} to convert
	 * @param charset the {@code Charset} to use
	 * @return the created {@code InputStream}
	 * @throws NullPointerException if the {@code String} or the {@code Charset} are {@code null}
	 * @since 1.0
	 */
	public static InputStream of(final String string, final Charset charset) {
		if (null == string) {
			throw new NullPointerException("Invalid string (not null expected)");
		}
		if (null == charset) {
			throw new NullPointerException("Invalid charset (not null expected)");
		}
		return of(string.getBytes(charset));
	}

	/**
	 * <p>Convert an {@code InputStream} to a {@code String} using {@link Charset#defaultCharset()}.</p>
	 * <p><b>Note</b>: The {@code InputStream} will not be closed.</p>
	 * @param inputStream the {@code InputStream} to convert
	 * @return the created {@code String}
	 * @throws IOException might occurs with I/O operations
	 * @throws NullPointerException if the {@code InputStream} is {@code null}
	 * @since 1.0
	 */
	public static String toString(final InputStream inputStream) throws IOException {
		return toString(inputStream, Charset.defaultCharset());
	}

	/**
	 * <p>Convert an {@code InputStream} to a {@code String} using a custom {@code Charset}.</p>
	 * <p><b>Note</b>: The {@code InputStream} will not be closed.</p>
	 * @param inputStream the {@code InputStream} to convert
	 * @param charset the {@code Charset} to use
	 * @return the created {@code String}
	 * @throws IOException might occurs with I/O operations
	 * @throws NullPointerException if the {@code InputStream} or the {@code Charset} are {@code null}
	 * @since 1.0
	 */
	public static String toString(final InputStream inputStream, final Charset charset) throws IOException {
		if (null == inputStream) {
			throw new NullPointerException("Invalid input stream (not null expected)");
		}
		if (null == charset) {
			throw new NullPointerException("Invalid charset (not null expected)");
		}
		return Readers.toString(toReader(inputStream, charset));
	}

	/**
	 * <p>Convert an {@code InputStream} to a {@code Reader} using {@link Charset#defaultCharset()}.</p>
	 * @param inputStream the {@code InputStream} to convert
	 * @return the created {@code Reader}
	 * @throws NullPointerException if the {@code InputStream} is {@code null}
	 * @since 1.0
	 */
	public static Reader toReader(final InputStream inputStream) {
		return toReader(inputStream, Charset.defaultCharset());
	}

	/**
	 * <p>Convert an {@code InputStream} to a {@code Reader} using a custom {@code Charset}.</p>
	 * @param inputStream the {@code InputStream} to convert
	 * @param charset the {@code Charset} to use
	 * @return the created {@code Reader}
	 * @throws NullPointerException if the {@code InputStream} or the {@code Charset} are {@code null}
	 * @since 1.0
	 */
	public static Reader toReader(final InputStream inputStream, final Charset charset) {
		if (null == inputStream) {
			throw new NullPointerException("Invalid input stream (not null expected)");
		}
		if (null == charset) {
			throw new NullPointerException("Invalid charset (not null expected)");
		}
		return new InputStreamReader(buffered(inputStream), charset);
	}
}