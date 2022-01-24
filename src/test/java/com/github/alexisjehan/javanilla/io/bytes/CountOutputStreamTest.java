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

/**
 * <p>{@link CountOutputStream} unit tests.</p>
 */
final class CountOutputStreamTest {

	private static final byte[] BYTES = ByteArrays.of((byte) 1, (byte) 2, (byte) 3);

	@Test
	void testConstructorInvalid() {
		assertThatNullPointerException().isThrownBy(() -> new CountOutputStream(null));
	}

	@Test
	@SuppressWarnings("deprecation")
	void testWriteByte() throws IOException {
		try (final var countOutputStream = new CountOutputStream(OutputStreams.EMPTY)) {
			assertThat(countOutputStream.getCount()).isZero();
			countOutputStream.write(BYTES[0]);
			assertThat(countOutputStream.getCount()).isEqualTo(1L);
			countOutputStream.write(BYTES[1]);
			assertThat(countOutputStream.getCount()).isEqualTo(2L);
			countOutputStream.write(BYTES[2]);
			assertThat(countOutputStream.getCount()).isEqualTo(3L);
		}
	}

	@Test
	@SuppressWarnings("deprecation")
	void testWriteBytes() throws IOException {
		try (final var countOutputStream = new CountOutputStream(OutputStreams.EMPTY)) {
			assertThat(countOutputStream.getCount()).isZero();
			countOutputStream.write(BYTES, 0, 0);
			assertThat(countOutputStream.getCount()).isZero();
			countOutputStream.write(BYTES, 0, 2);
			assertThat(countOutputStream.getCount()).isEqualTo(2L);
		}
	}

	@Test
	@SuppressWarnings("deprecation")
	void testWriteBytesInvalid() throws IOException {
		try (final var countOutputStream = new CountOutputStream(OutputStreams.EMPTY)) {
			assertThatNullPointerException().isThrownBy(() -> countOutputStream.write(null, 0, 2));
			assertThatIllegalArgumentException().isThrownBy(() -> countOutputStream.write(BYTES, -1, 3));
			assertThatIllegalArgumentException().isThrownBy(() -> countOutputStream.write(BYTES, 4, 3));
			assertThatIllegalArgumentException().isThrownBy(() -> countOutputStream.write(BYTES, 0, -1));
			assertThatIllegalArgumentException().isThrownBy(() -> countOutputStream.write(BYTES, 0, 4));
		}
	}
}