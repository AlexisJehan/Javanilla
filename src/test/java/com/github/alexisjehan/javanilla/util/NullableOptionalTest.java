/*
 * MIT License
 *
 * Copyright (c) 2018-2026 Alexis Jehan
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
package com.github.alexisjehan.javanilla.util;

import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.assertj.core.api.Assertions.assertThatIllegalStateException;
import static org.assertj.core.api.Assertions.assertThatNullPointerException;

final class NullableOptionalTest {

	private static final Integer VALUE = null;

	private final NullableOptional<Integer> nullableOptional = NullableOptional.of(VALUE);

	@Test
	void testGet() {
		assertThat(NullableOptional.of(null).get()).isNull();
		assertThat(NullableOptional.of(1).get()).isEqualTo(1);
		assertThat(NullableOptional.empty()).satisfies(
				otherNullableOptional -> assertThatExceptionOfType(NoSuchElementException.class).isThrownBy(otherNullableOptional::get)
		);
	}

	@Test
	void testIsPresent() {
		assertThat(NullableOptional.of(null).isPresent()).isTrue();
		assertThat(NullableOptional.of(1).isPresent()).isTrue();
		assertThat(NullableOptional.empty().isPresent()).isFalse();
	}

	@Test
	void testIsEmpty() {
		assertThat(NullableOptional.of(null).isEmpty()).isFalse();
		assertThat(NullableOptional.of(1).isEmpty()).isFalse();
		assertThat(NullableOptional.empty().isEmpty()).isTrue();
	}

	@Test
	void testIfPresent() {
		assertThat(NullableOptional.of(null)).satisfies(otherNullableOptional -> {
			final var atomicBoolean = new AtomicBoolean(false);
			otherNullableOptional.ifPresent(value -> atomicBoolean.set(true));
			assertThat(atomicBoolean.get()).isTrue();
		});
		assertThat(NullableOptional.of(1)).satisfies(otherNullableOptional -> {
			final var atomicBoolean = new AtomicBoolean(false);
			otherNullableOptional.ifPresent(value -> atomicBoolean.set(true));
			assertThat(atomicBoolean.get()).isTrue();
		});
		assertThat(NullableOptional.empty()).satisfies(otherNullableOptional -> {
			final var atomicBoolean = new AtomicBoolean(false);
			otherNullableOptional.ifPresent(value -> atomicBoolean.set(true));
			assertThat(atomicBoolean.get()).isFalse();
		});
	}

	@Test
	void testIfPresentInvalid() {
		assertThatNullPointerException().isThrownBy(() -> NullableOptional.of(null).ifPresent(null));
	}

	@Test
	void testIfPresentOrElse() {
		assertThat(NullableOptional.of(null)).satisfies(otherNullableOptional -> {
			final var atomicInteger = new AtomicInteger(-1);
			otherNullableOptional.ifPresentOrElse(value -> atomicInteger.set(1), () -> atomicInteger.set(0));
			assertThat(atomicInteger).hasValue(1);
		});
		assertThat(NullableOptional.of(1)).satisfies(otherNullableOptional -> {
			final var atomicInteger = new AtomicInteger(-1);
			otherNullableOptional.ifPresentOrElse(value -> atomicInteger.set(1), () -> atomicInteger.set(0));
			assertThat(atomicInteger).hasValue(1);
		});
		assertThat(NullableOptional.empty()).satisfies(otherNullableOptional -> {
			final var atomicInteger = new AtomicInteger(-1);
			otherNullableOptional.ifPresentOrElse(value -> atomicInteger.set(1), () -> atomicInteger.set(0));
			assertThat(atomicInteger).hasValue(0);
		});
	}

	@Test
	void testIfPresentOrElseInvalid() {
		assertThatNullPointerException().isThrownBy(() -> NullableOptional.of(null).ifPresentOrElse(null, () -> {}));
		assertThatNullPointerException().isThrownBy(() -> NullableOptional.of(null).ifPresentOrElse(value -> {}, null));
	}

	@Test
	void testFilter() {
		assertThat(NullableOptional.of(null)).satisfies(otherNullableOptional -> {
			assertThat(otherNullableOptional.filter(value -> true).get()).isNull();
			assertThat(otherNullableOptional.filter(value -> false).isEmpty()).isTrue();
		});
		assertThat(NullableOptional.of(1)).satisfies(otherNullableOptional -> {
			assertThat(otherNullableOptional.filter(value -> true).get()).isEqualTo(1);
			assertThat(otherNullableOptional.filter(value -> false).isEmpty()).isTrue();
		});
		assertThat(NullableOptional.empty()).satisfies(otherNullableOptional -> {
			assertThat(otherNullableOptional.filter(value -> true).isEmpty()).isTrue();
			assertThat(otherNullableOptional.filter(value -> false).isEmpty()).isTrue();
		});
	}

	@Test
	void testFilterInvalid() {
		assertThatNullPointerException().isThrownBy(() -> NullableOptional.of(null).filter(null));
	}

	@Test
	void testMap() {
		final var mapper = (Function<Object, Integer>) value -> null == value ? 1 : null;
		assertThat(NullableOptional.of(null).map(mapper).get()).isEqualTo(1);
		assertThat(NullableOptional.of(1).map(mapper).get()).isNull();
		assertThat(NullableOptional.empty().map(mapper).isEmpty()).isTrue();
	}

	@Test
	void testMapInvalid() {
		assertThatNullPointerException().isThrownBy(() -> NullableOptional.of(null).map(null));
	}

	@Test
	void testFlatMap() {
		final var mapper = (Function<Object, NullableOptional<Integer>>) value -> null == value ? NullableOptional.of(1) : NullableOptional.empty();
		assertThat(NullableOptional.of(null).flatMap(mapper).get()).isEqualTo(1);
		assertThat(NullableOptional.of(1).flatMap(mapper).isEmpty()).isTrue();
		assertThat(NullableOptional.empty().flatMap(mapper).isEmpty()).isTrue();
	}

	@Test
	void testFlatMapInvalid() {
		assertThatNullPointerException().isThrownBy(() -> NullableOptional.of(null).flatMap(null));
		assertThatNullPointerException().isThrownBy(() -> NullableOptional.of(null).flatMap(value -> null));
	}

	@Test
	void testOr() {
		assertThat(NullableOptional.of(null)).satisfies(otherNullableOptional -> {
			assertThat(otherNullableOptional.or(() -> NullableOptional.of(1)).get()).isNull();
			assertThat(otherNullableOptional.or(() -> NullableOptional.of(null)).get()).isNull();
			assertThat(otherNullableOptional.or(NullableOptional::empty).get()).isNull();
		});
		assertThat(NullableOptional.of(1)).satisfies(otherNullableOptional -> {
			assertThat(otherNullableOptional.or(() -> NullableOptional.of(1)).get()).isEqualTo(1);
			assertThat(otherNullableOptional.or(() -> NullableOptional.of(null)).get()).isEqualTo(1);
			assertThat(otherNullableOptional.or(NullableOptional::empty).get()).isEqualTo(1);
		});
		assertThat(NullableOptional.empty()).satisfies(otherNullableOptional -> {
			assertThat(otherNullableOptional.or(() -> NullableOptional.of(1)).get()).isEqualTo(1);
			assertThat(otherNullableOptional.or(() -> NullableOptional.of(null)).get()).isNull();
			assertThat(otherNullableOptional.or(NullableOptional::empty).isEmpty()).isTrue();
		});
	}

	@Test
	void testOrInvalid() {
		assertThatNullPointerException().isThrownBy(() -> NullableOptional.of(null).or(null));
		assertThatNullPointerException().isThrownBy(() -> NullableOptional.empty().or(() -> null).get());
	}

	@Test
	void testStream() {
		assertThat(NullableOptional.of(null).stream()).containsExactly((Object) null);
		assertThat(NullableOptional.of(1).stream()).containsExactly(1);
		assertThat(NullableOptional.empty().stream()).isEmpty();
	}

	@Test
	void testOrElse() {
		assertThat(NullableOptional.of(null).orElse(0)).isNull();
		assertThat(NullableOptional.of(1).orElse(0)).isEqualTo(1);
		assertThat(NullableOptional.empty().orElse(0)).isEqualTo(0);
	}

	@Test
	void testOrElseGet() {
		assertThat(NullableOptional.of(null).orElseGet(() -> 0)).isNull();
		assertThat(NullableOptional.of(1).orElseGet(() -> 0)).isEqualTo(1);
		assertThat(NullableOptional.empty().orElseGet(() -> 0)).isEqualTo(0);
	}

	@Test
	void testOrElseGetInvalid() {
		assertThatNullPointerException().isThrownBy(() -> NullableOptional.of(null).orElseGet(null));
	}

	@Test
	void testOrElseThrow() {
		assertThat(NullableOptional.of(null).orElseThrow()).isNull();
		assertThat(NullableOptional.of(1).orElseThrow()).isEqualTo(1);
		assertThat(NullableOptional.empty()).satisfies(
				otherNullableOptional -> assertThatExceptionOfType(NoSuchElementException.class).isThrownBy(otherNullableOptional::orElseThrow)
		);
	}

	@Test
	void testOrElseThrowSupplier() {
		assertThat(NullableOptional.of(null).orElseThrow(IllegalStateException::new)).isNull();
		assertThat(NullableOptional.of(1).orElseThrow(IllegalStateException::new)).isEqualTo(1);
		assertThatIllegalStateException().isThrownBy(() -> NullableOptional.empty().orElseThrow(IllegalStateException::new));
	}

	@Test
	void testOrElseThrowSupplierInvalid() {
		assertThatNullPointerException().isThrownBy(() -> NullableOptional.of(null).orElseThrow(null));
	}

	@Test
	void testEqualsAndHashCodeAndToString() {
		assertThat(nullableOptional.equals(nullableOptional)).isTrue();
		assertThat(nullableOptional).isNotEqualTo(new Object());
		assertThat(NullableOptional.of(VALUE)).satisfies(otherNullableOptional -> {
			assertThat(otherNullableOptional).isNotSameAs(nullableOptional);
			assertThat(otherNullableOptional).isEqualTo(nullableOptional);
			assertThat(otherNullableOptional).hasSameHashCodeAs(nullableOptional);
			assertThat(otherNullableOptional).hasToString(nullableOptional.toString());
		});
		assertThat(NullableOptional.of(1)).satisfies(otherNullableOptional -> {
			assertThat(otherNullableOptional).isNotSameAs(nullableOptional);
			assertThat(otherNullableOptional).isNotEqualTo(nullableOptional);
			assertThat(otherNullableOptional).doesNotHaveSameHashCodeAs(nullableOptional);
			assertThat(otherNullableOptional).doesNotHaveToString(nullableOptional.toString());
		});
		assertThat(NullableOptional.empty()).satisfies(otherNullableOptional -> {
			assertThat(otherNullableOptional).isNotSameAs(nullableOptional);
			assertThat(otherNullableOptional).isNotEqualTo(nullableOptional);
			assertThat(otherNullableOptional).doesNotHaveSameHashCodeAs(nullableOptional);
			assertThat(otherNullableOptional).doesNotHaveToString(nullableOptional.toString());
		});
	}

	@Test
	void testToOptional() {
		assertThat(NullableOptional.of(null).toOptional()).isEmpty();
		assertThat(NullableOptional.of(1).toOptional()).hasValue(1);
		assertThat(NullableOptional.empty().toOptional()).isEmpty();
	}

	@Test
	void testOfOptional() {
		assertThat(NullableOptional.ofOptional(Optional.ofNullable(null)).isEmpty()).isTrue();
		assertThat(NullableOptional.ofOptional(Optional.of(1)).get()).isEqualTo(1);
		assertThat(NullableOptional.ofOptional(Optional.empty()).isEmpty()).isTrue();
	}

	@Test
	void testOfOptionalInvalid() {
		assertThatNullPointerException().isThrownBy(() -> NullableOptional.ofOptional(null));
	}
}