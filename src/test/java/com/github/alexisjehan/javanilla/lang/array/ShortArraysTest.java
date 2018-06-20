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
 * <p>{@link ShortArrays} unit tests.</p>
 */
final class ShortArraysTest {

	@Test
	void testEmpty() {
		assertThat(ShortArrays.EMPTY).isEmpty();
	}

	@Test
	void testNullToEmpty() {
		assertThat(ShortArrays.nullToEmpty(null)).isEmpty();
		assertThat(ShortArrays.nullToEmpty(ShortArrays.EMPTY)).isEmpty();
	}

	@Test
	void testNullToDefault() {
		assertThat(ShortArrays.nullToDefault(null, ShortArrays.singleton((short) 1))).containsExactly((short) 1);
		assertThat(ShortArrays.nullToDefault(ShortArrays.EMPTY, ShortArrays.singleton((short) 1))).isEmpty();
	}

	@Test
	void testNullToDefaultNull() {
		assertThatNullPointerException().isThrownBy(() -> ShortArrays.nullToDefault(ShortArrays.EMPTY, null));
	}

	@Test
	void testEmptyToNull() {
		assertThat(ShortArrays.emptyToNull(null)).isNull();
		assertThat(ShortArrays.emptyToNull(ShortArrays.EMPTY)).isNull();
		assertThat(ShortArrays.emptyToNull(ShortArrays.singleton((short) 1))).containsExactly((short) 1);
	}

	@Test
	void testEmptyToDefault() {
		assertThat(ShortArrays.emptyToDefault(null, ShortArrays.singleton((short) 1))).isNull();
		assertThat(ShortArrays.emptyToDefault(ShortArrays.EMPTY, ShortArrays.singleton((short) 1))).containsExactly((short) 1);
		assertThat(ShortArrays.emptyToDefault(ShortArrays.singleton((short) 1), ShortArrays.singleton((short) 1))).containsExactly((short) 1);
	}

	@Test
	void testEmptyToDefaultInvalid() {
		assertThatIllegalArgumentException().isThrownBy(() -> ShortArrays.emptyToDefault(ShortArrays.EMPTY, ShortArrays.EMPTY));
	}

	@Test
	void testIndexOf() {
		final var array = ShortArrays.of((short) 1, (short) 2, (short) 3);
		assertThat(ShortArrays.indexOf(array, (short) 1)).isEqualTo(0);
		assertThat(ShortArrays.indexOf(array, (short) 2)).isEqualTo(1);
		assertThat(ShortArrays.indexOf(array, (short) 3)).isEqualTo(2);
		assertThat(ShortArrays.indexOf(array, (short) 1, 1)).isEqualTo(-1);
		assertThat(ShortArrays.indexOf(array, (short) 2, 2)).isEqualTo(-1);
		assertThat(ShortArrays.indexOf(array, (short) 4)).isEqualTo(-1);
	}

	@Test
	void testIndexOfNull() {
		assertThatNullPointerException().isThrownBy(() -> ShortArrays.indexOf(null, (short) 0));
	}

	@Test
	void testIndexOfInvalidFromIndex() {
		assertThatExceptionOfType(IndexOutOfBoundsException.class).isThrownBy(() -> ShortArrays.indexOf(ShortArrays.of((short) 1), (short) 1, -1));
		assertThatExceptionOfType(IndexOutOfBoundsException.class).isThrownBy(() -> ShortArrays.indexOf(ShortArrays.of((short) 1), (short) 1,  1));
	}

	@Test
	void testLastIndexOf() {
		final var array = ShortArrays.of((short) 1, (short) 2, (short) 3, (short) 1);
		assertThat(ShortArrays.lastIndexOf(array, (short) 1)).isEqualTo(3);
		assertThat(ShortArrays.lastIndexOf(array, (short) 2)).isEqualTo(1);
		assertThat(ShortArrays.lastIndexOf(array, (short) 3)).isEqualTo(2);
		assertThat(ShortArrays.lastIndexOf(array, (short) 1, 1)).isEqualTo( 3);
		assertThat(ShortArrays.lastIndexOf(array, (short) 2, 2)).isEqualTo(-1);
		assertThat(ShortArrays.lastIndexOf(array, (short) 3, 3)).isEqualTo(-1);
		assertThat(ShortArrays.lastIndexOf(array, (short) 4)).isEqualTo(-1);
	}

	@Test
	void testLastIndexOfNull() {
		assertThatNullPointerException().isThrownBy(() -> ShortArrays.lastIndexOf(null, (short) 0));
	}

	@Test
	void testLastIndexOfInvalidFromIndex() {
		assertThatExceptionOfType(IndexOutOfBoundsException.class).isThrownBy(() -> ShortArrays.lastIndexOf(ShortArrays.of((short) 1), (short) 1, -1));
		assertThatExceptionOfType(IndexOutOfBoundsException.class).isThrownBy(() -> ShortArrays.lastIndexOf(ShortArrays.of((short) 1), (short) 1,  1));
	}

	@Test
	void testContains() {
		assertThat(ShortArrays.contains(ShortArrays.EMPTY, (short) 1)).isFalse();
		final var array = ShortArrays.of((short) 1, (short) 2, (short) 3);
		assertThat(ShortArrays.contains(array, (short) 1)).isTrue();
		assertThat(ShortArrays.contains(array, (short) 2)).isTrue();
		assertThat(ShortArrays.contains(array, (short) 3)).isTrue();
		assertThat(ShortArrays.contains(array, (short) 4)).isFalse();
	}

	@Test
	void testContainsNull() {
		assertThatNullPointerException().isThrownBy(() -> ShortArrays.contains(null, (short) 0));
	}

	@Test
	void testContainsOnce() {
		assertThat(ShortArrays.containsOnce(ShortArrays.EMPTY, (short) 1)).isFalse();
		final var array = ShortArrays.of((short) 1, (short) 2, (short) 2, (short) 3);
		assertThat(ShortArrays.containsOnce(array, (short) 1)).isTrue();
		assertThat(ShortArrays.containsOnce(array, (short) 2)).isFalse();
		assertThat(ShortArrays.containsOnce(array, (short) 3)).isTrue();
		assertThat(ShortArrays.containsOnce(array, (short) 4)).isFalse();
	}

	@Test
	void testContainsOnceNull() {
		assertThatNullPointerException().isThrownBy(() -> ShortArrays.containsOnce(null, (short) 0));
	}

	@Test
	void testContainsOnly() {
		assertThat(ShortArrays.containsOnly(ShortArrays.EMPTY, (short) 1)).isFalse();
		final var array1 = ShortArrays.of((short) 1, (short) 1);
		assertThat(ShortArrays.containsOnly(array1, (short) 1)).isTrue();
		assertThat(ShortArrays.containsOnly(array1, (short) 2)).isFalse();
		final var array2 = ShortArrays.of((short) 1, (short) 2);
		assertThat(ShortArrays.containsOnly(array2, (short) 1)).isFalse();
		assertThat(ShortArrays.containsOnly(array2, (short) 2)).isFalse();
	}

	@Test
	void testContainsOnlyNull() {
		assertThatNullPointerException().isThrownBy(() -> ShortArrays.containsOnly(null, (short) 0));
	}

	@Test
	void testContainsAny() {
		assertThat(ShortArrays.containsAny(ShortArrays.EMPTY, (short) 1, (short) 2)).isFalse();
		final var array = ShortArrays.of((short) 1, (short) 2, (short) 3);
		assertThat(ShortArrays.containsAny(array)).isFalse();
		assertThat(ShortArrays.containsAny(array, (short) 1, (short) 2)).isTrue();
		assertThat(ShortArrays.containsAny(array, (short) 3, (short) 4)).isTrue();
		assertThat(ShortArrays.containsAny(array, (short) 5, (short) 6)).isFalse();
	}

	@Test
	void testContainsAnyNull() {
		assertThatNullPointerException().isThrownBy(() -> ShortArrays.containsAny(null, (short) 0));
		assertThatNullPointerException().isThrownBy(() -> ShortArrays.containsAny(ShortArrays.of((short) 1, (short) 2, (short) 3), (short[]) null));
	}

	@Test
	void testContainsAll() {
		assertThat(ShortArrays.containsAll(ShortArrays.EMPTY, (short) 1, (short) 2)).isFalse();
		final var array = ShortArrays.of((short) 1, (short) 2, (short) 3);
		assertThat(ShortArrays.containsAll(array)).isFalse();
		assertThat(ShortArrays.containsAll(array, (short) 1, (short) 2, (short) 3)).isTrue();
		assertThat(ShortArrays.containsAll(array, (short) 3, (short) 2, (short) 1)).isTrue();
		assertThat(ShortArrays.containsAll(array, (short) 1, (short) 2)).isTrue();
		assertThat(ShortArrays.containsAll(array, (short) 1, (short) 2, (short) 3, (short) 4)).isFalse();
	}

	@Test
	void testContainsAllNull() {
		assertThatNullPointerException().isThrownBy(() -> ShortArrays.containsAll(null, (short) 0));
		assertThatNullPointerException().isThrownBy(() -> ShortArrays.containsAll(ShortArrays.of((short) 1, (short) 2, (short) 3), (short[]) null));
	}

	@Test
	void testConcat() {
		final var array = ShortArrays.of((short) 1, (short) 2, (short) 3);
		assertThat(ShortArrays.concat(array, array)).isEqualTo(ShortArrays.of((short) 1, (short) 2, (short) 3, (short) 1, (short) 2, (short) 3));
	}

	@Test
	void testConcatOne() {
		final var array = ShortArrays.of((short) 1, (short) 2, (short) 3);
		assertThat(ShortArrays.concat(array)).isEqualTo(array);
	}

	@Test
	void testConcatNone() {
		assertThat(ShortArrays.concat()).isEmpty();
	}

	@Test
	void testConcatNull() {
		assertThatNullPointerException().isThrownBy(() -> ShortArrays.concat((short[][]) null));
		assertThatNullPointerException().isThrownBy(() -> ShortArrays.concat((List<short[]>) null));
		assertThatNullPointerException().isThrownBy(() -> ShortArrays.concat((short[]) null));
	}

	@Test
	void testJoin() {
		final var array = ShortArrays.of((short) 1, (short) 2, (short) 3);
		assertThat(ShortArrays.join(ShortArrays.of((short) 0), array, array)).isEqualTo(ShortArrays.of((short) 1, (short) 2, (short) 3, (short) 0, (short) 1, (short) 2, (short) 3));
	}

	@Test
	void testJoinEmptySeparator() {
		final var array = ShortArrays.of((short) 1, (short) 2, (short) 3);
		assertThat(ShortArrays.join(ShortArrays.EMPTY, array, array)).isEqualTo(ShortArrays.concat(array, array));
	}

	@Test
	void testJoinOne() {
		final var array = ShortArrays.of((short) 1, (short) 2, (short) 3);
		assertThat(ShortArrays.join(ShortArrays.of((short) 0), array)).isEqualTo(array);
	}

	@Test
	void testJoinNone() {
		assertThat(ShortArrays.join(ShortArrays.of((short) 0))).isEmpty();
	}

	@Test
	void testJoinNull() {
		assertThatNullPointerException().isThrownBy(() -> ShortArrays.join(null, ShortArrays.EMPTY));
		assertThatNullPointerException().isThrownBy(() -> ShortArrays.join(ShortArrays.EMPTY, (short[][]) null));
		assertThatNullPointerException().isThrownBy(() -> ShortArrays.join(ShortArrays.EMPTY, (List<short[]>) null));
		assertThatNullPointerException().isThrownBy(() -> ShortArrays.join(ShortArrays.EMPTY, (short[]) null));
	}

	@Test
	void testSingleton() {
		assertThat(ShortArrays.singleton((short) 1)).containsExactly((short) 1);
	}

	@Test
	void testOf() {
		assertThat(ShortArrays.of()).isEmpty();
		assertThat(ShortArrays.of((short) 1, (short) 2, (short) 3)).containsExactly((short) 1, (short) 2, (short) 3);
	}

	@Test
	void testOfNull() {
		assertThatNullPointerException().isThrownBy(() -> ShortArrays.of((short[]) null));
	}
}