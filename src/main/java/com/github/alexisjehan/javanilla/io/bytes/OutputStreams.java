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
package com.github.alexisjehan.javanilla.io.bytes;

import com.github.alexisjehan.javanilla.util.iteration.Iterables;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Collection;

/**
 * <p>An utility class that provides {@link OutputStream} tools.</p>
 * @since 1.0.0
 */
public final class OutputStreams {

	/**
	 * <p>An empty {@code OutputStream} that writes nothing.</p>
	 * @since 1.0.0
	 */
	public static final OutputStream EMPTY = new OutputStream() {
		@Override
		public void write(final int b) {
			// Do nothing
		}

		@Override
		public void write(final byte[] bytes) {
			if (null == bytes) {
				throw new NullPointerException("Invalid bytes (not null expected)");
			}
			// Do nothing
		}

		@Override
		public void write(final byte[] bytes, final int offset, final int length) {
			if (null == bytes) {
				throw new NullPointerException("Invalid bytes (not null expected)");
			}
			if (0 > offset || bytes.length < offset) {
				throw new IndexOutOfBoundsException("Invalid offset: " + offset + " (between 0 and " + bytes.length + " expected)");
			}
			if (0 > length || bytes.length - offset < length) {
				throw new IndexOutOfBoundsException("Invalid length: " + length + " (between 0 and " + (bytes.length - offset) + " expected)");
			}
			// Do nothing
		}
	};

	/**
	 * <p>Constructor not available.</p>
	 * @since 1.0.0
	 */
	private OutputStreams() {
		// Not available
	}

	/**
	 * <p>Wrap an {@code OutputStream} replacing {@code null} by an empty one.</p>
	 * @param outputStream the {@code OutputStream} or {@code null}
	 * @return a non-{@code null} {@code OutputStream}
	 * @since 1.0.0
	 */
	public static OutputStream nullToEmpty(final OutputStream outputStream) {
		return nullToDefault(outputStream, EMPTY);
	}

	/**
	 * <p>Wrap an {@code OutputStream} replacing {@code null} by a default one.</p>
	 * @param outputStream the {@code OutputStream} or {@code null}
	 * @param defaultOutputStream the default {@code OutputStream}
	 * @param <O> the {@code OutputStream} type
	 * @return a non-{@code null} {@code OutputStream}
	 * @throws NullPointerException if the default {@code OutputStream} is {@code null}
	 * @since 1.1.0
	 */
	public static <O extends OutputStream> O nullToDefault(final O outputStream, final O defaultOutputStream) {
		if (null == defaultOutputStream) {
			throw new NullPointerException("Invalid default OutputStream (not null expected)");
		}
		return null != outputStream ? outputStream : defaultOutputStream;
	}

	/**
	 * <p>Decorate an {@code OutputStream} as a {@code BufferedOutputStream} if it was not already.</p>
	 * @param outputStream the {@code OutputStream} to decorate
	 * @return a {@code BufferedOutputStream}
	 * @throws NullPointerException if the {@code OutputStream} is {@code null}
	 * @since 1.0.0
	 */
	public static BufferedOutputStream buffered(final OutputStream outputStream) {
		if (null == outputStream) {
			throw new NullPointerException("Invalid OutputStream (not null expected)");
		}
		if (outputStream instanceof BufferedOutputStream) {
			return (BufferedOutputStream) outputStream;
		}
		return new BufferedOutputStream(outputStream);
	}

	/**
	 * <p>Decorate an {@code OutputStream} so that its {@link OutputStream#close()} method has no effect.</p>
	 * @param outputStream the {@code OutputStream} to decorate
	 * @return an uncloseable {@code OutputStream}
	 * @throws NullPointerException if the {@code OutputStream} is {@code null}
	 * @since 1.0.0
	 */
	public static OutputStream uncloseable(final OutputStream outputStream) {
		if (null == outputStream) {
			throw new NullPointerException("Invalid OutputStream (not null expected)");
		}
		return new FilterOutputStream(outputStream) {
			@Override
			public void close() {
				// Do nothing
			}
		};
	}

	/**
	 * <p>Wrap multiple {@code OutputStream}s into a single one.</p>
	 * @param outputStreams the {@code OutputStream} array to wrap
	 * @return the "tee-ed" {@code OutputStream}
	 * @throws NullPointerException if the {@code OutputStream} array or any of them is {@code null}
	 * @since 1.0.0
	 */
	public static OutputStream tee(final OutputStream... outputStreams) {
		if (null == outputStreams) {
			throw new NullPointerException("Invalid OutputStreams (not null expected)");
		}
		return tee(Arrays.asList(outputStreams));
	}

	/**
	 * <p>Wrap multiple {@code OutputStream}s into a single one.</p>
	 * @param outputStreams the {@code OutputStream} {@code Collection} to wrap
	 * @return the "tee-ed" {@code OutputStream}
	 * @throws NullPointerException if the {@code OutputStream} {@code Collection} or any of them is {@code null}
	 * @since 1.0.0
	 */
	public static OutputStream tee(final Collection<OutputStream> outputStreams) {
		if (null == outputStreams) {
			throw new NullPointerException("Invalid OutputStreams (not null expected)");
		}
		for (final var indexedOutputStream : Iterables.index(outputStreams)) {
			if (null == indexedOutputStream.getElement()) {
				throw new NullPointerException("Invalid OutputStream at index " + indexedOutputStream.getIndex() + " (not null expected)");
			}
		}
		final var size = outputStreams.size();
		if (0 == size) {
			return EMPTY;
		}
		if (1 == size) {
			return outputStreams.iterator().next();
		}
		return new OutputStream() {
			@Override
			public void write(final int b) throws IOException {
				for (final var outputStream : outputStreams) {
					outputStream.write(b);
				}
			}

			@Override
			public void write(final byte[] bytes) throws IOException {
				if (null == bytes) {
					throw new NullPointerException("Invalid bytes (not null expected)");
				}
				for (final var outputStream : outputStreams) {
					outputStream.write(bytes);
				}
			}

			@Override
			public void write(final byte[] bytes, final int offset, final int length) throws IOException {
				if (null == bytes) {
					throw new NullPointerException("Invalid bytes (not null expected)");
				}
				if (0 > offset || bytes.length < offset) {
					throw new IndexOutOfBoundsException("Invalid offset: " + offset + " (between 0 and " + bytes.length + " expected)");
				}
				if (0 > length || bytes.length - offset < length) {
					throw new IndexOutOfBoundsException("Invalid length: " + length + " (between 0 and " + (bytes.length - offset) + " expected)");
				}
				for (final var outputStream : outputStreams) {
					outputStream.write(bytes, offset, length);
				}
			}

			@Override
			public void flush() throws IOException {
				for (final var outputStream : outputStreams) {
					outputStream.flush();
				}
			}

			@Override
			public void close() throws IOException {
				for (final var outputStream : outputStreams) {
					outputStream.close();
				}
			}
		};
	}

	/**
	 * <p>Convert an {@code OutputStream} to a {@code Writer} using {@link Charset#defaultCharset()}.</p>
	 * @param outputStream the {@code OutputStream} to convert
	 * @return the created {@code Writer}
	 * @throws NullPointerException if the {@code OutputStream} is {@code null}
	 * @since 1.0.0
	 */
	public static Writer toWriter(final OutputStream outputStream) {
		return toWriter(outputStream, Charset.defaultCharset());
	}

	/**
	 * <p>Convert an {@code OutputStream} to a {@code Writer} using a custom {@code Charset}.</p>
	 * @param outputStream the {@code OutputStream} to convert
	 * @param charset the {@code Charset} to use
	 * @return the created {@code Writer}
	 * @throws NullPointerException if the {@code OutputStream} or the {@code Charset} is {@code null}
	 * @since 1.0.0
	 */
	public static Writer toWriter(final OutputStream outputStream, final Charset charset) {
		if (null == outputStream) {
			throw new NullPointerException("Invalid OutputStream (not null expected)");
		}
		if (null == charset) {
			throw new NullPointerException("Invalid Charset (not null expected)");
		}
		return new OutputStreamWriter(buffered(outputStream), charset);
	}

	/**
	 * <p>Create a {@code BufferedOutputStream} from a {@code Path}.</p>
	 * <p><b>Warning</b>: If the file of the {@code Path} already exists its content is erased.</p>
	 * @param path the {@code Path} to convert
	 * @return the created {@code BufferedOutputStream}
	 * @throws FileNotFoundException if the file of the {@code Path} is not writable
	 * @throws NullPointerException if the {@code Path} is {@code null}
	 * @since 1.2.0
	 */
	public static BufferedOutputStream of(final Path path) throws FileNotFoundException {
		if (null == path) {
			throw new NullPointerException("Invalid Path (not null expected)");
		}
		return new BufferedOutputStream(new FileOutputStream(path.toFile()));
	}
}