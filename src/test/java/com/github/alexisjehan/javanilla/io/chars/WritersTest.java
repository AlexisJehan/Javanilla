/*
 * MIT License
 *
 * Copyright (c) 2018-2019 Alexis Jehan
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package com.github.alexisjehan.javanilla.io.chars;

import com.github.alexisjehan.javanilla.lang.Strings;
import com.github.alexisjehan.javanilla.lang.array.CharArrays;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junitpioneer.jupiter.TempDirectory;

import java.io.BufferedWriter;
import java.io.CharArrayWriter;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIOException;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.assertj.core.api.Assertions.assertThatNullPointerException;

/**
 * <p>{@link Writers} unit tests.</p>
 */
final class WritersTest {

	private static final char[] CHARS = CharArrays.of('a', 'b', 'c');
	private static final String STRING = "abc";

	@Test
	@SuppressWarnings("deprecation")
	void testEmpty() throws IOException {
		try (final var emptyWriter = Writers.EMPTY) {
			emptyWriter.write('a');
			emptyWriter.write(CharArrays.EMPTY);
			emptyWriter.write(CHARS);
			assertThatNullPointerException().isThrownBy(() -> emptyWriter.write((char[]) null));
			emptyWriter.write(CHARS, 0, 0);
			emptyWriter.write(CHARS, 0, 1);
			assertThatNullPointerException().isThrownBy(() -> emptyWriter.write((char[]) null, 0, 2));
			assertThatIllegalArgumentException().isThrownBy(() -> emptyWriter.write(CHARS, -1, 3));
			assertThatIllegalArgumentException().isThrownBy(() -> emptyWriter.write(CHARS, 4, 3));
			assertThatIllegalArgumentException().isThrownBy(() -> emptyWriter.write(CHARS, 0, -1));
			assertThatIllegalArgumentException().isThrownBy(() -> emptyWriter.write(CHARS, 0, 4));
			emptyWriter.write(Strings.EMPTY);
			emptyWriter.write(STRING);
			assertThatNullPointerException().isThrownBy(() -> emptyWriter.write((String) null));
			emptyWriter.write(STRING, 0, 0);
			emptyWriter.write(STRING, 0, 1);
			assertThatNullPointerException().isThrownBy(() -> emptyWriter.write((String) null, 0, 2));
			assertThatIllegalArgumentException().isThrownBy(() -> emptyWriter.write(STRING, -1, 3));
			assertThatIllegalArgumentException().isThrownBy(() -> emptyWriter.write(STRING, 4, 3));
			assertThatIllegalArgumentException().isThrownBy(() -> emptyWriter.write(STRING, 0, -1));
			assertThatIllegalArgumentException().isThrownBy(() -> emptyWriter.write(STRING, 0, 4));
			assertThat(emptyWriter.append(Strings.EMPTY)).isSameAs(emptyWriter);
			assertThat(emptyWriter.append(STRING)).isSameAs(emptyWriter);
			assertThatNullPointerException().isThrownBy(() -> emptyWriter.append(null));
			assertThat(emptyWriter.append(STRING, 0, 0)).isSameAs(emptyWriter);
			assertThat(emptyWriter.append(STRING, 0, 1)).isSameAs(emptyWriter);
			assertThatNullPointerException().isThrownBy(() -> emptyWriter.append(null, 0, 2));
			assertThatIllegalArgumentException().isThrownBy(() -> emptyWriter.append(STRING, -1, 3));
			assertThatIllegalArgumentException().isThrownBy(() -> emptyWriter.append(STRING, 4, 3));
			assertThatIllegalArgumentException().isThrownBy(() -> emptyWriter.append(STRING, 0, -1));
			assertThatIllegalArgumentException().isThrownBy(() -> emptyWriter.append(STRING, 0, 4));
			assertThat(emptyWriter.append('a')).isSameAs(emptyWriter);
			emptyWriter.flush();
		}
	}

	@Test
	@SuppressWarnings("deprecation")
	void testNullToEmpty() {
		assertThat(Writers.nullToEmpty(null)).isSameAs(Writers.EMPTY);
		assertThat(Writers.nullToEmpty(Writers.EMPTY)).isSameAs(Writers.EMPTY);
	}

	@Test
	@SuppressWarnings("deprecation")
	void testNullToDefault() {
		assertThat(Writers.nullToDefault(null, Writers.EMPTY)).isSameAs(Writers.EMPTY);
		assertThat(Writers.nullToDefault(Writers.EMPTY, Writers.EMPTY)).isSameAs(Writers.EMPTY);
	}

	@Test
	@SuppressWarnings("deprecation")
	void testNullToDefaultInvalid() {
		assertThatNullPointerException().isThrownBy(() -> Writers.nullToDefault(Writers.EMPTY, null));
	}

	@Test
	@SuppressWarnings("deprecation")
	void testBuffered() throws IOException {
		try (final var writer = Writers.EMPTY) {
			assertThat(writer).isNotInstanceOf(BufferedWriter.class);
			try (final var bufferedWriter = Writers.buffered(writer)) {
				assertThat(writer).isNotSameAs(bufferedWriter);
				assertThat(bufferedWriter).isInstanceOf(BufferedWriter.class);
			}
		}
		try (final var writer = new BufferedWriter(Writers.EMPTY)) {
			assertThat(writer).isInstanceOf(BufferedWriter.class);
			try (final var bufferedWriter = Writers.buffered(writer)) {
				assertThat(writer).isSameAs(bufferedWriter);
				assertThat(bufferedWriter).isInstanceOf(BufferedWriter.class);
			}
		}
	}

	@Test
	void testBufferedInvalid() {
		assertThatNullPointerException().isThrownBy(() -> Writers.buffered(null));
	}

	@Test
	void testUncloseable() throws IOException {
		final var writer = new Writer() {
			@Override
			public void write(final char[] chars, final int offset, final int length) {
				// Do nothing
			}

			@Override
			public void flush() {
				// Do nothing
			}

			@Override
			public void close() throws IOException {
				throw new IOException();
			}
		};
		assertThatIOException().isThrownBy(writer::close);
		Writers.uncloseable(writer).close();
	}

	@Test
	void testUncloseableInvalid() {
		assertThatNullPointerException().isThrownBy(() -> Writers.uncloseable(null));
	}

	@Test
	@SuppressWarnings("deprecation")
	void testTee() throws IOException {
		assertThat(Writers.tee()).isSameAs(Writers.EMPTY);
		assertThat(Writers.tee(Writers.EMPTY)).isSameAs(Writers.EMPTY);
		try (final var fooWriter = new CharArrayWriter()) {
			try (final var barWriter = new CharArrayWriter()) {
				try (final var teeWriter = Writers.tee(fooWriter, barWriter)) {
					teeWriter.write('a');
					teeWriter.write(CharArrays.EMPTY);
					teeWriter.write(CHARS);
					assertThatNullPointerException().isThrownBy(() -> teeWriter.write((char[]) null));
					teeWriter.write(CHARS, 0, 0);
					teeWriter.write(CHARS, 0, 1);
					assertThatNullPointerException().isThrownBy(() -> teeWriter.write((char[]) null, 0, 2));
					assertThatIllegalArgumentException().isThrownBy(() -> teeWriter.write(CHARS, -1, 3));
					assertThatIllegalArgumentException().isThrownBy(() -> teeWriter.write(CHARS, 4, 3));
					assertThatIllegalArgumentException().isThrownBy(() -> teeWriter.write(CHARS, 0, -1));
					assertThatIllegalArgumentException().isThrownBy(() -> teeWriter.write(CHARS, 0, 4));
					teeWriter.write(Strings.EMPTY);
					teeWriter.write(STRING);
					assertThatNullPointerException().isThrownBy(() -> teeWriter.write((String) null));
					teeWriter.write(STRING, 0, 0);
					teeWriter.write(STRING, 0, 1);
					assertThatNullPointerException().isThrownBy(() -> teeWriter.write((String) null, 0, 2));
					assertThatIllegalArgumentException().isThrownBy(() -> teeWriter.write(STRING, -1, 3));
					assertThatIllegalArgumentException().isThrownBy(() -> teeWriter.write(STRING, 4, 3));
					assertThatIllegalArgumentException().isThrownBy(() -> teeWriter.write(STRING, 0, -1));
					assertThatIllegalArgumentException().isThrownBy(() -> teeWriter.write(STRING, 0, 4));
					assertThat(teeWriter.append(Strings.EMPTY)).isSameAs(teeWriter);
					assertThat(teeWriter.append(STRING)).isSameAs(teeWriter);
					assertThatNullPointerException().isThrownBy(() -> teeWriter.append(null));
					assertThat(teeWriter.append(STRING, 0, 0)).isSameAs(teeWriter);
					assertThat(teeWriter.append(STRING, 0, 1)).isSameAs(teeWriter);
					assertThatNullPointerException().isThrownBy(() -> teeWriter.append(null, 0, 2));
					assertThatIllegalArgumentException().isThrownBy(() -> teeWriter.append(STRING, -1, 3));
					assertThatIllegalArgumentException().isThrownBy(() -> teeWriter.append(STRING, 4, 3));
					assertThatIllegalArgumentException().isThrownBy(() -> teeWriter.append(STRING, 0, -1));
					assertThatIllegalArgumentException().isThrownBy(() -> teeWriter.append(STRING, 0, 4));
					assertThat(teeWriter.append('a')).isSameAs(teeWriter);
					teeWriter.flush();
				}
				assertThat(barWriter.toCharArray()).containsExactly('a', 'a', 'b', 'c', 'a', 'a', 'b', 'c', 'a', 'a', 'b', 'c', 'a', 'a');
			}
			assertThat(fooWriter.toCharArray()).containsExactly('a', 'a', 'b', 'c', 'a', 'a', 'b', 'c', 'a', 'a', 'b', 'c', 'a', 'a');
		}
	}

	@Test
	void testTeeInvalid() {
		assertThatNullPointerException().isThrownBy(() -> Writers.tee((Writer[]) null));
		assertThatNullPointerException().isThrownBy(() -> Writers.tee((List<Writer>) null));
		assertThatNullPointerException().isThrownBy(() -> Writers.tee((Writer) null));
	}

	@Test
	@ExtendWith(TempDirectory.class)
	void testOf(@TempDirectory.TempDir final Path tmpDirectory) throws IOException {
		final var path = tmpDirectory.resolve("testOf");
		try (final var pathWriter = Writers.of(path)) {
			pathWriter.write(CHARS);
		}
		assertThat(path).hasContent(STRING);
	}

	@Test
	void testOfInvalid() {
		assertThatNullPointerException().isThrownBy(() -> Writers.of(null));
		assertThatNullPointerException().isThrownBy(() -> Writers.of(Paths.get(getClass().getName() + ".testOfInvalid.txt"), null));
	}
}