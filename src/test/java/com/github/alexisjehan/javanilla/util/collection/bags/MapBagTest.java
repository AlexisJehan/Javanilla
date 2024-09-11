/*
 * MIT License
 *
 * Copyright (c) 2018-2024 Alexis Jehan
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
package com.github.alexisjehan.javanilla.util.collection.bags;

import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.LongAdder;
import java.util.function.Supplier;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNullPointerException;

@Deprecated
final class MapBagTest extends AbstractBagTest {

	private static final List<String> COLLECTION = List.of("foo", "bar");

	private final MapBag<String> mapBag = new MapBag<>(COLLECTION);

	@Override
	<E> MapBag<E> newBag() {
		return new MapBag<>();
	}

	@Test
	void testConstructorSupplier() {
		final var fooAdder = new LongAdder();
		fooAdder.add(10);
		final var barAdder = new LongAdder();
		barAdder.add(-10);
		assertThat(new MapBag<>(() -> new HashMap<>(Map.ofEntries(Map.entry("foo", fooAdder), Map.entry("bar", barAdder))))).satisfies(otherMapBag -> {
			assertThat(otherMapBag.count("foo")).isEqualTo(10);
			assertThat(otherMapBag.count("bar")).isZero();
		});
	}

	@Test
	void testConstructorCollection() {
		assertThat(new MapBag<>(List.of()).isEmpty()).isTrue();
		assertThat(new MapBag<>(List.of("foo", "foo", "bar"))).satisfies(otherMapBag -> {
			assertThat(otherMapBag.count("foo")).isEqualTo(2);
			assertThat(otherMapBag.count("bar")).isEqualTo(1);
		});
	}

	@Test
	void testConstructorInvalid() {
		assertThatNullPointerException().isThrownBy(() -> new MapBag<>((Supplier<Map<String, LongAdder>>) null));
		assertThatNullPointerException().isThrownBy(() -> new MapBag<>(() -> null));
		assertThatNullPointerException().isThrownBy(() -> new MapBag<>((Collection<String>) null));
	}

	@Test
	void testEqualsAndHashCodeAndToString() {
		assertThat(mapBag.equals(mapBag)).isTrue();
		assertThat(mapBag).isNotEqualTo(new Object());
		assertThat(new MapBag<>(COLLECTION)).satisfies(otherMapBag -> {
			assertThat(otherMapBag).isNotSameAs(mapBag);
			assertThat(otherMapBag).isEqualTo(mapBag);
			assertThat(otherMapBag).hasSameHashCodeAs(mapBag);
			assertThat(otherMapBag).hasToString(mapBag.toString());
		});
		assertThat(new MapBag<>(List.of("foo", "bar", "bar"))).satisfies(otherMapBag -> {
			assertThat(otherMapBag).isNotSameAs(mapBag);
			assertThat(otherMapBag).isNotEqualTo(mapBag);
			assertThat(otherMapBag).doesNotHaveSameHashCodeAs(mapBag);
			assertThat(otherMapBag).doesNotHaveToString(mapBag.toString());
		});
		assertThat(new MapBag<>(List.of("foo"))).satisfies(otherMapBag -> {
			assertThat(otherMapBag).isNotSameAs(mapBag);
			assertThat(otherMapBag).isNotEqualTo(mapBag);
			assertThat(otherMapBag).doesNotHaveSameHashCodeAs(mapBag);
			assertThat(otherMapBag).doesNotHaveToString(mapBag.toString());
		});
		assertThat(new MapBag<>(List.of("fooo", "bar"))).satisfies(otherMapBag -> {
			assertThat(otherMapBag).isNotSameAs(mapBag);
			assertThat(otherMapBag).isNotEqualTo(mapBag);
			assertThat(otherMapBag).doesNotHaveSameHashCodeAs(mapBag);
			assertThat(otherMapBag).doesNotHaveToString(mapBag.toString());
		});
		assertThat(new MapBag<>(List.of("bar", "bar"))).satisfies(otherMapBag -> {
			assertThat(otherMapBag).isNotSameAs(mapBag);
			assertThat(otherMapBag).isNotEqualTo(mapBag);
			assertThat(otherMapBag).doesNotHaveSameHashCodeAs(mapBag);
			assertThat(otherMapBag).doesNotHaveToString(mapBag.toString());
		});
	}
}