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
import org.junit.jupiter.api.Test;

import java.io.*;
import java.lang.invoke.MethodHandles;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Fail.fail;

/**
 * <p>{@link LineReader} unit tests.</p>
 */
final class LineReaderTest {

	private static final Path INPUT = new File(Objects.requireNonNull(MethodHandles.lookup().lookupClass().getClassLoader().getResource("input.txt")).getFile()).toPath();

	@Test
	void testConstructor() {
		try {
			try (final var lineReader = new LineReader(INPUT)) {
				assertThat(lineReader.read()).isEqualTo(new String(Files.readAllBytes(INPUT)));
				assertThat(lineReader.read()).isNull();
			}
			try (final var lineReader = new LineReader(INPUT, StandardCharsets.ISO_8859_1)) {
				assertThat(lineReader.read()).isEqualTo(new String(Files.readAllBytes(INPUT), StandardCharsets.ISO_8859_1));
				assertThat(lineReader.read()).isNull();
			}
			try (final var lineReader = new LineReader(Files.newBufferedReader(INPUT))) {
				assertThat(lineReader.read()).isEqualTo(new String(Files.readAllBytes(INPUT)));
				assertThat(lineReader.read()).isNull();
			}
		} catch (final IOException e) {
			fail(e.getMessage());
		}
	}

	@Test
	void testConstructorNull() {
		assertThatNullPointerException().isThrownBy(() -> new LineReader((Reader) null, LineSeparator.DEFAULT));
		assertThatNullPointerException().isThrownBy(() -> new LineReader(new StringReader(""), null));
	}

	@Test
	void testReadLf() {
		try {
			try (final var lineReader = new LineReader(new StringReader("line1\n\nline2"), LineSeparator.LF)) {
				assertThat(lineReader.read()).isEqualTo("line1");
				assertThat(lineReader.read()).isEmpty();
				assertThat(lineReader.read()).isEqualTo("line2");
				assertThat(lineReader.read()).isNull();
			}
			try (final var lineReader = new LineReader(new StringReader("line1\n\nline2\n"), LineSeparator.LF)) {
				assertThat(lineReader.read()).isEqualTo("line1");
				assertThat(lineReader.read()).isEmpty();
				assertThat(lineReader.read()).isEqualTo("line2");
				assertThat(lineReader.read()).isNull();
			}
			try (final var lineReader = new LineReader(new StringReader("line1\n\nline2\n\n"), LineSeparator.LF)) {
				assertThat(lineReader.read()).isEqualTo("line1");
				assertThat(lineReader.read()).isEmpty();
				assertThat(lineReader.read()).isEqualTo("line2");
				assertThat(lineReader.read()).isEmpty();
				assertThat(lineReader.read()).isNull();
			}
			try (final var lineReader = new LineReader(new StringReader("line1\n\nline2"), LineSeparator.LF, false)) {
				assertThat(lineReader.read()).isEqualTo("line1");
				assertThat(lineReader.read()).isEmpty();
				assertThat(lineReader.read()).isEqualTo("line2");
				assertThat(lineReader.read()).isNull();
			}
			try (final var lineReader = new LineReader(new StringReader("line1\n\nline2\n"), LineSeparator.LF, false)) {
				assertThat(lineReader.read()).isEqualTo("line1");
				assertThat(lineReader.read()).isEmpty();
				assertThat(lineReader.read()).isEqualTo("line2");
				assertThat(lineReader.read()).isEmpty();
				assertThat(lineReader.read()).isNull();
			}
			try (final var lineReader = new LineReader(new StringReader("line1\n\nline2\n\n"), LineSeparator.LF, false)) {
				assertThat(lineReader.read()).isEqualTo("line1");
				assertThat(lineReader.read()).isEmpty();
				assertThat(lineReader.read()).isEqualTo("line2");
				assertThat(lineReader.read()).isEmpty();
				assertThat(lineReader.read()).isEmpty();
				assertThat(lineReader.read()).isNull();
			}
		} catch (final IOException e) {
			fail(e.getMessage());
		}
	}

	@Test
	void testReadCrLf() {
		try {
			try (final var lineReader = new LineReader(new StringReader("line1\r\n\r\nline2"), LineSeparator.CR_LF)) {
				assertThat(lineReader.read()).isEqualTo("line1");
				assertThat(lineReader.read()).isEmpty();
				assertThat(lineReader.read()).isEqualTo("line2");
				assertThat(lineReader.read()).isNull();
			}
			try (final var lineReader = new LineReader(new StringReader("line1\r\n\r\nline2\r\n"), LineSeparator.CR_LF)) {
				assertThat(lineReader.read()).isEqualTo("line1");
				assertThat(lineReader.read()).isEmpty();
				assertThat(lineReader.read()).isEqualTo("line2");
				assertThat(lineReader.read()).isNull();
			}
			try (final var lineReader = new LineReader(new StringReader("line1\r\n\r\nline2\r\n\r\n"), LineSeparator.CR_LF)) {
				assertThat(lineReader.read()).isEqualTo("line1");
				assertThat(lineReader.read()).isEmpty();
				assertThat(lineReader.read()).isEqualTo("line2");
				assertThat(lineReader.read()).isEmpty();
				assertThat(lineReader.read()).isNull();
			}
			try (final var lineReader = new LineReader(new StringReader("line1\r\n\r\nline2"), LineSeparator.CR_LF, false)) {
				assertThat(lineReader.read()).isEqualTo("line1");
				assertThat(lineReader.read()).isEmpty();
				assertThat(lineReader.read()).isEqualTo("line2");
				assertThat(lineReader.read()).isNull();
			}
			try (final var lineReader = new LineReader(new StringReader("line1\r\n\r\nline2\r\n"), LineSeparator.CR_LF, false)) {
				assertThat(lineReader.read()).isEqualTo("line1");
				assertThat(lineReader.read()).isEmpty();
				assertThat(lineReader.read()).isEqualTo("line2");
				assertThat(lineReader.read()).isEmpty();
				assertThat(lineReader.read()).isNull();
			}
			try (final var lineReader = new LineReader(new StringReader("line1\r\n\r\nline2\r\n\r\n"), LineSeparator.CR_LF, false)) {
				assertThat(lineReader.read()).isEqualTo("line1");
				assertThat(lineReader.read()).isEmpty();
				assertThat(lineReader.read()).isEqualTo("line2");
				assertThat(lineReader.read()).isEmpty();
				assertThat(lineReader.read()).isEmpty();
				assertThat(lineReader.read()).isNull();
			}
		} catch (final IOException e) {
			fail(e.getMessage());
		}
	}

	@Test
	void testReadCr() {
		try {
			try (final var lineReader = new LineReader(new StringReader("line1\r\rline2"), LineSeparator.CR)) {
				assertThat(lineReader.read()).isEqualTo("line1");
				assertThat(lineReader.read()).isEmpty();
				assertThat(lineReader.read()).isEqualTo("line2");
				assertThat(lineReader.read()).isNull();
			}
			try (final var lineReader = new LineReader(new StringReader("line1\r\rline2\r"), LineSeparator.CR)) {
				assertThat(lineReader.read()).isEqualTo("line1");
				assertThat(lineReader.read()).isEmpty();
				assertThat(lineReader.read()).isEqualTo("line2");
				assertThat(lineReader.read()).isNull();
			}
			try (final var lineReader = new LineReader(new StringReader("line1\r\rline2\r\r"), LineSeparator.CR)) {
				assertThat(lineReader.read()).isEqualTo("line1");
				assertThat(lineReader.read()).isEmpty();
				assertThat(lineReader.read()).isEqualTo("line2");
				assertThat(lineReader.read()).isEmpty();
				assertThat(lineReader.read()).isNull();
			}
			try (final var lineReader = new LineReader(new StringReader("line1\r\rline2"), LineSeparator.CR, false)) {
				assertThat(lineReader.read()).isEqualTo("line1");
				assertThat(lineReader.read()).isEmpty();
				assertThat(lineReader.read()).isEqualTo("line2");
				assertThat(lineReader.read()).isNull();
			}
			try (final var lineReader = new LineReader(new StringReader("line1\r\rline2\r"), LineSeparator.CR, false)) {
				assertThat(lineReader.read()).isEqualTo("line1");
				assertThat(lineReader.read()).isEmpty();
				assertThat(lineReader.read()).isEqualTo("line2");
				assertThat(lineReader.read()).isEmpty();
				assertThat(lineReader.read()).isNull();
			}
			try (final var lineReader = new LineReader(new StringReader("line1\r\rline2\r\r"), LineSeparator.CR, false)) {
				assertThat(lineReader.read()).isEqualTo("line1");
				assertThat(lineReader.read()).isEmpty();
				assertThat(lineReader.read()).isEqualTo("line2");
				assertThat(lineReader.read()).isEmpty();
				assertThat(lineReader.read()).isEmpty();
				assertThat(lineReader.read()).isNull();
			}
		} catch (final IOException e) {
			fail(e.getMessage());
		}
	}

	@Test
	void testReadDefault() {
		try {
			try (final var lineReader = new LineReader(new StringReader("line1\n\rline2"), LineSeparator.DEFAULT)) {
				assertThat(lineReader.read()).isEqualTo("line1");
				assertThat(lineReader.read()).isEmpty();
				assertThat(lineReader.read()).isEqualTo("line2");
				assertThat(lineReader.read()).isNull();
			}
			try (final var lineReader = new LineReader(new StringReader("line1\n\rline2\n"), LineSeparator.DEFAULT)) {
				assertThat(lineReader.read()).isEqualTo("line1");
				assertThat(lineReader.read()).isEmpty();
				assertThat(lineReader.read()).isEqualTo("line2");
				assertThat(lineReader.read()).isNull();
			}
			try (final var lineReader = new LineReader(new StringReader("line1\r\nline2\r\n"), LineSeparator.DEFAULT)) {
				assertThat(lineReader.read()).isEqualTo("line1");
				assertThat(lineReader.read()).isEmpty();
				assertThat(lineReader.read()).isEqualTo("line2");
				assertThat(lineReader.read()).isEmpty();
				assertThat(lineReader.read()).isNull();
			}
			try (final var lineReader = new LineReader(new StringReader("line1\n\rline2"), LineSeparator.DEFAULT, false)) {
				assertThat(lineReader.read()).isEqualTo("line1");
				assertThat(lineReader.read()).isEmpty();
				assertThat(lineReader.read()).isEqualTo("line2");
				assertThat(lineReader.read()).isNull();
			}
			try (final var lineReader = new LineReader(new StringReader("line1\n\rline2\n"), LineSeparator.DEFAULT, false)) {
				assertThat(lineReader.read()).isEqualTo("line1");
				assertThat(lineReader.read()).isEmpty();
				assertThat(lineReader.read()).isEqualTo("line2");
				assertThat(lineReader.read()).isEmpty();
				assertThat(lineReader.read()).isNull();
			}
			try (final var lineReader = new LineReader(new StringReader("line1\r\nline2\r\n"), LineSeparator.DEFAULT, false)) {
				assertThat(lineReader.read()).isEqualTo("line1");
				assertThat(lineReader.read()).isEmpty();
				assertThat(lineReader.read()).isEqualTo("line2");
				assertThat(lineReader.read()).isEmpty();
				assertThat(lineReader.read()).isEmpty();
				assertThat(lineReader.read()).isNull();
			}
		} catch (final IOException e) {
			fail(e.getMessage());
		}
	}

	@Test
	void testSkipLf() {
		try {
			try (final var lineReader = new LineReader(new StringReader("line1\n\nline2"), LineSeparator.LF)) {
				assertThat(lineReader.skip(0L)).isEqualTo(0L);
				assertThat(lineReader.skip(1L)).isEqualTo(1L);
				assertThat(lineReader.skip(3L)).isEqualTo(2L);
				assertThat(lineReader.skip(1L)).isEqualTo(0L);
			}
			try (final var lineReader = new LineReader(new StringReader("line1\n\nline2\n"), LineSeparator.LF)) {
				assertThat(lineReader.skip(0L)).isEqualTo(0L);
				assertThat(lineReader.skip(1L)).isEqualTo(1L);
				assertThat(lineReader.skip(3L)).isEqualTo(2L);
				assertThat(lineReader.skip(1L)).isEqualTo(0L);
			}
			try (final var lineReader = new LineReader(new StringReader("line1\n\nline2\n\n"), LineSeparator.LF)) {
				assertThat(lineReader.skip(0L)).isEqualTo(0L);
				assertThat(lineReader.skip(1L)).isEqualTo(1L);
				assertThat(lineReader.skip(4L)).isEqualTo(3L);
				assertThat(lineReader.skip(1L)).isEqualTo(0L);
			}
			try (final var lineReader = new LineReader(new StringReader("line1\n\nline2"), LineSeparator.LF, false)) {
				assertThat(lineReader.skip(0L)).isEqualTo(0L);
				assertThat(lineReader.skip(1L)).isEqualTo(1L);
				assertThat(lineReader.skip(3L)).isEqualTo(2L);
				assertThat(lineReader.skip(1L)).isEqualTo(0L);
			}
			try (final var lineReader = new LineReader(new StringReader("line1\n\nline2\n"), LineSeparator.LF, false)) {
				assertThat(lineReader.skip(0L)).isEqualTo(0L);
				assertThat(lineReader.skip(1L)).isEqualTo(1L);
				assertThat(lineReader.skip(4L)).isEqualTo(3L);
				assertThat(lineReader.skip(1L)).isEqualTo(0L);
			}
			try (final var lineReader = new LineReader(new StringReader("line1\n\nline2\n\n"), LineSeparator.LF, false)) {
				assertThat(lineReader.skip(0L)).isEqualTo(0L);
				assertThat(lineReader.skip(1L)).isEqualTo(1L);
				assertThat(lineReader.skip(5L)).isEqualTo(4L);
				assertThat(lineReader.skip(1L)).isEqualTo(0L);
			}
		} catch (final IOException e) {
			fail(e.getMessage());
		}
	}

	@Test
	void testSkipCrLf() {
		try {
			try (final var lineReader = new LineReader(new StringReader("line1\r\n\r\nline2"), LineSeparator.CR_LF)) {
				assertThat(lineReader.skip(0L)).isEqualTo(0L);
				assertThat(lineReader.skip(1L)).isEqualTo(1L);
				assertThat(lineReader.skip(3L)).isEqualTo(2L);
				assertThat(lineReader.skip(1L)).isEqualTo(0L);
			}
			try (final var lineReader = new LineReader(new StringReader("line1\r\n\r\nline2\r\n"), LineSeparator.CR_LF)) {
				assertThat(lineReader.skip(0L)).isEqualTo(0L);
				assertThat(lineReader.skip(1L)).isEqualTo(1L);
				assertThat(lineReader.skip(3L)).isEqualTo(2L);
				assertThat(lineReader.skip(1L)).isEqualTo(0L);
			}
			try (final var lineReader = new LineReader(new StringReader("line1\r\n\r\nline2\r\n\r\n"), LineSeparator.CR_LF)) {
				assertThat(lineReader.skip(0L)).isEqualTo(0L);
				assertThat(lineReader.skip(1L)).isEqualTo(1L);
				assertThat(lineReader.skip(4L)).isEqualTo(3L);
				assertThat(lineReader.skip(1L)).isEqualTo(0L);
			}
			try (final var lineReader = new LineReader(new StringReader("line1\r\n\r\nline2"), LineSeparator.CR_LF, false)) {
				assertThat(lineReader.skip(0L)).isEqualTo(0L);
				assertThat(lineReader.skip(1L)).isEqualTo(1L);
				assertThat(lineReader.skip(3L)).isEqualTo(2L);
				assertThat(lineReader.skip(1L)).isEqualTo(0L);
			}
			try (final var lineReader = new LineReader(new StringReader("line1\r\n\r\nline2\r\n"), LineSeparator.CR_LF, false)) {
				assertThat(lineReader.skip(0L)).isEqualTo(0L);
				assertThat(lineReader.skip(1L)).isEqualTo(1L);
				assertThat(lineReader.skip(4L)).isEqualTo(3L);
				assertThat(lineReader.skip(1L)).isEqualTo(0L);
			}
			try (final var lineReader = new LineReader(new StringReader("line1\r\n\r\nline2\r\n\r\n"), LineSeparator.CR_LF, false)) {
				assertThat(lineReader.skip(0L)).isEqualTo(0L);
				assertThat(lineReader.skip(1L)).isEqualTo(1L);
				assertThat(lineReader.skip(5L)).isEqualTo(4L);
				assertThat(lineReader.skip(1L)).isEqualTo(0L);
			}
		} catch (final IOException e) {
			fail(e.getMessage());
		}
	}

	@Test
	void testSkipCr() {
		try {
			try (final var lineReader = new LineReader(new StringReader("line1\r\rline2"), LineSeparator.CR)) {
				assertThat(lineReader.skip(0L)).isEqualTo(0L);
				assertThat(lineReader.skip(1L)).isEqualTo(1L);
				assertThat(lineReader.skip(3L)).isEqualTo(2L);
				assertThat(lineReader.skip(1L)).isEqualTo(0L);
			}
			try (final var lineReader = new LineReader(new StringReader("line1\r\rline2\r"), LineSeparator.CR)) {
				assertThat(lineReader.skip(0L)).isEqualTo(0L);
				assertThat(lineReader.skip(1L)).isEqualTo(1L);
				assertThat(lineReader.skip(3L)).isEqualTo(2L);
				assertThat(lineReader.skip(1L)).isEqualTo(0L);
			}
			try (final var lineReader = new LineReader(new StringReader("line1\r\rline2\r\r"), LineSeparator.CR)) {
				assertThat(lineReader.skip(0L)).isEqualTo(0L);
				assertThat(lineReader.skip(1L)).isEqualTo(1L);
				assertThat(lineReader.skip(4L)).isEqualTo(3L);
				assertThat(lineReader.skip(1L)).isEqualTo(0L);
			}
			try (final var lineReader = new LineReader(new StringReader("line1\r\rline2"), LineSeparator.CR, false)) {
				assertThat(lineReader.skip(0L)).isEqualTo(0L);
				assertThat(lineReader.skip(1L)).isEqualTo(1L);
				assertThat(lineReader.skip(3L)).isEqualTo(2L);
				assertThat(lineReader.skip(1L)).isEqualTo(0L);
			}
			try (final var lineReader = new LineReader(new StringReader("line1\r\rline2\r"), LineSeparator.CR, false)) {
				assertThat(lineReader.skip(0L)).isEqualTo(0L);
				assertThat(lineReader.skip(1L)).isEqualTo(1L);
				assertThat(lineReader.skip(4L)).isEqualTo(3L);
				assertThat(lineReader.skip(1L)).isEqualTo(0L);
			}
			try (final var lineReader = new LineReader(new StringReader("line1\r\rline2\r\r"), LineSeparator.CR, false)) {
				assertThat(lineReader.skip(0L)).isEqualTo(0L);
				assertThat(lineReader.skip(1L)).isEqualTo(1L);
				assertThat(lineReader.skip(5L)).isEqualTo(4L);
				assertThat(lineReader.skip(1L)).isEqualTo(0L);
			}
		} catch (final IOException e) {
			fail(e.getMessage());
		}
	}

	@Test
	void testSkipDefault() {
		try {
			try (final var lineReader = new LineReader(new StringReader("line1\n\rline2"), LineSeparator.DEFAULT)) {
				assertThat(lineReader.skip(0L)).isEqualTo(0L);
				assertThat(lineReader.skip(1L)).isEqualTo(1L);
				assertThat(lineReader.skip(3L)).isEqualTo(2L);
				assertThat(lineReader.skip(1L)).isEqualTo(0L);
			}
			try (final var lineReader = new LineReader(new StringReader("line1\n\rline2\n"), LineSeparator.DEFAULT)) {
				assertThat(lineReader.skip(0L)).isEqualTo(0L);
				assertThat(lineReader.skip(1L)).isEqualTo(1L);
				assertThat(lineReader.skip(3L)).isEqualTo(2L);
				assertThat(lineReader.skip(1L)).isEqualTo(0L);
			}
			try (final var lineReader = new LineReader(new StringReader("line1\r\nline2\r\n"), LineSeparator.DEFAULT)) {
				assertThat(lineReader.skip(0L)).isEqualTo(0L);
				assertThat(lineReader.skip(1L)).isEqualTo(1L);
				assertThat(lineReader.skip(4L)).isEqualTo(3L);
				assertThat(lineReader.skip(1L)).isEqualTo(0L);
			}
			try (final var lineReader = new LineReader(new StringReader("line1\n\rline2"), LineSeparator.DEFAULT, false)) {
				assertThat(lineReader.skip(0L)).isEqualTo(0L);
				assertThat(lineReader.skip(1L)).isEqualTo(1L);
				assertThat(lineReader.skip(3L)).isEqualTo(2L);
				assertThat(lineReader.skip(1L)).isEqualTo(0L);
			}
			try (final var lineReader = new LineReader(new StringReader("line1\n\rline2\n"), LineSeparator.DEFAULT, false)) {
				assertThat(lineReader.skip(0L)).isEqualTo(0L);
				assertThat(lineReader.skip(1L)).isEqualTo(1L);
				assertThat(lineReader.skip(4L)).isEqualTo(3L);
				assertThat(lineReader.skip(1L)).isEqualTo(0L);
			}
			try (final var lineReader = new LineReader(new StringReader("line1\r\nline2\r\n"), LineSeparator.DEFAULT, false)) {
				assertThat(lineReader.skip(0L)).isEqualTo(0L);
				assertThat(lineReader.skip(1L)).isEqualTo(1L);
				assertThat(lineReader.skip(5L)).isEqualTo(4L);
				assertThat(lineReader.skip(1L)).isEqualTo(0L);
			}
		} catch (final IOException e) {
			fail(e.getMessage());
		}
	}

	@Test
	void testSkipInvalid() {
		assertThatIllegalArgumentException().isThrownBy(() -> new LineReader().skip(-1L));
	}

	@Test
	void testTransferTo() {
		try {
			try (final var emptyLineReader = new LineReader(new StringReader(""))) {
				assertThat(emptyLineReader.transferTo(new LineWriter(new StringWriter()))).isEqualTo(0L);
			}
			try (final var lineReader = new LineReader(Files.newBufferedReader(INPUT))) {
				assertThat(lineReader.transferTo(new LineWriter(new StringWriter()))).isEqualTo(Files.lines(INPUT).count());
			}
		} catch (final IOException e) {
			Assertions.fail(e.getMessage());
		}
	}

	@Test
	void testTransferToNull() {
		assertThatNullPointerException().isThrownBy(() -> new LineReader(new StringReader("")).transferTo(null));
	}
}