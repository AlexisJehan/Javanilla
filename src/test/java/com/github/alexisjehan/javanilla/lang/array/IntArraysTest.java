/*
 * MIT License
 *
 * Copyright (c) 2018-2020 Alexis Jehan
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
package com.github.alexisjehan.javanilla.lang.array;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
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
		assertThat(IntArrays.nullToEmpty(IntArrays.singleton(1))).containsExactly(1);
	}

	@Test
	void testNullToDefault() {
		assertThat(IntArrays.nullToDefault(null, IntArrays.singleton(0))).containsExactly(0);
		assertThat(IntArrays.nullToDefault(IntArrays.EMPTY, IntArrays.singleton(0))).isEmpty();
		assertThat(IntArrays.nullToDefault(IntArrays.singleton(1), IntArrays.singleton(0))).containsExactly(1);
	}

	@Test
	void testNullToDefaultInvalid() {
		assertThatNullPointerException().isThrownBy(() -> IntArrays.nullToDefault(IntArrays.singleton(1), null));
	}

	@Test
	void testEmptyToNull() {
		assertThat(IntArrays.emptyToNull(IntArrays.EMPTY)).isNull();
		assertThat(IntArrays.emptyToNull(null)).isNull();
		assertThat(IntArrays.emptyToNull(IntArrays.of(1))).containsExactly(1);
	}

	@Test
	void testEmptyToDefault() {
		assertThat(IntArrays.emptyToDefault(null, IntArrays.singleton(0))).isNull();
		assertThat(IntArrays.emptyToDefault(IntArrays.EMPTY, IntArrays.singleton(0))).containsExactly(0);
		assertThat(IntArrays.emptyToDefault(IntArrays.singleton(1), IntArrays.singleton(0))).containsExactly(1);
	}

	@Test
	void testEmptyToDefaultInvalid() {
		assertThatIllegalArgumentException().isThrownBy(() -> IntArrays.emptyToDefault(IntArrays.singleton(1), IntArrays.EMPTY));
	}

	@Test
	void testIsEmpty() {
		assertThat(IntArrays.isEmpty(IntArrays.EMPTY)).isTrue();
		assertThat(IntArrays.isEmpty(IntArrays.singleton(1))).isFalse();
	}

	@Test
	void testIsEmptyInvalid() {
		assertThatNullPointerException().isThrownBy(() -> IntArrays.isEmpty(null));
	}

	@Test
	void testContainsAny() {
		assertThat(IntArrays.containsAny(IntArrays.EMPTY, 1)).isFalse();
		assertThat(IntArrays.containsAny(IntArrays.singleton(1), 1)).isTrue();
		assertThat(IntArrays.containsAny(IntArrays.singleton(1), 2)).isFalse();
		assertThat(IntArrays.containsAny(IntArrays.singleton(1), 1, 2)).isTrue();
	}

	@Test
	void testContainsAnyInvalid() {
		assertThatNullPointerException().isThrownBy(() -> IntArrays.containsAny(null, 1));
		assertThatNullPointerException().isThrownBy(() -> IntArrays.containsAny(IntArrays.singleton(1), (int[]) null));
		assertThatIllegalArgumentException().isThrownBy(() -> IntArrays.containsAny(IntArrays.singleton(1)));
	}

	@Test
	void testContainsAll() {
		assertThat(IntArrays.containsAll(IntArrays.EMPTY, 1)).isFalse();
		assertThat(IntArrays.containsAll(IntArrays.singleton(1), 1)).isTrue();
		assertThat(IntArrays.containsAll(IntArrays.singleton(1), 2)).isFalse();
		assertThat(IntArrays.containsAll(IntArrays.singleton(1), 1, 2)).isFalse();
		assertThat(IntArrays.containsAll(IntArrays.of(1, 2), 1)).isTrue();
		assertThat(IntArrays.containsAll(IntArrays.of(1, 2), 2)).isTrue();
		assertThat(IntArrays.containsAll(IntArrays.of(1, 2), 1, 2)).isTrue();
	}

	@Test
	void testContainsAllInvalid() {
		assertThatNullPointerException().isThrownBy(() -> IntArrays.containsAll(null, 1));
		assertThatNullPointerException().isThrownBy(() -> IntArrays.containsAll(IntArrays.singleton(1), (int[]) null));
		assertThatIllegalArgumentException().isThrownBy(() -> IntArrays.containsAll(IntArrays.singleton(1)));
	}

	@Test
	void testContainsOnce() {
		assertThat(IntArrays.containsOnce(IntArrays.EMPTY, 1)).isFalse();
		assertThat(IntArrays.containsOnce(IntArrays.singleton(1), 1)).isTrue();
		assertThat(IntArrays.containsOnce(IntArrays.singleton(1), 2)).isFalse();
		assertThat(IntArrays.containsOnce(IntArrays.singleton(1), 1, 2)).isFalse();
		assertThat(IntArrays.containsOnce(IntArrays.of(1, 1), 1)).isFalse();
		assertThat(IntArrays.containsOnce(IntArrays.of(1, 1), 2)).isFalse();
		assertThat(IntArrays.containsOnce(IntArrays.of(1, 1), 1, 2)).isFalse();
	}

	@Test
	void testContainsOnceInvalid() {
		assertThatNullPointerException().isThrownBy(() -> IntArrays.containsOnce(null, 1));
		assertThatNullPointerException().isThrownBy(() -> IntArrays.containsOnce(IntArrays.singleton(1), (int[]) null));
		assertThatIllegalArgumentException().isThrownBy(() -> IntArrays.containsOnce(IntArrays.singleton(1)));
	}

	@Test
	void testContainsOnly() {
		assertThat(IntArrays.containsOnly(IntArrays.EMPTY, 1)).isFalse();
		assertThat(IntArrays.containsOnly(IntArrays.singleton(1), 1)).isTrue();
		assertThat(IntArrays.containsOnly(IntArrays.singleton(1), 2)).isFalse();
		assertThat(IntArrays.containsOnly(IntArrays.singleton(1), 1, 2)).isTrue();
		assertThat(IntArrays.containsOnly(IntArrays.of(1, 2), 1)).isFalse();
		assertThat(IntArrays.containsOnly(IntArrays.of(1, 2), 2)).isFalse();
		assertThat(IntArrays.containsOnly(IntArrays.of(1, 2), 1, 2)).isTrue();
	}

	@Test
	void testContainsOnlyInvalid() {
		assertThatNullPointerException().isThrownBy(() -> IntArrays.containsOnly(null, 1));
		assertThatNullPointerException().isThrownBy(() -> IntArrays.containsOnly(IntArrays.singleton(1), (int[]) null));
		assertThatIllegalArgumentException().isThrownBy(() -> IntArrays.containsOnly(IntArrays.singleton(1)));
	}

	@Test
	void testIndexOf() {
		assertThat(IntArrays.indexOf(IntArrays.EMPTY, 1)).isEqualTo(-1);
		final var array = IntArrays.of(1, 2, 1);
		assertThat(IntArrays.indexOf(array, 1)).isEqualTo(0);
		assertThat(IntArrays.indexOf(array, 2)).isEqualTo(1);
		assertThat(IntArrays.indexOf(array, 1, 1)).isEqualTo(2);
		assertThat(IntArrays.indexOf(array, 2, 2)).isEqualTo(-1);
	}

	@Test
	void testIndexOfInvalid() {
		assertThatNullPointerException().isThrownBy(() -> IntArrays.indexOf(null, 1));
		assertThatIllegalArgumentException().isThrownBy(() -> IntArrays.indexOf(IntArrays.singleton(1), 1, -1));
		assertThatIllegalArgumentException().isThrownBy(() -> IntArrays.indexOf(IntArrays.singleton(1), 1, 1));
	}

	@Test
	void testLastIndexOf() {
		assertThat(IntArrays.lastIndexOf(IntArrays.EMPTY, 1)).isEqualTo(-1);
		final var array = IntArrays.of(1, 2, 1);
		assertThat(IntArrays.lastIndexOf(array, 1)).isEqualTo(2);
		assertThat(IntArrays.lastIndexOf(array, 2)).isEqualTo(1);
		assertThat(IntArrays.lastIndexOf(array, 1, 1)).isEqualTo(2);
		assertThat(IntArrays.lastIndexOf(array, 2, 2)).isEqualTo(-1);
	}

	@Test
	void testLastIndexOfInvalid() {
		assertThatNullPointerException().isThrownBy(() -> IntArrays.lastIndexOf(null, 1));
		assertThatIllegalArgumentException().isThrownBy(() -> IntArrays.lastIndexOf(IntArrays.singleton(1), 1, -1));
		assertThatIllegalArgumentException().isThrownBy(() -> IntArrays.lastIndexOf(IntArrays.singleton(1), 1, 1));
	}

	@Test
	void testFrequency() {
		assertThat(IntArrays.frequency(IntArrays.EMPTY, 1)).isEqualTo(0);
		final var array = IntArrays.of(1, 2, 1);
		assertThat(IntArrays.frequency(array, 1)).isEqualTo(2);
		assertThat(IntArrays.frequency(array, 2)).isEqualTo(1);
	}

	@Test
	void testFrequencyInvalid() {
		assertThatNullPointerException().isThrownBy(() -> IntArrays.frequency(null, 1));
	}

	@Test
	void testShuffle() {
		{
			final var array = IntArrays.singleton(1);
			IntArrays.shuffle(array);
			assertThat(array).containsExactly(1);
		}
		{
			final var array = IntArrays.of(1, 2, 1, 2);
			IntArrays.shuffle(array);
			assertThat(array).containsExactlyInAnyOrder(1, 2, 1, 2);
		}
	}

	@Test
	void testShuffleInvalid() {
		assertThatNullPointerException().isThrownBy(() -> IntArrays.shuffle(null));
	}

	@Test
	void testReverse() {
		{
			final var array = IntArrays.singleton(1);
			IntArrays.reverse(array);
			assertThat(array).containsExactly(1);
		}
		{
			// Even
			final var array = IntArrays.of(1, 2, 1, 2);
			IntArrays.reverse(array);
			assertThat(array).containsExactly(2, 1, 2, 1);
		}
		{
			// Odd
			final var array = IntArrays.of(1, 2, 2);
			IntArrays.reverse(array);
			assertThat(array).containsExactly(2, 2, 1);
		}
	}

	@Test
	void testReverseInvalid() {
		assertThatNullPointerException().isThrownBy(() -> IntArrays.reverse(null));
	}

	@Test
	void testReorder() {
		{
			final var array = IntArrays.singleton(1);
			IntArrays.reorder(array, 0);
			assertThat(array).containsExactly(1);
		}
		{
			final var array = IntArrays.of(1, 2, 1, 2);
			IntArrays.reorder(array, 2, 0, 3, 1);
			assertThat(array).containsExactly(1, 1, 2, 2);
		}
	}

	@Test
	void testReorderInvalid() {
		assertThatNullPointerException().isThrownBy(() -> IntArrays.reorder(null));
		assertThatNullPointerException().isThrownBy(() -> IntArrays.reorder(IntArrays.singleton(1), (int[]) null));
		assertThatIllegalArgumentException().isThrownBy(() -> IntArrays.reorder(IntArrays.of(1, 2), IntArrays.singleton(0)));
		assertThatIllegalArgumentException().isThrownBy(() -> IntArrays.reorder(IntArrays.of(1, 2), IntArrays.of(0, 0)));
		assertThatIllegalArgumentException().isThrownBy(() -> IntArrays.reorder(IntArrays.of(1, 2), IntArrays.of(-1, 1)));
		assertThatIllegalArgumentException().isThrownBy(() -> IntArrays.reorder(IntArrays.of(1, 2), IntArrays.of(2, 1)));
		assertThatIllegalArgumentException().isThrownBy(() -> IntArrays.reorder(IntArrays.of(1, 2), IntArrays.of(0, -1)));
		assertThatIllegalArgumentException().isThrownBy(() -> IntArrays.reorder(IntArrays.of(1, 2), IntArrays.of(0, 2)));
	}

	@Test
	void testSwap() {
		{
			final var array = IntArrays.singleton(1);
			IntArrays.swap(array, 0, 0);
			assertThat(array).containsExactly(1);
		}
		{
			final var array = IntArrays.of(1, 2, 1, 2);
			IntArrays.swap(array, 1, 2);
			assertThat(array).containsExactly(1, 1, 2, 2);
		}
	}

	@Test
	void testSwapInvalid() {
		assertThatNullPointerException().isThrownBy(() -> IntArrays.swap(null, 0, 0));
		assertThatIllegalArgumentException().isThrownBy(() -> IntArrays.swap(IntArrays.of(1, 2), -1, 1));
		assertThatIllegalArgumentException().isThrownBy(() -> IntArrays.swap(IntArrays.of(1, 2), 2, 1));
		assertThatIllegalArgumentException().isThrownBy(() -> IntArrays.swap(IntArrays.of(1, 2), 0, -1));
		assertThatIllegalArgumentException().isThrownBy(() -> IntArrays.swap(IntArrays.of(1, 2), 0, 2));
	}

	@Test
	void testAdd() {
		assertThat(IntArrays.add(IntArrays.EMPTY, 0)).containsExactly(0);
		assertThat(IntArrays.add(IntArrays.of(1, 2, 3), 0, 0)).containsExactly(0, 1, 2, 3);
		assertThat(IntArrays.add(IntArrays.of(1, 2, 3), 1, 0)).containsExactly(1, 0, 2, 3);
		assertThat(IntArrays.add(IntArrays.of(1, 2, 3), 2, 0)).containsExactly(1, 2, 0, 3);
		assertThat(IntArrays.add(IntArrays.of(1, 2, 3), 3, 0)).containsExactly(1, 2, 3, 0);
		assertThat(IntArrays.add(IntArrays.of(1, 2, 3), 0)).containsExactly(1, 2, 3, 0);
	}

	@Test
	void testAddInvalid() {
		assertThatNullPointerException().isThrownBy(() -> IntArrays.add(null, 0));
		assertThatNullPointerException().isThrownBy(() -> IntArrays.add(null, 0, 0));
		assertThatIllegalArgumentException().isThrownBy(() -> IntArrays.add(IntArrays.singleton(1), -1, 0));
		assertThatIllegalArgumentException().isThrownBy(() -> IntArrays.add(IntArrays.singleton(1), 2, 0));
	}

	@Test
	void testRemove() {
		assertThat(IntArrays.remove(IntArrays.singleton(1), 0)).isEmpty();
		assertThat(IntArrays.remove(IntArrays.of(1, 2, 3), 0)).containsExactly(2, 3);
		assertThat(IntArrays.remove(IntArrays.of(1, 2, 3), 1)).containsExactly(1, 3);
		assertThat(IntArrays.remove(IntArrays.of(1, 2, 3), 2)).containsExactly(1, 2);
	}

	@Test
	void testRemoveInvalid() {
		assertThatNullPointerException().isThrownBy(() -> IntArrays.remove(null, 0));
		assertThatIllegalArgumentException().isThrownBy(() -> IntArrays.remove(IntArrays.EMPTY, 0));
		assertThatIllegalArgumentException().isThrownBy(() -> IntArrays.remove(IntArrays.singleton(1), -1));
		assertThatIllegalArgumentException().isThrownBy(() -> IntArrays.remove(IntArrays.singleton(1), 1));
	}

	@Test
	void testConcat() {
		assertThat(IntArrays.concat()).isEmpty();
		assertThat(IntArrays.concat(IntArrays.singleton(1))).containsExactly(1);
		assertThat(IntArrays.concat(IntArrays.singleton(1), IntArrays.singleton(2))).containsExactly(1, 2);
	}

	@Test
	void testConcatInvalid() {
		assertThatNullPointerException().isThrownBy(() -> IntArrays.concat((int[][]) null));
		assertThatNullPointerException().isThrownBy(() -> IntArrays.concat((List<int[]>) null));
		assertThatNullPointerException().isThrownBy(() -> IntArrays.concat((int[]) null));
	}

	@Test
	void testJoin() {
		assertThat(IntArrays.join(IntArrays.EMPTY, IntArrays.singleton(1), IntArrays.singleton(2))).containsExactly(1, 2);
		assertThat(IntArrays.join(IntArrays.singleton(0))).isEmpty();
		assertThat(IntArrays.join(IntArrays.singleton(0), IntArrays.singleton(1))).containsExactly(1);
		assertThat(IntArrays.join(IntArrays.singleton(0), IntArrays.singleton(1), IntArrays.singleton(2))).containsExactly(1, 0, 2);
	}

	@Test
	void testJoinInvalid() {
		assertThatNullPointerException().isThrownBy(() -> IntArrays.join(null, IntArrays.singleton(1)));
		assertThatNullPointerException().isThrownBy(() -> IntArrays.join(IntArrays.singleton(0), (int[][]) null));
		assertThatNullPointerException().isThrownBy(() -> IntArrays.join(IntArrays.singleton(0), (List<int[]>) null));
		assertThatNullPointerException().isThrownBy(() -> IntArrays.join(IntArrays.singleton(0), (int[]) null));
	}

	@Test
	void testSingleton() {
		assertThat(IntArrays.singleton(1)).containsExactly(1);
	}

	@Test
	void testOf() {
		assertThat(IntArrays.of()).isEmpty();
		assertThat(IntArrays.of(1, 2)).containsExactly(1, 2);
	}

	@Test
	void testOfInvalid() {
		assertThatNullPointerException().isThrownBy(() -> IntArrays.of((int[]) null));
	}

	@Test
	void testOfBoxedToBoxed() {
		assertThat(IntArrays.of(IntArrays.toBoxed(IntArrays.EMPTY))).isEmpty();
		assertThat(IntArrays.of(IntArrays.toBoxed(IntArrays.of(1, 2)))).containsExactly(1, 2);
		assertThat(IntArrays.toBoxed(IntArrays.singleton(1))).isInstanceOf(Integer[].class);
		assertThat(IntArrays.of(IntArrays.toBoxed(IntArrays.singleton(1)))).isInstanceOf(int[].class);
	}

	@Test
	void testOfBoxedInvalid() {
		assertThatNullPointerException().isThrownBy(() -> IntArrays.of((Integer[]) null));
	}

	@Test
	void testToBoxedInvalid() {
		assertThatNullPointerException().isThrownBy(() -> IntArrays.toBoxed(null));
	}
}