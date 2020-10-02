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
import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.assertj.core.api.Assertions.assertThatNullPointerException;

/**
 * <p>{@link FloatArrays} unit tests.</p>
 */
final class FloatArraysTest {

	private static final float[] VALUES = {1.0f, 2.0f};

	@Test
	void testEmpty() {
		assertThat(FloatArrays.EMPTY).isEmpty();
	}

	@Test
	void testNullToEmpty() {
		assertThat(FloatArrays.nullToEmpty(null)).isEmpty();
		assertThat(FloatArrays.nullToEmpty(FloatArrays.EMPTY)).isEmpty();
		assertThat(FloatArrays.nullToEmpty(FloatArrays.of(VALUES))).containsExactly(VALUES);
	}

	@Test
	void testNullToDefault() {
		assertThat(FloatArrays.nullToDefault(null, FloatArrays.singleton(0.0f))).containsExactly(0.0f);
		assertThat(FloatArrays.nullToDefault(FloatArrays.EMPTY, FloatArrays.singleton(0.0f))).isEmpty();
		assertThat(FloatArrays.nullToDefault(FloatArrays.of(VALUES), FloatArrays.singleton(0.0f))).containsExactly(VALUES);
	}

	@Test
	void testNullToDefaultInvalid() {
		assertThatNullPointerException().isThrownBy(() -> FloatArrays.nullToDefault(FloatArrays.of(VALUES), null));
	}

	@Test
	void testEmptyToNull() {
		assertThat(FloatArrays.emptyToNull(null)).isNull();
		assertThat(FloatArrays.emptyToNull(FloatArrays.EMPTY)).isNull();
		assertThat(FloatArrays.emptyToNull(FloatArrays.of(VALUES))).containsExactly(VALUES);
	}

	@Test
	void testEmptyToDefault() {
		assertThat(FloatArrays.emptyToDefault(null, FloatArrays.singleton(0.0f))).isNull();
		assertThat(FloatArrays.emptyToDefault(FloatArrays.EMPTY, FloatArrays.singleton(0.0f))).containsExactly(0.0f);
		assertThat(FloatArrays.emptyToDefault(FloatArrays.of(VALUES), FloatArrays.singleton(0.0f))).containsExactly(VALUES);
	}

	@Test
	void testEmptyToDefaultInvalid() {
		assertThatIllegalArgumentException().isThrownBy(() -> FloatArrays.emptyToDefault(FloatArrays.of(VALUES), FloatArrays.EMPTY));
	}

	@Test
	void testIsEmpty() {
		assertThat(FloatArrays.isEmpty(FloatArrays.EMPTY)).isTrue();
		assertThat(FloatArrays.isEmpty(FloatArrays.of(VALUES))).isFalse();
	}

	@Test
	void testIsEmptyInvalid() {
		assertThatNullPointerException().isThrownBy(() -> FloatArrays.isEmpty(null));
	}

	@Test
	void testContainsAny() {
		assertThat(FloatArrays.containsAny(FloatArrays.EMPTY, 1.0f)).isFalse();
		assertThat(FloatArrays.containsAny(FloatArrays.singleton(1.0f), 1.0f)).isTrue();
		assertThat(FloatArrays.containsAny(FloatArrays.singleton(1.0f), 2.0f)).isFalse();
		assertThat(FloatArrays.containsAny(FloatArrays.singleton(1.0f), 1.0f, 2.0f)).isTrue();
	}

	@Test
	void testContainsAnyInvalid() {
		assertThatNullPointerException().isThrownBy(() -> FloatArrays.containsAny(null, 1.0f));
		assertThatNullPointerException().isThrownBy(() -> FloatArrays.containsAny(FloatArrays.of(VALUES), (float[]) null));
		assertThatIllegalArgumentException().isThrownBy(() -> FloatArrays.containsAny(FloatArrays.of(VALUES)));
	}

	@Test
	void testContainsAll() {
		assertThat(FloatArrays.containsAll(FloatArrays.EMPTY, 1.0f)).isFalse();
		assertThat(FloatArrays.containsAll(FloatArrays.singleton(1.0f), 1.0f)).isTrue();
		assertThat(FloatArrays.containsAll(FloatArrays.singleton(1.0f), 2.0f)).isFalse();
		assertThat(FloatArrays.containsAll(FloatArrays.singleton(1.0f), 1.0f, 2.0f)).isFalse();
		assertThat(FloatArrays.containsAll(FloatArrays.of(1.0f, 2.0f), 1.0f)).isTrue();
		assertThat(FloatArrays.containsAll(FloatArrays.of(1.0f, 2.0f), 2.0f)).isTrue();
		assertThat(FloatArrays.containsAll(FloatArrays.of(1.0f, 2.0f), 1.0f, 2.0f)).isTrue();
	}

	@Test
	void testContainsAllInvalid() {
		assertThatNullPointerException().isThrownBy(() -> FloatArrays.containsAll(null, 1.0f));
		assertThatNullPointerException().isThrownBy(() -> FloatArrays.containsAll(FloatArrays.of(VALUES), (float[]) null));
		assertThatIllegalArgumentException().isThrownBy(() -> FloatArrays.containsAll(FloatArrays.of(VALUES)));
	}

	@Test
	void testContainsOnce() {
		assertThat(FloatArrays.containsOnce(FloatArrays.EMPTY, 1.0f)).isFalse();
		assertThat(FloatArrays.containsOnce(FloatArrays.singleton(1.0f), 1.0f)).isTrue();
		assertThat(FloatArrays.containsOnce(FloatArrays.singleton(1.0f), 2.0f)).isFalse();
		assertThat(FloatArrays.containsOnce(FloatArrays.singleton(1.0f), 1.0f, 2.0f)).isFalse();
		assertThat(FloatArrays.containsOnce(FloatArrays.of(1.0f, 1.0f), 1.0f)).isFalse();
		assertThat(FloatArrays.containsOnce(FloatArrays.of(1.0f, 1.0f), 2.0f)).isFalse();
		assertThat(FloatArrays.containsOnce(FloatArrays.of(1.0f, 1.0f), 1.0f, 2.0f)).isFalse();
	}

	@Test
	void testContainsOnceInvalid() {
		assertThatNullPointerException().isThrownBy(() -> FloatArrays.containsOnce(null, 1.0f));
		assertThatNullPointerException().isThrownBy(() -> FloatArrays.containsOnce(FloatArrays.of(VALUES), (float[]) null));
		assertThatIllegalArgumentException().isThrownBy(() -> FloatArrays.containsOnce(FloatArrays.of(VALUES)));
	}

	@Test
	void testContainsOnly() {
		assertThat(FloatArrays.containsOnly(FloatArrays.EMPTY, 1.0f)).isFalse();
		assertThat(FloatArrays.containsOnly(FloatArrays.singleton(1.0f), 1.0f)).isTrue();
		assertThat(FloatArrays.containsOnly(FloatArrays.singleton(1.0f), 2.0f)).isFalse();
		assertThat(FloatArrays.containsOnly(FloatArrays.singleton(1.0f), 1.0f, 2.0f)).isTrue();
		assertThat(FloatArrays.containsOnly(FloatArrays.of(1.0f, 2.0f), 1.0f)).isFalse();
		assertThat(FloatArrays.containsOnly(FloatArrays.of(1.0f, 2.0f), 2.0f)).isFalse();
		assertThat(FloatArrays.containsOnly(FloatArrays.of(1.0f, 2.0f), 1.0f, 2.0f)).isTrue();
	}

	@Test
	void testContainsOnlyInvalid() {
		assertThatNullPointerException().isThrownBy(() -> FloatArrays.containsOnly(null, 1.0f));
		assertThatNullPointerException().isThrownBy(() -> FloatArrays.containsOnly(FloatArrays.of(VALUES), (float[]) null));
		assertThatIllegalArgumentException().isThrownBy(() -> FloatArrays.containsOnly(FloatArrays.of(VALUES)));
	}

	@Test
	void testIndexOf() {
		assertThat(FloatArrays.indexOf(FloatArrays.EMPTY, 1.0f)).isEqualTo(-1);
		assertThat(FloatArrays.indexOf(FloatArrays.of(1.0f, 2.0f, 1.0f), 1.0f)).isZero();
		assertThat(FloatArrays.indexOf(FloatArrays.of(1.0f, 2.0f, 1.0f), 2.0f)).isEqualTo(1);
		assertThat(FloatArrays.indexOf(FloatArrays.of(1.0f, 2.0f, 1.0f), 1.0f, 1)).isEqualTo(2);
		assertThat(FloatArrays.indexOf(FloatArrays.of(1.0f, 2.0f, 1.0f), 2.0f, 2)).isEqualTo(-1);
	}

	@Test
	void testIndexOfInvalid() {
		assertThatNullPointerException().isThrownBy(() -> FloatArrays.indexOf(null, 1.0f));
		assertThatIllegalArgumentException().isThrownBy(() -> FloatArrays.indexOf(FloatArrays.of(VALUES), 1.0f, -1));
		assertThatIllegalArgumentException().isThrownBy(() -> FloatArrays.indexOf(FloatArrays.of(VALUES), 1.0f, 2));
	}

	@Test
	void testLastIndexOf() {
		assertThat(FloatArrays.lastIndexOf(FloatArrays.EMPTY, 1.0f)).isEqualTo(-1);
		assertThat(FloatArrays.lastIndexOf(FloatArrays.of(1.0f, 2.0f, 1.0f), 1.0f)).isEqualTo(2);
		assertThat(FloatArrays.lastIndexOf(FloatArrays.of(1.0f, 2.0f, 1.0f), 2.0f)).isEqualTo(1);
		assertThat(FloatArrays.lastIndexOf(FloatArrays.of(1.0f, 2.0f, 1.0f), 1.0f, 1)).isEqualTo(2);
		assertThat(FloatArrays.lastIndexOf(FloatArrays.of(1.0f, 2.0f, 1.0f), 2.0f, 2)).isEqualTo(-1);
	}

	@Test
	void testLastIndexOfInvalid() {
		assertThatNullPointerException().isThrownBy(() -> FloatArrays.lastIndexOf(null, 1.0f));
		assertThatIllegalArgumentException().isThrownBy(() -> FloatArrays.lastIndexOf(FloatArrays.of(VALUES), 1.0f, -1));
		assertThatIllegalArgumentException().isThrownBy(() -> FloatArrays.lastIndexOf(FloatArrays.of(VALUES), 1.0f, 2));
	}

	@Test
	void testFrequency() {
		assertThat(FloatArrays.frequency(FloatArrays.EMPTY, 1.0f)).isZero();
		assertThat(FloatArrays.frequency(FloatArrays.of(1.0f, 2.0f, 1.0f), 1.0f)).isEqualTo(2);
		assertThat(FloatArrays.frequency(FloatArrays.of(1.0f, 2.0f, 1.0f), 2.0f)).isEqualTo(1);
	}

	@Test
	void testFrequencyInvalid() {
		assertThatNullPointerException().isThrownBy(() -> FloatArrays.frequency(null, 1.0f));
	}

	@Test
	@SuppressWarnings("deprecation")
	void testShuffle() {
		assertThat(FloatArrays.singleton(1.0f)).satisfies(array -> {
			FloatArrays.shuffle(array);
			assertThat(array).containsExactly(1.0f);
		});
		assertThat(FloatArrays.of(1.0f, 2.0f, 1.0f, 2.0f)).satisfies(array -> {
			FloatArrays.shuffle(array, new Random());
			assertThat(array).containsExactlyInAnyOrder(1.0f, 2.0f, 1.0f, 2.0f);
		});
	}

	@Test
	@SuppressWarnings("deprecation")
	void testShuffleInvalid() {
		assertThatNullPointerException().isThrownBy(() -> FloatArrays.shuffle(null));
		assertThatNullPointerException().isThrownBy(() -> FloatArrays.shuffle(FloatArrays.of(VALUES), null));
	}

	@Test
	void testReverse() {
		assertThat(FloatArrays.singleton(1.0f)).satisfies(array -> {
			FloatArrays.reverse(array);
			assertThat(array).containsExactly(1.0f);
		});

		// Even
		assertThat(FloatArrays.of(1.0f, 2.0f, 1.0f, 2.0f)).satisfies(array -> {
			FloatArrays.reverse(array);
			assertThat(array).containsExactly(2.0f, 1.0f, 2.0f, 1.0f);
		});

		// Odd
		assertThat(FloatArrays.of(1.0f, 2.0f, 2.0f)).satisfies(array -> {
			FloatArrays.reverse(array);
			assertThat(array).containsExactly(2.0f, 2.0f, 1.0f);
		});
	}

	@Test
	void testReverseInvalid() {
		assertThatNullPointerException().isThrownBy(() -> FloatArrays.reverse(null));
	}

	@Test
	void testReorder() {
		assertThat(FloatArrays.singleton(1.0f)).satisfies(array -> {
			FloatArrays.reorder(array, 0);
			assertThat(array).containsExactly(1.0f);
		});
		assertThat(FloatArrays.of(1.0f, 2.0f, 1.0f, 2.0f)).satisfies(array -> {
			FloatArrays.reorder(array, 2, 0, 3, 1);
			assertThat(array).containsExactly(1.0f, 1.0f, 2.0f, 2.0f);
		});
	}

	@Test
	void testReorderInvalid() {
		assertThatNullPointerException().isThrownBy(() -> FloatArrays.reorder(null));
		assertThatNullPointerException().isThrownBy(() -> FloatArrays.reorder(FloatArrays.of(VALUES), (int[]) null));
		assertThatIllegalArgumentException().isThrownBy(() -> FloatArrays.reorder(FloatArrays.of(VALUES), IntArrays.singleton(0)));
		assertThatIllegalArgumentException().isThrownBy(() -> FloatArrays.reorder(FloatArrays.of(VALUES), IntArrays.of(0, 0)));
		assertThatIllegalArgumentException().isThrownBy(() -> FloatArrays.reorder(FloatArrays.of(VALUES), IntArrays.of(-1, 1)));
		assertThatIllegalArgumentException().isThrownBy(() -> FloatArrays.reorder(FloatArrays.of(VALUES), IntArrays.of(2, 1)));
		assertThatIllegalArgumentException().isThrownBy(() -> FloatArrays.reorder(FloatArrays.of(VALUES), IntArrays.of(0, -1)));
		assertThatIllegalArgumentException().isThrownBy(() -> FloatArrays.reorder(FloatArrays.of(VALUES), IntArrays.of(0, 2)));
	}

	@Test
	void testSwap() {
		assertThat(FloatArrays.singleton(1.0f)).satisfies(array -> {
			FloatArrays.swap(array, 0, 0);
			assertThat(array).containsExactly(1.0f);
		});
		assertThat(FloatArrays.of(1.0f, 2.0f, 1.0f, 2.0f)).satisfies(array -> {
			FloatArrays.swap(array, 1, 2);
			assertThat(array).containsExactly(1.0f, 1.0f, 2.0f, 2.0f);
		});
	}

	@Test
	void testSwapInvalid() {
		assertThatNullPointerException().isThrownBy(() -> FloatArrays.swap(null, 0, 0));
		assertThatIllegalArgumentException().isThrownBy(() -> FloatArrays.swap(FloatArrays.of(VALUES), -1, 1));
		assertThatIllegalArgumentException().isThrownBy(() -> FloatArrays.swap(FloatArrays.of(VALUES), 2, 1));
		assertThatIllegalArgumentException().isThrownBy(() -> FloatArrays.swap(FloatArrays.of(VALUES), 0, -1));
		assertThatIllegalArgumentException().isThrownBy(() -> FloatArrays.swap(FloatArrays.of(VALUES), 0, 2));
	}

	@Test
	void testAdd() {
		assertThat(FloatArrays.add(FloatArrays.EMPTY, 0.0f)).containsExactly(0.0f);
		assertThat(FloatArrays.add(FloatArrays.of(1.0f, 2.0f, 3.0f), 0, 0.0f)).containsExactly(0.0f, 1.0f, 2.0f, 3.0f);
		assertThat(FloatArrays.add(FloatArrays.of(1.0f, 2.0f, 3.0f), 1, 0.0f)).containsExactly(1.0f, 0.0f, 2.0f, 3.0f);
		assertThat(FloatArrays.add(FloatArrays.of(1.0f, 2.0f, 3.0f), 2, 0.0f)).containsExactly(1.0f, 2.0f, 0.0f, 3.0f);
		assertThat(FloatArrays.add(FloatArrays.of(1.0f, 2.0f, 3.0f), 3, 0.0f)).containsExactly(1.0f, 2.0f, 3.0f, 0.0f);
		assertThat(FloatArrays.add(FloatArrays.of(1.0f, 2.0f, 3.0f), 0.0f)).containsExactly(1.0f, 2.0f, 3.0f, 0.0f);
	}

	@Test
	void testAddInvalid() {
		assertThatNullPointerException().isThrownBy(() -> FloatArrays.add(null, 0.0f));
		assertThatNullPointerException().isThrownBy(() -> FloatArrays.add(null, 0, 0.0f));
		assertThatIllegalArgumentException().isThrownBy(() -> FloatArrays.add(FloatArrays.of(VALUES), -1, 0.0f));
		assertThatIllegalArgumentException().isThrownBy(() -> FloatArrays.add(FloatArrays.of(VALUES), 3, 0.0f));
	}

	@Test
	void testRemove() {
		assertThat(FloatArrays.remove(FloatArrays.singleton(1.0f), 0)).isEmpty();
		assertThat(FloatArrays.remove(FloatArrays.of(1.0f, 2.0f, 3.0f), 0)).containsExactly(2.0f, 3.0f);
		assertThat(FloatArrays.remove(FloatArrays.of(1.0f, 2.0f, 3.0f), 1)).containsExactly(1.0f, 3.0f);
		assertThat(FloatArrays.remove(FloatArrays.of(1.0f, 2.0f, 3.0f), 2)).containsExactly(1.0f, 2.0f);
	}

	@Test
	void testRemoveInvalid() {
		assertThatNullPointerException().isThrownBy(() -> FloatArrays.remove(null, 0));
		assertThatIllegalArgumentException().isThrownBy(() -> FloatArrays.remove(FloatArrays.EMPTY, 0));
		assertThatIllegalArgumentException().isThrownBy(() -> FloatArrays.remove(FloatArrays.of(VALUES), -1));
		assertThatIllegalArgumentException().isThrownBy(() -> FloatArrays.remove(FloatArrays.of(VALUES), 2));
	}

	@Test
	void testConcat() {
		assertThat(FloatArrays.concat()).isEmpty();
		assertThat(FloatArrays.concat(FloatArrays.singleton(VALUES[0]))).containsExactly(VALUES[0]);
		assertThat(FloatArrays.concat(FloatArrays.singleton(VALUES[0]), FloatArrays.singleton(VALUES[1]))).containsExactly(VALUES);
	}

	@Test
	void testConcatInvalid() {
		assertThatNullPointerException().isThrownBy(() -> FloatArrays.concat((float[][]) null));
		assertThatNullPointerException().isThrownBy(() -> FloatArrays.concat((List<float[]>) null));
		assertThatNullPointerException().isThrownBy(() -> FloatArrays.concat((float[]) null));
	}

	@Test
	void testJoin() {
		assertThat(FloatArrays.join(FloatArrays.EMPTY, FloatArrays.singleton(VALUES[0]), FloatArrays.singleton(VALUES[1]))).containsExactly(VALUES);
		assertThat(FloatArrays.join(FloatArrays.singleton(0.0f))).isEmpty();
		assertThat(FloatArrays.join(FloatArrays.singleton(0.0f), FloatArrays.singleton(VALUES[0]))).containsExactly(VALUES[0]);
		assertThat(FloatArrays.join(FloatArrays.singleton(0.0f), FloatArrays.singleton(VALUES[0]), FloatArrays.singleton(VALUES[1]))).containsExactly(VALUES[0], 0.0f, VALUES[1]);
	}

	@Test
	void testJoinInvalid() {
		assertThatNullPointerException().isThrownBy(() -> FloatArrays.join(null, FloatArrays.of(VALUES)));
		assertThatNullPointerException().isThrownBy(() -> FloatArrays.join(FloatArrays.of(VALUES), (float[][]) null));
		assertThatNullPointerException().isThrownBy(() -> FloatArrays.join(FloatArrays.of(VALUES), (List<float[]>) null));
		assertThatNullPointerException().isThrownBy(() -> FloatArrays.join(FloatArrays.of(VALUES), (float[]) null));
	}

	@Test
	void testSingleton() {
		assertThat(FloatArrays.singleton(VALUES[0])).containsExactly(VALUES[0]);
	}

	@Test
	void testOf() {
		assertThat(FloatArrays.of()).isEmpty();
		assertThat(FloatArrays.of(VALUES)).containsExactly(VALUES);
	}

	@Test
	void testOfInvalid() {
		assertThatNullPointerException().isThrownBy(() -> FloatArrays.of((float[]) null));
	}

	@Test
	void testOfBoxedToBoxed() {
		assertThat(FloatArrays.of(FloatArrays.toBoxed(FloatArrays.EMPTY))).isEmpty();
		assertThat(FloatArrays.of(FloatArrays.toBoxed(FloatArrays.of(VALUES)))).containsExactly(VALUES);
		assertThat(FloatArrays.toBoxed(FloatArrays.of(VALUES))).isInstanceOf(Float[].class);
		assertThat(FloatArrays.of(FloatArrays.toBoxed(FloatArrays.of(VALUES)))).isInstanceOf(float[].class);
	}

	@Test
	void testOfBoxedInvalid() {
		assertThatNullPointerException().isThrownBy(() -> FloatArrays.of((Float[]) null));
	}

	@Test
	void testToBoxedInvalid() {
		assertThatNullPointerException().isThrownBy(() -> FloatArrays.toBoxed(null));
	}
}