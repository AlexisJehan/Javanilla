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

import com.github.alexisjehan.javanilla.lang.Strings;
import org.junit.jupiter.api.Test;

import java.nio.ByteOrder;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThatNullPointerException;

/**
 * <p>{@link ByteArrays} unit tests.</p>
 */
final class ByteArraysTest {

	@Test
	void testEmpty() {
		assertThat(ByteArrays.EMPTY).isEmpty();
	}

	@Test
	void testNullToEmpty() {
		assertThat(ByteArrays.nullToEmpty(null)).isEmpty();
		assertThat(ByteArrays.nullToEmpty(ByteArrays.EMPTY)).isEmpty();
		assertThat(ByteArrays.nullToEmpty(ByteArrays.singleton((byte) 1))).containsExactly((byte) 1);
	}

	@Test
	void testNullToDefault() {
		assertThat(ByteArrays.nullToDefault(null, ByteArrays.singleton((byte) 0))).containsExactly((byte) 0);
		assertThat(ByteArrays.nullToDefault(ByteArrays.EMPTY, ByteArrays.singleton((byte) 0))).isEmpty();
		assertThat(ByteArrays.nullToDefault(ByteArrays.singleton((byte) 1), ByteArrays.singleton((byte) 0))).containsExactly((byte) 1);
	}

	@Test
	void testNullToDefaultInvalid() {
		assertThatNullPointerException().isThrownBy(() -> ByteArrays.nullToDefault(ByteArrays.singleton((byte) 1), null));
	}

	@Test
	void testEmptyToNull() {
		assertThat(ByteArrays.emptyToNull(null)).isNull();
		assertThat(ByteArrays.emptyToNull(ByteArrays.EMPTY)).isNull();
		assertThat(ByteArrays.emptyToNull(ByteArrays.singleton((byte) 1))).containsExactly((byte) 1);
	}

	@Test
	void testEmptyToDefault() {
		assertThat(ByteArrays.emptyToDefault(null, ByteArrays.singleton((byte) 0))).isNull();
		assertThat(ByteArrays.emptyToDefault(ByteArrays.EMPTY, ByteArrays.singleton((byte) 0))).containsExactly((byte) 0);
		assertThat(ByteArrays.emptyToDefault(ByteArrays.singleton((byte) 1), ByteArrays.singleton((byte) 0))).containsExactly((byte) 1);
	}

	@Test
	void testEmptyToDefaultInvalid() {
		assertThatIllegalArgumentException().isThrownBy(() -> ByteArrays.emptyToDefault(ByteArrays.singleton((byte) 1), ByteArrays.EMPTY));
	}

	@Test
	void testIsEmpty() {
		assertThat(ByteArrays.isEmpty(ByteArrays.EMPTY)).isTrue();
		assertThat(ByteArrays.isEmpty(ByteArrays.singleton((byte) 1))).isFalse();
	}

	@Test
	void testIsEmptyInvalid() {
		assertThatNullPointerException().isThrownBy(() -> ByteArrays.isEmpty(null));
	}

	@Test
	void testContainsAny() {
		assertThat(ByteArrays.containsAny(ByteArrays.EMPTY, (byte) 1)).isFalse();
		assertThat(ByteArrays.containsAny(ByteArrays.singleton((byte) 1), (byte) 1)).isTrue();
		assertThat(ByteArrays.containsAny(ByteArrays.singleton((byte) 1), (byte) 2)).isFalse();
		assertThat(ByteArrays.containsAny(ByteArrays.singleton((byte) 1), (byte) 1, (byte) 2)).isTrue();
	}

	@Test
	void testContainsAnyInvalid() {
		assertThatNullPointerException().isThrownBy(() -> ByteArrays.containsAny(null, (byte) 1));
		assertThatNullPointerException().isThrownBy(() -> ByteArrays.containsAny(ByteArrays.singleton((byte) 1), (byte[]) null));
		assertThatIllegalArgumentException().isThrownBy(() -> ByteArrays.containsAny(ByteArrays.singleton((byte) 1)));
	}

	@Test
	void testContainsAll() {
		assertThat(ByteArrays.containsAll(ByteArrays.EMPTY, (byte) 1)).isFalse();
		assertThat(ByteArrays.containsAll(ByteArrays.singleton((byte) 1), (byte) 1)).isTrue();
		assertThat(ByteArrays.containsAll(ByteArrays.singleton((byte) 1), (byte) 2)).isFalse();
		assertThat(ByteArrays.containsAll(ByteArrays.singleton((byte) 1), (byte) 1, (byte) 2)).isFalse();
		assertThat(ByteArrays.containsAll(ByteArrays.of((byte) 1, (byte) 2), (byte) 1)).isTrue();
		assertThat(ByteArrays.containsAll(ByteArrays.of((byte) 1, (byte) 2), (byte) 2)).isTrue();
		assertThat(ByteArrays.containsAll(ByteArrays.of((byte) 1, (byte) 2), (byte) 1, (byte) 2)).isTrue();
	}

	@Test
	void testContainsAllInvalid() {
		assertThatNullPointerException().isThrownBy(() -> ByteArrays.containsAll(null, (byte) 1));
		assertThatNullPointerException().isThrownBy(() -> ByteArrays.containsAll(ByteArrays.singleton((byte) 1), (byte[]) null));
		assertThatIllegalArgumentException().isThrownBy(() -> ByteArrays.containsAll(ByteArrays.singleton((byte) 1)));
	}

	@Test
	void testContainsOnce() {
		assertThat(ByteArrays.containsOnce(ByteArrays.EMPTY, (byte) 1)).isFalse();
		assertThat(ByteArrays.containsOnce(ByteArrays.singleton((byte) 1), (byte) 1)).isTrue();
		assertThat(ByteArrays.containsOnce(ByteArrays.singleton((byte) 1), (byte) 2)).isFalse();
		assertThat(ByteArrays.containsOnce(ByteArrays.singleton((byte) 1), (byte) 1, (byte) 2)).isFalse();
		assertThat(ByteArrays.containsOnce(ByteArrays.of((byte) 1, (byte) 1), (byte) 1)).isFalse();
		assertThat(ByteArrays.containsOnce(ByteArrays.of((byte) 1, (byte) 1), (byte) 2)).isFalse();
		assertThat(ByteArrays.containsOnce(ByteArrays.of((byte) 1, (byte) 1), (byte) 1, (byte) 2)).isFalse();
	}

	@Test
	void testContainsOnceInvalid() {
		assertThatNullPointerException().isThrownBy(() -> ByteArrays.containsOnce(null, (byte) 1));
		assertThatNullPointerException().isThrownBy(() -> ByteArrays.containsOnce(ByteArrays.singleton((byte) 1), (byte[]) null));
		assertThatIllegalArgumentException().isThrownBy(() -> ByteArrays.containsOnce(ByteArrays.singleton((byte) 1)));
	}

	@Test
	void testContainsOnly() {
		assertThat(ByteArrays.containsOnly(ByteArrays.EMPTY, (byte) 1)).isFalse();
		assertThat(ByteArrays.containsOnly(ByteArrays.singleton((byte) 1), (byte) 1)).isTrue();
		assertThat(ByteArrays.containsOnly(ByteArrays.singleton((byte) 1), (byte) 2)).isFalse();
		assertThat(ByteArrays.containsOnly(ByteArrays.singleton((byte) 1), (byte) 1, (byte) 2)).isTrue();
		assertThat(ByteArrays.containsOnly(ByteArrays.of((byte) 1, (byte) 2), (byte) 1)).isFalse();
		assertThat(ByteArrays.containsOnly(ByteArrays.of((byte) 1, (byte) 2), (byte) 2)).isFalse();
		assertThat(ByteArrays.containsOnly(ByteArrays.of((byte) 1, (byte) 2), (byte) 1, (byte) 2)).isTrue();
	}

	@Test
	void testContainsOnlyInvalid() {
		assertThatNullPointerException().isThrownBy(() -> ByteArrays.containsOnly(null, (byte) 1));
		assertThatNullPointerException().isThrownBy(() -> ByteArrays.containsOnly(ByteArrays.singleton((byte) 1), (byte[]) null));
		assertThatIllegalArgumentException().isThrownBy(() -> ByteArrays.containsOnly(ByteArrays.singleton((byte) 1)));
	}

	@Test
	void testIndexOf() {
		assertThat(ByteArrays.indexOf(ByteArrays.EMPTY, (byte) 1)).isEqualTo(-1);
		final var array = ByteArrays.of((byte) 1, (byte) 2, (byte) 1);
		assertThat(ByteArrays.indexOf(array, (byte) 1)).isEqualTo(0);
		assertThat(ByteArrays.indexOf(array, (byte) 2)).isEqualTo(1);
		assertThat(ByteArrays.indexOf(array, (byte) 1, 1)).isEqualTo(2);
		assertThat(ByteArrays.indexOf(array, (byte) 2, 2)).isEqualTo(-1);
	}

	@Test
	void testIndexOfInvalid() {
		assertThatNullPointerException().isThrownBy(() -> ByteArrays.indexOf(null, (byte) 1));
		assertThatIllegalArgumentException().isThrownBy(() -> ByteArrays.indexOf(ByteArrays.singleton((byte) 1), (byte) 1, -1));
		assertThatIllegalArgumentException().isThrownBy(() -> ByteArrays.indexOf(ByteArrays.singleton((byte) 1), (byte) 1, 1));
	}

	@Test
	void testLastIndexOf() {
		assertThat(ByteArrays.lastIndexOf(ByteArrays.EMPTY, (byte) 1)).isEqualTo(-1);
		final var array = ByteArrays.of((byte) 1, (byte) 2, (byte) 1);
		assertThat(ByteArrays.lastIndexOf(array, (byte) 1)).isEqualTo(2);
		assertThat(ByteArrays.lastIndexOf(array, (byte) 2)).isEqualTo(1);
		assertThat(ByteArrays.lastIndexOf(array, (byte) 1, 1)).isEqualTo(2);
		assertThat(ByteArrays.lastIndexOf(array, (byte) 2, 2)).isEqualTo(-1);
	}

	@Test
	void testLastIndexOfInvalid() {
		assertThatNullPointerException().isThrownBy(() -> ByteArrays.lastIndexOf(null, (byte) 1));
		assertThatIllegalArgumentException().isThrownBy(() -> ByteArrays.lastIndexOf(ByteArrays.singleton((byte) 1), (byte) 1, -1));
		assertThatIllegalArgumentException().isThrownBy(() -> ByteArrays.lastIndexOf(ByteArrays.singleton((byte) 1), (byte) 1, 1));
	}

	@Test
	void testFrequency() {
		assertThat(ByteArrays.frequency(ByteArrays.EMPTY, (byte) 1)).isEqualTo(0);
		final var array = ByteArrays.of((byte) 1, (byte) 2, (byte) 1);
		assertThat(ByteArrays.frequency(array, (byte) 1)).isEqualTo(2);
		assertThat(ByteArrays.frequency(array, (byte) 2)).isEqualTo(1);
	}

	@Test
	void testFrequencyInvalid() {
		assertThatNullPointerException().isThrownBy(() -> ByteArrays.frequency(null, (byte) 1));
	}

	@Test
	void testShuffle() {
		{
			final var array = ByteArrays.singleton((byte) 1);
			ByteArrays.shuffle(array);
			assertThat(array).containsExactly((byte) 1);
		}
		{
			final var array = ByteArrays.of((byte) 1, (byte) 2, (byte) 1, (byte) 2);
			ByteArrays.shuffle(array);
			assertThat(array).containsExactlyInAnyOrder((byte) 1, (byte) 2, (byte) 1, (byte) 2);
		}
	}

	@Test
	void testShuffleInvalid() {
		assertThatNullPointerException().isThrownBy(() -> ByteArrays.shuffle(null));
	}

	@Test
	void testReverse() {
		{
			final var array = ByteArrays.singleton((byte) 1);
			ByteArrays.reverse(array);
			assertThat(array).containsExactly((byte) 1);
		}
		{
			// Even
			final var array = ByteArrays.of((byte) 1, (byte) 2, (byte) 1, (byte) 2);
			ByteArrays.reverse(array);
			assertThat(array).containsExactly((byte) 2, (byte) 1, (byte) 2, (byte) 1);
		}
		{
			// Odd
			final var array = ByteArrays.of((byte) 1, (byte) 2, (byte) 2);
			ByteArrays.reverse(array);
			assertThat(array).containsExactly((byte) 2, (byte) 2, (byte) 1);
		}
	}

	@Test
	void testReverseInvalid() {
		assertThatNullPointerException().isThrownBy(() -> ByteArrays.reverse(null));
	}

	@Test
	void testReorder() {
		{
			final var array = ByteArrays.singleton((byte) 1);
			ByteArrays.reorder(array, 0);
			assertThat(array).containsExactly((byte) 1);
		}
		{
			final var array = ByteArrays.of((byte) 1, (byte) 2, (byte) 1, (byte) 2);
			ByteArrays.reorder(array, 2, 0, 3, 1);
			assertThat(array).containsExactly((byte) 1, (byte) 1, (byte) 2, (byte) 2);
		}
	}

	@Test
	void testReorderInvalid() {
		assertThatNullPointerException().isThrownBy(() -> ByteArrays.reorder(null));
		assertThatNullPointerException().isThrownBy(() -> ByteArrays.reorder(ByteArrays.singleton((byte) 1), (int[]) null));
		assertThatIllegalArgumentException().isThrownBy(() -> ByteArrays.reorder(ByteArrays.of((byte) 1, (byte) 2), IntArrays.singleton(0)));
		assertThatIllegalArgumentException().isThrownBy(() -> ByteArrays.reorder(ByteArrays.of((byte) 1, (byte) 2), IntArrays.of(0, 0)));
		assertThatIllegalArgumentException().isThrownBy(() -> ByteArrays.reorder(ByteArrays.of((byte) 1, (byte) 2), IntArrays.of(-1, 1)));
		assertThatIllegalArgumentException().isThrownBy(() -> ByteArrays.reorder(ByteArrays.of((byte) 1, (byte) 2), IntArrays.of(2, 1)));
		assertThatIllegalArgumentException().isThrownBy(() -> ByteArrays.reorder(ByteArrays.of((byte) 1, (byte) 2), IntArrays.of(0, -1)));
		assertThatIllegalArgumentException().isThrownBy(() -> ByteArrays.reorder(ByteArrays.of((byte) 1, (byte) 2), IntArrays.of(0, 2)));
	}

	@Test
	void testSwap() {
		{
			final var array = ByteArrays.singleton((byte) 1);
			ByteArrays.swap(array, 0, 0);
			assertThat(array).containsExactly((byte) 1);
		}
		{
			final var array = ByteArrays.of((byte) 1, (byte) 2, (byte) 1, (byte) 2);
			ByteArrays.swap(array, 1, 2);
			assertThat(array).containsExactly((byte) 1, (byte) 1, (byte) 2, (byte) 2);
		}
	}

	@Test
	void testSwapInvalid() {
		assertThatNullPointerException().isThrownBy(() -> ByteArrays.swap(null, 0, 0));
		assertThatIllegalArgumentException().isThrownBy(() -> ByteArrays.swap(ByteArrays.of((byte) 1, (byte) 2), -1, 1));
		assertThatIllegalArgumentException().isThrownBy(() -> ByteArrays.swap(ByteArrays.of((byte) 1, (byte) 2), 2, 1));
		assertThatIllegalArgumentException().isThrownBy(() -> ByteArrays.swap(ByteArrays.of((byte) 1, (byte) 2), 0, -1));
		assertThatIllegalArgumentException().isThrownBy(() -> ByteArrays.swap(ByteArrays.of((byte) 1, (byte) 2), 0, 2));
	}

	@Test
	void testAdd() {
		assertThat(ByteArrays.add(ByteArrays.EMPTY, (byte) 0)).containsExactly((byte) 0);
		assertThat(ByteArrays.add(ByteArrays.of((byte) 1, (byte) 2, (byte) 3), 0, (byte) 0)).containsExactly((byte) 0, (byte) 1, (byte) 2, (byte) 3);
		assertThat(ByteArrays.add(ByteArrays.of((byte) 1, (byte) 2, (byte) 3), 1, (byte) 0)).containsExactly((byte) 1, (byte) 0, (byte) 2, (byte) 3);
		assertThat(ByteArrays.add(ByteArrays.of((byte) 1, (byte) 2, (byte) 3), 2, (byte) 0)).containsExactly((byte) 1, (byte) 2, (byte) 0, (byte) 3);
		assertThat(ByteArrays.add(ByteArrays.of((byte) 1, (byte) 2, (byte) 3), 3, (byte) 0)).containsExactly((byte) 1, (byte) 2, (byte) 3, (byte) 0);
		assertThat(ByteArrays.add(ByteArrays.of((byte) 1, (byte) 2, (byte) 3), (byte) 0)).containsExactly((byte) 1, (byte) 2, (byte) 3, (byte) 0);
	}

	@Test
	void testAddInvalid() {
		assertThatNullPointerException().isThrownBy(() -> ByteArrays.add(null, (byte) 0));
		assertThatNullPointerException().isThrownBy(() -> ByteArrays.add(null, 0, (byte) 0));
		assertThatIllegalArgumentException().isThrownBy(() -> ByteArrays.add(ByteArrays.singleton((byte) 1), -1, (byte) 0));
		assertThatIllegalArgumentException().isThrownBy(() -> ByteArrays.add(ByteArrays.singleton((byte) 1), 2, (byte) 0));
	}

	@Test
	void testRemove() {
		assertThat(ByteArrays.remove(ByteArrays.singleton((byte) 1), 0)).isEmpty();
		assertThat(ByteArrays.remove(ByteArrays.of((byte) 1, (byte) 2, (byte) 3), 0)).containsExactly((byte) 2, (byte) 3);
		assertThat(ByteArrays.remove(ByteArrays.of((byte) 1, (byte) 2, (byte) 3), 1)).containsExactly((byte) 1, (byte) 3);
		assertThat(ByteArrays.remove(ByteArrays.of((byte) 1, (byte) 2, (byte) 3), 2)).containsExactly((byte) 1, (byte) 2);
	}

	@Test
	void testRemoveInvalid() {
		assertThatNullPointerException().isThrownBy(() -> ByteArrays.remove(null, 0));
		assertThatIllegalArgumentException().isThrownBy(() -> ByteArrays.remove(ByteArrays.EMPTY, 0));
		assertThatIllegalArgumentException().isThrownBy(() -> ByteArrays.remove(ByteArrays.singleton((byte) 1), -1));
		assertThatIllegalArgumentException().isThrownBy(() -> ByteArrays.remove(ByteArrays.singleton((byte) 1), 1));
	}

	@Test
	void testConcat() {
		assertThat(ByteArrays.concat()).isEmpty();
		assertThat(ByteArrays.concat(ByteArrays.singleton((byte) 1))).containsExactly((byte) 1);
		assertThat(ByteArrays.concat(ByteArrays.singleton((byte) 1), ByteArrays.singleton((byte) 2))).containsExactly((byte) 1, (byte) 2);
	}

	@Test
	void testConcatInvalid() {
		assertThatNullPointerException().isThrownBy(() -> ByteArrays.concat((byte[][]) null));
		assertThatNullPointerException().isThrownBy(() -> ByteArrays.concat((List<byte[]>) null));
		assertThatNullPointerException().isThrownBy(() -> ByteArrays.concat((byte[]) null));
	}

	@Test
	void testJoin() {
		assertThat(ByteArrays.join(ByteArrays.EMPTY, ByteArrays.singleton((byte) 1), ByteArrays.singleton((byte) 2))).containsExactly((byte) 1, (byte) 2);
		assertThat(ByteArrays.join(ByteArrays.singleton((byte) 0))).isEmpty();
		assertThat(ByteArrays.join(ByteArrays.singleton((byte) 0), ByteArrays.singleton((byte) 1))).containsExactly((byte) 1);
		assertThat(ByteArrays.join(ByteArrays.singleton((byte) 0), ByteArrays.singleton((byte) 1), ByteArrays.singleton((byte) 2))).containsExactly((byte) 1, (byte) 0, (byte) 2);
	}

	@Test
	void testJoinInvalid() {
		assertThatNullPointerException().isThrownBy(() -> ByteArrays.join(null, ByteArrays.singleton((byte) 1)));
		assertThatNullPointerException().isThrownBy(() -> ByteArrays.join(ByteArrays.singleton((byte) 0), (byte[][]) null));
		assertThatNullPointerException().isThrownBy(() -> ByteArrays.join(ByteArrays.singleton((byte) 0), (List<byte[]>) null));
		assertThatNullPointerException().isThrownBy(() -> ByteArrays.join(ByteArrays.singleton((byte) 0), (byte[]) null));
	}

	@Test
	void testSingleton() {
		assertThat(ByteArrays.singleton((byte) 1)).containsExactly((byte) 1);
	}

	@Test
	void testOf() {
		assertThat(ByteArrays.of()).isEmpty();
		assertThat(ByteArrays.of((byte) 1, (byte) 2)).containsExactly((byte) 1, (byte) 2);
	}

	@Test
	void testOfInvalid() {
		assertThatNullPointerException().isThrownBy(() -> ByteArrays.of((byte[]) null));
	}

	@Test
	void testOfBoxedToBoxed() {
		assertThat(ByteArrays.of(ByteArrays.toBoxed(ByteArrays.EMPTY))).isEmpty();
		assertThat(ByteArrays.of(ByteArrays.toBoxed(ByteArrays.of((byte) 1, (byte) 2)))).containsExactly((byte) 1, (byte) 2);
		assertThat(ByteArrays.toBoxed(ByteArrays.singleton((byte) 1))).isInstanceOf(Byte[].class);
		assertThat(ByteArrays.of(ByteArrays.toBoxed(ByteArrays.singleton((byte) 1)))).isInstanceOf(byte[].class);
	}

	@Test
	void testOfBoxedInvalid() {
		assertThatNullPointerException().isThrownBy(() -> ByteArrays.of((Byte[]) null));
	}

	@Test
	void testToBoxedInvalid() {
		assertThatNullPointerException().isThrownBy(() -> ByteArrays.toBoxed(null));
	}

	@Test
	void testOfBooleanAndToBoolean() {
		assertThat(ByteArrays.toBoolean(ByteArrays.ofBoolean(false))).isFalse();
		assertThat(ByteArrays.toBoolean(ByteArrays.ofBoolean(true))).isTrue();
	}

	@Test
	void testToBooleanInvalid() {
		assertThatNullPointerException().isThrownBy(() -> ByteArrays.toBoolean(null));
		assertThatIllegalArgumentException().isThrownBy(() -> ByteArrays.toBoolean(ByteArrays.EMPTY));
	}

	@Test
	void testOfShortAndToShort() {
		assertThat(ByteArrays.toShort(ByteArrays.ofShort((short) 0))).isEqualTo((short) 0);
		assertThat(ByteArrays.toShort(ByteArrays.ofShort((short) 0, ByteOrder.BIG_ENDIAN), ByteOrder.BIG_ENDIAN)).isEqualTo((short) 0);
		assertThat(ByteArrays.toShort(ByteArrays.ofShort((short) 0, ByteOrder.LITTLE_ENDIAN), ByteOrder.LITTLE_ENDIAN)).isEqualTo((short) 0);

		assertThat(ByteArrays.toShort(ByteArrays.ofShort((short) -5, ByteOrder.BIG_ENDIAN), ByteOrder.BIG_ENDIAN)).isEqualTo((short) -5);
		assertThat(ByteArrays.toShort(ByteArrays.ofShort((short) -5, ByteOrder.LITTLE_ENDIAN), ByteOrder.LITTLE_ENDIAN)).isEqualTo((short) -5);
		assertThat(ByteArrays.toShort(ByteArrays.ofShort((short) -5, ByteOrder.BIG_ENDIAN), ByteOrder.LITTLE_ENDIAN)).isNotEqualTo((short) -5);
		assertThat(ByteArrays.toShort(ByteArrays.ofShort((short) -5, ByteOrder.LITTLE_ENDIAN), ByteOrder.BIG_ENDIAN)).isNotEqualTo((short) -5);

		assertThat(ByteArrays.toShort(ByteArrays.ofShort((short) 5, ByteOrder.BIG_ENDIAN), ByteOrder.BIG_ENDIAN)).isEqualTo((short) 5);
		assertThat(ByteArrays.toShort(ByteArrays.ofShort((short) 5, ByteOrder.LITTLE_ENDIAN), ByteOrder.LITTLE_ENDIAN)).isEqualTo((short) 5);
		assertThat(ByteArrays.toShort(ByteArrays.ofShort((short) 5, ByteOrder.BIG_ENDIAN), ByteOrder.LITTLE_ENDIAN)).isNotEqualTo((short) 5);
		assertThat(ByteArrays.toShort(ByteArrays.ofShort((short) 5, ByteOrder.LITTLE_ENDIAN), ByteOrder.BIG_ENDIAN)).isNotEqualTo((short) 5);

		assertThat(ByteArrays.toShort(ByteArrays.ofShort(Short.MIN_VALUE, ByteOrder.BIG_ENDIAN), ByteOrder.BIG_ENDIAN)).isEqualTo(Short.MIN_VALUE);
		assertThat(ByteArrays.toShort(ByteArrays.ofShort(Short.MIN_VALUE, ByteOrder.LITTLE_ENDIAN), ByteOrder.LITTLE_ENDIAN)).isEqualTo(Short.MIN_VALUE);
		assertThat(ByteArrays.toShort(ByteArrays.ofShort(Short.MIN_VALUE, ByteOrder.BIG_ENDIAN), ByteOrder.LITTLE_ENDIAN)).isNotEqualTo(Short.MIN_VALUE);
		assertThat(ByteArrays.toShort(ByteArrays.ofShort(Short.MIN_VALUE, ByteOrder.LITTLE_ENDIAN), ByteOrder.BIG_ENDIAN)).isNotEqualTo(Short.MIN_VALUE);

		assertThat(ByteArrays.toShort(ByteArrays.ofShort(Short.MAX_VALUE, ByteOrder.BIG_ENDIAN), ByteOrder.BIG_ENDIAN)).isEqualTo(Short.MAX_VALUE);
		assertThat(ByteArrays.toShort(ByteArrays.ofShort(Short.MAX_VALUE, ByteOrder.LITTLE_ENDIAN), ByteOrder.LITTLE_ENDIAN)).isEqualTo(Short.MAX_VALUE);
		assertThat(ByteArrays.toShort(ByteArrays.ofShort(Short.MAX_VALUE, ByteOrder.BIG_ENDIAN), ByteOrder.LITTLE_ENDIAN)).isNotEqualTo(Short.MAX_VALUE);
		assertThat(ByteArrays.toShort(ByteArrays.ofShort(Short.MAX_VALUE, ByteOrder.LITTLE_ENDIAN), ByteOrder.BIG_ENDIAN)).isNotEqualTo(Short.MAX_VALUE);
	}

	@Test
	void testOfShortInvalid() {
		assertThatNullPointerException().isThrownBy(() -> ByteArrays.ofShort((short) 0, null));
	}

	@Test
	void testToShortInvalid() {
		assertThatNullPointerException().isThrownBy(() -> ByteArrays.toShort(null));
		assertThatIllegalArgumentException().isThrownBy(() -> ByteArrays.toShort(ByteArrays.EMPTY));
		assertThatNullPointerException().isThrownBy(() -> ByteArrays.toShort(ByteArrays.ofShort((short) 0), null));
	}

	@Test
	void testOfCharAndToChar() {
		assertThat(ByteArrays.toChar(ByteArrays.ofChar(Character.MIN_VALUE))).isEqualTo(Character.MIN_VALUE);
		assertThat(ByteArrays.toChar(ByteArrays.ofChar(Character.MIN_VALUE, ByteOrder.BIG_ENDIAN), ByteOrder.BIG_ENDIAN)).isEqualTo(Character.MIN_VALUE);
		assertThat(ByteArrays.toChar(ByteArrays.ofChar(Character.MIN_VALUE, ByteOrder.LITTLE_ENDIAN), ByteOrder.LITTLE_ENDIAN)).isEqualTo(Character.MIN_VALUE);

		assertThat(ByteArrays.toChar(ByteArrays.ofChar((char) 5, ByteOrder.BIG_ENDIAN), ByteOrder.BIG_ENDIAN)).isEqualTo((char) 5);
		assertThat(ByteArrays.toChar(ByteArrays.ofChar((char) 5, ByteOrder.LITTLE_ENDIAN), ByteOrder.LITTLE_ENDIAN)).isEqualTo((char) 5);
		assertThat(ByteArrays.toChar(ByteArrays.ofChar((char) 5, ByteOrder.BIG_ENDIAN), ByteOrder.LITTLE_ENDIAN)).isNotEqualTo((char) 5);
		assertThat(ByteArrays.toChar(ByteArrays.ofChar((char) 5, ByteOrder.LITTLE_ENDIAN), ByteOrder.BIG_ENDIAN)).isNotEqualTo((char) 5);

		assertThat(ByteArrays.toChar(ByteArrays.ofChar(Character.MAX_VALUE, ByteOrder.BIG_ENDIAN), ByteOrder.BIG_ENDIAN)).isEqualTo(Character.MAX_VALUE);
		assertThat(ByteArrays.toChar(ByteArrays.ofChar(Character.MAX_VALUE, ByteOrder.LITTLE_ENDIAN), ByteOrder.LITTLE_ENDIAN)).isEqualTo(Character.MAX_VALUE);
	}

	@Test
	void testOfCharInvalid() {
		assertThatNullPointerException().isThrownBy(() -> ByteArrays.ofChar((char) 0, null));
	}

	@Test
	void testToCharInvalid() {
		assertThatNullPointerException().isThrownBy(() -> ByteArrays.toChar(null));
		assertThatIllegalArgumentException().isThrownBy(() -> ByteArrays.toChar(ByteArrays.EMPTY));
		assertThatNullPointerException().isThrownBy(() -> ByteArrays.toChar(ByteArrays.ofChar((char) 0), null));
	}

	@Test
	void testOfIntAndToInt() {
		assertThat(ByteArrays.toInt(ByteArrays.ofInt(0))).isEqualTo(0);
		assertThat(ByteArrays.toInt(ByteArrays.ofInt(0, ByteOrder.BIG_ENDIAN), ByteOrder.BIG_ENDIAN)).isEqualTo(0);
		assertThat(ByteArrays.toInt(ByteArrays.ofInt(0, ByteOrder.LITTLE_ENDIAN), ByteOrder.LITTLE_ENDIAN)).isEqualTo(0);

		assertThat(ByteArrays.toInt(ByteArrays.ofInt(-5, ByteOrder.BIG_ENDIAN), ByteOrder.BIG_ENDIAN)).isEqualTo(-5);
		assertThat(ByteArrays.toInt(ByteArrays.ofInt(-5, ByteOrder.LITTLE_ENDIAN), ByteOrder.LITTLE_ENDIAN)).isEqualTo(-5);
		assertThat(ByteArrays.toInt(ByteArrays.ofInt(-5, ByteOrder.BIG_ENDIAN), ByteOrder.LITTLE_ENDIAN)).isNotEqualTo(-5);
		assertThat(ByteArrays.toInt(ByteArrays.ofInt(-5, ByteOrder.LITTLE_ENDIAN), ByteOrder.BIG_ENDIAN)).isNotEqualTo(-5);

		assertThat(ByteArrays.toInt(ByteArrays.ofInt(5, ByteOrder.BIG_ENDIAN), ByteOrder.BIG_ENDIAN)).isEqualTo(5);
		assertThat(ByteArrays.toInt(ByteArrays.ofInt(5, ByteOrder.LITTLE_ENDIAN), ByteOrder.LITTLE_ENDIAN)).isEqualTo(5);
		assertThat(ByteArrays.toInt(ByteArrays.ofInt(5, ByteOrder.BIG_ENDIAN), ByteOrder.LITTLE_ENDIAN)).isNotEqualTo(5);
		assertThat(ByteArrays.toInt(ByteArrays.ofInt(5, ByteOrder.LITTLE_ENDIAN), ByteOrder.BIG_ENDIAN)).isNotEqualTo(5);

		assertThat(ByteArrays.toInt(ByteArrays.ofInt(Integer.MIN_VALUE, ByteOrder.BIG_ENDIAN), ByteOrder.BIG_ENDIAN)).isEqualTo(Integer.MIN_VALUE);
		assertThat(ByteArrays.toInt(ByteArrays.ofInt(Integer.MIN_VALUE, ByteOrder.LITTLE_ENDIAN), ByteOrder.LITTLE_ENDIAN)).isEqualTo(Integer.MIN_VALUE);
		assertThat(ByteArrays.toInt(ByteArrays.ofInt(Integer.MIN_VALUE, ByteOrder.BIG_ENDIAN), ByteOrder.LITTLE_ENDIAN)).isNotEqualTo(Integer.MIN_VALUE);
		assertThat(ByteArrays.toInt(ByteArrays.ofInt(Integer.MIN_VALUE, ByteOrder.LITTLE_ENDIAN), ByteOrder.BIG_ENDIAN)).isNotEqualTo(Integer.MIN_VALUE);

		assertThat(ByteArrays.toInt(ByteArrays.ofInt(Integer.MAX_VALUE, ByteOrder.BIG_ENDIAN), ByteOrder.BIG_ENDIAN)).isEqualTo(Integer.MAX_VALUE);
		assertThat(ByteArrays.toInt(ByteArrays.ofInt(Integer.MAX_VALUE, ByteOrder.LITTLE_ENDIAN), ByteOrder.LITTLE_ENDIAN)).isEqualTo(Integer.MAX_VALUE);
		assertThat(ByteArrays.toInt(ByteArrays.ofInt(Integer.MAX_VALUE, ByteOrder.BIG_ENDIAN), ByteOrder.LITTLE_ENDIAN)).isNotEqualTo(Integer.MAX_VALUE);
		assertThat(ByteArrays.toInt(ByteArrays.ofInt(Integer.MAX_VALUE, ByteOrder.LITTLE_ENDIAN), ByteOrder.BIG_ENDIAN)).isNotEqualTo(Integer.MAX_VALUE);
	}

	@Test
	void testOfIntInvalid() {
		assertThatNullPointerException().isThrownBy(() -> ByteArrays.ofInt(0, null));
	}

	@Test
	void testToIntInvalid() {
		assertThatNullPointerException().isThrownBy(() -> ByteArrays.toInt(null));
		assertThatIllegalArgumentException().isThrownBy(() -> ByteArrays.toInt(ByteArrays.EMPTY));
		assertThatNullPointerException().isThrownBy(() -> ByteArrays.toInt(ByteArrays.ofInt(0), null));
	}

	@Test
	void testOfLongAndToLong() {
		assertThat(ByteArrays.toLong(ByteArrays.ofLong(0L))).isEqualTo(0L);
		assertThat(ByteArrays.toLong(ByteArrays.ofLong(0L, ByteOrder.BIG_ENDIAN), ByteOrder.BIG_ENDIAN)).isEqualTo(0L);
		assertThat(ByteArrays.toLong(ByteArrays.ofLong(0L, ByteOrder.LITTLE_ENDIAN), ByteOrder.LITTLE_ENDIAN)).isEqualTo(0L);

		assertThat(ByteArrays.toLong(ByteArrays.ofLong(-5L, ByteOrder.BIG_ENDIAN), ByteOrder.BIG_ENDIAN)).isEqualTo(-5L);
		assertThat(ByteArrays.toLong(ByteArrays.ofLong(-5L, ByteOrder.LITTLE_ENDIAN), ByteOrder.LITTLE_ENDIAN)).isEqualTo(-5L);
		assertThat(ByteArrays.toLong(ByteArrays.ofLong(-5L, ByteOrder.BIG_ENDIAN), ByteOrder.LITTLE_ENDIAN)).isNotEqualTo(-5L);
		assertThat(ByteArrays.toLong(ByteArrays.ofLong(-5L, ByteOrder.LITTLE_ENDIAN), ByteOrder.BIG_ENDIAN)).isNotEqualTo(-5L);

		assertThat(ByteArrays.toLong(ByteArrays.ofLong(5L, ByteOrder.BIG_ENDIAN), ByteOrder.BIG_ENDIAN)).isEqualTo(5L);
		assertThat(ByteArrays.toLong(ByteArrays.ofLong(5L, ByteOrder.LITTLE_ENDIAN), ByteOrder.LITTLE_ENDIAN)).isEqualTo(5L);
		assertThat(ByteArrays.toLong(ByteArrays.ofLong(5L, ByteOrder.BIG_ENDIAN), ByteOrder.LITTLE_ENDIAN)).isNotEqualTo(5L);
		assertThat(ByteArrays.toLong(ByteArrays.ofLong(5L, ByteOrder.LITTLE_ENDIAN), ByteOrder.BIG_ENDIAN)).isNotEqualTo(5L);

		assertThat(ByteArrays.toLong(ByteArrays.ofLong(Long.MIN_VALUE, ByteOrder.BIG_ENDIAN), ByteOrder.BIG_ENDIAN)).isEqualTo(Long.MIN_VALUE);
		assertThat(ByteArrays.toLong(ByteArrays.ofLong(Long.MIN_VALUE, ByteOrder.LITTLE_ENDIAN), ByteOrder.LITTLE_ENDIAN)).isEqualTo(Long.MIN_VALUE);
		assertThat(ByteArrays.toLong(ByteArrays.ofLong(Long.MIN_VALUE, ByteOrder.BIG_ENDIAN), ByteOrder.LITTLE_ENDIAN)).isNotEqualTo(Long.MIN_VALUE);
		assertThat(ByteArrays.toLong(ByteArrays.ofLong(Long.MIN_VALUE, ByteOrder.LITTLE_ENDIAN), ByteOrder.BIG_ENDIAN)).isNotEqualTo(Long.MIN_VALUE);

		assertThat(ByteArrays.toLong(ByteArrays.ofLong(Long.MAX_VALUE, ByteOrder.BIG_ENDIAN), ByteOrder.BIG_ENDIAN)).isEqualTo(Long.MAX_VALUE);
		assertThat(ByteArrays.toLong(ByteArrays.ofLong(Long.MAX_VALUE, ByteOrder.LITTLE_ENDIAN), ByteOrder.LITTLE_ENDIAN)).isEqualTo(Long.MAX_VALUE);
		assertThat(ByteArrays.toLong(ByteArrays.ofLong(Long.MAX_VALUE, ByteOrder.BIG_ENDIAN), ByteOrder.LITTLE_ENDIAN)).isNotEqualTo(Long.MAX_VALUE);
		assertThat(ByteArrays.toLong(ByteArrays.ofLong(Long.MAX_VALUE, ByteOrder.LITTLE_ENDIAN), ByteOrder.BIG_ENDIAN)).isNotEqualTo(Long.MAX_VALUE);
	}

	@Test
	void testOfLongInvalid() {
		assertThatNullPointerException().isThrownBy(() -> ByteArrays.ofLong(0L, null));
	}

	@Test
	void testToLongInvalid() {
		assertThatNullPointerException().isThrownBy(() -> ByteArrays.toLong(null));
		assertThatIllegalArgumentException().isThrownBy(() -> ByteArrays.toLong(ByteArrays.EMPTY));
		assertThatNullPointerException().isThrownBy(() -> ByteArrays.toLong(ByteArrays.ofLong(0L), null));
	}

	@Test
	void testOfFloatAndToFloat() {
		assertThat(ByteArrays.toFloat(ByteArrays.ofFloat(0.0f))).isEqualTo(0.0f);
		assertThat(ByteArrays.toFloat(ByteArrays.ofFloat(0.0f, ByteOrder.BIG_ENDIAN), ByteOrder.BIG_ENDIAN)).isEqualTo(0.0f);
		assertThat(ByteArrays.toFloat(ByteArrays.ofFloat(0.0f, ByteOrder.LITTLE_ENDIAN), ByteOrder.LITTLE_ENDIAN)).isEqualTo(0.0f);

		assertThat(ByteArrays.toFloat(ByteArrays.ofFloat(-5.5f, ByteOrder.BIG_ENDIAN), ByteOrder.BIG_ENDIAN)).isEqualTo(-5.5f);
		assertThat(ByteArrays.toFloat(ByteArrays.ofFloat(-5.5f, ByteOrder.LITTLE_ENDIAN), ByteOrder.LITTLE_ENDIAN)).isEqualTo(-5.5f);
		assertThat(ByteArrays.toFloat(ByteArrays.ofFloat(-5.5f, ByteOrder.BIG_ENDIAN), ByteOrder.LITTLE_ENDIAN)).isNotEqualTo(-5.5f);
		assertThat(ByteArrays.toFloat(ByteArrays.ofFloat(-5.5f, ByteOrder.LITTLE_ENDIAN), ByteOrder.BIG_ENDIAN)).isNotEqualTo(-5.5f);

		assertThat(ByteArrays.toFloat(ByteArrays.ofFloat(5.5f, ByteOrder.BIG_ENDIAN), ByteOrder.BIG_ENDIAN)).isEqualTo(5.5f);
		assertThat(ByteArrays.toFloat(ByteArrays.ofFloat(5.5f, ByteOrder.LITTLE_ENDIAN), ByteOrder.LITTLE_ENDIAN)).isEqualTo(5.5f);
		assertThat(ByteArrays.toFloat(ByteArrays.ofFloat(5.5f, ByteOrder.BIG_ENDIAN), ByteOrder.LITTLE_ENDIAN)).isNotEqualTo(5.5f);
		assertThat(ByteArrays.toFloat(ByteArrays.ofFloat(5.5f, ByteOrder.LITTLE_ENDIAN), ByteOrder.BIG_ENDIAN)).isNotEqualTo(5.5f);

		assertThat(ByteArrays.toFloat(ByteArrays.ofFloat(Float.MIN_VALUE, ByteOrder.BIG_ENDIAN), ByteOrder.BIG_ENDIAN)).isEqualTo(Float.MIN_VALUE);
		assertThat(ByteArrays.toFloat(ByteArrays.ofFloat(Float.MIN_VALUE, ByteOrder.LITTLE_ENDIAN), ByteOrder.LITTLE_ENDIAN)).isEqualTo(Float.MIN_VALUE);
		assertThat(ByteArrays.toFloat(ByteArrays.ofFloat(Float.MIN_VALUE, ByteOrder.BIG_ENDIAN), ByteOrder.LITTLE_ENDIAN)).isNotEqualTo(Float.MIN_VALUE);
		assertThat(ByteArrays.toFloat(ByteArrays.ofFloat(Float.MIN_VALUE, ByteOrder.LITTLE_ENDIAN), ByteOrder.BIG_ENDIAN)).isNotEqualTo(Float.MIN_VALUE);

		assertThat(ByteArrays.toFloat(ByteArrays.ofFloat(Float.NEGATIVE_INFINITY, ByteOrder.BIG_ENDIAN), ByteOrder.BIG_ENDIAN)).isEqualTo(Float.NEGATIVE_INFINITY);
		assertThat(ByteArrays.toFloat(ByteArrays.ofFloat(Float.NEGATIVE_INFINITY, ByteOrder.LITTLE_ENDIAN), ByteOrder.LITTLE_ENDIAN)).isEqualTo(Float.NEGATIVE_INFINITY);
		assertThat(ByteArrays.toFloat(ByteArrays.ofFloat(Float.NEGATIVE_INFINITY, ByteOrder.BIG_ENDIAN), ByteOrder.LITTLE_ENDIAN)).isNotEqualTo(Float.NEGATIVE_INFINITY);
		assertThat(ByteArrays.toFloat(ByteArrays.ofFloat(Float.NEGATIVE_INFINITY, ByteOrder.LITTLE_ENDIAN), ByteOrder.BIG_ENDIAN)).isNotEqualTo(Float.NEGATIVE_INFINITY);

		assertThat(ByteArrays.toFloat(ByteArrays.ofFloat(Float.MIN_NORMAL, ByteOrder.BIG_ENDIAN), ByteOrder.BIG_ENDIAN)).isEqualTo(Float.MIN_NORMAL);
		assertThat(ByteArrays.toFloat(ByteArrays.ofFloat(Float.MIN_NORMAL, ByteOrder.LITTLE_ENDIAN), ByteOrder.LITTLE_ENDIAN)).isEqualTo(Float.MIN_NORMAL);
		assertThat(ByteArrays.toFloat(ByteArrays.ofFloat(Float.MIN_NORMAL, ByteOrder.BIG_ENDIAN), ByteOrder.LITTLE_ENDIAN)).isNotEqualTo(Float.MIN_NORMAL);
		assertThat(ByteArrays.toFloat(ByteArrays.ofFloat(Float.MIN_NORMAL, ByteOrder.LITTLE_ENDIAN), ByteOrder.BIG_ENDIAN)).isNotEqualTo(Float.MIN_NORMAL);

		assertThat(ByteArrays.toFloat(ByteArrays.ofFloat(Float.POSITIVE_INFINITY, ByteOrder.BIG_ENDIAN), ByteOrder.BIG_ENDIAN)).isEqualTo(Float.POSITIVE_INFINITY);
		assertThat(ByteArrays.toFloat(ByteArrays.ofFloat(Float.POSITIVE_INFINITY, ByteOrder.LITTLE_ENDIAN), ByteOrder.LITTLE_ENDIAN)).isEqualTo(Float.POSITIVE_INFINITY);
		assertThat(ByteArrays.toFloat(ByteArrays.ofFloat(Float.POSITIVE_INFINITY, ByteOrder.BIG_ENDIAN), ByteOrder.LITTLE_ENDIAN)).isNotEqualTo(Float.POSITIVE_INFINITY);
		assertThat(ByteArrays.toFloat(ByteArrays.ofFloat(Float.POSITIVE_INFINITY, ByteOrder.LITTLE_ENDIAN), ByteOrder.BIG_ENDIAN)).isNotEqualTo(Float.POSITIVE_INFINITY);

		assertThat(ByteArrays.toFloat(ByteArrays.ofFloat(Float.MAX_VALUE, ByteOrder.BIG_ENDIAN), ByteOrder.BIG_ENDIAN)).isEqualTo(Float.MAX_VALUE);
		assertThat(ByteArrays.toFloat(ByteArrays.ofFloat(Float.MAX_VALUE, ByteOrder.LITTLE_ENDIAN), ByteOrder.LITTLE_ENDIAN)).isEqualTo(Float.MAX_VALUE);
		assertThat(ByteArrays.toFloat(ByteArrays.ofFloat(Float.MAX_VALUE, ByteOrder.BIG_ENDIAN), ByteOrder.LITTLE_ENDIAN)).isNotEqualTo(Float.MAX_VALUE);
		assertThat(ByteArrays.toFloat(ByteArrays.ofFloat(Float.MAX_VALUE, ByteOrder.LITTLE_ENDIAN), ByteOrder.BIG_ENDIAN)).isNotEqualTo(Float.MAX_VALUE);

		assertThat(ByteArrays.toFloat(ByteArrays.ofFloat(Float.NaN, ByteOrder.BIG_ENDIAN), ByteOrder.BIG_ENDIAN)).isEqualTo(Float.NaN);
		assertThat(ByteArrays.toFloat(ByteArrays.ofFloat(Float.NaN, ByteOrder.LITTLE_ENDIAN), ByteOrder.LITTLE_ENDIAN)).isEqualTo(Float.NaN);
		assertThat(ByteArrays.toFloat(ByteArrays.ofFloat(Float.NaN, ByteOrder.BIG_ENDIAN), ByteOrder.LITTLE_ENDIAN)).isNotEqualTo(Float.NaN);
		assertThat(ByteArrays.toFloat(ByteArrays.ofFloat(Float.NaN, ByteOrder.LITTLE_ENDIAN), ByteOrder.BIG_ENDIAN)).isNotEqualTo(Float.NaN);
	}

	@Test
	void testOfFloatInvalid() {
		assertThatNullPointerException().isThrownBy(() -> ByteArrays.ofFloat(0.0f, null));
	}

	@Test
	void testToFloatInvalid() {
		assertThatNullPointerException().isThrownBy(() -> ByteArrays.toFloat(null));
		assertThatIllegalArgumentException().isThrownBy(() -> ByteArrays.toFloat(ByteArrays.EMPTY));
		assertThatNullPointerException().isThrownBy(() -> ByteArrays.toFloat(ByteArrays.ofFloat(0.0f), null));
	}

	@Test
	void testOfDoubleAndToDouble() {
		assertThat(ByteArrays.toDouble(ByteArrays.ofDouble(0.0d))).isEqualTo(0.0d);
		assertThat(ByteArrays.toDouble(ByteArrays.ofDouble(0.0d, ByteOrder.BIG_ENDIAN), ByteOrder.BIG_ENDIAN)).isEqualTo(0.0d);
		assertThat(ByteArrays.toDouble(ByteArrays.ofDouble(0.0d, ByteOrder.LITTLE_ENDIAN), ByteOrder.LITTLE_ENDIAN)).isEqualTo(0.0d);

		assertThat(ByteArrays.toDouble(ByteArrays.ofDouble(-5.5d, ByteOrder.BIG_ENDIAN), ByteOrder.BIG_ENDIAN)).isEqualTo(-5.5d);
		assertThat(ByteArrays.toDouble(ByteArrays.ofDouble(-5.5d, ByteOrder.LITTLE_ENDIAN), ByteOrder.LITTLE_ENDIAN)).isEqualTo(-5.5d);
		assertThat(ByteArrays.toDouble(ByteArrays.ofDouble(-5.5d, ByteOrder.BIG_ENDIAN), ByteOrder.LITTLE_ENDIAN)).isNotEqualTo(-5.5d);
		assertThat(ByteArrays.toDouble(ByteArrays.ofDouble(-5.5d, ByteOrder.LITTLE_ENDIAN), ByteOrder.BIG_ENDIAN)).isNotEqualTo(-5.5d);

		assertThat(ByteArrays.toDouble(ByteArrays.ofDouble(5.5d, ByteOrder.BIG_ENDIAN), ByteOrder.BIG_ENDIAN)).isEqualTo(5.5d);
		assertThat(ByteArrays.toDouble(ByteArrays.ofDouble(5.5d, ByteOrder.LITTLE_ENDIAN), ByteOrder.LITTLE_ENDIAN)).isEqualTo(5.5d);
		assertThat(ByteArrays.toDouble(ByteArrays.ofDouble(5.5d, ByteOrder.BIG_ENDIAN), ByteOrder.LITTLE_ENDIAN)).isNotEqualTo(5.5d);
		assertThat(ByteArrays.toDouble(ByteArrays.ofDouble(5.5d, ByteOrder.LITTLE_ENDIAN), ByteOrder.BIG_ENDIAN)).isNotEqualTo(5.5d);

		assertThat(ByteArrays.toDouble(ByteArrays.ofDouble(Double.MIN_VALUE, ByteOrder.BIG_ENDIAN), ByteOrder.BIG_ENDIAN)).isEqualTo(Double.MIN_VALUE);
		assertThat(ByteArrays.toDouble(ByteArrays.ofDouble(Double.MIN_VALUE, ByteOrder.LITTLE_ENDIAN), ByteOrder.LITTLE_ENDIAN)).isEqualTo(Double.MIN_VALUE);
		assertThat(ByteArrays.toDouble(ByteArrays.ofDouble(Double.MIN_VALUE, ByteOrder.BIG_ENDIAN), ByteOrder.LITTLE_ENDIAN)).isNotEqualTo(Double.MIN_VALUE);
		assertThat(ByteArrays.toDouble(ByteArrays.ofDouble(Double.MIN_VALUE, ByteOrder.LITTLE_ENDIAN), ByteOrder.BIG_ENDIAN)).isNotEqualTo(Double.MIN_VALUE);

		assertThat(ByteArrays.toDouble(ByteArrays.ofDouble(Double.NEGATIVE_INFINITY, ByteOrder.BIG_ENDIAN), ByteOrder.BIG_ENDIAN)).isEqualTo(Double.NEGATIVE_INFINITY);
		assertThat(ByteArrays.toDouble(ByteArrays.ofDouble(Double.NEGATIVE_INFINITY, ByteOrder.LITTLE_ENDIAN), ByteOrder.LITTLE_ENDIAN)).isEqualTo(Double.NEGATIVE_INFINITY);
		assertThat(ByteArrays.toDouble(ByteArrays.ofDouble(Double.NEGATIVE_INFINITY, ByteOrder.BIG_ENDIAN), ByteOrder.LITTLE_ENDIAN)).isNotEqualTo(Double.NEGATIVE_INFINITY);
		assertThat(ByteArrays.toDouble(ByteArrays.ofDouble(Double.NEGATIVE_INFINITY, ByteOrder.LITTLE_ENDIAN), ByteOrder.BIG_ENDIAN)).isNotEqualTo(Double.NEGATIVE_INFINITY);

		assertThat(ByteArrays.toDouble(ByteArrays.ofDouble(Double.MIN_NORMAL, ByteOrder.BIG_ENDIAN), ByteOrder.BIG_ENDIAN)).isEqualTo(Double.MIN_NORMAL);
		assertThat(ByteArrays.toDouble(ByteArrays.ofDouble(Double.MIN_NORMAL, ByteOrder.LITTLE_ENDIAN), ByteOrder.LITTLE_ENDIAN)).isEqualTo(Double.MIN_NORMAL);
		assertThat(ByteArrays.toDouble(ByteArrays.ofDouble(Double.MIN_NORMAL, ByteOrder.BIG_ENDIAN), ByteOrder.LITTLE_ENDIAN)).isNotEqualTo(Double.MIN_NORMAL);
		assertThat(ByteArrays.toDouble(ByteArrays.ofDouble(Double.MIN_NORMAL, ByteOrder.LITTLE_ENDIAN), ByteOrder.BIG_ENDIAN)).isNotEqualTo(Double.MIN_NORMAL);

		assertThat(ByteArrays.toDouble(ByteArrays.ofDouble(Double.POSITIVE_INFINITY, ByteOrder.BIG_ENDIAN), ByteOrder.BIG_ENDIAN)).isEqualTo(Double.POSITIVE_INFINITY);
		assertThat(ByteArrays.toDouble(ByteArrays.ofDouble(Double.POSITIVE_INFINITY, ByteOrder.LITTLE_ENDIAN), ByteOrder.LITTLE_ENDIAN)).isEqualTo(Double.POSITIVE_INFINITY);
		assertThat(ByteArrays.toDouble(ByteArrays.ofDouble(Double.POSITIVE_INFINITY, ByteOrder.BIG_ENDIAN), ByteOrder.LITTLE_ENDIAN)).isNotEqualTo(Double.POSITIVE_INFINITY);
		assertThat(ByteArrays.toDouble(ByteArrays.ofDouble(Double.POSITIVE_INFINITY, ByteOrder.LITTLE_ENDIAN), ByteOrder.BIG_ENDIAN)).isNotEqualTo(Double.POSITIVE_INFINITY);

		assertThat(ByteArrays.toDouble(ByteArrays.ofDouble(Double.MAX_VALUE, ByteOrder.BIG_ENDIAN), ByteOrder.BIG_ENDIAN)).isEqualTo(Double.MAX_VALUE);
		assertThat(ByteArrays.toDouble(ByteArrays.ofDouble(Double.MAX_VALUE, ByteOrder.LITTLE_ENDIAN), ByteOrder.LITTLE_ENDIAN)).isEqualTo(Double.MAX_VALUE);
		assertThat(ByteArrays.toDouble(ByteArrays.ofDouble(Double.MAX_VALUE, ByteOrder.BIG_ENDIAN), ByteOrder.LITTLE_ENDIAN)).isNotEqualTo(Double.MAX_VALUE);
		assertThat(ByteArrays.toDouble(ByteArrays.ofDouble(Double.MAX_VALUE, ByteOrder.LITTLE_ENDIAN), ByteOrder.BIG_ENDIAN)).isNotEqualTo(Double.MAX_VALUE);

		assertThat(ByteArrays.toDouble(ByteArrays.ofDouble(Double.NaN, ByteOrder.BIG_ENDIAN), ByteOrder.BIG_ENDIAN)).isEqualTo(Double.NaN);
		assertThat(ByteArrays.toDouble(ByteArrays.ofDouble(Double.NaN, ByteOrder.LITTLE_ENDIAN), ByteOrder.LITTLE_ENDIAN)).isEqualTo(Double.NaN);
		assertThat(ByteArrays.toDouble(ByteArrays.ofDouble(Double.NaN, ByteOrder.BIG_ENDIAN), ByteOrder.LITTLE_ENDIAN)).isNotEqualTo(Double.NaN);
		assertThat(ByteArrays.toDouble(ByteArrays.ofDouble(Double.NaN, ByteOrder.LITTLE_ENDIAN), ByteOrder.BIG_ENDIAN)).isNotEqualTo(Double.NaN);
	}

	@Test
	void testOfDoubleInvalid() {
		assertThatNullPointerException().isThrownBy(() -> ByteArrays.ofDouble(0.0d, null));
	}

	@Test
	void testToDoubleInvalid() {
		assertThatNullPointerException().isThrownBy(() -> ByteArrays.toDouble(null));
		assertThatIllegalArgumentException().isThrownBy(() -> ByteArrays.toDouble(ByteArrays.EMPTY));
		assertThatNullPointerException().isThrownBy(() -> ByteArrays.toDouble(ByteArrays.ofDouble(0.0d), null));
	}

	@Test
	void testOfBinaryString() {
		assertThat(ByteArrays.ofBinaryString(Strings.EMPTY)).isEqualTo(ByteArrays.EMPTY);
		assertThat(ByteArrays.ofBinaryString("00000000")).isEqualTo(ByteArrays.singleton((byte) 0b00000000));
		assertThat(ByteArrays.ofBinaryString("11111111")).isEqualTo(ByteArrays.singleton((byte) 0b11111111));
		assertThat(ByteArrays.ofBinaryString("0101010110101010")).isEqualTo(ByteArrays.of((byte) 0b01010101, (byte) 0b10101010));
	}

	@Test
	void testOfBinaryStringInvalid() {
		assertThatNullPointerException().isThrownBy(() -> ByteArrays.ofBinaryString(null));
		assertThatIllegalArgumentException().isThrownBy(() -> ByteArrays.ofBinaryString("0000000"));
		assertThatIllegalArgumentException().isThrownBy(() -> ByteArrays.ofBinaryString("0000000?"));
	}

	@Test
	void testToBinaryString() {
		assertThat(ByteArrays.toBinaryString(ByteArrays.EMPTY)).isEmpty();
		assertThat(ByteArrays.toBinaryString(ByteArrays.singleton((byte) 0b00000000))).isEqualTo("00000000");
		assertThat(ByteArrays.toBinaryString(ByteArrays.singleton((byte) 0b11111111))).isEqualTo("11111111");
		assertThat(ByteArrays.toBinaryString(ByteArrays.of((byte) 0b01010101, (byte) 0b10101010))).isEqualTo("0101010110101010");
	}

	@Test
	void testToBinaryStringInvalid() {
		assertThatNullPointerException().isThrownBy(() -> ByteArrays.toBinaryString(null));
	}

	@Test
	void testOfHexadecimalString() {
		assertThat(ByteArrays.ofHexadecimalString(Strings.EMPTY)).isEqualTo(ByteArrays.EMPTY);
		assertThat(ByteArrays.ofHexadecimalString("00")).isEqualTo(ByteArrays.singleton((byte) 0x00));
		assertThat(ByteArrays.ofHexadecimalString("ff")).isEqualTo(ByteArrays.singleton((byte) 0xff));
		assertThat(ByteArrays.ofHexadecimalString("FF")).isEqualTo(ByteArrays.singleton((byte) 0xff));
		assertThat(ByteArrays.ofHexadecimalString("0ff0")).isEqualTo(ByteArrays.of((byte) 0x0f, (byte) 0xf0));
	}

	@Test
	void testOfHexadecimalStringInvalid() {
		assertThatNullPointerException().isThrownBy(() -> ByteArrays.ofHexadecimalString(null));
		assertThatIllegalArgumentException().isThrownBy(() -> ByteArrays.ofHexadecimalString("0"));
		assertThatIllegalArgumentException().isThrownBy(() -> ByteArrays.ofHexadecimalString("0?"));
	}

	@Test
	void testToHexadecimalString() {
		assertThat(ByteArrays.toHexadecimalString(ByteArrays.EMPTY)).isEmpty();
		assertThat(ByteArrays.toHexadecimalString(ByteArrays.singleton((byte) 0x00))).isEqualTo("00");
		assertThat(ByteArrays.toHexadecimalString(ByteArrays.singleton((byte) 0xff))).isEqualTo("ff");
		assertThat(ByteArrays.toHexadecimalString(ByteArrays.of((byte) 0x0f, (byte) 0xf0))).isEqualTo("0ff0");
	}

	@Test
	void testToHexadecimalStringInvalid() {
		assertThatNullPointerException().isThrownBy(() -> ByteArrays.toHexadecimalString(null));
	}
}