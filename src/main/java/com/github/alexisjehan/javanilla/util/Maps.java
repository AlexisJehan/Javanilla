/*
 * MIT License
 *
 * Copyright (c) 2018-2024 Alexis Jehan
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
package com.github.alexisjehan.javanilla.util;

import com.github.alexisjehan.javanilla.misc.quality.Ensure;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.NavigableMap;
import java.util.SortedMap;

/**
 * A utility class that provides {@link Map} tools.
 * @since 1.8.0
 */
public final class Maps {

	/**
	 * Constructor.
	 * @since 1.8.0
	 */
	private Maps() {}

	/**
	 * Wrap a {@link Map} replacing {@code null} by an empty one.
	 * @param map the {@link Map} or {@code null}
	 * @param <K> the type of keys maintained by the map
	 * @param <V> the type of mapped values
	 * @return a non-{@code null} {@link Map}
	 * @since 1.8.0
	 */
	public static <K, V> Map<K, V> nullToEmpty(final Map<K, V> map) {
		return nullToDefault(map, Map.of());
	}

	/**
	 * Wrap a {@link SortedMap} replacing {@code null} by an empty one.
	 * @param sortedMap the {@link SortedMap} or {@code null}
	 * @param <K> the type of keys maintained by the map
	 * @param <V> the type of mapped values
	 * @return a non-{@code null} {@link SortedMap}
	 * @since 1.8.0
	 */
	public static <K, V> SortedMap<K, V> nullToEmpty(final SortedMap<K, V> sortedMap) {
		return nullToDefault(sortedMap, Collections.emptySortedMap());
	}

	/**
	 * Wrap a {@link NavigableMap} replacing {@code null} by an empty one.
	 * @param navigableMap the {@link NavigableMap} or {@code null}
	 * @param <K> the type of keys maintained by the map
	 * @param <V> the type of mapped values
	 * @return a non-{@code null} {@link NavigableMap}
	 * @since 1.8.0
	 */
	public static <K, V> NavigableMap<K, V> nullToEmpty(final NavigableMap<K, V> navigableMap) {
		return nullToDefault(navigableMap, Collections.emptyNavigableMap());
	}

	/**
	 * Wrap a {@link Map} replacing {@code null} by a default one.
	 * @param map the {@link Map} or {@code null}
	 * @param defaultMap the default {@link Map}
	 * @param <M> the {@link Map} type
	 * @return a non-{@code null} {@link Map}
	 * @throws NullPointerException if the default {@link Map} is {@code null}
	 * @since 1.8.0
	 */
	public static <M extends Map<?, ?>> M nullToDefault(final M map, final M defaultMap) {
		Ensure.notNull("defaultMap", defaultMap);
		return null != map ? map : defaultMap;
	}

	/**
	 * Wrap a {@link Map} replacing an empty one by {@code null}.
	 * @param map the {@link Map} or {@code null}
	 * @param <M> the {@link Map} type
	 * @return a non-empty {@link Map} or {@code null}
	 * @since 1.8.0
	 */
	public static <M extends Map<?, ?>> M emptyToNull(final M map) {
		return emptyToDefault(map, null);
	}

	/**
	 * Wrap a {@link Map} replacing an empty one by a default {@link Map}.
	 * @param map the {@link Map} or {@code null}
	 * @param defaultMap the default {@link Map} or {@code null}
	 * @param <M> the {@link Map} type
	 * @return a non-empty {@link Map} or {@code null}
	 * @throws IllegalArgumentException if the default {@link Map} is empty
	 * @since 1.8.0
	 */
	public static <M extends Map<?, ?>> M emptyToDefault(final M map, final M defaultMap) {
		if (null != defaultMap) {
			Ensure.notNullAndNotEmpty("defaultMap", defaultMap);
		}
		return null == map || !map.isEmpty() ? map : defaultMap;
	}

	/**
	 * Put all the entries to the specified {@link Map}.
	 * @param map the {@link Map} to put entries to
	 * @param entries the entries array
	 * @param <K> the type of keys maintained by the map
	 * @param <V> the type of mapped values
	 * @return {@code true} if at least one entry has been put
	 * @throws NullPointerException if the {@link Map} or the entries array is {@code null}
	 * @since 1.8.0
	 */
	@SafeVarargs
	@SuppressWarnings("varargs")
	public static <K, V> boolean putAll(final Map<K, V> map, final Map.Entry<? extends K, ? extends V>... entries) {
		Ensure.notNull("map", map);
		Ensure.notNullAndNotNullElements("entries", entries);
		var result = false;
		for (final var entry : entries) {
			map.put(entry.getKey(), entry.getValue());
			result = true;
		}
		return result;
	}

	/**
	 * Create an ordered {@link Map} from multiple entries.
	 * @param entries the entries array to convert
	 * @param <K> the type of keys maintained by the map
	 * @param <V> the type of mapped values
	 * @return the created ordered {@link Map}
	 * @throws NullPointerException if the entries array is {@code null}
	 * @since 1.8.0
	 */
	@SafeVarargs
	@SuppressWarnings("varargs")
	public static <K, V> Map<K, V> ofOrdered(final Map.Entry<? extends K, ? extends V>... entries) {
		Ensure.notNullAndNotNullElements("entries", entries);
		if (0 == entries.length) {
			return Map.ofEntries();
		}
		if (1 == entries.length) {
			return Map.ofEntries(entries[0]);
		}
		final var map = new LinkedHashMap<K, V>(entries.length);
		putAll(map, entries);
		return map;
	}
}