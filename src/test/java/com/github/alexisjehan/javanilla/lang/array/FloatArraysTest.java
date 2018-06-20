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
 * <p>{@link FloatArrays} unit tests.</p>
 */
final class FloatArraysTest {

	@Test
	void testEmpty() {
		assertThat(FloatArrays.EMPTY).isEmpty();
	}

	@Test
	void testNullToEmpty() {
		assertThat(FloatArrays.nullToEmpty(null)).isEmpty();
		assertThat(FloatArrays.nullToEmpty(FloatArrays.EMPTY)).isEmpty();
	}

	@Test
	void testNullToDefault() {
		assertThat(FloatArrays.nullToDefault(null, FloatArrays.singleton(1.0f))).containsExactly(1.0f);
		assertThat(FloatArrays.nullToDefault(FloatArrays.EMPTY, FloatArrays.singleton(1.0f))).isEmpty();
	}

	@Test
	void testNullToDefaultNull() {
		assertThatNullPointerException().isThrownBy(() -> FloatArrays.nullToDefault(FloatArrays.EMPTY, null));
	}

	@Test
	void testEmptyToNull() {
		assertThat(FloatArrays.emptyToNull(null)).isNull();
		assertThat(FloatArrays.emptyToNull(FloatArrays.EMPTY)).isNull();
		assertThat(FloatArrays.emptyToNull(FloatArrays.singleton(1.0f))).containsExactly(1.0f);
	}

	@Test
	void testEmptyToDefault() {
		assertThat(FloatArrays.emptyToDefault(null, FloatArrays.singleton(1.0f))).isNull();
		assertThat(FloatArrays.emptyToDefault(FloatArrays.EMPTY, FloatArrays.singleton(1.0f))).containsExactly(1.0f);
		assertThat(FloatArrays.emptyToDefault(FloatArrays.singleton(1.0f), FloatArrays.singleton(1.0f))).containsExactly(1.0f);
	}

	@Test
	void testEmptyToDefaultInvalid() {
		assertThatIllegalArgumentException().isThrownBy(() -> FloatArrays.emptyToDefault(FloatArrays.EMPTY, FloatArrays.EMPTY));
	}

	@Test
	void testIndexOf() {
		final var array = FloatArrays.of(1.0f, 2.0f, 3.0f);
		assertThat(FloatArrays.indexOf(array, 1.0f)).isEqualTo(0);
		assertThat(FloatArrays.indexOf(array, 2.0f)).isEqualTo(1);
		assertThat(FloatArrays.indexOf(array, 3.0f)).isEqualTo(2);
		assertThat(FloatArrays.indexOf(array, 1.0f, 1)).isEqualTo(-1);
		assertThat(FloatArrays.indexOf(array, 2.0f, 2)).isEqualTo(-1);
		assertThat(FloatArrays.indexOf(array, 4.0f)).isEqualTo(-1);
	}

	@Test
	void testIndexOfNull() {
		assertThatNullPointerException().isThrownBy(() -> FloatArrays.indexOf(null, 0.0f));
	}

	@Test
	void testIndexOfInvalidFromIndex() {
		assertThatExceptionOfType(IndexOutOfBoundsException.class).isThrownBy(() -> FloatArrays.indexOf(FloatArrays.of(1.0f), 1.0f, -1));
		assertThatExceptionOfType(IndexOutOfBoundsException.class).isThrownBy(() -> FloatArrays.indexOf(FloatArrays.of(1.0f), 1.0f,  1));
	}

	@Test
	void testLastIndexOf() {
		final var array = FloatArrays.of(1.0f, 2.0f, 3.0f, 1.0f);
		assertThat(FloatArrays.lastIndexOf(array, 1.0f)).isEqualTo(3);
		assertThat(FloatArrays.lastIndexOf(array, 2.0f)).isEqualTo(1);
		assertThat(FloatArrays.lastIndexOf(array, 3.0f)).isEqualTo(2);
		assertThat(FloatArrays.lastIndexOf(array, 1.0f, 1)).isEqualTo( 3);
		assertThat(FloatArrays.lastIndexOf(array, 2.0f, 2)).isEqualTo(-1);
		assertThat(FloatArrays.lastIndexOf(array, 3.0f, 3)).isEqualTo(-1);
		assertThat(FloatArrays.lastIndexOf(array, 4.0f)).isEqualTo(-1);
	}

	@Test
	void testLastIndexOfNull() {
		assertThatNullPointerException().isThrownBy(() -> FloatArrays.lastIndexOf(null, 0.0f));
	}

	@Test
	void testLastIndexOfInvalidFromIndex() {
		assertThatExceptionOfType(IndexOutOfBoundsException.class).isThrownBy(() -> FloatArrays.lastIndexOf(FloatArrays.of(1.0f), 1.0f, -1));
		assertThatExceptionOfType(IndexOutOfBoundsException.class).isThrownBy(() -> FloatArrays.lastIndexOf(FloatArrays.of(1.0f), 1.0f,  1));
	}

	@Test
	void testContains() {
		assertThat(FloatArrays.contains(FloatArrays.EMPTY, 1.0f)).isFalse();
		final var array = FloatArrays.of(1.0f, 2.0f, 3.0f);
		assertThat(FloatArrays.contains(array, 1.0f)).isTrue();
		assertThat(FloatArrays.contains(array, 2.0f)).isTrue();
		assertThat(FloatArrays.contains(array, 3.0f)).isTrue();
		assertThat(FloatArrays.contains(array, 4.0f)).isFalse();
	}

	@Test
	void testContainsNull() {
		assertThatNullPointerException().isThrownBy(() -> FloatArrays.contains(null, 0.0f));
	}

	@Test
	void testContainsOnce() {
		assertThat(FloatArrays.containsOnce(FloatArrays.EMPTY, 1.0f)).isFalse();
		final var array = FloatArrays.of(1.0f, 2.0f, 2.0f, 3.0f);
		assertThat(FloatArrays.containsOnce(array, 1.0f)).isTrue();
		assertThat(FloatArrays.containsOnce(array, 2.0f)).isFalse();
		assertThat(FloatArrays.containsOnce(array, 3.0f)).isTrue();
		assertThat(FloatArrays.containsOnce(array, 4.0f)).isFalse();
	}

	@Test
	void testContainsOnceNull() {
		assertThatNullPointerException().isThrownBy(() -> FloatArrays.containsOnce(null, 0.0f));
	}

	@Test
	void testContainsOnly() {
		assertThat(FloatArrays.containsOnly(FloatArrays.EMPTY, 1.0f)).isFalse();
		final var array1 = FloatArrays.of(1.0f, 1.0f);
		assertThat(FloatArrays.containsOnly(array1, 1.0f)).isTrue();
		assertThat(FloatArrays.containsOnly(array1, 2.0f)).isFalse();
		final var array2 = FloatArrays.of(1.0f, 2.0f);
		assertThat(FloatArrays.containsOnly(array2, 1.0f)).isFalse();
		assertThat(FloatArrays.containsOnly(array2, 2.0f)).isFalse();
	}

	@Test
	void testContainsOnlyNull() {
		assertThatNullPointerException().isThrownBy(() -> FloatArrays.containsOnly(null, 0.0f));
	}

	@Test
	void testContainsAny() {
		assertThat(FloatArrays.containsAny(FloatArrays.EMPTY, 1.0f, 2.0f)).isFalse();
		final var array = FloatArrays.of(1.0f, 2.0f, 3.0f);
		assertThat(FloatArrays.containsAny(array)).isFalse();
		assertThat(FloatArrays.containsAny(array, 1.0f, 2.0f)).isTrue();
		assertThat(FloatArrays.containsAny(array, 3.0f, 4.0f)).isTrue();
		assertThat(FloatArrays.containsAny(array, 5.0f, 6.0f)).isFalse();
	}

	@Test
	void testContainsAnyNull() {
		assertThatNullPointerException().isThrownBy(() -> FloatArrays.containsAny(null, 0.0f));
		assertThatNullPointerException().isThrownBy(() -> FloatArrays.containsAny(FloatArrays.of(1.0f, 2.0f, 3.0f), (float[]) null));
	}

	@Test
	void testContainsAll() {
		assertThat(FloatArrays.containsAll(FloatArrays.EMPTY, 1.0f, 2.0f)).isFalse();
		final var array = FloatArrays.of(1.0f, 2.0f, 3.0f);
		assertThat(FloatArrays.containsAll(array)).isFalse();
		assertThat(FloatArrays.containsAll(array, 1.0f, 2.0f, 3.0f)).isTrue();
		assertThat(FloatArrays.containsAll(array, 3.0f, 2.0f, 1.0f)).isTrue();
		assertThat(FloatArrays.containsAll(array, 1.0f, 2.0f)).isTrue();
		assertThat(FloatArrays.containsAll(array, 1.0f, 2.0f, 3.0f, 4.0f)).isFalse();
	}

	@Test
	void testContainsAllNull() {
		assertThatNullPointerException().isThrownBy(() -> FloatArrays.containsAll(null, 0.0f));
		assertThatNullPointerException().isThrownBy(() -> FloatArrays.containsAll(FloatArrays.of(1.0f, 2.0f, 3.0f), (float[]) null));
	}

	@Test
	void testConcat() {
		final var array = FloatArrays.of(1.0f, 2.0f, 3.0f);
		assertThat(FloatArrays.concat(array, array)).isEqualTo(FloatArrays.of(1.0f, 2.0f, 3.0f, 1.0f, 2.0f, 3.0f));
	}

	@Test
	void testConcatOne() {
		final var array = FloatArrays.of(1.0f, 2.0f, 3.0f);
		assertThat(FloatArrays.concat(array)).isEqualTo(array);
	}

	@Test
	void testConcatNone() {
		assertThat(FloatArrays.concat()).isEmpty();
	}

	@Test
	void testConcatNull() {
		assertThatNullPointerException().isThrownBy(() -> FloatArrays.concat((float[][]) null));
		assertThatNullPointerException().isThrownBy(() -> FloatArrays.concat((List<float[]>) null));
		assertThatNullPointerException().isThrownBy(() -> FloatArrays.concat((float[]) null));
	}

	@Test
	void testJoin() {
		final var array = FloatArrays.of(1.0f, 2.0f, 3.0f);
		assertThat(FloatArrays.join(FloatArrays.of(0.0f), array, array)).isEqualTo(FloatArrays.of(1.0f, 2.0f, 3.0f, 0.0f, 1.0f, 2.0f, 3.0f));
	}

	@Test
	void testJoinEmptySeparator() {
		final var array = FloatArrays.of(1.0f, 2.0f, 3.0f);
		assertThat(FloatArrays.join(FloatArrays.EMPTY, array, array)).isEqualTo(FloatArrays.concat(array, array));
	}

	@Test
	void testJoinOne() {
		final var array = FloatArrays.of(1.0f, 2.0f, 3.0f);
		assertThat(FloatArrays.join(FloatArrays.of(0.0f), array)).isEqualTo(array);
	}

	@Test
	void testJoinNone() {
		assertThat(FloatArrays.join(FloatArrays.of(0.0f))).isEmpty();
	}

	@Test
	void testJoinNull() {
		assertThatNullPointerException().isThrownBy(() -> FloatArrays.join(null, FloatArrays.EMPTY));
		assertThatNullPointerException().isThrownBy(() -> FloatArrays.join(FloatArrays.EMPTY, (float[][]) null));
		assertThatNullPointerException().isThrownBy(() -> FloatArrays.join(FloatArrays.EMPTY, (List<float[]>) null));
		assertThatNullPointerException().isThrownBy(() -> FloatArrays.join(FloatArrays.EMPTY, (float[]) null));
	}

	@Test
	void testSingleton() {
		assertThat(FloatArrays.singleton(1.0f)).containsExactly(1.0f);
	}

	@Test
	void testOf() {
		assertThat(FloatArrays.of()).isEmpty();
		assertThat(FloatArrays.of(1.0f, 2.0f, 3.0f)).containsExactly(1.0f, 2.0f, 3.0f);
	}

	@Test
	void testOfNull() {
		assertThatNullPointerException().isThrownBy(() -> FloatArrays.of((float[]) null));
	}
}