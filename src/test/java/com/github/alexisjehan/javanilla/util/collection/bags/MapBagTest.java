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

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.LongAdder;
import java.util.function.Supplier;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNullPointerException;

/**
 * <p>{@link MapBag} unit tests.</p>
 */
final class MapBagTest extends AbstractBagTest {

	@Override
	<E> Bag<E> newBag() {
		return new MapBag<>();
	}

	@Test
	void testConstructorSupplier() {
		final var adder1 = new LongAdder();
		adder1.add(10);
		final var adder2 = new LongAdder();
		adder2.add(-10);
		final var bag = new MapBag<>(() -> new HashMap<>(Map.ofEntries(Map.entry("foo", adder1), Map.entry("bar", adder2))));
		assertThat(bag.count("foo")).isEqualTo(10);
		assertThat(bag.count("bar")).isEqualTo(0);
	}

	@Test
	void testConstructorCollection() {
		final var bag = new MapBag<>(List.of("foo", "foo", "bar"));
		assertThat(bag.count("foo")).isEqualTo(2);
		assertThat(bag.count("bar")).isEqualTo(1);
		assertThat(new MapBag<>(Set.of()).isEmpty()).isTrue();
	}

	@Test
	void testConstructorInvalid() {
		assertThatNullPointerException().isThrownBy(() -> new MapBag<>((Supplier<Map<String, LongAdder>>) null));
		assertThatNullPointerException().isThrownBy(() -> new MapBag<>(() -> null));
		assertThatNullPointerException().isThrownBy(() -> new MapBag<>((Collection<String>) null));
	}

	@Test
	void testEqualsHashCodeToString() {
		final var bag = new MapBag<>(List.of("foo", "bar"));
		assertThat(bag).isEqualTo(bag);
		assertThat(bag).isNotEqualTo(1);
		{
			final var otherBag = new MapBag<>(List.of("foo", "bar"));
			assertThat(bag).isEqualTo(otherBag);
			assertThat(bag).hasSameHashCodeAs(otherBag);
			assertThat(bag).hasToString(otherBag.toString());
		}
		{
			final var otherBag = new MapBag<>(List.of("foo", "bar", "bar"));
			assertThat(bag).isNotEqualTo(otherBag);
			assertThat(bag.hashCode()).isNotEqualTo(otherBag.hashCode());
			assertThat(bag.toString()).isNotEqualTo(otherBag.toString());
		}
		{
			final var otherBag = new MapBag<>(List.of("foo"));
			assertThat(bag).isNotEqualTo(otherBag);
			assertThat(bag.hashCode()).isNotEqualTo(otherBag.hashCode());
			assertThat(bag.toString()).isNotEqualTo(otherBag.toString());
		}
		{
			final var otherBag = new MapBag<>(List.of("fooo", "bar"));
			assertThat(bag).isNotEqualTo(otherBag);
			assertThat(bag.hashCode()).isNotEqualTo(otherBag.hashCode());
			assertThat(bag.toString()).isNotEqualTo(otherBag.toString());
		}
		{
			final var otherBag = new MapBag<>(List.of("bar", "bar"));
			assertThat(bag).isNotEqualTo(otherBag);
			assertThat(bag.hashCode()).isNotEqualTo(otherBag.hashCode());
			assertThat(bag.toString()).isNotEqualTo(otherBag.toString());
		}
	}
}