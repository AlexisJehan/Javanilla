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

import org.jetbrains.annotations.NotNull;

import java.io.BufferedOutputStream;
import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Collection;

/**
 * <p>An utility class that provides {@link OutputStream} tools.</p>
 * @since 1.0.0
 */
public final class OutputStreams {

	/**
	 * <p>A composite {@code OutputStream}.</p>
	 * @since 1.0.0
	 */
	private static class TeeOutputStream extends OutputStream {

		/**
		 * <p>Delegated {@code OutputStream}s.</p>
		 * @since 1.0.0
		 */
		private final Iterable<OutputStream> outputStreams;

		/**
		 * <p>Private constructor.</p>
		 * @param outputStreams delegated {@code OutputStream}s
		 * @since 1.0.0
		 */
		private TeeOutputStream(final Iterable<OutputStream> outputStreams) {
			this.outputStreams = outputStreams;
		}

		@Override
		public void write(final int b) throws IOException {
			for (final var outputStream : outputStreams) {
				outputStream.write(b);
			}
		}

		@Override
		public void write(@NotNull final byte[] b) throws IOException {
			for (final var outputStream : outputStreams) {
				outputStream.write(b);
			}
		}

		@Override
		public void write(@NotNull final byte[] b, final int off, final int len) throws IOException {
			for (final var outputStream : outputStreams) {
				outputStream.write(b, off, len);
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
	}

	/**
	 * <p>A blank {@code OutputStream} that writes nothing.</p>
	 * @since 1.0.0
	 */
	public static final OutputStream BLANK = new OutputStream() {
		@Override
		public void write(final int b) {
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
	 * <p>Wrap an {@code OutputStream} replacing {@code null} by a blank {@code OutputStream}.</p>
	 * @param outputStream an {@code OutputStream} or {@code null}
	 * @return the non-{@code null} {@code OutputStream}
	 * @since 1.0.0
	 */
	public static OutputStream nullToBlank(final OutputStream outputStream) {
		return nullToDefault(outputStream, BLANK);
	}

	/**
	 * <p>Wrap an {@code OutputStream} replacing {@code null} by a default {@code OutputStream}.</p>
	 * @param outputStream an {@code OutputStream} or {@code null}
	 * @param defaultOutputStream the default {@code OutputStream}
	 * @return the non-{@code null} {@code OutputStream}
	 * @throws NullPointerException if the default {@code OutputStream} is {@code null}
	 * @since 1.1.0
	 */
	public static OutputStream nullToDefault(final OutputStream outputStream, final OutputStream defaultOutputStream) {
		if (null == defaultOutputStream) {
			throw new NullPointerException("Invalid default output stream (not null expected)");
		}
		return null != outputStream ? outputStream : defaultOutputStream;
	}

	/**
	 * <p>Wrap an {@code OutputStream} as a {@code BufferedOutputStream} if it was not already.</p>
	 * @param outputStream the {@code OutputStream} to wrap
	 * @return the buffered {@code OutputStream}
	 * @throws NullPointerException if the {@code OutputStream} is {@code null}
	 * @since 1.0.0
	 */
	public static BufferedOutputStream buffered(final OutputStream outputStream) {
		if (null == outputStream) {
			throw new NullPointerException("Invalid output stream (not null expected)");
		}
		if (outputStream instanceof BufferedOutputStream) {
			return (BufferedOutputStream) outputStream;
		}
		return new BufferedOutputStream(outputStream);
	}

	/**
	 * <p>Wrap an {@code OutputStream} so that its {@link OutputStream#close()} method has no effect.</p>
	 * @param outputStream the {@code OutputStream} to wrap
	 * @return the uncloseable {@code OutputStream}
	 * @throws NullPointerException if the {@code OutputStream} is {@code null}
	 * @since 1.0.0
	 */
	public static OutputStream uncloseable(final OutputStream outputStream) {
		if (null == outputStream) {
			throw new NullPointerException("Invalid output stream (not null expected)");
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
	 * @param outputStreams {@code OutputStream}s to wrap
	 * @return the "tee-ed" {@code OutputStream}
	 * @throws NullPointerException whether the {@code OutputStream}s array or any of the {@code OutputStream}s is
	 * {@code null}
	 * @since 1.0.0
	 */
	public static OutputStream tee(final OutputStream... outputStreams) {
		if (null == outputStreams) {
			throw new NullPointerException("Invalid output streams (not null expected)");
		}
		return tee(Arrays.asList(outputStreams));
	}

	/**
	 * <p>Write a collection of {@code OutputStream}s into a single one.</p>
	 * @param outputStreams {@code OutputStream}s to wrap
	 * @return the "tee-ed" {@code OutputStream}
	 * @throws NullPointerException whether the {@code OutputStream}s collection or any of the {@code OutputStream}s is
	 * {@code null}
	 * @since 1.0.0
	 */
	public static OutputStream tee(final Collection<OutputStream> outputStreams) {
		if (null == outputStreams) {
			throw new NullPointerException("Invalid output streams (not null expected)");
		}
		var i = 0;
		for (final var outputStream : outputStreams) {
			if (null == outputStream) {
				throw new NullPointerException("Invalid output stream at index " + i + " (not null expected)");
			}
			++i;
		}
		if (outputStreams.isEmpty()) {
			return BLANK;
		}
		if (1 == outputStreams.size()) {
			return outputStreams.iterator().next();
		}
		return new TeeOutputStream(outputStreams);
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
	 * @throws NullPointerException whether the {@code OutputStream} or the {@code Charset} is {@code null}
	 * @since 1.0.0
	 */
	public static Writer toWriter(final OutputStream outputStream, final Charset charset) {
		if (null == outputStream) {
			throw new NullPointerException("Invalid output stream (not null expected)");
		}
		if (null == charset) {
			throw new NullPointerException("Invalid charset (not null expected)");
		}
		return new OutputStreamWriter(buffered(outputStream), charset);
	}
}