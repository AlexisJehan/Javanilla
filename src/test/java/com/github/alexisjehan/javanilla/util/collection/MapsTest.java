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
package com.github.alexisjehan.javanilla.util.collection;

import com.github.alexisjehan.javanilla.lang.array.ObjectArrays;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.assertj.core.api.Assertions.assertThatNullPointerException;

final class MapsTest {

	private static final Map.Entry<String, Integer>[] ENTRIES = ObjectArrays.of(Map.entry("foo", 1), Map.entry("bar", 2));

	@Test
	void testNullToEmptyMap() {
		assertThat(Maps.nullToEmpty((Map<String, Integer>) null)).isEmpty();
		assertThat(Maps.nullToEmpty(Map.ofEntries())).isEmpty();
		assertThat(Maps.nullToEmpty(Map.ofEntries(ENTRIES))).containsOnly(ENTRIES);
	}

	@Test
	void testNullToEmptySortedMap() {
		assertThat(Maps.nullToEmpty((SortedMap<String, Integer>) null)).isEmpty();
		assertThat(Maps.nullToEmpty((SortedMap<String, Integer>) new TreeMap<>(Map.<String, Integer>ofEntries()))).isEmpty();
		assertThat(Maps.nullToEmpty((SortedMap<String, Integer>) new TreeMap<>(Map.ofEntries(ENTRIES)))).containsOnly(ENTRIES);
	}

	@Test
	void testNullToEmptyNavigableMap() {
		assertThat(Maps.nullToEmpty(null)).isEmpty();
		assertThat(Maps.nullToEmpty(new TreeMap<>(Map.<String, Integer>ofEntries()))).isEmpty();
		assertThat(Maps.nullToEmpty(new TreeMap<>(Map.ofEntries(ENTRIES)))).containsOnly(ENTRIES);
	}

	@Test
	void testNullToDefault() {
		assertThat(Maps.nullToDefault(null, Map.of("-", 0))).containsOnly(Map.entry("-", 0));
		assertThat(Maps.nullToDefault(Map.ofEntries(), Map.of("-", 0))).isEmpty();
		assertThat(Maps.nullToDefault(Map.ofEntries(ENTRIES), Map.of("-", 0))).containsOnly(ENTRIES);
	}

	@Test
	void testNullToDefaultInvalid() {
		assertThatNullPointerException().isThrownBy(() -> Maps.nullToDefault(Map.ofEntries(ENTRIES), null));
	}

	@Test
	void testEmptyToNull() {
		assertThat(Maps.emptyToNull((Map<String, Integer>) null)).isNull();
		assertThat(Maps.emptyToNull(Map.ofEntries())).isNull();
		assertThat(Maps.emptyToNull(Map.ofEntries(ENTRIES))).containsOnly(ENTRIES);
	}

	@Test
	void testEmptyToDefault() {
		assertThat(Maps.emptyToDefault(null, Map.of("-", 0))).isNull();
		assertThat(Maps.emptyToDefault(Map.ofEntries(), Map.of("-", 0))).containsOnly(Map.entry("-", 0));
		assertThat(Maps.emptyToDefault(Map.ofEntries(ENTRIES), Map.of("-", 0))).containsOnly(ENTRIES);
	}

	@Test
	void testEmptyToDefaultInvalid() {
		assertThatIllegalArgumentException().isThrownBy(() -> Maps.emptyToDefault(Map.ofEntries(ENTRIES), Map.ofEntries()));
	}

	@Test
	void testPutAll() {
		assertThat(Maps.putAll(Map.ofEntries())).isFalse();
		final var map = new HashMap<>();
		assertThat(Maps.putAll(map, ENTRIES)).isTrue();
		assertThat(map).containsOnly(ENTRIES);
	}

	@Test
	void testPutAllInvalid() {
		assertThatNullPointerException().isThrownBy(() -> Maps.putAll(null, ENTRIES));
		assertThatNullPointerException().isThrownBy(() -> Maps.putAll(Map.ofEntries(), (Map.Entry<String, Integer>[]) null));
		assertThatNullPointerException().isThrownBy(() -> Maps.putAll(Map.ofEntries(), (Map.Entry<String, Integer>) null));
	}

	@Test
	void testOfOrdered() {
		assertThat(Maps.ofOrdered()).isEmpty();
		assertThat(Maps.ofOrdered(ENTRIES[0])).containsExactly(ENTRIES[0]);
		assertThat(Maps.ofOrdered(ENTRIES)).containsExactly(ENTRIES);
	}

	@Test
	void testOfOrderedInvalid() {
		assertThatNullPointerException().isThrownBy(() -> Maps.ofOrdered((Map.Entry<String, Integer>[]) null));
		assertThatNullPointerException().isThrownBy(() -> Maps.ofOrdered((Map.Entry<String, Integer>) null));
	}
}