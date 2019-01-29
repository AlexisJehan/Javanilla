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
package com.github.alexisjehan.javanilla.util.iteration;

import com.github.alexisjehan.javanilla.io.bytes.InputStreams;
import com.github.alexisjehan.javanilla.io.chars.Readers;
import com.github.alexisjehan.javanilla.io.lines.LineReader;
import com.github.alexisjehan.javanilla.io.lines.LineSeparator;
import com.github.alexisjehan.javanilla.lang.array.ObjectArrays;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.io.UncheckedIOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import java.util.PrimitiveIterator;
import java.util.function.Function;
import java.util.function.Supplier;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.assertj.core.api.Assertions.assertThatIllegalStateException;
import static org.assertj.core.api.Assertions.assertThatNullPointerException;

/**
 * <p>{@link Iterators} unit tests.</p>
 */
final class IteratorsTest {

	@Test
	void testEmptyInt() {
		final var emptyIterator = Iterators.EMPTY_INT;
		assertThat(emptyIterator.hasNext()).isFalse();
		assertThatExceptionOfType(NoSuchElementException.class).isThrownBy(emptyIterator::nextInt);
		assertThatIllegalStateException().isThrownBy(emptyIterator::remove);
	}

	@Test
	void testEmptyLong() {
		final var emptyIterator = Iterators.EMPTY_LONG;
		assertThat(emptyIterator.hasNext()).isFalse();
		assertThatExceptionOfType(NoSuchElementException.class).isThrownBy(emptyIterator::nextLong);
		assertThatIllegalStateException().isThrownBy(emptyIterator::remove);
	}

	@Test
	void testEmptyDouble() {
		final var emptyIterator = Iterators.EMPTY_DOUBLE;
		assertThat(emptyIterator.hasNext()).isFalse();
		assertThatExceptionOfType(NoSuchElementException.class).isThrownBy(emptyIterator::nextDouble);
		assertThatIllegalStateException().isThrownBy(emptyIterator::remove);
	}

	@Test
	void testNullToEmptyOfInt() {
		assertThat(Iterators.nullToEmpty((PrimitiveIterator.OfInt) null)).isEmpty();
		assertThat(Iterators.nullToEmpty(Iterators.EMPTY_INT)).isEmpty();
		assertThat(Iterators.nullToEmpty(Iterators.singletonInt(1))).containsExactly(1);
	}

	@Test
	void testNullToEmptyOfLong() {
		assertThat(Iterators.nullToEmpty((PrimitiveIterator.OfLong) null)).isEmpty();
		assertThat(Iterators.nullToEmpty(Iterators.EMPTY_LONG)).isEmpty();
		assertThat(Iterators.nullToEmpty(Iterators.singletonLong(1L))).containsExactly(1L);
	}

	@Test
	void testNullToEmptyOfDouble() {
		assertThat(Iterators.nullToEmpty((PrimitiveIterator.OfDouble) null)).isEmpty();
		assertThat(Iterators.nullToEmpty(Iterators.EMPTY_DOUBLE)).isEmpty();
		assertThat(Iterators.nullToEmpty(Iterators.singletonDouble(1.0d))).containsExactly(1.0d);
	}

	@Test
	void testNullToEmptyIterator() {
		assertThat(Iterators.nullToEmpty((Iterator<Integer>) null)).isEmpty();
		assertThat(Iterators.nullToEmpty(Collections.emptyIterator())).isEmpty();
		assertThat(Iterators.nullToEmpty(Collections.singletonList(1).iterator())).containsExactly(1);
	}

	@Test
	void testNullToEmptyListIterator() {
		assertThat(Iterators.nullToEmpty((ListIterator<Integer>) null)).isEmpty();
		assertThat(Iterators.nullToEmpty(Collections.emptyListIterator())).isEmpty();
		assertThat(Iterators.nullToEmpty(Collections.singletonList(1).listIterator())).containsExactly(1);
	}

	@Test
	void testNullToDefault() {
		assertThat(Iterators.nullToDefault(null, Iterators.singleton(0))).containsExactly(0);
		assertThat(Iterators.nullToDefault(Collections.emptyIterator(), Iterators.singleton(0))).isEmpty();
		assertThat(Iterators.nullToDefault(Iterators.singleton(1), Iterators.singleton(0))).containsExactly(1);
	}

	@Test
	void testNullToDefaultInvalid() {
		assertThatNullPointerException().isThrownBy(() -> Iterators.nullToDefault(Iterators.singleton(1), null));
	}

	@Test
	void testEmptyToNull() {
		assertThat(Iterators.emptyToNull((Iterator<Integer>) null)).isNull();
		assertThat(Iterators.emptyToNull(Collections.emptyIterator())).isNull();
		assertThat(Iterators.emptyToNull(Collections.singletonList(1).iterator())).containsExactly(1);
	}

	@Test
	void testEmptyToDefault() {
		assertThat(Iterators.emptyToDefault(null, Iterators.singleton(0))).isNull();
		assertThat(Iterators.emptyToDefault(Collections.emptyIterator(), Iterators.singleton(0))).containsExactly(0);
		assertThat(Iterators.emptyToDefault(Iterators.singleton(1), Iterators.singleton(0))).containsExactly(1);
	}

	@Test
	void testEmptyToDefaultInvalid() {
		assertThatIllegalArgumentException().isThrownBy(() -> Iterators.emptyToDefault(Iterators.singleton(1), Collections.emptyIterator()));
	}

	@Test
	void testIsEmpty() {
		assertThat(Iterators.isEmpty(Collections.emptyIterator())).isTrue();
		assertThat(Iterators.isEmpty(Iterators.singleton(1))).isFalse();
	}

	@Test
	void testIsEmptyInvalid() {
		assertThatNullPointerException().isThrownBy(() -> Iterators.isEmpty(null));
	}

	@Test
	void testUnmodifiable() {
		assertThat(Iterators.unmodifiable(Collections.emptyIterator())).isEmpty();
		final var list = new ArrayList<>(List.of(1, 2, 3));
		final var iterator = list.iterator();
		iterator.next();
		iterator.remove();
		assertThat(list).containsExactly(2, 3);
		final var unmodifiableIterator = Iterators.unmodifiable(iterator);
		while (unmodifiableIterator.hasNext()) {
			unmodifiableIterator.next();
			assertThatExceptionOfType(UnsupportedOperationException.class).isThrownBy(unmodifiableIterator::remove);
		}
		assertThatExceptionOfType(NoSuchElementException.class).isThrownBy(unmodifiableIterator::next);
		assertThat(list).containsExactly(2, 3);
	}

	@Test
	void testUnmodifiableInvalid() {
		assertThatNullPointerException().isThrownBy(() -> Iterators.unmodifiable(null));
	}

	@Test
	void testFilter() {
		assertThat(Iterators.filter(Collections.emptyIterator(), element -> true)).isEmpty();
		final var list = new ArrayList<>(List.of(1, 2, 3));
		final var filterIterator = Iterators.filter(list.iterator(), i -> 0 == i % 2);
		while (filterIterator.hasNext()) {
			assertThat(filterIterator.next() % 2).isEqualTo(0);
			filterIterator.remove();
		}
		assertThatExceptionOfType(NoSuchElementException.class).isThrownBy(filterIterator::next);
		assertThat(list).containsExactly(1, 3);
	}

	@Test
	void testFilterInvalid() {
		assertThatNullPointerException().isThrownBy(() -> Iterators.filter(null, element -> true));
		assertThatNullPointerException().isThrownBy(() -> Iterators.filter(Iterators.singleton(1), null));
	}

	@Test
	void testMap() {
		assertThat(Iterators.map(Collections.emptyIterator(), Function.identity())).isEmpty();
		final var list = new ArrayList<>(List.of(1, 2, 3));
		final var mapIterator = Iterators.map(list.iterator(), i -> -i);
		while (mapIterator.hasNext()) {
			assertThat(mapIterator.next()).isNegative();
			mapIterator.remove();
		}
		assertThatExceptionOfType(NoSuchElementException.class).isThrownBy(mapIterator::next);
		assertThat(list).isEmpty();
	}

	@Test
	void testMapInvalid() {
		assertThatNullPointerException().isThrownBy(() -> Iterators.map(null, Function.identity()));
		assertThatNullPointerException().isThrownBy(() -> Iterators.map(Iterators.singleton(1), null));
	}

	@Test
	void testIndex() {
		assertThat(Iterators.index(Collections.emptyIterator())).isEmpty();
		final var list = new ArrayList<>(List.of(1, 2, 3));
		final var indexedIterator = Iterators.index(list.iterator());
		var index = 0;
		while (indexedIterator.hasNext()) {
			final var indexedElement = indexedIterator.next();
			assertThat(indexedElement.getIndex()).isEqualTo(index);
			assertThat(indexedElement.getElement()).isEqualTo(list.get(index));
			assertThatExceptionOfType(UnsupportedOperationException.class).isThrownBy(indexedIterator::remove);
			++index;
		}
		assertThatExceptionOfType(NoSuchElementException.class).isThrownBy(indexedIterator::next);
		assertThat(list).containsExactly(1, 2, 3);
	}

	@Test
	void testIndexInvalid() {
		assertThatNullPointerException().isThrownBy(() -> Iterators.index(null));
	}

	@Test
	void testUntil() {
		{
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
		{
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
	}

	@Test
	void testUntilInvalid() {
		assertThatNullPointerException().isThrownBy(() -> Iterators.until(null, 1));
	}

	@Test
	void testLength() {
		assertThat(Iterators.length(Collections.emptyIterator())).isEqualTo(0L);
		assertThat(Iterators.length(Iterators.singleton(1))).isEqualTo(1L);
		final var iterator = Iterators.of(1, 2, 3);
		assertThat(Iterators.length(iterator)).isEqualTo(3L);
		assertThat(Iterators.length(iterator)).isEqualTo(0L);
	}

	@Test
	void testLengthInvalid() {
		assertThatNullPointerException().isThrownBy(() -> Iterators.length(null));
	}

	@Test
	void testTransferTo() {
		final var list = new ArrayList<>();
		assertThat(Iterators.transferTo(Collections.singletonList(0).iterator(), list)).isEqualTo(1L);
		assertThat(list).containsExactly(0);
		final var iterator = Iterators.of(1, 2, 3);
		assertThat(Iterators.transferTo(iterator, list)).isEqualTo(3L);
		assertThat(list).containsExactly(0, 1, 2, 3);
		assertThat(Iterators.transferTo(iterator, list)).isEqualTo(0L);
		assertThat(list).containsExactly(0, 1, 2, 3);
	}

	@Test
	void testTransferToInvalid() {
		assertThatNullPointerException().isThrownBy(() -> Iterators.transferTo(null, new ArrayList<>()));
		assertThatNullPointerException().isThrownBy(() -> Iterators.transferTo(Iterators.singleton(1), null));
	}

	@Test
	void testGetOptionalFirst() {
		assertThat(Iterators.getOptionalFirst(Collections.emptyIterator()).isEmpty()).isTrue();
		assertThat(Iterators.getOptionalFirst(Collections.singletonList(0).iterator()).get()).isEqualTo(0);
		assertThat(Iterators.getOptionalFirst(Iterators.singleton(null)).get()).isNull();
		assertThat(Iterators.getOptionalFirst(Iterators.of(1, 2, 3)).get()).isEqualTo(1);
	}

	@Test
	void testGetOptionalFirstInvalid() {
		assertThatNullPointerException().isThrownBy(() -> Iterators.getOptionalFirst(null));
	}

	@Test
	void testGetOptionalLast() {
		assertThat(Iterators.getOptionalLast(Collections.emptyIterator()).isEmpty()).isTrue();
		assertThat(Iterators.getOptionalLast(Collections.singletonList(0).iterator()).get()).isEqualTo(0);
		assertThat(Iterators.getOptionalLast(Iterators.singleton(null)).get()).isNull();
		assertThat(Iterators.getOptionalLast(Iterators.of(1, 2, 3)).get()).isEqualTo(3);
	}

	@Test
	void testGetOptionalLastInvalid() {
		assertThatNullPointerException().isThrownBy(() -> Iterators.getOptionalLast(null));
	}

	@Test
	void testRemoveAll() {
		assertThat(Iterators.removeAll(Collections.emptyIterator(), Collections.singleton(1))).isFalse();
		assertThat(Iterators.removeAll(Iterators.of(1, 2, 3), Collections.emptySet())).isFalse();
		final var list = new ArrayList<>(List.of(1, 2, 3));
		Iterators.removeAll(list.iterator(), Collections.singleton(2));
		assertThat(list).containsExactly(1, 3);
	}

	@Test
	void testRemoveAllInvalid() {
		assertThatNullPointerException().isThrownBy(() -> Iterators.removeAll(null, Collections.singleton(1)));
		assertThatNullPointerException().isThrownBy(() -> Iterators.removeAll(Iterators.singleton(1), null));
	}

	@Test
	void testRemoveIf() {
		assertThat(Iterators.removeIf(Collections.emptyIterator(), element -> true)).isFalse();
		assertThat(Iterators.removeIf(Iterators.of(1, 2, 3), element -> false)).isFalse();
		final var list = new ArrayList<>(List.of(1, 2, 3));
		Iterators.removeIf(list.iterator(), element -> 2 == element);
		assertThat(list).containsExactly(1, 3);
	}

	@Test
	void testRemoveIfInvalid() {
		assertThatNullPointerException().isThrownBy(() -> Iterators.removeIf(null, element -> true));
		assertThatNullPointerException().isThrownBy(() -> Iterators.removeIf(Iterators.singleton(1), null));
	}

	@Test
	void testConcat() {
		assertThat(Iterators.concat()).isEmpty();
		assertThat(Iterators.concat(Iterators.singleton(1))).containsExactly(1);
		assertThat(Iterators.concat(Iterators.singleton(1), Iterators.singleton(2))).containsExactly(1, 2);
	}

	@Test
	void testConcatInvalid() {
		assertThatNullPointerException().isThrownBy(() -> Iterators.concat((Iterator<Integer>[]) null));
		assertThatNullPointerException().isThrownBy(() -> Iterators.concat((List<Iterator<Integer>>) null));
		assertThatNullPointerException().isThrownBy(() -> Iterators.concat((Iterator<Integer>) null));
	}

	@Test
	void testConcatSequenceIterator() {
		final var concatIterator = Iterators.concat(Iterators.of(1, 2, 3), Iterators.of(4, 5, 6));
		assertThat(concatIterator.next()).isEqualTo(1);
		while (concatIterator.hasNext()) {
			concatIterator.next();
		}
		assertThatExceptionOfType(NoSuchElementException.class).isThrownBy(concatIterator::next);
	}

	@Test
	void testJoin() {
		assertThat(Iterators.join(ObjectArrays.empty(Integer.class), Iterators.singleton(1), Iterators.singleton(2))).containsExactly(1, 2);
		assertThat(Iterators.join(ObjectArrays.singleton(0))).isEmpty();
		assertThat(Iterators.join(ObjectArrays.singleton(0), Iterators.singleton(1))).containsExactly(1);
		assertThat(Iterators.join(ObjectArrays.singleton(0), Iterators.singleton(1), Iterators.singleton(2))).containsExactly(1, 0, 2);
	}

	@Test
	void testJoinInvalid() {
		assertThatNullPointerException().isThrownBy(() -> Iterators.join(null, Iterators.singleton(1)));
		assertThatNullPointerException().isThrownBy(() -> Iterators.join(ObjectArrays.singleton(0), (Iterator<Integer>[]) null));
		assertThatNullPointerException().isThrownBy(() -> Iterators.join(ObjectArrays.singleton(0), (List<Iterator<Integer>>) null));
		assertThatNullPointerException().isThrownBy(() -> Iterators.join(ObjectArrays.singleton(0), (Iterator<Integer>) null));
	}

	@Test
	void testSingletonInt() {
		assertThat(Iterators.singletonInt(1)).containsExactly(1);
	}

	@Test
	void testSingletonLong() {
		assertThat(Iterators.singletonLong(1L)).containsExactly(1L);
	}

	@Test
	void testSingletonDouble() {
		assertThat(Iterators.singletonDouble(1.0d)).containsExactly(1.0d);
	}

	@Test
	void testSingleton() {
		assertThat(Iterators.singleton(1)).containsExactly(1);
	}

	@Test
	void testOfInt() {
		assertThat(Iterators.ofInt()).isEmpty();
		assertThat(Iterators.ofInt(1, 2, 3)).containsExactly(1, 2, 3);
	}

	@Test
	void testOfIntInvalid() {
		assertThatNullPointerException().isThrownBy(() -> Iterators.ofInt((int[]) null));
	}

	@Test
	void testOfLong() {
		assertThat(Iterators.ofLong()).isEmpty();
		assertThat(Iterators.ofLong(1L, 2L, 3L)).containsExactly(1L, 2L, 3L);
	}

	@Test
	void testOfLongInvalid() {
		assertThatNullPointerException().isThrownBy(() -> Iterators.ofLong((long[]) null));
	}

	@Test
	void testOfDouble() {
		assertThat(Iterators.ofDouble()).isEmpty();
		assertThat(Iterators.ofDouble(1.0d, 2.0d, 3.0d)).containsExactly(1.0d, 2.0d, 3.0d);
	}

	@Test
	void testOfDoubleInvalid() {
		assertThatNullPointerException().isThrownBy(() -> Iterators.ofDouble((double[]) null));
	}

	@Test
	void testOf() {
		assertThat(Iterators.of()).isEmpty();
		assertThat(Iterators.of(1, 2, 3)).containsExactly(1, 2, 3);
	}

	@Test
	void testOfInvalid() {
		assertThatNullPointerException().isThrownBy(() -> Iterators.of((Integer[]) null));
	}

	@Test
	void testToSet() {
		assertThat(Iterators.toSet(Collections.emptyIterator())).isEmpty();
		assertThat(Iterators.toSet(Collections.singleton(1).iterator())).containsExactly(1);
		assertThat(Iterators.toSet(Iterators.of(1, 2, 2, 3))).containsExactlyInAnyOrder(1, 2, 3);
	}

	@Test
	void testToSetInvalid() {
		assertThatNullPointerException().isThrownBy(() -> Iterators.toSet(null));
	}

	@Test
	void testToList() {
		assertThat(Iterators.toList(Collections.emptyIterator())).isEmpty();
		assertThat(Iterators.toList(Collections.singletonList(1).iterator())).containsExactly(1);
		assertThat(Iterators.toList(Iterators.of(1, 2, 2, 3))).containsExactly(1, 2, 2, 3);
	}

	@Test
	void testToListInvalid() {
		assertThatNullPointerException().isThrownBy(() -> Iterators.toList(null));
	}

	@Test
	void testOfInputStream() {
		final var inputStreamIterator = Iterators.of(InputStreams.of((byte) 1, (byte) 2, (byte) 3));
		while (inputStreamIterator.hasNext()) {
			assertThat(inputStreamIterator.next()).isNotEqualTo(-1);
		}
		assertThatExceptionOfType(NoSuchElementException.class).isThrownBy(inputStreamIterator::next);
		assertThatExceptionOfType(UncheckedIOException.class).isThrownBy(() -> Iterators.of(new InputStream() {
			@Override
			public int read() throws IOException {
				throw new IOException();
			}
		}).hasNext());
	}

	@Test
	void testOfInputStreamInvalid() {
		assertThatNullPointerException().isThrownBy(() -> Iterators.of((InputStream) null));
	}

	@Test
	void testToInputStream() throws IOException {
		assertThat(Iterators.toInputStream(Collections.emptyIterator()).read()).isEqualTo(-1);
		try (final var inputStream = Iterators.toInputStream(Iterators.of(0, 255))) {
			assertThat(inputStream.read()).isEqualTo(0);
			assertThat(inputStream.read()).isEqualTo(255);
			assertThat(inputStream.read()).isEqualTo(-1);
		}
	}

	@Test
	void testToInputStreamInvalid() {
		assertThatNullPointerException().isThrownBy(() -> Iterators.toInputStream(null));
	}

	@Test
	void testOfReader() {
		final var readerIterator = Iterators.of(Readers.of('a', 'b', 'c'));
		while (readerIterator.hasNext()) {
			assertThat(readerIterator.next()).isNotEqualTo(-1);
		}
		assertThatExceptionOfType(NoSuchElementException.class).isThrownBy(readerIterator::next);
		assertThatExceptionOfType(UncheckedIOException.class).isThrownBy(() -> Iterators.of(new Reader() {
			@Override
			public int read(final char[] buffer, final int offset, final int length) throws IOException {
				throw new IOException();
			}

			@Override
			public void close() {
				// Nothing to do
			}
		}).hasNext());
	}

	@Test
	void testOfReaderInvalid() {
		assertThatNullPointerException().isThrownBy(() -> Iterators.of((Reader) null));
	}

	@Test
	void testToReader() throws IOException {
		assertThat(Iterators.toReader(Collections.emptyIterator()).read()).isEqualTo(-1);
		try (final var reader = Iterators.toReader(Iterators.of((int) 'a', (int) 'b', (int) 'c', (int) 'd'))) {
			assertThat(reader.read()).isEqualTo('a');
			final var buffer = new char[2];
			assertThat(reader.read(buffer, 0, 0)).isEqualTo(0);
			assertThat(reader.read(buffer, 0, 2)).isEqualTo(2);
			assertThatNullPointerException().isThrownBy(() -> reader.read(null, 0, 2));
			assertThatIllegalArgumentException().isThrownBy(() -> reader.read(buffer, -1, 2));
			assertThatIllegalArgumentException().isThrownBy(() -> reader.read(buffer, 3, 2));
			assertThatIllegalArgumentException().isThrownBy(() -> reader.read(buffer, 0, -1));
			assertThatIllegalArgumentException().isThrownBy(() -> reader.read(buffer, 0, 3));
			assertThat(buffer).containsExactly('b', 'c');
			assertThat(reader.read(buffer, 0, 2)).isEqualTo(1);
			assertThat(buffer[0]).isEqualTo('d');
			assertThat(reader.read()).isEqualTo(-1);
			assertThat(reader.read(buffer, 0, 1)).isEqualTo(-1);
		}
	}

	@Test
	void testToReaderInvalid() {
		assertThatNullPointerException().isThrownBy(() -> Iterators.toReader(null));
	}

	@Test
	void testOfBufferedReader() {
		final var bufferedReaderIterator = Iterators.of(Readers.buffered(Readers.of("abc\ndef\nghi")));
		while (bufferedReaderIterator.hasNext()) {
			assertThat(bufferedReaderIterator.next()).isNotNull();
		}
		assertThatExceptionOfType(NoSuchElementException.class).isThrownBy(bufferedReaderIterator::next);
		assertThatExceptionOfType(UncheckedIOException.class).isThrownBy(() -> Iterators.of(Readers.buffered(new Reader() {
			@Override
			public int read(final char[] buffer, final int offset, final int length) throws IOException {
				throw new IOException();
			}

			@Override
			public void close() {
				// Nothing to do
			}
		})).hasNext());
	}

	@Test
	void testOfBufferedReaderInvalid() {
		assertThatNullPointerException().isThrownBy(() -> Iterators.of((BufferedReader) null));
	}

	@Test
	void testOfLineReader() {
		final var lineReaderIterator = Iterators.of(new LineReader(Readers.of("abc\ndef\nghi"), LineSeparator.DEFAULT));
		while (lineReaderIterator.hasNext()) {
			assertThat(lineReaderIterator.next()).isNotNull();
		}
		assertThatExceptionOfType(NoSuchElementException.class).isThrownBy(lineReaderIterator::next);
		assertThatExceptionOfType(UncheckedIOException.class).isThrownBy(() -> Iterators.of(new LineReader(new Reader() {
			@Override
			public int read(final char[] buffer, final int offset, final int length) throws IOException {
				throw new IOException();
			}

			@Override
			public void close() {
				// Nothing to do
			}
		}, LineSeparator.DEFAULT)).hasNext());
	}

	@Test
	void testOfLineReaderInvalid() {
		assertThatNullPointerException().isThrownBy(() -> Iterators.of((LineReader) null));
	}
}