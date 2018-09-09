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

import com.github.alexisjehan.javanilla.lang.array.CharArrays;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

/**
 * <p>{@link Writers} unit tests.</p>
 */
final class WritersTest {

	@Test
	void testEmpty() throws IOException {
		final var chars = CharArrays.of('a', 'b', 'c');
		final var string = "abc";
		try (final var emptyWriter = Writers.EMPTY) {
			emptyWriter.write('a');
			emptyWriter.write(chars);
			assertThatNullPointerException().isThrownBy(() -> emptyWriter.write((char[]) null));
			emptyWriter.write(chars, 0, 1);
			assertThatNullPointerException().isThrownBy(() -> emptyWriter.write((char[]) null, 0, 2));
			assertThatExceptionOfType(IndexOutOfBoundsException.class).isThrownBy(() -> emptyWriter.write(chars, -1, 3));
			assertThatExceptionOfType(IndexOutOfBoundsException.class).isThrownBy(() -> emptyWriter.write(chars, 4, 3));
			assertThatExceptionOfType(IndexOutOfBoundsException.class).isThrownBy(() -> emptyWriter.write(chars, 0, -1));
			assertThatExceptionOfType(IndexOutOfBoundsException.class).isThrownBy(() -> emptyWriter.write(chars, 0, 4));
			emptyWriter.write(string);
			assertThatNullPointerException().isThrownBy(() -> emptyWriter.write((String) null));
			emptyWriter.write(string, 0, 1);
			assertThatNullPointerException().isThrownBy(() -> emptyWriter.write((String) null, 0, 2));
			assertThatExceptionOfType(IndexOutOfBoundsException.class).isThrownBy(() -> emptyWriter.write(string, -1, 3));
			assertThatExceptionOfType(IndexOutOfBoundsException.class).isThrownBy(() -> emptyWriter.write(string, 4, 3));
			assertThatExceptionOfType(IndexOutOfBoundsException.class).isThrownBy(() -> emptyWriter.write(string, 0, -1));
			assertThatExceptionOfType(IndexOutOfBoundsException.class).isThrownBy(() -> emptyWriter.write(string, 0, 4));
			assertThat(emptyWriter.append(string)).isSameAs(emptyWriter);
			assertThatNullPointerException().isThrownBy(() -> emptyWriter.append(null));
			assertThat(emptyWriter.append(string, 0, 1)).isSameAs(emptyWriter);
			assertThatNullPointerException().isThrownBy(() -> emptyWriter.append(null, 0, 2));
			assertThatExceptionOfType(IndexOutOfBoundsException.class).isThrownBy(() -> emptyWriter.append(string, -1, 3));
			assertThatExceptionOfType(IndexOutOfBoundsException.class).isThrownBy(() -> emptyWriter.append(string, 4, 3));
			assertThatExceptionOfType(IndexOutOfBoundsException.class).isThrownBy(() -> emptyWriter.append(string, 0, -1));
			assertThatExceptionOfType(IndexOutOfBoundsException.class).isThrownBy(() -> emptyWriter.append(string, 0, 4));
			assertThat(emptyWriter.append('a')).isSameAs(emptyWriter);
			emptyWriter.flush();
		}
	}

	@Test
	void testNullToEmpty() {
		assertThat(Writers.nullToEmpty(null)).isSameAs(Writers.EMPTY);
		assertThat(Writers.nullToEmpty(Writers.EMPTY)).isSameAs(Writers.EMPTY);
	}

	@Test
	void testNullToDefault() {
		assertThat(Writers.nullToDefault(null, Writers.EMPTY)).isSameAs(Writers.EMPTY);
		assertThat(Writers.nullToDefault(Writers.EMPTY, Writers.EMPTY)).isSameAs(Writers.EMPTY);
	}

	@Test
	void testNullToDefaultInvalid() {
		assertThatNullPointerException().isThrownBy(() -> Writers.nullToDefault(Writers.EMPTY, null));
	}

	@Test
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
		{
			Writers.uncloseable(writer).close();
		}
	}

	@Test
	void testUncloseableInvalid() {
		assertThatNullPointerException().isThrownBy(() -> Writers.uncloseable(null));
	}

	@Test
	void testTee() throws IOException {
		assertThat(Writers.tee()).isSameAs(Writers.EMPTY);
		assertThat(Writers.tee(Writers.EMPTY)).isSameAs(Writers.EMPTY);
		final var chars = CharArrays.of('a', 'b', 'c');
		final var string = "abc";
		try (final var fooWriter = new CharArrayWriter()) {
			try (final var barWriter = new CharArrayWriter()) {
				try (final var teeWriter = Writers.tee(fooWriter, barWriter)) {
					teeWriter.write('a');
					teeWriter.write(chars);
					assertThatNullPointerException().isThrownBy(() -> teeWriter.write((char[]) null));
					teeWriter.write(chars, 0, 1);
					assertThatNullPointerException().isThrownBy(() -> teeWriter.write((char[]) null, 0, 2));
					assertThatExceptionOfType(IndexOutOfBoundsException.class).isThrownBy(() -> teeWriter.write(chars, -1, 3));
					assertThatExceptionOfType(IndexOutOfBoundsException.class).isThrownBy(() -> teeWriter.write(chars, 4, 3));
					assertThatExceptionOfType(IndexOutOfBoundsException.class).isThrownBy(() -> teeWriter.write(chars, 0, -1));
					assertThatExceptionOfType(IndexOutOfBoundsException.class).isThrownBy(() -> teeWriter.write(chars, 0, 4));
					teeWriter.write("abc");
					assertThatNullPointerException().isThrownBy(() -> teeWriter.write((String) null));
					teeWriter.write("abc", 0, 1);
					assertThatNullPointerException().isThrownBy(() -> teeWriter.write((String) null, 0, 2));
					assertThatExceptionOfType(IndexOutOfBoundsException.class).isThrownBy(() -> teeWriter.write(string, -1, 3));
					assertThatExceptionOfType(IndexOutOfBoundsException.class).isThrownBy(() -> teeWriter.write(string, 4, 3));
					assertThatExceptionOfType(IndexOutOfBoundsException.class).isThrownBy(() -> teeWriter.write(string, 0, -1));
					assertThatExceptionOfType(IndexOutOfBoundsException.class).isThrownBy(() -> teeWriter.write(string, 0, 4));
					assertThat(teeWriter.append("abc")).isSameAs(teeWriter);
					assertThatNullPointerException().isThrownBy(() -> teeWriter.append(null));
					assertThat(teeWriter.append("abc", 0, 1)).isSameAs(teeWriter);
					assertThatNullPointerException().isThrownBy(() -> teeWriter.append(null, 0, 2));
					assertThatExceptionOfType(IndexOutOfBoundsException.class).isThrownBy(() -> teeWriter.append(string, -1, 3));
					assertThatExceptionOfType(IndexOutOfBoundsException.class).isThrownBy(() -> teeWriter.append(string, 4, 3));
					assertThatExceptionOfType(IndexOutOfBoundsException.class).isThrownBy(() -> teeWriter.append(string, 0, -1));
					assertThatExceptionOfType(IndexOutOfBoundsException.class).isThrownBy(() -> teeWriter.append(string, 0, 4));
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
	void testOf() throws IOException {
		final var path = File.createTempFile(getClass().getName() + ".testOf_", ".txt").toPath();
		try (final var pathWriter = Writers.of(path)) {
			pathWriter.write(CharArrays.of('a', 'b', 'c'));
		}
		assertThat(path).hasContent("abc");
		Files.delete(path);
	}

	@Test
	void testOfInvalid() {
		assertThatNullPointerException().isThrownBy(() -> Writers.of(null));
		assertThatNullPointerException().isThrownBy(() -> Writers.of(Paths.get(getClass().getName() + ".testOfInvalid.txt"), null));
	}
}