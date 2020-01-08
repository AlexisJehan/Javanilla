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
		assertThat(FloatArrays.nullToEmpty(FloatArrays.singleton(1.0f))).containsExactly(1.0f);
	}

	@Test
	void testNullToDefault() {
		assertThat(FloatArrays.nullToDefault(null, FloatArrays.singleton(0.0f))).containsExactly(0.0f);
		assertThat(FloatArrays.nullToDefault(FloatArrays.EMPTY, FloatArrays.singleton(0.0f))).isEmpty();
		assertThat(FloatArrays.nullToDefault(FloatArrays.singleton(1.0f), FloatArrays.singleton(0.0f))).containsExactly(1.0f);
	}

	@Test
	void testNullToDefaultInvalid() {
		assertThatNullPointerException().isThrownBy(() -> FloatArrays.nullToDefault(FloatArrays.singleton(1.0f), null));
	}

	@Test
	void testEmptyToNull() {
		assertThat(FloatArrays.emptyToNull(null)).isNull();
		assertThat(FloatArrays.emptyToNull(FloatArrays.EMPTY)).isNull();
		assertThat(FloatArrays.emptyToNull(FloatArrays.singleton(1.0f))).containsExactly(1.0f);
	}

	@Test
	void testEmptyToDefault() {
		assertThat(FloatArrays.emptyToDefault(null, FloatArrays.singleton(0.0f))).isNull();
		assertThat(FloatArrays.emptyToDefault(FloatArrays.EMPTY, FloatArrays.singleton(0.0f))).containsExactly(0.0f);
		assertThat(FloatArrays.emptyToDefault(FloatArrays.singleton(1.0f), FloatArrays.singleton(0.0f))).containsExactly(1.0f);
	}

	@Test
	void testEmptyToDefaultInvalid() {
		assertThatIllegalArgumentException().isThrownBy(() -> FloatArrays.emptyToDefault(FloatArrays.singleton(1.0f), FloatArrays.EMPTY));
	}

	@Test
	void testIsEmpty() {
		assertThat(FloatArrays.isEmpty(FloatArrays.EMPTY)).isTrue();
		assertThat(FloatArrays.isEmpty(FloatArrays.singleton(1.0f))).isFalse();
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
		assertThatNullPointerException().isThrownBy(() -> FloatArrays.containsAny(FloatArrays.singleton(1.0f), (float[]) null));
		assertThatIllegalArgumentException().isThrownBy(() -> FloatArrays.containsAny(FloatArrays.singleton(1.0f)));
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
		assertThatNullPointerException().isThrownBy(() -> FloatArrays.containsAll(FloatArrays.singleton(1.0f), (float[]) null));
		assertThatIllegalArgumentException().isThrownBy(() -> FloatArrays.containsAll(FloatArrays.singleton(1.0f)));
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
		assertThatNullPointerException().isThrownBy(() -> FloatArrays.containsOnce(FloatArrays.singleton(1.0f), (float[]) null));
		assertThatIllegalArgumentException().isThrownBy(() -> FloatArrays.containsOnce(FloatArrays.singleton(1.0f)));
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
		assertThatNullPointerException().isThrownBy(() -> FloatArrays.containsOnly(FloatArrays.singleton(1.0f), (float[]) null));
		assertThatIllegalArgumentException().isThrownBy(() -> FloatArrays.containsOnly(FloatArrays.singleton(1.0f)));
	}

	@Test
	void testIndexOf() {
		assertThat(FloatArrays.indexOf(FloatArrays.EMPTY, 1.0f)).isEqualTo(-1);
		final var array = FloatArrays.of(1.0f, 2.0f, 1.0f);
		assertThat(FloatArrays.indexOf(array, 1.0f)).isEqualTo(0);
		assertThat(FloatArrays.indexOf(array, 2.0f)).isEqualTo(1);
		assertThat(FloatArrays.indexOf(array, 1.0f, 1)).isEqualTo(2);
		assertThat(FloatArrays.indexOf(array, 2.0f, 2)).isEqualTo(-1);
	}

	@Test
	void testIndexOfInvalid() {
		assertThatNullPointerException().isThrownBy(() -> FloatArrays.indexOf(null, 1.0f));
		assertThatIllegalArgumentException().isThrownBy(() -> FloatArrays.indexOf(FloatArrays.singleton(1.0f), 1.0f, -1));
		assertThatIllegalArgumentException().isThrownBy(() -> FloatArrays.indexOf(FloatArrays.singleton(1.0f), 1.0f, 1));
	}

	@Test
	void testLastIndexOf() {
		assertThat(FloatArrays.lastIndexOf(FloatArrays.EMPTY, 1.0f)).isEqualTo(-1);
		final var array = FloatArrays.of(1.0f, 2.0f, 1.0f);
		assertThat(FloatArrays.lastIndexOf(array, 1.0f)).isEqualTo(2);
		assertThat(FloatArrays.lastIndexOf(array, 2.0f)).isEqualTo(1);
		assertThat(FloatArrays.lastIndexOf(array, 1.0f, 1)).isEqualTo(2);
		assertThat(FloatArrays.lastIndexOf(array, 2.0f, 2)).isEqualTo(-1);
	}

	@Test
	void testLastIndexOfInvalid() {
		assertThatNullPointerException().isThrownBy(() -> FloatArrays.lastIndexOf(null, 1.0f));
		assertThatIllegalArgumentException().isThrownBy(() -> FloatArrays.lastIndexOf(FloatArrays.singleton(1.0f), 1.0f, -1));
		assertThatIllegalArgumentException().isThrownBy(() -> FloatArrays.lastIndexOf(FloatArrays.singleton(1.0f), 1.0f, 1));
	}

	@Test
	void testFrequency() {
		assertThat(FloatArrays.frequency(FloatArrays.EMPTY, 1.0f)).isEqualTo(0);
		final var array = FloatArrays.of(1.0f, 2.0f, 1.0f);
		assertThat(FloatArrays.frequency(array, 1.0f)).isEqualTo(2);
		assertThat(FloatArrays.frequency(array, 2.0f)).isEqualTo(1);
	}

	@Test
	void testFrequencyInvalid() {
		assertThatNullPointerException().isThrownBy(() -> FloatArrays.frequency(null, 1.0f));
	}

	@Test
	void testShuffle() {
		{
			final var array = FloatArrays.singleton(1.0f);
			FloatArrays.shuffle(array);
			assertThat(array).containsExactly(1.0f);
		}
		{
			final var array = FloatArrays.of(1.0f, 2.0f, 1.0f, 2.0f);
			FloatArrays.shuffle(array);
			assertThat(array).containsExactlyInAnyOrder(1.0f, 2.0f, 1.0f, 2.0f);
		}
	}

	@Test
	void testShuffleInvalid() {
		assertThatNullPointerException().isThrownBy(() -> FloatArrays.shuffle(null));
	}

	@Test
	void testReverse() {
		{
			final var array = FloatArrays.singleton(1.0f);
			FloatArrays.reverse(array);
			assertThat(array).containsExactly(1.0f);
		}
		{
			// Even
			final var array = FloatArrays.of(1.0f, 2.0f, 1.0f, 2.0f);
			FloatArrays.reverse(array);
			assertThat(array).containsExactly(2.0f, 1.0f, 2.0f, 1.0f);
		}
		{
			// Odd
			final var array = FloatArrays.of(1.0f, 2.0f, 2.0f);
			FloatArrays.reverse(array);
			assertThat(array).containsExactly(2.0f, 2.0f, 1.0f);
		}
	}

	@Test
	void testReverseInvalid() {
		assertThatNullPointerException().isThrownBy(() -> FloatArrays.reverse(null));
	}

	@Test
	void testReorder() {
		{
			final var array = FloatArrays.singleton(1.0f);
			FloatArrays.reorder(array, 0);
			assertThat(array).containsExactly(1.0f);
		}
		{
			final var array = FloatArrays.of(1.0f, 2.0f, 1.0f, 2.0f);
			FloatArrays.reorder(array, 2, 0, 3, 1);
			assertThat(array).containsExactly(1.0f, 1.0f, 2.0f, 2.0f);
		}
	}

	@Test
	void testReorderInvalid() {
		assertThatNullPointerException().isThrownBy(() -> FloatArrays.reorder(null));
		assertThatNullPointerException().isThrownBy(() -> FloatArrays.reorder(FloatArrays.singleton(1.0f), (int[]) null));
		assertThatIllegalArgumentException().isThrownBy(() -> FloatArrays.reorder(FloatArrays.of(1.0f, 2.0f), IntArrays.singleton(0)));
		assertThatIllegalArgumentException().isThrownBy(() -> FloatArrays.reorder(FloatArrays.of(1.0f, 2.0f), IntArrays.of(0, 0)));
		assertThatIllegalArgumentException().isThrownBy(() -> FloatArrays.reorder(FloatArrays.of(1.0f, 2.0f), IntArrays.of(-1, 1)));
		assertThatIllegalArgumentException().isThrownBy(() -> FloatArrays.reorder(FloatArrays.of(1.0f, 2.0f), IntArrays.of(2, 1)));
		assertThatIllegalArgumentException().isThrownBy(() -> FloatArrays.reorder(FloatArrays.of(1.0f, 2.0f), IntArrays.of(0, -1)));
		assertThatIllegalArgumentException().isThrownBy(() -> FloatArrays.reorder(FloatArrays.of(1.0f, 2.0f), IntArrays.of(0, 2)));
	}

	@Test
	void testSwap() {
		{
			final var array = FloatArrays.singleton(1.0f);
			FloatArrays.swap(array, 0, 0);
			assertThat(array).containsExactly(1.0f);
		}
		{
			final var array = FloatArrays.of(1.0f, 2.0f, 1.0f, 2.0f);
			FloatArrays.swap(array, 1, 2);
			assertThat(array).containsExactly(1.0f, 1.0f, 2.0f, 2.0f);
		}
	}

	@Test
	void testSwapInvalid() {
		assertThatNullPointerException().isThrownBy(() -> FloatArrays.swap(null, 0, 0));
		assertThatIllegalArgumentException().isThrownBy(() -> FloatArrays.swap(FloatArrays.of(1.0f, 2.0f), -1, 1));
		assertThatIllegalArgumentException().isThrownBy(() -> FloatArrays.swap(FloatArrays.of(1.0f, 2.0f), 2, 1));
		assertThatIllegalArgumentException().isThrownBy(() -> FloatArrays.swap(FloatArrays.of(1.0f, 2.0f), 0, -1));
		assertThatIllegalArgumentException().isThrownBy(() -> FloatArrays.swap(FloatArrays.of(1.0f, 2.0f), 0, 2));
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
		assertThatIllegalArgumentException().isThrownBy(() -> FloatArrays.add(FloatArrays.singleton(1), -1, 0.0f));
		assertThatIllegalArgumentException().isThrownBy(() -> FloatArrays.add(FloatArrays.singleton(1), 2, 0.0f));
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
		assertThatIllegalArgumentException().isThrownBy(() -> FloatArrays.remove(FloatArrays.singleton(1.0f), -1));
		assertThatIllegalArgumentException().isThrownBy(() -> FloatArrays.remove(FloatArrays.singleton(1.0f), 1));
	}

	@Test
	void testConcat() {
		assertThat(FloatArrays.concat()).isEmpty();
		assertThat(FloatArrays.concat(FloatArrays.singleton(1.0f))).containsExactly(1.0f);
		assertThat(FloatArrays.concat(FloatArrays.singleton(1.0f), FloatArrays.singleton(2.0f))).containsExactly(1.0f, 2.0f);
	}

	@Test
	void testConcatInvalid() {
		assertThatNullPointerException().isThrownBy(() -> FloatArrays.concat((float[][]) null));
		assertThatNullPointerException().isThrownBy(() -> FloatArrays.concat((List<float[]>) null));
		assertThatNullPointerException().isThrownBy(() -> FloatArrays.concat((float[]) null));
	}

	@Test
	void testJoin() {
		assertThat(FloatArrays.join(FloatArrays.EMPTY, FloatArrays.singleton(1.0f), FloatArrays.singleton(2.0f))).containsExactly(1.0f, 2.0f);
		assertThat(FloatArrays.join(FloatArrays.singleton(0.0f))).isEmpty();
		assertThat(FloatArrays.join(FloatArrays.singleton(0.0f), FloatArrays.singleton(1.0f))).containsExactly(1.0f);
		assertThat(FloatArrays.join(FloatArrays.singleton(0.0f), FloatArrays.singleton(1.0f), FloatArrays.singleton(2.0f))).containsExactly(1.0f, 0.0f, 2.0f);
	}

	@Test
	void testJoinInvalid() {
		assertThatNullPointerException().isThrownBy(() -> FloatArrays.join(null, FloatArrays.singleton(1.0f)));
		assertThatNullPointerException().isThrownBy(() -> FloatArrays.join(FloatArrays.singleton(0.0f), (float[][]) null));
		assertThatNullPointerException().isThrownBy(() -> FloatArrays.join(FloatArrays.singleton(0.0f), (List<float[]>) null));
		assertThatNullPointerException().isThrownBy(() -> FloatArrays.join(FloatArrays.singleton(0.0f), (float[]) null));
	}

	@Test
	void testSingleton() {
		assertThat(FloatArrays.singleton(1.0f)).containsExactly(1.0f);
	}

	@Test
	void testOf() {
		assertThat(FloatArrays.of()).isEmpty();
		assertThat(FloatArrays.of(1.0f, 2.0f)).containsExactly(1.0f, 2.0f);
	}

	@Test
	void testOfInvalid() {
		assertThatNullPointerException().isThrownBy(() -> FloatArrays.of((float[]) null));
	}

	@Test
	void testOfBoxedToBoxed() {
		assertThat(FloatArrays.of(FloatArrays.toBoxed(FloatArrays.EMPTY))).isEmpty();
		assertThat(FloatArrays.of(FloatArrays.toBoxed(FloatArrays.of(1.0f, 2.0f)))).containsExactly(1.0f, 2.0f);
		assertThat(FloatArrays.toBoxed(FloatArrays.singleton(1.0f))).isInstanceOf(Float[].class);
		assertThat(FloatArrays.of(FloatArrays.toBoxed(FloatArrays.singleton(1.0f)))).isInstanceOf(float[].class);
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