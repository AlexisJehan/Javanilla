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

import com.github.alexisjehan.javanilla.lang.array.ByteArrays;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UncheckedIOException;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.assertj.core.api.Assertions.assertThatNullPointerException;

/**
 * <p>{@link UncheckedOutputStream} unit tests.</p>
 */
final class UncheckedOutputStreamTest {

	private static final OutputStream EXCEPTION = new OutputStream() {
		@Override
		public void write(final int b) throws IOException {
			throw new IOException();
		}

		@Override
		public void write(final byte[] bytes) throws IOException {
			throw new IOException();
		}

		@Override
		public void write(final byte[] bytes, final int offset, final int length) throws IOException {
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
		assertThatNullPointerException().isThrownBy(() -> new UncheckedOutputStream(null));
	}

	@Test
	void testWriteByte() {
		try (final var uncheckedOutputStream = new UncheckedOutputStream(OutputStreams.EMPTY)) {
			uncheckedOutputStream.write(1);
		}
		final var uncheckedOutputStream = new UncheckedOutputStream(EXCEPTION);
		assertThatExceptionOfType(UncheckedIOException.class).isThrownBy(() -> uncheckedOutputStream.write(1));
	}

	@Test
	void testWriteBytes() {
		try (final var uncheckedOutputStream = new UncheckedOutputStream(OutputStreams.EMPTY)) {
			uncheckedOutputStream.write(ByteArrays.singleton((byte) 1));
		}
		final var uncheckedOutputStream = new UncheckedOutputStream(EXCEPTION);
		assertThatExceptionOfType(UncheckedIOException.class).isThrownBy(() -> uncheckedOutputStream.write(ByteArrays.singleton((byte) 1)));
	}

	@Test
	void testWriteBytesInvalid() {
		try (final var uncheckedOutputStream = new UncheckedOutputStream(OutputStreams.EMPTY)) {
			assertThatNullPointerException().isThrownBy(() -> uncheckedOutputStream.write(null));
		}
	}

	@Test
	void testWriteBytesOffsetLength() {
		try (final var uncheckedOutputStream = new UncheckedOutputStream(OutputStreams.EMPTY)) {
			uncheckedOutputStream.write(ByteArrays.singleton((byte) 1), 0, 1);
		}
		final var uncheckedOutputStream = new UncheckedOutputStream(EXCEPTION);
		assertThatExceptionOfType(UncheckedIOException.class).isThrownBy(() -> uncheckedOutputStream.write(ByteArrays.singleton((byte) 1), 0, 1));
	}

	@Test
	void testWriteBytesOffsetLengthInvalid() {
		final var bytes = ByteArrays.of((byte) 1, (byte) 2, (byte) 3);
		try (final var uncheckedOutputStream = new UncheckedOutputStream(OutputStreams.EMPTY)) {
			assertThatNullPointerException().isThrownBy(() -> uncheckedOutputStream.write(null, 0, 2));
			assertThatExceptionOfType(IndexOutOfBoundsException.class).isThrownBy(() -> uncheckedOutputStream.write(bytes, -1, 3));
			assertThatExceptionOfType(IndexOutOfBoundsException.class).isThrownBy(() -> uncheckedOutputStream.write(bytes, 4, 3));
			assertThatExceptionOfType(IndexOutOfBoundsException.class).isThrownBy(() -> uncheckedOutputStream.write(bytes, 0, -1));
			assertThatExceptionOfType(IndexOutOfBoundsException.class).isThrownBy(() -> uncheckedOutputStream.write(bytes, 0, 4));
		}
	}

	@Test
	void testFlush() {
		try (final var uncheckedOutputStream = new UncheckedOutputStream(OutputStreams.EMPTY)) {
			uncheckedOutputStream.flush();
		}
		final var uncheckedOutputStream = new UncheckedOutputStream(EXCEPTION);
		assertThatExceptionOfType(UncheckedIOException.class).isThrownBy(uncheckedOutputStream::flush);
	}

	@Test
	void testClose() {
		{
			final var uncheckedOutputStream = new UncheckedOutputStream(OutputStreams.EMPTY);
			uncheckedOutputStream.close();
		}
		final var uncheckedOutputStream = new UncheckedOutputStream(EXCEPTION);
		assertThatExceptionOfType(UncheckedIOException.class).isThrownBy(uncheckedOutputStream::close);
	}
}