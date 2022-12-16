/*
 * MIT License
 *
 * Copyright (c) 2018-2022 Alexis Jehan
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

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.assertj.core.api.Assertions.assertThatNullPointerException;

final class RangeInputStreamTest {

	private static final byte[] BYTES = ByteArrays.of((byte) 1, (byte) 2, (byte) 3);

	@Test
	void testConstructorInvalid() {
		assertThatNullPointerException().isThrownBy(() -> new RangeInputStream(null, 0L, 0L));
		assertThatIllegalArgumentException().isThrownBy(() -> new RangeInputStream(InputStreams.of(BYTES), -1L, 0L));
		assertThatIllegalArgumentException().isThrownBy(() -> new RangeInputStream(InputStreams.of(BYTES), 1L, 0L));
	}

	@Test
	void testReadByte() throws IOException {
		try (final var rangeInputStream = new RangeInputStream(InputStreams.of(BYTES), 0L, 0L)) {
			assertThat(rangeInputStream.getFromIndex()).isZero();
			assertThat(rangeInputStream.getToIndex()).isZero();
			assertThat(rangeInputStream.read()).isEqualTo(BYTES[0]);
			assertThat(rangeInputStream.read()).isEqualTo(-1);
		}
		try (final var rangeInputStream = new RangeInputStream(InputStreams.of(BYTES), 1L, 1L)) {
			assertThat(rangeInputStream.getFromIndex()).isEqualTo(1L);
			assertThat(rangeInputStream.getToIndex()).isEqualTo(1L);
			assertThat(rangeInputStream.read()).isEqualTo(BYTES[1]);
			assertThat(rangeInputStream.read()).isEqualTo(-1);
		}
		try (final var rangeInputStream = new RangeInputStream(InputStreams.of(BYTES), 0L, 10L)) {
			assertThat(rangeInputStream.getFromIndex()).isZero();
			assertThat(rangeInputStream.getToIndex()).isEqualTo(10L);
			assertThat(rangeInputStream.read()).isEqualTo(BYTES[0]);
			assertThat(rangeInputStream.read()).isEqualTo(BYTES[1]);
			assertThat(rangeInputStream.read()).isEqualTo(BYTES[2]);
			assertThat(rangeInputStream.read()).isEqualTo(-1);
		}
		try (final var rangeInputStream = new RangeInputStream(InputStreams.of(BYTES), 10L, 10L)) {
			assertThat(rangeInputStream.getFromIndex()).isEqualTo(10L);
			assertThat(rangeInputStream.getToIndex()).isEqualTo(10L);
			assertThat(rangeInputStream.read()).isEqualTo(-1);
		}
	}

	@Test
	void testReadBuffer() throws IOException {
		final var buffer = new byte[2];
		try (final var rangeInputStream = new RangeInputStream(InputStreams.of(BYTES), 0L, 0L)) {
			assertThat(rangeInputStream.getFromIndex()).isZero();
			assertThat(rangeInputStream.getToIndex()).isZero();
			assertThat(rangeInputStream.read(buffer, 0, 0)).isZero();
			assertThat(rangeInputStream.read(buffer, 0, 2)).isEqualTo(1);
			assertThat(rangeInputStream.read(buffer, 0, 2)).isEqualTo(-1);
		}
		try (final var rangeInputStream = new RangeInputStream(InputStreams.of(BYTES), 1L, 1L)) {
			assertThat(rangeInputStream.getFromIndex()).isEqualTo(1L);
			assertThat(rangeInputStream.getToIndex()).isEqualTo(1L);
			assertThat(rangeInputStream.read(buffer, 0, 0)).isZero();
			assertThat(rangeInputStream.read(buffer, 0, 2)).isEqualTo(1);
			assertThat(rangeInputStream.read(buffer, 0, 2)).isEqualTo(-1);
		}
		try (final var rangeInputStream = new RangeInputStream(InputStreams.of(BYTES), 0L, 10L)) {
			assertThat(rangeInputStream.getFromIndex()).isZero();
			assertThat(rangeInputStream.getToIndex()).isEqualTo(10L);
			assertThat(rangeInputStream.read(buffer, 0, 0)).isZero();
			assertThat(rangeInputStream.read(buffer, 0, 2)).isEqualTo(2);
			assertThat(rangeInputStream.read(buffer, 0, 2)).isEqualTo(1);
			assertThat(rangeInputStream.read(buffer, 0, 2)).isEqualTo(-1);
		}
		try (final var rangeInputStream = new RangeInputStream(InputStreams.of(BYTES), 10L, 10L)) {
			assertThat(rangeInputStream.getFromIndex()).isEqualTo(10L);
			assertThat(rangeInputStream.getToIndex()).isEqualTo(10L);
			assertThat(rangeInputStream.read(buffer, 0, 2)).isEqualTo(-1);
		}
	}

	@Test
	void testReadBufferInvalid() throws IOException {
		final var buffer = new byte[2];
		try (final var rangeInputStream = new RangeInputStream(InputStreams.of(BYTES), 0L, 0L)) {
			assertThatNullPointerException().isThrownBy(() -> rangeInputStream.read(null, 0, 2));
			assertThatIllegalArgumentException().isThrownBy(() -> rangeInputStream.read(buffer, -1, 2));
			assertThatIllegalArgumentException().isThrownBy(() -> rangeInputStream.read(buffer, 3, 2));
			assertThatIllegalArgumentException().isThrownBy(() -> rangeInputStream.read(buffer, 0, -1));
			assertThatIllegalArgumentException().isThrownBy(() -> rangeInputStream.read(buffer, 0, 3));
		}
	}

	@Test
	void testSkip() throws IOException {
		try (final var rangeInputStream = new RangeInputStream(InputStreams.of(BYTES), 0L, 0L)) {
			assertThat(rangeInputStream.getFromIndex()).isZero();
			assertThat(rangeInputStream.getToIndex()).isZero();
			assertThat(rangeInputStream.skip(-1L)).isZero();
			assertThat(rangeInputStream.skip(0L)).isZero();
			assertThat(rangeInputStream.skip(1L)).isEqualTo(1L);
			assertThat(rangeInputStream.skip(1L)).isZero();
		}
		try (final var rangeInputStream = new RangeInputStream(InputStreams.of(BYTES), 1L, 1L)) {
			assertThat(rangeInputStream.getFromIndex()).isEqualTo(1L);
			assertThat(rangeInputStream.getToIndex()).isEqualTo(1L);
			assertThat(rangeInputStream.skip(-1L)).isZero();
			assertThat(rangeInputStream.skip(0L)).isZero();
			assertThat(rangeInputStream.skip(1L)).isEqualTo(1L);
			assertThat(rangeInputStream.skip(1L)).isZero();
		}
		try (final var rangeInputStream = new RangeInputStream(InputStreams.of(BYTES), 0L, 10L)) {
			assertThat(rangeInputStream.getFromIndex()).isZero();
			assertThat(rangeInputStream.getToIndex()).isEqualTo(10L);
			assertThat(rangeInputStream.skip(-1L)).isZero();
			assertThat(rangeInputStream.skip(0L)).isZero();
			assertThat(rangeInputStream.skip(1L)).isEqualTo(1L);
			assertThat(rangeInputStream.skip(10L)).isEqualTo(2L);
		}
		try (final var rangeInputStream = new RangeInputStream(InputStreams.of(BYTES), 10L, 10L)) {
			assertThat(rangeInputStream.getFromIndex()).isEqualTo(10L);
			assertThat(rangeInputStream.getToIndex()).isEqualTo(10L);
			assertThat(rangeInputStream.skip(-1L)).isZero();
			assertThat(rangeInputStream.skip(0L)).isZero();
			assertThat(rangeInputStream.skip(1L)).isZero();
		}
	}

	@Test
	void testMarkAndReset() throws IOException {
		try (final var rangeInputStream = new RangeInputStream(InputStreams.of(BYTES), 0L, 0L)) {
			rangeInputStream.mark(2);
			assertThat(rangeInputStream.read()).isEqualTo(BYTES[0]);
			assertThat(rangeInputStream.read()).isEqualTo(-1);
			rangeInputStream.reset();
			assertThat(rangeInputStream.read()).isEqualTo(BYTES[0]);
			assertThat(rangeInputStream.read()).isEqualTo(-1);
			rangeInputStream.reset();
			assertThat(rangeInputStream.read()).isEqualTo(BYTES[0]);
			assertThat(rangeInputStream.read()).isEqualTo(-1);
		}
		try (final var rangeInputStream = new RangeInputStream(InputStreams.of(BYTES), 1L, 1L)) {
			rangeInputStream.mark(2);
			assertThat(rangeInputStream.read()).isEqualTo(BYTES[1]);
			assertThat(rangeInputStream.read()).isEqualTo(-1);
			rangeInputStream.reset();
			assertThat(rangeInputStream.read()).isEqualTo(BYTES[1]);
			assertThat(rangeInputStream.read()).isEqualTo(-1);
			rangeInputStream.reset();
			assertThat(rangeInputStream.read()).isEqualTo(BYTES[1]);
			assertThat(rangeInputStream.read()).isEqualTo(-1);
		}
		try (final var rangeInputStream = new RangeInputStream(InputStreams.of(BYTES), 0L, 10L)) {
			assertThat(rangeInputStream.read()).isEqualTo(BYTES[0]);
			rangeInputStream.mark(2);
			assertThat(rangeInputStream.read()).isEqualTo(BYTES[1]);
			assertThat(rangeInputStream.read()).isEqualTo(BYTES[2]);
			assertThat(rangeInputStream.read()).isEqualTo(-1);
			rangeInputStream.reset();
			assertThat(rangeInputStream.read()).isEqualTo(BYTES[1]);
			assertThat(rangeInputStream.read()).isEqualTo(BYTES[2]);
			assertThat(rangeInputStream.read()).isEqualTo(-1);
			rangeInputStream.reset();
			assertThat(rangeInputStream.read()).isEqualTo(BYTES[1]);
			assertThat(rangeInputStream.read()).isEqualTo(BYTES[2]);
			assertThat(rangeInputStream.read()).isEqualTo(-1);
		}
		try (final var rangeInputStream = new RangeInputStream(InputStreams.of(BYTES), 10L, 10L)) {
			rangeInputStream.mark(2);
			assertThat(rangeInputStream.read()).isEqualTo(-1);
			rangeInputStream.reset();
			assertThat(rangeInputStream.read()).isEqualTo(-1);
			rangeInputStream.reset();
			assertThat(rangeInputStream.read()).isEqualTo(-1);
		}
	}
}