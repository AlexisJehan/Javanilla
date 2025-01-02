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
package com.github.alexisjehan.javanilla.io.chars;

import com.github.alexisjehan.javanilla.lang.array.CharArrays;
import org.junit.jupiter.api.Test;

import java.io.CharArrayWriter;
import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.assertj.core.api.Assertions.assertThatNullPointerException;

@Deprecated
final class RangeWriterTest {

	private static final char[] CHARS = CharArrays.of('a', 'b', 'c');

	@Test
	void testConstructorInvalid() {
		assertThatNullPointerException().isThrownBy(() -> new RangeWriter(null, 0L, 0L));
		assertThatIllegalArgumentException().isThrownBy(() -> new RangeWriter(Writers.EMPTY, -1L, 0L));
		assertThatIllegalArgumentException().isThrownBy(() -> new RangeWriter(Writers.EMPTY, 1L, 0L));
	}

	@Test
	void testWriteChar() throws IOException {
		try (var writer = new CharArrayWriter()) {
			try (var rangeWriter = new RangeWriter(writer, 0L, 0L)) {
				assertThat(rangeWriter.getFromIndex()).isZero();
				assertThat(rangeWriter.getToIndex()).isZero();
				rangeWriter.write(CHARS[0]);
				rangeWriter.write(CHARS[1]);
				rangeWriter.write(CHARS[2]);
			}
			assertThat(writer.toCharArray()).containsExactly(CHARS[0]);
		}
		try (var writer = new CharArrayWriter()) {
			try (var rangeWriter = new RangeWriter(writer, 1L, 1L)) {
				assertThat(rangeWriter.getFromIndex()).isEqualTo(1L);
				assertThat(rangeWriter.getToIndex()).isEqualTo(1L);
				rangeWriter.write(CHARS[0]);
				rangeWriter.write(CHARS[1]);
				rangeWriter.write(CHARS[2]);
			}
			assertThat(writer.toCharArray()).containsExactly(CHARS[1]);
		}
		try (var writer = new CharArrayWriter()) {
			try (var rangeWriter = new RangeWriter(writer, 0L, 10L)) {
				assertThat(rangeWriter.getFromIndex()).isZero();
				assertThat(rangeWriter.getToIndex()).isEqualTo(10L);
				rangeWriter.write(CHARS[0]);
				rangeWriter.write(CHARS[1]);
				rangeWriter.write(CHARS[2]);
			}
			assertThat(writer.toCharArray()).containsExactly(CHARS);
		}
		try (var writer = new CharArrayWriter()) {
			try (var rangeWriter = new RangeWriter(writer, 10L, 10L)) {
				assertThat(rangeWriter.getFromIndex()).isEqualTo(10L);
				assertThat(rangeWriter.getToIndex()).isEqualTo(10L);
				rangeWriter.write(CHARS[0]);
				rangeWriter.write(CHARS[1]);
				rangeWriter.write(CHARS[2]);
			}
			assertThat(writer.toCharArray()).isEmpty();
		}
	}

	@Test
	void testWriteChars() throws IOException {
		try (var writer = new CharArrayWriter()) {
			try (var rangeWriter = new RangeWriter(writer, 0L, 0L)) {
				assertThat(rangeWriter.getFromIndex()).isZero();
				assertThat(rangeWriter.getToIndex()).isZero();
				rangeWriter.write(CHARS, 0, 0);
				rangeWriter.write(CHARS, 0, 2);
				rangeWriter.write(CHARS, 2, 1);
			}
			assertThat(writer.toCharArray()).containsExactly(CHARS[0]);
		}
		try (var writer = new CharArrayWriter()) {
			try (var rangeWriter = new RangeWriter(writer, 1L, 1L)) {
				assertThat(rangeWriter.getFromIndex()).isEqualTo(1L);
				assertThat(rangeWriter.getToIndex()).isEqualTo(1L);
				rangeWriter.write(CHARS, 0, 0);
				rangeWriter.write(CHARS, 0, 2);
				rangeWriter.write(CHARS, 2, 1);
			}
			assertThat(writer.toCharArray()).containsExactly(CHARS[1]);
		}
		try (var writer = new CharArrayWriter()) {
			try (var rangeWriter = new RangeWriter(writer, 0L, 10L)) {
				assertThat(rangeWriter.getFromIndex()).isZero();
				assertThat(rangeWriter.getToIndex()).isEqualTo(10L);
				rangeWriter.write(CHARS, 0, 0);
				rangeWriter.write(CHARS, 0, 2);
				rangeWriter.write(CHARS, 2, 1);
			}
			assertThat(writer.toCharArray()).containsExactly(CHARS);
		}
		try (var writer = new CharArrayWriter()) {
			try (var rangeWriter = new RangeWriter(writer, 10L, 10L)) {
				assertThat(rangeWriter.getFromIndex()).isEqualTo(10L);
				assertThat(rangeWriter.getToIndex()).isEqualTo(10L);
				rangeWriter.write(CHARS, 0, 0);
				rangeWriter.write(CHARS, 0, 2);
				rangeWriter.write(CHARS, 2, 1);
			}
			assertThat(writer.toCharArray()).isEmpty();
		}
	}

	@Test
	void testWriteCharsInvalid() throws IOException {
		try (var rangeWriter = new RangeWriter(Writers.EMPTY, 0L, 0L)) {
			assertThatNullPointerException().isThrownBy(() -> rangeWriter.write((char[]) null, 0, 2));
			assertThatIllegalArgumentException().isThrownBy(() -> rangeWriter.write(CHARS, -1, 3));
			assertThatIllegalArgumentException().isThrownBy(() -> rangeWriter.write(CHARS, 4, 3));
			assertThatIllegalArgumentException().isThrownBy(() -> rangeWriter.write(CHARS, 0, -1));
			assertThatIllegalArgumentException().isThrownBy(() -> rangeWriter.write(CHARS, 0, 4));
		}
	}

	@Test
	void testWriteString() throws IOException {
		try (var writer = new CharArrayWriter()) {
			try (var rangeWriter = new RangeWriter(writer, 0L, 0L)) {
				assertThat(rangeWriter.getFromIndex()).isZero();
				assertThat(rangeWriter.getToIndex()).isZero();
				rangeWriter.write(new String(CHARS), 0, 0);
				rangeWriter.write(new String(CHARS), 0, 2);
				rangeWriter.write(new String(CHARS), 2, 1);
			}
			assertThat(writer.toCharArray()).containsExactly(CHARS[0]);
		}
		try (var writer = new CharArrayWriter()) {
			try (var rangeWriter = new RangeWriter(writer, 1L, 1L)) {
				assertThat(rangeWriter.getFromIndex()).isEqualTo(1L);
				assertThat(rangeWriter.getToIndex()).isEqualTo(1L);
				rangeWriter.write(new String(CHARS), 0, 0);
				rangeWriter.write(new String(CHARS), 0, 2);
				rangeWriter.write(new String(CHARS), 2, 1);
			}
			assertThat(writer.toCharArray()).containsExactly(CHARS[1]);
		}
		try (var writer = new CharArrayWriter()) {
			try (var rangeWriter = new RangeWriter(writer, 0L, 10L)) {
				assertThat(rangeWriter.getFromIndex()).isZero();
				assertThat(rangeWriter.getToIndex()).isEqualTo(10L);
				rangeWriter.write(new String(CHARS), 0, 0);
				rangeWriter.write(new String(CHARS), 0, 2);
				rangeWriter.write(new String(CHARS), 2, 1);
			}
			assertThat(writer.toCharArray()).containsExactly(CHARS);
		}
		try (var writer = new CharArrayWriter()) {
			try (var rangeWriter = new RangeWriter(writer, 10L, 10L)) {
				assertThat(rangeWriter.getFromIndex()).isEqualTo(10L);
				assertThat(rangeWriter.getToIndex()).isEqualTo(10L);
				rangeWriter.write(new String(CHARS), 0, 0);
				rangeWriter.write(new String(CHARS), 0, 2);
				rangeWriter.write(new String(CHARS), 2, 1);
			}
			assertThat(writer.toCharArray()).isEmpty();
		}
	}

	@Test
	void testWriteStringInvalid() throws IOException {
		try (var rangeWriter = new RangeWriter(Writers.EMPTY, 0L, 0L)) {
			assertThatNullPointerException().isThrownBy(() -> rangeWriter.write((String) null, 0, 2));
			assertThatIllegalArgumentException().isThrownBy(() -> rangeWriter.write(new String(CHARS), -1, 3));
			assertThatIllegalArgumentException().isThrownBy(() -> rangeWriter.write(new String(CHARS), 4, 3));
			assertThatIllegalArgumentException().isThrownBy(() -> rangeWriter.write(new String(CHARS), 0, -1));
			assertThatIllegalArgumentException().isThrownBy(() -> rangeWriter.write(new String(CHARS), 0, 4));
		}
	}
}