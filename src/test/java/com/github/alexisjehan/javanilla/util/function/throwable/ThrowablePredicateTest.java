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
import java.util.function.Predicate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.assertj.core.api.Assertions.assertThatIOException;
import static org.assertj.core.api.Assertions.assertThatNullPointerException;

/**
 * <p>{@link ThrowablePredicate} unit tests.</p>
 */
final class ThrowablePredicateTest {

	@Test
	void testTest() throws IOException {
		final var throwablePredicate1 = (ThrowablePredicate<Integer, IOException>) t -> t >= 2;
		final var throwablePredicate2 = (ThrowablePredicate<Integer, IOException>) t -> {
			throw new IOException();
		};
		assertThat(throwablePredicate1.test(1)).isFalse();
		assertThat(throwablePredicate1.test(3)).isTrue();
		assertThatIOException().isThrownBy(() -> throwablePredicate2.test(1));
		assertThatIOException().isThrownBy(() -> throwablePredicate2.test(3));
	}

	@Test
	void testAnd() throws IOException {
		final var throwablePredicate1 = (ThrowablePredicate<Integer, IOException>) t -> t >= 2;
		final var throwablePredicate2 = (ThrowablePredicate<Integer, IOException>) t -> t <= 2;
		final var throwablePredicate3 = (ThrowablePredicate<Integer, IOException>) t -> {
			throw new IOException();
		};
		assertThat(throwablePredicate1.and(throwablePredicate2).test(1)).isFalse();
		assertThat(throwablePredicate1.and(throwablePredicate2).test(3)).isFalse();
		assertThat(throwablePredicate2.and(throwablePredicate1).test(1)).isFalse();
		assertThat(throwablePredicate2.and(throwablePredicate1).test(3)).isFalse();
		assertThat(throwablePredicate1.and(throwablePredicate1).test(1)).isFalse();
		assertThat(throwablePredicate1.and(throwablePredicate1).test(3)).isTrue();
		assertThat(throwablePredicate2.and(throwablePredicate2).test(1)).isTrue();
		assertThat(throwablePredicate2.and(throwablePredicate2).test(3)).isFalse();
		assertThat(throwablePredicate1.and(throwablePredicate3).test(1)).isFalse();
		assertThatIOException().isThrownBy(() -> throwablePredicate1.and(throwablePredicate3).test(3));
		assertThatIOException().isThrownBy(() -> throwablePredicate3.and(throwablePredicate1).test(1));
		assertThatIOException().isThrownBy(() -> throwablePredicate3.and(throwablePredicate1).test(3));
	}

	@Test
	void testAndInvalid() {
		final var throwablePredicate = (ThrowablePredicate<Integer, IOException>) t -> {
			throw new IOException();
		};
		assertThatNullPointerException().isThrownBy(() -> throwablePredicate.and(null));
	}

	@Test
	void testNegate() throws IOException {
		final var throwablePredicate1 = (ThrowablePredicate<Integer, IOException>) t -> t >= 2;
		final var throwablePredicate2 = (ThrowablePredicate<Integer, IOException>) t -> {
			throw new IOException();
		};
		assertThat(throwablePredicate1.negate().test(1)).isTrue();
		assertThat(throwablePredicate1.negate().test(3)).isFalse();
		assertThatIOException().isThrownBy(() -> throwablePredicate2.negate().test(1));
		assertThatIOException().isThrownBy(() -> throwablePredicate2.negate().test(3));
	}

	@Test
	void testOr() throws IOException {
		final var throwablePredicate1 = (ThrowablePredicate<Integer, IOException>) t -> t >= 2;
		final var throwablePredicate2 = (ThrowablePredicate<Integer, IOException>) t -> t <= 2;
		final var throwablePredicate3 = (ThrowablePredicate<Integer, IOException>) t -> {
			throw new IOException();
		};
		assertThat(throwablePredicate1.or(throwablePredicate2).test(1)).isTrue();
		assertThat(throwablePredicate1.or(throwablePredicate2).test(3)).isTrue();
		assertThat(throwablePredicate2.or(throwablePredicate1).test(1)).isTrue();
		assertThat(throwablePredicate2.or(throwablePredicate1).test(3)).isTrue();
		assertThat(throwablePredicate1.or(throwablePredicate1).test(1)).isFalse();
		assertThat(throwablePredicate1.or(throwablePredicate1).test(3)).isTrue();
		assertThat(throwablePredicate2.or(throwablePredicate2).test(1)).isTrue();
		assertThat(throwablePredicate2.or(throwablePredicate2).test(3)).isFalse();
		assertThatIOException().isThrownBy(() -> throwablePredicate1.or(throwablePredicate3).test(1));
		assertThat(throwablePredicate1.or(throwablePredicate3).test(3)).isTrue();
		assertThatIOException().isThrownBy(() -> throwablePredicate3.or(throwablePredicate1).test(1));
		assertThatIOException().isThrownBy(() -> throwablePredicate3.or(throwablePredicate1).test(3));
	}

	@Test
	void testOrInvalid() {
		final var throwablePredicate = (ThrowablePredicate<Integer, IOException>) t -> {
			throw new IOException();
		};
		assertThatNullPointerException().isThrownBy(() -> throwablePredicate.or(null));
	}

	@Test
	void testIsEqual() {
		final var throwablePredicate1 = ThrowablePredicate.isEqual(1);
		final var throwablePredicate2 = ThrowablePredicate.isEqual(null);
		assertThat(throwablePredicate1.test(null)).isFalse();
		assertThat(throwablePredicate1.test(1)).isTrue();
		assertThat(throwablePredicate2.test(null)).isTrue();
		assertThat(throwablePredicate2.test(3)).isFalse();
	}

	@Test
	void testUnchecked() {
		final var throwablePredicate1 = (ThrowablePredicate<Integer, IOException>) t -> t >= 2;
		final var throwablePredicate2 = (ThrowablePredicate<Integer, IOException>) t -> {
			throw new IOException();
		};
		assertThat(ThrowablePredicate.unchecked(throwablePredicate1).test(1)).isFalse();
		assertThat(ThrowablePredicate.unchecked(throwablePredicate1).test(3)).isTrue();
		assertThatExceptionOfType(UncheckedIOException.class).isThrownBy(() -> ThrowablePredicate.unchecked(throwablePredicate2).test(1));
		assertThatExceptionOfType(UncheckedIOException.class).isThrownBy(() -> ThrowablePredicate.unchecked(throwablePredicate2).test(3));
	}

	@Test
	void testUncheckedInvalid() {
		assertThatNullPointerException().isThrownBy(() -> ThrowablePredicate.unchecked(null));
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