/*
 * MIT License
 *
 * Copyright (c) 2018-2026 Alexis Jehan
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
package com.github.alexisjehan.javanilla.io;

import com.github.alexisjehan.javanilla.lang.Strings;
import com.github.alexisjehan.javanilla.lang.array.ByteArrays;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIOException;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.assertj.core.api.Assertions.assertThatNullPointerException;

final class InputStreamsTest {

	private static final byte[] BYTES = ByteArrays.of((byte) 1, (byte) 2, (byte) 3);

	@Test
	void testEmpty() throws IOException {
		final var buffer = new byte[2];
		try (var emptyInputStream = InputStreams.EMPTY) {
			assertThat(emptyInputStream.read()).isEqualTo(-1);
			assertThat(emptyInputStream.read(ByteArrays.EMPTY)).isZero();
			assertThat(emptyInputStream.read(buffer)).isEqualTo(-1);
			assertThatNullPointerException().isThrownBy(() -> emptyInputStream.read(null));
			assertThat(emptyInputStream.read(buffer, 0, 0)).isZero();
			assertThat(emptyInputStream.read(buffer, 0, 1)).isEqualTo(-1);
			assertThatNullPointerException().isThrownBy(() -> emptyInputStream.read(null, 0, 2));
			assertThatIllegalArgumentException().isThrownBy(() -> emptyInputStream.read(buffer, -1, 2));
			assertThatIllegalArgumentException().isThrownBy(() -> emptyInputStream.read(buffer, 3, 2));
			assertThatIllegalArgumentException().isThrownBy(() -> emptyInputStream.read(buffer, 0, -1));
			assertThatIllegalArgumentException().isThrownBy(() -> emptyInputStream.read(buffer, 0, 3));
			assertThat(emptyInputStream.readAllBytes()).isEmpty();
			assertThat(emptyInputStream.readNBytes(buffer, 0, 1)).isZero();
			assertThatNullPointerException().isThrownBy(() -> emptyInputStream.readNBytes(null, 0, 2));
			assertThatIllegalArgumentException().isThrownBy(() -> emptyInputStream.readNBytes(buffer, -1, 2));
			assertThatIllegalArgumentException().isThrownBy(() -> emptyInputStream.readNBytes(buffer, 3, 2));
			assertThatIllegalArgumentException().isThrownBy(() -> emptyInputStream.readNBytes(buffer, 0, -1));
			assertThatIllegalArgumentException().isThrownBy(() -> emptyInputStream.readNBytes(buffer, 0, 3));
			assertThat(emptyInputStream.skip(1)).isZero();
			assertThat(emptyInputStream.transferTo(OutputStreams.EMPTY)).isZero();
			assertThatNullPointerException().isThrownBy(() -> emptyInputStream.transferTo(null));
		}
	}

	@Test
	void testNullToEmpty() {
		assertThat(InputStreams.nullToEmpty(null)).isEmpty();
		assertThat(InputStreams.nullToEmpty(InputStreams.EMPTY)).isEmpty();
		assertThat(InputStreams.nullToEmpty(InputStreams.of(BYTES))).hasBinaryContent(BYTES);
	}

	@Test
	void testNullToDefault() {
		assertThat(InputStreams.nullToDefault(null, InputStreams.singleton((byte) 0))).hasBinaryContent(ByteArrays.singleton((byte) 0));
		assertThat(InputStreams.nullToDefault(InputStreams.EMPTY, InputStreams.singleton((byte) 0))).isEmpty();
		assertThat(InputStreams.nullToDefault(InputStreams.of(BYTES), InputStreams.singleton((byte) 0))).hasBinaryContent(BYTES);
	}

	@Test
	void testNullToDefaultInvalid() {
		assertThatNullPointerException().isThrownBy(() -> InputStreams.nullToDefault(InputStreams.of(BYTES), null));
	}

	@Test
	void testBuffered() throws IOException {
		try (var inputStream = InputStreams.EMPTY) {
			assertThat(inputStream).isNotInstanceOf(BufferedInputStream.class);
			try (var bufferedInputStream = InputStreams.buffered(inputStream)) {
				assertThat(inputStream).isNotSameAs(bufferedInputStream);
				assertThat(bufferedInputStream).isInstanceOf(BufferedInputStream.class);
			}
		}
		try (var inputStream = new BufferedInputStream(InputStreams.EMPTY)) {
			assertThat(inputStream).isInstanceOf(BufferedInputStream.class);
			try (var bufferedInputStream = InputStreams.buffered(inputStream)) {
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
		try (var inputStream = InputStreams.EMPTY) {
			assertThat(inputStream.markSupported()).isFalse();
			try (var markSupportedInputStream = InputStreams.markSupported(inputStream)) {
				assertThat(inputStream).isNotSameAs(markSupportedInputStream);
				assertThat(markSupportedInputStream.markSupported()).isTrue();
			}
		}
		try (var inputStream = new BufferedInputStream(InputStreams.EMPTY)) {
			assertThat(inputStream.markSupported()).isTrue();
			try (var markSupportedInputStream = InputStreams.markSupported(inputStream)) {
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
		InputStreams.uncloseable(inputStream).close();
	}

	@Test
	void testUncloseableInvalid() {
		assertThatNullPointerException().isThrownBy(() -> InputStreams.uncloseable(null));
	}

	@Test
	void testConcat() {
		assertThat(InputStreams.concat()).isEmpty();
		assertThat(InputStreams.concat(InputStreams.singleton(BYTES[0]))).hasBinaryContent(ByteArrays.singleton(BYTES[0]));
		assertThat(InputStreams.concat(InputStreams.singleton(BYTES[0]), InputStreams.singleton(BYTES[1]), InputStreams.singleton(BYTES[2]))).hasBinaryContent(BYTES);
		assertThat(InputStreams.concat(List.of(InputStreams.singleton(BYTES[0]), InputStreams.singleton(BYTES[1]), InputStreams.singleton(BYTES[2])))).hasBinaryContent(BYTES);
	}

	@Test
	void testConcatInvalid() {
		assertThatNullPointerException().isThrownBy(() -> InputStreams.concat((InputStream[]) null));
		assertThatNullPointerException().isThrownBy(() -> InputStreams.concat((InputStream) null));
		assertThatNullPointerException().isThrownBy(() -> InputStreams.concat((List<InputStream>) null));
		assertThatNullPointerException().isThrownBy(() -> InputStreams.concat(Collections.singletonList(null)));
	}

	@Test
	void testJoin() {
		assertThat(InputStreams.join(ByteArrays.EMPTY, InputStreams.singleton(BYTES[0]), InputStreams.singleton(BYTES[1]), InputStreams.singleton(BYTES[2]))).hasBinaryContent(BYTES);
		assertThat(InputStreams.join(ByteArrays.singleton((byte) 0))).isEmpty();
		assertThat(InputStreams.join(ByteArrays.singleton((byte) 0), InputStreams.singleton(BYTES[0]))).hasBinaryContent(ByteArrays.singleton(BYTES[0]));
		assertThat(InputStreams.join(ByteArrays.singleton((byte) 0), InputStreams.singleton(BYTES[0]), InputStreams.singleton(BYTES[1]), InputStreams.singleton(BYTES[2]))).hasBinaryContent(ByteArrays.of(BYTES[0], (byte) 0, BYTES[1], (byte) 0, BYTES[2]));
		assertThat(InputStreams.join(ByteArrays.singleton((byte) 0), List.of(InputStreams.singleton(BYTES[0]), InputStreams.singleton(BYTES[1]), InputStreams.singleton(BYTES[2])))).hasBinaryContent(ByteArrays.of(BYTES[0], (byte) 0, BYTES[1], (byte) 0, BYTES[2]));
	}

	@Test
	void testJoinInvalid() {
		assertThatNullPointerException().isThrownBy(() -> InputStreams.join(null, InputStreams.of(BYTES)));
		assertThatNullPointerException().isThrownBy(() -> InputStreams.join(ByteArrays.singleton((byte) 0), (InputStream[]) null));
		assertThatNullPointerException().isThrownBy(() -> InputStreams.join(ByteArrays.singleton((byte) 0), (InputStream) null));
		assertThatNullPointerException().isThrownBy(() -> InputStreams.join(ByteArrays.singleton((byte) 0), (List<InputStream>) null));
		assertThatNullPointerException().isThrownBy(() -> InputStreams.join(ByteArrays.singleton((byte) 0), Collections.singletonList(null)));
	}

	@Test
	void testLength() throws IOException {
		assertThat(InputStreams.length(InputStreams.EMPTY)).isZero();
		assertThat(InputStreams.length(InputStreams.of(BYTES))).isEqualTo(BYTES.length);
	}

	@Test
	void testLengthInvalid() {
		assertThatNullPointerException().isThrownBy(() -> InputStreams.length(null));
	}

	@Test
	void testSingleton() throws IOException {
		assertThat(InputStreams.toBytes(InputStreams.singleton(BYTES[0]))).containsExactly(BYTES[0]);
	}

	@Test
	void testOfBytesAndToBytes() throws IOException {
		assertThat(InputStreams.toBytes(InputStreams.of())).isEmpty();
		assertThat(InputStreams.toBytes(InputStreams.of(BYTES))).containsExactly(BYTES);
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
		assertThat(InputStreams.toString(InputStreams.of(new String(BYTES)))).isEqualTo(new String(BYTES));
		assertThat(InputStreams.toString(InputStreams.of(new String(BYTES, StandardCharsets.ISO_8859_1), StandardCharsets.ISO_8859_1))).isEqualTo(new String(BYTES, StandardCharsets.ISO_8859_1));

		// Not the same charset
		assertThat(InputStreams.toString(InputStreams.of(new String(BYTES, StandardCharsets.ISO_8859_1), StandardCharsets.UTF_16))).isNotEqualTo(new String(BYTES, StandardCharsets.ISO_8859_1));
	}

	@Test
	void testOfStringInvalid() {
		assertThatNullPointerException().isThrownBy(() -> InputStreams.of((String) null));
		assertThatNullPointerException().isThrownBy(() -> InputStreams.of(new String(BYTES), null));
	}

	@Test
	void testToStringInvalid() {
		assertThatNullPointerException().isThrownBy(() -> InputStreams.toString(null));
		assertThatNullPointerException().isThrownBy(() -> InputStreams.toString(InputStreams.of(BYTES), null));
	}

	@Test
	void testOfPath(@TempDir final Path tmpDirectory) throws IOException {
		final var tmpFile = tmpDirectory.resolve("testOfPath");
		Files.write(tmpFile, ByteArrays.of(BYTES));
		try (var inputStream = InputStreams.of(tmpFile)) {
			assertThat(inputStream).hasBinaryContent(BYTES);
		}
	}

	@Test
	void testOfPathInvalid() {
		assertThatNullPointerException().isThrownBy(() -> InputStreams.of((Path) null));
	}

	@Test
	void testToReader() throws IOException {
		assertThat(Readers.toString(InputStreams.toReader(InputStreams.EMPTY))).isEmpty();
		assertThat(Readers.toString(InputStreams.toReader(InputStreams.of(new String(BYTES, StandardCharsets.ISO_8859_1), StandardCharsets.ISO_8859_1)))).isEqualTo(new String(BYTES, StandardCharsets.ISO_8859_1));

		// Not the same charset
		assertThat(Readers.toString(InputStreams.toReader(InputStreams.of(new String(BYTES, StandardCharsets.ISO_8859_1), StandardCharsets.UTF_16)))).isNotEqualTo(new String(BYTES, StandardCharsets.ISO_8859_1));
	}

	@Test
	void testToReaderInvalid() {
		assertThatNullPointerException().isThrownBy(() -> InputStreams.toReader(null));
		assertThatNullPointerException().isThrownBy(() -> InputStreams.toReader(InputStreams.of(BYTES), null));
	}
}