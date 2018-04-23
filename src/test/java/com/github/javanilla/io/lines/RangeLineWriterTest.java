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
package com.github.javanilla.io.lines;

import com.github.javanilla.io.chars.Writers;
import org.junit.jupiter.api.Test;

import java.io.CharArrayWriter;
import java.io.File;
import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.*;

/**
 * <p>{@link RangeLineWriter} unit tests.</p>
 */
final class RangeLineWriterTest {

	private static final Path INPUT = new File(Objects.requireNonNull(MethodHandles.lookup().lookupClass().getClassLoader().getResource("input-lines.txt")).getFile()).toPath();

	@Test
	void testConstructorNull() {
		assertThatNullPointerException().isThrownBy(() -> new RangeLineWriter(null, 6L, 20L));
	}

	@Test
	void testConstructorInvalid() {
		assertThatExceptionOfType(IndexOutOfBoundsException.class).isThrownBy(() -> new RangeLineWriter(new LineWriter(Writers.BLANK), -5L, 20L));
		assertThatExceptionOfType(IndexOutOfBoundsException.class).isThrownBy(() -> new RangeLineWriter(new LineWriter(Writers.BLANK), 10L, 5L));
	}

	@Test
	void testWriteRange() {
		try (final var charArrayWriter = new CharArrayWriter()) {
			try (final var rangeLineWriter = new RangeLineWriter(new LineWriter(charArrayWriter), 6L, 10L)) {
				assertThat(rangeLineWriter.getFromIndex()).isEqualTo(6L);
				assertThat(rangeLineWriter.getToIndex()).isEqualTo(10L);
				try (final var reader = Files.newBufferedReader(INPUT)) {
					String line;
					while (null != (line = reader.readLine())) {
						rangeLineWriter.write(line);
					}
				}
			}
			assertThat(charArrayWriter.toString()).isEqualTo(Files.lines(INPUT).skip(6).limit(11 - 6).collect(Collectors.joining(System.lineSeparator())));
		} catch (final IOException e) {
			fail(e.getMessage());
		}
	}

	@Test
	void testWriteAll() {
		try (final var charArrayWriter = new CharArrayWriter()) {
			try (final var rangeLineWriter = new RangeLineWriter(new LineWriter(charArrayWriter), 2000L)) {
				assertThat(rangeLineWriter.getFromIndex()).isEqualTo(0L);
				assertThat(rangeLineWriter.getToIndex()).isEqualTo(2000L);
				try (final var reader = Files.newBufferedReader(INPUT)) {
					String line;
					while (null != (line = reader.readLine())) {
						rangeLineWriter.write(line);
					}
				}
			}
			assertThat(charArrayWriter.toString()).isEqualTo(Files.lines(INPUT).collect(Collectors.joining(System.lineSeparator())));
		} catch (final IOException e) {
			fail(e.getMessage());
		}
	}

	@Test
	void testWriteOut() {
		try (final var charArrayWriter = new CharArrayWriter()) {
			try (final var rangeLineWriter = new RangeLineWriter(new LineWriter(charArrayWriter), 1000L, 2000L)) {
				assertThat(rangeLineWriter.getFromIndex()).isEqualTo(1000L);
				assertThat(rangeLineWriter.getToIndex()).isEqualTo(2000L);
				try (final var reader = Files.newBufferedReader(INPUT)) {
					String line;
					while (null != (line = reader.readLine())) {
						rangeLineWriter.write(line);
					}
				}
			}
			assertThat(charArrayWriter.toString()).isEmpty();
		} catch (final IOException e) {
			fail(e.getMessage());
		}
	}

	@Test
	void testNewLineRange() {
		try (final var charArrayWriter = new CharArrayWriter()) {
			try (final var rangeLineWriter = new RangeLineWriter(new LineWriter(charArrayWriter), 6L, 10L)) {
				assertThat(rangeLineWriter.getFromIndex()).isEqualTo(6L);
				assertThat(rangeLineWriter.getToIndex()).isEqualTo(10L);
				try (final var reader = Files.newBufferedReader(INPUT)) {
					while (null != reader.readLine()) {
						rangeLineWriter.newLine();
					}
				}
			}
			assertThat(charArrayWriter.toString()).isEqualTo(Files.lines(INPUT).skip(6).limit(11 - 6).map(s -> "").collect(Collectors.joining(System.lineSeparator())) + System.lineSeparator());
		} catch (final IOException e) {
			fail(e.getMessage());
		}
	}

	@Test
	void testNewLineAll() {
		try (final var charArrayWriter = new CharArrayWriter()) {
			try (final var rangeLineWriter = new RangeLineWriter(new LineWriter(charArrayWriter), 2000L)) {
				assertThat(rangeLineWriter.getFromIndex()).isEqualTo(0L);
				assertThat(rangeLineWriter.getToIndex()).isEqualTo(2000L);
				try (final var reader = Files.newBufferedReader(INPUT)) {
					while (null != reader.readLine()) {
						rangeLineWriter.newLine();
					}
				}
			}
			assertThat(charArrayWriter.toString()).isEqualTo(Files.lines(INPUT).map(s -> "").collect(Collectors.joining(System.lineSeparator())) + System.lineSeparator());
		} catch (final IOException e) {
			fail(e.getMessage());
		}
	}

	@Test
	void testNewLineOut() {
		try (final var charArrayWriter = new CharArrayWriter()) {
			try (final var rangeLineWriter = new RangeLineWriter(new LineWriter(charArrayWriter), 1000L, 2000L)) {
				assertThat(rangeLineWriter.getFromIndex()).isEqualTo(1000L);
				assertThat(rangeLineWriter.getToIndex()).isEqualTo(2000L);
				try (final var reader = Files.newBufferedReader(INPUT)) {
					while (null != reader.readLine()) {
						rangeLineWriter.newLine();
					}
				}
			}
			assertThat(charArrayWriter.toString()).isEmpty();
		} catch (final IOException e) {
			fail(e.getMessage());
		}
	}
}