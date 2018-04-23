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
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.Reader;
import java.io.StringReader;
import java.util.*;
import java.util.function.Function;

import static org.assertj.core.api.Assertions.*;

/**
 * <p>{@link Iterables} unit tests.</p>
 */
final class IterablesTest {

	@Test
	void testEmptyInt() {
		assertThat(Iterables.EMPTY_INT.iterator().hasNext()).isFalse();
		assertThatExceptionOfType(NoSuchElementException.class).isThrownBy(() -> Iterables.EMPTY_INT.iterator().nextInt());
	}

	@Test
	void testEmptyLong() {
		assertThat(Iterables.EMPTY_LONG.iterator().hasNext()).isFalse();
		assertThatExceptionOfType(NoSuchElementException.class).isThrownBy(() -> Iterables.EMPTY_LONG.iterator().nextLong());
	}

	@Test
	void testEmptyDouble() {
		assertThat(Iterables.EMPTY_DOUBLE.iterator().hasNext()).isFalse();
		assertThatExceptionOfType(NoSuchElementException.class).isThrownBy(() -> Iterables.EMPTY_DOUBLE.iterator().nextDouble());
	}

	@Test
	void testEmpty() {
		assertThat(Iterables.empty().iterator().hasNext()).isFalse();
		assertThatExceptionOfType(NoSuchElementException.class).isThrownBy(() -> Iterables.empty().iterator().next());
	}

	@Test
	void testNullToEmpty() {
		assertThat(Iterables.nullToEmpty(null)).isEmpty();
		assertThat(Iterables.nullToEmpty(Iterables.empty())).isEmpty();
		assertThat(Iterables.nullToEmpty(Iterables.of(1))).isNotEmpty();
	}

	@Test
	void testUnmodifiable() {
		final var list = new ArrayList<>(Arrays.asList(1, null, 2));
		final var iterator = list.iterator();
		iterator.next();
		iterator.remove();
		assertThat(list).hasSize(2);
		final var unmodifiableIterable = Iterables.unmodifiable(list);
		for (var i = 0; i < 2; ++i) {
			final var unmodifiableIterator = unmodifiableIterable.iterator();
			while (unmodifiableIterator.hasNext()) {
				unmodifiableIterator.next();
				assertThatExceptionOfType(UnsupportedOperationException.class).isThrownBy(unmodifiableIterator::remove);
			}
			assertThatExceptionOfType(NoSuchElementException.class).isThrownBy(unmodifiableIterator::next);
			assertThat(list).hasSize(2);
		}
	}

	@Test
	void testUnmodifiableNull() {
		assertThatNullPointerException().isThrownBy(() -> Iterables.unmodifiable(null));
	}

	@Test
	void testMap() {
		final var list = new ArrayList<>(Arrays.asList(1, 2, 3));
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
	void testMapNull() {
		assertThatNullPointerException().isThrownBy(() -> Iterables.map(null, Function.identity()));
		assertThatNullPointerException().isThrownBy(() -> Iterables.map(Iterables.empty(), null));
	}

	@Test
	void testTransferTo() {
		final var iterable = Iterables.ofInt(1, 2, 3);
		final var list = new ArrayList<>();
		assertThat(Iterables.transferTo(iterable, list)).isEqualTo(3L);
		assertThat(list).containsExactly(1, 2, 3);
		assertThat(Iterables.transferTo(iterable, list)).isEqualTo(3L);
		assertThat(list).containsExactly(1, 2, 3, 1, 2, 3);
	}

	@Test
	void testTransferToNull() {
		assertThatNullPointerException().isThrownBy(() -> Iterables.transferTo(null, new ArrayList<>()));
		assertThatNullPointerException().isThrownBy(() -> Iterables.transferTo(Iterables.empty(), null));
	}

	@Test
	void testConcat() {
		assertThat(Iterables.concat(Iterables.of(1, 2, 3), Iterables.of(1L, 2L, 3L))).containsExactly(1, 2, 3, 1L, 2L, 3L);
	}

	@Test
	void testConcatOne() {
		assertThat(Iterables.concat(Iterables.of(1, 2, 3))).containsExactly(1, 2, 3);
	}

	@Test
	void testConcatNone() {
		assertThat(Iterables.concat()).isEmpty();
	}

	@Test
	void testConcatEmpty() {
		final var concatIterable = Iterables.concat(Iterables.empty(), Iterables.empty());
		assertThat(concatIterable).isEmpty();
		assertThat(concatIterable.iterator().hasNext()).isFalse();
		assertThatExceptionOfType(NoSuchElementException.class).isThrownBy(() -> concatIterable.iterator().next());
	}

	@Test
	void testConcatNull() {
		assertThatNullPointerException().isThrownBy(() -> Iterables.concat((Iterable<?>[]) null));
		assertThatNullPointerException().isThrownBy(() -> Iterables.concat((List<Iterable<?>>) null));
		assertThatNullPointerException().isThrownBy(() -> Iterables.concat((Iterable<?>) null));
	}

	@Test
	void testJoin() {
		assertThat(Iterables.join(ObjectArrays.of(0.0d), Iterables.of(1, 2, 3), Iterables.of(1L, 2L, 3L))).containsExactly(1, 2, 3, 0.0d, 1L, 2L, 3L);
	}

	@Test
	void testJoinEmptySeparator() {
		assertThat(Iterables.join(ObjectArrays.empty(Integer.class), Iterables.of(1, 2, 3), Iterables.of(1L, 2L, 3L))).containsExactly(1, 2, 3, 1L, 2L, 3L);
	}

	@Test
	void testJoinOne() {
		assertThat(Iterables.join(ObjectArrays.of(0.0d), Iterables.of(1, 2, 3))).containsExactly(1, 2, 3);
	}

	@Test
	void testJoinNone() {
		assertThat(Iterables.join(ObjectArrays.of(0.0d))).isEmpty();
	}

	@Test
	void testJoinEmpty() {
		assertThat(Iterables.join(ObjectArrays.of(0.0d), Iterables.empty(), Iterables.empty())).containsExactly(0.0d);
	}

	@Test
	void testJoinNull() {
		assertThatNullPointerException().isThrownBy(() -> Iterables.join(null, Iterables.empty()));
		assertThatNullPointerException().isThrownBy(() -> Iterables.join(ObjectArrays.empty(Integer.class), (Iterable<?>[]) null));
		assertThatNullPointerException().isThrownBy(() -> Iterables.join(ObjectArrays.empty(Integer.class), (List<Iterable<?>>) null));
		assertThatNullPointerException().isThrownBy(() -> Iterables.join(ObjectArrays.empty(Integer.class), (Iterable<?>) null));
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
	void testOfIntNull() {
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
	void testOfLongNull() {
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
	void testOfDoubleNull() {
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
	void testOfNull() {
		assertThatNullPointerException().isThrownBy(() -> Iterables.of((Integer[]) null));
	}

	@Test
	void testToSet() {
		assertThat(Iterables.toSet(Iterables.empty())).isEmpty();
		assertThat(Iterables.toSet(Iterables.of(1, null, null, 2))).containsExactlyInAnyOrder(1, null, 2);
	}

	@Test
	void testToSetNull() {
		assertThatNullPointerException().isThrownBy(() -> Iterables.toSet(null));
	}

	@Test
	void testToList() {
		assertThat(Iterables.toList(Iterables.empty())).isEmpty();
		assertThat(Iterables.toList(Iterables.of(1, null, null, 2))).containsExactly(1, null, null, 2);
	}

	@Test
	void testToListNull() {
		assertThatNullPointerException().isThrownBy(() -> Iterables.toList(null));
	}

	@Test
	void testWrapInputStream() {
		final var iterable = Iterables.wrap(InputStreams.of((byte) 1, (byte) 2, (byte) 3));
		assertThat(iterable.iterator()).containsExactly(1, 2, 3);
		assertThatExceptionOfType(IllegalStateException.class).isThrownBy(iterable::iterator);
	}

	@Test
	void testWrapInputStreamNull() {
		assertThatNullPointerException().isThrownBy(() -> Iterables.wrap((InputStream) null));
	}

	@Test
	void testWrapReader() {
		final var iterable = Iterables.wrap(Readers.of((char) 1, (char) 2, (char) 3));
		assertThat(iterable.iterator()).containsExactly(1, 2, 3);
		assertThatExceptionOfType(IllegalStateException.class).isThrownBy(iterable::iterator);
	}

	@Test
	void testWrapReaderNull() {
		assertThatNullPointerException().isThrownBy(() -> Iterables.wrap((Reader) null));
	}

	@Test
	void testWrapBufferedReader() {
		final var iterable = Iterables.wrap(new BufferedReader(new StringReader(String.join("\n", "1", "2", "3"))));
		assertThat(iterable.iterator()).containsExactly("1", "2", "3");
		assertThatExceptionOfType(IllegalStateException.class).isThrownBy(iterable::iterator);
	}

	@Test
	void testWrapBufferedReaderNull() {
		assertThatNullPointerException().isThrownBy(() -> Iterables.wrap((BufferedReader) null));
	}

	@Test
	void testWrapLineReader() {
		final var iterable = Iterables.wrap(new LineReader(new StringReader(String.join("\n", "1", "2", "3")), LineSeparator.LF));
		assertThat(iterable.iterator()).containsExactly("1", "2", "3");
		assertThatExceptionOfType(IllegalStateException.class).isThrownBy(iterable::iterator);
	}

	@Test
	void testWrapLineReaderNull() {
		assertThatNullPointerException().isThrownBy(() -> Iterables.wrap((LineReader) null));
	}

	@Test
	void testWrapPrimitiveIteratorOfInt() {
		final var iterable = Iterables.wrap(Iterables.ofInt(1, 2, 3).iterator());
		assertThat(iterable.iterator()).containsExactly(1, 2, 3);
		assertThatExceptionOfType(IllegalStateException.class).isThrownBy(iterable::iterator);
	}

	@Test
	void testWrapPrimitiveIteratorOfIntNull() {
		assertThatNullPointerException().isThrownBy(() -> Iterables.wrap((PrimitiveIterator.OfInt) null));
	}

	@Test
	void testWrapPrimitiveIteratorOfLong() {
		final var iterable = Iterables.wrap(Iterables.ofLong(1L, 2L, 3L).iterator());
		assertThat(iterable.iterator()).containsExactly(1L, 2L, 3L);
		assertThatExceptionOfType(IllegalStateException.class).isThrownBy(iterable::iterator);
	}

	@Test
	void testWrapPrimitiveIteratorOfLongNull() {
		assertThatNullPointerException().isThrownBy(() -> Iterables.wrap((PrimitiveIterator.OfLong) null));
	}

	@Test
	void testWrapPrimitiveIteratorOfDouble() {
		final var iterable = Iterables.wrap(Iterables.ofDouble(1.0d, 2.0d, 3.0d).iterator());
		assertThat(iterable.iterator()).containsExactly(1.0d, 2.0d, 3.0d);
		assertThatExceptionOfType(IllegalStateException.class).isThrownBy(iterable::iterator);
	}

	@Test
	void testWrapPrimitiveIteratorOfDoubleNull() {
		assertThatNullPointerException().isThrownBy(() -> Iterables.wrap((PrimitiveIterator.OfDouble) null));
	}

	@Test
	void testWrapIterator() {
		final var iterable = Iterables.wrap(Iterables.of(1, null, 2).iterator());
		assertThat(iterable.iterator()).containsExactly(1, null, 2);
		assertThatExceptionOfType(IllegalStateException.class).isThrownBy(iterable::iterator);
	}

	@Test
	void testWrapIteratorNull() {
		assertThatNullPointerException().isThrownBy(() -> Iterables.wrap((Iterator<Integer>) null));
	}
}