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
package com.github.alexisjehan.javanilla.util.bag;

import org.junit.jupiter.api.Test;

import java.util.AbstractMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.assertj.core.api.Assertions.assertThatNullPointerException;

final class BagsTest {

	private static final String ELEMENT = "foo";
	private static final long QUANTITY = 5L;

	private final Bag<String> emptyBag = Bags.empty();
	private final Bag<String> singletonBag = Bags.singleton(ELEMENT, QUANTITY);

	@Test
	void testEmpty() {
		assertThatExceptionOfType(UnsupportedOperationException.class).isThrownBy(() -> emptyBag.add("foo"));
		assertThatExceptionOfType(UnsupportedOperationException.class).isThrownBy(() -> emptyBag.add("foo", 2L));
		assertThatExceptionOfType(UnsupportedOperationException.class).isThrownBy(() -> emptyBag.remove("foo"));
		assertThatExceptionOfType(UnsupportedOperationException.class).isThrownBy(() -> emptyBag.remove("foo", 2L));
		assertThatExceptionOfType(UnsupportedOperationException.class).isThrownBy(() -> emptyBag.removeAll("foo"));
		assertThatExceptionOfType(UnsupportedOperationException.class).isThrownBy(emptyBag::clear);
		assertThat(emptyBag.count("foo")).isZero();
		assertThat(emptyBag.count("bar")).isZero();
		assertThat(emptyBag.count(null)).isZero();
		assertThat(emptyBag.distinct()).isZero();
		assertThat(emptyBag.size()).isZero();
		assertThat(emptyBag.min().isEmpty()).isTrue();
		assertThat(emptyBag.max().isEmpty()).isTrue();
		assertThat(emptyBag.toSet()).isEmpty();
		assertThat(emptyBag.toMap()).isEmpty();
	}

	@Test
	void testEmptyEqualsAndHashCodeAndToString() {
		assertThat(emptyBag.equals(emptyBag)).isTrue();
		assertThat(emptyBag).isNotEqualTo(new Object());
		assertThat(Bags.empty()).satisfies(otherBag -> {
			assertThat(emptyBag).isSameAs(otherBag);
			assertThat(emptyBag).isEqualTo(otherBag);
			assertThat(emptyBag).hasSameHashCodeAs(otherBag);
			assertThat(emptyBag).hasToString(otherBag.toString());
		});
		assertThat(new MapBag<>()).satisfies(otherBag -> {
			assertThat(emptyBag).isNotSameAs(otherBag);
			assertThat(emptyBag).isEqualTo(otherBag);
			assertThat(emptyBag).hasSameHashCodeAs(otherBag);
			assertThat(emptyBag).hasToString(otherBag.toString());
		});
		assertThat(Bags.singleton("foo", 5L)).satisfies(otherBag -> {
			assertThat(emptyBag).isNotSameAs(otherBag);
			assertThat(emptyBag).isNotEqualTo(otherBag);
			assertThat(emptyBag).doesNotHaveSameHashCodeAs(otherBag);
			assertThat(emptyBag).doesNotHaveToString(otherBag.toString());
		});
	}

	@Test
	void testNullToEmpty() {
		assertThat(Bags.nullToEmpty(null).isEmpty()).isTrue();
		assertThat(Bags.nullToEmpty(Bags.empty()).isEmpty()).isTrue();
		assertThat(Bags.nullToEmpty(Bags.singleton("foo"))).isEqualTo(Bags.singleton("foo"));
	}

	@Test
	void testNullToDefault() {
		assertThat(Bags.nullToDefault(null, Bags.singleton("-"))).isEqualTo(Bags.singleton("-"));
		assertThat(Bags.nullToDefault(Bags.empty(), Bags.singleton("-")).isEmpty()).isTrue();
		assertThat(Bags.nullToDefault(Bags.singleton("foo"), Bags.singleton("-"))).isEqualTo(Bags.singleton("foo"));
	}

	@Test
	void testNullToDefaultInvalid() {
		assertThatNullPointerException().isThrownBy(() -> Bags.nullToDefault(Bags.singleton("foo"), null));
	}

	@Test
	void testEmptyToNull() {
		assertThat(Bags.emptyToNull((Bag<?>) null)).isNull();
		assertThat(Bags.emptyToNull(Bags.empty())).isNull();
		assertThat(Bags.emptyToNull(Bags.singleton("foo"))).isEqualTo(Bags.singleton("foo"));
	}

	@Test
	void testEmptyToDefault() {
		assertThat(Bags.emptyToDefault(null, Bags.singleton("-"))).isNull();
		assertThat(Bags.emptyToDefault(Bags.empty(), Bags.singleton("-"))).isEqualTo(Bags.singleton("-"));
		assertThat(Bags.emptyToDefault(Bags.singleton("foo"), Bags.singleton("-"))).isEqualTo(Bags.singleton("foo"));
	}

	@Test
	void testEmptyToDefaultInvalid() {
		assertThatIllegalArgumentException().isThrownBy(() -> Bags.emptyToDefault(Bags.singleton("foo"), Bags.empty()));
	}

	@Test
	void testUnmodifiable() {
		final var unmodifiableBag = Bags.unmodifiable(Bags.of("foo", "foo", "bar"));
		assertThat(unmodifiableBag.count("foo")).isEqualTo(2L);
		assertThat(unmodifiableBag.count("bar")).isEqualTo(1L);
		assertThatExceptionOfType(UnsupportedOperationException.class).isThrownBy(() -> unmodifiableBag.add("foo"));
		assertThatExceptionOfType(UnsupportedOperationException.class).isThrownBy(() -> unmodifiableBag.add("foo", 2L));
		assertThatExceptionOfType(UnsupportedOperationException.class).isThrownBy(() -> unmodifiableBag.remove("foo"));
		assertThatExceptionOfType(UnsupportedOperationException.class).isThrownBy(() -> unmodifiableBag.remove("foo", 2L));
		assertThatExceptionOfType(UnsupportedOperationException.class).isThrownBy(() -> unmodifiableBag.removeAll("foo"));
		assertThatExceptionOfType(UnsupportedOperationException.class).isThrownBy(unmodifiableBag::clear);
	}

	@Test
	void testUnmodifiableInvalid() {
		assertThatNullPointerException().isThrownBy(() -> Bags.unmodifiable(null));
	}

	@Test
	void testSingleton() {
		assertThat(Bags.singleton("foo", 5L)).satisfies(singletonBag -> {
			assertThatExceptionOfType(UnsupportedOperationException.class).isThrownBy(() -> singletonBag.add("foo"));
			assertThatExceptionOfType(UnsupportedOperationException.class).isThrownBy(() -> singletonBag.add("foo", 2L));
			assertThatExceptionOfType(UnsupportedOperationException.class).isThrownBy(() -> singletonBag.remove("foo"));
			assertThatExceptionOfType(UnsupportedOperationException.class).isThrownBy(() -> singletonBag.remove("foo", 2L));
			assertThatExceptionOfType(UnsupportedOperationException.class).isThrownBy(() -> singletonBag.removeAll("foo"));
			assertThatExceptionOfType(UnsupportedOperationException.class).isThrownBy(singletonBag::clear);
			assertThat(singletonBag.count("foo")).isEqualTo(5L);
			assertThat(singletonBag.count("bar")).isZero();
			assertThat(singletonBag.count(null)).isZero();
			assertThat(singletonBag.distinct()).isEqualTo(1L);
			assertThat(singletonBag.size()).isEqualTo(5L);
			assertThat(singletonBag.min().get()).isEqualTo("foo");
			assertThat(singletonBag.max().get()).isEqualTo("foo");
			assertThat(singletonBag.toSet()).containsExactlyInAnyOrder("foo");
			assertThat(singletonBag.toMap()).containsOnly(Map.entry("foo", 5L));
		});

		// Zero
		assertThat(Bags.singleton("foo", 0L)).satisfies(singletonBag -> {
			assertThatExceptionOfType(UnsupportedOperationException.class).isThrownBy(() -> singletonBag.add("foo"));
			assertThatExceptionOfType(UnsupportedOperationException.class).isThrownBy(() -> singletonBag.add("foo", 2L));
			assertThatExceptionOfType(UnsupportedOperationException.class).isThrownBy(() -> singletonBag.remove("foo"));
			assertThatExceptionOfType(UnsupportedOperationException.class).isThrownBy(() -> singletonBag.remove("foo", 2L));
			assertThatExceptionOfType(UnsupportedOperationException.class).isThrownBy(() -> singletonBag.removeAll("foo"));
			assertThatExceptionOfType(UnsupportedOperationException.class).isThrownBy(singletonBag::clear);
			assertThat(singletonBag.count("foo")).isZero();
			assertThat(singletonBag.count("bar")).isZero();
			assertThat(singletonBag.count(null)).isZero();
			assertThat(singletonBag.distinct()).isZero();
			assertThat(singletonBag.size()).isZero();
			assertThat(singletonBag.min().isEmpty()).isTrue();
			assertThat(singletonBag.max().isEmpty()).isTrue();
			assertThat(singletonBag.toSet()).isEmpty();
			assertThat(singletonBag.toMap()).isEmpty();
		});

		// Null
		assertThat(Bags.singleton(null, 5L)).satisfies(singletonBag -> {
			assertThatExceptionOfType(UnsupportedOperationException.class).isThrownBy(() -> singletonBag.add("foo"));
			assertThatExceptionOfType(UnsupportedOperationException.class).isThrownBy(() -> singletonBag.add("foo", 2L));
			assertThatExceptionOfType(UnsupportedOperationException.class).isThrownBy(() -> singletonBag.remove("foo"));
			assertThatExceptionOfType(UnsupportedOperationException.class).isThrownBy(() -> singletonBag.remove("foo", 2L));
			assertThatExceptionOfType(UnsupportedOperationException.class).isThrownBy(() -> singletonBag.removeAll("foo"));
			assertThatExceptionOfType(UnsupportedOperationException.class).isThrownBy(singletonBag::clear);
			assertThat(singletonBag.count("foo")).isZero();
			assertThat(singletonBag.count("bar")).isZero();
			assertThat(singletonBag.count(null)).isEqualTo(5L);
			assertThat(singletonBag.distinct()).isEqualTo(1L);
			assertThat(singletonBag.size()).isEqualTo(5L);
			assertThat(singletonBag.min().get()).isNull();
			assertThat(singletonBag.max().get()).isNull();
			assertThat(singletonBag.toSet()).containsExactlyInAnyOrder((String) null);
			assertThat(singletonBag.toMap()).containsOnly(new AbstractMap.SimpleImmutableEntry<>(null, 5L));
		});
	}

	@Test
	void testSingletonInvalid() {
		assertThatIllegalArgumentException().isThrownBy(() -> Bags.singleton("foo", -1L));
	}

	@Test
	void testSingletonEqualsAndHashCodeAndToString() {
		assertThat(singletonBag.equals(singletonBag)).isTrue();
		assertThat(singletonBag).isNotEqualTo(new Object());
		assertThat(Bags.singleton(ELEMENT, QUANTITY)).satisfies(otherBag -> {
			assertThat(singletonBag).isNotSameAs(otherBag);
			assertThat(singletonBag).isEqualTo(otherBag);
			assertThat(singletonBag).hasSameHashCodeAs(otherBag);
			assertThat(singletonBag).hasToString(otherBag.toString());
		});
		assertThat(new MapBag<>(List.of("foo", "foo", "foo", "foo", "foo"))).satisfies(otherBag -> {
			assertThat(singletonBag).isNotSameAs(otherBag);
			assertThat(singletonBag).isEqualTo(otherBag);
			assertThat(singletonBag).hasSameHashCodeAs(otherBag);
			assertThat(singletonBag).hasToString(otherBag.toString());
		});
		assertThat(Bags.singleton(null, QUANTITY)).satisfies(otherBag -> {
			assertThat(singletonBag).isNotSameAs(otherBag);
			assertThat(singletonBag).isNotEqualTo(otherBag);
			assertThat(singletonBag).doesNotHaveSameHashCodeAs(otherBag);
			assertThat(singletonBag).doesNotHaveToString(otherBag.toString());
		});
		assertThat(Bags.singleton(ELEMENT, 0L)).satisfies(otherBag -> {
			assertThat(singletonBag).isNotSameAs(otherBag);
			assertThat(singletonBag).isNotEqualTo(otherBag);
			assertThat(singletonBag).doesNotHaveSameHashCodeAs(otherBag);
			assertThat(singletonBag).doesNotHaveToString(otherBag.toString());
		});
	}

	@Test
	void testOf() {
		assertThat(Bags.of().toSet()).isEmpty();
		assertThat(Bags.of("foo").toSet()).containsExactly("foo");
		final var bag = Bags.of("foo", "foo", "foo", "bar", "bar", null);
		assertThatExceptionOfType(UnsupportedOperationException.class).isThrownBy(() -> bag.add("foo"));
		assertThatExceptionOfType(UnsupportedOperationException.class).isThrownBy(() -> bag.add("foo", 2L));
		assertThatExceptionOfType(UnsupportedOperationException.class).isThrownBy(() -> bag.remove("foo"));
		assertThatExceptionOfType(UnsupportedOperationException.class).isThrownBy(() -> bag.remove("foo", 2L));
		assertThatExceptionOfType(UnsupportedOperationException.class).isThrownBy(() -> bag.removeAll("foo"));
		assertThatExceptionOfType(UnsupportedOperationException.class).isThrownBy(bag::clear);
		assertThat(bag.count("foo")).isEqualTo(3L);
		assertThat(bag.count("bar")).isEqualTo(2L);
		assertThat(bag.count(null)).isEqualTo(1L);
		assertThat(bag.distinct()).isEqualTo(3L);
		assertThat(bag.size()).isEqualTo(6L);
		assertThat(bag.min().get()).isNull();
		assertThat(bag.max().get()).isEqualTo("foo");
		assertThat(bag.toSet()).containsExactlyInAnyOrder("foo", "bar", null);
		assertThat(bag.toMap()).containsOnly(Map.entry("foo", 3L), Map.entry("bar", 2L), new AbstractMap.SimpleImmutableEntry<>(null, 1L));
	}

	@Test
	void testOfInvalid() {
		assertThatNullPointerException().isThrownBy(() -> Bags.of((Object[]) null));
	}
}