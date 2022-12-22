package com.github.alexisjehan.javanilla.io.line;

import com.github.alexisjehan.javanilla.io.Writers;
import com.github.alexisjehan.javanilla.lang.array.ObjectArrays;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.StringWriter;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.assertj.core.api.Assertions.assertThatNullPointerException;

final class RangeLineWriterTest {

	private static final String[] LINES = ObjectArrays.of("abc", "def", "ghi");

	@Test
	void testConstructorInvalid() {
		assertThatNullPointerException().isThrownBy(() -> new RangeLineWriter(null, 0L, 0L));
		assertThatIllegalArgumentException().isThrownBy(() -> new RangeLineWriter(new LineWriter(Writers.EMPTY), -1L, 0L));
		assertThatIllegalArgumentException().isThrownBy(() -> new RangeLineWriter(new LineWriter(Writers.EMPTY), 1L, 0L));
	}

	@Test
	void testWrite() throws IOException {
		try (final var writer = new StringWriter()) {
			try (final var rangeLineWriter = new RangeLineWriter(new LineWriter(writer), 0L, 0L)) {
				assertThat(rangeLineWriter.getFromIndex()).isZero();
				assertThat(rangeLineWriter.getToIndex()).isZero();
				rangeLineWriter.write(LINES[0]);
				rangeLineWriter.write(LINES[1]);
				rangeLineWriter.write(LINES[2]);
				rangeLineWriter.flush();
			}
			assertThat(writer).hasToString(LINES[0]);
		}
		try (final var writer = new StringWriter()) {
			try (final var rangeLineWriter = new RangeLineWriter(new LineWriter(writer), 1L, 1L)) {
				assertThat(rangeLineWriter.getFromIndex()).isEqualTo(1L);
				assertThat(rangeLineWriter.getToIndex()).isEqualTo(1L);
				rangeLineWriter.write(LINES[0]);
				rangeLineWriter.write(LINES[1]);
				rangeLineWriter.write(LINES[2]);
				rangeLineWriter.flush();
			}
			assertThat(writer).hasToString(LINES[1]);
		}
		try (final var writer = new StringWriter()) {
			try (final var rangeLineWriter = new RangeLineWriter(new LineWriter(writer), 0L, 10L)) {
				assertThat(rangeLineWriter.getFromIndex()).isZero();
				assertThat(rangeLineWriter.getToIndex()).isEqualTo(10L);
				rangeLineWriter.write(LINES[0]);
				rangeLineWriter.write(LINES[1]);
				rangeLineWriter.write(LINES[2]);
				rangeLineWriter.flush();
			}
			assertThat(writer).hasToString(String.join(System.lineSeparator(), LINES[0], LINES[1], LINES[2]));
		}
		try (final var writer = new StringWriter()) {
			try (final var rangeLineWriter = new RangeLineWriter(new LineWriter(writer), 10L, 10L)) {
				assertThat(rangeLineWriter.getFromIndex()).isEqualTo(10L);
				assertThat(rangeLineWriter.getToIndex()).isEqualTo(10L);
				rangeLineWriter.write(LINES[0]);
				rangeLineWriter.write(LINES[1]);
				rangeLineWriter.write(LINES[2]);
				rangeLineWriter.flush();
			}
			assertThat(writer.toString()).isEmpty();
		}
	}

	@Test
	void testNewLine() throws IOException {
		try (final var writer = new StringWriter()) {
			try (final var rangeLineWriter = new RangeLineWriter(new LineWriter(writer), 0L, 0L)) {
				assertThat(rangeLineWriter.getFromIndex()).isZero();
				assertThat(rangeLineWriter.getToIndex()).isZero();
				rangeLineWriter.newLine();
				rangeLineWriter.newLine();
				rangeLineWriter.newLine();
				rangeLineWriter.flush();
			}
			assertThat(writer).hasToString(System.lineSeparator());
		}
		try (final var writer = new StringWriter()) {
			try (final var rangeLineWriter = new RangeLineWriter(new LineWriter(writer), 1L, 1L)) {
				assertThat(rangeLineWriter.getFromIndex()).isEqualTo(1L);
				assertThat(rangeLineWriter.getToIndex()).isEqualTo(1L);
				rangeLineWriter.newLine();
				rangeLineWriter.newLine();
				rangeLineWriter.newLine();
				rangeLineWriter.flush();
			}
			assertThat(writer).hasToString(System.lineSeparator());
		}
		try (final var writer = new StringWriter()) {
			try (final var rangeLineWriter = new RangeLineWriter(new LineWriter(writer), 0L, 10L)) {
				assertThat(rangeLineWriter.getFromIndex()).isZero();
				assertThat(rangeLineWriter.getToIndex()).isEqualTo(10L);
				rangeLineWriter.newLine();
				rangeLineWriter.newLine();
				rangeLineWriter.newLine();
				rangeLineWriter.flush();
			}
			assertThat(writer).hasToString(System.lineSeparator() + System.lineSeparator() + System.lineSeparator());
		}
		try (final var writer = new StringWriter()) {
			try (final var rangeLineWriter = new RangeLineWriter(new LineWriter(writer), 10L, 10L)) {
				assertThat(rangeLineWriter.getFromIndex()).isEqualTo(10L);
				assertThat(rangeLineWriter.getToIndex()).isEqualTo(10L);
				rangeLineWriter.newLine();
				rangeLineWriter.newLine();
				rangeLineWriter.newLine();
				rangeLineWriter.flush();
			}
			assertThat(writer.toString()).isEmpty();
		}
	}
}