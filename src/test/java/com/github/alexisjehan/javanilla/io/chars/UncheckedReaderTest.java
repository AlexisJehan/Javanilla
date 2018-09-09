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

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.Reader;
import java.io.UncheckedIOException;
import java.io.Writer;
import java.nio.CharBuffer;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.assertj.core.api.Assertions.assertThatNullPointerException;

/**
 * <p>{@link UncheckedReader} unit tests.</p>
 */
final class UncheckedReaderTest {

	private static final Reader EXCEPTION = new Reader() {
		@Override
		public int read(final CharBuffer charBuffer) throws IOException {
			throw new IOException();
		}

		@Override
		public int read() throws IOException {
			throw new IOException();
		}

		@Override
		public int read(final char[] buffer) throws IOException {
			throw new IOException();
		}

		@Override
		public int read(final char[] buffer, int offset, int length) throws IOException {
			throw new IOException();
		}

		@Override
		public long skip(final long n) throws IOException {
			throw new IOException();
		}

		@Override
		public boolean ready() throws IOException {
			throw new IOException();
		}

		@Override
		public void mark(final int limit) throws IOException {
			throw new IOException();
		}

		@Override
		public void reset() throws IOException {
			throw new IOException();
		}

		@Override
		public void close() throws IOException {
			throw new IOException();
		}

		@Override
		public long transferTo(final Writer writer) throws IOException {
			throw new IOException();
		}
	};

	@Test
	void testConstructorInvalid() {
		assertThatNullPointerException().isThrownBy(() -> new UncheckedReader(null));
	}

	@Test
	void testReadCharBuffer() {
		try (final var uncheckedReader = new UncheckedReader(Readers.EMPTY)) {
			uncheckedReader.read(CharBuffer.wrap(new char[1]));
		}
		final var uncheckedReader = new UncheckedReader(EXCEPTION);
		assertThatExceptionOfType(UncheckedIOException.class).isThrownBy(() -> uncheckedReader.read(CharBuffer.wrap(new char[1])));
	}

	@Test
	void testReadCharBufferInvalid() {
		try (final var uncheckedReader = new UncheckedReader(Readers.EMPTY)) {
			assertThatNullPointerException().isThrownBy(() -> uncheckedReader.read((CharBuffer) null));
		}
	}

	@Test
	void testReadChar() {
		try (final var uncheckedReader = new UncheckedReader(Readers.EMPTY)) {
			uncheckedReader.read();
		}
		final var uncheckedReader = new UncheckedReader(EXCEPTION);
		assertThatExceptionOfType(UncheckedIOException.class).isThrownBy(uncheckedReader::read);
	}

	@Test
	void testReadBuffer() {
		final var buffer = new char[2];
		try (final var uncheckedReader = new UncheckedReader(Readers.EMPTY)) {
			uncheckedReader.read(buffer);
		}
		final var uncheckedReader = new UncheckedReader(EXCEPTION);
		assertThatExceptionOfType(UncheckedIOException.class).isThrownBy(() -> uncheckedReader.read(buffer));
	}

	@Test
	void testReadBufferInvalid() {
		try (final var uncheckedReader = new UncheckedReader(Readers.EMPTY)) {
			assertThatNullPointerException().isThrownBy(() -> uncheckedReader.read((char[]) null));
		}
	}

	@Test
	void testReadBufferOffsetLength() {
		final var buffer = new char[2];
		try (final var uncheckedReader = new UncheckedReader(Readers.EMPTY)) {
			uncheckedReader.read(buffer, 0, 1);
		}
		final var uncheckedReader = new UncheckedReader(EXCEPTION);
		assertThatExceptionOfType(UncheckedIOException.class).isThrownBy(() -> uncheckedReader.read(buffer, 0, 1));
	}

	@Test
	void testReadBufferOffsetLengthInvalid() {
		final var buffer = new char[2];
		try (final var uncheckedReader = new UncheckedReader(Readers.EMPTY)) {
			assertThatNullPointerException().isThrownBy(() -> uncheckedReader.read(null, 0, 2));
			assertThatExceptionOfType(IndexOutOfBoundsException.class).isThrownBy(() -> uncheckedReader.read(buffer, -1, 2));
			assertThatExceptionOfType(IndexOutOfBoundsException.class).isThrownBy(() -> uncheckedReader.read(buffer, 3, 2));
			assertThatExceptionOfType(IndexOutOfBoundsException.class).isThrownBy(() -> uncheckedReader.read(buffer, 0, -1));
			assertThatExceptionOfType(IndexOutOfBoundsException.class).isThrownBy(() -> uncheckedReader.read(buffer, 0, 3));
		}
	}

	@Test
	void testSkip() {
		try (final var uncheckedReader = new UncheckedReader(Readers.EMPTY)) {
			uncheckedReader.skip(1L);
		}
		final var uncheckedReader = new UncheckedReader(EXCEPTION);
		assertThatExceptionOfType(UncheckedIOException.class).isThrownBy(() -> uncheckedReader.skip(1L));
	}

	@Test
	void testReady() {
		try (final var uncheckedReader = new UncheckedReader(Readers.EMPTY)) {
			uncheckedReader.ready();
		}
		final var uncheckedReader = new UncheckedReader(EXCEPTION);
		assertThatExceptionOfType(UncheckedIOException.class).isThrownBy(uncheckedReader::ready);
	}

	@Test
	void testMarkSupportedMarkReset() {
		try (final var uncheckedReader = new UncheckedReader(Readers.buffered(Readers.EMPTY))) {
			assertThat(uncheckedReader.markSupported()).isTrue();
			uncheckedReader.mark(1);
			uncheckedReader.reset();
		}
		final var uncheckedReader = new UncheckedReader(EXCEPTION);
		assertThatExceptionOfType(UncheckedIOException.class).isThrownBy(() -> uncheckedReader.mark(1));
		assertThatExceptionOfType(UncheckedIOException.class).isThrownBy(uncheckedReader::reset);
	}

	@Test
	void testClose() {
		{
			final var uncheckedReader = new UncheckedReader(Readers.EMPTY);
			uncheckedReader.close();
		}
		final var uncheckedReader = new UncheckedReader(EXCEPTION);
		assertThatExceptionOfType(UncheckedIOException.class).isThrownBy(uncheckedReader::close);
	}

	@Test
	void testTransferTo() {
		try (final var uncheckedReader = new UncheckedReader(Readers.EMPTY)) {
			uncheckedReader.transferTo(Writers.EMPTY);
		}
		final var uncheckedReader = new UncheckedReader(EXCEPTION);
		assertThatExceptionOfType(UncheckedIOException.class).isThrownBy(() -> uncheckedReader.transferTo(Writers.EMPTY));
	}

	@Test
	void testTransferToInvalid() {
		try (final var uncheckedReader = new UncheckedReader(Readers.EMPTY)) {
			assertThatNullPointerException().isThrownBy(() -> uncheckedReader.transferTo(null));
		}
	}
}