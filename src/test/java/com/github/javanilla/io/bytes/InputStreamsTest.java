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
package com.github.javanilla.io.bytes;

import com.github.javanilla.io.chars.Readers;
import com.github.javanilla.lang.array.ByteArrays;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.invoke.MethodHandles;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Objects;

import static org.assertj.core.api.Assertions.*;

/**
 * <p>{@link InputStreams} unit tests.</p>
 */
final class InputStreamsTest {

	private static final Path INPUT = new File(Objects.requireNonNull(MethodHandles.lookup().lookupClass().getClassLoader().getResource("input.txt")).getFile()).toPath();

	@Test
	void testEmpty() {
		try (final var emptyInputStream = InputStreams.EMPTY) {
			assertThat(emptyInputStream.read()).isEqualTo(-1);
		} catch (final IOException e) {
			fail(e.getMessage());
		}
	}

	@Test
	void testEndless() {
		try (final var endlessInputStream = InputStreams.ENDLESS) {
			assertThat(endlessInputStream.read()).isNotEqualTo(-1);
		} catch (final IOException e) {
			fail(e.getMessage());
		}
	}

	@Test
	void testNullToEmpty() {
		assertThat(InputStreams.nullToEmpty(null)).isSameAs(InputStreams.EMPTY);
		assertThat(InputStreams.nullToEmpty(InputStreams.EMPTY)).isSameAs(InputStreams.EMPTY);
		assertThat(InputStreams.nullToEmpty(InputStreams.ENDLESS)).isNotSameAs(InputStreams.EMPTY);
	}

	@Test
	void testBuffered() {
		try {
			try (final var inputStream = InputStreams.EMPTY) {
				assertThat(inputStream).isNotSameAs(InputStreams.buffered(inputStream));
				assertThat(InputStreams.buffered(inputStream)).isInstanceOf(BufferedInputStream.class);
			}
			try (final var bufferedInputStream = new BufferedInputStream(InputStreams.EMPTY)) {
				assertThat(bufferedInputStream).isSameAs(InputStreams.buffered(bufferedInputStream));
				assertThat(InputStreams.buffered(bufferedInputStream)).isInstanceOf(BufferedInputStream.class);
			}
		} catch (final IOException e) {
			fail(e.getMessage());
		}
	}

	@Test
	void testBufferedNull() {
		assertThatNullPointerException().isThrownBy(() -> InputStreams.buffered(null));
	}

	@Test
	void testMarkSupported() {
		try {
			try (final var inputStream = InputStreams.EMPTY) {
				assertThat(inputStream).isNotSameAs(InputStreams.markSupported(inputStream));
				assertThat(inputStream.markSupported()).isFalse();
				assertThat(InputStreams.markSupported(inputStream).markSupported()).isTrue();
			}
			try (final var bufferedInputStream = new BufferedInputStream(InputStreams.EMPTY)) {
				assertThat(bufferedInputStream).isSameAs(InputStreams.markSupported(bufferedInputStream));
				assertThat(bufferedInputStream.markSupported()).isTrue();
				assertThat(InputStreams.markSupported(bufferedInputStream).markSupported()).isTrue();
			}
		} catch (final IOException e) {
			fail(e.getMessage());
		}
	}

	@Test
	void testMarkSupportedNull() {
		assertThatNullPointerException().isThrownBy(() -> InputStreams.markSupported(null));
	}

	@Test
	void testUncloseable() {
		try {
			try (final var closeableInputStream = Files.newInputStream(INPUT)) {
				assertThat(closeableInputStream.read()).isNotEqualTo(-1);
				closeableInputStream.close();
				assertThatIOException().isThrownBy(closeableInputStream::read);
			}
			try (final var uncloseableInputStream = InputStreams.uncloseable(Files.newInputStream(INPUT))) {
				assertThat(uncloseableInputStream.read()).isNotEqualTo(-1);
				uncloseableInputStream.close();
				assertThat(uncloseableInputStream.read()).isNotEqualTo(-1);
			}
		} catch (final IOException e) {
			fail(e.getMessage());
		}
	}

	@Test
	void testUncloseableNull() {
		assertThatNullPointerException().isThrownBy(() -> InputStreams.uncloseable(null));
	}

	@Test
	void testLength() {
		try {
			try (final var emptyInputStream = InputStreams.EMPTY) {
				assertThat(InputStreams.length(emptyInputStream)).isEqualTo(0L);
			}
			try (final var inputStream = Files.newInputStream(INPUT)) {
				assertThat(InputStreams.length(inputStream)).isEqualTo(INPUT.toFile().length());
			}
		} catch (final IOException e) {
			fail(e.getMessage());
		}
	}

	@Test
	void testLengthNull() {
		assertThatNullPointerException().isThrownBy(() -> InputStreams.length(null));
	}

	@Test
	void testConcat() {
		try (final var concatInputStream = InputStreams.concat(Files.newInputStream(INPUT), Files.newInputStream(INPUT))) {
			assertThat(InputStreams.toBytes(concatInputStream)).isEqualTo(ByteArrays.concat(Files.readAllBytes(INPUT), Files.readAllBytes(INPUT)));
		} catch (final IOException e) {
			fail(e.getMessage());
		}
	}

	@Test
	void testConcatOne() {
		try (final var concatInputStream = InputStreams.concat(Files.newInputStream(INPUT))) {
			assertThat(InputStreams.toBytes(concatInputStream)).isEqualTo(Files.readAllBytes(INPUT));
		} catch (final IOException e) {
			fail(e.getMessage());
		}
	}

	@Test
	void testConcatNone() {
		try (final var concatInputStream = InputStreams.concat()) {
			assertThat(InputStreams.toBytes(concatInputStream)).isEmpty();
		} catch (final IOException e) {
			fail(e.getMessage());
		}
	}

	@Test
	void testConcatNull() {
		assertThatNullPointerException().isThrownBy(() -> InputStreams.concat((InputStream[]) null));
		assertThatNullPointerException().isThrownBy(() -> InputStreams.concat((List<InputStream>) null));
		assertThatNullPointerException().isThrownBy(() -> InputStreams.concat((InputStream) null));
	}

	@Test
	void testJoin() {
		try (final var joinInputStream = InputStreams.join(" - ".getBytes(), Files.newInputStream(INPUT), Files.newInputStream(INPUT))) {
			assertThat(InputStreams.toBytes(joinInputStream)).isEqualTo(ByteArrays.join(" - ".getBytes(), Files.readAllBytes(INPUT), Files.readAllBytes(INPUT)));
		} catch (final IOException e) {
			fail(e.getMessage());
		}
	}

	@Test
	void testJoinEmptySeparator() {
		try (final var joinInputStream = InputStreams.join(ByteArrays.EMPTY, Files.newInputStream(INPUT), Files.newInputStream(INPUT))) {
			assertThat(InputStreams.toBytes(joinInputStream)).isEqualTo(ByteArrays.concat(Files.readAllBytes(INPUT), Files.readAllBytes(INPUT)));
		} catch (final IOException e) {
			fail(e.getMessage());
		}
	}

	@Test
	void testJoinOne() {
		try (final var joinInputStream = InputStreams.join(" - ".getBytes(), Files.newInputStream(INPUT))) {
			assertThat(InputStreams.toBytes(joinInputStream)).isEqualTo(Files.readAllBytes(INPUT));
		} catch (final IOException e) {
			fail(e.getMessage());
		}
	}

	@Test
	void testJoinNone() {
		try (final var joinInputStream = InputStreams.join(" - ".getBytes())) {
			assertThat(InputStreams.toBytes(joinInputStream)).isEmpty();
		} catch (final IOException e) {
			fail(e.getMessage());
		}
	}

	@Test
	void testJoinNull() {
		assertThatNullPointerException().isThrownBy(() -> InputStreams.join(null, InputStreams.EMPTY));
		assertThatNullPointerException().isThrownBy(() -> InputStreams.join(ByteArrays.EMPTY, (InputStream[]) null));
		assertThatNullPointerException().isThrownBy(() -> InputStreams.join(ByteArrays.EMPTY, (List<InputStream>) null));
		assertThatNullPointerException().isThrownBy(() -> InputStreams.join(ByteArrays.EMPTY, (InputStream) null));
	}

	@Test
	void testOfBytesAndToBytes() {
		try {
			assertThat(InputStreams.toBytes(InputStreams.of(ByteArrays.EMPTY))).isEmpty();
			assertThat(InputStreams.toBytes(InputStreams.of((byte) 0, (byte) 255))).containsExactly((byte) 0, (byte) 255);
			assertThat(InputStreams.toBytes(InputStreams.of(Files.readAllBytes(INPUT)))).isEqualTo(Files.readAllBytes(INPUT));
		} catch (final IOException e) {
			fail(e.getMessage());
		}
	}

	@Test
	void testOfBytesNull() {
		assertThatNullPointerException().isThrownBy(() -> InputStreams.of((byte[]) null));
	}

	@Test
	void testToBytesNull() {
		assertThatNullPointerException().isThrownBy(() -> InputStreams.toBytes(null));
	}

	@Test
	void testOfStringAndToString() {
		try {
			assertThat(InputStreams.toString(InputStreams.of(""))).isEmpty();
			assertThat(InputStreams.toString(InputStreams.of(new String(Files.readAllBytes(INPUT), StandardCharsets.ISO_8859_1), StandardCharsets.ISO_8859_1))).isEqualTo(new String(Files.readAllBytes(INPUT), StandardCharsets.ISO_8859_1));

			// Not the same charset
			assertThat(InputStreams.toString(InputStreams.of(new String(Files.readAllBytes(INPUT), StandardCharsets.UTF_8), StandardCharsets.UTF_16))).isNotEqualTo(new String(Files.readAllBytes(INPUT), StandardCharsets.UTF_8));
		} catch (final IOException e) {
			fail(e.getMessage());
		}
	}

	@Test
	void testOfStringNull() {
		assertThatNullPointerException().isThrownBy(() -> InputStreams.of((String) null));
		assertThatNullPointerException().isThrownBy(() -> InputStreams.of("", null));
	}

	@Test
	void testToStringNull() {
		assertThatNullPointerException().isThrownBy(() -> InputStreams.toString(null));
		assertThatNullPointerException().isThrownBy(() -> InputStreams.toString(InputStreams.EMPTY, null));
	}

	@Test
	void testToReader() {
		try {
			Assertions.assertThat(Readers.toString(InputStreams.toReader(InputStreams.of("")))).isEmpty();
			assertThat(Readers.toString(InputStreams.toReader(InputStreams.of(new String(Files.readAllBytes(INPUT), StandardCharsets.ISO_8859_1), StandardCharsets.ISO_8859_1)))).isEqualTo(new String(Files.readAllBytes(INPUT), StandardCharsets.ISO_8859_1));

			// Not the same charset
			assertThat(Readers.toString(InputStreams.toReader(InputStreams.of(new String(Files.readAllBytes(INPUT), StandardCharsets.UTF_8), StandardCharsets.UTF_16)))).isNotEqualTo(new String(Files.readAllBytes(INPUT), StandardCharsets.ISO_8859_1));
		} catch (final IOException e) {
			fail(e.getMessage());
		}
	}

	@Test
	void testToReaderNull() {
		assertThatNullPointerException().isThrownBy(() -> InputStreams.toReader(null));
		assertThatNullPointerException().isThrownBy(() -> InputStreams.toReader(InputStreams.EMPTY, null));
	}
}