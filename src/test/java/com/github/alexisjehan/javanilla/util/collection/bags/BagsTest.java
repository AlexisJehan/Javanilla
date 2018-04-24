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
package com.github.alexisjehan.javanilla.util.collection.bags;

import org.junit.jupiter.api.Test;

import java.util.AbstractMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.*;

/**
 * <p>{@link Bags} unit tests.</p>
 */
final class BagsTest {

	@Test
	void testEmpty() {
		final var emptyBag = Bags.empty();
		assertThatExceptionOfType(UnsupportedOperationException.class).isThrownBy(() -> emptyBag.add("foo"));
		assertThatExceptionOfType(UnsupportedOperationException.class).isThrownBy(() -> emptyBag.remove("foo"));
		assertThatExceptionOfType(UnsupportedOperationException.class).isThrownBy(emptyBag::clear);
		assertThat(emptyBag.count("foo")).isEqualTo(0L);
		assertThat(emptyBag.count("bar")).isEqualTo(0L);
		assertThat(emptyBag.count(null)).isEqualTo(0L);
		assertThat(emptyBag.distinct()).isEqualTo(0L);
		assertThat(emptyBag.size()).isEqualTo(0L);
		assertThat(emptyBag.min()).isEmpty();
		assertThat(emptyBag.max()).isEmpty();
		assertThat(emptyBag.toSet()).isEmpty();
		assertThat(emptyBag.toMap()).isEmpty();
	}

	@Test
	void testNullToEmpty() {
		assertThat(Bags.nullToEmpty(null).isEmpty()).isTrue();
		assertThat(Bags.nullToEmpty(Bags.empty()).isEmpty()).isTrue();
		assertThat(Bags.nullToEmpty(Bags.singleton("foo")).isEmpty()).isFalse();
	}

	@Test
	void testEmptyToNull() {
		assertThat(Bags.emptyToNull(null)).isNull();
		assertThat(Bags.emptyToNull(Bags.empty())).isNull();
		assertThat(Bags.emptyToNull(Bags.singleton("foo"))).isNotNull();
	}

	@Test
	void testUnModifiable() {
		final var unmodifiableBag = Bags.unmodifiable(Bags.of("foo", "foo", "bar"));
		assertThat(unmodifiableBag.count("foo")).isEqualTo(2L);
		assertThat(unmodifiableBag.count("bar")).isEqualTo(1L);
		assertThatExceptionOfType(UnsupportedOperationException.class).isThrownBy(() -> unmodifiableBag.add("foo"));
		assertThatExceptionOfType(UnsupportedOperationException.class).isThrownBy(() -> unmodifiableBag.remove("foo"));
		assertThatExceptionOfType(UnsupportedOperationException.class).isThrownBy(unmodifiableBag::clear);
	}

	@Test
	void testUnModifiableNull() {
		assertThatNullPointerException().isThrownBy(() -> Bags.unmodifiable(null));
	}

	@Test
	void testSingleton() {
		final var singletonBag = Bags.singleton("foo", 5L);
		assertThatExceptionOfType(UnsupportedOperationException.class).isThrownBy(() -> singletonBag.add("foo"));
		assertThatExceptionOfType(UnsupportedOperationException.class).isThrownBy(() -> singletonBag.remove("foo"));
		assertThatExceptionOfType(UnsupportedOperationException.class).isThrownBy(singletonBag::clear);
		assertThat(singletonBag.count("foo")).isEqualTo(5L);
		assertThat(singletonBag.count("bar")).isEqualTo(0L);
		assertThat(singletonBag.count(null)).isEqualTo(0L);
		assertThat(singletonBag.distinct()).isEqualTo(1L);
		assertThat(singletonBag.size()).isEqualTo(5L);
		assertThat(singletonBag.min()).hasValue("foo");
		assertThat(singletonBag.max()).hasValue("foo");
		assertThat(singletonBag.toSet()).containsExactlyInAnyOrder("foo");
		assertThat(singletonBag.toMap()).containsOnly(Map.entry("foo", 5L));
	}

	@Test
	void testSingletonZero() {
		final var singletonBag = Bags.singleton("foo", 0L);
		assertThatExceptionOfType(UnsupportedOperationException.class).isThrownBy(() -> singletonBag.add("foo"));
		assertThatExceptionOfType(UnsupportedOperationException.class).isThrownBy(() -> singletonBag.remove("foo"));
		assertThatExceptionOfType(UnsupportedOperationException.class).isThrownBy(singletonBag::clear);
		assertThat(singletonBag.count("foo")).isEqualTo(0L);
		assertThat(singletonBag.count("bar")).isEqualTo(0L);
		assertThat(singletonBag.count(null)).isEqualTo(0L);
		assertThat(singletonBag.distinct()).isEqualTo(0L);
		assertThat(singletonBag.size()).isEqualTo(0L);
		assertThat(singletonBag.min()).isEmpty();
		assertThat(singletonBag.max()).isEmpty();
		assertThat(singletonBag.toSet()).isEmpty();
		assertThat(singletonBag.toMap()).isEmpty();
	}

	@Test
	void testSingletonNull() {
		final var singletonBag = Bags.singleton(null, 5L);
		assertThatExceptionOfType(UnsupportedOperationException.class).isThrownBy(() -> singletonBag.add("foo"));
		assertThatExceptionOfType(UnsupportedOperationException.class).isThrownBy(() -> singletonBag.remove("foo"));
		assertThatExceptionOfType(UnsupportedOperationException.class).isThrownBy(singletonBag::clear);
		assertThat(singletonBag.count("foo")).isEqualTo(0L);
		assertThat(singletonBag.count("bar")).isEqualTo(0L);
		assertThat(singletonBag.count(null)).isEqualTo(5L);
		assertThat(singletonBag.distinct()).isEqualTo(1L);
		assertThat(singletonBag.size()).isEqualTo(5L);
		assertThat(singletonBag.min()).isEmpty();
		assertThat(singletonBag.max()).isEmpty();
		assertThat(singletonBag.toSet()).containsExactlyInAnyOrder((String) null);
		assertThat(singletonBag.toMap()).containsOnly(new AbstractMap.SimpleImmutableEntry<>(null, 5L));
	}

	@Test
	void testSingletonInvalid() {
		assertThatIllegalArgumentException().isThrownBy(() -> Bags.singleton("foo", -1L));
	}

	@Test
	void testOf() {
		final var bag = Bags.of("foo", "foo", "foo", "bar", "bar", null);
		assertThatExceptionOfType(UnsupportedOperationException.class).isThrownBy(() -> bag.add("foo"));
		assertThatExceptionOfType(UnsupportedOperationException.class).isThrownBy(() -> bag.remove("foo"));
		assertThatExceptionOfType(UnsupportedOperationException.class).isThrownBy(bag::clear);
		assertThat(bag.count("foo")).isEqualTo(3L);
		assertThat(bag.count("bar")).isEqualTo(2L);
		assertThat(bag.count(null)).isEqualTo(1L);
		assertThat(bag.distinct()).isEqualTo(3L);
		assertThat(bag.size()).isEqualTo(6L);
		assertThat(bag.min()).isEmpty();
		assertThat(bag.max()).hasValue("foo");
		assertThat(bag.toSet()).containsExactlyInAnyOrder("foo", "bar", null);
		assertThat(bag.toMap()).containsOnly(Map.entry("foo", 3L), Map.entry("bar", 2L), new AbstractMap.SimpleImmutableEntry<>(null, 1L));
	}

	@Test
	void testOfNull() {
		assertThatNullPointerException().isThrownBy(() -> Bags.of((Object[]) null));
	}
}