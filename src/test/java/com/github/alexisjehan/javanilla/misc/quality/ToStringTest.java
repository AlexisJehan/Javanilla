/*
 * MIT License
 *
 * Copyright (c) 2018-2025 Alexis Jehan
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

import com.github.alexisjehan.javanilla.lang.Strings;
import com.github.alexisjehan.javanilla.lang.array.BooleanArrays;
import com.github.alexisjehan.javanilla.lang.array.ByteArrays;
import com.github.alexisjehan.javanilla.lang.array.CharArrays;
import com.github.alexisjehan.javanilla.lang.array.DoubleArrays;
import com.github.alexisjehan.javanilla.lang.array.FloatArrays;
import com.github.alexisjehan.javanilla.lang.array.IntArrays;
import com.github.alexisjehan.javanilla.lang.array.LongArrays;
import com.github.alexisjehan.javanilla.lang.array.ObjectArrays;
import com.github.alexisjehan.javanilla.lang.array.ShortArrays;
import com.github.alexisjehan.javanilla.misc.tuple.Pair;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNullPointerException;

final class ToStringTest {

	@Test
	void testToStringBoolean() {
		assertThat(ToString.toString(true)).isEqualTo("true");
		assertThat(ToString.toString(false)).isEqualTo("false");
	}

	@Test
	void testToStringByte() {
		assertThat(ToString.toString((byte) 1)).isEqualTo("1");
		assertThat(ToString.toString((byte) 0)).isEqualTo("0");
	}

	@Test
	void testToStringShort() {
		assertThat(ToString.toString((short) 1)).isEqualTo("1");
		assertThat(ToString.toString((short) 0)).isEqualTo("0");
	}

	@Test
	void testToStringChar() {
		assertThat(ToString.toString('a')).isEqualTo("'a'");
		assertThat(ToString.toString('?')).isEqualTo("'?'");
		assertThat(ToString.toString('\'')).isEqualTo("'\\''");
	}

	@Test
	void testToStringInt() {
		assertThat(ToString.toString(1)).isEqualTo("1");
		assertThat(ToString.toString(0)).isEqualTo("0");
	}

	@Test
	void testToStringLong() {
		assertThat(ToString.toString(1L)).isEqualTo("1");
		assertThat(ToString.toString(0L)).isEqualTo("0");
	}

	@Test
	void testToStringFloat() {
		assertThat(ToString.toString(1.0f)).isEqualTo("1.0");
		assertThat(ToString.toString(0.0f)).isEqualTo("0.0");
	}

	@Test
	void testToStringDouble() {
		assertThat(ToString.toString(1.0d)).isEqualTo("1.0");
		assertThat(ToString.toString(0.0d)).isEqualTo("0.0");
	}

	@Test
	void testToStringCharSequence() {
		assertThat(ToString.toString("foo")).isEqualTo("\"foo\"");
		assertThat(ToString.toString(Strings.EMPTY)).isEqualTo("\"\"");
		assertThat(ToString.toString("\"")).isEqualTo("\"\\\"\"");
		assertThat(ToString.toString((CharSequence) null)).isEqualTo("null");
	}

	@Test
	void testToStringObject() {
		final var object = Integer.valueOf(1);
		assertThat(ToString.toString(object)).isEqualTo("1");
		assertThat(ToString.toString((Integer) null)).isEqualTo("null");
	}

	@Test
	void testToStringBooleanArray() {
		assertThat(ToString.toString(BooleanArrays.singleton(true))).isEqualTo("[true]");
		assertThat(ToString.toString(BooleanArrays.of(true, false))).isEqualTo("[true, false]");
		assertThat(ToString.toString(BooleanArrays.EMPTY)).isEqualTo("[]");
		assertThat(ToString.toString((boolean[]) null)).isEqualTo("null");
	}

	@Test
	void testToStringByteArray() {
		assertThat(ToString.toString(ByteArrays.singleton((byte) 1))).isEqualTo("[1]");
		assertThat(ToString.toString(ByteArrays.of((byte) 1, (byte) 0))).isEqualTo("[1, 0]");
		assertThat(ToString.toString(ByteArrays.EMPTY)).isEqualTo("[]");
		assertThat(ToString.toString((byte[]) null)).isEqualTo("null");
	}

	@Test
	void testToStringShortArray() {
		assertThat(ToString.toString(ShortArrays.singleton((short) 1))).isEqualTo("[1]");
		assertThat(ToString.toString(ShortArrays.of((short) 1, (short) 0))).isEqualTo("[1, 0]");
		assertThat(ToString.toString(ShortArrays.EMPTY)).isEqualTo("[]");
		assertThat(ToString.toString((short[]) null)).isEqualTo("null");
	}

	@Test
	void testToStringCharArray() {
		assertThat(ToString.toString(CharArrays.singleton('a'))).isEqualTo("['a']");
		assertThat(ToString.toString(CharArrays.of('a', '?'))).isEqualTo("['a', '?']");
		assertThat(ToString.toString(CharArrays.EMPTY)).isEqualTo("[]");
		assertThat(ToString.toString((char[]) null)).isEqualTo("null");
	}

	@Test
	void testToStringIntArray() {
		assertThat(ToString.toString(IntArrays.singleton(1))).isEqualTo("[1]");
		assertThat(ToString.toString(IntArrays.of(1, 0))).isEqualTo("[1, 0]");
		assertThat(ToString.toString(IntArrays.EMPTY)).isEqualTo("[]");
		assertThat(ToString.toString((int[]) null)).isEqualTo("null");
	}

	@Test
	void testToStringLongArray() {
		assertThat(ToString.toString(LongArrays.singleton(1L))).isEqualTo("[1]");
		assertThat(ToString.toString(LongArrays.of(1L, 0L))).isEqualTo("[1, 0]");
		assertThat(ToString.toString(LongArrays.EMPTY)).isEqualTo("[]");
		assertThat(ToString.toString((long[]) null)).isEqualTo("null");
	}

	@Test
	void testToStringFloatArray() {
		assertThat(ToString.toString(FloatArrays.singleton(1.0f))).isEqualTo("[1.0]");
		assertThat(ToString.toString(FloatArrays.of(1.0f, 0.0f))).isEqualTo("[1.0, 0.0]");
		assertThat(ToString.toString(FloatArrays.EMPTY)).isEqualTo("[]");
		assertThat(ToString.toString((float[]) null)).isEqualTo("null");
	}

	@Test
	void testToStringDoubleArray() {
		assertThat(ToString.toString(DoubleArrays.singleton(1.0d))).isEqualTo("[1.0]");
		assertThat(ToString.toString(DoubleArrays.of(1.0d, 0.0d))).isEqualTo("[1.0, 0.0]");
		assertThat(ToString.toString(DoubleArrays.EMPTY)).isEqualTo("[]");
		assertThat(ToString.toString((double[]) null)).isEqualTo("null");
	}

	@Test
	void testToStringCharSequenceArray() {
		assertThat(ToString.toString(ObjectArrays.singleton("foo"))).isEqualTo("[\"foo\"]");
		assertThat(ToString.toString(ObjectArrays.of("foo", Strings.EMPTY))).isEqualTo("[\"foo\", \"\"]");
		assertThat(ToString.toString(ObjectArrays.empty(CharSequence.class))).isEqualTo("[]");
		assertThat(ToString.toString((CharSequence[]) null)).isEqualTo("null");
	}

	@Test
	void testToStringObjectArray() {
		final var object = Integer.valueOf(1);
		assertThat(ToString.toString(ObjectArrays.singleton(object))).isEqualTo("[1]");
		assertThat(ToString.toString(ObjectArrays.of(object, null))).isEqualTo("[1, null]");
		assertThat(ToString.toString(ObjectArrays.empty(Integer.class))).isEqualTo("[]");
		assertThat(ToString.toString((Integer[]) null)).isEqualTo("null");
	}

	@Test
	@Deprecated
	@SuppressWarnings("unchecked")
	void testOfLegacy() {
		assertThat(Integer.valueOf(1)).satisfies(object -> {
			assertThat(ToString.of(object, com.github.alexisjehan.javanilla.misc.tuples.Pair.of("foo", ToString.toString(1)))).isEqualTo("Integer{foo=1}");
			assertThat(ToString.of(object, com.github.alexisjehan.javanilla.misc.tuples.Pair.of("foo", ToString.toString(1)), com.github.alexisjehan.javanilla.misc.tuples.Pair.of("bar", ToString.toString((Integer) null)))).isEqualTo("Integer{foo=1, bar=null}");
			assertThat(ToString.of(object, ObjectArrays.empty(com.github.alexisjehan.javanilla.misc.tuples.Pair.class))).isEqualTo("Integer@" + object.hashCode());
		});
		assertThat(new Object() {}).satisfies(object -> {
			assertThat(ToString.of(object, com.github.alexisjehan.javanilla.misc.tuples.Pair.of("foo", ToString.toString(1)))).isEqualTo(getClass().getSimpleName() + "$1{foo=1}");
			assertThat(ToString.of(object, com.github.alexisjehan.javanilla.misc.tuples.Pair.of("foo", ToString.toString(1)), com.github.alexisjehan.javanilla.misc.tuples.Pair.of("bar", ToString.toString((Integer) null)))).isEqualTo(getClass().getSimpleName() + "$1{foo=1, bar=null}");
			assertThat(ToString.of(object, ObjectArrays.empty(com.github.alexisjehan.javanilla.misc.tuples.Pair.class))).isEqualTo(getClass().getSimpleName() + "$1@" + object.hashCode());
		});
	}

	@Test
	@Deprecated
	void testOfLegacyInvalid() {
		final var object = Integer.valueOf(1);
		assertThatNullPointerException().isThrownBy(() -> ToString.of(null, com.github.alexisjehan.javanilla.misc.tuples.Pair.of("foo", ToString.toString(1))));
		assertThatNullPointerException().isThrownBy(() -> ToString.of(object, (com.github.alexisjehan.javanilla.misc.tuples.Pair<String, String>[]) null));
		assertThatNullPointerException().isThrownBy(() -> ToString.of(object, (com.github.alexisjehan.javanilla.misc.tuples.Pair<String, String>) null));
	}

	@Test
	@SuppressWarnings("unchecked")
	void testOf() {
		assertThat(Integer.valueOf(1)).satisfies(object -> {
			assertThat(ToString.of(object, Pair.of("foo", ToString.toString(1)))).isEqualTo("Integer{foo=1}");
			assertThat(ToString.of(object, Pair.of("foo", ToString.toString(1)), Pair.of("bar", ToString.toString((Integer) null)))).isEqualTo("Integer{foo=1, bar=null}");
			assertThat(ToString.of(object, ObjectArrays.empty(Pair.class))).isEqualTo("Integer@" + object.hashCode());
		});
		assertThat(new Object() {}).satisfies(object -> {
			assertThat(ToString.of(object, Pair.of("foo", ToString.toString(1)))).isEqualTo(getClass().getSimpleName() + "$2{foo=1}");
			assertThat(ToString.of(object, Pair.of("foo", ToString.toString(1)), Pair.of("bar", ToString.toString((Integer) null)))).isEqualTo(getClass().getSimpleName() + "$2{foo=1, bar=null}");
			assertThat(ToString.of(object, ObjectArrays.empty(Pair.class))).isEqualTo(getClass().getSimpleName() + "$2@" + object.hashCode());
		});
	}

	@Test
	void testOfInvalid() {
		final var object = Integer.valueOf(1);
		assertThatNullPointerException().isThrownBy(() -> ToString.of(null, Pair.of("foo", ToString.toString(1))));
		assertThatNullPointerException().isThrownBy(() -> ToString.of(object, (Pair<String, String>[]) null));
		assertThatNullPointerException().isThrownBy(() -> ToString.of(object, (Pair<String, String>) null));
	}
}