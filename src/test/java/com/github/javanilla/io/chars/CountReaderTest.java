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
import java.util.Objects;

import static org.assertj.core.api.Assertions.*;

/**
 * <p>{@link CountReader} unit tests.</p>
 */
final class CountReaderTest {

	private static final Path INPUT = new File(Objects.requireNonNull(MethodHandles.lookup().lookupClass().getClassLoader().getResource("input.txt")).getFile()).toPath();

	@Test
	void testConstructorNull() {
		assertThatNullPointerException().isThrownBy(() -> new CountReader(null));
	}

	@Test
	void testReadOneByOne() {
		try (final var countReader = new CountReader(Files.newBufferedReader(INPUT))) {
			assertThat(countReader.getCount()).isEqualTo(0L);
			assertThat(countReader.read()).isNotEqualTo(-1);
			assertThat(countReader.getCount()).isEqualTo(1L);
			for (var i = 0; i < 10; ++i) {
				assertThat(countReader.read()).isNotEqualTo(-1);
			}
			assertThat(countReader.getCount()).isEqualTo(11L);
			while (-1 != countReader.read());
			assertThat(countReader.getCount()).isEqualTo(INPUT.toFile().length());
			assertThat(countReader.read()).isEqualTo(-1);
		} catch (final IOException e) {
			fail(e.getMessage());
		}
	}

	@Test
	void testReadBuffered() {
		try (final var countReader = new CountReader(Files.newBufferedReader(INPUT))) {
			assertThat(countReader.getCount()).isEqualTo(0L);
			final var buffer = new char[10];
			assertThat(countReader.read(buffer, 0, 10)).isNotEqualTo(-1);
			assertThat(countReader.getCount()).isEqualTo(10L);
			assertThat(countReader.read(buffer, 3, 5)).isNotEqualTo(-1);
			assertThat(countReader.getCount()).isEqualTo(15L);
			while (-1 != countReader.read(buffer, 0, buffer.length));
			assertThat(countReader.getCount()).isEqualTo(INPUT.toFile().length());
			assertThat(countReader.read()).isEqualTo(-1);
		} catch (final IOException e) {
			fail(e.getMessage());
		}
	}

	@Test
	void testSkip() {
		try (final var countReader = new CountReader(Files.newBufferedReader(INPUT))) {
			assertThat(countReader.getCount()).isEqualTo(0L);
			countReader.skip(10L);
			assertThat(countReader.getCount()).isEqualTo(10L);
			countReader.skip(0L);
			assertThat(countReader.getCount()).isEqualTo(10L);
			countReader.skip(5L);
			assertThat(countReader.getCount()).isEqualTo(15L);
			countReader.skip(1000L);
			assertThat(countReader.read()).isEqualTo(-1);
		} catch (final IOException e) {
			fail(e.getMessage());
		}
	}

	@Test
	void testMark() {
		try (final var countReader = new CountReader(Files.newBufferedReader(INPUT))) {
			assertThat(countReader.getCount()).isEqualTo(0L);
			assertThat(countReader.read()).isNotEqualTo(-1);
			assertThat(countReader.getCount()).isEqualTo(1L);
			countReader.mark(10);
			assertThat(countReader.getCount()).isEqualTo(1L);
			countReader.skip(10L);
			assertThat(countReader.getCount()).isEqualTo(11L);
			countReader.reset();
			assertThat(countReader.getCount()).isEqualTo(1L);
			assertThat(countReader.read()).isNotEqualTo(-1);
			assertThat(countReader.getCount()).isEqualTo(2L);
		} catch (final IOException e) {
			fail(e.getMessage());
		}
	}
}