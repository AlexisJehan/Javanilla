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
package com.github.alexisjehan.javanilla.io.lines;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNullPointerException;
import static org.assertj.core.api.Fail.fail;

/**
 * <p>{@link LineWriter} unit tests.</p>
 */
final class LineWriterTest {

	private static Path output;

	@BeforeAll
	static void init() {
		try {
			output = File.createTempFile("output_", ".txt").toPath();
			output.toFile().deleteOnExit();
		} catch (final IOException e) {
			Assertions.fail(e.getMessage());
		}
	}

	@Test
	void testConstructor() {
		try {
			try (final var lineWriter = new LineWriter(output)) {
				lineWriter.write("line1");
				lineWriter.write("line2");
				lineWriter.flush();
			}
			assertThat(new String(Files.readAllBytes(output))).isEqualTo("line1" + System.lineSeparator() + "line2");
			try (final var lineWriter = new LineWriter(output, StandardCharsets.ISO_8859_1)) {
				lineWriter.write("line1");
				lineWriter.write("line2");
				lineWriter.flush();
			}
			assertThat(new String(Files.readAllBytes(output), StandardCharsets.ISO_8859_1)).isEqualTo("line1" + System.lineSeparator() + "line2");
		} catch (final IOException e) {
			fail(e.getMessage());
		}
	}

	@Test
	void testConstructorNull() {
		assertThatNullPointerException().isThrownBy(() -> new LineWriter((Writer) null));
		assertThatNullPointerException().isThrownBy(() -> new LineWriter(new StringWriter(), null));
	}

	@Test
	void testWriteLf() {
		try {
			try (final var stringWriter = new StringWriter()) {
				try (final var lineWriter = new LineWriter(stringWriter, LineSeparator.LF)) {
					lineWriter.write("line1");
					lineWriter.write("line2");
				}
				assertThat(stringWriter.toString()).isEqualTo("line1\nline2");
			}
			try (final var stringWriter = new StringWriter()) {
				try (final var lineWriter = new LineWriter(stringWriter, LineSeparator.LF, true)) {
					lineWriter.write("line1");
					lineWriter.write("line2");
				}
				assertThat(stringWriter.toString()).isEqualTo("line1\nline2\n");
			}
		} catch (final IOException e) {
			fail(e.getMessage());
		}
	}

	@Test
	void testWriteCrLf() {
		try {
			try (final var stringWriter = new StringWriter()) {
				try (final var lineWriter = new LineWriter(stringWriter, LineSeparator.CR_LF)) {
					lineWriter.write("line1");
					lineWriter.write("line2");
				}
				assertThat(stringWriter.toString()).isEqualTo("line1\r\nline2");
			}
			try (final var stringWriter = new StringWriter()) {
				try (final var lineWriter = new LineWriter(stringWriter, LineSeparator.CR_LF, true)) {
					lineWriter.write("line1");
					lineWriter.write("line2");
				}
				assertThat(stringWriter.toString()).isEqualTo("line1\r\nline2\r\n");
			}
		} catch (final IOException e) {
			fail(e.getMessage());
		}
	}

	@Test
	void testWriteCr() {
		try {
			try (final var stringWriter = new StringWriter()) {
				try (final var lineWriter = new LineWriter(stringWriter, LineSeparator.CR)) {
					lineWriter.write("line1");
					lineWriter.write("line2");
				}
				assertThat(stringWriter.toString()).isEqualTo("line1\rline2");
			}
			try (final var stringWriter = new StringWriter()) {
				try (final var lineWriter = new LineWriter(stringWriter, LineSeparator.CR, true)) {
					lineWriter.write("line1");
					lineWriter.write("line2");
				}
				assertThat(stringWriter.toString()).isEqualTo("line1\rline2\r");
			}
		} catch (final IOException e) {
			fail(e.getMessage());
		}
	}

	@Test
	void testWriteDefault() {
		try {
			try (final var stringWriter = new StringWriter()) {
				try (final var lineWriter = new LineWriter(stringWriter)) {
					lineWriter.write("line1");
					lineWriter.write("line2");
				}
				assertThat(stringWriter.toString()).isEqualTo("line1" + System.lineSeparator() + "line2");
			}
			try (final var stringWriter = new StringWriter()) {
				try (final var lineWriter = new LineWriter(stringWriter, LineSeparator.DEFAULT, true)) {
					lineWriter.write("line1");
					lineWriter.write("line2");
				}
				assertThat(stringWriter.toString()).isEqualTo("line1" + System.lineSeparator() + "line2" + System.lineSeparator());
			}
		} catch (final IOException e) {
			fail(e.getMessage());
		}
	}

	@Test
	void testWriteNull() {
		assertThatNullPointerException().isThrownBy(() -> new LineWriter().write("line"));
		assertThatNullPointerException().isThrownBy(() -> new LineWriter(new StringWriter()).write(null));
	}

	@Test
	void testNewLineLf() {
		try (final var stringWriter = new StringWriter()) {
			try (final var lineWriter = new LineWriter(stringWriter, LineSeparator.LF)) {
				lineWriter.newLine();
			}
			assertThat(stringWriter.toString()).isEqualTo("\n");
		} catch (final IOException e) {
			fail(e.getMessage());
		}
	}

	@Test
	void testNewLineCrLf() {
		try (final var stringWriter = new StringWriter()) {
			try (final var lineWriter = new LineWriter(stringWriter, LineSeparator.CR_LF)) {
				lineWriter.newLine();
			}
			assertThat(stringWriter.toString()).isEqualTo("\r\n");
		} catch (final IOException e) {
			fail(e.getMessage());
		}
	}

	@Test
	void testNewLineCr() {
		try (final var stringWriter = new StringWriter()) {
			try (final var lineWriter = new LineWriter(stringWriter, LineSeparator.CR)) {
				lineWriter.newLine();
			}
			assertThat(stringWriter.toString()).isEqualTo("\r");
		} catch (final IOException e) {
			fail(e.getMessage());
		}
	}

	@Test
	void testNewLineDefault() {
		try (final var stringWriter = new StringWriter()) {
			try (final var lineWriter = new LineWriter(stringWriter)) {
				lineWriter.newLine();
			}
			assertThat(stringWriter.toString()).isEqualTo(System.lineSeparator());
		} catch (final IOException e) {
			fail(e.getMessage());
		}
	}
}