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
package com.github.alexisjehan.javanilla.util.function.throwable;

import com.github.alexisjehan.javanilla.misc.tuples.Pair;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.ArrayList;
import java.util.function.BiFunction;

import static org.assertj.core.api.Assertions.*;

/**
 * <p>{@link ThrowableBiFunction} unit tests.</p>
 */
final class ThrowableBiFunctionTest {

	@Test
	void testAndThen() throws IOException {
		final var list = new ArrayList<>();
		final var throwableBiFunction = (ThrowableBiFunction<Integer, Float, Integer, IOException>) (t, u) -> {
			list.add(Pair.of(t, u));
			return t + Math.round(u);
		};
		assertThat(throwableBiFunction.andThen(t -> {
			list.add(Pair.of(t + 1, 0.0f));
			return t;
		}).apply(1, 2.3f)).isEqualTo(3);
		assertThat(list).contains(Pair.of(1, 2.3f), Pair.of(4, 0.0f));

		list.clear();
		final var throwableFunction = (ThrowableFunction<Integer, Integer, IOException>) t -> {
			throw new IOException();
		};
		assertThatIOException().isThrownBy(() -> throwableBiFunction.andThen(throwableFunction).apply(1, 2.3f));
		assertThat(list).contains(Pair.of(1, 2.3f));
	}

	@Test
	void testAndThenInvalid() {
		final var throwableBiFunction = (ThrowableBiFunction<Integer, Float, Integer, IOException>) (t, u) -> {
			throw new IOException();
		};
		assertThatNullPointerException().isThrownBy(() -> throwableBiFunction.andThen(null));
	}

	@Test
	void testUnchecked() throws IOException {
		final var list = new ArrayList<>();
		final var throwableBiFunction1 = (ThrowableBiFunction<Integer, Float, Integer, IOException>) (t, u) -> {
			list.add(Pair.of(t, u));
			return t + Math.round(u);
		};
		assertThat(throwableBiFunction1.apply(1, 2.3f)).isEqualTo(3);
		assertThat(ThrowableBiFunction.unchecked(throwableBiFunction1).apply(1, 2.3f)).isEqualTo(3);
		assertThat(list).contains(Pair.of(1, 2.3f), Pair.of(1, 2.3f));

		final var throwableBiFunction2 = (ThrowableBiFunction<Integer, Float, Integer, IOException>) (t, u) -> {
			throw new IOException();
		};
		final var biFunction = ThrowableBiFunction.unchecked(throwableBiFunction2);
		assertThatExceptionOfType(UncheckedIOException.class).isThrownBy(() -> biFunction.apply(1, 2.3f));
	}

	@Test
	void testUncheckedInvalid() {
		assertThatNullPointerException().isThrownBy(() -> ThrowableBiFunction.unchecked(null));
	}

	@Test
	void testOf() {
		final var function = (BiFunction<Integer, Float, Integer>) (t, u) -> {
			throw new UncheckedIOException(new IOException());
		};
		final var throwableBiFunction = ThrowableBiFunction.of(function);
		assertThatExceptionOfType(UncheckedIOException.class).isThrownBy(() -> throwableBiFunction.apply(1, 2.3f));
	}

	@Test
	void testOfInvalid() {
		assertThatNullPointerException().isThrownBy(() -> ThrowableBiFunction.of(null));
	}
}