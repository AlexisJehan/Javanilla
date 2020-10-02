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
package com.github.alexisjehan.javanilla.io.lines;

import com.github.alexisjehan.javanilla.io.chars.Readers;
import com.github.alexisjehan.javanilla.lang.array.ObjectArrays;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNullPointerException;

/**
 * <p>{@link CountLineReader} unit tests.</p>
 */
final class CountLineReaderTest {

	private static final String[] LINES = ObjectArrays.of("abc", "def", "ghi");

	@Test
	void testConstructorInvalid() {
		assertThatNullPointerException().isThrownBy(() -> new CountLineReader(null));
	}

	@Test
	void testRead() throws IOException {
		try (final var countLineReader = new CountLineReader(new LineReader(Readers.of(String.join("\n", LINES))))) {
			assertThat(countLineReader.getCount()).isZero();
			assertThat(countLineReader.read()).isEqualTo(LINES[0]);
			assertThat(countLineReader.getCount()).isEqualTo(1L);
			assertThat(countLineReader.read()).isEqualTo(LINES[1]);
			assertThat(countLineReader.getCount()).isEqualTo(2L);
			assertThat(countLineReader.read()).isEqualTo(LINES[2]);
			assertThat(countLineReader.getCount()).isEqualTo(3L);
			assertThat(countLineReader.read()).isNull();
			assertThat(countLineReader.getCount()).isEqualTo(3L);
		}
	}

	@Test
	void testSkip() throws IOException {
		try (final var countLineReader = new CountLineReader(new LineReader(Readers.of(String.join("\n", LINES))))) {
			assertThat(countLineReader.getCount()).isZero();
			assertThat(countLineReader.skip(-1L)).isZero();
			assertThat(countLineReader.skip(0L)).isZero();
			assertThat(countLineReader.getCount()).isZero();
			assertThat(countLineReader.skip(2L)).isEqualTo(2L);
			assertThat(countLineReader.getCount()).isEqualTo(2L);
			assertThat(countLineReader.skip(2L)).isEqualTo(1L);
			assertThat(countLineReader.getCount()).isEqualTo(3L);
		}
	}
}