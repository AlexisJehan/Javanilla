/*
 * MIT License
 *
 * Copyright (c) 2018-2025 Alexis Jehan
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

import com.github.alexisjehan.javanilla.misc.quality.Ensure;

import java.io.BufferedOutputStream;
import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;
import java.util.Set;

/**
 * A utility class that provides {@link OutputStream} tools.
 * @deprecated since 1.8.0, use {@link com.github.alexisjehan.javanilla.io.OutputStreams} instead
 * @since 1.0.0
 */
@Deprecated(since = "1.8.0")
public final class OutputStreams {

	/**
	 * An empty {@link OutputStream} that writes nothing.
	 * @since 1.0.0
	 */
	public static final OutputStream EMPTY = new OutputStream() {

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void write(final int i) {
			// Empty
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void write(final byte[] bytes) {
			Ensure.notNull("bytes", bytes);
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void write(final byte[] bytes, final int offset, final int length) {
			Ensure.notNull("bytes", bytes);
			Ensure.between("offset", offset, 0, bytes.length);
			Ensure.between("length", length, 0, bytes.length - offset);
		}
	};

	/**
	 * Constructor.
	 * @since 1.0.0
	 */
	private OutputStreams() {}

	/**
	 * Wrap an {@link OutputStream} replacing {@code null} by an empty one.
	 * @param outputStream the {@link OutputStream} or {@code null}
	 * @return a non-{@code null} {@link OutputStream}
	 * @since 1.0.0
	 */
	public static OutputStream nullToEmpty(final OutputStream outputStream) {
		return nullToDefault(outputStream, EMPTY);
	}

	/**
	 * Wrap an {@link OutputStream} replacing {@code null} by a default one.
	 * @param outputStream the {@link OutputStream} or {@code null}
	 * @param defaultOutputStream the default {@link OutputStream}
	 * @param <O> the {@link OutputStream} type
	 * @return a non-{@code null} {@link OutputStream}
	 * @throws NullPointerException if the default {@link OutputStream} is {@code null}
	 * @since 1.1.0
	 */
	public static <O extends OutputStream> O nullToDefault(final O outputStream, final O defaultOutputStream) {
		Ensure.notNull("defaultOutputStream", defaultOutputStream);
		return null != outputStream ? outputStream : defaultOutputStream;
	}

	/**
	 * Decorate an {@link OutputStream} as a {@link BufferedOutputStream} if it was not already.
	 * @param outputStream the {@link OutputStream} to decorate
	 * @return a {@link BufferedOutputStream}
	 * @throws NullPointerException if the {@link OutputStream} is {@code null}
	 * @since 1.0.0
	 */
	public static BufferedOutputStream buffered(final OutputStream outputStream) {
		Ensure.notNull("outputStream", outputStream);
		if (outputStream instanceof BufferedOutputStream) {
			return (BufferedOutputStream) outputStream;
		}
		return new BufferedOutputStream(outputStream);
	}

	/**
	 * Decorate an {@link OutputStream} so that its {@link OutputStream#close()} method has no effect.
	 * @param outputStream the {@link OutputStream} to decorate
	 * @return an {@link OutputStream} that cannot be closed
	 * @throws NullPointerException if the {@link OutputStream} is {@code null}
	 * @since 1.0.0
	 */
	public static OutputStream uncloseable(final OutputStream outputStream) {
		Ensure.notNull("outputStream", outputStream);
		return new FilterOutputStream(outputStream) {

			/**
			 * {@inheritDoc}
			 */
			@Override
			public void close() {
				// Empty
			}
		};
	}

	/**
	 * Wrap multiple {@link OutputStream}s into a single one.
	 * @param outputStreams the {@link OutputStream} array to wrap
	 * @return the "tee-ed" {@link OutputStream}
	 * @throws NullPointerException if the {@link OutputStream} array or any of them is {@code null}
	 * @since 1.0.0
	 */
	public static OutputStream tee(final OutputStream... outputStreams) {
		Ensure.notNullAndNotNullElements("outputStreams", outputStreams);
		return tee(Set.of(outputStreams));
	}

	/**
	 * Wrap multiple {@link OutputStream}s into a single one.
	 * @param outputStreams the {@link OutputStream} {@link Collection} to wrap
	 * @return the "tee-ed" {@link OutputStream}
	 * @throws NullPointerException if the {@link OutputStream} {@link Collection} or any of them is {@code null}
	 * @since 1.0.0
	 */
	public static OutputStream tee(final Collection<? extends OutputStream> outputStreams) {
		Ensure.notNullAndNotNullElements("outputStreams", outputStreams);
		final var size = outputStreams.size();
		if (0 == size) {
			return EMPTY;
		}
		if (1 == size) {
			return outputStreams.iterator().next();
		}
		return new OutputStream() {

			/**
			 * {@inheritDoc}
			 */
			@Override
			public void write(final int i) throws IOException {
				for (final var outputStream : outputStreams) {
					outputStream.write(i);
				}
			}

			/**
			 * {@inheritDoc}
			 */
			@Override
			public void write(final byte[] bytes) throws IOException {
				Ensure.notNull("bytes", bytes);
				if (0 < bytes.length) {
					for (final var outputStream : outputStreams) {
						outputStream.write(bytes);
					}
				}
			}

			/**
			 * {@inheritDoc}
			 */
			@Override
			public void write(final byte[] bytes, final int offset, final int length) throws IOException {
				Ensure.notNull("bytes", bytes);
				Ensure.between("offset", offset, 0, bytes.length);
				Ensure.between("length", length, 0, bytes.length - offset);
				if (0 < length) {
					for (final var outputStream : outputStreams) {
						outputStream.write(bytes, offset, length);
					}
				}
			}

			/**
			 * {@inheritDoc}
			 */
			@Override
			public void flush() throws IOException {
				for (final var outputStream : outputStreams) {
					outputStream.flush();
				}
			}

			/**
			 * {@inheritDoc}
			 */
			@Override
			public void close() throws IOException {
				for (final var outputStream : outputStreams) {
					outputStream.close();
				}
			}
		};
	}

	/**
	 * Create a {@link BufferedOutputStream} from a {@link Path}.
	 *
	 * <p><b>Warning</b>: If the file of the {@link Path} already exists its content is erased.</p>
	 * @param path the {@link Path} to convert
	 * @return the created {@link BufferedOutputStream}
	 * @throws IOException might occur with I/O operations
	 * @throws NullPointerException if the {@link Path} is {@code null}
	 * @since 1.2.0
	 */
	public static BufferedOutputStream of(final Path path) throws IOException {
		Ensure.notNull("path", path);
		return new BufferedOutputStream(Files.newOutputStream(path));
	}

	/**
	 * Convert an {@link OutputStream} to a {@link Writer} using {@link Charset#defaultCharset()}.
	 * @param outputStream the {@link OutputStream} to convert
	 * @return the created {@link Writer}
	 * @throws NullPointerException if the {@link OutputStream} is {@code null}
	 * @since 1.0.0
	 */
	public static Writer toWriter(final OutputStream outputStream) {
		return toWriter(outputStream, Charset.defaultCharset());
	}

	/**
	 * Convert an {@link OutputStream} to a {@link Writer} using a custom {@link Charset}.
	 * @param outputStream the {@link OutputStream} to convert
	 * @param charset the {@link Charset} to use
	 * @return the created {@link Writer}
	 * @throws NullPointerException if the {@link OutputStream} or the {@link Charset} is {@code null}
	 * @since 1.0.0
	 */
	public static Writer toWriter(final OutputStream outputStream, final Charset charset) {
		Ensure.notNull("outputStream", outputStream);
		Ensure.notNull("charset", charset);
		return new OutputStreamWriter(buffered(outputStream), charset);
	}
}