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
package com.github.javanilla.io.lines;

import com.github.javanilla.io.chars.Readers;
import com.github.javanilla.lang.Strings;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.lang.invoke.MethodHandles;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

import static org.assertj.core.api.Assertions.*;

/**
 * <p>{@link LineSeparator} unit tests.</p>
 */
final class LineSeparatorTest {

	private static final Path INPUT = new File(Objects.requireNonNull(MethodHandles.lookup().lookupClass().getClassLoader().getResource("input.txt")).getFile()).toPath();

	@Test
	void testReadLf() {
		try {
			try (final var reader = new StringReader("")) {
				final var builder = new StringBuilder();
				assertThat(LineSeparator.LF.read(reader, builder)).isEqualTo(-1);
				assertThat(builder.toString()).isEmpty();
			}
			try (final var reader = new StringReader("\nabc")) {
				final var builder = new StringBuilder();
				assertThat(LineSeparator.LF.read(reader, builder)).isNotEqualTo(-1);
				assertThat(builder.toString()).isEmpty();
				builder.setLength(0);
				assertThat(LineSeparator.LF.read(reader, builder)).isEqualTo(-1);
				assertThat(builder.toString()).isEqualTo("abc");
			}
			try (final var reader = new StringReader("abc\n")) {
				final var builder = new StringBuilder();
				assertThat(LineSeparator.LF.read(reader, builder)).isNotEqualTo(-1);
				assertThat(builder.toString()).isEqualTo("abc");
				builder.setLength(0);
				assertThat(LineSeparator.LF.read(reader, builder)).isEqualTo(-1);
				assertThat(builder.toString()).isEmpty();
			}
			try (final var reader = new StringReader("\rabc\n\ndef\r")) {
				final var builder = new StringBuilder();
				assertThat(LineSeparator.LF.read(reader, builder)).isNotEqualTo(-1);
				assertThat(builder.toString()).isEqualTo("\rabc");
				builder.setLength(0);
				assertThat(LineSeparator.LF.read(reader, builder)).isNotEqualTo(-1);
				assertThat(builder.toString()).isEmpty();
				builder.setLength(0);
				assertThat(LineSeparator.LF.read(reader, builder)).isEqualTo(-1);
				assertThat(builder.toString()).isEqualTo("def\r");
			}
		} catch (final IOException e) {
			fail(e.getMessage());
		}
	}

	@Test
	void testReadCrLf() {
		try {
			try (final var reader = new StringReader("")) {
				final var builder = new StringBuilder();
				assertThat(LineSeparator.CR_LF.read(reader, builder)).isEqualTo(-1);
				assertThat(builder.toString()).isEmpty();
			}
			try (final var reader = new StringReader("\r\nabc")) {
				final var builder = new StringBuilder();
				assertThat(LineSeparator.CR_LF.read(reader, builder)).isNotEqualTo(-1);
				assertThat(builder.toString()).isEmpty();
				builder.setLength(0);
				assertThat(LineSeparator.CR_LF.read(reader, builder)).isEqualTo(-1);
				assertThat(builder.toString()).isEqualTo("abc");
			}
			try (final var reader = new StringReader("abc\r\n")) {
				final var builder = new StringBuilder();
				assertThat(LineSeparator.CR_LF.read(reader, builder)).isNotEqualTo(-1);
				assertThat(builder.toString()).isEqualTo("abc");
				builder.setLength(0);
				assertThat(LineSeparator.CR_LF.read(reader, builder)).isEqualTo(-1);
				assertThat(builder.toString()).isEmpty();
			}
			try (final var reader = new StringReader("\rabc\r\n\r\ndef\n")) {
				final var builder = new StringBuilder();
				assertThat(LineSeparator.CR_LF.read(reader, builder)).isNotEqualTo(-1);
				assertThat(builder.toString()).isEqualTo("\rabc");
				builder.setLength(0);
				assertThat(LineSeparator.CR_LF.read(reader, builder)).isNotEqualTo(-1);
				assertThat(builder.toString()).isEmpty();
				builder.setLength(0);
				assertThat(LineSeparator.CR_LF.read(reader, builder)).isEqualTo(-1);
				assertThat(builder.toString()).isEqualTo("def\n");
			}
			try (final var reader = new StringReader("\nabc\r\n\r\ndef\r")) {
				final var builder = new StringBuilder();
				assertThat(LineSeparator.CR_LF.read(reader, builder)).isNotEqualTo(-1);
				assertThat(builder.toString()).isEqualTo("\nabc");
				builder.setLength(0);
				assertThat(LineSeparator.CR_LF.read(reader, builder)).isNotEqualTo(-1);
				assertThat(builder.toString()).isEmpty();
				builder.setLength(0);
				assertThat(LineSeparator.CR_LF.read(reader, builder)).isEqualTo(-1);
				assertThat(builder.toString()).isEqualTo("def\r");
			}
		} catch (final IOException e) {
			fail(e.getMessage());
		}
	}

	@Test
	void testReadCr() {
		try {
			try (final var reader = new StringReader("")) {
				final var builder = new StringBuilder();
				assertThat(LineSeparator.CR.read(reader, builder)).isEqualTo(-1);
				assertThat(builder.toString()).isEmpty();
			}
			try (final var reader = new StringReader("\rabc")) {
				final var builder = new StringBuilder();
				assertThat(LineSeparator.CR.read(reader, builder)).isNotEqualTo(-1);
				assertThat(builder.toString()).isEmpty();
				builder.setLength(0);
				assertThat(LineSeparator.CR.read(reader, builder)).isEqualTo(-1);
				assertThat(builder.toString()).isEqualTo("abc");
			}
			try (final var reader = new StringReader("abc\r")) {
				final var builder = new StringBuilder();
				assertThat(LineSeparator.CR.read(reader, builder)).isNotEqualTo(-1);
				assertThat(builder.toString()).isEqualTo("abc");
				builder.setLength(0);
				assertThat(LineSeparator.CR.read(reader, builder)).isEqualTo(-1);
				assertThat(builder.toString()).isEmpty();
			}
			try (final var reader = new StringReader("\nabc\r\rdef\n")) {
				final var builder = new StringBuilder();
				assertThat(LineSeparator.CR.read(reader, builder)).isNotEqualTo(-1);
				assertThat(builder.toString()).isEqualTo("\nabc");
				builder.setLength(0);
				assertThat(LineSeparator.CR.read(reader, builder)).isNotEqualTo(-1);
				assertThat(builder.toString()).isEmpty();
				builder.setLength(0);
				assertThat(LineSeparator.CR.read(reader, builder)).isEqualTo(-1);
				assertThat(builder.toString()).isEqualTo("def\n");
			}
		} catch (final IOException e) {
			fail(e.getMessage());
		}
	}

	@Test
	void testReadDefault() {
		try {
			try (final var reader = new StringReader("")) {
				final var builder = new StringBuilder();
				assertThat(LineSeparator.DEFAULT.read(reader, builder)).isEqualTo(-1);
				assertThat(builder.toString()).isEmpty();
			}
			try (final var reader = new StringReader("\rabc")) {
				final var builder = new StringBuilder();
				assertThat(LineSeparator.DEFAULT.read(reader, builder)).isNotEqualTo(-1);
				assertThat(builder.toString()).isEmpty();
				builder.setLength(0);
				assertThat(LineSeparator.DEFAULT.read(reader, builder)).isEqualTo(-1);
				assertThat(builder.toString()).isEqualTo("abc");
			}
			try (final var reader = new StringReader("abc\n")) {
				final var builder = new StringBuilder();
				assertThat(LineSeparator.DEFAULT.read(reader, builder)).isNotEqualTo(-1);
				assertThat(builder.toString()).isEqualTo("abc");
				builder.setLength(0);
				assertThat(LineSeparator.DEFAULT.read(reader, builder)).isEqualTo(-1);
				assertThat(builder.toString()).isEmpty();
			}
			try (final var reader = new StringReader("\nabc\r\ndef\r")) {
				final var builder = new StringBuilder();
				assertThat(LineSeparator.DEFAULT.read(reader, builder)).isNotEqualTo(-1);
				assertThat(builder.toString()).isEmpty();
				builder.setLength(0);
				assertThat(LineSeparator.DEFAULT.read(reader, builder)).isNotEqualTo(-1);
				assertThat(builder.toString()).isEqualTo("abc");
				builder.setLength(0);
				assertThat(LineSeparator.DEFAULT.read(reader, builder)).isNotEqualTo(-1);
				assertThat(builder.toString()).isEmpty();
				builder.setLength(0);
				assertThat(LineSeparator.DEFAULT.read(reader, builder)).isNotEqualTo(-1);
				assertThat(builder.toString()).isEqualTo("def");
				builder.setLength(0);
				assertThat(LineSeparator.DEFAULT.read(reader, builder)).isEqualTo(-1);
				assertThat(builder.toString()).isEmpty();
			}
		} catch (final IOException e) {
			fail(e.getMessage());
		}
	}

	@Test
	void testDetect() {
		try {
			assertThat(LineSeparator.detect(new StringReader(""))).isEqualTo(LineSeparator.DEFAULT);
			assertThat(LineSeparator.detect(new StringReader("\nabcdef"))).isEqualTo(LineSeparator.LF);
			assertThat(LineSeparator.detect(new StringReader("abc\r\ndef"))).isEqualTo(LineSeparator.CR_LF);
			assertThat(LineSeparator.detect(new StringReader("abcdef\r"))).isEqualTo(LineSeparator.CR);
			assertThat(LineSeparator.detect(new StringReader("abc\ndef\r\nghi\rjkl"))).isEqualTo(LineSeparator.DEFAULT);
			assertThat(LineSeparator.detect(new StringReader("abc\ndef\r\rghi"))).isEqualTo(LineSeparator.CR);
			assertThat(LineSeparator.detect(new StringReader(Strings.repeat(' ', 10_000) + "\n"))).isEqualTo(LineSeparator.DEFAULT);
			assertThat(LineSeparator.detect(Files.newBufferedReader(INPUT))).isEqualTo(LineSeparator.DEFAULT);
		} catch (final IOException e) {
			fail(e.getMessage());
		}
	}

	@Test
	void testDetectMark() {
		try {
			try (final var reader = new StringReader("abcdef")) {
				assertThat(LineSeparator.detect(reader)).isEqualTo(LineSeparator.DEFAULT);
				Assertions.assertThat(Readers.toString(reader)).isEqualTo("abcdef");
			}
			try (final var reader = new BufferedReader(new InputStreamReader(new FileInputStream(INPUT.toFile())))) {
				assertThat(LineSeparator.detect(reader)).isEqualTo(LineSeparator.DEFAULT);
				assertThat(Readers.toString(reader)).isEqualTo(new String(Files.readAllBytes(INPUT)));
			}
		} catch (final IOException e) {
			fail(e.getMessage());
		}
	}

	@Test
	void testDetectNull() {
		assertThatNullPointerException().isThrownBy(() -> LineSeparator.detect((Path) null));
		assertThatNullPointerException().isThrownBy(() -> LineSeparator.detect(Paths.get("."), null));
		assertThatNullPointerException().isThrownBy(() -> LineSeparator.detect((Reader) null));
	}

	@Test
	void testDetectInvalid() {
		assertThatIllegalArgumentException().isThrownBy(() -> LineSeparator.detect(new InputStreamReader(new FileInputStream(INPUT.toFile()))));
	}
}