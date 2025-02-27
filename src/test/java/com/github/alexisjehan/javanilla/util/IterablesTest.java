/*
 * MIT License
 *
 * Copyright (c) 2018-2025 Alexis Jehan
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
import java.io.InputStream;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.PrimitiveIterator;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.assertj.core.api.Assertions.assertThatIllegalStateException;
import static org.assertj.core.api.Assertions.assertThatNullPointerException;

final class IterablesTest {

	private static final int[] INT_ELEMENTS = IntArrays.of(1, 2, 3);

	private static final long[] LONG_ELEMENTS = LongArrays.of(1L, 2L, 3L);

	private static final double[] DOUBLE_ELEMENTS = DoubleArrays.of(1.0d, 2.0d, 3.0d);

	private static final Integer[] ELEMENTS = ObjectArrays.of(1, 2, 3);

	@Test
	void testEmptyInt() {
		final var emptyIterable = Iterables.EMPTY_INT;
		for (var i = 0; 2 > i; ++i) {
			final var emptyIterator = emptyIterable.iterator();
			assertThat(emptyIterator.hasNext()).isFalse();
			assertThatExceptionOfType(NoSuchElementException.class).isThrownBy(emptyIterator::nextInt);
			assertThatIllegalStateException().isThrownBy(emptyIterator::remove);
		}
	}

	@Test
	void testEmptyLong() {
		final var emptyIterable = Iterables.EMPTY_LONG;
		for (var i = 0; 2 > i; ++i) {
			final var emptyIterator = emptyIterable.iterator();
			assertThat(emptyIterator.hasNext()).isFalse();
			assertThatExceptionOfType(NoSuchElementException.class).isThrownBy(emptyIterator::nextLong);
			assertThatIllegalStateException().isThrownBy(emptyIterator::remove);
		}
	}

	@Test
	void testEmptyDouble() {
		final var emptyIterable = Iterables.EMPTY_DOUBLE;
		for (var i = 0; 2 > i; ++i) {
			final var emptyIterator = emptyIterable.iterator();
			assertThat(emptyIterator.hasNext()).isFalse();
			assertThatExceptionOfType(NoSuchElementException.class).isThrownBy(emptyIterator::nextDouble);
			assertThatIllegalStateException().isThrownBy(emptyIterator::remove);
		}
	}

	@Test
	void testEmpty() {
		final var emptyIterable = Iterables.empty();
		for (var i = 0; 2 > i; ++i) {
			final var emptyIterator = emptyIterable.iterator();
			assertThat(emptyIterator.hasNext()).isFalse();
			assertThatExceptionOfType(NoSuchElementException.class).isThrownBy(emptyIterator::next);
			assertThatIllegalStateException().isThrownBy(emptyIterator::remove);
		}
	}

	@Test
	void testNullToEmptyOfInt() {
		assertThat(Iterables.nullToEmpty((PrimitiveIterable.OfInt) null)).isEmpty();
		assertThat(Iterables.nullToEmpty(Iterables.EMPTY_INT)).isEmpty();
		assertThat(Iterables.nullToEmpty(Iterables.ofInts(INT_ELEMENTS))).containsExactly(IntArrays.toBoxed(INT_ELEMENTS));
	}

	@Test
	void testNullToEmptyOfLong() {
		assertThat(Iterables.nullToEmpty((PrimitiveIterable.OfLong) null)).isEmpty();
		assertThat(Iterables.nullToEmpty(Iterables.EMPTY_LONG)).isEmpty();
		assertThat(Iterables.nullToEmpty(Iterables.ofLongs(LONG_ELEMENTS))).containsExactly(LongArrays.toBoxed(LONG_ELEMENTS));
	}

	@Test
	void testNullToEmptyOfDouble() {
		assertThat(Iterables.nullToEmpty((PrimitiveIterable.OfDouble) null)).isEmpty();
		assertThat(Iterables.nullToEmpty(Iterables.EMPTY_DOUBLE)).isEmpty();
		assertThat(Iterables.nullToEmpty(Iterables.ofDoubles(DOUBLE_ELEMENTS))).containsExactly(DoubleArrays.toBoxed(DOUBLE_ELEMENTS));
	}

	@Test
	void testNullToEmpty() {
		assertThat(Iterables.nullToEmpty((Iterable<Integer>) null)).isEmpty();
		assertThat(Iterables.nullToEmpty(Iterables.empty())).isEmpty();
		assertThat(Iterables.nullToEmpty(Iterables.of(ELEMENTS))).containsExactly(ELEMENTS);
	}

	@Test
	void testNullToDefault() {
		assertThat(Iterables.nullToDefault(null, Iterables.singleton(0))).containsExactly(0);
		assertThat(Iterables.nullToDefault(Iterables.empty(), Iterables.singleton(0))).isEmpty();
		assertThat(Iterables.nullToDefault(Iterables.of(ELEMENTS), Iterables.singleton(0))).containsExactly(ELEMENTS);
	}

	@Test
	void testNullToDefaultInvalid() {
		assertThatNullPointerException().isThrownBy(() -> Iterables.nullToDefault(Iterables.of(ELEMENTS), null));
	}

	@Test
	void testUnmodifiable() {
		assertThat(Iterables.unmodifiable(Iterables.empty())).isEmpty();
		final var list = new ArrayList<>(List.of(ELEMENTS));
		final var iterator = list.iterator();
		iterator.next();
		iterator.remove();
		assertThat(list).containsExactly(ELEMENTS[1], ELEMENTS[2]);
		final var unmodifiableIterable = Iterables.unmodifiable(list);
		for (var i = 0; 2 > i; ++i) {
			final var unmodifiableIterator = unmodifiableIterable.iterator();
			while (unmodifiableIterator.hasNext()) {
				unmodifiableIterator.next();
				assertThatExceptionOfType(UnsupportedOperationException.class).isThrownBy(unmodifiableIterator::remove);
			}
			assertThatExceptionOfType(NoSuchElementException.class).isThrownBy(unmodifiableIterator::next);
			assertThat(list).containsExactly(ELEMENTS[1], ELEMENTS[2]);
		}
	}

	@Test
	void testUnmodifiableInvalid() {
		assertThatNullPointerException().isThrownBy(() -> Iterables.unmodifiable(null));
	}

	@Test
	void testIndex() {
		assertThat(Iterables.index(Iterables.empty())).isEmpty();
		final var list = new ArrayList<>(List.of(ELEMENTS));
		final var indexIterable = Iterables.index(list);
		for (var i = 0; 2 > i; ++i) {
			final var indexIterator = indexIterable.iterator();
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
	}

	@Test
	void testIndexInvalid() {
		assertThatNullPointerException().isThrownBy(() -> Iterables.index(null));
	}

	@Test
	void testFilter() {
		assertThat(Iterables.filter(Iterables.empty(), element -> true)).isEmpty();
		final var list = new ArrayList<>(List.of(ELEMENTS));
		final var filterIterable = Iterables.filter(list, element -> ELEMENTS[0].equals(element));
		for (var i = 0; 2 > i; ++i) {
			final var filterIterator = filterIterable.iterator();
			while (filterIterator.hasNext()) {
				assertThat(ELEMENTS[0]).isEqualTo(filterIterator.next());
				filterIterator.remove();
			}
			assertThatExceptionOfType(NoSuchElementException.class).isThrownBy(filterIterator::next);
			assertThat(list).containsExactly(ELEMENTS[1], ELEMENTS[2]);
		}
	}

	@Test
	void testFilterInvalid() {
		assertThatNullPointerException().isThrownBy(() -> Iterables.filter(null, element -> true));
		assertThatNullPointerException().isThrownBy(() -> Iterables.filter(Iterables.of(ELEMENTS), null));
	}

	@Test
	void testMap() {
		assertThat(Iterables.map(Iterables.empty(), Function.identity())).isEmpty();
		final var list = new ArrayList<>(List.of(ELEMENTS));
		final var mapIterable = Iterables.map(list, element -> -element);
		for (var i = 0; 2 > i; ++i) {
			final var mapIterator = mapIterable.iterator();
			while (mapIterator.hasNext()) {
				assertThat(mapIterator.next()).isNegative();
				mapIterator.remove();
			}
			assertThatExceptionOfType(NoSuchElementException.class).isThrownBy(mapIterator::next);
			assertThat(list).isEmpty();
		}
	}

	@Test
	void testMapInvalid() {
		assertThatNullPointerException().isThrownBy(() -> Iterables.map(null, Function.identity()));
		assertThatNullPointerException().isThrownBy(() -> Iterables.map(Iterables.of(ELEMENTS), null));
	}

	@Test
	void testConcat() {
		assertThat(Iterables.concat()).isEmpty();
		assertThat(Iterables.concat(Iterables.singleton(ELEMENTS[0]))).containsExactly(ELEMENTS[0]);
		assertThat(Iterables.concat(Iterables.singleton(ELEMENTS[0]), Iterables.singleton(ELEMENTS[1]), Iterables.singleton(ELEMENTS[2]))).containsExactly(ELEMENTS);
		assertThat(Iterables.concat(List.of(Iterables.singleton(ELEMENTS[0]), Iterables.singleton(ELEMENTS[1]), Iterables.singleton(ELEMENTS[2])))).containsExactly(ELEMENTS);
	}

	@Test
	void testConcatInvalid() {
		assertThatNullPointerException().isThrownBy(() -> Iterables.concat((Iterable<Integer>[]) null));
		assertThatNullPointerException().isThrownBy(() -> Iterables.concat((Iterable<Integer>) null));
		assertThatNullPointerException().isThrownBy(() -> Iterables.concat((List<Iterable<Integer>>) null));
		assertThatNullPointerException().isThrownBy(() -> Iterables.concat(Collections.singletonList(null)));
	}

	@Test
	void testJoin() {
		assertThat(Iterables.join(ObjectArrays.empty(Integer.class), Iterables.singleton(ELEMENTS[0]), Iterables.singleton(ELEMENTS[1]), Iterables.singleton(ELEMENTS[2]))).containsExactly(ELEMENTS);
		assertThat(Iterables.join(ObjectArrays.singleton(0))).isEmpty();
		assertThat(Iterables.join(ObjectArrays.singleton(0), Iterables.singleton(ELEMENTS[0]))).containsExactly(ELEMENTS[0]);
		assertThat(Iterables.join(ObjectArrays.singleton(0), Iterables.singleton(ELEMENTS[0]), Iterables.singleton(ELEMENTS[1]), Iterables.singleton(ELEMENTS[2]))).containsExactly(ELEMENTS[0], 0, ELEMENTS[1], 0, ELEMENTS[2]);
		assertThat(Iterables.join(ObjectArrays.singleton(0), List.of(Iterables.singleton(ELEMENTS[0]), Iterables.singleton(ELEMENTS[1]), Iterables.singleton(ELEMENTS[2])))).containsExactly(ELEMENTS[0], 0, ELEMENTS[1], 0, ELEMENTS[2]);
	}

	@Test
	void testJoinInvalid() {
		assertThatNullPointerException().isThrownBy(() -> Iterables.join(null, Iterables.of(ELEMENTS)));
		assertThatNullPointerException().isThrownBy(() -> Iterables.join(ObjectArrays.singleton(0), (Iterable<Integer>[]) null));
		assertThatNullPointerException().isThrownBy(() -> Iterables.join(ObjectArrays.singleton(0), (Iterable<Integer>) null));
		assertThatNullPointerException().isThrownBy(() -> Iterables.join(ObjectArrays.singleton(0), (List<Iterable<Integer>>) null));
		assertThatNullPointerException().isThrownBy(() -> Iterables.join(ObjectArrays.singleton(0), Collections.singletonList(null)));
	}

	@Test
	void testLength() {
		assertThat(Iterables.length(List.of())).isZero();
		assertThat(Iterables.length(List.of(ELEMENTS[0]))).isEqualTo(1L);
		assertThat(Iterables.length(List.of(ELEMENTS))).isEqualTo(3L);
		assertThat(Iterables.length(Iterables.empty())).isZero();
		assertThat(Iterables.length(Iterables.singleton(ELEMENTS[0]))).isEqualTo(1L);
		final var iterable = Iterables.of(ELEMENTS);
		for (var i = 0; 2 > i; ++i) {
			assertThat(Iterables.length(iterable)).isEqualTo(3L);
		}
	}

	@Test
	void testLengthInvalid() {
		assertThatNullPointerException().isThrownBy(() -> Iterables.length(null));
	}

	@Test
	void testTransferTo() {
		final var list = new ArrayList<>();
		assertThat(Iterables.transferTo(List.of(), list)).isZero();
		assertThat(list).isEmpty();
		assertThat(Iterables.transferTo(List.of(ELEMENTS[0]), list)).isEqualTo(1L);
		assertThat(list).containsExactly(ELEMENTS[0]);
		assertThat(Iterables.transferTo(List.of(ELEMENTS), list)).isEqualTo(3L);
		assertThat(list).containsExactly(ELEMENTS[0], ELEMENTS[0], ELEMENTS[1], ELEMENTS[2]);
		list.clear();
		assertThat(Iterables.transferTo(Iterables.empty(), list)).isZero();
		assertThat(list).isEmpty();
		assertThat(Iterables.transferTo(Iterables.singleton(ELEMENTS[0]), list)).isEqualTo(1L);
		assertThat(list).containsExactly(ELEMENTS[0]);
		final var iterable = Iterables.of(ELEMENTS);
		assertThat(Iterables.transferTo(iterable, list)).isEqualTo(3L);
		assertThat(list).containsExactly(ELEMENTS[0], ELEMENTS[0], ELEMENTS[1], ELEMENTS[2]);
		assertThat(Iterables.transferTo(iterable, list)).isEqualTo(3L);
		assertThat(list).containsExactly(ELEMENTS[0], ELEMENTS[0], ELEMENTS[1], ELEMENTS[2], ELEMENTS[0], ELEMENTS[1], ELEMENTS[2]);
	}

	@Test
	void testTransferToInvalid() {
		assertThatNullPointerException().isThrownBy(() -> Iterables.transferTo(null, new ArrayList<>()));
		assertThatNullPointerException().isThrownBy(() -> Iterables.transferTo(Iterables.of(ELEMENTS), null));
	}

	@Test
	void testGetOptionalFirst() {
		assertThat(Iterables.getOptionalFirst(List.of()).isEmpty()).isTrue();
		assertThat(Iterables.getOptionalFirst(List.of(ELEMENTS[0])).get()).isEqualTo(ELEMENTS[0]);
		assertThat(Iterables.getOptionalFirst(List.of(ELEMENTS)).get()).isEqualTo(ELEMENTS[0]);
		assertThat(Iterables.getOptionalFirst(Iterables.empty()).isEmpty()).isTrue();
		assertThat(Iterables.getOptionalFirst(Iterables.singleton(ELEMENTS[0])).get()).isEqualTo(ELEMENTS[0]);
		assertThat(Iterables.getOptionalFirst(Iterables.singleton(null)).get()).isNull();
		final var iterable = Iterables.of(ELEMENTS);
		for (var i = 0; 2 > i; ++i) {
			assertThat(Iterables.getOptionalFirst(iterable).get()).isEqualTo(ELEMENTS[0]);
		}
	}

	@Test
	void testGetOptionalFirstInvalid() {
		assertThatNullPointerException().isThrownBy(() -> Iterables.getOptionalFirst(null));
	}

	@Test
	void testGetOptionalLast() {
		assertThat(Iterables.getOptionalLast(List.of()).isEmpty()).isTrue();
		assertThat(Iterables.getOptionalLast(List.of(ELEMENTS[0])).get()).isEqualTo(ELEMENTS[0]);
		assertThat(Iterables.getOptionalLast(List.of(ELEMENTS)).get()).isEqualTo(ELEMENTS[2]);
		assertThat(Iterables.getOptionalLast(Iterables.empty()).isEmpty()).isTrue();
		assertThat(Iterables.getOptionalLast(Iterables.singleton(ELEMENTS[0])).get()).isEqualTo(ELEMENTS[0]);
		assertThat(Iterables.getOptionalLast(Iterables.singleton(null)).get()).isNull();
		final var iterable = Iterables.of(ELEMENTS);
		for (var i = 0; 2 > i; ++i) {
			assertThat(Iterables.getOptionalLast(iterable).get()).isEqualTo(ELEMENTS[2]);
		}
	}

	@Test
	void testGetOptionalLastInvalid() {
		assertThatNullPointerException().isThrownBy(() -> Iterables.getOptionalLast(null));
	}

	@Test
	void testSingletonInt() {
		final var iterable = Iterables.singleton(INT_ELEMENTS[0]);
		for (var i = 0; 2 > i; ++i) {
			assertThat(iterable).containsExactly(INT_ELEMENTS[0]);
		}
	}

	@Test
	void testSingletonLong() {
		final var iterable = Iterables.singleton(LONG_ELEMENTS[0]);
		for (var i = 0; 2 > i; ++i) {
			assertThat(iterable).containsExactly(LONG_ELEMENTS[0]);
		}
	}

	@Test
	void testSingletonDouble() {
		final var iterable = Iterables.singleton(DOUBLE_ELEMENTS[0]);
		for (var i = 0; 2 > i; ++i) {
			assertThat(iterable).containsExactly(DOUBLE_ELEMENTS[0]);
		}
	}

	@Test
	void testSingleton() {
		final var iterable = Iterables.singleton(ELEMENTS[0]);
		for (var i = 0; 2 > i; ++i) {
			assertThat(iterable).containsExactly(ELEMENTS[0]);
		}
	}

	@Test
	void testOfInts() {
		assertThat(Iterables.ofInts()).isEmpty();
		final var iterable = Iterables.ofInts(INT_ELEMENTS);
		for (var i = 0; 2 > i; ++i) {
			assertThat(iterable.iterator()).toIterable().containsExactly(IntArrays.toBoxed(INT_ELEMENTS));
		}
	}

	@Test
	void testOfIntsInvalid() {
		assertThatNullPointerException().isThrownBy(() -> Iterables.ofInts((int[]) null));
	}

	@Test
	void testOfLongs() {
		assertThat(Iterables.ofLongs()).isEmpty();
		final var iterable = Iterables.ofLongs(LONG_ELEMENTS);
		for (var i = 0; 2 > i; ++i) {
			assertThat(iterable.iterator()).toIterable().containsExactly(LongArrays.toBoxed(LONG_ELEMENTS));
		}
	}

	@Test
	void testOfLongsInvalid() {
		assertThatNullPointerException().isThrownBy(() -> Iterables.ofLongs((long[]) null));
	}

	@Test
	void testOfDoubles() {
		assertThat(Iterables.ofDoubles()).isEmpty();
		final var iterable = Iterables.ofDoubles(DOUBLE_ELEMENTS);
		for (var i = 0; 2 > i; ++i) {
			assertThat(iterable.iterator()).toIterable().containsExactly(DoubleArrays.toBoxed(DOUBLE_ELEMENTS));
		}
	}

	@Test
	void testOfDoublesInvalid() {
		assertThatNullPointerException().isThrownBy(() -> Iterables.ofDoubles((double[]) null));
	}

	@Test
	void testOf() {
		assertThat(Iterables.of()).isEmpty();
		final var iterable = Iterables.of(ELEMENTS);
		for (var i = 0; 2 > i; ++i) {
			assertThat(iterable.iterator()).toIterable().containsExactly(ELEMENTS);
		}
	}

	@Test
	void testOfInvalid() {
		assertThatNullPointerException().isThrownBy(() -> Iterables.of((Integer[]) null));
	}

	@Test
	void testToSet() {
		assertThat(Iterables.toSet(Set.of())).isEmpty();
		assertThat(Iterables.toSet(Set.of(ELEMENTS[0]))).containsExactlyInAnyOrder(ELEMENTS[0]);
		assertThat(Iterables.toSet(Set.of(ELEMENTS))).containsExactlyInAnyOrder(ELEMENTS);
		assertThat(Iterables.toSet(Iterables.empty())).isEmpty();
		assertThat(Iterables.toSet(Iterables.singleton(ELEMENTS[0]))).containsExactlyInAnyOrder(ELEMENTS[0]);
		assertThat(Iterables.toSet(Iterables.of(ELEMENTS))).containsExactlyInAnyOrder(ELEMENTS);
	}

	@Test
	void testToSetInvalid() {
		assertThatNullPointerException().isThrownBy(() -> Iterables.toSet(null));
	}

	@Test
	void testToList() {
		assertThat(Iterables.toList(List.of())).isEmpty();
		assertThat(Iterables.toList(List.of(ELEMENTS[0]))).containsExactly(ELEMENTS[0]);
		assertThat(Iterables.toList(List.of(ELEMENTS))).containsExactly(ELEMENTS);
		assertThat(Iterables.toList(Iterables.empty())).isEmpty();
		assertThat(Iterables.toList(Iterables.singleton(ELEMENTS[0]))).containsExactly(ELEMENTS[0]);
		assertThat(Iterables.toList(Iterables.of(ELEMENTS))).containsExactly(ELEMENTS);
	}

	@Test
	void testToListInvalid() {
		assertThatNullPointerException().isThrownBy(() -> Iterables.toList(null));
	}

	@Test
	void testWrapInputStream() {
		final var iterable = Iterables.wrap(InputStreams.of((byte) 1, (byte) 2, (byte) 3));
		assertThat(iterable.iterator()).toIterable().containsExactly(1, 2, 3);
		assertThatExceptionOfType(IllegalStateException.class).isThrownBy(iterable::iterator);
	}

	@Test
	void testWrapInputStreamInvalid() {
		assertThatNullPointerException().isThrownBy(() -> Iterables.wrap((InputStream) null));
	}

	@Test
	void testWrapReader() {
		final var iterable = Iterables.wrap(Readers.of('a', 'b', 'c'));
		assertThat(iterable.iterator()).toIterable().containsExactly((int) 'a', (int) 'b', (int) 'c');
		assertThatExceptionOfType(IllegalStateException.class).isThrownBy(iterable::iterator);
	}

	@Test
	void testWrapReaderInvalid() {
		assertThatNullPointerException().isThrownBy(() -> Iterables.wrap((Reader) null));
	}

	@Test
	void testWrapBufferedReader() {
		final var iterable = Iterables.wrap(new BufferedReader(new StringReader(String.join("\n", "abc", "def", "ghi"))));
		assertThat(iterable.iterator()).toIterable().containsExactly("abc", "def", "ghi");
		assertThatExceptionOfType(IllegalStateException.class).isThrownBy(iterable::iterator);
	}

	@Test
	void testWrapBufferedReaderInvalid() {
		assertThatNullPointerException().isThrownBy(() -> Iterables.wrap((BufferedReader) null));
	}

	@Test
	void testWrapLineReader() {
		final var iterable = Iterables.wrap(new LineReader(new StringReader(String.join("\n", "abc", "def", "ghi")), LineSeparator.DEFAULT));
		assertThat(iterable.iterator()).toIterable().containsExactly("abc", "def", "ghi");
		assertThatExceptionOfType(IllegalStateException.class).isThrownBy(iterable::iterator);
	}

	@Test
	void testWrapLineReaderInvalid() {
		assertThatNullPointerException().isThrownBy(() -> Iterables.wrap((LineReader) null));
	}

	@Test
	void testWrapStream() {
		final var iterable = Iterables.wrap(Stream.of(ELEMENTS));
		assertThat(iterable.iterator()).toIterable().containsExactly(ELEMENTS);
		assertThatExceptionOfType(IllegalStateException.class).isThrownBy(iterable::iterator);
	}

	@Test
	void testWrapStreamInvalid() {
		assertThatNullPointerException().isThrownBy(() -> Iterables.wrap((Stream<Integer>) null));
	}

	@Test
	void testWrapPrimitiveIteratorOfInt() {
		final var iterable = Iterables.wrap(Iterables.ofInts(INT_ELEMENTS).iterator());
		assertThat(iterable.iterator()).toIterable().containsExactly(IntArrays.toBoxed(INT_ELEMENTS));
		assertThatExceptionOfType(IllegalStateException.class).isThrownBy(iterable::iterator);
	}

	@Test
	void testWrapPrimitiveIteratorOfIntInvalid() {
		assertThatNullPointerException().isThrownBy(() -> Iterables.wrap((PrimitiveIterator.OfInt) null));
	}

	@Test
	void testWrapPrimitiveIteratorOfLong() {
		final var iterable = Iterables.wrap(Iterables.ofLongs(LONG_ELEMENTS).iterator());
		assertThat(iterable.iterator()).toIterable().containsExactly(LongArrays.toBoxed(LONG_ELEMENTS));
		assertThatExceptionOfType(IllegalStateException.class).isThrownBy(iterable::iterator);
	}

	@Test
	void testWrapPrimitiveIteratorOfLongInvalid() {
		assertThatNullPointerException().isThrownBy(() -> Iterables.wrap((PrimitiveIterator.OfLong) null));
	}

	@Test
	void testWrapPrimitiveIteratorOfDouble() {
		final var iterable = Iterables.wrap(Iterables.ofDoubles(DOUBLE_ELEMENTS).iterator());
		assertThat(iterable.iterator()).toIterable().containsExactly(DoubleArrays.toBoxed(DOUBLE_ELEMENTS));
		assertThatExceptionOfType(IllegalStateException.class).isThrownBy(iterable::iterator);
	}

	@Test
	void testWrapPrimitiveIteratorOfDoubleInvalid() {
		assertThatNullPointerException().isThrownBy(() -> Iterables.wrap((PrimitiveIterator.OfDouble) null));
	}

	@Test
	void testWrapIterator() {
		final var iterable = Iterables.wrap(Iterables.of(ELEMENTS).iterator());
		assertThat(iterable.iterator()).toIterable().containsExactly(ELEMENTS);
		assertThatExceptionOfType(IllegalStateException.class).isThrownBy(iterable::iterator);
	}

	@Test
	void testWrapIteratorInvalid() {
		assertThatNullPointerException().isThrownBy(() -> Iterables.wrap((Iterator<Integer>) null));
	}
}