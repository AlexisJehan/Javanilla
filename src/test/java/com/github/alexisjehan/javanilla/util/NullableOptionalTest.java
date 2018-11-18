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
package com.github.alexisjehan.javanilla.util;

import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;

import static org.assertj.core.api.Assertions.*;

/**
 * <p>{@link NullableOptional} unit tests.</p>
 */
final class NullableOptionalTest {

	@Test
	void testGet() {
		assertThat(NullableOptional.of(null).get()).isNull();
		assertThat(NullableOptional.of(null).isPresent()).isTrue();
		assertThat(NullableOptional.of(null).isEmpty()).isFalse();
		assertThatExceptionOfType(NoSuchElementException.class).isThrownBy(() -> NullableOptional.empty().get());
		assertThat(NullableOptional.empty().isPresent()).isFalse();
		assertThat(NullableOptional.empty().isEmpty()).isTrue();
	}

	@Test
	void testIfPresent() {
		{
			final var atomicBoolean = new AtomicBoolean(false);
			NullableOptional.of(null).ifPresent(e -> atomicBoolean.set(true));
			assertThat(atomicBoolean.get()).isTrue();
		}
		{
			final var atomicBoolean = new AtomicBoolean(false);
			NullableOptional.empty().ifPresent(e -> atomicBoolean.set(true));
			assertThat(atomicBoolean.get()).isFalse();
		}
	}

	@Test
	void testIfPresentInvalid() {
		assertThatNullPointerException().isThrownBy(() -> NullableOptional.of(null).ifPresent(null));
	}

	@Test
	void testIfPresentOrElse() {
		{
			final var atomicInteger = new AtomicInteger(-1);
			NullableOptional.of(null).ifPresentOrElse(e -> atomicInteger.set(1), () -> atomicInteger.set(0));
			assertThat(atomicInteger).hasValue(1);
		}
		{
			final var atomicInteger = new AtomicInteger(-1);
			NullableOptional.empty().ifPresentOrElse(e -> atomicInteger.set(1), () -> atomicInteger.set(0));
			assertThat(atomicInteger).hasValue(0);
		}
	}

	@Test
	void testIfPresentOrElseInvalid() {
		assertThatNullPointerException().isThrownBy(() -> NullableOptional.of(null).ifPresentOrElse(null, () -> {}));
		assertThatNullPointerException().isThrownBy(() -> NullableOptional.of(null).ifPresentOrElse(e -> {}, null));
	}

	@Test
	void testFilter() {
		assertThat(NullableOptional.of(null).filter(e -> true).get()).isNull();
		assertThat(NullableOptional.of(null).filter(e -> false).isEmpty()).isTrue();
		assertThat(NullableOptional.empty().filter(e -> true).isEmpty()).isTrue();
		assertThat(NullableOptional.empty().filter(e -> false).isEmpty()).isTrue();
	}

	@Test
	void testFilterInvalid() {
		assertThatNullPointerException().isThrownBy(() -> NullableOptional.of(null).filter(null));
	}

	@Test
	void testMap() {
		final Function<Object, Integer> mapper = object -> null == object ? 1 : null;
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
		final Function<Object, NullableOptional<Integer>> mapper = object -> null == object ? NullableOptional.of(1) : NullableOptional.empty();
		assertThat(NullableOptional.of(null).flatMap(mapper).get()).isEqualTo(1);
		assertThat(NullableOptional.of(1).flatMap(mapper).isEmpty()).isTrue();
		assertThat(NullableOptional.empty().flatMap(mapper).isEmpty()).isTrue();
	}

	@Test
	void testFlatMapInvalid() {
		assertThatNullPointerException().isThrownBy(() -> NullableOptional.of(null).flatMap(null));
		assertThatNullPointerException().isThrownBy(() -> NullableOptional.of(null).flatMap(object -> null));
	}

	@Test
	void testOr() {
		assertThat(NullableOptional.of(null).or(() -> NullableOptional.of(1)).get()).isNull();
		assertThat(NullableOptional.of(1).or(() -> NullableOptional.of(null)).get()).isEqualTo(1);
		assertThat(NullableOptional.empty().or(() -> NullableOptional.of(null)).get()).isNull();
	}

	@Test
	void testOrInvalid() {
		assertThatNullPointerException().isThrownBy(() -> NullableOptional.of(null).or(null));
		assertThatNullPointerException().isThrownBy(() -> NullableOptional.empty().or(() -> null).get());
	}

	@Test
	void testStream() {
		assertThat(NullableOptional.of(null).stream()).containsExactly((Object) null);
		assertThat(NullableOptional.empty().stream()).isEmpty();
	}

	@Test
	void testOrElse() {
		assertThat(NullableOptional.of(null).orElse(1)).isNull();
		assertThat(NullableOptional.empty().orElse(1)).isEqualTo(1);
	}

	@Test
	void testOrElseGet() {
		assertThat(NullableOptional.of(null).orElseGet(() -> 1)).isNull();
		assertThat(NullableOptional.empty().orElseGet(() -> 1)).isEqualTo(1);
	}

	@Test
	void testOrElseGetInvalid() {
		assertThatNullPointerException().isThrownBy(() -> NullableOptional.of(null).orElseGet(null));
	}

	@Test
	void testOrElseThrow() {
		assertThat(NullableOptional.of(null).orElseThrow(IllegalStateException::new)).isNull();
		assertThatIllegalStateException().isThrownBy(() -> NullableOptional.empty().orElseThrow(IllegalStateException::new));
	}

	@Test
	void testOrElseThrowInvalid() {
		assertThatNullPointerException().isThrownBy(() -> NullableOptional.of(null).orElseThrow(null));
	}

	@Test
	void testEqualsHashCode() {
		final var nullableOptional = NullableOptional.of(null);
		assertThat(nullableOptional).isEqualTo(nullableOptional);
		assertThat(nullableOptional).isNotEqualTo(1);
		{
			final var otherNullableOptional = NullableOptional.of(null);
			assertThat(otherNullableOptional).isEqualTo(nullableOptional);
			assertThat(otherNullableOptional).hasSameHashCodeAs(nullableOptional);
			assertThat(otherNullableOptional).hasToString(nullableOptional.toString());
		}
		{
			final var otherNullableOptional = NullableOptional.of(1);
			assertThat(otherNullableOptional).isNotEqualTo(nullableOptional);
			assertThat(otherNullableOptional.hashCode()).isNotEqualTo(nullableOptional.hashCode());
			assertThat(otherNullableOptional.toString()).isNotEqualTo(nullableOptional.toString());
		}
		{
			final var otherNullableOptional = NullableOptional.empty();
			assertThat(otherNullableOptional).isNotEqualTo(nullableOptional);
			assertThat(otherNullableOptional.hashCode()).isNotEqualTo(nullableOptional.hashCode());
			assertThat(otherNullableOptional.toString()).isNotEqualTo(nullableOptional.toString());
		}
	}

	@Test
	void testToOptional() {
		assertThat(NullableOptional.of(1).toOptional()).hasValue(1);
		assertThat(NullableOptional.of(null).toOptional()).isEmpty();
		assertThat(NullableOptional.empty().toOptional()).isEmpty();
	}

	@Test
	void testOfOptional() {
		assertThat(NullableOptional.ofOptional(Optional.of(1)).get()).isEqualTo(1);
		assertThat(NullableOptional.ofOptional(Optional.empty()).isEmpty()).isTrue();
	}

	@Test
	void testOfOptionalInvalid() {
		assertThatNullPointerException().isThrownBy(() -> NullableOptional.ofOptional(null));
	}
}