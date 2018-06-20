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

import java.util.*;

/**
 * <p>An utility class that provides {@link Map} tools.</p>
 * @since 1.0.0
 */
public final class Maps {

	/**
	 * <p>Constructor not available.</p>
	 * @since 1.0.0
	 */
	private Maps() {
		// Not available
	}

	/**
	 * <p>Wrap a {@code Map} replacing {@code null} by an empty {@code Map}.</p>
	 * @param map a {@code Map} or {@code null}
	 * @param <K> the type of keys maintained by the map
	 * @param <V> the type of mapped values
	 * @return the non-{@code null} {@code Map}
	 * @since 1.0.0
	 */
	public static <K, V> Map<K, V> nullToEmpty(final Map<K, V> map) {
		return nullToDefault(map, Collections.emptyMap());
	}

	/**
	 * <p>Wrap a {@code SortedMap} replacing {@code null} by an empty {@code SortedMap}.</p>
	 * @param sortedMap a {@code SortedMap} or {@code null}
	 * @param <K> the type of keys maintained by the map
	 * @param <V> the type of mapped values
	 * @return the non-{@code null} {@code SortedMap}
	 * @since 1.0.0
	 */
	public static <K, V> SortedMap<K, V> nullToEmpty(final SortedMap<K, V> sortedMap) {
		return nullToDefault(sortedMap, Collections.emptySortedMap());
	}

	/**
	 * <p>Wrap a {@code NavigableMap} replacing {@code null} by an empty {@code NavigableMap}.</p>
	 * @param navigableMap a {@code NavigableMap} or {@code null}
	 * @param <K> the type of keys maintained by the map
	 * @param <V> the type of mapped values
	 * @return the non-{@code null} {@code NavigableMap}
	 * @since 1.0.0
	 */
	public static <K, V> NavigableMap<K, V> nullToEmpty(final NavigableMap<K, V> navigableMap) {
		return nullToDefault(navigableMap, Collections.emptyNavigableMap());
	}

	/**
	 * <p>Wrap a {@code Map} replacing {@code null} by a default {@code Map}.</p>
	 * @param map a {@code Map} or {@code null}
	 * @param defaultMap the default {@code Map}
	 * @param <K> the type of keys maintained by the map
	 * @param <V> the type of mapped values
	 * @return the non-{@code null} {@code Map}
	 * @throws NullPointerException if the default {@code Map} is {@code null}
	 * @since 1.1.0
	 */
	public static <K, V> Map<K, V> nullToDefault(final Map<K, V> map, final Map<K, V> defaultMap) {
		if (null == defaultMap) {
			throw new NullPointerException("Invalid default map (not null expected)");
		}
		return null != map ? map : defaultMap;
	}

	/**
	 * <p>Wrap a {@code SortedMap} replacing {@code null} by a default {@code SortedMap}.</p>
	 * @param sortedMap a {@code SortedMap} or {@code null}
	 * @param defaultSortedMap the default {@code SortedMap}
	 * @param <K> the type of keys maintained by the map
	 * @param <V> the type of mapped values
	 * @return the non-{@code null} {@code SortedMap}
	 * @throws NullPointerException if the default {@code SortedMap} is {@code null}
	 * @since 1.1.0
	 */
	public static <K, V> SortedMap<K, V> nullToDefault(final SortedMap<K, V> sortedMap, final SortedMap<K, V> defaultSortedMap) {
		if (null == defaultSortedMap) {
			throw new NullPointerException("Invalid default sorted map (not null expected)");
		}
		return null != sortedMap ? sortedMap : defaultSortedMap;
	}

	/**
	 * <p>Wrap a {@code NavigableMap} replacing {@code null} by a default {@code NavigableMap}.</p>
	 * @param navigableMap a {@code NavigableMap} or {@code null}
	 * @param defaultNavigableMap the default {@code NavigableMap}
	 * @param <K> the type of keys maintained by the map
	 * @param <V> the type of mapped values
	 * @return the non-{@code null} {@code NavigableMap}
	 * @throws NullPointerException if the default {@code NavigableMap} is {@code null}
	 * @since 1.1.0
	 */
	public static <K, V> NavigableMap<K, V> nullToDefault(final NavigableMap<K, V> navigableMap, final NavigableMap<K, V> defaultNavigableMap) {
		if (null == defaultNavigableMap) {
			throw new NullPointerException("Invalid default navigable map (not null expected)");
		}
		return null != navigableMap ? navigableMap : defaultNavigableMap;
	}

	/**
	 * <p>Wrap a {@code Map} replacing an empty one by {@code null}.</p>
	 * @param map a {@code Map} or {@code null}
	 * @param <K> the type of keys maintained by the map
	 * @param <V> the type of mapped values
	 * @return the non-empty {@code Map} or {@code null}
	 * @since 1.0.0
	 */
	public static <K, V> Map<K, V> emptyToNull(final Map<K, V> map) {
		return emptyToDefault(map, null);
	}

	/**
	 * <p>Wrap a {@code SortedMap} replacing an empty one by {@code null}.</p>
	 * @param sortedMap a {@code SortedMap} or {@code null}
	 * @param <K> the type of keys maintained by the map
	 * @param <V> the type of mapped values
	 * @return the non-empty {@code SortedMap} or {@code null}
	 * @since 1.0.0
	 */
	public static <K, V> SortedMap<K, V> emptyToNull(final SortedMap<K, V> sortedMap) {
		return emptyToDefault(sortedMap, null);
	}

	/**
	 * <p>Wrap a {@code NavigableMap} replacing an empty one by {@code null}.</p>
	 * @param navigableMap a {@code NavigableMap} or {@code null}
	 * @param <K> the type of keys maintained by the map
	 * @param <V> the type of mapped values
	 * @return the non-empty {@code NavigableMap} or {@code null}
	 * @since 1.0.0
	 */
	public static <K, V> NavigableMap<K, V> emptyToNull(final NavigableMap<K, V> navigableMap) {
		return emptyToDefault(navigableMap, null);
	}

	/**
	 * <p>Wrap a {@code Map} replacing an empty one by a default {@code Map}.</p>
	 * @param map a {@code Map} or {@code null}
	 * @param defaultMap the default {@code Map} or {@code null}
	 * @param <K> the type of keys maintained by the map
	 * @param <V> the type of mapped values
	 * @return the non-empty {@code Map} or {@code null}
	 * @throws IllegalArgumentException if the default {@code Map} is empty
	 * @since 1.1.0
	 */
	public static <K, V> Map<K, V> emptyToDefault(final Map<K, V> map, final Map<K, V> defaultMap) {
		if (null != defaultMap && defaultMap.isEmpty()) {
			throw new IllegalArgumentException("Invalid default map (not empty expected)");
		}
		if (null == map) {
			return null;
		}
		return !map.isEmpty() ? map : defaultMap;
	}

	/**
	 * <p>Wrap a {@code SortedMap} replacing an empty one by a default {@code SortedMap}.</p>
	 * @param sortedMap a {@code SortedMap} or {@code null}
	 * @param defaultSortedMap the default {@code SortedMap} or {@code null}
	 * @param <K> the type of keys maintained by the map
	 * @param <V> the type of mapped values
	 * @return the non-empty {@code SortedMap} or {@code null}
	 * @throws IllegalArgumentException if the default {@code SortedMap} is empty
	 * @since 1.1.0
	 */
	public static <K, V> SortedMap<K, V> emptyToDefault(final SortedMap<K, V> sortedMap, final SortedMap<K, V> defaultSortedMap) {
		if (null != defaultSortedMap && defaultSortedMap.isEmpty()) {
			throw new IllegalArgumentException("Invalid default sorted map (not empty expected)");
		}
		if (null == sortedMap) {
			return null;
		}
		return !sortedMap.isEmpty() ? sortedMap : defaultSortedMap;
	}

	/**
	 * <p>Wrap a {@code NavigableMap} replacing an empty one by a default {@code NavigableMap}.</p>
	 * @param navigableMap a {@code NavigableMap} or {@code null}
	 * @param defaultNavigableMap the default {@code NavigableMap} or {@code null}
	 * @param <K> the type of keys maintained by the map
	 * @param <V> the type of mapped values
	 * @return the non-empty {@code NavigableMap} or {@code null}
	 * @throws IllegalArgumentException if the default {@code NavigableMap} is empty
	 * @since 1.1.0
	 */
	public static <K, V> NavigableMap<K, V> emptyToDefault(final NavigableMap<K, V> navigableMap, final NavigableMap<K, V> defaultNavigableMap) {
		if (null != defaultNavigableMap && defaultNavigableMap.isEmpty()) {
			throw new IllegalArgumentException("Invalid default navigable map (not empty expected)");
		}
		if (null == navigableMap) {
			return null;
		}
		return !navigableMap.isEmpty() ? navigableMap : defaultNavigableMap;
	}

	/**
	 * <p>Create an immutable ordered {@code Map} with an array of entries.</p>
	 * <p><b>Note</b>: {@code null} value may be restricted depending of the {@code Map} implementation.</p>
	 * @param entries entries of the {@code Map}
	 * @param <K> the type of keys maintained by the map
	 * @param <V> the type of mapped values
	 * @return the created immutable ordered {@code Map}
	 * @throws NullPointerException if the entries array is {@code null}
	 * @since 1.0.0
	 */
	@SafeVarargs
	public static <K, V> Map<K, V> ofEntriesOrdered(final Map.Entry<? extends K, ? extends V>... entries) {
		if (null == entries) {
			throw new NullPointerException("Invalid entries (not null expected)");
		}
		if (0 == entries.length) {
			return Collections.emptyMap();
		} else if (1 == entries.length) {
			return Collections.singletonMap(entries[0].getKey(), entries[0].getValue());
		}
		final var map = new LinkedHashMap<K, V>(entries.length);
		for (final var entry : entries) {
			map.put(entry.getKey(), entry.getValue());
		}
		return Collections.unmodifiableMap(map);
	}
}