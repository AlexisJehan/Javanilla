/*
MIT License

Copyright (c) 2018 Alexis Jehan

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
*/
package com.github.javanilla.lang.array;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
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
		assertThat(CharArrays.nullToEmpty(CharArrays.of((char) 1))).isNotEmpty();
	}

	@Test
	void testEmptyToNull() {
		assertThat(CharArrays.emptyToNull(CharArrays.EMPTY)).isNull();
		assertThat(CharArrays.emptyToNull(null)).isNull();
		assertThat(CharArrays.emptyToNull(CharArrays.of((char) 1))).isNotNull();
	}

	@Test
	void testIndexOf() {
		final var array = CharArrays.of((char) 1, (char) 2, (char) 3);
		assertThat(CharArrays.indexOf(array, (char) 1)).isEqualTo(0);
		assertThat(CharArrays.indexOf(array, (char) 2)).isEqualTo(1);
		assertThat(CharArrays.indexOf(array, (char) 3)).isEqualTo(2);
		assertThat(CharArrays.indexOf(array, (char) 1, 1)).isEqualTo(-1);
		assertThat(CharArrays.indexOf(array, (char) 2, 2)).isEqualTo(-1);
		assertThat(CharArrays.indexOf(array, (char) 4)).isEqualTo(-1);
	}

	@Test
	void testIndexOfNull() {
		assertThatNullPointerException().isThrownBy(() -> CharArrays.indexOf(null, (char) 0));
	}

	@Test
	void testIndexOfInvalidFromIndex() {
		assertThatExceptionOfType(IndexOutOfBoundsException.class).isThrownBy(() -> CharArrays.indexOf(CharArrays.of((char) 1), (char) 1, -1));
		assertThatExceptionOfType(IndexOutOfBoundsException.class).isThrownBy(() -> CharArrays.indexOf(CharArrays.of((char) 1), (char) 1,  1));
	}

	@Test
	void testLastIndexOf() {
		final var array = CharArrays.of((char) 1, (char) 2, (char) 3, (char) 1);
		assertThat(CharArrays.lastIndexOf(array, (char) 1)).isEqualTo(3);
		assertThat(CharArrays.lastIndexOf(array, (char) 2)).isEqualTo(1);
		assertThat(CharArrays.lastIndexOf(array, (char) 3)).isEqualTo(2);
		assertThat(CharArrays.lastIndexOf(array, (char) 1, 1)).isEqualTo( 3);
		assertThat(CharArrays.lastIndexOf(array, (char) 2, 2)).isEqualTo(-1);
		assertThat(CharArrays.lastIndexOf(array, (char) 3, 3)).isEqualTo(-1);
		assertThat(CharArrays.lastIndexOf(array, (char) 4)).isEqualTo(-1);
	}

	@Test
	void testLastIndexOfNull() {
		assertThatNullPointerException().isThrownBy(() -> CharArrays.lastIndexOf(null, (char) 0));
	}

	@Test
	void testLastIndexOfInvalidFromIndex() {
		assertThatExceptionOfType(IndexOutOfBoundsException.class).isThrownBy(() -> CharArrays.lastIndexOf(CharArrays.of((char) 1), (char) 1, -1));
		assertThatExceptionOfType(IndexOutOfBoundsException.class).isThrownBy(() -> CharArrays.lastIndexOf(CharArrays.of((char) 1), (char) 1,  1));
	}

	@Test
	void testContains() {
		assertThat(CharArrays.contains(CharArrays.EMPTY, (char) 1)).isFalse();
		final var array = CharArrays.of((char) 1, (char) 2, (char) 3);
		assertThat(CharArrays.contains(array, (char) 1)).isTrue();
		assertThat(CharArrays.contains(array, (char) 2)).isTrue();
		assertThat(CharArrays.contains(array, (char) 3)).isTrue();
		assertThat(CharArrays.contains(array, (char) 4)).isFalse();
	}

	@Test
	void testContainsNull() {
		assertThatNullPointerException().isThrownBy(() -> CharArrays.contains(null, (char) 0));
	}

	@Test
	void testContainsOnly() {
		assertThat(CharArrays.containsOnly(CharArrays.EMPTY, (char) 1)).isFalse();
		final var array1 = CharArrays.of((char) 1, (char) 1);
		assertThat(CharArrays.containsOnly(array1, (char) 1)).isTrue();
		assertThat(CharArrays.containsOnly(array1, (char) 2)).isFalse();
		final var array2 = CharArrays.of((char) 1, (char) 2);
		assertThat(CharArrays.containsOnly(array2, (char) 1)).isFalse();
		assertThat(CharArrays.containsOnly(array2, (char) 2)).isFalse();
	}

	@Test
	void testContainsOnlyNull() {
		assertThatNullPointerException().isThrownBy(() -> CharArrays.containsOnly(null, (char) 0));
	}

	@Test
	void testContainsAny() {
		assertThat(CharArrays.containsAny(CharArrays.EMPTY, (char) 1, (char) 2)).isFalse();
		final var array = CharArrays.of((char) 1, (char) 2, (char) 3);
		assertThat(CharArrays.containsAny(array)).isFalse();
		assertThat(CharArrays.containsAny(array, (char) 1, (char) 2)).isTrue();
		assertThat(CharArrays.containsAny(array, (char) 3, (char) 4)).isTrue();
		assertThat(CharArrays.containsAny(array, (char) 5, (char) 6)).isFalse();
	}

	@Test
	void testContainsAnyNull() {
		assertThatNullPointerException().isThrownBy(() -> CharArrays.containsAny(null, (char) 0));
		assertThatNullPointerException().isThrownBy(() -> CharArrays.containsAny(CharArrays.of((char) 1, (char) 2, (char) 3), (char[]) null));
	}

	@Test
	void testContainsAll() {
		assertThat(CharArrays.containsAll(CharArrays.EMPTY, (char) 1, (char) 2)).isFalse();
		final var array = CharArrays.of((char) 1, (char) 2, (char) 3);
		assertThat(CharArrays.containsAll(array)).isFalse();
		assertThat(CharArrays.containsAll(array, (char) 1, (char) 2, (char) 3)).isTrue();
		assertThat(CharArrays.containsAll(array, (char) 3, (char) 2, (char) 1)).isTrue();
		assertThat(CharArrays.containsAll(array, (char) 1, (char) 2)).isTrue();
		assertThat(CharArrays.containsAll(array, (char) 1, (char) 2, (char) 3, (char) 4)).isFalse();
	}

	@Test
	void testContainsAllNull() {
		assertThatNullPointerException().isThrownBy(() -> CharArrays.containsAll(null, (char) 0));
		assertThatNullPointerException().isThrownBy(() -> CharArrays.containsAll(CharArrays.of((char) 1, (char) 2, (char) 3), (char[]) null));
	}

	@Test
	void testConcat() {
		final var array = CharArrays.of((char) 1, (char) 2, (char) 3);
		assertThat(CharArrays.concat(array, array)).isEqualTo(CharArrays.of((char) 1, (char) 2, (char) 3, (char) 1, (char) 2, (char) 3));
	}

	@Test
	void testConcatOne() {
		final var array = CharArrays.of((char) 1, (char) 2, (char) 3);
		assertThat(CharArrays.concat(array)).isEqualTo(array);
	}

	@Test
	void testConcatNone() {
		assertThat(CharArrays.concat()).isEmpty();
	}

	@Test
	void testConcatNull() {
		assertThatNullPointerException().isThrownBy(() -> CharArrays.concat((char[][]) null));
		assertThatNullPointerException().isThrownBy(() -> CharArrays.concat((List<char[]>) null));
		assertThatNullPointerException().isThrownBy(() -> CharArrays.concat((char[]) null));
	}

	@Test
	void testJoin() {
		final var array = CharArrays.of((char) 1, (char) 2, (char) 3);
		assertThat(CharArrays.join(CharArrays.of((char) 0), array, array)).isEqualTo(CharArrays.of((char) 1, (char) 2, (char) 3, (char) 0, (char) 1, (char) 2, (char) 3));
	}

	@Test
	void testJoinEmptySeparator() {
		final var array = CharArrays.of((char) 1, (char) 2, (char) 3);
		assertThat(CharArrays.join(CharArrays.EMPTY, array, array)).isEqualTo(CharArrays.concat(array, array));
	}

	@Test
	void testJoinOne() {
		final var array = CharArrays.of((char) 1, (char) 2, (char) 3);
		assertThat(CharArrays.join(CharArrays.of((char) 0), array)).isEqualTo(array);
	}

	@Test
	void testJoinNone() {
		assertThat(CharArrays.join(CharArrays.of((char) 0))).isEmpty();
	}

	@Test
	void testJoinNull() {
		assertThatNullPointerException().isThrownBy(() -> CharArrays.join(null, CharArrays.EMPTY));
		assertThatNullPointerException().isThrownBy(() -> CharArrays.join(CharArrays.EMPTY, (char[][]) null));
		assertThatNullPointerException().isThrownBy(() -> CharArrays.join(CharArrays.EMPTY, (List<char[]>) null));
		assertThatNullPointerException().isThrownBy(() -> CharArrays.join(CharArrays.EMPTY, (char[]) null));
	}

	@Test
	void testOf() {
		assertThat(CharArrays.of()).isEmpty();
		assertThat(CharArrays.of((char) 1, (char) 2, (char) 3)).containsExactly((char) 1, (char) 2, (char) 3);
	}

	@Test
	void testOfNull() {
		assertThatNullPointerException().isThrownBy(() -> CharArrays.of((char[]) null));
	}
}