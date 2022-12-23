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
package com.github.alexisjehan.javanilla.util.function.throwable;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.function.Function;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.assertj.core.api.Assertions.assertThatIOException;
import static org.assertj.core.api.Assertions.assertThatNullPointerException;

@SuppressWarnings("deprecation")
final class ThrowableFunctionTest {

	@Test
	void testApply() throws IOException {
		final var throwableFunction = (ThrowableFunction<Integer, Integer, IOException>) t -> t + 1;
		final var exceptionThrowableFunction = (ThrowableFunction<Integer, Integer, IOException>) t -> {
			throw new IOException();
		};
		assertThat(throwableFunction.apply(1)).isEqualTo(2);
		assertThat(throwableFunction.apply(3)).isEqualTo(4);
		assertThatIOException().isThrownBy(() -> exceptionThrowableFunction.apply(1));
		assertThatIOException().isThrownBy(() -> exceptionThrowableFunction.apply(3));
	}

	@Test
	void testCompose() throws IOException {
		final var fooThrowableFunction = (ThrowableFunction<Integer, Integer, IOException>) t -> t + 1;
		final var barThrowableFunction = (ThrowableFunction<Integer, Integer, IOException>) t -> -t;
		final var exceptionThrowableFunction = (ThrowableFunction<Integer, Integer, IOException>) t -> {
			throw new IOException();
		};
		assertThat(fooThrowableFunction.compose(barThrowableFunction).apply(1)).isZero();
		assertThat(fooThrowableFunction.compose(barThrowableFunction).apply(3)).isEqualTo(-2);
		assertThat(barThrowableFunction.compose(fooThrowableFunction).apply(1)).isEqualTo(-2);
		assertThat(barThrowableFunction.compose(fooThrowableFunction).apply(3)).isEqualTo(-4);
		assertThatIOException().isThrownBy(() -> fooThrowableFunction.compose(exceptionThrowableFunction).apply(1));
		assertThatIOException().isThrownBy(() -> fooThrowableFunction.compose(exceptionThrowableFunction).apply(3));
		assertThatIOException().isThrownBy(() -> exceptionThrowableFunction.compose(fooThrowableFunction).apply(1));
		assertThatIOException().isThrownBy(() -> exceptionThrowableFunction.compose(fooThrowableFunction).apply(3));
	}

	@Test
	void testComposeInvalid() {
		final var throwableFunction = (ThrowableFunction<Integer, Integer, IOException>) t -> t + 1;
		assertThatNullPointerException().isThrownBy(() -> throwableFunction.compose(null));
	}

	@Test
	void testAndThen() throws IOException {
		final var fooThrowableFunction = (ThrowableFunction<Integer, Integer, IOException>) t -> t + 1;
		final var barThrowableFunction = (ThrowableFunction<Integer, Integer, IOException>) t -> -t;
		final var exceptionThrowableFunction = (ThrowableFunction<Integer, Integer, IOException>) t -> {
			throw new IOException();
		};
		assertThat(fooThrowableFunction.andThen(barThrowableFunction).apply(1)).isEqualTo(-2);
		assertThat(fooThrowableFunction.andThen(barThrowableFunction).apply(3)).isEqualTo(-4);
		assertThat(barThrowableFunction.andThen(fooThrowableFunction).apply(1)).isZero();
		assertThat(barThrowableFunction.andThen(fooThrowableFunction).apply(3)).isEqualTo(-2);
		assertThatIOException().isThrownBy(() -> fooThrowableFunction.andThen(exceptionThrowableFunction).apply(1));
		assertThatIOException().isThrownBy(() -> fooThrowableFunction.andThen(exceptionThrowableFunction).apply(3));
		assertThatIOException().isThrownBy(() -> exceptionThrowableFunction.andThen(fooThrowableFunction).apply(1));
		assertThatIOException().isThrownBy(() -> exceptionThrowableFunction.andThen(fooThrowableFunction).apply(3));
	}

	@Test
	void testAndThenInvalid() {
		final var throwableFunction = (ThrowableFunction<Integer, Integer, IOException>) t -> t + 1;
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
		final var throwableFunction = (ThrowableFunction<Integer, Integer, IOException>) t -> t + 1;
		final var exceptionThrowableFunction = (ThrowableFunction<Integer, Integer, IOException>) t -> {
			throw new IOException();
		};
		assertThat(ThrowableFunction.unchecked(throwableFunction).apply(1)).isEqualTo(2);
		assertThat(ThrowableFunction.unchecked(throwableFunction).apply(3)).isEqualTo(4);
		assertThat(ThrowableFunction.unchecked(exceptionThrowableFunction)).satisfies(uncheckedExceptionThrowableFunction -> {
			assertThatExceptionOfType(UncheckedIOException.class).isThrownBy(() -> uncheckedExceptionThrowableFunction.apply(1));
			assertThatExceptionOfType(UncheckedIOException.class).isThrownBy(() -> uncheckedExceptionThrowableFunction.apply(3));
		});
	}

	@Test
	void testUncheckedInvalid() {
		assertThatNullPointerException().isThrownBy(() -> ThrowableFunction.unchecked(null));
	}

	@Test
	void testSneaky() {
		final var throwableFunction = (ThrowableFunction<Integer, Integer, IOException>) t -> t + 1;
		final var exceptionThrowableFunction = (ThrowableFunction<Integer, Integer, IOException>) t -> {
			throw new IOException();
		};
		assertThat(ThrowableFunction.sneaky(throwableFunction).apply(1)).isEqualTo(2);
		assertThat(ThrowableFunction.sneaky(throwableFunction).apply(3)).isEqualTo(4);
		assertThat(ThrowableFunction.sneaky(exceptionThrowableFunction)).satisfies(sneakyExceptionThrowableFunction -> {
			assertThatExceptionOfType(IOException.class).isThrownBy(() -> sneakyExceptionThrowableFunction.apply(1));
			assertThatExceptionOfType(IOException.class).isThrownBy(() -> sneakyExceptionThrowableFunction.apply(3));
		});
	}

	@Test
	void testSneakyInvalid() {
		assertThatNullPointerException().isThrownBy(() -> ThrowableFunction.sneaky(null));
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