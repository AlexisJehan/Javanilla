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
		assertThat(Sets.nullToEmpty(Collections.emptySet())).isEmpty();
		assertThat(Sets.nullToEmpty(Collections.singleton("foo"))).containsExactly("foo");
	}

	@Test
	void testNullToEmptySortedSet() {
		assertThat(Sets.nullToEmpty((SortedSet<String>) null)).isEmpty();
		assertThat(Sets.nullToEmpty(Collections.emptySortedSet())).isEmpty();
		assertThat(Sets.nullToEmpty((SortedSet<String>) new TreeSet<>(Collections.singleton("foo")))).containsExactly("foo");
	}

	@Test
	void testNullToEmptyNavigableSet() {
		assertThat(Sets.nullToEmpty(null)).isEmpty();
		assertThat(Sets.nullToEmpty(Collections.emptyNavigableSet())).isEmpty();
		assertThat(Sets.nullToEmpty(new TreeSet<>(Collections.singleton("foo")))).containsExactly("foo");
	}

	@Test
	void testNullToDefault() {
		assertThat(Sets.nullToDefault(null, Collections.singleton("bar"))).containsExactly("bar");
		assertThat(Sets.nullToDefault(Collections.emptySet(), Collections.singleton("bar"))).isEmpty();
		assertThat(Sets.nullToDefault(Collections.singleton("foo"), Collections.singleton("bar"))).containsExactly("foo");
	}

	@Test
	void testNullToDefaultInvalid() {
		assertThatNullPointerException().isThrownBy(() -> Sets.nullToDefault(Collections.singleton("foo"), null));
	}

	@Test
	void testEmptyToNull() {
		assertThat(Sets.emptyToNull((Set<String>) null)).isNull();
		assertThat(Sets.emptyToNull(Collections.emptySet())).isNull();
		assertThat(Sets.emptyToNull(Collections.singleton("foo"))).containsExactly("foo");
	}

	@Test
	void testEmptyToDefault() {
		assertThat(Sets.emptyToDefault(null, Collections.singleton("bar"))).isNull();
		assertThat(Sets.emptyToDefault(Collections.emptySet(), Collections.singleton("bar"))).containsExactly("bar");
		assertThat(Sets.emptyToDefault(Collections.singleton("foo"), Collections.singleton("bar"))).containsExactly("foo");
	}

	@Test
	void testEmptyToDefaultInvalid() {
		assertThatIllegalArgumentException().isThrownBy(() -> Sets.emptyToDefault(Collections.singleton("foo"), Collections.emptySet()));
	}

	@Test
	void testUnion() {
		assertThat(Sets.union()).isEmpty();
		assertThat(Sets.union(Set.of(1, 2))).containsExactlyInAnyOrder(1, 2);
		assertThat(Sets.union(Set.of(1, 2), Set.of(2, 3))).containsExactlyInAnyOrder(1, 2, 3);
	}

	@Test
	void testUnionInvalid() {
		assertThatNullPointerException().isThrownBy(() -> Sets.union((Set<?>[]) null));
		assertThatNullPointerException().isThrownBy(() -> Sets.union((Collection<Set<?>>) null));
		assertThatNullPointerException().isThrownBy(() -> Sets.union((Set<?>) null));
	}

	@Test
	void testIntersect() {
		assertThat(Sets.intersect()).isEmpty();
		assertThat(Sets.intersect(Set.of(1, 2))).containsExactlyInAnyOrder(1, 2);
		assertThat(Sets.intersect(Set.of(1, 2), Set.of(2, 3))).containsExactlyInAnyOrder(2);
	}

	@Test
	void testIntersectInvalid() {
		assertThatNullPointerException().isThrownBy(() -> Sets.intersect((Set<?>[]) null));
		assertThatNullPointerException().isThrownBy(() -> Sets.intersect((Collection<Set<?>>) null));
		assertThatNullPointerException().isThrownBy(() -> Sets.intersect((Set<?>) null));
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