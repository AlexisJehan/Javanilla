/*
 * MIT License
 *
 * Copyright (c) 2018-2020 Alexis Jehan
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
package com.github.alexisjehan.javanilla.io.chars;

import com.github.alexisjehan.javanilla.lang.array.CharArrays;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.assertj.core.api.Assertions.assertThatNullPointerException;

/**
 * <p>{@link CountReader} unit tests.</p>
 */
final class CountReaderTest {

	private static final char[] CHARS = CharArrays.of('a', 'b', 'c');

	@Test
	void testConstructorInvalid() {
		assertThatNullPointerException().isThrownBy(() -> new CountReader(null));
	}

	@Test
	void testReadChar() throws IOException {
		try (final var countReader = new CountReader(Readers.of(CHARS))) {
			assertThat(countReader.getCount()).isEqualTo(0L);
			assertThat(countReader.read()).isEqualTo(CHARS[0]);
			assertThat(countReader.getCount()).isEqualTo(1L);
			assertThat(countReader.read()).isEqualTo(CHARS[1]);
			assertThat(countReader.getCount()).isEqualTo(2L);
			assertThat(countReader.read()).isEqualTo(CHARS[2]);
			assertThat(countReader.getCount()).isEqualTo(3L);
			assertThat(countReader.read()).isEqualTo(-1);
			assertThat(countReader.getCount()).isEqualTo(3L);
		}
	}

	@Test
	void testReadBuffer() throws IOException {
		final var buffer = new char[2];
		try (final var countReader = new CountReader(Readers.of(CHARS))) {
			assertThat(countReader.getCount()).isEqualTo(0L);
			assertThat(countReader.read(buffer, 0, 0)).isEqualTo(0);
			assertThat(countReader.getCount()).isEqualTo(0L);
			assertThat(countReader.read(buffer, 0, 2)).isEqualTo(2);
			assertThat(countReader.getCount()).isEqualTo(2L);
			assertThat(countReader.read(buffer, 0, 2)).isEqualTo(1);
			assertThat(countReader.getCount()).isEqualTo(3L);
			assertThat(countReader.read(buffer, 0, 2)).isEqualTo(-1);
			assertThat(countReader.getCount()).isEqualTo(3L);
		}
	}

	@Test
	void testReadBufferInvalid() throws IOException {
		final var buffer = new char[2];
		try (final var countReader = new CountReader(Readers.of(CHARS))) {
			assertThatNullPointerException().isThrownBy(() -> countReader.read(null, 0, 2));
			assertThatIllegalArgumentException().isThrownBy(() -> countReader.read(buffer, -1, 2));
			assertThatIllegalArgumentException().isThrownBy(() -> countReader.read(buffer, 3, 2));
			assertThatIllegalArgumentException().isThrownBy(() -> countReader.read(buffer, 0, -1));
			assertThatIllegalArgumentException().isThrownBy(() -> countReader.read(buffer, 0, 3));
		}
	}

	@Test
	void testSkip() throws IOException {
		try (final var countReader = new CountReader(Readers.of(CHARS))) {
			assertThat(countReader.getCount()).isEqualTo(0L);
			assertThat(countReader.skip(-1L)).isEqualTo(0L);
			assertThat(countReader.skip(0L)).isEqualTo(0L);
			assertThat(countReader.getCount()).isEqualTo(0L);
			assertThat(countReader.skip(2L)).isEqualTo(2L);
			assertThat(countReader.getCount()).isEqualTo(2L);
			assertThat(countReader.skip(2L)).isEqualTo(1L);
			assertThat(countReader.getCount()).isEqualTo(3L);
		}
	}

	@Test
	void testMarkReset() throws IOException {
		try (final var countReader = new CountReader(Readers.of(CHARS))) {
			assertThat(countReader.getCount()).isEqualTo(0L);
			assertThat(countReader.read()).isEqualTo(CHARS[0]);
			assertThat(countReader.getCount()).isEqualTo(1L);
			countReader.mark(2);
			assertThat(countReader.getCount()).isEqualTo(1L);
			assertThat(countReader.read()).isEqualTo(CHARS[1]);
			assertThat(countReader.getCount()).isEqualTo(2L);
			countReader.reset();
			assertThat(countReader.getCount()).isEqualTo(1L);
			assertThat(countReader.read()).isEqualTo(CHARS[1]);
			assertThat(countReader.getCount()).isEqualTo(2L);
			countReader.reset();
			assertThat(countReader.getCount()).isEqualTo(1L);
			assertThat(countReader.read()).isEqualTo(CHARS[1]);
			assertThat(countReader.getCount()).isEqualTo(2L);
		}
	}
}