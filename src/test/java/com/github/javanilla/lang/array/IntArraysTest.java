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
package com.github.javanilla.lang.array;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThatNullPointerException;

/**
 * <p>{@link IntArrays} unit tests.</p>
 */
final class IntArraysTest {

	@Test
	void testEmpty() {
		assertThat(IntArrays.EMPTY).isEmpty();
	}

	@Test
	void testNullToEmpty() {
		assertThat(IntArrays.nullToEmpty(null)).isEmpty();
		assertThat(IntArrays.nullToEmpty(IntArrays.EMPTY)).isEmpty();
		assertThat(IntArrays.nullToEmpty(IntArrays.of(1))).isNotEmpty();
	}

	@Test
	void testEmptyToNull() {
		assertThat(IntArrays.emptyToNull(IntArrays.EMPTY)).isNull();
		assertThat(IntArrays.emptyToNull(null)).isNull();
		assertThat(IntArrays.emptyToNull(IntArrays.of(1))).isNotNull();
	}

	@Test
	void testIndexOf() {
		final var array = IntArrays.of(1, 2, 3);
		assertThat(IntArrays.indexOf(array, 1)).isEqualTo(0);
		assertThat(IntArrays.indexOf(array, 2)).isEqualTo(1);
		assertThat(IntArrays.indexOf(array, 3)).isEqualTo(2);
		assertThat(IntArrays.indexOf(array, 1, 1)).isEqualTo(-1);
		assertThat(IntArrays.indexOf(array, 2, 2)).isEqualTo(-1);
		assertThat(IntArrays.indexOf(array, 4)).isEqualTo(-1);
	}

	@Test
	void testIndexOfNull() {
		assertThatNullPointerException().isThrownBy(() -> IntArrays.indexOf(null, 0));
	}

	@Test
	void testIndexOfInvalidFromIndex() {
		assertThatExceptionOfType(IndexOutOfBoundsException.class).isThrownBy(() -> IntArrays.indexOf(IntArrays.of(1), 1, -1));
		assertThatExceptionOfType(IndexOutOfBoundsException.class).isThrownBy(() -> IntArrays.indexOf(IntArrays.of(1), 1,  1));
	}

	@Test
	void testLastIndexOf() {
		final var array = IntArrays.of(1, 2, 3, 1);
		assertThat(IntArrays.lastIndexOf(array, 1)).isEqualTo(3);
		assertThat(IntArrays.lastIndexOf(array, 2)).isEqualTo(1);
		assertThat(IntArrays.lastIndexOf(array, 3)).isEqualTo(2);
		assertThat(IntArrays.lastIndexOf(array, 1, 1)).isEqualTo( 3);
		assertThat(IntArrays.lastIndexOf(array, 2, 2)).isEqualTo(-1);
		assertThat(IntArrays.lastIndexOf(array, 3, 3)).isEqualTo(-1);
		assertThat(IntArrays.lastIndexOf(array, 4)).isEqualTo(-1);
	}

	@Test
	void testLastIndexOfNull() {
		assertThatNullPointerException().isThrownBy(() -> IntArrays.lastIndexOf(null, 0));
	}

	@Test
	void testLastIndexOfInvalidFromIndex() {
		assertThatExceptionOfType(IndexOutOfBoundsException.class).isThrownBy(() -> IntArrays.lastIndexOf(IntArrays.of(1), 1, -1));
		assertThatExceptionOfType(IndexOutOfBoundsException.class).isThrownBy(() -> IntArrays.lastIndexOf(IntArrays.of(1), 1,  1));
	}

	@Test
	void testContains() {
		assertThat(IntArrays.contains(IntArrays.EMPTY, 1)).isFalse();
		final var array = IntArrays.of(1, 2, 3);
		assertThat(IntArrays.contains(array, 1)).isTrue();
		assertThat(IntArrays.contains(array, 2)).isTrue();
		assertThat(IntArrays.contains(array, 3)).isTrue();
		assertThat(IntArrays.contains(array, 4)).isFalse();
	}

	@Test
	void testContainsNull() {
		assertThatNullPointerException().isThrownBy(() -> IntArrays.contains(null, 0));
	}

	@Test
	void testContainsOnly() {
		assertThat(IntArrays.containsOnly(IntArrays.EMPTY, 1)).isFalse();
		final var array1 = IntArrays.of(1, 1);
		assertThat(IntArrays.containsOnly(array1, 1)).isTrue();
		assertThat(IntArrays.containsOnly(array1, 2)).isFalse();
		final var array2 = IntArrays.of(1, 2);
		assertThat(IntArrays.containsOnly(array2, 1)).isFalse();
		assertThat(IntArrays.containsOnly(array2, 2)).isFalse();
	}

	@Test
	void testContainsOnlyNull() {
		assertThatNullPointerException().isThrownBy(() -> IntArrays.containsOnly(null, 0));
	}

	@Test
	void testContainsAny() {
		assertThat(IntArrays.containsAny(IntArrays.EMPTY, 1, 2)).isFalse();
		final var array = IntArrays.of(1, 2, 3);
		assertThat(IntArrays.containsAny(array)).isFalse();
		assertThat(IntArrays.containsAny(array, 1, 2)).isTrue();
		assertThat(IntArrays.containsAny(array, 3, 4)).isTrue();
		assertThat(IntArrays.containsAny(array, 5, 6)).isFalse();
	}

	@Test
	void testContainsAnyNull() {
		assertThatNullPointerException().isThrownBy(() -> IntArrays.containsAny(null, 0));
		assertThatNullPointerException().isThrownBy(() -> IntArrays.containsAny(IntArrays.of(1, 2, 3), (int[]) null));
	}

	@Test
	void testContainsAll() {
		assertThat(IntArrays.containsAll(IntArrays.EMPTY, 1, 2)).isFalse();
		final var array = IntArrays.of(1, 2, 3);
		assertThat(IntArrays.containsAll(array)).isFalse();
		assertThat(IntArrays.containsAll(array, 1, 2, 3)).isTrue();
		assertThat(IntArrays.containsAll(array, 3, 2, 1)).isTrue();
		assertThat(IntArrays.containsAll(array, 1, 2)).isTrue();
		assertThat(IntArrays.containsAll(array, 1, 2, 3, 4)).isFalse();
	}

	@Test
	void testContainsAllNull() {
		assertThatNullPointerException().isThrownBy(() -> IntArrays.containsAll(null, 0));
		assertThatNullPointerException().isThrownBy(() -> IntArrays.containsAll(IntArrays.of(1, 2, 3), (int[]) null));
	}

	@Test
	void testConcat() {
		final var array = IntArrays.of(1, 2, 3);
		assertThat(IntArrays.concat(array, array)).isEqualTo(IntArrays.of(1, 2, 3, 1, 2, 3));
	}

	@Test
	void testConcatOne() {
		final var array = IntArrays.of(1, 2, 3);
		assertThat(IntArrays.concat(array)).isEqualTo(array);
	}

	@Test
	void testConcatNone() {
		assertThat(IntArrays.concat()).isEmpty();
	}

	@Test
	void testConcatNull() {
		assertThatNullPointerException().isThrownBy(() -> IntArrays.concat((int[][]) null));
		assertThatNullPointerException().isThrownBy(() -> IntArrays.concat((List<int[]>) null));
		assertThatNullPointerException().isThrownBy(() -> IntArrays.concat((int[]) null));
	}

	@Test
	void testJoin() {
		final var array = IntArrays.of(1, 2, 3);
		assertThat(IntArrays.join(IntArrays.of(0), array, array)).isEqualTo(IntArrays.of(1, 2, 3, 0, 1, 2, 3));
	}

	@Test
	void testJoinEmptySeparator() {
		final var array = IntArrays.of(1, 2, 3);
		assertThat(IntArrays.join(IntArrays.EMPTY, array, array)).isEqualTo(IntArrays.concat(array, array));
	}

	@Test
	void testJoinOne() {
		final var array = IntArrays.of(1, 2, 3);
		assertThat(IntArrays.join(IntArrays.of(0), array)).isEqualTo(array);
	}

	@Test
	void testJoinNone() {
		assertThat(IntArrays.join(IntArrays.of(0))).isEmpty();
	}

	@Test
	void testJoinNull() {
		assertThatNullPointerException().isThrownBy(() -> IntArrays.join(null, IntArrays.EMPTY));
		assertThatNullPointerException().isThrownBy(() -> IntArrays.join(IntArrays.EMPTY, (int[][]) null));
		assertThatNullPointerException().isThrownBy(() -> IntArrays.join(IntArrays.EMPTY, (List<int[]>) null));
		assertThatNullPointerException().isThrownBy(() -> IntArrays.join(IntArrays.EMPTY, (int[]) null));
	}

	@Test
	void testOf() {
		assertThat(IntArrays.of()).isEmpty();
		assertThat(IntArrays.of(1, 2, 3)).containsExactly(1, 2, 3);
	}

	@Test
	void testOfNull() {
		assertThatNullPointerException().isThrownBy(() -> IntArrays.of((int[]) null));
	}
}