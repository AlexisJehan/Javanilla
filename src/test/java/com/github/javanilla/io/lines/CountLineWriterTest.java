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

import java.io.File;
import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;

import static org.assertj.core.api.Assertions.*;

/**
 * <p>{@link CountLineWriter} unit tests.</p>
 */
final class CountLineWriterTest {

	private static final Path INPUT = new File(Objects.requireNonNull(MethodHandles.lookup().lookupClass().getClassLoader().getResource("input-lines.txt")).getFile()).toPath();

	@Test
	void testConstructorNull() {
		assertThatNullPointerException().isThrownBy(() -> new CountLineWriter(null));
	}

	@Test
	void testWrite() {
		try (final var countLineWriter = new CountLineWriter(new LineWriter(Writers.BLANK))) {
			try (final var lineReader = new LineReader(INPUT)) {
				assertThat(countLineWriter.getCount()).isEqualTo(0L);
				countLineWriter.write(lineReader.read());
				assertThat(countLineWriter.getCount()).isEqualTo(1L);
				for (var i = 0; i < 10; ++i) {
					countLineWriter.write(lineReader.read());
				}
				assertThat(countLineWriter.getCount()).isEqualTo(11L);
				String line;
				while (null != (line = lineReader.read())) {
					countLineWriter.write(line);
				}
				assertThat(countLineWriter.getCount()).isEqualTo(Files.lines(INPUT).count());
				countLineWriter.flush();
			}
		} catch (final IOException e) {
			fail(e.getMessage());
		}
	}

	@Test
	void testNewLine() {
		try (final var countLineWriter = new CountLineWriter(new LineWriter(Writers.BLANK))) {
			assertThat(countLineWriter.getCount()).isEqualTo(0L);
			countLineWriter.newLine();
			assertThat(countLineWriter.getCount()).isEqualTo(1L);
			for (var i = 0; i < 10; ++i) {
				countLineWriter.newLine();
			}
			assertThat(countLineWriter.getCount()).isEqualTo(11L);
			countLineWriter.flush();
		} catch (final IOException e) {
			fail(e.getMessage());
		}
	}
}