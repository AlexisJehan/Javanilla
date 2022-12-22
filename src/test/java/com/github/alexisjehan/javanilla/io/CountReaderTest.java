package com.github.alexisjehan.javanilla.io;

import com.github.alexisjehan.javanilla.lang.array.CharArrays;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.assertj.core.api.Assertions.assertThatNullPointerException;

final class CountReaderTest {

	private static final char[] CHARS = CharArrays.of('a', 'b', 'c');

	@Test
	void testConstructorInvalid() {
		assertThatNullPointerException().isThrownBy(() -> new CountReader(null));
	}

	@Test
	void testReadChar() throws IOException {
		try (final var countReader = new CountReader(Readers.of(CHARS))) {
			assertThat(countReader.getCount()).isZero();
			assertThat(countReader.read()).isEqualTo(CHARS[0]);
			assertThat(countReader.getCount()).isEqualTo(1L);
			assertThat(countReader.read()).isEqualTo(CHARS[1]);
			assertThat(countReader.getCount()).isEqualTo(2L);
			assertThat(countReader.read()).isEqualTo(CHARS[2]);
			assertThat(countReader.getCount()).isEqualTo(3L);
			assertThat(countReader.read()).isEqualTo(-1);
			assertThat(countReader.getCount()).isEqualTo(3L);
		}
	}

	@Test
	void testReadBuffer() throws IOException {
		final var buffer = new char[2];
		try (final var countReader = new CountReader(Readers.of(CHARS))) {
			assertThat(countReader.getCount()).isZero();
			assertThat(countReader.read(buffer, 0, 0)).isZero();
			assertThat(countReader.getCount()).isZero();
			assertThat(countReader.read(buffer, 0, 2)).isEqualTo(2);
			assertThat(countReader.getCount()).isEqualTo(2L);
			assertThat(countReader.read(buffer, 0, 2)).isEqualTo(1);
			assertThat(countReader.getCount()).isEqualTo(3L);
			assertThat(countReader.read(buffer, 0, 2)).isEqualTo(-1);
			assertThat(countReader.getCount()).isEqualTo(3L);
		}
	}

	@Test
	void testReadBufferInvalid() throws IOException {
		final var buffer = new char[2];
		try (final var countReader = new CountReader(Readers.of(CHARS))) {
			assertThatNullPointerException().isThrownBy(() -> countReader.read(null, 0, 2));
			assertThatIllegalArgumentException().isThrownBy(() -> countReader.read(buffer, -1, 2));
			assertThatIllegalArgumentException().isThrownBy(() -> countReader.read(buffer, 3, 2));
			assertThatIllegalArgumentException().isThrownBy(() -> countReader.read(buffer, 0, -1));
			assertThatIllegalArgumentException().isThrownBy(() -> countReader.read(buffer, 0, 3));
		}
	}

	@Test
	void testSkip() throws IOException {
		try (final var countReader = new CountReader(Readers.of(CHARS))) {
			assertThat(countReader.getCount()).isZero();
			assertThat(countReader.skip(-1L)).isZero();
			assertThat(countReader.skip(0L)).isZero();
			assertThat(countReader.getCount()).isZero();
			assertThat(countReader.skip(2L)).isEqualTo(2L);
			assertThat(countReader.getCount()).isEqualTo(2L);
			assertThat(countReader.skip(2L)).isEqualTo(1L);
			assertThat(countReader.getCount()).isEqualTo(3L);
		}
	}

	@Test
	void testMarkAndReset() throws IOException {
		try (final var countReader = new CountReader(Readers.of(CHARS))) {
			assertThat(countReader.getCount()).isZero();
			assertThat(countReader.read()).isEqualTo(CHARS[0]);
			assertThat(countReader.getCount()).isEqualTo(1L);
			countReader.mark(2);
			assertThat(countReader.getCount()).isEqualTo(1L);
			assertThat(countReader.read()).isEqualTo(CHARS[1]);
			assertThat(countReader.getCount()).isEqualTo(2L);
			countReader.reset();
			assertThat(countReader.getCount()).isEqualTo(1L);
			assertThat(countReader.read()).isEqualTo(CHARS[1]);
			assertThat(countReader.getCount()).isEqualTo(2L);
			countReader.reset();
			assertThat(countReader.getCount()).isEqualTo(1L);
			assertThat(countReader.read()).isEqualTo(CHARS[1]);
			assertThat(countReader.getCount()).isEqualTo(2L);
		}
	}
}