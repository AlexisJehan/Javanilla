/*
 * MIT License
 *
 * Copyright (c) 2018-2022 Alexis Jehan
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
import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.assertj.core.api.Assertions.assertThatNullPointerException;

/**
 * <p>{@link ShortArrays} unit tests.</p>
 */
final class ShortArraysTest {

	private static final short[] VALUES = {(short) 1, (short) 2};

	@Test
	void testEmpty() {
		assertThat(ShortArrays.EMPTY).isEmpty();
	}

	@Test
	void testNullToEmpty() {
		assertThat(ShortArrays.nullToEmpty(null)).isEmpty();
		assertThat(ShortArrays.nullToEmpty(ShortArrays.EMPTY)).isEmpty();
		assertThat(ShortArrays.nullToEmpty(ShortArrays.of(VALUES))).containsExactly(VALUES);
	}

	@Test
	void testNullToDefault() {
		assertThat(ShortArrays.nullToDefault(null, ShortArrays.singleton((short) 0))).containsExactly((short) 0);
		assertThat(ShortArrays.nullToDefault(ShortArrays.EMPTY, ShortArrays.singleton((short) 0))).isEmpty();
		assertThat(ShortArrays.nullToDefault(ShortArrays.of(VALUES), ShortArrays.singleton((short) 0))).containsExactly(VALUES);
	}

	@Test
	void testNullToDefaultInvalid() {
		assertThatNullPointerException().isThrownBy(() -> ShortArrays.nullToDefault(ShortArrays.of(VALUES), null));
	}

	@Test
	void testEmptyToNull() {
		assertThat(ShortArrays.emptyToNull(null)).isNull();
		assertThat(ShortArrays.emptyToNull(ShortArrays.EMPTY)).isNull();
		assertThat(ShortArrays.emptyToNull(ShortArrays.of(VALUES))).containsExactly(VALUES);
	}

	@Test
	void testEmptyToDefault() {
		assertThat(ShortArrays.emptyToDefault(null, ShortArrays.singleton((short) 0))).isNull();
		assertThat(ShortArrays.emptyToDefault(ShortArrays.EMPTY, ShortArrays.singleton((short) 0))).containsExactly((short) 0);
		assertThat(ShortArrays.emptyToDefault(ShortArrays.of(VALUES), ShortArrays.singleton((short) 0))).containsExactly(VALUES);
	}

	@Test
	void testEmptyToDefaultInvalid() {
		assertThatIllegalArgumentException().isThrownBy(() -> ShortArrays.emptyToDefault(ShortArrays.of(VALUES), ShortArrays.EMPTY));
	}

	@Test
	void testIsEmpty() {
		assertThat(ShortArrays.isEmpty(ShortArrays.EMPTY)).isTrue();
		assertThat(ShortArrays.isEmpty(ShortArrays.of(VALUES))).isFalse();
	}

	@Test
	void testIsEmptyInvalid() {
		assertThatNullPointerException().isThrownBy(() -> ShortArrays.isEmpty(null));
	}

	@Test
	void testContainsAny() {
		assertThat(ShortArrays.containsAny(ShortArrays.EMPTY, (short) 1)).isFalse();
		assertThat(ShortArrays.containsAny(ShortArrays.singleton((short) 1), (short) 1)).isTrue();
		assertThat(ShortArrays.containsAny(ShortArrays.singleton((short) 1), (short) 2)).isFalse();
		assertThat(ShortArrays.containsAny(ShortArrays.singleton((short) 1), (short) 1, (short) 2)).isTrue();
	}

	@Test
	void testContainsAnyInvalid() {
		assertThatNullPointerException().isThrownBy(() -> ShortArrays.containsAny(null, (short) 1));
		assertThatNullPointerException().isThrownBy(() -> ShortArrays.containsAny(ShortArrays.of(VALUES), (short[]) null));
		assertThatIllegalArgumentException().isThrownBy(() -> ShortArrays.containsAny(ShortArrays.of(VALUES)));
	}

	@Test
	void testContainsAll() {
		assertThat(ShortArrays.containsAll(ShortArrays.EMPTY, (short) 1)).isFalse();
		assertThat(ShortArrays.containsAll(ShortArrays.singleton((short) 1), (short) 1)).isTrue();
		assertThat(ShortArrays.containsAll(ShortArrays.singleton((short) 1), (short) 2)).isFalse();
		assertThat(ShortArrays.containsAll(ShortArrays.singleton((short) 1), (short) 1, (short) 2)).isFalse();
		assertThat(ShortArrays.containsAll(ShortArrays.of((short) 1, (short) 2), (short) 1)).isTrue();
		assertThat(ShortArrays.containsAll(ShortArrays.of((short) 1, (short) 2), (short) 2)).isTrue();
		assertThat(ShortArrays.containsAll(ShortArrays.of((short) 1, (short) 2), (short) 1, (short) 2)).isTrue();
	}

	@Test
	void testContainsAllInvalid() {
		assertThatNullPointerException().isThrownBy(() -> ShortArrays.containsAll(null, (short) 1));
		assertThatNullPointerException().isThrownBy(() -> ShortArrays.containsAll(ShortArrays.of(VALUES), (short[]) null));
		assertThatIllegalArgumentException().isThrownBy(() -> ShortArrays.containsAll(ShortArrays.of(VALUES)));
	}

	@Test
	void testContainsOnce() {
		assertThat(ShortArrays.containsOnce(ShortArrays.EMPTY, (short) 1)).isFalse();
		assertThat(ShortArrays.containsOnce(ShortArrays.singleton((short) 1), (short) 1)).isTrue();
		assertThat(ShortArrays.containsOnce(ShortArrays.singleton((short) 1), (short) 2)).isFalse();
		assertThat(ShortArrays.containsOnce(ShortArrays.singleton((short) 1), (short) 1, (short) 2)).isFalse();
		assertThat(ShortArrays.containsOnce(ShortArrays.of((short) 1, (short) 1), (short) 1)).isFalse();
		assertThat(ShortArrays.containsOnce(ShortArrays.of((short) 1, (short) 1), (short) 2)).isFalse();
		assertThat(ShortArrays.containsOnce(ShortArrays.of((short) 1, (short) 1), (short) 1, (short) 2)).isFalse();
	}

	@Test
	void testContainsOnceInvalid() {
		assertThatNullPointerException().isThrownBy(() -> ShortArrays.containsOnce(null, (short) 1));
		assertThatNullPointerException().isThrownBy(() -> ShortArrays.containsOnce(ShortArrays.of(VALUES), (short[]) null));
		assertThatIllegalArgumentException().isThrownBy(() -> ShortArrays.containsOnce(ShortArrays.of(VALUES)));
	}

	@Test
	void testContainsOnly() {
		assertThat(ShortArrays.containsOnly(ShortArrays.EMPTY, (short) 1)).isFalse();
		assertThat(ShortArrays.containsOnly(ShortArrays.singleton((short) 1), (short) 1)).isTrue();
		assertThat(ShortArrays.containsOnly(ShortArrays.singleton((short) 1), (short) 2)).isFalse();
		assertThat(ShortArrays.containsOnly(ShortArrays.singleton((short) 1), (short) 1, (short) 2)).isTrue();
		assertThat(ShortArrays.containsOnly(ShortArrays.of((short) 1, (short) 2), (short) 1)).isFalse();
		assertThat(ShortArrays.containsOnly(ShortArrays.of((short) 1, (short) 2), (short) 2)).isFalse();
		assertThat(ShortArrays.containsOnly(ShortArrays.of((short) 1, (short) 2), (short) 1, (short) 2)).isTrue();
	}

	@Test
	void testContainsOnlyInvalid() {
		assertThatNullPointerException().isThrownBy(() -> ShortArrays.containsOnly(null, (short) 1));
		assertThatNullPointerException().isThrownBy(() -> ShortArrays.containsOnly(ShortArrays.of(VALUES), (short[]) null));
		assertThatIllegalArgumentException().isThrownBy(() -> ShortArrays.containsOnly(ShortArrays.of(VALUES)));
	}

	@Test
	void testIndexOf() {
		assertThat(ShortArrays.indexOf(ShortArrays.EMPTY, (short) 1)).isEqualTo(-1);
		assertThat(ShortArrays.indexOf(ShortArrays.of((short) 1, (short) 2, (short) 1), (short) 1)).isZero();
		assertThat(ShortArrays.indexOf(ShortArrays.of((short) 1, (short) 2, (short) 1), (short) 2)).isEqualTo(1);
		assertThat(ShortArrays.indexOf(ShortArrays.of((short) 1, (short) 2, (short) 1), (short) 1, 1)).isEqualTo(2);
		assertThat(ShortArrays.indexOf(ShortArrays.of((short) 1, (short) 2, (short) 1), (short) 2, 2)).isEqualTo(-1);
	}

	@Test
	void testIndexOfInvalid() {
		assertThatNullPointerException().isThrownBy(() -> ShortArrays.indexOf(null, (short) 1));
		assertThatIllegalArgumentException().isThrownBy(() -> ShortArrays.indexOf(ShortArrays.of(VALUES), (short) 1, -1));
		assertThatIllegalArgumentException().isThrownBy(() -> ShortArrays.indexOf(ShortArrays.of(VALUES), (short) 1, 2));
	}

	@Test
	void testLastIndexOf() {
		assertThat(ShortArrays.lastIndexOf(ShortArrays.EMPTY, (short) 1)).isEqualTo(-1);
		assertThat(ShortArrays.lastIndexOf(ShortArrays.of((short) 1, (short) 2, (short) 1), (short) 1)).isEqualTo(2);
		assertThat(ShortArrays.lastIndexOf(ShortArrays.of((short) 1, (short) 2, (short) 1), (short) 2)).isEqualTo(1);
		assertThat(ShortArrays.lastIndexOf(ShortArrays.of((short) 1, (short) 2, (short) 1), (short) 1, 1)).isEqualTo(2);
		assertThat(ShortArrays.lastIndexOf(ShortArrays.of((short) 1, (short) 2, (short) 1), (short) 2, 2)).isEqualTo(-1);
	}

	@Test
	void testLastIndexOfInvalid() {
		assertThatNullPointerException().isThrownBy(() -> ShortArrays.lastIndexOf(null, (short) 1));
		assertThatIllegalArgumentException().isThrownBy(() -> ShortArrays.lastIndexOf(ShortArrays.of(VALUES), (short) 1, -1));
		assertThatIllegalArgumentException().isThrownBy(() -> ShortArrays.lastIndexOf(ShortArrays.of(VALUES), (short) 1, 2));
	}

	@Test
	void testFrequency() {
		assertThat(ShortArrays.frequency(ShortArrays.EMPTY, (short) 1)).isZero();
		assertThat(ShortArrays.frequency(ShortArrays.of((short) 1, (short) 2, (short) 1), (short) 1)).isEqualTo(2);
		assertThat(ShortArrays.frequency(ShortArrays.of((short) 1, (short) 2, (short) 1), (short) 2)).isEqualTo(1);
	}

	@Test
	void testFrequencyInvalid() {
		assertThatNullPointerException().isThrownBy(() -> ShortArrays.frequency(null, (short) 1));
	}

	@Test
	@SuppressWarnings("deprecation")
	void testShuffle() {
		assertThat(ShortArrays.singleton((short) 1)).satisfies(array -> {
			ShortArrays.shuffle(array);
			assertThat(array).containsExactly((short) 1);
		});
		assertThat(ShortArrays.of((short) 1, (short) 2, (short) 1, (short) 2)).satisfies(array -> {
			ShortArrays.shuffle(array, new Random());
			assertThat(array).containsExactlyInAnyOrder((short) 1, (short) 2, (short) 1, (short) 2);
		});
	}

	@Test
	@SuppressWarnings("deprecation")
	void testShuffleInvalid() {
		assertThatNullPointerException().isThrownBy(() -> ShortArrays.shuffle(null));
		assertThatNullPointerException().isThrownBy(() -> ShortArrays.shuffle(ShortArrays.of(VALUES), null));
	}

	@Test
	void testReverse() {
		assertThat(ShortArrays.singleton((short) 1)).satisfies(array -> {
			ShortArrays.reverse(array);
			assertThat(array).containsExactly((short) 1);
		});

		// Even
		assertThat(ShortArrays.of((short) 1, (short) 2, (short) 1, (short) 2)).satisfies(array -> {
			ShortArrays.reverse(array);
			assertThat(array).containsExactly((short) 2, (short) 1, (short) 2, (short) 1);
		});

		// Odd
		assertThat(ShortArrays.of((short) 1, (short) 2, (short) 2)).satisfies(array -> {
			ShortArrays.reverse(array);
			assertThat(array).containsExactly((short) 2, (short) 2, (short) 1);
		});
	}

	@Test
	void testReverseInvalid() {
		assertThatNullPointerException().isThrownBy(() -> ShortArrays.reverse(null));
	}

	@Test
	void testReorder() {
		assertThat(ShortArrays.singleton((short) 1)).satisfies(array -> {
			ShortArrays.reorder(array, 0);
			assertThat(array).containsExactly((short) 1);
		});
		assertThat(ShortArrays.of((short) 1, (short) 2, (short) 1, (short) 2)).satisfies(array -> {
			ShortArrays.reorder(array, 2, 0, 3, 1);
			assertThat(array).containsExactly((short) 1, (short) 1, (short) 2, (short) 2);
		});
	}

	@Test
	void testReorderInvalid() {
		assertThatNullPointerException().isThrownBy(() -> ShortArrays.reorder(null));
		assertThatNullPointerException().isThrownBy(() -> ShortArrays.reorder(ShortArrays.of(VALUES), (int[]) null));
		assertThatIllegalArgumentException().isThrownBy(() -> ShortArrays.reorder(ShortArrays.of(VALUES), IntArrays.singleton(0)));
		assertThatIllegalArgumentException().isThrownBy(() -> ShortArrays.reorder(ShortArrays.of(VALUES), IntArrays.of(0, 0)));
		assertThatIllegalArgumentException().isThrownBy(() -> ShortArrays.reorder(ShortArrays.of(VALUES), IntArrays.of(-1, 1)));
		assertThatIllegalArgumentException().isThrownBy(() -> ShortArrays.reorder(ShortArrays.of(VALUES), IntArrays.of(2, 1)));
		assertThatIllegalArgumentException().isThrownBy(() -> ShortArrays.reorder(ShortArrays.of(VALUES), IntArrays.of(0, -1)));
		assertThatIllegalArgumentException().isThrownBy(() -> ShortArrays.reorder(ShortArrays.of(VALUES), IntArrays.of(0, 2)));
	}

	@Test
	void testSwap() {
		assertThat(ShortArrays.singleton((short) 1)).satisfies(array -> {
			ShortArrays.swap(array, 0, 0);
			assertThat(array).containsExactly((short) 1);
		});
		assertThat(ShortArrays.of((short) 1, (short) 2, (short) 1, (short) 2)).satisfies(array -> {
			ShortArrays.swap(array, 1, 2);
			assertThat(array).containsExactly((short) 1, (short) 1, (short) 2, (short) 2);
		});
	}

	@Test
	void testSwapInvalid() {
		assertThatNullPointerException().isThrownBy(() -> ShortArrays.swap(null, 0, 0));
		assertThatIllegalArgumentException().isThrownBy(() -> ShortArrays.swap(ShortArrays.of(VALUES), -1, 1));
		assertThatIllegalArgumentException().isThrownBy(() -> ShortArrays.swap(ShortArrays.of(VALUES), 2, 1));
		assertThatIllegalArgumentException().isThrownBy(() -> ShortArrays.swap(ShortArrays.of(VALUES), 0, -1));
		assertThatIllegalArgumentException().isThrownBy(() -> ShortArrays.swap(ShortArrays.of(VALUES), 0, 2));
	}

	@Test
	void testAdd() {
		assertThat(ShortArrays.add(ShortArrays.EMPTY, (short) 0)).containsExactly((short) 0);
		assertThat(ShortArrays.add(ShortArrays.of((short) 1, (short) 2, (short) 3), 0, (short) 0)).containsExactly((short) 0, (short) 1, (short) 2, (short) 3);
		assertThat(ShortArrays.add(ShortArrays.of((short) 1, (short) 2, (short) 3), 1, (short) 0)).containsExactly((short) 1, (short) 0, (short) 2, (short) 3);
		assertThat(ShortArrays.add(ShortArrays.of((short) 1, (short) 2, (short) 3), 2, (short) 0)).containsExactly((short) 1, (short) 2, (short) 0, (short) 3);
		assertThat(ShortArrays.add(ShortArrays.of((short) 1, (short) 2, (short) 3), 3, (short) 0)).containsExactly((short) 1, (short) 2, (short) 3, (short) 0);
		assertThat(ShortArrays.add(ShortArrays.of((short) 1, (short) 2, (short) 3), (short) 0)).containsExactly((short) 1, (short) 2, (short) 3, (short) 0);
	}

	@Test
	void testAddInvalid() {
		assertThatNullPointerException().isThrownBy(() -> ShortArrays.add(null, (short) 0));
		assertThatNullPointerException().isThrownBy(() -> ShortArrays.add(null, 0, (short) 0));
		assertThatIllegalArgumentException().isThrownBy(() -> ShortArrays.add(ShortArrays.of(VALUES), -1, (short) 0));
		assertThatIllegalArgumentException().isThrownBy(() -> ShortArrays.add(ShortArrays.of(VALUES), 3, (short) 0));
	}

	@Test
	void testRemove() {
		assertThat(ShortArrays.remove(ShortArrays.singleton((short) 1), 0)).isEmpty();
		assertThat(ShortArrays.remove(ShortArrays.of((short) 1, (short) 2, (short) 3), 0)).containsExactly((short) 2, (short) 3);
		assertThat(ShortArrays.remove(ShortArrays.of((short) 1, (short) 2, (short) 3), 1)).containsExactly((short) 1, (short) 3);
		assertThat(ShortArrays.remove(ShortArrays.of((short) 1, (short) 2, (short) 3), 2)).containsExactly((short) 1, (short) 2);
	}

	@Test
	void testRemoveInvalid() {
		assertThatNullPointerException().isThrownBy(() -> ShortArrays.remove(null, 0));
		assertThatIllegalArgumentException().isThrownBy(() -> ShortArrays.remove(ShortArrays.EMPTY, 0));
		assertThatIllegalArgumentException().isThrownBy(() -> ShortArrays.remove(ShortArrays.of(VALUES), -1));
		assertThatIllegalArgumentException().isThrownBy(() -> ShortArrays.remove(ShortArrays.of(VALUES), 2));
	}

	@Test
	void testConcat() {
		assertThat(ShortArrays.concat()).isEmpty();
		assertThat(ShortArrays.concat(ShortArrays.singleton(VALUES[0]))).containsExactly(VALUES[0]);
		assertThat(ShortArrays.concat(ShortArrays.singleton(VALUES[0]), ShortArrays.singleton(VALUES[1]))).containsExactly(VALUES);
		assertThat(ShortArrays.concat(List.of(ShortArrays.singleton(VALUES[0]), ShortArrays.singleton(VALUES[1])))).containsExactly(VALUES);
	}

	@Test
	void testConcatInvalid() {
		assertThatNullPointerException().isThrownBy(() -> ShortArrays.concat((short[][]) null));
		assertThatNullPointerException().isThrownBy(() -> ShortArrays.concat((short[]) null));
		assertThatNullPointerException().isThrownBy(() -> ShortArrays.concat((List<short[]>) null));
		assertThatNullPointerException().isThrownBy(() -> ShortArrays.concat(Collections.singletonList(null)));
	}

	@Test
	void testJoin() {
		assertThat(ShortArrays.join(ShortArrays.EMPTY, ShortArrays.singleton(VALUES[0]), ShortArrays.singleton(VALUES[1]))).containsExactly(VALUES);
		assertThat(ShortArrays.join(ShortArrays.singleton((short) 0))).isEmpty();
		assertThat(ShortArrays.join(ShortArrays.singleton((short) 0), ShortArrays.singleton(VALUES[0]))).containsExactly(VALUES[0]);
		assertThat(ShortArrays.join(ShortArrays.singleton((short) 0), ShortArrays.singleton(VALUES[0]), ShortArrays.singleton(VALUES[1]))).containsExactly(VALUES[0], (short) 0, VALUES[1]);
		assertThat(ShortArrays.join(ShortArrays.singleton((short) 0), List.of(ShortArrays.singleton(VALUES[0]), ShortArrays.singleton(VALUES[1])))).containsExactly(VALUES[0], (short) 0, VALUES[1]);
	}

	@Test
	void testJoinInvalid() {
		assertThatNullPointerException().isThrownBy(() -> ShortArrays.join(null, ShortArrays.of(VALUES)));
		assertThatNullPointerException().isThrownBy(() -> ShortArrays.join(ShortArrays.of(VALUES), (short[][]) null));
		assertThatNullPointerException().isThrownBy(() -> ShortArrays.join(ShortArrays.of(VALUES), (short[]) null));
		assertThatNullPointerException().isThrownBy(() -> ShortArrays.join(ShortArrays.of(VALUES), (List<short[]>) null));
		assertThatNullPointerException().isThrownBy(() -> ShortArrays.join(ShortArrays.of(VALUES), Collections.singletonList(null)));
	}

	@Test
	void testSingleton() {
		assertThat(ShortArrays.singleton(VALUES[0])).containsExactly(VALUES[0]);
	}

	@Test
	void testOfShorts() {
		assertThat(ShortArrays.of()).isEmpty();
		assertThat(ShortArrays.of(VALUES)).containsExactly(VALUES);
	}

	@Test
	void testOfShortsInvalid() {
		assertThatNullPointerException().isThrownBy(() -> ShortArrays.of((short[]) null));
	}

	@Test
	void testOfBoxedToBoxed() {
		assertThat(ShortArrays.of(ShortArrays.toBoxed(ShortArrays.EMPTY))).isEmpty();
		assertThat(ShortArrays.of(ShortArrays.toBoxed(ShortArrays.of(VALUES)))).containsExactly(VALUES);
		assertThat(ShortArrays.toBoxed(ShortArrays.of(VALUES))).isInstanceOf(Short[].class);
		assertThat(ShortArrays.of(ShortArrays.toBoxed(ShortArrays.of(VALUES)))).isInstanceOf(short[].class);
	}

	@Test
	void testOfBoxedInvalid() {
		assertThatNullPointerException().isThrownBy(() -> ShortArrays.of((Short[]) null));
	}

	@Test
	void testToBoxedInvalid() {
		assertThatNullPointerException().isThrownBy(() -> ShortArrays.toBoxed(null));
	}
}