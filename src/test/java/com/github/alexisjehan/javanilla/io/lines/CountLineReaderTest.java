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

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;

import static org.assertj.core.api.Assertions.*;

/**
 * <p>{@link CountLineReader} unit tests.</p>
 */
final class CountLineReaderTest {

	private static final Path INPUT = new File(Objects.requireNonNull(MethodHandles.lookup().lookupClass().getClassLoader().getResource("input-lines.txt")).getFile()).toPath();

	@Test
	void testConstructorNull() {
		assertThatNullPointerException().isThrownBy(() -> new CountLineReader(null));
	}

	@Test
	void testRead() {
		try (final var countLineReader = new CountLineReader(new LineReader(INPUT))) {
			assertThat(countLineReader.getCount()).isEqualTo(0L);
			assertThat(countLineReader.read()).isNotNull();
			assertThat(countLineReader.getCount()).isEqualTo(1L);
			for (var i = 0; i < 10; ++i) {
				assertThat(countLineReader.read()).isNotNull();
			}
			assertThat(countLineReader.getCount()).isEqualTo(11L);
			while (null != countLineReader.read());
			assertThat(countLineReader.getCount()).isEqualTo(Files.lines(INPUT).count());
			assertThat(countLineReader.read()).isNull();
		} catch (final IOException e) {
			fail(e.getMessage());
		}
	}

	@Test
	void testSkip() {
		try (final var countLineReader = new CountLineReader(new LineReader(INPUT))) {
			assertThat(countLineReader.getCount()).isEqualTo(0L);
			countLineReader.skip(10L);
			assertThat(countLineReader.getCount()).isEqualTo(10L);
			countLineReader.skip(0L);
			assertThat(countLineReader.getCount()).isEqualTo(10L);
			countLineReader.skip(5L);
			assertThat(countLineReader.getCount()).isEqualTo(15L);
			countLineReader.skip(1000L);
			assertThat(countLineReader.read()).isNull();
		} catch (final IOException e) {
			fail(e.getMessage());
		}
	}
}