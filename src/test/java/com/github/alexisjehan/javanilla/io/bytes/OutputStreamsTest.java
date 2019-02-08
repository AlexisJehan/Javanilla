/*
 * MIT License
 *
 * Copyright (c) 2018-2019 Alexis Jehan
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
package com.github.alexisjehan.javanilla.io.bytes;

import com.github.alexisjehan.javanilla.lang.array.ByteArrays;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIOException;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.assertj.core.api.Assertions.assertThatNullPointerException;

/**
 * <p>{@link OutputStreams} unit tests.</p>
 */
final class OutputStreamsTest {

	private static final byte[] BYTES = ByteArrays.of((byte) 1, (byte) 2, (byte) 3);

	@Test
	@SuppressWarnings("deprecation")
	void testEmpty() throws IOException {
		try (final var emptyOutputStream = OutputStreams.EMPTY) {
			emptyOutputStream.write(1);
			emptyOutputStream.write(BYTES);
			assertThatNullPointerException().isThrownBy(() -> emptyOutputStream.write(null));
			emptyOutputStream.write(BYTES, 0, 1);
			assertThatNullPointerException().isThrownBy(() -> emptyOutputStream.write(null, 0, 2));
			assertThatIllegalArgumentException().isThrownBy(() -> emptyOutputStream.write(BYTES, -1, 3));
			assertThatIllegalArgumentException().isThrownBy(() -> emptyOutputStream.write(BYTES, 4, 3));
			assertThatIllegalArgumentException().isThrownBy(() -> emptyOutputStream.write(BYTES, 0, -1));
			assertThatIllegalArgumentException().isThrownBy(() -> emptyOutputStream.write(BYTES, 0, 4));
		}
	}

	@Test
	@SuppressWarnings("deprecation")
	void testNullToEmpty() {
		assertThat(OutputStreams.nullToEmpty(null)).isSameAs(OutputStreams.EMPTY);
		assertThat(OutputStreams.nullToEmpty(OutputStreams.EMPTY)).isSameAs(OutputStreams.EMPTY);
	}

	@Test
	@SuppressWarnings("deprecation")
	void testNullToDefault() {
		assertThat(OutputStreams.nullToDefault(null, OutputStreams.EMPTY)).isSameAs(OutputStreams.EMPTY);
		assertThat(OutputStreams.nullToDefault(OutputStreams.EMPTY, OutputStreams.EMPTY)).isSameAs(OutputStreams.EMPTY);
	}

	@Test
	@SuppressWarnings("deprecation")
	void testNullToDefaultInvalid() {
		assertThatNullPointerException().isThrownBy(() -> OutputStreams.nullToDefault(OutputStreams.EMPTY, null));
	}

	@Test
	@SuppressWarnings("deprecation")
	void testBuffered() throws IOException {
		try (final var outputStream = OutputStreams.EMPTY) {
			assertThat(outputStream).isNotInstanceOf(BufferedOutputStream.class);
			try (final var bufferedOutputStream = OutputStreams.buffered(outputStream)) {
				assertThat(outputStream).isNotSameAs(bufferedOutputStream);
				assertThat(bufferedOutputStream).isInstanceOf(BufferedOutputStream.class);
			}
		}
		try (final var outputStream = new BufferedOutputStream(OutputStreams.EMPTY)) {
			assertThat(outputStream).isInstanceOf(BufferedOutputStream.class);
			try (final var bufferedOutputStream = OutputStreams.buffered(outputStream)) {
				assertThat(outputStream).isSameAs(bufferedOutputStream);
				assertThat(bufferedOutputStream).isInstanceOf(BufferedOutputStream.class);
			}
		}
	}

	@Test
	void testBufferedInvalid() {
		assertThatNullPointerException().isThrownBy(() -> OutputStreams.buffered(null));
	}

	@Test
	void testUncloseable() throws IOException {
		final var outputStream = new OutputStream() {
			@Override
			public void write(final int i) {
				// Do nothing
			}

			@Override
			public void close() throws IOException {
				throw new IOException();
			}
		};
		assertThatIOException().isThrownBy(outputStream::close);
		OutputStreams.uncloseable(outputStream).close();
	}

	@Test
	void testUncloseableInvalid() {
		assertThatNullPointerException().isThrownBy(() -> OutputStreams.uncloseable(null));
	}

	@Test
	@SuppressWarnings("deprecation")
	void testTee() throws IOException {
		assertThat(OutputStreams.tee()).isSameAs(OutputStreams.EMPTY);
		assertThat(OutputStreams.tee(OutputStreams.EMPTY)).isSameAs(OutputStreams.EMPTY);
		try (final var fooOutputStream = new ByteArrayOutputStream()) {
			try (final var barOutputStream = new ByteArrayOutputStream()) {
				try (final var teeOutputStream = OutputStreams.tee(fooOutputStream, barOutputStream)) {
					teeOutputStream.write(1);
					teeOutputStream.write(ByteArrays.EMPTY);
					teeOutputStream.write(BYTES);
					assertThatNullPointerException().isThrownBy(() -> teeOutputStream.write(null));
					teeOutputStream.write(BYTES, 0, 0);
					teeOutputStream.write(BYTES, 0, 1);
					assertThatNullPointerException().isThrownBy(() -> teeOutputStream.write(null, 0, 2));
					assertThatIllegalArgumentException().isThrownBy(() -> teeOutputStream.write(BYTES, -1, 3));
					assertThatIllegalArgumentException().isThrownBy(() -> teeOutputStream.write(BYTES, 4, 3));
					assertThatIllegalArgumentException().isThrownBy(() -> teeOutputStream.write(BYTES, 0, -1));
					assertThatIllegalArgumentException().isThrownBy(() -> teeOutputStream.write(BYTES, 0, 4));
					teeOutputStream.flush();
				}
				assertThat(barOutputStream.toByteArray()).containsExactly((byte) 1, (byte) 1, (byte) 2, (byte) 3, (byte) 1);
			}
			assertThat(fooOutputStream.toByteArray()).containsExactly((byte) 1, (byte) 1, (byte) 2, (byte) 3, (byte) 1);
		}
	}

	@Test
	void testTeeInvalid() {
		assertThatNullPointerException().isThrownBy(() -> OutputStreams.tee((OutputStream[]) null));
		assertThatNullPointerException().isThrownBy(() -> OutputStreams.tee((List<OutputStream>) null));
		assertThatNullPointerException().isThrownBy(() -> OutputStreams.tee((OutputStream) null));
	}

	@Test
	void testToWriter() throws IOException {
		try (final var outputStream = new ByteArrayOutputStream()) {
			try (final var writer = OutputStreams.toWriter(outputStream)) {
				writer.write("foo");
			}
			assertThat(new String(outputStream.toByteArray())).isEqualTo("foo");
		}
		try (final var outputStream = new ByteArrayOutputStream()) {
			try (final var writer = OutputStreams.toWriter(outputStream, StandardCharsets.ISO_8859_1)) {
				writer.write("foo");
			}
			assertThat(new String(outputStream.toByteArray(), StandardCharsets.ISO_8859_1)).isEqualTo("foo");
		}

		// Not the same charset
		try (final var outputStream = new ByteArrayOutputStream()) {
			try (final var writer = OutputStreams.toWriter(outputStream, StandardCharsets.UTF_16)) {
				writer.write("foo");
			}
			assertThat(new String(outputStream.toByteArray(), StandardCharsets.UTF_8)).isNotEqualTo("foo");
		}
	}

	@Test
	@SuppressWarnings("deprecation")
	void testToWriterInvalid() {
		assertThatNullPointerException().isThrownBy(() -> OutputStreams.toWriter(null));
		assertThatNullPointerException().isThrownBy(() -> OutputStreams.toWriter(OutputStreams.EMPTY, null));
	}

	@Test
	void testOf(@TempDir final Path tmpDirectory) throws IOException {
		final var path = tmpDirectory.resolve("testOf");
		try (final var pathOutputStream = OutputStreams.of(path)) {
			pathOutputStream.write(BYTES);
		}
		assertThat(path).hasBinaryContent(BYTES);
	}

	@Test
	void testOfInvalid() {
		assertThatNullPointerException().isThrownBy(() -> OutputStreams.of(null));
	}
}