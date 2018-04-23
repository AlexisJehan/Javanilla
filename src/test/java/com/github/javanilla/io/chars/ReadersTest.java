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
package com.github.javanilla.io.chars;

import com.github.javanilla.lang.array.CharArrays;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.lang.invoke.MethodHandles;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Objects;

import static org.assertj.core.api.Assertions.*;

/**
 * <p>{@link Readers} unit tests.</p>
 */
final class ReadersTest {

	private static final Path INPUT = new File(Objects.requireNonNull(MethodHandles.lookup().lookupClass().getClassLoader().getResource("input.txt")).getFile()).toPath();

	@Test
	void testEmpty() {
		try (final var emptyReader = Readers.EMPTY) {
			assertThat(emptyReader.read()).isEqualTo(-1);
			final var buffer = new char[2];
			assertThat(emptyReader.read(buffer, 0, 0)).isEqualTo(0);
			assertThat(emptyReader.read(buffer, 0, 2)).isEqualTo(-1);
		} catch (final IOException e) {
			fail(e.getMessage());
		}
	}

	@Test
	void testEndless() {
		try (final var endlessReader = Readers.ENDLESS) {
			assertThat(endlessReader.read()).isNotEqualTo(-1);
			final var buffer = new char[2];
			assertThat(endlessReader.read(buffer, 0, 0)).isEqualTo(0);
			assertThat(endlessReader.read(buffer, 0, 2)).isNotEqualTo(-1);
			assertThatExceptionOfType(IndexOutOfBoundsException.class).isThrownBy(() -> endlessReader.read(buffer, -1,  2));
			assertThatExceptionOfType(IndexOutOfBoundsException.class).isThrownBy(() -> endlessReader.read(buffer,  3,  2));
			assertThatExceptionOfType(IndexOutOfBoundsException.class).isThrownBy(() -> endlessReader.read(buffer,  0, -1));
			assertThatExceptionOfType(IndexOutOfBoundsException.class).isThrownBy(() -> endlessReader.read(buffer,  0,  3));
		} catch (final IOException e) {
			fail(e.getMessage());
		}
	}

	@Test
	void testNullToEmpty() {
		assertThat(Readers.nullToEmpty(null)).isSameAs(Readers.EMPTY);
		assertThat(Readers.nullToEmpty(Readers.EMPTY)).isSameAs(Readers.EMPTY);
		assertThat(Readers.nullToEmpty(Readers.ENDLESS)).isNotSameAs(Readers.EMPTY);
	}

	@Test
	void testBuffered() {
		try {
			try (final var reader = Readers.EMPTY) {
				assertThat(reader).isNotSameAs(Readers.buffered(reader));
				assertThat(Readers.buffered(reader)).isInstanceOf(BufferedReader.class);
			}
			try (final var bufferedReader = new BufferedReader(Readers.EMPTY)) {
				assertThat(bufferedReader).isSameAs(Readers.buffered(bufferedReader));
				assertThat(Readers.buffered(bufferedReader)).isInstanceOf(BufferedReader.class);
			}
		} catch (final IOException e) {
			fail(e.getMessage());
		}
	}

	@Test
	void testBufferedNull() {
		assertThatNullPointerException().isThrownBy(() -> Readers.buffered(null));
	}

	@Test
	void testMarkSupported() {
		try {
			try (final var reader = Readers.EMPTY) {
				assertThat(reader).isNotSameAs(Readers.markSupported(reader));
				assertThat(reader.markSupported()).isFalse();
				assertThat(Readers.markSupported(reader).markSupported()).isTrue();
			}
			try (final var bufferedReader = new BufferedReader(Readers.EMPTY)) {
				assertThat(bufferedReader).isSameAs(Readers.markSupported(bufferedReader));
				assertThat(bufferedReader.markSupported()).isTrue();
				assertThat(Readers.markSupported(bufferedReader).markSupported()).isTrue();
			}
		} catch (final IOException e) {
			fail(e.getMessage());
		}
	}

	@Test
	void testMarkSupportedNull() {
		assertThatNullPointerException().isThrownBy(() -> Readers.markSupported(null));
	}

	@Test
	void testUncloseable() {
		try {
			try (final var closeableReader = Files.newBufferedReader(INPUT)) {
				assertThat(closeableReader.read()).isNotEqualTo(-1);
				closeableReader.close();
				assertThatIOException().isThrownBy(closeableReader::read);
			}
			try (final var uncloseableReader = Readers.uncloseable(Files.newBufferedReader(INPUT))) {
				assertThat(uncloseableReader.read()).isNotEqualTo(-1);
				uncloseableReader.close();
				assertThat(uncloseableReader.read()).isNotEqualTo(-1);
			}
		} catch (final IOException e) {
			fail(e.getMessage());
		}
	}

	@Test
	void testUncloseableNull() {
		assertThatNullPointerException().isThrownBy(() -> Readers.uncloseable(null));
	}

	@Test
	void testLength() {
		try {
			try (final var emptyReader = Readers.EMPTY) {
				assertThat(Readers.length(emptyReader)).isEqualTo(0L);
			}
			try (final var reader = Files.newBufferedReader(INPUT)) {
				assertThat(Readers.length(reader)).isEqualTo(INPUT.toFile().length());
			}
		} catch (final IOException e) {
			fail(e.getMessage());
		}
	}

	@Test
	void testLengthNull() {
		assertThatNullPointerException().isThrownBy(() -> Readers.length(null));
	}

	@Test
	void testConcat() {
		try (final var concatReader = Readers.concat(Files.newBufferedReader(INPUT), Files.newBufferedReader(INPUT))) {
			assertThat(Readers.toChars(concatReader)).isEqualTo(CharArrays.concat(new String(Files.readAllBytes(INPUT)).toCharArray(), new String(Files.readAllBytes(INPUT)).toCharArray()));
		} catch (final IOException e) {
			fail(e.getMessage());
		}
	}

	@Test
	void testConcatOne() {
		try (final var concatReader = Readers.concat(Files.newBufferedReader(INPUT))) {
			assertThat(Readers.toChars(concatReader)).isEqualTo(new String(Files.readAllBytes(INPUT)).toCharArray());
		} catch (final IOException e) {
			fail(e.getMessage());
		}
	}

	@Test
	void testConcatNone() {
		try (final var concatReader = Readers.concat()) {
			assertThat(Readers.toChars(concatReader)).isEmpty();
		} catch (final IOException e) {
			fail(e.getMessage());
		}
	}

	@Test
	void testConcatSequenceReader() {
		try (final var concatReader = Readers.concat(Files.newBufferedReader(INPUT), Files.newBufferedReader(INPUT))) {
			final var buffer = new char[10];
			assertThat(concatReader.read(buffer, 0, 0)).isEqualTo(0);
			assertThatExceptionOfType(IndexOutOfBoundsException.class).isThrownBy(() -> concatReader.read(buffer, -1, 10));
			assertThatExceptionOfType(IndexOutOfBoundsException.class).isThrownBy(() -> concatReader.read(buffer, 11, 10));
			assertThatExceptionOfType(IndexOutOfBoundsException.class).isThrownBy(() -> concatReader.read(buffer,  0, -1));
			assertThatExceptionOfType(IndexOutOfBoundsException.class).isThrownBy(() -> concatReader.read(buffer,  0, 11));
			while (-1 != concatReader.read());
			assertThat(concatReader.read(buffer, 0, buffer.length)).isEqualTo(-1);
		} catch (final IOException e) {
			fail(e.getMessage());
		}
	}

	@Test
	void testConcatSequenceReaderClose() {
		try {
			final var concatReader = Readers.concat(Files.newBufferedReader(INPUT), Files.newBufferedReader(INPUT));
			concatReader.close();
		} catch (final IOException e) {
			fail(e.getMessage());
		}
	}

	@Test
	void testConcatNull() {
		assertThatNullPointerException().isThrownBy(() -> Readers.concat((Reader[]) null));
		assertThatNullPointerException().isThrownBy(() -> Readers.concat((List<Reader>) null));
		assertThatNullPointerException().isThrownBy(() -> Readers.concat((Reader) null));
	}

	@Test
	void testJoin() {
		try (final var joinReader = Readers.join(" - ".toCharArray(), Files.newBufferedReader(INPUT), Files.newBufferedReader(INPUT))) {
			assertThat(Readers.toChars(joinReader)).isEqualTo(CharArrays.join(" - ".toCharArray(), new String(Files.readAllBytes(INPUT)).toCharArray(), new String(Files.readAllBytes(INPUT)).toCharArray()));
		} catch (final IOException e) {
			fail(e.getMessage());
		}
	}

	@Test
	void testJoinEmptySeparator() {
		try (final var joinReader = Readers.join(CharArrays.EMPTY, Files.newBufferedReader(INPUT), Files.newBufferedReader(INPUT))) {
			assertThat(Readers.toChars(joinReader)).isEqualTo(CharArrays.concat(new String(Files.readAllBytes(INPUT)).toCharArray(), new String(Files.readAllBytes(INPUT)).toCharArray()));
		} catch (final IOException e) {
			fail(e.getMessage());
		}
	}

	@Test
	void testJoinOne() {
		try (final var joinReader = Readers.join(" - ".toCharArray(), Files.newBufferedReader(INPUT))) {
			assertThat(Readers.toChars(joinReader)).isEqualTo(new String(Files.readAllBytes(INPUT)).toCharArray());
		} catch (final IOException e) {
			fail(e.getMessage());
		}
	}

	@Test
	void testJoinNone() {
		try (final var joinReader = Readers.join(" - ".toCharArray())) {
			assertThat(Readers.toChars(joinReader)).isEmpty();
		} catch (final IOException e) {
			fail(e.getMessage());
		}
	}

	@Test
	void testJoinNull() {
		assertThatNullPointerException().isThrownBy(() -> Readers.join(null, Readers.EMPTY));
		assertThatNullPointerException().isThrownBy(() -> Readers.join(CharArrays.EMPTY, (Reader[]) null));
		assertThatNullPointerException().isThrownBy(() -> Readers.join(CharArrays.EMPTY, (List<Reader>) null));
		assertThatNullPointerException().isThrownBy(() -> Readers.join(CharArrays.EMPTY, (Reader) null));
	}

	@Test
	void testOfCharsAndToChars() {
		try {
			assertThat(Readers.toChars(Readers.of(CharArrays.EMPTY))).isEmpty();
			assertThat(Readers.toChars(Readers.of((char) 0, (char) 255))).containsExactly((char) 0, (char) 255);
			assertThat(Readers.toChars(Readers.of(new String(Files.readAllBytes(INPUT)).toCharArray()))).isEqualTo(new String(Files.readAllBytes(INPUT)).toCharArray());
		} catch (final IOException e) {
			fail(e.getMessage());
		}
	}

	@Test
	void testOfCharsNull() {
		assertThatNullPointerException().isThrownBy(() -> Readers.of((char[]) null));
	}

	@Test
	void testToCharsNull() {
		assertThatNullPointerException().isThrownBy(() -> Readers.toChars(null));
	}

	@Test
	void testOfStringAndToString() {
		try {
			assertThat(Readers.toString(Readers.of(""))).isEmpty();
			assertThat(Readers.toString(Readers.of(new String(Files.readAllBytes(INPUT), StandardCharsets.ISO_8859_1)))).isEqualTo(new String(Files.readAllBytes(INPUT), StandardCharsets.ISO_8859_1));

			// Not the same charset
			assertThat(Readers.toString(Readers.of(new String(Files.readAllBytes(INPUT), StandardCharsets.UTF_16)))).isNotEqualTo(new String(Files.readAllBytes(INPUT), StandardCharsets.UTF_8));
		} catch (final IOException e) {
			fail(e.getMessage());
		}
	}

	@Test
	void testOfStringNull() {
		assertThatNullPointerException().isThrownBy(() -> Readers.of((String) null));
	}

	@Test
	void testToStringNull() {
		assertThatNullPointerException().isThrownBy(() -> Readers.toString(null));
	}
}