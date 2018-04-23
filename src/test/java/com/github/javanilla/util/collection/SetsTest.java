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
package com.github.javanilla.util.collection;

import org.junit.jupiter.api.Test;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNullPointerException;

/**
 * <p>{@link Sets} unit tests.</p>
 */
final class SetsTest {

	@Test
	void testNullToEmptySet() {
		assertThat(Sets.nullToEmpty((Set<Object>) null)).isEmpty();
		assertThat(Sets.nullToEmpty(Collections.emptySet())).isEmpty();
		assertThat(Sets.nullToEmpty(Collections.singleton("foo"))).isNotEmpty();
	}

	@Test
	void testNullToEmptySortedSet() {
		assertThat(Sets.nullToEmpty((SortedSet<Object>) null)).isEmpty();
		assertThat(Sets.nullToEmpty(Collections.emptySortedSet())).isEmpty();
		assertThat(Sets.nullToEmpty((SortedSet<String>) new TreeSet<>(Collections.singleton("foo")))).isNotEmpty();
	}

	@Test
	void testNullToEmptyNavigableSet() {
		assertThat(Sets.nullToEmpty(null)).isEmpty();
		assertThat(Sets.nullToEmpty(Collections.emptyNavigableSet())).isEmpty();
		assertThat(Sets.nullToEmpty(new TreeSet<>(Collections.singleton("foo")))).isNotEmpty();
	}

	@Test
	void testEmptyToNullSet() {
		assertThat(Sets.emptyToNull((Set<Object>) null)).isNull();
		assertThat(Sets.emptyToNull(Collections.emptySet())).isNull();
		assertThat(Sets.emptyToNull(Collections.singleton("foo"))).isNotNull();
	}

	@Test
	void testEmptyToNullSortedSet() {
		assertThat(Sets.emptyToNull((SortedSet<Object>) null)).isNull();
		assertThat(Sets.emptyToNull(Collections.emptySortedSet())).isNull();
		assertThat(Sets.emptyToNull((SortedSet<String>) new TreeSet<>(Collections.singleton("foo")))).isNotNull();
	}

	@Test
	void testEmptyToNullNavigableSet() {
		assertThat(Sets.emptyToNull(null)).isNull();
		assertThat(Sets.emptyToNull(Collections.emptyNavigableSet())).isNull();
		assertThat(Sets.emptyToNull(new TreeSet<>(Collections.singleton("foo")))).isNotNull();
	}

	@Test
	void testOfEntriesOrdered() {
		assertThat(Sets.ofOrdered()).isEmpty();
		assertThat(Sets.ofOrdered("foo")).containsExactly("foo");
		assertThat(Sets.ofOrdered("foo", "bar")).containsExactly("foo", "bar");
	}

	@Test
	void testOfEntriesOrderedNull() {
		assertThatNullPointerException().isThrownBy(() -> Sets.ofOrdered((Object[]) null));
	}
}