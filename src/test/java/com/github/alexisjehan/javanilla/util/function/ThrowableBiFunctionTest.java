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
package com.github.alexisjehan.javanilla.util.function;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.UncheckedIOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.assertj.core.api.Assertions.assertThatIOException;
import static org.assertj.core.api.Assertions.assertThatNullPointerException;

final class ThrowableBiFunctionTest {

	@Test
	void testApply() throws IOException {
		final var throwableBiFunction = (ThrowableBiFunction<Integer, Integer, Integer, IOException>) Integer::sum;
		final var exceptionThrowableBiFunction = (ThrowableBiFunction<Integer, Integer, Integer, IOException>) (t, u) -> {
			throw new IOException();
		};
		assertThat(throwableBiFunction.apply(1, 2)).isEqualTo(3);
		assertThat(throwableBiFunction.apply(3, 3)).isEqualTo(6);
		assertThatIOException().isThrownBy(() -> exceptionThrowableBiFunction.apply(1, 2));
		assertThatIOException().isThrownBy(() -> exceptionThrowableBiFunction.apply(3, 3));
	}

	@Test
	void testAndThen() throws IOException {
		final var throwableBiFunction = (ThrowableBiFunction<Integer, Integer, Integer, IOException>) Integer::sum;
		final var exceptionThrowableBiFunction = (ThrowableBiFunction<Integer, Integer, Integer, IOException>) (t, u) -> {
			throw new IOException();
		};
		final var throwableFunction = (ThrowableFunction<Integer, Integer, IOException>) t -> -t;
		assertThat(throwableBiFunction.andThen(throwableFunction).apply(1, 2)).isEqualTo(-3);
		assertThat(throwableBiFunction.andThen(throwableFunction).apply(3, 3)).isEqualTo(-6);
		assertThatIOException().isThrownBy(() -> exceptionThrowableBiFunction.andThen(throwableFunction).apply(1, 2));
		assertThatIOException().isThrownBy(() -> exceptionThrowableBiFunction.andThen(throwableFunction).apply(3, 3));
	}

	@Test
	void testAndThenInvalid() {
		final var throwableBiFunction = (ThrowableBiFunction<Integer, Integer, Integer, IOException>) Integer::sum;
		assertThatNullPointerException().isThrownBy(() -> throwableBiFunction.andThen(null));
	}

	@Test
	void testUnchecked() {
		final var throwableBiFunction = (ThrowableBiFunction<Integer, Integer, Integer, IOException>) Integer::sum;
		final var exceptionThrowableBiFunction = (ThrowableBiFunction<Integer, Integer, Integer, IOException>) (t, u) -> {
			throw new IOException();
		};
		assertThat(ThrowableBiFunction.unchecked(throwableBiFunction).apply(1, 2)).isEqualTo(3);
		assertThat(ThrowableBiFunction.unchecked(throwableBiFunction).apply(3, 3)).isEqualTo(6);
		assertThat(ThrowableBiFunction.unchecked(exceptionThrowableBiFunction)).satisfies(uncheckedExceptionThrowableBiFunction -> {
			assertThatExceptionOfType(UncheckedIOException.class).isThrownBy(() -> uncheckedExceptionThrowableBiFunction.apply(1, 2));
			assertThatExceptionOfType(UncheckedIOException.class).isThrownBy(() -> uncheckedExceptionThrowableBiFunction.apply(3, 3));
		});
	}

	@Test
	void testUncheckedInvalid() {
		assertThatNullPointerException().isThrownBy(() -> ThrowableBiFunction.unchecked(null));
	}

	@Test
	void testSneaky() {
		final var throwableBiFunction = (ThrowableBiFunction<Integer, Integer, Integer, IOException>) Integer::sum;
		final var exceptionThrowableBiFunction = (ThrowableBiFunction<Integer, Integer, Integer, IOException>) (t, u) -> {
			throw new IOException();
		};
		assertThat(ThrowableBiFunction.sneaky(throwableBiFunction).apply(1, 2)).isEqualTo(3);
		assertThat(ThrowableBiFunction.sneaky(throwableBiFunction).apply(3, 3)).isEqualTo(6);
		assertThat(ThrowableBiFunction.sneaky(exceptionThrowableBiFunction)).satisfies(sneakyExceptionThrowableBiFunction -> {
			assertThatExceptionOfType(IOException.class).isThrownBy(() -> sneakyExceptionThrowableBiFunction.apply(1, 2));
			assertThatExceptionOfType(IOException.class).isThrownBy(() -> sneakyExceptionThrowableBiFunction.apply(3, 3));
		});
	}

	@Test
	void testSneakyInvalid() {
		assertThatNullPointerException().isThrownBy(() -> ThrowableBiFunction.sneaky(null));
	}

	@Test
	void testOf() throws Throwable {
		final var throwableBiFunction = ThrowableBiFunction.of(Integer::sum);
		assertThat(throwableBiFunction.apply(1, 2)).isEqualTo(3);
		assertThat(throwableBiFunction.apply(3, 3)).isEqualTo(6);
	}

	@Test
	void testOfInvalid() {
		assertThatNullPointerException().isThrownBy(() -> ThrowableBiFunction.of(null));
	}
}