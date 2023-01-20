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
package com.github.alexisjehan.javanilla.io.line;

import com.github.alexisjehan.javanilla.io.Readers;
import com.github.alexisjehan.javanilla.lang.array.ObjectArrays;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.assertj.core.api.Assertions.assertThatNullPointerException;

final class LineReaderTest {

	private static final String[] LINES = ObjectArrays.of("abc", "def", "ghi");

	@Test
	void testConstructor(@TempDir final Path tmpDirectory) throws IOException {
		final var tmpFile = tmpDirectory.resolve("testConstructor");
		Files.write(tmpFile, List.of(LINES));
		try (var lineReader = new LineReader(tmpFile)) {
			assertThat(lineReader.read()).isEqualTo(LINES[0]);
			assertThat(lineReader.read()).isEqualTo(LINES[1]);
			assertThat(lineReader.read()).isEqualTo(LINES[2]);
			assertThat(lineReader.read()).isNull();
		}
		Files.write(tmpFile, List.of(LINES), StandardCharsets.ISO_8859_1);
		try (var lineReader = new LineReader(tmpFile, StandardCharsets.ISO_8859_1)) {
			assertThat(lineReader.read()).isEqualTo(LINES[0]);
			assertThat(lineReader.read()).isEqualTo(LINES[1]);
			assertThat(lineReader.read()).isEqualTo(LINES[2]);
			assertThat(lineReader.read()).isNull();
		}
	}

	@Test
	void testConstructorInvalid() {
		assertThatNullPointerException().isThrownBy(() -> new LineReader((Reader) null, LineSeparator.DEFAULT));
		assertThatNullPointerException().isThrownBy(() -> new LineReader(Readers.of(String.join("\n", LINES)), null));
	}

	@Test
	void testRead() throws IOException {
		try (var lineReader = new LineReader(new StringReader("foo\n\rbar"), LineSeparator.DEFAULT)) {
			assertThat(lineReader.read()).isEqualTo("foo");
			assertThat(lineReader.read()).isEmpty();
			assertThat(lineReader.read()).isEqualTo("bar");
			assertThat(lineReader.read()).isNull();
		}
		try (var lineReader = new LineReader(new StringReader("foo\n\rbar\n"), LineSeparator.DEFAULT)) {
			assertThat(lineReader.read()).isEqualTo("foo");
			assertThat(lineReader.read()).isEmpty();
			assertThat(lineReader.read()).isEqualTo("bar");
			assertThat(lineReader.read()).isNull();
		}
		try (var lineReader = new LineReader(new StringReader("foo\r\nbar\r\n"), LineSeparator.DEFAULT)) {
			assertThat(lineReader.read()).isEqualTo("foo");
			assertThat(lineReader.read()).isEmpty();
			assertThat(lineReader.read()).isEqualTo("bar");
			assertThat(lineReader.read()).isEmpty();
			assertThat(lineReader.read()).isNull();
		}
		try (var lineReader = new LineReader(new StringReader("foo\n\rbar"), LineSeparator.DEFAULT, false)) {
			assertThat(lineReader.read()).isEqualTo("foo");
			assertThat(lineReader.read()).isEmpty();
			assertThat(lineReader.read()).isEqualTo("bar");
			assertThat(lineReader.read()).isNull();
		}
		try (var lineReader = new LineReader(new StringReader("foo\n\rbar\n"), LineSeparator.DEFAULT, false)) {
			assertThat(lineReader.read()).isEqualTo("foo");
			assertThat(lineReader.read()).isEmpty();
			assertThat(lineReader.read()).isEqualTo("bar");
			assertThat(lineReader.read()).isEmpty();
			assertThat(lineReader.read()).isNull();
		}
		try (var lineReader = new LineReader(new StringReader("foo\r\nbar\r\n"), LineSeparator.DEFAULT, false)) {
			assertThat(lineReader.read()).isEqualTo("foo");
			assertThat(lineReader.read()).isEmpty();
			assertThat(lineReader.read()).isEqualTo("bar");
			assertThat(lineReader.read()).isEmpty();
			assertThat(lineReader.read()).isEmpty();
			assertThat(lineReader.read()).isNull();
		}
	}

	@Test
	void testSkip() throws IOException {
		try (var lineReader = new LineReader(new StringReader("foo\n\rbar"), LineSeparator.DEFAULT)) {
			assertThat(lineReader.skip(0L)).isZero();
			assertThat(lineReader.skip(1L)).isEqualTo(1L);
			assertThat(lineReader.skip(3L)).isEqualTo(2L);
			assertThat(lineReader.skip(1L)).isZero();
		}
		try (var lineReader = new LineReader(new StringReader("foo\n\rbar\n"), LineSeparator.DEFAULT)) {
			assertThat(lineReader.skip(0L)).isZero();
			assertThat(lineReader.skip(1L)).isEqualTo(1L);
			assertThat(lineReader.skip(3L)).isEqualTo(2L);
			assertThat(lineReader.skip(1L)).isZero();
		}
		try (var lineReader = new LineReader(new StringReader("foo\r\nbar\r\n"), LineSeparator.DEFAULT)) {
			assertThat(lineReader.skip(0L)).isZero();
			assertThat(lineReader.skip(1L)).isEqualTo(1L);
			assertThat(lineReader.skip(4L)).isEqualTo(3L);
			assertThat(lineReader.skip(1L)).isZero();
		}
		try (var lineReader = new LineReader(new StringReader("foo\n\rbar"), LineSeparator.DEFAULT, false)) {
			assertThat(lineReader.skip(0L)).isZero();
			assertThat(lineReader.skip(1L)).isEqualTo(1L);
			assertThat(lineReader.skip(3L)).isEqualTo(2L);
			assertThat(lineReader.skip(1L)).isZero();
		}
		try (var lineReader = new LineReader(new StringReader("foo\n\rbar\n"), LineSeparator.DEFAULT, false)) {
			assertThat(lineReader.skip(0L)).isZero();
			assertThat(lineReader.skip(1L)).isEqualTo(1L);
			assertThat(lineReader.skip(4L)).isEqualTo(3L);
			assertThat(lineReader.skip(1L)).isZero();
		}
		try (var lineReader = new LineReader(new StringReader("foo\r\nbar\r\n"), LineSeparator.DEFAULT, false)) {
			assertThat(lineReader.skip(0L)).isZero();
			assertThat(lineReader.skip(1L)).isEqualTo(1L);
			assertThat(lineReader.skip(5L)).isEqualTo(4L);
			assertThat(lineReader.skip(1L)).isZero();
		}
	}

	@Test
	void testSkipInvalid() {
		assertThatIllegalArgumentException().isThrownBy(() -> new LineReader().skip(-1L));
	}

	@Test
	void testTransferTo() throws IOException {
		try (var writer = new StringWriter()) {
			try (var lineReader = new LineReader(Readers.EMPTY, LineSeparator.DEFAULT)) {
				assertThat(lineReader.transferTo(new LineWriter(writer))).isZero();
			}
			assertThat(writer.toString()).isEmpty();
		}
		try (var writer = new StringWriter()) {
			try (var lineReader = new LineReader(Readers.of(String.join("\n", LINES)), LineSeparator.DEFAULT)) {
				assertThat(lineReader.transferTo(new LineWriter(writer))).isEqualTo(3L);
			}
			assertThat(writer).hasToString(String.join(System.lineSeparator(), LINES[0], LINES[1], LINES[2]));
		}
	}

	@Test
	void testTransferToInvalid() {
		assertThatNullPointerException().isThrownBy(() -> new LineReader(Readers.of(String.join("\n", LINES)), LineSeparator.DEFAULT).transferTo(null));
	}
}