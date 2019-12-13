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

package com.github.alexisjehan.javanilla.lang.array;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
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
		assertThat(BooleanArrays.nullToEmpty(BooleanArrays.singleton(true))).containsExactly(true);
	}

	@Test
	void testNullToDefault() {
		assertThat(BooleanArrays.nullToDefault(null, BooleanArrays.singleton(false))).containsExactly(false);
		assertThat(BooleanArrays.nullToDefault(BooleanArrays.EMPTY, BooleanArrays.singleton(false))).isEmpty();
		assertThat(BooleanArrays.nullToDefault(BooleanArrays.singleton(true), BooleanArrays.singleton(false))).containsExactly(true);
	}

	@Test
	void testNullToDefaultInvalid() {
		assertThatNullPointerException().isThrownBy(() -> BooleanArrays.nullToDefault(BooleanArrays.singleton(true), null));
	}

	@Test
	void testEmptyToNull() {
		assertThat(BooleanArrays.emptyToNull(null)).isNull();
		assertThat(BooleanArrays.emptyToNull(BooleanArrays.EMPTY)).isNull();
		assertThat(BooleanArrays.emptyToNull(BooleanArrays.singleton(true))).containsExactly(true);
	}

	@Test
	void testEmptyToDefault() {
		assertThat(BooleanArrays.emptyToDefault(null, BooleanArrays.singleton(false))).isNull();
		assertThat(BooleanArrays.emptyToDefault(BooleanArrays.EMPTY, BooleanArrays.singleton(false))).containsExactly(false);
		assertThat(BooleanArrays.emptyToDefault(BooleanArrays.singleton(true), BooleanArrays.singleton(false))).containsExactly(true);
	}

	@Test
	void testEmptyToDefaultInvalid() {
		assertThatIllegalArgumentException().isThrownBy(() -> BooleanArrays.emptyToDefault(BooleanArrays.singleton(true), BooleanArrays.EMPTY));
	}

	@Test
	void testIsEmpty() {
		assertThat(BooleanArrays.isEmpty(BooleanArrays.EMPTY)).isTrue();
		assertThat(BooleanArrays.isEmpty(BooleanArrays.singleton(true))).isFalse();
	}

	@Test
	void testIsEmptyInvalid() {
		assertThatNullPointerException().isThrownBy(() -> BooleanArrays.isEmpty(null));
	}

	@Test
	void testContainsAny() {
		assertThat(BooleanArrays.containsAny(BooleanArrays.EMPTY, true)).isFalse();
		assertThat(BooleanArrays.containsAny(BooleanArrays.singleton(true), true)).isTrue();
		assertThat(BooleanArrays.containsAny(BooleanArrays.singleton(true), false)).isFalse();
		assertThat(BooleanArrays.containsAny(BooleanArrays.singleton(true), true, false)).isTrue();
	}

	@Test
	void testContainsAnyInvalid() {
		assertThatNullPointerException().isThrownBy(() -> BooleanArrays.containsAny(null, true));
		assertThatNullPointerException().isThrownBy(() -> BooleanArrays.containsAny(BooleanArrays.singleton(true), (boolean[]) null));
		assertThatIllegalArgumentException().isThrownBy(() -> BooleanArrays.containsAny(BooleanArrays.singleton(true)));
	}

	@Test
	void testContainsAll() {
		assertThat(BooleanArrays.containsAll(BooleanArrays.EMPTY, true)).isFalse();
		assertThat(BooleanArrays.containsAll(BooleanArrays.singleton(true), true)).isTrue();
		assertThat(BooleanArrays.containsAll(BooleanArrays.singleton(true), false)).isFalse();
		assertThat(BooleanArrays.containsAll(BooleanArrays.singleton(true), true, false)).isFalse();
		assertThat(BooleanArrays.containsAll(BooleanArrays.of(true, false), true)).isTrue();
		assertThat(BooleanArrays.containsAll(BooleanArrays.of(true, false), false)).isTrue();
		assertThat(BooleanArrays.containsAll(BooleanArrays.of(true, false), true, false)).isTrue();
	}

	@Test
	void testContainsAllInvalid() {
		assertThatNullPointerException().isThrownBy(() -> BooleanArrays.containsAll(null, true));
		assertThatNullPointerException().isThrownBy(() -> BooleanArrays.containsAll(BooleanArrays.singleton(true), (boolean[]) null));
		assertThatIllegalArgumentException().isThrownBy(() -> BooleanArrays.containsAll(BooleanArrays.singleton(true)));
	}

	@Test
	void testContainsOnce() {
		assertThat(BooleanArrays.containsOnce(BooleanArrays.EMPTY, true)).isFalse();
		assertThat(BooleanArrays.containsOnce(BooleanArrays.singleton(true), true)).isTrue();
		assertThat(BooleanArrays.containsOnce(BooleanArrays.singleton(true), false)).isFalse();
		assertThat(BooleanArrays.containsOnce(BooleanArrays.singleton(true), true, false)).isFalse();
		assertThat(BooleanArrays.containsOnce(BooleanArrays.of(true, true), true)).isFalse();
		assertThat(BooleanArrays.containsOnce(BooleanArrays.of(true, true), false)).isFalse();
		assertThat(BooleanArrays.containsOnce(BooleanArrays.of(true, true), true, false)).isFalse();
	}

	@Test
	void testContainsOnceInvalid() {
		assertThatNullPointerException().isThrownBy(() -> BooleanArrays.containsOnce(null, true));
		assertThatNullPointerException().isThrownBy(() -> BooleanArrays.containsOnce(BooleanArrays.singleton(true), (boolean[]) null));
		assertThatIllegalArgumentException().isThrownBy(() -> BooleanArrays.containsOnce(BooleanArrays.singleton(true)));
	}

	@Test
	void testContainsOnly() {
		assertThat(BooleanArrays.containsOnly(BooleanArrays.EMPTY, true)).isFalse();
		assertThat(BooleanArrays.containsOnly(BooleanArrays.singleton(true), true)).isTrue();
		assertThat(BooleanArrays.containsOnly(BooleanArrays.singleton(true), false)).isFalse();
		assertThat(BooleanArrays.containsOnly(BooleanArrays.singleton(true), true, false)).isTrue();
		assertThat(BooleanArrays.containsOnly(BooleanArrays.of(true, false), true)).isFalse();
		assertThat(BooleanArrays.containsOnly(BooleanArrays.of(true, false), false)).isFalse();
		assertThat(BooleanArrays.containsOnly(BooleanArrays.of(true, false), true, false)).isTrue();
	}

	@Test
	void testContainsOnlyInvalid() {
		assertThatNullPointerException().isThrownBy(() -> BooleanArrays.containsOnly(null, true));
		assertThatNullPointerException().isThrownBy(() -> BooleanArrays.containsOnly(BooleanArrays.singleton(true), (boolean[]) null));
		assertThatIllegalArgumentException().isThrownBy(() -> BooleanArrays.containsOnly(BooleanArrays.singleton(true)));
	}

	@Test
	void testIndexOf() {
		assertThat(BooleanArrays.indexOf(BooleanArrays.EMPTY, true)).isEqualTo(-1);
		final var array = BooleanArrays.of(true, false, true);
		assertThat(BooleanArrays.indexOf(array, true)).isEqualTo(0);
		assertThat(BooleanArrays.indexOf(array, false)).isEqualTo(1);
		assertThat(BooleanArrays.indexOf(array, true, 1)).isEqualTo(2);
		assertThat(BooleanArrays.indexOf(array, false, 2)).isEqualTo(-1);
	}

	@Test
	void testIndexOfInvalid() {
		assertThatNullPointerException().isThrownBy(() -> BooleanArrays.indexOf(null, true));
		assertThatIllegalArgumentException().isThrownBy(() -> BooleanArrays.indexOf(BooleanArrays.singleton(true), true, -1));
		assertThatIllegalArgumentException().isThrownBy(() -> BooleanArrays.indexOf(BooleanArrays.singleton(true), true, 1));
	}

	@Test
	void testLastIndexOf() {
		assertThat(BooleanArrays.lastIndexOf(BooleanArrays.EMPTY, true)).isEqualTo(-1);
		final var array = BooleanArrays.of(true, false, true);
		assertThat(BooleanArrays.lastIndexOf(array, true)).isEqualTo(2);
		assertThat(BooleanArrays.lastIndexOf(array, false)).isEqualTo(1);
		assertThat(BooleanArrays.lastIndexOf(array, true, 1)).isEqualTo(2);
		assertThat(BooleanArrays.lastIndexOf(array, false, 2)).isEqualTo(-1);
	}

	@Test
	void testLastIndexOfInvalid() {
		assertThatNullPointerException().isThrownBy(() -> BooleanArrays.lastIndexOf(null, true));
		assertThatIllegalArgumentException().isThrownBy(() -> BooleanArrays.lastIndexOf(BooleanArrays.singleton(true), true, -1));
		assertThatIllegalArgumentException().isThrownBy(() -> BooleanArrays.lastIndexOf(BooleanArrays.singleton(true), true, 1));
	}

	@Test
	void testFrequency() {
		assertThat(BooleanArrays.frequency(BooleanArrays.EMPTY, true)).isEqualTo(0);
		final var array = BooleanArrays.of(true, false, true);
		assertThat(BooleanArrays.frequency(array, true)).isEqualTo(2);
		assertThat(BooleanArrays.frequency(array, false)).isEqualTo(1);
	}

	@Test
	void testFrequencyInvalid() {
		assertThatNullPointerException().isThrownBy(() -> BooleanArrays.frequency(null, true));
	}

	@Test
	void testShuffle() {
		{
			final var array = BooleanArrays.singleton(true);
			BooleanArrays.shuffle(array);
			assertThat(array).containsExactly(true);
		}
		{
			final var array = BooleanArrays.of(true, false, true, false);
			BooleanArrays.shuffle(array);
			assertThat(array).containsExactlyInAnyOrder(true, false, true, false);
		}
	}

	@Test
	void testShuffleInvalid() {
		assertThatNullPointerException().isThrownBy(() -> BooleanArrays.shuffle(null));
	}

	@Test
	void testReverse() {
		{
			final var array = BooleanArrays.singleton(true);
			BooleanArrays.reverse(array);
			assertThat(array).containsExactly(true);
		}
		{
			// Even
			final var array = BooleanArrays.of(true, false, true, false);
			BooleanArrays.reverse(array);
			assertThat(array).containsExactly(false, true, false, true);
		}
		{
			// Odd
			final var array = BooleanArrays.of(true, false, false);
			BooleanArrays.reverse(array);
			assertThat(array).containsExactly(false, false, true);
		}
	}

	@Test
	void testReverseInvalid() {
		assertThatNullPointerException().isThrownBy(() -> BooleanArrays.reverse(null));
	}

	@Test
	void testReorder() {
		{
			final var array = BooleanArrays.singleton(true);
			BooleanArrays.reorder(array, 0);
			assertThat(array).containsExactly(true);
		}
		{
			final var array = BooleanArrays.of(true, false, true, false);
			BooleanArrays.reorder(array, 2, 0, 3, 1);
			assertThat(array).containsExactly(true, true, false, false);
		}
	}

	@Test
	void testReorderInvalid() {
		assertThatNullPointerException().isThrownBy(() -> BooleanArrays.reorder(null));
		assertThatNullPointerException().isThrownBy(() -> BooleanArrays.reorder(BooleanArrays.singleton(true), (int[]) null));
		assertThatIllegalArgumentException().isThrownBy(() -> BooleanArrays.reorder(BooleanArrays.of(true, false), IntArrays.singleton(0)));
		assertThatIllegalArgumentException().isThrownBy(() -> BooleanArrays.reorder(BooleanArrays.of(true, false), IntArrays.of(0, 0)));
		assertThatIllegalArgumentException().isThrownBy(() -> BooleanArrays.reorder(BooleanArrays.of(true, false), IntArrays.of(-1, 1)));
		assertThatIllegalArgumentException().isThrownBy(() -> BooleanArrays.reorder(BooleanArrays.of(true, false), IntArrays.of(2, 1)));
		assertThatIllegalArgumentException().isThrownBy(() -> BooleanArrays.reorder(BooleanArrays.of(true, false), IntArrays.of(0, -1)));
		assertThatIllegalArgumentException().isThrownBy(() -> BooleanArrays.reorder(BooleanArrays.of(true, false), IntArrays.of(0, 2)));
	}

	@Test
	void testSwap() {
		{
			final var array = BooleanArrays.singleton(true);
			BooleanArrays.swap(array, 0, 0);
			assertThat(array).containsExactly(true);
		}
		{
			final var array = BooleanArrays.of(true, false, true, false);
			BooleanArrays.swap(array, 1, 2);
			assertThat(array).containsExactly(true, true, false, false);
		}
	}

	@Test
	void testSwapInvalid() {
		assertThatNullPointerException().isThrownBy(() -> BooleanArrays.swap(null, 0, 0));
		assertThatIllegalArgumentException().isThrownBy(() -> BooleanArrays.swap(BooleanArrays.of(true, false), -1, 1));
		assertThatIllegalArgumentException().isThrownBy(() -> BooleanArrays.swap(BooleanArrays.of(true, false), 2, 1));
		assertThatIllegalArgumentException().isThrownBy(() -> BooleanArrays.swap(BooleanArrays.of(true, false), 0, -1));
		assertThatIllegalArgumentException().isThrownBy(() -> BooleanArrays.swap(BooleanArrays.of(true, false), 0, 2));
	}

	@Test
	void testAdd() {
		assertThat(BooleanArrays.add(BooleanArrays.EMPTY, false)).containsExactly(false);
		assertThat(BooleanArrays.add(BooleanArrays.of(true, true, true), 0, false)).containsExactly(false, true, true, true);
		assertThat(BooleanArrays.add(BooleanArrays.of(true, true, true), 1, false)).containsExactly(true, false, true, true);
		assertThat(BooleanArrays.add(BooleanArrays.of(true, true, true), 2, false)).containsExactly(true, true, false, true);
		assertThat(BooleanArrays.add(BooleanArrays.of(true, true, true), 3, false)).containsExactly(true, true, true, false);
		assertThat(BooleanArrays.add(BooleanArrays.of(true, true, true), false)).containsExactly(true, true, true, false);
	}

	@Test
	void testAddInvalid() {
		assertThatNullPointerException().isThrownBy(() -> BooleanArrays.add(null, false));
		assertThatNullPointerException().isThrownBy(() -> BooleanArrays.add(null, 0, false));
		assertThatIllegalArgumentException().isThrownBy(() -> BooleanArrays.add(BooleanArrays.singleton(true), -1, false));
		assertThatIllegalArgumentException().isThrownBy(() -> BooleanArrays.add(BooleanArrays.singleton(true), 2, false));
	}

	@Test
	void testRemove() {
		assertThat(BooleanArrays.remove(BooleanArrays.singleton(true), 0)).isEmpty();
		assertThat(BooleanArrays.remove(BooleanArrays.of(true, false, true), 0)).containsExactly(false, true);
		assertThat(BooleanArrays.remove(BooleanArrays.of(true, false, true), 1)).containsExactly(true, true);
		assertThat(BooleanArrays.remove(BooleanArrays.of(true, false, true), 2)).containsExactly(true, false);
	}

	@Test
	void testRemoveInvalid() {
		assertThatNullPointerException().isThrownBy(() -> BooleanArrays.remove(null, 0));
		assertThatIllegalArgumentException().isThrownBy(() -> BooleanArrays.remove(BooleanArrays.EMPTY, 0));
		assertThatIllegalArgumentException().isThrownBy(() -> BooleanArrays.remove(BooleanArrays.singleton(true), -1));
		assertThatIllegalArgumentException().isThrownBy(() -> BooleanArrays.remove(BooleanArrays.singleton(true), 1));
	}

	@Test
	void testConcat() {
		assertThat(BooleanArrays.concat()).isEmpty();
		assertThat(BooleanArrays.concat(BooleanArrays.singleton(true))).containsExactly(true);
		assertThat(BooleanArrays.concat(BooleanArrays.singleton(true), BooleanArrays.singleton(false))).containsExactly(true, false);
	}

	@Test
	void testConcatInvalid() {
		assertThatNullPointerException().isThrownBy(() -> BooleanArrays.concat((boolean[][]) null));
		assertThatNullPointerException().isThrownBy(() -> BooleanArrays.concat((List<boolean[]>) null));
		assertThatNullPointerException().isThrownBy(() -> BooleanArrays.concat((boolean[]) null));
	}

	@Test
	void testJoin() {
		assertThat(BooleanArrays.join(BooleanArrays.EMPTY, BooleanArrays.singleton(true), BooleanArrays.singleton(false))).containsExactly(true, false);
		assertThat(BooleanArrays.join(BooleanArrays.singleton(false))).isEmpty();
		assertThat(BooleanArrays.join(BooleanArrays.singleton(false), BooleanArrays.singleton(true))).containsExactly(true);
		assertThat(BooleanArrays.join(BooleanArrays.singleton(false), BooleanArrays.singleton(true), BooleanArrays.singleton(false))).containsExactly(true, false, false);
	}

	@Test
	void testJoinInvalid() {
		assertThatNullPointerException().isThrownBy(() -> BooleanArrays.join(null, BooleanArrays.singleton(true)));
		assertThatNullPointerException().isThrownBy(() -> BooleanArrays.join(BooleanArrays.singleton(false), (boolean[][]) null));
		assertThatNullPointerException().isThrownBy(() -> BooleanArrays.join(BooleanArrays.singleton(false), (List<boolean[]>) null));
		assertThatNullPointerException().isThrownBy(() -> BooleanArrays.join(BooleanArrays.singleton(false), (boolean[]) null));
	}

	@Test
	void testSingleton() {
		assertThat(BooleanArrays.singleton(true)).containsExactly(true);
	}

	@Test
	void testOf() {
		assertThat(BooleanArrays.of()).isEmpty();
		assertThat(BooleanArrays.of(true, false)).containsExactly(true, false);
	}

	@Test
	void testOfInvalid() {
		assertThatNullPointerException().isThrownBy(() -> BooleanArrays.of((boolean[]) null));
	}

	@Test
	void testOfBoxedToBoxed() {
		assertThat(BooleanArrays.of(BooleanArrays.toBoxed(BooleanArrays.EMPTY))).isEmpty();
		assertThat(BooleanArrays.of(BooleanArrays.toBoxed(BooleanArrays.of(true, false)))).containsExactly(true, false);
		assertThat(BooleanArrays.toBoxed(BooleanArrays.singleton(true))).isInstanceOf(Boolean[].class);
		assertThat(BooleanArrays.of(BooleanArrays.toBoxed(BooleanArrays.singleton(true)))).isInstanceOf(boolean[].class);
	}

	@Test
	void testOfBoxedInvalid() {
		assertThatNullPointerException().isThrownBy(() -> BooleanArrays.of((Boolean[]) null));
	}

	@Test
	void testToBoxedInvalid() {
		assertThatNullPointerException().isThrownBy(() -> BooleanArrays.toBoxed(null));
	}
}