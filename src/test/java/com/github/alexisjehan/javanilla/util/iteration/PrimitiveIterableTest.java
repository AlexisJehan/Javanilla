/*
 * MIT License
 *
 * Copyright (c) 2018-2022 Alexis Jehan
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
package com.github.alexisjehan.javanilla.util.iteration;

import com.github.alexisjehan.javanilla.lang.array.DoubleArrays;
import com.github.alexisjehan.javanilla.lang.array.IntArrays;
import com.github.alexisjehan.javanilla.lang.array.LongArrays;
import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.DoubleAdder;
import java.util.concurrent.atomic.LongAdder;
import java.util.function.DoubleConsumer;
import java.util.function.IntConsumer;
import java.util.function.LongConsumer;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNullPointerException;

@SuppressWarnings("deprecation")
final class PrimitiveIterableTest {

	private static final int[] INT_ELEMENTS = IntArrays.of(1, 2, 3);
	private static final long[] LONG_ELEMENTS = LongArrays.of(1L, 2L, 3L);
	private static final double[] DOUBLE_ELEMENTS = DoubleArrays.of(1.0d, 2.0d, 3.0d);

	@Test
	void testOfInt() {
		final var primitiveIterable = Iterables.ofInts(INT_ELEMENTS);
		for (var i = 0; i < 2; ++i) {
			assertThat(primitiveIterable).containsExactly(IntArrays.toBoxed(INT_ELEMENTS));
		}
	}

	@Test
	void testOfIntForEach() {
		final var primitiveIterable = Iterables.ofInts(INT_ELEMENTS);
		final var adder = new LongAdder();
		primitiveIterable.forEach((IntConsumer) adder::add);
		assertThat(adder.intValue()).isEqualTo(IntStream.of(INT_ELEMENTS).sum());
	}

	@Test
	void testOfIntForEachInvalid() {
		assertThatNullPointerException().isThrownBy(() -> Iterables.EMPTY_INT.forEach((IntConsumer) null));
	}

	@Test
	void testOfLong() {
		final var primitiveIterable = Iterables.ofLongs(LONG_ELEMENTS);
		for (var i = 0; i < 2; ++i) {
			assertThat(primitiveIterable).containsExactly(LongArrays.toBoxed(LONG_ELEMENTS));
		}
	}

	@Test
	void testOfLongForEach() {
		final var primitiveIterable = Iterables.ofLongs(LONG_ELEMENTS);
		final var adder = new LongAdder();
		primitiveIterable.forEach((LongConsumer) adder::add);
		assertThat(adder.longValue()).isEqualTo(LongStream.of(LONG_ELEMENTS).sum());
	}

	@Test
	void testOfLongForEachInvalid() {
		assertThatNullPointerException().isThrownBy(() -> Iterables.EMPTY_LONG.forEach((LongConsumer) null));
	}

	@Test
	void testOfDouble() {
		final var primitiveIterable = Iterables.ofDoubles(DOUBLE_ELEMENTS);
		for (var i = 0; i < 2; ++i) {
			assertThat(primitiveIterable).containsExactly(DoubleArrays.toBoxed(DOUBLE_ELEMENTS));
		}
	}

	@Test
	void testOfDoubleForEach() {
		final var primitiveIterable = Iterables.ofDoubles(DOUBLE_ELEMENTS);
		final var adder = new DoubleAdder();
		primitiveIterable.forEach((DoubleConsumer) adder::add);
		assertThat(adder.doubleValue()).isEqualTo(DoubleStream.of(DOUBLE_ELEMENTS).sum());
	}

	@Test
	void testOfDoubleForEachInvalid() {
		assertThatNullPointerException().isThrownBy(() -> Iterables.EMPTY_DOUBLE.forEach((DoubleConsumer) null));
	}
}