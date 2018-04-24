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
package com.github.alexisjehan.javanilla.io.lines;

import com.github.alexisjehan.javanilla.io.chars.Readers;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;

import static org.assertj.core.api.Assertions.*;

/**
 * <p>{@link RangeLineReader} unit tests.</p>
 */
final class RangeLineReaderTest {

	private static final Path INPUT = new File(Objects.requireNonNull(MethodHandles.lookup().lookupClass().getClassLoader().getResource("input-lines.txt")).getFile()).toPath();

	@Test
	void testConstructorNull() {
		assertThatNullPointerException().isThrownBy(() -> new RangeLineReader(null, 6L, 20L));
	}

	@Test
	void testConstructorInvalid() {
		assertThatExceptionOfType(IndexOutOfBoundsException.class).isThrownBy(() -> new RangeLineReader(new LineReader(new BufferedReader(Readers.EMPTY)), -5L, 20L));
		assertThatExceptionOfType(IndexOutOfBoundsException.class).isThrownBy(() -> new RangeLineReader(new LineReader(new BufferedReader(Readers.EMPTY)), 10L, 5L));
	}

	@Test
	void testReadRange() {
		try (final var rangeLineReader = new RangeLineReader(new LineReader(INPUT), 6L, 10L)) {
			assertThat(rangeLineReader.getFromIndex()).isEqualTo(6L);
			assertThat(rangeLineReader.getToIndex()).isEqualTo(10L);
			final var lines = Files.readAllLines(INPUT);
			for (var i = 6; i <= 10; ++i) {
				assertThat(rangeLineReader.read()).isEqualTo(lines.get(i));
			}
			assertThat(rangeLineReader.read()).isNull();
		} catch (final IOException e) {
			fail(e.getMessage());
		}
	}

	@Test
	void testReadAll() {
		try (final var rangeLineReader = new RangeLineReader(new LineReader(INPUT), 2000L)) {
			assertThat(rangeLineReader.getFromIndex()).isEqualTo(0L);
			assertThat(rangeLineReader.getToIndex()).isEqualTo(2000L);
			var count = 0L;
			while (null != rangeLineReader.read()) {
				++count;
			}
			assertThat(count).isEqualTo(Files.lines(INPUT).count());
		} catch (final IOException e) {
			fail(e.getMessage());
		}
	}

	@Test
	void testReadOut() {
		try (final var rangeLineReader = new RangeLineReader(new LineReader(INPUT), 1000L, 2000L)) {
			assertThat(rangeLineReader.getFromIndex()).isEqualTo(1000L);
			assertThat(rangeLineReader.getToIndex()).isEqualTo(2000L);
			assertThat(rangeLineReader.read()).isNull();
		} catch (final IOException e) {
			fail(e.getMessage());
		}
	}

	@Test
	void testSkip() {
		try (final var rangeLineReader = new RangeLineReader(new LineReader(INPUT), 6L, 20L)) {
			assertThat(rangeLineReader.getFromIndex()).isEqualTo(6L);
			assertThat(rangeLineReader.getToIndex()).isEqualTo(20L);
			final var lines = Files.readAllLines(INPUT);
			assertThat(rangeLineReader.read()).isEqualTo(lines.get(6));
			rangeLineReader.skip(5L);
			assertThat(rangeLineReader.read()).isEqualTo(lines.get(12));
			rangeLineReader.skip(0L);
			assertThat(rangeLineReader.read()).isEqualTo(lines.get(13));
			rangeLineReader.skip(5L);
			assertThat(rangeLineReader.read()).isEqualTo(lines.get(19));
			rangeLineReader.skip(1L);
			assertThat(rangeLineReader.read()).isNull();
		} catch (final IOException e) {
			fail(e.getMessage());
		}
	}
}