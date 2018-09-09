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

import static org.assertj.core.api.Assertions.*;

/**
 * <p>{@link CountOutputStream} unit tests.</p>
 */
final class CountOutputStreamTest {

	@Test
	void testConstructorInvalid() {
		assertThatNullPointerException().isThrownBy(() -> new CountOutputStream(null));
	}

	@Test
	void testWriteByte() throws IOException {
		try (final var countOutputStream = new CountOutputStream(OutputStreams.EMPTY)) {
			assertThat(countOutputStream.getCount()).isEqualTo(0L);
			countOutputStream.write(1);
			assertThat(countOutputStream.getCount()).isEqualTo(1L);
			countOutputStream.write(2);
			assertThat(countOutputStream.getCount()).isEqualTo(2L);
			countOutputStream.write(3);
			assertThat(countOutputStream.getCount()).isEqualTo(3L);
		}
	}

	@Test
	void testWriteBytes() throws IOException {
		final var bytes = ByteArrays.of((byte) 1, (byte) 2, (byte) 3);
		try (final var countOutputStream = new CountOutputStream(OutputStreams.EMPTY)) {
			assertThat(countOutputStream.getCount()).isEqualTo(0L);
			countOutputStream.write(bytes, 0, 0);
			assertThat(countOutputStream.getCount()).isEqualTo(0L);
			countOutputStream.write(bytes, 0, 2);
			assertThat(countOutputStream.getCount()).isEqualTo(2L);
		}
	}

	@Test
	void testWriteBytesInvalid() throws IOException {
		final var bytes = ByteArrays.of((byte) 1, (byte) 2, (byte) 3);
		try (final var countOutputStream = new CountOutputStream(OutputStreams.EMPTY)) {
			assertThatNullPointerException().isThrownBy(() -> countOutputStream.write(null, 0, 2));
			assertThatExceptionOfType(IndexOutOfBoundsException.class).isThrownBy(() -> countOutputStream.write(bytes, -1, 3));
			assertThatExceptionOfType(IndexOutOfBoundsException.class).isThrownBy(() -> countOutputStream.write(bytes, 4, 3));
			assertThatExceptionOfType(IndexOutOfBoundsException.class).isThrownBy(() -> countOutputStream.write(bytes, 0, -1));
			assertThatExceptionOfType(IndexOutOfBoundsException.class).isThrownBy(() -> countOutputStream.write(bytes, 0, 4));
		}
	}
}