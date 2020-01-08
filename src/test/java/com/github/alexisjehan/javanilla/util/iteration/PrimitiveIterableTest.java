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
package com.github.alexisjehan.javanilla.util.iteration;

import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.DoubleAdder;
import java.util.concurrent.atomic.LongAdder;
import java.util.function.DoubleConsumer;
import java.util.function.IntConsumer;
import java.util.function.LongConsumer;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNullPointerException;

/**
 * <p>{@link PrimitiveIterable} unit tests.</p>
 */
final class PrimitiveIterableTest {

	@Test
	void testOfInt() {
		final var primitiveIterable = Iterables.ofInt(1, 2, 3);
		assertThat(primitiveIterable).containsExactly(1, 2, 3);
		assertThat(primitiveIterable).containsExactly(1, 2, 3);
	}

	@Test
	void testOfIntForEach() {
		final var primitiveIterable = Iterables.ofInt(1, 2, 3);
		final var adder = new LongAdder();
		primitiveIterable.forEach((IntConsumer) adder::add);
		assertThat(adder.intValue()).isEqualTo(6);
	}

	@Test
	void testOfIntForEachInvalid() {
		assertThatNullPointerException().isThrownBy(() -> Iterables.EMPTY_INT.forEach((IntConsumer) null));
	}

	@Test
	void testOfLong() {
		final var primitiveIterable = Iterables.ofLong(1L, 2L, 3L);
		assertThat(primitiveIterable).containsExactly(1L, 2L, 3L);
		assertThat(primitiveIterable).containsExactly(1L, 2L, 3L);
	}

	@Test
	void testOfLongForEach() {
		final var primitiveIterable = Iterables.ofLong(1L, 2L, 3L);
		final var adder = new LongAdder();
		primitiveIterable.forEach((LongConsumer) adder::add);
		assertThat(adder.longValue()).isEqualTo(6L);
	}

	@Test
	void testOfLongForEachInvalid() {
		assertThatNullPointerException().isThrownBy(() -> Iterables.EMPTY_LONG.forEach((LongConsumer) null));
	}

	@Test
	void testOfDouble() {
		final var primitiveIterable = Iterables.ofDouble(1.0d, 2.0d, 3.0d);
		assertThat(primitiveIterable).containsExactly(1.0d, 2.0d, 3.0d);
		assertThat(primitiveIterable).containsExactly(1.0d, 2.0d, 3.0d);
	}

	@Test
	void testOfDoubleForEach() {
		final var primitiveIterable = Iterables.ofDouble(1.0d, 2.0d, 3.0d);
		final var adder = new DoubleAdder();
		primitiveIterable.forEach((DoubleConsumer) adder::add);
		assertThat(adder.doubleValue()).isEqualTo(6.0d);
	}

	@Test
	void testOfDoubleForEachInvalid() {
		assertThatNullPointerException().isThrownBy(() -> Iterables.EMPTY_DOUBLE.forEach((DoubleConsumer) null));
	}
}