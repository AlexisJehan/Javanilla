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

import com.github.alexisjehan.javanilla.util.iteration.Iterables;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Collection;

/**
 * <p>An utility class that provides {@link Writer} tools.</p>
 * @since 1.0.0
 */
public final class Writers {

	/**
	 * <p>An empty {@code Writer} that writes nothing.</p>
	 * @since 1.0.0
	 */
	public static final Writer EMPTY = new Writer() {
		@Override
		public void write(final int c) {
			// Do nothing
		}

		@Override
		public void write(final char[] chars) {
			if (null == chars) {
				throw new NullPointerException("Invalid chars (not null expected)");
			}
			// Do nothing
		}

		@Override
		public void write(final char[] chars, final int offset, final int length) {
			if (null == chars) {
				throw new NullPointerException("Invalid chars (not null expected)");
			}
			if (0 > offset || chars.length < offset) {
				throw new IndexOutOfBoundsException("Invalid offset: " + offset + " (between 0 and " + chars.length + " expected)");
			}
			if (0 > length || chars.length - offset < length) {
				throw new IndexOutOfBoundsException("Invalid length: " + length + " (between 0 and " + (chars.length - offset) + " expected)");
			}
			// Do nothing
		}

		@Override
		public void write(final String string) {
			if (null == string) {
				throw new NullPointerException("Invalid String (not null expected)");
			}
			// Do nothing
		}

		@Override
		public void write(final String string, final int offset, final int length) {
			if (null == string) {
				throw new NullPointerException("Invalid String (not null expected)");
			}
			final var stringLength = string.length();
			if (0 > offset || stringLength < offset) {
				throw new IndexOutOfBoundsException("Invalid offset: " + offset + " (between 0 and " + stringLength + " expected)");
			}
			if (0 > length || stringLength - offset < length) {
				throw new IndexOutOfBoundsException("Invalid length: " + length + " (between 0 and " + (stringLength - offset) + " expected)");
			}
			// Do nothing
		}

		@Override
		public Writer append(final CharSequence charSequence) {
			if (null == charSequence) {
				throw new NullPointerException("Invalid CharSequence (not null expected)");
			}
			return this;
		}

		@Override
		public Writer append(final CharSequence charSequence, final int start, final int end) {
			if (null == charSequence) {
				throw new NullPointerException("Invalid CharSequence (not null expected)");
			}
			final var length = charSequence.length();
			if (0 > start) {
				throw new IndexOutOfBoundsException("Invalid start: " + start + " (greater than 0 expected)");
			}
			if (start > end || length < end) {
				throw new IndexOutOfBoundsException("Invalid end: " + end + " (between " + start + " and " + length + " expected)");
			}
			return this;
		}

		@Override
		public Writer append(final char c) {
			return this;
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
	 * @since 1.0.0
	 */
	private Writers() {
		// Not available
	}

	/**
	 * <p>Wrap a {@code Writer} replacing {@code null} by an empty one.</p>
	 * @param writer the {@code Writer} or {@code null}
	 * @return a non-{@code null} {@code Writer}
	 * @since 1.0.0
	 */
	public static Writer nullToEmpty(final Writer writer) {
		return nullToDefault(writer, EMPTY);
	}

	/**
	 * <p>Wrap a {@code Writer} replacing {@code null} by a default one.</p>
	 * @param writer the {@code Writer} or {@code null}
	 * @param defaultWriter the default {@code Writer}
	 * @param <W> the {@code Writer} type
	 * @return a non-{@code null} {@code Writer}
	 * @throws NullPointerException if the default {@code Writer} is {@code null}
	 * @since 1.1.0
	 */
	public static <W extends Writer> W nullToDefault(final W writer, final W defaultWriter) {
		if (null == defaultWriter) {
			throw new NullPointerException("Invalid default Writer (not null expected)");
		}
		return null != writer ? writer : defaultWriter;
	}

	/**
	 * <p>Decorate a {@code Writer} as a {@code BufferedWriter} if it was not already.</p>
	 * @param writer the {@code Writer} to decorate
	 * @return a {@code BufferedWriter}
	 * @throws NullPointerException if the {@code Writer} is {@code null}
	 * @since 1.0.0
	 */
	public static BufferedWriter buffered(final Writer writer) {
		if (null == writer) {
			throw new NullPointerException("Invalid Writer (not null expected)");
		}
		if (writer instanceof BufferedWriter) {
			return (BufferedWriter) writer;
		}
		return new BufferedWriter(writer);
	}

	/**
	 * <p>Decorate a {@code Writer} so that its {@link Writer#close()} method has no effect.</p>
	 * @param writer the {@code Writer} to decorate
	 * @return an uncloseable {@code Writer}
	 * @throws NullPointerException if the {@code Writer} is {@code null}
	 * @since 1.0.0
	 */
	public static Writer uncloseable(final Writer writer) {
		if (null == writer) {
			throw new NullPointerException("Invalid Writer (not null expected)");
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
	 * @param writers the {@code Writer} array to wrap
	 * @return the "tee-ed" {@code Writer}
	 * @throws NullPointerException if the {@code Writer} array or any of them is {@code null}
	 * @since 1.0.0
	 */
	public static Writer tee(final Writer... writers) {
		if (null == writers) {
			throw new NullPointerException("Invalid Writers (not null expected)");
		}
		return tee(Arrays.asList(writers));
	}

	/**
	 * <p>Wrap multiple {@code Writer}s into a single one.</p>
	 * @param writers the {@code Writer} {@code Collection} to wrap
	 * @return the "tee-ed" {@code Writer}
	 * @throws NullPointerException if the {@code Writer} {@code Collection} or any of them is {@code null}
	 * @since 1.0.0
	 */
	public static Writer tee(final Collection<Writer> writers) {
		if (null == writers) {
			throw new NullPointerException("Invalid Writers (not null expected)");
		}
		for (final var indexedWriter : Iterables.index(writers)) {
			if (null == indexedWriter.getElement()) {
				throw new NullPointerException("Invalid Writer at index " + indexedWriter.getIndex() + " (not null expected)");
			}
		}
		final var size = writers.size();
		if (0 == size) {
			return EMPTY;
		}
		if (1 == size) {
			return writers.iterator().next();
		}
		return new Writer() {
			@Override
			public void write(final int c) throws IOException {
				for (final var writer : writers) {
					writer.write(c);
				}
			}

			@Override
			public void write(final char[] chars) throws IOException {
				if (null == chars) {
					throw new NullPointerException("Invalid chars (not null expected)");
				}
				for (final var writer : writers) {
					writer.write(chars);
				}
			}

			@Override
			public void write(final char[] chars, final int offset, final int length) throws IOException {
				if (null == chars) {
					throw new NullPointerException("Invalid chars (not null expected)");
				}
				if (0 > offset || chars.length < offset) {
					throw new IndexOutOfBoundsException("Invalid offset: " + offset + " (between 0 and " + chars.length + " expected)");
				}
				if (0 > length || chars.length - offset < length) {
					throw new IndexOutOfBoundsException("Invalid length: " + length + " (between 0 and " + (chars.length - offset) + " expected)");
				}
				for (final var writer : writers) {
					writer.write(chars, offset, length);
				}
			}

			@Override
			public void write(final String string) throws IOException {
				if (null == string) {
					throw new NullPointerException("Invalid String (not null expected)");
				}
				for (final var writer : writers) {
					writer.write(string);
				}
			}

			@Override
			public void write(final String string, final int offset, final int length) throws IOException {
				if (null == string) {
					throw new NullPointerException("Invalid String (not null expected)");
				}
				final var stringLength = string.length();
				if (0 > offset || stringLength < offset) {
					throw new IndexOutOfBoundsException("Invalid offset: " + offset + " (between 0 and " + stringLength + " expected)");
				}
				if (0 > length || stringLength - offset < length) {
					throw new IndexOutOfBoundsException("Invalid length: " + length + " (between 0 and " + (stringLength - offset) + " expected)");
				}
				for (final var writer : writers) {
					writer.write(string, offset, length);
				}
			}

			@Override
			public Writer append(final CharSequence charSequence) throws IOException {
				if (null == charSequence) {
					throw new NullPointerException("Invalid CharSequence (not null expected)");
				}
				for (final var writer : writers) {
					writer.append(charSequence);
				}
				return this;
			}

			@Override
			public Writer append(final CharSequence charSequence, final int start, final int end) throws IOException {
				if (null == charSequence) {
					throw new NullPointerException("Invalid CharSequence (not null expected)");
				}
				final var length = charSequence.length();
				if (0 > start) {
					throw new IndexOutOfBoundsException("Invalid start: " + start + " (greater than 0 expected)");
				}
				if (start > end || length < end) {
					throw new IndexOutOfBoundsException("Invalid end: " + end + " (between " + start + " and " + length + " expected)");
				}
				for (final var writer : writers) {
					writer.append(charSequence, start, end);
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
		};
	}

	/**
	 * <p>Create a {@code BufferedWriter} from a {@code Path} using {@link Charset#defaultCharset()}.</p>
	 * @param path the {@code Path} to convert
	 * @return the created {@code BufferedWriter}
	 * @throws FileNotFoundException if the file of the {@code Path} is not writable
	 * @throws NullPointerException if the {@code Path} is {@code null}
	 * @since 1.2.0
	 */
	public static BufferedWriter of(final Path path) throws FileNotFoundException {
		return of(path, Charset.defaultCharset());
	}

	/**
	 * <p>Create a {@code BufferedWriter} from a {@code Path} using a custom {@code Charset}.</p>
	 * @param path the {@code Path} to convert
	 * @param charset the {@code Charset} to use
	 * @return the created {@code BufferedWriter}
	 * @throws FileNotFoundException if the file of the {@code Path} is not writable
	 * @throws NullPointerException if the {@code Path} or the {@code Charset} is {@code null}
	 * @since 1.2.0
	 */
	public static BufferedWriter of(final Path path, final Charset charset) throws FileNotFoundException {
		if (null == path) {
			throw new NullPointerException("Invalid Path (not null expected)");
		}
		if (null == charset) {
			throw new NullPointerException("Invalid Charset (not null expected)");
		}
		return new BufferedWriter(new OutputStreamWriter(new FileOutputStream(path.toFile()), charset));
	}
}