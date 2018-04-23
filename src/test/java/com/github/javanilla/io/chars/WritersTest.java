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

import com.github.javanilla.lang.array.CharArrays;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.lang.invoke.MethodHandles;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Objects;

import static org.assertj.core.api.Assertions.*;

/**
 * <p>{@link Writers} unit tests.</p>
 */
final class WritersTest {

	private static final Path INPUT = new File(Objects.requireNonNull(MethodHandles.lookup().lookupClass().getClassLoader().getResource("input.txt")).getFile()).toPath();

	private static Path output;

	@BeforeAll
	static void init() {
		try {
			output = File.createTempFile("output_", ".txt").toPath();
			output.toFile().deleteOnExit();
		} catch (final IOException e) {
			fail(e.getMessage());
		}
	}

	@Test
	void testBlank() {
		try (final var blankWriter = Writers.BLANK) {
			blankWriter.write(1);
			blankWriter.flush();
		} catch (final IOException e) {
			fail(e.getMessage());
		}
	}

	@Test
	void testNullToBlank() {
		assertThat(Writers.nullToBlank(null)).isSameAs(Writers.BLANK);
		assertThat(Writers.nullToBlank(Writers.BLANK)).isSameAs(Writers.BLANK);
	}

	@Test
	void testBuffered() {
		try {
			try (final var writer = Writers.BLANK) {
				assertThat(writer).isNotSameAs(Writers.buffered(writer));
				assertThat(Writers.buffered(writer)).isInstanceOf(BufferedWriter.class);
			}
			try (final var bufferedWriter = new BufferedWriter(Writers.BLANK)) {
				assertThat(bufferedWriter).isSameAs(Writers.buffered(bufferedWriter));
				assertThat(Writers.buffered(bufferedWriter)).isInstanceOf(BufferedWriter.class);
			}
		} catch (final IOException e) {
			fail(e.getMessage());
		}
	}

	@Test
	void testBufferedNull() {
		assertThatNullPointerException().isThrownBy(() -> Writers.buffered(null));
	}

	@Test
	void testUncloseable() {
		try {
			try (final var closeableWriter = Files.newBufferedWriter(output)) {
				closeableWriter.write(1);
				closeableWriter.close();
				assertThatIOException().isThrownBy(() -> closeableWriter.write(1));
			}
			try (final var uncloseableWriter = Writers.uncloseable(Files.newBufferedWriter(output))) {
				uncloseableWriter.write(1);
				uncloseableWriter.close();
				uncloseableWriter.write(1);
			}
		} catch (final IOException e) {
			fail(e.getMessage());
		}
	}

	@Test
	void testUncloseableNull() {
		assertThatNullPointerException().isThrownBy(() -> Writers.uncloseable(null));
	}

	@Test
	void testTee() {
		try {
			try (final var firstWriter = new CharArrayWriter()) {
				try (final var secondWriter = new CharArrayWriter()) {
					try (final var teeWriter = Writers.tee(firstWriter, secondWriter)) {
						try (final var reader = Files.newBufferedReader(INPUT)) {
							reader.transferTo(teeWriter);
						}
					}
					assertThat(secondWriter.toCharArray()).isEqualTo(new String(Files.readAllBytes(INPUT)).toCharArray());
				}
				assertThat(firstWriter.toCharArray()).isEqualTo(new String(Files.readAllBytes(INPUT)).toCharArray());
			}
			try (final var firstWriter = new CharArrayWriter()) {
				try (final var secondWriter = new CharArrayWriter()) {
					try (final var teeWriter = Writers.tee(firstWriter, secondWriter)) {
						teeWriter.write(0);
						teeWriter.write(CharArrays.of((char) 1, (char) 2));
						teeWriter.write("345");
						teeWriter.write("678", 0, 3);
						teeWriter.append("abc");
						teeWriter.append("def", 0, 2);
						teeWriter.append('g');
						teeWriter.flush();
					}
					assertThat(firstWriter.toCharArray()).isEqualTo(secondWriter.toCharArray());
				}
			}
		} catch (final IOException e) {
			fail(e.getMessage());
		}
	}

	@Test
	void testTeeOne() {
		try (final var writer = new CharArrayWriter()) {
			try (final var teeWriter = Writers.tee(writer)) {
				assertThat(teeWriter).isSameAs(writer);
				try (final var reader = Files.newBufferedReader(INPUT)) {
					reader.transferTo(teeWriter);
				}
			}
			assertThat(writer.toCharArray()).isEqualTo(new String(Files.readAllBytes(INPUT)).toCharArray());
		} catch (final IOException e) {
			fail(e.getMessage());
		}
	}

	@Test
	void testTeeNone() {
		try (final var teeWriter = Writers.tee()) {
			assertThat(teeWriter).isSameAs(Writers.BLANK);
		} catch (final IOException e) {
			fail(e.getMessage());
		}
	}

	@Test
	void testTeeNull() {
		assertThatNullPointerException().isThrownBy(() -> Writers.tee((Writer[]) null));
		assertThatNullPointerException().isThrownBy(() -> Writers.tee((List<Writer>) null));
		assertThatNullPointerException().isThrownBy(() -> Writers.tee((Writer) null));
	}
}