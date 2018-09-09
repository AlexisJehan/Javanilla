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

import com.github.alexisjehan.javanilla.lang.array.CharArrays;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.io.Writer;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.assertj.core.api.Assertions.assertThatNullPointerException;

/**
 * <p>{@link UncheckedWriter} unit tests.</p>
 */
final class UncheckedWriterTest {

	private static final Writer EXCEPTION = new Writer() {
		@Override
		public void write(final int c) throws IOException {
			throw new IOException();
		}

		@Override
		public void write(final char[] chars) throws IOException {
			throw new IOException();
		}

		@Override
		public void write(final char[] chars, final int offset, final int length) throws IOException {
			throw new IOException();
		}

		@Override
		public void write(final String string) throws IOException {
			throw new IOException();
		}

		@Override
		public void write(final String string, final int offset, final int length) throws IOException {
			throw new IOException();
		}

		@Override
		public Writer append(final CharSequence charSequence) throws IOException {
			throw new IOException();
		}

		@Override
		public Writer append(final CharSequence charSequence, final int start, final int end) throws IOException {
			throw new IOException();
		}

		@Override
		public Writer append(final char c) throws IOException {
			throw new IOException();
		}

		@Override
		public void flush() throws IOException {
			throw new IOException();
		}

		@Override
		public void close() throws IOException {
			throw new IOException();
		}
	};

	@Test
	void testConstructorInvalid() {
		assertThatNullPointerException().isThrownBy(() -> new UncheckedWriter(null));
	}

	@Test
	void testWriteChar() {
		try (final var uncheckedWriter = new UncheckedWriter(Writers.EMPTY)) {
			uncheckedWriter.write(1);
		}
		final var uncheckedWriter = new UncheckedWriter(EXCEPTION);
		assertThatExceptionOfType(UncheckedIOException.class).isThrownBy(() -> uncheckedWriter.write(1));
	}

	@Test
	void testWriteChars() {
		try (final var uncheckedWriter = new UncheckedWriter(Writers.EMPTY)) {
			uncheckedWriter.write(CharArrays.singleton('a'));
		}
		final var uncheckedWriter = new UncheckedWriter(EXCEPTION);
		assertThatExceptionOfType(UncheckedIOException.class).isThrownBy(() -> uncheckedWriter.write(CharArrays.singleton('a')));
	}

	@Test
	void testWriteCharsInvalid() {
		try (final var uncheckedWriter = new UncheckedWriter(Writers.EMPTY)) {
			assertThatNullPointerException().isThrownBy(() -> uncheckedWriter.write((char[]) null));
		}
	}

	@Test
	void testWriteCharsOffsetLength() {
		try (final var uncheckedWriter = new UncheckedWriter(Writers.EMPTY)) {
			uncheckedWriter.write(CharArrays.singleton('a'), 0, 1);
		}
		final var uncheckedWriter = new UncheckedWriter(EXCEPTION);
		assertThatExceptionOfType(UncheckedIOException.class).isThrownBy(() -> uncheckedWriter.write(CharArrays.singleton('a'), 0, 1));
	}

	@Test
	void testWriteCharsOffsetLengthInvalid() {
		final var chars = CharArrays.of('a', 'b', 'c');
		try (final var uncheckedWriter = new UncheckedWriter(Writers.EMPTY)) {
			assertThatNullPointerException().isThrownBy(() -> uncheckedWriter.write((char[]) null, 0, 2));
			assertThatExceptionOfType(IndexOutOfBoundsException.class).isThrownBy(() -> uncheckedWriter.write(chars, -1, 3));
			assertThatExceptionOfType(IndexOutOfBoundsException.class).isThrownBy(() -> uncheckedWriter.write(chars, 4, 3));
			assertThatExceptionOfType(IndexOutOfBoundsException.class).isThrownBy(() -> uncheckedWriter.write(chars, 0, -1));
			assertThatExceptionOfType(IndexOutOfBoundsException.class).isThrownBy(() -> uncheckedWriter.write(chars, 0, 4));
		}
	}

	@Test
	void testWriteString() {
		try (final var uncheckedWriter = new UncheckedWriter(Writers.EMPTY)) {
			uncheckedWriter.write("a");
		}
		final var uncheckedWriter = new UncheckedWriter(EXCEPTION);
		assertThatExceptionOfType(UncheckedIOException.class).isThrownBy(() -> uncheckedWriter.write("a"));
	}

	@Test
	void testWriteStringInvalid() {
		try (final var uncheckedWriter = new UncheckedWriter(Writers.EMPTY)) {
			assertThatNullPointerException().isThrownBy(() -> uncheckedWriter.write((String) null));
		}
	}

	@Test
	void testWriteStringOffsetLength() {
		try (final var uncheckedWriter = new UncheckedWriter(Writers.EMPTY)) {
			uncheckedWriter.write("a", 0, 1);
		}
		final var uncheckedWriter = new UncheckedWriter(EXCEPTION);
		assertThatExceptionOfType(UncheckedIOException.class).isThrownBy(() -> uncheckedWriter.write("a", 0, 1));
	}

	@Test
	void testWriteStringOffsetLengthInvalid() {
		final var string = "abc";
		try (final var uncheckedWriter = new UncheckedWriter(Writers.EMPTY)) {
			assertThatNullPointerException().isThrownBy(() -> uncheckedWriter.write((String) null, 0, 2));
			assertThatExceptionOfType(IndexOutOfBoundsException.class).isThrownBy(() -> uncheckedWriter.write(string, -1, 3));
			assertThatExceptionOfType(IndexOutOfBoundsException.class).isThrownBy(() -> uncheckedWriter.write(string, 4, 3));
			assertThatExceptionOfType(IndexOutOfBoundsException.class).isThrownBy(() -> uncheckedWriter.write(string, 0, -1));
			assertThatExceptionOfType(IndexOutOfBoundsException.class).isThrownBy(() -> uncheckedWriter.write(string, 0, 4));
		}
	}

	@Test
	void testAppend() {
		try (final var uncheckedWriter = new UncheckedWriter(Writers.EMPTY)) {
			assertThat(uncheckedWriter.append("a")).isSameAs(uncheckedWriter);
		}
		final var uncheckedWriter = new UncheckedWriter(EXCEPTION);
		assertThatExceptionOfType(UncheckedIOException.class).isThrownBy(() -> uncheckedWriter.append("a"));
	}

	@Test
	void testAppendInvalid() {
		try (final var uncheckedWriter = new UncheckedWriter(Writers.EMPTY)) {
			assertThatNullPointerException().isThrownBy(() -> uncheckedWriter.append(null));
		}
	}

	@Test
	void testAppendStartEnd() {
		try (final var uncheckedWriter = new UncheckedWriter(Writers.EMPTY)) {
			assertThat(uncheckedWriter.append("a", 0, 1)).isSameAs(uncheckedWriter);
		}
		final var uncheckedWriter = new UncheckedWriter(EXCEPTION);
		assertThatExceptionOfType(UncheckedIOException.class).isThrownBy(() -> uncheckedWriter.append("a", 0, 1));
	}

	@Test
	void testAppendStartEndInvalid() {
		final var string = "abc";
		try (final var uncheckedWriter = new UncheckedWriter(Writers.EMPTY)) {
			assertThatNullPointerException().isThrownBy(() -> uncheckedWriter.append(null, 0, 2));
			assertThatExceptionOfType(IndexOutOfBoundsException.class).isThrownBy(() -> uncheckedWriter.append(string, -1, 3));
			assertThatExceptionOfType(IndexOutOfBoundsException.class).isThrownBy(() -> uncheckedWriter.append(string, 4, 3));
			assertThatExceptionOfType(IndexOutOfBoundsException.class).isThrownBy(() -> uncheckedWriter.append(string, 0, -1));
			assertThatExceptionOfType(IndexOutOfBoundsException.class).isThrownBy(() -> uncheckedWriter.append(string, 0, 4));
		}
	}

	@Test
	void testAppendChar() {
		try (final var uncheckedWriter = new UncheckedWriter(Writers.EMPTY)) {
			assertThat(uncheckedWriter.append('a')).isSameAs(uncheckedWriter);
		}
		final var uncheckedWriter = new UncheckedWriter(EXCEPTION);
		assertThatExceptionOfType(UncheckedIOException.class).isThrownBy(() -> uncheckedWriter.append('a'));
	}

	@Test
	void testFlush() {
		try (final var uncheckedWriter = new UncheckedWriter(Writers.EMPTY)) {
			uncheckedWriter.flush();
		}
		final var uncheckedWriter = new UncheckedWriter(EXCEPTION);
		assertThatExceptionOfType(UncheckedIOException.class).isThrownBy(uncheckedWriter::flush);
	}

	@Test
	void testClose() {
		{
			final var uncheckedWriter = new UncheckedWriter(Writers.EMPTY);
			uncheckedWriter.close();
		}
		final var uncheckedWriter = new UncheckedWriter(EXCEPTION);
		assertThatExceptionOfType(UncheckedIOException.class).isThrownBy(uncheckedWriter::close);
	}
}