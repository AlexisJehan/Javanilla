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
	void testAnd() throws IOException {
		final ThrowablePredicate<Integer, IOException> throwablePredicate1 = t -> 1 == t;
		assertThat(throwablePredicate1.and(t -> 0 == t).test(1)).isFalse();
		assertThat(throwablePredicate1.and(t -> 1 == t).test(1)).isTrue();
		assertThat(throwablePredicate1.and(t -> 0 == t).test(0)).isFalse();
		assertThat(throwablePredicate1.and(t -> 1 == t).test(0)).isFalse();

		final ThrowablePredicate<Integer, IOException> throwablePredicate2 = t -> {
			throw new IOException();
		};
		assertThatIOException().isThrownBy(() -> throwablePredicate1.and(throwablePredicate2).test(1));
	}

	@Test
	void testAndInvalid() {
		final ThrowablePredicate<Integer, IOException> throwablePredicate = t -> {
			throw new IOException();
		};
		assertThatNullPointerException().isThrownBy(() -> throwablePredicate.and(null));
	}

	@Test
	void testNegate() throws IOException {
		final ThrowablePredicate<Integer, IOException> throwablePredicate1 = t -> 1 == t;
		assertThat(throwablePredicate1.negate().test(1)).isFalse();
		assertThat(throwablePredicate1.negate().negate().test(1)).isTrue();
	}

	@Test
	void testOr() throws IOException {
		final ThrowablePredicate<Integer, IOException> throwablePredicate1 = t -> 0 == t;
		assertThat(throwablePredicate1.or(t -> 0 == t).test(1)).isFalse();
		assertThat(throwablePredicate1.or(t -> 1 == t).test(1)).isTrue();
		assertThat(throwablePredicate1.or(t -> 0 == t).test(0)).isTrue();
		assertThat(throwablePredicate1.or(t -> 1 == t).test(0)).isTrue();

		final ThrowablePredicate<Integer, IOException> throwablePredicate2 = t -> {
			throw new IOException();
		};
		assertThatIOException().isThrownBy(() -> throwablePredicate1.or(throwablePredicate2).test(1));
	}

	@Test
	void testOrInvalid() {
		final ThrowablePredicate<Integer, IOException> throwablePredicate = t -> {
			throw new IOException();
		};
		assertThatNullPointerException().isThrownBy(() -> throwablePredicate.or(null));
	}

	@Test
	void testUnchecked() throws IOException {
		final ThrowablePredicate<Integer, IOException> throwablePredicate1 = t -> 1 == t;
		assertThat(throwablePredicate1.test(1)).isTrue();
		assertThat(ThrowablePredicate.unchecked(throwablePredicate1).test(1)).isTrue();

		final ThrowablePredicate<Integer, IOException> throwablePredicate2 = t -> {
			throw new IOException();
		};
		final var predicate = ThrowablePredicate.unchecked(throwablePredicate2);
		assertThatExceptionOfType(UncheckedIOException.class).isThrownBy(() -> predicate.test(1));
	}

	@Test
	void testUncheckedInvalid() {
		assertThatNullPointerException().isThrownBy(() -> ThrowablePredicate.unchecked(null));
	}

	@Test
	void testOf() {
		final Predicate<Integer> predicate = t -> {
			throw new UncheckedIOException(new IOException());
		};
		final var throwablePredicate = ThrowablePredicate.of(predicate);
		assertThatExceptionOfType(UncheckedIOException.class).isThrownBy(() -> throwablePredicate.test(1));
	}

	@Test
	void testOfInvalid() {
		assertThatNullPointerException().isThrownBy(() -> ThrowablePredicate.of(null));
	}
}