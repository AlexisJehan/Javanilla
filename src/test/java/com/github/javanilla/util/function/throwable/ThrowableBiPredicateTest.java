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
package com.github.javanilla.util.function.throwable;

import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.function.BiPredicate;

import static org.assertj.core.api.Assertions.*;

/**
 * <p>{@link ThrowableBiPredicate} unit tests.</p>
 */
final class ThrowableBiPredicateTest {

	@Test
	void testSimple() {
		final ThrowableBiPredicate<Integer, Float, IOException> throwableBiPredicate = (t, u) -> {
			throw new IOException();
		};
		assertThatIOException().isThrownBy(() -> throwableBiPredicate.test(1, 2.3f));
	}

	@Test
	void testAnd() {
		final ThrowableBiPredicate<Integer, Float, IOException> throwableBiPredicate1 = (t, u) -> 1 == t && 2.3f == u;
		try {
			assertThat(throwableBiPredicate1.and((t, u) -> 0 == t && 0.0f == u).test(1, 2.3f)).isFalse();
			assertThat(throwableBiPredicate1.and((t, u) -> 1 == t && 2.3f == u).test(1, 2.3f)).isTrue();
			assertThat(throwableBiPredicate1.and((t, u) -> 0 == t && 0.0f == u).test(0, 0.0f)).isFalse();
			assertThat(throwableBiPredicate1.and((t, u) -> 1 == t && 2.3f == u).test(0, 0.0f)).isFalse();
		} catch (final IOException e) {
			fail(e.getMessage());
		}

		final ThrowableBiPredicate<Integer, Float, IOException> throwableBiPredicate2 = (t, u) -> {
			throw new IOException();
		};
		assertThatIOException().isThrownBy(() -> throwableBiPredicate1.and(throwableBiPredicate2).test(1, 2.3f));
	}

	@Test
	void testAndNull() {
		final ThrowableBiPredicate<Integer, Float, IOException> throwableBiPredicate = (t, u) -> {
			throw new IOException();
		};
		assertThatNullPointerException().isThrownBy(() -> throwableBiPredicate.and(null));
	}

	@Test
	void testNegate() {
		final ThrowableBiPredicate<Integer, Float, IOException> throwableBiPredicate1 = (t, u) -> 1 == t && 2.3f == u;
		try {
			assertThat(throwableBiPredicate1.negate().test(1, 2.3f)).isFalse();
			assertThat(throwableBiPredicate1.negate().negate().test(1, 2.3f)).isTrue();
		} catch (final IOException e) {
			fail(e.getMessage());
		}
	}

	@Test
	void testOr() {
		final ThrowableBiPredicate<Integer, Float, IOException> throwableBiPredicate1 = (t, u) -> 0 == t && 0f == u;
		try {
			assertThat(throwableBiPredicate1.or((t, u) -> 0 == t && 0.0f == u).test(1, 2.3f)).isFalse();
			assertThat(throwableBiPredicate1.or((t, u) -> 1 == t && 2.3f == u).test(1, 2.3f)).isTrue();
			assertThat(throwableBiPredicate1.or((t, u) -> 0 == t && 0.0f == u).test(0, 0.0f)).isTrue();
			assertThat(throwableBiPredicate1.or((t, u) -> 1 == t && 2.3f == u).test(0, 0.0f)).isTrue();
		} catch (final IOException e) {
			fail(e.getMessage());
		}

		final ThrowableBiPredicate<Integer, Float, IOException> throwableBiPredicate2 = (t, u) -> {
			throw new IOException();
		};
		assertThatIOException().isThrownBy(() -> throwableBiPredicate1.or(throwableBiPredicate2).test(1, 2.3f));
	}

	@Test
	void testOrNull() {
		final ThrowableBiPredicate<Integer, Float, IOException> throwableBiPredicate = (t, u) -> {
			throw new IOException();
		};
		assertThatNullPointerException().isThrownBy(() -> throwableBiPredicate.or(null));
	}

	@Test
	void testUnchecked() {
		final ThrowableBiPredicate<Integer, Float, IOException> throwableBiPredicate1 = (t, u) -> 1 == t && 2.3f == u;
		try {
			assertThat(throwableBiPredicate1.test(1, 2.3f)).isTrue();
		} catch (final IOException e) {
			fail(e.getMessage());
		}
		assertThat(ThrowableBiPredicate.unchecked(throwableBiPredicate1).test(1, 2.3f)).isTrue();

		final ThrowableBiPredicate<Integer, Float, IOException> throwableBiPredicate2 = (t, u) -> {
			throw new IOException();
		};
		final var biPredicate = ThrowableBiPredicate.unchecked(throwableBiPredicate2);
		assertThatExceptionOfType(UncheckedIOException.class).isThrownBy(() -> biPredicate.test(1, 2.3f));
	}

	@Test
	void testUncheckedNull() {
		assertThatNullPointerException().isThrownBy(() -> ThrowableBiPredicate.unchecked(null));
	}

	@Test
	void testOf() {
		final BiPredicate<Integer, Float> biPredicate = (t, u) -> {
			throw new UncheckedIOException(new IOException());
		};
		final var throwableBiPredicate = ThrowableBiPredicate.of(biPredicate);
		assertThatExceptionOfType(UncheckedIOException.class).isThrownBy(() -> throwableBiPredicate.test(1, 2.3f));
	}

	@Test
	void testOfNull() {
		assertThatNullPointerException().isThrownBy(() -> ThrowableBiPredicate.of(null));
	}
}