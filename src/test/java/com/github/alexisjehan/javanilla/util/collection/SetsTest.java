/*
 * MIT License
 *
 * Copyright (c) 2018-2020 Alexis Jehan
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
package com.github.alexisjehan.javanilla.util.collection;

import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.Collections;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.assertj.core.api.Assertions.assertThatNullPointerException;

/**
 * <p>{@link Sets} unit tests.</p>
 */
final class SetsTest {

	@Test
	void testNullToEmptySet() {
		assertThat(Sets.nullToEmpty((Set<String>) null)).isEmpty();
		assertThat(Sets.nullToEmpty(Set.of())).isEmpty();
		assertThat(Sets.nullToEmpty(Set.of("foo"))).containsExactly("foo");
	}

	@Test
	void testNullToEmptySortedSet() {
		assertThat(Sets.nullToEmpty((SortedSet<String>) null)).isEmpty();
		assertThat(Sets.nullToEmpty(Collections.emptySortedSet())).isEmpty();
		assertThat(Sets.nullToEmpty((SortedSet<String>) new TreeSet<>(Set.of("foo")))).containsExactly("foo");
	}

	@Test
	void testNullToEmptyNavigableSet() {
		assertThat(Sets.nullToEmpty(null)).isEmpty();
		assertThat(Sets.nullToEmpty(Collections.emptyNavigableSet())).isEmpty();
		assertThat(Sets.nullToEmpty(new TreeSet<>(Set.of("foo")))).containsExactly("foo");
	}

	@Test
	void testNullToDefault() {
		assertThat(Sets.nullToDefault(null, Set.of("bar"))).containsExactly("bar");
		assertThat(Sets.nullToDefault(Set.of(), Set.of("bar"))).isEmpty();
		assertThat(Sets.nullToDefault(Set.of("foo"), Set.of("bar"))).containsExactly("foo");
	}

	@Test
	void testNullToDefaultInvalid() {
		assertThatNullPointerException().isThrownBy(() -> Sets.nullToDefault(Set.of("foo"), null));
	}

	@Test
	void testEmptyToNull() {
		assertThat(Sets.emptyToNull((Set<String>) null)).isNull();
		assertThat(Sets.emptyToNull(Set.of())).isNull();
		assertThat(Sets.emptyToNull(Set.of("foo"))).containsExactly("foo");
	}

	@Test
	void testEmptyToDefault() {
		assertThat(Sets.emptyToDefault(null, Set.of("bar"))).isNull();
		assertThat(Sets.emptyToDefault(Set.of(), Set.of("bar"))).containsExactly("bar");
		assertThat(Sets.emptyToDefault(Set.of("foo"), Set.of("bar"))).containsExactly("foo");
	}

	@Test
	void testEmptyToDefaultInvalid() {
		assertThatIllegalArgumentException().isThrownBy(() -> Sets.emptyToDefault(Set.of("foo"), Set.of()));
	}

	@Test
	void testUnion() {
		assertThat(Sets.union()).isEmpty();
		assertThat(Sets.union(Set.of(1, 2))).containsExactlyInAnyOrder(1, 2);
		assertThat(Sets.union(Set.of(1, 2), Set.of(2, 3))).containsExactlyInAnyOrder(1, 2, 3);
	}

	@Test
	void testUnionInvalid() {
		assertThatNullPointerException().isThrownBy(() -> Sets.union((Set<Integer>[]) null));
		assertThatNullPointerException().isThrownBy(() -> Sets.union((Collection<Set<Integer>>) null));
		assertThatNullPointerException().isThrownBy(() -> Sets.union((Set<Integer>) null));
	}

	@Test
	void testIntersect() {
		assertThat(Sets.intersect()).isEmpty();
		assertThat(Sets.intersect(Set.of(1, 2))).containsExactlyInAnyOrder(1, 2);
		assertThat(Sets.intersect(Set.of(1, 2), Set.of(2, 3))).containsExactlyInAnyOrder(2);
	}

	@Test
	void testIntersectInvalid() {
		assertThatNullPointerException().isThrownBy(() -> Sets.intersect((Set<Integer>[]) null));
		assertThatNullPointerException().isThrownBy(() -> Sets.intersect((Collection<Set<Integer>>) null));
		assertThatNullPointerException().isThrownBy(() -> Sets.intersect((Set<Integer>) null));
	}

	@Test
	void testOfOrdered() {
		assertThat(Sets.ofOrdered()).isEmpty();
		assertThat(Sets.ofOrdered("foo")).containsExactly("foo");
		assertThat(Sets.ofOrdered("foo", "bar")).containsExactly("foo", "bar");
	}

	@Test
	void testOfOrderedInvalid() {
		assertThatNullPointerException().isThrownBy(() -> Sets.ofOrdered((String[]) null));
	}
}