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
package com.github.javanilla.io.bytes;

import org.junit.jupiter.api.Test;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;

import static org.assertj.core.api.Assertions.*;

/**
 * <p>{@link CountInputStream} unit tests.</p>
 */
final class CountInputStreamTest {

	private static final Path INPUT = new File(Objects.requireNonNull(MethodHandles.lookup().lookupClass().getClassLoader().getResource("input.txt")).getFile()).toPath();

	@Test
	void testConstructorNull() {
		assertThatNullPointerException().isThrownBy(() -> new CountInputStream(null));
	}

	@Test
	void testReadOneByOne() {
		try (final var countInputStream = new CountInputStream(Files.newInputStream(INPUT))) {
			assertThat(countInputStream.getCount()).isEqualTo(0L);
			assertThat(countInputStream.read()).isNotEqualTo(-1);
			assertThat(countInputStream.getCount()).isEqualTo(1L);
			for (var i = 0; i < 10; ++i) {
				assertThat(countInputStream.read()).isNotEqualTo(-1);
			}
			assertThat(countInputStream.getCount()).isEqualTo(11L);
			while (-1 != countInputStream.read());
			assertThat(countInputStream.getCount()).isEqualTo(INPUT.toFile().length());
			assertThat(countInputStream.read()).isEqualTo(-1);
		} catch (final IOException e) {
			fail(e.getMessage());
		}
	}

	@Test
	void testReadBuffered() {
		try (final var countInputStream = new CountInputStream(Files.newInputStream(INPUT))) {
			assertThat(countInputStream.getCount()).isEqualTo(0L);
			final var buffer = new byte[10];
			assertThat(countInputStream.read(buffer, 0, 10)).isNotEqualTo(-1);
			assertThat(countInputStream.getCount()).isEqualTo(10L);
			assertThat(countInputStream.read(buffer, 3, 5)).isNotEqualTo(-1);
			assertThat(countInputStream.getCount()).isEqualTo(15L);
			while (-1 != countInputStream.read(buffer, 0, buffer.length));
			assertThat(countInputStream.getCount()).isEqualTo(INPUT.toFile().length());
			assertThat(countInputStream.read()).isEqualTo(-1);
		} catch (final IOException e) {
			fail(e.getMessage());
		}
	}

	@Test
	void testSkip() {
		try (final var countInputStream = new CountInputStream(Files.newInputStream(INPUT))) {
			assertThat(countInputStream.getCount()).isEqualTo(0L);
			countInputStream.skip(10L);
			assertThat(countInputStream.getCount()).isEqualTo(10L);
			countInputStream.skip(0L);
			assertThat(countInputStream.getCount()).isEqualTo(10L);
			countInputStream.skip(5L);
			assertThat(countInputStream.getCount()).isEqualTo(15L);
			countInputStream.skip(1000L);
			assertThat(countInputStream.read()).isEqualTo(-1);
		} catch (final IOException e) {
			fail(e.getMessage());
		}
	}

	@Test
	void testMark() {
		try (final var countInputStream = new CountInputStream(new BufferedInputStream(Files.newInputStream(INPUT)))) {
			assertThat(countInputStream.getCount()).isEqualTo(0L);
			assertThat(countInputStream.read()).isNotEqualTo(-1);
			assertThat(countInputStream.getCount()).isEqualTo(1L);
			countInputStream.mark(10);
			assertThat(countInputStream.getCount()).isEqualTo(1L);
			countInputStream.skip(10L);
			assertThat(countInputStream.getCount()).isEqualTo(11L);
			countInputStream.reset();
			assertThat(countInputStream.getCount()).isEqualTo(1L);
			assertThat(countInputStream.read()).isNotEqualTo(-1);
			assertThat(countInputStream.getCount()).isEqualTo(2L);
		} catch (final IOException e) {
			fail(e.getMessage());
		}
	}
}