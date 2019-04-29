/*
 * MIT License
 *
 * Copyright (c) 2018-2019 Alexis Jehan
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
 * <p>{@link CountWriter} unit tests.</p>
 */
final class CountWriterTest {

	private static final char[] CHARS = CharArrays.of('a', 'b', 'c');
	private static final String STRING = "abc";

	@Test
	void testConstructorInvalid() {
		assertThatNullPointerException().isThrownBy(() -> new CountWriter(null));
	}

	@Test
	@SuppressWarnings("deprecation")
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
	@SuppressWarnings("deprecation")
	void testWriteChars() throws IOException {
		try (final var countWriter = new CountWriter(Writers.EMPTY)) {
			assertThat(countWriter.getCount()).isEqualTo(0L);
			countWriter.write(CHARS, 0, 0);
			assertThat(countWriter.getCount()).isEqualTo(0L);
			countWriter.write(CHARS, 0, 2);
			assertThat(countWriter.getCount()).isEqualTo(2L);
		}
	}

	@Test
	@SuppressWarnings("deprecation")
	void testWriteCharsInvalid() throws IOException {
		try (final var countWriter = new CountWriter(Writers.EMPTY)) {
			assertThatNullPointerException().isThrownBy(() -> countWriter.write((char[]) null, 0, 2));
			assertThatIllegalArgumentException().isThrownBy(() -> countWriter.write(CHARS, -1, 3));
			assertThatIllegalArgumentException().isThrownBy(() -> countWriter.write(CHARS, 4, 3));
			assertThatIllegalArgumentException().isThrownBy(() -> countWriter.write(CHARS, 0, -1));
			assertThatIllegalArgumentException().isThrownBy(() -> countWriter.write(CHARS, 0, 4));
		}
	}

	@Test
	@SuppressWarnings("deprecation")
	void testWriteString() throws IOException {
		try (final var countWriter = new CountWriter(Writers.EMPTY)) {
			assertThat(countWriter.getCount()).isEqualTo(0L);
			countWriter.write(STRING, 0, 0);
			assertThat(countWriter.getCount()).isEqualTo(0L);
			countWriter.write(STRING, 0, 2);
			assertThat(countWriter.getCount()).isEqualTo(2L);
		}
	}

	@Test
	@SuppressWarnings("deprecation")
	void testWriteStringInvalid() throws IOException {
		try (final var countWriter = new CountWriter(Writers.EMPTY)) {
			assertThatNullPointerException().isThrownBy(() -> countWriter.write((String) null, 0, 2));
			assertThatIllegalArgumentException().isThrownBy(() -> countWriter.write(STRING, -1, 3));
			assertThatIllegalArgumentException().isThrownBy(() -> countWriter.write(STRING, 4, 3));
			assertThatIllegalArgumentException().isThrownBy(() -> countWriter.write(STRING, 0, -1));
			assertThatIllegalArgumentException().isThrownBy(() -> countWriter.write(STRING, 0, 4));
		}
	}
}