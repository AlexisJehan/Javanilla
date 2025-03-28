/*
 * MIT License
 *
 * Copyright (c) 2018-2025 Alexis Jehan
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

final class LongArraysTest {

	private static final long[] VALUES = {1L, 2L};

	@Test
	void testEmpty() {
		assertThat(LongArrays.EMPTY).isEmpty();
	}

	@Test
	void testNullToEmpty() {
		assertThat(LongArrays.nullToEmpty(null)).isEmpty();
		assertThat(LongArrays.nullToEmpty(LongArrays.EMPTY)).isEmpty();
		assertThat(LongArrays.nullToEmpty(LongArrays.of(VALUES))).containsExactly(VALUES);
	}

	@Test
	void testNullToDefault() {
		assertThat(LongArrays.nullToDefault(null, LongArrays.singleton(0L))).containsExactly(0L);
		assertThat(LongArrays.nullToDefault(LongArrays.EMPTY, LongArrays.singleton(0L))).isEmpty();
		assertThat(LongArrays.nullToDefault(LongArrays.of(VALUES), LongArrays.singleton(0L))).containsExactly(VALUES);
	}

	@Test
	void testNullToDefaultInvalid() {
		assertThatNullPointerException().isThrownBy(() -> LongArrays.nullToDefault(LongArrays.of(VALUES), null));
	}

	@Test
	void testEmptyToNull() {
		assertThat(LongArrays.emptyToNull(null)).isNull();
		assertThat(LongArrays.emptyToNull(LongArrays.EMPTY)).isNull();
		assertThat(LongArrays.emptyToNull(LongArrays.of(VALUES))).containsExactly(VALUES);
	}

	@Test
	void testEmptyToDefault() {
		assertThat(LongArrays.emptyToDefault(null, LongArrays.singleton(0L))).isNull();
		assertThat(LongArrays.emptyToDefault(LongArrays.EMPTY, LongArrays.singleton(0L))).containsExactly(0L);
		assertThat(LongArrays.emptyToDefault(LongArrays.of(VALUES), LongArrays.singleton(0L))).containsExactly(VALUES);
	}

	@Test
	void testEmptyToDefaultInvalid() {
		assertThatIllegalArgumentException().isThrownBy(() -> LongArrays.emptyToDefault(LongArrays.of(VALUES), LongArrays.EMPTY));
	}

	@Test
	@Deprecated
	void testAddLegacy() {
		assertThat(LongArrays.add(LongArrays.of(1L, 2L, 3L), 0, 0L)).containsExactly(0L, 1L, 2L, 3L);
		assertThat(LongArrays.add(LongArrays.of(1L, 2L, 3L), 1, 0L)).containsExactly(1L, 0L, 2L, 3L);
		assertThat(LongArrays.add(LongArrays.of(1L, 2L, 3L), 2, 0L)).containsExactly(1L, 2L, 0L, 3L);
		assertThat(LongArrays.add(LongArrays.of(1L, 2L, 3L), 3, 0L)).containsExactly(1L, 2L, 3L, 0L);
	}

	@Test
	@Deprecated
	void testAddLegacyInvalid() {
		assertThatNullPointerException().isThrownBy(() -> LongArrays.add(null, 0, 0L));
		assertThatIllegalArgumentException().isThrownBy(() -> LongArrays.add(LongArrays.of(VALUES), -1, 0L));
		assertThatIllegalArgumentException().isThrownBy(() -> LongArrays.add(LongArrays.of(VALUES), 3, 0L));
	}

	@Test
	void testAdd() {
		assertThat(LongArrays.add(LongArrays.EMPTY, 0L)).containsExactly(0L);
		assertThat(LongArrays.add(LongArrays.of(1L, 2L, 3L), 0L, 0)).containsExactly(0L, 1L, 2L, 3L);
		assertThat(LongArrays.add(LongArrays.of(1L, 2L, 3L), 0L, 1)).containsExactly(1L, 0L, 2L, 3L);
		assertThat(LongArrays.add(LongArrays.of(1L, 2L, 3L), 0L, 2)).containsExactly(1L, 2L, 0L, 3L);
		assertThat(LongArrays.add(LongArrays.of(1L, 2L, 3L), 0L, 3)).containsExactly(1L, 2L, 3L, 0L);
		assertThat(LongArrays.add(LongArrays.of(1L, 2L, 3L), 0L)).containsExactly(1L, 2L, 3L, 0L);
	}

	@Test
	void testAddInvalid() {
		assertThatNullPointerException().isThrownBy(() -> LongArrays.add(null, 0L));
		assertThatNullPointerException().isThrownBy(() -> LongArrays.add(null, 0L, 0));
		assertThatIllegalArgumentException().isThrownBy(() -> LongArrays.add(LongArrays.of(VALUES), 0L, -1));
		assertThatIllegalArgumentException().isThrownBy(() -> LongArrays.add(LongArrays.of(VALUES), 0L, 3));
	}

	@Test
	void testRemove() {
		assertThat(LongArrays.remove(LongArrays.singleton(1L), 0)).isEmpty();
		assertThat(LongArrays.remove(LongArrays.of(1L, 2L, 3L), 0)).containsExactly(2L, 3L);
		assertThat(LongArrays.remove(LongArrays.of(1L, 2L, 3L), 1)).containsExactly(1L, 3L);
		assertThat(LongArrays.remove(LongArrays.of(1L, 2L, 3L), 2)).containsExactly(1L, 2L);
	}

	@Test
	void testRemoveInvalid() {
		assertThatNullPointerException().isThrownBy(() -> LongArrays.remove(null, 0));
		assertThatIllegalArgumentException().isThrownBy(() -> LongArrays.remove(LongArrays.EMPTY, 0));
		assertThatIllegalArgumentException().isThrownBy(() -> LongArrays.remove(LongArrays.of(VALUES), -1));
		assertThatIllegalArgumentException().isThrownBy(() -> LongArrays.remove(LongArrays.of(VALUES), 2));
	}

	@Test
	void testConcat() {
		assertThat(LongArrays.concat()).isEmpty();
		assertThat(LongArrays.concat(LongArrays.singleton(VALUES[0]))).containsExactly(VALUES[0]);
		assertThat(LongArrays.concat(LongArrays.singleton(VALUES[0]), LongArrays.singleton(VALUES[1]))).containsExactly(VALUES);
		assertThat(LongArrays.concat(List.of(LongArrays.singleton(VALUES[0]), LongArrays.singleton(VALUES[1])))).containsExactly(VALUES);
	}

	@Test
	void testConcatInvalid() {
		assertThatNullPointerException().isThrownBy(() -> LongArrays.concat((long[][]) null));
		assertThatNullPointerException().isThrownBy(() -> LongArrays.concat((long[]) null));
		assertThatNullPointerException().isThrownBy(() -> LongArrays.concat((List<long[]>) null));
		assertThatNullPointerException().isThrownBy(() -> LongArrays.concat(Collections.singletonList(null)));
	}

	@Test
	void testJoin() {
		assertThat(LongArrays.join(LongArrays.EMPTY, LongArrays.singleton(VALUES[0]), LongArrays.singleton(VALUES[1]))).containsExactly(VALUES);
		assertThat(LongArrays.join(LongArrays.singleton(0L))).isEmpty();
		assertThat(LongArrays.join(LongArrays.singleton(0L), LongArrays.singleton(VALUES[0]))).containsExactly(VALUES[0]);
		assertThat(LongArrays.join(LongArrays.singleton(0L), LongArrays.singleton(VALUES[0]), LongArrays.singleton(VALUES[1]))).containsExactly(VALUES[0], 0L, VALUES[1]);
		assertThat(LongArrays.join(LongArrays.singleton(0L), List.of(LongArrays.singleton(VALUES[0]), LongArrays.singleton(VALUES[1])))).containsExactly(VALUES[0], 0L, VALUES[1]);
	}

	@Test
	void testJoinInvalid() {
		assertThatNullPointerException().isThrownBy(() -> LongArrays.join(null, LongArrays.of(VALUES)));
		assertThatNullPointerException().isThrownBy(() -> LongArrays.join(LongArrays.of(VALUES), (long[][]) null));
		assertThatNullPointerException().isThrownBy(() -> LongArrays.join(LongArrays.of(VALUES), (long[]) null));
		assertThatNullPointerException().isThrownBy(() -> LongArrays.join(LongArrays.of(VALUES), (List<long[]>) null));
		assertThatNullPointerException().isThrownBy(() -> LongArrays.join(LongArrays.of(VALUES), Collections.singletonList(null)));
	}

	@Test
	void testContainsAny() {
		assertThat(LongArrays.containsAny(LongArrays.EMPTY, 1L)).isFalse();
		assertThat(LongArrays.containsAny(LongArrays.singleton(1L), 1L)).isTrue();
		assertThat(LongArrays.containsAny(LongArrays.singleton(1L), 2L)).isFalse();
		assertThat(LongArrays.containsAny(LongArrays.singleton(1L), 1L, 2L)).isTrue();
	}

	@Test
	void testContainsAnyInvalid() {
		assertThatNullPointerException().isThrownBy(() -> LongArrays.containsAny(null, 1L));
		assertThatNullPointerException().isThrownBy(() -> LongArrays.containsAny(LongArrays.of(VALUES), (long[]) null));
		assertThatIllegalArgumentException().isThrownBy(() -> LongArrays.containsAny(LongArrays.of(VALUES)));
	}

	@Test
	void testContainsAll() {
		assertThat(LongArrays.containsAll(LongArrays.EMPTY, 1L)).isFalse();
		assertThat(LongArrays.containsAll(LongArrays.singleton(1L), 1L)).isTrue();
		assertThat(LongArrays.containsAll(LongArrays.singleton(1L), 2L)).isFalse();
		assertThat(LongArrays.containsAll(LongArrays.singleton(1L), 1L, 2L)).isFalse();
		assertThat(LongArrays.containsAll(LongArrays.of(1L, 2L), 1L)).isTrue();
		assertThat(LongArrays.containsAll(LongArrays.of(1L, 2L), 2L)).isTrue();
		assertThat(LongArrays.containsAll(LongArrays.of(1L, 2L), 1L, 2L)).isTrue();
	}

	@Test
	void testContainsAllInvalid() {
		assertThatNullPointerException().isThrownBy(() -> LongArrays.containsAll(null, 1L));
		assertThatNullPointerException().isThrownBy(() -> LongArrays.containsAll(LongArrays.of(VALUES), (long[]) null));
		assertThatIllegalArgumentException().isThrownBy(() -> LongArrays.containsAll(LongArrays.of(VALUES)));
	}

	@Test
	void testContainsOnce() {
		assertThat(LongArrays.containsOnce(LongArrays.EMPTY, 1L)).isFalse();
		assertThat(LongArrays.containsOnce(LongArrays.singleton(1L), 1L)).isTrue();
		assertThat(LongArrays.containsOnce(LongArrays.singleton(1L), 2L)).isFalse();
		assertThat(LongArrays.containsOnce(LongArrays.singleton(1L), 1L, 2L)).isFalse();
		assertThat(LongArrays.containsOnce(LongArrays.of(1L, 1L), 1L)).isFalse();
		assertThat(LongArrays.containsOnce(LongArrays.of(1L, 1L), 2L)).isFalse();
		assertThat(LongArrays.containsOnce(LongArrays.of(1L, 1L), 1L, 2L)).isFalse();
	}

	@Test
	void testContainsOnceInvalid() {
		assertThatNullPointerException().isThrownBy(() -> LongArrays.containsOnce(null, 1L));
		assertThatNullPointerException().isThrownBy(() -> LongArrays.containsOnce(LongArrays.of(VALUES), (long[]) null));
		assertThatIllegalArgumentException().isThrownBy(() -> LongArrays.containsOnce(LongArrays.of(VALUES)));
	}

	@Test
	void testContainsOnly() {
		assertThat(LongArrays.containsOnly(LongArrays.EMPTY, 1L)).isFalse();
		assertThat(LongArrays.containsOnly(LongArrays.singleton(1L), 1L)).isTrue();
		assertThat(LongArrays.containsOnly(LongArrays.singleton(1L), 2L)).isFalse();
		assertThat(LongArrays.containsOnly(LongArrays.singleton(1L), 1L, 2L)).isTrue();
		assertThat(LongArrays.containsOnly(LongArrays.of(1L, 2L), 1L)).isFalse();
		assertThat(LongArrays.containsOnly(LongArrays.of(1L, 2L), 2L)).isFalse();
		assertThat(LongArrays.containsOnly(LongArrays.of(1L, 2L), 1L, 2L)).isTrue();
	}

	@Test
	void testContainsOnlyInvalid() {
		assertThatNullPointerException().isThrownBy(() -> LongArrays.containsOnly(null, 1L));
		assertThatNullPointerException().isThrownBy(() -> LongArrays.containsOnly(LongArrays.of(VALUES), (long[]) null));
		assertThatIllegalArgumentException().isThrownBy(() -> LongArrays.containsOnly(LongArrays.of(VALUES)));
	}

	@Test
	void testIndexOf() {
		assertThat(LongArrays.indexOf(LongArrays.EMPTY, 1L)).isEqualTo(-1);
		assertThat(LongArrays.indexOf(LongArrays.of(1L, 2L, 1L), 1L)).isZero();
		assertThat(LongArrays.indexOf(LongArrays.of(1L, 2L, 1L), 2L)).isEqualTo(1);
		assertThat(LongArrays.indexOf(LongArrays.of(1L, 2L, 1L), 1L, 1)).isEqualTo(2);
		assertThat(LongArrays.indexOf(LongArrays.of(1L, 2L, 1L), 2L, 2)).isEqualTo(-1);
	}

	@Test
	void testIndexOfInvalid() {
		assertThatNullPointerException().isThrownBy(() -> LongArrays.indexOf(null, 1L));
		assertThatIllegalArgumentException().isThrownBy(() -> LongArrays.indexOf(LongArrays.of(VALUES), 1L, -1));
		assertThatIllegalArgumentException().isThrownBy(() -> LongArrays.indexOf(LongArrays.of(VALUES), 1L, 2));
	}

	@Test
	void testLastIndexOf() {
		assertThat(LongArrays.lastIndexOf(LongArrays.EMPTY, 1L)).isEqualTo(-1);
		assertThat(LongArrays.lastIndexOf(LongArrays.of(1L, 2L, 1L), 1L)).isEqualTo(2);
		assertThat(LongArrays.lastIndexOf(LongArrays.of(1L, 2L, 1L), 2L)).isEqualTo(1);
		assertThat(LongArrays.lastIndexOf(LongArrays.of(1L, 2L, 1L), 1L, 1)).isEqualTo(2);
		assertThat(LongArrays.lastIndexOf(LongArrays.of(1L, 2L, 1L), 2L, 2)).isEqualTo(-1);
	}

	@Test
	void testLastIndexOfInvalid() {
		assertThatNullPointerException().isThrownBy(() -> LongArrays.lastIndexOf(null, 1L));
		assertThatIllegalArgumentException().isThrownBy(() -> LongArrays.lastIndexOf(LongArrays.of(VALUES), 1L, -1));
		assertThatIllegalArgumentException().isThrownBy(() -> LongArrays.lastIndexOf(LongArrays.of(VALUES), 1L, 2));
	}

	@Test
	void testFrequency() {
		assertThat(LongArrays.frequency(LongArrays.EMPTY, 1L)).isZero();
		assertThat(LongArrays.frequency(LongArrays.of(1L, 2L, 1L), 1L)).isEqualTo(2);
		assertThat(LongArrays.frequency(LongArrays.of(1L, 2L, 1L), 2L)).isEqualTo(1);
	}

	@Test
	void testFrequencyInvalid() {
		assertThatNullPointerException().isThrownBy(() -> LongArrays.frequency(null, 1L));
	}

	@Test
	void testReverse() {
		assertThat(LongArrays.singleton(1L)).satisfies(array -> {
			LongArrays.reverse(array);
			assertThat(array).containsExactly(1L);
		});

		// Even
		assertThat(LongArrays.of(1L, 2L, 1L, 2L)).satisfies(array -> {
			LongArrays.reverse(array);
			assertThat(array).containsExactly(2L, 1L, 2L, 1L);
		});

		// Odd
		assertThat(LongArrays.of(1L, 2L, 2L)).satisfies(array -> {
			LongArrays.reverse(array);
			assertThat(array).containsExactly(2L, 2L, 1L);
		});
	}

	@Test
	void testReverseInvalid() {
		assertThatNullPointerException().isThrownBy(() -> LongArrays.reverse(null));
	}

	@Test
	void testReorder() {
		assertThat(LongArrays.singleton(1L)).satisfies(array -> {
			LongArrays.reorder(array, 0);
			assertThat(array).containsExactly(1L);
		});
		assertThat(LongArrays.of(1L, 2L, 1L, 2L)).satisfies(array -> {
			LongArrays.reorder(array, 2, 0, 3, 1);
			assertThat(array).containsExactly(1L, 1L, 2L, 2L);
		});
	}

	@Test
	void testReorderInvalid() {
		assertThatNullPointerException().isThrownBy(() -> LongArrays.reorder(null));
		assertThatNullPointerException().isThrownBy(() -> LongArrays.reorder(LongArrays.of(VALUES), (int[]) null));
		assertThatIllegalArgumentException().isThrownBy(() -> LongArrays.reorder(LongArrays.of(VALUES), IntArrays.singleton(0)));
		assertThatIllegalArgumentException().isThrownBy(() -> LongArrays.reorder(LongArrays.of(VALUES), IntArrays.of(0, 0)));
		assertThatIllegalArgumentException().isThrownBy(() -> LongArrays.reorder(LongArrays.of(VALUES), IntArrays.of(-1, 1)));
		assertThatIllegalArgumentException().isThrownBy(() -> LongArrays.reorder(LongArrays.of(VALUES), IntArrays.of(2, 1)));
		assertThatIllegalArgumentException().isThrownBy(() -> LongArrays.reorder(LongArrays.of(VALUES), IntArrays.of(0, -1)));
		assertThatIllegalArgumentException().isThrownBy(() -> LongArrays.reorder(LongArrays.of(VALUES), IntArrays.of(0, 2)));
	}

	@Test
	@Deprecated
	void testShuffleLegacy() {
		assertThat(LongArrays.singleton(1L)).satisfies(array -> {
			LongArrays.shuffle(array);
			assertThat(array).containsExactly(1L);
		});
	}

	@Test
	@Deprecated
	void testShuffleLegacyInvalid() {
		assertThatNullPointerException().isThrownBy(() -> LongArrays.shuffle(null));
	}

	@Test
	void testShuffle() {
		assertThat(LongArrays.of(1L, 2L, 1L, 2L)).satisfies(array -> {
			LongArrays.shuffle(array, ThreadLocalRandom.current());
			assertThat(array).containsExactlyInAnyOrder(1L, 2L, 1L, 2L);
		});
	}

	@Test
	void testShuffleInvalid() {
		assertThatNullPointerException().isThrownBy(() -> LongArrays.shuffle(null, ThreadLocalRandom.current()));
		assertThatNullPointerException().isThrownBy(() -> LongArrays.shuffle(LongArrays.of(VALUES), null));
	}

	@Test
	void testSwap() {
		assertThat(LongArrays.singleton(1L)).satisfies(array -> {
			LongArrays.swap(array, 0, 0);
			assertThat(array).containsExactly(1L);
		});
		assertThat(LongArrays.of(1L, 2L, 1L, 2L)).satisfies(array -> {
			LongArrays.swap(array, 1, 2);
			assertThat(array).containsExactly(1L, 1L, 2L, 2L);
		});
	}

	@Test
	void testSwapInvalid() {
		assertThatNullPointerException().isThrownBy(() -> LongArrays.swap(null, 0, 0));
		assertThatIllegalArgumentException().isThrownBy(() -> LongArrays.swap(LongArrays.of(VALUES), -1, 1));
		assertThatIllegalArgumentException().isThrownBy(() -> LongArrays.swap(LongArrays.of(VALUES), 2, 1));
		assertThatIllegalArgumentException().isThrownBy(() -> LongArrays.swap(LongArrays.of(VALUES), 0, -1));
		assertThatIllegalArgumentException().isThrownBy(() -> LongArrays.swap(LongArrays.of(VALUES), 0, 2));
	}

	@Test
	void testIsEmpty() {
		assertThat(LongArrays.isEmpty(LongArrays.EMPTY)).isTrue();
		assertThat(LongArrays.isEmpty(LongArrays.of(VALUES))).isFalse();
	}

	@Test
	void testIsEmptyInvalid() {
		assertThatNullPointerException().isThrownBy(() -> LongArrays.isEmpty(null));
	}

	@Test
	void testSingleton() {
		assertThat(LongArrays.singleton(VALUES[0])).containsExactly(VALUES[0]);
	}

	@Test
	void testOfLongs() {
		assertThat(LongArrays.of()).isEmpty();
		assertThat(LongArrays.of(VALUES)).containsExactly(VALUES);
	}

	@Test
	void testOfLongsInvalid() {
		assertThatNullPointerException().isThrownBy(() -> LongArrays.of((long[]) null));
	}

	@Test
	void testOfBoxedAndToBoxed() {
		assertThat(LongArrays.of(LongArrays.toBoxed(LongArrays.EMPTY))).isEmpty();
		assertThat(LongArrays.of(LongArrays.toBoxed(LongArrays.of(VALUES)))).containsExactly(VALUES);
		assertThat(LongArrays.toBoxed(LongArrays.of(VALUES))).isInstanceOf(Long[].class);
		assertThat(LongArrays.of(LongArrays.toBoxed(LongArrays.of(VALUES)))).isInstanceOf(long[].class);
	}

	@Test
	void testOfBoxedInvalid() {
		assertThatNullPointerException().isThrownBy(() -> LongArrays.of((Long[]) null));
	}

	@Test
	void testToBoxedInvalid() {
		assertThatNullPointerException().isThrownBy(() -> LongArrays.toBoxed(null));
	}
}