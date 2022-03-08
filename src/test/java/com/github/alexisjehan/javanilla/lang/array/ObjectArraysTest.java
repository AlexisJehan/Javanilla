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
 * <p>{@link ObjectArrays} unit tests.</p>
 */
final class ObjectArraysTest {

	private static final Integer[] VALUES = {1, null};

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
		assertThat(ObjectArrays.nullToEmpty(Integer.class, ObjectArrays.of(VALUES))).containsExactly(VALUES);
	}

	@Test
	void testNullToDefault() {
		assertThat(ObjectArrays.nullToDefault(null, ObjectArrays.singleton(0))).containsExactly(0);
		assertThat(ObjectArrays.nullToDefault(ObjectArrays.empty(Integer.class), ObjectArrays.singleton(0))).isEmpty();
		assertThat(ObjectArrays.nullToDefault(ObjectArrays.of(VALUES), ObjectArrays.singleton(0))).containsExactly(VALUES);
	}

	@Test
	void testNullToDefaultInvalid() {
		assertThatNullPointerException().isThrownBy(() -> ObjectArrays.nullToDefault(ObjectArrays.of(VALUES), null));
	}

	@Test
	void testEmptyToNull() {
		assertThat(ObjectArrays.<Integer>emptyToNull(null)).isNull();
		assertThat(ObjectArrays.emptyToNull(ObjectArrays.empty(Integer.class))).isNull();
		assertThat(ObjectArrays.emptyToNull(ObjectArrays.of(VALUES))).containsExactly(VALUES);
	}

	@Test
	void testEmptyToDefault() {
		assertThat(ObjectArrays.emptyToDefault(null, ObjectArrays.singleton(0))).isNull();
		assertThat(ObjectArrays.emptyToDefault(ObjectArrays.empty(Integer.class), ObjectArrays.singleton(0))).containsExactly(0);
		assertThat(ObjectArrays.emptyToDefault(ObjectArrays.of(VALUES), ObjectArrays.singleton(0))).containsExactly(VALUES);
	}

	@Test
	void testEmptyToDefaultInvalid() {
		assertThatIllegalArgumentException().isThrownBy(() -> ObjectArrays.emptyToDefault(ObjectArrays.of(VALUES), ObjectArrays.empty(Integer.class)));
	}

	@Test
	void testIsEmpty() {
		assertThat(ObjectArrays.isEmpty(ObjectArrays.empty(Integer.class))).isTrue();
		assertThat(ObjectArrays.isEmpty(ObjectArrays.of(VALUES))).isFalse();
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
		assertThatNullPointerException().isThrownBy(() -> ObjectArrays.containsAny(ObjectArrays.of(VALUES), (Object[]) null));
		assertThatIllegalArgumentException().isThrownBy(() -> ObjectArrays.containsAny(ObjectArrays.of(VALUES)));
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
		assertThatNullPointerException().isThrownBy(() -> ObjectArrays.containsAll(ObjectArrays.of(VALUES), (Object[]) null));
		assertThatIllegalArgumentException().isThrownBy(() -> ObjectArrays.containsAll(ObjectArrays.of(VALUES)));
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
		assertThatNullPointerException().isThrownBy(() -> ObjectArrays.containsOnce(ObjectArrays.of(VALUES), (Object[]) null));
		assertThatIllegalArgumentException().isThrownBy(() -> ObjectArrays.containsOnce(ObjectArrays.of(VALUES)));
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
		assertThatNullPointerException().isThrownBy(() -> ObjectArrays.containsOnly(ObjectArrays.of(VALUES), (Object[]) null));
		assertThatIllegalArgumentException().isThrownBy(() -> ObjectArrays.containsOnly(ObjectArrays.of(VALUES)));
	}

	@Test
	void testIndexOf() {
		assertThat(ObjectArrays.indexOf(ObjectArrays.empty(Integer.class), 1)).isEqualTo(-1);
		assertThat(ObjectArrays.indexOf(ObjectArrays.of(1, null, 1), 1)).isZero();
		assertThat(ObjectArrays.indexOf(ObjectArrays.of(1, null, 1), null)).isEqualTo(1);
		assertThat(ObjectArrays.indexOf(ObjectArrays.of(1, null, 1), 1, 1)).isEqualTo(2);
		assertThat(ObjectArrays.indexOf(ObjectArrays.of(1, null, 1), null, 2)).isEqualTo(-1);
	}

	@Test
	void testIndexOfInvalid() {
		assertThatNullPointerException().isThrownBy(() -> ObjectArrays.indexOf(null, 1));
		assertThatIllegalArgumentException().isThrownBy(() -> ObjectArrays.indexOf(ObjectArrays.of(VALUES), 1, -1));
		assertThatIllegalArgumentException().isThrownBy(() -> ObjectArrays.indexOf(ObjectArrays.of(VALUES), 1, 2));
	}

	@Test
	void testLastIndexOf() {
		assertThat(ObjectArrays.lastIndexOf(ObjectArrays.empty(Integer.class), 1)).isEqualTo(-1);
		assertThat(ObjectArrays.lastIndexOf(ObjectArrays.of(1, null, 1), 1)).isEqualTo(2);
		assertThat(ObjectArrays.lastIndexOf(ObjectArrays.of(1, null, 1), null)).isEqualTo(1);
		assertThat(ObjectArrays.lastIndexOf(ObjectArrays.of(1, null, 1), 1, 1)).isEqualTo(2);
		assertThat(ObjectArrays.lastIndexOf(ObjectArrays.of(1, null, 1), null, 2)).isEqualTo(-1);
	}

	@Test
	void testLastIndexOfInvalid() {
		assertThatNullPointerException().isThrownBy(() -> ObjectArrays.lastIndexOf(null, 1));
		assertThatIllegalArgumentException().isThrownBy(() -> ObjectArrays.lastIndexOf(ObjectArrays.of(VALUES), 1, -1));
		assertThatIllegalArgumentException().isThrownBy(() -> ObjectArrays.lastIndexOf(ObjectArrays.of(VALUES), 1, 2));
	}

	@Test
	void testFrequency() {
		assertThat(ObjectArrays.frequency(ObjectArrays.empty(Integer.class), 1)).isZero();
		assertThat(ObjectArrays.frequency(ObjectArrays.of(1, null, 1), 1)).isEqualTo(2);
		assertThat(ObjectArrays.frequency(ObjectArrays.of(1, null, 1), null)).isEqualTo(1);
	}

	@Test
	void testFrequencyInvalid() {
		assertThatNullPointerException().isThrownBy(() -> ObjectArrays.frequency(null, 1));
	}

	@Test
	@SuppressWarnings("deprecation")
	void testShuffle() {
		assertThat(ObjectArrays.singleton(1)).satisfies(array -> {
			ObjectArrays.shuffle(array);
			assertThat(array).containsExactly(1);
		});
		assertThat(ObjectArrays.of(1, 2, 1, 2)).satisfies(array -> {
			ObjectArrays.shuffle(array, new Random());
			assertThat(array).containsExactlyInAnyOrder(1, 2, 1, 2);
		});
	}

	@Test
	@SuppressWarnings("deprecation")
	void testShuffleInvalid() {
		assertThatNullPointerException().isThrownBy(() -> ObjectArrays.shuffle(null));
		assertThatNullPointerException().isThrownBy(() -> ObjectArrays.shuffle(ObjectArrays.of(VALUES), null));
	}

	@Test
	void testReverse() {
		assertThat(ObjectArrays.singleton(1)).satisfies(array -> {
			ObjectArrays.reverse(array);
			assertThat(array).containsExactly(1);
		});

		// Even
		assertThat(ObjectArrays.of(1, 2, 1, 2)).satisfies(array -> {
			ObjectArrays.reverse(array);
			assertThat(array).containsExactly(2, 1, 2, 1);
		});

		// Odd
		assertThat(ObjectArrays.of(1, 2, 2)).satisfies(array -> {
			ObjectArrays.reverse(array);
			assertThat(array).containsExactly(2, 2, 1);
		});
	}

	@Test
	void testReverseInvalid() {
		assertThatNullPointerException().isThrownBy(() -> ObjectArrays.reverse(null));
	}

	@Test
	void testReorder() {
		assertThat(ObjectArrays.singleton(1)).satisfies(array -> {
			ObjectArrays.reorder(array, 0);
			assertThat(array).containsExactly(1);
		});
		assertThat(ObjectArrays.of(1, 2, 1, 2)).satisfies(array -> {
			ObjectArrays.reorder(array, 2, 0, 3, 1);
			assertThat(array).containsExactly(1, 1, 2, 2);
		});
	}

	@Test
	void testReorderInvalid() {
		assertThatNullPointerException().isThrownBy(() -> ObjectArrays.reorder(null));
		assertThatNullPointerException().isThrownBy(() -> ObjectArrays.reorder(ObjectArrays.of(VALUES), (int[]) null));
		assertThatIllegalArgumentException().isThrownBy(() -> ObjectArrays.reorder(ObjectArrays.of(VALUES), IntArrays.singleton(0)));
		assertThatIllegalArgumentException().isThrownBy(() -> ObjectArrays.reorder(ObjectArrays.of(VALUES), IntArrays.of(0, 0)));
		assertThatIllegalArgumentException().isThrownBy(() -> ObjectArrays.reorder(ObjectArrays.of(VALUES), IntArrays.of(-1, 1)));
		assertThatIllegalArgumentException().isThrownBy(() -> ObjectArrays.reorder(ObjectArrays.of(VALUES), IntArrays.of(2, 1)));
		assertThatIllegalArgumentException().isThrownBy(() -> ObjectArrays.reorder(ObjectArrays.of(VALUES), IntArrays.of(0, -1)));
		assertThatIllegalArgumentException().isThrownBy(() -> ObjectArrays.reorder(ObjectArrays.of(VALUES), IntArrays.of(0, 2)));
	}

	@Test
	void testSwap() {
		assertThat(ObjectArrays.singleton(1)).satisfies(array -> {
			ObjectArrays.swap(array, 0, 0);
			assertThat(array).containsExactly(1);
		});
		assertThat(ObjectArrays.of(1, 2, 1, 2)).satisfies(array -> {
			ObjectArrays.swap(array, 1, 2);
			assertThat(array).containsExactly(1, 1, 2, 2);
		});
	}

	@Test
	void testSwapInvalid() {
		assertThatNullPointerException().isThrownBy(() -> ObjectArrays.swap(null, 0, 0));
		assertThatIllegalArgumentException().isThrownBy(() -> ObjectArrays.swap(ObjectArrays.of(VALUES), -1, 1));
		assertThatIllegalArgumentException().isThrownBy(() -> ObjectArrays.swap(ObjectArrays.of(VALUES), 2, 1));
		assertThatIllegalArgumentException().isThrownBy(() -> ObjectArrays.swap(ObjectArrays.of(VALUES), 0, -1));
		assertThatIllegalArgumentException().isThrownBy(() -> ObjectArrays.swap(ObjectArrays.of(VALUES), 0, 2));
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
		assertThatIllegalArgumentException().isThrownBy(() -> ObjectArrays.add(ObjectArrays.of(VALUES), -1, 0));
		assertThatIllegalArgumentException().isThrownBy(() -> ObjectArrays.add(ObjectArrays.of(VALUES), 3, 0));
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
		assertThatIllegalArgumentException().isThrownBy(() -> ObjectArrays.remove(ObjectArrays.of(VALUES), -1));
		assertThatIllegalArgumentException().isThrownBy(() -> ObjectArrays.remove(ObjectArrays.of(VALUES), 2));
	}

	@Test
	void testConcat() {
		assertThat(ObjectArrays.concat(Integer.class)).isEmpty();
		assertThat(ObjectArrays.concat(Integer.class, ObjectArrays.singleton(Integer.class, VALUES[0]))).containsExactly(VALUES[0]);
		assertThat(ObjectArrays.concat(Integer.class, ObjectArrays.singleton(Integer.class, VALUES[0]), ObjectArrays.singleton(Integer.class, VALUES[1]))).containsExactly(VALUES);
		assertThat(ObjectArrays.concat(Integer.class, List.of(ObjectArrays.singleton(Integer.class, VALUES[0]), ObjectArrays.singleton(Integer.class, VALUES[1])))).containsExactly(VALUES);
	}

	@Test
	void testConcatInvalid() {
		assertThatNullPointerException().isThrownBy(() -> ObjectArrays.concat(null, ObjectArrays.of(VALUES)));
		assertThatNullPointerException().isThrownBy(() -> ObjectArrays.concat(Integer.class, (Integer[][]) null));
		assertThatNullPointerException().isThrownBy(() -> ObjectArrays.concat(Integer.class, (Integer[]) null));
		assertThatNullPointerException().isThrownBy(() -> ObjectArrays.concat(Integer.class, (List<Integer[]>) null));
		assertThatNullPointerException().isThrownBy(() -> ObjectArrays.concat(Integer.class, Collections.singletonList(null)));
	}

	@Test
	@SuppressWarnings("deprecation")
	void testJoin() {
		assertThat(ObjectArrays.join(Integer.class, ObjectArrays.empty(Integer.class), ObjectArrays.singleton(Integer.class, VALUES[0]), ObjectArrays.singleton(Integer.class, VALUES[1]))).containsExactly(VALUES);
		assertThat(ObjectArrays.join(Integer.class, ObjectArrays.singleton(0))).isEmpty();
		assertThat(ObjectArrays.join(Integer.class, ObjectArrays.singleton(0), ObjectArrays.singleton(Integer.class, VALUES[0]))).containsExactly(VALUES[0]);
		assertThat(ObjectArrays.join(Integer.class, ObjectArrays.singleton(0), ObjectArrays.singleton(Integer.class, VALUES[0]), ObjectArrays.singleton(Integer.class, VALUES[1]))).containsExactly(VALUES[0], 0, VALUES[1]);
		assertThat(ObjectArrays.join(Integer.class, ObjectArrays.singleton(0), List.of(ObjectArrays.singleton(Integer.class, VALUES[0]), ObjectArrays.singleton(Integer.class, VALUES[1])))).containsExactly(VALUES[0], 0, VALUES[1]);

		assertThat(ObjectArrays.join(ObjectArrays.empty(Integer.class), ObjectArrays.singleton(Integer.class, VALUES[0]), ObjectArrays.singleton(Integer.class, VALUES[1]))).containsExactly(VALUES);
		assertThat(ObjectArrays.join(ObjectArrays.singleton(0))).isEmpty();
		assertThat(ObjectArrays.join(ObjectArrays.singleton(0), ObjectArrays.singleton(Integer.class, VALUES[0]))).containsExactly(VALUES[0]);
		assertThat(ObjectArrays.join(ObjectArrays.singleton(0), ObjectArrays.singleton(Integer.class, VALUES[0]), ObjectArrays.singleton(Integer.class, VALUES[1]))).containsExactly(VALUES[0], 0, VALUES[1]);
		assertThat(ObjectArrays.join(ObjectArrays.singleton(0), List.of(ObjectArrays.singleton(Integer.class, VALUES[0]), ObjectArrays.singleton(Integer.class, VALUES[1])))).containsExactly(VALUES[0], 0, VALUES[1]);
	}

	@Test
	@SuppressWarnings("deprecation")
	void testJoinInvalid() {
		assertThatNullPointerException().isThrownBy(() -> ObjectArrays.join((Class<Integer>) null, ObjectArrays.of(VALUES), ObjectArrays.of(VALUES)));
		assertThatNullPointerException().isThrownBy(() -> ObjectArrays.join(Integer.class, null, ObjectArrays.of(VALUES)));
		assertThatNullPointerException().isThrownBy(() -> ObjectArrays.join(Integer.class, ObjectArrays.of(VALUES), (Integer[][]) null));
		assertThatNullPointerException().isThrownBy(() -> ObjectArrays.join(Integer.class, ObjectArrays.of(VALUES), (Integer[]) null));
		assertThatNullPointerException().isThrownBy(() -> ObjectArrays.join(Integer.class, ObjectArrays.of(VALUES), (List<Integer[]>) null));
		assertThatNullPointerException().isThrownBy(() -> ObjectArrays.join(Integer.class, ObjectArrays.of(VALUES), Collections.singletonList(null)));

		assertThatNullPointerException().isThrownBy(() -> ObjectArrays.join((Integer[]) null, ObjectArrays.of(VALUES)));
		assertThatNullPointerException().isThrownBy(() -> ObjectArrays.join(ObjectArrays.of(VALUES), (Integer[][]) null));
		assertThatNullPointerException().isThrownBy(() -> ObjectArrays.join(ObjectArrays.of(VALUES), (Integer[]) null));
		assertThatNullPointerException().isThrownBy(() -> ObjectArrays.join(ObjectArrays.of(VALUES), (List<Integer[]>) null));
		assertThatNullPointerException().isThrownBy(() -> ObjectArrays.join(ObjectArrays.of(VALUES), Collections.singletonList(null)));
	}

	@Test
	void testSingleton() {
		assertThat(ObjectArrays.singleton(VALUES[0])).containsExactly(VALUES[0]);
		assertThat(ObjectArrays.singleton(Integer.class, VALUES[0])).containsExactly(VALUES[0]);
	}

	@Test
	void testSingletonInvalid() {
		assertThatNullPointerException().isThrownBy(() -> ObjectArrays.singleton(null));
		assertThatNullPointerException().isThrownBy(() -> ObjectArrays.singleton(null, VALUES[0]));
	}

	@Test
	void testOf() {
		assertThat(ObjectArrays.<Integer>of()).isEmpty();
		assertThat(ObjectArrays.of(VALUES)).containsExactly(VALUES);
	}

	@Test
	void testOfInvalid() {
		assertThatNullPointerException().isThrownBy(() -> ObjectArrays.of((Integer[]) null));
	}
}