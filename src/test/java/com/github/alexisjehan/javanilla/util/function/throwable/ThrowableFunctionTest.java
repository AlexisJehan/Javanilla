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

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.ArrayList;
import java.util.function.Function;

import static org.assertj.core.api.Assertions.*;

/**
 * <p>{@link ThrowableFunction} unit tests.</p>
 */
final class ThrowableFunctionTest {

	@Test
	void testCompose() throws IOException {
		final var list = new ArrayList<>();
		final var throwableFunction1 = (ThrowableFunction<Integer, Integer, IOException>) t -> {
			list.add(t);
			return t;
		};
		assertThat(throwableFunction1.compose((Integer t) -> {
			list.add(t + 1);
			return t;
		}).apply(1)).isEqualTo(1);
		assertThat(list).contains(1, 2);

		list.clear();
		final var throwableFunction2 = (ThrowableFunction<Integer, Integer, IOException>) t -> {
			throw new IOException();
		};
		assertThatIOException().isThrownBy(() -> throwableFunction1.compose(throwableFunction2).apply(1));
		assertThat(list.isEmpty()).isTrue();
	}

	@Test
	void testComposeInvalid() {
		final var throwableFunction = (ThrowableFunction<Integer, Integer, IOException>) t -> {
			throw new IOException();
		};
		assertThatNullPointerException().isThrownBy(() -> throwableFunction.compose(null));
	}

	@Test
	void testAndThen() throws IOException {
		final var list = new ArrayList<>();
		final var throwableFunction1 = (ThrowableFunction<Integer, Integer, IOException>) t -> {
			list.add(t);
			return t;
		};
		assertThat(throwableFunction1.andThen(t -> {
			list.add(t + 1);
			return t;
		}).apply(1)).isEqualTo(1);
		assertThat(list).contains(1, 2);

		list.clear();
		final var throwableFunction2 = (ThrowableFunction<Integer, Integer, IOException>) t -> {
			throw new IOException();
		};
		assertThatIOException().isThrownBy(() -> throwableFunction1.andThen(throwableFunction2).apply(1));
		assertThat(list).contains(1);
	}

	@Test
	void testAndThenInvalid() {
		final var throwableFunction = (ThrowableFunction<Integer, Integer, IOException>) t -> {
			throw new IOException();
		};
		assertThatNullPointerException().isThrownBy(() -> throwableFunction.andThen(null));
	}

	@Test
	void testUnchecked() throws IOException {
		final var list = new ArrayList<>();
		final var throwableFunction1 = (ThrowableFunction<Integer, Integer, IOException>) t -> {
			list.add(t);
			return t;
		};
		assertThat(throwableFunction1.apply(1)).isEqualTo(1);
		assertThat(ThrowableFunction.unchecked(throwableFunction1).apply(1)).isEqualTo(1);
		assertThat(list).contains(1, 1);

		final var throwableFunction2 = (ThrowableFunction<Integer, Integer, IOException>) t -> {
			throw new IOException();
		};
		final var function = ThrowableFunction.unchecked(throwableFunction2);
		assertThatExceptionOfType(UncheckedIOException.class).isThrownBy(() -> function.apply(1));
	}

	@Test
	void testUncheckedInvalid() {
		assertThatNullPointerException().isThrownBy(() -> ThrowableFunction.unchecked(null));
	}

	@Test
	void testOf() {
		final var function = (Function<Integer, Integer>) t -> {
			throw new UncheckedIOException(new IOException());
		};
		final var throwableFunction = ThrowableFunction.of(function);
		assertThatExceptionOfType(UncheckedIOException.class).isThrownBy(() -> throwableFunction.apply(1));
	}

	@Test
	void testOfInvalid() {
		assertThatNullPointerException().isThrownBy(() -> ThrowableFunction.of(null));
	}
}