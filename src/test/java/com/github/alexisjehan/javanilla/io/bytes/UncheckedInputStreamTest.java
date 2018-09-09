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

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UncheckedIOException;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.assertj.core.api.Assertions.assertThatNullPointerException;

/**
 * <p>{@link UncheckedInputStream} unit tests.</p>
 */
final class UncheckedInputStreamTest {

	private static final InputStream EXCEPTION = new InputStream() {
		@Override
		public int read() throws IOException {
			throw new IOException();
		}

		@Override
		public int read(final byte[] buffer) throws IOException {
			throw new IOException();
		}

		@Override
		public int read(final byte[] buffer, final int offset, final int length) throws IOException {
			throw new IOException();
		}

		@Override
		public byte[] readAllBytes() throws IOException {
			throw new IOException();
		}

		@Override
		public int readNBytes(final byte[] buffer, final int offset, final int length) throws IOException {
			throw new IOException();
		}

		@Override
		public long skip(final long n) throws IOException {
			throw new IOException();
		}

		@Override
		public int available() throws IOException {
			throw new IOException();
		}

		@Override
		public void close() throws IOException {
			throw new IOException();
		}

		@Override
		public void reset() throws IOException {
			throw new IOException();
		}

		@Override
		public long transferTo(final OutputStream outputStream) throws IOException {
			throw new IOException();
		}
	};

	@Test
	void testConstructorInvalid() {
		assertThatNullPointerException().isThrownBy(() -> new UncheckedInputStream(null));
	}

	@Test
	void testReadByte() {
		try (final var uncheckedInputStream = new UncheckedInputStream(InputStreams.EMPTY)) {
			uncheckedInputStream.read();
		}
		final var uncheckedInputStream = new UncheckedInputStream(EXCEPTION);
		assertThatExceptionOfType(UncheckedIOException.class).isThrownBy(uncheckedInputStream::read);
	}

	@Test
	void testReadBuffer() {
		final var buffer = new byte[2];
		try (final var uncheckedInputStream = new UncheckedInputStream(InputStreams.EMPTY)) {
			uncheckedInputStream.read(buffer);
		}
		final var uncheckedInputStream = new UncheckedInputStream(EXCEPTION);
		assertThatExceptionOfType(UncheckedIOException.class).isThrownBy(() -> uncheckedInputStream.read(buffer));
	}

	@Test
	void testReadBufferInvalid() {
		try (final var uncheckedInputStream = new UncheckedInputStream(InputStreams.EMPTY)) {
			assertThatNullPointerException().isThrownBy(() -> uncheckedInputStream.read(null));
		}
	}

	@Test
	void testReadBufferOffsetLength() {
		final var buffer = new byte[2];
		try (final var uncheckedInputStream = new UncheckedInputStream(InputStreams.EMPTY)) {
			uncheckedInputStream.read(buffer, 0, 1);
		}
		final var uncheckedInputStream = new UncheckedInputStream(EXCEPTION);
		assertThatExceptionOfType(UncheckedIOException.class).isThrownBy(() -> uncheckedInputStream.read(buffer, 0, 1));
	}

	@Test
	void testReadBufferOffsetLengthInvalid() {
		final var buffer = new byte[2];
		try (final var uncheckedInputStream = new UncheckedInputStream(InputStreams.EMPTY)) {
			assertThatNullPointerException().isThrownBy(() -> uncheckedInputStream.read(null, 0, 2));
			assertThatExceptionOfType(IndexOutOfBoundsException.class).isThrownBy(() -> uncheckedInputStream.read(buffer, -1, 2));
			assertThatExceptionOfType(IndexOutOfBoundsException.class).isThrownBy(() -> uncheckedInputStream.read(buffer, 3, 2));
			assertThatExceptionOfType(IndexOutOfBoundsException.class).isThrownBy(() -> uncheckedInputStream.read(buffer, 0, -1));
			assertThatExceptionOfType(IndexOutOfBoundsException.class).isThrownBy(() -> uncheckedInputStream.read(buffer, 0, 3));
		}
	}

	@Test
	void testReadAllBytes() {
		try (final var uncheckedInputStream = new UncheckedInputStream(InputStreams.EMPTY)) {
			uncheckedInputStream.readAllBytes();
		}
		final var uncheckedInputStream = new UncheckedInputStream(EXCEPTION);
		assertThatExceptionOfType(UncheckedIOException.class).isThrownBy(uncheckedInputStream::readAllBytes);
	}

	@Test
	void testReadNBytes() {
		final var buffer = new byte[2];
		try (final var uncheckedInputStream = new UncheckedInputStream(InputStreams.EMPTY)) {
			uncheckedInputStream.readNBytes(buffer, 0, 1);
		}
		final var uncheckedInputStream = new UncheckedInputStream(EXCEPTION);
		assertThatExceptionOfType(UncheckedIOException.class).isThrownBy(() -> uncheckedInputStream.readNBytes(buffer, 0, 1));
	}

	@Test
	void testReadNBytesInvalid() {
		final var buffer = new byte[2];
		try (final var uncheckedInputStream = new UncheckedInputStream(InputStreams.EMPTY)) {
			assertThatNullPointerException().isThrownBy(() -> uncheckedInputStream.readNBytes(null, 0, 2));
			assertThatExceptionOfType(IndexOutOfBoundsException.class).isThrownBy(() -> uncheckedInputStream.readNBytes(buffer, -1, 2));
			assertThatExceptionOfType(IndexOutOfBoundsException.class).isThrownBy(() -> uncheckedInputStream.readNBytes(buffer, 3, 2));
			assertThatExceptionOfType(IndexOutOfBoundsException.class).isThrownBy(() -> uncheckedInputStream.readNBytes(buffer, 0, -1));
			assertThatExceptionOfType(IndexOutOfBoundsException.class).isThrownBy(() -> uncheckedInputStream.readNBytes(buffer, 0, 3));
		}
	}

	@Test
	void testSkip() {
		try (final var uncheckedInputStream = new UncheckedInputStream(InputStreams.EMPTY)) {
			uncheckedInputStream.skip(1L);
		}
		final var uncheckedInputStream = new UncheckedInputStream(EXCEPTION);
		assertThatExceptionOfType(UncheckedIOException.class).isThrownBy(() -> uncheckedInputStream.skip(1L));
	}

	@Test
	void testAvailable() {
		try (final var uncheckedInputStream = new UncheckedInputStream(InputStreams.EMPTY)) {
			uncheckedInputStream.available();
		}
		final var uncheckedInputStream = new UncheckedInputStream(EXCEPTION);
		assertThatExceptionOfType(UncheckedIOException.class).isThrownBy(uncheckedInputStream::available);
	}

	@Test
	void testClose() {
		{
			final var uncheckedInputStream = new UncheckedInputStream(InputStreams.EMPTY);
			uncheckedInputStream.close();
		}
		final var uncheckedInputStream = new UncheckedInputStream(EXCEPTION);
		assertThatExceptionOfType(UncheckedIOException.class).isThrownBy(uncheckedInputStream::close);
	}

	@Test
	void testMarkReset() {
		try (final var uncheckedInputStream = new UncheckedInputStream(InputStreams.buffered(InputStreams.EMPTY))) {
			uncheckedInputStream.mark(1);
			uncheckedInputStream.reset();
		}
		final var uncheckedInputStream = new UncheckedInputStream(EXCEPTION);
		assertThatExceptionOfType(UncheckedIOException.class).isThrownBy(uncheckedInputStream::reset);
	}

	@Test
	void testTransferTo() {
		try (final var uncheckedInputStream = new UncheckedInputStream(InputStreams.EMPTY)) {
			uncheckedInputStream.transferTo(OutputStreams.EMPTY);
		}
		final var uncheckedInputStream = new UncheckedInputStream(EXCEPTION);
		assertThatExceptionOfType(UncheckedIOException.class).isThrownBy(() -> uncheckedInputStream.transferTo(OutputStreams.EMPTY));
	}

	@Test
	void testTransferToInvalid() {
		try (final var uncheckedInputStream = new UncheckedInputStream(InputStreams.EMPTY)) {
			assertThatNullPointerException().isThrownBy(() -> uncheckedInputStream.transferTo(null));
		}
	}
}