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

import java.io.CharArrayWriter;
import java.io.File;
import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Objects;

import static org.assertj.core.api.Assertions.*;

/**
 * <p>{@link RangeWriter} unit tests.</p>
 */
final class RangeWriterTest {

	private static final Path INPUT = new File(Objects.requireNonNull(MethodHandles.lookup().lookupClass().getClassLoader().getResource("input.txt")).getFile()).toPath();

	@Test
	void testConstructorNull() {
		assertThatNullPointerException().isThrownBy(() -> new RangeWriter(null, 6L, 20L));
	}

	@Test
	void testConstructorInvalid() {
		assertThatExceptionOfType(IndexOutOfBoundsException.class).isThrownBy(() -> new RangeWriter(Writers.BLANK, -5L, 20L));
		assertThatExceptionOfType(IndexOutOfBoundsException.class).isThrownBy(() -> new RangeWriter(Writers.BLANK, 10L, 5L));
	}

	@Test
	void testWriteRangeOneByOne() {
		try (final var charArrayWriter = new CharArrayWriter()) {
			try (final var rangeWriter = new RangeWriter(charArrayWriter, 6L, 10L)) {
				assertThat(rangeWriter.getFromIndex()).isEqualTo(6L);
				assertThat(rangeWriter.getToIndex()).isEqualTo(10L);
				try (final var reader = Files.newBufferedReader(INPUT)) {
					int c;
					while (-1 != (c = reader.read())) {
						rangeWriter.write(c);
					}
				}
			}
			assertThat(charArrayWriter.toCharArray()).isEqualTo(Arrays.copyOfRange(new String(Files.readAllBytes(INPUT)).toCharArray(), 6, 11)); // ipsum
		} catch (final IOException e) {
			fail(e.getMessage());
		}
	}

	@Test
	void testWriteRangeBuffered() {
		try (final var charArrayWriter = new CharArrayWriter()) {
			try (final var rangeWriter = new RangeWriter(charArrayWriter, 6L, 10L)) {
				assertThat(rangeWriter.getFromIndex()).isEqualTo(6L);
				assertThat(rangeWriter.getToIndex()).isEqualTo(10L);
				try (final var reader = Files.newBufferedReader(INPUT)) {
					final var buffer = new char[5];
					int n;
					while (-1 != (n = reader.read(buffer, 0, buffer.length))) {
						rangeWriter.write(buffer, 0, n);
					}
				}
			}
			assertThat(charArrayWriter.toCharArray()).isEqualTo(Arrays.copyOfRange(new String(Files.readAllBytes(INPUT)).toCharArray(), 6, 11)); // ipsum
		} catch (final IOException e) {
			fail(e.getMessage());
		}
	}

	@Test
	void testWriteRangeBufferedString() {
		try (final var charArrayWriter = new CharArrayWriter()) {
			try (final var rangeWriter = new RangeWriter(charArrayWriter, 6L, 10L)) {
				assertThat(rangeWriter.getFromIndex()).isEqualTo(6L);
				assertThat(rangeWriter.getToIndex()).isEqualTo(10L);
				try (final var reader = Files.newBufferedReader(INPUT)) {
					final var buffer = new char[5];
					int n;
					while (-1 != (n = reader.read(buffer, 0, buffer.length))) {
						rangeWriter.write(new String(buffer), 0, n);
					}
				}
			}
			assertThat(charArrayWriter.toCharArray()).isEqualTo(Arrays.copyOfRange(new String(Files.readAllBytes(INPUT)).toCharArray(), 6, 11)); // ipsum
		} catch (final IOException e) {
			fail(e.getMessage());
		}
	}

	@Test
	void testWriteAllOneByOne() {
		try (final var charArrayWriter = new CharArrayWriter()) {
			try (final var rangeWriter = new RangeWriter(charArrayWriter, 2000L)) {
				assertThat(rangeWriter.getFromIndex()).isEqualTo(0L);
				assertThat(rangeWriter.getToIndex()).isEqualTo(2000L);
				try (final var reader = Files.newBufferedReader(INPUT)) {
					int c;
					while (-1 != (c = reader.read())) {
						rangeWriter.write(c);
					}
				}
			}
			assertThat(charArrayWriter.toCharArray()).isEqualTo(new String(Files.readAllBytes(INPUT)).toCharArray());
		} catch (final IOException e) {
			fail(e.getMessage());
		}
	}

	@Test
	void testWriteAllBuffered() {
		try (final var charArrayWriter = new CharArrayWriter()) {
			try (final var rangeWriter = new RangeWriter(charArrayWriter, 2000L)) {
				assertThat(rangeWriter.getFromIndex()).isEqualTo(0L);
				assertThat(rangeWriter.getToIndex()).isEqualTo(2000L);
				try (final var reader = Files.newBufferedReader(INPUT)) {
					final var buffer = new char[5];
					int n;
					while (-1 != (n = reader.read(buffer, 0, buffer.length))) {
						rangeWriter.write(buffer, 0, n);
					}
				}
			}
			assertThat(charArrayWriter.toCharArray()).isEqualTo(new String(Files.readAllBytes(INPUT)).toCharArray());
		} catch (final IOException e) {
			fail(e.getMessage());
		}
	}

	@Test
	void testWriteAllBufferedString() {
		try (final var charArrayWriter = new CharArrayWriter()) {
			try (final var rangeWriter = new RangeWriter(charArrayWriter, 2000L)) {
				assertThat(rangeWriter.getFromIndex()).isEqualTo(0L);
				assertThat(rangeWriter.getToIndex()).isEqualTo(2000L);
				try (final var reader = Files.newBufferedReader(INPUT)) {
					final var buffer = new char[5];
					int n;
					while (-1 != (n = reader.read(buffer, 0, buffer.length))) {
						rangeWriter.write(new String(buffer), 0, n);
					}
				}
			}
			assertThat(charArrayWriter.toCharArray()).isEqualTo(new String(Files.readAllBytes(INPUT)).toCharArray());
		} catch (final IOException e) {
			fail(e.getMessage());
		}
	}

	@Test
	void testWriteOutOneByOne() {
		try (final var charArrayWriter = new CharArrayWriter()) {
			try (final var rangeWriter = new RangeWriter(charArrayWriter, 1000L, 2000L)) {
				assertThat(rangeWriter.getFromIndex()).isEqualTo(1000L);
				assertThat(rangeWriter.getToIndex()).isEqualTo(2000L);
				try (final var reader = Files.newBufferedReader(INPUT)) {
					int c;
					while (-1 != (c = reader.read())) {
						rangeWriter.write(c);
					}
				}
			}
			assertThat(charArrayWriter.toCharArray()).isEmpty();
		} catch (final IOException e) {
			fail(e.getMessage());
		}
	}

	@Test
	void testWriteOutBuffered() {
		try (final var charArrayWriter = new CharArrayWriter()) {
			try (final var rangeWriter = new RangeWriter(charArrayWriter, 1000L, 2000L)) {
				assertThat(rangeWriter.getFromIndex()).isEqualTo(1000L);
				assertThat(rangeWriter.getToIndex()).isEqualTo(2000L);
				try (final var reader = Files.newBufferedReader(INPUT)) {
					final var buffer = new char[5];
					int n;
					while (-1 != (n = reader.read(buffer, 0, buffer.length))) {
						rangeWriter.write(buffer, 0, n);
					}
				}
			}
			assertThat(charArrayWriter.toCharArray()).isEmpty();
		} catch (final IOException e) {
			fail(e.getMessage());
		}
	}

	@Test
	void testWriteOutBufferedString() {
		try (final var charArrayWriter = new CharArrayWriter()) {
			try (final var rangeWriter = new RangeWriter(charArrayWriter, 1000L, 2000L)) {
				assertThat(rangeWriter.getFromIndex()).isEqualTo(1000L);
				assertThat(rangeWriter.getToIndex()).isEqualTo(2000L);
				try (final var reader = Files.newBufferedReader(INPUT)) {
					final var buffer = new char[5];
					int n;
					while (-1 != (n = reader.read(buffer, 0, buffer.length))) {
						rangeWriter.write(new String(buffer), 0, n);
					}
				}
			}
			assertThat(charArrayWriter.toCharArray()).isEmpty();
		} catch (final IOException e) {
			fail(e.getMessage());
		}
	}
}