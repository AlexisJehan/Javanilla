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
package com.github.alexisjehan.javanilla.util.function.throwable;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.function.Function;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.assertj.core.api.Assertions.assertThatIOException;
import static org.assertj.core.api.Assertions.assertThatNullPointerException;

/**
 * <p>{@link ThrowableFunction} unit tests.</p>
 */
final class ThrowableFunctionTest {

	@Test
	void testApply() throws IOException {
		final var throwableFunction1 = (ThrowableFunction<Integer, Integer, IOException>) t -> t + 1;
		final var throwableFunction2 = (ThrowableFunction<Integer, Integer, IOException>) t -> {
			throw new IOException();
		};
		assertThat(throwableFunction1.apply(1)).isEqualTo(2);
		assertThat(throwableFunction1.apply(3)).isEqualTo(4);
		assertThatIOException().isThrownBy(() -> throwableFunction2.apply(1));
		assertThatIOException().isThrownBy(() -> throwableFunction2.apply(3));
	}

	@Test
	void testCompose() throws IOException {
		final var throwableFunction1 = (ThrowableFunction<Integer, Integer, IOException>) t -> t + 1;
		final var throwableFunction2 = (ThrowableFunction<Integer, Integer, IOException>) t -> -t;
		final var throwableFunction3 = (ThrowableFunction<Integer, Integer, IOException>) t -> {
			throw new IOException();
		};
		assertThat(throwableFunction1.compose(throwableFunction2).apply(1)).isEqualTo(0);
		assertThat(throwableFunction1.compose(throwableFunction2).apply(3)).isEqualTo(-2);
		assertThat(throwableFunction2.compose(throwableFunction1).apply(1)).isEqualTo(-2);
		assertThat(throwableFunction2.compose(throwableFunction1).apply(3)).isEqualTo(-4);
		assertThatIOException().isThrownBy(() -> throwableFunction1.compose(throwableFunction3).apply(1));
		assertThatIOException().isThrownBy(() -> throwableFunction1.compose(throwableFunction3).apply(3));
		assertThatIOException().isThrownBy(() -> throwableFunction3.compose(throwableFunction1).apply(1));
		assertThatIOException().isThrownBy(() -> throwableFunction3.compose(throwableFunction1).apply(3));
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
		final var throwableFunction1 = (ThrowableFunction<Integer, Integer, IOException>) t -> t + 1;
		final var throwableFunction2 = (ThrowableFunction<Integer, Integer, IOException>) t -> -t;
		final var throwableFunction3 = (ThrowableFunction<Integer, Integer, IOException>) t -> {
			throw new IOException();
		};
		assertThat(throwableFunction1.andThen(throwableFunction2).apply(1)).isEqualTo(-2);
		assertThat(throwableFunction1.andThen(throwableFunction2).apply(3)).isEqualTo(-4);
		assertThat(throwableFunction2.andThen(throwableFunction1).apply(1)).isEqualTo(0);
		assertThat(throwableFunction2.andThen(throwableFunction1).apply(3)).isEqualTo(-2);
		assertThatIOException().isThrownBy(() -> throwableFunction1.andThen(throwableFunction3).apply(1));
		assertThatIOException().isThrownBy(() -> throwableFunction1.andThen(throwableFunction3).apply(3));
		assertThatIOException().isThrownBy(() -> throwableFunction3.andThen(throwableFunction1).apply(1));
		assertThatIOException().isThrownBy(() -> throwableFunction3.andThen(throwableFunction1).apply(3));
	}

	@Test
	void testAndThenInvalid() {
		final var throwableFunction = (ThrowableFunction<Integer, Integer, IOException>) t -> {
			throw new IOException();
		};
		assertThatNullPointerException().isThrownBy(() -> throwableFunction.andThen(null));
	}

	@Test
	void testIdentity() throws Throwable {
		final var throwableFunction = ThrowableFunction.identity();
		assertThat(throwableFunction.apply(1)).isEqualTo(1);
		assertThat(throwableFunction.apply(3)).isEqualTo(3);
	}

	@Test
	void testUnchecked() {
		final var throwableFunction1 = (ThrowableFunction<Integer, Integer, IOException>) t -> t + 1;
		final var throwableFunction2 = (ThrowableFunction<Integer, Integer, IOException>) t -> {
			throw new IOException();
		};
		assertThat(ThrowableFunction.unchecked(throwableFunction1).apply(1)).isEqualTo(2);
		assertThat(ThrowableFunction.unchecked(throwableFunction1).apply(3)).isEqualTo(4);
		assertThatExceptionOfType(UncheckedIOException.class).isThrownBy(() -> ThrowableFunction.unchecked(throwableFunction2).apply(1));
		assertThatExceptionOfType(UncheckedIOException.class).isThrownBy(() -> ThrowableFunction.unchecked(throwableFunction2).apply(3));
	}

	@Test
	void testUncheckedInvalid() {
		assertThatNullPointerException().isThrownBy(() -> ThrowableFunction.unchecked(null));
	}

	@Test
	void testOf() throws Throwable {
		final var throwableFunction = ThrowableFunction.of((Function<Integer, Integer>) t -> t + 1);
		assertThat(throwableFunction.apply(1)).isEqualTo(2);
		assertThat(throwableFunction.apply(3)).isEqualTo(4);
	}

	@Test
	void testOfInvalid() {
		assertThatNullPointerException().isThrownBy(() -> ThrowableFunction.of(null));
	}
}