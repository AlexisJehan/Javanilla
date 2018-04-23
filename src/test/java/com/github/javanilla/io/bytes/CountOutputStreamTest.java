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

import java.io.File;
import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;

import static org.assertj.core.api.Assertions.*;

/**
 * <p>{@link CountOutputStream} unit tests.</p>
 */
final class CountOutputStreamTest {

	private static final Path INPUT = new File(Objects.requireNonNull(MethodHandles.lookup().lookupClass().getClassLoader().getResource("input.txt")).getFile()).toPath();

	@Test
	void testConstructorNull() {
		assertThatNullPointerException().isThrownBy(() -> new CountOutputStream(null));
	}

	@Test
	void testWriteOneByOne() {
		try (final var countOutputStream = new CountOutputStream(OutputStreams.BLANK)) {
			try (final var inputStream = Files.newInputStream(INPUT)) {
				assertThat(countOutputStream.getCount()).isEqualTo(0L);
				countOutputStream.write(inputStream.read());
				assertThat(countOutputStream.getCount()).isEqualTo(1L);
				for (var i = 0; i < 10; ++i) {
					countOutputStream.write(inputStream.read());
				}
				assertThat(countOutputStream.getCount()).isEqualTo(11L);
				int b;
				while (-1 != (b = inputStream.read())) {
					countOutputStream.write(b);
				}
				assertThat(countOutputStream.getCount()).isEqualTo(INPUT.toFile().length());
			}
		} catch (final IOException e) {
			fail(e.getMessage());
		}
	}

	@Test
	void testWriteBuffered() {
		try (final var countOutputStream = new CountOutputStream(OutputStreams.BLANK)) {
			try (final var inputStream = Files.newInputStream(INPUT)) {
				assertThat(countOutputStream.getCount()).isEqualTo(0L);
				final var buffer = new byte[10];
				inputStream.read(buffer, 0, 10);
				countOutputStream.write(buffer, 0, 10);
				assertThat(countOutputStream.getCount()).isEqualTo(10L);
				inputStream.read(buffer, 3, 5);
				countOutputStream.write(buffer, 3, 5);
				assertThat(countOutputStream.getCount()).isEqualTo(15L);
			}
		} catch (final IOException e) {
			fail(e.getMessage());
		}
	}
}