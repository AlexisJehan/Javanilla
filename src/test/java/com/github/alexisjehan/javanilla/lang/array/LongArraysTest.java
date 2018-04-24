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
package com.github.alexisjehan.javanilla.lang.array;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThatNullPointerException;

/**
 * <p>{@link LongArrays} unit tests.</p>
 */
final class LongArraysTest {

	@Test
	void testEmpty() {
		assertThat(LongArrays.EMPTY).isEmpty();
	}

	@Test
	void testNullToEmpty() {
		assertThat(LongArrays.nullToEmpty(null)).isEmpty();
		assertThat(LongArrays.nullToEmpty(LongArrays.EMPTY)).isEmpty();
		assertThat(LongArrays.nullToEmpty(LongArrays.of(1L))).isNotEmpty();
	}

	@Test
	void testEmptyToNull() {
		assertThat(LongArrays.emptyToNull(LongArrays.EMPTY)).isNull();
		assertThat(LongArrays.emptyToNull(null)).isNull();
		assertThat(LongArrays.emptyToNull(LongArrays.of(1L))).isNotNull();
	}

	@Test
	void testIndexOf() {
		final var array = LongArrays.of(1L, 2L, 3L);
		assertThat(LongArrays.indexOf(array, 1L)).isEqualTo(0);
		assertThat(LongArrays.indexOf(array, 2L)).isEqualTo(1);
		assertThat(LongArrays.indexOf(array, 3L)).isEqualTo(2);
		assertThat(LongArrays.indexOf(array, 1L, 1)).isEqualTo(-1);
		assertThat(LongArrays.indexOf(array, 2L, 2)).isEqualTo(-1);
		assertThat(LongArrays.indexOf(array, 4L)).isEqualTo(-1);
	}

	@Test
	void testIndexOfNull() {
		assertThatNullPointerException().isThrownBy(() -> LongArrays.indexOf(null, 0L));
	}

	@Test
	void testIndexOfInvalidFromIndex() {
		assertThatExceptionOfType(IndexOutOfBoundsException.class).isThrownBy(() -> LongArrays.indexOf(LongArrays.of(1L), 1, -1));
		assertThatExceptionOfType(IndexOutOfBoundsException.class).isThrownBy(() -> LongArrays.indexOf(LongArrays.of(1L), 1,  1));
	}

	@Test
	void testLastIndexOf() {
		final var array = LongArrays.of(1L, 2L, 3L, 1L);
		assertThat(LongArrays.lastIndexOf(array, 1L)).isEqualTo(3);
		assertThat(LongArrays.lastIndexOf(array, 2L)).isEqualTo(1);
		assertThat(LongArrays.lastIndexOf(array, 3L)).isEqualTo(2);
		assertThat(LongArrays.lastIndexOf(array, 1L, 1)).isEqualTo( 3);
		assertThat(LongArrays.lastIndexOf(array, 2L, 2)).isEqualTo(-1);
		assertThat(LongArrays.lastIndexOf(array, 3L, 3)).isEqualTo(-1);
		assertThat(LongArrays.lastIndexOf(array, 4L)).isEqualTo(-1);
	}

	@Test
	void testLastIndexOfNull() {
		assertThatNullPointerException().isThrownBy(() -> LongArrays.lastIndexOf(null, 0L));
	}

	@Test
	void testLastIndexOfInvalidFromIndex() {
		assertThatExceptionOfType(IndexOutOfBoundsException.class).isThrownBy(() -> LongArrays.lastIndexOf(LongArrays.of(1L), 1, -1));
		assertThatExceptionOfType(IndexOutOfBoundsException.class).isThrownBy(() -> LongArrays.lastIndexOf(LongArrays.of(1L), 1,  1));
	}

	@Test
	void testContains() {
		assertThat(LongArrays.contains(LongArrays.EMPTY, 1L)).isFalse();
		final var array = LongArrays.of(1L, 2L, 3L);
		assertThat(LongArrays.contains(array, 1L)).isTrue();
		assertThat(LongArrays.contains(array, 2L)).isTrue();
		assertThat(LongArrays.contains(array, 3L)).isTrue();
		assertThat(LongArrays.contains(array, 4L)).isFalse();
	}

	@Test
	void testContainsNull() {
		assertThatNullPointerException().isThrownBy(() -> LongArrays.contains(null, 0L));
	}

	@Test
	void testContainsOnly() {
		assertThat(LongArrays.containsOnly(LongArrays.EMPTY, 1L)).isFalse();
		final var array1 = LongArrays.of(1L, 1L);
		assertThat(LongArrays.containsOnly(array1, 1L)).isTrue();
		assertThat(LongArrays.containsOnly(array1, 2L)).isFalse();
		final var array2 = LongArrays.of(1L, 2L);
		assertThat(LongArrays.containsOnly(array2, 1L)).isFalse();
		assertThat(LongArrays.containsOnly(array2, 2L)).isFalse();
	}

	@Test
	void testContainsOnlyNull() {
		assertThatNullPointerException().isThrownBy(() -> LongArrays.containsOnly(null, 0L));
	}

	@Test
	void testContainsAny() {
		assertThat(LongArrays.containsAny(LongArrays.EMPTY, 1L, 2L)).isFalse();
		final var array = LongArrays.of(1L, 2L, 3L);
		assertThat(LongArrays.containsAny(array)).isFalse();
		assertThat(LongArrays.containsAny(array, 1L, 2L)).isTrue();
		assertThat(LongArrays.containsAny(array, 3L, 4L)).isTrue();
		assertThat(LongArrays.containsAny(array, 5L, 6L)).isFalse();
	}

	@Test
	void testContainsAnyNull() {
		assertThatNullPointerException().isThrownBy(() -> LongArrays.containsAny(null, 0L));
		assertThatNullPointerException().isThrownBy(() -> LongArrays.containsAny(LongArrays.of(1L, 2L, 3L), (long[]) null));
	}

	@Test
	void testContainsAll() {
		assertThat(LongArrays.containsAll(LongArrays.EMPTY, 1L, 2L)).isFalse();
		final var array = LongArrays.of(1L, 2L, 3L);
		assertThat(LongArrays.containsAll(array)).isFalse();
		assertThat(LongArrays.containsAll(array, 1L, 2L, 3L)).isTrue();
		assertThat(LongArrays.containsAll(array, 3L, 2L, 1L)).isTrue();
		assertThat(LongArrays.containsAll(array, 1L, 2L)).isTrue();
		assertThat(LongArrays.containsAll(array, 1L, 2L, 3L, 4L)).isFalse();
	}

	@Test
	void testContainsAllNull() {
		assertThatNullPointerException().isThrownBy(() -> LongArrays.containsAll(null, 0L));
		assertThatNullPointerException().isThrownBy(() -> LongArrays.containsAll(LongArrays.of(1L, 2L, 3L), (long[]) null));
	}

	@Test
	void testConcat() {
		final var array = LongArrays.of(1L, 2L, 3L);
		assertThat(LongArrays.concat(array, array)).isEqualTo(LongArrays.of(1L, 2L, 3L, 1L, 2L, 3L));
	}

	@Test
	void testConcatOne() {
		final var array = LongArrays.of(1L, 2L, 3L);
		assertThat(LongArrays.concat(array)).isEqualTo(array);
	}

	@Test
	void testConcatNone() {
		assertThat(LongArrays.concat()).isEmpty();
	}

	@Test
	void testConcatNull() {
		assertThatNullPointerException().isThrownBy(() -> LongArrays.concat((long[][]) null));
		assertThatNullPointerException().isThrownBy(() -> LongArrays.concat((List<long[]>) null));
		assertThatNullPointerException().isThrownBy(() -> LongArrays.concat((long[]) null));
	}

	@Test
	void testJoin() {
		final var array = LongArrays.of(1L, 2L, 3L);
		assertThat(LongArrays.join(LongArrays.of(0L), array, array)).isEqualTo(LongArrays.of(1L, 2L, 3L, 0L, 1L, 2L, 3L));
	}

	@Test
	void testJoinEmptySeparator() {
		final var array = LongArrays.of(1L, 2L, 3L);
		assertThat(LongArrays.join(LongArrays.EMPTY, array, array)).isEqualTo(LongArrays.concat(array, array));
	}

	@Test
	void testJoinOne() {
		final var array = LongArrays.of(1L, 2L, 3L);
		assertThat(LongArrays.join(LongArrays.of(0L), array)).isEqualTo(array);
	}

	@Test
	void testJoinNone() {
		assertThat(LongArrays.join(LongArrays.of(0L))).isEmpty();
	}

	@Test
	void testJoinNull() {
		assertThatNullPointerException().isThrownBy(() -> LongArrays.join(null, LongArrays.EMPTY));
		assertThatNullPointerException().isThrownBy(() -> LongArrays.join(LongArrays.EMPTY, (long[][]) null));
		assertThatNullPointerException().isThrownBy(() -> LongArrays.join(LongArrays.EMPTY, (List<long[]>) null));
		assertThatNullPointerException().isThrownBy(() -> LongArrays.join(LongArrays.EMPTY, (long[]) null));
	}

	@Test
	void testOf() {
		assertThat(LongArrays.of()).isEmpty();
		assertThat(LongArrays.of(1L, 2L, 3L)).containsExactly(1L, 2L, 3L);
	}

	@Test
	void testOfNull() {
		assertThatNullPointerException().isThrownBy(() -> LongArrays.of((long[]) null));
	}
}