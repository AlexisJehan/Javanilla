/*
 * MIT License
 *
 * Copyright (c) 2018-2023 Alexis Jehan
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

import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.assertj.core.api.Assertions.assertThatNullPointerException;

final class IntArraysTest {

	private static final int[] VALUES = {1, 2};

	@Test
	void testEmpty() {
		assertThat(IntArrays.EMPTY).isEmpty();
	}

	@Test
	void testNullToEmpty() {
		assertThat(IntArrays.nullToEmpty(null)).isEmpty();
		assertThat(IntArrays.nullToEmpty(IntArrays.EMPTY)).isEmpty();
		assertThat(IntArrays.nullToEmpty(IntArrays.of(VALUES))).containsExactly(VALUES);
	}

	@Test
	void testNullToDefault() {
		assertThat(IntArrays.nullToDefault(null, IntArrays.singleton(0))).containsExactly(0);
		assertThat(IntArrays.nullToDefault(IntArrays.EMPTY, IntArrays.singleton(0))).isEmpty();
		assertThat(IntArrays.nullToDefault(IntArrays.of(VALUES), IntArrays.singleton(0))).containsExactly(VALUES);
	}

	@Test
	void testNullToDefaultInvalid() {
		assertThatNullPointerException().isThrownBy(() -> IntArrays.nullToDefault(IntArrays.of(VALUES), null));
	}

	@Test
	void testEmptyToNull() {
		assertThat(IntArrays.emptyToNull(IntArrays.EMPTY)).isNull();
		assertThat(IntArrays.emptyToNull(null)).isNull();
		assertThat(IntArrays.emptyToNull(IntArrays.of(VALUES))).containsExactly(VALUES);
	}

	@Test
	void testEmptyToDefault() {
		assertThat(IntArrays.emptyToDefault(null, IntArrays.singleton(0))).isNull();
		assertThat(IntArrays.emptyToDefault(IntArrays.EMPTY, IntArrays.singleton(0))).containsExactly(0);
		assertThat(IntArrays.emptyToDefault(IntArrays.of(VALUES), IntArrays.singleton(0))).containsExactly(VALUES);
	}

	@Test
	void testEmptyToDefaultInvalid() {
		assertThatIllegalArgumentException().isThrownBy(() -> IntArrays.emptyToDefault(IntArrays.of(VALUES), IntArrays.EMPTY));
	}

	@Test
	void testAdd() {
		assertThat(IntArrays.add(IntArrays.EMPTY, 0)).containsExactly(0);
		assertThat(IntArrays.add(IntArrays.of(1, 2, 3), 0, 0)).containsExactly(0, 1, 2, 3);
		assertThat(IntArrays.add(IntArrays.of(1, 2, 3), 0, 1)).containsExactly(1, 0, 2, 3);
		assertThat(IntArrays.add(IntArrays.of(1, 2, 3), 0, 2)).containsExactly(1, 2, 0, 3);
		assertThat(IntArrays.add(IntArrays.of(1, 2, 3), 0, 3)).containsExactly(1, 2, 3, 0);
		assertThat(IntArrays.add(IntArrays.of(1, 2, 3), 0)).containsExactly(1, 2, 3, 0);
	}

	@Test
	void testAddInvalid() {
		assertThatNullPointerException().isThrownBy(() -> IntArrays.add(null, 0));
		assertThatNullPointerException().isThrownBy(() -> IntArrays.add(null, 0, 0));
		assertThatIllegalArgumentException().isThrownBy(() -> IntArrays.add(IntArrays.of(VALUES), 0, -1));
		assertThatIllegalArgumentException().isThrownBy(() -> IntArrays.add(IntArrays.of(VALUES), 0, 3));
	}

	@Test
	@Deprecated
	void testAddTemporary() {
		assertThat(IntArrays.addTemporary(IntArrays.of(1, 2, 3), 0, 0)).containsExactly(0, 1, 2, 3);
		assertThat(IntArrays.addTemporary(IntArrays.of(1, 2, 3), 0, 1)).containsExactly(1, 0, 2, 3);
		assertThat(IntArrays.addTemporary(IntArrays.of(1, 2, 3), 0, 2)).containsExactly(1, 2, 0, 3);
		assertThat(IntArrays.addTemporary(IntArrays.of(1, 2, 3), 0, 3)).containsExactly(1, 2, 3, 0);
	}

	@Test
	@Deprecated
	void testAddTemporaryInvalid() {
		assertThatNullPointerException().isThrownBy(() -> IntArrays.addTemporary(null, 0, 0));
		assertThatIllegalArgumentException().isThrownBy(() -> IntArrays.addTemporary(IntArrays.of(VALUES), 0, -1));
		assertThatIllegalArgumentException().isThrownBy(() -> IntArrays.addTemporary(IntArrays.of(VALUES), 0, 3));
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
		assertThatIllegalArgumentException().isThrownBy(() -> IntArrays.remove(IntArrays.of(VALUES), -1));
		assertThatIllegalArgumentException().isThrownBy(() -> IntArrays.remove(IntArrays.of(VALUES), 2));
	}

	@Test
	void testConcat() {
		assertThat(IntArrays.concat()).isEmpty();
		assertThat(IntArrays.concat(IntArrays.singleton(VALUES[0]))).containsExactly(VALUES[0]);
		assertThat(IntArrays.concat(IntArrays.singleton(VALUES[0]), IntArrays.singleton(VALUES[1]))).containsExactly(VALUES);
		assertThat(IntArrays.concat(List.of(IntArrays.singleton(VALUES[0]), IntArrays.singleton(VALUES[1])))).containsExactly(VALUES);
	}

	@Test
	void testConcatInvalid() {
		assertThatNullPointerException().isThrownBy(() -> IntArrays.concat((int[][]) null));
		assertThatNullPointerException().isThrownBy(() -> IntArrays.concat((int[]) null));
		assertThatNullPointerException().isThrownBy(() -> IntArrays.concat((List<int[]>) null));
		assertThatNullPointerException().isThrownBy(() -> IntArrays.concat(Collections.singletonList(null)));
	}

	@Test
	void testJoin() {
		assertThat(IntArrays.join(IntArrays.EMPTY, IntArrays.singleton(VALUES[0]), IntArrays.singleton(VALUES[1]))).containsExactly(VALUES);
		assertThat(IntArrays.join(IntArrays.singleton(0))).isEmpty();
		assertThat(IntArrays.join(IntArrays.singleton(0), IntArrays.singleton(VALUES[0]))).containsExactly(VALUES[0]);
		assertThat(IntArrays.join(IntArrays.singleton(0), IntArrays.singleton(VALUES[0]), IntArrays.singleton(VALUES[1]))).containsExactly(VALUES[0], 0, VALUES[1]);
		assertThat(IntArrays.join(IntArrays.singleton(0), List.of(IntArrays.singleton(VALUES[0]), IntArrays.singleton(VALUES[1])))).containsExactly(VALUES[0], 0, VALUES[1]);
	}

	@Test
	void testJoinInvalid() {
		assertThatNullPointerException().isThrownBy(() -> IntArrays.join(null, IntArrays.of(VALUES)));
		assertThatNullPointerException().isThrownBy(() -> IntArrays.join(IntArrays.of(VALUES), (int[][]) null));
		assertThatNullPointerException().isThrownBy(() -> IntArrays.join(IntArrays.of(VALUES), (int[]) null));
		assertThatNullPointerException().isThrownBy(() -> IntArrays.join(IntArrays.of(VALUES), (List<int[]>) null));
		assertThatNullPointerException().isThrownBy(() -> IntArrays.join(IntArrays.of(VALUES), Collections.singletonList(null)));
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
		assertThatNullPointerException().isThrownBy(() -> IntArrays.containsAny(IntArrays.of(VALUES), (int[]) null));
		assertThatIllegalArgumentException().isThrownBy(() -> IntArrays.containsAny(IntArrays.of(VALUES)));
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
		assertThatNullPointerException().isThrownBy(() -> IntArrays.containsAll(IntArrays.of(VALUES), (int[]) null));
		assertThatIllegalArgumentException().isThrownBy(() -> IntArrays.containsAll(IntArrays.of(VALUES)));
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
		assertThatNullPointerException().isThrownBy(() -> IntArrays.containsOnce(IntArrays.of(VALUES), (int[]) null));
		assertThatIllegalArgumentException().isThrownBy(() -> IntArrays.containsOnce(IntArrays.of(VALUES)));
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
		assertThatNullPointerException().isThrownBy(() -> IntArrays.containsOnly(IntArrays.of(VALUES), (int[]) null));
		assertThatIllegalArgumentException().isThrownBy(() -> IntArrays.containsOnly(IntArrays.of(VALUES)));
	}

	@Test
	void testIndexOf() {
		assertThat(IntArrays.indexOf(IntArrays.EMPTY, 1)).isEqualTo(-1);
		assertThat(IntArrays.indexOf(IntArrays.of(1, 2, 1), 1)).isZero();
		assertThat(IntArrays.indexOf(IntArrays.of(1, 2, 1), 2)).isEqualTo(1);
		assertThat(IntArrays.indexOf(IntArrays.of(1, 2, 1), 1, 1)).isEqualTo(2);
		assertThat(IntArrays.indexOf(IntArrays.of(1, 2, 1), 2, 2)).isEqualTo(-1);
	}

	@Test
	void testIndexOfInvalid() {
		assertThatNullPointerException().isThrownBy(() -> IntArrays.indexOf(null, 1));
		assertThatIllegalArgumentException().isThrownBy(() -> IntArrays.indexOf(IntArrays.of(VALUES), 1, -1));
		assertThatIllegalArgumentException().isThrownBy(() -> IntArrays.indexOf(IntArrays.of(VALUES), 1, 2));
	}

	@Test
	void testLastIndexOf() {
		assertThat(IntArrays.lastIndexOf(IntArrays.EMPTY, 1)).isEqualTo(-1);
		assertThat(IntArrays.lastIndexOf(IntArrays.of(1, 2, 1), 1)).isEqualTo(2);
		assertThat(IntArrays.lastIndexOf(IntArrays.of(1, 2, 1), 2)).isEqualTo(1);
		assertThat(IntArrays.lastIndexOf(IntArrays.of(1, 2, 1), 1, 1)).isEqualTo(2);
		assertThat(IntArrays.lastIndexOf(IntArrays.of(1, 2, 1), 2, 2)).isEqualTo(-1);
	}

	@Test
	void testLastIndexOfInvalid() {
		assertThatNullPointerException().isThrownBy(() -> IntArrays.lastIndexOf(null, 1));
		assertThatIllegalArgumentException().isThrownBy(() -> IntArrays.lastIndexOf(IntArrays.of(VALUES), 1, -1));
		assertThatIllegalArgumentException().isThrownBy(() -> IntArrays.lastIndexOf(IntArrays.of(VALUES), 1, 2));
	}

	@Test
	void testFrequency() {
		assertThat(IntArrays.frequency(IntArrays.EMPTY, 1)).isZero();
		assertThat(IntArrays.frequency(IntArrays.of(1, 2, 1), 1)).isEqualTo(2);
		assertThat(IntArrays.frequency(IntArrays.of(1, 2, 1), 2)).isEqualTo(1);
	}

	@Test
	void testFrequencyInvalid() {
		assertThatNullPointerException().isThrownBy(() -> IntArrays.frequency(null, 1));
	}

	@Test
	void testReverse() {
		assertThat(IntArrays.singleton(1)).satisfies(array -> {
			IntArrays.reverse(array);
			assertThat(array).containsExactly(1);
		});

		// Even
		assertThat(IntArrays.of(1, 2, 1, 2)).satisfies(array -> {
			IntArrays.reverse(array);
			assertThat(array).containsExactly(2, 1, 2, 1);
		});

		// Odd
		assertThat(IntArrays.of(1, 2, 2)).satisfies(array -> {
			IntArrays.reverse(array);
			assertThat(array).containsExactly(2, 2, 1);
		});
	}

	@Test
	void testReverseInvalid() {
		assertThatNullPointerException().isThrownBy(() -> IntArrays.reverse(null));
	}

	@Test
	void testReorder() {
		assertThat(IntArrays.singleton(1)).satisfies(array -> {
			IntArrays.reorder(array, 0);
			assertThat(array).containsExactly(1);
		});
		assertThat(IntArrays.of(1, 2, 1, 2)).satisfies(array -> {
			IntArrays.reorder(array, 2, 0, 3, 1);
			assertThat(array).containsExactly(1, 1, 2, 2);
		});
	}

	@Test
	void testReorderInvalid() {
		assertThatNullPointerException().isThrownBy(() -> IntArrays.reorder(null));
		assertThatNullPointerException().isThrownBy(() -> IntArrays.reorder(IntArrays.of(VALUES), (int[]) null));
		assertThatIllegalArgumentException().isThrownBy(() -> IntArrays.reorder(IntArrays.of(VALUES), IntArrays.singleton(0)));
		assertThatIllegalArgumentException().isThrownBy(() -> IntArrays.reorder(IntArrays.of(VALUES), IntArrays.of(0, 0)));
		assertThatIllegalArgumentException().isThrownBy(() -> IntArrays.reorder(IntArrays.of(VALUES), IntArrays.of(-1, 1)));
		assertThatIllegalArgumentException().isThrownBy(() -> IntArrays.reorder(IntArrays.of(VALUES), IntArrays.of(2, 1)));
		assertThatIllegalArgumentException().isThrownBy(() -> IntArrays.reorder(IntArrays.of(VALUES), IntArrays.of(0, -1)));
		assertThatIllegalArgumentException().isThrownBy(() -> IntArrays.reorder(IntArrays.of(VALUES), IntArrays.of(0, 2)));
	}

	@Test
	@Deprecated
	void testShuffleLegacy() {
		assertThat(IntArrays.singleton(1)).satisfies(array -> {
			IntArrays.shuffle(array);
			assertThat(array).containsExactly(1);
		});
	}

	@Test
	@Deprecated
	void testShuffleLegacyInvalid() {
		assertThatNullPointerException().isThrownBy(() -> IntArrays.shuffle(null));
	}

	@Test
	void testShuffle() {
		assertThat(IntArrays.of(1, 2, 1, 2)).satisfies(array -> {
			IntArrays.shuffle(array, ThreadLocalRandom.current());
			assertThat(array).containsExactlyInAnyOrder(1, 2, 1, 2);
		});
	}

	@Test
	void testShuffleInvalid() {
		assertThatNullPointerException().isThrownBy(() -> IntArrays.shuffle(null, ThreadLocalRandom.current()));
		assertThatNullPointerException().isThrownBy(() -> IntArrays.shuffle(IntArrays.of(VALUES), null));
	}

	@Test
	void testSwap() {
		assertThat(IntArrays.singleton(1)).satisfies(array -> {
			IntArrays.swap(array, 0, 0);
			assertThat(array).containsExactly(1);
		});
		assertThat(IntArrays.of(1, 2, 1, 2)).satisfies(array -> {
			IntArrays.swap(array, 1, 2);
			assertThat(array).containsExactly(1, 1, 2, 2);
		});
	}

	@Test
	void testSwapInvalid() {
		assertThatNullPointerException().isThrownBy(() -> IntArrays.swap(null, 0, 0));
		assertThatIllegalArgumentException().isThrownBy(() -> IntArrays.swap(IntArrays.of(VALUES), -1, 1));
		assertThatIllegalArgumentException().isThrownBy(() -> IntArrays.swap(IntArrays.of(VALUES), 2, 1));
		assertThatIllegalArgumentException().isThrownBy(() -> IntArrays.swap(IntArrays.of(VALUES), 0, -1));
		assertThatIllegalArgumentException().isThrownBy(() -> IntArrays.swap(IntArrays.of(VALUES), 0, 2));
	}

	@Test
	void testIsEmpty() {
		assertThat(IntArrays.isEmpty(IntArrays.EMPTY)).isTrue();
		assertThat(IntArrays.isEmpty(IntArrays.of(VALUES))).isFalse();
	}

	@Test
	void testIsEmptyInvalid() {
		assertThatNullPointerException().isThrownBy(() -> IntArrays.isEmpty(null));
	}

	@Test
	void testSingleton() {
		assertThat(IntArrays.singleton(VALUES[0])).containsExactly(VALUES[0]);
	}

	@Test
	void testOfInts() {
		assertThat(IntArrays.of()).isEmpty();
		assertThat(IntArrays.of(VALUES)).containsExactly(VALUES);
	}

	@Test
	void testOfIntsInvalid() {
		assertThatNullPointerException().isThrownBy(() -> IntArrays.of((int[]) null));
	}

	@Test
	void testOfBoxedAndToBoxed() {
		assertThat(IntArrays.of(IntArrays.toBoxed(IntArrays.EMPTY))).isEmpty();
		assertThat(IntArrays.of(IntArrays.toBoxed(IntArrays.of(VALUES)))).containsExactly(VALUES);
		assertThat(IntArrays.toBoxed(IntArrays.of(VALUES))).isInstanceOf(Integer[].class);
		assertThat(IntArrays.of(IntArrays.toBoxed(IntArrays.of(VALUES)))).isInstanceOf(int[].class);
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