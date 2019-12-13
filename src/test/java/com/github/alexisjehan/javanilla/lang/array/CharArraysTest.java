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

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.assertj.core.api.Assertions.assertThatNullPointerException;

/**
 * <p>{@link CharArrays} unit tests.</p>
 */
final class CharArraysTest {

	@Test
	void testEmpty() {
		assertThat(CharArrays.EMPTY).isEmpty();
	}

	@Test
	void testNullToEmpty() {
		assertThat(CharArrays.nullToEmpty(null)).isEmpty();
		assertThat(CharArrays.nullToEmpty(CharArrays.EMPTY)).isEmpty();
		assertThat(CharArrays.nullToEmpty(CharArrays.singleton('a'))).containsExactly('a');
	}

	@Test
	void testNullToDefault() {
		assertThat(CharArrays.nullToDefault(null, CharArrays.singleton('-'))).containsExactly('-');
		assertThat(CharArrays.nullToDefault(CharArrays.EMPTY, CharArrays.singleton('-'))).isEmpty();
		assertThat(CharArrays.nullToDefault(CharArrays.singleton('a'), CharArrays.singleton('-'))).containsExactly('a');
	}

	@Test
	void testNullToDefaultInvalid() {
		assertThatNullPointerException().isThrownBy(() -> CharArrays.nullToDefault(CharArrays.singleton('a'), null));
	}

	@Test
	void testEmptyToNull() {
		assertThat(CharArrays.emptyToNull(null)).isNull();
		assertThat(CharArrays.emptyToNull(CharArrays.EMPTY)).isNull();
		assertThat(CharArrays.emptyToNull(CharArrays.singleton('a'))).containsExactly('a');
	}

	@Test
	void testEmptyToDefault() {
		assertThat(CharArrays.emptyToDefault(null, CharArrays.singleton('-'))).isNull();
		assertThat(CharArrays.emptyToDefault(CharArrays.EMPTY, CharArrays.singleton('-'))).containsExactly('-');
		assertThat(CharArrays.emptyToDefault(CharArrays.singleton('a'), CharArrays.singleton('-'))).containsExactly('a');
	}

	@Test
	void testEmptyToDefaultInvalid() {
		assertThatIllegalArgumentException().isThrownBy(() -> CharArrays.emptyToDefault(CharArrays.singleton('a'), CharArrays.EMPTY));
	}

	@Test
	void testIsEmpty() {
		assertThat(CharArrays.isEmpty(CharArrays.EMPTY)).isTrue();
		assertThat(CharArrays.isEmpty(CharArrays.singleton('a'))).isFalse();
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
		assertThatNullPointerException().isThrownBy(() -> CharArrays.containsAny(CharArrays.singleton('a'), (char[]) null));
		assertThatIllegalArgumentException().isThrownBy(() -> CharArrays.containsAny(CharArrays.singleton('a')));
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
		assertThatNullPointerException().isThrownBy(() -> CharArrays.containsAll(CharArrays.singleton('a'), (char[]) null));
		assertThatIllegalArgumentException().isThrownBy(() -> CharArrays.containsAll(CharArrays.singleton('a')));
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
		assertThatNullPointerException().isThrownBy(() -> CharArrays.containsOnce(CharArrays.singleton('a'), (char[]) null));
		assertThatIllegalArgumentException().isThrownBy(() -> CharArrays.containsOnce(CharArrays.singleton('a')));
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
		assertThatNullPointerException().isThrownBy(() -> CharArrays.containsOnly(CharArrays.singleton('a'), (char[]) null));
		assertThatIllegalArgumentException().isThrownBy(() -> CharArrays.containsOnly(CharArrays.singleton('a')));
	}

	@Test
	void testIndexOf() {
		assertThat(CharArrays.indexOf(CharArrays.EMPTY, 'a')).isEqualTo(-1);
		final var array = CharArrays.of('a', 'b', 'a');
		assertThat(CharArrays.indexOf(array, 'a')).isEqualTo(0);
		assertThat(CharArrays.indexOf(array, 'b')).isEqualTo(1);
		assertThat(CharArrays.indexOf(array, 'a', 1)).isEqualTo(2);
		assertThat(CharArrays.indexOf(array, 'b', 2)).isEqualTo(-1);
	}

	@Test
	void testIndexOfInvalid() {
		assertThatNullPointerException().isThrownBy(() -> CharArrays.indexOf(null, 'a'));
		assertThatIllegalArgumentException().isThrownBy(() -> CharArrays.indexOf(CharArrays.singleton('a'), 'a', -1));
		assertThatIllegalArgumentException().isThrownBy(() -> CharArrays.indexOf(CharArrays.singleton('a'), 'a', 1));
	}

	@Test
	void testLastIndexOf() {
		assertThat(CharArrays.lastIndexOf(CharArrays.EMPTY, 'a')).isEqualTo(-1);
		final var array = CharArrays.of('a', 'b', 'a');
		assertThat(CharArrays.lastIndexOf(array, 'a')).isEqualTo(2);
		assertThat(CharArrays.lastIndexOf(array, 'b')).isEqualTo(1);
		assertThat(CharArrays.lastIndexOf(array, 'a', 1)).isEqualTo(2);
		assertThat(CharArrays.lastIndexOf(array, 'b', 2)).isEqualTo(-1);
	}

	@Test
	void testLastIndexOfInvalid() {
		assertThatNullPointerException().isThrownBy(() -> CharArrays.lastIndexOf(null, 'a'));
		assertThatIllegalArgumentException().isThrownBy(() -> CharArrays.lastIndexOf(CharArrays.singleton('a'), 'a', -1));
		assertThatIllegalArgumentException().isThrownBy(() -> CharArrays.lastIndexOf(CharArrays.singleton('a'), 'a', 1));
	}

	@Test
	void testFrequency() {
		assertThat(CharArrays.frequency(CharArrays.EMPTY, 'a')).isEqualTo(0);
		final var array = CharArrays.of('a', 'b', 'a');
		assertThat(CharArrays.frequency(array, 'a')).isEqualTo(2);
		assertThat(CharArrays.frequency(array, 'b')).isEqualTo(1);
	}

	@Test
	void testFrequencyInvalid() {
		assertThatNullPointerException().isThrownBy(() -> CharArrays.frequency(null, 'a'));
	}

	@Test
	void testShuffle() {
		{
			final var array = CharArrays.singleton('a');
			CharArrays.shuffle(array);
			assertThat(array).containsExactly('a');
		}
		{
			final var array = CharArrays.of('a', 'b', 'a', 'b');
			CharArrays.shuffle(array);
			assertThat(array).containsExactlyInAnyOrder('a', 'b', 'a', 'b');
		}
	}

	@Test
	void testShuffleInvalid() {
		assertThatNullPointerException().isThrownBy(() -> CharArrays.shuffle(null));
	}

	@Test
	void testReverse() {
		{
			final var array = CharArrays.singleton('a');
			CharArrays.reverse(array);
			assertThat(array).containsExactly('a');
		}
		{
			// Even
			final var array = CharArrays.of('a', 'b', 'a', 'b');
			CharArrays.reverse(array);
			assertThat(array).containsExactly('b', 'a', 'b', 'a');
		}
		{
			// Odd
			final var array = CharArrays.of('a', 'b', 'b');
			CharArrays.reverse(array);
			assertThat(array).containsExactly('b', 'b', 'a');
		}
	}

	@Test
	void testReverseInvalid() {
		assertThatNullPointerException().isThrownBy(() -> CharArrays.reverse(null));
	}

	@Test
	void testReorder() {
		{
			final var array = CharArrays.singleton('a');
			CharArrays.reorder(array, 0);
			assertThat(array).containsExactly('a');
		}
		{
			final var array = CharArrays.of('a', 'b', 'a', 'b');
			CharArrays.reorder(array, 2, 0, 3, 1);
			assertThat(array).containsExactly('a', 'a', 'b', 'b');
		}
	}

	@Test
	void testReorderInvalid() {
		assertThatNullPointerException().isThrownBy(() -> CharArrays.reorder(null));
		assertThatNullPointerException().isThrownBy(() -> CharArrays.reorder(CharArrays.singleton('a'), (int[]) null));
		assertThatIllegalArgumentException().isThrownBy(() -> CharArrays.reorder(CharArrays.of('a', 'b'), IntArrays.singleton(0)));
		assertThatIllegalArgumentException().isThrownBy(() -> CharArrays.reorder(CharArrays.of('a', 'b'), IntArrays.of(0, 0)));
		assertThatIllegalArgumentException().isThrownBy(() -> CharArrays.reorder(CharArrays.of('a', 'b'), IntArrays.of(-1, 1)));
		assertThatIllegalArgumentException().isThrownBy(() -> CharArrays.reorder(CharArrays.of('a', 'b'), IntArrays.of(2, 1)));
		assertThatIllegalArgumentException().isThrownBy(() -> CharArrays.reorder(CharArrays.of('a', 'b'), IntArrays.of(0, -1)));
		assertThatIllegalArgumentException().isThrownBy(() -> CharArrays.reorder(CharArrays.of('a', 'b'), IntArrays.of(0, 2)));
	}

	@Test
	void testSwap() {
		{
			final var array = CharArrays.singleton('a');
			CharArrays.swap(array, 0, 0);
			assertThat(array).containsExactly('a');
		}
		{
			final var array = CharArrays.of('a', 'b', 'a', 'b');
			CharArrays.swap(array, 1, 2);
			assertThat(array).containsExactly('a', 'a', 'b', 'b');
		}
	}

	@Test
	void testSwapInvalid() {
		assertThatNullPointerException().isThrownBy(() -> CharArrays.swap(null, 0, 0));
		assertThatIllegalArgumentException().isThrownBy(() -> CharArrays.swap(CharArrays.of('a', 'b'), -1, 1));
		assertThatIllegalArgumentException().isThrownBy(() -> CharArrays.swap(CharArrays.of('a', 'b'), 2, 1));
		assertThatIllegalArgumentException().isThrownBy(() -> CharArrays.swap(CharArrays.of('a', 'b'), 0, -1));
		assertThatIllegalArgumentException().isThrownBy(() -> CharArrays.swap(CharArrays.of('a', 'b'), 0, 2));
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
		assertThatIllegalArgumentException().isThrownBy(() -> CharArrays.add(CharArrays.singleton('a'), -1, '-'));
		assertThatIllegalArgumentException().isThrownBy(() -> CharArrays.add(CharArrays.singleton('a'), 2, '-'));
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
		assertThatIllegalArgumentException().isThrownBy(() -> CharArrays.remove(CharArrays.singleton('a'), -1));
		assertThatIllegalArgumentException().isThrownBy(() -> CharArrays.remove(CharArrays.singleton('a'), 1));
	}

	@Test
	void testConcat() {
		assertThat(CharArrays.concat()).isEmpty();
		assertThat(CharArrays.concat(CharArrays.singleton('a'))).containsExactly('a');
		assertThat(CharArrays.concat(CharArrays.singleton('a'), CharArrays.singleton('b'))).containsExactly('a', 'b');
	}

	@Test
	void testConcatInvalid() {
		assertThatNullPointerException().isThrownBy(() -> CharArrays.concat((char[][]) null));
		assertThatNullPointerException().isThrownBy(() -> CharArrays.concat((List<char[]>) null));
		assertThatNullPointerException().isThrownBy(() -> CharArrays.concat((char[]) null));
	}

	@Test
	void testJoin() {
		assertThat(CharArrays.join(CharArrays.EMPTY, CharArrays.singleton('a'), CharArrays.singleton('b'))).containsExactly('a', 'b');
		assertThat(CharArrays.join(CharArrays.singleton('-'))).isEmpty();
		assertThat(CharArrays.join(CharArrays.singleton('-'), CharArrays.singleton('a'))).containsExactly('a');
		assertThat(CharArrays.join(CharArrays.singleton('-'), CharArrays.singleton('a'), CharArrays.singleton('b'))).containsExactly('a', '-', 'b');
	}

	@Test
	void testJoinInvalid() {
		assertThatNullPointerException().isThrownBy(() -> CharArrays.join(null, CharArrays.singleton('a')));
		assertThatNullPointerException().isThrownBy(() -> CharArrays.join(CharArrays.singleton('-'), (char[][]) null));
		assertThatNullPointerException().isThrownBy(() -> CharArrays.join(CharArrays.singleton('-'), (List<char[]>) null));
		assertThatNullPointerException().isThrownBy(() -> CharArrays.join(CharArrays.singleton('-'), (char[]) null));
	}

	@Test
	void testSingleton() {
		assertThat(CharArrays.singleton('a')).containsExactly('a');
	}

	@Test
	void testOf() {
		assertThat(CharArrays.of()).isEmpty();
		assertThat(CharArrays.of('a', 'b')).containsExactly('a', 'b');
	}

	@Test
	void testOfInvalid() {
		assertThatNullPointerException().isThrownBy(() -> CharArrays.of((char[]) null));
	}

	@Test
	void testOfBoxedToBoxed() {
		assertThat(CharArrays.of(CharArrays.toBoxed(CharArrays.EMPTY))).isEmpty();
		assertThat(CharArrays.of(CharArrays.toBoxed(CharArrays.of('a', 'b')))).containsExactly('a', 'b');
		assertThat(CharArrays.toBoxed(CharArrays.singleton('a'))).isInstanceOf(Character[].class);
		assertThat(CharArrays.of(CharArrays.toBoxed(CharArrays.singleton('a')))).isInstanceOf(char[].class);
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