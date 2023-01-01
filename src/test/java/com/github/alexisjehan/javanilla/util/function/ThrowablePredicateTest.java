/*
 * MIT License
 *
 * Copyright (c) 2018-2023 Alexis Jehan
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
import java.util.function.Predicate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.assertj.core.api.Assertions.assertThatIOException;
import static org.assertj.core.api.Assertions.assertThatNullPointerException;

final class ThrowablePredicateTest {

	@Test
	void testTest() throws IOException {
		final var throwablePredicate = (ThrowablePredicate<Integer, IOException>) t -> t >= 2;
		final var exceptionThrowablePredicate = (ThrowablePredicate<Integer, IOException>) t -> {
			throw new IOException();
		};
		assertThat(throwablePredicate.test(1)).isFalse();
		assertThat(throwablePredicate.test(3)).isTrue();
		assertThatIOException().isThrownBy(() -> exceptionThrowablePredicate.test(1));
		assertThatIOException().isThrownBy(() -> exceptionThrowablePredicate.test(3));
	}

	@Test
	void testAnd() throws IOException {
		final var fooThrowablePredicate = (ThrowablePredicate<Integer, IOException>) t -> t >= 2;
		final var barThrowablePredicate = (ThrowablePredicate<Integer, IOException>) t -> t <= 2;
		final var exceptionThrowablePredicate = (ThrowablePredicate<Integer, IOException>) t -> {
			throw new IOException();
		};
		assertThat(fooThrowablePredicate.and(barThrowablePredicate).test(1)).isFalse();
		assertThat(fooThrowablePredicate.and(barThrowablePredicate).test(3)).isFalse();
		assertThat(barThrowablePredicate.and(fooThrowablePredicate).test(1)).isFalse();
		assertThat(barThrowablePredicate.and(fooThrowablePredicate).test(3)).isFalse();
		assertThat(fooThrowablePredicate.and(fooThrowablePredicate).test(1)).isFalse();
		assertThat(fooThrowablePredicate.and(fooThrowablePredicate).test(3)).isTrue();
		assertThat(barThrowablePredicate.and(barThrowablePredicate).test(1)).isTrue();
		assertThat(barThrowablePredicate.and(barThrowablePredicate).test(3)).isFalse();
		assertThat(fooThrowablePredicate.and(exceptionThrowablePredicate).test(1)).isFalse();
		assertThatIOException().isThrownBy(() -> fooThrowablePredicate.and(exceptionThrowablePredicate).test(3));
		assertThatIOException().isThrownBy(() -> exceptionThrowablePredicate.and(fooThrowablePredicate).test(1));
		assertThatIOException().isThrownBy(() -> exceptionThrowablePredicate.and(fooThrowablePredicate).test(3));
	}

	@Test
	void testAndInvalid() {
		final var throwablePredicate = (ThrowablePredicate<Integer, IOException>) t -> t >= 2;
		assertThatNullPointerException().isThrownBy(() -> throwablePredicate.and(null));
	}

	@Test
	void testNegate() throws IOException {
		final var throwablePredicate = (ThrowablePredicate<Integer, IOException>) t -> t >= 2;
		final var exceptionThrowablePredicate = (ThrowablePredicate<Integer, IOException>) t -> {
			throw new IOException();
		};
		assertThat(throwablePredicate.negate().test(1)).isTrue();
		assertThat(throwablePredicate.negate().test(3)).isFalse();
		assertThatIOException().isThrownBy(() -> exceptionThrowablePredicate.negate().test(1));
		assertThatIOException().isThrownBy(() -> exceptionThrowablePredicate.negate().test(3));
	}

	@Test
	void testOr() throws IOException {
		final var fooThrowablePredicate = (ThrowablePredicate<Integer, IOException>) t -> t >= 2;
		final var barThrowablePredicate = (ThrowablePredicate<Integer, IOException>) t -> t <= 2;
		final var exceptionThrowablePredicate = (ThrowablePredicate<Integer, IOException>) t -> {
			throw new IOException();
		};
		assertThat(fooThrowablePredicate.or(barThrowablePredicate).test(1)).isTrue();
		assertThat(fooThrowablePredicate.or(barThrowablePredicate).test(3)).isTrue();
		assertThat(barThrowablePredicate.or(fooThrowablePredicate).test(1)).isTrue();
		assertThat(barThrowablePredicate.or(fooThrowablePredicate).test(3)).isTrue();
		assertThat(fooThrowablePredicate.or(fooThrowablePredicate).test(1)).isFalse();
		assertThat(fooThrowablePredicate.or(fooThrowablePredicate).test(3)).isTrue();
		assertThat(barThrowablePredicate.or(barThrowablePredicate).test(1)).isTrue();
		assertThat(barThrowablePredicate.or(barThrowablePredicate).test(3)).isFalse();
		assertThatIOException().isThrownBy(() -> fooThrowablePredicate.or(exceptionThrowablePredicate).test(1));
		assertThat(fooThrowablePredicate.or(exceptionThrowablePredicate).test(3)).isTrue();
		assertThatIOException().isThrownBy(() -> exceptionThrowablePredicate.or(fooThrowablePredicate).test(1));
		assertThatIOException().isThrownBy(() -> exceptionThrowablePredicate.or(fooThrowablePredicate).test(3));
	}

	@Test
	void testOrInvalid() {
		final var throwablePredicate = (ThrowablePredicate<Integer, IOException>) t -> t >= 2;
		assertThatNullPointerException().isThrownBy(() -> throwablePredicate.or(null));
	}

	@Test
	void testIsEqual() {
		final var fooThrowablePredicate = ThrowablePredicate.isEqual(1);
		final var barThrowablePredicate = ThrowablePredicate.isEqual(null);
		assertThat(fooThrowablePredicate.test(null)).isFalse();
		assertThat(fooThrowablePredicate.test(1)).isTrue();
		assertThat(barThrowablePredicate.test(null)).isTrue();
		assertThat(barThrowablePredicate.test(3)).isFalse();
	}

	@Test
	void testUnchecked() {
		final var throwablePredicate = (ThrowablePredicate<Integer, IOException>) t -> t >= 2;
		final var exceptionThrowablePredicate = (ThrowablePredicate<Integer, IOException>) t -> {
			throw new IOException();
		};
		assertThat(ThrowablePredicate.unchecked(throwablePredicate).test(1)).isFalse();
		assertThat(ThrowablePredicate.unchecked(throwablePredicate).test(3)).isTrue();
		assertThat(ThrowablePredicate.unchecked(exceptionThrowablePredicate)).satisfies(uncheckedExceptionThrowablePredicate -> {
			assertThatExceptionOfType(UncheckedIOException.class).isThrownBy(() -> uncheckedExceptionThrowablePredicate.test(1));
			assertThatExceptionOfType(UncheckedIOException.class).isThrownBy(() -> uncheckedExceptionThrowablePredicate.test(3));
		});
	}

	@Test
	void testUncheckedInvalid() {
		assertThatNullPointerException().isThrownBy(() -> ThrowablePredicate.unchecked(null));
	}

	@Test
	void testSneaky() {
		final var throwablePredicate = (ThrowablePredicate<Integer, IOException>) t -> t >= 2;
		final var exceptionThrowablePredicate = (ThrowablePredicate<Integer, IOException>) t -> {
			throw new IOException();
		};
		assertThat(ThrowablePredicate.sneaky(throwablePredicate).test(1)).isFalse();
		assertThat(ThrowablePredicate.sneaky(throwablePredicate).test(3)).isTrue();
		assertThat(ThrowablePredicate.sneaky(exceptionThrowablePredicate)).satisfies(sneakyExceptionThrowablePredicate -> {
			assertThatExceptionOfType(IOException.class).isThrownBy(() -> sneakyExceptionThrowablePredicate.test(1));
			assertThatExceptionOfType(IOException.class).isThrownBy(() -> sneakyExceptionThrowablePredicate.test(3));
		});
	}

	@Test
	void testSneakyInvalid() {
		assertThatNullPointerException().isThrownBy(() -> ThrowablePredicate.sneaky(null));
	}

	@Test
	void testOf() throws Throwable {
		final var throwablePredicate = ThrowablePredicate.of((Predicate<Integer>) t -> t >= 2);
		assertThat(throwablePredicate.test(1)).isFalse();
		assertThat(throwablePredicate.test(3)).isTrue();
	}

	@Test
	void testOfInvalid() {
		assertThatNullPointerException().isThrownBy(() -> ThrowablePredicate.of(null));
	}
}