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

import com.github.alexisjehan.javanilla.lang.Strings;
import com.github.alexisjehan.javanilla.lang.array.CharArrays;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.nio.CharBuffer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

/**
 * <p>{@link Readers} unit tests.</p>
 */
final class ReadersTest {

	@Test
	void testEmpty() throws IOException {
		final var buffer = new char[2];
		try (final var emptyReader = Readers.EMPTY) {
			assertThat(emptyReader.read(CharBuffer.wrap(buffer))).isEqualTo(-1);
			assertThatNullPointerException().isThrownBy(() -> emptyReader.read((CharBuffer) null));
			assertThat(emptyReader.read()).isEqualTo(-1);
			assertThat(emptyReader.read(CharArrays.EMPTY)).isEqualTo(0);
			assertThat(emptyReader.read(buffer)).isEqualTo(-1);
			assertThatNullPointerException().isThrownBy(() -> emptyReader.read((char[]) null));
			assertThat(emptyReader.read(buffer, 0, 0)).isEqualTo(0);
			assertThat(emptyReader.read(buffer, 0, 1)).isEqualTo(-1);
			assertThatNullPointerException().isThrownBy(() -> emptyReader.read(null, 0, 2));
			assertThatExceptionOfType(IndexOutOfBoundsException.class).isThrownBy(() -> emptyReader.read(buffer, -1, 2));
			assertThatExceptionOfType(IndexOutOfBoundsException.class).isThrownBy(() -> emptyReader.read(buffer, 3, 2));
			assertThatExceptionOfType(IndexOutOfBoundsException.class).isThrownBy(() -> emptyReader.read(buffer, 0, -1));
			assertThatExceptionOfType(IndexOutOfBoundsException.class).isThrownBy(() -> emptyReader.read(buffer, 0, 3));
			assertThat(emptyReader.skip(1)).isEqualTo(0);
			assertThat(emptyReader.transferTo(Writers.EMPTY)).isEqualTo(0L);
			assertThatNullPointerException().isThrownBy(() -> emptyReader.transferTo(null));
		}
	}

	@Test
	void testNullToEmpty() throws IOException {
		assertThat(Readers.toChars(Readers.nullToEmpty(null))).isEmpty();
		assertThat(Readers.toChars(Readers.nullToEmpty(Readers.EMPTY))).isEmpty();
		assertThat(Readers.toChars(Readers.nullToEmpty(Readers.singleton('a')))).containsExactly('a');
	}

	@Test
	void testNullToDefault() throws IOException {
		assertThat(Readers.toChars(Readers.nullToDefault(null, Readers.singleton('-')))).containsExactly('-');
		assertThat(Readers.toChars(Readers.nullToDefault(Readers.EMPTY, Readers.singleton('-')))).isEmpty();
		assertThat(Readers.toChars(Readers.nullToDefault(Readers.singleton('a'), Readers.singleton('-')))).containsExactly('a');
	}

	@Test
	void testNullToDefaultInvalid() {
		assertThatNullPointerException().isThrownBy(() -> Readers.nullToDefault(Readers.singleton('a'), null));
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
		{
			Readers.uncloseable(reader).close();
		}
	}

	@Test
	void testUncloseableInvalid() {
		assertThatNullPointerException().isThrownBy(() -> Readers.uncloseable(null));
	}

	@Test
	void testLength() throws IOException {
		assertThat(Readers.length(Readers.EMPTY)).isEqualTo(0L);
		assertThat(Readers.length(Readers.of('a', 'b', 'c'))).isEqualTo(3L);
	}

	@Test
	void testLengthInvalid() {
		assertThatNullPointerException().isThrownBy(() -> Readers.length(null));
	}

	@Test
	void testConcat() throws IOException {
		assertThat(Readers.toChars(Readers.concat())).isEmpty();
		assertThat(Readers.toChars(Readers.concat(Readers.singleton('a')))).containsExactly('a');
		assertThat(Readers.toChars(Readers.concat(Readers.singleton('a'), Readers.singleton('b')))).containsExactly('a', 'b');
	}

	@Test
	void testConcatInvalid() {
		assertThatNullPointerException().isThrownBy(() -> Readers.concat((Reader[]) null));
		assertThatNullPointerException().isThrownBy(() -> Readers.concat((List<Reader>) null));
		assertThatNullPointerException().isThrownBy(() -> Readers.concat((Reader) null));
	}

	@Test
	void testConcatSequenceReader() throws IOException {
		final var buffer = new char[2];
		try (final var concatReader = Readers.concat(Readers.of('a', 'b', 'c'), Readers.of('d', 'e', 'f'))) {
			assertThat(concatReader.read(buffer, 0, 0)).isEqualTo(0);
			assertThatNullPointerException().isThrownBy(() -> concatReader.read(null, 0, 2));
			assertThatExceptionOfType(IndexOutOfBoundsException.class).isThrownBy(() -> concatReader.read(buffer, -1, 2));
			assertThatExceptionOfType(IndexOutOfBoundsException.class).isThrownBy(() -> concatReader.read(buffer, 3, 2));
			assertThatExceptionOfType(IndexOutOfBoundsException.class).isThrownBy(() -> concatReader.read(buffer, 0, -1));
			assertThatExceptionOfType(IndexOutOfBoundsException.class).isThrownBy(() -> concatReader.read(buffer, 0, 3));
			while (-1 != concatReader.read());
			assertThat(concatReader.read(buffer, 0, 1)).isEqualTo(-1);
		}
		final var concatReader = Readers.concat(Readers.of('a', 'b', 'c'), Readers.of('d', 'e', 'f'));
		concatReader.close();
	}

	@Test
	void testJoin() throws IOException {
		assertThat(Readers.toChars(Readers.join(CharArrays.EMPTY, Readers.singleton('a'), Readers.singleton('b')))).containsExactly('a', 'b');
		assertThat(Readers.toChars(Readers.join(CharArrays.singleton('-')))).isEmpty();
		assertThat(Readers.toChars(Readers.join(CharArrays.singleton('-'), Readers.singleton('a')))).containsExactly('a');
		assertThat(Readers.toChars(Readers.join(CharArrays.singleton('-'), Readers.singleton('a'), Readers.singleton('b')))).containsExactly('a', '-', 'b');
	}

	@Test
	void testJoinInvalid() {
		assertThatNullPointerException().isThrownBy(() -> Readers.join(null, Readers.singleton('a')));
		assertThatNullPointerException().isThrownBy(() -> Readers.join(CharArrays.singleton('-'), (Reader[]) null));
		assertThatNullPointerException().isThrownBy(() -> Readers.join(CharArrays.singleton('-'), (List<Reader>) null));
		assertThatNullPointerException().isThrownBy(() -> Readers.join(CharArrays.singleton('-'), (Reader) null));
	}

	@Test
	void testSingleton() throws IOException {
		assertThat(Readers.toChars(Readers.singleton('a'))).containsExactly('a');
	}

	@Test
	void testOfCharsAndToChars() throws IOException {
		assertThat(Readers.toChars(Readers.of())).isEmpty();
		assertThat(Readers.toChars(Readers.of('a', 'b', 'c'))).containsExactly('a', 'b', 'c');
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
		assertThat(Readers.toString(Readers.of(new String("foo".getBytes(), StandardCharsets.ISO_8859_1)))).isEqualTo(new String("foo".getBytes(), StandardCharsets.ISO_8859_1));

		// Not the same charset
		assertThat(Readers.toString(Readers.of(new String("foo".getBytes(), StandardCharsets.UTF_16)))).isNotEqualTo(new String("foo".getBytes(), StandardCharsets.UTF_8));
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
	void testOfPath() throws IOException {
		final var path = File.createTempFile(getClass().getName() + ".testOfPath_", ".txt").toPath();
		Files.write(path, "abc".getBytes());
		try (final var pathReader = Readers.of(path)) {
			assertThat(Readers.toChars(pathReader)).containsExactly('a', 'b', 'c');
		}
		Files.delete(path);
	}

	@Test
	void testOfPathInvalid() {
		assertThatNullPointerException().isThrownBy(() -> Readers.of((Path) null));
		assertThatNullPointerException().isThrownBy(() -> Readers.of(Paths.get(getClass().getName() + ".testOfPathInvalid.txt"), null));
	}
}