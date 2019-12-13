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

package com.github.alexisjehan.javanilla.util.collection.bags;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

/**
 * <p>{@link LimitedBag} unit tests.</p>
 */
final class LimitedBagTest {

	@Test
	void testConstructor() {
		final var bag = new LimitedBag<>(new MapBag<>(List.of("foo", "foo", "bar", "bar", "fooo")), 2);
		assertThat(bag.count("foo")).isEqualTo(2L);
		assertThat(bag.count("bar")).isEqualTo(2L);
		assertThat(bag.count("fooo")).isEqualTo(0L);
	}

	@Test
	void testConstructorInvalid() {
		assertThatIllegalArgumentException().isThrownBy(() -> new LimitedBag<>(new MapBag<>(), 1));
	}

	@Test
	void testAdd() {
		final var bag = new LimitedBag<>(new MapBag<>(), 2);
		bag.add("foo");
		assertThat(bag.count("foo")).isEqualTo(1L);
		bag.add("bar", 2L);
		assertThat(bag.count("bar")).isEqualTo(2L);
		bag.add("bar", 2L);
		assertThat(bag.count("bar")).isEqualTo(4L);
		bag.add("fooo", 3L);
		assertThat(bag.count("foo")).isEqualTo(0L);
	}

	@Test
	void testEqualsHashCodeToString() {
		final var bag = new LimitedBag<>(new MapBag<>(List.of("foo", "bar")), 2);
		assertThat(bag).isEqualTo(bag);
		assertThat(bag).isNotEqualTo(1);
		{
			final var otherBag = new LimitedBag<>(new MapBag<>(List.of("foo", "bar")), 2);
			assertThat(bag).isNotSameAs(otherBag);
			assertThat(bag).isEqualTo(otherBag);
			assertThat(bag).hasSameHashCodeAs(otherBag);
			assertThat(bag).hasToString(otherBag.toString());
		}
		{
			final var otherBag = new LimitedBag<>(new MapBag<>(List.of("foo", "bar")), 10);
			assertThat(bag).isNotSameAs(otherBag);
			assertThat(bag).isEqualTo(otherBag);
			assertThat(bag).hasSameHashCodeAs(otherBag);
			assertThat(bag.toString()).isNotEqualTo(otherBag.toString()); // Distinct representation
		}
		{
			final var otherBag = new LimitedBag<>(new MapBag<>(List.of("foo")), 2);
			assertThat(bag).isNotSameAs(otherBag);
			assertThat(bag).isNotEqualTo(otherBag);
			assertThat(bag.hashCode()).isNotEqualTo(otherBag.hashCode());
			assertThat(bag.toString()).isNotEqualTo(otherBag.toString());
		}
		{
			final var otherBag = new LimitedBag<>(new MapBag<>(List.of("fooo", "bar")), 2);
			assertThat(bag).isNotSameAs(otherBag);
			assertThat(bag).isNotEqualTo(otherBag);
			assertThat(bag.hashCode()).isNotEqualTo(otherBag.hashCode());
			assertThat(bag.toString()).isNotEqualTo(otherBag.toString());
		}
		{
			final var otherBag = new LimitedBag<>(new MapBag<>(List.of("bar", "bar")), 2);
			assertThat(bag).isNotSameAs(otherBag);
			assertThat(bag).isNotEqualTo(otherBag);
			assertThat(bag.hashCode()).isNotEqualTo(otherBag.hashCode());
			assertThat(bag.toString()).isNotEqualTo(otherBag.toString());
		}
	}
}