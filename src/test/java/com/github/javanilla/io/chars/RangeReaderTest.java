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
package com.github.javanilla.io.chars;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Objects;

import static org.assertj.core.api.Assertions.*;

/**
 * <p>{@link RangeReader} unit tests.</p>
 */
final class RangeReaderTest {

	private static final Path INPUT = new File(Objects.requireNonNull(MethodHandles.lookup().lookupClass().getClassLoader().getResource("input.txt")).getFile()).toPath();

	@Test
	void testConstructorNull() {
		assertThatNullPointerException().isThrownBy(() -> new RangeReader(null, 6L, 20L));
	}

	@Test
	void testConstructorInvalid() {
		assertThatExceptionOfType(IndexOutOfBoundsException.class).isThrownBy(() -> new RangeReader(Readers.EMPTY, -5L, 20L));
		assertThatExceptionOfType(IndexOutOfBoundsException.class).isThrownBy(() -> new RangeReader(Readers.EMPTY, 10L, 5L));
	}

	@Test
	void testReadRangeOneByOne() {
		try (final var rangeReader = new RangeReader(Files.newBufferedReader(INPUT), 6L, 10L)) {
			assertThat(rangeReader.getFromIndex()).isEqualTo(6L);
			assertThat(rangeReader.getToIndex()).isEqualTo(10L);
			final var chars = new String(Files.readAllBytes(INPUT)).toCharArray();
			for (var i = 6; i <= 10; ++i) {
				assertThat(rangeReader.read()).isEqualTo(chars[i]); // i, p, s, u, m
			}
			assertThat(rangeReader.read()).isEqualTo(-1);
		} catch (final IOException e) {
			fail(e.getMessage());
		}
	}

	@Test
	void testReadRangeBuffered() {
		try (final var rangeReader = new RangeReader(Files.newBufferedReader(INPUT), 6L, 20L)) {
			assertThat(rangeReader.getFromIndex()).isEqualTo(6L);
			assertThat(rangeReader.getToIndex()).isEqualTo(20L);
			final var chars = new String(Files.readAllBytes(INPUT)).toCharArray();
			final var buffer = new char[6];
			var offset = 6;
			while (true) {
				final var read = rangeReader.read(buffer, 0, buffer.length);
				if (-1 != read) {
					// "ipsum ", "dolor ", "sit"
					assertThat(Arrays.copyOfRange(buffer, 0, read)).isEqualTo(Arrays.copyOfRange(chars, offset, offset += read));
				} else {
					break;
				}
			}
			assertThat(rangeReader.read()).isEqualTo(-1);
		} catch (final IOException e) {
			fail(e.getMessage());
		}
	}

	@Test
	void testReadAllOneByOne() {
		try (final var rangeReader = new RangeReader(Files.newBufferedReader(INPUT), 2000L)) {
			assertThat(rangeReader.getFromIndex()).isEqualTo(0L);
			assertThat(rangeReader.getToIndex()).isEqualTo(2000L);
			var count = 0L;
			while (-1 != rangeReader.read()) {
				++count;
			}
			assertThat(count).isEqualTo(INPUT.toFile().length());
		} catch (final IOException e) {
			fail(e.getMessage());
		}
	}

	@Test
	void testReadAllBuffered() {
		try (final var rangeReader = new RangeReader(Files.newBufferedReader(INPUT), 2000L)) {
			assertThat(rangeReader.getFromIndex()).isEqualTo(0L);
			assertThat(rangeReader.getToIndex()).isEqualTo(2000L);
			final var buffer = new char[6];
			var count = 0L;
			int read;
			while (-1 != (read = rangeReader.read(buffer, 0, buffer.length))) {
				count += read;
			}
			assertThat(count).isEqualTo(INPUT.toFile().length());
		} catch (final IOException e) {
			fail(e.getMessage());
		}
	}

	@Test
	void testReadOutOneByOne() {
		try (final var rangeReader = new RangeReader(Files.newBufferedReader(INPUT), 1000L, 2000L)) {
			assertThat(rangeReader.getFromIndex()).isEqualTo(1000L);
			assertThat(rangeReader.getToIndex()).isEqualTo(2000L);
			assertThat(rangeReader.read()).isEqualTo(-1);
		} catch (final IOException e) {
			fail(e.getMessage());
		}
	}

	@Test
	void testReadOutBuffered() {
		try (final var rangeReader = new RangeReader(Files.newBufferedReader(INPUT), 1000L, 2000L)) {
			assertThat(rangeReader.getFromIndex()).isEqualTo(1000L);
			assertThat(rangeReader.getToIndex()).isEqualTo(2000L);
			final var buffer = new char[6];
			assertThat(-1).isEqualTo(rangeReader.read(buffer, 0, buffer.length));
		} catch (final IOException e) {
			fail(e.getMessage());
		}
	}

	@Test
	void testSkip() {
		try (final var rangeReader = new RangeReader(Files.newBufferedReader(INPUT), 6L, 20L)) {
			assertThat(rangeReader.getFromIndex()).isEqualTo(6L);
			assertThat(rangeReader.getToIndex()).isEqualTo(20L);
			final var chars = new String(Files.readAllBytes(INPUT)).toCharArray();
			assertThat(rangeReader.read()).isEqualTo(chars[6]);
			rangeReader.skip(5L);
			assertThat(rangeReader.read()).isEqualTo(chars[12]);
			rangeReader.skip(0L);
			assertThat(rangeReader.read()).isEqualTo(chars[13]);
			rangeReader.skip(5L);
			assertThat(rangeReader.read()).isEqualTo(chars[19]);
			rangeReader.skip(1L);
			assertThat(rangeReader.read()).isEqualTo(-1);
		} catch (final IOException e) {
			fail(e.getMessage());
		}
	}

	@Test
	void testMark() {
		try (final var rangeReader = new RangeReader(Files.newBufferedReader(INPUT), 6L, 20L)) {
			assertThat(rangeReader.getFromIndex()).isEqualTo(6L);
			assertThat(rangeReader.getToIndex()).isEqualTo(20L);
			final var chars = new String(Files.readAllBytes(INPUT)).toCharArray();
			assertThat(rangeReader.read()).isEqualTo(chars[6]);
			rangeReader.mark(10);
			assertThat(rangeReader.read()).isEqualTo(chars[7]);
			rangeReader.skip(10L);
			assertThat(rangeReader.read()).isEqualTo(chars[18]);
			rangeReader.reset();
			assertThat(rangeReader.read()).isEqualTo(chars[7]);
			assertThat(rangeReader.read()).isEqualTo(chars[8]);
			rangeReader.skip(12L);
			assertThat(rangeReader.read()).isEqualTo(-1);
			rangeReader.reset();
			assertThat(rangeReader.read()).isEqualTo(chars[7]);
		} catch (final IOException e) {
			fail(e.getMessage());
		}
	}
}