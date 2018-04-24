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
package com.github.alexisjehan.javanilla.io.bytes;

import com.github.alexisjehan.javanilla.lang.array.ByteArrays;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.lang.invoke.MethodHandles;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Objects;

import static org.assertj.core.api.Assertions.*;

/**
 * <p>{@link OutputStreams} unit tests.</p>
 */
final class OutputStreamsTest {

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
		try (final var blankOutputStream = OutputStreams.BLANK) {
			blankOutputStream.write(1);
		} catch (final IOException e) {
			fail(e.getMessage());
		}
	}

	@Test
	void testNullToBlank() {
		assertThat(OutputStreams.nullToBlank(null)).isSameAs(OutputStreams.BLANK);
		assertThat(OutputStreams.nullToBlank(OutputStreams.BLANK)).isSameAs(OutputStreams.BLANK);
	}

	@Test
	void testBuffered() {
		try {
			try (final var outputStream = OutputStreams.BLANK) {
				assertThat(outputStream).isNotSameAs(OutputStreams.buffered(outputStream));
				assertThat(OutputStreams.buffered(outputStream)).isInstanceOf(BufferedOutputStream.class);
			}
			try (final var bufferedOutputStream = new BufferedOutputStream(OutputStreams.BLANK)) {
				assertThat(bufferedOutputStream).isSameAs(OutputStreams.buffered(bufferedOutputStream));
				assertThat(OutputStreams.buffered(bufferedOutputStream)).isInstanceOf(BufferedOutputStream.class);
			}
		} catch (final IOException e) {
			fail(e.getMessage());
		}
	}

	@Test
	void testBufferedNull() {
		assertThatNullPointerException().isThrownBy(() -> OutputStreams.buffered(null));
	}

	@Test
	void testUncloseable() {
		try {
			try (final var closeableOutputStream = Files.newOutputStream(output)) {
				closeableOutputStream.write(1);
				closeableOutputStream.close();
				assertThatIOException().isThrownBy(() -> closeableOutputStream.write(1));
			}
			try (final var uncloseableOutputStream = OutputStreams.uncloseable(Files.newOutputStream(output))) {
				uncloseableOutputStream.write(1);
				uncloseableOutputStream.close();
				uncloseableOutputStream.write(1);
			}
		} catch (final IOException e) {
			fail(e.getMessage());
		}
	}

	@Test
	void testUncloseableNull() {
		assertThatNullPointerException().isThrownBy(() -> OutputStreams.uncloseable(null));
	}

	@Test
	void testTee() {
		try {
			try (final var firstOutputStream = new ByteArrayOutputStream()) {
				try (final var secondOutputStream = new ByteArrayOutputStream()) {
					try (final var teeOutputStream = OutputStreams.tee(firstOutputStream, secondOutputStream)) {
						try (final var inputStream = Files.newInputStream(INPUT)) {
							inputStream.transferTo(teeOutputStream);
						}
					}
					assertThat(secondOutputStream.toByteArray()).isEqualTo(Files.readAllBytes(INPUT));
				}
				assertThat(firstOutputStream.toByteArray()).isEqualTo(Files.readAllBytes(INPUT));
			}
			try (final var firstOutputStream = new ByteArrayOutputStream()) {
				try (final var secondOutputStream = new ByteArrayOutputStream()) {
					try (final var teeOutputStream = OutputStreams.tee(firstOutputStream, secondOutputStream)) {
						teeOutputStream.write(0);
						teeOutputStream.write(ByteArrays.of((byte) 1, (byte) 2));
						teeOutputStream.flush();
					}
					assertThat(firstOutputStream.toByteArray()).isEqualTo(secondOutputStream.toByteArray());
				}
			}
		} catch (final IOException e) {
			fail(e.getMessage());
		}
	}

	@Test
	void testTeeOne() {
		try (final var outputStream = new ByteArrayOutputStream()) {
			try (final var teeOutputStream = OutputStreams.tee(outputStream)) {
				assertThat(teeOutputStream).isSameAs(outputStream);
				try (final var inputStream = Files.newInputStream(INPUT)) {
					inputStream.transferTo(teeOutputStream);
				}
			}
			assertThat(outputStream.toByteArray()).isEqualTo(Files.readAllBytes(INPUT));
		} catch (final IOException e) {
			fail(e.getMessage());
		}
	}

	@Test
	void testTeeNone() {
		try (final var teeOutputStream = OutputStreams.tee()) {
			assertThat(teeOutputStream).isSameAs(OutputStreams.BLANK);
		} catch (final IOException e) {
			fail(e.getMessage());
		}
	}

	@Test
	void testTeeNull() {
		assertThatNullPointerException().isThrownBy(() -> OutputStreams.tee((OutputStream[]) null));
		assertThatNullPointerException().isThrownBy(() -> OutputStreams.tee((List<OutputStream>) null));
		assertThatNullPointerException().isThrownBy(() -> OutputStreams.tee((OutputStream) null));
	}

	@Test
	void testToWriter() {
		try {
			try (final var outputStream = new ByteArrayOutputStream()) {
				try (final var writer = OutputStreams.toWriter(outputStream)) {
					writer.write("");
				}
				assertThat(new String(outputStream.toByteArray())).isEmpty();
			}

			try (final var outputStream = new ByteArrayOutputStream()) {
				final var input = new String(Files.readAllBytes(INPUT), StandardCharsets.ISO_8859_1);
				try (final var writer = OutputStreams.toWriter(outputStream, StandardCharsets.ISO_8859_1)) {
					writer.write(input);
				}
				assertThat(new String(outputStream.toByteArray(), StandardCharsets.ISO_8859_1)).isEqualTo(input);
			}

			// Not the same charset
			try (final var outputStream = new ByteArrayOutputStream()) {
				final var input = new String(Files.readAllBytes(INPUT), StandardCharsets.UTF_8);
				try (final var writer = OutputStreams.toWriter(outputStream, StandardCharsets.UTF_16)) {
					writer.write(input);
				}
				assertThat(new String(outputStream.toByteArray(), StandardCharsets.ISO_8859_1)).isNotEqualTo(input);
			}
		} catch (final IOException e) {
			fail(e.getMessage());
		}
	}

	@Test
	void testToWriterNull() {
		assertThatNullPointerException().isThrownBy(() -> OutputStreams.toWriter(null));
		assertThatNullPointerException().isThrownBy(() -> OutputStreams.toWriter(OutputStreams.BLANK, null));
	}
}