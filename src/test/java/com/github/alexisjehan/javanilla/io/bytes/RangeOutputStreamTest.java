/*
 * MIT License
 *
 * Copyright (c) 2018-2023 Alexis Jehan
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

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.assertj.core.api.Assertions.assertThatNullPointerException;

@SuppressWarnings("deprecation")
final class RangeOutputStreamTest {

	private static final byte[] BYTES = ByteArrays.of((byte) 1, (byte) 2, (byte) 3);

	@Test
	void testConstructorInvalid() {
		assertThatNullPointerException().isThrownBy(() -> new RangeOutputStream(null, 0L, 0L));
		assertThatIllegalArgumentException().isThrownBy(() -> new RangeOutputStream(OutputStreams.EMPTY, -1L, 0L));
		assertThatIllegalArgumentException().isThrownBy(() -> new RangeOutputStream(OutputStreams.EMPTY, 1L, 0L));
	}

	@Test
	void testWriteByte() throws IOException {
		try (final var outputStream = new ByteArrayOutputStream()) {
			try (final var rangeOutputStream = new RangeOutputStream(outputStream, 0L, 0L)) {
				assertThat(rangeOutputStream.getFromIndex()).isZero();
				assertThat(rangeOutputStream.getToIndex()).isZero();
				rangeOutputStream.write(BYTES[0]);
				rangeOutputStream.write(BYTES[1]);
				rangeOutputStream.write(BYTES[2]);
			}
			assertThat(outputStream.toByteArray()).containsExactly(BYTES[0]);
		}
		try (final var outputStream = new ByteArrayOutputStream()) {
			try (final var rangeOutputStream = new RangeOutputStream(outputStream, 1L, 1L)) {
				assertThat(rangeOutputStream.getFromIndex()).isEqualTo(1L);
				assertThat(rangeOutputStream.getToIndex()).isEqualTo(1L);
				rangeOutputStream.write(BYTES[0]);
				rangeOutputStream.write(BYTES[1]);
				rangeOutputStream.write(BYTES[2]);
			}
			assertThat(outputStream.toByteArray()).containsExactly(BYTES[1]);
		}
		try (final var outputStream = new ByteArrayOutputStream()) {
			try (final var rangeOutputStream = new RangeOutputStream(outputStream, 0L, 10L)) {
				assertThat(rangeOutputStream.getFromIndex()).isZero();
				assertThat(rangeOutputStream.getToIndex()).isEqualTo(10L);
				rangeOutputStream.write(BYTES[0]);
				rangeOutputStream.write(BYTES[1]);
				rangeOutputStream.write(BYTES[2]);
			}
			assertThat(outputStream.toByteArray()).containsExactly(BYTES);
		}
		try (final var outputStream = new ByteArrayOutputStream()) {
			try (final var rangeOutputStream = new RangeOutputStream(outputStream, 10L, 10L)) {
				assertThat(rangeOutputStream.getFromIndex()).isEqualTo(10L);
				assertThat(rangeOutputStream.getToIndex()).isEqualTo(10L);
				rangeOutputStream.write(BYTES[0]);
				rangeOutputStream.write(BYTES[1]);
				rangeOutputStream.write(BYTES[2]);
			}
			assertThat(outputStream.toByteArray()).isEmpty();
		}
	}

	@Test
	void testWriteBytes() throws IOException {
		try (final var outputStream = new ByteArrayOutputStream()) {
			try (final var rangeOutputStream = new RangeOutputStream(outputStream, 0L, 0L)) {
				assertThat(rangeOutputStream.getFromIndex()).isZero();
				assertThat(rangeOutputStream.getToIndex()).isZero();
				rangeOutputStream.write(BYTES, 0, 0);
				rangeOutputStream.write(BYTES, 0, 2);
				rangeOutputStream.write(BYTES, 2, 1);
			}
			assertThat(outputStream.toByteArray()).containsExactly(BYTES[0]);
		}
		try (final var outputStream = new ByteArrayOutputStream()) {
			try (final var rangeOutputStream = new RangeOutputStream(outputStream, 1L, 1L)) {
				assertThat(rangeOutputStream.getFromIndex()).isEqualTo(1L);
				assertThat(rangeOutputStream.getToIndex()).isEqualTo(1L);
				rangeOutputStream.write(BYTES, 0, 0);
				rangeOutputStream.write(BYTES, 0, 2);
				rangeOutputStream.write(BYTES, 2, 1);
			}
			assertThat(outputStream.toByteArray()).containsExactly(BYTES[1]);
		}
		try (final var outputStream = new ByteArrayOutputStream()) {
			try (final var rangeOutputStream = new RangeOutputStream(outputStream, 0L, 10L)) {
				assertThat(rangeOutputStream.getFromIndex()).isZero();
				assertThat(rangeOutputStream.getToIndex()).isEqualTo(10L);
				rangeOutputStream.write(BYTES, 0, 0);
				rangeOutputStream.write(BYTES, 0, 2);
				rangeOutputStream.write(BYTES, 2, 1);
			}
			assertThat(outputStream.toByteArray()).containsExactly(BYTES);
		}
		try (final var outputStream = new ByteArrayOutputStream()) {
			try (final var rangeOutputStream = new RangeOutputStream(outputStream, 10L, 10L)) {
				assertThat(rangeOutputStream.getFromIndex()).isEqualTo(10L);
				assertThat(rangeOutputStream.getToIndex()).isEqualTo(10L);
				rangeOutputStream.write(BYTES, 0, 0);
				rangeOutputStream.write(BYTES, 0, 2);
				rangeOutputStream.write(BYTES, 2, 1);
			}
			assertThat(outputStream.toByteArray()).isEmpty();
		}
	}

	@Test
	void testWriteBytesInvalid() throws IOException {
		try (final var rangeOutputStream = new RangeOutputStream(OutputStreams.EMPTY, 0L, 0L)) {
			assertThatNullPointerException().isThrownBy(() -> rangeOutputStream.write(null, 0, 2));
			assertThatIllegalArgumentException().isThrownBy(() -> rangeOutputStream.write(BYTES, -1, 3));
			assertThatIllegalArgumentException().isThrownBy(() -> rangeOutputStream.write(BYTES, 4, 3));
			assertThatIllegalArgumentException().isThrownBy(() -> rangeOutputStream.write(BYTES, 0, -1));
			assertThatIllegalArgumentException().isThrownBy(() -> rangeOutputStream.write(BYTES, 0, 4));
		}
	}
}