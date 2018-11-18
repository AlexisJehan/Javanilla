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
		assertThat(DoubleArrays.nullToEmpty(DoubleArrays.singleton(1.0d))).containsExactly(1.0d);
	}

	@Test
	void testNullToDefault() {
		assertThat(DoubleArrays.nullToDefault(null, DoubleArrays.singleton(0.0d))).containsExactly(0.0d);
		assertThat(DoubleArrays.nullToDefault(DoubleArrays.EMPTY, DoubleArrays.singleton(0.0d))).isEmpty();
		assertThat(DoubleArrays.nullToDefault(DoubleArrays.singleton(1.0d), DoubleArrays.singleton(0.0d))).containsExactly(1.0d);
	}

	@Test
	void testNullToDefaultInvalid() {
		assertThatNullPointerException().isThrownBy(() -> DoubleArrays.nullToDefault(DoubleArrays.singleton(1.0d), null));
	}

	@Test
	void testEmptyToNull() {
		assertThat(DoubleArrays.emptyToNull(null)).isNull();
		assertThat(DoubleArrays.emptyToNull(DoubleArrays.EMPTY)).isNull();
		assertThat(DoubleArrays.emptyToNull(DoubleArrays.singleton(1.0d))).containsExactly(1.0d);
	}

	@Test
	void testEmptyToDefault() {
		assertThat(DoubleArrays.emptyToDefault(null, DoubleArrays.singleton(0.0d))).isNull();
		assertThat(DoubleArrays.emptyToDefault(DoubleArrays.EMPTY, DoubleArrays.singleton(0.0d))).containsExactly(0.0d);
		assertThat(DoubleArrays.emptyToDefault(DoubleArrays.singleton(1.0d), DoubleArrays.singleton(0.0d))).containsExactly(1.0d);
	}

	@Test
	void testEmptyToDefaultInvalid() {
		assertThatIllegalArgumentException().isThrownBy(() -> DoubleArrays.emptyToDefault(DoubleArrays.singleton(1.0d), DoubleArrays.EMPTY));
	}

	@Test
	void testIsEmpty() {
		assertThat(DoubleArrays.isEmpty(DoubleArrays.EMPTY)).isTrue();
		assertThat(DoubleArrays.isEmpty(DoubleArrays.singleton(1.0d))).isFalse();
	}

	@Test
	void testIsEmptyInvalid() {
		assertThatNullPointerException().isThrownBy(() -> DoubleArrays.isEmpty(null));
	}

	@Test
	void testContainsAny() {
		assertThat(DoubleArrays.containsAny(DoubleArrays.EMPTY, 1.0d)).isFalse();
		assertThat(DoubleArrays.containsAny(DoubleArrays.singleton(1.0d), 1.0d)).isTrue();
		assertThat(DoubleArrays.containsAny(DoubleArrays.singleton(1.0d), 2.0d)).isFalse();
		assertThat(DoubleArrays.containsAny(DoubleArrays.singleton(1.0d), 1.0d, 2.0d)).isTrue();
	}

	@Test
	void testContainsAnyInvalid() {
		assertThatNullPointerException().isThrownBy(() -> DoubleArrays.containsAny(null, 1.0d));
		assertThatNullPointerException().isThrownBy(() -> DoubleArrays.containsAny(DoubleArrays.singleton(1.0d), (double[]) null));
		assertThatIllegalArgumentException().isThrownBy(() -> DoubleArrays.containsAny(DoubleArrays.singleton(1.0d)));
	}

	@Test
	void testContainsAll() {
		assertThat(DoubleArrays.containsAll(DoubleArrays.EMPTY, 1.0d)).isFalse();
		assertThat(DoubleArrays.containsAll(DoubleArrays.singleton(1.0d), 1.0d)).isTrue();
		assertThat(DoubleArrays.containsAll(DoubleArrays.singleton(1.0d), 2.0d)).isFalse();
		assertThat(DoubleArrays.containsAll(DoubleArrays.singleton(1.0d), 1.0d, 2.0d)).isFalse();
		assertThat(DoubleArrays.containsAll(DoubleArrays.of(1.0d, 2.0d), 1.0d)).isTrue();
		assertThat(DoubleArrays.containsAll(DoubleArrays.of(1.0d, 2.0d), 2.0d)).isTrue();
		assertThat(DoubleArrays.containsAll(DoubleArrays.of(1.0d, 2.0d), 1.0d, 2.0d)).isTrue();
	}

	@Test
	void testContainsAllInvalid() {
		assertThatNullPointerException().isThrownBy(() -> DoubleArrays.containsAll(null, 1.0d));
		assertThatNullPointerException().isThrownBy(() -> DoubleArrays.containsAll(DoubleArrays.singleton(1.0d), (double[]) null));
		assertThatIllegalArgumentException().isThrownBy(() -> DoubleArrays.containsAll(DoubleArrays.singleton(1.0d)));
	}

	@Test
	void testContainsOnce() {
		assertThat(DoubleArrays.containsOnce(DoubleArrays.EMPTY, 1.0d)).isFalse();
		assertThat(DoubleArrays.containsOnce(DoubleArrays.singleton(1.0d), 1.0d)).isTrue();
		assertThat(DoubleArrays.containsOnce(DoubleArrays.singleton(1.0d), 2.0d)).isFalse();
		assertThat(DoubleArrays.containsOnce(DoubleArrays.singleton(1.0d), 1.0d, 2.0d)).isFalse();
		assertThat(DoubleArrays.containsOnce(DoubleArrays.of(1.0d, 1.0d), 1.0d)).isFalse();
		assertThat(DoubleArrays.containsOnce(DoubleArrays.of(1.0d, 1.0d), 2.0d)).isFalse();
		assertThat(DoubleArrays.containsOnce(DoubleArrays.of(1.0d, 1.0d), 1.0d, 2.0d)).isFalse();
	}

	@Test
	void testContainsOnceInvalid() {
		assertThatNullPointerException().isThrownBy(() -> DoubleArrays.containsOnce(null, 1.0d));
		assertThatNullPointerException().isThrownBy(() -> DoubleArrays.containsOnce(DoubleArrays.singleton(1.0d), (double[]) null));
		assertThatIllegalArgumentException().isThrownBy(() -> DoubleArrays.containsOnce(DoubleArrays.singleton(1.0d)));
	}

	@Test
	void testContainsOnly() {
		assertThat(DoubleArrays.containsOnly(DoubleArrays.EMPTY, 1.0d)).isFalse();
		assertThat(DoubleArrays.containsOnly(DoubleArrays.singleton(1.0d), 1.0d)).isTrue();
		assertThat(DoubleArrays.containsOnly(DoubleArrays.singleton(1.0d), 2.0d)).isFalse();
		assertThat(DoubleArrays.containsOnly(DoubleArrays.singleton(1.0d), 1.0d, 2.0d)).isTrue();
		assertThat(DoubleArrays.containsOnly(DoubleArrays.of(1.0d, 2.0d), 1.0d)).isFalse();
		assertThat(DoubleArrays.containsOnly(DoubleArrays.of(1.0d, 2.0d), 2.0d)).isFalse();
		assertThat(DoubleArrays.containsOnly(DoubleArrays.of(1.0d, 2.0d), 1.0d, 2.0d)).isTrue();
	}

	@Test
	void testContainsOnlyInvalid() {
		assertThatNullPointerException().isThrownBy(() -> DoubleArrays.containsOnly(null, 1.0d));
		assertThatNullPointerException().isThrownBy(() -> DoubleArrays.containsOnly(DoubleArrays.singleton(1.0d), (double[]) null));
		assertThatIllegalArgumentException().isThrownBy(() -> DoubleArrays.containsOnly(DoubleArrays.singleton(1.0d)));
	}

	@Test
	void testIndexOf() {
		assertThat(DoubleArrays.indexOf(DoubleArrays.EMPTY, 1.0d)).isEqualTo(-1);
		final var array = DoubleArrays.of(1.0d, 2.0d, 1.0d);
		assertThat(DoubleArrays.indexOf(array, 1.0d)).isEqualTo(0);
		assertThat(DoubleArrays.indexOf(array, 2.0d)).isEqualTo(1);
		assertThat(DoubleArrays.indexOf(array, 1.0d, 1)).isEqualTo(2);
		assertThat(DoubleArrays.indexOf(array, 2.0d, 2)).isEqualTo(-1);
	}

	@Test
	void testIndexOfInvalid() {
		assertThatNullPointerException().isThrownBy(() -> DoubleArrays.indexOf(null, 1.0d));
		assertThatIllegalArgumentException().isThrownBy(() -> DoubleArrays.indexOf(DoubleArrays.singleton(1.0d), 1.0d, -1));
		assertThatIllegalArgumentException().isThrownBy(() -> DoubleArrays.indexOf(DoubleArrays.singleton(1.0d), 1.0d, 1));
	}

	@Test
	void testLastIndexOf() {
		assertThat(DoubleArrays.lastIndexOf(DoubleArrays.EMPTY, 1.0d)).isEqualTo(-1);
		final var array = DoubleArrays.of(1.0d, 2.0d, 1.0d);
		assertThat(DoubleArrays.lastIndexOf(array, 1.0d)).isEqualTo(2);
		assertThat(DoubleArrays.lastIndexOf(array, 2.0d)).isEqualTo(1);
		assertThat(DoubleArrays.lastIndexOf(array, 1.0d, 1)).isEqualTo(2);
		assertThat(DoubleArrays.lastIndexOf(array, 2.0d, 2)).isEqualTo(-1);
	}

	@Test
	void testLastIndexOfInvalid() {
		assertThatNullPointerException().isThrownBy(() -> DoubleArrays.lastIndexOf(null, 1.0d));
		assertThatIllegalArgumentException().isThrownBy(() -> DoubleArrays.lastIndexOf(DoubleArrays.singleton(1.0d), 1.0d, -1));
		assertThatIllegalArgumentException().isThrownBy(() -> DoubleArrays.lastIndexOf(DoubleArrays.singleton(1.0d), 1.0d, 1));
	}

	@Test
	void testFrequency() {
		assertThat(DoubleArrays.frequency(DoubleArrays.EMPTY, 1.0d)).isEqualTo(0);
		final var array = DoubleArrays.of(1.0d, 2.0d, 1.0d);
		assertThat(DoubleArrays.frequency(array, 1.0d)).isEqualTo(2);
		assertThat(DoubleArrays.frequency(array, 2.0d)).isEqualTo(1);
	}

	@Test
	void testFrequencyInvalid() {
		assertThatNullPointerException().isThrownBy(() -> DoubleArrays.frequency(null, 1.0d));
	}

	@Test
	void testShuffle() {
		{
			final var array = DoubleArrays.singleton(1.0d);
			DoubleArrays.shuffle(array);
			assertThat(array).containsExactly(1.0d);
		}
		{
			final var array = DoubleArrays.of(1.0d, 2.0d, 1.0d, 2.0d);
			DoubleArrays.shuffle(array);
			assertThat(array).containsExactlyInAnyOrder(1.0d, 2.0d, 1.0d, 2.0d);
		}
	}

	@Test
	void testShuffleInvalid() {
		assertThatNullPointerException().isThrownBy(() -> DoubleArrays.shuffle(null));
	}

	@Test
	void testReverse() {
		{
			final var array = DoubleArrays.singleton(1.0d);
			DoubleArrays.reverse(array);
			assertThat(array).containsExactly(1.0d);
		}
		{
			// Even
			final var array = DoubleArrays.of(1.0d, 2.0d, 1.0d, 2.0d);
			DoubleArrays.reverse(array);
			assertThat(array).containsExactly(2.0d, 1.0d, 2.0d, 1.0d);
		}
		{
			// Odd
			final var array = DoubleArrays.of(1.0d, 2.0d, 2.0d);
			DoubleArrays.reverse(array);
			assertThat(array).containsExactly(2.0d, 2.0d, 1.0d);
		}
	}

	@Test
	void testReverseInvalid() {
		assertThatNullPointerException().isThrownBy(() -> DoubleArrays.reverse(null));
	}

	@Test
	void testReorder() {
		{
			final var array = DoubleArrays.singleton(1.0d);
			DoubleArrays.reorder(array, 0);
			assertThat(array).containsExactly(1.0d);
		}
		{
			final var array = DoubleArrays.of(1.0d, 2.0d, 1.0d, 2.0d);
			DoubleArrays.reorder(array, 2, 0, 3, 1);
			assertThat(array).containsExactly(1.0d, 1.0d, 2.0d, 2.0d);
		}
	}

	@Test
	void testReorderInvalid() {
		assertThatNullPointerException().isThrownBy(() -> DoubleArrays.reorder(null));
		assertThatNullPointerException().isThrownBy(() -> DoubleArrays.reorder(DoubleArrays.singleton(1.0d), (int[]) null));
		assertThatIllegalArgumentException().isThrownBy(() -> DoubleArrays.reorder(DoubleArrays.of(1.0d, 2.0d), IntArrays.singleton(0)));
		assertThatIllegalArgumentException().isThrownBy(() -> DoubleArrays.reorder(DoubleArrays.of(1.0d, 2.0d), IntArrays.of(0, 0)));
		assertThatIllegalArgumentException().isThrownBy(() -> DoubleArrays.reorder(DoubleArrays.of(1.0d, 2.0d), IntArrays.of(-1, 1)));
		assertThatIllegalArgumentException().isThrownBy(() -> DoubleArrays.reorder(DoubleArrays.of(1.0d, 2.0d), IntArrays.of(2, 1)));
		assertThatIllegalArgumentException().isThrownBy(() -> DoubleArrays.reorder(DoubleArrays.of(1.0d, 2.0d), IntArrays.of(0, -1)));
		assertThatIllegalArgumentException().isThrownBy(() -> DoubleArrays.reorder(DoubleArrays.of(1.0d, 2.0d), IntArrays.of(0, 2)));
	}

	@Test
	void testSwap() {
		{
			final var array = DoubleArrays.singleton(1.0d);
			DoubleArrays.swap(array, 0, 0);
			assertThat(array).containsExactly(1.0d);
		}
		{
			final var array = DoubleArrays.of(1.0d, 2.0d, 1.0d, 2.0d);
			DoubleArrays.swap(array, 1, 2);
			assertThat(array).containsExactly(1.0d, 1.0d, 2.0d, 2.0d);
		}
	}

	@Test
	void testSwapInvalid() {
		assertThatNullPointerException().isThrownBy(() -> DoubleArrays.swap(null, 0, 0));
		assertThatIllegalArgumentException().isThrownBy(() -> DoubleArrays.swap(DoubleArrays.of(1.0d, 2.0d), -1, 1));
		assertThatIllegalArgumentException().isThrownBy(() -> DoubleArrays.swap(DoubleArrays.of(1.0d, 2.0d), 2, 1));
		assertThatIllegalArgumentException().isThrownBy(() -> DoubleArrays.swap(DoubleArrays.of(1.0d, 2.0d), 0, -1));
		assertThatIllegalArgumentException().isThrownBy(() -> DoubleArrays.swap(DoubleArrays.of(1.0d, 2.0d), 0, 2));
	}

	@Test
	void testConcat() {
		assertThat(DoubleArrays.concat()).isEmpty();
		assertThat(DoubleArrays.concat(DoubleArrays.singleton(1.0d))).containsExactly(1.0d);
		assertThat(DoubleArrays.concat(DoubleArrays.singleton(1.0d), DoubleArrays.singleton(2.0d))).containsExactly(1.0d, 2.0d);
	}

	@Test
	void testConcatInvalid() {
		assertThatNullPointerException().isThrownBy(() -> DoubleArrays.concat((double[][]) null));
		assertThatNullPointerException().isThrownBy(() -> DoubleArrays.concat((List<double[]>) null));
		assertThatNullPointerException().isThrownBy(() -> DoubleArrays.concat((double[]) null));
	}

	@Test
	void testJoin() {
		assertThat(DoubleArrays.join(DoubleArrays.EMPTY, DoubleArrays.singleton(1.0d), DoubleArrays.singleton(2.0d))).containsExactly(1.0d, 2.0d);
		assertThat(DoubleArrays.join(DoubleArrays.singleton(0.0d))).isEmpty();
		assertThat(DoubleArrays.join(DoubleArrays.singleton(0.0d), DoubleArrays.singleton(1.0d))).containsExactly(1.0d);
		assertThat(DoubleArrays.join(DoubleArrays.singleton(0.0d), DoubleArrays.singleton(1.0d), DoubleArrays.singleton(2.0d))).containsExactly(1.0d, 0.0d, 2.0d);
	}

	@Test
	void testJoinInvalid() {
		assertThatNullPointerException().isThrownBy(() -> DoubleArrays.join(null, DoubleArrays.singleton(1.0d)));
		assertThatNullPointerException().isThrownBy(() -> DoubleArrays.join(DoubleArrays.singleton(0.0d), (double[][]) null));
		assertThatNullPointerException().isThrownBy(() -> DoubleArrays.join(DoubleArrays.singleton(0.0d), (List<double[]>) null));
		assertThatNullPointerException().isThrownBy(() -> DoubleArrays.join(DoubleArrays.singleton(0.0d), (double[]) null));
	}

	@Test
	void testSingleton() {
		assertThat(DoubleArrays.singleton(1.0d)).containsExactly(1.0d);
	}

	@Test
	void testOf() {
		assertThat(DoubleArrays.of()).isEmpty();
		assertThat(DoubleArrays.of(1.0d, 2.0d)).containsExactly(1.0d, 2.0d);
	}

	@Test
	void testOfInvalid() {
		assertThatNullPointerException().isThrownBy(() -> DoubleArrays.of((double[]) null));
	}

	@Test
	void testOfBoxedToBoxed() {
		assertThat(DoubleArrays.of(DoubleArrays.toBoxed(DoubleArrays.EMPTY))).isEmpty();
		assertThat(DoubleArrays.of(DoubleArrays.toBoxed(DoubleArrays.of(1.0d, 2.0d)))).containsExactly(1.0d, 2.0d);
		assertThat(DoubleArrays.toBoxed(DoubleArrays.singleton(1.0d))).isInstanceOf(Double[].class);
		assertThat(DoubleArrays.of(DoubleArrays.toBoxed(DoubleArrays.singleton(1.0d)))).isInstanceOf(double[].class);
	}

	@Test
	void testOfBoxedInvalid() {
		assertThatNullPointerException().isThrownBy(() -> DoubleArrays.of((Double[]) null));
	}

	@Test
	void testToBoxedInvalid() {
		assertThatNullPointerException().isThrownBy(() -> DoubleArrays.toBoxed(null));
	}
}