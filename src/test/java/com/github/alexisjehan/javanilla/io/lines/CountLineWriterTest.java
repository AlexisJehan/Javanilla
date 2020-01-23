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

import com.github.alexisjehan.javanilla.io.chars.Writers;
import com.github.alexisjehan.javanilla.lang.array.ObjectArrays;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNullPointerException;

/**
 * <p>{@link CountLineWriter} unit tests.</p>
 */
final class CountLineWriterTest {

	private static final String[] LINES = ObjectArrays.of("abc", "def", "ghi");

	@Test
	void testConstructorInvalid() {
		assertThatNullPointerException().isThrownBy(() -> new CountLineWriter(null));
	}

	@Test
	@SuppressWarnings("deprecation")
	void testWrite() throws IOException {
		try (final var countLineWriter = new CountLineWriter(new LineWriter(Writers.EMPTY))) {
			assertThat(countLineWriter.getCount()).isEqualTo(0L);
			countLineWriter.write(LINES[0]);
			assertThat(countLineWriter.getCount()).isEqualTo(1L);
			countLineWriter.write(LINES[1]);
			assertThat(countLineWriter.getCount()).isEqualTo(2L);
			countLineWriter.write(LINES[2]);
			assertThat(countLineWriter.getCount()).isEqualTo(3L);
			countLineWriter.flush();
		}
	}

	@Test
	@SuppressWarnings("deprecation")
	void testNewLine() throws IOException {
		try (final var countLineWriter = new CountLineWriter(new LineWriter(Writers.EMPTY))) {
			assertThat(countLineWriter.getCount()).isEqualTo(0L);
			countLineWriter.newLine();
			assertThat(countLineWriter.getCount()).isEqualTo(1L);
			countLineWriter.newLine();
			assertThat(countLineWriter.getCount()).isEqualTo(2L);
			countLineWriter.newLine();
			assertThat(countLineWriter.getCount()).isEqualTo(3L);
			countLineWriter.flush();
		}
	}
}