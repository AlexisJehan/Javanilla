/*
 * MIT License
 *
 * Copyright (c) 2018-2021 Alexis Jehan
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

/**
 * <p>{@link CountInputStream} unit tests.</p>
 */
final class CountInputStreamTest {

	private static final byte[] BYTES = ByteArrays.of((byte) 1, (byte) 2, (byte) 3);

	@Test
	void testConstructorInvalid() {
		assertThatNullPointerException().isThrownBy(() -> new CountInputStream(null));
	}

	@Test
	void testReadByte() throws IOException {
		try (final var countInputStream = new CountInputStream(InputStreams.of(BYTES))) {
			assertThat(countInputStream.getCount()).isZero();
			assertThat(countInputStream.read()).isEqualTo(BYTES[0]);
			assertThat(countInputStream.getCount()).isEqualTo(1L);
			assertThat(countInputStream.read()).isEqualTo(BYTES[1]);
			assertThat(countInputStream.getCount()).isEqualTo(2L);
			assertThat(countInputStream.read()).isEqualTo(BYTES[2]);
			assertThat(countInputStream.getCount()).isEqualTo(3L);
			assertThat(countInputStream.read()).isEqualTo(-1);
			assertThat(countInputStream.getCount()).isEqualTo(3L);
		}
	}

	@Test
	void testReadBuffer() throws IOException {
		final var buffer = new byte[2];
		try (final var countInputStream = new CountInputStream(InputStreams.of(BYTES))) {
			assertThat(countInputStream.getCount()).isZero();
			assertThat(countInputStream.read(buffer, 0, 0)).isZero();
			assertThat(countInputStream.getCount()).isZero();
			assertThat(countInputStream.read(buffer, 0, 2)).isEqualTo(2);
			assertThat(countInputStream.getCount()).isEqualTo(2L);
			assertThat(countInputStream.read(buffer, 0, 2)).isEqualTo(1);
			assertThat(countInputStream.getCount()).isEqualTo(3L);
			assertThat(countInputStream.read(buffer, 0, 2)).isEqualTo(-1);
			assertThat(countInputStream.getCount()).isEqualTo(3L);
		}
	}

	@Test
	void testReadBufferInvalid() throws IOException {
		final var buffer = new byte[2];
		try (final var countInputStream = new CountInputStream(InputStreams.of(BYTES))) {
			assertThatNullPointerException().isThrownBy(() -> countInputStream.read(null, 0, 2));
			assertThatIllegalArgumentException().isThrownBy(() -> countInputStream.read(buffer, -1, 2));
			assertThatIllegalArgumentException().isThrownBy(() -> countInputStream.read(buffer, 3, 2));
			assertThatIllegalArgumentException().isThrownBy(() -> countInputStream.read(buffer, 0, -1));
			assertThatIllegalArgumentException().isThrownBy(() -> countInputStream.read(buffer, 0, 3));
		}
	}

	@Test
	void testSkip() throws IOException {
		try (final var countInputStream = new CountInputStream(InputStreams.of(BYTES))) {
			assertThat(countInputStream.getCount()).isZero();
			assertThat(countInputStream.skip(-1L)).isZero();
			assertThat(countInputStream.skip(0L)).isZero();
			assertThat(countInputStream.getCount()).isZero();
			assertThat(countInputStream.skip(2L)).isEqualTo(2L);
			assertThat(countInputStream.getCount()).isEqualTo(2L);
			assertThat(countInputStream.skip(2L)).isEqualTo(1L);
			assertThat(countInputStream.getCount()).isEqualTo(3L);
		}
	}

	@Test
	void testMarkReset() throws IOException {
		try (final var countInputStream = new CountInputStream(InputStreams.buffered(InputStreams.of(BYTES)))) {
			assertThat(countInputStream.getCount()).isZero();
			assertThat(countInputStream.read()).isEqualTo(BYTES[0]);
			assertThat(countInputStream.getCount()).isEqualTo(1L);
			countInputStream.mark(2);
			assertThat(countInputStream.getCount()).isEqualTo(1L);
			assertThat(countInputStream.read()).isEqualTo(BYTES[1]);
			assertThat(countInputStream.getCount()).isEqualTo(2L);
			countInputStream.reset();
			assertThat(countInputStream.getCount()).isEqualTo(1L);
			assertThat(countInputStream.read()).isEqualTo(BYTES[1]);
			assertThat(countInputStream.getCount()).isEqualTo(2L);
			countInputStream.reset();
			assertThat(countInputStream.getCount()).isEqualTo(1L);
			assertThat(countInputStream.read()).isEqualTo(BYTES[1]);
			assertThat(countInputStream.getCount()).isEqualTo(2L);
		}
	}
}