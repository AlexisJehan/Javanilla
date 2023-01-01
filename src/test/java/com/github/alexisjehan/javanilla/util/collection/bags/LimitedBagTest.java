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
package com.github.alexisjehan.javanilla.util.collection.bags;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

@SuppressWarnings("deprecation")
final class LimitedBagTest extends AbstractBagTest {

	private static final List<String> COLLECTION = List.of("foo", "bar");
	private static final int LIMIT = 2;

	private final LimitedBag<String> limitedBag = new LimitedBag<>(new MapBag<>(COLLECTION), LIMIT);

	@Override
	<E> LimitedBag<E> newBag() {
		return new LimitedBag<>(new MapBag<>(), 2);
	}

	@Test
	void testConstructor() {
		final var limitedBag = new LimitedBag<>(new MapBag<>(List.of("foo", "foo", "bar", "bar", "fooo")), LIMIT);
		assertThat(limitedBag.count("foo")).isEqualTo(2L);
		assertThat(limitedBag.count("bar")).isEqualTo(2L);
		assertThat(limitedBag.count("fooo")).isZero();
		assertThat(limitedBag.getLimit()).isEqualTo(LIMIT);
	}

	@Test
	void testConstructorInvalid() {
		assertThatIllegalArgumentException().isThrownBy(() -> new LimitedBag<>(new MapBag<>(), 1));
	}

	@Test
	@Override
	void testAdd() {
		final var limitedBag = newBag();
		limitedBag.add("foo");
		assertThat(limitedBag.count("foo")).isEqualTo(1L);
		limitedBag.add("bar", 2L);
		assertThat(limitedBag.count("bar")).isEqualTo(2L);
		limitedBag.add("bar", 2L);
		assertThat(limitedBag.count("bar")).isEqualTo(4L);
		limitedBag.add("fooo", 3L);
		assertThat(limitedBag.count("foo")).isZero();
	}

	@Test
	void testEqualsAndHashCodeAndToString() {
		assertThat(limitedBag.equals(limitedBag)).isTrue();
		assertThat(limitedBag).isNotEqualTo(new Object());
		assertThat(new LimitedBag<>(new MapBag<>(COLLECTION), LIMIT)).satisfies(otherBag -> {
			assertThat(limitedBag).isNotSameAs(otherBag);
			assertThat(limitedBag).isEqualTo(otherBag);
			assertThat(limitedBag).hasSameHashCodeAs(otherBag);
			assertThat(limitedBag).hasToString(otherBag.toString());
		});
		assertThat(new LimitedBag<>(new MapBag<>(COLLECTION), 10)).satisfies(otherBag -> {
			assertThat(limitedBag).isNotSameAs(otherBag);
			assertThat(limitedBag).isEqualTo(otherBag);
			assertThat(limitedBag).hasSameHashCodeAs(otherBag);
			assertThat(limitedBag).doesNotHaveToString(otherBag.toString()); // Custom implementation
		});
		assertThat((Bag<String>) new MapBag<>(COLLECTION)).satisfies(otherBag -> {
			assertThat(limitedBag).isNotSameAs(otherBag);
			assertThat(limitedBag).isEqualTo(otherBag);
			assertThat(limitedBag).hasSameHashCodeAs(otherBag);
			assertThat(limitedBag).doesNotHaveToString(otherBag.toString()); // Custom implementation
		});
		assertThat(new LimitedBag<>(new MapBag<>(List.of("foo")), LIMIT)).satisfies(otherBag -> {
			assertThat(limitedBag).isNotSameAs(otherBag);
			assertThat(limitedBag).isNotEqualTo(otherBag);
			assertThat(limitedBag).doesNotHaveSameHashCodeAs(otherBag);
			assertThat(limitedBag).doesNotHaveToString(otherBag.toString());
		});
		assertThat(new LimitedBag<>(new MapBag<>(List.of("fooo", "bar")), LIMIT)).satisfies(otherBag -> {
			assertThat(limitedBag).isNotSameAs(otherBag);
			assertThat(limitedBag).isNotEqualTo(otherBag);
			assertThat(limitedBag).doesNotHaveSameHashCodeAs(otherBag);
			assertThat(limitedBag).doesNotHaveToString(otherBag.toString());
		});
		assertThat(new LimitedBag<>(new MapBag<>(List.of("bar", "bar")), LIMIT)).satisfies(otherBag -> {
			assertThat(limitedBag).isNotSameAs(otherBag);
			assertThat(limitedBag).isNotEqualTo(otherBag);
			assertThat(limitedBag).doesNotHaveSameHashCodeAs(otherBag);
			assertThat(limitedBag).doesNotHaveToString(otherBag.toString());
		});
	}

	@Test
	void testGetLimit() {
		assertThat(limitedBag.getLimit()).isEqualTo(LIMIT);
	}
}