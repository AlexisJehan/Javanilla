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
package com.github.alexisjehan.javanilla.io.bytes;

import org.junit.jupiter.api.Test;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Objects;

import static org.assertj.core.api.Assertions.*;

/**
 * <p>{@link RangeInputStream} unit tests.</p>
 */
final class RangeInputStreamTest {

	private static final Path INPUT = new File(Objects.requireNonNull(MethodHandles.lookup().lookupClass().getClassLoader().getResource("input.txt")).getFile()).toPath();

	@Test
	void testConstructorNull() {
		assertThatNullPointerException().isThrownBy(() -> new RangeInputStream(null, 6L, 20L));
	}

	@Test
	void testConstructorInvalid() {
		assertThatExceptionOfType(IndexOutOfBoundsException.class).isThrownBy(() -> new RangeInputStream(InputStreams.EMPTY, -5L, 20L));
		assertThatExceptionOfType(IndexOutOfBoundsException.class).isThrownBy(() -> new RangeInputStream(InputStreams.EMPTY, 10L, 5L));
	}

	@Test
	void testReadRangeOneByOne() {
		try (final var rangeInputStream = new RangeInputStream(Files.newInputStream(INPUT), 6L, 10L)) {
			assertThat(rangeInputStream.getFromIndex()).isEqualTo(6L);
			assertThat(rangeInputStream.getToIndex()).isEqualTo(10L);
			final var bytes = Files.readAllBytes(INPUT);
			for (var i = 6; i <= 10; ++i) {
				assertThat(rangeInputStream.read()).isEqualTo(bytes[i]); // i, p, s, u, m
			}
			assertThat(rangeInputStream.read()).isEqualTo(-1);
		} catch (final IOException e) {
			fail(e.getMessage());
		}
	}

	@Test
	void testReadRangeBuffered() {
		try (final var rangeInputStream = new RangeInputStream(Files.newInputStream(INPUT), 6L, 20L)) {
			assertThat(rangeInputStream.getFromIndex()).isEqualTo(6L);
			assertThat(rangeInputStream.getToIndex()).isEqualTo(20L);
			final var bytes = Files.readAllBytes(INPUT);
			final var buffer = new byte[6];
			var offset = 6;
			while (true) {
				final var read = rangeInputStream.read(buffer, 0, buffer.length);
				if (-1 != read) {
					// "ipsum ", "dolor ", "sit"
					assertThat(Arrays.copyOfRange(buffer, 0, read)).isEqualTo(Arrays.copyOfRange(bytes, offset, offset += read));
				} else {
					break;
				}
			}
			assertThat(rangeInputStream.read()).isEqualTo(-1);
		} catch (final IOException e) {
			fail(e.getMessage());
		}
	}

	@Test
	void testReadAllOneByOne() {
		try (final var rangeInputStream = new RangeInputStream(Files.newInputStream(INPUT), 2000L)) {
			assertThat(rangeInputStream.getFromIndex()).isEqualTo(0L);
			assertThat(rangeInputStream.getToIndex()).isEqualTo(2000L);
			var count = 0L;
			while (-1 != rangeInputStream.read()) {
				++count;
			}
			assertThat(count).isEqualTo(INPUT.toFile().length());
		} catch (final IOException e) {
			fail(e.getMessage());
		}
	}

	@Test
	void testReadAllBuffered() {
		try (final var rangeInputStream = new RangeInputStream(Files.newInputStream(INPUT), 2000L)) {
			assertThat(rangeInputStream.getFromIndex()).isEqualTo(0L);
			assertThat(rangeInputStream.getToIndex()).isEqualTo(2000L);
			final var buffer = new byte[6];
			var count = 0L;
			int read;
			while (-1 != (read = rangeInputStream.read(buffer, 0, buffer.length))) {
				count += read;
			}
			assertThat(count).isEqualTo(INPUT.toFile().length());
		} catch (final IOException e) {
			fail(e.getMessage());
		}
	}

	@Test
	void testReadOutOneByOne() {
		try (final var rangeInputStream = new RangeInputStream(Files.newInputStream(INPUT), 1000L, 2000L)) {
			assertThat(rangeInputStream.getFromIndex()).isEqualTo(1000L);
			assertThat(rangeInputStream.getToIndex()).isEqualTo(2000L);
			assertThat(rangeInputStream.read()).isEqualTo(-1);
		} catch (final IOException e) {
			fail(e.getMessage());
		}
	}

	@Test
	void testReadOutBuffered() {
		try (final var rangeInputStream = new RangeInputStream(Files.newInputStream(INPUT), 1000L, 2000L)) {
			assertThat(rangeInputStream.getFromIndex()).isEqualTo(1000L);
			assertThat(rangeInputStream.getToIndex()).isEqualTo(2000L);
			final var buffer = new byte[6];
			assertThat(-1).isEqualTo(rangeInputStream.read(buffer, 0, buffer.length));
		} catch (final IOException e) {
			fail(e.getMessage());
		}
	}

	@Test
	void testSkip() {
		try (final var rangeInputStream = new RangeInputStream(Files.newInputStream(INPUT), 6L, 20L)) {
			assertThat(rangeInputStream.getFromIndex()).isEqualTo(6L);
			assertThat(rangeInputStream.getToIndex()).isEqualTo(20L);
			final var bytes = Files.readAllBytes(INPUT);
			assertThat(rangeInputStream.read()).isEqualTo(bytes[6]);
			rangeInputStream.skip(5L);
			assertThat(rangeInputStream.read()).isEqualTo(bytes[12]);
			rangeInputStream.skip(0L);
			assertThat(rangeInputStream.read()).isEqualTo(bytes[13]);
			rangeInputStream.skip(5L);
			assertThat(rangeInputStream.read()).isEqualTo(bytes[19]);
			rangeInputStream.skip(1L);
			assertThat(rangeInputStream.read()).isEqualTo(-1);
		} catch (final IOException e) {
			fail(e.getMessage());
		}
	}

	@Test
	void testMark() {
		try (final var rangeInputStream = new RangeInputStream(new BufferedInputStream(Files.newInputStream(INPUT)), 6L, 20L)) {
			assertThat(rangeInputStream.getFromIndex()).isEqualTo(6L);
			assertThat(rangeInputStream.getToIndex()).isEqualTo(20L);
			final var bytes = Files.readAllBytes(INPUT);
			assertThat(rangeInputStream.read()).isEqualTo(bytes[6]);
			rangeInputStream.mark(10);
			assertThat(rangeInputStream.read()).isEqualTo(bytes[7]);
			rangeInputStream.skip(10L);
			assertThat(rangeInputStream.read()).isEqualTo(bytes[18]);
			rangeInputStream.reset();
			assertThat(rangeInputStream.read()).isEqualTo(bytes[7]);
			assertThat(rangeInputStream.read()).isEqualTo(bytes[8]);
			rangeInputStream.skip(12L);
			assertThat(rangeInputStream.read()).isEqualTo(-1);
			rangeInputStream.reset();
			assertThat(rangeInputStream.read()).isEqualTo(bytes[7]);
		} catch (final IOException e) {
			fail(e.getMessage());
		}
	}
}