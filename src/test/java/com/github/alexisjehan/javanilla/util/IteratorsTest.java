/*
 * MIT License
 *
 * Copyright (c) 2018-2026 Alexis Jehan
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
package com.github.alexisjehan.javanilla.util;

import com.github.alexisjehan.javanilla.io.InputStreams;
import com.github.alexisjehan.javanilla.io.Readers;
import com.github.alexisjehan.javanilla.io.line.LineReader;
import com.github.alexisjehan.javanilla.io.line.LineSeparator;
import com.github.alexisjehan.javanilla.lang.array.DoubleArrays;
import com.github.alexisjehan.javanilla.lang.array.IntArrays;
import com.github.alexisjehan.javanilla.lang.array.LongArrays;
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
import java.util.concurrent.atomic.LongAdder;
import java.util.function.Function;
import java.util.function.Supplier;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.assertj.core.api.Assertions.assertThatIllegalStateException;
import static org.assertj.core.api.Assertions.assertThatNullPointerException;

final class IteratorsTest {

	private static final int[] INT_ELEMENTS = IntArrays.of(1, 2, 3);

	private static final long[] LONG_ELEMENTS = LongArrays.of(1L, 2L, 3L);

	private static final double[] DOUBLE_ELEMENTS = DoubleArrays.of(1.0d, 2.0d, 3.0d);

	private static final Integer[] ELEMENTS = ObjectArrays.of(1, 2, 3);

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
		assertThat(Iterators.nullToEmpty((PrimitiveIterator.OfInt) null)).toIterable().isEmpty();
		assertThat(Iterators.nullToEmpty(Iterators.EMPTY_INT)).toIterable().isEmpty();
		assertThat(Iterators.nullToEmpty(Iterators.ofInts(INT_ELEMENTS))).toIterable().containsExactly(IntArrays.toBoxed(INT_ELEMENTS));
	}

	@Test
	void testNullToEmptyOfLong() {
		assertThat(Iterators.nullToEmpty((PrimitiveIterator.OfLong) null)).toIterable().isEmpty();
		assertThat(Iterators.nullToEmpty(Iterators.EMPTY_LONG)).toIterable().isEmpty();
		assertThat(Iterators.nullToEmpty(Iterators.ofLongs(LONG_ELEMENTS))).toIterable().containsExactly(LongArrays.toBoxed(LONG_ELEMENTS));
	}

	@Test
	void testNullToEmptyOfDouble() {
		assertThat(Iterators.nullToEmpty((PrimitiveIterator.OfDouble) null)).toIterable().isEmpty();
		assertThat(Iterators.nullToEmpty(Iterators.EMPTY_DOUBLE)).toIterable().isEmpty();
		assertThat(Iterators.nullToEmpty(Iterators.ofDoubles(DOUBLE_ELEMENTS))).toIterable().containsExactly(DoubleArrays.toBoxed(DOUBLE_ELEMENTS));
	}

	@Test
	void testNullToEmptyIterator() {
		assertThat(Iterators.nullToEmpty((Iterator<Integer>) null)).toIterable().isEmpty();
		assertThat(Iterators.nullToEmpty(Collections.emptyIterator())).toIterable().isEmpty();
		assertThat(Iterators.nullToEmpty(Iterators.of(ELEMENTS))).toIterable().containsExactly(ELEMENTS);
	}

	@Test
	void testNullToEmptyListIterator() {
		assertThat(Iterators.nullToEmpty((ListIterator<Integer>) null)).toIterable().isEmpty();
		assertThat(Iterators.nullToEmpty(Collections.emptyListIterator())).toIterable().isEmpty();
		assertThat(Iterators.nullToEmpty(List.of(ELEMENTS).listIterator())).toIterable().containsExactly(ELEMENTS);
	}

	@Test
	void testNullToDefault() {
		assertThat(Iterators.nullToDefault(null, Iterators.singleton(0))).toIterable().containsExactly(0);
		assertThat(Iterators.nullToDefault(Collections.emptyIterator(), Iterators.singleton(0))).toIterable().isEmpty();
		assertThat(Iterators.nullToDefault(Iterators.of(ELEMENTS), Iterators.singleton(0))).toIterable().containsExactly(ELEMENTS);
	}

	@Test
	void testNullToDefaultInvalid() {
		assertThatNullPointerException().isThrownBy(() -> Iterators.nullToDefault(Iterators.of(ELEMENTS), null));
	}

	@Test
	void testEmptyToNull() {
		assertThat(Iterators.emptyToNull((Iterator<Integer>) null)).isNull();
		assertThat(Iterators.emptyToNull(Collections.emptyIterator())).isNull();
		assertThat(Iterators.emptyToNull(Iterators.of(ELEMENTS))).toIterable().containsExactly(ELEMENTS);
	}

	@Test
	void testEmptyToDefault() {
		assertThat(Iterators.emptyToDefault(null, Iterators.singleton(0))).isNull();
		assertThat(Iterators.emptyToDefault(Collections.emptyIterator(), Iterators.singleton(0))).toIterable().containsExactly(0);
		assertThat(Iterators.emptyToDefault(Iterators.of(ELEMENTS), Iterators.singleton(0))).toIterable().containsExactly(ELEMENTS);
	}

	@Test
	void testEmptyToDefaultInvalid() {
		assertThatIllegalArgumentException().isThrownBy(() -> Iterators.emptyToDefault(Iterators.of(ELEMENTS), Collections.emptyIterator()));
	}

	@Test
	void testUnmodifiable() {
		assertThat(Iterators.unmodifiable(Collections.emptyIterator())).toIterable().isEmpty();
		final var list = new ArrayList<>(List.of(ELEMENTS));
		final var iterator = list.iterator();
		iterator.next();
		iterator.remove();
		assertThat(list).containsExactly(ELEMENTS[1], ELEMENTS[2]);
		final var unmodifiableIterator = Iterators.unmodifiable(iterator);
		while (unmodifiableIterator.hasNext()) {
			unmodifiableIterator.next();
			assertThatExceptionOfType(UnsupportedOperationException.class).isThrownBy(unmodifiableIterator::remove);
		}
		assertThatExceptionOfType(NoSuchElementException.class).isThrownBy(unmodifiableIterator::next);
		assertThat(list).containsExactly(ELEMENTS[1], ELEMENTS[2]);
	}

	@Test
	void testUnmodifiableInvalid() {
		assertThatNullPointerException().isThrownBy(() -> Iterators.unmodifiable(null));
	}

	@Test
	void testIndex() {
		assertThat(Iterators.index(Collections.emptyIterator())).toIterable().isEmpty();
		final var list = new ArrayList<>(List.of(ELEMENTS));
		final var indexIterator = Iterators.index(list.iterator());
		var index = 0;
		while (indexIterator.hasNext()) {
			final var indexedElement = indexIterator.next();
			assertThat(indexedElement.getIndex()).isEqualTo(index);
			assertThat(indexedElement.getElement()).isEqualTo(list.get(index));
			assertThatExceptionOfType(UnsupportedOperationException.class).isThrownBy(indexIterator::remove);
			++index;
		}
		assertThatExceptionOfType(NoSuchElementException.class).isThrownBy(indexIterator::next);
		assertThat(list).containsExactly(ELEMENTS);
	}

	@Test
	void testIndexInvalid() {
		assertThatNullPointerException().isThrownBy(() -> Iterators.index(null));
	}

	@Test
	void testFilter() {
		assertThat(Iterators.filter(Collections.emptyIterator(), element -> true)).toIterable().isEmpty();
		final var list = new ArrayList<>(List.of(ELEMENTS));
		final var filterIterator = Iterators.filter(list.iterator(), element -> ELEMENTS[0].equals(element));
		while (filterIterator.hasNext()) {
			assertThat(ELEMENTS[0]).isEqualTo(filterIterator.next());
			filterIterator.remove();
		}
		assertThatExceptionOfType(NoSuchElementException.class).isThrownBy(filterIterator::next);
		assertThat(list).containsExactly(ELEMENTS[1], ELEMENTS[2]);
	}

	@Test
	void testFilterInvalid() {
		assertThatNullPointerException().isThrownBy(() -> Iterators.filter(null, element -> true));
		assertThatNullPointerException().isThrownBy(() -> Iterators.filter(Iterators.of(ELEMENTS), null));
	}

	@Test
	void testMap() {
		assertThat(Iterators.map(Collections.emptyIterator(), Function.identity())).toIterable().isEmpty();
		final var list = new ArrayList<>(List.of(ELEMENTS));
		final var mapIterator = Iterators.map(list.iterator(), element -> -element);
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
		assertThatNullPointerException().isThrownBy(() -> Iterators.map(Iterators.of(ELEMENTS), null));
	}

	@Test
	void testConcat() {
		assertThat(Iterators.concat()).toIterable().isEmpty();
		assertThat(Iterators.concat(Iterators.singleton(ELEMENTS[0]))).toIterable().containsExactly(ELEMENTS[0]);
		assertThat(Iterators.concat(Iterators.singleton(ELEMENTS[0]), Iterators.singleton(ELEMENTS[1]), Iterators.singleton(ELEMENTS[2]))).toIterable().containsExactly(ELEMENTS);
		assertThat(Iterators.concat(List.of(Iterators.singleton(ELEMENTS[0]), Iterators.singleton(ELEMENTS[1]), Iterators.singleton(ELEMENTS[2])))).toIterable().containsExactly(ELEMENTS);
	}

	@Test
	void testConcatInvalid() {
		assertThatNullPointerException().isThrownBy(() -> Iterators.concat((Iterator<Integer>[]) null));
		assertThatNullPointerException().isThrownBy(() -> Iterators.concat((Iterator<Integer>) null));
		assertThatNullPointerException().isThrownBy(() -> Iterators.concat((List<Iterator<Integer>>) null));
		assertThatNullPointerException().isThrownBy(() -> Iterators.concat(Collections.singletonList(null)));
	}

	@Test
	void testConcatSequenceIterator() {
		final var concatIterator = Iterators.concat(Iterators.of(ELEMENTS), Iterators.of(ELEMENTS));
		while (concatIterator.hasNext()) {
			concatIterator.next();
		}
		assertThatExceptionOfType(NoSuchElementException.class).isThrownBy(concatIterator::next);
	}

	@Test
	void testJoin() {
		assertThat(Iterators.join(ObjectArrays.empty(Integer.class), Iterators.singleton(ELEMENTS[0]), Iterators.singleton(ELEMENTS[1]), Iterators.singleton(ELEMENTS[2]))).toIterable().containsExactly(ELEMENTS);
		assertThat(Iterators.join(ObjectArrays.singleton(0))).toIterable().isEmpty();
		assertThat(Iterators.join(ObjectArrays.singleton(0), Iterators.singleton(ELEMENTS[0]))).toIterable().containsExactly(ELEMENTS[0]);
		assertThat(Iterators.join(ObjectArrays.singleton(0), Iterators.singleton(ELEMENTS[0]), Iterators.singleton(ELEMENTS[1]), Iterators.singleton(ELEMENTS[2]))).toIterable().containsExactly(ELEMENTS[0], 0, ELEMENTS[1], 0, ELEMENTS[2]);
		assertThat(Iterators.join(ObjectArrays.singleton(0), List.of(Iterators.singleton(ELEMENTS[0]), Iterators.singleton(ELEMENTS[1]), Iterators.singleton(ELEMENTS[2])))).toIterable().containsExactly(ELEMENTS[0], 0, ELEMENTS[1], 0, ELEMENTS[2]);
	}

	@Test
	void testJoinInvalid() {
		assertThatNullPointerException().isThrownBy(() -> Iterators.join(null, Iterators.of(ELEMENTS)));
		assertThatNullPointerException().isThrownBy(() -> Iterators.join(ObjectArrays.singleton(0), (Iterator<Integer>[]) null));
		assertThatNullPointerException().isThrownBy(() -> Iterators.join(ObjectArrays.singleton(0), (Iterator<Integer>) null));
		assertThatNullPointerException().isThrownBy(() -> Iterators.join(ObjectArrays.singleton(0), (List<Iterator<Integer>>) null));
		assertThatNullPointerException().isThrownBy(() -> Iterators.join(ObjectArrays.singleton(0), Collections.singletonList(null)));
	}

	@Test
	void testUntil() {
		final var untilIterator = Iterators.until(new Supplier<>() {

			private final LongAdder adder = new LongAdder();

			@Override
			public Integer get() {
				final var i = adder.intValue();
				adder.increment();
				return i;
			}
		}, 2);
		assertThat(untilIterator.next()).isZero();
		assertThat(untilIterator.next()).isEqualTo(1);
		assertThatExceptionOfType(NoSuchElementException.class).isThrownBy(untilIterator::next);
	}

	@Test
	void testUntilInvalid() {
		assertThatNullPointerException().isThrownBy(() -> Iterators.until(null, ELEMENTS[2]));
	}

	@Test
	void testRemoveAll() {
		assertThat(Iterators.removeAll(Collections.emptyIterator(), List.of(ELEMENTS[0]))).isFalse();
		assertThat(Iterators.removeAll(Iterators.of(ELEMENTS), List.of())).isFalse();
		final var list = new ArrayList<>(List.of(ELEMENTS));
		Iterators.removeAll(list.iterator(), List.of(ELEMENTS[0]));
		assertThat(list).containsExactly(ELEMENTS[1], ELEMENTS[2]);
	}

	@Test
	void testRemoveAllInvalid() {
		assertThatNullPointerException().isThrownBy(() -> Iterators.removeAll(null, List.of(ELEMENTS[0])));
		assertThatNullPointerException().isThrownBy(() -> Iterators.removeAll(Iterators.of(ELEMENTS), null));
	}

	@Test
	void testRemoveIf() {
		assertThat(Iterators.removeIf(Collections.emptyIterator(), element -> true)).isFalse();
		assertThat(Iterators.removeIf(Iterators.of(ELEMENTS), element -> false)).isFalse();
		final var list = new ArrayList<>(List.of(ELEMENTS));
		Iterators.removeIf(list.iterator(), element -> ELEMENTS[0].equals(element));
		assertThat(list).containsExactly(ELEMENTS[1], ELEMENTS[2]);
	}

	@Test
	void testRemoveIfInvalid() {
		assertThatNullPointerException().isThrownBy(() -> Iterators.removeIf(null, element -> true));
		assertThatNullPointerException().isThrownBy(() -> Iterators.removeIf(Iterators.of(ELEMENTS), null));
	}

	@Test
	void testLength() {
		assertThat(Iterators.length(Collections.emptyIterator())).isZero();
		assertThat(Iterators.length(Iterators.singleton(ELEMENTS[0]))).isEqualTo(1L);
		final var iterator = Iterators.of(ELEMENTS);
		assertThat(Iterators.length(iterator)).isEqualTo(3L);
		assertThat(Iterators.length(iterator)).isZero();
	}

	@Test
	void testLengthInvalid() {
		assertThatNullPointerException().isThrownBy(() -> Iterators.length(null));
	}

	@Test
	void testTransferTo() {
		final var list = new ArrayList<>();
		assertThat(Iterators.transferTo(Collections.emptyIterator(), list)).isZero();
		assertThat(list).isEmpty();
		assertThat(Iterators.transferTo(Iterators.singleton(ELEMENTS[0]), list)).isEqualTo(1L);
		assertThat(list).containsExactly(ELEMENTS[0]);
		final var iterator = Iterators.of(ELEMENTS);
		assertThat(Iterators.transferTo(iterator, list)).isEqualTo(3L);
		assertThat(list).containsExactly(ELEMENTS[0], ELEMENTS[0], ELEMENTS[1], ELEMENTS[2]);
		assertThat(Iterators.transferTo(iterator, list)).isZero();
		assertThat(list).containsExactly(ELEMENTS[0], ELEMENTS[0], ELEMENTS[1], ELEMENTS[2]);
	}

	@Test
	void testTransferToInvalid() {
		assertThatNullPointerException().isThrownBy(() -> Iterators.transferTo(null, new ArrayList<>()));
		assertThatNullPointerException().isThrownBy(() -> Iterators.transferTo(Iterators.of(ELEMENTS), null));
	}

	@Test
	void testGetOptionalFirst() {
		assertThat(Iterators.getOptionalFirst(Collections.emptyIterator()).isEmpty()).isTrue();
		assertThat(Iterators.getOptionalFirst(Iterators.singleton(ELEMENTS[0])).get()).isEqualTo(ELEMENTS[0]);
		assertThat(Iterators.getOptionalFirst(Iterators.singleton(null)).get()).isNull();
		assertThat(Iterators.getOptionalFirst(Iterators.of(ELEMENTS)).get()).isEqualTo(1);
	}

	@Test
	void testGetOptionalFirstInvalid() {
		assertThatNullPointerException().isThrownBy(() -> Iterators.getOptionalFirst(null));
	}

	@Test
	void testGetOptionalLast() {
		assertThat(Iterators.getOptionalLast(Collections.emptyIterator()).isEmpty()).isTrue();
		assertThat(Iterators.getOptionalLast(Iterators.singleton(ELEMENTS[0])).get()).isEqualTo(ELEMENTS[0]);
		assertThat(Iterators.getOptionalLast(Iterators.singleton(null)).get()).isNull();
		assertThat(Iterators.getOptionalLast(Iterators.of(ELEMENTS)).get()).isEqualTo(ELEMENTS[2]);
	}

	@Test
	void testGetOptionalLastInvalid() {
		assertThatNullPointerException().isThrownBy(() -> Iterators.getOptionalLast(null));
	}

	@Test
	void testIsEmpty() {
		assertThat(Iterators.isEmpty(Collections.emptyIterator())).isTrue();
		assertThat(Iterators.isEmpty(Iterators.of(ELEMENTS))).isFalse();
	}

	@Test
	void testIsEmptyInvalid() {
		assertThatNullPointerException().isThrownBy(() -> Iterators.isEmpty(null));
	}

	@Test
	void testSingletonInt() {
		assertThat(Iterators.singleton(INT_ELEMENTS[0])).toIterable().containsExactly(INT_ELEMENTS[0]);
	}

	@Test
	void testSingletonLong() {
		assertThat(Iterators.singleton(LONG_ELEMENTS[0])).toIterable().containsExactly(LONG_ELEMENTS[0]);
	}

	@Test
	void testSingletonDouble() {
		assertThat(Iterators.singleton(DOUBLE_ELEMENTS[0])).toIterable().containsExactly(DOUBLE_ELEMENTS[0]);
	}

	@Test
	void testSingleton() {
		assertThat(Iterators.singleton(ELEMENTS[0])).toIterable().containsExactly(ELEMENTS[0]);
	}

	@Test
	void testOfInts() {
		assertThat(Iterators.ofInts()).toIterable().isEmpty();
		assertThat(Iterators.ofInts(INT_ELEMENTS)).toIterable().containsExactly(IntArrays.toBoxed(INT_ELEMENTS));
	}

	@Test
	void testOfIntsInvalid() {
		assertThatNullPointerException().isThrownBy(() -> Iterators.ofInts((int[]) null));
	}

	@Test
	void testOfLongs() {
		assertThat(Iterators.ofLongs()).toIterable().isEmpty();
		assertThat(Iterators.ofLongs(LONG_ELEMENTS)).toIterable().containsExactly(LongArrays.toBoxed(LONG_ELEMENTS));
	}

	@Test
	void testOfLongsInvalid() {
		assertThatNullPointerException().isThrownBy(() -> Iterators.ofLongs((long[]) null));
	}

	@Test
	void testOfDoubles() {
		assertThat(Iterators.ofDoubles()).toIterable().isEmpty();
		assertThat(Iterators.ofDoubles(DOUBLE_ELEMENTS)).toIterable().containsExactly(DoubleArrays.toBoxed(DOUBLE_ELEMENTS));
	}

	@Test
	void testOfDoublesInvalid() {
		assertThatNullPointerException().isThrownBy(() -> Iterators.ofDoubles((double[]) null));
	}

	@Test
	void testOf() {
		assertThat(Iterators.of()).toIterable().isEmpty();
		assertThat(Iterators.of(ELEMENTS)).toIterable().containsExactly(ELEMENTS);
	}

	@Test
	void testOfInvalid() {
		assertThatNullPointerException().isThrownBy(() -> Iterators.of((Integer[]) null));
	}

	@Test
	void testOfInputStream() {
		final var inputStreamIterator = Iterators.of(InputStreams.of((byte) 1, (byte) 2, (byte) 3));
		while (inputStreamIterator.hasNext()) {
			assertThat(inputStreamIterator.next()).isNotEqualTo(-1);
		}
		assertThatExceptionOfType(NoSuchElementException.class).isThrownBy(inputStreamIterator::next);
		assertThat(
				Iterators.of(new InputStream() {

					@Override
					public int read() throws IOException {
						throw new IOException();
					}
				})
		).satisfies(exceptionInputStreamIterator -> assertThatExceptionOfType(UncheckedIOException.class).isThrownBy(exceptionInputStreamIterator::hasNext));
	}

	@Test
	void testOfInputStreamInvalid() {
		assertThatNullPointerException().isThrownBy(() -> Iterators.of((InputStream) null));
	}

	@Test
	void testOfReader() {
		final var readerIterator = Iterators.of(Readers.of('a', 'b', 'c'));
		while (readerIterator.hasNext()) {
			assertThat(readerIterator.next()).isNotEqualTo(-1);
		}
		assertThatExceptionOfType(NoSuchElementException.class).isThrownBy(readerIterator::next);
		assertThat(
				Iterators.of(new Reader() {

					@Override
					public int read(final char[] buffer, final int offset, final int length) throws IOException {
						throw new IOException();
					}

					@Override
					public void close() {
						// Empty
					}
				})
		).satisfies(exceptionReaderIterator -> assertThatExceptionOfType(UncheckedIOException.class).isThrownBy(exceptionReaderIterator::hasNext));
	}

	@Test
	void testOfReaderInvalid() {
		assertThatNullPointerException().isThrownBy(() -> Iterators.of((Reader) null));
	}

	@Test
	void testOfBufferedReader() {
		final var bufferedReaderIterator = Iterators.of(Readers.buffered(Readers.of(String.join("\n", "abc", "def", "ghi"))));
		while (bufferedReaderIterator.hasNext()) {
			assertThat(bufferedReaderIterator.next()).isNotNull();
		}
		assertThatExceptionOfType(NoSuchElementException.class).isThrownBy(bufferedReaderIterator::next);
		assertThat(
				Iterators.of(Readers.buffered(
						new Reader() {

							@Override
							public int read(final char[] buffer, final int offset, final int length) throws IOException {
								throw new IOException();
							}

							@Override
							public void close() {
								// Empty
							}
						}
				))
		).satisfies(exceptionBufferedReaderIterator -> assertThatExceptionOfType(UncheckedIOException.class).isThrownBy(exceptionBufferedReaderIterator::hasNext));
	}

	@Test
	void testOfBufferedReaderInvalid() {
		assertThatNullPointerException().isThrownBy(() -> Iterators.of((BufferedReader) null));
	}

	@Test
	void testOfLineReader() {
		final var lineReaderIterator = Iterators.of(new LineReader(Readers.of(String.join("\n", "abc", "def", "ghi")), LineSeparator.DEFAULT));
		while (lineReaderIterator.hasNext()) {
			assertThat(lineReaderIterator.next()).isNotNull();
		}
		assertThatExceptionOfType(NoSuchElementException.class).isThrownBy(lineReaderIterator::next);
		assertThat(
				Iterators.of(new LineReader(new Reader() {

					@Override
					public int read(final char[] buffer, final int offset, final int length) throws IOException {
						throw new IOException();
					}

					@Override
					public void close() {
						// Empty
					}
				}, LineSeparator.DEFAULT))
		).satisfies(exceptionLineReaderIterator -> assertThatExceptionOfType(UncheckedIOException.class).isThrownBy(exceptionLineReaderIterator::hasNext));
	}

	@Test
	void testOfLineReaderInvalid() {
		assertThatNullPointerException().isThrownBy(() -> Iterators.of((LineReader) null));
	}

	@Test
	void testToSet() {
		assertThat(Iterators.toSet(Collections.emptyIterator())).isEmpty();
		assertThat(Iterators.toSet(Iterators.singleton(ELEMENTS[0]))).containsExactlyInAnyOrder(ELEMENTS[0]);
		assertThat(Iterators.toSet(Iterators.of(ELEMENTS))).containsExactlyInAnyOrder(ELEMENTS);
	}

	@Test
	void testToSetInvalid() {
		assertThatNullPointerException().isThrownBy(() -> Iterators.toSet(null));
	}

	@Test
	void testToList() {
		assertThat(Iterators.toList(Collections.emptyIterator())).isEmpty();
		assertThat(Iterators.toList(Iterators.singleton(ELEMENTS[0]))).containsExactly(ELEMENTS[0]);
		assertThat(Iterators.toList(Iterators.of(ELEMENTS))).containsExactly(ELEMENTS);
	}

	@Test
	void testToListInvalid() {
		assertThatNullPointerException().isThrownBy(() -> Iterators.toList(null));
	}

	@Test
	void testToInputStream() throws IOException {
		assertThat(Iterators.toInputStream(Collections.emptyIterator()).read()).isEqualTo(-1);
		try (var inputStream = Iterators.toInputStream(Iterators.of(1, 2, 3))) {
			assertThat(inputStream.read()).isEqualTo(1);
			assertThat(inputStream.read()).isEqualTo(2);
			assertThat(inputStream.read()).isEqualTo(3);
			assertThat(inputStream.read()).isEqualTo(-1);
		}
	}

	@Test
	void testToInputStreamInvalid() {
		assertThatNullPointerException().isThrownBy(() -> Iterators.toInputStream(null));
	}

	@Test
	void testToReader() throws IOException {
		assertThat(Iterators.toReader(Collections.emptyIterator()).read()).isEqualTo(-1);
		try (var reader = Iterators.toReader(Iterators.of((int) 'a', (int) 'b', (int) 'c', (int) 'd'))) {
			assertThat(reader.read()).isEqualTo('a');
			final var buffer = new char[2];
			assertThat(reader.read(buffer, 0, 0)).isZero();
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
}