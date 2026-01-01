/*
 * MIT License
 *
 * Copyright (c) 2018-2026 Alexis Jehan
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
package com.github.alexisjehan.javanilla.misc.quality;

import com.github.alexisjehan.javanilla.lang.array.BooleanArrays;
import com.github.alexisjehan.javanilla.lang.array.ByteArrays;
import com.github.alexisjehan.javanilla.lang.array.CharArrays;
import com.github.alexisjehan.javanilla.lang.array.DoubleArrays;
import com.github.alexisjehan.javanilla.lang.array.FloatArrays;
import com.github.alexisjehan.javanilla.lang.array.IntArrays;
import com.github.alexisjehan.javanilla.lang.array.LongArrays;
import com.github.alexisjehan.javanilla.lang.array.ObjectArrays;
import com.github.alexisjehan.javanilla.lang.array.ShortArrays;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

final class EqualsTest {

	@Test
	void testEqualsBoolean() {
		assertThat(Equals.equals(true, true)).isTrue();
		assertThat(Equals.equals(true, false)).isFalse();
	}

	@Test
	void testEqualsByte() {
		assertThat(Equals.equals((byte) 1, (byte) 1)).isTrue();
		assertThat(Equals.equals((byte) 1, (byte) 2)).isFalse();
	}

	@Test
	void testEqualsShort() {
		assertThat(Equals.equals((short) 1, (short) 1)).isTrue();
		assertThat(Equals.equals((short) 1, (short) 2)).isFalse();
	}

	@Test
	void testEqualsChar() {
		assertThat(Equals.equals('a', 'a')).isTrue();
		assertThat(Equals.equals('a', 'b')).isFalse();
	}

	@Test
	void testEqualsInt() {
		assertThat(Equals.equals(1, 1)).isTrue();
		assertThat(Equals.equals(1, 2)).isFalse();
	}

	@Test
	void testEqualsLong() {
		assertThat(Equals.equals(1L, 1L)).isTrue();
		assertThat(Equals.equals(1L, 2L)).isFalse();
	}

	@Test
	void testEqualsFloat() {
		assertThat(Equals.equals(1.0f, 1.0f)).isTrue();
		assertThat(Equals.equals(1.0f, 2.0f)).isFalse();
	}

	@Test
	void testEqualsDouble() {
		assertThat(Equals.equals(1.0d, 1.0d)).isTrue();
		assertThat(Equals.equals(1.0d, 2.0d)).isFalse();
	}

	@Test
	void testEqualsObject() {
		assertThat(Equals.equals((Integer) null, null)).isTrue();
		assertThat(Equals.equals(1, null)).isFalse();
		assertThat(Equals.equals(null, 1)).isFalse();
		assertThat(Equals.equals(Integer.valueOf(1), Integer.valueOf(1))).isTrue();
		assertThat(Equals.equals(Integer.valueOf(1), Integer.valueOf(2))).isFalse();
	}

	@Test
	void testEqualsBooleanArray() {
		assertThat(Equals.equals((boolean[]) null, null)).isTrue();
		assertThat(Equals.equals(BooleanArrays.singleton(true), null)).isFalse();
		assertThat(Equals.equals(null, BooleanArrays.singleton(true))).isFalse();
		assertThat(Equals.equals(BooleanArrays.singleton(true), BooleanArrays.singleton(true))).isTrue();
		assertThat(Equals.equals(BooleanArrays.singleton(true), BooleanArrays.of(true, false))).isFalse();
	}

	@Test
	void testEqualsByteArray() {
		assertThat(Equals.equals((byte[]) null, null)).isTrue();
		assertThat(Equals.equals(ByteArrays.singleton((byte) 1), null)).isFalse();
		assertThat(Equals.equals(null, ByteArrays.singleton((byte) 1))).isFalse();
		assertThat(Equals.equals(ByteArrays.singleton((byte) 1), ByteArrays.singleton((byte) 1))).isTrue();
		assertThat(Equals.equals(ByteArrays.singleton((byte) 1), ByteArrays.of((byte) 1, (byte) 2))).isFalse();
	}

	@Test
	void testEqualsShortArray() {
		assertThat(Equals.equals((short[]) null, null)).isTrue();
		assertThat(Equals.equals(ShortArrays.singleton((short) 1), null)).isFalse();
		assertThat(Equals.equals(null, ShortArrays.singleton((short) 1))).isFalse();
		assertThat(Equals.equals(ShortArrays.singleton((short) 1), ShortArrays.singleton((short) 1))).isTrue();
		assertThat(Equals.equals(ShortArrays.singleton((short) 1), ShortArrays.of((short) 1, (short) 2))).isFalse();
	}

	@Test
	void testEqualsCharArray() {
		assertThat(Equals.equals((char[]) null, null)).isTrue();
		assertThat(Equals.equals(CharArrays.singleton('a'), null)).isFalse();
		assertThat(Equals.equals(null, CharArrays.singleton('a'))).isFalse();
		assertThat(Equals.equals(CharArrays.singleton('a'), CharArrays.singleton('a'))).isTrue();
		assertThat(Equals.equals(CharArrays.singleton('a'), CharArrays.of('a', 'b'))).isFalse();
	}

	@Test
	void testEqualsIntArray() {
		assertThat(Equals.equals((int[]) null, null)).isTrue();
		assertThat(Equals.equals(IntArrays.singleton(1), null)).isFalse();
		assertThat(Equals.equals(null, IntArrays.singleton(1))).isFalse();
		assertThat(Equals.equals(IntArrays.singleton(1), IntArrays.singleton(1))).isTrue();
		assertThat(Equals.equals(IntArrays.singleton(1), IntArrays.of(1, 2))).isFalse();
	}

	@Test
	void testEqualsLongArray() {
		assertThat(Equals.equals((long[]) null, null)).isTrue();
		assertThat(Equals.equals(LongArrays.singleton(1L), null)).isFalse();
		assertThat(Equals.equals(null, LongArrays.singleton(1L))).isFalse();
		assertThat(Equals.equals(LongArrays.singleton(1L), LongArrays.singleton(1L))).isTrue();
		assertThat(Equals.equals(LongArrays.singleton(1L), LongArrays.of(1L, 2L))).isFalse();
	}

	@Test
	void testEqualsFloatArray() {
		assertThat(Equals.equals((float[]) null, null)).isTrue();
		assertThat(Equals.equals(FloatArrays.singleton(1.0f), null)).isFalse();
		assertThat(Equals.equals(null, FloatArrays.singleton(1.0f))).isFalse();
		assertThat(Equals.equals(FloatArrays.singleton(1.0f), FloatArrays.singleton(1.0f))).isTrue();
		assertThat(Equals.equals(FloatArrays.singleton(1.0f), FloatArrays.of(1.0f, 2.0f))).isFalse();
	}

	@Test
	void testEqualsDoubleArray() {
		assertThat(Equals.equals((double[]) null, null)).isTrue();
		assertThat(Equals.equals(DoubleArrays.singleton(1.0d), null)).isFalse();
		assertThat(Equals.equals(null, DoubleArrays.singleton(1.0d))).isFalse();
		assertThat(Equals.equals(DoubleArrays.singleton(1.0d), DoubleArrays.singleton(1.0d))).isTrue();
		assertThat(Equals.equals(DoubleArrays.singleton(1.0d), DoubleArrays.of(1.0d, 2.0d))).isFalse();
	}

	@Test
	void testEqualsObjectArray() {
		assertThat(Equals.equals((Integer[]) null, null)).isTrue();
		assertThat(Equals.equals(ObjectArrays.singleton(1), null)).isFalse();
		assertThat(Equals.equals(null, ObjectArrays.singleton(1))).isFalse();
		assertThat(Equals.equals(ObjectArrays.singleton(1), ObjectArrays.singleton(1))).isTrue();
		assertThat(Equals.equals(ObjectArrays.singleton(1), ObjectArrays.of(1, 2))).isFalse();
	}
}