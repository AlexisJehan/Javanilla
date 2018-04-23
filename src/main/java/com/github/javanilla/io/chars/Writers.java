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
package com.github.javanilla.io.chars;

import org.jetbrains.annotations.NotNull;

import java.io.BufferedWriter;
import java.io.FilterWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Arrays;
import java.util.Collection;

/**
 * <p>An utility class that provides {@link Writer} tools.</p>
 * @since 1.0
 */
public final class Writers {

	/**
	 * <p>A composed {@code Writer}.</p>
	 * @since 1.0
	 */
	private static class TeeWriter extends Writer {

		/**
		 * <p>Collection of delegated {@code Writer}s.</p>
		 * @since 1.0
		 */
		private Collection<Writer> writers;

		/**
		 * <p>Private constructor.</p>
		 * @param writers delegated {@code Writer}s
		 * @since 1.0
		 */
		private TeeWriter(final Collection<Writer> writers) {
			this.writers = writers;
		}

		@Override
		public void write(final int c) throws IOException {
			for (final var writer : writers) {
				writer.write(c);
			}
		}

		@Override
		public void write(@NotNull final char[] cbuf) throws IOException {
			for (final var writer : writers) {
				writer.write(cbuf);
			}
		}

		@Override
		public void write(@NotNull final char[] cbuf, final int off, final int len) throws IOException {
			for (final var writer : writers) {
				writer.write(cbuf, off, len);
			}
		}

		@Override
		public void write(@NotNull final String str) throws IOException {
			for (final var writer : writers) {
				writer.write(str);
			}
		}

		@Override
		public void write(@NotNull final String str, final int off, final int len) throws IOException {
			for (final var writer : writers) {
				writer.write(str, off, len);
			}
		}

		@Override
		public Writer append(final CharSequence csq) throws IOException {
			for (final var writer : writers) {
				writer.append(csq);
			}
			return this;
		}

		@Override
		public Writer append(final CharSequence csq, final int start, final int end) throws IOException {
			for (final var writer : writers) {
				writer.append(csq, start, end);
			}
			return this;
		}

		@Override
		public Writer append(final char c) throws IOException {
			for (final var writer : writers) {
				writer.append(c);
			}
			return this;
		}

		@Override
		public void flush() throws IOException {
			for (final var writer : writers) {
				writer.flush();
			}
		}

		@Override
		public void close() throws IOException {
			for (final var writer : writers) {
				writer.close();
			}
		}
	}

	/**
	 * <p>A blank {@code Writer} that writes no char.</p>
	 * @since 1.0
	 */
	public static final Writer BLANK = new Writer() {
		@Override
		public void write(@NotNull final char[] cbuf, final int off, final int len) {
			// Do nothing
		}

		@Override
		public void flush() {
			// Do nothing
		}

		@Override
		public void close() {
			// Do nothing
		}
	};

	/**
	 * <p>Constructor not available.</p>
	 * @since 1.0
	 */
	private Writers() {
		// Not available
	}

	/**
	 * <p>Wrap a {@code Writer} replacing {@code null} by a blank {@code Writer}.</p>
	 * @param writer a {@code Writer} or {@code null}
	 * @return a non-{@code null} {@code Writer}
	 * @since 1.0
	 */
	public static Writer nullToBlank(final Writer writer) {
		return null != writer ? writer : BLANK;
	}

	/**
	 * <p>Wrap a {@code Writer} as a {@code BufferedWriter} if it was not already.</p>
	 * @param writer the {@code Writer} to wrap
	 * @return the buffered {@code Writer}
	 * @throws NullPointerException if the {@code Writer} is {@code null}
	 * @since 1.0
	 */
	public static BufferedWriter buffered(final Writer writer) {
		if (null == writer) {
			throw new NullPointerException("Invalid writer (not null expected)");
		}
		if (writer instanceof BufferedWriter) {
			return (BufferedWriter) writer;
		}
		return new BufferedWriter(writer);
	}

	/**
	 * <p>Wrap a {@code Writer} whose {@link Writer#close()} method has no effect.</p>
	 * @param writer the {@code Writer} to wrap
	 * @return the uncloseable {@code Writer}
	 * @throws NullPointerException if the {@code Writer} is {@code null}
	 * @since 1.0
	 */
	public static Writer uncloseable(final Writer writer) {
		if (null == writer) {
			throw new NullPointerException("Invalid writer (not null expected)");
		}
		return new FilterWriter(writer) {
			@Override
			public void close() {
				// Do nothing
			}
		};
	}

	/**
	 * <p>Wrap multiple {@code Writer}s into a single one.</p>
	 * @param writers {@code Writer}s to wrap
	 * @return the "tee-ed" {@code Writer}
	 * @throws NullPointerException if the {@code Writer}s array or any of the {@code Writer}s is {@code null}
	 * @since 1.0
	 */
	public static Writer tee(final Writer... writers) {
		if (null == writers) {
			throw new NullPointerException("Invalid writers (not null expected)");
		}
		return tee(Arrays.asList(writers));
	}

	/**
	 * <p>Wrap a collection of {@code Writer}s into a single one.</p>
	 * @param writers {@code Writer}s to wrap
	 * @return the "tee-ed" {@code Writer}
	 * @throws NullPointerException if the {@code Writer}s collection or any of the {@code Writer}s is {@code null}
	 * @since 1.0
	 */
	public static Writer tee(final Collection<Writer> writers) {
		if (null == writers) {
			throw new NullPointerException("Invalid writers (not null expected)");
		}
		for (final var writer : writers) {
			if (null == writer) {
				throw new NullPointerException("Invalid writer (not null expected)");
			}
		}
		if (writers.isEmpty()) {
			return BLANK;
		}
		if (1 == writers.size()) {
			return writers.iterator().next();
		}
		return new TeeWriter(writers);
	}
}