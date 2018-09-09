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

import com.github.alexisjehan.javanilla.io.chars.Readers;
import com.github.alexisjehan.javanilla.io.chars.Writers;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.UncheckedIOException;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.assertj.core.api.Assertions.assertThatNullPointerException;

/**
 * <p>{@link UncheckedLineReader} unit tests.</p>
 */
final class UncheckedLineReaderTest {

	private static final LineReader EXCEPTION = new LineReader() {
		@Override
		public String read() throws IOException {
			throw new IOException();
		}

		@Override
		public long skip(final long n) throws IOException {
			throw new IOException();
		}

		@Override
		public long transferTo(final LineWriter lineWriter) throws IOException {
			throw new IOException();
		}

		@Override
		public void close() throws IOException {
			throw new IOException();
		}
	};

	@Test
	void testConstructorInvalid() {
		assertThatNullPointerException().isThrownBy(() -> new UncheckedLineReader(null));
	}

	@Test
	void testRead() {
		try (final var uncheckedLineReader = new UncheckedLineReader(new LineReader(Readers.EMPTY, LineSeparator.DEFAULT))) {
			uncheckedLineReader.read();
		}
		final var uncheckedLineReader = new UncheckedLineReader(EXCEPTION);
		assertThatExceptionOfType(UncheckedIOException.class).isThrownBy(uncheckedLineReader::read);
	}

	@Test
	void testSkip() {
		try (final var uncheckedLineReader = new UncheckedLineReader(new LineReader(Readers.EMPTY, LineSeparator.DEFAULT))) {
			uncheckedLineReader.skip(1L);
		}
		final var uncheckedLineReader = new UncheckedLineReader(EXCEPTION);
		assertThatExceptionOfType(UncheckedIOException.class).isThrownBy(() -> uncheckedLineReader.skip(1L));
	}

	@Test
	void testTransferTo() {
		try (final var uncheckedLineReader = new UncheckedLineReader(new LineReader(Readers.EMPTY, LineSeparator.DEFAULT))) {
			uncheckedLineReader.transferTo(new LineWriter(Writers.EMPTY));
		}
		final var uncheckedLineReader = new UncheckedLineReader(EXCEPTION);
		assertThatExceptionOfType(UncheckedIOException.class).isThrownBy(() -> uncheckedLineReader.transferTo(new LineWriter(Writers.EMPTY)));
	}

	@Test
	void testClose() {
		{
			final var uncheckedLineReader = new UncheckedLineReader(new LineReader(Readers.EMPTY, LineSeparator.DEFAULT));
			uncheckedLineReader.close();
		}
		final var uncheckedLineReader = new UncheckedLineReader(EXCEPTION);
		assertThatExceptionOfType(UncheckedIOException.class).isThrownBy(uncheckedLineReader::close);
	}
}