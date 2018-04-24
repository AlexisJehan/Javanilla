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
package com.github.alexisjehan.javanilla.io.chars;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;

import static org.assertj.core.api.Assertions.*;

/**
 * <p>{@link CountWriter} unit tests.</p>
 */
final class CountWriterTest {

	private static final Path INPUT = new File(Objects.requireNonNull(MethodHandles.lookup().lookupClass().getClassLoader().getResource("input.txt")).getFile()).toPath();

	@Test
	void testConstructorNull() {
		assertThatNullPointerException().isThrownBy(() -> new CountWriter(null));
	}

	@Test
	void testWriteOneByOne() {
		try (final var countWriter = new CountWriter(Writers.BLANK)) {
			try (final var reader = Files.newBufferedReader(INPUT)) {
				assertThat(countWriter.getCount()).isEqualTo(0L);
				countWriter.write(reader.read());
				assertThat(countWriter.getCount()).isEqualTo(1L);
				for (var i = 0; i < 10; ++i) {
					countWriter.write(reader.read());
				}
				assertThat(countWriter.getCount()).isEqualTo(11L);
				int b;
				while (-1 != (b = reader.read())) {
					countWriter.write(b);
				}
				assertThat(countWriter.getCount()).isEqualTo(INPUT.toFile().length());
			}
		} catch (final IOException e) {
			fail(e.getMessage());
		}
	}

	@Test
	void testWriteBuffered() {
		try (final var countWriter = new CountWriter(Writers.BLANK)) {
			try (final var reader = Files.newBufferedReader(INPUT)) {
				assertThat(countWriter.getCount()).isEqualTo(0L);
				final var buffer = new char[10];
				reader.read(buffer, 0, 10);
				countWriter.write(buffer, 0, 10);
				assertThat(countWriter.getCount()).isEqualTo(10L);
				reader.read(buffer, 3, 5);
				countWriter.write(buffer, 3, 5);
				assertThat(countWriter.getCount()).isEqualTo(15L);
			}
		} catch (final IOException e) {
			fail(e.getMessage());
		}
	}

	@Test
	void testWriteBufferedString() {
		try (final var countWriter = new CountWriter(Writers.BLANK)) {
			try (final var reader = Files.newBufferedReader(INPUT)) {
				assertThat(countWriter.getCount()).isEqualTo(0L);
				final var buffer = new char[10];
				reader.read(buffer, 0, 10);
				countWriter.write(new String(buffer), 0, 10);
				assertThat(countWriter.getCount()).isEqualTo(10L);
				reader.read(buffer, 3, 5);
				countWriter.write(new String(buffer), 3, 5);
				assertThat(countWriter.getCount()).isEqualTo(15L);
			}
		} catch (final IOException e) {
			fail(e.getMessage());
		}
	}
}