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
		try (final var lineReader = new LineReader(tmpFile)) {
			assertThat(lineReader.read()).isEqualTo(LINES[0]);
			assertThat(lineReader.read()).isEqualTo(LINES[1]);
			assertThat(lineReader.read()).isEqualTo(LINES[2]);
			assertThat(lineReader.read()).isNull();
		}
		Files.write(tmpFile, List.of(LINES), StandardCharsets.ISO_8859_1);
		try (final var lineReader = new LineReader(tmpFile, StandardCharsets.ISO_8859_1)) {
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
		try (final var lineReader = new LineReader(new StringReader("foo\n\rbar"), LineSeparator.DEFAULT)) {
			assertThat(lineReader.read()).isEqualTo("foo");
			assertThat(lineReader.read()).isEmpty();
			assertThat(lineReader.read()).isEqualTo("bar");
			assertThat(lineReader.read()).isNull();
		}
		try (final var lineReader = new LineReader(new StringReader("foo\n\rbar\n"), LineSeparator.DEFAULT)) {
			assertThat(lineReader.read()).isEqualTo("foo");
			assertThat(lineReader.read()).isEmpty();
			assertThat(lineReader.read()).isEqualTo("bar");
			assertThat(lineReader.read()).isNull();
		}
		try (final var lineReader = new LineReader(new StringReader("foo\r\nbar\r\n"), LineSeparator.DEFAULT)) {
			assertThat(lineReader.read()).isEqualTo("foo");
			assertThat(lineReader.read()).isEmpty();
			assertThat(lineReader.read()).isEqualTo("bar");
			assertThat(lineReader.read()).isEmpty();
			assertThat(lineReader.read()).isNull();
		}
		try (final var lineReader = new LineReader(new StringReader("foo\n\rbar"), LineSeparator.DEFAULT, false)) {
			assertThat(lineReader.read()).isEqualTo("foo");
			assertThat(lineReader.read()).isEmpty();
			assertThat(lineReader.read()).isEqualTo("bar");
			assertThat(lineReader.read()).isNull();
		}
		try (final var lineReader = new LineReader(new StringReader("foo\n\rbar\n"), LineSeparator.DEFAULT, false)) {
			assertThat(lineReader.read()).isEqualTo("foo");
			assertThat(lineReader.read()).isEmpty();
			assertThat(lineReader.read()).isEqualTo("bar");
			assertThat(lineReader.read()).isEmpty();
			assertThat(lineReader.read()).isNull();
		}
		try (final var lineReader = new LineReader(new StringReader("foo\r\nbar\r\n"), LineSeparator.DEFAULT, false)) {
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
		try (final var lineReader = new LineReader(new StringReader("foo\n\rbar"), LineSeparator.DEFAULT)) {
			assertThat(lineReader.skip(0L)).isZero();
			assertThat(lineReader.skip(1L)).isEqualTo(1L);
			assertThat(lineReader.skip(3L)).isEqualTo(2L);
			assertThat(lineReader.skip(1L)).isZero();
		}
		try (final var lineReader = new LineReader(new StringReader("foo\n\rbar\n"), LineSeparator.DEFAULT)) {
			assertThat(lineReader.skip(0L)).isZero();
			assertThat(lineReader.skip(1L)).isEqualTo(1L);
			assertThat(lineReader.skip(3L)).isEqualTo(2L);
			assertThat(lineReader.skip(1L)).isZero();
		}
		try (final var lineReader = new LineReader(new StringReader("foo\r\nbar\r\n"), LineSeparator.DEFAULT)) {
			assertThat(lineReader.skip(0L)).isZero();
			assertThat(lineReader.skip(1L)).isEqualTo(1L);
			assertThat(lineReader.skip(4L)).isEqualTo(3L);
			assertThat(lineReader.skip(1L)).isZero();
		}
		try (final var lineReader = new LineReader(new StringReader("foo\n\rbar"), LineSeparator.DEFAULT, false)) {
			assertThat(lineReader.skip(0L)).isZero();
			assertThat(lineReader.skip(1L)).isEqualTo(1L);
			assertThat(lineReader.skip(3L)).isEqualTo(2L);
			assertThat(lineReader.skip(1L)).isZero();
		}
		try (final var lineReader = new LineReader(new StringReader("foo\n\rbar\n"), LineSeparator.DEFAULT, false)) {
			assertThat(lineReader.skip(0L)).isZero();
			assertThat(lineReader.skip(1L)).isEqualTo(1L);
			assertThat(lineReader.skip(4L)).isEqualTo(3L);
			assertThat(lineReader.skip(1L)).isZero();
		}
		try (final var lineReader = new LineReader(new StringReader("foo\r\nbar\r\n"), LineSeparator.DEFAULT, false)) {
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
		try (final var writer = new StringWriter()) {
			try (final var lineReader = new LineReader(Readers.EMPTY, LineSeparator.DEFAULT)) {
				assertThat(lineReader.transferTo(new LineWriter(writer))).isZero();
			}
			assertThat(writer.toString()).isEmpty();
		}
		try (final var writer = new StringWriter()) {
			try (final var lineReader = new LineReader(Readers.of(String.join("\n", LINES)), LineSeparator.DEFAULT)) {
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