package com.github.alexisjehan.javanilla.io;

import com.github.alexisjehan.javanilla.lang.array.ByteArrays;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.assertj.core.api.Assertions.assertThatNullPointerException;

final class CountInputStreamTest {

	private static final byte[] BYTES = ByteArrays.of((byte) 1, (byte) 2, (byte) 3);

	@Test
	void testConstructorInvalid() {
		assertThatNullPointerException().isThrownBy(() -> new CountInputStream(null));
	}

	@Test
	void testReadByte() throws IOException {
		try (final var countInputStream = new CountInputStream(InputStreams.of(BYTES))) {
			assertThat(countInputStream.getCount()).isZero();
			assertThat(countInputStream.read()).isEqualTo(BYTES[0]);
			assertThat(countInputStream.getCount()).isEqualTo(1L);
			assertThat(countInputStream.read()).isEqualTo(BYTES[1]);
			assertThat(countInputStream.getCount()).isEqualTo(2L);
			assertThat(countInputStream.read()).isEqualTo(BYTES[2]);
			assertThat(countInputStream.getCount()).isEqualTo(3L);
			assertThat(countInputStream.read()).isEqualTo(-1);
			assertThat(countInputStream.getCount()).isEqualTo(3L);
		}
	}

	@Test
	void testReadBuffer() throws IOException {
		final var buffer = new byte[2];
		try (final var countInputStream = new CountInputStream(InputStreams.of(BYTES))) {
			assertThat(countInputStream.getCount()).isZero();
			assertThat(countInputStream.read(buffer, 0, 0)).isZero();
			assertThat(countInputStream.getCount()).isZero();
			assertThat(countInputStream.read(buffer, 0, 2)).isEqualTo(2);
			assertThat(countInputStream.getCount()).isEqualTo(2L);
			assertThat(countInputStream.read(buffer, 0, 2)).isEqualTo(1);
			assertThat(countInputStream.getCount()).isEqualTo(3L);
			assertThat(countInputStream.read(buffer, 0, 2)).isEqualTo(-1);
			assertThat(countInputStream.getCount()).isEqualTo(3L);
		}
	}

	@Test
	void testReadBufferInvalid() throws IOException {
		final var buffer = new byte[2];
		try (final var countInputStream = new CountInputStream(InputStreams.of(BYTES))) {
			assertThatNullPointerException().isThrownBy(() -> countInputStream.read(null, 0, 2));
			assertThatIllegalArgumentException().isThrownBy(() -> countInputStream.read(buffer, -1, 2));
			assertThatIllegalArgumentException().isThrownBy(() -> countInputStream.read(buffer, 3, 2));
			assertThatIllegalArgumentException().isThrownBy(() -> countInputStream.read(buffer, 0, -1));
			assertThatIllegalArgumentException().isThrownBy(() -> countInputStream.read(buffer, 0, 3));
		}
	}

	@Test
	void testSkip() throws IOException {
		try (final var countInputStream = new CountInputStream(InputStreams.of(BYTES))) {
			assertThat(countInputStream.getCount()).isZero();
			assertThat(countInputStream.skip(-1L)).isZero();
			assertThat(countInputStream.skip(0L)).isZero();
			assertThat(countInputStream.getCount()).isZero();
			assertThat(countInputStream.skip(2L)).isEqualTo(2L);
			assertThat(countInputStream.getCount()).isEqualTo(2L);
			assertThat(countInputStream.skip(2L)).isEqualTo(1L);
			assertThat(countInputStream.getCount()).isEqualTo(3L);
		}
	}

	@Test
	void testMarkAndReset() throws IOException {
		try (final var countInputStream = new CountInputStream(InputStreams.buffered(InputStreams.of(BYTES)))) {
			assertThat(countInputStream.getCount()).isZero();
			assertThat(countInputStream.read()).isEqualTo(BYTES[0]);
			assertThat(countInputStream.getCount()).isEqualTo(1L);
			countInputStream.mark(2);
			assertThat(countInputStream.getCount()).isEqualTo(1L);
			assertThat(countInputStream.read()).isEqualTo(BYTES[1]);
			assertThat(countInputStream.getCount()).isEqualTo(2L);
			countInputStream.reset();
			assertThat(countInputStream.getCount()).isEqualTo(1L);
			assertThat(countInputStream.read()).isEqualTo(BYTES[1]);
			assertThat(countInputStream.getCount()).isEqualTo(2L);
			countInputStream.reset();
			assertThat(countInputStream.getCount()).isEqualTo(1L);
			assertThat(countInputStream.read()).isEqualTo(BYTES[1]);
			assertThat(countInputStream.getCount()).isEqualTo(2L);
		}
	}
}