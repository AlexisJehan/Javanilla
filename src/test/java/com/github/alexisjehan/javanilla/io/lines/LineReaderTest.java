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

import com.github.alexisjehan.javanilla.io.chars.Readers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junitpioneer.jupiter.TempDirectory;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.assertj.core.api.Assertions.assertThatNullPointerException;

/**
 * <p>{@link LineReader} unit tests.</p>
 */
final class LineReaderTest {

	@Test
	@ExtendWith(TempDirectory.class)
	void testConstructor(@TempDirectory.TempDir final Path tmpDirectory) throws IOException {
		final var path = tmpDirectory.resolve("testConstructor");
		Files.write(path, "abc\ndef".getBytes());
		try (final var lineReader = new LineReader(path)) {
			assertThat(lineReader.read()).isEqualTo("abc");
			assertThat(lineReader.read()).isEqualTo("def");
			assertThat(lineReader.read()).isNull();
		}
		Files.write(path, "abc\ndef".getBytes(StandardCharsets.ISO_8859_1));
		try (final var lineReader = new LineReader(path, StandardCharsets.ISO_8859_1)) {
			assertThat(lineReader.read()).isEqualTo("abc");
			assertThat(lineReader.read()).isEqualTo("def");
			assertThat(lineReader.read()).isNull();
		}
	}

	@Test
	void testConstructorInvalid() {
		assertThatNullPointerException().isThrownBy(() -> new LineReader((Reader) null, LineSeparator.DEFAULT));
		assertThatNullPointerException().isThrownBy(() -> new LineReader(Readers.singleton('a'), null));
	}

	@Test
	void testRead() throws IOException {
		try (final var lineReader = new LineReader(new StringReader("abc\n\rdef"), LineSeparator.DEFAULT)) {
			assertThat(lineReader.read()).isEqualTo("abc");
			assertThat(lineReader.read()).isEmpty();
			assertThat(lineReader.read()).isEqualTo("def");
			assertThat(lineReader.read()).isNull();
		}
		try (final var lineReader = new LineReader(new StringReader("abc\n\rdef\n"), LineSeparator.DEFAULT)) {
			assertThat(lineReader.read()).isEqualTo("abc");
			assertThat(lineReader.read()).isEmpty();
			assertThat(lineReader.read()).isEqualTo("def");
			assertThat(lineReader.read()).isNull();
		}
		try (final var lineReader = new LineReader(new StringReader("abc\r\ndef\r\n"), LineSeparator.DEFAULT)) {
			assertThat(lineReader.read()).isEqualTo("abc");
			assertThat(lineReader.read()).isEmpty();
			assertThat(lineReader.read()).isEqualTo("def");
			assertThat(lineReader.read()).isEmpty();
			assertThat(lineReader.read()).isNull();
		}
		try (final var lineReader = new LineReader(new StringReader("abc\n\rdef"), LineSeparator.DEFAULT, false)) {
			assertThat(lineReader.read()).isEqualTo("abc");
			assertThat(lineReader.read()).isEmpty();
			assertThat(lineReader.read()).isEqualTo("def");
			assertThat(lineReader.read()).isNull();
		}
		try (final var lineReader = new LineReader(new StringReader("abc\n\rdef\n"), LineSeparator.DEFAULT, false)) {
			assertThat(lineReader.read()).isEqualTo("abc");
			assertThat(lineReader.read()).isEmpty();
			assertThat(lineReader.read()).isEqualTo("def");
			assertThat(lineReader.read()).isEmpty();
			assertThat(lineReader.read()).isNull();
		}
		try (final var lineReader = new LineReader(new StringReader("abc\r\ndef\r\n"), LineSeparator.DEFAULT, false)) {
			assertThat(lineReader.read()).isEqualTo("abc");
			assertThat(lineReader.read()).isEmpty();
			assertThat(lineReader.read()).isEqualTo("def");
			assertThat(lineReader.read()).isEmpty();
			assertThat(lineReader.read()).isEmpty();
			assertThat(lineReader.read()).isNull();
		}
	}

	@Test
	void testSkip() throws IOException {
		try (final var lineReader = new LineReader(new StringReader("abc\n\rdef"), LineSeparator.DEFAULT)) {
			assertThat(lineReader.skip(0L)).isEqualTo(0L);
			assertThat(lineReader.skip(1L)).isEqualTo(1L);
			assertThat(lineReader.skip(3L)).isEqualTo(2L);
			assertThat(lineReader.skip(1L)).isEqualTo(0L);
		}
		try (final var lineReader = new LineReader(new StringReader("abc\n\rdef\n"), LineSeparator.DEFAULT)) {
			assertThat(lineReader.skip(0L)).isEqualTo(0L);
			assertThat(lineReader.skip(1L)).isEqualTo(1L);
			assertThat(lineReader.skip(3L)).isEqualTo(2L);
			assertThat(lineReader.skip(1L)).isEqualTo(0L);
		}
		try (final var lineReader = new LineReader(new StringReader("abc\r\ndef\r\n"), LineSeparator.DEFAULT)) {
			assertThat(lineReader.skip(0L)).isEqualTo(0L);
			assertThat(lineReader.skip(1L)).isEqualTo(1L);
			assertThat(lineReader.skip(4L)).isEqualTo(3L);
			assertThat(lineReader.skip(1L)).isEqualTo(0L);
		}
		try (final var lineReader = new LineReader(new StringReader("abc\n\rdef"), LineSeparator.DEFAULT, false)) {
			assertThat(lineReader.skip(0L)).isEqualTo(0L);
			assertThat(lineReader.skip(1L)).isEqualTo(1L);
			assertThat(lineReader.skip(3L)).isEqualTo(2L);
			assertThat(lineReader.skip(1L)).isEqualTo(0L);
		}
		try (final var lineReader = new LineReader(new StringReader("abc\n\rdef\n"), LineSeparator.DEFAULT, false)) {
			assertThat(lineReader.skip(0L)).isEqualTo(0L);
			assertThat(lineReader.skip(1L)).isEqualTo(1L);
			assertThat(lineReader.skip(4L)).isEqualTo(3L);
			assertThat(lineReader.skip(1L)).isEqualTo(0L);
		}
		try (final var lineReader = new LineReader(new StringReader("abc\r\ndef\r\n"), LineSeparator.DEFAULT, false)) {
			assertThat(lineReader.skip(0L)).isEqualTo(0L);
			assertThat(lineReader.skip(1L)).isEqualTo(1L);
			assertThat(lineReader.skip(5L)).isEqualTo(4L);
			assertThat(lineReader.skip(1L)).isEqualTo(0L);
		}
	}

	@Test
	void testSkipInvalid() {
		assertThatIllegalArgumentException().isThrownBy(() -> new LineReader().skip(-1L));
	}

	@Test
	void testTransferTo() throws IOException {
		try (final var stringWriter = new StringWriter()) {
			try (final var lineReader = new LineReader(Readers.EMPTY, LineSeparator.DEFAULT)) {
				assertThat(lineReader.transferTo(new LineWriter(stringWriter))).isEqualTo(0L);
			}
			assertThat(stringWriter.toString()).isEmpty();
		}
		try (final var stringWriter = new StringWriter()) {
			try (final var lineReader = new LineReader(new StringReader("abc\ndef"), LineSeparator.DEFAULT)) {
				assertThat(lineReader.transferTo(new LineWriter(stringWriter))).isEqualTo(2L);
			}
			assertThat(stringWriter.toString()).isEqualTo("abc" + System.lineSeparator() + "def");
		}
	}

	@Test
	void testTransferToInvalid() {
		assertThatNullPointerException().isThrownBy(() -> new LineReader(Readers.singleton('a'), LineSeparator.DEFAULT).transferTo(null));
	}
}