/*
 * MIT License
 *
 * Copyright (c) 2018-2023 Alexis Jehan
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
import com.github.alexisjehan.javanilla.lang.array.ObjectArrays;
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

@Deprecated
final class LineWriterTest {

	private static final String[] LINES = ObjectArrays.of("abc", "def", "ghi");

	@Test
	void testConstructor(@TempDir final Path tmpDirectory) throws IOException {
		final var tmpFile = tmpDirectory.resolve("testConstructor");
		try (final var lineWriter = new LineWriter(tmpFile)) {
			lineWriter.write(LINES[0]);
			lineWriter.write(LINES[1]);
			lineWriter.write(LINES[2]);
			lineWriter.flush();
		}
		assertThat(new String(Files.readAllBytes(tmpFile))).isEqualTo(String.join(System.lineSeparator(), LINES[0], LINES[1], LINES[2]));
		try (final var lineWriter = new LineWriter(tmpFile, StandardCharsets.ISO_8859_1)) {
			lineWriter.write(LINES[0]);
			lineWriter.write(LINES[1]);
			lineWriter.write(LINES[2]);
			lineWriter.flush();
		}
		assertThat(new String(Files.readAllBytes(tmpFile), StandardCharsets.ISO_8859_1)).isEqualTo(String.join(System.lineSeparator(), LINES[0], LINES[1], LINES[2]));
	}

	@Test
	void testConstructorInvalid() {
		assertThatNullPointerException().isThrownBy(() -> new LineWriter((Writer) null));
		assertThatNullPointerException().isThrownBy(() -> new LineWriter(Writers.EMPTY, null));
	}

	@Test
	void testWriteLf() throws IOException {
		try (final var writer = new StringWriter()) {
			try (final var lineWriter = new LineWriter(writer, LineSeparator.LF)) {
				lineWriter.write(LINES[0]);
				lineWriter.write(LINES[1]);
				lineWriter.write(LINES[2]);
				lineWriter.flush();
			}
			assertThat(writer).hasToString(String.join("\n", LINES[0], LINES[1], LINES[2]));
		}
		try (final var writer = new StringWriter()) {
			try (final var lineWriter = new LineWriter(writer, LineSeparator.LF, true)) {
				lineWriter.write(LINES[0]);
				lineWriter.write(LINES[1]);
				lineWriter.write(LINES[2]);
				lineWriter.flush();
			}
			assertThat(writer).hasToString(String.join("\n", LINES[0], LINES[1], LINES[2]) + "\n");
		}
	}

	@Test
	void testWriteCrLf() throws IOException {
		try (final var writer = new StringWriter()) {
			try (final var lineWriter = new LineWriter(writer, LineSeparator.CR_LF)) {
				lineWriter.write(LINES[0]);
				lineWriter.write(LINES[1]);
				lineWriter.write(LINES[2]);
				lineWriter.flush();
			}
			assertThat(writer).hasToString(String.join("\r\n", LINES[0], LINES[1], LINES[2]));
		}
		try (final var writer = new StringWriter()) {
			try (final var lineWriter = new LineWriter(writer, LineSeparator.CR_LF, true)) {
				lineWriter.write(LINES[0]);
				lineWriter.write(LINES[1]);
				lineWriter.write(LINES[2]);
				lineWriter.flush();
			}
			assertThat(writer).hasToString(String.join("\r\n", LINES[0], LINES[1], LINES[2]) + "\r\n");
		}
	}

	@Test
	void testWriteCr() throws IOException {
		try (final var writer = new StringWriter()) {
			try (final var lineWriter = new LineWriter(writer, LineSeparator.CR)) {
				lineWriter.write(LINES[0]);
				lineWriter.write(LINES[1]);
				lineWriter.write(LINES[2]);
				lineWriter.flush();
			}
			assertThat(writer).hasToString(String.join("\r", LINES[0], LINES[1], LINES[2]));
		}
		try (final var writer = new StringWriter()) {
			try (final var lineWriter = new LineWriter(writer, LineSeparator.CR, true)) {
				lineWriter.write(LINES[0]);
				lineWriter.write(LINES[1]);
				lineWriter.write(LINES[2]);
				lineWriter.flush();
			}
			assertThat(writer).hasToString(String.join("\r", LINES[0], LINES[1], LINES[2]) + "\r");
		}
	}

	@Test
	void testWriteDefault() throws IOException {
		try (final var writer = new StringWriter()) {
			try (final var lineWriter = new LineWriter(writer)) {
				lineWriter.write(LINES[0]);
				lineWriter.write(LINES[1]);
				lineWriter.write(LINES[2]);
				lineWriter.flush();
			}
			assertThat(writer).hasToString(String.join(System.lineSeparator(), LINES[0], LINES[1], LINES[2]));
		}
		try (final var writer = new StringWriter()) {
			try (final var lineWriter = new LineWriter(writer, LineSeparator.DEFAULT, true)) {
				lineWriter.write(LINES[0]);
				lineWriter.write(LINES[1]);
				lineWriter.write(LINES[2]);
				lineWriter.flush();
			}
			assertThat(writer).hasToString(String.join(System.lineSeparator(), LINES[0], LINES[1], LINES[2]) + System.lineSeparator());
		}
	}

	@Test
	void testWriteInvalid() {
		assertThatNullPointerException().isThrownBy(() -> new LineWriter(Writers.EMPTY).write(null));
	}

	@Test
	void testNewLineLf() throws IOException {
		try (final var writer = new StringWriter()) {
			try (final var lineWriter = new LineWriter(writer, LineSeparator.LF)) {
				lineWriter.newLine();
				lineWriter.flush();
			}
			assertThat(writer).hasToString("\n");
		}
	}

	@Test
	void testNewLineCrLf() throws IOException {
		try (final var writer = new StringWriter()) {
			try (final var lineWriter = new LineWriter(writer, LineSeparator.CR_LF)) {
				lineWriter.newLine();
				lineWriter.flush();
			}
			assertThat(writer).hasToString("\r\n");
		}
	}

	@Test
	void testNewLineCr() throws IOException {
		try (final var writer = new StringWriter()) {
			try (final var lineWriter = new LineWriter(writer, LineSeparator.CR)) {
				lineWriter.newLine();
				lineWriter.flush();
			}
			assertThat(writer).hasToString("\r");
		}
	}

	@Test
	void testNewLineDefault() throws IOException {
		try (final var writer = new StringWriter()) {
			try (final var lineWriter = new LineWriter(writer)) {
				lineWriter.newLine();
				lineWriter.flush();
			}
			assertThat(writer).hasToString(System.lineSeparator());
		}
	}
}