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

package com.github.alexisjehan.javanilla.io.lines;

import com.github.alexisjehan.javanilla.io.chars.Readers;
import com.github.alexisjehan.javanilla.lang.Strings;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.assertj.core.api.Assertions.assertThatNullPointerException;

/**
 * <p>{@link LineSeparator} unit tests.</p>
 */
final class LineSeparatorTest {

	@Test
	@SuppressWarnings("deprecation")
	void testReadLf() throws IOException {
		try (final var reader = Readers.EMPTY) {
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
	}

	@Test
	@SuppressWarnings("deprecation")
	void testReadCrLf() throws IOException {
		try (final var reader = Readers.EMPTY) {
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
	}

	@Test
	@SuppressWarnings("deprecation")
	void testReadCr() throws IOException {
		try (final var reader = Readers.EMPTY) {
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
	}

	@Test
	@SuppressWarnings("deprecation")
	void testReadDefault() throws IOException {
		try (final var reader = Readers.EMPTY) {
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
	}

	@Test
	void testToString() {
		assertThat(LineSeparator.LF).hasToString("\n");
		assertThat(LineSeparator.CR_LF).hasToString("\r\n");
		assertThat(LineSeparator.CR).hasToString("\r");
		assertThat(LineSeparator.DEFAULT).hasToString(System.lineSeparator());
	}

	@Test
	@SuppressWarnings("deprecation")
	void testDetect(@TempDir final Path tmpDirectory) throws IOException {
		assertThat(LineSeparator.detect(new StringReader(Strings.EMPTY))).isEqualTo(LineSeparator.DEFAULT);
		assertThat(LineSeparator.detect(new StringReader("\nabcdef"))).isEqualTo(LineSeparator.LF);
		assertThat(LineSeparator.detect(new StringReader("abc\r\ndef"))).isEqualTo(LineSeparator.CR_LF);
		assertThat(LineSeparator.detect(new StringReader("abcdef\r"))).isEqualTo(LineSeparator.CR);
		assertThat(LineSeparator.detect(new StringReader("abc\ndef\r\nghi\rjkl"))).isEqualTo(LineSeparator.DEFAULT);
		assertThat(LineSeparator.detect(new StringReader("abc\ndef\r\rghi"))).isEqualTo(LineSeparator.CR);
		assertThat(LineSeparator.detect(new StringReader(Strings.repeat(' ', 10_000) + "\n"))).isEqualTo(LineSeparator.DEFAULT);
		final var path = tmpDirectory.resolve("testDetect");
		Files.createFile(path);
		assertThat(LineSeparator.detect(path)).isEqualTo(LineSeparator.DEFAULT);
	}

	@Test
	@SuppressWarnings("deprecation")
	void testDetectInvalid() {
		assertThatNullPointerException().isThrownBy(() -> LineSeparator.detect((Path) null));
		assertThatNullPointerException().isThrownBy(() -> LineSeparator.detect(Paths.get(getClass().getName() + ".testDetectInvalid.txt"), null));
		assertThatNullPointerException().isThrownBy(() -> LineSeparator.detect((Reader) null));
		assertThatIllegalArgumentException().isThrownBy(() -> LineSeparator.detect(Readers.EMPTY));
	}
}