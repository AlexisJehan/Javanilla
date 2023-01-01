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

import com.github.alexisjehan.javanilla.lang.Strings;
import org.junit.jupiter.api.Test;

import java.nio.ByteOrder;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.assertj.core.api.Assertions.assertThatNullPointerException;

final class ByteArraysTest {

	private static final byte[] VALUES = {(byte) 1, (byte) 2};

	@Test
	void testEmpty() {
		assertThat(ByteArrays.EMPTY).isEmpty();
	}

	@Test
	void testNullToEmpty() {
		assertThat(ByteArrays.nullToEmpty(null)).isEmpty();
		assertThat(ByteArrays.nullToEmpty(ByteArrays.EMPTY)).isEmpty();
		assertThat(ByteArrays.nullToEmpty(ByteArrays.of(VALUES))).containsExactly(VALUES);
	}

	@Test
	void testNullToDefault() {
		assertThat(ByteArrays.nullToDefault(null, ByteArrays.singleton((byte) 0))).containsExactly((byte) 0);
		assertThat(ByteArrays.nullToDefault(ByteArrays.EMPTY, ByteArrays.singleton((byte) 0))).isEmpty();
		assertThat(ByteArrays.nullToDefault(ByteArrays.of(VALUES), ByteArrays.singleton((byte) 0))).containsExactly(VALUES);
	}

	@Test
	void testNullToDefaultInvalid() {
		assertThatNullPointerException().isThrownBy(() -> ByteArrays.nullToDefault(ByteArrays.of(VALUES), null));
	}

	@Test
	void testEmptyToNull() {
		assertThat(ByteArrays.emptyToNull(null)).isNull();
		assertThat(ByteArrays.emptyToNull(ByteArrays.EMPTY)).isNull();
		assertThat(ByteArrays.emptyToNull(ByteArrays.of(VALUES))).containsExactly(VALUES);
	}

	@Test
	void testEmptyToDefault() {
		assertThat(ByteArrays.emptyToDefault(null, ByteArrays.singleton((byte) 0))).isNull();
		assertThat(ByteArrays.emptyToDefault(ByteArrays.EMPTY, ByteArrays.singleton((byte) 0))).containsExactly((byte) 0);
		assertThat(ByteArrays.emptyToDefault(ByteArrays.of(VALUES), ByteArrays.singleton((byte) 0))).containsExactly(VALUES);
	}

	@Test
	void testEmptyToDefaultInvalid() {
		assertThatIllegalArgumentException().isThrownBy(() -> ByteArrays.emptyToDefault(ByteArrays.of(VALUES), ByteArrays.EMPTY));
	}

	@Test
	@SuppressWarnings("deprecation")
	void testAddLegacy() {
		assertThat(ByteArrays.add(ByteArrays.of((byte) 1, (byte) 2, (byte) 3), 0, (byte) 0)).containsExactly((byte) 0, (byte) 1, (byte) 2, (byte) 3);
		assertThat(ByteArrays.add(ByteArrays.of((byte) 1, (byte) 2, (byte) 3), 1, (byte) 0)).containsExactly((byte) 1, (byte) 0, (byte) 2, (byte) 3);
		assertThat(ByteArrays.add(ByteArrays.of((byte) 1, (byte) 2, (byte) 3), 2, (byte) 0)).containsExactly((byte) 1, (byte) 2, (byte) 0, (byte) 3);
		assertThat(ByteArrays.add(ByteArrays.of((byte) 1, (byte) 2, (byte) 3), 3, (byte) 0)).containsExactly((byte) 1, (byte) 2, (byte) 3, (byte) 0);
	}

	@Test
	void testAdd() {
		assertThat(ByteArrays.add(ByteArrays.EMPTY, (byte) 0)).containsExactly((byte) 0);
		assertThat(ByteArrays.add(ByteArrays.of((byte) 1, (byte) 2, (byte) 3), (byte) 0, 0)).containsExactly((byte) 0, (byte) 1, (byte) 2, (byte) 3);
		assertThat(ByteArrays.add(ByteArrays.of((byte) 1, (byte) 2, (byte) 3), (byte) 0, 1)).containsExactly((byte) 1, (byte) 0, (byte) 2, (byte) 3);
		assertThat(ByteArrays.add(ByteArrays.of((byte) 1, (byte) 2, (byte) 3), (byte) 0, 2)).containsExactly((byte) 1, (byte) 2, (byte) 0, (byte) 3);
		assertThat(ByteArrays.add(ByteArrays.of((byte) 1, (byte) 2, (byte) 3), (byte) 0, 3)).containsExactly((byte) 1, (byte) 2, (byte) 3, (byte) 0);
		assertThat(ByteArrays.add(ByteArrays.of((byte) 1, (byte) 2, (byte) 3), (byte) 0)).containsExactly((byte) 1, (byte) 2, (byte) 3, (byte) 0);
	}

	@Test
	@SuppressWarnings("deprecation")
	void testAddInvalidLegacy() {
		assertThatNullPointerException().isThrownBy(() -> ByteArrays.add(null, 0, (byte) 0));
		assertThatIllegalArgumentException().isThrownBy(() -> ByteArrays.add(ByteArrays.of(VALUES), -1, (byte) 0));
		assertThatIllegalArgumentException().isThrownBy(() -> ByteArrays.add(ByteArrays.of(VALUES), 3, (byte) 0));
	}

	@Test
	void testAddInvalid() {
		assertThatNullPointerException().isThrownBy(() -> ByteArrays.add(null, (byte) 0));
		assertThatNullPointerException().isThrownBy(() -> ByteArrays.add(null, (byte) 0, 0));
		assertThatIllegalArgumentException().isThrownBy(() -> ByteArrays.add(ByteArrays.of(VALUES), (byte) 0, -1));
		assertThatIllegalArgumentException().isThrownBy(() -> ByteArrays.add(ByteArrays.of(VALUES), (byte) 0, 3));
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
		assertThatIllegalArgumentException().isThrownBy(() -> ByteArrays.remove(ByteArrays.of(VALUES), -1));
		assertThatIllegalArgumentException().isThrownBy(() -> ByteArrays.remove(ByteArrays.of(VALUES), 2));
	}

	@Test
	void testConcat() {
		assertThat(ByteArrays.concat()).isEmpty();
		assertThat(ByteArrays.concat(ByteArrays.singleton(VALUES[0]))).containsExactly(VALUES[0]);
		assertThat(ByteArrays.concat(ByteArrays.singleton(VALUES[0]), ByteArrays.singleton(VALUES[1]))).containsExactly(VALUES);
		assertThat(ByteArrays.concat(List.of(ByteArrays.singleton(VALUES[0]), ByteArrays.singleton(VALUES[1])))).containsExactly(VALUES);
	}

	@Test
	void testConcatInvalid() {
		assertThatNullPointerException().isThrownBy(() -> ByteArrays.concat((byte[][]) null));
		assertThatNullPointerException().isThrownBy(() -> ByteArrays.concat((byte[]) null));
		assertThatNullPointerException().isThrownBy(() -> ByteArrays.concat((List<byte[]>) null));
		assertThatNullPointerException().isThrownBy(() -> ByteArrays.concat(Collections.singletonList(null)));
	}

	@Test
	void testJoin() {
		assertThat(ByteArrays.join(ByteArrays.EMPTY, ByteArrays.singleton(VALUES[0]), ByteArrays.singleton(VALUES[1]))).containsExactly(VALUES);
		assertThat(ByteArrays.join(ByteArrays.singleton((byte) 0))).isEmpty();
		assertThat(ByteArrays.join(ByteArrays.singleton((byte) 0), ByteArrays.singleton(VALUES[0]))).containsExactly(VALUES[0]);
		assertThat(ByteArrays.join(ByteArrays.singleton((byte) 0), ByteArrays.singleton(VALUES[0]), ByteArrays.singleton(VALUES[1]))).containsExactly(VALUES[0], (byte) 0, VALUES[1]);
		assertThat(ByteArrays.join(ByteArrays.singleton((byte) 0), List.of(ByteArrays.singleton(VALUES[0]), ByteArrays.singleton(VALUES[1])))).containsExactly(VALUES[0], (byte) 0, VALUES[1]);
	}

	@Test
	void testJoinInvalid() {
		assertThatNullPointerException().isThrownBy(() -> ByteArrays.join(null, ByteArrays.of(VALUES)));
		assertThatNullPointerException().isThrownBy(() -> ByteArrays.join(ByteArrays.of(VALUES), (byte[][]) null));
		assertThatNullPointerException().isThrownBy(() -> ByteArrays.join(ByteArrays.of(VALUES), (byte[]) null));
		assertThatNullPointerException().isThrownBy(() -> ByteArrays.join(ByteArrays.of(VALUES), (List<byte[]>) null));
		assertThatNullPointerException().isThrownBy(() -> ByteArrays.join(ByteArrays.of(VALUES), Collections.singletonList(null)));
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
		assertThatNullPointerException().isThrownBy(() -> ByteArrays.containsAny(ByteArrays.of(VALUES), (byte[]) null));
		assertThatIllegalArgumentException().isThrownBy(() -> ByteArrays.containsAny(ByteArrays.of(VALUES)));
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
		assertThatNullPointerException().isThrownBy(() -> ByteArrays.containsAll(ByteArrays.of(VALUES), (byte[]) null));
		assertThatIllegalArgumentException().isThrownBy(() -> ByteArrays.containsAll(ByteArrays.of(VALUES)));
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
		assertThatNullPointerException().isThrownBy(() -> ByteArrays.containsOnce(ByteArrays.of(VALUES), (byte[]) null));
		assertThatIllegalArgumentException().isThrownBy(() -> ByteArrays.containsOnce(ByteArrays.of(VALUES)));
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
		assertThatNullPointerException().isThrownBy(() -> ByteArrays.containsOnly(ByteArrays.of(VALUES), (byte[]) null));
		assertThatIllegalArgumentException().isThrownBy(() -> ByteArrays.containsOnly(ByteArrays.of(VALUES)));
	}

	@Test
	void testIndexOf() {
		assertThat(ByteArrays.indexOf(ByteArrays.EMPTY, (byte) 1)).isEqualTo(-1);
		assertThat(ByteArrays.indexOf(ByteArrays.of((byte) 1, (byte) 2, (byte) 1), (byte) 1)).isZero();
		assertThat(ByteArrays.indexOf(ByteArrays.of((byte) 1, (byte) 2, (byte) 1), (byte) 2)).isEqualTo(1);
		assertThat(ByteArrays.indexOf(ByteArrays.of((byte) 1, (byte) 2, (byte) 1), (byte) 1, 1)).isEqualTo(2);
		assertThat(ByteArrays.indexOf(ByteArrays.of((byte) 1, (byte) 2, (byte) 1), (byte) 2, 2)).isEqualTo(-1);
	}

	@Test
	void testIndexOfInvalid() {
		assertThatNullPointerException().isThrownBy(() -> ByteArrays.indexOf(null, (byte) 1));
		assertThatIllegalArgumentException().isThrownBy(() -> ByteArrays.indexOf(ByteArrays.of(VALUES), (byte) 1, -1));
		assertThatIllegalArgumentException().isThrownBy(() -> ByteArrays.indexOf(ByteArrays.of(VALUES), (byte) 1, 2));
	}

	@Test
	void testLastIndexOf() {
		assertThat(ByteArrays.lastIndexOf(ByteArrays.EMPTY, (byte) 1)).isEqualTo(-1);
		assertThat(ByteArrays.lastIndexOf(ByteArrays.of((byte) 1, (byte) 2, (byte) 1), (byte) 1)).isEqualTo(2);
		assertThat(ByteArrays.lastIndexOf(ByteArrays.of((byte) 1, (byte) 2, (byte) 1), (byte) 2)).isEqualTo(1);
		assertThat(ByteArrays.lastIndexOf(ByteArrays.of((byte) 1, (byte) 2, (byte) 1), (byte) 1, 1)).isEqualTo(2);
		assertThat(ByteArrays.lastIndexOf(ByteArrays.of((byte) 1, (byte) 2, (byte) 1), (byte) 2, 2)).isEqualTo(-1);
	}

	@Test
	void testLastIndexOfInvalid() {
		assertThatNullPointerException().isThrownBy(() -> ByteArrays.lastIndexOf(null, (byte) 1));
		assertThatIllegalArgumentException().isThrownBy(() -> ByteArrays.lastIndexOf(ByteArrays.of(VALUES), (byte) 1, -1));
		assertThatIllegalArgumentException().isThrownBy(() -> ByteArrays.lastIndexOf(ByteArrays.of(VALUES), (byte) 1, 2));
	}

	@Test
	void testFrequency() {
		assertThat(ByteArrays.frequency(ByteArrays.EMPTY, (byte) 1)).isZero();
		assertThat(ByteArrays.frequency(ByteArrays.of((byte) 1, (byte) 2, (byte) 1), (byte) 1)).isEqualTo(2);
		assertThat(ByteArrays.frequency(ByteArrays.of((byte) 1, (byte) 2, (byte) 1), (byte) 2)).isEqualTo(1);
	}

	@Test
	void testFrequencyInvalid() {
		assertThatNullPointerException().isThrownBy(() -> ByteArrays.frequency(null, (byte) 1));
	}

	@Test
	void testReverse() {
		assertThat(ByteArrays.singleton((byte) 1)).satisfies(array -> {
			ByteArrays.reverse(array);
			assertThat(array).containsExactly((byte) 1);
		});

		// Even
		assertThat(ByteArrays.of((byte) 1, (byte) 2, (byte) 1, (byte) 2)).satisfies(array -> {
			ByteArrays.reverse(array);
			assertThat(array).containsExactly((byte) 2, (byte) 1, (byte) 2, (byte) 1);
		});

		// Odd
		assertThat(ByteArrays.of((byte) 1, (byte) 2, (byte) 2)).satisfies(array -> {
			ByteArrays.reverse(array);
			assertThat(array).containsExactly((byte) 2, (byte) 2, (byte) 1);
		});
	}

	@Test
	void testReverseInvalid() {
		assertThatNullPointerException().isThrownBy(() -> ByteArrays.reverse(null));
	}

	@Test
	void testReorder() {
		assertThat(ByteArrays.singleton((byte) 1)).satisfies(array -> {
			ByteArrays.reorder(array, 0);
			assertThat(array).containsExactly((byte) 1);
		});
		assertThat(ByteArrays.of((byte) 1, (byte) 2, (byte) 1, (byte) 2)).satisfies(array -> {
			ByteArrays.reorder(array, 2, 0, 3, 1);
			assertThat(array).containsExactly((byte) 1, (byte) 1, (byte) 2, (byte) 2);
		});
	}

	@Test
	void testReorderInvalid() {
		assertThatNullPointerException().isThrownBy(() -> ByteArrays.reorder(null));
		assertThatNullPointerException().isThrownBy(() -> ByteArrays.reorder(ByteArrays.of(VALUES), (int[]) null));
		assertThatIllegalArgumentException().isThrownBy(() -> ByteArrays.reorder(ByteArrays.of(VALUES), IntArrays.singleton(0)));
		assertThatIllegalArgumentException().isThrownBy(() -> ByteArrays.reorder(ByteArrays.of(VALUES), IntArrays.of(0, 0)));
		assertThatIllegalArgumentException().isThrownBy(() -> ByteArrays.reorder(ByteArrays.of(VALUES), IntArrays.of(-1, 1)));
		assertThatIllegalArgumentException().isThrownBy(() -> ByteArrays.reorder(ByteArrays.of(VALUES), IntArrays.of(2, 1)));
		assertThatIllegalArgumentException().isThrownBy(() -> ByteArrays.reorder(ByteArrays.of(VALUES), IntArrays.of(0, -1)));
		assertThatIllegalArgumentException().isThrownBy(() -> ByteArrays.reorder(ByteArrays.of(VALUES), IntArrays.of(0, 2)));
	}

	@Test
	@SuppressWarnings("deprecation")
	void testShuffleLegacy() {
		assertThat(ByteArrays.singleton((byte) 1)).satisfies(array -> {
			ByteArrays.shuffle(array);
			assertThat(array).containsExactly((byte) 1);
		});
	}

	@Test
	void testShuffle() {
		assertThat(ByteArrays.of((byte) 1, (byte) 2, (byte) 1, (byte) 2)).satisfies(array -> {
			ByteArrays.shuffle(array, ThreadLocalRandom.current());
			assertThat(array).containsExactlyInAnyOrder((byte) 1, (byte) 2, (byte) 1, (byte) 2);
		});
	}

	@Test
	@SuppressWarnings("deprecation")
	void testShuffleInvalidLegacy() {
		assertThatNullPointerException().isThrownBy(() -> ByteArrays.shuffle(null));
	}

	@Test
	void testShuffleInvalid() {
		assertThatNullPointerException().isThrownBy(() -> ByteArrays.shuffle(null, ThreadLocalRandom.current()));
		assertThatNullPointerException().isThrownBy(() -> ByteArrays.shuffle(ByteArrays.of(VALUES), null));
	}

	@Test
	void testSwap() {
		assertThat(ByteArrays.singleton((byte) 1)).satisfies(array -> {
			ByteArrays.swap(array, 0, 0);
			assertThat(array).containsExactly((byte) 1);
		});
		assertThat(ByteArrays.of((byte) 1, (byte) 2, (byte) 1, (byte) 2)).satisfies(array -> {
			ByteArrays.swap(array, 1, 2);
			assertThat(array).containsExactly((byte) 1, (byte) 1, (byte) 2, (byte) 2);
		});
	}

	@Test
	void testSwapInvalid() {
		assertThatNullPointerException().isThrownBy(() -> ByteArrays.swap(null, 0, 0));
		assertThatIllegalArgumentException().isThrownBy(() -> ByteArrays.swap(ByteArrays.of(VALUES), -1, 1));
		assertThatIllegalArgumentException().isThrownBy(() -> ByteArrays.swap(ByteArrays.of(VALUES), 2, 1));
		assertThatIllegalArgumentException().isThrownBy(() -> ByteArrays.swap(ByteArrays.of(VALUES), 0, -1));
		assertThatIllegalArgumentException().isThrownBy(() -> ByteArrays.swap(ByteArrays.of(VALUES), 0, 2));
	}

	@Test
	void testIsEmpty() {
		assertThat(ByteArrays.isEmpty(ByteArrays.EMPTY)).isTrue();
		assertThat(ByteArrays.isEmpty(ByteArrays.of(VALUES))).isFalse();
	}

	@Test
	void testIsEmptyInvalid() {
		assertThatNullPointerException().isThrownBy(() -> ByteArrays.isEmpty(null));
	}

	@Test
	void testSingleton() {
		assertThat(ByteArrays.singleton(VALUES[0])).containsExactly(VALUES[0]);
	}

	@Test
	void testOfBytes() {
		assertThat(ByteArrays.of()).isEmpty();
		assertThat(ByteArrays.of(VALUES)).containsExactly(VALUES);
	}

	@Test
	void testOfBytesInvalid() {
		assertThatNullPointerException().isThrownBy(() -> ByteArrays.of((byte[]) null));
	}

	@Test
	void testOfBoxedAndToBoxed() {
		assertThat(ByteArrays.of(ByteArrays.toBoxed(ByteArrays.EMPTY))).isEmpty();
		assertThat(ByteArrays.of(ByteArrays.toBoxed(ByteArrays.of(VALUES)))).containsExactly(VALUES);
		assertThat(ByteArrays.toBoxed(ByteArrays.of(VALUES))).isInstanceOf(Byte[].class);
		assertThat(ByteArrays.of(ByteArrays.toBoxed(ByteArrays.of(VALUES)))).isInstanceOf(byte[].class);
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
	@SuppressWarnings("deprecation")
	void testOfBooleanAndToBooleanLegacy() {
		assertThat(ByteArrays.toBoolean(ByteArrays.ofBoolean(false))).isFalse();
		assertThat(ByteArrays.toBoolean(ByteArrays.ofBoolean(true))).isTrue();
	}

	@Test
	void testOfBooleanAndToBoolean() {
		assertThat(ByteArrays.toBoolean(ByteArrays.of(false))).isFalse();
		assertThat(ByteArrays.toBoolean(ByteArrays.of(true))).isTrue();
	}

	@Test
	void testToBooleanInvalid() {
		assertThatNullPointerException().isThrownBy(() -> ByteArrays.toBoolean(null));
		assertThatIllegalArgumentException().isThrownBy(() -> ByteArrays.toBoolean(ByteArrays.EMPTY));
	}

	@Test
	@SuppressWarnings("deprecation")
	void testOfShortAndToShortLegacy() {
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
	void testOfShortAndToShort() {
		assertThat(ByteArrays.toShort(ByteArrays.of((short) 0))).isEqualTo((short) 0);
		assertThat(ByteArrays.toShort(ByteArrays.of((short) 0, ByteOrder.BIG_ENDIAN), ByteOrder.BIG_ENDIAN)).isEqualTo((short) 0);
		assertThat(ByteArrays.toShort(ByteArrays.of((short) 0, ByteOrder.LITTLE_ENDIAN), ByteOrder.LITTLE_ENDIAN)).isEqualTo((short) 0);

		assertThat(ByteArrays.toShort(ByteArrays.of((short) -5, ByteOrder.BIG_ENDIAN), ByteOrder.BIG_ENDIAN)).isEqualTo((short) -5);
		assertThat(ByteArrays.toShort(ByteArrays.of((short) -5, ByteOrder.LITTLE_ENDIAN), ByteOrder.LITTLE_ENDIAN)).isEqualTo((short) -5);
		assertThat(ByteArrays.toShort(ByteArrays.of((short) -5, ByteOrder.BIG_ENDIAN), ByteOrder.LITTLE_ENDIAN)).isNotEqualTo((short) -5);
		assertThat(ByteArrays.toShort(ByteArrays.of((short) -5, ByteOrder.LITTLE_ENDIAN), ByteOrder.BIG_ENDIAN)).isNotEqualTo((short) -5);

		assertThat(ByteArrays.toShort(ByteArrays.of((short) 5, ByteOrder.BIG_ENDIAN), ByteOrder.BIG_ENDIAN)).isEqualTo((short) 5);
		assertThat(ByteArrays.toShort(ByteArrays.of((short) 5, ByteOrder.LITTLE_ENDIAN), ByteOrder.LITTLE_ENDIAN)).isEqualTo((short) 5);
		assertThat(ByteArrays.toShort(ByteArrays.of((short) 5, ByteOrder.BIG_ENDIAN), ByteOrder.LITTLE_ENDIAN)).isNotEqualTo((short) 5);
		assertThat(ByteArrays.toShort(ByteArrays.of((short) 5, ByteOrder.LITTLE_ENDIAN), ByteOrder.BIG_ENDIAN)).isNotEqualTo((short) 5);

		assertThat(ByteArrays.toShort(ByteArrays.of(Short.MIN_VALUE, ByteOrder.BIG_ENDIAN), ByteOrder.BIG_ENDIAN)).isEqualTo(Short.MIN_VALUE);
		assertThat(ByteArrays.toShort(ByteArrays.of(Short.MIN_VALUE, ByteOrder.LITTLE_ENDIAN), ByteOrder.LITTLE_ENDIAN)).isEqualTo(Short.MIN_VALUE);
		assertThat(ByteArrays.toShort(ByteArrays.of(Short.MIN_VALUE, ByteOrder.BIG_ENDIAN), ByteOrder.LITTLE_ENDIAN)).isNotEqualTo(Short.MIN_VALUE);
		assertThat(ByteArrays.toShort(ByteArrays.of(Short.MIN_VALUE, ByteOrder.LITTLE_ENDIAN), ByteOrder.BIG_ENDIAN)).isNotEqualTo(Short.MIN_VALUE);

		assertThat(ByteArrays.toShort(ByteArrays.of(Short.MAX_VALUE, ByteOrder.BIG_ENDIAN), ByteOrder.BIG_ENDIAN)).isEqualTo(Short.MAX_VALUE);
		assertThat(ByteArrays.toShort(ByteArrays.of(Short.MAX_VALUE, ByteOrder.LITTLE_ENDIAN), ByteOrder.LITTLE_ENDIAN)).isEqualTo(Short.MAX_VALUE);
		assertThat(ByteArrays.toShort(ByteArrays.of(Short.MAX_VALUE, ByteOrder.BIG_ENDIAN), ByteOrder.LITTLE_ENDIAN)).isNotEqualTo(Short.MAX_VALUE);
		assertThat(ByteArrays.toShort(ByteArrays.of(Short.MAX_VALUE, ByteOrder.LITTLE_ENDIAN), ByteOrder.BIG_ENDIAN)).isNotEqualTo(Short.MAX_VALUE);
	}

	@Test
	@SuppressWarnings("deprecation")
	void testOfShortInvalidLegacy() {
		assertThatNullPointerException().isThrownBy(() -> ByteArrays.ofShort((short) 0, null));
	}

	@Test
	void testOfShortInvalid() {
		assertThatNullPointerException().isThrownBy(() -> ByteArrays.of((short) 0, null));
	}

	@Test
	void testToShortInvalid() {
		assertThatNullPointerException().isThrownBy(() -> ByteArrays.toShort(null));
		assertThatIllegalArgumentException().isThrownBy(() -> ByteArrays.toShort(ByteArrays.EMPTY));
		assertThatNullPointerException().isThrownBy(() -> ByteArrays.toShort(ByteArrays.of((short) 0), null));
	}

	@Test
	@SuppressWarnings("deprecation")
	void testOfCharAndToCharLegacy() {
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
	void testOfCharAndToChar() {
		assertThat(ByteArrays.toChar(ByteArrays.of(Character.MIN_VALUE))).isEqualTo(Character.MIN_VALUE);
		assertThat(ByteArrays.toChar(ByteArrays.of(Character.MIN_VALUE, ByteOrder.BIG_ENDIAN), ByteOrder.BIG_ENDIAN)).isEqualTo(Character.MIN_VALUE);
		assertThat(ByteArrays.toChar(ByteArrays.of(Character.MIN_VALUE, ByteOrder.LITTLE_ENDIAN), ByteOrder.LITTLE_ENDIAN)).isEqualTo(Character.MIN_VALUE);

		assertThat(ByteArrays.toChar(ByteArrays.of((char) 5, ByteOrder.BIG_ENDIAN), ByteOrder.BIG_ENDIAN)).isEqualTo((char) 5);
		assertThat(ByteArrays.toChar(ByteArrays.of((char) 5, ByteOrder.LITTLE_ENDIAN), ByteOrder.LITTLE_ENDIAN)).isEqualTo((char) 5);
		assertThat(ByteArrays.toChar(ByteArrays.of((char) 5, ByteOrder.BIG_ENDIAN), ByteOrder.LITTLE_ENDIAN)).isNotEqualTo((char) 5);
		assertThat(ByteArrays.toChar(ByteArrays.of((char) 5, ByteOrder.LITTLE_ENDIAN), ByteOrder.BIG_ENDIAN)).isNotEqualTo((char) 5);

		assertThat(ByteArrays.toChar(ByteArrays.of(Character.MAX_VALUE, ByteOrder.BIG_ENDIAN), ByteOrder.BIG_ENDIAN)).isEqualTo(Character.MAX_VALUE);
		assertThat(ByteArrays.toChar(ByteArrays.of(Character.MAX_VALUE, ByteOrder.LITTLE_ENDIAN), ByteOrder.LITTLE_ENDIAN)).isEqualTo(Character.MAX_VALUE);
	}

	@Test
	@SuppressWarnings("deprecation")
	void testOfCharInvalidLegacy() {
		assertThatNullPointerException().isThrownBy(() -> ByteArrays.ofChar((char) 0, null));
	}

	@Test
	void testOfCharInvalid() {
		assertThatNullPointerException().isThrownBy(() -> ByteArrays.of((char) 0, null));
	}

	@Test
	void testToCharInvalid() {
		assertThatNullPointerException().isThrownBy(() -> ByteArrays.toChar(null));
		assertThatIllegalArgumentException().isThrownBy(() -> ByteArrays.toChar(ByteArrays.EMPTY));
		assertThatNullPointerException().isThrownBy(() -> ByteArrays.toChar(ByteArrays.of((char) 0), null));
	}

	@Test
	@SuppressWarnings("deprecation")
	void testOfIntAndToIntLegacy() {
		assertThat(ByteArrays.toInt(ByteArrays.ofInt(0))).isZero();
		assertThat(ByteArrays.toInt(ByteArrays.ofInt(0, ByteOrder.BIG_ENDIAN), ByteOrder.BIG_ENDIAN)).isZero();
		assertThat(ByteArrays.toInt(ByteArrays.ofInt(0, ByteOrder.LITTLE_ENDIAN), ByteOrder.LITTLE_ENDIAN)).isZero();

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
	void testOfIntAndToInt() {
		assertThat(ByteArrays.toInt(ByteArrays.of(0))).isZero();
		assertThat(ByteArrays.toInt(ByteArrays.of(0, ByteOrder.BIG_ENDIAN), ByteOrder.BIG_ENDIAN)).isZero();
		assertThat(ByteArrays.toInt(ByteArrays.of(0, ByteOrder.LITTLE_ENDIAN), ByteOrder.LITTLE_ENDIAN)).isZero();

		assertThat(ByteArrays.toInt(ByteArrays.of(-5, ByteOrder.BIG_ENDIAN), ByteOrder.BIG_ENDIAN)).isEqualTo(-5);
		assertThat(ByteArrays.toInt(ByteArrays.of(-5, ByteOrder.LITTLE_ENDIAN), ByteOrder.LITTLE_ENDIAN)).isEqualTo(-5);
		assertThat(ByteArrays.toInt(ByteArrays.of(-5, ByteOrder.BIG_ENDIAN), ByteOrder.LITTLE_ENDIAN)).isNotEqualTo(-5);
		assertThat(ByteArrays.toInt(ByteArrays.of(-5, ByteOrder.LITTLE_ENDIAN), ByteOrder.BIG_ENDIAN)).isNotEqualTo(-5);

		assertThat(ByteArrays.toInt(ByteArrays.of(5, ByteOrder.BIG_ENDIAN), ByteOrder.BIG_ENDIAN)).isEqualTo(5);
		assertThat(ByteArrays.toInt(ByteArrays.of(5, ByteOrder.LITTLE_ENDIAN), ByteOrder.LITTLE_ENDIAN)).isEqualTo(5);
		assertThat(ByteArrays.toInt(ByteArrays.of(5, ByteOrder.BIG_ENDIAN), ByteOrder.LITTLE_ENDIAN)).isNotEqualTo(5);
		assertThat(ByteArrays.toInt(ByteArrays.of(5, ByteOrder.LITTLE_ENDIAN), ByteOrder.BIG_ENDIAN)).isNotEqualTo(5);

		assertThat(ByteArrays.toInt(ByteArrays.of(Integer.MIN_VALUE, ByteOrder.BIG_ENDIAN), ByteOrder.BIG_ENDIAN)).isEqualTo(Integer.MIN_VALUE);
		assertThat(ByteArrays.toInt(ByteArrays.of(Integer.MIN_VALUE, ByteOrder.LITTLE_ENDIAN), ByteOrder.LITTLE_ENDIAN)).isEqualTo(Integer.MIN_VALUE);
		assertThat(ByteArrays.toInt(ByteArrays.of(Integer.MIN_VALUE, ByteOrder.BIG_ENDIAN), ByteOrder.LITTLE_ENDIAN)).isNotEqualTo(Integer.MIN_VALUE);
		assertThat(ByteArrays.toInt(ByteArrays.of(Integer.MIN_VALUE, ByteOrder.LITTLE_ENDIAN), ByteOrder.BIG_ENDIAN)).isNotEqualTo(Integer.MIN_VALUE);

		assertThat(ByteArrays.toInt(ByteArrays.of(Integer.MAX_VALUE, ByteOrder.BIG_ENDIAN), ByteOrder.BIG_ENDIAN)).isEqualTo(Integer.MAX_VALUE);
		assertThat(ByteArrays.toInt(ByteArrays.of(Integer.MAX_VALUE, ByteOrder.LITTLE_ENDIAN), ByteOrder.LITTLE_ENDIAN)).isEqualTo(Integer.MAX_VALUE);
		assertThat(ByteArrays.toInt(ByteArrays.of(Integer.MAX_VALUE, ByteOrder.BIG_ENDIAN), ByteOrder.LITTLE_ENDIAN)).isNotEqualTo(Integer.MAX_VALUE);
		assertThat(ByteArrays.toInt(ByteArrays.of(Integer.MAX_VALUE, ByteOrder.LITTLE_ENDIAN), ByteOrder.BIG_ENDIAN)).isNotEqualTo(Integer.MAX_VALUE);
	}

	@Test
	@SuppressWarnings("deprecation")
	void testOfIntInvalidLegacy() {
		assertThatNullPointerException().isThrownBy(() -> ByteArrays.ofInt(0, null));
	}

	@Test
	void testOfIntInvalid() {
		assertThatNullPointerException().isThrownBy(() -> ByteArrays.of(0, null));
	}

	@Test
	void testToIntInvalid() {
		assertThatNullPointerException().isThrownBy(() -> ByteArrays.toInt(null));
		assertThatIllegalArgumentException().isThrownBy(() -> ByteArrays.toInt(ByteArrays.EMPTY));
		assertThatNullPointerException().isThrownBy(() -> ByteArrays.toInt(ByteArrays.of(0), null));
	}

	@Test
	@SuppressWarnings("deprecation")
	void testOfLongAndToLongLegacy() {
		assertThat(ByteArrays.toLong(ByteArrays.ofLong(0L))).isZero();
		assertThat(ByteArrays.toLong(ByteArrays.ofLong(0L, ByteOrder.BIG_ENDIAN), ByteOrder.BIG_ENDIAN)).isZero();
		assertThat(ByteArrays.toLong(ByteArrays.ofLong(0L, ByteOrder.LITTLE_ENDIAN), ByteOrder.LITTLE_ENDIAN)).isZero();

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
	void testOfLongAndToLong() {
		assertThat(ByteArrays.toLong(ByteArrays.of(0L))).isZero();
		assertThat(ByteArrays.toLong(ByteArrays.of(0L, ByteOrder.BIG_ENDIAN), ByteOrder.BIG_ENDIAN)).isZero();
		assertThat(ByteArrays.toLong(ByteArrays.of(0L, ByteOrder.LITTLE_ENDIAN), ByteOrder.LITTLE_ENDIAN)).isZero();

		assertThat(ByteArrays.toLong(ByteArrays.of(-5L, ByteOrder.BIG_ENDIAN), ByteOrder.BIG_ENDIAN)).isEqualTo(-5L);
		assertThat(ByteArrays.toLong(ByteArrays.of(-5L, ByteOrder.LITTLE_ENDIAN), ByteOrder.LITTLE_ENDIAN)).isEqualTo(-5L);
		assertThat(ByteArrays.toLong(ByteArrays.of(-5L, ByteOrder.BIG_ENDIAN), ByteOrder.LITTLE_ENDIAN)).isNotEqualTo(-5L);
		assertThat(ByteArrays.toLong(ByteArrays.of(-5L, ByteOrder.LITTLE_ENDIAN), ByteOrder.BIG_ENDIAN)).isNotEqualTo(-5L);

		assertThat(ByteArrays.toLong(ByteArrays.of(5L, ByteOrder.BIG_ENDIAN), ByteOrder.BIG_ENDIAN)).isEqualTo(5L);
		assertThat(ByteArrays.toLong(ByteArrays.of(5L, ByteOrder.LITTLE_ENDIAN), ByteOrder.LITTLE_ENDIAN)).isEqualTo(5L);
		assertThat(ByteArrays.toLong(ByteArrays.of(5L, ByteOrder.BIG_ENDIAN), ByteOrder.LITTLE_ENDIAN)).isNotEqualTo(5L);
		assertThat(ByteArrays.toLong(ByteArrays.of(5L, ByteOrder.LITTLE_ENDIAN), ByteOrder.BIG_ENDIAN)).isNotEqualTo(5L);

		assertThat(ByteArrays.toLong(ByteArrays.of(Long.MIN_VALUE, ByteOrder.BIG_ENDIAN), ByteOrder.BIG_ENDIAN)).isEqualTo(Long.MIN_VALUE);
		assertThat(ByteArrays.toLong(ByteArrays.of(Long.MIN_VALUE, ByteOrder.LITTLE_ENDIAN), ByteOrder.LITTLE_ENDIAN)).isEqualTo(Long.MIN_VALUE);
		assertThat(ByteArrays.toLong(ByteArrays.of(Long.MIN_VALUE, ByteOrder.BIG_ENDIAN), ByteOrder.LITTLE_ENDIAN)).isNotEqualTo(Long.MIN_VALUE);
		assertThat(ByteArrays.toLong(ByteArrays.of(Long.MIN_VALUE, ByteOrder.LITTLE_ENDIAN), ByteOrder.BIG_ENDIAN)).isNotEqualTo(Long.MIN_VALUE);

		assertThat(ByteArrays.toLong(ByteArrays.of(Long.MAX_VALUE, ByteOrder.BIG_ENDIAN), ByteOrder.BIG_ENDIAN)).isEqualTo(Long.MAX_VALUE);
		assertThat(ByteArrays.toLong(ByteArrays.of(Long.MAX_VALUE, ByteOrder.LITTLE_ENDIAN), ByteOrder.LITTLE_ENDIAN)).isEqualTo(Long.MAX_VALUE);
		assertThat(ByteArrays.toLong(ByteArrays.of(Long.MAX_VALUE, ByteOrder.BIG_ENDIAN), ByteOrder.LITTLE_ENDIAN)).isNotEqualTo(Long.MAX_VALUE);
		assertThat(ByteArrays.toLong(ByteArrays.of(Long.MAX_VALUE, ByteOrder.LITTLE_ENDIAN), ByteOrder.BIG_ENDIAN)).isNotEqualTo(Long.MAX_VALUE);
	}

	@Test
	@SuppressWarnings("deprecation")
	void testOfLongInvalidLegacy() {
		assertThatNullPointerException().isThrownBy(() -> ByteArrays.ofLong(0L, null));
	}

	@Test
	void testOfLongInvalid() {
		assertThatNullPointerException().isThrownBy(() -> ByteArrays.of(0L, null));
	}

	@Test
	void testToLongInvalid() {
		assertThatNullPointerException().isThrownBy(() -> ByteArrays.toLong(null));
		assertThatIllegalArgumentException().isThrownBy(() -> ByteArrays.toLong(ByteArrays.EMPTY));
		assertThatNullPointerException().isThrownBy(() -> ByteArrays.toLong(ByteArrays.of(0L), null));
	}

	@Test
	@SuppressWarnings("deprecation")
	void testOfFloatAndToFloatLegacy() {
		assertThat(ByteArrays.toFloat(ByteArrays.ofFloat(0.0f))).isZero();
		assertThat(ByteArrays.toFloat(ByteArrays.ofFloat(0.0f, ByteOrder.BIG_ENDIAN), ByteOrder.BIG_ENDIAN)).isZero();
		assertThat(ByteArrays.toFloat(ByteArrays.ofFloat(0.0f, ByteOrder.LITTLE_ENDIAN), ByteOrder.LITTLE_ENDIAN)).isZero();

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

		assertThat(ByteArrays.toFloat(ByteArrays.ofFloat(Float.NaN, ByteOrder.BIG_ENDIAN), ByteOrder.BIG_ENDIAN)).isNaN();
		assertThat(ByteArrays.toFloat(ByteArrays.ofFloat(Float.NaN, ByteOrder.LITTLE_ENDIAN), ByteOrder.LITTLE_ENDIAN)).isNaN();
		assertThat(ByteArrays.toFloat(ByteArrays.ofFloat(Float.NaN, ByteOrder.BIG_ENDIAN), ByteOrder.LITTLE_ENDIAN)).isNotNaN();
		assertThat(ByteArrays.toFloat(ByteArrays.ofFloat(Float.NaN, ByteOrder.LITTLE_ENDIAN), ByteOrder.BIG_ENDIAN)).isNotNaN();
	}

	@Test
	void testOfFloatAndToFloat() {
		assertThat(ByteArrays.toFloat(ByteArrays.of(0.0f))).isZero();
		assertThat(ByteArrays.toFloat(ByteArrays.of(0.0f, ByteOrder.BIG_ENDIAN), ByteOrder.BIG_ENDIAN)).isZero();
		assertThat(ByteArrays.toFloat(ByteArrays.of(0.0f, ByteOrder.LITTLE_ENDIAN), ByteOrder.LITTLE_ENDIAN)).isZero();

		assertThat(ByteArrays.toFloat(ByteArrays.of(-5.5f, ByteOrder.BIG_ENDIAN), ByteOrder.BIG_ENDIAN)).isEqualTo(-5.5f);
		assertThat(ByteArrays.toFloat(ByteArrays.of(-5.5f, ByteOrder.LITTLE_ENDIAN), ByteOrder.LITTLE_ENDIAN)).isEqualTo(-5.5f);
		assertThat(ByteArrays.toFloat(ByteArrays.of(-5.5f, ByteOrder.BIG_ENDIAN), ByteOrder.LITTLE_ENDIAN)).isNotEqualTo(-5.5f);
		assertThat(ByteArrays.toFloat(ByteArrays.of(-5.5f, ByteOrder.LITTLE_ENDIAN), ByteOrder.BIG_ENDIAN)).isNotEqualTo(-5.5f);

		assertThat(ByteArrays.toFloat(ByteArrays.of(5.5f, ByteOrder.BIG_ENDIAN), ByteOrder.BIG_ENDIAN)).isEqualTo(5.5f);
		assertThat(ByteArrays.toFloat(ByteArrays.of(5.5f, ByteOrder.LITTLE_ENDIAN), ByteOrder.LITTLE_ENDIAN)).isEqualTo(5.5f);
		assertThat(ByteArrays.toFloat(ByteArrays.of(5.5f, ByteOrder.BIG_ENDIAN), ByteOrder.LITTLE_ENDIAN)).isNotEqualTo(5.5f);
		assertThat(ByteArrays.toFloat(ByteArrays.of(5.5f, ByteOrder.LITTLE_ENDIAN), ByteOrder.BIG_ENDIAN)).isNotEqualTo(5.5f);

		assertThat(ByteArrays.toFloat(ByteArrays.of(Float.MIN_VALUE, ByteOrder.BIG_ENDIAN), ByteOrder.BIG_ENDIAN)).isEqualTo(Float.MIN_VALUE);
		assertThat(ByteArrays.toFloat(ByteArrays.of(Float.MIN_VALUE, ByteOrder.LITTLE_ENDIAN), ByteOrder.LITTLE_ENDIAN)).isEqualTo(Float.MIN_VALUE);
		assertThat(ByteArrays.toFloat(ByteArrays.of(Float.MIN_VALUE, ByteOrder.BIG_ENDIAN), ByteOrder.LITTLE_ENDIAN)).isNotEqualTo(Float.MIN_VALUE);
		assertThat(ByteArrays.toFloat(ByteArrays.of(Float.MIN_VALUE, ByteOrder.LITTLE_ENDIAN), ByteOrder.BIG_ENDIAN)).isNotEqualTo(Float.MIN_VALUE);

		assertThat(ByteArrays.toFloat(ByteArrays.of(Float.NEGATIVE_INFINITY, ByteOrder.BIG_ENDIAN), ByteOrder.BIG_ENDIAN)).isEqualTo(Float.NEGATIVE_INFINITY);
		assertThat(ByteArrays.toFloat(ByteArrays.of(Float.NEGATIVE_INFINITY, ByteOrder.LITTLE_ENDIAN), ByteOrder.LITTLE_ENDIAN)).isEqualTo(Float.NEGATIVE_INFINITY);
		assertThat(ByteArrays.toFloat(ByteArrays.of(Float.NEGATIVE_INFINITY, ByteOrder.BIG_ENDIAN), ByteOrder.LITTLE_ENDIAN)).isNotEqualTo(Float.NEGATIVE_INFINITY);
		assertThat(ByteArrays.toFloat(ByteArrays.of(Float.NEGATIVE_INFINITY, ByteOrder.LITTLE_ENDIAN), ByteOrder.BIG_ENDIAN)).isNotEqualTo(Float.NEGATIVE_INFINITY);

		assertThat(ByteArrays.toFloat(ByteArrays.of(Float.MIN_NORMAL, ByteOrder.BIG_ENDIAN), ByteOrder.BIG_ENDIAN)).isEqualTo(Float.MIN_NORMAL);
		assertThat(ByteArrays.toFloat(ByteArrays.of(Float.MIN_NORMAL, ByteOrder.LITTLE_ENDIAN), ByteOrder.LITTLE_ENDIAN)).isEqualTo(Float.MIN_NORMAL);
		assertThat(ByteArrays.toFloat(ByteArrays.of(Float.MIN_NORMAL, ByteOrder.BIG_ENDIAN), ByteOrder.LITTLE_ENDIAN)).isNotEqualTo(Float.MIN_NORMAL);
		assertThat(ByteArrays.toFloat(ByteArrays.of(Float.MIN_NORMAL, ByteOrder.LITTLE_ENDIAN), ByteOrder.BIG_ENDIAN)).isNotEqualTo(Float.MIN_NORMAL);

		assertThat(ByteArrays.toFloat(ByteArrays.of(Float.POSITIVE_INFINITY, ByteOrder.BIG_ENDIAN), ByteOrder.BIG_ENDIAN)).isEqualTo(Float.POSITIVE_INFINITY);
		assertThat(ByteArrays.toFloat(ByteArrays.of(Float.POSITIVE_INFINITY, ByteOrder.LITTLE_ENDIAN), ByteOrder.LITTLE_ENDIAN)).isEqualTo(Float.POSITIVE_INFINITY);
		assertThat(ByteArrays.toFloat(ByteArrays.of(Float.POSITIVE_INFINITY, ByteOrder.BIG_ENDIAN), ByteOrder.LITTLE_ENDIAN)).isNotEqualTo(Float.POSITIVE_INFINITY);
		assertThat(ByteArrays.toFloat(ByteArrays.of(Float.POSITIVE_INFINITY, ByteOrder.LITTLE_ENDIAN), ByteOrder.BIG_ENDIAN)).isNotEqualTo(Float.POSITIVE_INFINITY);

		assertThat(ByteArrays.toFloat(ByteArrays.of(Float.MAX_VALUE, ByteOrder.BIG_ENDIAN), ByteOrder.BIG_ENDIAN)).isEqualTo(Float.MAX_VALUE);
		assertThat(ByteArrays.toFloat(ByteArrays.of(Float.MAX_VALUE, ByteOrder.LITTLE_ENDIAN), ByteOrder.LITTLE_ENDIAN)).isEqualTo(Float.MAX_VALUE);
		assertThat(ByteArrays.toFloat(ByteArrays.of(Float.MAX_VALUE, ByteOrder.BIG_ENDIAN), ByteOrder.LITTLE_ENDIAN)).isNotEqualTo(Float.MAX_VALUE);
		assertThat(ByteArrays.toFloat(ByteArrays.of(Float.MAX_VALUE, ByteOrder.LITTLE_ENDIAN), ByteOrder.BIG_ENDIAN)).isNotEqualTo(Float.MAX_VALUE);

		assertThat(ByteArrays.toFloat(ByteArrays.of(Float.NaN, ByteOrder.BIG_ENDIAN), ByteOrder.BIG_ENDIAN)).isNaN();
		assertThat(ByteArrays.toFloat(ByteArrays.of(Float.NaN, ByteOrder.LITTLE_ENDIAN), ByteOrder.LITTLE_ENDIAN)).isNaN();
		assertThat(ByteArrays.toFloat(ByteArrays.of(Float.NaN, ByteOrder.BIG_ENDIAN), ByteOrder.LITTLE_ENDIAN)).isNotNaN();
		assertThat(ByteArrays.toFloat(ByteArrays.of(Float.NaN, ByteOrder.LITTLE_ENDIAN), ByteOrder.BIG_ENDIAN)).isNotNaN();
	}

	@Test
	@SuppressWarnings("deprecation")
	void testOfFloatInvalidLegacy() {
		assertThatNullPointerException().isThrownBy(() -> ByteArrays.ofFloat(0.0f, null));
	}

	@Test
	void testOfFloatInvalid() {
		assertThatNullPointerException().isThrownBy(() -> ByteArrays.of(0.0f, null));
	}

	@Test
	void testToFloatInvalid() {
		assertThatNullPointerException().isThrownBy(() -> ByteArrays.toFloat(null));
		assertThatIllegalArgumentException().isThrownBy(() -> ByteArrays.toFloat(ByteArrays.EMPTY));
		assertThatNullPointerException().isThrownBy(() -> ByteArrays.toFloat(ByteArrays.of(0.0f), null));
	}

	@Test
	@SuppressWarnings("deprecation")
	void testOfDoubleAndToDoubleLegacy() {
		assertThat(ByteArrays.toDouble(ByteArrays.ofDouble(0.0d))).isZero();
		assertThat(ByteArrays.toDouble(ByteArrays.ofDouble(0.0d, ByteOrder.BIG_ENDIAN), ByteOrder.BIG_ENDIAN)).isZero();
		assertThat(ByteArrays.toDouble(ByteArrays.ofDouble(0.0d, ByteOrder.LITTLE_ENDIAN), ByteOrder.LITTLE_ENDIAN)).isZero();

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

		assertThat(ByteArrays.toDouble(ByteArrays.ofDouble(Double.NaN, ByteOrder.BIG_ENDIAN), ByteOrder.BIG_ENDIAN)).isNaN();
		assertThat(ByteArrays.toDouble(ByteArrays.ofDouble(Double.NaN, ByteOrder.LITTLE_ENDIAN), ByteOrder.LITTLE_ENDIAN)).isNaN();
		assertThat(ByteArrays.toDouble(ByteArrays.ofDouble(Double.NaN, ByteOrder.BIG_ENDIAN), ByteOrder.LITTLE_ENDIAN)).isNotNaN();
		assertThat(ByteArrays.toDouble(ByteArrays.ofDouble(Double.NaN, ByteOrder.LITTLE_ENDIAN), ByteOrder.BIG_ENDIAN)).isNotNaN();
	}

	@Test
	void testOfDoubleAndToDouble() {
		assertThat(ByteArrays.toDouble(ByteArrays.of(0.0d))).isZero();
		assertThat(ByteArrays.toDouble(ByteArrays.of(0.0d, ByteOrder.BIG_ENDIAN), ByteOrder.BIG_ENDIAN)).isZero();
		assertThat(ByteArrays.toDouble(ByteArrays.of(0.0d, ByteOrder.LITTLE_ENDIAN), ByteOrder.LITTLE_ENDIAN)).isZero();

		assertThat(ByteArrays.toDouble(ByteArrays.of(-5.5d, ByteOrder.BIG_ENDIAN), ByteOrder.BIG_ENDIAN)).isEqualTo(-5.5d);
		assertThat(ByteArrays.toDouble(ByteArrays.of(-5.5d, ByteOrder.LITTLE_ENDIAN), ByteOrder.LITTLE_ENDIAN)).isEqualTo(-5.5d);
		assertThat(ByteArrays.toDouble(ByteArrays.of(-5.5d, ByteOrder.BIG_ENDIAN), ByteOrder.LITTLE_ENDIAN)).isNotEqualTo(-5.5d);
		assertThat(ByteArrays.toDouble(ByteArrays.of(-5.5d, ByteOrder.LITTLE_ENDIAN), ByteOrder.BIG_ENDIAN)).isNotEqualTo(-5.5d);

		assertThat(ByteArrays.toDouble(ByteArrays.of(5.5d, ByteOrder.BIG_ENDIAN), ByteOrder.BIG_ENDIAN)).isEqualTo(5.5d);
		assertThat(ByteArrays.toDouble(ByteArrays.of(5.5d, ByteOrder.LITTLE_ENDIAN), ByteOrder.LITTLE_ENDIAN)).isEqualTo(5.5d);
		assertThat(ByteArrays.toDouble(ByteArrays.of(5.5d, ByteOrder.BIG_ENDIAN), ByteOrder.LITTLE_ENDIAN)).isNotEqualTo(5.5d);
		assertThat(ByteArrays.toDouble(ByteArrays.of(5.5d, ByteOrder.LITTLE_ENDIAN), ByteOrder.BIG_ENDIAN)).isNotEqualTo(5.5d);

		assertThat(ByteArrays.toDouble(ByteArrays.of(Double.MIN_VALUE, ByteOrder.BIG_ENDIAN), ByteOrder.BIG_ENDIAN)).isEqualTo(Double.MIN_VALUE);
		assertThat(ByteArrays.toDouble(ByteArrays.of(Double.MIN_VALUE, ByteOrder.LITTLE_ENDIAN), ByteOrder.LITTLE_ENDIAN)).isEqualTo(Double.MIN_VALUE);
		assertThat(ByteArrays.toDouble(ByteArrays.of(Double.MIN_VALUE, ByteOrder.BIG_ENDIAN), ByteOrder.LITTLE_ENDIAN)).isNotEqualTo(Double.MIN_VALUE);
		assertThat(ByteArrays.toDouble(ByteArrays.of(Double.MIN_VALUE, ByteOrder.LITTLE_ENDIAN), ByteOrder.BIG_ENDIAN)).isNotEqualTo(Double.MIN_VALUE);

		assertThat(ByteArrays.toDouble(ByteArrays.of(Double.NEGATIVE_INFINITY, ByteOrder.BIG_ENDIAN), ByteOrder.BIG_ENDIAN)).isEqualTo(Double.NEGATIVE_INFINITY);
		assertThat(ByteArrays.toDouble(ByteArrays.of(Double.NEGATIVE_INFINITY, ByteOrder.LITTLE_ENDIAN), ByteOrder.LITTLE_ENDIAN)).isEqualTo(Double.NEGATIVE_INFINITY);
		assertThat(ByteArrays.toDouble(ByteArrays.of(Double.NEGATIVE_INFINITY, ByteOrder.BIG_ENDIAN), ByteOrder.LITTLE_ENDIAN)).isNotEqualTo(Double.NEGATIVE_INFINITY);
		assertThat(ByteArrays.toDouble(ByteArrays.of(Double.NEGATIVE_INFINITY, ByteOrder.LITTLE_ENDIAN), ByteOrder.BIG_ENDIAN)).isNotEqualTo(Double.NEGATIVE_INFINITY);

		assertThat(ByteArrays.toDouble(ByteArrays.of(Double.MIN_NORMAL, ByteOrder.BIG_ENDIAN), ByteOrder.BIG_ENDIAN)).isEqualTo(Double.MIN_NORMAL);
		assertThat(ByteArrays.toDouble(ByteArrays.of(Double.MIN_NORMAL, ByteOrder.LITTLE_ENDIAN), ByteOrder.LITTLE_ENDIAN)).isEqualTo(Double.MIN_NORMAL);
		assertThat(ByteArrays.toDouble(ByteArrays.of(Double.MIN_NORMAL, ByteOrder.BIG_ENDIAN), ByteOrder.LITTLE_ENDIAN)).isNotEqualTo(Double.MIN_NORMAL);
		assertThat(ByteArrays.toDouble(ByteArrays.of(Double.MIN_NORMAL, ByteOrder.LITTLE_ENDIAN), ByteOrder.BIG_ENDIAN)).isNotEqualTo(Double.MIN_NORMAL);

		assertThat(ByteArrays.toDouble(ByteArrays.of(Double.POSITIVE_INFINITY, ByteOrder.BIG_ENDIAN), ByteOrder.BIG_ENDIAN)).isEqualTo(Double.POSITIVE_INFINITY);
		assertThat(ByteArrays.toDouble(ByteArrays.of(Double.POSITIVE_INFINITY, ByteOrder.LITTLE_ENDIAN), ByteOrder.LITTLE_ENDIAN)).isEqualTo(Double.POSITIVE_INFINITY);
		assertThat(ByteArrays.toDouble(ByteArrays.of(Double.POSITIVE_INFINITY, ByteOrder.BIG_ENDIAN), ByteOrder.LITTLE_ENDIAN)).isNotEqualTo(Double.POSITIVE_INFINITY);
		assertThat(ByteArrays.toDouble(ByteArrays.of(Double.POSITIVE_INFINITY, ByteOrder.LITTLE_ENDIAN), ByteOrder.BIG_ENDIAN)).isNotEqualTo(Double.POSITIVE_INFINITY);

		assertThat(ByteArrays.toDouble(ByteArrays.of(Double.MAX_VALUE, ByteOrder.BIG_ENDIAN), ByteOrder.BIG_ENDIAN)).isEqualTo(Double.MAX_VALUE);
		assertThat(ByteArrays.toDouble(ByteArrays.of(Double.MAX_VALUE, ByteOrder.LITTLE_ENDIAN), ByteOrder.LITTLE_ENDIAN)).isEqualTo(Double.MAX_VALUE);
		assertThat(ByteArrays.toDouble(ByteArrays.of(Double.MAX_VALUE, ByteOrder.BIG_ENDIAN), ByteOrder.LITTLE_ENDIAN)).isNotEqualTo(Double.MAX_VALUE);
		assertThat(ByteArrays.toDouble(ByteArrays.of(Double.MAX_VALUE, ByteOrder.LITTLE_ENDIAN), ByteOrder.BIG_ENDIAN)).isNotEqualTo(Double.MAX_VALUE);

		assertThat(ByteArrays.toDouble(ByteArrays.of(Double.NaN, ByteOrder.BIG_ENDIAN), ByteOrder.BIG_ENDIAN)).isNaN();
		assertThat(ByteArrays.toDouble(ByteArrays.of(Double.NaN, ByteOrder.LITTLE_ENDIAN), ByteOrder.LITTLE_ENDIAN)).isNaN();
		assertThat(ByteArrays.toDouble(ByteArrays.of(Double.NaN, ByteOrder.BIG_ENDIAN), ByteOrder.LITTLE_ENDIAN)).isNotNaN();
		assertThat(ByteArrays.toDouble(ByteArrays.of(Double.NaN, ByteOrder.LITTLE_ENDIAN), ByteOrder.BIG_ENDIAN)).isNotNaN();
	}

	@Test
	@SuppressWarnings("deprecation")
	void testOfDoubleInvalidLegacy() {
		assertThatNullPointerException().isThrownBy(() -> ByteArrays.ofDouble(0.0d, null));
	}

	@Test
	void testOfDoubleInvalid() {
		assertThatNullPointerException().isThrownBy(() -> ByteArrays.of(0.0d, null));
	}

	@Test
	void testToDoubleInvalid() {
		assertThatNullPointerException().isThrownBy(() -> ByteArrays.toDouble(null));
		assertThatIllegalArgumentException().isThrownBy(() -> ByteArrays.toDouble(ByteArrays.EMPTY));
		assertThatNullPointerException().isThrownBy(() -> ByteArrays.toDouble(ByteArrays.of(0.0d), null));
	}

	@Test
	void testOfBinaryString() {
		assertThat(ByteArrays.ofBinaryString(Strings.EMPTY)).isEqualTo(ByteArrays.EMPTY);
		assertThat(ByteArrays.ofBinaryString(Strings.EMPTY, true)).isEqualTo(ByteArrays.EMPTY);
		assertThat(ByteArrays.ofBinaryString("00000000")).isEqualTo(ByteArrays.singleton((byte) 0b00000000));
		assertThat(ByteArrays.ofBinaryString("00000000", true)).isEqualTo(ByteArrays.singleton((byte) 0b00000000));
		assertThat(ByteArrays.ofBinaryString("11111111")).isEqualTo(ByteArrays.singleton((byte) 0b11111111));
		assertThat(ByteArrays.ofBinaryString("11111111", true)).isEqualTo(ByteArrays.singleton((byte) 0b11111111));
		assertThat(ByteArrays.ofBinaryString("0000111111110000")).isEqualTo(ByteArrays.of((byte) 0b00001111, (byte) 0b11110000));
		assertThat(ByteArrays.ofBinaryString("00001111 11110000", true)).isEqualTo(ByteArrays.of((byte) 0b00001111, (byte) 0b11110000));
		assertThat(ByteArrays.ofBinaryString("00001111111100001111000000001111")).isEqualTo(ByteArrays.of((byte) 0b00001111, (byte) 0b11110000, (byte) 0b11110000, (byte) 0b00001111));
		assertThat(ByteArrays.ofBinaryString("00001111 11110000 11110000 00001111", true)).isEqualTo(ByteArrays.of((byte) 0b00001111, (byte) 0b11110000, (byte) 0b11110000, (byte) 0b00001111));
	}

	@Test
	void testOfBinaryStringInvalid() {
		assertThatNullPointerException().isThrownBy(() -> ByteArrays.ofBinaryString(null));
		assertThatIllegalArgumentException().isThrownBy(() -> ByteArrays.ofBinaryString("0000000"));
		assertThatIllegalArgumentException().isThrownBy(() -> ByteArrays.ofBinaryString("0000000", true));
		assertThatIllegalArgumentException().isThrownBy(() -> ByteArrays.ofBinaryString("0000000!"));
		assertThatIllegalArgumentException().isThrownBy(() -> ByteArrays.ofBinaryString("0000000!", true));
		assertThatIllegalArgumentException().isThrownBy(() -> ByteArrays.ofBinaryString("0000000~"));
		assertThatIllegalArgumentException().isThrownBy(() -> ByteArrays.ofBinaryString("0000000~", true));
		assertThatIllegalArgumentException().isThrownBy(() -> ByteArrays.ofBinaryString("00000000?11111111"));
		assertThatIllegalArgumentException().isThrownBy(() -> ByteArrays.ofBinaryString("00000000?11111111", true));
	}

	@Test
	void testOfOctalString() {
		assertThat(ByteArrays.ofOctalString(Strings.EMPTY)).isEqualTo(ByteArrays.EMPTY);
		assertThat(ByteArrays.ofOctalString(Strings.EMPTY, true)).isEqualTo(ByteArrays.EMPTY);
		assertThat(ByteArrays.ofOctalString("000")).isEqualTo(ByteArrays.singleton((byte) 0000));
		assertThat(ByteArrays.ofOctalString("000", true)).isEqualTo(ByteArrays.singleton((byte) 0000));
		assertThat(ByteArrays.ofOctalString("377")).isEqualTo(ByteArrays.singleton((byte) 0377));
		assertThat(ByteArrays.ofOctalString("377", true)).isEqualTo(ByteArrays.singleton((byte) 0377));
		assertThat(ByteArrays.ofOctalString("017360")).isEqualTo(ByteArrays.of((byte) 0017, (byte) 0360));
		assertThat(ByteArrays.ofOctalString("017 360", true)).isEqualTo(ByteArrays.of((byte) 0017, (byte) 0360));
		assertThat(ByteArrays.ofOctalString("017360360017")).isEqualTo(ByteArrays.of((byte) 0017, (byte) 0360, (byte) 0360, (byte) 0017));
		assertThat(ByteArrays.ofOctalString("017 360 360 017", true)).isEqualTo(ByteArrays.of((byte) 0017, (byte) 0360, (byte) 0360, (byte) 0017));
	}

	@Test
	void testOfOctalStringInvalid() {
		assertThatNullPointerException().isThrownBy(() -> ByteArrays.ofOctalString(null));
		assertThatIllegalArgumentException().isThrownBy(() -> ByteArrays.ofOctalString("00"));
		assertThatIllegalArgumentException().isThrownBy(() -> ByteArrays.ofOctalString("00", true));
		assertThatIllegalArgumentException().isThrownBy(() -> ByteArrays.ofOctalString("00!"));
		assertThatIllegalArgumentException().isThrownBy(() -> ByteArrays.ofOctalString("00!", true));
		assertThatIllegalArgumentException().isThrownBy(() -> ByteArrays.ofOctalString("00~"));
		assertThatIllegalArgumentException().isThrownBy(() -> ByteArrays.ofOctalString("00~", true));
		assertThatIllegalArgumentException().isThrownBy(() -> ByteArrays.ofOctalString("000?377"));
		assertThatIllegalArgumentException().isThrownBy(() -> ByteArrays.ofOctalString("000?377", true));
	}

	@Test
	void testOfDecimalString() {
		assertThat(ByteArrays.ofDecimalString(Strings.EMPTY)).isEqualTo(ByteArrays.EMPTY);
		assertThat(ByteArrays.ofDecimalString(Strings.EMPTY, true)).isEqualTo(ByteArrays.EMPTY);
		assertThat(ByteArrays.ofDecimalString("000")).isEqualTo(ByteArrays.singleton((byte) 0));
		assertThat(ByteArrays.ofDecimalString("000", true)).isEqualTo(ByteArrays.singleton((byte) 0));
		assertThat(ByteArrays.ofDecimalString("255")).isEqualTo(ByteArrays.singleton((byte) 255));
		assertThat(ByteArrays.ofDecimalString("255", true)).isEqualTo(ByteArrays.singleton((byte) 255));
		assertThat(ByteArrays.ofDecimalString("015240")).isEqualTo(ByteArrays.of((byte) 15, (byte) 240));
		assertThat(ByteArrays.ofDecimalString("015 240", true)).isEqualTo(ByteArrays.of((byte) 15, (byte) 240));
		assertThat(ByteArrays.ofDecimalString("015240240015")).isEqualTo(ByteArrays.of((byte) 15, (byte) 240, (byte) 240, (byte) 15));
		assertThat(ByteArrays.ofDecimalString("015 240 240 015", true)).isEqualTo(ByteArrays.of((byte) 15, (byte) 240, (byte) 240, (byte) 15));
	}

	@Test
	void testOfDecimalStringInvalid() {
		assertThatNullPointerException().isThrownBy(() -> ByteArrays.ofDecimalString(null));
		assertThatIllegalArgumentException().isThrownBy(() -> ByteArrays.ofDecimalString("00"));
		assertThatIllegalArgumentException().isThrownBy(() -> ByteArrays.ofDecimalString("00", true));
		assertThatIllegalArgumentException().isThrownBy(() -> ByteArrays.ofDecimalString("00!"));
		assertThatIllegalArgumentException().isThrownBy(() -> ByteArrays.ofDecimalString("00!", true));
		assertThatIllegalArgumentException().isThrownBy(() -> ByteArrays.ofDecimalString("00~"));
		assertThatIllegalArgumentException().isThrownBy(() -> ByteArrays.ofDecimalString("00~", true));
		assertThatIllegalArgumentException().isThrownBy(() -> ByteArrays.ofDecimalString("000?255"));
		assertThatIllegalArgumentException().isThrownBy(() -> ByteArrays.ofDecimalString("000?255", true));
	}

	@Test
	void testOfHexadecimalString() {
		assertThat(ByteArrays.ofHexadecimalString(Strings.EMPTY)).isEqualTo(ByteArrays.EMPTY);
		assertThat(ByteArrays.ofHexadecimalString(Strings.EMPTY, true)).isEqualTo(ByteArrays.EMPTY);
		assertThat(ByteArrays.ofHexadecimalString("00")).isEqualTo(ByteArrays.singleton((byte) 0x00));
		assertThat(ByteArrays.ofHexadecimalString("00", true)).isEqualTo(ByteArrays.singleton((byte) 0x00));
		assertThat(ByteArrays.ofHexadecimalString("ff")).isEqualTo(ByteArrays.singleton((byte) 0xff));
		assertThat(ByteArrays.ofHexadecimalString("ff", true)).isEqualTo(ByteArrays.singleton((byte) 0xff));
		assertThat(ByteArrays.ofHexadecimalString("FF")).isEqualTo(ByteArrays.singleton((byte) 0xff));
		assertThat(ByteArrays.ofHexadecimalString("FF", true)).isEqualTo(ByteArrays.singleton((byte) 0xff));
		assertThat(ByteArrays.ofHexadecimalString("0ff0")).isEqualTo(ByteArrays.of((byte) 0x0f, (byte) 0xf0));
		assertThat(ByteArrays.ofHexadecimalString("0f f0", true)).isEqualTo(ByteArrays.of((byte) 0x0f, (byte) 0xf0));
		assertThat(ByteArrays.ofHexadecimalString("0FF0")).isEqualTo(ByteArrays.of((byte) 0x0f, (byte) 0xf0));
		assertThat(ByteArrays.ofHexadecimalString("0F F0", true)).isEqualTo(ByteArrays.of((byte) 0x0f, (byte) 0xf0));
		assertThat(ByteArrays.ofHexadecimalString("0ff0f00f")).isEqualTo(ByteArrays.of((byte) 0x0f, (byte) 0xf0, (byte) 0xf0, (byte) 0x0f));
		assertThat(ByteArrays.ofHexadecimalString("0f f0 f0 0f", true)).isEqualTo(ByteArrays.of((byte) 0x0f, (byte) 0xf0, (byte) 0xf0, (byte) 0x0f));
		assertThat(ByteArrays.ofHexadecimalString("0FF0F00F")).isEqualTo(ByteArrays.of((byte) 0x0f, (byte) 0xf0, (byte) 0xf0, (byte) 0x0f));
		assertThat(ByteArrays.ofHexadecimalString("0F F0 F0 0F", true)).isEqualTo(ByteArrays.of((byte) 0x0f, (byte) 0xf0, (byte) 0xf0, (byte) 0x0f));
	}

	@Test
	void testOfHexadecimalStringInvalid() {
		assertThatNullPointerException().isThrownBy(() -> ByteArrays.ofHexadecimalString(null));
		assertThatIllegalArgumentException().isThrownBy(() -> ByteArrays.ofHexadecimalString("0"));
		assertThatIllegalArgumentException().isThrownBy(() -> ByteArrays.ofHexadecimalString("0", true));
		assertThatIllegalArgumentException().isThrownBy(() -> ByteArrays.ofHexadecimalString("0!"));
		assertThatIllegalArgumentException().isThrownBy(() -> ByteArrays.ofHexadecimalString("0!", true));
		assertThatIllegalArgumentException().isThrownBy(() -> ByteArrays.ofHexadecimalString("0~"));
		assertThatIllegalArgumentException().isThrownBy(() -> ByteArrays.ofHexadecimalString("0~", true));
		assertThatIllegalArgumentException().isThrownBy(() -> ByteArrays.ofHexadecimalString("00?ff"));
		assertThatIllegalArgumentException().isThrownBy(() -> ByteArrays.ofHexadecimalString("00?ff", true));
		assertThatIllegalArgumentException().isThrownBy(() -> ByteArrays.ofHexadecimalString("00?FF"));
		assertThatIllegalArgumentException().isThrownBy(() -> ByteArrays.ofHexadecimalString("00?FF", true));
	}

	@Test
	void testToBinaryString() {
		assertThat(ByteArrays.toBinaryString(ByteArrays.EMPTY)).isEmpty();
		assertThat(ByteArrays.toBinaryString(ByteArrays.EMPTY, true)).isEmpty();
		assertThat(ByteArrays.toBinaryString(ByteArrays.singleton((byte) 0b00000000))).isEqualTo("00000000");
		assertThat(ByteArrays.toBinaryString(ByteArrays.singleton((byte) 0b00000000), true)).isEqualTo("00000000");
		assertThat(ByteArrays.toBinaryString(ByteArrays.singleton((byte) 0b11111111))).isEqualTo("11111111");
		assertThat(ByteArrays.toBinaryString(ByteArrays.singleton((byte) 0b11111111), true)).isEqualTo("11111111");
		assertThat(ByteArrays.toBinaryString(ByteArrays.of((byte) 0b00001111, (byte) 0b11110000))).isEqualTo("0000111111110000");
		assertThat(ByteArrays.toBinaryString(ByteArrays.of((byte) 0b00001111, (byte) 0b11110000), true)).isEqualTo("00001111 11110000");
		assertThat(ByteArrays.toBinaryString(ByteArrays.of((byte) 0b00001111, (byte) 0b11110000, (byte) 0b11110000, (byte) 0b00001111))).isEqualTo("00001111111100001111000000001111");
		assertThat(ByteArrays.toBinaryString(ByteArrays.of((byte) 0b00001111, (byte) 0b11110000, (byte) 0b11110000, (byte) 0b00001111), true)).isEqualTo("00001111 11110000 11110000 00001111");
	}

	@Test
	void testToBinaryStringInvalid() {
		assertThatNullPointerException().isThrownBy(() -> ByteArrays.toBinaryString(null));
	}

	@Test
	void testToOctalString() {
		assertThat(ByteArrays.toOctalString(ByteArrays.EMPTY)).isEmpty();
		assertThat(ByteArrays.toOctalString(ByteArrays.EMPTY, true)).isEmpty();
		assertThat(ByteArrays.toOctalString(ByteArrays.singleton((byte) 0000))).isEqualTo("000");
		assertThat(ByteArrays.toOctalString(ByteArrays.singleton((byte) 0000), true)).isEqualTo("000");
		assertThat(ByteArrays.toOctalString(ByteArrays.singleton((byte) 0377))).isEqualTo("377");
		assertThat(ByteArrays.toOctalString(ByteArrays.singleton((byte) 0377), true)).isEqualTo("377");
		assertThat(ByteArrays.toOctalString(ByteArrays.of((byte) 0017, (byte) 0360))).isEqualTo("017360");
		assertThat(ByteArrays.toOctalString(ByteArrays.of((byte) 0017, (byte) 0360), true)).isEqualTo("017 360");
		assertThat(ByteArrays.toOctalString(ByteArrays.of((byte) 0017, (byte) 0360, (byte) 0360, (byte) 0017))).isEqualTo("017360360017");
		assertThat(ByteArrays.toOctalString(ByteArrays.of((byte) 0017, (byte) 0360, (byte) 0360, (byte) 0017), true)).isEqualTo("017 360 360 017");
	}

	@Test
	void testToOctalStringInvalid() {
		assertThatNullPointerException().isThrownBy(() -> ByteArrays.toOctalString(null));
	}

	@Test
	void testToDecimalString() {
		assertThat(ByteArrays.toDecimalString(ByteArrays.EMPTY)).isEmpty();
		assertThat(ByteArrays.toDecimalString(ByteArrays.EMPTY, true)).isEmpty();
		assertThat(ByteArrays.toDecimalString(ByteArrays.singleton((byte) 0))).isEqualTo("000");
		assertThat(ByteArrays.toDecimalString(ByteArrays.singleton((byte) 0), true)).isEqualTo("000");
		assertThat(ByteArrays.toDecimalString(ByteArrays.singleton((byte) 255))).isEqualTo("255");
		assertThat(ByteArrays.toDecimalString(ByteArrays.singleton((byte) 255), true)).isEqualTo("255");
		assertThat(ByteArrays.toDecimalString(ByteArrays.of((byte) 15, (byte) 240))).isEqualTo("015240");
		assertThat(ByteArrays.toDecimalString(ByteArrays.of((byte) 15, (byte) 240), true)).isEqualTo("015 240");
		assertThat(ByteArrays.toDecimalString(ByteArrays.of((byte) 15, (byte) 240, (byte) 240, (byte) 15))).isEqualTo("015240240015");
		assertThat(ByteArrays.toDecimalString(ByteArrays.of((byte) 15, (byte) 240, (byte) 240, (byte) 15), true)).isEqualTo("015 240 240 015");
	}

	@Test
	void testToDecimalStringInvalid() {
		assertThatNullPointerException().isThrownBy(() -> ByteArrays.toDecimalString(null));
	}

	@Test
	void testToHexadecimalString() {
		assertThat(ByteArrays.toHexadecimalString(ByteArrays.EMPTY)).isEmpty();
		assertThat(ByteArrays.toHexadecimalString(ByteArrays.EMPTY, true)).isEmpty();
		assertThat(ByteArrays.toHexadecimalString(ByteArrays.singleton((byte) 0x00))).isEqualTo("00");
		assertThat(ByteArrays.toHexadecimalString(ByteArrays.singleton((byte) 0x00), true)).isEqualTo("00");
		assertThat(ByteArrays.toHexadecimalString(ByteArrays.singleton((byte) 0xff))).isEqualTo("ff");
		assertThat(ByteArrays.toHexadecimalString(ByteArrays.singleton((byte) 0xff), true)).isEqualTo("ff");
		assertThat(ByteArrays.toHexadecimalString(ByteArrays.of((byte) 0x0f, (byte) 0xf0))).isEqualTo("0ff0");
		assertThat(ByteArrays.toHexadecimalString(ByteArrays.of((byte) 0x0f, (byte) 0xf0), true)).isEqualTo("0f f0");
		assertThat(ByteArrays.toHexadecimalString(ByteArrays.of((byte) 0x0f, (byte) 0xf0, (byte) 0xf0, (byte) 0x0f))).isEqualTo("0ff0f00f");
		assertThat(ByteArrays.toHexadecimalString(ByteArrays.of((byte) 0x0f, (byte) 0xf0, (byte) 0xf0, (byte) 0x0f), true)).isEqualTo("0f f0 f0 0f");
	}

	@Test
	void testToHexadecimalStringInvalid() {
		assertThatNullPointerException().isThrownBy(() -> ByteArrays.toHexadecimalString(null));
	}
}