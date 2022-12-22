package com.github.alexisjehan.javanilla.io.line;

import com.github.alexisjehan.javanilla.io.Readers;
import com.github.alexisjehan.javanilla.lang.array.ObjectArrays;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNullPointerException;

final class CountLineReaderTest {

	private static final String[] LINES = ObjectArrays.of("abc", "def", "ghi");

	@Test
	void testConstructorInvalid() {
		assertThatNullPointerException().isThrownBy(() -> new CountLineReader(null));
	}

	@Test
	void testRead() throws IOException {
		try (final var countLineReader = new CountLineReader(new LineReader(Readers.of(String.join("\n", LINES))))) {
			assertThat(countLineReader.getCount()).isZero();
			assertThat(countLineReader.read()).isEqualTo(LINES[0]);
			assertThat(countLineReader.getCount()).isEqualTo(1L);
			assertThat(countLineReader.read()).isEqualTo(LINES[1]);
			assertThat(countLineReader.getCount()).isEqualTo(2L);
			assertThat(countLineReader.read()).isEqualTo(LINES[2]);
			assertThat(countLineReader.getCount()).isEqualTo(3L);
			assertThat(countLineReader.read()).isNull();
			assertThat(countLineReader.getCount()).isEqualTo(3L);
		}
	}

	@Test
	void testSkip() throws IOException {
		try (final var countLineReader = new CountLineReader(new LineReader(Readers.of(String.join("\n", LINES))))) {
			assertThat(countLineReader.getCount()).isZero();
			assertThat(countLineReader.skip(-1L)).isZero();
			assertThat(countLineReader.skip(0L)).isZero();
			assertThat(countLineReader.getCount()).isZero();
			assertThat(countLineReader.skip(2L)).isEqualTo(2L);
			assertThat(countLineReader.getCount()).isEqualTo(2L);
			assertThat(countLineReader.skip(2L)).isEqualTo(1L);
			assertThat(countLineReader.getCount()).isEqualTo(3L);
		}
	}
}