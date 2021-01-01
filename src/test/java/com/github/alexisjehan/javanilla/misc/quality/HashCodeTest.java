/*
 * MIT License
 *
 * Copyright (c) 2018-2021 Alexis Jehan
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
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.assertj.core.api.Assertions.assertThatNullPointerException;

/**
 * <p>{@link Equals} unit tests.</p>
 */
final class HashCodeTest {

	@Test
	void testHashCodeBoolean() {
		assertThat(HashCode.hashCode(true)).isEqualTo(HashCode.hashCode(true));
		assertThat(HashCode.hashCode(true)).isNotEqualTo(HashCode.hashCode(false));
	}

	@Test
	void testHashCodeByte() {
		assertThat(HashCode.hashCode((byte) 1)).isEqualTo(HashCode.hashCode((byte) 1));
		assertThat(HashCode.hashCode((byte) 1)).isNotEqualTo(HashCode.hashCode((byte) 2));
	}

	@Test
	void testHashCodeShort() {
		assertThat(HashCode.hashCode((short) 1)).isEqualTo(HashCode.hashCode((short) 1));
		assertThat(HashCode.hashCode((short) 1)).isNotEqualTo(HashCode.hashCode((short) 2));
	}

	@Test
	void testHashCodeChar() {
		assertThat(HashCode.hashCode('a')).isEqualTo(HashCode.hashCode('a'));
		assertThat(HashCode.hashCode('a')).isNotEqualTo(HashCode.hashCode('b'));
	}

	@Test
	void testHashCodeInt() {
		assertThat(HashCode.hashCode(1)).isEqualTo(HashCode.hashCode(1));
		assertThat(HashCode.hashCode(1)).isNotEqualTo(HashCode.hashCode(2));
	}

	@Test
	void testHashCodeLong() {
		assertThat(HashCode.hashCode(1L)).isEqualTo(HashCode.hashCode(1L));
		assertThat(HashCode.hashCode(1L)).isNotEqualTo(HashCode.hashCode(2L));
	}

	@Test
	void testHashCodeFloat() {
		assertThat(HashCode.hashCode(1.0f)).isEqualTo(HashCode.hashCode(1.0f));
		assertThat(HashCode.hashCode(1.0f)).isNotEqualTo(HashCode.hashCode(2.0f));
	}

	@Test
	void testHashCodeDouble() {
		assertThat(HashCode.hashCode(1.0d)).isEqualTo(HashCode.hashCode(1.0d));
		assertThat(HashCode.hashCode(1.0d)).isNotEqualTo(HashCode.hashCode(2.0d));
	}

	@Test
	void testHashCodeObject() {
		assertThat(HashCode.hashCode((Integer) null)).isEqualTo(HashCode.hashCode((Integer) null));
		assertThat(HashCode.hashCode(Integer.valueOf(1))).isNotEqualTo(HashCode.hashCode((Integer) null));
		assertThat(HashCode.hashCode((Integer) null)).isNotEqualTo(HashCode.hashCode(Integer.valueOf(1)));
		assertThat(HashCode.hashCode(Integer.valueOf(1))).isEqualTo(HashCode.hashCode(Integer.valueOf(1)));
		assertThat(HashCode.hashCode(Integer.valueOf(1))).isNotEqualTo(HashCode.hashCode(Integer.valueOf(2)));
	}

	@Test
	void testHashCodeBooleanArray() {
		assertThat(HashCode.hashCode((boolean[]) null)).isEqualTo(HashCode.hashCode((boolean[]) null));
		assertThat(HashCode.hashCode(BooleanArrays.singleton(true))).isNotEqualTo(HashCode.hashCode((boolean[]) null));
		assertThat(HashCode.hashCode((boolean[]) null)).isNotEqualTo(HashCode.hashCode(BooleanArrays.singleton(true)));
		assertThat(HashCode.hashCode(BooleanArrays.singleton(true))).isEqualTo(HashCode.hashCode(BooleanArrays.singleton(true)));
		assertThat(HashCode.hashCode(BooleanArrays.singleton(true))).isNotEqualTo(HashCode.hashCode(BooleanArrays.of(true, false)));
	}

	@Test
	void testHashCodeByteArray() {
		assertThat(HashCode.hashCode((byte[]) null)).isEqualTo(HashCode.hashCode((byte[]) null));
		assertThat(HashCode.hashCode(ByteArrays.singleton((byte) 1))).isNotEqualTo(HashCode.hashCode((byte[]) null));
		assertThat(HashCode.hashCode((byte[]) null)).isNotEqualTo(HashCode.hashCode(ByteArrays.singleton((byte) 1)));
		assertThat(HashCode.hashCode(ByteArrays.singleton((byte) 1))).isEqualTo(HashCode.hashCode(ByteArrays.singleton((byte) 1)));
		assertThat(HashCode.hashCode(ByteArrays.singleton((byte) 1))).isNotEqualTo(HashCode.hashCode(ByteArrays.of((byte) 1, (byte) 2)));
	}

	@Test
	void testHashCodeShortArray() {
		assertThat(HashCode.hashCode((short[]) null)).isEqualTo(HashCode.hashCode((short[]) null));
		assertThat(HashCode.hashCode(ShortArrays.singleton((short) 1))).isNotEqualTo(HashCode.hashCode((short[]) null));
		assertThat(HashCode.hashCode((short[]) null)).isNotEqualTo(HashCode.hashCode(ShortArrays.singleton((short) 1)));
		assertThat(HashCode.hashCode(ShortArrays.singleton((short) 1))).isEqualTo(HashCode.hashCode(ShortArrays.singleton((short) 1)));
		assertThat(HashCode.hashCode(ShortArrays.singleton((short) 1))).isNotEqualTo(HashCode.hashCode(ShortArrays.of((short) 1, (short) 2)));
	}

	@Test
	void testHashCodeCharArray() {
		assertThat(HashCode.hashCode((char[]) null)).isEqualTo(HashCode.hashCode((char[]) null));
		assertThat(HashCode.hashCode(CharArrays.singleton('a'))).isNotEqualTo(HashCode.hashCode((char[]) null));
		assertThat(HashCode.hashCode((char[]) null)).isNotEqualTo(HashCode.hashCode(CharArrays.singleton('a')));
		assertThat(HashCode.hashCode(CharArrays.singleton('a'))).isEqualTo(HashCode.hashCode(CharArrays.singleton('a')));
		assertThat(HashCode.hashCode(CharArrays.singleton('a'))).isNotEqualTo(HashCode.hashCode(CharArrays.of('a', 'b')));
	}

	@Test
	void testHashCodeIntArray() {
		assertThat(HashCode.hashCode((int[]) null)).isEqualTo(HashCode.hashCode((int[]) null));
		assertThat(HashCode.hashCode(IntArrays.singleton(1))).isNotEqualTo(HashCode.hashCode((int[]) null));
		assertThat(HashCode.hashCode((int[]) null)).isNotEqualTo(HashCode.hashCode(IntArrays.singleton(1)));
		assertThat(HashCode.hashCode(IntArrays.singleton(1))).isEqualTo(HashCode.hashCode(IntArrays.singleton(1)));
		assertThat(HashCode.hashCode(IntArrays.singleton(1))).isNotEqualTo(HashCode.hashCode(IntArrays.of(1, 2)));
	}

	@Test
	void testHashCodeLongArray() {
		assertThat(HashCode.hashCode((long[]) null)).isEqualTo(HashCode.hashCode((long[]) null));
		assertThat(HashCode.hashCode(LongArrays.singleton(1L))).isNotEqualTo(HashCode.hashCode((long[]) null));
		assertThat(HashCode.hashCode((long[]) null)).isNotEqualTo(HashCode.hashCode(LongArrays.singleton(1L)));
		assertThat(HashCode.hashCode(LongArrays.singleton(1L))).isEqualTo(HashCode.hashCode(LongArrays.singleton(1L)));
		assertThat(HashCode.hashCode(LongArrays.singleton(1L))).isNotEqualTo(HashCode.hashCode(LongArrays.of(1L, 2L)));
	}

	@Test
	void testHashCodeFloatArray() {
		assertThat(HashCode.hashCode((float[]) null)).isEqualTo(HashCode.hashCode((float[]) null));
		assertThat(HashCode.hashCode(FloatArrays.singleton(1.0f))).isNotEqualTo(HashCode.hashCode((float[]) null));
		assertThat(HashCode.hashCode((float[]) null)).isNotEqualTo(HashCode.hashCode(FloatArrays.singleton(1.0f)));
		assertThat(HashCode.hashCode(FloatArrays.singleton(1.0f))).isEqualTo(HashCode.hashCode(FloatArrays.singleton(1.0f)));
		assertThat(HashCode.hashCode(FloatArrays.singleton(1.0f))).isNotEqualTo(HashCode.hashCode(FloatArrays.of(1.0f, 2.0f)));
	}

	@Test
	void testHashCodeDoubleArray() {
		assertThat(HashCode.hashCode((double[]) null)).isEqualTo(HashCode.hashCode((double[]) null));
		assertThat(HashCode.hashCode(DoubleArrays.singleton(1.0d))).isNotEqualTo(HashCode.hashCode((double[]) null));
		assertThat(HashCode.hashCode((double[]) null)).isNotEqualTo(HashCode.hashCode(DoubleArrays.singleton(1.0d)));
		assertThat(HashCode.hashCode(DoubleArrays.singleton(1.0d))).isEqualTo(HashCode.hashCode(DoubleArrays.singleton(1.0d)));
		assertThat(HashCode.hashCode(DoubleArrays.singleton(1.0d))).isNotEqualTo(HashCode.hashCode(DoubleArrays.of(1.0d, 2.0d)));
	}

	@Test
	void testHashCodeObjectArray() {
		assertThat(HashCode.hashCode((Integer[]) null)).isEqualTo(HashCode.hashCode((Integer[]) null));
		assertThat(HashCode.hashCode(ObjectArrays.singleton(1))).isNotEqualTo(HashCode.hashCode((Integer[]) null));
		assertThat(HashCode.hashCode((Integer[]) null)).isNotEqualTo(HashCode.hashCode(ObjectArrays.singleton(1)));
		assertThat(HashCode.hashCode(ObjectArrays.singleton(1))).isEqualTo(HashCode.hashCode(ObjectArrays.singleton(1)));
		assertThat(HashCode.hashCode(ObjectArrays.singleton(1))).isNotEqualTo(HashCode.hashCode(ObjectArrays.of(1, 2)));
	}

	@Test
	void testOf() {
		assertThat(HashCode.of(HashCode.hashCode(1))).isEqualTo(HashCode.of(HashCode.hashCode(1)));
		assertThat(HashCode.of(HashCode.hashCode(1))).isNotEqualTo(HashCode.of(HashCode.hashCode(2)));
		assertThat(HashCode.of(HashCode.hashCode(1), HashCode.hashCode(2))).isEqualTo(HashCode.of(HashCode.hashCode(1), HashCode.hashCode(2)));
		assertThat(HashCode.of(HashCode.hashCode(1), HashCode.hashCode(2))).isNotEqualTo(HashCode.of(HashCode.hashCode(2), HashCode.hashCode(2)));
	}

	@Test
	void testOfInvalid() {
		assertThatNullPointerException().isThrownBy(() -> HashCode.of((int[]) null));
		assertThatIllegalArgumentException().isThrownBy(HashCode::of);
	}
}