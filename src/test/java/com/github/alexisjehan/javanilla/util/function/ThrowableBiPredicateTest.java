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
package com.github.alexisjehan.javanilla.util.function;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.function.BiPredicate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.assertj.core.api.Assertions.assertThatIOException;
import static org.assertj.core.api.Assertions.assertThatNullPointerException;

final class ThrowableBiPredicateTest {

	@Test
	void testTest() throws IOException {
		final var throwableBiPredicate = (ThrowableBiPredicate<Integer, Integer, IOException>) (t, u) -> t >= u;
		final var exceptionThrowableBiPredicate = (ThrowableBiPredicate<Integer, Integer, IOException>) (t, u) -> {
			throw new IOException();
		};
		assertThat(throwableBiPredicate.test(1, 2)).isFalse();
		assertThat(throwableBiPredicate.test(3, 3)).isTrue();
		assertThatIOException().isThrownBy(() -> exceptionThrowableBiPredicate.test(1, 2));
		assertThatIOException().isThrownBy(() -> exceptionThrowableBiPredicate.test(3, 3));
	}

	@Test
	void testAnd() throws IOException {
		final var fooThrowableBiPredicate = (ThrowableBiPredicate<Integer, Integer, IOException>) (t, u) -> t >= u;
		final var barThrowableBiPredicate = (ThrowableBiPredicate<Integer, Integer, IOException>) (t, u) -> t <= u;
		final var exceptionThrowableBiPredicate = (ThrowableBiPredicate<Integer, Integer, IOException>) (t, u) -> {
			throw new IOException();
		};
		assertThat(fooThrowableBiPredicate.and(barThrowableBiPredicate).test(1, 2)).isFalse();
		assertThat(fooThrowableBiPredicate.and(barThrowableBiPredicate).test(3, 3)).isTrue();
		assertThat(barThrowableBiPredicate.and(fooThrowableBiPredicate).test(1, 2)).isFalse();
		assertThat(barThrowableBiPredicate.and(fooThrowableBiPredicate).test(3, 3)).isTrue();
		assertThat(fooThrowableBiPredicate.and(fooThrowableBiPredicate).test(1, 2)).isFalse();
		assertThat(fooThrowableBiPredicate.and(fooThrowableBiPredicate).test(3, 3)).isTrue();
		assertThat(barThrowableBiPredicate.and(barThrowableBiPredicate).test(1, 2)).isTrue();
		assertThat(barThrowableBiPredicate.and(barThrowableBiPredicate).test(3, 3)).isTrue();
		assertThat(fooThrowableBiPredicate.and(exceptionThrowableBiPredicate).test(1, 2)).isFalse();
		assertThatIOException().isThrownBy(() -> fooThrowableBiPredicate.and(exceptionThrowableBiPredicate).test(3, 3));
		assertThatIOException().isThrownBy(() -> exceptionThrowableBiPredicate.and(fooThrowableBiPredicate).test(1, 2));
		assertThatIOException().isThrownBy(() -> exceptionThrowableBiPredicate.and(fooThrowableBiPredicate).test(3, 3));
	}

	@Test
	void testAndInvalid() {
		final var throwableBiPredicate = (ThrowableBiPredicate<Integer, Integer, IOException>) (t, u) -> t >= u;
		assertThatNullPointerException().isThrownBy(() -> throwableBiPredicate.and(null));
	}

	@Test
	void testNegate() throws IOException {
		final var throwableBiPredicate = (ThrowableBiPredicate<Integer, Integer, IOException>) (t, u) -> t >= u;
		final var exceptionThrowableBiPredicate = (ThrowableBiPredicate<Integer, Integer, IOException>) (t, u) -> {
			throw new IOException();
		};
		assertThat(throwableBiPredicate.negate().test(1, 2)).isTrue();
		assertThat(throwableBiPredicate.negate().test(3, 3)).isFalse();
		assertThatIOException().isThrownBy(() -> exceptionThrowableBiPredicate.negate().test(1, 2));
		assertThatIOException().isThrownBy(() -> exceptionThrowableBiPredicate.negate().test(3, 3));
	}

	@Test
	void testOr() throws IOException {
		final var fooThrowableBiPredicate = (ThrowableBiPredicate<Integer, Integer, IOException>) (t, u) -> t >= u;
		final var barThrowableBiPredicate = (ThrowableBiPredicate<Integer, Integer, IOException>) (t, u) -> t <= u;
		final var exceptionThrowableBiPredicate = (ThrowableBiPredicate<Integer, Integer, IOException>) (t, u) -> {
			throw new IOException();
		};
		assertThat(fooThrowableBiPredicate.or(barThrowableBiPredicate).test(1, 2)).isTrue();
		assertThat(fooThrowableBiPredicate.or(barThrowableBiPredicate).test(3, 3)).isTrue();
		assertThat(barThrowableBiPredicate.or(fooThrowableBiPredicate).test(1, 2)).isTrue();
		assertThat(barThrowableBiPredicate.or(fooThrowableBiPredicate).test(3, 3)).isTrue();
		assertThat(fooThrowableBiPredicate.or(fooThrowableBiPredicate).test(1, 2)).isFalse();
		assertThat(fooThrowableBiPredicate.or(fooThrowableBiPredicate).test(3, 3)).isTrue();
		assertThat(barThrowableBiPredicate.or(barThrowableBiPredicate).test(1, 2)).isTrue();
		assertThat(barThrowableBiPredicate.or(barThrowableBiPredicate).test(3, 3)).isTrue();
		assertThatIOException().isThrownBy(() -> fooThrowableBiPredicate.or(exceptionThrowableBiPredicate).test(1, 2));
		assertThat(fooThrowableBiPredicate.or(exceptionThrowableBiPredicate).test(3, 3)).isTrue();
		assertThatIOException().isThrownBy(() -> exceptionThrowableBiPredicate.or(fooThrowableBiPredicate).test(1, 2));
		assertThatIOException().isThrownBy(() -> exceptionThrowableBiPredicate.or(fooThrowableBiPredicate).test(3, 3));
	}

	@Test
	void testOrInvalid() {
		final var throwableBiPredicate = (ThrowableBiPredicate<Integer, Integer, IOException>) (t, u) -> t >= u;
		assertThatNullPointerException().isThrownBy(() -> throwableBiPredicate.or(null));
	}

	@Test
	void testUnchecked() {
		final var throwableBiPredicate = (ThrowableBiPredicate<Integer, Integer, IOException>) (t, u) -> t >= u;
		final var exceptionThrowableBiPredicate = (ThrowableBiPredicate<Integer, Integer, IOException>) (t, u) -> {
			throw new IOException();
		};
		assertThat(ThrowableBiPredicate.unchecked(throwableBiPredicate).test(1, 2)).isFalse();
		assertThat(ThrowableBiPredicate.unchecked(throwableBiPredicate).test(3, 3)).isTrue();
		assertThat(ThrowableBiPredicate.unchecked(exceptionThrowableBiPredicate)).satisfies(uncheckedExceptionThrowableBiPredicate -> {
			assertThatExceptionOfType(UncheckedIOException.class).isThrownBy(() -> uncheckedExceptionThrowableBiPredicate.test(1, 2));
			assertThatExceptionOfType(UncheckedIOException.class).isThrownBy(() -> uncheckedExceptionThrowableBiPredicate.test(3, 3));
		});
	}

	@Test
	void testUncheckedInvalid() {
		assertThatNullPointerException().isThrownBy(() -> ThrowableBiPredicate.unchecked(null));
	}

	@Test
	void testSneaky() {
		final var throwableBiPredicate = (ThrowableBiPredicate<Integer, Integer, IOException>) (t, u) -> t >= u;
		final var exceptionThrowableBiPredicate = (ThrowableBiPredicate<Integer, Integer, IOException>) (t, u) -> {
			throw new IOException();
		};
		assertThat(ThrowableBiPredicate.sneaky(throwableBiPredicate).test(1, 2)).isFalse();
		assertThat(ThrowableBiPredicate.sneaky(throwableBiPredicate).test(3, 3)).isTrue();
		assertThat(ThrowableBiPredicate.sneaky(exceptionThrowableBiPredicate)).satisfies(sneakyExceptionThrowableBiPredicate -> {
			assertThatExceptionOfType(IOException.class).isThrownBy(() -> sneakyExceptionThrowableBiPredicate.test(1, 2));
			assertThatExceptionOfType(IOException.class).isThrownBy(() -> sneakyExceptionThrowableBiPredicate.test(3, 3));
		});
	}

	@Test
	void testSneakyInvalid() {
		assertThatNullPointerException().isThrownBy(() -> ThrowableBiPredicate.sneaky(null));
	}

	@Test
	void testOf() throws Throwable {
		final var throwableBiPredicate = ThrowableBiPredicate.of((BiPredicate<Integer, Integer>) (t, u) -> t >= u);
		assertThat(throwableBiPredicate.test(1, 2)).isFalse();
		assertThat(throwableBiPredicate.test(3, 3)).isTrue();
	}

	@Test
	void testOfInvalid() {
		assertThatNullPointerException().isThrownBy(() -> ThrowableBiPredicate.of(null));
	}
}