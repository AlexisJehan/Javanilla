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
 * <p>{@link DoubleArrays} unit tests.</p>
 */
final class DoubleArraysTest {

	@Test
	void testEmpty() {
		assertThat(DoubleArrays.EMPTY).isEmpty();
	}

	@Test
	void testNullToEmpty() {
		assertThat(DoubleArrays.nullToEmpty(null)).isEmpty();
		assertThat(DoubleArrays.nullToEmpty(DoubleArrays.EMPTY)).isEmpty();
		assertThat(DoubleArrays.nullToEmpty(DoubleArrays.of(1.0d))).isNotEmpty();
	}

	@Test
	void testEmptyToNull() {
		assertThat(DoubleArrays.emptyToNull(DoubleArrays.EMPTY)).isNull();
		assertThat(DoubleArrays.emptyToNull(null)).isNull();
		assertThat(DoubleArrays.emptyToNull(DoubleArrays.of(1.0d))).isNotNull();
	}

	@Test
	void testIndexOf() {
		final var array = DoubleArrays.of(1.0d, 2.0d, 3.0d);
		assertThat(DoubleArrays.indexOf(array, 1.0d)).isEqualTo(0);
		assertThat(DoubleArrays.indexOf(array, 2.0d)).isEqualTo(1);
		assertThat(DoubleArrays.indexOf(array, 3.0d)).isEqualTo(2);
		assertThat(DoubleArrays.indexOf(array, 1.0d, 1)).isEqualTo(-1);
		assertThat(DoubleArrays.indexOf(array, 2.0d, 2)).isEqualTo(-1);
		assertThat(DoubleArrays.indexOf(array, 4.0d)).isEqualTo(-1);
	}

	@Test
	void testIndexOfNull() {
		assertThatNullPointerException().isThrownBy(() -> DoubleArrays.indexOf(null, 0.0d));
	}

	@Test
	void testIndexOfInvalidFromIndex() {
		assertThatExceptionOfType(IndexOutOfBoundsException.class).isThrownBy(() -> DoubleArrays.indexOf(DoubleArrays.of(1.0d), 1.0d, -1));
		assertThatExceptionOfType(IndexOutOfBoundsException.class).isThrownBy(() -> DoubleArrays.indexOf(DoubleArrays.of(1.0d), 1.0d,  1));
	}

	@Test
	void testLastIndexOf() {
		final var array = DoubleArrays.of(1.0d, 2.0d, 3.0d, 1.0d);
		assertThat(DoubleArrays.lastIndexOf(array, 1.0d)).isEqualTo(3);
		assertThat(DoubleArrays.lastIndexOf(array, 2.0d)).isEqualTo(1);
		assertThat(DoubleArrays.lastIndexOf(array, 3.0d)).isEqualTo(2);
		assertThat(DoubleArrays.lastIndexOf(array, 1.0d, 1)).isEqualTo( 3);
		assertThat(DoubleArrays.lastIndexOf(array, 2.0d, 2)).isEqualTo(-1);
		assertThat(DoubleArrays.lastIndexOf(array, 3.0d, 3)).isEqualTo(-1);
		assertThat(DoubleArrays.lastIndexOf(array, 4.0d)).isEqualTo(-1);
	}

	@Test
	void testLastIndexOfNull() {
		assertThatNullPointerException().isThrownBy(() -> DoubleArrays.lastIndexOf(null, 0.0d));
	}

	@Test
	void testLastIndexOfInvalidFromIndex() {
		assertThatExceptionOfType(IndexOutOfBoundsException.class).isThrownBy(() -> DoubleArrays.lastIndexOf(DoubleArrays.of(1.0d), 1.0d, -1));
		assertThatExceptionOfType(IndexOutOfBoundsException.class).isThrownBy(() -> DoubleArrays.lastIndexOf(DoubleArrays.of(1.0d), 1.0d,  1));
	}

	@Test
	void testContains() {
		assertThat(DoubleArrays.contains(DoubleArrays.EMPTY, 1.0d)).isFalse();
		final var array = DoubleArrays.of(1.0d, 2.0d, 3.0d);
		assertThat(DoubleArrays.contains(array, 1.0d)).isTrue();
		assertThat(DoubleArrays.contains(array, 2.0d)).isTrue();
		assertThat(DoubleArrays.contains(array, 3.0d)).isTrue();
		assertThat(DoubleArrays.contains(array, 4.0d)).isFalse();
	}

	@Test
	void testContainsNull() {
		assertThatNullPointerException().isThrownBy(() -> DoubleArrays.contains(null, 0.0d));
	}

	@Test
	void testContainsOnly() {
		assertThat(DoubleArrays.containsOnly(DoubleArrays.EMPTY, 1.0d)).isFalse();
		final var array1 = DoubleArrays.of(1.0d, 1.0d);
		assertThat(DoubleArrays.containsOnly(array1, 1.0d)).isTrue();
		assertThat(DoubleArrays.containsOnly(array1, 2.0d)).isFalse();
		final var array2 = DoubleArrays.of(1.0d, 2.0d);
		assertThat(DoubleArrays.containsOnly(array2, 1.0d)).isFalse();
		assertThat(DoubleArrays.containsOnly(array2, 2.0d)).isFalse();
	}

	@Test
	void testContainsOnlyNull() {
		assertThatNullPointerException().isThrownBy(() -> DoubleArrays.containsOnly(null, 0.0d));
	}

	@Test
	void testContainsAny() {
		assertThat(DoubleArrays.containsAny(DoubleArrays.EMPTY, 1.0d, 2.0d)).isFalse();
		final var array = DoubleArrays.of(1.0d, 2.0d, 3.0d);
		assertThat(DoubleArrays.containsAny(array)).isFalse();
		assertThat(DoubleArrays.containsAny(array, 1.0d, 2.0d)).isTrue();
		assertThat(DoubleArrays.containsAny(array, 3.0d, 4.0d)).isTrue();
		assertThat(DoubleArrays.containsAny(array, 5.0d, 6.0d)).isFalse();
	}

	@Test
	void testContainsAnyNull() {
		assertThatNullPointerException().isThrownBy(() -> DoubleArrays.containsAny(null, 0.0d));
		assertThatNullPointerException().isThrownBy(() -> DoubleArrays.containsAny(DoubleArrays.of(1.0d, 2.0d, 3.0d), (double[]) null));
	}

	@Test
	void testContainsAll() {
		assertThat(DoubleArrays.containsAll(DoubleArrays.EMPTY, 1.0d, 2.0d)).isFalse();
		final var array = DoubleArrays.of(1.0d, 2.0d, 3.0d);
		assertThat(DoubleArrays.containsAll(array)).isFalse();
		assertThat(DoubleArrays.containsAll(array, 1.0d, 2.0d, 3.0d)).isTrue();
		assertThat(DoubleArrays.containsAll(array, 3.0d, 2.0d, 1.0d)).isTrue();
		assertThat(DoubleArrays.containsAll(array, 1.0d, 2.0d)).isTrue();
		assertThat(DoubleArrays.containsAll(array, 1.0d, 2.0d, 3.0d, 4.0d)).isFalse();
	}

	@Test
	void testContainsAllNull() {
		assertThatNullPointerException().isThrownBy(() -> DoubleArrays.containsAll(null, 0.0d));
		assertThatNullPointerException().isThrownBy(() -> DoubleArrays.containsAll(DoubleArrays.of(1.0d, 2.0d, 3.0d), (double[]) null));
	}

	@Test
	void testConcat() {
		final var array = DoubleArrays.of(1.0d, 2.0d, 3.0d);
		assertThat(DoubleArrays.concat(array, array)).isEqualTo(DoubleArrays.of(1.0d, 2.0d, 3.0d, 1.0d, 2.0d, 3.0d));
	}

	@Test
	void testConcatOne() {
		final var array = DoubleArrays.of(1.0d, 2.0d, 3.0d);
		assertThat(DoubleArrays.concat(array)).isEqualTo(array);
	}

	@Test
	void testConcatNone() {
		assertThat(DoubleArrays.concat()).isEmpty();
	}

	@Test
	void testConcatNull() {
		assertThatNullPointerException().isThrownBy(() -> DoubleArrays.concat((double[][]) null));
		assertThatNullPointerException().isThrownBy(() -> DoubleArrays.concat((List<double[]>) null));
		assertThatNullPointerException().isThrownBy(() -> DoubleArrays.concat((double[]) null));
	}

	@Test
	void testJoin() {
		final var array = DoubleArrays.of(1.0d, 2.0d, 3.0d);
		assertThat(DoubleArrays.join(DoubleArrays.of(0.0d), array, array)).isEqualTo(DoubleArrays.of(1.0d, 2.0d, 3.0d, 0.0d, 1.0d, 2.0d, 3.0d));
	}

	@Test
	void testJoinEmptySeparator() {
		final var array = DoubleArrays.of(1.0d, 2.0d, 3.0d);
		assertThat(DoubleArrays.join(DoubleArrays.EMPTY, array, array)).isEqualTo(DoubleArrays.concat(array, array));
	}

	@Test
	void testJoinOne() {
		final var array = DoubleArrays.of(1.0d, 2.0d, 3.0d);
		assertThat(DoubleArrays.join(DoubleArrays.of(0.0d), array)).isEqualTo(array);
	}

	@Test
	void testJoinNone() {
		assertThat(DoubleArrays.join(DoubleArrays.of(0.0d))).isEmpty();
	}

	@Test
	void testJoinNull() {
		assertThatNullPointerException().isThrownBy(() -> DoubleArrays.join(null, DoubleArrays.EMPTY));
		assertThatNullPointerException().isThrownBy(() -> DoubleArrays.join(DoubleArrays.EMPTY, (double[][]) null));
		assertThatNullPointerException().isThrownBy(() -> DoubleArrays.join(DoubleArrays.EMPTY, (List<double[]>) null));
		assertThatNullPointerException().isThrownBy(() -> DoubleArrays.join(DoubleArrays.EMPTY, (double[]) null));
	}

	@Test
	void testOf() {
		assertThat(DoubleArrays.of()).isEmpty();
		assertThat(DoubleArrays.of(1.0d, 2.0d, 3.0d)).containsExactly(1.0d, 2.0d, 3.0d);
	}

	@Test
	void testOfNull() {
		assertThatNullPointerException().isThrownBy(() -> DoubleArrays.of((double[]) null));
	}
}