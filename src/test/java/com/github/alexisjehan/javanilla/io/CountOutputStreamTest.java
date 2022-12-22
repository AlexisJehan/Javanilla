package com.github.alexisjehan.javanilla.io;

import com.github.alexisjehan.javanilla.lang.array.ByteArrays;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.assertj.core.api.Assertions.assertThatNullPointerException;

final class CountOutputStreamTest {

	private static final byte[] BYTES = ByteArrays.of((byte) 1, (byte) 2, (byte) 3);

	@Test
	void testConstructorInvalid() {
		assertThatNullPointerException().isThrownBy(() -> new CountOutputStream(null));
	}

	@Test
	void testWriteByte() throws IOException {
		try (final var countOutputStream = new CountOutputStream(OutputStreams.EMPTY)) {
			assertThat(countOutputStream.getCount()).isZero();
			countOutputStream.write(BYTES[0]);
			assertThat(countOutputStream.getCount()).isEqualTo(1L);
			countOutputStream.write(BYTES[1]);
			assertThat(countOutputStream.getCount()).isEqualTo(2L);
			countOutputStream.write(BYTES[2]);
			assertThat(countOutputStream.getCount()).isEqualTo(3L);
		}
	}

	@Test
	void testWriteBytes() throws IOException {
		try (final var countOutputStream = new CountOutputStream(OutputStreams.EMPTY)) {
			assertThat(countOutputStream.getCount()).isZero();
			countOutputStream.write(BYTES, 0, 0);
			assertThat(countOutputStream.getCount()).isZero();
			countOutputStream.write(BYTES, 0, 2);
			assertThat(countOutputStream.getCount()).isEqualTo(2L);
		}
	}

	@Test
	void testWriteBytesInvalid() throws IOException {
		try (final var countOutputStream = new CountOutputStream(OutputStreams.EMPTY)) {
			assertThatNullPointerException().isThrownBy(() -> countOutputStream.write(null, 0, 2));
			assertThatIllegalArgumentException().isThrownBy(() -> countOutputStream.write(BYTES, -1, 3));
			assertThatIllegalArgumentException().isThrownBy(() -> countOutputStream.write(BYTES, 4, 3));
			assertThatIllegalArgumentException().isThrownBy(() -> countOutputStream.write(BYTES, 0, -1));
			assertThatIllegalArgumentException().isThrownBy(() -> countOutputStream.write(BYTES, 0, 4));
		}
	}
}