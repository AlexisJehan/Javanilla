package com.github.alexisjehan.javanilla.io;

import com.github.alexisjehan.javanilla.lang.array.CharArrays;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.assertj.core.api.Assertions.assertThatNullPointerException;

final class CountWriterTest {

	private static final char[] CHARS = CharArrays.of('a', 'b', 'c');

	@Test
	void testConstructorInvalid() {
		assertThatNullPointerException().isThrownBy(() -> new CountWriter(null));
	}

	@Test
	void testWriteChar() throws IOException {
		try (final var countWriter = new CountWriter(Writers.EMPTY)) {
			assertThat(countWriter.getCount()).isZero();
			countWriter.write(CHARS[0]);
			assertThat(countWriter.getCount()).isEqualTo(1L);
			countWriter.write(CHARS[1]);
			assertThat(countWriter.getCount()).isEqualTo(2L);
			countWriter.write(CHARS[2]);
			assertThat(countWriter.getCount()).isEqualTo(3L);
		}
	}

	@Test
	void testWriteChars() throws IOException {
		try (final var countWriter = new CountWriter(Writers.EMPTY)) {
			assertThat(countWriter.getCount()).isZero();
			countWriter.write(CHARS, 0, 0);
			assertThat(countWriter.getCount()).isZero();
			countWriter.write(CHARS, 0, 2);
			assertThat(countWriter.getCount()).isEqualTo(2L);
		}
	}

	@Test
	void testWriteCharsInvalid() throws IOException {
		try (final var countWriter = new CountWriter(Writers.EMPTY)) {
			assertThatNullPointerException().isThrownBy(() -> countWriter.write((char[]) null, 0, 2));
			assertThatIllegalArgumentException().isThrownBy(() -> countWriter.write(CHARS, -1, 3));
			assertThatIllegalArgumentException().isThrownBy(() -> countWriter.write(CHARS, 4, 3));
			assertThatIllegalArgumentException().isThrownBy(() -> countWriter.write(CHARS, 0, -1));
			assertThatIllegalArgumentException().isThrownBy(() -> countWriter.write(CHARS, 0, 4));
		}
	}

	@Test
	void testWriteString() throws IOException {
		try (final var countWriter = new CountWriter(Writers.EMPTY)) {
			assertThat(countWriter.getCount()).isZero();
			countWriter.write(new String(CHARS), 0, 0);
			assertThat(countWriter.getCount()).isZero();
			countWriter.write(new String(CHARS), 0, 2);
			assertThat(countWriter.getCount()).isEqualTo(2L);
		}
	}

	@Test
	void testWriteStringInvalid() throws IOException {
		try (final var countWriter = new CountWriter(Writers.EMPTY)) {
			assertThatNullPointerException().isThrownBy(() -> countWriter.write((String) null, 0, 2));
			assertThatIllegalArgumentException().isThrownBy(() -> countWriter.write(new String(CHARS), -1, 3));
			assertThatIllegalArgumentException().isThrownBy(() -> countWriter.write(new String(CHARS), 4, 3));
			assertThatIllegalArgumentException().isThrownBy(() -> countWriter.write(new String(CHARS), 0, -1));
			assertThatIllegalArgumentException().isThrownBy(() -> countWriter.write(new String(CHARS), 0, 4));
		}
	}
}