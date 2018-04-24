/*
MIT License

Copyright (c) 2018 Alexis Jehan

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
*/
package com.github.alexisjehan.javanilla.util.function.throwable;

import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.function.Predicate;

import static org.assertj.core.api.Assertions.*;

/**
 * <p>{@link ThrowablePredicate} unit tests.</p>
 */
final class ThrowablePredicateTest {

	@Test
	void testSimple() {
		final ThrowablePredicate<Integer, IOException> throwablePredicate = t -> {
			throw new IOException();
		};
		assertThatIOException().isThrownBy(() -> throwablePredicate.test(1));
	}

	@Test
	void testAnd() {
		final ThrowablePredicate<Integer, IOException> throwablePredicate1 = t -> 1 == t;
		try {
			assertThat(throwablePredicate1.and(t -> 0 == t).test(1)).isFalse();
			assertThat(throwablePredicate1.and(t -> 1 == t).test(1)).isTrue();
			assertThat(throwablePredicate1.and(t -> 0 == t).test(0)).isFalse();
			assertThat(throwablePredicate1.and(t -> 1 == t).test(0)).isFalse();
		} catch (final IOException e) {
			fail(e.getMessage());
		}

		final ThrowablePredicate<Integer, IOException> throwablePredicate2 = t -> {
			throw new IOException();
		};
		assertThatIOException().isThrownBy(() -> throwablePredicate1.and(throwablePredicate2).test(1));
	}

	@Test
	void testAndNull() {
		final ThrowablePredicate<Integer, IOException> throwablePredicate = t -> {
			throw new IOException();
		};
		assertThatNullPointerException().isThrownBy(() -> throwablePredicate.and(null));
	}

	@Test
	void testNegate() {
		final ThrowablePredicate<Integer, IOException> throwablePredicate1 = t -> 1 == t;
		try {
			assertThat(throwablePredicate1.negate().test(1)).isFalse();
			assertThat(throwablePredicate1.negate().negate().test(1)).isTrue();
		} catch (final IOException e) {
			fail(e.getMessage());
		}
	}

	@Test
	void testOr() {
		final ThrowablePredicate<Integer, IOException> throwablePredicate1 = t -> 0 == t;
		try {
			assertThat(throwablePredicate1.or(t -> 0 == t).test(1)).isFalse();
			assertThat(throwablePredicate1.or(t -> 1 == t).test(1)).isTrue();
			assertThat(throwablePredicate1.or(t -> 0 == t).test(0)).isTrue();
			assertThat(throwablePredicate1.or(t -> 1 == t).test(0)).isTrue();
		} catch (final IOException e) {
			fail(e.getMessage());
		}

		final ThrowablePredicate<Integer, IOException> throwablePredicate2 = t -> {
			throw new IOException();
		};
		assertThatIOException().isThrownBy(() -> throwablePredicate1.or(throwablePredicate2).test(1));
	}

	@Test
	void testOrNull() {
		final ThrowablePredicate<Integer, IOException> throwablePredicate = t -> {
			throw new IOException();
		};
		assertThatNullPointerException().isThrownBy(() -> throwablePredicate.or(null));
	}

	@Test
	void testUnchecked() {
		final ThrowablePredicate<Integer, IOException> throwablePredicate1 = t -> 1 == t;
		try {
			assertThat(throwablePredicate1.test(1)).isTrue();
		} catch (final IOException e) {
			fail(e.getMessage());
		}
		assertThat(ThrowablePredicate.unchecked(throwablePredicate1).test(1)).isTrue();

		final ThrowablePredicate<Integer, IOException> throwablePredicate2 = t -> {
			throw new IOException();
		};
		final var predicate = ThrowablePredicate.unchecked(throwablePredicate2);
		assertThatExceptionOfType(UncheckedIOException.class).isThrownBy(() -> predicate.test(1));
	}

	@Test
	void testUncheckedNull() {
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
	void testOfNull() {
		assertThatNullPointerException().isThrownBy(() -> ThrowablePredicate.of(null));
	}
}