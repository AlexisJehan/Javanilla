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
package com.github.alexisjehan.javanilla.util;

import static org.assertj.core.api.Assertions.*;

import java.util.Comparator;

import com.github.alexisjehan.javanilla.lang.array.*;
import org.junit.jupiter.api.Test;

/**
 * <p>{@link Comparators} unit tests.</p>
 */
final class ComparatorsTest {

	@Test
	void testNumberAware() {
		assertThat(Comparator.<String>naturalOrder().compare("foo1", "foo1")).isEqualTo(Comparators.NUMBER_AWARE.compare("foo1", "foo1"));
		assertThat(Comparator.<String>naturalOrder().compare("foo1", "foo2")).isEqualTo(Comparators.NUMBER_AWARE.compare("foo1", "foo2"));

		// Different result compared to natural order
		assertThat(Comparator.<String>naturalOrder().compare("foo11", "foo2")).isEqualTo(-1);
		assertThat(Comparators.NUMBER_AWARE.compare("foo11", "foo2")).isEqualTo(1);

		assertThat(Comparators.NUMBER_AWARE.compare("", "foo1")).isEqualTo(-1);
		assertThat(Comparators.NUMBER_AWARE.compare("", "")).isEqualTo(0);
		assertThat(Comparators.NUMBER_AWARE.compare("foo2", "")).isEqualTo(1);

		assertThat(Comparators.NUMBER_AWARE.compare("foo12", "fooabc")).isEqualTo(-1);
		assertThat(Comparators.NUMBER_AWARE.compare("foode", "foo345")).isEqualTo(1);
		assertThat(Comparators.NUMBER_AWARE.compare("foo12", "foo*-+")).isEqualTo(1);
		assertThat(Comparators.NUMBER_AWARE.compare("foo#$", "foo345")).isEqualTo(-1);

		assertThat(Comparators.NUMBER_AWARE.compare("foo0a0", "foo0a1")).isEqualTo(-1);
		assertThat(Comparators.NUMBER_AWARE.compare("foo0a1", "foo0a0")).isEqualTo(1);
		assertThat(Comparators.NUMBER_AWARE.compare("foo0*0", "foo0*1")).isEqualTo(-1);
		assertThat(Comparators.NUMBER_AWARE.compare("foo0*1", "foo0*0")).isEqualTo(1);

		assertThat(Comparators.NUMBER_AWARE.compare("foo010", "foo010")).isEqualTo(0);
		assertThat(Comparators.NUMBER_AWARE.compare("foo010a", "foo010")).isEqualTo(1);
		assertThat(Comparators.NUMBER_AWARE.compare("foo010", "foo010a")).isEqualTo(-1);
	}

	@Test
	void testNumberAwareInvalid() {
		assertThatNullPointerException().isThrownBy(() -> Comparators.NUMBER_AWARE.compare("foo1", null));
		assertThatNullPointerException().isThrownBy(() -> Comparators.NUMBER_AWARE.compare(null, "foo2"));
	}

	@Test
	void testBooleanArrays() {
		assertThat(Comparators.BOOLEAN_ARRAYS.compare(BooleanArrays.EMPTY, BooleanArrays.EMPTY)).isEqualTo(0);
		assertThat(Comparators.BOOLEAN_ARRAYS.compare(BooleanArrays.of(true, false), BooleanArrays.EMPTY)).isEqualTo(1);
		assertThat(Comparators.BOOLEAN_ARRAYS.compare(BooleanArrays.EMPTY, BooleanArrays.of(false, true))).isEqualTo(-1);
		assertThat(Comparators.BOOLEAN_ARRAYS.compare(BooleanArrays.of(true, false), BooleanArrays.of(true, false))).isEqualTo(0);
		assertThat(Comparators.BOOLEAN_ARRAYS.compare(BooleanArrays.of(false, false), BooleanArrays.of(false, true))).isEqualTo(-1);
		assertThat(Comparators.BOOLEAN_ARRAYS.compare(BooleanArrays.of(false, false), BooleanArrays.of(false, false, false))).isEqualTo(-1);
		assertThat(Comparators.BOOLEAN_ARRAYS.compare(BooleanArrays.of(true, false), BooleanArrays.of(false, false, false))).isEqualTo(1);
	}

	@Test
	void testBooleanArraysInvalid() {
		assertThatNullPointerException().isThrownBy(() -> Comparators.BOOLEAN_ARRAYS.compare(BooleanArrays.of(true, false), null));
		assertThatNullPointerException().isThrownBy(() -> Comparators.BOOLEAN_ARRAYS.compare(null, BooleanArrays.of(false, true)));
	}

	@Test
	void testSignedByteArrays() {
		assertThat(Comparators.SIGNED_BYTE_ARRAYS.compare(ByteArrays.EMPTY, ByteArrays.EMPTY)).isEqualTo(0);
		assertThat(Comparators.SIGNED_BYTE_ARRAYS.compare(ByteArrays.of((byte) 5, (byte) -2), ByteArrays.EMPTY)).isEqualTo(1);
		assertThat(Comparators.SIGNED_BYTE_ARRAYS.compare(ByteArrays.EMPTY, ByteArrays.of((byte) -2, (byte) 5))).isEqualTo(-1);
		assertThat(Comparators.SIGNED_BYTE_ARRAYS.compare(ByteArrays.of((byte) 5, (byte) -2), ByteArrays.of((byte) 5, (byte) -2))).isEqualTo(0);
		assertThat(Comparators.SIGNED_BYTE_ARRAYS.compare(ByteArrays.of((byte) -2, (byte) -2), ByteArrays.of((byte) -2, (byte) 5))).isEqualTo(-1);
		assertThat(Comparators.SIGNED_BYTE_ARRAYS.compare(ByteArrays.of((byte) -2, (byte) -2), ByteArrays.of((byte) -2, (byte) -2, (byte) -2))).isEqualTo(-1);
		assertThat(Comparators.SIGNED_BYTE_ARRAYS.compare(ByteArrays.of((byte) 5, (byte) -2), ByteArrays.of((byte) -2, (byte) -2, (byte) -2))).isEqualTo(1);
		assertThat(Comparators.SIGNED_BYTE_ARRAYS.compare(ByteArrays.of(Byte.MAX_VALUE), ByteArrays.of(Byte.MIN_VALUE))).isEqualTo(1);
	}

	@Test
	void testSignedByteArraysInvalid() {
		assertThatNullPointerException().isThrownBy(() -> Comparators.SIGNED_BYTE_ARRAYS.compare(ByteArrays.of((byte) 5, (byte) -2), null));
		assertThatNullPointerException().isThrownBy(() -> Comparators.SIGNED_BYTE_ARRAYS.compare(null, ByteArrays.of((byte) -2, (byte) 5)));
	}

	@Test
	void testUnsignedByteArrays() {
		assertThat(Comparators.UNSIGNED_BYTE_ARRAYS.compare(ByteArrays.EMPTY, ByteArrays.EMPTY)).isEqualTo(0);
		assertThat(Comparators.UNSIGNED_BYTE_ARRAYS.compare(ByteArrays.of((byte) 5, (byte) 2), ByteArrays.EMPTY)).isEqualTo(1);
		assertThat(Comparators.UNSIGNED_BYTE_ARRAYS.compare(ByteArrays.EMPTY, ByteArrays.of((byte) 2, (byte) 5))).isEqualTo(-1);
		assertThat(Comparators.UNSIGNED_BYTE_ARRAYS.compare(ByteArrays.of((byte) 5, (byte) 2), ByteArrays.of((byte) 5, (byte) 2))).isEqualTo(0);
		assertThat(Comparators.UNSIGNED_BYTE_ARRAYS.compare(ByteArrays.of((byte) 2, (byte) 2), ByteArrays.of((byte) 2, (byte) 5))).isEqualTo(-1);
		assertThat(Comparators.UNSIGNED_BYTE_ARRAYS.compare(ByteArrays.of((byte) 2, (byte) 2), ByteArrays.of((byte) 2, (byte) 2, (byte) 2))).isEqualTo(-1);
		assertThat(Comparators.UNSIGNED_BYTE_ARRAYS.compare(ByteArrays.of((byte) 5, (byte) 2), ByteArrays.of((byte) 2, (byte) 2, (byte) 2))).isEqualTo(1);
		assertThat(Comparators.UNSIGNED_BYTE_ARRAYS.compare(ByteArrays.of(Byte.MAX_VALUE), ByteArrays.of((byte) 0))).isEqualTo(1);
		assertThat(Comparators.UNSIGNED_BYTE_ARRAYS.compare(ByteArrays.of(Byte.MAX_VALUE), ByteArrays.of(Byte.MIN_VALUE))).isNotEqualTo(1);
	}

	@Test
	void testUnsignedByteArraysInvalid() {
		assertThatNullPointerException().isThrownBy(() -> Comparators.UNSIGNED_BYTE_ARRAYS.compare(ByteArrays.of((byte) 5, (byte) 2), null));
		assertThatNullPointerException().isThrownBy(() -> Comparators.UNSIGNED_BYTE_ARRAYS.compare(null, ByteArrays.of((byte) 2, (byte) 5)));
	}

	@Test
	void testShortArrays() {
		assertThat(Comparators.SHORT_ARRAYS.compare(ShortArrays.EMPTY, ShortArrays.EMPTY)).isEqualTo(0);
		assertThat(Comparators.SHORT_ARRAYS.compare(ShortArrays.of((short) 5, (short) -2), ShortArrays.EMPTY)).isEqualTo(1);
		assertThat(Comparators.SHORT_ARRAYS.compare(ShortArrays.EMPTY, ShortArrays.of((short) -2, (short) 5))).isEqualTo(-1);
		assertThat(Comparators.SHORT_ARRAYS.compare(ShortArrays.of((short) 5, (short) -2), ShortArrays.of((short) 5, (short) -2))).isEqualTo(0);
		assertThat(Comparators.SHORT_ARRAYS.compare(ShortArrays.of((short) -2, (short) -2), ShortArrays.of((short) -2, (short) 5))).isEqualTo(-1);
		assertThat(Comparators.SHORT_ARRAYS.compare(ShortArrays.of((short) -2, (short) -2), ShortArrays.of((short) -2, (short) -2, (short) -2))).isEqualTo(-1);
		assertThat(Comparators.SHORT_ARRAYS.compare(ShortArrays.of((short) 5, (short) -2), ShortArrays.of((short) -2, (short) -2, (short) -2))).isEqualTo(1);
		assertThat(Comparators.SHORT_ARRAYS.compare(ShortArrays.of(Short.MAX_VALUE), ShortArrays.of(Short.MIN_VALUE))).isEqualTo(1);
	}

	@Test
	void testShortArraysInvalid() {
		assertThatNullPointerException().isThrownBy(() -> Comparators.SHORT_ARRAYS.compare(ShortArrays.of((short) 5, (short) -2), null));
		assertThatNullPointerException().isThrownBy(() -> Comparators.SHORT_ARRAYS.compare(null, ShortArrays.of((short) -2, (short) 5)));
	}

	@Test
	void testIntArrays() {
		assertThat(Comparators.INT_ARRAYS.compare(IntArrays.EMPTY, IntArrays.EMPTY)).isEqualTo(0);
		assertThat(Comparators.INT_ARRAYS.compare(IntArrays.of(5, -2), IntArrays.EMPTY)).isEqualTo(1);
		assertThat(Comparators.INT_ARRAYS.compare(IntArrays.EMPTY, IntArrays.of(-2, 5))).isEqualTo(-1);
		assertThat(Comparators.INT_ARRAYS.compare(IntArrays.of(5, -2), IntArrays.of(5, -2))).isEqualTo(0);
		assertThat(Comparators.INT_ARRAYS.compare(IntArrays.of(-2, -2), IntArrays.of(-2, 5))).isEqualTo(-1);
		assertThat(Comparators.INT_ARRAYS.compare(IntArrays.of(-2, -2), IntArrays.of(-2, -2, -2))).isEqualTo(-1);
		assertThat(Comparators.INT_ARRAYS.compare(IntArrays.of(5, -2), IntArrays.of(-2, -2, -2))).isEqualTo(1);
		assertThat(Comparators.INT_ARRAYS.compare(IntArrays.of(Integer.MAX_VALUE), IntArrays.of(Integer.MIN_VALUE))).isEqualTo(1);
	}

	@Test
	void testIntArraysInvalid() {
		assertThatNullPointerException().isThrownBy(() -> Comparators.INT_ARRAYS.compare(IntArrays.of(5, -2), null));
		assertThatNullPointerException().isThrownBy(() -> Comparators.INT_ARRAYS.compare(null, IntArrays.of(-2, 5)));
	}

	@Test
	void testLongArrays() {
		assertThat(Comparators.LONG_ARRAYS.compare(LongArrays.EMPTY, LongArrays.EMPTY)).isEqualTo(0);
		assertThat(Comparators.LONG_ARRAYS.compare(LongArrays.of(5L, -2L), LongArrays.EMPTY)).isEqualTo(1);
		assertThat(Comparators.LONG_ARRAYS.compare(LongArrays.EMPTY, LongArrays.of(-2L, 5L))).isEqualTo(-1);
		assertThat(Comparators.LONG_ARRAYS.compare(LongArrays.of(5L, -2L), LongArrays.of(5L, -2L))).isEqualTo(0);
		assertThat(Comparators.LONG_ARRAYS.compare(LongArrays.of(-2L, -2L), LongArrays.of(-2L, 5L))).isEqualTo(-1);
		assertThat(Comparators.LONG_ARRAYS.compare(LongArrays.of(-2L, -2L), LongArrays.of(-2L, -2L, -2L))).isEqualTo(-1);
		assertThat(Comparators.LONG_ARRAYS.compare(LongArrays.of(5L, -2L), LongArrays.of(-2L, -2L, -2L))).isEqualTo(1);
		assertThat(Comparators.LONG_ARRAYS.compare(LongArrays.of(Long.MAX_VALUE), LongArrays.of(Long.MIN_VALUE))).isEqualTo(1);
	}

	@Test
	void testLongArraysInvalid() {
		assertThatNullPointerException().isThrownBy(() -> Comparators.LONG_ARRAYS.compare(LongArrays.of(5L, -2L), null));
		assertThatNullPointerException().isThrownBy(() -> Comparators.LONG_ARRAYS.compare(null, LongArrays.of(-2L, 5L)));
	}

	@Test
	void testFloatArrays() {
		assertThat(Comparators.FLOAT_ARRAYS.compare(FloatArrays.EMPTY, FloatArrays.EMPTY)).isEqualTo(0);
		assertThat(Comparators.FLOAT_ARRAYS.compare(FloatArrays.of(0.5f, -0.2f), FloatArrays.EMPTY)).isEqualTo(1);
		assertThat(Comparators.FLOAT_ARRAYS.compare(FloatArrays.EMPTY, FloatArrays.of(-0.2f, 0.5f))).isEqualTo(-1);
		assertThat(Comparators.FLOAT_ARRAYS.compare(FloatArrays.of(0.5f, -0.2f), FloatArrays.of(0.5f, -0.2f))).isEqualTo(0);
		assertThat(Comparators.FLOAT_ARRAYS.compare(FloatArrays.of(-0.2f, -0.2f), FloatArrays.of(-0.2f, 0.5f))).isEqualTo(-1);
		assertThat(Comparators.FLOAT_ARRAYS.compare(FloatArrays.of(-0.2f, -0.2f), FloatArrays.of(-0.2f, -0.2f, -0.2f))).isEqualTo(-1);
		assertThat(Comparators.FLOAT_ARRAYS.compare(FloatArrays.of(0.5f, -0.2f), FloatArrays.of(-0.2f, -0.2f, -0.2f))).isEqualTo(1);
		assertThat(Comparators.FLOAT_ARRAYS.compare(FloatArrays.of(Float.MAX_VALUE), FloatArrays.of(Float.MIN_VALUE))).isEqualTo(1);
		assertThat(Comparators.FLOAT_ARRAYS.compare(FloatArrays.of(Float.MIN_NORMAL), FloatArrays.of(Float.MIN_VALUE))).isEqualTo(1);
		assertThat(Comparators.FLOAT_ARRAYS.compare(FloatArrays.of(Float.MIN_NORMAL), FloatArrays.of(Float.MAX_VALUE))).isEqualTo(-1);
		assertThat(Comparators.FLOAT_ARRAYS.compare(FloatArrays.of(Float.POSITIVE_INFINITY), FloatArrays.of(Float.MAX_VALUE))).isEqualTo(1);
		assertThat(Comparators.FLOAT_ARRAYS.compare(FloatArrays.of(Float.NaN), FloatArrays.of(Float.NaN))).isEqualTo(0);
	}

	@Test
	void testFloatArraysInvalid() {
		assertThatNullPointerException().isThrownBy(() -> Comparators.FLOAT_ARRAYS.compare(FloatArrays.of(0.5f, -0.2f), null));
		assertThatNullPointerException().isThrownBy(() -> Comparators.FLOAT_ARRAYS.compare(null, FloatArrays.of(-0.2f, 0.5f)));
	}

	@Test
	void testDoubleArrays() {
		assertThat(Comparators.DOUBLE_ARRAYS.compare(DoubleArrays.EMPTY, DoubleArrays.EMPTY)).isEqualTo(0);
		assertThat(Comparators.DOUBLE_ARRAYS.compare(DoubleArrays.of(0.5d, -0.2d), DoubleArrays.EMPTY)).isEqualTo(1);
		assertThat(Comparators.DOUBLE_ARRAYS.compare(DoubleArrays.EMPTY, DoubleArrays.of(-0.2d, 0.5d))).isEqualTo(-1);
		assertThat(Comparators.DOUBLE_ARRAYS.compare(DoubleArrays.of(0.5d, -0.2d), DoubleArrays.of(0.5d, -0.2d))).isEqualTo(0);
		assertThat(Comparators.DOUBLE_ARRAYS.compare(DoubleArrays.of(-0.2d, -0.2d), DoubleArrays.of(-0.2d, 0.5d))).isEqualTo(-1);
		assertThat(Comparators.DOUBLE_ARRAYS.compare(DoubleArrays.of(-0.2d, -0.2d), DoubleArrays.of(-0.2d, -0.2d, -0.2d))).isEqualTo(-1);
		assertThat(Comparators.DOUBLE_ARRAYS.compare(DoubleArrays.of(0.5d, -0.2d), DoubleArrays.of(-0.2d, -0.2d, -0.2d))).isEqualTo(1);
		assertThat(Comparators.DOUBLE_ARRAYS.compare(DoubleArrays.of(Double.MAX_VALUE), DoubleArrays.of(Double.MIN_VALUE))).isEqualTo(1);
		assertThat(Comparators.DOUBLE_ARRAYS.compare(DoubleArrays.of(Double.MIN_NORMAL), DoubleArrays.of(Double.MIN_VALUE))).isEqualTo(1);
		assertThat(Comparators.DOUBLE_ARRAYS.compare(DoubleArrays.of(Double.MIN_NORMAL), DoubleArrays.of(Double.MAX_VALUE))).isEqualTo(-1);
		assertThat(Comparators.DOUBLE_ARRAYS.compare(DoubleArrays.of(Double.POSITIVE_INFINITY), DoubleArrays.of(Double.MAX_VALUE))).isEqualTo(1);
		assertThat(Comparators.DOUBLE_ARRAYS.compare(DoubleArrays.of(Double.NaN), DoubleArrays.of(Double.NaN))).isEqualTo(0);
	}

	@Test
	void testDoubleArraysInvalid() {
		assertThatNullPointerException().isThrownBy(() -> Comparators.DOUBLE_ARRAYS.compare(DoubleArrays.of(0.5d, -0.2d), null));
		assertThatNullPointerException().isThrownBy(() -> Comparators.DOUBLE_ARRAYS.compare(null, DoubleArrays.of(-0.2d, 0.5d)));
	}

	@Test
	void testArrayComparable() {
		assertThat(Comparators.<Integer>array().compare(ObjectArrays.empty(Integer.class), ObjectArrays.empty(Integer.class))).isEqualTo(0);
		assertThat(Comparators.<Integer>array().compare(ObjectArrays.of(5, -2), ObjectArrays.empty(Integer.class))).isEqualTo(1);
		assertThat(Comparators.<Integer>array().compare(ObjectArrays.empty(Integer.class), ObjectArrays.of(-2, 5))).isEqualTo(-1);
		assertThat(Comparators.<Integer>array().compare(ObjectArrays.of(5, -2), ObjectArrays.of(5, -2))).isEqualTo(0);
		assertThat(Comparators.<Integer>array().compare(ObjectArrays.of(-2, -2), ObjectArrays.of(-2, 5))).isEqualTo(-1);
		assertThat(Comparators.<Integer>array().compare(ObjectArrays.of(-2, -2), ObjectArrays.of(-2, -2, -2))).isEqualTo(-1);
		assertThat(Comparators.<Integer>array().compare(ObjectArrays.of(5, -2), ObjectArrays.of(-2, -2, -2))).isEqualTo(1);
		assertThat(Comparators.<Integer>array().compare(ObjectArrays.of(Integer.MAX_VALUE), ObjectArrays.of(Integer.MIN_VALUE))).isEqualTo(1);
	}

	@Test
	void testArrayComparableInvalid() {
		assertThatNullPointerException().isThrownBy(() -> Comparators.<Integer>array().compare(ObjectArrays.of(5, -2), null));
		assertThatNullPointerException().isThrownBy(() -> Comparators.<Integer>array().compare(null, ObjectArrays.of(-2, 5)));
	}

	@Test
	void testArrayComparator() {
		// The given Comparator is not used when one of both array is empty, so same results are expected
		assertThat(Comparators.<Integer>array(Comparator.reverseOrder()).compare(ObjectArrays.empty(Integer.class), ObjectArrays.empty(Integer.class))).isEqualTo(0);
		assertThat(Comparators.<Integer>array(Comparator.reverseOrder()).compare(ObjectArrays.of(5, -2), ObjectArrays.empty(Integer.class))).isEqualTo(1);
		assertThat(Comparators.<Integer>array(Comparator.reverseOrder()).compare(ObjectArrays.empty(Integer.class), ObjectArrays.of(-2, 5))).isEqualTo(-1);

		// A reversed Comparator gives same results with same values
		assertThat(Comparators.<Integer>array(Comparator.reverseOrder()).compare(ObjectArrays.of(5, -2), ObjectArrays.of(5, -2))).isEqualTo(0);

		assertThat(Comparators.<Integer>array(Comparator.reverseOrder()).compare(ObjectArrays.of(-2, -2), ObjectArrays.of(-2, 5))).isNotEqualTo(-1);

		// Same values but different lengths, so same results are expected with a reversed Comparator
		assertThat(Comparators.<Integer>array(Comparator.reverseOrder()).compare(ObjectArrays.of(-2, -2), ObjectArrays.of(-2, -2, -2))).isEqualTo(-1);

		assertThat(Comparators.<Integer>array(Comparator.reverseOrder()).compare(ObjectArrays.of(5, -2), ObjectArrays.of(-2, -2, -2))).isNotEqualTo(1);
		assertThat(Comparators.<Integer>array(Comparator.reverseOrder()).compare(ObjectArrays.of(Integer.MAX_VALUE), ObjectArrays.of(Integer.MIN_VALUE))).isNotEqualTo(1);
	}

	@Test
	void testArrayComparatorInvalid() {
		assertThatNullPointerException().isThrownBy(() -> Comparators.<Integer>array(Comparator.reverseOrder()).compare(ObjectArrays.of(5, -2), null));
		assertThatNullPointerException().isThrownBy(() -> Comparators.<Integer>array(Comparator.reverseOrder()).compare(null, ObjectArrays.of(-2, 5)));
	}
}