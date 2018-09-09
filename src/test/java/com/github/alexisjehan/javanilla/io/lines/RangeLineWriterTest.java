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
package com.github.alexisjehan.javanilla.io.lines;

import com.github.alexisjehan.javanilla.io.chars.Writers;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.StringWriter;

import static org.assertj.core.api.Assertions.*;

/**
 * <p>{@link RangeLineWriter} unit tests.</p>
 */
final class RangeLineWriterTest {

	@Test
	void testConstructorInvalid() {
		assertThatNullPointerException().isThrownBy(() -> new RangeLineWriter(null, 0L));
		assertThatExceptionOfType(IndexOutOfBoundsException.class).isThrownBy(() -> new RangeLineWriter(new LineWriter(Writers.EMPTY), -1L, 0L));
		assertThatExceptionOfType(IndexOutOfBoundsException.class).isThrownBy(() -> new RangeLineWriter(new LineWriter(Writers.EMPTY), 1L, 0L));
	}

	@Test
	void testWrite() throws IOException {
		try (final var stringWriter = new StringWriter()) {
			try (final var rangeLineWriter = new RangeLineWriter(new LineWriter(stringWriter), 0L)) {
				assertThat(rangeLineWriter.getFromIndex()).isEqualTo(0L);
				assertThat(rangeLineWriter.getToIndex()).isEqualTo(0L);
				rangeLineWriter.write("abc");
				rangeLineWriter.write("def");
				rangeLineWriter.write("ghi");
				rangeLineWriter.flush();
			}
			assertThat(stringWriter).hasToString("abc");
		}
		try (final var stringWriter = new StringWriter()) {
			try (final var rangeLineWriter = new RangeLineWriter(new LineWriter(stringWriter), 1L, 1L)) {
				assertThat(rangeLineWriter.getFromIndex()).isEqualTo(1L);
				assertThat(rangeLineWriter.getToIndex()).isEqualTo(1L);
				rangeLineWriter.write("abc");
				rangeLineWriter.write("def");
				rangeLineWriter.write("ghi");
				rangeLineWriter.flush();
			}
			assertThat(stringWriter).hasToString("def");
		}
		try (final var stringWriter = new StringWriter()) {
			try (final var rangeLineWriter = new RangeLineWriter(new LineWriter(stringWriter), 10L)) {
				assertThat(rangeLineWriter.getFromIndex()).isEqualTo(0L);
				assertThat(rangeLineWriter.getToIndex()).isEqualTo(10L);
				rangeLineWriter.write("abc");
				rangeLineWriter.write("def");
				rangeLineWriter.write("ghi");
				rangeLineWriter.flush();
			}
			assertThat(stringWriter).hasToString("abc" + System.lineSeparator() + "def" + System.lineSeparator() + "ghi");
		}
		try (final var stringWriter = new StringWriter()) {
			try (final var rangeLineWriter = new RangeLineWriter(new LineWriter(stringWriter), 10L, 10L)) {
				assertThat(rangeLineWriter.getFromIndex()).isEqualTo(10L);
				assertThat(rangeLineWriter.getToIndex()).isEqualTo(10L);
				rangeLineWriter.write("abc");
				rangeLineWriter.write("def");
				rangeLineWriter.write("ghi");
				rangeLineWriter.flush();
			}
			assertThat(stringWriter.toString()).isEmpty();
		}
	}

	@Test
	void testNewLine() throws IOException {
		try (final var stringWriter = new StringWriter()) {
			try (final var rangeLineWriter = new RangeLineWriter(new LineWriter(stringWriter), 0L)) {
				assertThat(rangeLineWriter.getFromIndex()).isEqualTo(0L);
				assertThat(rangeLineWriter.getToIndex()).isEqualTo(0L);
				rangeLineWriter.newLine();
				rangeLineWriter.newLine();
				rangeLineWriter.newLine();
				rangeLineWriter.flush();
			}
			assertThat(stringWriter).hasToString(System.lineSeparator());
		}
		try (final var stringWriter = new StringWriter()) {
			try (final var rangeLineWriter = new RangeLineWriter(new LineWriter(stringWriter), 1L, 1L)) {
				assertThat(rangeLineWriter.getFromIndex()).isEqualTo(1L);
				assertThat(rangeLineWriter.getToIndex()).isEqualTo(1L);
				rangeLineWriter.newLine();
				rangeLineWriter.newLine();
				rangeLineWriter.newLine();
				rangeLineWriter.flush();
			}
			assertThat(stringWriter).hasToString(System.lineSeparator());
		}
		try (final var stringWriter = new StringWriter()) {
			try (final var rangeLineWriter = new RangeLineWriter(new LineWriter(stringWriter), 10L)) {
				assertThat(rangeLineWriter.getFromIndex()).isEqualTo(0L);
				assertThat(rangeLineWriter.getToIndex()).isEqualTo(10L);
				rangeLineWriter.newLine();
				rangeLineWriter.newLine();
				rangeLineWriter.newLine();
				rangeLineWriter.flush();
			}
			assertThat(stringWriter).hasToString(System.lineSeparator() + System.lineSeparator() + System.lineSeparator());
		}
		try (final var stringWriter = new StringWriter()) {
			try (final var rangeLineWriter = new RangeLineWriter(new LineWriter(stringWriter), 10L, 10L)) {
				assertThat(rangeLineWriter.getFromIndex()).isEqualTo(10L);
				assertThat(rangeLineWriter.getToIndex()).isEqualTo(10L);
				rangeLineWriter.newLine();
				rangeLineWriter.newLine();
				rangeLineWriter.newLine();
				rangeLineWriter.flush();
			}
			assertThat(stringWriter.toString()).isEmpty();
		}
	}
}