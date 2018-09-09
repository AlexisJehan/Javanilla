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
package com.github.alexisjehan.javanilla.io.chars;

import com.github.alexisjehan.javanilla.lang.array.CharArrays;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.assertj.core.api.Assertions.*;

/**
 * <p>{@link CountWriter} unit tests.</p>
 */
final class CountWriterTest {

	@Test
	void testConstructorInvalid() {
		assertThatNullPointerException().isThrownBy(() -> new CountWriter(null));
	}

	@Test
	void testWriteChar() throws IOException {
		try (final var countWriter = new CountWriter(Writers.EMPTY)) {
			assertThat(countWriter.getCount()).isEqualTo(0L);
			countWriter.write('a');
			assertThat(countWriter.getCount()).isEqualTo(1L);
			countWriter.write('b');
			assertThat(countWriter.getCount()).isEqualTo(2L);
			countWriter.write('c');
			assertThat(countWriter.getCount()).isEqualTo(3L);
		}
	}

	@Test
	void testWriteChars() throws IOException {
		final var chars = CharArrays.of('a', 'b', 'c');
		try (final var countWriter = new CountWriter(Writers.EMPTY)) {
			assertThat(countWriter.getCount()).isEqualTo(0L);
			countWriter.write(chars, 0, 0);
			assertThat(countWriter.getCount()).isEqualTo(0L);
			countWriter.write(chars, 0, 2);
			assertThat(countWriter.getCount()).isEqualTo(2L);
		}
	}

	@Test
	void testWriteCharsInvalid() throws IOException {
		final var chars = CharArrays.of('a', 'b', 'c');
		try (final var countWriter = new CountWriter(Writers.EMPTY)) {
			assertThatNullPointerException().isThrownBy(() -> countWriter.write((char[]) null, 0, 2));
			assertThatExceptionOfType(IndexOutOfBoundsException.class).isThrownBy(() -> countWriter.write(chars, -1, 3));
			assertThatExceptionOfType(IndexOutOfBoundsException.class).isThrownBy(() -> countWriter.write(chars, 4, 3));
			assertThatExceptionOfType(IndexOutOfBoundsException.class).isThrownBy(() -> countWriter.write(chars, 0, -1));
			assertThatExceptionOfType(IndexOutOfBoundsException.class).isThrownBy(() -> countWriter.write(chars, 0, 4));
		}
	}

	@Test
	void testWriteString() throws IOException {
		final var string = "abc";
		try (final var countWriter = new CountWriter(Writers.EMPTY)) {
			assertThat(countWriter.getCount()).isEqualTo(0L);
			countWriter.write(string, 0, 0);
			assertThat(countWriter.getCount()).isEqualTo(0L);
			countWriter.write(string, 0, 2);
			assertThat(countWriter.getCount()).isEqualTo(2L);
		}
	}

	@Test
	void testWriteStringInvalid() throws IOException {
		final var string = "abc";
		try (final var countWriter = new CountWriter(Writers.EMPTY)) {
			assertThatNullPointerException().isThrownBy(() -> countWriter.write((String) null, 0, 2));
			assertThatExceptionOfType(IndexOutOfBoundsException.class).isThrownBy(() -> countWriter.write(string, -1, 3));
			assertThatExceptionOfType(IndexOutOfBoundsException.class).isThrownBy(() -> countWriter.write(string, 4, 3));
			assertThatExceptionOfType(IndexOutOfBoundsException.class).isThrownBy(() -> countWriter.write(string, 0, -1));
			assertThatExceptionOfType(IndexOutOfBoundsException.class).isThrownBy(() -> countWriter.write(string, 0, 4));
		}
	}
}