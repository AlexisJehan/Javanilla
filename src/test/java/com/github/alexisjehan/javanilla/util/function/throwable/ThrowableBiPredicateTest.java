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
import java.util.function.BiPredicate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.assertj.core.api.Assertions.assertThatIOException;
import static org.assertj.core.api.Assertions.assertThatNullPointerException;

/**
 * <p>{@link ThrowableBiPredicate} unit tests.</p>
 */
final class ThrowableBiPredicateTest {

	@Test
	void testTest() throws IOException {
		final var throwableBiPredicate1 = (ThrowableBiPredicate<Integer, Integer, IOException>) (t, u) -> t >= u;
		final var throwableBiPredicate2 = (ThrowableBiPredicate<Integer, Integer, IOException>) (t, u) -> {
			throw new IOException();
		};
		assertThat(throwableBiPredicate1.test(1, 2)).isFalse();
		assertThat(throwableBiPredicate1.test(3, 3)).isTrue();
		assertThatIOException().isThrownBy(() -> throwableBiPredicate2.test(1, 2));
		assertThatIOException().isThrownBy(() -> throwableBiPredicate2.test(3, 3));
	}

	@Test
	void testAnd() throws IOException {
		final var throwableBiPredicate1 = (ThrowableBiPredicate<Integer, Integer, IOException>) (t, u) -> t >= u;
		final var throwableBiPredicate2 = (ThrowableBiPredicate<Integer, Integer, IOException>) (t, u) -> t <= u;
		final var throwableBiPredicate3 = (ThrowableBiPredicate<Integer, Integer, IOException>) (t, u) -> {
			throw new IOException();
		};
		assertThat(throwableBiPredicate1.and(throwableBiPredicate2).test(1, 2)).isFalse();
		assertThat(throwableBiPredicate1.and(throwableBiPredicate2).test(3, 3)).isTrue();
		assertThat(throwableBiPredicate2.and(throwableBiPredicate1).test(1, 2)).isFalse();
		assertThat(throwableBiPredicate2.and(throwableBiPredicate1).test(3, 3)).isTrue();
		assertThat(throwableBiPredicate1.and(throwableBiPredicate1).test(1, 2)).isFalse();
		assertThat(throwableBiPredicate1.and(throwableBiPredicate1).test(3, 3)).isTrue();
		assertThat(throwableBiPredicate2.and(throwableBiPredicate2).test(1, 2)).isTrue();
		assertThat(throwableBiPredicate2.and(throwableBiPredicate2).test(3, 3)).isTrue();
		assertThat(throwableBiPredicate1.and(throwableBiPredicate3).test(1, 2)).isFalse();
		assertThatIOException().isThrownBy(() -> throwableBiPredicate1.and(throwableBiPredicate3).test(3, 3));
		assertThatIOException().isThrownBy(() -> throwableBiPredicate3.and(throwableBiPredicate1).test(1, 2));
		assertThatIOException().isThrownBy(() -> throwableBiPredicate3.and(throwableBiPredicate1).test(3, 3));
	}

	@Test
	void testAndInvalid() {
		final var throwableBiPredicate = (ThrowableBiPredicate<Integer, Integer, IOException>) (t, u) -> {
			throw new IOException();
		};
		assertThatNullPointerException().isThrownBy(() -> throwableBiPredicate.and(null));
	}

	@Test
	void testNegate() throws IOException {
		final var throwableBiPredicate1 = (ThrowableBiPredicate<Integer, Integer, IOException>) (t, u) -> t >= u;
		final var throwableBiPredicate2 = (ThrowableBiPredicate<Integer, Integer, IOException>) (t, u) -> {
			throw new IOException();
		};
		assertThat(throwableBiPredicate1.negate().test(1, 2)).isTrue();
		assertThat(throwableBiPredicate1.negate().test(3, 3)).isFalse();
		assertThatIOException().isThrownBy(() -> throwableBiPredicate2.negate().test(1, 2));
		assertThatIOException().isThrownBy(() -> throwableBiPredicate2.negate().test(3, 3));
	}

	@Test
	void testOr() throws IOException {
		final var throwableBiPredicate1 = (ThrowableBiPredicate<Integer, Integer, IOException>) (t, u) -> t >= u;
		final var throwableBiPredicate2 = (ThrowableBiPredicate<Integer, Integer, IOException>) (t, u) -> t <= u;
		final var throwableBiPredicate3 = (ThrowableBiPredicate<Integer, Integer, IOException>) (t, u) -> {
			throw new IOException();
		};
		assertThat(throwableBiPredicate1.or(throwableBiPredicate2).test(1, 2)).isTrue();
		assertThat(throwableBiPredicate1.or(throwableBiPredicate2).test(3, 3)).isTrue();
		assertThat(throwableBiPredicate2.or(throwableBiPredicate1).test(1, 2)).isTrue();
		assertThat(throwableBiPredicate2.or(throwableBiPredicate1).test(3, 3)).isTrue();
		assertThat(throwableBiPredicate1.or(throwableBiPredicate1).test(1, 2)).isFalse();
		assertThat(throwableBiPredicate1.or(throwableBiPredicate1).test(3, 3)).isTrue();
		assertThat(throwableBiPredicate2.or(throwableBiPredicate2).test(1, 2)).isTrue();
		assertThat(throwableBiPredicate2.or(throwableBiPredicate2).test(3, 3)).isTrue();
		assertThatIOException().isThrownBy(() -> throwableBiPredicate1.or(throwableBiPredicate3).test(1, 2));
		assertThat(throwableBiPredicate1.or(throwableBiPredicate3).test(3, 3)).isTrue();
		assertThatIOException().isThrownBy(() -> throwableBiPredicate3.or(throwableBiPredicate1).test(1, 2));
		assertThatIOException().isThrownBy(() -> throwableBiPredicate3.or(throwableBiPredicate1).test(3, 3));
	}

	@Test
	void testOrInvalid() {
		final var throwableBiPredicate = (ThrowableBiPredicate<Integer, Integer, IOException>) (t, u) -> {
			throw new IOException();
		};
		assertThatNullPointerException().isThrownBy(() -> throwableBiPredicate.or(null));
	}

	@Test
	void testUnchecked() {
		final var throwableBiPredicate1 = (ThrowableBiPredicate<Integer, Integer, IOException>) (t, u) -> t >= u;
		final var throwableBiPredicate2 = (ThrowableBiPredicate<Integer, Integer, IOException>) (t, u) -> {
			throw new IOException();
		};
		assertThat(ThrowableBiPredicate.unchecked(throwableBiPredicate1).test(1, 2)).isFalse();
		assertThat(ThrowableBiPredicate.unchecked(throwableBiPredicate1).test(3, 3)).isTrue();
		assertThatExceptionOfType(UncheckedIOException.class).isThrownBy(() -> ThrowableBiPredicate.unchecked(throwableBiPredicate2).test(1, 2));
		assertThatExceptionOfType(UncheckedIOException.class).isThrownBy(() -> ThrowableBiPredicate.unchecked(throwableBiPredicate2).test(3, 3));
	}

	@Test
	void testUncheckedInvalid() {
		assertThatNullPointerException().isThrownBy(() -> ThrowableBiPredicate.unchecked(null));
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