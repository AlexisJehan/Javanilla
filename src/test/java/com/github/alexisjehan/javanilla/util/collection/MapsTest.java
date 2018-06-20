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
 * <p>{@link Maps} unit tests.</p>
 */
final class MapsTest {

	@Test
	void testNullToEmptyMap() {
		assertThat(Maps.nullToEmpty((Map<String, Integer>) null)).isEmpty();
		assertThat(Maps.nullToEmpty(Collections.emptyMap())).isEmpty();
		assertThat(Maps.nullToEmpty(Collections.singletonMap("foo", 1))).containsExactly(Map.entry("foo", 1));
	}

	@Test
	void testNullToEmptySortedMap() {
		assertThat(Maps.nullToEmpty((SortedMap<String, Integer>) null)).isEmpty();
		assertThat(Maps.nullToEmpty(Collections.emptySortedMap())).isEmpty();
		assertThat(Maps.nullToEmpty((SortedMap<String, Integer>) new TreeMap<>(Collections.singletonMap("foo", 1)))).containsExactly(Map.entry("foo", 1));
	}

	@Test
	void testNullToEmptyNavigableMap() {
		assertThat(Maps.nullToEmpty(null)).isEmpty();
		assertThat(Maps.nullToEmpty(Collections.emptyNavigableMap())).isEmpty();
		assertThat(Maps.nullToEmpty(new TreeMap<>(Collections.singletonMap("foo", 1)))).containsExactly(Map.entry("foo", 1));
	}

	@Test
	void testNullToDefaultMap() {
		assertThat(Maps.nullToDefault(null, Collections.singletonMap("foo", 1))).containsExactly(Map.entry("foo", 1));
		assertThat(Maps.nullToDefault(Collections.emptyMap(), Collections.singletonMap("foo", 1))).isEmpty();
		assertThat(Maps.nullToDefault(Collections.singletonMap("foo", 1), Collections.singletonMap("foo", 1))).containsExactly(Map.entry("foo", 1));
	}

	@Test
	void testNullToDefaultSortedMap() {
		assertThat(Maps.nullToDefault((SortedMap<String, Integer>) null, new TreeMap<>(Collections.singletonMap("foo", 1)))).containsExactly(Map.entry("foo", 1));
		assertThat(Maps.nullToDefault(Collections.emptySortedMap(), new TreeMap<>(Collections.singletonMap("foo", 1)))).isEmpty();
		assertThat(Maps.nullToDefault((SortedMap<String, Integer>) new TreeMap<>(Collections.singletonMap("foo", 1)), new TreeMap<>(Collections.singletonMap("foo", 1)))).containsExactly(Map.entry("foo", 1));
	}

	@Test
	void testNullToDefaultNavigableMap() {
		assertThat(Maps.nullToDefault(null, new TreeMap<>(Collections.singletonMap("foo", 1)))).containsExactly(Map.entry("foo", 1));
		assertThat(Maps.nullToDefault(Collections.emptyNavigableMap(), new TreeMap<>(Collections.singletonMap("foo", 1)))).isEmpty();
		assertThat(Maps.nullToDefault(new TreeMap<>(Collections.singletonMap("foo", 1)), new TreeMap<>(Collections.singletonMap("foo", 1)))).containsExactly(Map.entry("foo", 1));
	}

	@Test
	void testNullToDefaultNull() {
		assertThatNullPointerException().isThrownBy(() -> Maps.nullToDefault(Collections.emptyMap(), null));
		assertThatNullPointerException().isThrownBy(() -> Maps.nullToDefault(Collections.emptySortedMap(), null));
		assertThatNullPointerException().isThrownBy(() -> Maps.nullToDefault(Collections.emptyNavigableMap(), null));
	}

	@Test
	void testEmptyToNullMap() {
		assertThat(Maps.emptyToNull((Map<String, Integer>) null)).isNull();
		assertThat(Maps.emptyToNull(Collections.emptyMap())).isNull();
		assertThat(Maps.emptyToNull(Collections.singletonMap("foo", 1))).containsExactly(Map.entry("foo", 1));
	}

	@Test
	void testEmptyToNullSortedMap() {
		assertThat(Maps.emptyToNull((SortedMap<String, Integer>) null)).isNull();
		assertThat(Maps.emptyToNull(Collections.emptySortedMap())).isNull();
		assertThat(Maps.emptyToNull((SortedMap<String, Integer>) new TreeMap<>(Collections.singletonMap("foo", 1)))).containsExactly(Map.entry("foo", 1));
	}

	@Test
	void testEmptyToNullNavigableMap() {
		assertThat(Maps.emptyToNull(null)).isNull();
		assertThat(Maps.emptyToNull(Collections.emptyNavigableMap())).isNull();
		assertThat(Maps.emptyToNull(new TreeMap<>(Collections.singletonMap("foo", 1)))).containsExactly(Map.entry("foo", 1));
	}

	@Test
	void testEmptyToDefaultMap() {
		assertThat(Maps.emptyToDefault(null, Collections.singletonMap("foo", 1))).isNull();
		assertThat(Maps.emptyToDefault(Collections.emptyMap(), Collections.singletonMap("foo", 1))).containsExactly(Map.entry("foo", 1));
		assertThat(Maps.emptyToDefault(Collections.singletonMap("foo", 1), Collections.singletonMap("foo", 1))).containsExactly(Map.entry("foo", 1));
	}

	@Test
	void testEmptyToDefaultSortedMap() {
		assertThat(Maps.emptyToDefault((SortedMap<String, Integer>) null, new TreeMap<>(Collections.singletonMap("foo", 1)))).isNull();
		assertThat(Maps.emptyToDefault(Collections.emptySortedMap(), new TreeMap<>(Collections.singletonMap("foo", 1)))).containsExactly(Map.entry("foo", 1));
		assertThat(Maps.emptyToDefault((SortedMap<String, Integer>) new TreeMap<>(Collections.singletonMap("foo", 1)), new TreeMap<>(Collections.singletonMap("foo", 1)))).containsExactly(Map.entry("foo", 1));
	}

	@Test
	void testEmptyToDefaultNavigableMap() {
		assertThat(Maps.emptyToDefault(null, new TreeMap<>(Collections.singletonMap("foo", 1)))).isNull();
		assertThat(Maps.emptyToDefault(Collections.emptyNavigableMap(), new TreeMap<>(Collections.singletonMap("foo", 1)))).containsExactly(Map.entry("foo", 1));
		assertThat(Maps.emptyToDefault(new TreeMap<>(Collections.singletonMap("foo", 1)), new TreeMap<>(Collections.singletonMap("foo", 1)))).containsExactly(Map.entry("foo", 1));
	}

	@Test
	void testEmptyToDefaultInvalid() {
		assertThatIllegalArgumentException().isThrownBy(() -> Maps.emptyToDefault(Collections.emptyMap(), Collections.emptyMap()));
		assertThatIllegalArgumentException().isThrownBy(() -> Maps.emptyToDefault(Collections.emptySortedMap(), Collections.emptySortedMap()));
		assertThatIllegalArgumentException().isThrownBy(() -> Maps.emptyToDefault(Collections.emptyNavigableMap(), Collections.emptyNavigableMap()));
	}

	@Test
	void testOfEntriesOrdered() {
		assertThat(Maps.ofEntriesOrdered()).isEmpty();
		assertThat(Maps.ofEntriesOrdered(Map.entry("foo", 1))).containsExactly(Map.entry("foo", 1));
		assertThat(Maps.ofEntriesOrdered(Map.entry("foo", 1), Map.entry("bar", 2))).containsExactly(Map.entry("foo", 1), Map.entry("bar", 2));
	}

	@Test
	void testOfEntriesOrderedNull() {
		assertThatNullPointerException().isThrownBy(() -> Maps.ofEntriesOrdered((Map.Entry<String, Integer>[]) null));
	}
}