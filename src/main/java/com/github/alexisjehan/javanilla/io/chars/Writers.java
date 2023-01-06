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
package com.github.alexisjehan.javanilla.io.chars;

import com.github.alexisjehan.javanilla.misc.quality.Ensure;

import java.io.BufferedWriter;
import java.io.FilterWriter;
import java.io.IOException;
import java.io.Writer;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;
import java.util.Set;

/**
 * <p>An utility class that provides {@link Writer} tools.</p>
 * @deprecated since 1.8.0, use {@link com.github.alexisjehan.javanilla.io.Writers} instead
 * @since 1.0.0
 */
@Deprecated(since = "1.8.0")
public final class Writers {

	/**
	 * <p>An empty {@link Writer} that writes nothing.</p>
	 * @since 1.0.0
	 */
	public static final Writer EMPTY = new Writer() {

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void write(final int i) {
			// Do nothing
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void write(final char[] chars) {
			Ensure.notNull("chars", chars);
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void write(final char[] chars, final int offset, final int length) {
			Ensure.notNull("chars", chars);
			Ensure.between("offset", offset, 0, chars.length);
			Ensure.between("length", length, 0, chars.length - offset);
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void write(final String string) {
			Ensure.notNull("string", string);
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void write(final String string, final int offset, final int length) {
			Ensure.notNull("string", string);
			final var stringLength = string.length();
			Ensure.between("offset", offset, 0, stringLength);
			Ensure.between("length", length, 0, stringLength - offset);
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public Writer append(final CharSequence charSequence) {
			Ensure.notNull("charSequence", charSequence);
			return this;
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public Writer append(final CharSequence charSequence, final int start, final int end) {
			Ensure.notNull("charSequence", charSequence);
			final var charSequenceLength = charSequence.length();
			Ensure.between("start", start, 0, charSequenceLength);
			Ensure.between("end", end, start, charSequenceLength);
			return this;
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public Writer append(final char c) {
			return this;
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void flush() {
			// Do nothing
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void close() {
			// Do nothing
		}
	};

	/**
	 * <p>Constructor not available.</p>
	 * @since 1.0.0
	 */
	private Writers() {
		// Not available
	}

	/**
	 * <p>Wrap a {@link Writer} replacing {@code null} by an empty one.</p>
	 * @param writer the {@link Writer} or {@code null}
	 * @return a non-{@code null} {@link Writer}
	 * @since 1.0.0
	 */
	public static Writer nullToEmpty(final Writer writer) {
		return nullToDefault(writer, EMPTY);
	}

	/**
	 * <p>Wrap a {@link Writer} replacing {@code null} by a default one.</p>
	 * @param writer the {@link Writer} or {@code null}
	 * @param defaultWriter the default {@link Writer}
	 * @param <W> the {@link Writer} type
	 * @return a non-{@code null} {@link Writer}
	 * @throws NullPointerException if the default {@link Writer} is {@code null}
	 * @since 1.1.0
	 */
	public static <W extends Writer> W nullToDefault(final W writer, final W defaultWriter) {
		Ensure.notNull("defaultWriter", defaultWriter);
		return null != writer ? writer : defaultWriter;
	}

	/**
	 * <p>Decorate a {@link Writer} as a {@link BufferedWriter} if it was not already.</p>
	 * @param writer the {@link Writer} to decorate
	 * @return a {@link BufferedWriter}
	 * @throws NullPointerException if the {@link Writer} is {@code null}
	 * @since 1.0.0
	 */
	public static BufferedWriter buffered(final Writer writer) {
		Ensure.notNull("writer", writer);
		if (writer instanceof BufferedWriter) {
			return (BufferedWriter) writer;
		}
		return new BufferedWriter(writer);
	}

	/**
	 * <p>Decorate a {@link Writer} so that its {@link Writer#close()} method has no effect.</p>
	 * @param writer the {@link Writer} to decorate
	 * @return a {@link Writer} that cannot be closed
	 * @throws NullPointerException if the {@link Writer} is {@code null}
	 * @since 1.0.0
	 */
	public static Writer uncloseable(final Writer writer) {
		Ensure.notNull("writer", writer);
		return new FilterWriter(writer) {

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
	 * <p>Wrap multiple {@link Writer}s into a single one.</p>
	 * @param writers the {@link Writer} array to wrap
	 * @return the "tee-ed" {@link Writer}
	 * @throws NullPointerException if the {@link Writer} array or any of them is {@code null}
	 * @since 1.0.0
	 */
	public static Writer tee(final Writer... writers) {
		Ensure.notNullAndNotNullElements("writers", writers);
		return tee(Set.of(writers));
	}

	/**
	 * <p>Wrap multiple {@link Writer}s into a single one.</p>
	 * @param writers the {@link Writer} {@link Collection} to wrap
	 * @return the "tee-ed" {@link Writer}
	 * @throws NullPointerException if the {@link Writer} {@link Collection} or any of them is {@code null}
	 * @since 1.0.0
	 */
	public static Writer tee(final Collection<? extends Writer> writers) {
		Ensure.notNullAndNotNullElements("writers", writers);
		final var size = writers.size();
		if (0 == size) {
			return EMPTY;
		}
		if (1 == size) {
			return writers.iterator().next();
		}
		return new Writer() {

			/**
			 * {@inheritDoc}
			 */
			@Override
			public void write(final int i) throws IOException {
				for (final var writer : writers) {
					writer.write(i);
				}
			}

			/**
			 * {@inheritDoc}
			 */
			@Override
			public void write(final char[] chars) throws IOException {
				Ensure.notNull("chars", chars);
				if (0 < chars.length) {
					for (final var writer : writers) {
						writer.write(chars);
					}
				}
			}

			/**
			 * {@inheritDoc}
			 */
			@Override
			public void write(final char[] chars, final int offset, final int length) throws IOException {
				Ensure.notNull("chars", chars);
				Ensure.between("offset", offset, 0, chars.length);
				Ensure.between("length", length, 0, chars.length - offset);
				if (0 < length) {
					for (final var writer : writers) {
						writer.write(chars, offset, length);
					}
				}
			}

			/**
			 * {@inheritDoc}
			 */
			@Override
			public void write(final String string) throws IOException {
				Ensure.notNull("string", string);
				if (!string.isEmpty()) {
					for (final var writer : writers) {
						writer.write(string);
					}
				}
			}

			/**
			 * {@inheritDoc}
			 */
			@Override
			public void write(final String string, final int offset, final int length) throws IOException {
				Ensure.notNull("string", string);
				final var stringLength = string.length();
				Ensure.between("offset", offset, 0, stringLength);
				Ensure.between("length", length, 0, stringLength - offset);
				if (0 < length) {
					for (final var writer : writers) {
						writer.write(string, offset, length);
					}
				}
			}

			/**
			 * {@inheritDoc}
			 */
			@Override
			public Writer append(final CharSequence charSequence) throws IOException {
				Ensure.notNull("charSequence", charSequence);
				if (0 < charSequence.length()) {
					for (final var writer : writers) {
						writer.append(charSequence);
					}
				}
				return this;
			}

			/**
			 * {@inheritDoc}
			 */
			@Override
			public Writer append(final CharSequence charSequence, final int start, final int end) throws IOException {
				Ensure.notNull("charSequence", charSequence);
				final var charSequenceLength = charSequence.length();
				Ensure.between("start", start, 0, charSequenceLength);
				Ensure.between("end", end, start, charSequenceLength);
				if (start < end) {
					for (final var writer : writers) {
						writer.append(charSequence, start, end);
					}
				}
				return this;
			}

			/**
			 * {@inheritDoc}
			 */
			@Override
			public Writer append(final char c) throws IOException {
				for (final var writer : writers) {
					writer.append(c);
				}
				return this;
			}

			/**
			 * {@inheritDoc}
			 */
			@Override
			public void flush() throws IOException {
				for (final var writer : writers) {
					writer.flush();
				}
			}

			/**
			 * {@inheritDoc}
			 */
			@Override
			public void close() throws IOException {
				for (final var writer : writers) {
					writer.close();
				}
			}
		};
	}

	/**
	 * <p>Create a {@link BufferedWriter} from a {@link Path} using {@link Charset#defaultCharset()}.</p>
	 * @param path the {@link Path} to convert
	 * @return the created {@link BufferedWriter}
	 * @throws IOException might occur with I/O operations
	 * @throws NullPointerException if the {@link Path} is {@code null}
	 * @since 1.2.0
	 */
	public static BufferedWriter of(final Path path) throws IOException {
		return of(path, Charset.defaultCharset());
	}

	/**
	 * <p>Create a {@link BufferedWriter} from a {@link Path} using a custom {@link Charset}.</p>
	 * @param path the {@link Path} to convert
	 * @param charset the {@link Charset} to use
	 * @return the created {@link BufferedWriter}
	 * @throws IOException might occur with I/O operations
	 * @throws NullPointerException if the {@link Path} or the {@link Charset} is {@code null}
	 * @since 1.2.0
	 */
	public static BufferedWriter of(final Path path, final Charset charset) throws IOException {
		Ensure.notNull("path", path);
		Ensure.notNull("charset", charset);
		return Files.newBufferedWriter(path, charset);
	}
}