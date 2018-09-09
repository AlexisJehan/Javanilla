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

import com.github.alexisjehan.javanilla.io.chars.Readers;
import com.github.alexisjehan.javanilla.lang.Strings;
import com.github.alexisjehan.javanilla.lang.array.ByteArrays;
import org.junit.jupiter.api.Test;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

/**
 * <p>{@link InputStreams} unit tests.</p>
 */
final class InputStreamsTest {

	@Test
	void testEmpty() throws IOException {
		final var buffer = new byte[2];
		try (final var emptyInputStream = InputStreams.EMPTY) {
			assertThat(emptyInputStream.read()).isEqualTo(-1);
			assertThat(emptyInputStream.read(ByteArrays.EMPTY)).isEqualTo(0);
			assertThat(emptyInputStream.read(buffer)).isEqualTo(-1);
			assertThatNullPointerException().isThrownBy(() -> emptyInputStream.read(null));
			assertThat(emptyInputStream.read(buffer, 0, 0)).isEqualTo(0);
			assertThat(emptyInputStream.read(buffer, 0, 1)).isEqualTo(-1);
			assertThatNullPointerException().isThrownBy(() -> emptyInputStream.read(null, 0, 2));
			assertThatExceptionOfType(IndexOutOfBoundsException.class).isThrownBy(() -> emptyInputStream.read(buffer, -1, 2));
			assertThatExceptionOfType(IndexOutOfBoundsException.class).isThrownBy(() -> emptyInputStream.read(buffer, 3, 2));
			assertThatExceptionOfType(IndexOutOfBoundsException.class).isThrownBy(() -> emptyInputStream.read(buffer, 0, -1));
			assertThatExceptionOfType(IndexOutOfBoundsException.class).isThrownBy(() -> emptyInputStream.read(buffer, 0, 3));
			assertThat(emptyInputStream.readAllBytes()).isEmpty();
			assertThat(emptyInputStream.readNBytes(buffer, 0, 1)).isEqualTo(0);
			assertThatNullPointerException().isThrownBy(() -> emptyInputStream.readNBytes(null, 0, 2));
			assertThatExceptionOfType(IndexOutOfBoundsException.class).isThrownBy(() -> emptyInputStream.readNBytes(buffer, -1, 2));
			assertThatExceptionOfType(IndexOutOfBoundsException.class).isThrownBy(() -> emptyInputStream.readNBytes(buffer, 3, 2));
			assertThatExceptionOfType(IndexOutOfBoundsException.class).isThrownBy(() -> emptyInputStream.readNBytes(buffer, 0, -1));
			assertThatExceptionOfType(IndexOutOfBoundsException.class).isThrownBy(() -> emptyInputStream.readNBytes(buffer, 0, 3));
			assertThat(emptyInputStream.skip(1)).isEqualTo(0);
			assertThat(emptyInputStream.transferTo(OutputStreams.EMPTY)).isEqualTo(0L);
			assertThatNullPointerException().isThrownBy(() -> emptyInputStream.transferTo(null));
		}
	}

	@Test
	void testNullToEmpty() {
		assertThat(InputStreams.nullToEmpty(null)).hasSameContentAs(InputStreams.EMPTY);
		assertThat(InputStreams.nullToEmpty(InputStreams.EMPTY)).hasSameContentAs(InputStreams.EMPTY);
		assertThat(InputStreams.nullToEmpty(InputStreams.singleton((byte) 1))).hasSameContentAs(InputStreams.singleton((byte) 1));
	}

	@Test
	void testNullToDefault() {
		assertThat(InputStreams.nullToDefault(null, InputStreams.singleton((byte) 0))).hasSameContentAs(InputStreams.singleton((byte) 0));
		assertThat(InputStreams.nullToDefault(InputStreams.EMPTY, InputStreams.singleton((byte) 0))).hasSameContentAs(InputStreams.EMPTY);
		assertThat(InputStreams.nullToDefault(InputStreams.singleton((byte) 1), InputStreams.singleton((byte) 0))).hasSameContentAs(InputStreams.singleton((byte) 1));
	}

	@Test
	void testNullToDefaultInvalid() {
		assertThatNullPointerException().isThrownBy(() -> InputStreams.nullToDefault(InputStreams.singleton((byte) 1), null));
	}

	@Test
	void testBuffered() throws IOException {
		try (final var inputStream = InputStreams.EMPTY) {
			assertThat(inputStream).isNotInstanceOf(BufferedInputStream.class);
			try (final var bufferedInputStream = InputStreams.buffered(inputStream)) {
				assertThat(inputStream).isNotSameAs(bufferedInputStream);
				assertThat(bufferedInputStream).isInstanceOf(BufferedInputStream.class);
			}
		}
		try (final var inputStream = new BufferedInputStream(InputStreams.EMPTY)) {
			assertThat(inputStream).isInstanceOf(BufferedInputStream.class);
			try (final var bufferedInputStream = InputStreams.buffered(inputStream)) {
				assertThat(inputStream).isSameAs(bufferedInputStream);
				assertThat(bufferedInputStream).isInstanceOf(BufferedInputStream.class);
			}
		}
	}

	@Test
	void testBufferedInvalid() {
		assertThatNullPointerException().isThrownBy(() -> InputStreams.buffered(null));
	}

	@Test
	void testMarkSupported() throws IOException {
		try (final var inputStream = InputStreams.EMPTY) {
			assertThat(inputStream.markSupported()).isFalse();
			try (final var markSupportedInputStream = InputStreams.markSupported(inputStream)) {
				assertThat(inputStream).isNotSameAs(markSupportedInputStream);
				assertThat(markSupportedInputStream.markSupported()).isTrue();
			}
		}
		try (final var inputStream = new BufferedInputStream(InputStreams.EMPTY)) {
			assertThat(inputStream.markSupported()).isTrue();
			try (final var markSupportedInputStream = InputStreams.markSupported(inputStream)) {
				assertThat(inputStream).isSameAs(markSupportedInputStream);
				assertThat(markSupportedInputStream.markSupported()).isTrue();
			}
		}
	}

	@Test
	void testMarkSupportedInvalid() {
		assertThatNullPointerException().isThrownBy(() -> InputStreams.markSupported(null));
	}

	@Test
	void testUncloseable() throws IOException {
		final var inputStream = new InputStream() {
			@Override
			public int read() {
				return -1;
			}

			@Override
			public void close() throws IOException {
				throw new IOException();
			}
		};
		assertThatIOException().isThrownBy(inputStream::close);
		{
			InputStreams.uncloseable(inputStream).close();
		}
	}

	@Test
	void testUncloseableInvalid() {
		assertThatNullPointerException().isThrownBy(() -> InputStreams.uncloseable(null));
	}

	@Test
	void testLength() throws IOException {
		assertThat(InputStreams.length(InputStreams.EMPTY)).isEqualTo(0L);
		assertThat(InputStreams.length(InputStreams.of((byte) 1, (byte) 2, (byte) 3))).isEqualTo(3L);
	}

	@Test
	void testLengthInvalid() {
		assertThatNullPointerException().isThrownBy(() -> InputStreams.length(null));
	}

	@Test
	void testConcat() {
		assertThat(InputStreams.concat()).hasSameContentAs(InputStreams.EMPTY);
		assertThat(InputStreams.concat(InputStreams.singleton((byte) 1))).hasSameContentAs(InputStreams.singleton((byte) 1));
		assertThat(InputStreams.concat(InputStreams.singleton((byte) 1), InputStreams.singleton((byte) 2))).hasSameContentAs(InputStreams.of((byte) 1, (byte) 2));
	}

	@Test
	void testConcatInvalid() {
		assertThatNullPointerException().isThrownBy(() -> InputStreams.concat((InputStream[]) null));
		assertThatNullPointerException().isThrownBy(() -> InputStreams.concat((List<InputStream>) null));
		assertThatNullPointerException().isThrownBy(() -> InputStreams.concat((InputStream) null));
	}

	@Test
	void testJoin() {
		assertThat(InputStreams.join(ByteArrays.EMPTY, InputStreams.singleton((byte) 1), InputStreams.singleton((byte) 2))).hasSameContentAs(InputStreams.concat(InputStreams.singleton((byte) 1), InputStreams.singleton((byte) 2)));
		assertThat(InputStreams.join(ByteArrays.singleton((byte) 0))).hasSameContentAs(InputStreams.EMPTY);
		assertThat(InputStreams.join(ByteArrays.singleton((byte) 0), InputStreams.singleton((byte) 1))).hasSameContentAs(InputStreams.singleton((byte) 1));
		assertThat(InputStreams.join(ByteArrays.singleton((byte) 0), InputStreams.singleton((byte) 1), InputStreams.singleton((byte) 2))).hasSameContentAs(InputStreams.of((byte) 1, (byte) 0, (byte) 2));
	}

	@Test
	void testJoinInvalid() {
		assertThatNullPointerException().isThrownBy(() -> InputStreams.join(null, InputStreams.singleton((byte) 1)));
		assertThatNullPointerException().isThrownBy(() -> InputStreams.join(ByteArrays.singleton((byte) 0), (InputStream[]) null));
		assertThatNullPointerException().isThrownBy(() -> InputStreams.join(ByteArrays.singleton((byte) 0), (List<InputStream>) null));
		assertThatNullPointerException().isThrownBy(() -> InputStreams.join(ByteArrays.singleton((byte) 0), (InputStream) null));
	}

	@Test
	void testSingleton() throws IOException {
		assertThat(InputStreams.toBytes(InputStreams.singleton((byte) 1))).containsExactly((byte) 1);
	}

	@Test
	void testOfBytesAndToBytes() throws IOException {
		assertThat(InputStreams.toBytes(InputStreams.of())).isEmpty();
		assertThat(InputStreams.toBytes(InputStreams.of((byte) 1, (byte) 2, (byte) 3))).containsExactly((byte) 1, (byte) 2, (byte) 3);
	}

	@Test
	void testOfBytesInvalid() {
		assertThatNullPointerException().isThrownBy(() -> InputStreams.of((byte[]) null));
	}

	@Test
	void testToBytesInvalid() {
		assertThatNullPointerException().isThrownBy(() -> InputStreams.toBytes(null));
	}

	@Test
	void testOfStringAndToString() throws IOException {
		assertThat(InputStreams.toString(InputStreams.of(Strings.EMPTY))).isEmpty();
		assertThat(InputStreams.toString(InputStreams.of(new String("foo".getBytes(), StandardCharsets.ISO_8859_1), StandardCharsets.ISO_8859_1))).isEqualTo(new String("foo".getBytes(), StandardCharsets.ISO_8859_1));

		// Not the same charset
		assertThat(InputStreams.toString(InputStreams.of(new String("foo".getBytes(), StandardCharsets.UTF_8), StandardCharsets.UTF_16))).isNotEqualTo(new String("foo".getBytes(), StandardCharsets.UTF_8));
	}

	@Test
	void testOfStringInvalid() {
		assertThatNullPointerException().isThrownBy(() -> InputStreams.of((String) null));
		assertThatNullPointerException().isThrownBy(() -> InputStreams.of("foo", null));
	}

	@Test
	void testToStringInvalid() {
		assertThatNullPointerException().isThrownBy(() -> InputStreams.toString(null));
		assertThatNullPointerException().isThrownBy(() -> InputStreams.toString(InputStreams.singleton((byte) 1), null));
	}

	@Test
	void testToReader() throws IOException {
		assertThat(Readers.toString(InputStreams.toReader(InputStreams.EMPTY))).isEmpty();
		assertThat(Readers.toString(InputStreams.toReader(InputStreams.of(new String("foo".getBytes(), StandardCharsets.ISO_8859_1), StandardCharsets.ISO_8859_1)))).isEqualTo(new String("foo".getBytes(), StandardCharsets.ISO_8859_1));

		// Not the same charset
		assertThat(Readers.toString(InputStreams.toReader(InputStreams.of(new String("foo".getBytes(), StandardCharsets.UTF_8), StandardCharsets.UTF_16)))).isNotEqualTo(new String("foo".getBytes(), StandardCharsets.ISO_8859_1));
	}

	@Test
	void testToReaderInvalid() {
		assertThatNullPointerException().isThrownBy(() -> InputStreams.toReader(null));
		assertThatNullPointerException().isThrownBy(() -> InputStreams.toReader(InputStreams.singleton((byte) 1), null));
	}

	@Test
	void testOfPath() throws IOException {
		final var path = File.createTempFile(getClass().getName() + ".testOfPath_", ".txt").toPath();
		Files.write(path, ByteArrays.of((byte) 1, (byte) 2, (byte) 3));
		try (final var pathInputStream = InputStreams.of(path)) {
			assertThat(pathInputStream).hasSameContentAs(InputStreams.of((byte) 1, (byte) 2, (byte) 3));
		}
		Files.delete(path);
	}

	@Test
	void testOfPathInvalid() {
		assertThatNullPointerException().isThrownBy(() -> InputStreams.of((Path) null));
	}
}