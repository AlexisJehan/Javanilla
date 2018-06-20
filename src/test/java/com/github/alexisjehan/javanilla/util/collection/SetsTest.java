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
package com.github.alexisjehan.javanilla.util.collection;

import org.junit.jupiter.api.Test;

import java.util.*;

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
	void testNullToDefaultSet() {
		assertThat(Sets.nullToDefault(null, Collections.singleton("foo"))).containsExactly("foo");
		assertThat(Sets.nullToDefault(Collections.emptySet(), Collections.singleton("foo"))).isEmpty();
		assertThat(Sets.nullToDefault(Collections.singleton("foo"), Collections.singleton("foo"))).containsExactly("foo");
	}

	@Test
	void testNullToDefaultSortedSet() {
		assertThat(Sets.nullToDefault((SortedSet<String>) null, new TreeSet<>(Collections.singleton("foo")))).containsExactly("foo");
		assertThat(Sets.nullToDefault(Collections.emptySortedSet(), new TreeSet<>(Collections.singleton("foo")))).isEmpty();
		assertThat(Sets.nullToDefault((SortedSet<String>) new TreeSet<>(Collections.singleton("foo")), new TreeSet<>(Collections.singleton("foo")))).containsExactly("foo");
	}

	@Test
	void testNullToDefaultNavigableSet() {
		assertThat(Sets.nullToDefault(null, new TreeSet<>(Collections.singleton("foo")))).containsExactly("foo");
		assertThat(Sets.nullToDefault(Collections.emptyNavigableSet(), new TreeSet<>(Collections.singleton("foo")))).isEmpty();
		assertThat(Sets.nullToDefault(new TreeSet<>(Collections.singleton("foo")), new TreeSet<>(Collections.singleton("foo")))).containsExactly("foo");
	}

	@Test
	void testNullToDefaultNull() {
		assertThatNullPointerException().isThrownBy(() -> Sets.nullToDefault(Collections.emptySet(), null));
		assertThatNullPointerException().isThrownBy(() -> Sets.nullToDefault(Collections.emptySortedSet(), null));
		assertThatNullPointerException().isThrownBy(() -> Sets.nullToDefault(Collections.emptyNavigableSet(), null));
	}

	@Test
	void testEmptyToNullSet() {
		assertThat(Sets.emptyToNull((Set<String>) null)).isNull();
		assertThat(Sets.emptyToNull(Collections.emptySet())).isNull();
		assertThat(Sets.emptyToNull(Collections.singleton("foo"))).containsExactly("foo");
	}

	@Test
	void testEmptyToNullSortedSet() {
		assertThat(Sets.emptyToNull((SortedSet<String>) null)).isNull();
		assertThat(Sets.emptyToNull(Collections.emptySortedSet())).isNull();
		assertThat(Sets.emptyToNull((SortedSet<String>) new TreeSet<>(Collections.singleton("foo")))).containsExactly("foo");
	}

	@Test
	void testEmptyToNullNavigableSet() {
		assertThat(Sets.emptyToNull(null)).isNull();
		assertThat(Sets.emptyToNull(Collections.emptyNavigableSet())).isNull();
		assertThat(Sets.emptyToNull(new TreeSet<>(Collections.singleton("foo")))).containsExactly("foo");
	}

	@Test
	void testEmptyToDefaultSet() {
		assertThat(Sets.emptyToDefault(null, Collections.singleton("foo"))).isNull();
		assertThat(Sets.emptyToDefault(Collections.emptySet(), Collections.singleton("foo"))).containsExactly("foo");
		assertThat(Sets.emptyToDefault(Collections.singleton("foo"), Collections.singleton("foo"))).containsExactly("foo");
	}

	@Test
	void testEmptyToDefaultSortedSet() {
		assertThat(Sets.emptyToDefault((SortedSet<String>) null, new TreeSet<>(Collections.singleton("foo")))).isNull();
		assertThat(Sets.emptyToDefault(Collections.emptySortedSet(), new TreeSet<>(Collections.singleton("foo")))).containsExactly("foo");
		assertThat(Sets.emptyToDefault((SortedSet<String>) new TreeSet<>(Collections.singleton("foo")), new TreeSet<>(Collections.singleton("foo")))).containsExactly("foo");
	}

	@Test
	void testEmptyToDefaultNavigableSet() {
		assertThat(Sets.emptyToDefault(null, new TreeSet<>(Collections.singleton("foo")))).isNull();
		assertThat(Sets.emptyToDefault(Collections.emptyNavigableSet(), new TreeSet<>(Collections.singleton("foo")))).containsExactly("foo");
		assertThat(Sets.emptyToDefault(new TreeSet<>(Collections.singleton("foo")), new TreeSet<>(Collections.singleton("foo")))).containsExactly("foo");
	}

	@Test
	void testEmptyToDefaultNull() {
		assertThatIllegalArgumentException().isThrownBy(() -> Sets.emptyToDefault(Collections.emptySet(), Collections.emptySet()));
		assertThatIllegalArgumentException().isThrownBy(() -> Sets.emptyToDefault(Collections.emptySortedSet(), Collections.emptySortedSet()));
		assertThatIllegalArgumentException().isThrownBy(() -> Sets.emptyToDefault(Collections.emptyNavigableSet(), Collections.emptyNavigableSet()));
	}

	@Test
	void testOfEntriesOrdered() {
		assertThat(Sets.ofOrdered()).isEmpty();
		assertThat(Sets.ofOrdered("foo")).containsExactly("foo");
		assertThat(Sets.ofOrdered("foo", "bar")).containsExactly("foo", "bar");
	}

	@Test
	void testOfEntriesOrderedNull() {
		assertThatNullPointerException().isThrownBy(() -> Sets.ofOrdered((String[]) null));
	}
}