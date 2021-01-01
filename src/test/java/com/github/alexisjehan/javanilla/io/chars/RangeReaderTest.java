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
package com.github.alexisjehan.javanilla.io.chars;

import com.github.alexisjehan.javanilla.lang.array.CharArrays;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.assertj.core.api.Assertions.assertThatNullPointerException;

/**
 * <p>{@link RangeReader} unit tests.</p>
 */
final class RangeReaderTest {

	private static final char[] CHARS = CharArrays.of('a', 'b', 'c');

	@Test
	void testConstructorInvalid() {
		assertThatNullPointerException().isThrownBy(() -> new RangeReader(null, 0L, 0L));
		assertThatIllegalArgumentException().isThrownBy(() -> new RangeReader(Readers.of(CHARS), -1L, 0L));
		assertThatIllegalArgumentException().isThrownBy(() -> new RangeReader(Readers.of(CHARS), 1L, 0L));
	}

	@Test
	void testReadChar() throws IOException {
		try (final var rangeReader = new RangeReader(Readers.of(CHARS), 0L, 0L)) {
			assertThat(rangeReader.getFromIndex()).isZero();
			assertThat(rangeReader.getToIndex()).isZero();
			assertThat(rangeReader.read()).isEqualTo(CHARS[0]);
			assertThat(rangeReader.read()).isEqualTo(-1);
		}
		try (final var rangeReader = new RangeReader(Readers.of(CHARS), 1L, 1L)) {
			assertThat(rangeReader.getFromIndex()).isEqualTo(1L);
			assertThat(rangeReader.getToIndex()).isEqualTo(1L);
			assertThat(rangeReader.read()).isEqualTo(CHARS[1]);
			assertThat(rangeReader.read()).isEqualTo(-1);
		}
		try (final var rangeReader = new RangeReader(Readers.of(CHARS), 0L, 10L)) {
			assertThat(rangeReader.getFromIndex()).isZero();
			assertThat(rangeReader.getToIndex()).isEqualTo(10L);
			assertThat(rangeReader.read()).isEqualTo(CHARS[0]);
			assertThat(rangeReader.read()).isEqualTo(CHARS[1]);
			assertThat(rangeReader.read()).isEqualTo(CHARS[2]);
			assertThat(rangeReader.read()).isEqualTo(-1);
		}
		try (final var rangeReader = new RangeReader(Readers.of(CHARS), 10L, 10L)) {
			assertThat(rangeReader.getFromIndex()).isEqualTo(10L);
			assertThat(rangeReader.getToIndex()).isEqualTo(10L);
			assertThat(rangeReader.read()).isEqualTo(-1);
		}
	}

	@Test
	void testReadBuffer() throws IOException {
		final var buffer = new char[2];
		try (final var rangeReader = new RangeReader(Readers.of(CHARS), 0L, 0L)) {
			assertThat(rangeReader.getFromIndex()).isZero();
			assertThat(rangeReader.getToIndex()).isZero();
			assertThat(rangeReader.read(buffer, 0, 0)).isZero();
			assertThat(rangeReader.read(buffer, 0, 2)).isEqualTo(1);
			assertThat(rangeReader.read(buffer, 0, 2)).isEqualTo(-1);
		}
		try (final var rangeReader = new RangeReader(Readers.of(CHARS), 1L, 1L)) {
			assertThat(rangeReader.getFromIndex()).isEqualTo(1L);
			assertThat(rangeReader.getToIndex()).isEqualTo(1L);
			assertThat(rangeReader.read(buffer, 0, 0)).isZero();
			assertThat(rangeReader.read(buffer, 0, 2)).isEqualTo(1);
			assertThat(rangeReader.read(buffer, 0, 2)).isEqualTo(-1);
		}
		try (final var rangeReader = new RangeReader(Readers.of(CHARS), 0L, 10L)) {
			assertThat(rangeReader.getFromIndex()).isZero();
			assertThat(rangeReader.getToIndex()).isEqualTo(10L);
			assertThat(rangeReader.read(buffer, 0, 0)).isZero();
			assertThat(rangeReader.read(buffer, 0, 2)).isEqualTo(2);
			assertThat(rangeReader.read(buffer, 0, 2)).isEqualTo(1);
			assertThat(rangeReader.read(buffer, 0, 2)).isEqualTo(-1);
		}
		try (final var rangeReader = new RangeReader(Readers.of(CHARS), 10L, 10L)) {
			assertThat(rangeReader.getFromIndex()).isEqualTo(10L);
			assertThat(rangeReader.getToIndex()).isEqualTo(10L);
			assertThat(rangeReader.read(buffer, 0, 2)).isEqualTo(-1);
		}
	}

	@Test
	void testReadBufferInvalid() throws IOException {
		final var buffer = new char[2];
		try (final var rangeReader = new RangeReader(Readers.of(CHARS), 0L, 0L)) {
			assertThatNullPointerException().isThrownBy(() -> rangeReader.read(null, 0, 2));
			assertThatIllegalArgumentException().isThrownBy(() -> rangeReader.read(buffer, -1, 2));
			assertThatIllegalArgumentException().isThrownBy(() -> rangeReader.read(buffer, 3, 2));
			assertThatIllegalArgumentException().isThrownBy(() -> rangeReader.read(buffer, 0, -1));
			assertThatIllegalArgumentException().isThrownBy(() -> rangeReader.read(buffer, 0, 3));
		}
	}

	@Test
	void testSkip() throws IOException {
		try (final var rangeReader = new RangeReader(Readers.of(CHARS), 0L, 0L)) {
			assertThat(rangeReader.getFromIndex()).isZero();
			assertThat(rangeReader.getToIndex()).isZero();
			assertThat(rangeReader.skip(-1L)).isZero();
			assertThat(rangeReader.skip(0L)).isZero();
			assertThat(rangeReader.skip(1L)).isEqualTo(1L);
			assertThat(rangeReader.skip(1L)).isZero();
		}
		try (final var rangeReader = new RangeReader(Readers.of(CHARS), 1L, 1L)) {
			assertThat(rangeReader.getFromIndex()).isEqualTo(1L);
			assertThat(rangeReader.getToIndex()).isEqualTo(1L);
			assertThat(rangeReader.skip(-1L)).isZero();
			assertThat(rangeReader.skip(0L)).isZero();
			assertThat(rangeReader.skip(1L)).isEqualTo(1L);
			assertThat(rangeReader.skip(1L)).isZero();
		}
		try (final var rangeReader = new RangeReader(Readers.of(CHARS), 0L, 10L)) {
			assertThat(rangeReader.getFromIndex()).isZero();
			assertThat(rangeReader.getToIndex()).isEqualTo(10L);
			assertThat(rangeReader.skip(-1L)).isZero();
			assertThat(rangeReader.skip(0L)).isZero();
			assertThat(rangeReader.skip(1L)).isEqualTo(1L);
			assertThat(rangeReader.skip(10L)).isEqualTo(2L);
		}
		try (final var rangeReader = new RangeReader(Readers.of(CHARS), 10L, 10L)) {
			assertThat(rangeReader.getFromIndex()).isEqualTo(10L);
			assertThat(rangeReader.getToIndex()).isEqualTo(10L);
			assertThat(rangeReader.skip(-1L)).isZero();
			assertThat(rangeReader.skip(0L)).isZero();
			assertThat(rangeReader.skip(1L)).isZero();
		}
	}

	@Test
	void testMarkReset() throws IOException {
		try (final var rangeReader = new RangeReader(Readers.of(CHARS), 0L, 0L)) {
			rangeReader.mark(2);
			assertThat(rangeReader.read()).isEqualTo(CHARS[0]);
			assertThat(rangeReader.read()).isEqualTo(-1);
			rangeReader.reset();
			assertThat(rangeReader.read()).isEqualTo(CHARS[0]);
			assertThat(rangeReader.read()).isEqualTo(-1);
			rangeReader.reset();
			assertThat(rangeReader.read()).isEqualTo(CHARS[0]);
			assertThat(rangeReader.read()).isEqualTo(-1);
		}
		try (final var rangeReader = new RangeReader(Readers.of(CHARS), 1L, 1L)) {
			rangeReader.mark(2);
			assertThat(rangeReader.read()).isEqualTo(CHARS[1]);
			assertThat(rangeReader.read()).isEqualTo(-1);
			rangeReader.reset();
			assertThat(rangeReader.read()).isEqualTo(CHARS[1]);
			assertThat(rangeReader.read()).isEqualTo(-1);
			rangeReader.reset();
			assertThat(rangeReader.read()).isEqualTo(CHARS[1]);
			assertThat(rangeReader.read()).isEqualTo(-1);
		}
		try (final var rangeReader = new RangeReader(Readers.of(CHARS), 0L, 10L)) {
			assertThat(rangeReader.read()).isEqualTo(CHARS[0]);
			rangeReader.mark(2);
			assertThat(rangeReader.read()).isEqualTo(CHARS[1]);
			assertThat(rangeReader.read()).isEqualTo(CHARS[2]);
			assertThat(rangeReader.read()).isEqualTo(-1);
			rangeReader.reset();
			assertThat(rangeReader.read()).isEqualTo(CHARS[1]);
			assertThat(rangeReader.read()).isEqualTo(CHARS[2]);
			assertThat(rangeReader.read()).isEqualTo(-1);
			rangeReader.reset();
			assertThat(rangeReader.read()).isEqualTo(CHARS[1]);
			assertThat(rangeReader.read()).isEqualTo(CHARS[2]);
			assertThat(rangeReader.read()).isEqualTo(-1);
		}
		try (final var rangeReader = new RangeReader(Readers.of(CHARS), 10L, 10L)) {
			rangeReader.mark(2);
			assertThat(rangeReader.read()).isEqualTo(-1);
			rangeReader.reset();
			assertThat(rangeReader.read()).isEqualTo(-1);
			rangeReader.reset();
			assertThat(rangeReader.read()).isEqualTo(-1);
		}
	}
}