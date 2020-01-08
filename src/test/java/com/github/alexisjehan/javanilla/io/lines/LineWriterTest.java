/*
 * MIT License
 *
 * Copyright (c) 2018-2020 Alexis Jehan
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

import com.github.alexisjehan.javanilla.io.chars.Writers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNullPointerException;

/**
 * <p>{@link LineWriter} unit tests.</p>
 */
final class LineWriterTest {

	@Test
	void testConstructor(@TempDir final Path tmpDirectory) throws IOException {
		final var path = tmpDirectory.resolve("testConstructor");
		try (final var lineWriter = new LineWriter(path)) {
			lineWriter.write("abc");
			lineWriter.write("def");
			lineWriter.flush();
		}
		assertThat(new String(Files.readAllBytes(path))).isEqualTo("abc" + System.lineSeparator() + "def");
		try (final var lineWriter = new LineWriter(path, StandardCharsets.ISO_8859_1)) {
			lineWriter.write("abc");
			lineWriter.write("def");
			lineWriter.flush();
		}
		assertThat(new String(Files.readAllBytes(path), StandardCharsets.ISO_8859_1)).isEqualTo("abc" + System.lineSeparator() + "def");
	}

	@Test
	@SuppressWarnings("deprecation")
	void testConstructorInvalid() {
		assertThatNullPointerException().isThrownBy(() -> new LineWriter((Writer) null));
		assertThatNullPointerException().isThrownBy(() -> new LineWriter(Writers.EMPTY, null));
	}

	@Test
	void testWriteLf() throws IOException {
		try (final var stringWriter = new StringWriter()) {
			try (final var lineWriter = new LineWriter(stringWriter, LineSeparator.LF)) {
				lineWriter.write("abc");
				lineWriter.write("def");
				lineWriter.flush();
			}
			assertThat(stringWriter.toString()).isEqualTo("abc\ndef");
		}
		try (final var stringWriter = new StringWriter()) {
			try (final var lineWriter = new LineWriter(stringWriter, LineSeparator.LF, true)) {
				lineWriter.write("abc");
				lineWriter.write("def");
				lineWriter.flush();
			}
			assertThat(stringWriter.toString()).isEqualTo("abc\ndef\n");
		}
	}

	@Test
	void testWriteCrLf() throws IOException {
		try (final var stringWriter = new StringWriter()) {
			try (final var lineWriter = new LineWriter(stringWriter, LineSeparator.CR_LF)) {
				lineWriter.write("abc");
				lineWriter.write("def");
				lineWriter.flush();
			}
			assertThat(stringWriter.toString()).isEqualTo("abc\r\ndef");
		}
		try (final var stringWriter = new StringWriter()) {
			try (final var lineWriter = new LineWriter(stringWriter, LineSeparator.CR_LF, true)) {
				lineWriter.write("abc");
				lineWriter.write("def");
				lineWriter.flush();
			}
			assertThat(stringWriter.toString()).isEqualTo("abc\r\ndef\r\n");
		}
	}

	@Test
	void testWriteCr() throws IOException {
		try (final var stringWriter = new StringWriter()) {
			try (final var lineWriter = new LineWriter(stringWriter, LineSeparator.CR)) {
				lineWriter.write("abc");
				lineWriter.write("def");
				lineWriter.flush();
			}
			assertThat(stringWriter.toString()).isEqualTo("abc\rdef");
		}
		try (final var stringWriter = new StringWriter()) {
			try (final var lineWriter = new LineWriter(stringWriter, LineSeparator.CR, true)) {
				lineWriter.write("abc");
				lineWriter.write("def");
				lineWriter.flush();
			}
			assertThat(stringWriter.toString()).isEqualTo("abc\rdef\r");
		}
	}

	@Test
	void testWriteDefault() throws IOException {
		try (final var stringWriter = new StringWriter()) {
			try (final var lineWriter = new LineWriter(stringWriter)) {
				lineWriter.write("abc");
				lineWriter.write("def");
				lineWriter.flush();
			}
			assertThat(stringWriter.toString()).isEqualTo("abc" + System.lineSeparator() + "def");
		}
		try (final var stringWriter = new StringWriter()) {
			try (final var lineWriter = new LineWriter(stringWriter, LineSeparator.DEFAULT, true)) {
				lineWriter.write("abc");
				lineWriter.write("def");
				lineWriter.flush();
			}
			assertThat(stringWriter.toString()).isEqualTo("abc" + System.lineSeparator() + "def" + System.lineSeparator());
		}
	}

	@Test
	@SuppressWarnings("deprecation")
	void testWriteInvalid() {
		assertThatNullPointerException().isThrownBy(() -> new LineWriter(Writers.EMPTY).write(null));
	}

	@Test
	void testNewLineLf() throws IOException {
		try (final var stringWriter = new StringWriter()) {
			try (final var lineWriter = new LineWriter(stringWriter, LineSeparator.LF)) {
				lineWriter.newLine();
				lineWriter.flush();
			}
			assertThat(stringWriter.toString()).isEqualTo("\n");
		}
	}

	@Test
	void testNewLineCrLf() throws IOException {
		try (final var stringWriter = new StringWriter()) {
			try (final var lineWriter = new LineWriter(stringWriter, LineSeparator.CR_LF)) {
				lineWriter.newLine();
				lineWriter.flush();
			}
			assertThat(stringWriter.toString()).isEqualTo("\r\n");
		}
	}

	@Test
	void testNewLineCr() throws IOException {
		try (final var stringWriter = new StringWriter()) {
			try (final var lineWriter = new LineWriter(stringWriter, LineSeparator.CR)) {
				lineWriter.newLine();
				lineWriter.flush();
			}
			assertThat(stringWriter.toString()).isEqualTo("\r");
		}
	}

	@Test
	void testNewLineDefault() throws IOException {
		try (final var stringWriter = new StringWriter()) {
			try (final var lineWriter = new LineWriter(stringWriter)) {
				lineWriter.newLine();
				lineWriter.flush();
			}
			assertThat(stringWriter.toString()).isEqualTo(System.lineSeparator());
		}
	}
}