/*
 * MIT License
 *
 * Copyright (c) 2018-2022 Alexis Jehan
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

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

@SuppressWarnings("deprecation")
abstract class AbstractBagTest {

	abstract <E> Bag<E> newBag();

	@Test
	void testAdd() {
		final var bag = newBag();
		assertThat(bag.count("foo")).isZero();
		bag.add("foo");
		assertThat(bag.count("foo")).isEqualTo(1L);
		bag.add("foo", 0L);
		assertThat(bag.count("foo")).isEqualTo(1L);
		bag.add("foo", 10L);
		assertThat(bag.count("foo")).isEqualTo(11L);
	}

	@Test
	void testAddInvalid() {
		assertThatIllegalArgumentException().isThrownBy(() -> newBag().add(1, -1L));
	}

	@Test
	void testRemove() {
		final var bag = newBag();
		assertThat(bag.count("foo")).isZero();
		assertThat(bag.remove("foo")).isFalse();
		assertThat(bag.count("foo")).isZero();
		bag.add("foo", 10L);
		assertThat(bag.count("foo")).isEqualTo(10L);
		assertThat(bag.remove("foo")).isTrue();
		assertThat(bag.count("foo")).isEqualTo(9L);
		assertThat(bag.remove("foo", 0L)).isFalse();
		assertThat(bag.count("foo")).isEqualTo(9L);
		assertThat(bag.remove("foo", 10L)).isTrue();
		assertThat(bag.count("foo")).isZero();
	}

	@Test
	void testRemoveInvalid() {
		assertThatIllegalArgumentException().isThrownBy(() -> newBag().remove(1, -1L));
	}

	@Test
	void testRemoveAll() {
		final var bag = newBag();
		assertThat(bag.count("foo")).isZero();
		assertThat(bag.removeAll("foo")).isFalse();
		assertThat(bag.count("foo")).isZero();
		bag.add("foo", 10L);
		assertThat(bag.count("foo")).isEqualTo(10L);
		assertThat(bag.removeAll("foo")).isTrue();
		assertThat(bag.count("foo")).isZero();
	}

	@Test
	void testClear() {
		final var bag = newBag();
		assertThat(bag.count("foo")).isZero();
		assertThat(bag.count("bar")).isZero();
		bag.clear();
		assertThat(bag.count("foo")).isZero();
		assertThat(bag.count("bar")).isZero();
		bag.add("foo", 10L);
		bag.add("bar", 5L);
		assertThat(bag.count("foo")).isEqualTo(10L);
		assertThat(bag.count("bar")).isEqualTo(5L);
		bag.clear();
		assertThat(bag.count("foo")).isZero();
		assertThat(bag.count("bar")).isZero();
	}

	@Test
	void testIsEmpty() {
		final var bag = newBag();
		assertThat(bag.isEmpty()).isTrue();
		bag.add("foo", 10L);
		assertThat(bag.isEmpty()).isFalse();
		bag.remove("foo", 9L);
		assertThat(bag.isEmpty()).isFalse();
		bag.remove("foo", 1L);
		assertThat(bag.isEmpty()).isTrue();
	}

	@Test
	void testContainsAny() {
		final var bag = newBag();
		assertThat(bag.containsAny("foo")).isFalse();
		bag.add("foo");
		assertThat(bag.containsAny("foo")).isTrue();
		bag.remove("foo");
		assertThat(bag.containsAny("foo")).isFalse();
	}

	@Test
	void testContainsExactly() {
		final var bag = newBag();
		assertThat(bag.containsExactly("foo", 0L)).isTrue();
		assertThat(bag.containsExactly("foo", 2L)).isFalse();
		bag.add("foo", 2L);
		assertThat(bag.containsExactly("foo", 0L)).isFalse();
		assertThat(bag.containsExactly("foo", 2L)).isTrue();
		bag.remove("foo");
		assertThat(bag.containsExactly("foo", 0L)).isFalse();
		assertThat(bag.containsExactly("foo", 2L)).isFalse();
		bag.remove("foo");
		assertThat(bag.containsExactly("foo", 0L)).isTrue();
		assertThat(bag.containsExactly("foo", 2L)).isFalse();
	}

	@Test
	void testContainsExactlyInvalid() {
		assertThatIllegalArgumentException().isThrownBy(() -> newBag().containsExactly(1, -1L));
	}

	@Test
	void testContainsAtLeast() {
		final var bag = newBag();
		assertThat(bag.containsAtLeast("foo", 0L)).isTrue();
		assertThat(bag.containsAtLeast("foo", 2L)).isFalse();
		bag.add("foo", 2L);
		assertThat(bag.containsAtLeast("foo", 0L)).isTrue();
		assertThat(bag.containsAtLeast("foo", 2L)).isTrue();
		bag.remove("foo");
		assertThat(bag.containsAtLeast("foo", 0L)).isTrue();
		assertThat(bag.containsAtLeast("foo", 2L)).isFalse();
		bag.remove("foo");
		assertThat(bag.containsAtLeast("foo", 0L)).isTrue();
		assertThat(bag.containsAtLeast("foo", 2L)).isFalse();
	}

	@Test
	void testContainsAtLeastInvalid() {
		assertThatIllegalArgumentException().isThrownBy(() -> newBag().containsAtLeast(1, -1L));
	}

	@Test
	void testContainsAtMost() {
		final var bag = newBag();
		assertThat(bag.containsAtMost("foo", 0L)).isTrue();
		assertThat(bag.containsAtMost("foo", 2L)).isTrue();
		bag.add("foo", 2L);
		assertThat(bag.containsAtMost("foo", 0L)).isFalse();
		assertThat(bag.containsAtMost("foo", 2L)).isTrue();
		bag.remove("foo");
		assertThat(bag.containsAtMost("foo", 0L)).isFalse();
		assertThat(bag.containsAtMost("foo", 2L)).isTrue();
		bag.remove("foo");
		assertThat(bag.containsAtMost("foo", 0L)).isTrue();
		assertThat(bag.containsAtMost("foo", 2L)).isTrue();
	}

	@Test
	void testContainsAtMostInvalid() {
		assertThatIllegalArgumentException().isThrownBy(() -> newBag().containsAtMost(1, -1L));
	}

	@Test
	void testCount() {
		final var bag = newBag();
		assertThat(bag.count("foo")).isZero();
		bag.add("foo");
		assertThat(bag.count("foo")).isEqualTo(1L);
		bag.remove("foo");
		assertThat(bag.count("foo")).isZero();
	}

	@Test
	void testDistinct() {
		final var bag = newBag();
		assertThat(bag.distinct()).isZero();
		bag.add("foo");
		assertThat(bag.distinct()).isEqualTo(1L);
		bag.add("foo", 2L);
		assertThat(bag.distinct()).isEqualTo(1L);
		bag.add("bar");
		assertThat(bag.distinct()).isEqualTo(2L);
		bag.clear();
		assertThat(bag.distinct()).isZero();
	}

	@Test
	void testSize() {
		final var bag = newBag();
		assertThat(bag.size()).isZero();
		bag.add("foo");
		assertThat(bag.size()).isEqualTo(1L);
		bag.add("foo", 2L);
		assertThat(bag.size()).isEqualTo(3L);
		bag.add("bar");
		assertThat(bag.size()).isEqualTo(4L);
		bag.clear();
		assertThat(bag.size()).isZero();
	}

	@Test
	void testMin() {
		final var bag = newBag();
		assertThat(bag.min().isEmpty()).isTrue();
		bag.add("foo", 3L);
		assertThat(bag.min().get()).isEqualTo("foo");
		bag.add("bar", 2L);
		assertThat(bag.min().get()).isEqualTo("bar");
		bag.remove("foo", 2L);
		assertThat(bag.min().get()).isEqualTo("foo");
		bag.removeAll("foo");
		assertThat(bag.min().get()).isEqualTo("bar");
		bag.removeAll("bar");
		assertThat(bag.min().isEmpty()).isTrue();
	}

	@Test
	void testMax() {
		final var bag = newBag();
		assertThat(bag.max().isEmpty()).isTrue();
		bag.add("foo", 2L);
		assertThat(bag.max().get()).isEqualTo("foo");
		bag.add("bar", 3L);
		assertThat(bag.max().get()).isEqualTo("bar");
		bag.remove("bar", 2L);
		assertThat(bag.max().get()).isEqualTo("foo");
		bag.removeAll("foo");
		assertThat(bag.max().get()).isEqualTo("bar");
		bag.removeAll("bar");
		assertThat(bag.max().isEmpty()).isTrue();
	}

	@Test
	void testToSet() {
		final var bag = newBag();
		assertThat(bag.toSet()).isEmpty();
		bag.add("foo");
		assertThat(bag.toSet()).containsExactlyInAnyOrder("foo");
		bag.add("foo");
		assertThat(bag.toSet()).containsExactlyInAnyOrder("foo");
		bag.add("bar");
		assertThat(bag.toSet()).containsExactlyInAnyOrder("foo", "bar");
		bag.remove("foo");
		assertThat(bag.toSet()).containsExactlyInAnyOrder("foo", "bar");
		bag.remove("foo");
		assertThat(bag.toSet()).containsExactlyInAnyOrder("bar");
		bag.remove("bar");
		assertThat(bag.toSet()).isEmpty();
	}

	@Test
	void testToMap() {
		final var bag = newBag();
		assertThat(bag.toMap()).isEmpty();
		bag.add("foo");
		assertThat(bag.toMap()).containsOnly(Map.entry("foo", 1L));
		bag.add("foo");
		assertThat(bag.toMap()).containsOnly(Map.entry("foo", 2L));
		bag.add("bar");
		assertThat(bag.toMap()).containsOnly(Map.entry("foo", 2L), Map.entry("bar", 1L));
		bag.remove("foo");
		assertThat(bag.toMap()).containsOnly(Map.entry("foo", 1L), Map.entry("bar", 1L));
		bag.remove("foo");
		assertThat(bag.toMap()).containsOnly(Map.entry("bar", 1L));
		bag.remove("bar");
		assertThat(bag.toMap()).isEmpty();
	}
}