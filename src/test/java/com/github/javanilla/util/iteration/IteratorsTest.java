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
package com.github.javanilla.util.iteration;

import com.github.javanilla.io.bytes.InputStreams;
import com.github.javanilla.io.chars.Readers;
import com.github.javanilla.io.lines.LineReader;
import com.github.javanilla.io.lines.LineSeparator;
import com.github.javanilla.lang.array.ObjectArrays;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.*;
import java.util.function.Function;
import java.util.function.Supplier;

import static org.assertj.core.api.Assertions.*;

/**
 * <p>{@link Iterators} unit tests.</p>
 */
final class IteratorsTest {

	@Test
	void testEmptyInt() {
		assertThat(Iterators.EMPTY_INT.hasNext()).isFalse();
		assertThatExceptionOfType(NoSuchElementException.class).isThrownBy(Iterators.EMPTY_INT::nextInt);
		assertThatIllegalStateException().isThrownBy(Iterators.EMPTY_INT::remove);
	}

	@Test
	void testEmptyLong() {
		assertThat(Iterators.EMPTY_LONG.hasNext()).isFalse();
		assertThatExceptionOfType(NoSuchElementException.class).isThrownBy(Iterators.EMPTY_LONG::nextLong);
		assertThatIllegalStateException().isThrownBy(Iterators.EMPTY_LONG::remove);
	}

	@Test
	void testEmptyDouble() {
		assertThat(Iterators.EMPTY_DOUBLE.hasNext()).isFalse();
		assertThatExceptionOfType(NoSuchElementException.class).isThrownBy(Iterators.EMPTY_DOUBLE::nextDouble);
		assertThatIllegalStateException().isThrownBy(Iterators.EMPTY_DOUBLE::remove);
	}

	@Test
	void testNullToEmptyIterator() {
		assertThat(Iterators.nullToEmpty((Iterator<Object>) null)).isEmpty();
		assertThat(Iterators.nullToEmpty(Collections.emptyIterator())).isEmpty();
		assertThat(Iterators.nullToEmpty(Iterators.of(1))).isNotEmpty();
	}

	@Test
	void testNullToEmptyListIterator() {
		assertThat(Iterators.nullToEmpty(null)).isEmpty();
		assertThat(Iterators.nullToEmpty(Collections.emptyListIterator())).isEmpty();
		assertThat(Iterators.nullToEmpty(List.of(1).listIterator())).isNotEmpty();
	}

	@Test
	void testUnmodifiable() {
		final var list = new ArrayList<>(Arrays.asList(1, null, 2));
		final var iterator = list.iterator();
		iterator.next();
		iterator.remove();
		assertThat(list).hasSize(2);
		final var unmodifiableIterator = Iterators.unmodifiable(iterator);
		while (unmodifiableIterator.hasNext()) {
			unmodifiableIterator.next();
			assertThatExceptionOfType(UnsupportedOperationException.class).isThrownBy(unmodifiableIterator::remove);
		}
		assertThatExceptionOfType(NoSuchElementException.class).isThrownBy(unmodifiableIterator::next);
		assertThat(list).hasSize(2);
	}

	@Test
	void testUnmodifiableNull() {
		assertThatNullPointerException().isThrownBy(() -> Iterators.unmodifiable(null));
	}

	@Test
	void testMap() {
		final var list = new ArrayList<>(Arrays.asList(1, 2, 3));
		final var mapIterator = Iterators.map(list.iterator(), i -> -i);
		while (mapIterator.hasNext()) {
			assertThat(mapIterator.next()).isNegative();
			mapIterator.remove();
		}
		assertThatExceptionOfType(NoSuchElementException.class).isThrownBy(mapIterator::next);
		assertThat(list).isEmpty();
	}

	@Test
	void testMapNull() {
		assertThatNullPointerException().isThrownBy(() -> Iterators.map(null, Function.identity()));
		assertThatNullPointerException().isThrownBy(() -> Iterators.map(Collections.emptyIterator(), null));
	}

	@Test
	void testUntil() {
		final var array = ObjectArrays.of(1, 2000, null, 4000);
		final var untilIterator = Iterators.until(new Supplier<>() {
			private int i = 0;

			@Override
			public Integer get() {
				return array[i++];
			}
		}, 4000);
		assertThat(untilIterator.next()).isEqualTo(1);
		assertThat(untilIterator.next()).isEqualTo(2000);
		assertThat(untilIterator.next()).isNull();
		assertThatExceptionOfType(NoSuchElementException.class).isThrownBy(untilIterator::next);
	}

	@Test
	void testUntilNull() {
		assertThatNullPointerException().isThrownBy(() -> Iterators.until(null, 1));
		final var untilIterator = Iterators.until(new Supplier<>() {
			private int i = 0;

			@Override
			public Integer get() {
				return i < 3 ? ++i : null;
			}
		}, null);
		assertThat(untilIterator.next()).isEqualTo(1);
		assertThat(untilIterator.next()).isEqualTo(2);
		assertThat(untilIterator.next()).isEqualTo(3);
		assertThatExceptionOfType(NoSuchElementException.class).isThrownBy(untilIterator::next);
	}

	@Test
	void testTransferTo() {
		final var iterator = Iterators.ofInt(1, 2, 3);
		final var list = new ArrayList<>();
		assertThat(Iterators.transferTo(iterator, list)).isEqualTo(3L);
		assertThat(list).containsExactly(1, 2, 3);
		assertThat(Iterators.transferTo(iterator, list)).isEqualTo(0L);
		assertThat(list).containsExactly(1, 2, 3);
	}

	@Test
	void testTransferToNull() {
		assertThatNullPointerException().isThrownBy(() -> Iterators.transferTo(null, new ArrayList<>()));
		assertThatNullPointerException().isThrownBy(() -> Iterators.transferTo(Collections.emptyIterator(), null));
	}

	@Test
	void testConcat() {
		assertThat(Iterators.concat(Iterators.of(1, 2, 3), Iterators.of(1L, 2L, 3L))).containsExactly(1, 2, 3, 1L, 2L, 3L);
	}

	@Test
	void testConcatOne() {
		assertThat(Iterators.concat(Iterators.of(1, 2, 3))).containsExactly(1, 2, 3);
	}

	@Test
	void testConcatNone() {
		assertThat(Iterators.concat()).isEmpty();
	}

	@Test
	void testConcatEmpty() {
		final var concatIterator = Iterators.concat(Collections.emptyIterator(), Collections.emptyIterator());
		assertThat(concatIterator).isEmpty();
		assertThat(concatIterator.hasNext()).isFalse();
		assertThatExceptionOfType(NoSuchElementException.class).isThrownBy(concatIterator::next);
	}

	@Test
	void testConcatNull() {
		assertThatNullPointerException().isThrownBy(() -> Iterators.concat((Iterator<?>[]) null));
		assertThatNullPointerException().isThrownBy(() -> Iterators.concat((List<Iterator<?>>) null));
		assertThatNullPointerException().isThrownBy(() -> Iterators.concat((Iterator<?>) null));
	}

	@Test
	void testJoin() {
		assertThat(Iterators.join(ObjectArrays.of(0.0d), Iterators.of(1, 2, 3), Iterators.of(1L, 2L, 3L))).containsExactly(1, 2, 3, 0.0d, 1L, 2L, 3L);
	}

	@Test
	void testJoinEmptySeparator() {
		assertThat(Iterators.join(ObjectArrays.empty(Integer.class), Iterators.of(1, 2, 3), Iterators.of(1L, 2L, 3L))).containsExactly(1, 2, 3, 1L, 2L, 3L);
	}

	@Test
	void testJoinOne() {
		assertThat(Iterators.join(ObjectArrays.of(0.0d), Iterators.of(1, 2, 3))).containsExactly(1, 2, 3);
	}

	@Test
	void testJoinNone() {
		assertThat(Iterators.join(ObjectArrays.of(0.0d))).isEmpty();
	}

	@Test
	void testJoinEmpty() {
		assertThat(Iterators.join(ObjectArrays.of(0.0d), Collections.emptyIterator(), Collections.emptyIterator())).containsExactly(0.0d);
	}

	@Test
	void testJoinNull() {
		assertThatNullPointerException().isThrownBy(() -> Iterators.join(null, Collections.emptyIterator()));
		assertThatNullPointerException().isThrownBy(() -> Iterators.join(ObjectArrays.empty(Integer.class), (Iterator<?>[]) null));
		assertThatNullPointerException().isThrownBy(() -> Iterators.join(ObjectArrays.empty(Integer.class), (List<Iterator<?>>) null));
		assertThatNullPointerException().isThrownBy(() -> Iterators.join(ObjectArrays.empty(Integer.class), (Iterator<?>) null));
	}

	@Test
	void testOfInt() {
		assertThat(Iterators.ofInt()).isEmpty();
		assertThat(Iterators.ofInt(1, 2, 3)).containsExactly(1, 2, 3);
	}

	@Test
	void testOfIntNull() {
		assertThatNullPointerException().isThrownBy(() -> Iterators.ofInt((int[]) null));
	}

	@Test
	void testOfLong() {
		assertThat(Iterators.ofLong()).isEmpty();
		assertThat(Iterators.ofLong(1L, 2L, 3L)).containsExactly(1L, 2L, 3L);
	}

	@Test
	void testOfLongNull() {
		assertThatNullPointerException().isThrownBy(() -> Iterators.ofLong((long[]) null));
	}

	@Test
	void testOfDouble() {
		assertThat(Iterators.ofDouble()).isEmpty();
		assertThat(Iterators.ofDouble(1.0d, 2.0d, 3.0d)).containsExactly(1.0d, 2.0d, 3.0d);
	}

	@Test
	void testOfDoubleNull() {
		assertThatNullPointerException().isThrownBy(() -> Iterators.ofDouble((double[]) null));
	}

	@Test
	void testOf() {
		assertThat(Iterators.of()).isEmpty();
		assertThat(Iterators.of(1, null, 2)).containsExactly(1, null, 2);
	}

	@Test
	void testOfNull() {
		assertThatNullPointerException().isThrownBy(() -> Iterators.of((Integer[]) null));
	}

	@Test
	void testToSet() {
		assertThat(Iterators.toSet(Collections.emptyIterator())).isEmpty();
		assertThat(Iterators.toSet(Iterators.of(1, null, null, 2))).containsExactlyInAnyOrder(1, null, 2);
	}

	@Test
	void testToSetNull() {
		assertThatNullPointerException().isThrownBy(() -> Iterators.toSet(null));
	}

	@Test
	void testToList() {
		assertThat(Iterators.toList(Collections.emptyIterator())).isEmpty();
		assertThat(Iterators.toList(Iterators.of(1, null, null, 2))).containsExactly(1, null, null, 2);
	}

	@Test
	void testToListNull() {
		assertThatNullPointerException().isThrownBy(() -> Iterators.toList(null));
	}

	@Test
	void testToEnumeration() {
		assertThat(Iterators.toEnumeration(Collections.emptyIterator()).asIterator()).isEmpty();
		assertThat(Iterators.toEnumeration(Iterators.of(1, null, 2)).asIterator()).containsExactly(1, null, 2);
		final var enumeration = Iterators.toEnumeration(Iterators.of(1, null, 2));
		assertThat(enumeration.hasMoreElements()).isTrue();
		assertThat(enumeration.nextElement()).isEqualTo(1);
		assertThat(enumeration.nextElement()).isNull();
		assertThat(enumeration.nextElement()).isEqualTo(2);
		assertThat(enumeration.hasMoreElements()).isFalse();
	}

	@Test
	void testToEnumerationNull() {
		assertThatNullPointerException().isThrownBy(() -> Iterators.toEnumeration(null));
	}

	@Test
	void testOfInputStream() {
		final var inputStreamIterator = Iterators.of(InputStreams.of((byte) 0, (byte) 255));
		while (inputStreamIterator.hasNext()) {
			assertThat(inputStreamIterator.next()).isNotEqualTo(-1);
		}
		assertThatExceptionOfType(NoSuchElementException.class).isThrownBy(inputStreamIterator::next);
	}

	@Test
	void testOfInputStreamException() {
		assertThatExceptionOfType(UncheckedIOException.class).isThrownBy(() -> Iterators.of(new InputStream() {
			@Override
			public int read() throws IOException {
				throw new IOException();
			}
		}).hasNext());
	}

	@Test
	void testOfInputStreamNull() {
		assertThatNullPointerException().isThrownBy(() -> Iterators.of((InputStream) null));
	}

	@Test
	void testToInputStream() {
		try (final var inputStream = Iterators.toInputStream(Iterators.of(0, 255))) {
			assertThat(inputStream.read()).isEqualTo(0);
			assertThat(inputStream.read()).isEqualTo(255);
			assertThat(inputStream.read()).isEqualTo(-1);
		} catch (final IOException e) {
			fail(e.getMessage());
		}
	}

	@Test
	void testToInputStreamNull() {
		assertThatNullPointerException().isThrownBy(() -> Iterators.toInputStream(null));
	}

	@Test
	void testOfReader() {
		final var readerIterator = Iterators.of(Readers.of((char) 0, (char) 255));
		while (readerIterator.hasNext()) {
			assertThat(readerIterator.next()).isNotEqualTo(-1);
		}
		assertThatExceptionOfType(NoSuchElementException.class).isThrownBy(readerIterator::next);
	}

	@Test
	void testOfReaderException() {
		assertThatExceptionOfType(UncheckedIOException.class).isThrownBy(() -> Iterators.of(new Reader() {
			@Override
			public int read(@NotNull final char[] cbuf, final int off, final int len) throws IOException {
				throw new IOException();
			}

			@Override
			public void close() {
				// Nothing to do
			}
		}).hasNext());
	}

	@Test
	void testOfReaderNull() {
		assertThatNullPointerException().isThrownBy(() -> Iterators.of((Reader) null));
	}

	@Test
	void testToReader() {
		try (final var reader = Iterators.toReader(Iterators.of(0, 255, 0, 255))) {
			assertThat(reader.read()).isEqualTo(0);
			final var buffer = new char[2];
			assertThatExceptionOfType(IndexOutOfBoundsException.class).isThrownBy(() -> reader.read(buffer, -1,  3));
			assertThatExceptionOfType(IndexOutOfBoundsException.class).isThrownBy(() -> reader.read(buffer,  3,  3));
			assertThatExceptionOfType(IndexOutOfBoundsException.class).isThrownBy(() -> reader.read(buffer,  0, -1));
			assertThatExceptionOfType(IndexOutOfBoundsException.class).isThrownBy(() -> reader.read(buffer,  0,  3));
			assertThat(reader.read(buffer, 0, 0)).isEqualTo(0);
			assertThat(reader.read(buffer, 0, 2)).isEqualTo(2);
			assertThat(buffer).containsExactly((char) 255, (char) 0);
			assertThat(reader.read(buffer, 0, 2)).isEqualTo(1);
			assertThat(buffer[0]).isEqualTo((char) 255);
			assertThat(reader.read()).isEqualTo(-1);
			assertThat(reader.read(buffer, 0, 1)).isEqualTo(-1);
		} catch (final IOException e) {
			fail(e.getMessage());
		}
	}

	@Test
	void testToReaderNull() {
		assertThatNullPointerException().isThrownBy(() -> Iterators.toReader(null));
	}

	@Test
	void testOfBufferedReader() {
		final var bufferedReaderIterator = Iterators.of(Readers.buffered(Readers.of((char) 0, (char) 255)));
		while (bufferedReaderIterator.hasNext()) {
			assertThat(bufferedReaderIterator.next()).isNotNull();
		}
		assertThatExceptionOfType(NoSuchElementException.class).isThrownBy(bufferedReaderIterator::next);
	}

	@Test
	void testOfBufferedReaderException() {
		assertThatExceptionOfType(UncheckedIOException.class).isThrownBy(() -> Iterators.of(Readers.buffered(new Reader() {
			@Override
			public int read(@NotNull final char[] cbuf, final int off, final int len) throws IOException {
				throw new IOException();
			}

			@Override
			public void close() {
				// Nothing to do
			}
		})).hasNext());
	}

	@Test
	void testOfBufferedReaderNull() {
		assertThatNullPointerException().isThrownBy(() -> Iterators.of((BufferedReader) null));
	}

	@Test
	void testOfLineReader() {
		final var lineReaderIterator = Iterators.of(new LineReader(Readers.of((char) 0, (char) 255), LineSeparator.DEFAULT));
		while (lineReaderIterator.hasNext()) {
			assertThat(lineReaderIterator.next()).isNotNull();
		}
		assertThatExceptionOfType(NoSuchElementException.class).isThrownBy(lineReaderIterator::next);
	}

	@Test
	void testOfLineReaderException() {
		assertThatExceptionOfType(UncheckedIOException.class).isThrownBy(() -> Iterators.of(new LineReader(new Reader() {
			@Override
			public int read(@NotNull final char[] cbuf, final int off, final int len) throws IOException {
				throw new IOException();
			}

			@Override
			public void close() {
				// Nothing to do
			}
		}, LineSeparator.DEFAULT)).hasNext());
	}

	@Test
	void testOfLineReaderNull() {
		assertThatNullPointerException().isThrownBy(() -> Iterators.of((LineReader) null));
	}
}