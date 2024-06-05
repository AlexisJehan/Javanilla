/*
 * MIT License
 *
 * Copyright (c) 2018-2024 Alexis Jehan
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
package com.github.alexisjehan.javanilla.io.lines;

import com.github.alexisjehan.javanilla.io.chars.Readers;
import com.github.alexisjehan.javanilla.lang.array.ObjectArrays;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.assertj.core.api.Assertions.assertThatNullPointerException;

@Deprecated
final class RangeLineReaderTest {

	private static final String[] LINES = ObjectArrays.of("abc", "def", "ghi");

	@Test
	void testConstructorInvalid() {
		assertThatNullPointerException().isThrownBy(() -> new RangeLineReader(null, 0L, 0L));
		assertThatIllegalArgumentException().isThrownBy(() -> new RangeLineReader(new LineReader(Readers.of(String.join("\n", LINES))), -1L, 0L));
		assertThatIllegalArgumentException().isThrownBy(() -> new RangeLineReader(new LineReader(Readers.of(String.join("\n", LINES))), 1L, 0L));
	}

	@Test
	void testRead() throws IOException {
		try (var rangeLineReader = new RangeLineReader(new LineReader(Readers.of(String.join("\n", LINES))), 0L, 0L)) {
			assertThat(rangeLineReader.getFromIndex()).isZero();
			assertThat(rangeLineReader.getToIndex()).isZero();
			assertThat(rangeLineReader.read()).isEqualTo(LINES[0]);
			assertThat(rangeLineReader.read()).isNull();
		}
		try (var rangeLineReader = new RangeLineReader(new LineReader(Readers.of(String.join("\n", LINES))), 1L, 1L)) {
			assertThat(rangeLineReader.getFromIndex()).isEqualTo(1L);
			assertThat(rangeLineReader.getToIndex()).isEqualTo(1L);
			assertThat(rangeLineReader.read()).isEqualTo(LINES[1]);
			assertThat(rangeLineReader.read()).isNull();
		}
		try (var rangeLineReader = new RangeLineReader(new LineReader(Readers.of(String.join("\n", LINES))), 0L, 10L)) {
			assertThat(rangeLineReader.getFromIndex()).isZero();
			assertThat(rangeLineReader.getToIndex()).isEqualTo(10L);
			assertThat(rangeLineReader.read()).isEqualTo(LINES[0]);
			assertThat(rangeLineReader.read()).isEqualTo(LINES[1]);
			assertThat(rangeLineReader.read()).isEqualTo(LINES[2]);
			assertThat(rangeLineReader.read()).isNull();
		}
		try (var rangeLineReader = new RangeLineReader(new LineReader(Readers.of(String.join("\n", LINES))), 10L, 10L)) {
			assertThat(rangeLineReader.getFromIndex()).isEqualTo(10L);
			assertThat(rangeLineReader.getToIndex()).isEqualTo(10L);
			assertThat(rangeLineReader.read()).isNull();
		}
	}

	@Test
	void testSkip() throws IOException {
		try (var rangeLineReader = new RangeLineReader(new LineReader(Readers.of(String.join("\n", LINES))), 0L, 0L)) {
			assertThat(rangeLineReader.getFromIndex()).isZero();
			assertThat(rangeLineReader.getToIndex()).isZero();
			assertThat(rangeLineReader.skip(-1L)).isZero();
			assertThat(rangeLineReader.skip(0L)).isZero();
			assertThat(rangeLineReader.skip(1L)).isEqualTo(1L);
			assertThat(rangeLineReader.skip(1L)).isZero();
		}
		try (var rangeLineReader = new RangeLineReader(new LineReader(Readers.of(String.join("\n", LINES))), 1L, 1L)) {
			assertThat(rangeLineReader.getFromIndex()).isEqualTo(1L);
			assertThat(rangeLineReader.getToIndex()).isEqualTo(1L);
			assertThat(rangeLineReader.skip(-1L)).isZero();
			assertThat(rangeLineReader.skip(0L)).isZero();
			assertThat(rangeLineReader.skip(1L)).isEqualTo(1L);
			assertThat(rangeLineReader.skip(1L)).isZero();
		}
		try (var rangeLineReader = new RangeLineReader(new LineReader(Readers.of(String.join("\n", LINES))), 0L, 10L)) {
			assertThat(rangeLineReader.getFromIndex()).isZero();
			assertThat(rangeLineReader.getToIndex()).isEqualTo(10L);
			assertThat(rangeLineReader.skip(-1L)).isZero();
			assertThat(rangeLineReader.skip(0L)).isZero();
			assertThat(rangeLineReader.skip(1L)).isEqualTo(1L);
			assertThat(rangeLineReader.skip(10L)).isEqualTo(2L);
		}
		try (var rangeLineReader = new RangeLineReader(new LineReader(Readers.of(String.join("\n", LINES))), 10L, 10L)) {
			assertThat(rangeLineReader.getFromIndex()).isEqualTo(10L);
			assertThat(rangeLineReader.getToIndex()).isEqualTo(10L);
			assertThat(rangeLineReader.skip(-1L)).isZero();
			assertThat(rangeLineReader.skip(0L)).isZero();
			assertThat(rangeLineReader.skip(1L)).isZero();
		}
	}
}