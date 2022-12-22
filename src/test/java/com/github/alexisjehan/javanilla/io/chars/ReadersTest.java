/*
 * MIT License
 *
 * Copyright (c) 2018-2022 Alexis Jehan
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

import com.github.alexisjehan.javanilla.lang.Strings;
import com.github.alexisjehan.javanilla.lang.array.CharArrays;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.nio.CharBuffer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIOException;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.assertj.core.api.Assertions.assertThatNullPointerException;

@SuppressWarnings("deprecation")
final class ReadersTest {

	private static final char[] CHARS = CharArrays.of('a', 'b', 'c');

	@Test
	void testEmpty() throws IOException {
		final var buffer = new char[2];
		try (final var emptyReader = Readers.EMPTY) {
			assertThat(emptyReader.read(CharBuffer.wrap(buffer))).isEqualTo(-1);
			assertThatNullPointerException().isThrownBy(() -> emptyReader.read((CharBuffer) null));
			assertThat(emptyReader.read()).isEqualTo(-1);
			assertThat(emptyReader.read(CharArrays.EMPTY)).isZero();
			assertThat(emptyReader.read(buffer)).isEqualTo(-1);
			assertThatNullPointerException().isThrownBy(() -> emptyReader.read((char[]) null));
			assertThat(emptyReader.read(buffer, 0, 0)).isZero();
			assertThat(emptyReader.read(buffer, 0, 1)).isEqualTo(-1);
			assertThatNullPointerException().isThrownBy(() -> emptyReader.read(null, 0, 2));
			assertThatIllegalArgumentException().isThrownBy(() -> emptyReader.read(buffer, -1, 2));
			assertThatIllegalArgumentException().isThrownBy(() -> emptyReader.read(buffer, 3, 2));
			assertThatIllegalArgumentException().isThrownBy(() -> emptyReader.read(buffer, 0, -1));
			assertThatIllegalArgumentException().isThrownBy(() -> emptyReader.read(buffer, 0, 3));
			assertThat(emptyReader.skip(1)).isZero();
			assertThat(emptyReader.transferTo(Writers.EMPTY)).isZero();
			assertThatNullPointerException().isThrownBy(() -> emptyReader.transferTo(null));
		}
	}

	@Test
	void testNullToEmpty() throws IOException {
		assertThat(Readers.toChars(Readers.nullToEmpty(null))).isEmpty();
		assertThat(Readers.toChars(Readers.nullToEmpty(Readers.EMPTY))).isEmpty();
		assertThat(Readers.toChars(Readers.nullToEmpty(Readers.of(CHARS)))).containsExactly(CHARS);
	}

	@Test
	void testNullToDefault() throws IOException {
		assertThat(Readers.toChars(Readers.nullToDefault(null, Readers.singleton('-')))).containsExactly('-');
		assertThat(Readers.toChars(Readers.nullToDefault(Readers.EMPTY, Readers.singleton('-')))).isEmpty();
		assertThat(Readers.toChars(Readers.nullToDefault(Readers.of(CHARS), Readers.singleton('-')))).containsExactly(CHARS);
	}

	@Test
	void testNullToDefaultInvalid() {
		assertThatNullPointerException().isThrownBy(() -> Readers.nullToDefault(Readers.of(CHARS), null));
	}

	@Test
	void testBuffered() throws IOException {
		try (final var reader = Readers.EMPTY) {
			assertThat(reader).isNotInstanceOf(BufferedReader.class);
			try (final var bufferedReader = Readers.buffered(reader)) {
				assertThat(reader).isNotSameAs(bufferedReader);
				assertThat(bufferedReader).isInstanceOf(BufferedReader.class);
			}
		}
		try (final var reader = new BufferedReader(Readers.EMPTY)) {
			assertThat(reader).isInstanceOf(BufferedReader.class);
			try (final var bufferedReader = Readers.buffered(reader)) {
				assertThat(reader).isSameAs(bufferedReader);
				assertThat(bufferedReader).isInstanceOf(BufferedReader.class);
			}
		}
	}

	@Test
	void testBufferedInvalid() {
		assertThatNullPointerException().isThrownBy(() -> Readers.buffered(null));
	}

	@Test
	void testMarkSupported() throws IOException {
		try (final var reader = Readers.EMPTY) {
			assertThat(reader.markSupported()).isFalse();
			try (final var markSupportedReader = Readers.markSupported(reader)) {
				assertThat(reader).isNotSameAs(markSupportedReader);
				assertThat(markSupportedReader.markSupported()).isTrue();
			}
		}
		try (final var reader = new BufferedReader(Readers.EMPTY)) {
			assertThat(reader.markSupported()).isTrue();
			try (final var markSupportedReader = Readers.markSupported(reader)) {
				assertThat(reader).isSameAs(markSupportedReader);
				assertThat(markSupportedReader.markSupported()).isTrue();
			}
		}
	}

	@Test
	void testMarkSupportedInvalid() {
		assertThatNullPointerException().isThrownBy(() -> Readers.markSupported(null));
	}

	@Test
	void testUncloseable() throws IOException {
		final var reader = new Reader() {
			@Override
			public int read(final char[] buffer, final int offset, final int length) {
				return -1;
			}

			@Override
			public void close() throws IOException {
				throw new IOException();
			}
		};
		assertThatIOException().isThrownBy(reader::close);
		Readers.uncloseable(reader).close();
	}

	@Test
	void testUncloseableInvalid() {
		assertThatNullPointerException().isThrownBy(() -> Readers.uncloseable(null));
	}

	@Test
	void testConcat() throws IOException {
		assertThat(Readers.toChars(Readers.concat())).isEmpty();
		assertThat(Readers.toChars(Readers.concat(Readers.singleton(CHARS[0])))).containsExactly(CHARS[0]);
		assertThat(Readers.toChars(Readers.concat(Readers.singleton(CHARS[0]), Readers.singleton(CHARS[1]), Readers.singleton(CHARS[2])))).containsExactly(CHARS);
		assertThat(Readers.toChars(Readers.concat(List.of(Readers.singleton(CHARS[0]), Readers.singleton(CHARS[1]), Readers.singleton(CHARS[2]))))).containsExactly(CHARS);
	}

	@Test
	void testConcatInvalid() {
		assertThatNullPointerException().isThrownBy(() -> Readers.concat((Reader[]) null));
		assertThatNullPointerException().isThrownBy(() -> Readers.concat((Reader) null));
		assertThatNullPointerException().isThrownBy(() -> Readers.concat((List<Reader>) null));
		assertThatNullPointerException().isThrownBy(() -> Readers.concat(Collections.singletonList(null)));
	}

	@Test
	void testConcatSequenceReader() throws IOException {
		final var buffer = new char[2];
		try (final var concatReader = Readers.concat(Readers.singleton(CHARS[0]), Readers.singleton(CHARS[1]), Readers.singleton(CHARS[2]))) {
			assertThat(concatReader.read(buffer, 0, 0)).isZero();
			assertThatNullPointerException().isThrownBy(() -> concatReader.read(null, 0, 2));
			assertThatIllegalArgumentException().isThrownBy(() -> concatReader.read(buffer, -1, 2));
			assertThatIllegalArgumentException().isThrownBy(() -> concatReader.read(buffer, 3, 2));
			assertThatIllegalArgumentException().isThrownBy(() -> concatReader.read(buffer, 0, -1));
			assertThatIllegalArgumentException().isThrownBy(() -> concatReader.read(buffer, 0, 3));
			while (-1 != concatReader.read()) {
				// Nothing to do
			}
			assertThat(concatReader.read(buffer, 0, 1)).isEqualTo(-1);
		}
		final var concatReader = Readers.concat(Readers.singleton(CHARS[0]), Readers.singleton(CHARS[1]), Readers.singleton(CHARS[2]));
		concatReader.close();
	}

	@Test
	void testJoin() throws IOException {
		assertThat(Readers.toChars(Readers.join(CharArrays.EMPTY, Readers.singleton(CHARS[0]), Readers.singleton(CHARS[1]), Readers.singleton(CHARS[2])))).containsExactly(CHARS);
		assertThat(Readers.toChars(Readers.join(CharArrays.singleton('-')))).isEmpty();
		assertThat(Readers.toChars(Readers.join(CharArrays.singleton('-'), Readers.singleton(CHARS[0])))).containsExactly(CHARS[0]);
		assertThat(Readers.toChars(Readers.join(CharArrays.singleton('-'), Readers.singleton(CHARS[0]), Readers.singleton(CHARS[1]), Readers.singleton(CHARS[2])))).containsExactly(CHARS[0], '-', CHARS[1], '-', CHARS[2]);
		assertThat(Readers.toChars(Readers.join(CharArrays.singleton('-'), List.of(Readers.singleton(CHARS[0]), Readers.singleton(CHARS[1]), Readers.singleton(CHARS[2]))))).containsExactly(CHARS[0], '-', CHARS[1], '-', CHARS[2]);
	}

	@Test
	void testJoinInvalid() {
		assertThatNullPointerException().isThrownBy(() -> Readers.join(null, Readers.of(CHARS)));
		assertThatNullPointerException().isThrownBy(() -> Readers.join(CharArrays.singleton('-'), (Reader[]) null));
		assertThatNullPointerException().isThrownBy(() -> Readers.join(CharArrays.singleton('-'), (Reader) null));
		assertThatNullPointerException().isThrownBy(() -> Readers.join(CharArrays.singleton('-'), (List<Reader>) null));
		assertThatNullPointerException().isThrownBy(() -> Readers.join(CharArrays.singleton('-'), Collections.singletonList(null)));
	}

	@Test
	void testLength() throws IOException {
		assertThat(Readers.length(Readers.EMPTY)).isZero();
		assertThat(Readers.length(Readers.of(CHARS))).isEqualTo(CHARS.length);
	}

	@Test
	void testLengthInvalid() {
		assertThatNullPointerException().isThrownBy(() -> Readers.length(null));
	}

	@Test
	void testSingleton() throws IOException {
		assertThat(Readers.toChars(Readers.singleton(CHARS[0]))).containsExactly(CHARS[0]);
	}

	@Test
	void testOfCharsAndToChars() throws IOException {
		assertThat(Readers.toChars(Readers.of())).isEmpty();
		assertThat(Readers.toChars(Readers.of(CHARS))).containsExactly(CHARS);
	}

	@Test
	void testOfCharsInvalid() {
		assertThatNullPointerException().isThrownBy(() -> Readers.of((char[]) null));
	}

	@Test
	void testToCharsInvalid() {
		assertThatNullPointerException().isThrownBy(() -> Readers.toChars(null));
	}

	@Test
	void testOfStringAndToString() throws IOException {
		assertThat(Readers.toString(Readers.of(Strings.EMPTY))).isEmpty();
		assertThat(Readers.toString(Readers.of(new String(CHARS)))).isEqualTo(new String(CHARS));
	}

	@Test
	void testOfStringInvalid() {
		assertThatNullPointerException().isThrownBy(() -> Readers.of((String) null));
	}

	@Test
	void testToStringInvalid() {
		assertThatNullPointerException().isThrownBy(() -> Readers.toString(null));
	}

	@Test
	void testOfPath(@TempDir final Path tmpDirectory) throws IOException {
		final var tmpFile = tmpDirectory.resolve("testOfPath");
		Files.write(tmpFile, new String(CHARS).getBytes());
		try (final var reader = Readers.of(tmpFile)) {
			assertThat(Readers.toChars(reader)).containsExactly(CHARS);
		}
	}

	@Test
	void testOfPathInvalid(@TempDir final Path tmpDirectory) {
		assertThatNullPointerException().isThrownBy(() -> Readers.of((Path) null));
		assertThatNullPointerException().isThrownBy(() -> Readers.of(tmpDirectory.resolve("testOfPathInvalid"), null));
	}
}