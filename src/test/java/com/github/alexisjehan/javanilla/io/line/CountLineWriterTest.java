package com.github.alexisjehan.javanilla.io.line;

import com.github.alexisjehan.javanilla.io.Writers;
import com.github.alexisjehan.javanilla.lang.array.ObjectArrays;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNullPointerException;

final class CountLineWriterTest {

	private static final String[] LINES = ObjectArrays.of("abc", "def", "ghi");

	@Test
	void testConstructorInvalid() {
		assertThatNullPointerException().isThrownBy(() -> new CountLineWriter(null));
	}

	@Test
	void testWrite() throws IOException {
		try (final var countLineWriter = new CountLineWriter(new LineWriter(Writers.EMPTY))) {
			assertThat(countLineWriter.getCount()).isZero();
			countLineWriter.write(LINES[0]);
			assertThat(countLineWriter.getCount()).isEqualTo(1L);
			countLineWriter.write(LINES[1]);
			assertThat(countLineWriter.getCount()).isEqualTo(2L);
			countLineWriter.write(LINES[2]);
			assertThat(countLineWriter.getCount()).isEqualTo(3L);
			countLineWriter.flush();
		}
	}

	@Test
	void testNewLine() throws IOException {
		try (final var countLineWriter = new CountLineWriter(new LineWriter(Writers.EMPTY))) {
			assertThat(countLineWriter.getCount()).isZero();
			countLineWriter.newLine();
			assertThat(countLineWriter.getCount()).isEqualTo(1L);
			countLineWriter.newLine();
			assertThat(countLineWriter.getCount()).isEqualTo(2L);
			countLineWriter.newLine();
			assertThat(countLineWriter.getCount()).isEqualTo(3L);
			countLineWriter.flush();
		}
	}
}