/*
 * MIT License
 *
 * Copyright (c) 2018-2025 Alexis Jehan
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
import java.io.StringWriter;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.assertj.core.api.Assertions.assertThatNullPointerException;

@Deprecated
final class RangeLineWriterTest {

	private static final String[] LINES = ObjectArrays.of("abc", "def", "ghi");

	@Test
	void testConstructorInvalid() {
		assertThatNullPointerException().isThrownBy(() -> new RangeLineWriter(null, 0L, 0L));
		assertThatIllegalArgumentException().isThrownBy(() -> new RangeLineWriter(new LineWriter(Writers.EMPTY), -1L, 0L));
		assertThatIllegalArgumentException().isThrownBy(() -> new RangeLineWriter(new LineWriter(Writers.EMPTY), 1L, 0L));
	}

	@Test
	void testWrite() throws IOException {
		try (var writer = new StringWriter()) {
			try (var rangeLineWriter = new RangeLineWriter(new LineWriter(writer), 0L, 0L)) {
				assertThat(rangeLineWriter.getFromIndex()).isZero();
				assertThat(rangeLineWriter.getToIndex()).isZero();
				rangeLineWriter.write(LINES[0]);
				rangeLineWriter.write(LINES[1]);
				rangeLineWriter.write(LINES[2]);
				rangeLineWriter.flush();
			}
			assertThat(writer).hasToString(LINES[0]);
		}
		try (var writer = new StringWriter()) {
			try (var rangeLineWriter = new RangeLineWriter(new LineWriter(writer), 1L, 1L)) {
				assertThat(rangeLineWriter.getFromIndex()).isEqualTo(1L);
				assertThat(rangeLineWriter.getToIndex()).isEqualTo(1L);
				rangeLineWriter.write(LINES[0]);
				rangeLineWriter.write(LINES[1]);
				rangeLineWriter.write(LINES[2]);
				rangeLineWriter.flush();
			}
			assertThat(writer).hasToString(LINES[1]);
		}
		try (var writer = new StringWriter()) {
			try (var rangeLineWriter = new RangeLineWriter(new LineWriter(writer), 0L, 10L)) {
				assertThat(rangeLineWriter.getFromIndex()).isZero();
				assertThat(rangeLineWriter.getToIndex()).isEqualTo(10L);
				rangeLineWriter.write(LINES[0]);
				rangeLineWriter.write(LINES[1]);
				rangeLineWriter.write(LINES[2]);
				rangeLineWriter.flush();
			}
			assertThat(writer).hasToString(String.join(System.lineSeparator(), LINES[0], LINES[1], LINES[2]));
		}
		try (var writer = new StringWriter()) {
			try (var rangeLineWriter = new RangeLineWriter(new LineWriter(writer), 10L, 10L)) {
				assertThat(rangeLineWriter.getFromIndex()).isEqualTo(10L);
				assertThat(rangeLineWriter.getToIndex()).isEqualTo(10L);
				rangeLineWriter.write(LINES[0]);
				rangeLineWriter.write(LINES[1]);
				rangeLineWriter.write(LINES[2]);
				rangeLineWriter.flush();
			}
			assertThat(writer.toString()).isEmpty();
		}
	}

	@Test
	void testNewLine() throws IOException {
		try (var writer = new StringWriter()) {
			try (var rangeLineWriter = new RangeLineWriter(new LineWriter(writer), 0L, 0L)) {
				assertThat(rangeLineWriter.getFromIndex()).isZero();
				assertThat(rangeLineWriter.getToIndex()).isZero();
				rangeLineWriter.newLine();
				rangeLineWriter.newLine();
				rangeLineWriter.newLine();
				rangeLineWriter.flush();
			}
			assertThat(writer).hasToString(System.lineSeparator());
		}
		try (var writer = new StringWriter()) {
			try (var rangeLineWriter = new RangeLineWriter(new LineWriter(writer), 1L, 1L)) {
				assertThat(rangeLineWriter.getFromIndex()).isEqualTo(1L);
				assertThat(rangeLineWriter.getToIndex()).isEqualTo(1L);
				rangeLineWriter.newLine();
				rangeLineWriter.newLine();
				rangeLineWriter.newLine();
				rangeLineWriter.flush();
			}
			assertThat(writer).hasToString(System.lineSeparator());
		}
		try (var writer = new StringWriter()) {
			try (var rangeLineWriter = new RangeLineWriter(new LineWriter(writer), 0L, 10L)) {
				assertThat(rangeLineWriter.getFromIndex()).isZero();
				assertThat(rangeLineWriter.getToIndex()).isEqualTo(10L);
				rangeLineWriter.newLine();
				rangeLineWriter.newLine();
				rangeLineWriter.newLine();
				rangeLineWriter.flush();
			}
			assertThat(writer).hasToString(System.lineSeparator() + System.lineSeparator() + System.lineSeparator());
		}
		try (var writer = new StringWriter()) {
			try (var rangeLineWriter = new RangeLineWriter(new LineWriter(writer), 10L, 10L)) {
				assertThat(rangeLineWriter.getFromIndex()).isEqualTo(10L);
				assertThat(rangeLineWriter.getToIndex()).isEqualTo(10L);
				rangeLineWriter.newLine();
				rangeLineWriter.newLine();
				rangeLineWriter.newLine();
				rangeLineWriter.flush();
			}
			assertThat(writer.toString()).isEmpty();
		}
	}
}