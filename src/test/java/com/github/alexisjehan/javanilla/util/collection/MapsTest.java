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
	void testNullToDefault() {
		assertThat(Maps.nullToDefault(null, Collections.singletonMap("bar", 2))).containsExactly(Map.entry("bar", 2));
		assertThat(Maps.nullToDefault(Collections.emptyMap(), Collections.singletonMap("bar", 2))).isEmpty();
		assertThat(Maps.nullToDefault(Collections.singletonMap("foo", 1), Collections.singletonMap("bar", 2))).containsExactly(Map.entry("foo", 1));
	}

	@Test
	void testNullToDefaultInvalid() {
		assertThatNullPointerException().isThrownBy(() -> Maps.nullToDefault(Collections.singletonMap("foo", 1), null));
	}

	@Test
	void testEmptyToNull() {
		assertThat(Maps.emptyToNull((Map<String, Integer>) null)).isNull();
		assertThat(Maps.emptyToNull(Collections.emptyMap())).isNull();
		assertThat(Maps.emptyToNull(Collections.singletonMap("foo", 1))).containsExactly(Map.entry("foo", 1));
	}

	@Test
	void testEmptyToDefault() {
		assertThat(Maps.emptyToDefault(null, Collections.singletonMap("bar", 2))).isNull();
		assertThat(Maps.emptyToDefault(Collections.emptyMap(), Collections.singletonMap("bar", 2))).containsExactly(Map.entry("bar", 2));
		assertThat(Maps.emptyToDefault(Collections.singletonMap("foo", 1), Collections.singletonMap("bar", 2))).containsExactly(Map.entry("foo", 1));
	}

	@Test
	void testEmptyToDefaultInvalid() {
		assertThatIllegalArgumentException().isThrownBy(() -> Maps.emptyToDefault(Collections.singletonMap("foo", 1), Collections.emptyMap()));
	}

	@Test
	void testPutAll() {
		assertThat(Maps.putAll(Collections.emptyMap())).isFalse();
		final var map = new HashMap<>();
		assertThat(Maps.putAll(map, Map.entry("foo", 1), Map.entry("bar", 2))).isTrue();
		assertThat(map).contains(Map.entry("foo", 1), Map.entry("bar", 2));
	}

	@Test
	void testPutAllInvalid() {
		assertThatNullPointerException().isThrownBy(() -> Maps.putAll(null, Map.entry("foo", 1)));
		assertThatNullPointerException().isThrownBy(() -> Maps.putAll(Collections.emptyMap(), (Map.Entry<String, Integer>[]) null));
	}

	@Test
	void testOfOrdered() {
		assertThat(Maps.ofOrdered()).isEmpty();
		assertThat(Maps.ofOrdered(Map.entry("foo", 1))).containsExactly(Map.entry("foo", 1));
		assertThat(Maps.ofOrdered(Map.entry("foo", 1), Map.entry("bar", 2))).containsExactly(Map.entry("foo", 1), Map.entry("bar", 2));
	}

	@Test
	void testOfOrderedInvalid() {
		assertThatNullPointerException().isThrownBy(() -> Maps.ofOrdered((Map.Entry<String, Integer>[]) null));
	}
}