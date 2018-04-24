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
package com.github.alexisjehan.javanilla.lang.array;

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
		assertThat(ByteArrays.nullToEmpty(ByteArrays.of((byte) 1))).isNotEmpty();
	}

	@Test
	void testEmptyToNull() {
		assertThat(ByteArrays.emptyToNull(ByteArrays.EMPTY)).isNull();
		assertThat(ByteArrays.emptyToNull(null)).isNull();
		assertThat(ByteArrays.emptyToNull(ByteArrays.of((byte) 1))).isNotNull();
	}

	@Test
	void testIndexOf() {
		final var array = ByteArrays.of((byte) 1, (byte) 2, (byte) 3);
		assertThat(ByteArrays.indexOf(array, (byte) 1)).isEqualTo(0);
		assertThat(ByteArrays.indexOf(array, (byte) 2)).isEqualTo(1);
		assertThat(ByteArrays.indexOf(array, (byte) 3)).isEqualTo(2);
		assertThat(ByteArrays.indexOf(array, (byte) 1, 1)).isEqualTo(-1);
		assertThat(ByteArrays.indexOf(array, (byte) 2, 2)).isEqualTo(-1);
		assertThat(ByteArrays.indexOf(array, (byte) 4)).isEqualTo(-1);
	}

	@Test
	void testIndexOfNull() {
		assertThatNullPointerException().isThrownBy(() -> ByteArrays.indexOf(null, (byte) 0));
	}

	@Test
	void testIndexOfInvalidFromIndex() {
		assertThatExceptionOfType(IndexOutOfBoundsException.class).isThrownBy(() -> ByteArrays.indexOf(ByteArrays.of((byte) 1), (byte) 1, -1));
		assertThatExceptionOfType(IndexOutOfBoundsException.class).isThrownBy(() -> ByteArrays.indexOf(ByteArrays.of((byte) 1), (byte) 1,  1));
	}

	@Test
	void testLastIndexOf() {
		final var array = ByteArrays.of((byte) 1, (byte) 2, (byte) 3, (byte) 1);
		assertThat(ByteArrays.lastIndexOf(array, (byte) 1)).isEqualTo(3);
		assertThat(ByteArrays.lastIndexOf(array, (byte) 2)).isEqualTo(1);
		assertThat(ByteArrays.lastIndexOf(array, (byte) 3)).isEqualTo(2);
		assertThat(ByteArrays.lastIndexOf(array, (byte) 1, 1)).isEqualTo( 3);
		assertThat(ByteArrays.lastIndexOf(array, (byte) 2, 2)).isEqualTo(-1);
		assertThat(ByteArrays.lastIndexOf(array, (byte) 3, 3)).isEqualTo(-1);
		assertThat(ByteArrays.lastIndexOf(array, (byte) 4)).isEqualTo(-1);
	}

	@Test
	void testLastIndexOfNull() {
		assertThatNullPointerException().isThrownBy(() -> ByteArrays.lastIndexOf(null, (byte) 0));
	}

	@Test
	void testLastIndexOfInvalidFromIndex() {
		assertThatExceptionOfType(IndexOutOfBoundsException.class).isThrownBy(() -> ByteArrays.lastIndexOf(ByteArrays.of((byte) 1), (byte) 1, -1));
		assertThatExceptionOfType(IndexOutOfBoundsException.class).isThrownBy(() -> ByteArrays.lastIndexOf(ByteArrays.of((byte) 1), (byte) 1,  1));
	}

	@Test
	void testContains() {
		assertThat(ByteArrays.contains(ByteArrays.EMPTY, (byte) 1)).isFalse();
		final var array = ByteArrays.of((byte) 1, (byte) 2, (byte) 3);
		assertThat(ByteArrays.contains(array, (byte) 1)).isTrue();
		assertThat(ByteArrays.contains(array, (byte) 2)).isTrue();
		assertThat(ByteArrays.contains(array, (byte) 3)).isTrue();
		assertThat(ByteArrays.contains(array, (byte) 4)).isFalse();
	}

	@Test
	void testContainsNull() {
		assertThatNullPointerException().isThrownBy(() -> ByteArrays.contains(null, (byte) 0));
	}

	@Test
	void testContainsOnly() {
		assertThat(ByteArrays.containsOnly(ByteArrays.EMPTY, (byte) 1)).isFalse();
		final var array1 = ByteArrays.of((byte) 1, (byte) 1);
		assertThat(ByteArrays.containsOnly(array1, (byte) 1)).isTrue();
		assertThat(ByteArrays.containsOnly(array1, (byte) 2)).isFalse();
		final var array2 = ByteArrays.of((byte) 1, (byte) 2);
		assertThat(ByteArrays.containsOnly(array2, (byte) 1)).isFalse();
		assertThat(ByteArrays.containsOnly(array2, (byte) 2)).isFalse();
	}

	@Test
	void testContainsOnlyNull() {
		assertThatNullPointerException().isThrownBy(() -> ByteArrays.containsOnly(null, (byte) 0));
	}

	@Test
	void testContainsAny() {
		assertThat(ByteArrays.containsAny(ByteArrays.EMPTY, (byte) 1, (byte) 2)).isFalse();
		final var array = ByteArrays.of((byte) 1, (byte) 2, (byte) 3);
		assertThat(ByteArrays.containsAny(array)).isFalse();
		assertThat(ByteArrays.containsAny(array, (byte) 1, (byte) 2)).isTrue();
		assertThat(ByteArrays.containsAny(array, (byte) 3, (byte) 4)).isTrue();
		assertThat(ByteArrays.containsAny(array, (byte) 5, (byte) 6)).isFalse();
	}

	@Test
	void testContainsAnyNull() {
		assertThatNullPointerException().isThrownBy(() -> ByteArrays.containsAny(null, (byte) 0));
		assertThatNullPointerException().isThrownBy(() -> ByteArrays.containsAny(ByteArrays.of((byte) 1, (byte) 2, (byte) 3), (byte[]) null));
	}

	@Test
	void testContainsAll() {
		assertThat(ByteArrays.containsAll(ByteArrays.EMPTY, (byte) 1, (byte) 2)).isFalse();
		final var array = ByteArrays.of((byte) 1, (byte) 2, (byte) 3);
		assertThat(ByteArrays.containsAll(array)).isFalse();
		assertThat(ByteArrays.containsAll(array, (byte) 1, (byte) 2, (byte) 3)).isTrue();
		assertThat(ByteArrays.containsAll(array, (byte) 3, (byte) 2, (byte) 1)).isTrue();
		assertThat(ByteArrays.containsAll(array, (byte) 1, (byte) 2)).isTrue();
		assertThat(ByteArrays.containsAll(array, (byte) 1, (byte) 2, (byte) 3, (byte) 4)).isFalse();
	}

	@Test
	void testContainsAllNull() {
		assertThatNullPointerException().isThrownBy(() -> ByteArrays.containsAll(null, (byte) 0));
		assertThatNullPointerException().isThrownBy(() -> ByteArrays.containsAll(ByteArrays.of((byte) 1, (byte) 2, (byte) 3), (byte[]) null));
	}

	@Test
	void testConcat() {
		final var array = ByteArrays.of((byte) 1, (byte) 2, (byte) 3);
		assertThat(ByteArrays.concat(array, array)).isEqualTo(ByteArrays.of((byte) 1, (byte) 2, (byte) 3, (byte) 1, (byte) 2, (byte) 3));
	}

	@Test
	void testConcatOne() {
		final var array = ByteArrays.of((byte) 1, (byte) 2, (byte) 3);
		assertThat(ByteArrays.concat(array)).isEqualTo(array);
	}

	@Test
	void testConcatNone() {
		assertThat(ByteArrays.concat()).isEmpty();
	}

	@Test
	void testConcatNull() {
		assertThatNullPointerException().isThrownBy(() -> ByteArrays.concat((byte[][]) null));
		assertThatNullPointerException().isThrownBy(() -> ByteArrays.concat((List<byte[]>) null));
		assertThatNullPointerException().isThrownBy(() -> ByteArrays.concat((byte[]) null));
	}

	@Test
	void testJoin() {
		final var array = ByteArrays.of((byte) 1, (byte) 2, (byte) 3);
		assertThat(ByteArrays.join(ByteArrays.of((byte) 0), array, array)).isEqualTo(ByteArrays.of((byte) 1, (byte) 2, (byte) 3, (byte) 0, (byte) 1, (byte) 2, (byte) 3));
	}

	@Test
	void testJoinEmptySeparator() {
		final var array = ByteArrays.of((byte) 1, (byte) 2, (byte) 3);
		assertThat(ByteArrays.join(ByteArrays.EMPTY, array, array)).isEqualTo(ByteArrays.concat(array, array));
	}

	@Test
	void testJoinOne() {
		final var array = ByteArrays.of((byte) 1, (byte) 2, (byte) 3);
		assertThat(ByteArrays.join(ByteArrays.of((byte) 0), array)).isEqualTo(array);
	}

	@Test
	void testJoinNone() {
		assertThat(ByteArrays.join(ByteArrays.of((byte) 0))).isEmpty();
	}

	@Test
	void testJoinNull() {
		assertThatNullPointerException().isThrownBy(() -> ByteArrays.join(null, ByteArrays.EMPTY));
		assertThatNullPointerException().isThrownBy(() -> ByteArrays.join(ByteArrays.EMPTY, (byte[][]) null));
		assertThatNullPointerException().isThrownBy(() -> ByteArrays.join(ByteArrays.EMPTY, (List<byte[]>) null));
		assertThatNullPointerException().isThrownBy(() -> ByteArrays.join(ByteArrays.EMPTY, (byte[]) null));
	}

	@Test
	void testOf() {
		assertThat(ByteArrays.of()).isEmpty();
		assertThat(ByteArrays.of((byte) 1, (byte) 2, (byte) 3)).containsExactly((byte) 1, (byte) 2, (byte) 3);
	}

	@Test
	void testOfNull() {
		assertThatNullPointerException().isThrownBy(() -> ByteArrays.of((byte[]) null));
	}

	@Test
	void testOfBooleanAndToBoolean() {
		assertThat(ByteArrays.toBoolean(ByteArrays.ofBoolean(false))).isEqualTo(false);
		assertThat(ByteArrays.toBoolean(ByteArrays.ofBoolean( true))).isEqualTo( true);
	}

	@Test
	void testToBooleanNull() {
		assertThatNullPointerException().isThrownBy(() -> ByteArrays.toBoolean(null));
	}

	@Test
	void testToBooleanInvalid() {
		assertThatIllegalArgumentException().isThrownBy(() -> ByteArrays.toBoolean(ByteArrays.EMPTY));
	}

	@Test
	void testOfShortAndToShort() {
		assertThat(ByteArrays.toShort(ByteArrays.ofShort((short) 0))).isEqualTo((short) 0);
		assertThat(ByteArrays.toShort(ByteArrays.ofShort((short) 0,    ByteOrder.BIG_ENDIAN),    ByteOrder.BIG_ENDIAN)).isEqualTo((short) 0);
		assertThat(ByteArrays.toShort(ByteArrays.ofShort((short) 0, ByteOrder.LITTLE_ENDIAN), ByteOrder.LITTLE_ENDIAN)).isEqualTo((short) 0);

		assertThat(ByteArrays.toShort(ByteArrays.ofShort((short) -5,    ByteOrder.BIG_ENDIAN),    ByteOrder.BIG_ENDIAN)).isEqualTo((short) -5);
		assertThat(ByteArrays.toShort(ByteArrays.ofShort((short) -5, ByteOrder.LITTLE_ENDIAN), ByteOrder.LITTLE_ENDIAN)).isEqualTo((short) -5);
		assertThat(ByteArrays.toShort(ByteArrays.ofShort((short) -5,    ByteOrder.BIG_ENDIAN), ByteOrder.LITTLE_ENDIAN)).isNotEqualTo((short) -5);
		assertThat(ByteArrays.toShort(ByteArrays.ofShort((short) -5, ByteOrder.LITTLE_ENDIAN),    ByteOrder.BIG_ENDIAN)).isNotEqualTo((short) -5);

		assertThat(ByteArrays.toShort(ByteArrays.ofShort((short) 5,    ByteOrder.BIG_ENDIAN),    ByteOrder.BIG_ENDIAN)).isEqualTo((short) 5);
		assertThat(ByteArrays.toShort(ByteArrays.ofShort((short) 5, ByteOrder.LITTLE_ENDIAN), ByteOrder.LITTLE_ENDIAN)).isEqualTo((short) 5);
		assertThat(ByteArrays.toShort(ByteArrays.ofShort((short) 5,    ByteOrder.BIG_ENDIAN), ByteOrder.LITTLE_ENDIAN)).isNotEqualTo((short) 5);
		assertThat(ByteArrays.toShort(ByteArrays.ofShort((short) 5, ByteOrder.LITTLE_ENDIAN),    ByteOrder.BIG_ENDIAN)).isNotEqualTo((short) 5);

		assertThat(ByteArrays.toShort(ByteArrays.ofShort(Short.MIN_VALUE,    ByteOrder.BIG_ENDIAN),    ByteOrder.BIG_ENDIAN)).isEqualTo(Short.MIN_VALUE);
		assertThat(ByteArrays.toShort(ByteArrays.ofShort(Short.MIN_VALUE, ByteOrder.LITTLE_ENDIAN), ByteOrder.LITTLE_ENDIAN)).isEqualTo(Short.MIN_VALUE);
		assertThat(ByteArrays.toShort(ByteArrays.ofShort(Short.MIN_VALUE,    ByteOrder.BIG_ENDIAN), ByteOrder.LITTLE_ENDIAN)).isNotEqualTo(Short.MIN_VALUE);
		assertThat(ByteArrays.toShort(ByteArrays.ofShort(Short.MIN_VALUE, ByteOrder.LITTLE_ENDIAN),    ByteOrder.BIG_ENDIAN)).isNotEqualTo(Short.MIN_VALUE);

		assertThat(ByteArrays.toShort(ByteArrays.ofShort(Short.MAX_VALUE,    ByteOrder.BIG_ENDIAN),    ByteOrder.BIG_ENDIAN)).isEqualTo(Short.MAX_VALUE);
		assertThat(ByteArrays.toShort(ByteArrays.ofShort(Short.MAX_VALUE, ByteOrder.LITTLE_ENDIAN), ByteOrder.LITTLE_ENDIAN)).isEqualTo(Short.MAX_VALUE);
		assertThat(ByteArrays.toShort(ByteArrays.ofShort(Short.MAX_VALUE,    ByteOrder.BIG_ENDIAN), ByteOrder.LITTLE_ENDIAN)).isNotEqualTo(Short.MAX_VALUE);
		assertThat(ByteArrays.toShort(ByteArrays.ofShort(Short.MAX_VALUE, ByteOrder.LITTLE_ENDIAN),    ByteOrder.BIG_ENDIAN)).isNotEqualTo(Short.MAX_VALUE);
	}

	@Test
	void testOfShortNull() {
		assertThatNullPointerException().isThrownBy(() -> ByteArrays.ofShort((short) 0, null));
	}

	@Test
	void testToShortNull() {
		assertThatNullPointerException().isThrownBy(() -> ByteArrays.toShort(null));
		assertThatNullPointerException().isThrownBy(() -> ByteArrays.toShort(ByteArrays.EMPTY, null));
	}

	@Test
	void testToShortInvalid() {
		assertThatIllegalArgumentException().isThrownBy(() -> ByteArrays.toShort(ByteArrays.EMPTY));
	}

	@Test
	void testOfCharAndToChar() {
		assertThat(ByteArrays.toChar(ByteArrays.ofChar(Character.MIN_VALUE))).isEqualTo(Character.MIN_VALUE);
		assertThat(ByteArrays.toChar(ByteArrays.ofChar(Character.MIN_VALUE,    ByteOrder.BIG_ENDIAN),    ByteOrder.BIG_ENDIAN)).isEqualTo(Character.MIN_VALUE);
		assertThat(ByteArrays.toChar(ByteArrays.ofChar(Character.MIN_VALUE, ByteOrder.LITTLE_ENDIAN), ByteOrder.LITTLE_ENDIAN)).isEqualTo(Character.MIN_VALUE);

		assertThat(ByteArrays.toChar(ByteArrays.ofChar((char) 5,    ByteOrder.BIG_ENDIAN),    ByteOrder.BIG_ENDIAN)).isEqualTo((char) 5);
		assertThat(ByteArrays.toChar(ByteArrays.ofChar((char) 5, ByteOrder.LITTLE_ENDIAN), ByteOrder.LITTLE_ENDIAN)).isEqualTo((char) 5);
		assertThat(ByteArrays.toChar(ByteArrays.ofChar((char) 5,    ByteOrder.BIG_ENDIAN), ByteOrder.LITTLE_ENDIAN)).isNotEqualTo((char) 5);
		assertThat(ByteArrays.toChar(ByteArrays.ofChar((char) 5, ByteOrder.LITTLE_ENDIAN),    ByteOrder.BIG_ENDIAN)).isNotEqualTo((char) 5);

		assertThat(ByteArrays.toChar(ByteArrays.ofChar(Character.MAX_VALUE,    ByteOrder.BIG_ENDIAN),    ByteOrder.BIG_ENDIAN)).isEqualTo(Character.MAX_VALUE);
		assertThat(ByteArrays.toChar(ByteArrays.ofChar(Character.MAX_VALUE, ByteOrder.LITTLE_ENDIAN), ByteOrder.LITTLE_ENDIAN)).isEqualTo(Character.MAX_VALUE);
	}

	@Test
	void testOfCharNull() {
		assertThatNullPointerException().isThrownBy(() -> ByteArrays.ofChar((char) 0, null));
	}

	@Test
	void testToCharNull() {
		assertThatNullPointerException().isThrownBy(() -> ByteArrays.toChar(null));
		assertThatNullPointerException().isThrownBy(() -> ByteArrays.toChar(ByteArrays.EMPTY, null));
	}

	@Test
	void testToCharInvalid() {
		assertThatIllegalArgumentException().isThrownBy(() -> ByteArrays.toChar(ByteArrays.EMPTY));
	}

	@Test
	void testOfIntAndToInt() {
		assertThat(ByteArrays.toInt(ByteArrays.ofInt(0))).isEqualTo(0);
		assertThat(ByteArrays.toInt(ByteArrays.ofInt(0,    ByteOrder.BIG_ENDIAN),    ByteOrder.BIG_ENDIAN)).isEqualTo(0);
		assertThat(ByteArrays.toInt(ByteArrays.ofInt(0, ByteOrder.LITTLE_ENDIAN), ByteOrder.LITTLE_ENDIAN)).isEqualTo(0);

		assertThat(ByteArrays.toInt(ByteArrays.ofInt(-5,    ByteOrder.BIG_ENDIAN),    ByteOrder.BIG_ENDIAN)).isEqualTo(-5);
		assertThat(ByteArrays.toInt(ByteArrays.ofInt(-5, ByteOrder.LITTLE_ENDIAN), ByteOrder.LITTLE_ENDIAN)).isEqualTo(-5);
		assertThat(ByteArrays.toInt(ByteArrays.ofInt(-5,    ByteOrder.BIG_ENDIAN), ByteOrder.LITTLE_ENDIAN)).isNotEqualTo(-5);
		assertThat(ByteArrays.toInt(ByteArrays.ofInt(-5, ByteOrder.LITTLE_ENDIAN),    ByteOrder.BIG_ENDIAN)).isNotEqualTo(-5);

		assertThat(ByteArrays.toInt(ByteArrays.ofInt(5,    ByteOrder.BIG_ENDIAN),    ByteOrder.BIG_ENDIAN)).isEqualTo(5);
		assertThat(ByteArrays.toInt(ByteArrays.ofInt(5, ByteOrder.LITTLE_ENDIAN), ByteOrder.LITTLE_ENDIAN)).isEqualTo(5);
		assertThat(ByteArrays.toInt(ByteArrays.ofInt(5,    ByteOrder.BIG_ENDIAN), ByteOrder.LITTLE_ENDIAN)).isNotEqualTo(5);
		assertThat(ByteArrays.toInt(ByteArrays.ofInt(5, ByteOrder.LITTLE_ENDIAN),    ByteOrder.BIG_ENDIAN)).isNotEqualTo(5);

		assertThat(ByteArrays.toInt(ByteArrays.ofInt(Integer.MIN_VALUE,    ByteOrder.BIG_ENDIAN),    ByteOrder.BIG_ENDIAN)).isEqualTo(Integer.MIN_VALUE);
		assertThat(ByteArrays.toInt(ByteArrays.ofInt(Integer.MIN_VALUE, ByteOrder.LITTLE_ENDIAN), ByteOrder.LITTLE_ENDIAN)).isEqualTo(Integer.MIN_VALUE);
		assertThat(ByteArrays.toInt(ByteArrays.ofInt(Integer.MIN_VALUE,    ByteOrder.BIG_ENDIAN), ByteOrder.LITTLE_ENDIAN)).isNotEqualTo(Integer.MIN_VALUE);
		assertThat(ByteArrays.toInt(ByteArrays.ofInt(Integer.MIN_VALUE, ByteOrder.LITTLE_ENDIAN),    ByteOrder.BIG_ENDIAN)).isNotEqualTo(Integer.MIN_VALUE);

		assertThat(ByteArrays.toInt(ByteArrays.ofInt(Integer.MAX_VALUE,    ByteOrder.BIG_ENDIAN),    ByteOrder.BIG_ENDIAN)).isEqualTo(Integer.MAX_VALUE);
		assertThat(ByteArrays.toInt(ByteArrays.ofInt(Integer.MAX_VALUE, ByteOrder.LITTLE_ENDIAN), ByteOrder.LITTLE_ENDIAN)).isEqualTo(Integer.MAX_VALUE);
		assertThat(ByteArrays.toInt(ByteArrays.ofInt(Integer.MAX_VALUE,    ByteOrder.BIG_ENDIAN), ByteOrder.LITTLE_ENDIAN)).isNotEqualTo(Integer.MAX_VALUE);
		assertThat(ByteArrays.toInt(ByteArrays.ofInt(Integer.MAX_VALUE, ByteOrder.LITTLE_ENDIAN),    ByteOrder.BIG_ENDIAN)).isNotEqualTo(Integer.MAX_VALUE);
	}

	@Test
	void testOfIntNull() {
		assertThatNullPointerException().isThrownBy(() -> ByteArrays.ofInt(0, null));
	}

	@Test
	void testToIntNull() {
		assertThatNullPointerException().isThrownBy(() -> ByteArrays.toInt(null));
		assertThatNullPointerException().isThrownBy(() -> ByteArrays.toInt(ByteArrays.EMPTY, null));
	}

	@Test
	void testToIntInvalid() {
		assertThatIllegalArgumentException().isThrownBy(() -> ByteArrays.toInt(ByteArrays.EMPTY));
	}

	@Test
	void testOfLongAndToLong() {
		assertThat(ByteArrays.toLong(ByteArrays.ofLong(0L))).isEqualTo(0L);
		assertThat(ByteArrays.toLong(ByteArrays.ofLong(0L,    ByteOrder.BIG_ENDIAN),    ByteOrder.BIG_ENDIAN)).isEqualTo(0L);
		assertThat(ByteArrays.toLong(ByteArrays.ofLong(0L, ByteOrder.LITTLE_ENDIAN), ByteOrder.LITTLE_ENDIAN)).isEqualTo(0L);

		assertThat(ByteArrays.toLong(ByteArrays.ofLong(-5L,    ByteOrder.BIG_ENDIAN),    ByteOrder.BIG_ENDIAN)).isEqualTo(-5L);
		assertThat(ByteArrays.toLong(ByteArrays.ofLong(-5L, ByteOrder.LITTLE_ENDIAN), ByteOrder.LITTLE_ENDIAN)).isEqualTo(-5L);
		assertThat(ByteArrays.toLong(ByteArrays.ofLong(-5L,    ByteOrder.BIG_ENDIAN), ByteOrder.LITTLE_ENDIAN)).isNotEqualTo(-5L);
		assertThat(ByteArrays.toLong(ByteArrays.ofLong(-5L, ByteOrder.LITTLE_ENDIAN),    ByteOrder.BIG_ENDIAN)).isNotEqualTo(-5L);

		assertThat(ByteArrays.toLong(ByteArrays.ofLong(5L,    ByteOrder.BIG_ENDIAN),    ByteOrder.BIG_ENDIAN)).isEqualTo(5L);
		assertThat(ByteArrays.toLong(ByteArrays.ofLong(5L, ByteOrder.LITTLE_ENDIAN), ByteOrder.LITTLE_ENDIAN)).isEqualTo(5L);
		assertThat(ByteArrays.toLong(ByteArrays.ofLong(5L,    ByteOrder.BIG_ENDIAN), ByteOrder.LITTLE_ENDIAN)).isNotEqualTo(5L);
		assertThat(ByteArrays.toLong(ByteArrays.ofLong(5L, ByteOrder.LITTLE_ENDIAN),    ByteOrder.BIG_ENDIAN)).isNotEqualTo(5L);

		assertThat(ByteArrays.toLong(ByteArrays.ofLong(Long.MIN_VALUE,    ByteOrder.BIG_ENDIAN),    ByteOrder.BIG_ENDIAN)).isEqualTo(Long.MIN_VALUE);
		assertThat(ByteArrays.toLong(ByteArrays.ofLong(Long.MIN_VALUE, ByteOrder.LITTLE_ENDIAN), ByteOrder.LITTLE_ENDIAN)).isEqualTo(Long.MIN_VALUE);
		assertThat(ByteArrays.toLong(ByteArrays.ofLong(Long.MIN_VALUE,    ByteOrder.BIG_ENDIAN), ByteOrder.LITTLE_ENDIAN)).isNotEqualTo(Long.MIN_VALUE);
		assertThat(ByteArrays.toLong(ByteArrays.ofLong(Long.MIN_VALUE, ByteOrder.LITTLE_ENDIAN),    ByteOrder.BIG_ENDIAN)).isNotEqualTo(Long.MIN_VALUE);

		assertThat(ByteArrays.toLong(ByteArrays.ofLong(Long.MAX_VALUE,    ByteOrder.BIG_ENDIAN),    ByteOrder.BIG_ENDIAN)).isEqualTo(Long.MAX_VALUE);
		assertThat(ByteArrays.toLong(ByteArrays.ofLong(Long.MAX_VALUE, ByteOrder.LITTLE_ENDIAN), ByteOrder.LITTLE_ENDIAN)).isEqualTo(Long.MAX_VALUE);
		assertThat(ByteArrays.toLong(ByteArrays.ofLong(Long.MAX_VALUE,    ByteOrder.BIG_ENDIAN), ByteOrder.LITTLE_ENDIAN)).isNotEqualTo(Long.MAX_VALUE);
		assertThat(ByteArrays.toLong(ByteArrays.ofLong(Long.MAX_VALUE, ByteOrder.LITTLE_ENDIAN),    ByteOrder.BIG_ENDIAN)).isNotEqualTo(Long.MAX_VALUE);
	}

	@Test
	void testOfLongNull() {
		assertThatNullPointerException().isThrownBy(() -> ByteArrays.ofLong(0L, null));
	}

	@Test
	void testToLongNull() {
		assertThatNullPointerException().isThrownBy(() -> ByteArrays.toLong(null));
		assertThatNullPointerException().isThrownBy(() -> ByteArrays.toLong(ByteArrays.EMPTY, null));
	}

	@Test
	void testToLongInvalid() {
		assertThatIllegalArgumentException().isThrownBy(() -> ByteArrays.toLong(ByteArrays.EMPTY));
	}

	@Test
	void testOfFloatAndToFloat() {
		assertThat(ByteArrays.toFloat(ByteArrays.ofFloat(0.0f))).isEqualTo(0.0f);
		assertThat(ByteArrays.toFloat(ByteArrays.ofFloat(0.0f,    ByteOrder.BIG_ENDIAN),    ByteOrder.BIG_ENDIAN)).isEqualTo(0.0f);
		assertThat(ByteArrays.toFloat(ByteArrays.ofFloat(0.0f, ByteOrder.LITTLE_ENDIAN), ByteOrder.LITTLE_ENDIAN)).isEqualTo(0.0f);

		assertThat(ByteArrays.toFloat(ByteArrays.ofFloat(-5.5f,    ByteOrder.BIG_ENDIAN),    ByteOrder.BIG_ENDIAN)).isEqualTo(-5.5f);
		assertThat(ByteArrays.toFloat(ByteArrays.ofFloat(-5.5f, ByteOrder.LITTLE_ENDIAN), ByteOrder.LITTLE_ENDIAN)).isEqualTo(-5.5f);
		assertThat(ByteArrays.toFloat(ByteArrays.ofFloat(-5.5f,    ByteOrder.BIG_ENDIAN), ByteOrder.LITTLE_ENDIAN)).isNotEqualTo(-5.5f);
		assertThat(ByteArrays.toFloat(ByteArrays.ofFloat(-5.5f, ByteOrder.LITTLE_ENDIAN),    ByteOrder.BIG_ENDIAN)).isNotEqualTo(-5.5f);

		assertThat(ByteArrays.toFloat(ByteArrays.ofFloat(5.5f,    ByteOrder.BIG_ENDIAN),    ByteOrder.BIG_ENDIAN)).isEqualTo(5.5f);
		assertThat(ByteArrays.toFloat(ByteArrays.ofFloat(5.5f, ByteOrder.LITTLE_ENDIAN), ByteOrder.LITTLE_ENDIAN)).isEqualTo(5.5f);
		assertThat(ByteArrays.toFloat(ByteArrays.ofFloat(5.5f,    ByteOrder.BIG_ENDIAN), ByteOrder.LITTLE_ENDIAN)).isNotEqualTo(5.5f);
		assertThat(ByteArrays.toFloat(ByteArrays.ofFloat(5.5f, ByteOrder.LITTLE_ENDIAN),    ByteOrder.BIG_ENDIAN)).isNotEqualTo(5.5f);

		assertThat(ByteArrays.toFloat(ByteArrays.ofFloat(Float.MIN_VALUE,    ByteOrder.BIG_ENDIAN),    ByteOrder.BIG_ENDIAN)).isEqualTo(Float.MIN_VALUE);
		assertThat(ByteArrays.toFloat(ByteArrays.ofFloat(Float.MIN_VALUE, ByteOrder.LITTLE_ENDIAN), ByteOrder.LITTLE_ENDIAN)).isEqualTo(Float.MIN_VALUE);
		assertThat(ByteArrays.toFloat(ByteArrays.ofFloat(Float.MIN_VALUE,    ByteOrder.BIG_ENDIAN), ByteOrder.LITTLE_ENDIAN)).isNotEqualTo(Float.MIN_VALUE);
		assertThat(ByteArrays.toFloat(ByteArrays.ofFloat(Float.MIN_VALUE, ByteOrder.LITTLE_ENDIAN),    ByteOrder.BIG_ENDIAN)).isNotEqualTo(Float.MIN_VALUE);

		assertThat(ByteArrays.toFloat(ByteArrays.ofFloat(Float.NEGATIVE_INFINITY,    ByteOrder.BIG_ENDIAN),    ByteOrder.BIG_ENDIAN)).isEqualTo(Float.NEGATIVE_INFINITY);
		assertThat(ByteArrays.toFloat(ByteArrays.ofFloat(Float.NEGATIVE_INFINITY, ByteOrder.LITTLE_ENDIAN), ByteOrder.LITTLE_ENDIAN)).isEqualTo(Float.NEGATIVE_INFINITY);
		assertThat(ByteArrays.toFloat(ByteArrays.ofFloat(Float.NEGATIVE_INFINITY,    ByteOrder.BIG_ENDIAN), ByteOrder.LITTLE_ENDIAN)).isNotEqualTo(Float.NEGATIVE_INFINITY);
		assertThat(ByteArrays.toFloat(ByteArrays.ofFloat(Float.NEGATIVE_INFINITY, ByteOrder.LITTLE_ENDIAN),    ByteOrder.BIG_ENDIAN)).isNotEqualTo(Float.NEGATIVE_INFINITY);

		assertThat(ByteArrays.toFloat(ByteArrays.ofFloat(Float.MIN_NORMAL,    ByteOrder.BIG_ENDIAN),    ByteOrder.BIG_ENDIAN)).isEqualTo(Float.MIN_NORMAL);
		assertThat(ByteArrays.toFloat(ByteArrays.ofFloat(Float.MIN_NORMAL, ByteOrder.LITTLE_ENDIAN), ByteOrder.LITTLE_ENDIAN)).isEqualTo(Float.MIN_NORMAL);
		assertThat(ByteArrays.toFloat(ByteArrays.ofFloat(Float.MIN_NORMAL,    ByteOrder.BIG_ENDIAN), ByteOrder.LITTLE_ENDIAN)).isNotEqualTo(Float.MIN_NORMAL);
		assertThat(ByteArrays.toFloat(ByteArrays.ofFloat(Float.MIN_NORMAL, ByteOrder.LITTLE_ENDIAN),    ByteOrder.BIG_ENDIAN)).isNotEqualTo(Float.MIN_NORMAL);

		assertThat(ByteArrays.toFloat(ByteArrays.ofFloat(Float.POSITIVE_INFINITY,    ByteOrder.BIG_ENDIAN),    ByteOrder.BIG_ENDIAN)).isEqualTo(Float.POSITIVE_INFINITY);
		assertThat(ByteArrays.toFloat(ByteArrays.ofFloat(Float.POSITIVE_INFINITY, ByteOrder.LITTLE_ENDIAN), ByteOrder.LITTLE_ENDIAN)).isEqualTo(Float.POSITIVE_INFINITY);
		assertThat(ByteArrays.toFloat(ByteArrays.ofFloat(Float.POSITIVE_INFINITY,    ByteOrder.BIG_ENDIAN), ByteOrder.LITTLE_ENDIAN)).isNotEqualTo(Float.POSITIVE_INFINITY);
		assertThat(ByteArrays.toFloat(ByteArrays.ofFloat(Float.POSITIVE_INFINITY, ByteOrder.LITTLE_ENDIAN),    ByteOrder.BIG_ENDIAN)).isNotEqualTo(Float.POSITIVE_INFINITY);

		assertThat(ByteArrays.toFloat(ByteArrays.ofFloat(Float.MAX_VALUE,    ByteOrder.BIG_ENDIAN),    ByteOrder.BIG_ENDIAN)).isEqualTo(Float.MAX_VALUE);
		assertThat(ByteArrays.toFloat(ByteArrays.ofFloat(Float.MAX_VALUE, ByteOrder.LITTLE_ENDIAN), ByteOrder.LITTLE_ENDIAN)).isEqualTo(Float.MAX_VALUE);
		assertThat(ByteArrays.toFloat(ByteArrays.ofFloat(Float.MAX_VALUE,    ByteOrder.BIG_ENDIAN), ByteOrder.LITTLE_ENDIAN)).isNotEqualTo(Float.MAX_VALUE);
		assertThat(ByteArrays.toFloat(ByteArrays.ofFloat(Float.MAX_VALUE, ByteOrder.LITTLE_ENDIAN),    ByteOrder.BIG_ENDIAN)).isNotEqualTo(Float.MAX_VALUE);

		assertThat(ByteArrays.toFloat(ByteArrays.ofFloat(Float.NaN,    ByteOrder.BIG_ENDIAN),    ByteOrder.BIG_ENDIAN)).isEqualTo(Float.NaN);
		assertThat(ByteArrays.toFloat(ByteArrays.ofFloat(Float.NaN, ByteOrder.LITTLE_ENDIAN), ByteOrder.LITTLE_ENDIAN)).isEqualTo(Float.NaN);
		assertThat(ByteArrays.toFloat(ByteArrays.ofFloat(Float.NaN,    ByteOrder.BIG_ENDIAN), ByteOrder.LITTLE_ENDIAN)).isNotEqualTo(Float.NaN);
		assertThat(ByteArrays.toFloat(ByteArrays.ofFloat(Float.NaN, ByteOrder.LITTLE_ENDIAN),    ByteOrder.BIG_ENDIAN)).isNotEqualTo(Float.NaN);
	}

	@Test
	void testOfFloatNull() {
		assertThatNullPointerException().isThrownBy(() -> ByteArrays.ofFloat(0.0f, null));
	}

	@Test
	void testToFloatNull() {
		assertThatNullPointerException().isThrownBy(() -> ByteArrays.toFloat(null));
		assertThatNullPointerException().isThrownBy(() -> ByteArrays.toFloat(ByteArrays.EMPTY, null));
	}

	@Test
	void testToFloatInvalid() {
		assertThatIllegalArgumentException().isThrownBy(() -> ByteArrays.toFloat(ByteArrays.EMPTY));
	}

	@Test
	void testOfDoubleAndToDouble() {
		assertThat(ByteArrays.toDouble(ByteArrays.ofDouble(0.0d))).isEqualTo(0.0d);
		assertThat(ByteArrays.toDouble(ByteArrays.ofDouble(0.0d,    ByteOrder.BIG_ENDIAN),    ByteOrder.BIG_ENDIAN)).isEqualTo(0.0d);
		assertThat(ByteArrays.toDouble(ByteArrays.ofDouble(0.0d, ByteOrder.LITTLE_ENDIAN), ByteOrder.LITTLE_ENDIAN)).isEqualTo(0.0d);

		assertThat(ByteArrays.toDouble(ByteArrays.ofDouble(-5.5d,    ByteOrder.BIG_ENDIAN),    ByteOrder.BIG_ENDIAN)).isEqualTo(-5.5d);
		assertThat(ByteArrays.toDouble(ByteArrays.ofDouble(-5.5d, ByteOrder.LITTLE_ENDIAN), ByteOrder.LITTLE_ENDIAN)).isEqualTo(-5.5d);
		assertThat(ByteArrays.toDouble(ByteArrays.ofDouble(-5.5d,    ByteOrder.BIG_ENDIAN), ByteOrder.LITTLE_ENDIAN)).isNotEqualTo(-5.5d);
		assertThat(ByteArrays.toDouble(ByteArrays.ofDouble(-5.5d, ByteOrder.LITTLE_ENDIAN),    ByteOrder.BIG_ENDIAN)).isNotEqualTo(-5.5d);

		assertThat(ByteArrays.toDouble(ByteArrays.ofDouble(5.5d,    ByteOrder.BIG_ENDIAN),    ByteOrder.BIG_ENDIAN)).isEqualTo(5.5d);
		assertThat(ByteArrays.toDouble(ByteArrays.ofDouble(5.5d, ByteOrder.LITTLE_ENDIAN), ByteOrder.LITTLE_ENDIAN)).isEqualTo(5.5d);
		assertThat(ByteArrays.toDouble(ByteArrays.ofDouble(5.5d,    ByteOrder.BIG_ENDIAN), ByteOrder.LITTLE_ENDIAN)).isNotEqualTo(5.5d);
		assertThat(ByteArrays.toDouble(ByteArrays.ofDouble(5.5d, ByteOrder.LITTLE_ENDIAN),    ByteOrder.BIG_ENDIAN)).isNotEqualTo(5.5d);

		assertThat(ByteArrays.toDouble(ByteArrays.ofDouble(Double.MIN_VALUE,    ByteOrder.BIG_ENDIAN),    ByteOrder.BIG_ENDIAN)).isEqualTo(Double.MIN_VALUE);
		assertThat(ByteArrays.toDouble(ByteArrays.ofDouble(Double.MIN_VALUE, ByteOrder.LITTLE_ENDIAN), ByteOrder.LITTLE_ENDIAN)).isEqualTo(Double.MIN_VALUE);
		assertThat(ByteArrays.toDouble(ByteArrays.ofDouble(Double.MIN_VALUE,    ByteOrder.BIG_ENDIAN), ByteOrder.LITTLE_ENDIAN)).isNotEqualTo(Double.MIN_VALUE);
		assertThat(ByteArrays.toDouble(ByteArrays.ofDouble(Double.MIN_VALUE, ByteOrder.LITTLE_ENDIAN),    ByteOrder.BIG_ENDIAN)).isNotEqualTo(Double.MIN_VALUE);

		assertThat(ByteArrays.toDouble(ByteArrays.ofDouble(Double.NEGATIVE_INFINITY,    ByteOrder.BIG_ENDIAN),    ByteOrder.BIG_ENDIAN)).isEqualTo(Double.NEGATIVE_INFINITY);
		assertThat(ByteArrays.toDouble(ByteArrays.ofDouble(Double.NEGATIVE_INFINITY, ByteOrder.LITTLE_ENDIAN), ByteOrder.LITTLE_ENDIAN)).isEqualTo(Double.NEGATIVE_INFINITY);
		assertThat(ByteArrays.toDouble(ByteArrays.ofDouble(Double.NEGATIVE_INFINITY,    ByteOrder.BIG_ENDIAN), ByteOrder.LITTLE_ENDIAN)).isNotEqualTo(Double.NEGATIVE_INFINITY);
		assertThat(ByteArrays.toDouble(ByteArrays.ofDouble(Double.NEGATIVE_INFINITY, ByteOrder.LITTLE_ENDIAN),    ByteOrder.BIG_ENDIAN)).isNotEqualTo(Double.NEGATIVE_INFINITY);

		assertThat(ByteArrays.toDouble(ByteArrays.ofDouble(Double.MIN_NORMAL,    ByteOrder.BIG_ENDIAN),    ByteOrder.BIG_ENDIAN)).isEqualTo(Double.MIN_NORMAL);
		assertThat(ByteArrays.toDouble(ByteArrays.ofDouble(Double.MIN_NORMAL, ByteOrder.LITTLE_ENDIAN), ByteOrder.LITTLE_ENDIAN)).isEqualTo(Double.MIN_NORMAL);
		assertThat(ByteArrays.toDouble(ByteArrays.ofDouble(Double.MIN_NORMAL,    ByteOrder.BIG_ENDIAN), ByteOrder.LITTLE_ENDIAN)).isNotEqualTo(Double.MIN_NORMAL);
		assertThat(ByteArrays.toDouble(ByteArrays.ofDouble(Double.MIN_NORMAL, ByteOrder.LITTLE_ENDIAN),    ByteOrder.BIG_ENDIAN)).isNotEqualTo(Double.MIN_NORMAL);

		assertThat(ByteArrays.toDouble(ByteArrays.ofDouble(Double.POSITIVE_INFINITY,    ByteOrder.BIG_ENDIAN),    ByteOrder.BIG_ENDIAN)).isEqualTo(Double.POSITIVE_INFINITY);
		assertThat(ByteArrays.toDouble(ByteArrays.ofDouble(Double.POSITIVE_INFINITY, ByteOrder.LITTLE_ENDIAN), ByteOrder.LITTLE_ENDIAN)).isEqualTo(Double.POSITIVE_INFINITY);
		assertThat(ByteArrays.toDouble(ByteArrays.ofDouble(Double.POSITIVE_INFINITY,    ByteOrder.BIG_ENDIAN), ByteOrder.LITTLE_ENDIAN)).isNotEqualTo(Double.POSITIVE_INFINITY);
		assertThat(ByteArrays.toDouble(ByteArrays.ofDouble(Double.POSITIVE_INFINITY, ByteOrder.LITTLE_ENDIAN),    ByteOrder.BIG_ENDIAN)).isNotEqualTo(Double.POSITIVE_INFINITY);

		assertThat(ByteArrays.toDouble(ByteArrays.ofDouble(Double.MAX_VALUE,    ByteOrder.BIG_ENDIAN),    ByteOrder.BIG_ENDIAN)).isEqualTo(Double.MAX_VALUE);
		assertThat(ByteArrays.toDouble(ByteArrays.ofDouble(Double.MAX_VALUE, ByteOrder.LITTLE_ENDIAN), ByteOrder.LITTLE_ENDIAN)).isEqualTo(Double.MAX_VALUE);
		assertThat(ByteArrays.toDouble(ByteArrays.ofDouble(Double.MAX_VALUE,    ByteOrder.BIG_ENDIAN), ByteOrder.LITTLE_ENDIAN)).isNotEqualTo(Double.MAX_VALUE);
		assertThat(ByteArrays.toDouble(ByteArrays.ofDouble(Double.MAX_VALUE, ByteOrder.LITTLE_ENDIAN),    ByteOrder.BIG_ENDIAN)).isNotEqualTo(Double.MAX_VALUE);

		assertThat(ByteArrays.toDouble(ByteArrays.ofDouble(Double.NaN,    ByteOrder.BIG_ENDIAN),    ByteOrder.BIG_ENDIAN)).isEqualTo(Double.NaN);
		assertThat(ByteArrays.toDouble(ByteArrays.ofDouble(Double.NaN, ByteOrder.LITTLE_ENDIAN), ByteOrder.LITTLE_ENDIAN)).isEqualTo(Double.NaN);
		assertThat(ByteArrays.toDouble(ByteArrays.ofDouble(Double.NaN,    ByteOrder.BIG_ENDIAN), ByteOrder.LITTLE_ENDIAN)).isNotEqualTo(Double.NaN);
		assertThat(ByteArrays.toDouble(ByteArrays.ofDouble(Double.NaN, ByteOrder.LITTLE_ENDIAN),    ByteOrder.BIG_ENDIAN)).isNotEqualTo(Double.NaN);
	}

	@Test
	void testOfDoubleNull() {
		assertThatNullPointerException().isThrownBy(() -> ByteArrays.ofDouble(0.0d, null));
	}

	@Test
	void testToDoubleNull() {
		assertThatNullPointerException().isThrownBy(() -> ByteArrays.toDouble(null));
		assertThatNullPointerException().isThrownBy(() -> ByteArrays.toDouble(ByteArrays.EMPTY, null));
	}

	@Test
	void testToDoubleInvalid() {
		assertThatIllegalArgumentException().isThrownBy(() -> ByteArrays.toDouble(ByteArrays.EMPTY));
	}

	@Test
	void testOfHexString() {
		assertThat(ByteArrays.ofHexString(    "")).isEqualTo(ByteArrays.EMPTY);
		assertThat(ByteArrays.ofHexString(  "00")).isEqualTo(ByteArrays.of((byte) 0x00));
		assertThat(ByteArrays.ofHexString(  "ff")).isEqualTo(ByteArrays.of((byte) 0xff));
		assertThat(ByteArrays.ofHexString(  "FF")).isEqualTo(ByteArrays.of((byte) 0xff));
		assertThat(ByteArrays.ofHexString("0xff")).isEqualTo(ByteArrays.of((byte) 0xff));
	}

	@Test
	void testOfHexStringNull() {
		assertThatNullPointerException().isThrownBy(() -> ByteArrays.ofHexString(null));
	}

	@Test
	void testOfHexStringInvalid() {
		assertThatIllegalArgumentException().isThrownBy(() -> ByteArrays.ofHexString("0"));
		assertThatIllegalArgumentException().isThrownBy(() -> ByteArrays.ofHexString("0x"));
		assertThatIllegalArgumentException().isThrownBy(() -> ByteArrays.ofHexString("x0"));
	}

	@Test
	void testToHexString() {
		assertThat(ByteArrays.toHexString(ByteArrays.EMPTY)).isEmpty();
		assertThat(ByteArrays.toHexString(ByteArrays.of((byte)   0))).isEqualTo("00");
		assertThat(ByteArrays.toHexString(ByteArrays.of((byte) 255))).isEqualTo("ff");
	}

	@Test
	void testToHexStringNull() {
		assertThatNullPointerException().isThrownBy(() -> ByteArrays.toHexString(null));
	}
}