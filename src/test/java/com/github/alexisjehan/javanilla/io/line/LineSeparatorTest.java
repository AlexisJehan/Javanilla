/*
 * MIT License
 *
 * Copyright (c) 2018-2026 Alexis Jehan
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
package com.github.alexisjehan.javanilla.io.line;

import com.github.alexisjehan.javanilla.io.Readers;
import com.github.alexisjehan.javanilla.lang.Strings;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.assertj.core.api.Assertions.assertThatNullPointerException;

final class LineSeparatorTest {

	@Test
	void testReadLf() throws IOException {
		final var lineSeparator = LineSeparator.LF;
		try (var reader = Readers.EMPTY) {
			final var builder = new StringBuilder();
			assertThat(lineSeparator.read(reader, builder)).isEqualTo(-1);
			assertThat(builder.toString()).isEmpty();
		}
		try (var reader = new StringReader("\nfoo")) {
			final var builder = new StringBuilder();
			assertThat(lineSeparator.read(reader, builder)).isNotEqualTo(-1);
			assertThat(builder.toString()).isEmpty();
			builder.setLength(0);
			assertThat(lineSeparator.read(reader, builder)).isEqualTo(-1);
			assertThat(builder).hasToString("foo");
		}
		try (var reader = new StringReader("foo\n")) {
			final var builder = new StringBuilder();
			assertThat(lineSeparator.read(reader, builder)).isNotEqualTo(-1);
			assertThat(builder).hasToString("foo");
			builder.setLength(0);
			assertThat(lineSeparator.read(reader, builder)).isEqualTo(-1);
			assertThat(builder.toString()).isEmpty();
		}
		try (var reader = new StringReader("\rfoo\n\nbar\r")) {
			final var builder = new StringBuilder();
			assertThat(lineSeparator.read(reader, builder)).isNotEqualTo(-1);
			assertThat(builder).hasToString("\rfoo");
			builder.setLength(0);
			assertThat(lineSeparator.read(reader, builder)).isNotEqualTo(-1);
			assertThat(builder.toString()).isEmpty();
			builder.setLength(0);
			assertThat(lineSeparator.read(reader, builder)).isEqualTo(-1);
			assertThat(builder).hasToString("bar\r");
		}
	}

	@Test
	void testReadCrLf() throws IOException {
		final var lineSeparator = LineSeparator.CR_LF;
		try (var reader = Readers.EMPTY) {
			final var builder = new StringBuilder();
			assertThat(lineSeparator.read(reader, builder)).isEqualTo(-1);
			assertThat(builder.toString()).isEmpty();
		}
		try (var reader = new StringReader("\r\nfoo")) {
			final var builder = new StringBuilder();
			assertThat(lineSeparator.read(reader, builder)).isNotEqualTo(-1);
			assertThat(builder.toString()).isEmpty();
			builder.setLength(0);
			assertThat(lineSeparator.read(reader, builder)).isEqualTo(-1);
			assertThat(builder).hasToString("foo");
		}
		try (var reader = new StringReader("foo\r\n")) {
			final var builder = new StringBuilder();
			assertThat(lineSeparator.read(reader, builder)).isNotEqualTo(-1);
			assertThat(builder).hasToString("foo");
			builder.setLength(0);
			assertThat(lineSeparator.read(reader, builder)).isEqualTo(-1);
			assertThat(builder.toString()).isEmpty();
		}
		try (var reader = new StringReader("\rfoo\r\n\r\nbar\n")) {
			final var builder = new StringBuilder();
			assertThat(lineSeparator.read(reader, builder)).isNotEqualTo(-1);
			assertThat(builder).hasToString("\rfoo");
			builder.setLength(0);
			assertThat(lineSeparator.read(reader, builder)).isNotEqualTo(-1);
			assertThat(builder.toString()).isEmpty();
			builder.setLength(0);
			assertThat(lineSeparator.read(reader, builder)).isEqualTo(-1);
			assertThat(builder).hasToString("bar\n");
		}
		try (var reader = new StringReader("\nfoo\r\n\r\nbar\r")) {
			final var builder = new StringBuilder();
			assertThat(lineSeparator.read(reader, builder)).isNotEqualTo(-1);
			assertThat(builder).hasToString("\nfoo");
			builder.setLength(0);
			assertThat(lineSeparator.read(reader, builder)).isNotEqualTo(-1);
			assertThat(builder.toString()).isEmpty();
			builder.setLength(0);
			assertThat(lineSeparator.read(reader, builder)).isEqualTo(-1);
			assertThat(builder).hasToString("bar\r");
		}
	}

	@Test
	void testReadCr() throws IOException {
		final var lineSeparator = LineSeparator.CR;
		try (var reader = Readers.EMPTY) {
			final var builder = new StringBuilder();
			assertThat(lineSeparator.read(reader, builder)).isEqualTo(-1);
			assertThat(builder.toString()).isEmpty();
		}
		try (var reader = new StringReader("\rfoo")) {
			final var builder = new StringBuilder();
			assertThat(lineSeparator.read(reader, builder)).isNotEqualTo(-1);
			assertThat(builder.toString()).isEmpty();
			builder.setLength(0);
			assertThat(lineSeparator.read(reader, builder)).isEqualTo(-1);
			assertThat(builder).hasToString("foo");
		}
		try (var reader = new StringReader("foo\r")) {
			final var builder = new StringBuilder();
			assertThat(lineSeparator.read(reader, builder)).isNotEqualTo(-1);
			assertThat(builder).hasToString("foo");
			builder.setLength(0);
			assertThat(lineSeparator.read(reader, builder)).isEqualTo(-1);
			assertThat(builder.toString()).isEmpty();
		}
		try (var reader = new StringReader("\nfoo\r\rbar\n")) {
			final var builder = new StringBuilder();
			assertThat(lineSeparator.read(reader, builder)).isNotEqualTo(-1);
			assertThat(builder).hasToString("\nfoo");
			builder.setLength(0);
			assertThat(lineSeparator.read(reader, builder)).isNotEqualTo(-1);
			assertThat(builder.toString()).isEmpty();
			builder.setLength(0);
			assertThat(lineSeparator.read(reader, builder)).isEqualTo(-1);
			assertThat(builder).hasToString("bar\n");
		}
	}

	@Test
	void testReadDefault() throws IOException {
		final var lineSeparator = LineSeparator.DEFAULT;
		try (var reader = Readers.EMPTY) {
			final var builder = new StringBuilder();
			assertThat(lineSeparator.read(reader, builder)).isEqualTo(-1);
			assertThat(builder.toString()).isEmpty();
		}
		try (var reader = new StringReader("\rfoo")) {
			final var builder = new StringBuilder();
			assertThat(lineSeparator.read(reader, builder)).isNotEqualTo(-1);
			assertThat(builder.toString()).isEmpty();
			builder.setLength(0);
			assertThat(lineSeparator.read(reader, builder)).isEqualTo(-1);
			assertThat(builder).hasToString("foo");
		}
		try (var reader = new StringReader("foo\n")) {
			final var builder = new StringBuilder();
			assertThat(lineSeparator.read(reader, builder)).isNotEqualTo(-1);
			assertThat(builder).hasToString("foo");
			builder.setLength(0);
			assertThat(lineSeparator.read(reader, builder)).isEqualTo(-1);
			assertThat(builder.toString()).isEmpty();
		}
		try (var reader = new StringReader("\nfoo\r\nbar\r")) {
			final var builder = new StringBuilder();
			assertThat(lineSeparator.read(reader, builder)).isNotEqualTo(-1);
			assertThat(builder.toString()).isEmpty();
			builder.setLength(0);
			assertThat(lineSeparator.read(reader, builder)).isNotEqualTo(-1);
			assertThat(builder).hasToString("foo");
			builder.setLength(0);
			assertThat(lineSeparator.read(reader, builder)).isNotEqualTo(-1);
			assertThat(builder.toString()).isEmpty();
			builder.setLength(0);
			assertThat(lineSeparator.read(reader, builder)).isNotEqualTo(-1);
			assertThat(builder).hasToString("bar");
			builder.setLength(0);
			assertThat(lineSeparator.read(reader, builder)).isEqualTo(-1);
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
		assertThat(LineSeparator.detect(new StringReader("\nfoobar"))).isEqualTo(LineSeparator.LF);
		assertThat(LineSeparator.detect(new StringReader("foo\r\nbar"))).isEqualTo(LineSeparator.CR_LF);
		assertThat(LineSeparator.detect(new StringReader("foobar\r"))).isEqualTo(LineSeparator.CR);
		assertThat(LineSeparator.detect(new StringReader("foo\nbar\r\nfoo\rbar"))).isEqualTo(LineSeparator.DEFAULT);
		assertThat(LineSeparator.detect(new StringReader("foo\nbar\r\rfoo"))).isEqualTo(LineSeparator.CR);
		assertThat(LineSeparator.detect(new StringReader(Strings.repeat(' ', 10_000) + "\n"))).isEqualTo(LineSeparator.DEFAULT);
		final var tmpFile = tmpDirectory.resolve("testDetect");
		Files.createFile(tmpFile);
		assertThat(LineSeparator.detect(tmpFile)).isEqualTo(LineSeparator.DEFAULT);
	}

	@Test
	void testDetectInvalid(@TempDir final Path tmpDirectory) {
		assertThatNullPointerException().isThrownBy(() -> LineSeparator.detect((Path) null));
		assertThatNullPointerException().isThrownBy(() -> LineSeparator.detect(tmpDirectory.resolve("testDetectInvalid"), null));
		assertThatNullPointerException().isThrownBy(() -> LineSeparator.detect((Reader) null));
		assertThatIllegalArgumentException().isThrownBy(() -> LineSeparator.detect(Readers.EMPTY));
	}
}