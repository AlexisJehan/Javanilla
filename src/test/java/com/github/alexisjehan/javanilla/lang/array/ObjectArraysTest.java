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

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNullPointerException;

/**
 * <p>{@link ObjectArrays} unit tests.</p>
 */
final class ObjectArraysTest {

	@Test
	void testEmpty() {
		assertThat(ObjectArrays.empty(Integer.class)).isEmpty();
		assertThat(ObjectArrays.empty(Integer.class)).isInstanceOf(Integer[].class);
	}

	@Test
	void testEmptyNull() {
		assertThatNullPointerException().isThrownBy(() -> ObjectArrays.empty(null));
	}

	@Test
	void testNullToEmpty() {
		assertThat(ObjectArrays.nullToEmpty(Integer.class, null)).isEmpty();
		assertThat(ObjectArrays.nullToEmpty(Integer.class, ObjectArrays.empty(Integer.class))).isEmpty();
		assertThat(ObjectArrays.nullToEmpty(Integer.class, ObjectArrays.singleton(1))).containsExactly(1);
	}

	@Test
	void testNullToDefault() {
		assertThat(ObjectArrays.nullToDefault(null, ObjectArrays.singleton(0))).containsExactly(0);
		assertThat(ObjectArrays.nullToDefault(ObjectArrays.empty(Integer.class), ObjectArrays.singleton(0))).isEmpty();
		assertThat(ObjectArrays.nullToDefault(ObjectArrays.singleton(1), ObjectArrays.singleton(0))).containsExactly(1);
	}

	@Test
	void testNullToDefaultInvalid() {
		assertThatNullPointerException().isThrownBy(() -> ObjectArrays.nullToDefault(ObjectArrays.singleton(1), null));
	}

	@Test
	void testEmptyToNull() {
		assertThat(ObjectArrays.emptyToNull(null)).isNull();
		assertThat(ObjectArrays.emptyToNull(ObjectArrays.empty(Integer.class))).isNull();
		assertThat(ObjectArrays.emptyToNull(ObjectArrays.singleton(1))).containsExactly(1);
	}

	@Test
	void testEmptyToDefault() {
		assertThat(ObjectArrays.emptyToDefault(null, ObjectArrays.singleton(0))).isNull();
		assertThat(ObjectArrays.emptyToDefault(ObjectArrays.empty(Integer.class), ObjectArrays.singleton(0))).containsExactly(0);
		assertThat(ObjectArrays.emptyToDefault(ObjectArrays.singleton(1), ObjectArrays.singleton(0))).containsExactly(1);
	}

	@Test
	void testEmptyToDefaultInvalid() {
		assertThatIllegalArgumentException().isThrownBy(() -> ObjectArrays.emptyToDefault(ObjectArrays.singleton(1), ObjectArrays.empty(Integer.class)));
	}

	@Test
	void testIsEmpty() {
		assertThat(ObjectArrays.isEmpty(ObjectArrays.empty(Integer.class))).isTrue();
		assertThat(ObjectArrays.isEmpty(ObjectArrays.singleton(1))).isFalse();
	}

	@Test
	void testIsEmptyInvalid() {
		assertThatNullPointerException().isThrownBy(() -> ObjectArrays.isEmpty(null));
	}

	@Test
	void testContainsAny() {
		assertThat(ObjectArrays.containsAny(ObjectArrays.empty(Integer.class), 1)).isFalse();
		assertThat(ObjectArrays.containsAny(ObjectArrays.singleton(1), 1)).isTrue();
		assertThat(ObjectArrays.containsAny(ObjectArrays.singleton(1), (Integer) null)).isFalse();
		assertThat(ObjectArrays.containsAny(ObjectArrays.singleton(1), 1, null)).isTrue();
	}

	@Test
	void testContainsAnyInvalid() {
		assertThatNullPointerException().isThrownBy(() -> ObjectArrays.containsAny(null, 1));
		assertThatNullPointerException().isThrownBy(() -> ObjectArrays.containsAny(ObjectArrays.singleton(1), (Object[]) null));
		assertThatIllegalArgumentException().isThrownBy(() -> ObjectArrays.containsAny(ObjectArrays.singleton(1)));
	}

	@Test
	void testContainsAll() {
		assertThat(ObjectArrays.containsAll(ObjectArrays.empty(Integer.class), 1)).isFalse();
		assertThat(ObjectArrays.containsAll(ObjectArrays.singleton(1), 1)).isTrue();
		assertThat(ObjectArrays.containsAll(ObjectArrays.singleton(1), (Integer) null)).isFalse();
		assertThat(ObjectArrays.containsAll(ObjectArrays.singleton(1), 1, null)).isFalse();
		assertThat(ObjectArrays.containsAll(ObjectArrays.of(1, null), 1)).isTrue();
		assertThat(ObjectArrays.containsAll(ObjectArrays.of(1, null), (Integer) null)).isTrue();
		assertThat(ObjectArrays.containsAll(ObjectArrays.of(1, null), 1, null)).isTrue();
	}

	@Test
	void testContainsAllInvalid() {
		assertThatNullPointerException().isThrownBy(() -> ObjectArrays.containsAll(null, 1));
		assertThatNullPointerException().isThrownBy(() -> ObjectArrays.containsAll(ObjectArrays.singleton(1), (Object[]) null));
		assertThatIllegalArgumentException().isThrownBy(() -> ObjectArrays.containsAll(ObjectArrays.singleton(1)));
	}

	@Test
	void testContainsOnce() {
		assertThat(ObjectArrays.containsOnce(ObjectArrays.empty(Integer.class), 1)).isFalse();
		assertThat(ObjectArrays.containsOnce(ObjectArrays.singleton(1), 1)).isTrue();
		assertThat(ObjectArrays.containsOnce(ObjectArrays.singleton(1), (Integer) null)).isFalse();
		assertThat(ObjectArrays.containsOnce(ObjectArrays.singleton(1), 1, null)).isFalse();
		assertThat(ObjectArrays.containsOnce(ObjectArrays.of(1, 1), 1)).isFalse();
		assertThat(ObjectArrays.containsOnce(ObjectArrays.of(1, 1), (Integer) null)).isFalse();
		assertThat(ObjectArrays.containsOnce(ObjectArrays.of(1, 1), 1, null)).isFalse();
	}

	@Test
	void testContainsOnceInvalid() {
		assertThatNullPointerException().isThrownBy(() -> ObjectArrays.containsOnce(null, 1));
		assertThatNullPointerException().isThrownBy(() -> ObjectArrays.containsOnce(ObjectArrays.singleton(1), (Object[]) null));
		assertThatIllegalArgumentException().isThrownBy(() -> ObjectArrays.containsOnce(ObjectArrays.singleton(1)));
	}

	@Test
	void testContainsOnly() {
		assertThat(ObjectArrays.containsOnly(ObjectArrays.empty(Integer.class), 1)).isFalse();
		assertThat(ObjectArrays.containsOnly(ObjectArrays.singleton(1), 1)).isTrue();
		assertThat(ObjectArrays.containsOnly(ObjectArrays.singleton(1), (Integer) null)).isFalse();
		assertThat(ObjectArrays.containsOnly(ObjectArrays.singleton(1), 1, null)).isTrue();
		assertThat(ObjectArrays.containsOnly(ObjectArrays.of(1, null), 1)).isFalse();
		assertThat(ObjectArrays.containsOnly(ObjectArrays.of(1, null), (Integer) null)).isFalse();
		assertThat(ObjectArrays.containsOnly(ObjectArrays.of(1, null), 1, null)).isTrue();
	}

	@Test
	void testContainsOnlyInvalid() {
		assertThatNullPointerException().isThrownBy(() -> ObjectArrays.containsOnly(null, 1));
		assertThatNullPointerException().isThrownBy(() -> ObjectArrays.containsOnly(ObjectArrays.singleton(1), (Object[]) null));
		assertThatIllegalArgumentException().isThrownBy(() -> ObjectArrays.containsOnly(ObjectArrays.singleton(1)));
	}

	@Test
	void testIndexOf() {
		assertThat(ObjectArrays.indexOf(ObjectArrays.empty(Integer.class), 1)).isEqualTo(-1);
		final var array = ObjectArrays.of(1, null, 1);
		assertThat(ObjectArrays.indexOf(array, 1)).isEqualTo(0);
		assertThat(ObjectArrays.indexOf(array, null)).isEqualTo(1);
		assertThat(ObjectArrays.indexOf(array, 1, 1)).isEqualTo(2);
		assertThat(ObjectArrays.indexOf(array, null, 2)).isEqualTo(-1);
	}

	@Test
	void testIndexOfInvalid() {
		assertThatNullPointerException().isThrownBy(() -> ObjectArrays.indexOf(null, 1));
		assertThatIllegalArgumentException().isThrownBy(() -> ObjectArrays.indexOf(ObjectArrays.singleton(1), 1, -1));
		assertThatIllegalArgumentException().isThrownBy(() -> ObjectArrays.indexOf(ObjectArrays.singleton(1), 1, 1));
	}

	@Test
	void testLastIndexOf() {
		assertThat(ObjectArrays.lastIndexOf(ObjectArrays.empty(Integer.class), 1)).isEqualTo(-1);
		final var array = ObjectArrays.of(1, null, 1);
		assertThat(ObjectArrays.lastIndexOf(array, 1)).isEqualTo(2);
		assertThat(ObjectArrays.lastIndexOf(array, null)).isEqualTo(1);
		assertThat(ObjectArrays.lastIndexOf(array, 1, 1)).isEqualTo(2);
		assertThat(ObjectArrays.lastIndexOf(array, null, 2)).isEqualTo(-1);
	}

	@Test
	void testLastIndexOfInvalid() {
		assertThatNullPointerException().isThrownBy(() -> ObjectArrays.lastIndexOf(null, 1));
		assertThatIllegalArgumentException().isThrownBy(() -> ObjectArrays.lastIndexOf(ObjectArrays.singleton(1), 1, -1));
		assertThatIllegalArgumentException().isThrownBy(() -> ObjectArrays.lastIndexOf(ObjectArrays.singleton(1), 1, 1));
	}

	@Test
	void testFrequency() {
		assertThat(ObjectArrays.frequency(ObjectArrays.empty(Integer.class), 1)).isEqualTo(0);
		final var array = ObjectArrays.of(1, null, 1);
		assertThat(ObjectArrays.frequency(array, 1)).isEqualTo(2);
		assertThat(ObjectArrays.frequency(array, null)).isEqualTo(1);
	}

	@Test
	void testFrequencyInvalid() {
		assertThatNullPointerException().isThrownBy(() -> ObjectArrays.frequency(null, 1));
	}

	@Test
	void testShuffle() {
		{
			final var array = ObjectArrays.singleton(1);
			ObjectArrays.shuffle(array);
			assertThat(array).containsExactly(1);
		}
		{
			final var array = ObjectArrays.of(1, 2, 1, 2);
			ObjectArrays.shuffle(array);
			assertThat(array).containsExactlyInAnyOrder(1, 2, 1, 2);
		}
	}

	@Test
	void testShuffleInvalid() {
		assertThatNullPointerException().isThrownBy(() -> ObjectArrays.shuffle(null));
	}

	@Test
	void testReverse() {
		{
			final var array = ObjectArrays.singleton(1);
			ObjectArrays.reverse(array);
			assertThat(array).containsExactly(1);
		}
		{
			// Even
			final var array = ObjectArrays.of(1, 2, 1, 2);
			ObjectArrays.reverse(array);
			assertThat(array).containsExactly(2, 1, 2, 1);
		}
		{
			// Odd
			final var array = ObjectArrays.of(1, 2, 2);
			ObjectArrays.reverse(array);
			assertThat(array).containsExactly(2, 2, 1);
		}
	}

	@Test
	void testReverseInvalid() {
		assertThatNullPointerException().isThrownBy(() -> ObjectArrays.reverse(null));
	}

	@Test
	void testReorder() {
		{
			final var array = ObjectArrays.singleton(1);
			ObjectArrays.reorder(array, 0);
			assertThat(array).containsExactly(1);
		}
		{
			final var array = ObjectArrays.of(1, 2, 1, 2);
			ObjectArrays.reorder(array, 2, 0, 3, 1);
			assertThat(array).containsExactly(1, 1, 2, 2);
		}
	}

	@Test
	void testReorderInvalid() {
		assertThatNullPointerException().isThrownBy(() -> ObjectArrays.reorder(null));
		assertThatNullPointerException().isThrownBy(() -> ObjectArrays.reorder(ObjectArrays.singleton(1), (int[]) null));
		assertThatIllegalArgumentException().isThrownBy(() -> ObjectArrays.reorder(ObjectArrays.of(1, 2), IntArrays.singleton(0)));
		assertThatIllegalArgumentException().isThrownBy(() -> ObjectArrays.reorder(ObjectArrays.of(1, 2), IntArrays.of(0, 0)));
		assertThatIllegalArgumentException().isThrownBy(() -> ObjectArrays.reorder(ObjectArrays.of(1, 2), IntArrays.of(-1, 1)));
		assertThatIllegalArgumentException().isThrownBy(() -> ObjectArrays.reorder(ObjectArrays.of(1, 2), IntArrays.of(2, 1)));
		assertThatIllegalArgumentException().isThrownBy(() -> ObjectArrays.reorder(ObjectArrays.of(1, 2), IntArrays.of(0, -1)));
		assertThatIllegalArgumentException().isThrownBy(() -> ObjectArrays.reorder(ObjectArrays.of(1, 2), IntArrays.of(0, 2)));
	}

	@Test
	void testSwap() {
		{
			final var array = ObjectArrays.singleton(1);
			ObjectArrays.swap(array, 0, 0);
			assertThat(array).containsExactly(1);
		}
		{
			final var array = ObjectArrays.of(1, 2, 1, 2);
			ObjectArrays.swap(array, 1, 2);
			assertThat(array).containsExactly(1, 1, 2, 2);
		}
	}

	@Test
	void testSwapInvalid() {
		assertThatNullPointerException().isThrownBy(() -> ObjectArrays.swap(null, 0, 0));
		assertThatIllegalArgumentException().isThrownBy(() -> ObjectArrays.swap(ObjectArrays.of(1, 2), -1, 1));
		assertThatIllegalArgumentException().isThrownBy(() -> ObjectArrays.swap(ObjectArrays.of(1, 2), 2, 1));
		assertThatIllegalArgumentException().isThrownBy(() -> ObjectArrays.swap(ObjectArrays.of(1, 2), 0, -1));
		assertThatIllegalArgumentException().isThrownBy(() -> ObjectArrays.swap(ObjectArrays.of(1, 2), 0, 2));
	}

	@Test
	void testAdd() {
		assertThat(ObjectArrays.add(ObjectArrays.empty(Integer.class), 0)).containsExactly(0);
		assertThat(ObjectArrays.add(ObjectArrays.of(1, 2, 3), 0, 0)).containsExactly(0, 1, 2, 3);
		assertThat(ObjectArrays.add(ObjectArrays.of(1, 2, 3), 1, 0)).containsExactly(1, 0, 2, 3);
		assertThat(ObjectArrays.add(ObjectArrays.of(1, 2, 3), 2, 0)).containsExactly(1, 2, 0, 3);
		assertThat(ObjectArrays.add(ObjectArrays.of(1, 2, 3), 3, 0)).containsExactly(1, 2, 3, 0);
		assertThat(ObjectArrays.add(ObjectArrays.of(1, 2, 3), 0)).containsExactly(1, 2, 3, 0);
	}

	@Test
	void testAddInvalid() {
		assertThatNullPointerException().isThrownBy(() -> ObjectArrays.add(null, 0));
		assertThatNullPointerException().isThrownBy(() -> ObjectArrays.add(null, 0, 0));
		assertThatIllegalArgumentException().isThrownBy(() -> ObjectArrays.add(ObjectArrays.singleton(1), -1, 0));
		assertThatIllegalArgumentException().isThrownBy(() -> ObjectArrays.add(ObjectArrays.singleton(1), 2, 0));
	}

	@Test
	void testRemove() {
		assertThat(ObjectArrays.remove(ObjectArrays.singleton(1), 0)).isEmpty();
		assertThat(ObjectArrays.remove(ObjectArrays.of(1, 2, 3), 0)).containsExactly(2, 3);
		assertThat(ObjectArrays.remove(ObjectArrays.of(1, 2, 3), 1)).containsExactly(1, 3);
		assertThat(ObjectArrays.remove(ObjectArrays.of(1, 2, 3), 2)).containsExactly(1, 2);
	}

	@Test
	void testRemoveInvalid() {
		assertThatNullPointerException().isThrownBy(() -> ObjectArrays.remove(null, 0));
		assertThatIllegalArgumentException().isThrownBy(() -> ObjectArrays.remove(ObjectArrays.empty(Integer.class), 0));
		assertThatIllegalArgumentException().isThrownBy(() -> ObjectArrays.remove(ObjectArrays.singleton(1), -1));
		assertThatIllegalArgumentException().isThrownBy(() -> ObjectArrays.remove(ObjectArrays.singleton(1), 1));
	}

	@Test
	void testConcat() {
		assertThat(ObjectArrays.concat(Integer.class)).isEmpty();
		assertThat(ObjectArrays.concat(Integer.class, ObjectArrays.singleton(1))).containsExactly(1);
		assertThat(ObjectArrays.concat(Integer.class, ObjectArrays.singleton(1), ObjectArrays.singleton(Integer.class, null))).containsExactly(1, null);
	}

	@Test
	void testConcatInvalid() {
		assertThatNullPointerException().isThrownBy(() -> ObjectArrays.concat(null, ObjectArrays.singleton(1)));
		assertThatNullPointerException().isThrownBy(() -> ObjectArrays.concat(Integer.class, (Integer[][]) null));
		assertThatNullPointerException().isThrownBy(() -> ObjectArrays.concat(Integer.class, (List<Integer[]>) null));
		assertThatNullPointerException().isThrownBy(() -> ObjectArrays.concat(Integer.class, (Integer[]) null));
	}

	@Test
	void testJoin() {
		assertThat(ObjectArrays.join(ObjectArrays.empty(Integer.class), ObjectArrays.singleton(1), ObjectArrays.singleton(Integer.class, null))).containsExactly(1, null);
		assertThat(ObjectArrays.join(ObjectArrays.singleton(0))).isEmpty();
		assertThat(ObjectArrays.join(ObjectArrays.singleton(0), ObjectArrays.singleton(1))).containsExactly(1);
		assertThat(ObjectArrays.join(ObjectArrays.singleton(0), ObjectArrays.singleton(1), ObjectArrays.singleton(Integer.class, null))).containsExactly(1, 0, null);
	}

	@Test
	void testJoinInvalid() {
		assertThatNullPointerException().isThrownBy(() -> ObjectArrays.join(null, ObjectArrays.singleton(1)));
		assertThatNullPointerException().isThrownBy(() -> ObjectArrays.join(ObjectArrays.singleton(0), (Integer[][]) null));
		assertThatNullPointerException().isThrownBy(() -> ObjectArrays.join(ObjectArrays.singleton(0), (List<Integer[]>) null));
		assertThatNullPointerException().isThrownBy(() -> ObjectArrays.join(ObjectArrays.singleton(0), (Integer[]) null));
	}

	@Test
	void testSingleton() {
		assertThat(ObjectArrays.singleton(1)).containsExactly(1);
		assertThat(ObjectArrays.singleton(Integer.class, null)).containsExactly((Integer) null);
	}

	@Test
	void testSingletonInvalid() {
		assertThatNullPointerException().isThrownBy(() -> ObjectArrays.singleton(null));
		assertThatNullPointerException().isThrownBy(() -> ObjectArrays.singleton(null, 1));
	}

	@Test
	void testOf() {
		assertThat(ObjectArrays.of()).isEmpty();
		assertThat(ObjectArrays.of(1, null)).containsExactly(1, null);
	}

	@Test
	void testOfInvalid() {
		assertThatNullPointerException().isThrownBy(() -> ObjectArrays.of((Integer[]) null));
	}
}