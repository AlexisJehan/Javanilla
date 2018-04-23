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

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNullPointerException;

/**
 * <p>{@link ObjectArrays} unit tests.</p>
 */
final class ObjectArraysTest {

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
		assertThat(ObjectArrays.nullToEmpty(Integer.class, ObjectArrays.of(1))).isNotEmpty();
	}

	@Test
	void testEmptyToNull() {
		assertThat(ObjectArrays.emptyToNull(ObjectArrays.empty(Integer.class))).isNull();
		assertThat(ObjectArrays.emptyToNull(null)).isNull();
		assertThat(ObjectArrays.emptyToNull(ObjectArrays.of(1))).isNotNull();
	}

	@Test
	void testIndexOf() {
		final var array = ObjectArrays.of(1, 2000, null);
		assertThat(ObjectArrays.indexOf(array,    1)).isEqualTo(0);
		assertThat(ObjectArrays.indexOf(array, 2000)).isEqualTo(1);
		assertThat(ObjectArrays.indexOf(array, null)).isEqualTo(2);
		assertThat(ObjectArrays.indexOf(array,    1, 1)).isEqualTo(-1);
		assertThat(ObjectArrays.indexOf(array, 2000, 2)).isEqualTo(-1);
		assertThat(ObjectArrays.indexOf(array, 3)).isEqualTo(-1);
	}

	@Test
	void testIndexOfNull() {
		assertThatNullPointerException().isThrownBy(() -> ObjectArrays.indexOf(null, 0));
	}

	@Test
	void testIndexOfInvalidFromIndex() {
		assertThatExceptionOfType(IndexOutOfBoundsException.class).isThrownBy(() -> ObjectArrays.indexOf(ObjectArrays.of(1), 1, -1));
		assertThatExceptionOfType(IndexOutOfBoundsException.class).isThrownBy(() -> ObjectArrays.indexOf(ObjectArrays.of(1), 1,  1));
	}

	@Test
	void testLastIndexOf() {
		final var array = ObjectArrays.of(1, 2000, null, 1);
		assertThat(ObjectArrays.lastIndexOf(array,    1)).isEqualTo(3);
		assertThat(ObjectArrays.lastIndexOf(array, 2000)).isEqualTo(1);
		assertThat(ObjectArrays.lastIndexOf(array, null)).isEqualTo(2);
		assertThat(ObjectArrays.lastIndexOf(array,    1, 1)).isEqualTo( 3);
		assertThat(ObjectArrays.lastIndexOf(array, 2000, 2)).isEqualTo(-1);
		assertThat(ObjectArrays.lastIndexOf(array, null, 3)).isEqualTo(-1);
		assertThat(ObjectArrays.lastIndexOf(array, (char) 3)).isEqualTo(-1);
	}

	@Test
	void testLastIndexOfNull() {
		assertThatNullPointerException().isThrownBy(() -> ObjectArrays.lastIndexOf(null, 0));
	}

	@Test
	void testLastIndexOfInvalidFromIndex() {
		assertThatExceptionOfType(IndexOutOfBoundsException.class).isThrownBy(() -> ObjectArrays.lastIndexOf(ObjectArrays.of(1), 1, -1));
		assertThatExceptionOfType(IndexOutOfBoundsException.class).isThrownBy(() -> ObjectArrays.lastIndexOf(ObjectArrays.of(1), 1,  1));
	}

	@Test
	void testContains() {
		assertThat(ObjectArrays.contains(ObjectArrays.empty(Integer.class), 1)).isFalse();
		final var array = ObjectArrays.of(1, 2000, null);
		assertThat(ObjectArrays.contains(array,    1)).isTrue();
		assertThat(ObjectArrays.contains(array, 2000)).isTrue();
		assertThat(ObjectArrays.contains(array, null)).isTrue();
		assertThat(ObjectArrays.contains(array,    3)).isFalse();
	}

	@Test
	void testContainsNull() {
		assertThatNullPointerException().isThrownBy(() -> ObjectArrays.contains(null, 0));
	}

	@Test
	void testContainsOnly() {
		assertThat(ObjectArrays.containsOnly(ObjectArrays.empty(Integer.class), 1)).isFalse();
		final var array1 = ObjectArrays.of(1, 1);
		assertThat(ObjectArrays.containsOnly(array1,    1)).isTrue();
		assertThat(ObjectArrays.containsOnly(array1, 2000)).isFalse();
		assertThat(ObjectArrays.containsOnly(array1, null)).isFalse();
		final var array2 = ObjectArrays.of(2000, null);
		assertThat(ObjectArrays.containsOnly(array2,    1)).isFalse();
		assertThat(ObjectArrays.containsOnly(array2, 2000)).isFalse();
		assertThat(ObjectArrays.containsOnly(array2, null)).isFalse();
	}

	@Test
	void testContainsOnlyNull() {
		assertThatNullPointerException().isThrownBy(() -> ObjectArrays.containsOnly(null, 0));
	}

	@Test
	void testContainsAny() {
		assertThat(ObjectArrays.containsAny(ObjectArrays.empty(Integer.class), 1, 2)).isFalse();
		final var array = ObjectArrays.of(1, 2000, null);
		assertThat(ObjectArrays.containsAny(array)).isFalse();
		assertThat(ObjectArrays.containsAny(array,    1, 2000)).isTrue();
		assertThat(ObjectArrays.containsAny(array, 2000, null)).isTrue();
		assertThat(ObjectArrays.containsAny(array, null, 3000)).isTrue();
		assertThat(ObjectArrays.containsAny(array, 3000, 4000)).isFalse();
	}

	@Test
	void testContainsAnyNull() {
		assertThatNullPointerException().isThrownBy(() -> ObjectArrays.containsAny(null, 0));
		assertThatNullPointerException().isThrownBy(() -> ObjectArrays.containsAny(ObjectArrays.empty(Integer.class), (Integer[]) null));
	}

	@Test
	void testContainsAll() {
		assertThat(ObjectArrays.containsAll(ObjectArrays.empty(Integer.class), 1, 2)).isFalse();
		final var array = ObjectArrays.of(1, 2000, null);
		assertThat(ObjectArrays.containsAll(array)).isFalse();
		assertThat(ObjectArrays.containsAll(array, 1, 2000, null)).isTrue();
		assertThat(ObjectArrays.containsAll(array, null, 2000, 1)).isTrue();
		assertThat(ObjectArrays.containsAll(array, 1, 2000)).isTrue();
		assertThat(ObjectArrays.containsAll(array, 1, 2000, null, 3)).isFalse();
	}

	@Test
	void testContainsAllNull() {
		assertThatNullPointerException().isThrownBy(() -> ObjectArrays.containsAll(null, 0));
		assertThatNullPointerException().isThrownBy(() -> ObjectArrays.containsAll(ObjectArrays.empty(Integer.class), (Integer[]) null));
	}

	@Test
	void testOf() {
		assertThat(ObjectArrays.of()).isEmpty();
		assertThat(ObjectArrays.of()).isInstanceOf(Object[].class);
		assertThat(ObjectArrays.<Integer>of()).isEmpty();
		assertThat(ObjectArrays.<Integer>of()).isInstanceOf(Integer[].class);
		assertThat(ObjectArrays.of(1)).isInstanceOf(Integer[].class);
		assertThat(ObjectArrays.of(1, 2, null)).containsExactly(1, 2, null);
		assertThat(ObjectArrays.of(1, 2, null)).isInstanceOf(Integer[].class);
	}

	@Test
	void testOfNull() {
		assertThatNullPointerException().isThrownBy(() -> ObjectArrays.of((Integer[]) null));
	}
}