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
import java.io.InputStream;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.PrimitiveIterator;
import java.util.function.Function;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.assertj.core.api.Assertions.assertThatIllegalStateException;
import static org.assertj.core.api.Assertions.assertThatNullPointerException;

/**
 * <p>{@link Iterables} unit tests.</p>
 */
final class IterablesTest {

	@Test
	void testEmptyInt() {
		final var emptyIterable = Iterables.EMPTY_INT;
		for (var i = 0; i < 2; ++i) {
			final var emptyIterator = emptyIterable.iterator();
			assertThat(emptyIterator.hasNext()).isFalse();
			assertThatExceptionOfType(NoSuchElementException.class).isThrownBy(emptyIterator::nextInt);
			assertThatIllegalStateException().isThrownBy(emptyIterator::remove);
		}
	}

	@Test
	void testEmptyLong() {
		final var emptyIterable = Iterables.EMPTY_LONG;
		for (var i = 0; i < 2; ++i) {
			final var emptyIterator = emptyIterable.iterator();
			assertThat(emptyIterator.hasNext()).isFalse();
			assertThatExceptionOfType(NoSuchElementException.class).isThrownBy(emptyIterator::nextLong);
			assertThatIllegalStateException().isThrownBy(emptyIterator::remove);
		}
	}

	@Test
	void testEmptyDouble() {
		final var emptyIterable = Iterables.EMPTY_DOUBLE;
		for (var i = 0; i < 2; ++i) {
			final var emptyIterator = emptyIterable.iterator();
			assertThat(emptyIterator.hasNext()).isFalse();
			assertThatExceptionOfType(NoSuchElementException.class).isThrownBy(emptyIterator::nextDouble);
			assertThatIllegalStateException().isThrownBy(emptyIterator::remove);
		}
	}

	@Test
	void testEmpty() {
		final var emptyIterable = Iterables.empty();
		for (var i = 0; i < 2; ++i) {
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
		assertThat(Iterables.nullToEmpty(Iterables.singletonInt(1))).containsExactly(1);
	}

	@Test
	void testNullToEmptyOfLong() {
		assertThat(Iterables.nullToEmpty((PrimitiveIterable.OfLong) null)).isEmpty();
		assertThat(Iterables.nullToEmpty(Iterables.EMPTY_LONG)).isEmpty();
		assertThat(Iterables.nullToEmpty(Iterables.singletonLong(1L))).containsExactly(1L);
	}

	@Test
	void testNullToEmptyOfDouble() {
		assertThat(Iterables.nullToEmpty((PrimitiveIterable.OfDouble) null)).isEmpty();
		assertThat(Iterables.nullToEmpty(Iterables.EMPTY_DOUBLE)).isEmpty();
		assertThat(Iterables.nullToEmpty(Iterables.singletonDouble(1.0d))).containsExactly(1.0d);
	}

	@Test
	void testNullToEmpty() {
		assertThat(Iterables.nullToEmpty((Iterable<?>) null)).isEmpty();
		assertThat(Iterables.nullToEmpty(Iterables.empty())).isEmpty();
		assertThat(Iterables.nullToEmpty(Iterables.singleton(1))).containsExactly(1);
	}

	@Test
	void testNullToDefault() {
		assertThat(Iterables.nullToDefault(null, Iterables.singleton(0))).containsExactly(0);
		assertThat(Iterables.nullToDefault(Iterables.empty(), Iterables.singleton(0))).isEmpty();
		assertThat(Iterables.nullToDefault(Iterables.singleton(1), Iterables.singleton(0))).containsExactly(1);
	}

	@Test
	void testNullToDefaultInvalid() {
		assertThatNullPointerException().isThrownBy(() -> Iterables.nullToDefault(Iterables.singleton(1), null));
	}

	@Test
	void testUnmodifiable() {
		assertThat(Iterables.unmodifiable(Iterables.empty())).isEmpty();
		final var list = new ArrayList<>(List.of(1, 2, 3));
		final var iterator = list.iterator();
		iterator.next();
		iterator.remove();
		assertThat(list).containsExactly(2, 3);
		final var unmodifiableIterable = Iterables.unmodifiable(list);
		for (var i = 0; i < 2; ++i) {
			final var unmodifiableIterator = unmodifiableIterable.iterator();
			while (unmodifiableIterator.hasNext()) {
				unmodifiableIterator.next();
				assertThatExceptionOfType(UnsupportedOperationException.class).isThrownBy(unmodifiableIterator::remove);
			}
			assertThatExceptionOfType(NoSuchElementException.class).isThrownBy(unmodifiableIterator::next);
			assertThat(list).containsExactly(2, 3);
		}
	}

	@Test
	void testUnmodifiableInvalid() {
		assertThatNullPointerException().isThrownBy(() -> Iterables.unmodifiable(null));
	}

	@Test
	void testFilter() {
		assertThat(Iterables.filter(Iterables.empty(), element -> true)).isEmpty();
		final var list = new ArrayList<>(List.of(1, 2, 3));
		final var filterIterable = Iterables.filter(list, i -> 0 == i % 2);
		for (var i = 0; i < 2; ++i) {
			final var filterIterator = filterIterable.iterator();
			while (filterIterator.hasNext()) {
				assertThat(filterIterator.next() % 2).isEqualTo(0);
				filterIterator.remove();
			}
			assertThatExceptionOfType(NoSuchElementException.class).isThrownBy(filterIterator::next);
			assertThat(list).containsExactly(1, 3);
		}
	}

	@Test
	void testFilterInvalid() {
		assertThatNullPointerException().isThrownBy(() -> Iterables.filter(null, element -> true));
		assertThatNullPointerException().isThrownBy(() -> Iterables.filter(Iterables.singleton(1), null));
	}

	@Test
	void testMap() {
		assertThat(Iterables.map(Iterables.empty(), Function.identity())).isEmpty();
		final var list = new ArrayList<>(List.of(1, 2, 3));
		final var mapIterable = Iterables.map(list, i -> -i);
		for (var i = 0; i < 2; ++i) {
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
		assertThatNullPointerException().isThrownBy(() -> Iterables.map(Iterables.singleton(1), null));
	}

	@Test
	void testIndex() {
		assertThat(Iterables.index(Iterables.empty())).isEmpty();
		final var list = new ArrayList<>(List.of(1, 2, 3));
		final var indexedIterable = Iterables.index(list);
		for (var i = 0; i < 2; ++i) {
			final var indexedIterator = indexedIterable.iterator();
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
	}

	@Test
	void testIndexInvalid() {
		assertThatNullPointerException().isThrownBy(() -> Iterables.index(null));
	}

	@Test
	void testLength() {
		assertThat(Iterables.length(Collections.emptySet())).isEqualTo(0L);
		assertThat(Iterables.length(Iterables.singleton(1))).isEqualTo(1L);
		final var iterable = Iterables.of(1, 2, 3);
		for (var i = 0; i < 2; ++i) {
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
		assertThat(Iterables.transferTo(Collections.singletonList(0), list)).isEqualTo(1L);
		assertThat(list).containsExactly(0);
		final var iterable = Iterables.of(1, 2, 3);
		assertThat(Iterables.transferTo(iterable, list)).isEqualTo(3L);
		assertThat(list).containsExactly(0, 1, 2, 3);
		assertThat(Iterables.transferTo(iterable, list)).isEqualTo(3L);
		assertThat(list).containsExactly(0, 1, 2, 3, 1, 2, 3);
	}

	@Test
	void testTransferToInvalid() {
		assertThatNullPointerException().isThrownBy(() -> Iterables.transferTo(null, new ArrayList<>()));
		assertThatNullPointerException().isThrownBy(() -> Iterables.transferTo(Iterables.singleton(1), null));
	}

	@Test
	void testGetOptionalFirst() {
		assertThat(Iterables.getOptionalFirst(Iterables.empty()).isEmpty()).isTrue();
		assertThat(Iterables.getOptionalFirst(Collections.singletonList(0)).get()).isEqualTo(0);
		assertThat(Iterables.getOptionalFirst(Iterables.singleton(null)).get()).isNull();
		final var iterable = Iterables.of(1, 2, 3);
		for (var i = 0; i < 2; ++i) {
			assertThat(Iterables.getOptionalFirst(iterable).get()).isEqualTo(1);
		}
	}

	@Test
	void testGetOptionalFirstInvalid() {
		assertThatNullPointerException().isThrownBy(() -> Iterables.getOptionalFirst(null));
	}

	@Test
	void testGetOptionalLast() {
		assertThat(Iterables.getOptionalLast(Iterables.empty()).isEmpty()).isTrue();
		assertThat(Iterables.getOptionalLast(Collections.singletonList(0)).get()).isEqualTo(0);
		assertThat(Iterables.getOptionalLast(Iterables.singleton(null)).get()).isNull();
		final var iterable = Iterables.of(1, 2, 3);
		for (var i = 0; i < 2; ++i) {
			assertThat(Iterables.getOptionalLast(iterable).get()).isEqualTo(3);
		}
	}

	@Test
	void testGetOptionalLastInvalid() {
		assertThatNullPointerException().isThrownBy(() -> Iterables.getOptionalLast(null));
	}

	@Test
	void testConcat() {
		assertThat(Iterables.concat()).isEmpty();
		assertThat(Iterables.concat(Iterables.singleton(1))).containsExactly(1);
		assertThat(Iterables.concat(Iterables.singleton(1), Iterables.singleton(2))).containsExactly(1, 2);
	}

	@Test
	void testConcatInvalid() {
		assertThatNullPointerException().isThrownBy(() -> Iterables.concat((Iterable<?>[]) null));
		assertThatNullPointerException().isThrownBy(() -> Iterables.concat((List<Iterable<?>>) null));
		assertThatNullPointerException().isThrownBy(() -> Iterables.concat((Iterable<?>) null));
	}

	@Test
	void testJoin() {
		assertThat(Iterables.join(ObjectArrays.empty(Integer.class), Iterables.singleton(1), Iterables.singleton(2))).containsExactly(1, 2);
		assertThat(Iterables.join(ObjectArrays.singleton(0))).isEmpty();
		assertThat(Iterables.join(ObjectArrays.singleton(0), Iterables.singleton(1))).containsExactly(1);
		assertThat(Iterables.join(ObjectArrays.singleton(0), Iterables.singleton(1), Iterables.singleton(2))).containsExactly(1, 0, 2);
	}

	@Test
	void testJoinInvalid() {
		assertThatNullPointerException().isThrownBy(() -> Iterables.join(null, Iterables.singleton(1)));
		assertThatNullPointerException().isThrownBy(() -> Iterables.join(ObjectArrays.singleton(0), (Iterable<?>[]) null));
		assertThatNullPointerException().isThrownBy(() -> Iterables.join(ObjectArrays.singleton(0), (List<Iterable<?>>) null));
		assertThatNullPointerException().isThrownBy(() -> Iterables.join(ObjectArrays.singleton(0), (Iterable<?>) null));
	}

	@Test
	void testSingletonInt() {
		final var iterable = Iterables.singletonInt(1);
		for (var i = 0; i < 2; ++i) {
			assertThat(iterable).containsExactly(1);
		}
	}

	@Test
	void testSingletonLong() {
		final var iterable = Iterables.singletonLong(1L);
		for (var i = 0; i < 2; ++i) {
			assertThat(iterable).containsExactly(1L);
		}
	}

	@Test
	void testSingletonDouble() {
		final var iterable = Iterables.singletonDouble(1.0d);
		for (var i = 0; i < 2; ++i) {
			assertThat(iterable).containsExactly(1.0d);
		}
	}

	@Test
	void testSingleton() {
		final var iterable = Iterables.singleton(1);
		for (var i = 0; i < 2; ++i) {
			assertThat(iterable).containsExactly(1);
		}
	}

	@Test
	void testOfInt() {
		assertThat(Iterables.ofInt()).isEmpty();
		final var iterable = Iterables.ofInt(1, 2, 3);
		for (var i = 0; i < 2; ++i) {
			assertThat(iterable.iterator()).containsExactly(1, 2, 3);
		}
	}

	@Test
	void testOfIntInvalid() {
		assertThatNullPointerException().isThrownBy(() -> Iterables.ofInt((int[]) null));
	}

	@Test
	void testOfLong() {
		assertThat(Iterables.ofLong()).isEmpty();
		final var iterable = Iterables.ofLong(1L, 2L, 3L);
		for (var i = 0; i < 2; ++i) {
			assertThat(iterable.iterator()).containsExactly(1L, 2L, 3L);
		}
	}

	@Test
	void testOfLongInvalid() {
		assertThatNullPointerException().isThrownBy(() -> Iterables.ofLong((long[]) null));
	}

	@Test
	void testOfDouble() {
		assertThat(Iterables.ofDouble()).isEmpty();
		final var iterable = Iterables.ofDouble(1.0d, 2.0d, 3.0d);
		for (var i = 0; i < 2; ++i) {
			assertThat(iterable.iterator()).containsExactly(1.0d, 2.0d, 3.0d);
		}
	}

	@Test
	void testOfDoubleInvalid() {
		assertThatNullPointerException().isThrownBy(() -> Iterables.ofDouble((double[]) null));
	}

	@Test
	void testOf() {
		assertThat(Iterables.of()).isEmpty();
		final var iterable = Iterables.of(1, null, 2);
		for (var i = 0; i < 2; ++i) {
			assertThat(iterable.iterator()).containsExactly(1, null, 2);
		}
	}

	@Test
	void testOfInvalid() {
		assertThatNullPointerException().isThrownBy(() -> Iterables.of((Integer[]) null));
	}

	@Test
	void testToSet() {
		assertThat(Iterables.toSet(Iterables.empty())).isEmpty();
		assertThat(Iterables.toSet(Collections.singleton(1))).containsExactly(1);
		assertThat(Iterables.toSet(Iterables.of(1, 2, 2, 3))).containsExactlyInAnyOrder(1, 2, 3);
	}

	@Test
	void testToSetInvalid() {
		assertThatNullPointerException().isThrownBy(() -> Iterables.toSet(null));
	}

	@Test
	void testToList() {
		assertThat(Iterables.toList(Iterables.empty())).isEmpty();
		assertThat(Iterables.toList(Collections.singletonList(1))).containsExactly(1);
		assertThat(Iterables.toList(Iterables.of(1, 2, 2, 3))).containsExactly(1, 2, 2, 3);
	}

	@Test
	void testToListInvalid() {
		assertThatNullPointerException().isThrownBy(() -> Iterables.toList(null));
	}

	@Test
	void testWrapInputStream() {
		final var iterable = Iterables.wrap(InputStreams.of((byte) 1, (byte) 2, (byte) 3));
		assertThat(iterable.iterator()).containsExactly(1, 2, 3);
		assertThatExceptionOfType(IllegalStateException.class).isThrownBy(iterable::iterator);
	}

	@Test
	void testWrapInputStreamInvalid() {
		assertThatNullPointerException().isThrownBy(() -> Iterables.wrap((InputStream) null));
	}

	@Test
	void testWrapReader() {
		final var iterable = Iterables.wrap(Readers.of('a', 'b', 'c'));
		assertThat(iterable.iterator()).containsExactly((int) 'a', (int) 'b', (int) 'c');
		assertThatExceptionOfType(IllegalStateException.class).isThrownBy(iterable::iterator);
	}

	@Test
	void testWrapReaderInvalid() {
		assertThatNullPointerException().isThrownBy(() -> Iterables.wrap((Reader) null));
	}

	@Test
	void testWrapBufferedReader() {
		final var iterable = Iterables.wrap(new BufferedReader(new StringReader("abc\ndef\nghi")));
		assertThat(iterable.iterator()).containsExactly("abc", "def", "ghi");
		assertThatExceptionOfType(IllegalStateException.class).isThrownBy(iterable::iterator);
	}

	@Test
	void testWrapBufferedReaderInvalid() {
		assertThatNullPointerException().isThrownBy(() -> Iterables.wrap((BufferedReader) null));
	}

	@Test
	void testWrapLineReader() {
		final var iterable = Iterables.wrap(new LineReader(new StringReader("abc\ndef\nghi"), LineSeparator.DEFAULT));
		assertThat(iterable.iterator()).containsExactly("abc", "def", "ghi");
		assertThatExceptionOfType(IllegalStateException.class).isThrownBy(iterable::iterator);
	}

	@Test
	void testWrapLineReaderInvalid() {
		assertThatNullPointerException().isThrownBy(() -> Iterables.wrap((LineReader) null));
	}

	@Test
	void testWrapStream() {
		final var iterable = Iterables.wrap(Stream.of(1, 2, 3));
		assertThat(iterable.iterator()).containsExactly(1, 2, 3);
		assertThatExceptionOfType(IllegalStateException.class).isThrownBy(iterable::iterator);
	}

	@Test
	void testWrapStreamInvalid() {
		assertThatNullPointerException().isThrownBy(() -> Iterables.wrap((Stream<?>) null));
	}

	@Test
	void testWrapPrimitiveIteratorOfInt() {
		final var iterable = Iterables.wrap(Iterables.ofInt(1, 2, 3).iterator());
		assertThat(iterable.iterator()).containsExactly(1, 2, 3);
		assertThatExceptionOfType(IllegalStateException.class).isThrownBy(iterable::iterator);
	}

	@Test
	void testWrapPrimitiveIteratorOfIntInvalid() {
		assertThatNullPointerException().isThrownBy(() -> Iterables.wrap((PrimitiveIterator.OfInt) null));
	}

	@Test
	void testWrapPrimitiveIteratorOfLong() {
		final var iterable = Iterables.wrap(Iterables.ofLong(1L, 2L, 3L).iterator());
		assertThat(iterable.iterator()).containsExactly(1L, 2L, 3L);
		assertThatExceptionOfType(IllegalStateException.class).isThrownBy(iterable::iterator);
	}

	@Test
	void testWrapPrimitiveIteratorOfLongInvalid() {
		assertThatNullPointerException().isThrownBy(() -> Iterables.wrap((PrimitiveIterator.OfLong) null));
	}

	@Test
	void testWrapPrimitiveIteratorOfDouble() {
		final var iterable = Iterables.wrap(Iterables.ofDouble(1.0d, 2.0d, 3.0d).iterator());
		assertThat(iterable.iterator()).containsExactly(1.0d, 2.0d, 3.0d);
		assertThatExceptionOfType(IllegalStateException.class).isThrownBy(iterable::iterator);
	}

	@Test
	void testWrapPrimitiveIteratorOfDoubleInvalid() {
		assertThatNullPointerException().isThrownBy(() -> Iterables.wrap((PrimitiveIterator.OfDouble) null));
	}

	@Test
	void testWrapIterator() {
		final var iterable = Iterables.wrap(Iterables.of(1, 2, 3).iterator());
		assertThat(iterable.iterator()).containsExactly(1, 2, 3);
		assertThatExceptionOfType(IllegalStateException.class).isThrownBy(iterable::iterator);
	}

	@Test
	void testWrapIteratorInvalid() {
		assertThatNullPointerException().isThrownBy(() -> Iterables.wrap((Iterator<?>) null));
	}
}