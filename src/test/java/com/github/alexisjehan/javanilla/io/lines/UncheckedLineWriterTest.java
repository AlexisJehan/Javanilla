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
package com.github.alexisjehan.javanilla.io.lines;

import com.github.alexisjehan.javanilla.io.chars.Writers;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.UncheckedIOException;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.assertj.core.api.Assertions.assertThatNullPointerException;

/**
 * <p>{@link UncheckedLineWriter} unit tests.</p>
 */
final class UncheckedLineWriterTest {

	private static final LineWriter EXCEPTION = new LineWriter() {
		@Override
		public void write(final String line) throws IOException {
			throw new IOException();
		}

		@Override
		public void newLine() throws IOException {
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
		assertThatNullPointerException().isThrownBy(() -> new UncheckedLineWriter(null));
	}

	@Test
	void testWrite() {
		try (final var uncheckedLineWriter = new UncheckedLineWriter(new LineWriter(Writers.EMPTY))) {
			uncheckedLineWriter.write("abc");
		}
		final var uncheckedLineWriter = new UncheckedLineWriter(EXCEPTION);
		assertThatExceptionOfType(UncheckedIOException.class).isThrownBy(() -> uncheckedLineWriter.write("abc"));
	}

	@Test
	void testNewLine() {
		try (final var uncheckedLineWriter = new UncheckedLineWriter(new LineWriter(Writers.EMPTY))) {
			uncheckedLineWriter.newLine();
		}
		final var uncheckedLineWriter = new UncheckedLineWriter(EXCEPTION);
		assertThatExceptionOfType(UncheckedIOException.class).isThrownBy(uncheckedLineWriter::newLine);
	}

	@Test
	void testFlush() {
		try (final var uncheckedLineWriter = new UncheckedLineWriter(new LineWriter(Writers.EMPTY))) {
			uncheckedLineWriter.flush();
		}
		final var uncheckedLineWriter = new UncheckedLineWriter(EXCEPTION);
		assertThatExceptionOfType(UncheckedIOException.class).isThrownBy(uncheckedLineWriter::flush);
	}

	@Test
	void testClose() {
		{
			final var uncheckedLineWriter = new UncheckedLineWriter(new LineWriter(Writers.EMPTY));
			uncheckedLineWriter.close();
		}
		final var uncheckedLineWriter = new UncheckedLineWriter(EXCEPTION);
		assertThatExceptionOfType(UncheckedIOException.class).isThrownBy(uncheckedLineWriter::close);
	}
}