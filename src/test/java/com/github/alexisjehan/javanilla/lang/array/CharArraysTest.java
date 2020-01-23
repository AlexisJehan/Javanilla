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
 * <p>{@link CharArrays} unit tests.</p>
 */
final class CharArraysTest {

	private static final char[] VALUES = {'a', 'b'};

	@Test
	void testEmpty() {
		assertThat(CharArrays.EMPTY).isEmpty();
	}

	@Test
	void testNullToEmpty() {
		assertThat(CharArrays.nullToEmpty(null)).isEmpty();
		assertThat(CharArrays.nullToEmpty(CharArrays.EMPTY)).isEmpty();
		assertThat(CharArrays.nullToEmpty(CharArrays.of(VALUES))).containsExactly(VALUES);
	}

	@Test
	void testNullToDefault() {
		assertThat(CharArrays.nullToDefault(null, CharArrays.singleton('-'))).containsExactly('-');
		assertThat(CharArrays.nullToDefault(CharArrays.EMPTY, CharArrays.singleton('-'))).isEmpty();
		assertThat(CharArrays.nullToDefault(CharArrays.of(VALUES), CharArrays.singleton('-'))).containsExactly(VALUES);
	}

	@Test
	void testNullToDefaultInvalid() {
		assertThatNullPointerException().isThrownBy(() -> CharArrays.nullToDefault(CharArrays.of(VALUES), null));
	}

	@Test
	void testEmptyToNull() {
		assertThat(CharArrays.emptyToNull(null)).isNull();
		assertThat(CharArrays.emptyToNull(CharArrays.EMPTY)).isNull();
		assertThat(CharArrays.emptyToNull(CharArrays.of(VALUES))).containsExactly(VALUES);
	}

	@Test
	void testEmptyToDefault() {
		assertThat(CharArrays.emptyToDefault(null, CharArrays.singleton('-'))).isNull();
		assertThat(CharArrays.emptyToDefault(CharArrays.EMPTY, CharArrays.singleton('-'))).containsExactly('-');
		assertThat(CharArrays.emptyToDefault(CharArrays.of(VALUES), CharArrays.singleton('-'))).containsExactly(VALUES);
	}

	@Test
	void testEmptyToDefaultInvalid() {
		assertThatIllegalArgumentException().isThrownBy(() -> CharArrays.emptyToDefault(CharArrays.of(VALUES), CharArrays.EMPTY));
	}

	@Test
	void testIsEmpty() {
		assertThat(CharArrays.isEmpty(CharArrays.EMPTY)).isTrue();
		assertThat(CharArrays.isEmpty(CharArrays.of(VALUES))).isFalse();
	}

	@Test
	void testIsEmptyInvalid() {
		assertThatNullPointerException().isThrownBy(() -> CharArrays.isEmpty(null));
	}

	@Test
	void testContainsAny() {
		assertThat(CharArrays.containsAny(CharArrays.EMPTY, 'a')).isFalse();
		assertThat(CharArrays.containsAny(CharArrays.singleton('a'), 'a')).isTrue();
		assertThat(CharArrays.containsAny(CharArrays.singleton('a'), 'b')).isFalse();
		assertThat(CharArrays.containsAny(CharArrays.singleton('a'), 'a', 'b')).isTrue();
	}

	@Test
	void testContainsAnyInvalid() {
		assertThatNullPointerException().isThrownBy(() -> CharArrays.containsAny(null, 'a'));
		assertThatNullPointerException().isThrownBy(() -> CharArrays.containsAny(CharArrays.of(VALUES), (char[]) null));
		assertThatIllegalArgumentException().isThrownBy(() -> CharArrays.containsAny(CharArrays.of(VALUES)));
	}

	@Test
	void testContainsAll() {
		assertThat(CharArrays.containsAll(CharArrays.EMPTY, 'a')).isFalse();
		assertThat(CharArrays.containsAll(CharArrays.singleton('a'), 'a')).isTrue();
		assertThat(CharArrays.containsAll(CharArrays.singleton('a'), 'b')).isFalse();
		assertThat(CharArrays.containsAll(CharArrays.singleton('a'), 'a', 'b')).isFalse();
		assertThat(CharArrays.containsAll(CharArrays.of('a', 'b'), 'a')).isTrue();
		assertThat(CharArrays.containsAll(CharArrays.of('a', 'b'), 'b')).isTrue();
		assertThat(CharArrays.containsAll(CharArrays.of('a', 'b'), 'a', 'b')).isTrue();
	}

	@Test
	void testContainsAllInvalid() {
		assertThatNullPointerException().isThrownBy(() -> CharArrays.containsAll(null, 'a'));
		assertThatNullPointerException().isThrownBy(() -> CharArrays.containsAll(CharArrays.of(VALUES), (char[]) null));
		assertThatIllegalArgumentException().isThrownBy(() -> CharArrays.containsAll(CharArrays.of(VALUES)));
	}

	@Test
	void testContainsOnce() {
		assertThat(CharArrays.containsOnce(CharArrays.EMPTY, 'a')).isFalse();
		assertThat(CharArrays.containsOnce(CharArrays.singleton('a'), 'a')).isTrue();
		assertThat(CharArrays.containsOnce(CharArrays.singleton('a'), 'b')).isFalse();
		assertThat(CharArrays.containsOnce(CharArrays.singleton('a'), 'a', 'b')).isFalse();
		assertThat(CharArrays.containsOnce(CharArrays.of('a', 'a'), 'a')).isFalse();
		assertThat(CharArrays.containsOnce(CharArrays.of('a', 'a'), 'b')).isFalse();
		assertThat(CharArrays.containsOnce(CharArrays.of('a', 'a'), 'a', 'b')).isFalse();
	}

	@Test
	void testContainsOnceInvalid() {
		assertThatNullPointerException().isThrownBy(() -> CharArrays.containsOnce(null, 'a'));
		assertThatNullPointerException().isThrownBy(() -> CharArrays.containsOnce(CharArrays.of(VALUES), (char[]) null));
		assertThatIllegalArgumentException().isThrownBy(() -> CharArrays.containsOnce(CharArrays.of(VALUES)));
	}

	@Test
	void testContainsOnly() {
		assertThat(CharArrays.containsOnly(CharArrays.EMPTY, 'a')).isFalse();
		assertThat(CharArrays.containsOnly(CharArrays.singleton('a'), 'a')).isTrue();
		assertThat(CharArrays.containsOnly(CharArrays.singleton('a'), 'b')).isFalse();
		assertThat(CharArrays.containsOnly(CharArrays.singleton('a'), 'a', 'b')).isTrue();
		assertThat(CharArrays.containsOnly(CharArrays.of('a', 'b'), 'a')).isFalse();
		assertThat(CharArrays.containsOnly(CharArrays.of('a', 'b'), 'b')).isFalse();
		assertThat(CharArrays.containsOnly(CharArrays.of('a', 'b'), 'a', 'b')).isTrue();
	}

	@Test
	void testContainsOnlyInvalid() {
		assertThatNullPointerException().isThrownBy(() -> CharArrays.containsOnly(null, 'a'));
		assertThatNullPointerException().isThrownBy(() -> CharArrays.containsOnly(CharArrays.of(VALUES), (char[]) null));
		assertThatIllegalArgumentException().isThrownBy(() -> CharArrays.containsOnly(CharArrays.of(VALUES)));
	}

	@Test
	void testIndexOf() {
		assertThat(CharArrays.indexOf(CharArrays.EMPTY, 'a')).isEqualTo(-1);
		assertThat(CharArrays.indexOf(CharArrays.of('a', 'b', 'a'), 'a')).isEqualTo(0);
		assertThat(CharArrays.indexOf(CharArrays.of('a', 'b', 'a'), 'b')).isEqualTo(1);
		assertThat(CharArrays.indexOf(CharArrays.of('a', 'b', 'a'), 'a', 1)).isEqualTo(2);
		assertThat(CharArrays.indexOf(CharArrays.of('a', 'b', 'a'), 'b', 2)).isEqualTo(-1);
	}

	@Test
	void testIndexOfInvalid() {
		assertThatNullPointerException().isThrownBy(() -> CharArrays.indexOf(null, 'a'));
		assertThatIllegalArgumentException().isThrownBy(() -> CharArrays.indexOf(CharArrays.of(VALUES), 'a', -1));
		assertThatIllegalArgumentException().isThrownBy(() -> CharArrays.indexOf(CharArrays.of(VALUES), 'a', 2));
	}

	@Test
	void testLastIndexOf() {
		assertThat(CharArrays.lastIndexOf(CharArrays.EMPTY, 'a')).isEqualTo(-1);
		assertThat(CharArrays.lastIndexOf(CharArrays.of('a', 'b', 'a'), 'a')).isEqualTo(2);
		assertThat(CharArrays.lastIndexOf(CharArrays.of('a', 'b', 'a'), 'b')).isEqualTo(1);
		assertThat(CharArrays.lastIndexOf(CharArrays.of('a', 'b', 'a'), 'a', 1)).isEqualTo(2);
		assertThat(CharArrays.lastIndexOf(CharArrays.of('a', 'b', 'a'), 'b', 2)).isEqualTo(-1);
	}

	@Test
	void testLastIndexOfInvalid() {
		assertThatNullPointerException().isThrownBy(() -> CharArrays.lastIndexOf(null, 'a'));
		assertThatIllegalArgumentException().isThrownBy(() -> CharArrays.lastIndexOf(CharArrays.of(VALUES), 'a', -1));
		assertThatIllegalArgumentException().isThrownBy(() -> CharArrays.lastIndexOf(CharArrays.of(VALUES), 'a', 2));
	}

	@Test
	void testFrequency() {
		assertThat(CharArrays.frequency(CharArrays.EMPTY, 'a')).isEqualTo(0);
		assertThat(CharArrays.frequency(CharArrays.of('a', 'b', 'a'), 'a')).isEqualTo(2);
		assertThat(CharArrays.frequency(CharArrays.of('a', 'b', 'a'), 'b')).isEqualTo(1);
	}

	@Test
	void testFrequencyInvalid() {
		assertThatNullPointerException().isThrownBy(() -> CharArrays.frequency(null, 'a'));
	}

	@Test
	void testShuffle() {
		assertThat(CharArrays.singleton('a')).satisfies(array -> {
			CharArrays.shuffle(array);
			assertThat(array).containsExactly('a');
		});
		assertThat(CharArrays.of('a', 'b', 'a', 'b')).satisfies(array -> {
			CharArrays.shuffle(array);
			assertThat(array).containsExactlyInAnyOrder('a', 'b', 'a', 'b');
		});
	}

	@Test
	void testShuffleInvalid() {
		assertThatNullPointerException().isThrownBy(() -> CharArrays.shuffle(null));
	}

	@Test
	void testReverse() {
		assertThat(CharArrays.singleton('a')).satisfies(array -> {
			CharArrays.reverse(array);
			assertThat(array).containsExactly('a');
		});

		// Even
		assertThat(CharArrays.of('a', 'b', 'a', 'b')).satisfies(array -> {
			CharArrays.reverse(array);
			assertThat(array).containsExactly('b', 'a', 'b', 'a');
		});

		// Odd
		assertThat(CharArrays.of('a', 'b', 'b')).satisfies(array -> {
			CharArrays.reverse(array);
			assertThat(array).containsExactly('b', 'b', 'a');
		});
	}

	@Test
	void testReverseInvalid() {
		assertThatNullPointerException().isThrownBy(() -> CharArrays.reverse(null));
	}

	@Test
	void testReorder() {
		assertThat(CharArrays.singleton('a')).satisfies(array -> {
			CharArrays.reorder(array, 0);
			assertThat(array).containsExactly('a');
		});
		assertThat(CharArrays.of('a', 'b', 'a', 'b')).satisfies(array -> {
			CharArrays.reorder(array, 2, 0, 3, 1);
			assertThat(array).containsExactly('a', 'a', 'b', 'b');
		});
	}

	@Test
	void testReorderInvalid() {
		assertThatNullPointerException().isThrownBy(() -> CharArrays.reorder(null));
		assertThatNullPointerException().isThrownBy(() -> CharArrays.reorder(CharArrays.of(VALUES), (int[]) null));
		assertThatIllegalArgumentException().isThrownBy(() -> CharArrays.reorder(CharArrays.of(VALUES), IntArrays.singleton(0)));
		assertThatIllegalArgumentException().isThrownBy(() -> CharArrays.reorder(CharArrays.of(VALUES), IntArrays.of(0, 0)));
		assertThatIllegalArgumentException().isThrownBy(() -> CharArrays.reorder(CharArrays.of(VALUES), IntArrays.of(-1, 1)));
		assertThatIllegalArgumentException().isThrownBy(() -> CharArrays.reorder(CharArrays.of(VALUES), IntArrays.of(2, 1)));
		assertThatIllegalArgumentException().isThrownBy(() -> CharArrays.reorder(CharArrays.of(VALUES), IntArrays.of(0, -1)));
		assertThatIllegalArgumentException().isThrownBy(() -> CharArrays.reorder(CharArrays.of(VALUES), IntArrays.of(0, 2)));
	}

	@Test
	void testSwap() {
		assertThat(CharArrays.singleton('a')).satisfies(array -> {
			CharArrays.swap(array, 0, 0);
			assertThat(array).containsExactly('a');
		});
		assertThat(CharArrays.of('a', 'b', 'a', 'b')).satisfies(array -> {
			CharArrays.swap(array, 1, 2);
			assertThat(array).containsExactly('a', 'a', 'b', 'b');
		});
	}

	@Test
	void testSwapInvalid() {
		assertThatNullPointerException().isThrownBy(() -> CharArrays.swap(null, 0, 0));
		assertThatIllegalArgumentException().isThrownBy(() -> CharArrays.swap(CharArrays.of(VALUES), -1, 1));
		assertThatIllegalArgumentException().isThrownBy(() -> CharArrays.swap(CharArrays.of(VALUES), 2, 1));
		assertThatIllegalArgumentException().isThrownBy(() -> CharArrays.swap(CharArrays.of(VALUES), 0, -1));
		assertThatIllegalArgumentException().isThrownBy(() -> CharArrays.swap(CharArrays.of(VALUES), 0, 2));
	}

	@Test
	void testAdd() {
		assertThat(CharArrays.add(CharArrays.EMPTY, '-')).containsExactly('-');
		assertThat(CharArrays.add(CharArrays.of('a', 'b', 'c'), 0, '-')).containsExactly('-', 'a', 'b', 'c');
		assertThat(CharArrays.add(CharArrays.of('a', 'b', 'c'), 1, '-')).containsExactly('a', '-', 'b', 'c');
		assertThat(CharArrays.add(CharArrays.of('a', 'b', 'c'), 2, '-')).containsExactly('a', 'b', '-', 'c');
		assertThat(CharArrays.add(CharArrays.of('a', 'b', 'c'), 3, '-')).containsExactly('a', 'b', 'c', '-');
		assertThat(CharArrays.add(CharArrays.of('a', 'b', 'c'), '-')).containsExactly('a', 'b', 'c', '-');
	}

	@Test
	void testAddInvalid() {
		assertThatNullPointerException().isThrownBy(() -> CharArrays.add(null, '-'));
		assertThatNullPointerException().isThrownBy(() -> CharArrays.add(null, 0, '-'));
		assertThatIllegalArgumentException().isThrownBy(() -> CharArrays.add(CharArrays.of(VALUES), -1, '-'));
		assertThatIllegalArgumentException().isThrownBy(() -> CharArrays.add(CharArrays.of(VALUES), 3, '-'));
	}

	@Test
	void testRemove() {
		assertThat(CharArrays.remove(CharArrays.singleton('a'), 0)).isEmpty();
		assertThat(CharArrays.remove(CharArrays.of('a', 'b', 'c'), 0)).containsExactly('b', 'c');
		assertThat(CharArrays.remove(CharArrays.of('a', 'b', 'c'), 1)).containsExactly('a', 'c');
		assertThat(CharArrays.remove(CharArrays.of('a', 'b', 'c'), 2)).containsExactly('a', 'b');
	}

	@Test
	void testRemoveInvalid() {
		assertThatNullPointerException().isThrownBy(() -> CharArrays.remove(null, 0));
		assertThatIllegalArgumentException().isThrownBy(() -> CharArrays.remove(CharArrays.EMPTY, 0));
		assertThatIllegalArgumentException().isThrownBy(() -> CharArrays.remove(CharArrays.of(VALUES), -1));
		assertThatIllegalArgumentException().isThrownBy(() -> CharArrays.remove(CharArrays.of(VALUES), 2));
	}

	@Test
	void testConcat() {
		assertThat(CharArrays.concat()).isEmpty();
		assertThat(CharArrays.concat(CharArrays.singleton(VALUES[0]))).containsExactly(VALUES[0]);
		assertThat(CharArrays.concat(CharArrays.singleton(VALUES[0]), CharArrays.singleton(VALUES[1]))).containsExactly(VALUES);
	}

	@Test
	void testConcatInvalid() {
		assertThatNullPointerException().isThrownBy(() -> CharArrays.concat((char[][]) null));
		assertThatNullPointerException().isThrownBy(() -> CharArrays.concat((List<char[]>) null));
		assertThatNullPointerException().isThrownBy(() -> CharArrays.concat((char[]) null));
	}

	@Test
	void testJoin() {
		assertThat(CharArrays.join(CharArrays.EMPTY, CharArrays.singleton(VALUES[0]), CharArrays.singleton(VALUES[1]))).containsExactly(VALUES);
		assertThat(CharArrays.join(CharArrays.singleton('-'))).isEmpty();
		assertThat(CharArrays.join(CharArrays.singleton('-'), CharArrays.singleton(VALUES[0]))).containsExactly(VALUES[0]);
		assertThat(CharArrays.join(CharArrays.singleton('-'), CharArrays.singleton(VALUES[0]), CharArrays.singleton(VALUES[1]))).containsExactly(VALUES[0], '-', VALUES[1]);
	}

	@Test
	void testJoinInvalid() {
		assertThatNullPointerException().isThrownBy(() -> CharArrays.join(null, CharArrays.of(VALUES)));
		assertThatNullPointerException().isThrownBy(() -> CharArrays.join(CharArrays.of(VALUES), (char[][]) null));
		assertThatNullPointerException().isThrownBy(() -> CharArrays.join(CharArrays.of(VALUES), (List<char[]>) null));
		assertThatNullPointerException().isThrownBy(() -> CharArrays.join(CharArrays.of(VALUES), (char[]) null));
	}

	@Test
	void testSingleton() {
		assertThat(CharArrays.singleton(VALUES[0])).containsExactly(VALUES[0]);
	}

	@Test
	void testOf() {
		assertThat(CharArrays.of()).isEmpty();
		assertThat(CharArrays.of(VALUES)).containsExactly(VALUES);
	}

	@Test
	void testOfInvalid() {
		assertThatNullPointerException().isThrownBy(() -> CharArrays.of((char[]) null));
	}

	@Test
	void testOfBoxedToBoxed() {
		assertThat(CharArrays.of(CharArrays.toBoxed(CharArrays.EMPTY))).isEmpty();
		assertThat(CharArrays.of(CharArrays.toBoxed(CharArrays.of(VALUES)))).containsExactly(VALUES);
		assertThat(CharArrays.toBoxed(CharArrays.of(VALUES))).isInstanceOf(Character[].class);
		assertThat(CharArrays.of(CharArrays.toBoxed(CharArrays.of(VALUES)))).isInstanceOf(char[].class);
	}

	@Test
	void testOfBoxedInvalid() {
		assertThatNullPointerException().isThrownBy(() -> CharArrays.of((Character[]) null));
	}

	@Test
	void testToBoxedInvalid() {
		assertThatNullPointerException().isThrownBy(() -> CharArrays.toBoxed(null));
	}
}