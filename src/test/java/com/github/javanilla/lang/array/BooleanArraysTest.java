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
 * <p>{@link BooleanArrays} unit tests.</p>
 */
final class BooleanArraysTest {

	@Test
	void testEmpty() {
		assertThat(BooleanArrays.EMPTY).isEmpty();
	}

	@Test
	void testNullToEmpty() {
		assertThat(BooleanArrays.nullToEmpty(null)).isEmpty();
		assertThat(BooleanArrays.nullToEmpty(BooleanArrays.EMPTY)).isEmpty();
		assertThat(BooleanArrays.nullToEmpty(BooleanArrays.of(true))).isNotEmpty();
	}

	@Test
	void testEmptyToNull() {
		assertThat(BooleanArrays.emptyToNull(BooleanArrays.EMPTY)).isNull();
		assertThat(BooleanArrays.emptyToNull(null)).isNull();
		assertThat(BooleanArrays.emptyToNull(BooleanArrays.of(true))).isNotNull();
	}

	@Test
	void testIndexOf() {
		final var array = BooleanArrays.of(true, false);
		assertThat(BooleanArrays.indexOf(array,  true)).isEqualTo(0);
		assertThat(BooleanArrays.indexOf(array, false)).isEqualTo(1);
		assertThat(BooleanArrays.indexOf(array, true, 1)).isEqualTo(-1);
		assertThat(BooleanArrays.indexOf(BooleanArrays.of(true, true), false)).isEqualTo(-1);
	}

	@Test
	void testIndexOfNull() {
		assertThatNullPointerException().isThrownBy(() -> BooleanArrays.indexOf(null, false));
	}

	@Test
	void testIndexOfInvalidFromIndex() {
		assertThatExceptionOfType(IndexOutOfBoundsException.class).isThrownBy(() -> BooleanArrays.indexOf(BooleanArrays.of(true), true, -1));
		assertThatExceptionOfType(IndexOutOfBoundsException.class).isThrownBy(() -> BooleanArrays.indexOf(BooleanArrays.of(true), true,  1));
	}

	@Test
	void testLastIndexOf() {
		final var array = BooleanArrays.of(true, false, true);
		assertThat(BooleanArrays.lastIndexOf(array, true)).isEqualTo(2);
		assertThat(BooleanArrays.lastIndexOf(array, false)).isEqualTo(1);
		assertThat(BooleanArrays.lastIndexOf(array, true, 1)).isEqualTo(2);
		assertThat(BooleanArrays.lastIndexOf(BooleanArrays.of(true, true), false)).isEqualTo(-1);
	}

	@Test
	void testLastIndexOfNull() {
		assertThatNullPointerException().isThrownBy(() -> BooleanArrays.lastIndexOf(null, false));
	}

	@Test
	void testLastIndexOfInvalidFromIndex() {
		assertThatExceptionOfType(IndexOutOfBoundsException.class).isThrownBy(() -> BooleanArrays.lastIndexOf(BooleanArrays.of(true), true, -1));
		assertThatExceptionOfType(IndexOutOfBoundsException.class).isThrownBy(() -> BooleanArrays.lastIndexOf(BooleanArrays.of(true), true,  1));
	}

	@Test
	void testContains() {
		assertThat(BooleanArrays.contains(BooleanArrays.EMPTY, true)).isFalse();
		final var array = BooleanArrays.of(true, false);
		assertThat(BooleanArrays.contains(array,  true)).isTrue();
		assertThat(BooleanArrays.contains(array, false)).isTrue();
		assertThat(BooleanArrays.contains(BooleanArrays.of(true, true), false)).isFalse();
	}

	@Test
	void testContainsNull() {
		assertThatNullPointerException().isThrownBy(() -> BooleanArrays.contains(null, false));
	}

	@Test
	void testContainsOnly() {
		assertThat(BooleanArrays.containsOnly(BooleanArrays.EMPTY, true)).isFalse();
		final var array1 = BooleanArrays.of(true, true);
		assertThat(BooleanArrays.containsOnly(array1,  true)).isTrue();
		assertThat(BooleanArrays.containsOnly(array1, false)).isFalse();
		final var array2 = BooleanArrays.of(true, false);
		assertThat(BooleanArrays.containsOnly(array2,  true)).isFalse();
		assertThat(BooleanArrays.containsOnly(array2, false)).isFalse();
	}

	@Test
	void testContainsOnlyNull() {
		assertThatNullPointerException().isThrownBy(() -> BooleanArrays.containsOnly(null, false));
	}

	@Test
	void testContainsAny() {
		assertThat(BooleanArrays.containsAny(BooleanArrays.EMPTY, true, false)).isFalse();
		assertThat(BooleanArrays.containsAny(BooleanArrays.of(true))).isFalse();
		assertThat(BooleanArrays.containsAny(BooleanArrays.of(true),  true)).isTrue();
		assertThat(BooleanArrays.containsAny(BooleanArrays.of(true), true, false)).isTrue();
		assertThat(BooleanArrays.containsAny(BooleanArrays.of(true), false)).isFalse();
	}

	@Test
	void testContainsAnyNull() {
		assertThatNullPointerException().isThrownBy(() -> BooleanArrays.containsAny(null, false));
		assertThatNullPointerException().isThrownBy(() -> BooleanArrays.containsAny(BooleanArrays.of(true), (boolean[]) null));
	}

	@Test
	void testContainsAll() {
		assertThat(BooleanArrays.containsAll(BooleanArrays.EMPTY, true, false)).isFalse();
		assertThat(BooleanArrays.containsAll(BooleanArrays.of(true))).isFalse();
		assertThat(BooleanArrays.containsAll(BooleanArrays.of(true),  true)).isTrue();
		assertThat(BooleanArrays.containsAll(BooleanArrays.of(true), true, false)).isFalse();
		assertThat(BooleanArrays.containsAll(BooleanArrays.of(true), false)).isFalse();
	}

	@Test
	void testContainsAllNull() {
		assertThatNullPointerException().isThrownBy(() -> BooleanArrays.containsAll(null, false));
		assertThatNullPointerException().isThrownBy(() -> BooleanArrays.containsAll(BooleanArrays.of(true), (boolean[]) null));
	}

	@Test
	void testConcat() {
		final var array = BooleanArrays.of(true, false);
		assertThat(BooleanArrays.concat(array, array)).isEqualTo(BooleanArrays.of(true, false, true, false));
	}

	@Test
	void testConcatOne() {
		final var array = BooleanArrays.of(true, false);
		assertThat(BooleanArrays.concat(array)).isEqualTo(array);
	}

	@Test
	void testConcatNone() {
		assertThat(BooleanArrays.concat()).isEmpty();
	}

	@Test
	void testConcatNull() {
		assertThatNullPointerException().isThrownBy(() -> BooleanArrays.concat((boolean[][]) null));
		assertThatNullPointerException().isThrownBy(() -> BooleanArrays.concat((List<boolean[]>) null));
		assertThatNullPointerException().isThrownBy(() -> BooleanArrays.concat((boolean[]) null));
	}

	@Test
	void testJoin() {
		final var array = BooleanArrays.of(true, false);
		assertThat(BooleanArrays.join(BooleanArrays.of(false), array, array)).isEqualTo(BooleanArrays.of(true, false, false, true, false));
	}

	@Test
	void testJoinEmptySeparator() {
		final var array = BooleanArrays.of(true, false);
		assertThat(BooleanArrays.join(BooleanArrays.EMPTY, array, array)).isEqualTo(BooleanArrays.concat(array, array));
	}

	@Test
	void testJoinOne() {
		final var array = BooleanArrays.of(true, false);
		assertThat(BooleanArrays.join(BooleanArrays.of(false), array)).isEqualTo(array);
	}

	@Test
	void testJoinNone() {
		assertThat(BooleanArrays.join(BooleanArrays.of(false))).isEmpty();
	}

	@Test
	void testJoinNull() {
		assertThatNullPointerException().isThrownBy(() -> BooleanArrays.join(null, BooleanArrays.EMPTY));
		assertThatNullPointerException().isThrownBy(() -> BooleanArrays.join(BooleanArrays.EMPTY, (boolean[][]) null));
		assertThatNullPointerException().isThrownBy(() -> BooleanArrays.join(BooleanArrays.EMPTY, (List<boolean[]>) null));
		assertThatNullPointerException().isThrownBy(() -> BooleanArrays.join(BooleanArrays.EMPTY, (boolean[]) null));
	}

	@Test
	void testOf() {
		assertThat(BooleanArrays.of()).isEmpty();
		assertThat(BooleanArrays.of(true, false)).containsExactly(true, false);
	}

	@Test
	void testOfNull() {
		assertThatNullPointerException().isThrownBy(() -> BooleanArrays.of((boolean[]) null));
	}
}